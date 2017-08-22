package com.binaryheap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class CompleteBinaryTree<E> extends BinaryTree<E> implements Iterable<E>, Collection<E>, Serializable {

    //---------------- MODIFICATION -------------//
    //For offer method in BinaryHeap//
    protected Node<E> lastAddedNode = null;

    public CompleteBinaryTree() {
        super();
    }

    protected CompleteBinaryTree(Node<E> root, int size) {
        super(root, size);
    }

    /**
     * Constructs a new complete binary tree with data in its root,leftTree
     * as its left subtree and rightTree as its right subtree.
     *
     * @param data The data to be placed to the new root.
     * @param leftTree The left subtree of the new tree.
     * @param rightTree The right subtree of the new tree.
     */
    public CompleteBinaryTree(E data, CompleteBinaryTree<E> leftTree, CompleteBinaryTree<E> rightTree) {
        super(data, leftTree, rightTree);
    }

    /**
     * Adds item where it belongs in the tree.
     * That is to the most left position in the last level.
     *
     * @param data The item to be inserted
     * @return true
     */
    //peeked from http://stackoverflow.com/questions/16630823/binary-tree-insert-algorithm
    //modified by me
    public boolean add(E data) {
        lastAddedNode = new Node<E>(data);
        if (root == null)
            root = lastAddedNode;
        else {
            Queue<Node<E>> treeNodes = new ArrayDeque<>();
            Node<E> nextTreeNode = root;
            while (nextTreeNode.left != null && nextTreeNode.right != null) {
                treeNodes.offer(nextTreeNode.left);
                treeNodes.offer(nextTreeNode.right);
                nextTreeNode = treeNodes.remove();
            }
            if (nextTreeNode.left == null)
            {
                nextTreeNode.left = lastAddedNode;
                nextTreeNode.left.parent= nextTreeNode;//---------------- MODIFICATION -------------//
            }
            else
            {
                nextTreeNode.right = lastAddedNode;
                nextTreeNode.right.parent = nextTreeNode;//---------------- MODIFICATION -------------//
            }
        }
        ++size;
        return true;
    }

    /**
     * Return the left subtree.
     *
     * @return The left subtree or null if either the root or
     * the left subtree is null
     */
    public CompleteBinaryTree<E> getLeftSubtree() {
        if (root != null && root.left != null) {
            //---------------- MODIFICATION -------------//
            Node<E> node = new Node<>(root.left.data,null,root.left.left,root.left.right);
            return new CompleteBinaryTree<>(node, size(root.left));
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
    public CompleteBinaryTree<E> getRightSubtree() {
        if (root != null && root.right != null) {
            //---------------- MODIFICATION -------------//
            Node<E> node = new Node<>(root.right.data,null,root.right.left,root.right.right);
            return new CompleteBinaryTree<>(node, size(root.right));
        } else {
            return null;
        }
    }

    /**
     * Method to read a binary tree.
     * pre: The input consists of a preorder traversal
     * of the binary tree. The line "null" indicates a null tree.
     *
     * @param bR The input file
     * @return The complete binary tree
     * @throws IOException If there is an input error
     */
    public static CompleteBinaryTree<String> readBinaryTree(BufferedReader bR) throws IOException {
        CompleteBinaryTree<String> newCompleteBinaryTree = new CompleteBinaryTree<>();
        String data;
        while ((data = bR.readLine()) != null) {
            data = data.trim();
            if (!data.equals("null"))
                newCompleteBinaryTree.add(data);
        }

        return newCompleteBinaryTree;
    }
}
