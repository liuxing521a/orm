package org.itas.util.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.itas.util.Logger;
import org.itas.util.Utils.TimeUtil;
import org.itas.util.cache.LinkedList.Node;

public class LocalCache<K, V> implements Cache<K, V> {

    /**
     * The map the keys and values are stored in.
     */
    protected Map<K, Element<V>> map;

    /**
     * Linked list to maintain order that cache objects are accessed
     * in, most used to least used.
     */
    protected LinkedList<K> lastAccessedList;

    /**
     * Linked list to maintain time that cache objects were initially added
     * to the cache, most recently added to oldest added.
     */
    protected LinkedList<K> ageList;

    /**
     * Maximum size in bytes that the cache can grow to.
     */
    private long maxCacheSize;

    /**
     * Maintains the current size of the cache in bytes.
     */
    private int cacheSize = 0;

    /**
     * Maximum length of time objects can exist in cache before expiring.
     */
    protected long maxLifetime;

    /**
     * Maintain the number of cache hits and misses. A cache hit occurs every
     * time the get method is called and the cache contains the requested
     * object. A cache miss represents the opposite occurence.<p>
     *
     * Keeping track of cache hits and misses lets one measure how efficient
     * the cache is; the higher the percentage of hits, the more efficient.
     */
    protected long cacheHits, cacheMisses = 0L;

    /**
     * The name of the cache.
     */
    private String name;

    /**
     * Create a new default cache and specify the maximum size of for the cache in
     * bytes, and the maximum lifetime of objects.
     *
     * @param name a name for the cache.
     * @param maxSize the maximum size of the cache in bytes. -1 means the cache
     *      has no max size.
     * @param maxLifetime the maximum amount of time objects can exist in
     *      cache before being deleted. -1 means objects never expire.
     */
    public LocalCache(String name, long maxSize, long maxLifetime) {
        this.name = name;
        this.maxCacheSize = maxSize;
        this.maxLifetime = maxLifetime;

        // Our primary data structure is a HashMap. The default capacity of 11
        // is too small in almost all cases, so we set it bigger.
        map = new HashMap<K, Element<V>>(103);

        lastAccessedList = new LinkedList<K>();
        ageList = new LinkedList<K>();
    }

    public synchronized V put(K key, V value) {
        // Delete an old entry if it exists.
        V answer = remove(key);

        int objectSize = 1;
        try {
             objectSize = CacheSizes.sizeOf(value);
        }
        catch (CalculateSizeException e) {
//             Log.warn(e.getMessage(), e); TODO
        }

        // If the object is bigger than the entire cache, simply don't add it.
        if (maxCacheSize > 0 && objectSize > maxCacheSize * .90) {
//            Log.warn("Cache: " + name + " -- object with key " + key +
//                    " is too large to fit in cache. Size is " + objectSize);TODO
            return value;
        }
        cacheSize += objectSize;
        LocalCache.Element<V> cacheObject = new LocalCache.Element<V>(value, objectSize);
        map.put(key, cacheObject);
        // Make an entry into the cache order list.
        Node<K> lastAccessedNode = lastAccessedList.addFirst(key);
        // Store the cache order list entry so that we can get back to it
        // during later lookups.
        cacheObject.accessNode = lastAccessedNode;
        // Add the object to the age list
        Node<K> ageNode = ageList.addFirst(key);
        // We make an explicit call to currentTimeMillis() so that total accuracy
        // of lifetime calculations is better than one second.
        ageNode.timestamp = System.currentTimeMillis();
        cacheObject.lifeNode = ageNode;

        // If cache is too full, remove least used cache entries until it is
        // not too full.
        expiredCacheFull();

        return answer;
    }

    @SuppressWarnings("unchecked")
	public synchronized V get(Object key) {
        // First, clear all entries that have been in cache longer than the
        // maximum defined age.
        expiredOutLifeElement();

        Element<V> cacheObject = map.get(key);
        if (Objects.isNull(cacheObject)) {
            cacheMisses++;
            return null;
        }

        // The object exists in cache, so increment cache hits. Also, increment
        // the object's read count.
        cacheHits++;

        // Remove the object from it's current place in the cache order list,
        // and re-insert it at the front of the list.
        cacheObject.accessNode.remove();
        lastAccessedList.addFirst((Node<K>) cacheObject.accessNode);

        return cacheObject.data;
    }

