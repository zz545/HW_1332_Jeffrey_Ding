import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement selection sort.
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if ((arr == null) || (comparator == null)) {
            throw new IllegalArgumentException("The array or the comparator is null");
        }
        int length = arr.length;
        int min;
        for (int i = 0; i < length; i++) {
            min = i;
            for (int j = i + 1; j < length; j++) {
                if (comparator.compare(arr[min], arr[j]) > 0) {
                    min = j;
                }
            }
            swap(arr, min, i);
        }
    }

    /**
     * Helper method for most swaps needed.
     *
     * @param arr The array that needs a part to be swapped
     * @param a First index of the swap
     * @param b Second index of the swap
     * @param <T> Data type to sort
     */
    private static <T> void swap(T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * Implement cocktail sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if ((arr == null) || (comparator == null)) {
            throw new IllegalArgumentException("The array or the comparator is null");
        }
        int start = 0;
        int end = arr.length - 1;
        boolean swapped = true;
        while ((end > start) && (swapped)) {
            swapped = false;
            int lastSwapped = 0;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    swapped = true;
                    lastSwapped = i;
                }
            }
            end = lastSwapped;
            if (swapped) {
                swapped = false;
                int lastSwapped2 = 0;
                for (int j = end; j > start; j--) {
                    if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                        swap(arr, j, j - 1);
                        swapped = true;
                        lastSwapped2 = j;
                    }
                }
                start = lastSwapped2;
            }
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * <p>
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if ((arr == null) || (comparator == null)) {
            throw new IllegalArgumentException("The array or the comparator is null");
        }
        if (arr.length > 1) {
            T[] left = (T[]) new Object[arr.length / 2];
            T[] right = (T[]) new Object[arr.length - left.length];
            for (int i = 0; i < left.length; i++) {
                left[i] = arr[i];
            }
            for (int j = 0; j < right.length; j++) {
                right[j] = arr[j + left.length];
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            merge(arr, comparator, left, right);
        }
    }

    /**
     * Helper method for mergeSort.
     *
     * @param arr The array that is inputted to merge
     * @param comparator Used to compare the generics
     * @param left Data on the left side
     * @param right Data on the right side
     * @param <T> Data type to sort
     */
    private static <T> void merge(T[] arr, Comparator<T> comparator, T[] left, T[] right) {
        int i = 0;
        int j = 0;
        for (int k = 0; k < arr.length; k++) {
            if (j >= right.length || ((i < left.length) && (comparator.compare(left[i], right[j])) <= 0)) {
                arr[k] = left[i++];
            } else {
                arr[k] = right[j++];
            }
        }
    }

    /**
     * Implement kth select.
     * <p>
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     * <p>
     * int pivotIndex = rand.nextInt(b - a) + a;
     * <p>
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     * <p>
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     * <p>
     * It should be:
     * in-place
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * You may assume that the array doesn't contain any null elements.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if ((arr == null) || (comparator == null)
                || (rand == null) || ((k < 1) || (k > arr.length))) {
            throw new IllegalArgumentException("One of your inputs is null or"
                    + "doesn't exist in the length");
        }
        return kthSelectHelper(k, arr, comparator, rand, 0, arr.length);
    }

    /**
     * Helper method for kthSelect.
     *
     * @param k The index to retrieve data from + 1 (due to 0-indexing) if the array was sorted;
     *          the 'k' in "kth select"; e.g. if k == 1, return the smallest element in the array
     * @param arr The array to sort
     * @param comparator Used to compare the generics
     * @param rand Use to find a random number for a pivot index
     * @param left The left index
     * @param right The right Index
     * @return Returns a data "T" of the smallest element
     * @param <T> Data type to sort
     */
    private static <T> T kthSelectHelper(int k, T[] arr, Comparator<T> comparator, Random rand, int left, int right) {
        int pivotIndex = rand.nextInt(right - left) + left;
        T pivotInfo = arr[pivotIndex];
        swap(arr, left, pivotIndex);
        int i = left + 1;
        int j = right - 1;
        while (i <= j) {
            while ((i <= j) && (comparator.compare(arr[i], pivotInfo) <= 0)) {
                i++;
            }
            while ((i <= j) && (comparator.compare(arr[j], pivotInfo) >= 0)) {
                j--;
            }
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        swap(arr, left, j);
        if (j == k - 1) {
            return arr[j];
        } else if (j > k - 1) {
            return kthSelectHelper(k, arr, comparator, rand, left, j);
        } else {
            return kthSelectHelper(k, arr, comparator, rand, j + 1, right);
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     * <p>
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * <p>
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Your array is null");
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        int mod = 10;
        int div = 1;
        boolean cont = true;
        while (cont) {
            cont = false;
            for (int i = 0; i < arr.length; i++) {
                int bucket = arr[i] / div;
                if (bucket / 10 != 0) {
                    cont = true;
                }
                if (buckets[bucket % mod + 9] == null) {
                    buckets[bucket % mod + 9] = new LinkedList<>();
                }
                buckets[bucket % mod + 9].add(arr[i]);
            }
            int arrIndex = 0;
            for (LinkedList<Integer> buck : buckets) {
                if (buck != null) {
                    for (int num : buck) {
                        arr[arrIndex++] = num;
                    }
                    buck.clear();
                }
            }
            div = div * 10;
        }
    }

    /**
     * Implement heap sort.
     * <p>
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     * <p>
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     * <p>
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        PriorityQueue<Integer> prio = new PriorityQueue<>();
        for (int i : data) {
            prio.add(i);
        }
        int[] arr = new int[data.size()];
        for (int j = 0; j < arr.length; j++) {
            arr[j] = prio.remove();
        }
        return arr;
    }
}
