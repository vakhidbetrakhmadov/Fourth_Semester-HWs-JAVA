package com.restermans;

import java.util.NoSuchElementException;

/**
 * The <code>Stack</code> class represents a last-in-first-out
 * (LIFO) stack of objects.
 */
public class StackC<T> implements StackInterface<T> {

    private Node<T> head;
    private int size;

    public StackC()
    {
        head = null;
        size = 0;
    }

    /**
     * @see StackInterface
     */
    public void push(T e)
    {
        head = new Node<T>(e,head);
        ++size;
    }

    /**
     * @see StackInterface
     */
    public T pop()
    {
        if(isEmpty())
            throw new NoSuchElementException();

        T toReturn = head.data;
        head = head.next;
        --size;
        return toReturn;
    }

    /**
     * @see StackInterface
     */
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * @see StackInterface
     */
    public int size()
    {
        return size;
    }


    private static class Node<T> {
        /** The data value. */
        private T data;
        /** The link to the next node. */
        private Node<T> next;

        /** Construct a node with the given data value.
         @param data The data value
         */
        private Node(T data) {
            this.data = data;
            this.next = null;
        }

        /** Construct a node with the given data value and next node reference.
         @param data The data value
         @param next The next node reference
         */
        private Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }
}
