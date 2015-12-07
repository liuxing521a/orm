// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LinkedList.java

package org.itas.common.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


// Referenced classes of package daff.util:
//            Collection, Iterator, ArrayIterator, List, 
//            Set

public class LinkedList<E> implements List<E>
{
    public class Node
    {

        public String toString()
        {
            return data.toString();
        }

        public Node getNext()
        {
            return next;
        }

        public Object getData()
        {
            return data;
        }

        public Node getPrev()
        {
            return prev;
        }

        Object data;
        Node next;
        Node prev;

        Node()
        {
        }
    }


    public List subList(int i, int j)
    {
        return null;
    }

    public Node findNode(Object obj)
    {
        for(Node node = first; node != null; node = node.next)
            if(node.data == obj || node.data.equals(obj))
                return node;

        return null;
    }

    public boolean add(Object obj)
    {
        if(contains(obj))
            return false;
        if(obj == null)
        {
            throw new IllegalArgumentException("item to been added is null!");
        } else
        {
            Node node = new Node();
            node.data = obj;
            addNode(node);
            return true;
        }
    }

    public void add(int i, Object obj)
    {
    }

    public Node getNode(int i)
    {
        int j = 0;
        Node node;
        for(node = first; node != null && j < i; j++)
            node = node.next;

        return node;
    }

    public boolean addAll(Collection collection)
    {
        for(Iterator iterator1 = collection.iterator(); iterator1.hasNext(); add(iterator1.next()));
        return true;
    }

    public boolean addAll(int i, Collection collection)
    {
        for(Iterator iterator1 = collection.iterator(); iterator1.hasNext(); add(i++, iterator1.next()));
        return true;
    }

    public Node getLast()
    {
        return last;
    }

    public int indexOf(Object obj)
    {
        return -1;
    }

    public Node getFirst()
    {
        return first;
    }

    public void swap(Node node, Node node1)
    {
        Object obj = node.data;
        node.data = node1.data;
        node1.data = obj;
    }

    public boolean equals(Object obj)
    {
        return false;
    }

    public Iterator iterator()
    {
        Object aobj[] = toArray();
        return new ArrayIterator(aobj, aobj.length);
    }

    public Node addBefor(Object obj, Node node)
    {
        if(node == null)
        {
            throw new IllegalArgumentException("target is null");
        } else
        {
            Node node1 = new Node();
            node1.data = obj;
            insertNodeBefor(node1, node);
            return node1;
        }
    }

    public boolean containsAll(Collection collection)
    {
        for(Iterator iterator1 = collection.iterator(); iterator1.hasNext();)
            if(!contains(iterator1.next()))
                return false;

        return true;
    }

    public boolean retainAll(Collection collection)
    {
        clear();
        addAll(collection);
        return true;
    }

    public Node addAfter(Object obj, Node node)
    {
        if(node == null)
        {
            throw new IllegalArgumentException("target is null");
        } else
        {
            Node node1 = new Node();
            node1.data = obj;
            insertNodeAfter(node1, node);
            return node1;
        }
    }

    public boolean isEmpty()
    {
        return first == null;
    }

    public boolean remove(Object obj)
    {
        Node node = findNode(obj);
        if(node == null)
            return false;
        else
            return removeNode(node);
    }

    public Object remove(int i)
    {
        Node node = getNode(i);
        removeNode(node);
        return node.data;
    }

    public boolean removeAll(Collection collection)
    {
        boolean flag = true;
        for(Iterator iterator1 = collection.iterator(); iterator1.hasNext();)
            flag &= remove(iterator1.next());

        return flag;
    }

    public Object[] toArray()
    {
        Object aobj[] = new Object[size];
        Node node = first;
        int i = 0;
        for(; node != null; node = node.next)
            aobj[i++] = node.data;

        return aobj;
    }

    public Object[] toArray(Object aobj[])
    {
        int i = 0;
        for(Node node = first; i < aobj.length && node != null; node = node.next)
            aobj[i++] = node.data;

        return aobj;
    }

    public Object get(int i)
    {
        if(size <= i)
            return null;
        Node node = getNode(i);
        if(node != null)
            return node.data;
        else
            return null;
    }

    public Object set(int i, Object obj)
    {
        return null;
    }

    public LinkedList()
    {
    }

    public boolean removeNode(Node node)
    {
        if(node == null)
            return false;
        if(node == first)
        {
            first = node.next;
            if(first != null)
                first.prev = null;
        } else
        if(node == last)
        {
            last = node.prev;
            if(last != null)
                last.next = null;
        } else
        {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        node.next = null;
        node.prev = null;
        size--;
        if(size == 0)
            first = last = null;
        else
        if(size == 1)
            last = null;
        return true;
    }

    public void insertNodeBefor(Node node, Node node1)
    {
        if(first == node1)
            first = node;
        if(last == null)
            last = node1;
        node.next = node1;
        node.prev = node1.prev;
        if(node.prev != null)
            node.prev.next = node;
        node1.prev = node;
        size++;
    }

    public void insertNodeAfter(Node node, Node node1)
    {
        if(first == null)
        {
            first = node;
            node.prev = null;
            node.next = null;
        } else
        if(last == null)
        {
            last = node;
            first.next = last;
            last.prev = first;
            last.next = null;
        } else
        {
            node.prev = node1;
            node.next = node1.next;
            if(node1 == last)
                last = node;
            else
                node1.next.prev = node;
            node1.next = node;
        }
        size++;
    }

    public boolean contains(Object obj)
    {
        for(Node node = first; node != null; node = node.next)
            if(node.data == obj || node.data.equals(obj))
                return true;

        return false;
    }

    public int size()
    {
        return size;
    }

    public void copyFrom(LinkedList linkedlist)
    {
        first = linkedlist.first;
        last = linkedlist.last;
        size = linkedlist.size;
    }

    public void moveBefor(Node node, Node node1)
    {
        removeNode(node);
        insertNodeBefor(node, node1);
    }

    public void addNode(Node node)
    {
        node.prev = null;
        node.next = null;
        if(first == null)
            first = node;
        else
        if(last == null)
        {
            last = node;
            first.next = last;
            last.prev = first;
        } else
        {
            node.prev = last;
            last.next = node;
            last = node;
        }
        size++;
    }

    public void moveAfter(Node node, Node node1)
    {
        if(node1 == null)
        {
            return;
        } else
        {
            removeNode(node);
            insertNodeAfter(node, node1);
            return;
        }
    }

    public void clear()
    {
        first = last = null;
        size = 0;
    }

    public int lastIndexOf(Object obj)
    {
        return -1;
    }

    private Node first;
    private Node last;
    private int size;
	/* (non-Javadoc)
	 * @see java.util.List#listIterator()
	 */
	@Override
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.List#listIterator(int)
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}
}
