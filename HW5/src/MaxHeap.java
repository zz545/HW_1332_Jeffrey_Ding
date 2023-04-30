import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Jeffrey Ding
 * @version 1.0
 * @userid jding94
 * @GTID 903754704
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * <p>
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     * <p>
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     * <p>
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     * <p>
     * Consider how to most efficiently determine if the list contains null data.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Your data is null");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        backingArray[0] = null;
        for (int i = 1; i < data.size() + 1; i++) {
            if (data.get(i - 1) == null) {
                throw new IllegalArgumentException("At least point in your ArrayList is null");
            } else {
                backingArray[i] = data.get(i - 1);
                size++;
            }
        }
        int swap = size / 2;
        while (swap != 0) {
            downHeap(swap);
            swap--;
        }
    }

    /**
     * Adds the data to the heap.
     * <p>
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Your data is null");
        } else {
            if (size == backingArray.length - 1) {
                T[] temp = (T[]) new Comparable[backingArray.length * 2];
                temp[0] = null;
                for (int i = 1; i < size + 1; i++) {
                    temp[i] = backingArray[i];
                }
                temp[size + 1] = data;
                backingArray = temp;
            } else {
                backingArray[size + 1] = data;
            }
            size++;
        }
        int holder = size;
        upHeap(holder);
    }

    /**
     * Helper method for add(T data).
     *
     * @param holder Tracks the current "node"
     */
    private void upHeap(int holder) {
        if (holder == 1) {
            return;
        }
        while (holder != 0) {
            if (holder == 1) {
                return;
            }
            if (backingArray[holder].compareTo(backingArray[holder / 2]) > 0) {
                T temp = backingArray[holder / 2];
                backingArray[holder / 2] = backingArray[holder];
                backingArray[holder] = temp;
            } else {
                return;
            }
            holder = holder / 2;
        }
    }

    /**
     * Removes and returns the root of the heap.
     * <p>
     * Do not shrink the backing array.
     * <p>
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty");
        } else {
            T temp = backingArray[1];
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size--;
            boolean isLeaf = false;
            int base = 1;
            downHeap(base);
            return temp;
        }
    }

    /**
     * Helper method that downHeaps for both remove() and MaxHeap(ArrayList data).
     *
     * @param base Where to start looking for a downHeap under that node.
     */
    private void downHeap(int base) {
        //check if leaf first then add to the end of the while statement
        boolean hasRight = true;
        boolean isLeaf = false;
        if (base * 2 > size) {
            isLeaf = true;
        }
        if (base * 2 + 1 > size) {
            hasRight = false;
        }
        while (!isLeaf) {
            if (!hasRight) {
                int compareLeft2 = backingArray[base].compareTo(backingArray[base * 2]);
                if (compareLeft2 < 0) {
                    T temp = backingArray[base];
                    backingArray[base] = backingArray[base * 2];
                    backingArray[base * 2] = temp;
                }
                return;
            }
            int compareLeft = backingArray[base].compareTo(backingArray[base * 2]);
            int compareRight = backingArray[base].compareTo(backingArray[base * 2 + 1]);
            //need to make priority for heap ie switch the max; not always the right one
            if ((compareLeft > 0) && (compareRight > 0)) {
                return;
            } else if (compareRight > 0) {
                T temp = backingArray[base];
                backingArray[base] = backingArray[base * 2];
                backingArray[base * 2] = temp;
                base = base * 2;
            } else if (compareLeft > 0) {
                T temp = backingArray[base];
                backingArray[base] = backingArray[base * 2 + 1];
                backingArray[base * 2 + 1] = temp;
                base = base * 2 + 1;
            } else if ((compareLeft < 0) && (compareRight < 0)) {
                T temp = backingArray[base];
                if (backingArray[base * 2].compareTo(backingArray[base * 2 + 1]) > 0) {
                    backingArray[base] = backingArray[base * 2];
                    backingArray[base * 2] = temp;
                    base = base * 2;
                } else {
                    backingArray[base] = backingArray[base * 2 + 1];
                    backingArray[base * 2 + 1] = temp;
                    base = base * 2 + 1;
                }
            }
            if (base * 2 > size) {
                isLeaf = true;
            }
            if (base * 2 + 1 > size) {
                hasRight = false;
            }
        }
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (size == 0) {
            throw new NoSuchElementException("This heap is empty");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     * <p>
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
