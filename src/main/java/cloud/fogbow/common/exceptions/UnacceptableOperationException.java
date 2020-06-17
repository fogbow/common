package cloud.fogbow.common.exceptions;

import cloud.fogbow.common.constants.Messages;

public class UnacceptableOperationException extends FogbowException {
    private static final long serialVersionUID = 1L;

    public UnacceptableOperationException() {
        super(Messages.Exception.NO_AVAILABLE_RESOURCES);
    }

    public UnacceptableOperationException(Throwable cause) {
        super(Messages.Exception.NO_AVAILABLE_RESOURCES, cause);
    }

    public UnacceptableOperationException(String message) {
        super(message);
    }

    public UnacceptableOperationException(String message, Throwable cause) {
        super(message, cause);
    }

}
