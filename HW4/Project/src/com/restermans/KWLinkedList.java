package com.restermans;

import java.util.*;

/**
 * Doubly-linked list implementation
 * @param <E> the type of elements held in this collection
 */
public class KWLinkedList<E> extends AbstractCollection<E> implements Iterable<E> {
    // Data Fields
    /** A reference to the head of the list. */
    private Node<E> head;
    /** A reference to the end of the list. */
    private Node<E> tail;
    /** The size of the list. */
    private int size;

    public KWLinkedList()
    {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * <p>This method is equivalent to {@link #addLast}.
     *
     * @param obj element to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     */
    public boolean add(E obj)
    {
        listIterator(size).add(obj);
        return true;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any
     * subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param obj element to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public void add(int index, E obj) {
        listIterator(index).add(obj);
    }

    /**
     * Inserts the specified element at the front of this list.
     *
     * @param obj the element to insert
     */
    public void addFirst(E obj)
    {
        listIterator().add(obj);
    }

    /**
     * Inserts the specified element at the end of this list.
     *
     * @param obj the element to insert
     */
    public void addLast(E obj)
    {
        listIterator(size).add(obj);
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E get(int index)
    {
        return listIterator(index).next();
    }

    /**
     * Retrieves, but does not remove, the head (first element) of this list.
     *
     * @return the head of this list
     * @throws NoSuchElementException if this list is empty
     * @since 1.5
     */
    public E getFirst()
    {
        return listIterator().next();
    }

    /**
     * Returns the last element in this list.
     *
     * @return the last element in this list
     * @throws NoSuchElementException if this list is empty
     */
    public E getLast()
    {
        return listIterator(size-1).next();
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
    public E set(int index, E data)
    {
        ListIterator<E> it = listIterator(index);
        E toReturn = it.next();
        it.set(data);
        return toReturn;
    }

    public E remove()
    {
        ListIterator<E> it = listIterator();
        E toReturn = it.next();
        it.remove();
        return toReturn;
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
    public E remove(int index)
    {
        ListIterator<E> it = listIterator(index);
        E toReturn = it.next();
        it.remove();
        return toReturn;
    }

    /**
     * Removes and returns the first element from this list.
     *
     * @return the first element from this list
     * @throws NoSuchElementException if this list is empty
     */
    public E removeFirst()
    {
        return remove();
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return the last element from this list
     * @throws NoSuchElementException if this list is empty
     */
    public E removeLast()
    {
        return remove(size-1);
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
     * @param obj element to search for
     * @return the index of the first occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
     */
    public int indexOf(E obj)
    {
        ListIterator<E> it = listIterator();
        while(it.hasNext())
        {
            if(it.next().equals(obj))
            {
                return it.previousIndex();
            }
        }

        return -1;
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    public Iterator<E> iterator()
    {
        return new KWIterator();
    }


    public ListIterator<E> listIterator()
    {
        return  new KWListIter(0);
    }

    /**
     * Returns a list-iterator of the elements in this list (in proper
     * sequence), starting at the specified position in the list.
     * Obeys the general contract of {@code List.listIterator(int)}.<p>
     *
     * The list-iterator is <i>fail-fast</i>: if the list is structurally
     * modified at any time after the Iterator is created, in any way except
     * through the list-iterator's own {@code remove} or {@code add}
     * methods, the list-iterator will throw a
     * {@code ConcurrentModificationException}.  Thus, in the face of
     * concurrent modification, the iterator fails quickly and cleanly, rather
     * than risking arbitrary, non-deterministic behavior at an undetermined
     * time in the future.
     *
     * @param index index of the first element to be returned from the
     *              list-iterator (by a call to {@code next})
     * @return a ListIterator of the elements in this list (in proper
     *         sequence), starting at the specified position in the list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @see List#listIterator(int)
     */
    public ListIterator<E> listIterator(int index)
    {
        return  new KWListIter(index);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private class KWIterator implements Iterator<E>
    {
        /**
         * A reference to the next item.
         */
        protected Node<E> nextItem;
        /**
         * A reference to the last item returned.
         */
        protected Node<E> lastItemReturned;
        /**
         * The index of the current item.
         */
        protected int index;

        private KWIterator()
        {
            nextItem = head;
            index = 0;
            lastItemReturned = null;
        }

        /**
         * Indicate whether movement forward is defined.
         *
         * @return true if call to next will not throw an exception
         */
        public boolean hasNext() {
            return nextItem != null;
        }

        /**
         * Move the iterator forward and return the next item.
         *
         * @return The next item in the list
         * @throws NoSuchElementException if there is no such object
         */

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastItemReturned = nextItem;
            nextItem = nextItem.next;
            index++;
            return lastItemReturned.data;
        }

        public void remove()
        {
            if(lastItemReturned == null)
                throw new IllegalStateException();

            if(lastItemReturned == head && lastItemReturned == tail)
            {
                head = null;
                tail = null;
            }
            else if(lastItemReturned == head)
            {
                head = head.next;
                head.prev = null;
            }
            else if (lastItemReturned == tail)
            {
                tail = tail.prev;
                tail.next = null;
            }
            else
            {
                lastItemReturned.prev.next = lastItemReturned.next;
                lastItemReturned.next.prev = lastItemReturned.prev;
            }

            lastItemReturned = null;
            --index;
            --size;
        }
    }

   //-----------------------------------------------------------------------------------------------------------------//
    private class KWListIter extends KWIterator implements ListIterator<E> {

        public KWListIter(int i) {
            super();
            // Validate i parameter.
            if (i < 0 || i > size) {
                throw new IndexOutOfBoundsException("Invalid index " + i);
            }
            // Special case of last item.
            if (i == size) {
                index = size;
                nextItem = null;
            } else { // Start at the beginning
                for (index = 0; index < i; index++) {
                    nextItem = nextItem.next;
                }
            }
        }

        /**
         * Indicate whether movement backward is defined.
         *
         * @return true if call to previous will not throw an exception
         */
        public boolean hasPrevious() {
            return index > 0;
        }

        /**
         * Move the iterator backward and return the previous item.
         *
         * @return The previous item in the list
         * @throws NoSuchElementException if there is no such object
         */
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            if (nextItem == null) { // Iterator is past the last element
                nextItem = tail;
            } else {
                nextItem = nextItem.prev;
            }
            lastItemReturned = nextItem;
            index--;
            return lastItemReturned.data;
        }

        /**
         * Add a new item between the item that will be returned
         * by next and the item that will be returned by previous.
         * If previous is called after add, the element added is
         * returned.
         *
         * @param obj The item to be inserted
         */
        public void add(E obj) {
            if (head == null)
            {   // Add to an empty list.
                head = new Node<>(obj);
                tail = head;
            }
            else if (nextItem == head)
            {   // Insert at head.
                // Create a new node.
                Node<E> newNode = new Node<>(obj);
                // Link it to the nextItem.
                newNode.next = nextItem; // Step 1
                // Link nextItem to the new node.
                nextItem.prev = newNode; // Step 2
                // The new node is now the head.
                head = newNode; // Step 3
            }
            else if (nextItem == null)
            {   // Insert at tail.
                // Create a new node.
                Node<E> newNode = new Node<>(obj);
                // Link the tail to the new node.
                tail.next = newNode; // Step 1
                // Link the new node to the tail.
                newNode.prev = tail; // Step 2
                // The new node is the new tail.
                tail = newNode; // Step 3
            }
            else
            {   // Insert into the middle.
                // Create a new node.
                Node<E> newNode = new Node<>(obj);
                // Link it to nextItem.prev.
                newNode.prev = nextItem.prev; // Step 1
                nextItem.prev.next = newNode; // Step 2
                // Link it to the nextItem.
                newNode.next = nextItem; // Step 3
                nextItem.prev = newNode; // Step 4
            }

            // Increase size and index and set lastItemReturned.
            size++;
            index++;
            lastItemReturned = null;
        }

        public int nextIndex()
        {
            return index;
        }

        public int previousIndex()
        {
            return index -1;
        }

        public void set(E e)
        {
            if(lastItemReturned == null)
                throw new IllegalStateException();

            lastItemReturned.data = e;
        }
    }

    //----------------------------------------------------------------------------------------------------------------//
    private static class Node<E> {
        /** The data value. */
        private E data;
        /** The link to the next node. */
        private Node<E> next;
        /** The link to the previous node. */
        private Node<E> prev;

        /** Construct a node with the given data value.
         @param dataItem The data value
         */
        public Node(E dataItem) {
            data = dataItem;
            next = null;
            prev = null;
        }

        public Node(E dataItem,Node<E> next,Node<E> prev) {
            data = dataItem;
            this.next = next;
            this.prev = prev;
        }

        public String toString()
        {
            return data.toString();
        }
    }
}
