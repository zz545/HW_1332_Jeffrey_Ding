
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if ((data == null) || data.contains(null)) {
            throw new IllegalArgumentException("Your data is null");
        } else {
            size = 0;
            for (T temp : data) {
                add(temp);
            }
        }
    }

    /**
     * Adds the data to the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * The data becomes a leaf in the tree.
     * <p>
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Your data is null");
        } else if (root == null) {
            root = new BSTNode<T>(data);
            size++;
        } else {
            add(root, data);
        }
    }

    /**
     * Helper method for add(T data)
     *
     * @param curr Node which traces through the tree
     * @param data The data that is being added
     */
    private void add(BSTNode<T> curr, T data) {
        if (root == null) {
            root = new BSTNode<>(data);
            size = 1;
        } else if (data.compareTo(curr.getData()) < 0) {
            if (curr.getLeft() == null) {
                curr.setLeft(new BSTNode<>(data));
                size++;
            } else {
                add(curr.getLeft(), data);
            }
        } else if (data.compareTo(curr.getData()) > 0) {
            if (curr.getRight() == null) {
                curr.setRight(new BSTNode<>(data));
                size++;
            } else {
                add(curr.getRight(), data);
            }
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Your data is null");
        } else {
            BSTNode<T> dummy = new BSTNode<T>(null);
            root = remove(root, data, dummy);
            return dummy.getData();
        }
    }

    /**
     * Helper method for the remove(T data) method.
     *
     * @param curr  Node which traces through the tree
     * @param data  The data that is being searched for to remove
     * @param dummy The node that is removed
     * @return Returns the current node
     */
    private BSTNode<T> remove(BSTNode<T> curr, T data, BSTNode<T> dummy) {
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
                BSTNode<T> dummy2 = new BSTNode<T>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }

    /**
     * Helper method for remove(BSTNode<T> curr, T data, BSTNode<T> dummy).
     *
     * @param curr  Node that is traced though to find the successor
     * @param dummy Node that is to be removed
     * @return returns the current node
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Your data is null");
        } else {
            return get(root, data);
        }
    }

    /**
     * Helper method for the get(T data) method.
     *
     * @param curr Node which traces through the tree
     * @param data The data that is being searched for
     * @return Returns the node which has the data's data
     */
    private T get(BSTNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Your data was not found");
        } else if (data.compareTo(curr.getData()) < 0) {
            return get(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return get(curr.getRight(), data);
        } else {
            return curr.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
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
        } catch (NoSuchElementException nsee) {
            return false;
        } catch (Exception e) {
            System.out.println("You are wrong");
        }
        return true;
    }

    /**
     * Generate a pre-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>(9);
        preorder(root, list);
        return list;
    }

    /**
     * Helper method for preorder.
     *
     * @param curr Node to trace through the tree
     * @param list An arraylist to store the traced nodes.
     */
    private void preorder(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            list.add(curr.getData());
            preorder(curr.getLeft(), list);
            preorder(curr.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        inorder(root, list);
        return list;
    }

    /**
     * Helper method for inorder().
     *
     * @param curr Node to trace through the tree
     * @param list An arraylist to store the traced nodes.
     */
    private void inorder(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            inorder(curr.getLeft(), list);
            list.add(curr.getData());
            inorder(curr.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        postorder(root, list);
        return list;
    }

    /**
     * Helper method for postorder().
     *
     * @param curr Node to trace through the tree
     * @param list An arraylist to store the traced nodes.
     */
    private void postorder(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            postorder(curr.getLeft(), list);
            postorder(curr.getRight(), list);
            list.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     * <p>
     * This does not need to be done recursively.
     * <p>
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     * <p>
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> list = new ArrayList<>();
        queue.add(root);
        if (root == null) {
            return list;
        }
        while (!queue.isEmpty()) {
            BSTNode<T> temp = queue.poll();
            list.add(temp.getData());
            if (temp.getLeft() != null) {
                queue.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                queue.add(temp.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     * <p>
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else if ((root.getRight() == null) && (root.getLeft() == null)) {
            return 0;
        } else {
            return height(root);
        }
    }

    /**
     * Helper method for height().
     *
     * @param root The base node
     * @return Returns the next node or null to find height
     */
    private int height(BSTNode<T> root) {
        if (root == null) {
            return -1;
        } else {
            int left = height(root.getLeft());
            int right = height(root.getRight());
            if (left > right) {
                return (left + 1);
            } else {
                return (right + 1);
            }
        }
    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     * <p>
     * This must be done recursively.
     * <p>
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     * <p>
     * EXAMPLE: Given the BST below composed of Integers:
     * <p>
     * 50
     * /    \
     * 25      75
     * /  \
     * 12   37
     * /  \    \
     * 10  15    40
     * /
     * 13
     * <p>
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     * <p>
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        if ((k < 0) || (k > size)) {
            throw new IllegalArgumentException("Your wanted size is either too big or too small");
        } else if (root == null) {
            List<T> list = new ArrayList<>();
            return list;
        } else {
            List<T> list = new ArrayList<>(k);
            try {
                kLargest(root, k, list);
            } catch (Exception e) {
                return list;
            }
            return null;
        }
    }

    /**
     * Helper method for kLargest(int k).
     *
     * @param curr Current node
     * @param k    The number of nodes to store
     * @param list The list of nodes stored
     * @return Returns the next node or null to find value of parent node
     * @throws Exception The exit function to escape the recursion
     */
    private List<T> kLargest(BSTNode<T> curr, int k, List<T> list) throws Exception {
        if (list.size() == k) {
            throw new Exception("You have reached the end");
        } else if (curr == null) {
            return null;
        } else {
            kLargest(curr.getRight(), k, list);
            list.add(0, curr.getData());
            kLargest(curr.getLeft(), k, list);
        }
        return null;
    }


    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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