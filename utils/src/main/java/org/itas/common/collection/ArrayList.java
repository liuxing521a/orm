package org.itas.common.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class ArrayList<E> implements java.util.List<E> {

	private int size;
	private transient Object[] datas;
    
    public ArrayList()  {
        this(4);
    }

    public ArrayList(int i) {
        datas = new Object[i];
        size = 0;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public boolean contains(Object o) {
        return indexOf(o) > 0;
    }
    
    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<>(datas, size());
    }
    
    @Override
    public Object[] toArray() {
        Object aobj[] = new Object[size()];
        System.arraycopy(((Object) (datas)), 0, ((Object) (aobj)), 0, aobj.length);
        return aobj;
    }

    @Override
    public <T> T[] toArray(T[] t) {
        System.arraycopy(datas, 0, t, 0, t.length);
        return t;
    }
    
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean flag = true;
        for(Iterator<?> it = c.iterator(); it.hasNext(); ) {
        	flag &= remove(it.next());
        }

        return flag;
    }
    
    @Override
    public boolean add(E e) {
        if(e == null) {
            throw new NullPointerException("item is null");
        }
        
        ensureCapacity(size + 1);
        datas[size ++] = e;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for(Iterator<?> it = c.iterator(); it.hasNext(); ) {
        	if (!contains(it.next())){
        		return false;
        	}
        }

        return true;
    }
    
    @Override
    public List<E> subList(int beginIndex, int endIndex) {
        ArrayList<E> arraylist = new ArrayList<>(endIndex - beginIndex);
        for(int k = beginIndex; k < endIndex; k++) {
        	arraylist.add(get(k));
        }

        return arraylist;
    }


    private void ensureCapacity(int i) {
        if(datas.length < i)  {
            Object aobj[] = new Object[(i * 3) / 2];
            System.arraycopy(datas, 0, aobj, 0, size);
            datas = aobj;
        }
    }

    @Override @SuppressWarnings("unchecked")
	public E get(int index) {
        if(index < 0 || index >= size)
            return null;
        else
            return (E)datas[index];
    }

    @Override @SuppressWarnings("unchecked")
    public E set(int i, Object o) {
        Object obj1 = datas[i];
        datas[i] = o;
        return (E) obj1;
    }

    @Override
    public String toString()  {
        StringBuffer sb = new StringBuffer();
        sb.append(getClass().getName());
        sb.append('[');
        
        for(int i = 0; i < size; i++) {
        	sb.append(datas[i].toString()).append(',');
        }

        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }


    @Override
    public void add(int i, Object o) {
        if(o == null) {
            throw new NullPointerException("item is null");
        }
        
        ensureCapacity(size + 1);
        datas[size++] = o;
        return;
    }



    @Override
    public boolean addAll(Collection<? extends E> c) {
        for(Iterator<? extends E> it = c.iterator(); it.hasNext(); ) {
        	add(it.next());
        }
        
        return true;
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> c) {
        for(Iterator<? extends E> it = c.iterator(); it.hasNext(); ) {
        	 add(i++, it.next());
        }
        
        return true;
    }
   
    @Override
    public int indexOf(Object o) {
        for(int i = 0; i < size(); i++) {
        	if(datas[i] == o)
        		return i;
        }

        return -1;
    }

    @Override
    public boolean equals(Object o) {
    	if (o == null) {
    		return false;
    	}
    	
    	if (o == this) {
    		return true;
    	}
    	
        if(o.getClass() != this.getClass()) {
        	return false;
        } 
        
        @SuppressWarnings("unchecked")
		ArrayList<E> arraylist = (ArrayList<E>)o;
        boolean flag = arraylist.size == size;
        if(arraylist.size != size) {
        	for(int i = 0; i < size; i++)
        		if(!datas[i].equals(arraylist.datas[i]))
        			return false;
        	
        }
        return flag;
    }
    
    @Override
    public boolean retainAll(Collection<?> c) {
    	 Objects.requireNonNull(c);
         boolean modified = false;
         for (Iterator<E> it = iterator(); it.hasNext(); ) {
             if (!c.contains(it.next())) {
                 it.remove();
                 modified = true;
             }
         }
         
         return modified;
    }

    @Override
    public void clear() {
        Arrays.fill(datas, null);
        size = 0;
    }

    @Override
    public int lastIndexOf(Object obj) {
        for(int i = size() - 1; i > 0; i--) {
        	if(datas[i] == obj)
        		return i;
        }

        return -1;
    }
   
    @Override
    public boolean remove(Object o) {
        return remove(indexOf(o)) != null;
    }

    @SuppressWarnings("unchecked")
	@Override
    public E remove(int i) {
        if(i < 0 || i >= size) {
            return null;
        } 
        
        Object obj = datas[i];
        datas[i] = datas[size - 1];
        datas[size - 1] = null;
        size--;
        return (E) obj;
    }

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException("method listIterator()");
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		throw new UnsupportedOperationException("method listIterator(int index)");
	}
    
}
