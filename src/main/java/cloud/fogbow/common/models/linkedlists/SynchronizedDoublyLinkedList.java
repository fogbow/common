package cloud.fogbow.common.models.linkedlists;


import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.InternalServerErrorException;

public class SynchronizedDoublyLinkedList<T> implements ChainedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private Node<T> current;

    public SynchronizedDoublyLinkedList() {
        this.head = this.tail = this.current = null;
    }

    public synchronized Node<T> getCurrent() {
        return this.current;
    }

    public synchronized Node<T> getHead() {
        return this.head;
    }

    public synchronized Node<T> getTail() {
        return this.tail;
    }

    @Override
    public synchronized void addItem(T item) throws InternalServerErrorException {
        if (item == null) {
            throw new InternalServerErrorException(Messages.Exception.ATTEMPTING_TO_ADD_A_NULL_ITEM);
        }
        if (this.head == null) {
            Node<T> firstNode = new Node<>(null, item, null);
            this.tail = this.head = this.current = firstNode;
        } else {
            Node<T> newItem = new Node<>(this.tail, item, null);
            this.tail.setNext(newItem);
            this.tail = newItem;
            /**
             * The check below is useful when current pointer just passed by all the list and was
             * pointing to null, and a new item was inserted at the end of this list. So, current
             * should point to the new inserted item (new tail), instead of null.
             */
            if (this.current == null) {
                this.current = this.tail;
            }
        }
    }

    @Override
    public synchronized void resetPointer() {
        this.current = this.head;
    }

    @Override
    public synchronized T getNext() {
        if (this.current == null) {
            return null;
        }
        T currentItem = this.current.getValue();
        this.current = this.current.getNext();
        return currentItem;
    }

    /**
     * This method removes a given item. Note that this remove method should not modify the current
     * pointer (i.e., after removing, the current pointer, must point to the same element before
     * this operation), unless the item to be removed, is the one in current pointer. In this case,
     * we must remove this order, and point current to the next one.
     *
     * @param item
     * @return True if it was removed from the list. False, if another thread removed this order
     * from the list and the item couldn't be find.
     * @throws InternalServerErrorException when the passed parameter is null (should never occur)
     */
    @Override
    public synchronized boolean removeItem(T item) throws InternalServerErrorException {
        if (item == null) {
            throw new InternalServerErrorException(Messages.Exception.ATTEMPTING_TO_REMOVE_A_NULL_ITEM);
        }
        Node<T> nodeToRemove = findNodeToRemove(item);
        if (nodeToRemove == null) {
            return false;
        }
        if (nodeToRemove.getPrevious() != null) {
            nodeToRemove.getPrevious().setNext(nodeToRemove.getNext());
        } else { // removing the head
            this.head = nodeToRemove.getNext();
        }
        if (nodeToRemove.getNext() != null) {
            nodeToRemove.getNext().setPrevious(nodeToRemove.getPrevious());
        } else { // removing the tail
            this.tail = nodeToRemove.getPrevious();
        }
        if (this.current == nodeToRemove) { // fix current, if current was pointing to cell just removed
            this.current = nodeToRemove.getNext();
        }
        return true;
    }

    /**
     * @param item - Never null
     */
    protected synchronized Node<T> findNodeToRemove(T item) {
        Node<T> currentNode = this.head;
        while (currentNode != null) {
            if (item == currentNode.getValue()) {
                return currentNode;
            }
            currentNode = currentNode.getNext();
        }
        return null;
    }
}
