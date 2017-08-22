package com.restermans;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/**
 *  This class extends AbstractCollection class and implements appendAnything
 *  method which is a copy of addAll method from AbstractCollection class.
 *
 *  @param <T> the type of elements in this Collection
 *
 *  @author  Vakhid Betrakhmadov
 */
public abstract class MyAbstractCollection<T> extends AbstractCollection<T>
{
    /**
     * <p>This implementation iterates over the specified collection, and adds
     * each object returned by the iterator to this collection, in turn.</p>
     * @param collection collection containing elements to be added to this list
     * @return true if this object was modified,false otherwise
     */
    public boolean appendAnything(Collection<T> collection)
    {
        boolean modified = false;
        Iterator<T> iterator = collection.iterator();
        while(iterator.hasNext())
            if(this.add(iterator.next()))
                modified = true;

        return modified;
    }
}
