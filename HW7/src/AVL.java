import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize an empty AVL.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Your inputted data is null");
        } else {
            size = 0;
            for (T input : data) {
                add(input);
            }
        }
    }

    /**
     * Adds the element to the tree.
     * <p>
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     * <p>
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * <p>
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Your data is null");
        } else {
            root = addHelper(root, data);
        }
    }

    /**
     * Helper method for add to make it recursive.
     *
     * @param node The node that the code is looking at the moment
     * @param data The data that is to be added
     * @return The node that is to be looked at next
     */
    private AVLNode<T> addHelper(AVLNode<T> node, T data) {
        if (node == null) {
            size++;
            return new AVLNode<T>(data);
        }
        int temp = data.compareTo(node.getData());
        if (temp < 0) {
            node.setLeft(addHelper(node.getLeft(), data));
        } else if (temp > 0) {
            node.setRight(addHelper(node.getRight(), data));
        } else {
            return node;
        }
        findInfo(node);
        return balence(node);
    }

    /**
     * Helper method to update a node's height and balance factor.
     *
     * @param node The node that is to be updated
     */
    private void findInfo(AVLNode<T> node) {
        int left;
        int right;
        if (node.getLeft() == null) {
            left = -1;
        } else {
            left = node.getLeft().getHeight();
        }
        if (node.getRight() == null) {
            right = -1;
        } else {
            right = node.getRight().getHeight();
        }
        node.setHeight(Math.max(left, right) + 1);
        node.setBalanceFactor(left - right);
    }

    /**
     * Helper method to balence the tree.
     *
     * @param node To be balenced
     * @return The node that is now on top
     */
    private AVLNode<T> balence(AVLNode<T> node) {
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() == -1) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            node = rotateRight(node);
        } else if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() == 1) {
                node.setRight(rotateRight(node.getRight()));
            }
            node = rotateLeft(node);
        }
        return node;
    }

    /**
     * Helper method to rotate left.
     *
     * @param node head node that is to be rotated left
     * @return returns the new head node
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> temp = node.getRight();
        node.setRight(temp.getLeft());
        temp.setLeft(node);
        findInfo(node);
        findInfo(temp);
        return temp;
    }

    /**
     * Helper method to rotate right.
     *
     * @param node head node that is to be rotated right
     * @return returns the new head node
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> temp = node.getLeft();
        node.setLeft(temp.getRight());
        temp.setRight(node);
        findInfo(node);
        findInfo(temp);
        return temp;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     * <p>
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Your data is null");
        }
        AVLNode<T> dummy = new AVLNode<T>(null);
        root = remove(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Helper method for the remove(T data) method.
     *
     * @param curr  Node which traces through the tree
     * @param data  The data that is being searched for to remove
     * @param dummy The node that is removed
     * @return Returns the current node
     */
    private AVLNode<T> remove(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Your data is not in the tree");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(remove(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(remove(curr.getRight(), data, dummy));
        } else {
            dummy.setData(curr.getData());
            size--;
            if ((curr.getLeft() == null) && curr.getRight() == null) {
                return null;
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else {
                AVLNode<T> dummy2 = new AVLNode<T>(null);
                curr.setLeft(removePredecessor(curr.getLeft(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        findInfo(curr);
        return balence(curr);
    }

    /**
     * Helper method for remove(AVLNode curr, T data, AVLNode dummy).
     *
     * @param curr  Node that is traced though to find the predecessor
     * @param dummy Node that is to be removed
     * @return returns the current node
     */
    private AVLNode<T> removePredecessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getRight() == null) {
            dummy.setData(curr.getData());
            return curr.getLeft();
        }
        curr.setRight(removePredecessor(curr.getRight(), dummy));
        findInfo(curr);
        return balence(curr);
    }

    /**
     * Returns the element from the tree matching the given parameter.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Your data is null");
        } else {
            return getHelper(root, data);
        }
    }

    /**
     * Helper method for the get method to make it recursive.
     *
     * @param node The node that the code is on at the moment
     * @param data The data that is being looked for in the tree
     * @return The data is that a node with the target data contains.
     */
    private T getHelper(AVLNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("The data is not in the tree");
        }
        int temp = data.compareTo(node.getData());
        if (temp < 0) {
            return getHelper(node.getLeft(), data);
        } else if (temp > 0) {
            return getHelper(node.getRight(), data);
        } else {
            return node.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Your data is null");
        }
        try {
            get(data);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     * <p>
     * This must be done recursively.
     * <p>
     * Your list should not have duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     * <p>
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     * <p>
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     * <p>
     * Example Tree:
     * 10
     * /        \
     * 5          15
     * /   \      /    \
     * 2     7    13    20
     * / \   / \     \  / \
     * 1   4 6   8   14 17  25
     * /           \          \
     * 0             9         30
     * <p>
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> list = new ArrayList<>();
        deepestBranches(root, list);
        return list;
    }

    /**
     * Helper method for deepestBranches to make it recursive.
     *
     * @param node The node that the code is looking at the moment
     * @param list The list of all the data that is at the max height
     */
    private void deepestBranches(AVLNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        list.add(node.getData());
        if ((node.getLeft() != null) && (node.getHeight() - node.getLeft().getHeight() == 1)) {
            deepestBranches(node.getLeft(), list);
        }
        if ((node.getRight() != null) && (node.getHeight() - node.getRight().getHeight() == 1)) {
            deepestBranches(node.getRight(), list);
        }
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     * <p>
     * This must be done recursively.
     * <p>
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     * <p>
     * Example Tree:
     * 10
     * /        \
     * 5          15
     * /   \      /    \
     * 2     7    13    20
     * / \   / \     \  / \
     * 1   4 6   8   14 17  25
     * /           \          \
     * 0             9         30
     * <p>
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @return a sorted list of data that is > data1 and < data2
     * @throws IllegalArgumentException if data1 or data2 are null
     *                                  or if data1 > data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if ((data1 == null) || (data2 == null) || (data1.compareTo(data2) > 0)) {
            throw new IllegalArgumentException("Your data is either null or data1 > data2");
        }
        List<T> list = new ArrayList<>();
        if (data1 == data2) {
            return list;
        }
        sortedInBetweenHelper(root, list, data1, data2);
        return list;
    }

    /**
     * Helper method for sortInBetween to make it recursive.
     *
     * @param node The node that the search is looking at
     * @param list The list of data in between data1 and data2
     * @param data1 The min cap for where to include
     * @param data2 The max cap for where to include
     */
    private void sortedInBetweenHelper(AVLNode<T> node, List<T> list, T data1, T data2) {
        if (node == null) {
            return;
        }
        if (node.getData().compareTo(data1) > 0) {
            sortedInBetweenHelper(node.getLeft(), list, data1, data2);
        }
        if ((node.getData().compareTo(data1) > 0) && (node.getData().compareTo(data2)) < 0) {
            list.add(node.getData());
        }
        if (node.getData().compareTo(data2) < 0) {
            sortedInBetweenHelper(node.getRight(), list, data1, data2);
        }
    }

    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
