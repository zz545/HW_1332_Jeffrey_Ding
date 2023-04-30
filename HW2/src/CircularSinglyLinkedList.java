import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Jeffrey Ding
 * @version 1.0
 * @userid jding94 (i.e. gburdell3)
 * @GTID 903754704 (i.e. 900000000)
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     * <p>
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if ((index < 0) || (index > size)) {
            throw new IndexOutOfBoundsException("Your desired index is not a real index");
        } else if (data == null) {
            throw new IllegalArgumentException("Your data is null");
        } else {
            if (head == null) {
                head = new CircularSinglyLinkedListNode<>(data);
                head.setNext(head);
            } else if (head == head.getNext()) {
                if (index == 0) {
                    CircularSinglyLinkedListNode<T> temp = new CircularSinglyLinkedListNode<>(head.getData());
                    head.setNext(temp);
                    head.setData(data);
                    temp.setNext(head);
                } else {
                    CircularSinglyLinkedListNode<T> temp = new CircularSinglyLinkedListNode<>(data);
                    head.setNext(temp);
                    temp.setNext(head);
                }
            } else if (index == 0) {
                CircularSinglyLinkedListNode<T> temp = new CircularSinglyLinkedListNode<>(head.getData());
                temp.setNext(head.getNext());
                head.setNext(temp);
                head.setData(data);
            } else if (index == size) {
                CircularSinglyLinkedListNode<T> temp = new CircularSinglyLinkedListNode<>(head.getData());
                temp.setNext(head.getNext());
                head.setNext(temp);
                head.setData(data);
                head = temp;
            } else {
                CircularSinglyLinkedListNode<T> current = head;
                CircularSinglyLinkedListNode<T> temp = new CircularSinglyLinkedListNode<>(data);
                for (int i = 0; i < index - 1; i++) {
                    current = current.getNext();
                }
                temp.setNext(current.getNext());
                current.setNext(temp);
            }
            size++;
        }
    }

    /**
     * Adds the data to the front of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
        /*
        if (data == null) {
            throw new IllegalArgumentException("Your data is null");
        } else {
            CircularSinglyLinkedListNode<T> temp = head;
            temp.setNext(head.getNext());
            head.setNext(temp);
            head.setData(data);
        }
         */
    }

    /**
     * Adds the data to the back of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the data at the specified index.
     * <p>
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if ((index < 0) || (index >= size)) {
            throw new IndexOutOfBoundsException("The index is out of bounds");
        } else {
            if (index == 0) {
                if (head.getNext() == head) {
                    CircularSinglyLinkedListNode<T> temp = new CircularSinglyLinkedListNode<>(head.getData());
                    head = null;
                    size--;
                    return temp.getData();
                } else {
                    CircularSinglyLinkedListNode<T> temp = new CircularSinglyLinkedListNode<>(head.getData());
                    head.setData(head.getNext().getData());
                    head.setNext(head.getNext().getNext());
                    size--;
                    return temp.getData();
                }
            } else {
                CircularSinglyLinkedListNode<T> current = head;
                for (int i = 0; i < index - 1; i++) {
                    current = current.getNext();
                }
                CircularSinglyLinkedListNode<T> temp = new CircularSinglyLinkedListNode<>(current.getNext().getData());
                current.setNext(current.getNext().getNext());
                size--;
                return temp.getData();
            }
        }
    }

    /**
     * Removes and returns the first data of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty.");
        } else {
            return removeAtIndex(0);
        }
    }

    /**
     * Removes and returns the last data of the list.
     * <p>
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty.");
        } else {
            return removeAtIndex(size - 1);
        }
    }

    /**
     * Returns the data at the specified index.
     * <p>
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if ((index < 0) || (index >= size)) {
            throw new IndexOutOfBoundsException("Your index is not in the allowed size.");
        } else {
            if (index == 0) {
                return head.getData();
            } else {
                CircularSinglyLinkedListNode<T> current = head;
                for (int i = 0; i < index; i++) {
                    current = current.getNext();
                }
                return current.getData();
            }
        }
    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * Clears the list.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     * <p>
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            CircularSinglyLinkedListNode<T> current = head;
            CircularSinglyLinkedListNode<T> temp = new CircularSinglyLinkedListNode<>(null);
            CircularSinglyLinkedListNode<T> temp2 = new CircularSinglyLinkedListNode<>(null);
            if (head == null) {
                throw new NoSuchElementException("There isn't a head.");
            }
            if (head.getData().equals(data)) {
                temp = current;
            }
            while (current.getNext() != head) {
                if (current.getNext().getData().equals(data)) {
                    temp = current.getNext();
                    temp2 = current;
                }
                current = current.getNext();
            }
            if (temp.getData() == null) {
                throw new NoSuchElementException("The data is not found.");
            }
            if (temp != null) {
                if (size == 1) {
                    size--;
                    head = null;
                    return temp.getData();
                } else if (size == 2 && temp != head) {
                    size--;
                    head.setNext(head);
                    return temp.getData();
                } else if (temp == head) {
                    return removeFromFront();
                }
                temp2.setNext(temp.getNext());
            }
            size--;
            return temp.getData();
        }
    }

    /**
     * Returns an array representation of the linked list.
     * <p>
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] array = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.getData();
            current = current.getNext();
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
