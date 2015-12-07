package org.itas.common.collection;


import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class IntHashMap<T> {

    /**
     * The hash table data.
     */
    private transient Entry<T> table[];

    /**
     * The total number of entries in the hash table.
     */
    private transient int count;

    /**
     * The table is rehashed when its size exceeds this threshold.  (The
     * value of this field is (int)(capacity * loadFactor).)
     *
     * @serial
     */
    private int threshold;

    /**
     * The load factor for the hashtable.
     *
     * @serial
     */
    private final float loadFactor;

    /**
     * <p>Innerclass that acts as a datastructure to create a new entry in the
     * table.</p>
     */
    public static class Entry<T> {
        final int hash;
        T value;
        Entry<T> next;

        /**
         * <p>Create a new entry with the given values.</p>
         *
         * @param hash The code used to hash the object with
         * @param key The key used to enter this in the table
         * @param value The value for this key
         * @param next A reference to the next entry in the table
         */
        protected Entry(int hash, T value, Entry<T> next) {
            this.hash = hash;
            this.value = value;
            this.next = next;
        }

		public int getKey() {
			return hash;
		}
		
		public T getValue(){
			return value;
		}
    }

    public IntHashMap() {
        this(16, 0.75f);
    }

    /**
     * <p>Constructs a new, empty hashtable with the specified initial capacity
     * and default load factor, which is <code>0.75</code>.</p>
     *
     * @param  initialCapacity the initial capacity of the hashtable.
     * @throws IllegalArgumentException if the initial capacity is less
     *   than zero.
     */
    public IntHashMap(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    /**
     * <p>Constructs a new, empty hashtable with the specified initial
     * capacity and the specified load factor.</p>
     *
     * @param initialCapacity the initial capacity of the hashtable.
     * @param loadFactor the load factor of the hashtable.
     * @throws IllegalArgumentException  if the initial capacity is less
     *             than zero, or if the load factor is nonpositive.
     */
    @SuppressWarnings("unchecked")
	public IntHashMap(int initialCapacity, float loadFactor) {
        super();
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        if (loadFactor <= 0) {
            throw new IllegalArgumentException("Illegal Load: " + loadFactor);
        }
        if (initialCapacity == 0) {
            initialCapacity = 1;
        }

        this.loadFactor = loadFactor;
        table = new Entry[initialCapacity];
        threshold = (int) (initialCapacity * loadFactor);
    }

    /**
     * <p>Returns the number of keys in this hashtable.</p>
     *
     * @return  the number of keys in this hashtable.
     */
    public int size() {
        return count;
    }

    /**
     * <p>Tests if this hashtable maps no keys to values.</p>
     *
     * @return  <code>true</code> if this hashtable maps no keys to values;
     *          <code>false</code> otherwise.
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * <p>Tests if some key maps into the specified value in this hashtable.
     * This operation is more expensive than the <code>containsKey</code>
     * method.</p>
     *
     * <p>Note that this method is identical in functionality to containsValue,
     * (which is part of the Map interface in the collections framework).</p>
     *
     * @param      value   a value to search for.
     * @return     <code>true</code> if and only if some key maps to the
     *             <code>value</code> argument in this hashtable as
     *             determined by the <tt>equals</tt> method;
     *             <code>false</code> otherwise.
     * @throws  NullPointerException  if the value is <code>null</code>.
     * @see        #containsKey(int)
     * @see        #containsValue(Object)
     * @see        java.util.Map
     */
    public boolean contains(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }

        Entry<T> tab[] = table;
        for (int i = tab.length; i-- > 0;) {
            for (Entry<T> e = tab[i]; e != null; e = e.next) {
                if (e.value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>Returns <code>true</code> if this HashMap maps one or more keys
     * to this value.</p>
     *
     * <p>Note that this method is identical in functionality to contains
     * (which predates the Map interface).</p>
     *
     * @param value value whose presence in this HashMap is to be tested.
     * @return boolean <code>true</code> if the value is contained
     * @see    java.util.Map
     * @since JDK1.2
     */
    public boolean containsValue(Object value) {
        return contains(value);
    }

    /**
     * <p>Tests if the specified object is a key in this hashtable.</p>
     *
     * @param  key  possible key.
     * @return <code>true</code> if and only if the specified object is a
     *    key in this hashtable, as determined by the <tt>equals</tt>
     *    method; <code>false</code> otherwise.
     * @see #contains(Object)
     */
    public boolean containsKey(int key) {
        Entry<T> tab[] = table;
        for (Entry<T> e = tab[getIndex(key)]; e != null; e = e.next) {
            if (e.hash == key) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>Returns the value to which the specified key is mapped in this map.</p>
     *
     * @param   key   a key in the hashtable.
     * @return  the value to which the key is mapped in this hashtable;
     *          <code>null</code> if the key is not mapped to any value in
     *          this hashtable.
     * @see     #put(int, Object)
     */
    public T get(int key) {
        Entry<T> tab[] = table;
        for (Entry<T> e = tab[getIndex(key)]; e != null; e = e.next) {
            if (e.hash == key) {
                return e.value;
            }
        }
        return null;
    }

    int getNumItemsInEntry(Entry<T> e){
    	int count = 0;
    	for (;e != null; e = e.next){
    		count ++;
    	}
    	return count;
    }
    
    public String getMapInfo(){
    	StringBuilder sb = new StringBuilder();
    	sb.append("Total slots: ");
    	sb.append(table.length);
    	sb.append(", total item count: ");
    	sb.append(count);
    	sb.append("\nNum of slots non-empty: ");
    	java.util.List<Integer> itemCount = new java.util.ArrayList<Integer>();
    	for (Entry<T> e : table){
    		int count = getNumItemsInEntry(e);
    		if (count > 0){
    			itemCount.add(count);
    		}
    	}
    	sb.append(itemCount.size());
    	sb.append("\nCollision level: ");
    	int level = 0;
    	for (Integer i : itemCount){
    		if (i > 1){
    			level += i;
    		}
    	}
    	sb.append(level / count);
    	return sb.toString();
    }
    
    public double getMapLevel(){
    	java.util.List<Integer> itemCount = new java.util.ArrayList<Integer>();
    	for (Entry<T> e : table){
    		int i = getNumItemsInEntry(e);
    		if (i > 0){
    			itemCount.add(i);
    		}
    	}
    	
    	double level = 0;
    	for (Integer i : itemCount){
    		if (i > 1){
    			level += (i-1);
    		}
    	}
    	double result = level/(double)count;
//    	System.out.println(level+"/"+count+"="+result);
    	return result;
    }
    /**
     * <p>Increases the capacity of and internally reorganizes this
     * hashtable, in order to accommodate and access its entries more
     * efficiently.</p>
     *
     * <p>This method is called automatically when the number of keys
     * in the hashtable exceeds this hashtable's capacity and load
     * factor.</p>
     */
    protected void rehash() {
        int oldCapacity = table.length;
        Entry<T> oldMap[] = table;

        int newCapacity = oldCapacity << 1;
        @SuppressWarnings("unchecked")
		Entry<T> newMap[] = new Entry[newCapacity];

        threshold = (int) (newCapacity * loadFactor);
        table = newMap;

        for (int i = oldCapacity; i-- > 0;) {
            for (Entry<T> old = oldMap[i]; old != null;) {
                Entry<T> e = old;
                old = old.next;

                int index = getIndex(e.hash);
                e.next = newMap[index];
                newMap[index] = e;
            }
        }
    }

    /**
     * <p>Maps the specified <code>key</code> to the specified
     * <code>value</code> in this hashtable. The key cannot be
     * <code>null</code>. </p>
     *
     * <p>The value can be retrieved by calling the <code>get</code> method
     * with a key that is equal to the original key.</p>
     *
     * @param key     the hashtable key.
     * @param value   the value.
     * @return the previous value of the specified key in this hashtable,
     *         or <code>null</code> if it did not have one.
     * @throws  NullPointerException  if the key is <code>null</code>.
     * @see     #get(int)
     */
    public T put(int key, T value) {
        // Makes sure the key is not already in the hashtable.
        Entry<T> tab[] = table;
        int index = getIndex(key);
        for (Entry<T> e = tab[getIndex(key)]; e != null; e = e.next) {
            if (e.hash == key) {
                T old = e.value;
                e.value = value;
                return old;
            }
        }

        if (count >= threshold) {
            // Rehash the table if the threshold is exceeded
            rehash();

            tab = table;
            index = getIndex(key);
        }

        // Creates the new entry.
        Entry<T> e = new Entry<T>(key, value, tab[index]);
        tab[index] = e;
        count++;
        return null;
    }

    /**
     * <p>Removes the key (and its corresponding value) from this
     * hashtable.</p>
     *
     * <p>This method does nothing if the key is not present in the
     * hashtable.</p>
     *
     * @param   key   the key that needs to be removed.
     * @return  the value to which the key had been mapped in this hashtable,
     *          or <code>null</code> if the key did not have a mapping.
     */
    public T remove(int key) {
        Entry<T> tab[] = table;
        int index = getIndex(key);
        for (Entry<T> e = tab[index], prev = null; e != null; prev = e, e = e.next) {
            if (e.hash == key) {
                if (prev != null) {
                    prev.next = e.next;
                } else {
                    tab[index] = e.next;
                }
                count--;
                T oldValue = e.value;
                e.value = null;
                return oldValue;
            }
        }
        return null;
    }

    /**
     * <p>Clears this hashtable so that it contains no keys.</p>
     */
    public void clear() {
        Entry<T> tab[] = table;
        for (int index = tab.length; --index >= 0;) {
            tab[index] = null;
        }
        count = 0;
    }
    /**
     * Removes and returns the entry associated with the specified key
     * in the HashMap.  Returns null if the HashMap contains no mapping
     * for this key.
     */
    final Entry<T> removeEntryForKey(int key) {
        int index = getIndex(key);
        Entry<T> prev = table[index];
        Entry<T> e = prev;

        while (e != null) {
            Entry<T> next = e.next;
            if (e.hash == key ) {
                count--;
                if (prev == e)
                    table[index] = next;
                else
                    prev.next = next;
                return e;
            }
            prev = e;
            e = next;
        }

        return e;
    }

    /**
     * Special version of remove for EntrySet.
     */
    final Entry<T> removeMapping(Object o) {
        if (!(o instanceof Entry))
            return null;

        @SuppressWarnings("unchecked")
		Entry<T> entry = (Entry<T>) o;
        int key = entry.getKey();
        Entry<T> prev = getEntry(key);
        Entry<T> e = prev;

        while (e != null) {
            Entry<T> next = e.next;
            if (e.hash == key && e.equals(entry)) {
                count--;
                if (prev == e)
                    table[getIndex(key)] = next;
                else
                    prev.next = next;
                return e;
            }
            prev = e;
            e = next;
        }

        return e;
    }
    
    private abstract class HashIterator<E> implements Iterator<E> {
        Entry<T> next;	// next entry to return
        int index;		// current slot
        Entry<T> current;	// current entry

        HashIterator() {
            if (count > 0) { // advance to first entry
                Entry<T>[] t = table;
                while (index < t.length && (next = t[index++]) == null)
                    ;
            }
        }

        public final boolean hasNext() {
            return next != null;
        }

        final Entry<T> nextEntry() {
            Entry<T> e = next;
            if (e == null)
                throw new NoSuchElementException();

            if ((next = e.next) == null) {
                Entry<T>[] t = table;
                while (index < t.length && (next = t[index++]) == null)
                    ;
            }
	    current = e;
            return e;
        }

        public void remove() {
            if (current == null)
                throw new IllegalStateException();
            int k = current.hash;
            current = null;
            IntHashMap.this.removeEntryForKey(k);
        }

    }


    private final class ValueIterator extends HashIterator<T> {
        public T next() {
            return nextEntry().value;
        }

    }

    private final class KeyIterator extends HashIterator<Integer> {
        public Integer next() {
            return nextEntry().getKey();
        }
    }

    private final class EntryIterator extends HashIterator<Entry<T>> {
        public Entry<T> next() {
            return nextEntry();
        }
    }

    // Subclass overrides these to alter behavior of views' iterator() method
    Iterator<Integer> newKeyIterator()   {
        return new KeyIterator();
    }
    Iterator<T> newValueIterator()   {
        return new ValueIterator();
    }
    Iterator<Entry<T>> newEntryIterator()   {
        return new EntryIterator();
    }

    private Set<Integer> keySet;
    private Collection<T> values;

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations.  It does not support the <tt>add</tt> or <tt>addAll</tt>
     * operations.
     */
    public Set<Integer> keySet() {

        Set<Integer> ks = keySet;
        return (ks != null ? ks : (keySet = new KeySet()));
    }

    final class KeySet extends AbstractSet<Integer> {
        public Iterator<Integer> iterator() {
            return newKeyIterator();
        }
        public int size() {
            return count;
        }
        public boolean contains(int o) {
            return containsKey(o);
        }
        public boolean remove(Object o) {
                return IntHashMap.this.removeEntryForKey((Integer)o) != null;    
        }
        public void clear() {
            IntHashMap.this.clear();
        }
    }
    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own <tt>remove</tt> operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Collection.remove</tt>, <tt>removeAll</tt>,
     * <tt>retainAll</tt> and <tt>clear</tt> operations.  It does not
     * support the <tt>add</tt> or <tt>addAll</tt> operations.
     */
    public Collection<T> values() {
        Collection<T> vs = values;
        return (vs != null ? vs : (values = new Values()));
    }

    private final class Values extends AbstractCollection<T> {
        public Iterator<T> iterator() {
            return newValueIterator();
        }
        public int size() {
            return count;
        }
        public boolean contains(Object o) {
            return containsValue(o);
        }
        public void clear() {
            IntHashMap.this.clear();
        }
    }

    // Views

    private transient Set<Entry<T>> entrySet = null;
    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation, or through the
     * <tt>setValue</tt> operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and
     * <tt>clear</tt> operations.  It does not support the
     * <tt>add</tt> or <tt>addAll</tt> operations.
     *
     * @return a set view of the mappings contained in this map
     */
    public Set<Entry<T>> entrySet() {
	return entrySet0();
    }

    private Set<Entry<T>> entrySet0() {
        Set<Entry<T>> es = entrySet;
        return es != null ? es : (entrySet = new EntrySet());
    }

    private final class EntrySet extends AbstractSet<Entry<T>> {
        public Iterator<Entry<T>> iterator() {
            return newEntryIterator();
        }
        public boolean contains(Object o) {
            if (!(o instanceof Entry))
                return false;
            @SuppressWarnings("unchecked")
			Entry<T> e = (Entry<T>) o;
            Entry<T> candidate = getEntry(e.getKey());
            return candidate != null && candidate.equals(e);
        }
        public boolean remove(Object o) {
            return removeMapping(o) != null;
        }
        public int size() {
            return count;
        }
        public void clear() {
            IntHashMap.this.clear();
        }
    }

	Entry<T> getEntry(int key) {
		return table[getIndex(key)];
	}
	
	int getIndex(int key){
		return  key & (table.length -1 );
	}
}
