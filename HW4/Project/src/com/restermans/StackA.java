package com.restermans;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * The <code>Stack</code> class represents a last-in-first-out
 * (LIFO) stack of objects.
 */
public class StackA<T> extends ArrayList<T> implements StackInterface<T> {

    public StackA() {
        super();
    }

    /**
     * @see StackInterface
     */
    public void push(T e) {
        add(e);
    }

    /**
     * @see StackInterface
     */
    public T pop() {
        if (isEmpty())
            throw new NoSuchElementException();

        return remove(size() - 1);
    }

    /**
     * @see StackInterface
     */
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * @see StackInterface
     */
    public int size() {
        return super.size();
    }
}
