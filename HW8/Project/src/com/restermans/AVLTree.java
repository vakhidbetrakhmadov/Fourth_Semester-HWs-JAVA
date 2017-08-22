package com.restermans;

/**
 * Self-balancing binary search tree using the algorithm defined
 * by Adelson-Velskii and Landis.
 *
 * @author Koffman and Wolfgang
 */

public class AVLTree<E extends Comparable<E>> extends BinarySearchTreeWithRotate<E> {

    // Insert nested class AVLNode<E> here.

    // Data Fields
    /**
     * Flag to indicate that height of tree has increased.
     */
    private boolean increase;

    /**
     * Flag to indicate that height of tree has decreased
     */
    private boolean decrease;

    /**
     * Class to represent an AVL Node. It extends the
     * BinaryTree.Node by adding the balance field.
     */
    private static class AVLNode<E> extends Node<E> {
        /**
         * * Constant to indicate left-heavy
         */
        public static final int LEFT_HEAVY = -1;

        /**
         * Constant to indicate balanced
         */
        public static final int BALANCED = 0;

        /**
         * Constant to indicate right-heavy
         */
        public static final int RIGHT_HEAVY = 1;

        /**
         * balance is right subtree height � left subtree height
         */
        private int balance;

        // Methods

        /**
         * Construct a node with the given item as the data field.
         *
         * @param item The data field
         */
        public AVLNode(E item) {
            super(item);
            balance = BALANCED;
        }

        /**
         * Return a string representation of this object.
         * The balance value is appended to the contents.
         *
         * @return String representation of this object
         */
        public String toString() {
            return balance + ": " + super.toString();
        }
    }

    /**
     * add starter method.
     * pre: the item to insert implements the Comparable interface.
     *
     * @param item The item being inserted.
     * @return true if the object is inserted; false
     * if the object already exists in the tree
     * @throws ClassCastException if item is not Comparable
     */
    public boolean add(E item) {
        increase = false;
        root = add((AVLNode<E>) root, item);
        return addReturn;
    }

