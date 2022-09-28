import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
 *
 * @author Kathie Huynh
 * @version 1.0
 * @userid khuynh46
 * @GTID 903669289
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
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
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        }
        for (T item : data) {
            if (root == null) {
                BSTNode<T> newNode = new BSTNode<T>(item);
                root = newNode;
                size = 1;
            } else {
                add(item);
            }
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        }
        if (root == null) {
            root = new BSTNode<>(data);
            size++;
            return;
        }
        addR(root, data);
    }

    /**
     * Adds the data  to the tree recursively.
     * @param current the current node
     * @param data the data to add to the tree
     */
    private void addR(BSTNode<T> current, T data) {
        if (data.compareTo(current.getData()) < 0) {
            if (current.getLeft() == null) {
                current.setLeft(new BSTNode<>(data));
                size++;
            } else {
                addR(current.getLeft(), data);
            }
        } else if (data.compareTo(current.getData()) > 0) {
            if (current.getRight() == null) {
                current.setRight(new BSTNode<>(data));
                size++;
            } else {
                addR(current.getRight(), data);
            }
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        }
        if (root == null) {
            throw new NoSuchElementException("Cannot find data");
        }

        BSTNode<T> dummy = new BSTNode<>(null);
        root = removeR(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Recursively removes the node and stores data in dummy node.
     * 
     * @param current the current node
     * @param data the data of the node to remove
     * @param dummy the node to store removed data
     * @return the current node
     */
    private BSTNode<T> removeR(BSTNode<T> current, T data, BSTNode<T> dummy) {
        if (current == null) {
            throw new NoSuchElementException("Cannot find data");
        } else if (current.getData().compareTo(data) > 0) {
            current.setLeft(removeR(current.getLeft(), data, dummy));
        } else if (current.getData().compareTo(data) < 0) {
            current.setRight(removeR(current.getRight(), data, dummy));
        } else {
            dummy.setData(current.getData());
            size--;
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            } else if (current.getLeft() == null) {
                return current.getRight();
            } else if (current.getRight() == null) {
                return current.getLeft();
            } else {
                BSTNode<T> sucDummy = new BSTNode<>(null);
                current.setRight(sucR(current.getRight(), sucDummy));
                current.setData(sucDummy.getData());
            }
        }
        return current;
    }

    /**
     * recursively finds the successor and removes it.
     * 
     * @param current the current node
     * @param dummy the node to store the successor's data
     * @return the successor's right child
     */
    private BSTNode<T> sucR(BSTNode<T> current, BSTNode<T> dummy) {
        if (current.getLeft() == null) {
            dummy.setData(current.getData());
            return current.getRight();
        } else {
            current.setLeft(sucR(current.getLeft(), dummy));
        }
        return current; // doesnt reach ?
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        }

        BSTNode<T> target = getNode(root, data);
        if (target == null) {
            throw new NoSuchElementException("Cannot find data " + data);
        } else {
            return target.getData();
        }
    }

    /**
     * Returns the node with data matching the given data recursively.
     * @param current the current node
     * @param data the data to search for
     * @return the node with data that matches the given data
     */
    private BSTNode<T> getNode(BSTNode<T> current, T data) {
        if (current == null) {
            return null;
        } else if (data.compareTo(current.getData()) == 0) {
            return current;
        } else if (data.compareTo(current.getData()) < 0) {
            return getNode(current.getLeft(), data);
        } else {
            return getNode(current.getRight(), data);
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot search for null data");
        }
        return search(root, data);
    }

    /**
     * Returns whether or not there is a node with data matching 
     * the given data, recursively.
     * @param current the current node
     * @param data the data to search for
     * @return whether the tree has the node containing the data or not
     */
    private boolean search(BSTNode<T> current, T data) {
        if (current == null) {
            return false;
        }
        if (data.compareTo(current.getData()) < 0) {
            return search(current.getLeft(), data);
        } else if (data.compareTo(current.getData()) > 0) {
            return search(current.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        if (root == null) {
            return new ArrayList<T>();
        }
        return preorderR(new ArrayList<T>(), root);
    }

    /**
     * Create a preorder traversal of the tree recursively.
     * @param list the current list containing the traversal
     * @param current the current node
     * @return the list containing the traversal
     */
    private ArrayList<T> preorderR(ArrayList<T> list, BSTNode<T> current) {
        list.add(current.getData());
        if (current.getLeft() != null) {
            preorderR(list, current.getLeft());
        }
        if (current.getRight() != null) {
            preorderR(list, current.getRight());
        }
        return list;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        if (root == null) {
            return new ArrayList<T>();
        }
        return inorderR(new ArrayList<>(), root);
    }

    /**
     * Create a inorder traversal of the tree recursively.
     * @param list the current list containing the traversal
     * @param current the current node
     * @return the list containing the traversal
     */
    private ArrayList<T> inorderR(ArrayList<T> list, BSTNode<T> current) {
        if (current.getLeft() != null) {
            inorderR(list, current.getLeft());
        }
        list.add(current.getData());
        if (current.getRight() != null) {
            inorderR(list, current.getRight());
        }
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        if (root == null) {
            return new ArrayList<T>();
        }
        return postorderR(new ArrayList<>(), root);
    }

    /**
     * Create a postorder traversal of the tree recursively.
     * @param list the current list containing the traversal
     * @param current the current node
     * @return the list containing the traversal
     */
    private ArrayList<T> postorderR(ArrayList<T> list, BSTNode<T> current) {
        if (current.getLeft() != null) {
            postorderR(list, current.getLeft());
        }
        if (current.getRight() != null) {
            postorderR(list, current.getRight());
        }
        list.add(current.getData());
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        LinkedList<BSTNode<T>> q = new LinkedList<>();
        List<T> sol = new ArrayList<>();
        if (root == null) {
            return sol;
        }
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> current = q.poll();
            sol.add(current.getData());
            if (current.getLeft() != null) {
                q.add(current.getLeft());
            }
            if (current.getRight() != null) {
                q.add(current.getRight());
            }
        }
        return sol;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return Math.max(getHeight(root.getLeft()), getHeight(root.getRight())) + 1;
    }

    /**
     * Returns the height of the node recursively.
     * @param current the current node
     * @return the int of the height of the beginning node
     */
    private int getHeight(BSTNode<T> current) {
        // counts height of leaves as 1
        if (current == null) {
            return -1;
        } else {
            return Math.max(1 + getHeight(current.getLeft()),
                1 + getHeight(current.getRight()));
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     * 
     * This must be done recursively.
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *           50
     *          /  \
     *         25   75
     *       /    \
     *      12    37
     *     /  \     \
     *   11    15   40
     *  /
     * 10
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 10] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        if (root == null) {
            return new ArrayList<T>();
        }
        ArrayList<T> sol = new ArrayList<>();
        maxDataPerLevel(sol, root, 0);
        return sol;
    }

    /**
     * Adds the max value on each level of the tree to the list.
     * @param list the list with max values on each level
     * @param current the current node
     * @param level the current level
     */
    private void maxDataPerLevel(ArrayList<T> list, BSTNode<T> current, int level) {
        // if (current.getLeft() == null &&)
        if (current != null) {
            if (level == list.size()) {
                list.add(current.getData());
            }
            maxDataPerLevel(list, current.getRight(), level + 1);
            maxDataPerLevel(list, current.getLeft(), level + 1);
        }
    }

    /**
     * Returns the root of the tree.
     *
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
     *
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