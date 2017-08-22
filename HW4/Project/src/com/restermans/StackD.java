package com.restermans;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * The <code>Stack</code> class represents a last-in-first-out
 * (LIFO) stack of objects.
 */
public class StackD<T> implements StackInterface<T>{

    private Queue<T> queue = null;

    public StackD()
    {
        queue = new ArrayDeque<T>();
    }

    /**
     * @see StackInterface
     */
    public void push(T e)
    {
        queue.offer(e);
    }

    /**
     * @see StackInterface
     */
    public T pop()
    {
        if(isEmpty())
            throw new NoSuchElementException();

        T toReturn = null;
        for(Iterator<T> iterator = queue.iterator();
                iterator.hasNext();
                /*nothing*/)
        {
            toReturn = iterator.next();
            if(!iterator.hasNext())
                iterator.remove();
        }

        return toReturn;
    }

    /**
     * @see StackInterface
     */
    public boolean isEmpty()
    {
        return queue.isEmpty();
    }

    /**
     * @see StackInterface
     */
    public int size()
    {
       return queue.size();
    }
}
