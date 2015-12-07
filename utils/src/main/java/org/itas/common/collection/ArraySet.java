package org.itas.common.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;


public class ArraySet<E> implements Set<E> {

	private ArrayList<E> datas;
	
	public ArraySet() {
		this(2);
	}
	
    public ArraySet(int i) {
    	datas = new ArrayList<E>(i);
    }

    @Override
    public boolean add(E e) {
        if(contains(e)) {
        	return false;
        }
        
        return datas.add(e);
    }

	@Override
	public int size() {
		return datas.size();
	}

	@Override
	public boolean isEmpty() {
		return datas.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return datas.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return datas.iterator();
	}

	@Override
	public Object[] toArray() {
		return datas.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return datas.toArray(a);
	}

	@Override
	public boolean remove(Object o) {
		return datas.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return datas.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return datas.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return datas.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return datas.retainAll(c);
	}

	@Override
	public void clear() {
		datas.clear();
	}
}
