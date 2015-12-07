package org.itas.core.cache;

import java.util.Objects;


/** 环形双向链表 */
class LinkedList<E> {

	private Node<E> head;

    public LinkedList() {
    	head = new Node<E>();
    }

    public Node<E> getFirst() {
        Node<E> node = head.next;
        if (node == head) {
            return null;
        }
        return node;
    }

    public Node<E> getLast() {
        Node<E> node = head.previous;
        if (node == head) {
            return null;
        }
        return node;
    }

    public Node<E> addFirst(Node<E> node) {
    	return node.insert(head.next, head);
    }

    public Node<E> addFirst(E object) {
        return new Node<E>(object, head.next, head);
    }

    public Node<E> addLast(Node<E> node) {
    	return node.insert(head, head.previous);
    }

    public Node<E> addLast(E object) {
        return new Node<E>(object, head, head.previous);
    }

    public void clear() {
        //Remove all references in the list.
        Node<E> node = getLast();
        while (node != null) {
            node.remove();
            node = getLast();
        }

        //Re-initialize.
        head.next = head.previous = head;
    }

    @Override
	public String toString() {
        Node<E> node = head.next;
        StringBuilder buf = new StringBuilder();
        while (node != head) {
            buf.append(node.toString()).append(", ");
            node = node.next;
        }
        return buf.toString();
    }

	/** 链表节点 */
	static class Node<E> {
		/** 存储对象*/
		public E object;

		/** 上一个节点*/
	    public Node<E> previous;
	    
	    /** 下一个节点*/
	    public Node<E> next;

	    /** 时间戳*/
	    public long timestamp;

	    public Node() {
	    	previous = next = this;
	    }

	    public Node(E object, Node<E> next, Node<E> previous) {
	    	if (Objects.nonNull(next) && Objects.nonNull(previous)) {
	    		this.insert(next, previous);
	    	}
	    	
	        this.object = object;
	    }

	    public Node<E> remove() {
	        previous.next = next;
	        next.previous = previous;
	        previous = next = null;
	        return this;
	    }
	    
	    public Node<E> insert(Node<E> next, Node<E> previous) {
	        this.next = next;
	        this.previous = previous;
	        this.previous.next = this.next.previous = this;
	        return this;
	    }

	    @Override
		public String toString() {
	        return Objects.isNull(object) ? "null" : object.toString();
	    }
	}
}
