package com.familytree;

import java.io.*;
import java.util.*;

/**
 * Class for a binary tree that stores type E objects.
 *
 * @author Koffman and Wolfgang
 */

public class BinaryTree<E>
        extends AbstractCollection<E>
        implements Collection<E>, Iterable<E>, Serializable {

    /**
     * Class to encapsulate a tree node.
     */
    protected static class Node<E>
            implements Serializable {
        // Data Fields
        /**
         * The information stored in this node.
         */
        protected E data;

        /**
         * Reference to the left child.
         */
        protected Node<E> left;

        /**
         * Reference to the right child.
         */
        protected Node<E> right;

        // Constructors

        /**
         * Construct a node with given data and no children.
         *
         * @param data The data to store in this node
         */
        public Node(E data) {
            this.data = data;
            left = null;
            right = null;
        }

        // Methods

        /**
         * Return a string representation of the node.
         *
         * @return A string representation of the data fields
         */
        public String toString() {
            return data.toString();
        }
    }

    // Data Field
    /**
     * The root of the binary tree
     */
    protected Node<E> root;
    /**
     * The size of the binary tree
     */
    protected int size;

    public BinaryTree() {
        root = null;
        size = 0;
    }

    protected BinaryTree(Node<E> root, int size) {
        this.root = root;
        this.size = size;
    }

    /**
     * Constructs a new binary tree with data in its root,leftTree
     * as its left subtree and rightTree as its right subtree.
     *
     * @param data The data to be placed to the new root.
     * @param leftTree The left subtree of the new tree.
     * @param rightTree The right subtree of the new tree.
     */
    public BinaryTree(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
        root = new Node<E>(data);
        size = 1;
        if (leftTree != null) {
            root.left = leftTree.root;
            size += leftTree.size;
        } else {
            root.left = null;
        }
        if (rightTree != null) {
            root.right = rightTree.root;
            size += rightTree.size;
        } else {
            root.right = null;
        }
    }

    /**
     * Return the left subtree.
     *
     * @return The left subtree or null if either the root or
     * the left subtree is null
     */
    public BinaryTree<E> getLeftSubtree() {
        if (root != null && root.left != null) {
            return new BinaryTree<>(root.left, size(root.left));
        } else {
            return null;
        }
    }


    /**
     * Return the right sub-tree
     *
     * @return the right sub-tree or
     * null if either the root or the
     * right subtree is null.
     */
    public BinaryTree<E> getRightSubtree() {
        if (root != null && root.right != null) {
            return new BinaryTree<>(root.right, size(root.right));
        } else {
            return null;
        }
    }


    /**** BEGIN EXERCISE ****/
    /**
     * Return the data field of the root
     *
     * @return the data field of the root
     * or null if the root is null
     */
    public E getData() {
        if (root != null) {
            return root.data;
        } else {
            return null;
        }
    }
    /**** END EXERCISE ****/

    /**
     * Determine whether this tree is a leaf.
     *
     * @return true if the root has no children
     */
    public boolean isLeaf() {
        return (root.left == null && root.right == null);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        preOrderTraverse(root, 1, sb);
        return sb.toString();
    }

    /**
     * Perform a preorder traversal.
     *
     * @param node  The local root
     * @param depth The depth
     * @param sb    The string buffer to save the output
     */
    protected void preOrderTraverse(Node<E> node, int depth,
                                    StringBuilder sb) {
        for (int i = 1; i < depth; i++) {
            sb.append(" |");
        }
        if (node == null) {
            sb.append("null\n");
        } else {
            sb.append(node.toString());
            sb.append("\n");
            preOrderTraverse(node.left, depth + 1, sb);
            preOrderTraverse(node.right, depth + 1, sb);
        }
    }

    /**
     * Method to read a binary tree.
     * pre: The input consists of a preorder traversal
     * of the binary tree. The line "null" indicates a null tree.
     *
     * @param bR The input file
     * @return The binary tree
     * @throws IOException If there is an input error
     */
    public static BinaryTree<String>
    readBinaryTree(BufferedReader bR) throws IOException {
        // Read a line and trim leading and trailing spaces.
        String data = bR.readLine().trim();
        if (data.equals("null")) {
            return null;
        } else {
            BinaryTree<String> leftTree = readBinaryTree(bR);
            BinaryTree<String> rightTree = readBinaryTree(bR);
            return new BinaryTree<String>(data, leftTree, rightTree);
        }
    }

    /**
     * Returns an iterator over the elements contained in this tree.
     * Iterator traverses tree in pre-order.
     * @return an iterator over the elements contained in this tree
     */
    public Iterator<E> iterator() {
        return new BinaryTreeIterator();
    }


    public int size() {
        return this.size;
    }

    protected int size(Node<E> root) {
        if (root == null)
            return 0;

        return 1 + size(root.left) + size(root.right);
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation iterates over the collection looking for the
     * specified element.  If it finds the element, it removes the element
     * from the collection using the iterator's remove method.
     *
     * <p>Note that this implementation throws an
     * <tt>UnsupportedOperationException</tt> if the iterator returned by this
     * collection's iterator method does not implement the <tt>remove</tt>
     * method and this collection contains the specified object.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     */
    public boolean remove(Object item) {
        throw new UnsupportedOperationException("remove");
    }

    //----------------------------------------------------------------------------------------------------------------//

    /**
     * BinaryTreeIterator class implements Iterator and traverses tree in pre-order
     */
    protected class BinaryTreeIterator implements Iterator<E> {

        protected Deque<Node<E>> nextNodes = new ArrayDeque<>();
        protected Node<E> nextNode = null;
        protected Node<E> previousNode = null;

        public BinaryTreeIterator() {
            if (root != null)
                nextNodes.push(root);
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        public boolean hasNext() {
            return !nextNodes.isEmpty();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public E next() {
            next(nextNodes);
            System.out.print(nextNode.toString() + " ");
            return nextNode.data;
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.  The behavior of an iterator
         * is unspecified if the underlying collection is modified while the
         * iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *         operation is not supported by this iterator
         *
         * @throws IllegalStateException if the {@code next} method has not
         *         yet been called, or the {@code remove} method has already
         *         been called after the last call to the {@code next}
         *         method
         */
        public void remove() {
            if (previousNode == null)
                throw new IllegalStateException();

            BinaryTree.this.remove(previousNode.data);
            previousNode = null;
        }


        /**
         * Helper method to manipulate stack of the next nodes of the tree.
         * @param nextNodesStack stack of the next nodes of the tree.
         * Contains only root of the tree if next method has not been called yet.
         *
         * Note. Traverse order might be changed  by manipulating only this method.
         */
        protected void next(Deque<Node<E>> nextNodesStack) {
            if (!hasNext())
                throw new NoSuchElementException();

            nextNode = nextNodesStack.pop();
            if (nextNode.right != null)
                nextNodesStack.push(nextNode.right);
            if (nextNode.left != null)
                nextNodesStack.push(nextNode.left);

            previousNode = nextNode;
        }
    }
}
