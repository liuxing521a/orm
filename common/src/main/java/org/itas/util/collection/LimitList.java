package org.itas.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LimitList<E> implements List<E> {

	private final int limit;
	
	private final List<E> array;

	public LimitList(int limit) {
		this(limit, new ArrayList<>(limit));
	}
   
	public LimitList(int limit, List<E> array) {
    	this.limit = limit;
    	this.array = array;
    }
    
	@Override
	public int size() {
		return array.size();
	}

	@Override
	public boolean isEmpty() {
		return array.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return array.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return array.iterator();
	}

	@Override
	public Object[] toArray() {
		return array.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return array.toArray(a);
	}

	@Override
	public boolean add(E e) {
		if (array.size() >= limit) {
			array.remove(array.size() - 1);
		}
		
		return array.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return array.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return array.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		array.addAll(c);
		while (array.size() > limit) {
			array.remove(array.size() - 1);
		}
		
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		array.addAll(index, c);
		while (array.size() > limit) {
			array.remove(array.size() - 1);
		}
		
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return array.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return array.retainAll(c);
	}

	@Override
	public void clear() {
		array.clear();
	}

	@Override
	public E get(int index) {
		return array.get(index);
	}

	@Override
	public E set(int index, E element) {
		return array.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		array.add(index, element);
		if (array.size() > limit) {
			array.remove(array.size() - 1);
		}
	}

	@Override
	public E remove(int index) {
		return array.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return array.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return array.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return array.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return array.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return array.subList(fromIndex, toIndex);
	}

}
