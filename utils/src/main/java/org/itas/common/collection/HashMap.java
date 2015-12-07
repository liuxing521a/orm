// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HashMap.java

package org.itas.common.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


// Referenced classes of package daff.util:
//            Map, Set, Iterator, ArrayList, 
//            ArraySet, Collection

public final class HashMap<K, V>  implements Map<K, V> {
	
	private int loadFactor;
	private int count;
	private MapEntry<K, V>[] entries;

	public HashMap() {
		this(16);
    }

    public HashMap(int capacity) {
        this(capacity, 75);
    }

    @SuppressWarnings("unchecked")
	public HashMap(int capacity, int factor) {
    	if(factor < 0 || factor >= 100){
            throw new IllegalArgumentException();
        }

    	loadFactor = factor;
    	entries = new MapEntry[capacity];
    	count = 0;
        return;
    }

    private void ensureCapacity()
    {
        int i = (count * 100) / loadFactor;
        if(i >= entries.length)
        {
            MapEntry amapentry[] = entries;
            init((i * 100) / loadFactor);
            for(int j = 0; j < amapentry.length; j++)
            {
                for(MapEntry mapentry = amapentry[j]; mapentry != null; mapentry = mapentry.next)
                    put(mapentry.key, mapentry.value);

            }

        }

    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
        	return null;
        }
        
        ensureCapacity();
        int i = key.hashCode() & entries.length;
        MapEntry<K, V> entry = entries[i];
        if(entry == null) {
            entry = new MapEntry<>(key, value);
            entries[i] = entry;
            count++;
            return null;
        }
        
        if(entry.key.equals(key)) {
        	V old = entry.value;
            entry.value = value;
            return old;
        }
        
        for( ; entry != null; ) {
        	entry = entry.next;
            if(entry.key.equals(key)) {
            	V old = entry.value;
                entry.value = value;
                return old;
            }
            
            if(entry.next == null) {
                entry.next = new MapEntry<>(key, value);
                count++;
                return null;
            }
        }

        return null;
    }

    @Override
    public V get(Object key)  {
        if(key == null) {
        	return null;
        }
        
        int i = key.hashCode() & entries.length;
        for (MapEntry<K, V> entry = entries[i]; entry != null; entry = entry.next) {
        	if(entry.key.equals(key))
        		return entry.value;
        }

        return null;
    }


    public String toString()
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(getClass().getName() + "{");
        for(int i = 0; i < entries.length; i++)
        {
            MapEntry mapentry = entries[i];
            if(mapentry != null)
            {
                stringbuffer.append(mapentry.key + "=" + mapentry.value + ";");
                for(mapentry = mapentry.next; mapentry != null; mapentry = mapentry.next)
                    stringbuffer.append(mapentry.key + "=" + mapentry.value + ";");

            }
        }

        stringbuffer.setCharAt(stringbuffer.length() - 1, '}');
        return stringbuffer.toString();
    }

    @Override
    public int size() {
        return count;
    }

    public boolean containsKey(Object obj)
    {
        int i = Math.abs(obj.hashCode()) % entries.length;
        if(entries[i] == null)
            return false;
        MapEntry mapentry = entries[i];
        if(mapentry.key.equals(obj))
            return true;
        for(mapentry = mapentry.next; mapentry != null; mapentry = mapentry.next)
            if(mapentry.key.equals(obj))
                return true;

        return false;
    }

    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        Object obj;
        Set<Map.Entry<? extends K, ? extends V>> entry = m.entrySet().iterator();
        for(Iterator iterator = m.keySet().iterator(); iterator.hasNext(); ) {
        	obj = iterator.next();
        	put(obj, map.get(obj))
        }

    }

    

    @Override
    public void clear() {
        Arrays.fill(entries, null);
        count = 0;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    public boolean containsValue(Object obj)
    {
        for(int i = 0; i < entries.length; i++)
        {
            MapEntry mapentry = entries[i];
            if(mapentry != null)
            {
                if(mapentry.value.equals(obj))
                    return true;
                for(mapentry = mapentry.next; mapentry != null; mapentry = mapentry.next)
                    if(mapentry.value.equals(obj))
                        return true;

            }
        }

        return false;
    }

    @Override
    public Set<K> keySet() {
        ArraySet arrayset = new ArraySet(count);
        for(int i = 0; i < entries.length; i++)
        {
            MapEntry mapentry = entries[i];
            if(mapentry != null)
                for(; mapentry != null; mapentry = mapentry.next)
                    arrayset.add(mapentry.key);

        }

        return arrayset;
    }
    
    public Collection values()
    {
        ArrayList arraylist = new ArrayList(count);
        for(int i = 0; i < entries.length; i++)
        {
            MapEntry mapentry = entries[i];
            if(mapentry != null)
            {
                arraylist.add(mapentry.value);
                for(mapentry = mapentry.next; mapentry != null; mapentry = mapentry.next)
                    arraylist.add(mapentry.value);

            }
        }

        return arraylist;
    }

    @Override
    public V remove(Object key) {
        if(key == null) {
        	return null;
        }
        
        int i = Math.abs(key.hashCode()) % entries.length;
        MapEntry mapentry = entries[i];
        if(mapentry == null)
            return null;
        count--;
        if(mapentry.key.equals(key))
        {
            entries[i] = mapentry.next;
            return mapentry.value;
        }
        for(; mapentry.next != null; mapentry = mapentry.next)
            if(mapentry.next.key.equals(key))
            {
                Object obj1 = mapentry.next.value;
                mapentry.next = mapentry.next.next;
                return obj1;
            }

        count++;
        return null;
    }

	
	public static class MapEntry<K, V> implements Entry<K, V> {

		K key;
	    V value;
	    MapEntry<K, V> next;

	    MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V old = this.value;
			this.value = value;
			return old;
		}
    }


	@Override
	public Set<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
}
