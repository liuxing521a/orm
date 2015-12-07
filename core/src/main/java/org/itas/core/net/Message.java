package org.itas.core.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import org.itas.buffer.SendAble;
import org.itas.common.Utils.Objects;
import org.itas.common.collection.CircularQueue;
import org.itas.core.User;

public class Message {
	
	static final AttributeKey<User> key = AttributeKey.valueOf("user");

	/** 消息缓冲队列*/
	private static final CircularQueue<Message> MESSAGE_QUEUE = new CircularQueue<Message>();
	

	/** 消息头*/
    private short head;
    
    /** 消息体*/
    private ByteBuf body;
    
    /** 连接channel*/
	private Channel channel;

	private Message() {
	}

	
	public void init(short head, ByteBuf body, Channel channel) {
		this.head = head;
		this.body = body;
		this.channel = channel;
	}
	
	public ByteBuf getBuffer() {
		return body;
	}

	public short getHead() {
		return head;
	}
	
    public short getMethod() {
        return (byte)head;
    }

    public short getClazz() {
        return (byte) (head >>> 8);
    }
    
	public String getHexHead() {
		return Integer.toHexString(getHead());
	}
	
	public String getHexClazz() {
		return Integer.toHexString(getClazz());
	}

	public String getHexMethod() {
		return Integer.toHexString(getMethod());
    }
	
	private User getUser() {
		return channel.attr(key).get();
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public ByteBuf pack() {
		return body.duplicate();
	}
	
	public void release() {
        this.head = 0;
		this.body = null;
		this.channel = null;
		
		synchronized (MESSAGE_QUEUE) {
			MESSAGE_QUEUE.push(this);
		}
	}
	
	
	@Override
	public String toString() {
		return String.format(
				"host=%s,head=%s,clazz=%s,method=%s,length=%s",
				getSessionInfo(channel),
				Integer.toHexString(getHead()).toUpperCase(), 
				Integer.toHexString(getClazz()).toUpperCase(),
				Integer.toHexString(getMethod()).toUpperCase(), 
				body.writerIndex());
	}
	
	public String toString(int...userIds) {
		return String.format(
				"users=%s,head=%s,clazz=%s,method=%s,length=%s",
				Objects.toString(userIds),
				Integer.toHexString(getHead()).toUpperCase(), 
				Integer.toHexString(getClazz()).toUpperCase(),
				Integer.toHexString(getMethod()).toUpperCase(), 
				body.writerIndex());
	}

	public String toString(Iterable<Integer> userIds) {
		return String.format(
				"users=%s,head=%s,clazz=%s,method=%s,length=%s",
				Objects.toString(userIds),
				Integer.toHexString(getHead()).toUpperCase(), 
				Integer.toHexString(getClazz()).toUpperCase(),
				Integer.toHexString(getMethod()).toUpperCase(), 
				body.writerIndex());
	}
	
	private String getSessionInfo(Channel session) {
		StringBuffer builder = new StringBuffer("");
		if (session != null) {
			User user = getUser();
			builder.append(Objects.isNull(user) ? "null" : user.getId()).append("@").append(session.remoteAddress());
		}
		
		return builder.toString();
	}
	
	public static Message allocate(short head, SendAble sendAbel) {
		return allocate(head, Unpooled.wrappedBuffer(sendAbel.toBuffer()), null);
	}
	
	public static Message allocate(short head, ByteBuf buffer, Channel channel) {
		Message msg;
		synchronized (MESSAGE_QUEUE) {
			msg = MESSAGE_QUEUE.pop();
		}
		
		if (Objects.isNull(msg)) {
			msg = new Message();
		}
		
		msg.init(head, buffer, channel);
		return msg;
	}
	
}