    /**
     * Recursive add method. Inserts the given object into the tree.
     * post: addReturn is set true if the item is inserted,
     * false if the item is already in the tree.
     *
     * @param localRoot The local root of the subtree
     * @param item      The object to be inserted
     * @return The new local root of the subtree with the item
     * inserted
     */
    private AVLNode<E> add(AVLNode<E> localRoot, E item) {
        if (localRoot == null) {
            addReturn = true;
            increase = true;
            return new AVLNode<E>(item);
        }

        if (item.compareTo(localRoot.data) == 0) {
            // Item is already in the tree.
            increase = false;
            addReturn = false;
            return localRoot;
        } else if (item.compareTo(localRoot.data) < 0) {
            // item < data
            localRoot.left = add((AVLNode<E>) localRoot.left, item);

            if (increase) {
                decrementBalance(localRoot);
                if (localRoot.balance < AVLNode.LEFT_HEAVY) {
                    increase = false;
                    return rebalanceLeft(localRoot,false);
                }
            }
            return localRoot; // Rebalance not needed.
        } else {
            // item > data
            localRoot.right = add((AVLNode<E>) localRoot.right, item);
            if (increase) {
                incrementBalance(localRoot);
                if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
                    increase = false;
                    return rebalanceRight(localRoot,false);
                }
            }
            return localRoot;
        }
    }

    // ------------------------------------------------------------------------------------------------------------- //
    /**
     * Starter method delete.
     * post: The object is not in the tree.
     *
     * @param target The object to be deleted
     * @return The object deleted from the tree or null if the object was not in the tree
     * @throws ClassCastException if target does not implement
     *                            Comparable
     */
    public E delete(E target) {
        decrease = true;
        root = delete((AVLNode<E>)root, target);
        return deleteReturn;
    }

    /**
     * Recursive delete method.
     * post: The item is not in the tree;
     * deleteReturn is equal to the deleted item
     * as it was stored in the tree or null
     * if the item was not found.
     *
     * @param localRoot The root of the current subtree
     * @param item      The item to be deleted
     * @return The modified local root that does not contain
     * the item
     */
    private AVLNode<E> delete(AVLNode<E> localRoot, E item) {
        if (localRoot == null) {
            // item is not in the tree.
            deleteReturn = null;
            decrease = false;
            return localRoot;
        }

        // Search for item to delete.
        int compResult = item.compareTo(localRoot.data);
        if (compResult < 0) {
            // item is smaller than localRoot.data.
            localRoot.left = delete((AVLNode<E>) localRoot.left, item);
            if(decrease) {
                incrementBalance(localRoot);
                if(localRoot.balance > AVLNode.RIGHT_HEAVY){
                    decrease = false;
                    localRoot = rebalanceRight(localRoot,true);
                    if(localRoot.balance == AVLNode.BALANCED)
                        decrease = true;
                }
            }
            return localRoot;
        } else if (compResult > 0) {
            // item is larger than localRoot.data.
            localRoot.right = delete((AVLNode<E>)localRoot.right, item);
            if(decrease){
                decrementBalance(localRoot);
                if(localRoot.balance < AVLNode.LEFT_HEAVY){
                    decrease = false;
                    localRoot = rebalanceLeft(localRoot,true);
                    if(localRoot.balance == AVLNode.BALANCED)
                        decrease = true;
                }
            }
            return localRoot;
        } else {
            // item is at local root.
            deleteReturn = localRoot.data;
            if (localRoot.left == null) {
                // If there is no left child, return right child
                // which can also be null.
                return (AVLNode<E>)localRoot.right;
            } else if (localRoot.right == null) {
                // If there is no right child, return left child.
                return (AVLNode<E>)localRoot.left;
            } else {
                // Node being deleted has 2 children, replace the data
                // with inorder predecessor.
                if (localRoot.left.right == null) {
                    // The left child has no right child.
                    // Replace the data with the data in the
                    // left child.
                    localRoot.data = localRoot.left.data;
                    // Replace the left child with its left child.
                    localRoot.left = localRoot.left.left;
                } else {
                    // Search for the inorder predecessor (ip) and
                    // replace deleted node�s data with ip.
                    localRoot.data = findReplacement(localRoot);
                }

                if(decrease){
                    incrementBalance(localRoot);
                    if(localRoot.balance > AVLNode.RIGHT_HEAVY){
                        decrease = false;
                        localRoot = rebalanceRight(localRoot,true);
                        if(localRoot.balance == AVLNode.BALANCED)
                            decrease = true;
                    }
                }

                return localRoot;
            }
        }
    }

    // Helper method
    private E findReplacement(AVLNode<E> parent){
        AVLNode<E> parentLeft = (AVLNode<E>) parent.left;
        E result = findLargestChild(parentLeft);
        if(decrease){
            decrementBalance(parentLeft);
            if(parentLeft.balance < AVLNode.LEFT_HEAVY){
                decrease = false;
                parentLeft = rebalanceLeft(parentLeft,true);
                if(parentLeft.balance == AVLNode.BALANCED)
                    decrease = true;
                parent.left = parentLeft;
            }
        }
        return result;
    }

    // Helper method
    private E findLargestChild(AVLNode<E> parent) {
        // If the right child has no right child, it is
        // the inorder predecessor.
        if (parent.right.right == null) {
            E returnValue = parent.right.data;
            parent.right = parent.right.left;
//            decrease = true;
            return returnValue;
        } else {
            AVLNode<E> parentRight = (AVLNode<E>) parent.right;
            E result = findLargestChild(parentRight);
            if(decrease){
                decrementBalance(parentRight);
                if(parentRight.balance < AVLNode.LEFT_HEAVY){
                    decrease = false;
                    parentRight = rebalanceLeft(parentRight,true);
                    if(parentRight.balance == AVLNode.BALANCED)
                        decrease = true;
                    parent.right = parentRight;
                }
            }
            return result;
        }
    }
    // ------------------------------------------------------------------------------------------------------------- //

    /**
     * Method to decrement the balance field and to reset the value of
     * increase or decrease.
     *
     * @param node The AVL node whose balance is to be incremented
     */
    private void decrementBalance(AVLNode<E> node) {
        // Decrement the balance.
        node.balance--;
        if (node.balance < AVLNode.BALANCED)
            decrease = false;
        else
            increase = false;
    }

    /**
     * Method to increment the balance field and to reset the value of
     * increase or decrease.
     *
     * @param node The AVL node whose balance is to be incremented
     */
    private void incrementBalance(AVLNode<E> node) {
        node.balance++;
        if (node.balance > AVLNode.BALANCED)
            decrease = false;
        else
            increase = false;
    }

    /**
     * rebalanceRight
     *
     * @param localRoot Root of the AVL subtree that needs rebalancing
     * @return a new localRoot
     */
    private AVLNode<E> rebalanceRight(AVLNode<E> localRoot, boolean isDelete) {

        AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;
        if (rightChild.balance < AVLNode.BALANCED) {

            AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.left;

            if (rightLeftChild.balance > AVLNode.BALANCED) {
                rightChild.balance = AVLNode.BALANCED;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.LEFT_HEAVY;
            } else if(rightLeftChild.balance < AVLNode.BALANCED){
                rightChild.balance = AVLNode.RIGHT_HEAVY;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            } else {
                rightChild.balance = AVLNode.BALANCED;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }

            localRoot.right = rotateRight(rightChild);
        } else {
            if (isDelete && rightChild.balance == AVLNode.BALANCED) {
                rightChild.balance = AVLNode.LEFT_HEAVY;
                localRoot.balance = AVLNode.RIGHT_HEAVY;
            } else {
                rightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
        }

        return (AVLNode<E>) rotateLeft(localRoot);
    }

    /**
     * Method to rebalance left.
     *
     * @param localRoot Root of the AVL subtree that needs rebalancing
     * @return a new localRoot
     */
    private AVLNode<E> rebalanceLeft(AVLNode<E> localRoot,boolean isDelete) {
        // Obtain reference to left child.
        AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
        // See whether left-right heavy.
        if (leftChild.balance > AVLNode.BALANCED) {
            // Obtain reference to left-right child.
            AVLNode<E> leftRightChild = (AVLNode<E>) leftChild.right;
            /** Adjust the balances to be their new values after
             the rotations are performed.
             */
            if (leftRightChild.balance < AVLNode.BALANCED) {
                leftChild.balance = AVLNode.BALANCED;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.RIGHT_HEAVY;
            } else if(leftRightChild.balance > AVLNode.BALANCED){
                leftChild.balance = AVLNode.LEFT_HEAVY;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            } else {
                leftChild.balance = AVLNode.BALANCED;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            // Perform left rotation.
            localRoot.left = rotateLeft(leftChild);
        } else { //Left-Left case
            /** In this case the leftChild (the new root)
             and the root (new right child) will both be balanced
             after the rotation.
             */
            if (isDelete && leftChild.balance == AVLNode.BALANCED){
                leftChild.balance = AVLNode.RIGHT_HEAVY;
                localRoot.balance = AVLNode.LEFT_HEAVY;
            } else {
                leftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }


        }
        // Now rotate the local root right.
        return (AVLNode<E>) rotateRight(localRoot);
    }
}
