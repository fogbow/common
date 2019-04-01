package cloud.fogbow.common.models.linkedlists;

public class Node<T> {
    private Node<T> next;
    private Node<T> previous;
    private T value;

    public Node(Node previous, T value, Node next) {
        this.previous = previous;
        this.value = value;
        this.next = next;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