    public synchronized V remove(Object key) {
        LocalCache.Element<V> cacheObject = map.get(key);
        // If the object is not in cache, stop trying to remove it.
        if (cacheObject == null) {
            return null;
        }
        // remove from the hash map
        map.remove(key);
        // remove from the cache order list
        cacheObject.accessNode.remove();
        cacheObject.lifeNode.remove();
        // remove references to linked list nodes
        cacheObject.lifeNode = null;
        cacheObject.accessNode = null;
        // removed the object, so subtract its size from the total.
        cacheSize -= cacheObject.memorySize;
        return cacheObject.data;
    }

    public synchronized void clear() {
        Object[] keys = map.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            remove(keys[i]);
        }

        // Now, reset all containers.
        map.clear();
        lastAccessedList.clear();
        lastAccessedList = new LinkedList<K>();
        ageList.clear();
        ageList = new LinkedList<K>();

        cacheSize = 0;
        cacheHits = 0;
        cacheMisses = 0;
    }

    public int size() {
        // First, clear all entries that have been in cache longer than the
        // maximum defined age.
        expiredOutLifeElement();

        return map.size();
    }

    public boolean isEmpty() {
        // First, clear all entries that have been in cache longer than the
        // maximum defined age.
        expiredOutLifeElement();

        return map.isEmpty();
    }

	public Collection<V> values() {
        // First, clear all entries that have been in cache longer than the
        // maximum defined age.
        expiredOutLifeElement();
        return new CacheObjectCollection<>(map.values());
    }

    /**
     * Wraps a cached object collection to return a view of its inner objects
     */
    @SuppressWarnings("hiding")
	private final class CacheObjectCollection<V> implements Collection<V> {
        private Collection<Element<V>> cachedObjects;

        private CacheObjectCollection(Collection<LocalCache.Element<V>> cachedObjects) {
            this.cachedObjects = new ArrayList<Element<V>>(cachedObjects);
        }

        public int size() {
            return cachedObjects.size();
        }

        public boolean isEmpty() {
            return size() == 0;
        }

        public boolean contains(Object o) {
            Iterator<V> it = iterator();
            while (it.hasNext()) {
                if (it.next().equals(o)) {
                    return true;
                }
            }
            return false;
        }

        public Iterator<V> iterator() {
            return new Iterator<V>() {
                private final Iterator<LocalCache.Element<V>> it = cachedObjects.iterator();

                public boolean hasNext() {
                    return it.hasNext();
                }

                public V next() {
                    if(it.hasNext()) {
                        LocalCache.Element<V> object = it.next();
                        if(object == null) {
                            return null;
                        } else {
                            return object.data;
                        }
                    }
                    else {
                        throw new NoSuchElementException();
                    }
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        public Object[] toArray() {
            Object[] array = new Object[size()];
            Iterator<?> it = iterator();
            int i = 0;
            while (it.hasNext()) {
                array[i] = it.next();
            }
            return array;
        }

        @SuppressWarnings({ "unchecked"})
		public <V> V[] toArray(V[] a) {
            Iterator<V> it = (Iterator<V>) iterator();
            int i = 0;
            while (it.hasNext()) {
                a[i++] = it.next();
            }
            return a;
        }

        public boolean containsAll(Collection<?> c) {
            Iterator<?> it = c.iterator();
            while(it.hasNext()) {
                if(!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        public boolean add(V o) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends V> coll) {
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(Collection<?> coll) {
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(Collection<?> coll) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

    public boolean containsKey(Object key) {
        // First, clear all entries that have been in cache longer than the
        // maximum defined age.
        expiredOutLifeElement();

        return map.containsKey(key);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Iterator<? extends K> i = map.keySet().iterator(); i.hasNext();) {
            K key = i.next();
            V value = map.get(key);
            put(key, value);
        }
    }

    public boolean containsValue(Object value) {
        // First, clear all entries that have been in cache longer than the
        // maximum defined age.
        expiredOutLifeElement();

        if(value == null) {
            return containsNullValue();
        }

        Iterator<?> it = values().iterator();
        while(it.hasNext()) {
            if(value.equals(it.next())) {
                 return true;
            }
        }
        return false;
    }

    private boolean containsNullValue() {
        Iterator<?> it = values().iterator();
        while(it.hasNext()) {
            if(it.next() == null) {
                return true;
            }
        }
        return false;
    }

    public Set<Entry<K, V>> entrySet() {
        // First, clear all entries that have been in cache longer than the
        // maximum defined age.
        expiredOutLifeElement();
        // TODO Make this work right

        synchronized (this) {
            final Map<K, V> result = new HashMap<K, V>();
            for (final Entry<K, LocalCache.Element<V>> entry : map.entrySet()) {
                result.put(entry.getKey(), entry.getValue().data);
            }
            return result.entrySet();
        }
    }

    public Set<K> keySet() {
        // First, clear all entries that have been in cache longer than the
        // maximum defined age.
        expiredOutLifeElement();
        synchronized (this) {
            return new HashSet<K>(map.keySet());
        }
    }

    /**
     * Returns the name of this cache. The name is completely arbitrary
     * and used only for display to administrators.
     *
     * @return the name of this cache.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this cache.
     *
     * @param name the name of this cache.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the number of cache hits. A cache hit occurs every
     * time the get method is called and the cache contains the requested
     * object.<p>
     *
     * Keeping track of cache hits and misses lets one measure how efficient
     * the cache is; the higher the percentage of hits, the more efficient.
     *
     * @return the number of cache hits.
     */
    public long getCacheHits() {
        return cacheHits;
    }

    /**
     * Returns the number of cache misses. A cache miss occurs every
     * time the get method is called and the cache does not contain the
     * requested object.<p>
     *
     * Keeping track of cache hits and misses lets one measure how efficient
     * the cache is; the higher the percentage of hits, the more efficient.
     *
     * @return the number of cache hits.
     */
    public long getCacheMisses() {
        return cacheMisses;
    }

    /**
     * Returns the size of the cache contents in bytes. This value is only a
     * rough approximation, so cache users should expect that actual VM
     * memory used by the cache could be significantly higher than the value
     * reported by this method.
     *
     * @return the size of the cache contents in bytes.
     */
    public int getCacheSize() {
        return cacheSize;
    }

    /**
     * Returns the maximum size of the cache (in bytes). If the cache grows larger
     * than the max size, the least frequently used items will be removed. If
     * the max cache size is set to -1, there is no size limit.
     *
     * @return the maximum size of the cache (-1 indicates unlimited max size).
     */
    public long getMaxCacheSize() {
        return maxCacheSize;
    }

    /**
     * Sets the maximum size of the cache. If the cache grows larger
     * than the max size, the least frequently used items will be removed. If
     * the max cache size is set to -1, there is no size limit.
     *
     * @param maxCacheSize the maximum size of this cache (-1 indicates unlimited max size).
     */
    public void setMaxCacheSize(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
        // It's possible that the new max size is smaller than our current cache
        // size. If so, we need to delete infrequently used items.
        expiredCacheFull();
    }

    /**
     * Returns the maximum number of milleseconds that any object can live
     * in cache. Once the specified number of milleseconds passes, the object
     * will be automatically expried from cache. If the max lifetime is set
     * to -1, then objects never expire.
     *
     * @return the maximum number of milleseconds before objects are expired.
     */
    public long getMaxLifetime() {
        return maxLifetime;
    }

    /**
     * Sets the maximum number of milleseconds that any object can live
     * in cache. Once the specified number of milleseconds passes, the object
     * will be automatically expried from cache. If the max lifetime is set
     * to -1, then objects never expire.
     *
     * @param maxLifetime the maximum number of milleseconds before objects are expired.
     */
    public void setMaxLifetime(long maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    protected void expiredOutLifeElement() {
        if (maxLifetime <= 0) {
            return;
        }

        Node<K> node = ageList.getLast();
        if (Objects.isNull(node)) {
            return;
        }

        long expireTime = TimeUtil.systemTime() - maxLifetime;
        while (expireTime > node.timestamp) {
            remove(node.object);

            node = ageList.getLast();
            if (Objects.isNull(node)) {
                return;
            }
        }
    }

    protected final void expiredCacheFull() {
        if (maxCacheSize < 0) {
            return;
        }

        int desiredSize = (int)(maxCacheSize * .97);
        if (cacheSize >= desiredSize) {
            // First, delete any old entries to see how much memory that frees.
            expiredOutLifeElement();
            desiredSize = (int)(maxCacheSize * .90);
            if (cacheSize > desiredSize) {
                long t = TimeUtil.systemTime();
                do {
                    remove(lastAccessedList.getLast().object);
                } while (cacheSize > desiredSize);
                t = System.currentTimeMillis() - t;
                Logger.warn("Cache " + name + " was full, shrinked to 90% in " + t + "ms.");
            }
        }
    }

    private static class Element<V> {
    	
    	/** 存储对象key*/
        public V data;

        /** 存储对象占存空间byte*/
        public int memorySize;
        
        /** 对象*/
        public Node<?> accessNode;

        /** */
        public Node<?> lifeNode;


        public Element(V data, int size) {
            this.data = data;
            this.memorySize = size;
        }
    }
}
