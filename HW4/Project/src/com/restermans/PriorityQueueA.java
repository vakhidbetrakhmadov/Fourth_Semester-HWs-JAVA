package com.restermans;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

/**
 * An unbounded priority {@linkplain Queue queue} based on a priority heap.
 * The elements of the priority queue are ordered according to their
 * {@linkplain Comparable natural ordering}
 * A priority queue relying on natural ordering also does not permit
 * insertion of non-comparable objects (doing so may result in
 * {@code ClassCastException}).
 *
 * @param <E> the type of elements held in this collection
 */
public class PriorityQueueA<E extends Comparable> extends LinkedList<E> {

    public PriorityQueueA()
    {
        super();
    }

    /**
     * Returns the number of elements in this collection.
     * @return the number of elements in this collection
     */
    public int size()
    {
        return super.size();
    }

    /**
     * Tests if this collection is empty.
     *
     * @return  <code>true</code> if and only if this collection contains
     *          no items; <code>false</code> otherwise.
     */
    public boolean isEmpty()
    {
        return super.isEmpty();
    }

    /**
     * Deletes and returns the highest priority element. Lower values have higher priority.
     * @return the removed element
     */
    public E deleteMin()
    {
        return removeFirst();
    }

    /**
    * Inserts the specified element into this priority queue.
    * Elements with higher priority are inserted to the head of the queue
     * @param  e the element to be inserted
    * @return {@code true} (as specified by {@link java.util.Collection#add})
    */
    public boolean insert(E e)
    {
        if(size() == 0 || getFirst().compareTo(e) >= 0)
            addFirst(e);
        else if(getLast().compareTo(e)<=0)
        {
            addLast(e);
        }
        else
        {
            ListIterator<E> listIterator = listIterator();
            while(listIterator.hasNext())
            {
                if(listIterator.next().compareTo(e) >= 0)
                {
                    listIterator.previous();
                    listIterator.add(e);
                    return true;
                }
            }
        }

        return true;
    }
}
