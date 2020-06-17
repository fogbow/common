package cloud.fogbow.common.models.linkedlists;

import cloud.fogbow.common.constants.Messages;
import cloud.fogbow.common.exceptions.UnexpectedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SynchronizedDoublyLinkedListTest {

    private SynchronizedDoublyLinkedList<Integer> integerList;

    @Before
    public void initialize() {
        this.integerList = new SynchronizedDoublyLinkedList<>();
    }

    // test case: Adding an element to the list should reflect on getHead()
    // getCurrent() and getTail().
    @Test
    public void testAddFirst() throws UnexpectedException {
        // verify
        Assert.assertNull(this.integerList.getHead());
        Assert.assertNull(this.integerList.getCurrent());
        Assert.assertNull(this.integerList.getTail());

        // set up
        Integer number = new Integer(1);

        // exercise
        this.integerList.addItem(number);

        // verify
        Assert.assertEquals(number, this.integerList.getHead().getValue());
        Assert.assertEquals(number, this.integerList.getCurrent().getValue());
        Assert.assertEquals(number, this.integerList.getTail().getValue());
    }

    // test case: When a new element is added to the list and the current pointer
    // already points to the end of it, a newly added element should be the next element.
    @Test
    public void testAddOrder() throws UnexpectedException {
        // verify
        Assert.assertNull(this.integerList.getHead());

        // set up
        Integer numberOne = 1;
        Integer numberTwo = 2;
        Integer numberThree = 3;

        // exercise
        this.integerList.addItem(numberOne);
        this.integerList.addItem(numberTwo);

        // verify
        Assert.assertEquals(numberOne, this.integerList.getNext());
        Assert.assertEquals(numberTwo, this.integerList.getNext());
        // Next node of the second item is null.
        Assert.assertNull(this.integerList.getNext());

        // exercise
        this.integerList.addItem(numberThree);

        // verify
        // addItem() should have fixed this.current to point to the newly item added
        // to the tail of the list.
        Assert.assertEquals(numberThree, this.integerList.getNext());
    }

    // test case: Adding a null element to the list should throw an UnexpectedException.
    @Test
    public void testAddNullOrder() {
        // verify
        Assert.assertNull(this.integerList.getHead());

        try {
            // set up
            // exercise
            this.integerList.addItem(null);
            Assert.fail("Null item should not be added.");
        } catch (UnexpectedException e) {
            // verify
            Assert.assertEquals(Messages.Exception.ATTEMPTING_TO_ADD_A_NULL_ITEM, e.getMessage());
        }
    }

    // test case: Navigating the list once, reseting the pointer and navigating again should
    // produce the same item of visited elements.
    @Test
    public void testResetPointer() throws UnexpectedException {
        // verify
        Assert.assertNull(this.integerList.getHead());

        // set up
        Integer numberOne = 1;
        Integer numberTwo = 2;

        // exercise
        this.integerList.addItem(numberOne);
        this.integerList.addItem(numberTwo);

        // verify
        Assert.assertEquals(numberOne, this.integerList.getNext());
        Assert.assertEquals(numberTwo, this.integerList.getNext());
        Assert.assertNull(this.integerList.getNext());

        // exercise
        this.integerList.resetPointer();

        // verify
        Assert.assertEquals(numberOne, this.integerList.getNext());
        Assert.assertEquals(numberTwo, this.integerList.getNext());
        Assert.assertNull(this.integerList.getNext());
    }

    // test case: Removing a null item should throw an UnexpectedException.
    @Test
    public void testRemoveNullItem() {
        // verify
        Assert.assertNull(this.integerList.getHead());

        try {
            // set up
            // exercise
            this.integerList.removeItem(null);
            Assert.fail("Null item should not be removed.");
        } catch (UnexpectedException e) {
            // verify
            Assert.assertEquals(Messages.Exception.ATTEMPTING_TO_REMOVE_A_NULL_ITEM, e.getMessage());
        }
    }

    // test case: Searching for an element that was added to the list should
    // return that element.
    @Test
    public void testFindNodeToRemove() throws UnexpectedException {
        // verify
        Assert.assertNull(this.integerList.getHead());

        // set up
        Integer numberOne = 1;
        Integer numberTwo = 2;
        Integer numberThree = 3;
        Integer numberFour = 4;

        // exercise
        this.integerList.addItem(numberOne);
        this.integerList.addItem(numberTwo);
        this.integerList.addItem(numberThree);
        this.integerList.addItem(numberFour);

        Node<Integer> nodeToRemove = this.integerList.findNodeToRemove(numberTwo);

        // verify
        Assert.assertEquals(numberOne, this.integerList.getHead().getValue());
        Assert.assertEquals(numberTwo, nodeToRemove.getValue());
        Assert.assertEquals(numberOne, nodeToRemove.getPrevious().getValue());
        Assert.assertEquals(numberThree, nodeToRemove.getNext().getValue());
        Assert.assertEquals(numberFour, this.integerList.getTail().getValue());
    }

    // test case: Removing an element that is the head of the list should
    // update the references of the pointers head and current.
    @Test
    public void testRemoveItemOnHead() throws UnexpectedException {
        // verify
        Assert.assertNull(this.integerList.getHead());

        // set up
        Integer numberOne = 1;
        Integer numberTwo = 2;
        Integer numberThree = 3;
        Integer numberFour = 4;

        // exercise
        this.integerList.addItem(numberOne);
        this.integerList.addItem(numberTwo);
        this.integerList.addItem(numberThree);
        this.integerList.addItem(numberFour);

        // verify
        Assert.assertEquals(numberOne, this.integerList.getHead().getValue());
        Assert.assertEquals(numberFour, this.integerList.getTail().getValue());

        Assert.assertEquals(numberOne, this.integerList.getCurrent().getValue());

        // exercise
        this.integerList.removeItem(numberOne);

        // verify
        Assert.assertEquals(numberTwo, this.integerList.getCurrent().getValue());
        Assert.assertEquals(numberTwo, this.integerList.getHead().getValue());
        Assert.assertNull(this.integerList.getHead().getPrevious());
        Assert.assertEquals(numberFour, this.integerList.getTail().getValue());
        Assert.assertEquals(numberTwo, this.integerList.getNext());
        Assert.assertEquals(numberThree, this.integerList.getNext());
        Assert.assertEquals(numberFour, this.integerList.getNext());
        Assert.assertNull(this.integerList.getNext());
    }

    // test case: Removing an element that is the tail of the list should update
    // the tail pointer of the list.
    @Test
    public void testRemoveItemOnTail() throws UnexpectedException {
        // verify
        Assert.assertNull(this.integerList.getHead());

        // set up
        Integer numberOne = 1;
        Integer numberTwo = 2;
        Integer numberThree = 3;

        // exercise
        this.integerList.addItem(numberOne);
        this.integerList.addItem(numberTwo);
        this.integerList.addItem(numberThree);

        // verify
        Assert.assertEquals(numberOne, this.integerList.getHead().getValue());
        Assert.assertEquals(numberThree, this.integerList.getTail().getValue());

        // exercise
        this.integerList.removeItem(numberThree);

        // verify
        Assert.assertEquals(numberOne, this.integerList.getHead().getValue());
        Assert.assertEquals(numberTwo, this.integerList.getTail().getValue());
        Assert.assertEquals(numberOne, this.integerList.getNext());
        Assert.assertEquals(numberTwo, this.integerList.getNext());
        Assert.assertNull(this.integerList.getNext());
    }

    // test case: A list containing one element should have the head, current,
    // tail and next pointers to null.
    @Test
    public void testRemoveItemOneElementOnList() throws UnexpectedException {
        // verify
        Assert.assertNull(this.integerList.getHead());

        // set up
        Integer numberOne = 1;

        // exercise
        this.integerList.addItem(numberOne);

        // verify
        Assert.assertEquals(numberOne, this.integerList.getHead().getValue());
        Assert.assertEquals(numberOne, this.integerList.getCurrent().getValue());
        Assert.assertEquals(numberOne, this.integerList.getTail().getValue());

        // exercise
        this.integerList.removeItem(numberOne);

        // verify
        Assert.assertNull(this.integerList.getHead());
        Assert.assertNull(this.integerList.getCurrent());
        Assert.assertNull(this.integerList.getTail());
        Assert.assertNull(this.integerList.getNext());
    }

    // test case: Removing elements from the middle of the list should keep
    // its consistency. E.g.: removing 2 from [1, 2, 3] should produce [1, 3].
    @Test
    public void testRemoveItem() throws Exception {
        // verify
        Assert.assertNull(this.integerList.getHead());

        // set up
        Integer numberOne = 1;
        Integer numberTwo = 2;
        Integer numberThree = 3;
        Integer numberFour = 4;

        // exercise
        this.integerList.addItem(numberOne);
        this.integerList.addItem(numberTwo);
        this.integerList.addItem(numberThree);
        this.integerList.addItem(numberFour);
        this.integerList.removeItem(numberThree);

        // verify
        Assert.assertEquals(numberOne, this.integerList.getHead().getValue());
        Assert.assertEquals(numberFour, this.integerList.getTail().getValue());
        Assert.assertEquals(numberOne, this.integerList.getNext());
        Assert.assertEquals(numberTwo, this.integerList.getNext());
        Assert.assertEquals(numberFour, this.integerList.getNext());
        Assert.assertNull(this.integerList.getNext());

        // exercise
        this.integerList.resetPointer();
        this.integerList.removeItem(numberTwo);

        // verify
        Assert.assertEquals(numberOne, this.integerList.getHead().getValue());
        Assert.assertEquals(numberFour, this.integerList.getTail().getValue());
        Assert.assertEquals(numberOne, this.integerList.getNext());
        Assert.assertEquals(numberFour, this.integerList.getNext());
        Assert.assertNull(this.integerList.getNext());
    }

    // test case: A list that had all of its elements removed should behave like
    // a newly created list.
    @Test
    public void testReinitializingList() throws UnexpectedException {
        // verify
        Assert.assertNull(this.integerList.getHead());

        // set up
        Integer numberOne = 1;
        Integer numberTwo = 2;

        // exercise
        this.integerList.addItem(numberOne);

        // verify
        Assert.assertEquals(numberOne, this.integerList.getHead().getValue());
        Assert.assertEquals(numberOne, this.integerList.getCurrent().getValue());
        Assert.assertEquals(numberOne, this.integerList.getTail().getValue());
        Assert.assertEquals(numberOne, this.integerList.getNext());
        Assert.assertNull(this.integerList.getNext());

        // exercise
        this.integerList.removeItem(numberOne);

        // verify
        Assert.assertNull(this.integerList.getHead());
        Assert.assertNull(this.integerList.getCurrent());
        Assert.assertNull(this.integerList.getTail());
        Assert.assertNull(this.integerList.getNext());

        // exercise
        this.integerList.addItem(numberTwo);

        // verify
        Assert.assertEquals(numberTwo, this.integerList.getHead().getValue());
        Assert.assertEquals(numberTwo, this.integerList.getCurrent().getValue());
        Assert.assertEquals(numberTwo, this.integerList.getTail().getValue());
        Assert.assertEquals(numberTwo, this.integerList.getNext());
        Assert.assertNull(this.integerList.getNext());
    }
}
