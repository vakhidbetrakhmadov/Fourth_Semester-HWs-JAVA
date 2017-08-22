package com.restermans;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * The <code>Stack</code> class represents a last-in-first-out
 * (LIFO) stack of objects.
 */
public class StackB<T> implements StackInterface<T>{

    private ArrayList<T> arrayList  = null;

    public StackB() {
        arrayList = new ArrayList<T>();
    }

    /**
     * @see StackInterface
     */
    public void push(T e) {
        arrayList.add(e);
    }

    /**
     * @see StackInterface
     */
    public T pop() {
        if(isEmpty())
            throw new NoSuchElementException();

        return arrayList.remove(arrayList.size()-1);
    }

    /**
     * @see StackInterface
     */
    public boolean isEmpty() {
        return arrayList.isEmpty();
    }

    /**
     * @see StackInterface
     */
    public int size() {
        return arrayList.size();
    }

}
