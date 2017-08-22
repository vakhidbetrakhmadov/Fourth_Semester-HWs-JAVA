package com.restermans;

/**
 * The <code>Stack</code> class represents a last-in-first-out
 * (LIFO) stack of objects.
 */
public interface StackInterface<T> {

    /**
     * Pushes an item onto the top of this stack.
     * @param e   the item to be pushed onto this stack.
     */
    void push(T e);

    /**
     * Removes the object at the top of this stack and returns that
     * object as the value of this function.
     *
     * @return  The object at the top of this stack.
     * @throws java.util.NoSuchElementException  if this stack is empty.
     */
    T pop();

    /**
     * Tests if this stack is empty.
     *
     * @return  <code>true</code> if and only if this stack contains
     *          no items; <code>false</code> otherwise.
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in this stack.
     * @return the number of elements in this collection
     */
    int size();
}