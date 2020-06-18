package cloud.fogbow.common.models.linkedlists;

import cloud.fogbow.common.exceptions.InternalServerErrorException;

public interface ChainedList<T> {

    public void addItem(T item) throws InternalServerErrorException;

    public void resetPointer();

    public T getNext();

    public boolean removeItem(T item) throws InternalServerErrorException;
}
