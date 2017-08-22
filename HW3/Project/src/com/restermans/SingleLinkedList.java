package com.restermans;

import java.util.*;

/**
 * Single-linked list implementation of the {@code MyList}interfaces.
 * Implements all optional list operations, and permits all
 * elements.
 *
 * <p>All of the operations perform as could be expected for a single-linked
 * list.  Operations that index into the list will traverse the list from
 * the beginning.
 *
 * <p> This implementation keeps deleted nodes for later reuse,thus decreasing garbage collection.
 *
 * @param <T> the type of elements held in this collection
 *
 * @author  Vakhid Betrakhmadov
 **/
public class SingleLinkedList<T> extends MyAbstractCollection<T> implements MyList<T>
{
    /**
     * Pointer to first node.
    **/
    private Node<T> head ;

    /**
     * Size of the list.
     **/
    private int size;

    /**
     * Stack to keep deleted nodes
     **/
    private Deque<Node<T>> deletedStack;

    /**
     * Constructs an empty list.
     */
    public SingleLinkedList()
    {
        size = 0;
        head = null;
        deletedStack = new ArrayDeque<>(100);
    }

    /**
     * Returns a string representation of this collection.  The string
     * representation consists of just one concatenated string
     *
     * @return a string representation of this collection
     */
    public String toString()
    {
        String toReturn = "";
        Iterator<T> iterator = iterator();
        while (iterator.hasNext())
            toReturn+=iterator.next().toString();

        return toReturn;
    }

    /**
     * Returns a string representation of all deleted nodes currently kept for reuse. The string
     * representation is an array delimited by comma and enclose in square brackets.
     *
     * @return a string representation of all deleted nodes currently kept for reuse
     */
    public String deletedToString()
    {
        return deletedStack.toString();
    }

    /**
     * Returns a string representation of this collection in reverse order. The string
     * representation is an array delimited by comma and enclose in square brackets.
     *
     * @return a string representation of this collection in reverse order
     */
    public String reverseToString()
    {
        return "["+reverseToString(head)+"]";
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public T get(int index)
    {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException(Integer.toString(index));

        Node<T> node = head;
        for (int i = 0;node != null && i < index;node = node.next,++i);
        return node.data;
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @param index index of the element to replace
     * @param data element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public T set(int index, T data)
    {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException(Integer.toString(index));

        T toReturn = null;
        Node<T> node = head;
        for (int i = 0;node != null && i < index;node = node.next,++i);
        toReturn = node.data;
        node.data = data;

        return toReturn;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param data element to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     */
    public boolean add(T data)
    {
        if(size == 0)
        {
            addFirst(data);
        }
        else
        {
            Node<T> lastNode = null;
            for (lastNode = head;lastNode.next != null;lastNode = lastNode.next);
            addAfter(lastNode,data);
        }

        return true;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any
     * subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param data element to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public boolean add(int index,T data)
    {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException(Integer.toString(index));

        if(index == 0)
        {
            addFirst(data);
        }
        else
        {
            Node<T> node = null;
            int i;
            for (node = head,i = 0;node.next != null && i < index;node = node.next,++i);
            addAfter(node,data);
        }

        return true;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size()
    {
        return size;
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the lowest index {@code i} such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * @param target element to search for
     * @return the index of the first occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
     */
    public int indexOf(T target)
    {
        int index ;
        Node<T> node;
        for (index = 0,node = head;node != null && !node.data.equals(target);node = node.next,++index);

        return (index == 0 ? (size == 0 ? -1 : 0 ) : (index == size ?  -1 : index ) );
    }

    /**
     * Removes the element at the specified position in this list.  Shifts any
     * subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public T remove(int index)
    {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException(Integer.toString(index));
        if(size == 0)
            throw new NoSuchElementException(Integer.toString(size));

        T toReturn = null;
        if(index == 0)
        {
            toReturn = removeFirst();
        }
        else
        {
            Node<T> node = head;
            for (int i = 0;i < index-1;node = node.next,++i);
            toReturn = removeAfter(node);
        }

        return toReturn;
    }

    /**
     * Returns an iterator over the elements in this list (in proper
     * sequence).<p>
     *
     * This implementation merely returns a list iterator over the list.
     *
     * @return an iterator over the elements in this list (in proper sequence)
     */
    public Iterator<T> iterator()
    {
        return new SingleLinkedListIterator();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void addAfter(Node<T> node,T data)
    {
        if(deletedStack.size()!=0)
            node.next = deletedStack.pop().set(data,node.next);
        else
            node.next  =  new Node<T>(data,node.next);

        ++size;
    }

    private T removeAfter(Node<T> node)
    {
        T toReturn = node.next.data;
        deletedStack.push(node.next);
        node.next = node.next.next;
        --size;
        return toReturn;
    }

    private void addFirst(T data)
    {
        if(deletedStack.size()!=0)
            head = deletedStack.pop().set(data,head);
        else
            head = new Node<>(data,head);

        ++ size;
    }

    private T removeFirst()
    {
        T toReturn = head.data;
        deletedStack.push(head);
        head = head.next;
        --size;
        return toReturn;
    }

    private String reverseToString(Node<T> node)
    {
        if(node == null)
            return "";

        return reverseToString(node.next) + node.data.toString() + (node == head ? "" : ",");
    }

    //----------------------------------------------------------------------------------------------------------------//
    private class SingleLinkedListIterator implements Iterator<T>
    {
        private Node<T> next;
        private Node<T> previous;
        int nextIndex;

        private SingleLinkedListIterator()
        {
            next = head;
            nextIndex = 0;
            previous = null;
        }

        public void remove()
        {
            if(previous == null)
                throw new IllegalStateException();

            --nextIndex;
            SingleLinkedList.this.remove(nextIndex);
            previous = null;
        }

        public boolean hasNext()
        {
            return (next != null);
        }

        public T next()
        {
            if(!hasNext())
                throw new NoSuchElementException();

            ++nextIndex;
            previous = next;
            next = next.next;
            return previous.data;
        }
    }

    //----------------------------------------------------------------------------------------------------------------//
    private static class Node<T>
    {
        private T data;
        private Node<T> next;

        private Node(T data)
        {
            this.data = data;
            this.next = null;
        }

        private Node(T data,Node<T> next)
        {
            this.data = data;
            this.next = next;
        }

        private Node<T> set(T data,Node<T> next)
        {
            this.data = data;
            this.next = next;
            return this;
        }

        public String toString()
        {
            return data.toString();
        }
    }
}
