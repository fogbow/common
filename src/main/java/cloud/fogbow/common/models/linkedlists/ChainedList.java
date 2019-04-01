package cloud.fogbow.common.models.linkedlists;

public interface ChainedList<T> {

    public void addItem(T item);

    public void resetPointer();

    public T getNext();

    public boolean removeItem(T item);
}
