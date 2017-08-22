package com.restermans;

import java.util.Collection;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * A collection designed for holding elements prior to processing.
 * @param <E> the type of elements held in this collection
 */
public class MyQueue<E> extends KWLinkedList<E> implements Queue<E>{

    public MyQueue()
    {
        super();
    }

    /**
     * Retrieves and removes the head of this queue.  This method differs
     * from {@link #poll poll} only in that it throws an exception if this
     * queue is empty.
     *
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    public E remove()
    {
        return super.remove();
    }

    /**
     * Retrieves, but does not remove, the head of this queue.  This method
     * differs from {@link #peek peek} only in that it throws an exception
     * if this queue is empty.
     *
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    public E element()
    {
        return getFirst();
    }

    /**
     * Inserts the specified element into this queue if it is possible to do
     * so immediately without violating capacity restrictions.
     * When using a capacity-restricted queue, this method is generally
     * preferable to {@link #add}, which can fail to insert an element only
     * by throwing an exception.
     *
     * @param e the element to add
     * @return {@code true} if the element was added to this queue, else
     *         {@code false}
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this queue
     * @throws NullPointerException if the specified element is null and
     *         this queue does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *         prevents it from being added to this queue
     */
    public boolean offer(E e)
    {
        addLast(e);
        return true;
    }

    /**
     * Retrieves, but does not remove, the head of this queue,
     * or returns {@code null} if this queue is empty.
     *
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    public E peek()
    {
        return getFirst();
    }

    /**
     * Retrieves and removes the head of this queue,
     * or returns {@code null} if this queue is empty.
     *
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    public E poll()
    {
        return remove();
    }

    /**
     * Inserts the specified element into this queue if it is possible to do so
     * immediately without violating capacity restrictions, returning
     * {@code true} upon success and throwing an {@code IllegalStateException}
     * if no space is currently available.
     *
     * @param e the element to add
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws IllegalStateException if the element cannot be added at this
     *         time due to capacity restrictions
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this queue
     * @throws NullPointerException if the specified element is null and
     *         this queue does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *         prevents it from being added to this queue
     */
    public boolean add(E e)
    {
        return offer(e);
    }

    /**
     * This static method takes any queque and reverses it. Passed queue is not changed.
     * @param queue queue to be reversed
     * @param <E> the type of elements held in the passed queue
     * @return copy of the passed queue in reversed order
     */
    public static <E extends Comparable>  Queue<E> reverseQueue(Queue<E> queue)
    {
        Queue<E> myQueue = new MyQueue<E>();
        myQueue.addAll(queue);
        reverseQueueHelper(myQueue);
        return myQueue;
    }

    private static  <E extends Comparable> void reverseQueueHelper(Queue<E> queue)
    {
        if(queue.size() == 0)
            return;

        E temp = queue.remove();
        reverseQueueHelper(queue);
        queue.add(temp);
    }

    /**
     * Reverses this queue.
     */
    public void reverse()
    {
        ListIterator<E> start = listIterator();
        ListIterator<E> end = listIterator(size());
        E temp = null;
        while(start.nextIndex() < end.previousIndex() )
        {
            temp = start.next();
            start.previous();
            start.set(end.previous());
            start.next();
            end.next();
            end.set(temp);
            end.previous();
        }
    }
}
