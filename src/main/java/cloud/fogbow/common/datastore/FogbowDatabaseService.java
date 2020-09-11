package cloud.fogbow.common.datastore;

import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.InternalServerErrorException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.TransactionSystemException;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

public class FogbowDatabaseService<T> {
    public static final String SIZE_CONSTRAINT_MESSAGE = "{javax.validation.constraints.Size.message}";

    public <S extends T> void safeSave(S o, JpaRepository<T, ?> repository) throws InternalServerErrorException {
        try {
            repository.save(o);
        } catch (RuntimeException e) {
            if (isSizeViolation(e)) {
                // transaction needs to be cleared before saving again
                safeFlush(repository);

                StorableObjectTruncateHelper<S> truncateHelper = new StorableObjectTruncateHelper<>(o.getClass());
                S truncated = truncateHelper.truncate(o);

                saveTruncatedObject(truncated, repository);
            } else {
                throw new InternalServerErrorException(e.getMessage());
            }
        }
    }

    private void saveTruncatedObject(T truncatedObject, JpaRepository<T, ?> repository)
            throws InternalServerErrorException {
        try {
            repository.save(truncatedObject);
        } catch (DataIntegrityViolationException e) {
            throw new InternalServerErrorException(Messages.Exception.DATABASE_INTEGRITY_VIOLATED);
        }
    }

    /**
     * Clears the transaction silently.
     *
     * @param repository
     */
    private void safeFlush(JpaRepository repository) {
        try {
            repository.flush();
        } catch (Exception e) {
        }
    }

    private boolean isSizeViolation(RuntimeException e) {
        if (e instanceof ConstraintViolationException) {
            return isSizeViolation((ConstraintViolationException) e);
        } else if (e instanceof TransactionSystemException) {
            return isSizeViolation((TransactionSystemException) e);
        }
        return false;
    }

    private boolean isSizeViolation(TransactionSystemException e) {
        Throwable e1 = e.getCause();
        if (e1 != null && e1 instanceof RollbackException) {
            Throwable e2 = e1.getCause();
            if (e2 != null && e2 instanceof ConstraintViolationException) {
                ConstraintViolationException constraintViolationException = (ConstraintViolationException) e2;
                return isSizeViolation(constraintViolationException);
            }
        }
        return false;
    }

    private boolean isSizeViolation(ConstraintViolationException e) {
        ConstraintViolationException constraintViolationException = e;
        Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
        if (constraintViolations.iterator().hasNext()) {
            ConstraintViolation<?> constraintViolation = constraintViolations.iterator().next();
            return constraintViolation.getMessageTemplate().equals(SIZE_CONSTRAINT_MESSAGE);
        }
        return false;
    }
}
