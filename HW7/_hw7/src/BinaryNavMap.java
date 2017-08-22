import java.util.*;
import java.util.function.Function;

public class BinaryNavMap<K, V> extends AbstractMap<K, V>
        implements NavigableMap<K, V>, Cloneable, java.io.Serializable {

    /* ------- Data fields --------*/
    private Entry<K, V> root = null;
    private Comparator<? super K> comparator = null;
    private int size = 0;
    private V putReturn = null;
    private V deleteReturn = null;

    /* ----- Constructors --------*/
    public BinaryNavMap() {
    }

    public BinaryNavMap(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }

    /* ----------- Helper methods -------------*/
    private static boolean valEquals(Object o1, Object o2) {
        return (o1 == null ? o2 == null : o1.equals(o2));
    }

    private int compare(Object k1, Object k2) {
        return comparator == null ? ((Comparable<? super K>) k1).compareTo((K) k2)
                : comparator.compare((K) k1, (K) k2);
    }

    private Entry<K, V> put(Entry<K, V> root, Entry<K, V> toAdd) {
        int compareResult;
        if (root == null) {
            putReturn = null;
            return toAdd;
        } else if ((compareResult = compare(toAdd.getKey(), root.getKey())) == 0) {
            putReturn = root.getValue();
            root.setValue(toAdd.getValue());
            return root;
        } else if (compareResult < 0) {
            root.left = put(root.left, toAdd);
            return root;
        } else {
            root.right = put(root.right, toAdd);
            return root;
        }
    }

    private Entry<K, V> getEntry(Object key) {
        if (key == null)
            throw new NullPointerException();
        Entry<K, V> p = root;
        while (p != null) {
            int cmp = compare(key, p.getKey());
            if (cmp < 0)
                p = p.left;
            else if (cmp > 0)
                p = p.right;
            else
                return p;
        }
        return null;
    }

    private V deleteEntry(K key) {
        root = deleteEntry(root, key);
        if (deleteReturn != null)
            --size;
        return deleteReturn;
    }

    private Entry<K, V> deleteEntry(Entry<K, V> root, K key) {
        if (root == null) {
            deleteReturn = null;
            return null;
        }
        // Search for item to delete.
        int compResult = compare(key, root.getKey());
        if (compResult < 0) {
            // item is smaller than localRoot.data.
            root.left = deleteEntry(root.left, key);
            return root;
        } else if (compResult > 0) {
            // item is larger than localRoot.data.
            root.right = deleteEntry(root.right, key);
            return root;
        } else {
            // item is at local root.
            deleteReturn = root.getValue();
            if (root.left == null) {
                // If there is no left child, return right child
                // which can also be null.
                return root.right;
            } else if (root.right == null) {
                // If there is no right child, return left child.
                return root.left;
            } else {
                // Node being deleted has 2 children, replace the data
                // with inorder predecessor.
                if (root.left.right == null) {
                    // The left child has no right child.
                    // Replace the data with the data in the
                    // left child.
                    root.setValue(root.left.getValue());
                    // Replace the left child with its left child.
                    root.left = root.left.left;
                    return root;
                } else {
                    // Search for the inorder predecessor (ip) and
                    // replace deleted node�s data with ip.
                    root.setValue(findLargestChild(root.left));
                    return root;
                }
            }
        }
    }

    private V findLargestChild(Entry<K, V> parent) {
        // If the right child has no right child, it is
        // the inorder predecessor.
        if (parent.right.right == null) {
            V returnValue = parent.right.getValue();
            parent.right = parent.right.left;
            return returnValue;
        } else {
            return findLargestChild(parent.right);
        }
    }

    private Entry<K, V> getLowerEntry(Entry<K, V> root, K key) {
        Entry<K, V> result;
        if ((result = root) != null) {
            int cmp = compare(key, root.getKey());
            if (cmp > 0) {
                if (root.right != null)
                    result = getLowerEntry(root.right, key);
                return (result != null ? result : root);
            } else {
                if (root.left != null)
                    result = getLowerEntry(root.left, key);
                else
                    return null;
            }
        }
        return result;
    }

    private Entry<K, V> getFloorEntry(Entry<K, V> root, K key) {
        Entry<K, V> result;
        if ((result = root) != null) {
            int cmp = compare(key, root.getKey());
            if (cmp == 0)
                return root;
            else if (cmp > 0) {
                if (root.right != null)
                    result = getFloorEntry(root.right, key);
                return (result != null ? result : root);
            } else {
                if (root.left != null)
                    result = getFloorEntry(root.left, key);
                else
                    return null;
            }
        }
        return result;
    }

    private Entry<K, V> getHigherEntry(Entry<K, V> root, K key) {
        Entry<K, V> result;
        if ((result = root) != null) {
            int cmp = compare(key, root.getKey());
            if (cmp < 0) {
                if (root.left != null)
                    result = getHigherEntry(root.left, key);
                return (result != null ? result : root);
            } else {
                if (root.right != null)
                    result = getHigherEntry(root.right, key);
                else
                    return null;
            }
        }
        return result;
    }

    private Entry<K, V> getCeilingEntry(Entry<K, V> root, K key) {
        Entry<K, V> result;
        if ((result = root) != null) {
            int cmp = compare(key, root.getKey());
            if (cmp == 0)
                return root;
            else if (cmp < 0) {
                if (root.left != null)
                    result = getCeilingEntry(root.left, key);
                return (result != null ? result : root);
            } else {
                if (root.right != null)
                    result = getCeilingEntry(root.right, key);
                else
                    return null;
            }
        }
        return result;
    }

    /**
     * Returns the first Entry in the BinaryNavMap (according to the BinaryNavMap's
     * key-sort function).  Returns null if the BinaryNavMap is empty.
     */
    private Entry<K, V> getFirstEntry() {
        Entry<K, V> p = root;
        if (p != null)
            while (p.left != null)
                p = p.left;
        return p;
    }

    /**
     * Returns the last Entry in the BinaryNavMap (according to the BinaryNavMap's
     * key-sort function).  Returns null if the BinaryNavMap is empty.
     */
    private Entry<K, V> getLastEntry() {
        Entry<K, V> p = root;
        if (p != null)
            while (p.right != null)
                p = p.right;
        return p;
    }

    /*-------------------------------- Nested classes ---------------------------------*/
    private static class Entry<K, V> implements Map.Entry<K, V> {
        K key;
        V value;
        Entry<K, V> left = null;
        Entry<K, V> right = null;

        /**
         * Make a new cell with given key, value, and parent, and with
         * {@code null} child links, and BLACK color.
         */
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns the key.
         *
         * @return the key
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value associated with the key.
         *
         * @return the value associated with the key
         */
        public V getValue() {
            return value;
        }

        /**
         * Replaces the value currently associated with the key with the given
         * value.
         *
         * @return the value associated with the key before this method was
         * called
         */
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;

            return valEquals(key, e.getKey()) && valEquals(value, e.getValue());
        }

        public int hashCode() {
            int keyHash = (key == null ? 0 : key.hashCode());
            int valueHash = (value == null ? 0 : value.hashCode());
            return keyHash ^ valueHash;
        }

        public String toString() {
            return key + "=" + value;
        }
    }

    // ------ EntrySet class ------- //
    class EntrySet extends AbstractSet<Map.Entry<K, V>> {

        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
            Object value = entry.getValue();
            Entry<K, V> p = getEntry(entry.getKey());
            return p != null && valEquals(p.getValue(), value);
        }

        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
            Object value = entry.getValue();
            Entry<K, V> p = getEntry(entry.getKey());
            if (p != null && valEquals(p.getValue(), value)) {
                deleteEntry(p.getKey());
                return true;
            }
            return false;
        }

        public int size() {
            return BinaryNavMap.this.size();
        }

        public void clear() {
            BinaryNavMap.this.clear();
        }
    }

    // ------ NavigableKeySet class ------- //
    class NavigableKeySet extends AbstractSet<K> implements NavigableSet<K>
    {
        public Iterator<K> iterator(){ return new KeyIterator();}
        public Iterator<K> descendingIterator(){return new DescendingKeyIterator();}
        public boolean contains(Object key){return BinaryNavMap.this.containsKey(key);}
        public boolean remove(Object key){ return (BinaryNavMap.this.remove(key) != null);}
        public int size(){return BinaryNavMap.this.size();}
        public void clear(){BinaryNavMap.this.clear();}
        public K ceiling(K key){return BinaryNavMap.this.ceilingKey(key);}
        public K floor(K key){return BinaryNavMap.this.floorKey(key);}
        public K higher(K key){return BinaryNavMap.this.higherKey(key);}
        public K lower(K key){return BinaryNavMap.this.lowerKey(key);}
        public K pollFirst(){return BinaryNavMap.this.pollFirstEntry().getKey();}
        public K pollLast(){return BinaryNavMap.this.pollLastEntry().getKey();}
        public Comparator<? super K> comparator(){return BinaryNavMap.this.comparator();}
        public K first(){return BinaryNavMap.this.firstKey();}
        public K last(){return BinaryNavMap.this.lastKey();}
        public NavigableSet<K> descendingSet() { return new DescendingNavigableKeySet();}
        public NavigableSet<K> subSet(K fromElement, boolean fromInclusive,K toElement, boolean toInclusive){
            return new SubSet(fromElement,fromInclusive,toElement,toInclusive);
        }
        public NavigableSet<K> headSet(K toElement, boolean toInclusive) {return new SubSet(first(),true,toElement,toInclusive);}
        public NavigableSet<K> tailSet(K fromElement, boolean inclusive){return new SubSet(fromElement,inclusive,last(),true);}
        public SortedSet<K> subSet(K fromElement,K toElement){return subSet(fromElement,true,toElement,false);}
        public SortedSet<K> headSet(K toElement){return headSet(toElement,false);}
        public SortedSet<K> tailSet(K fromElement){return tailSet(fromElement,false);}

        // ------ DescendingNavigableKeySet class ------- //
        class DescendingNavigableKeySet extends AbstractSet<K> implements NavigableSet<K>
        {
            public Iterator<K> iterator(){ return NavigableKeySet.this.descendingIterator();}
            public Iterator<K> descendingIterator(){return NavigableKeySet.this.iterator();}
            public boolean contains(Object key){return NavigableKeySet.this.contains(key);}
            public boolean remove(Object key){ return NavigableKeySet.this.remove(key);}
            public int size(){return NavigableKeySet.this.size();}
            public void clear(){NavigableKeySet.this.clear();}
            public K ceiling(K key){return NavigableKeySet.this.floor(key);}
            public K floor(K key){return NavigableKeySet.this.ceiling(key);}
            public K higher(K key){return NavigableKeySet.this.lower(key);}
            public K lower(K key){return NavigableKeySet.this.higher(key);}
            public K pollFirst(){return NavigableKeySet.this.pollLast();}
            public K pollLast(){return NavigableKeySet.this.pollFirst();}
            public Comparator<? super K> comparator(){return BinaryNavMap.this.comparator();}
            public K first(){return NavigableKeySet.this.last();}
            public K last(){return NavigableKeySet.this.first();}
            public NavigableSet<K> descendingSet() { return NavigableKeySet.this;}
            public NavigableSet<K> subSet(K fromElement, boolean fromInclusive,K toElement, boolean toInclusive){
                return new DescendingSubSet(fromElement,fromInclusive,toElement,toInclusive);
            }
            public NavigableSet<K> headSet(K toElement, boolean toInclusive) {return new DescendingSubSet(first(),true,toElement,toInclusive);}
            public NavigableSet<K> tailSet(K fromElement, boolean inclusive){return new DescendingSubSet(fromElement,inclusive,last(),true);}
            public SortedSet<K> subSet(K fromElement,K toElement){return subSet(fromElement,true,toElement,false);}
            public SortedSet<K> headSet(K toElement){return headSet(toElement,false);}
            public SortedSet<K> tailSet(K fromElement){return tailSet(fromElement,false);}

            // -------------- DescendingNavigableKeySubSet ----------- //
            class DescendingSubSet extends AbstractSet<K> implements NavigableSet<K>{
                SubSet s;
                DescendingSubSet(K fromElement, boolean fromInclusive,K toElement, boolean toInclusive ){
                    s = new SubSet(toElement,toInclusive,fromElement,fromInclusive);
                }

                public Iterator<K> iterator(){ return s.descendingIterator();}
                public Iterator<K> descendingIterator(){return s.iterator();}
                public boolean contains(Object key){return s.contains(key);}
                public boolean remove(Object key){ return s.remove(key);}
                public int size(){return s.size();}
                public void clear(){s.clear();}
                public K ceiling(K key){return s.floor(key);}
                public K floor(K key){return s.ceiling(key);}
                public K higher(K key){return s.lower(key);}
                public K lower(K key){return s.higher(key);}
                public K pollFirst(){return s.pollLast();}
                public K pollLast(){return s.pollFirst();}
                public Comparator<? super K> comparator(){return BinaryNavMap.this.comparator();}
                public K first(){return s.last();}
                public K last(){return s.first();}
                public NavigableSet<K> descendingSet() { return s;}
                public NavigableSet<K> subSet(K fromElement, boolean fromInclusive,K toElement, boolean toInclusive){
                    return new DescendingSubSet(fromElement,fromInclusive,toElement,toInclusive);
                }
                public NavigableSet<K> headSet(K toElement, boolean toInclusive) {return new DescendingSubSet(first(),true,toElement,toInclusive);}
                public NavigableSet<K> tailSet(K fromElement, boolean inclusive){return new DescendingSubSet(fromElement,inclusive,last(),true);}
                public SortedSet<K> subSet(K fromElement,K toElement){return subSet(fromElement,true,toElement,false);}
                public SortedSet<K> headSet(K toElement){return headSet(toElement,false);}
                public SortedSet<K> tailSet(K fromElement){return tailSet(fromElement,false);}
            }
        }

        // ------ NavigableKeySet class's SubSet class ------- //
        class SubSet extends AbstractSet<K> implements NavigableSet<K> {
            RangerChecker rangeChecker = null;

            SubSet(K fromElement, boolean fromInclusive,K toElement, boolean toInclusive ){
                if((compare(fromElement,toElement) == 0 && ((!fromInclusive  && toInclusive) || (!toInclusive && fromInclusive)))
                        || compare(fromElement,toElement) > 0)
                    throw new IllegalArgumentException();
                rangeChecker = new RangerChecker(fromElement,fromInclusive,toElement,toInclusive);
            }

            // Helper method
            private int subSetSize(){
                int i = 0;
                for(Iterator<K> it = iterator();it.hasNext();){
                    it.next();i++;
                }
                return i;
            }
            // Helper method
            private void subSetClear(){
                for(Iterator<K> it = iterator();it.hasNext();) {
                    it.next(); it.remove();
                }
            }

            public Iterator<K> iterator(){ return new KeyIterator(rangeChecker);}
            public Iterator<K> descendingIterator(){return new DescendingKeyIterator(rangeChecker);}
            public boolean contains(Object o){return (rangeChecker.apply(o) && BinaryNavMap.this.containsKey(o));}
            public boolean remove(Object o){ return (rangeChecker.apply(o) && (BinaryNavMap.this.remove(o) != null));}
            public int size(){return subSetSize();}
            public void clear(){subSetClear();}
            public K ceiling(K key){
                K result = BinaryNavMap.this.ceilingKey(key);
                if(result!=null) {
                    if(rangeChecker.apply(result))
                        return result;
                    else if(compare(result,rangeChecker.fromElement) <= 0)
                        return (rangeChecker.fromInclusive && contains(rangeChecker.fromElement) ? rangeChecker.fromElement :
                                (rangeChecker.apply (result = BinaryNavMap.this.higherKey(rangeChecker.fromElement)) ?
                                        result : null));
                    else if(compare(result,rangeChecker.toElement) == 0 && rangeChecker.toInclusive)
                        return rangeChecker.toElement;
                    else
                        return null;
                }
                return null;
            }
            public K floor(K key){
                K result = BinaryNavMap.this.floorKey(key);
                if(result!=null) {
                    if(rangeChecker.apply(result))
                        return result;
                    else if(compare(result,rangeChecker.toElement) >= 0)
                        return (rangeChecker.toInclusive && contains(rangeChecker.toElement) ? rangeChecker.toElement :
                                (rangeChecker.apply (result = BinaryNavMap.this.lowerKey(rangeChecker.toElement)) ?
                                        result : null));
                    else if(compare(result,rangeChecker.fromElement) == 0 && rangeChecker.fromInclusive)
                        return rangeChecker.fromElement;
                    else
                        return null;
                }
                return null;
            }
            public K higher(K key){
                K result = BinaryNavMap.this.higherKey(key);
                if(result!=null) {
                    if(rangeChecker.apply(result))
                        return result;
                    else if(compare(result,rangeChecker.fromElement) <= 0)
                        return (rangeChecker.fromInclusive && contains(rangeChecker.fromElement) ? rangeChecker.fromElement :
                                (rangeChecker.apply (result = BinaryNavMap.this.higherKey(rangeChecker.fromElement)) ?
                                        result : null));
                    else if(compare(result,rangeChecker.toElement) == 0 && rangeChecker.toInclusive)
                        return rangeChecker.toElement;
                    else
                        return null;
                }
                return null;
            }
            public K lower(K key){
                K result = BinaryNavMap.this.lowerKey(key);
                if(result!=null) {
                    if(rangeChecker.apply(result))
                        return result;
                    else if(compare(result,rangeChecker.toElement) >= 0)
                        return (rangeChecker.toInclusive && contains(rangeChecker.toElement) ? rangeChecker.toElement :
                                (rangeChecker.apply (result = BinaryNavMap.this.lowerKey(rangeChecker.toElement)) ?
                                        result : null));
                    else if(compare(result,rangeChecker.fromElement) == 0 && rangeChecker.fromInclusive)
                        return rangeChecker.fromElement;
                    else
                        return null;
                }
                return null;
            }
            public K pollFirst(){
                K toDelete = first();
                if(toDelete != null) {
                    deleteEntry(toDelete);
                    rangeChecker.fromElement = higher(rangeChecker.fromElement);
                    rangeChecker.fromInclusive = true;
                    if(rangeChecker.fromElement == null || compare(rangeChecker.fromElement,rangeChecker.toElement) > 0){
                        rangeChecker.fromElement = rangeChecker.toElement;
                        rangeChecker.fromInclusive = false;
                    }
                }
                return toDelete;
            }
            public K pollLast(){
                K toDelete = last();
                if(toDelete != null){
                    deleteEntry(toDelete);
                    rangeChecker.toElement = lower(rangeChecker.toElement);
                    rangeChecker.toInclusive = true;
                    if(rangeChecker.toElement == null || compare(rangeChecker.toElement,rangeChecker.fromElement) < 0){
                        rangeChecker.toElement = rangeChecker.fromElement;
                        rangeChecker.toInclusive = false;
                    }
                }
                return toDelete;
            }
            public Comparator<? super K> comparator(){return BinaryNavMap.this.comparator();}
            public K first(){return (rangeChecker.fromInclusive && contains(rangeChecker.fromElement)? rangeChecker.fromElement : higherKey(rangeChecker.fromElement));}
            public K last(){return (rangeChecker.toInclusive  && contains(rangeChecker.toElement)? rangeChecker.toElement : lowerKey(rangeChecker.toElement));}
            public NavigableSet<K> descendingSet() {
                return new DescendingNavigableKeySet().subSet(rangeChecker.toElement,rangeChecker.toInclusive,rangeChecker.fromElement,rangeChecker.fromInclusive);}
            public NavigableSet<K> subSet(K fromElement, boolean fromInclusive,K toElement, boolean toInclusive){
                return new SubSet(fromElement,fromInclusive,toElement,toInclusive);
            }
            public NavigableSet<K> headSet(K toElement, boolean toInclusive) {return new SubSet(first(),true,toElement,toInclusive);}
            public NavigableSet<K> tailSet(K fromElement, boolean inclusive){return new SubSet(fromElement,inclusive,last(),true);}
            public SortedSet<K> subSet(K fromElement,K toElement){return subSet(fromElement,true,toElement,false);}
            public SortedSet<K> headSet(K toElement){return headSet(toElement,false);}
            public SortedSet<K> tailSet(K fromElement){return tailSet(fromElement,false);}
        }
    }

    // ------ SubMap class ------- //
    class SubMap extends AbstractMap<K,V> implements NavigableMap<K,V>{
        NavigableKeySet.SubSet s;

        public SubMap(K fromElement, boolean fromInclusive,K toElement, boolean toInclusive ){
            s = (NavigableKeySet.SubSet) new NavigableKeySet().subSet(fromElement,fromInclusive,toElement,toInclusive);
        }

        // ------ EntrySubSet class ------- //
        class EntrySubSet extends AbstractSet<Map.Entry<K, V>> {
            public Iterator<Map.Entry<K, V>> iterator() {
                return new EntryIterator(s.rangeChecker);
            }
            public boolean contains(Object o) {return s.contains(o);}
            public boolean remove(Object o) { return s.remove(o);}
            public int size() {
                return s.size();
            }
            public void clear() {
                s.clear();
            }
        }

        public int size() {return s.size();}
        public V put(K key, V value) {throw new UnsupportedOperationException();}
        public V get(Object key) {return (s.contains(key) ? BinaryNavMap.this.get(key) : null);}
        public boolean containsKey(Object key){ return s.contains(key);}
        public V remove(Object key){
            V result = get(key);
            if(result != null)
                s.remove(key);
            return result;
        }
        public void clear(){s.clear();}
        public Entry<K, V> lowerEntry(K key){K result = s.lower(key);return (result != null ? getEntry(result) : null);}
        public K lowerKey(K key){ return s.lower(key);}
        public Entry<K, V> floorEntry(K key){K result = s.floor(key);return (result != null ? getEntry(result) : null);}
        public K floorKey(K key){return s.floor(key);}
        public Entry<K, V> ceilingEntry(K key){K result = s.ceiling(key);return (result != null ? getEntry(result) : null);}
        public K ceilingKey(K key){return s.ceiling(key);}
        public Entry<K, V> higherEntry(K key){K result = s.higher(key);return (result != null ? getEntry(result) : null);}
        public K higherKey(K key){return s.higher(key);}
        public Entry<K, V> firstEntry(){K k = s.first(); return (k != null ? getEntry(k) : null);}
        public Entry<K, V> lastEntry(){K k = s.last(); return (k != null ? getEntry(k) : null);}
        public Entry<K, V> pollFirstEntry(){
            Map.Entry<K,V> result = firstEntry();
            if(result != null)
                deleteEntry(result.getKey());
            return result;
        }
        public Entry<K, V> pollLastEntry(){
            Map.Entry<K,V> result = lastEntry();
            if(result != null)
                deleteEntry(result.getKey());
            return result;
        }
        public K firstKey() {return s.first();}
        public K lastKey() {return s.last();}
        public Set<Map.Entry<K, V>> entrySet() {return new EntrySubSet();}
        public NavigableMap<K, V> descendingMap() {return new DescendingMap().subMap(s.rangeChecker.toElement,s.rangeChecker.toInclusive,
                s.rangeChecker.fromElement,s.rangeChecker.fromInclusive);}
        public NavigableSet<K> navigableKeySet() {return s;}
        public NavigableSet<K> descendingKeySet() {return s.descendingSet();}
        public Comparator<? super K> comparator() {return BinaryNavMap.this.comparator();}
        public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            return new SubMap(fromKey,fromInclusive,toKey,toInclusive);}
        public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {return subMap(s.rangeChecker.fromElement,s.rangeChecker.fromInclusive,toKey,inclusive);}
        public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {return subMap(fromKey,inclusive,s.rangeChecker.toElement,s.rangeChecker.toInclusive);}
        public SortedMap<K, V> subMap(K fromKey, K toKey) {return subMap(fromKey, true, toKey, false);}
        public SortedMap<K, V> headMap(K toKey) {return headMap(toKey,false);}
        public SortedMap<K, V> tailMap(K fromKey) {return tailMap(fromKey,false);}
    }

    // ------ DescendingMap class ------- //
    class DescendingMap extends AbstractMap<K, V> implements NavigableMap<K, V> {

        class DescendingEntrySet extends AbstractSet<Map.Entry<K, V>> implements Set<Map.Entry<K, V>>{
            public Iterator<Map.Entry<K, V>> iterator() {
                return new DescendingEntryIterator();
            }
            public boolean contains(Object o){ return BinaryNavMap.this.entrySet().contains(o);}
            public boolean remove(Object o){ return BinaryNavMap.this.entrySet().remove(o);}
            public int size(){ return BinaryNavMap.this.size();}
            public void clear(){ BinaryNavMap.this.clear();}
        }

        // ------ DescendingSubMap class ------- //
        class DescendingSubMap extends AbstractMap<K,V> implements NavigableMap<K,V>{
            SubMap m;
            public DescendingSubMap(K fromElement, boolean fromInclusive,K toElement, boolean toInclusive ){
                m = new SubMap(toElement,toInclusive,fromElement,fromInclusive);
            }

            // ------ EntrySubSet class ------- //
            class DescendingEntrySubSet extends AbstractSet<Map.Entry<K, V>> {
                public Iterator<Map.Entry<K, V>> iterator() {
                    return new DescendingEntryIterator(m.s.rangeChecker);
                }
                public boolean contains(Object o) {return m.s.contains(o);}
                public boolean remove(Object o) { return m.s.remove(o);}
                public int size() {
                    return m.s.size();
                }
                public void clear() {m.s.clear();}
            }

            public int size() {return m.s.size();}
            public V put(K key, V value) {throw new UnsupportedOperationException();}
            public V get(Object key) {return (m.s.contains(key) ? BinaryNavMap.this.get(key) : null);}
            public boolean containsKey(Object key){ return m.s.contains(key);}
            public V remove(Object key){
                V result = get(key);
                if(result != null)
                    m.s.remove(key);
                return result;
            }
            public void clear(){m.s.clear();}
            public Entry<K, V> lowerEntry(K key){K k = m.s.higher(key);return (k != null ? getEntry(k) : null);}
            public K lowerKey(K key){ return m.s.higher(key);}
            public Entry<K, V> floorEntry(K key){K k = m.s.ceiling(key);return (k != null ? getEntry(k) : null);}
            public K floorKey(K key){return m.s.ceiling(key);}
            public Entry<K, V> ceilingEntry(K key){K k = m.s.floor(key);return (k != null ? getEntry(k) : null);}
            public K ceilingKey(K key){return m.s.floor(key);}
            public Entry<K, V> higherEntry(K key){K k = m.s.lower(key);return (k != null ? getEntry(k) : null);}
            public K higherKey(K key){return m.s.lower(key);}
            public Entry<K, V> firstEntry(){K k = m.s.last(); return (k != null ? getEntry(k) : null);}
            public Entry<K, V> lastEntry(){K k = m.s.first(); return (k != null ? getEntry(k) : null);}
            public Entry<K, V> pollFirstEntry(){
                Map.Entry<K,V> result = firstEntry();
                if(result != null)
                    deleteEntry(result.getKey());
                return result;
            }
            public Entry<K, V> pollLastEntry(){
                Map.Entry<K,V> result = lastEntry();
                if(result != null)
                    deleteEntry(result.getKey());
                return result;
            }
            public K firstKey() {return m.s.last();}
            public K lastKey() {return m.s.first();}
            public Set<Map.Entry<K, V>> entrySet() {return new DescendingEntrySubSet();}
            public NavigableMap<K, V> descendingMap() {return m;}
            public NavigableSet<K> navigableKeySet() {return m.s.descendingSet();}
            public NavigableSet<K> descendingKeySet() {return m.s;}
            public Comparator<? super K> comparator() {return BinaryNavMap.this.comparator();}
            public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
                return new DescendingSubMap(fromKey,fromInclusive,toKey,toInclusive);}
            public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {return subMap(m.s.rangeChecker.fromElement,m.s.rangeChecker.fromInclusive,toKey,inclusive);}
            public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {return subMap(fromKey,inclusive,m.s.rangeChecker.toElement,m.s.rangeChecker.toInclusive);}
            public SortedMap<K, V> subMap(K fromKey, K toKey) {return subMap(fromKey, true, toKey, false);}
            public SortedMap<K, V> headMap(K toKey) {return headMap(toKey,false);}
            public SortedMap<K, V> tailMap(K fromKey) {return tailMap(fromKey,false);}
        }

        public int size() {return BinaryNavMap.this.size();}
        public V put(K key, V value) {return BinaryNavMap.this.put(key, value);}
        public V get(Object key) {return BinaryNavMap.this.get(key);}
        public boolean containsKey(Object key){ return BinaryNavMap.this.containsKey(key);}
        public V remove(Object key){ return BinaryNavMap.this.remove(key);}
        public void clear(){BinaryNavMap.this.clear();}
        public Entry<K, V> lowerEntry(K key){return BinaryNavMap.this.higherEntry(key);}
        public K lowerKey(K key){ return BinaryNavMap.this.higherKey(key);}
        public Entry<K, V> floorEntry(K key){return BinaryNavMap.this.ceilingEntry(key);}
        public K floorKey(K key){return BinaryNavMap.this.ceilingKey(key);}
        public Entry<K, V> ceilingEntry(K key){return BinaryNavMap.this.floorEntry(key);}
        public K ceilingKey(K key){return BinaryNavMap.this.floorKey(key);}
        public Entry<K, V> higherEntry(K key){return BinaryNavMap.this.lowerEntry(key);}
        public K higherKey(K key){return BinaryNavMap.this.lowerKey(key);}
        public Entry<K, V> firstEntry(){return BinaryNavMap.this.lastEntry();}
        public Entry<K, V> lastEntry(){return BinaryNavMap.this.firstEntry(); }
        public Entry<K, V> pollFirstEntry(){return BinaryNavMap.this.pollLastEntry();}
        public Entry<K, V> pollLastEntry(){return BinaryNavMap.this.pollFirstEntry();}
        public K firstKey() {return BinaryNavMap.this.lastKey();}
        public K lastKey() {return BinaryNavMap.this.firstKey();}
        public Set<Map.Entry<K, V>> entrySet() {return new DescendingEntrySet();}
        public NavigableMap<K, V> descendingMap() {return BinaryNavMap.this;}
        public NavigableSet<K> navigableKeySet() {return new NavigableKeySet().descendingSet();}
        public NavigableSet<K> descendingKeySet() {return new NavigableKeySet();}
        public Comparator<? super K> comparator() {return BinaryNavMap.this.comparator();}
        public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            return new DescendingSubMap(fromKey,fromInclusive,toKey,toInclusive);}
        public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {return subMap(firstKey(),true,toKey,inclusive);}
        public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {return subMap(fromKey,inclusive,lastKey(),true);}
        public SortedMap<K, V> subMap(K fromKey, K toKey) {return subMap(fromKey, true, toKey, false);}
        public SortedMap<K, V> headMap(K toKey) {return headMap(toKey,false);}
        public SortedMap<K, V> tailMap(K fromKey) {return tailMap(fromKey,false);}
    }

    // ----------------- Iterators --------------------- //
    private abstract class PrivateEntryIterator<T> implements Iterator<T> {
        protected Deque<Entry<K, V>> entries = new ArrayDeque<>();
        protected Entry<K, V> next = null;
        protected Entry<K, V> lastReturned = null;
        protected Entry<K, V> current = null;
        protected Function<K,Boolean> rangeChecker = null;

        protected PrivateEntryIterator() {
            current = root;
        }

        protected PrivateEntryIterator(Function<K,Boolean> rangeChecker) {
            current = root;
            this.rangeChecker = rangeChecker;
            if(current != null){
                do{
                    nextEntry();
                }while(!rangeChecker.apply(next.getKey()) && (current != null || !entries.isEmpty())); // get to fromElement
                current = null;
                if(rangeChecker.apply(next.getKey())) // (0,false,0,false) case check
                    entries.push(next);
            }
        }

        protected Entry<K, V> nextEntry() {
            nextEntry(entries);
            return next;
        }

        protected void nextEntry(Deque<Entry<K, V>> entries) {
            while (current != null) {
                entries.push(current);
                current = current.left;
            }
            next = entries.pop();
            lastReturned = next;
            current = next.right;
        }

        public boolean hasNext() {
            if(rangeChecker == null)
                return (current != null || !entries.isEmpty());
            if(current != null && !rangeChecker.apply(current.getKey()))
                return false;
            if(current == null && !entries.isEmpty() && !rangeChecker.apply(entries.peek().getKey()))
                return false;
            return (current != null || !entries.isEmpty());
        }

        public void remove() {
            if (lastReturned == null)
                throw new IllegalStateException();
            deleteEntry(lastReturned.getKey());
            lastReturned = null;
        }
    }

    // In-order traverse iterator
    private class EntryIterator extends PrivateEntryIterator<Map.Entry<K, V>> {
        public EntryIterator(){super();}
        public EntryIterator(Function<K,Boolean> rangeChecker){super(rangeChecker);}

        public Map.Entry<K, V> next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return nextEntry();
        }
    }

    private class KeyIterator extends PrivateEntryIterator<K> {
        public KeyIterator(){super();}
        public KeyIterator(Function<K,Boolean> rangeChecker){super(rangeChecker);}
        public K next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return nextEntry().getKey();
        }
    }

    private class DescendingEntryIterator extends PrivateEntryIterator<Map.Entry<K, V>> {
        public DescendingEntryIterator(){super();}
        public DescendingEntryIterator(Function<K,Boolean> rangeChecker){super(rangeChecker);}

        public Map.Entry<K, V> next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return nextEntry();
        }

        protected void nextEntry(Deque<Entry<K, V>> entries) {
            while (current != null) {
                entries.push(current);
                current = current.right;
            }
            next = entries.pop();
            lastReturned = next;
            current = next.left;
        }
    }

    private class DescendingKeyIterator extends PrivateEntryIterator<K> {
        public DescendingKeyIterator(){super();}
        public DescendingKeyIterator(Function<K,Boolean> rangeChecker){super(rangeChecker);}

        public K next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return nextEntry().getKey();
        }

        protected void nextEntry(Deque<Entry<K, V>> entries) {
            while (current != null) {
                entries.push(current);
                current = current.right;
            }
            next = entries.pop();
            lastReturned = next;
            current = next.left;
        }
    }

    // ------ Helper inner class
    class RangerChecker implements Function<K,Boolean> {
        private RangerChecker(K fromElement, boolean fromInclusive,K toElement, boolean toInclusive ){
            this.fromElement = fromElement; this.toElement = toElement;
            this.fromInclusive = fromInclusive; this.toInclusive = toInclusive;
        }

        K fromElement;
        K toElement;
        boolean fromInclusive;
        boolean toInclusive;

        public Boolean apply(Object key){
            if(fromInclusive && toInclusive)
                return (compare(key,fromElement) >= 0 && compare(key,toElement) <= 0);
            else if(!fromInclusive && toInclusive)
                return (compare(key,fromElement) > 0 && compare(key,toElement) <= 0);
            else if(fromInclusive && !toInclusive)
                return (compare(key,fromElement) >= 0 && compare(key,toElement) < 0);
            else
                return (compare(key,fromElement) > 0 && compare(key,toElement) < 0);
        }
    }//------------ Helper class end


    //********** -------------------- Main methods ------------------------**********//

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     *
     * @return the previous value associated with {@code key}, or
     *         {@code null} if there was no mapping for {@code key}.
     *         (A {@code null} return can also indicate that the map
     *         previously associated {@code null} with {@code key}.)
     * @throws ClassCastException if the specified key cannot be compared
     *         with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *         and this map uses natural ordering, or its comparator
     *         does not permit null keys
     */
    public V put(K key, V value) {
        Entry<K, V> toAdd = new Entry<>(key, value);
        root = put(root, toAdd);
        if (putReturn == null)
            ++size;

        return putReturn;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code key} compares
     * equal to {@code k} according to the map's ordering, then this
     * method returns {@code v}; otherwise it returns {@code null}.
     * (There can be at most one such mapping.)
     *
     * <p>A return value of {@code null} does not <em>necessarily</em>
     * indicate that the map contains no mapping for the key; it's also
     * possible that the map explicitly maps the key to {@code null}.
     * The {@link #containsKey containsKey} operation may be used to
     * distinguish these two cases.
     *
     * @throws ClassCastException if the specified key cannot be compared
     *         with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *         and this map uses natural ordering, or its comparator
     *         does not permit null keys
     */
    public V get(Object key) {
        Entry<K, V> p = getEntry(key);
        return (p == null ? null : p.value);
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     *
     * <p>The set's iterator returns the entries in ascending key order. The
     * sets's spliterator is
     * <em><a href="Spliterator.html#binding">late-binding</a></em>,
     * <em>fail-fast</em>, and additionally reports {@link Spliterator#SORTED} and
     * {@link Spliterator#ORDERED} with an encounter order that is ascending key
     * order.
     *
     * <p>The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own {@code remove} operation, or through the
     * {@code setValue} operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the {@code Iterator.remove},
     * {@code Set.remove}, {@code removeAll}, {@code retainAll} and
     * {@code clear} operations.  It does not support the
     * {@code add} or {@code addAll} operations.
     */
    public Set<Map.Entry<K, V>> entrySet() {  //done
        return new EntrySet();
    }

    /**
     * Returns {@code true} if this map contains a mapping for the specified
     * key.
     *
     * @param key key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the
     * specified key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map uses natural ordering, or its comparator
     *                              does not permit null keys
     */
    public boolean containsKey(Object key) {
        return getEntry(key) != null;
    }

    /**
     * Removes the mapping for this key from this BinaryNavMap if present.
     *
     * @param key key for which mapping should be removed
     * @return the previous value associated with {@code key}, or
     * {@code null} if there was no mapping for {@code key}.
     * (A {@code null} return can also indicate that the map
     * previously associated {@code null} with {@code key}.)
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map uses natural ordering, or its comparator
     *                              does not permit null keys
     */
    public V remove(Object key) {
        Entry<K, V> p = getEntry(key);
        if (p == null)
            return null;

        V oldValue = p.value;
        deleteEntry(p.getKey());
        return oldValue;
    }

    /**
     * Removes all of the mappings from this map.
     * The map will be empty after this call returns.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Returns a key-value mapping associated with the greatest key
     * strictly less than the given key, or {@code null} if there is
     * no such key.
     *
     * @param key the key
     * @return an entry with the greatest key less than {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public Entry<K, V> lowerEntry(K key) { //done
        return getLowerEntry(root, key);
    }

    /**
     * Returns the greatest key strictly less than the given key, or
     * {@code null} if there is no such key.
     *
     * @param key the key
     * @return the greatest key less than {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public K lowerKey(K key) { //done
        Entry<K, V> toReturn = getLowerEntry(root, key);
        return (toReturn == null ? null : toReturn.getKey());
    }

    /**
     * Returns a key-value mapping associated with the greatest key
     * less than or equal to the given key, or {@code null} if there
     * is no such key.
     *
     * @param key the key
     * @return an entry with the greatest key less than or equal to
     * {@code key}, or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public Entry<K, V> floorEntry(K key) { //done
        return getFloorEntry(root, key);
    }

    /**
     * Returns the greatest key less than or equal to the given key,
     * or {@code null} if there is no such key.
     *
     * @param key the key
     * @return the greatest key less than or equal to {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public K floorKey(K key) { //done
        Entry<K, V> toReturn = getFloorEntry(root, key);
        return (toReturn == null ? null : toReturn.getKey());
    }

    /**
     * Returns a key-value mapping associated with the least key
     * greater than or equal to the given key, or {@code null} if
     * there is no such key.
     *
     * @param key the key
     * @return an entry with the least key greater than or equal to
     * {@code key}, or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public Entry<K, V> ceilingEntry(K key) { //done
        return getCeilingEntry(root, key);
    }

    /**
     * Returns the least key greater than or equal to the given key,
     * or {@code null} if there is no such key.
     *
     * @param key the key
     * @return the least key greater than or equal to {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public K ceilingKey(K key) { //done
        Entry<K, V> toReturn = getCeilingEntry(root, key);
        return (toReturn == null ? null : toReturn.getKey());
    }

    /**
     * Returns a key-value mapping associated with the least key
     * strictly greater than the given key, or {@code null} if there
     * is no such key.
     *
     * @param key the key
     * @return an entry with the least key greater than {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public Entry<K, V> higherEntry(K key) { //done
        return getHigherEntry(root, key);
    }

    /**
     * Returns the least key strictly greater than the given key, or
     * {@code null} if there is no such key.
     *
     * @param key the key
     * @return the least key greater than {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public K higherKey(K key) { //done
        Entry<K, V> toReturn = getHigherEntry(root, key);
        return (toReturn == null ? null : toReturn.getKey());
    }

    /**
     * Returns a key-value mapping associated with the least
     * key in this map, or {@code null} if the map is empty.
     *
     * @return an entry with the least key,
     * or {@code null} if this map is empty
     */
    @Override
    public Entry<K, V> firstEntry() { //done
        return getFirstEntry();
    }

    /**
     * Returns a key-value mapping associated with the greatest
     * key in this map, or {@code null} if the map is empty.
     *
     * @return an entry with the greatest key,
     * or {@code null} if this map is empty
     */
    @Override
    public Entry<K, V> lastEntry() { //done
        return getLastEntry();
    }

    /**
     * Removes and returns a key-value mapping associated with
     * the least key in this map, or {@code null} if the map is empty.
     *
     * @return the removed first entry of this map,
     * or {@code null} if this map is empty
     */
    @Override
    public Entry<K, V> pollFirstEntry() { //done
        Entry<K, V> result = getFirstEntry();
        if (result != null)
            deleteEntry(result.getKey());
        return result;
    }

    /**
     * Removes and returns a key-value mapping associated with
     * the greatest key in this map, or {@code null} if the map is empty.
     *
     * @return the removed last entry of this map,
     * or {@code null} if this map is empty
     */
    @Override
    public Entry<K, V> pollLastEntry() { //done
        Entry<K, V> result = getLastEntry();
        if (result != null)
            deleteEntry(result.getKey());
        return result;
    }

    /**
     * Returns a reverse order view of the mappings contained in this map.
     * The descending map is backed by this map, so changes to the map are
     * reflected in the descending map, and vice-versa.  If either map is
     * modified while an iteration over a collection view of either map
     * is in progress (except through the iterator's own {@code remove}
     * operation), the results of the iteration are undefined.
     *
     * <p>The returned map has an ordering equivalent to
     * <tt>{@link Collections#reverseOrder(Comparator) Collections.reverseOrder}(comparator())</tt>.
     * The expression {@code m.descendingMap().descendingMap()} returns a
     * view of {@code m} essentially equivalent to {@code m}.
     *
     * @return a reverse order view of this map
     */
    @Override
    public NavigableMap<K, V> descendingMap() {return new DescendingMap();}

    /**
     * Returns a {@link NavigableSet} view of the keys contained in this map.
     * The set's iterator returns the keys in ascending order.
     * The set is backed by the map, so changes to the map are reflected in
     * the set, and vice-versa.  If the map is modified while an iteration
     * over the set is in progress (except through the iterator's own {@code
     * remove} operation), the results of the iteration are undefined.  The
     * set supports element removal, which removes the corresponding mapping
     * from the map, via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear} operations.
     * It does not support the {@code add} or {@code addAll} operations.
     *
     * @return a navigable set view of the keys in this map
     */
    @Override
    public NavigableSet<K> navigableKeySet() {
        return new NavigableKeySet();
    }

    /**
     * Returns a reverse order {@link NavigableSet} view of the keys contained in this map.
     * The set's iterator returns the keys in descending order.
     * The set is backed by the map, so changes to the map are reflected in
     * the set, and vice-versa.  If the map is modified while an iteration
     * over the set is in progress (except through the iterator's own {@code
     * remove} operation), the results of the iteration are undefined.  The
     * set supports element removal, which removes the corresponding mapping
     * from the map, via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear} operations.
     * It does not support the {@code add} or {@code addAll} operations.
     *
     * @return a reverse order navigable set view of the keys in this map
     */
    @Override
    public NavigableSet<K> descendingKeySet() {
        return new NavigableKeySet().descendingSet();
    }

    /**
     * Returns a view of the portion of this map whose keys range from
     * {@code fromKey} to {@code toKey}.  If {@code fromKey} and
     * {@code toKey} are equal, the returned map is empty unless
     * {@code fromInclusive} and {@code toInclusive} are both true.  The
     * returned map is backed by this map, so changes in the returned map are
     * reflected in this map, and vice-versa.  The returned map supports all
     * optional map operations that this map supports.

     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside of its range, or to construct a
     * submap either of whose endpoints lie outside its range.
     *
     * @param fromKey       low endpoint of the keys in the returned map
     * @param fromInclusive {@code true} if the low endpoint
     *                      is to be included in the returned view
     * @param toKey         high endpoint of the keys in the returned map
     * @param toInclusive   {@code true} if the high endpoint
     *                      is to be included in the returned view
     * @return a view of the portion of this map whose keys range from
     * {@code fromKey} to {@code toKey}
     * @throws ClassCastException       if {@code fromKey} and {@code toKey}
     *                                  cannot be compared to one another using this map's comparator
     *                                  (or, if the map has no comparator, using natural ordering).
     *                                  Implementations may, but are not required to, throw this
     *                                  exception if {@code fromKey} or {@code toKey}
     *                                  cannot be compared to keys currently in the map.
     * @throws NullPointerException     if {@code fromKey} or {@code toKey}
     *                                  is null and this map does not permit null keys
     * @throws IllegalArgumentException if {@code fromKey} is greater than
     *                                  {@code toKey}; or if this map itself has a restricted
     *                                  range, and {@code fromKey} or {@code toKey} lies
     *                                  outside the bounds of the range
     */
    @Override
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive,
                                     K toKey, boolean toInclusive) {
        return new SubMap(fromKey,fromInclusive,toKey,toInclusive);
    }

    /**
     * Returns a view of the portion of this map whose keys are less than (or
     * equal to, if {@code inclusive} is true) {@code toKey}.  The returned
     * map is backed by this map, so changes in the returned map are reflected
     * in this map, and vice-versa.  The returned map supports all optional
     * map operations that this map supports.

     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * @param toKey     high endpoint of the keys in the returned map
     * @param inclusive {@code true} if the high endpoint
     *                  is to be included in the returned view
     * @return a view of the portion of this map whose keys are less than
     * (or equal to, if {@code inclusive} is true) {@code toKey}
     * @throws ClassCastException       if {@code toKey} is not compatible
     *                                  with this map's comparator (or, if the map has no comparator,
     *                                  if {@code toKey} does not implement {@link Comparable}).
     *                                  Implementations may, but are not required to, throw this
     *                                  exception if {@code toKey} cannot be compared to keys
     *                                  currently in the map.
     * @throws NullPointerException     if {@code toKey} is null
     *                                  and this map does not permit null keys
     * @throws IllegalArgumentException if this map itself has a
     *                                  restricted range, and {@code toKey} lies outside the
     *                                  bounds of the range
     */
    @Override
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        return subMap(firstKey(),true,toKey,inclusive);
    }

    /**
     * Returns a view of the portion of this map whose keys are greater than (or
     * equal to, if {@code inclusive} is true) {@code fromKey}.  The returned
     * map is backed by this map, so changes in the returned map are reflected
     * in this map, and vice-versa.  The returned map supports all optional
     * map operations that this map supports.

     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * @param fromKey   low endpoint of the keys in the returned map
     * @param inclusive {@code true} if the low endpoint
     *                  is to be included in the returned view
     * @return a view of the portion of this map whose keys are greater than
     * (or equal to, if {@code inclusive} is true) {@code fromKey}
     * @throws ClassCastException       if {@code fromKey} is not compatible
     *                                  with this map's comparator (or, if the map has no comparator,
     *                                  if {@code fromKey} does not implement {@link Comparable}).
     *                                  Implementations may, but are not required to, throw this
     *                                  exception if {@code fromKey} cannot be compared to keys
     *                                  currently in the map.
     * @throws NullPointerException     if {@code fromKey} is null
     *                                  and this map does not permit null keys
     * @throws IllegalArgumentException if this map itself has a
     *                                  restricted range, and {@code fromKey} lies outside the
     *                                  bounds of the range
     */
    @Override
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        return subMap(fromKey,inclusive,lastKey(),true);
    }

    /**
     * Returns the comparator used to order the keys in this map, or
     * {@code null} if this map uses the {@linkplain Comparable
     * natural ordering} of its keys.
     *
     * @return the comparator used to order the keys in this map,
     * or {@code null} if this map uses the natural ordering
     * of its keys
     */
    @Override
    public Comparator<? super K> comparator() {
        return comparator;
    } //done

    /**
     * {@inheritDoc}

     * <p>Equivalent to {@code subMap(fromKey, true, toKey, false)}.
     *
     * @param fromKey from key
     * @param toKey to key
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return subMap(fromKey, true, toKey, false);
    }

    /**
     * {@inheritDoc}

     * <p>Equivalent to {@code headMap(toKey, false)}.
     *
     * @param toKey to key
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> headMap(K toKey) {
        return headMap(toKey,false);
    }

    /**
     * {@inheritDoc}

     * <p>Equivalent to {@code tailMap(fromKey, true)}.
     *
     * @param fromKey rom key
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        return tailMap(fromKey,false);
    }

    /**
     * Returns the first (lowest) key currently in this map.
     *
     * @return the first (lowest) key currently in this map
     * @throws NoSuchElementException if this map is empty
     */
    @Override
    public K firstKey() { //done
        Entry<K, V> toReturn = getFirstEntry();
        return (toReturn == null ? null : toReturn.getKey());
    }

    /**
     * Returns the last (highest) key currently in this map.
     *
     * @return the last (highest) key currently in this map
     * @throws NoSuchElementException if this map is empty
     */
    @Override
    public K lastKey() { //done
        Entry<K, V> toReturn = getLastEntry();
        return (toReturn == null ? null : toReturn.getKey());
    }
}