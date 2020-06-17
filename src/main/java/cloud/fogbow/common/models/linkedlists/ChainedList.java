package cloud.fogbow.common.models.linkedlists;

import cloud.fogbow.common.exceptions.UnexpectedException;

public interface ChainedList<T> {

    public void addItem(T item) throws UnexpectedException;

    public void resetPointer();

    public T getNext();

    public boolean removeItem(T item) throws UnexpectedException;
}
