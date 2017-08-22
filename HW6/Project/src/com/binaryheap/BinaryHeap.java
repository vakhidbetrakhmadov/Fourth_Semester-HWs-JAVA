package com.binaryheap;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class BinaryHeap<T> extends CompleteBinaryTree<T> implements Queue<T>{

    private Comparator<T> comparator;

    public BinaryHeap()
    {
        this(null);
    }

    public BinaryHeap(Comparator<T> comparator)
    {
        super();
        this.comparator = comparator;
    }

    public boolean add(T element)
    {
        if (offer(element))
            return true;
        else
            throw new IllegalStateException("Queue full");
    }

    public T remove()
    {
        T x = poll();
        if (x != null)
            return x;
        else
            throw new NoSuchElementException();
    }

    public T element()
    {
        T x = peek();
        if (x != null)
            return x;
        else
            throw new NoSuchElementException();
    }

    public boolean offer(T element)
    {
        super.add(element);

        Node<T> child = lastAddedNode;
        Node<T> parent = child.parent;

        while(parent!= null && compare(child.data,parent.data) < 0)
        {
            swap(child,parent);
            child = parent;
            parent = child.parent;
        }

        return true;
    }

    public T poll()
    {
        if(isEmpty())
            return null;

        T result = root.data;
        if(size() == 1)
        {
            --size;
            root = null;
            return result;
        }

        Node<T> lastNode = getLastNode();
        root.data = lastNode.data;
        if(lastNode.parent.left == lastNode)
            lastNode.parent.left = null;
        else
            lastNode.parent.right = null;

        Node<T> parent = root;
        Node<T> leftChild = parent.left;
        Node<T>  rightChild = parent.right;
        boolean stop = false;

        while(leftChild !=null && !stop )
        {
            Node<T> minChild = leftChild;
            if(rightChild != null && compare(leftChild.data,rightChild.data) > 0)
                minChild = rightChild;

            if(compare(parent.data,minChild.data) > 0)
            {
                swap(parent,minChild);
                parent = minChild;
                leftChild = parent.left;
                rightChild = parent.right;
            }
            else
                stop = true;
        }

        --size;

        return result;
    }

    public T peek()
    {
        return (isEmpty() ? null : root.data);
    }


    // -------------- Unsupported operations -----------------//
    public CompleteBinaryTree<T> getLeftSubtree()
    {
        throw new UnsupportedOperationException();
    }

    public CompleteBinaryTree<T> getRightSubtree()
    {
        throw new UnsupportedOperationException();
    }

    public static CompleteBinaryTree<String> readBinaryTree(BufferedReader bR) throws IOException
    {
        throw new UnsupportedOperationException();
    }

    /*----------------------------------- Helper methods -------------------------------*/

    private void swap(Node<T> left,Node<T> right)
    {
        T temp = left.data;
        left.data = right.data;
        right.data = temp;
    }

    private int compare(T left,T right)
    {
        if(comparator == null)
            return ((Comparable<T>)left).compareTo(right);

        return comparator.compare(left,right);
    }

    private Node<T> getLastNode()
    {
        Queue<Node<T>> treeNodes = new ArrayDeque<>();
        treeNodes.offer(root);
        Node<T> nextTreeNode;
        do {
            nextTreeNode = treeNodes.poll();
            if(nextTreeNode.left != null)
                treeNodes.offer(nextTreeNode.left);
            if(nextTreeNode.right != null)
                treeNodes.offer(nextTreeNode.right);
        }while(!treeNodes.isEmpty());

        return nextTreeNode;
    }
}
