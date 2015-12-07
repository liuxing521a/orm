package org.itas.core;

import io.netty.channel.Channel;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import org.itas.buffer.SendAble;
import org.itas.common.Logger;
import org.itas.core.annotation.UnSave;
import org.itas.core.net.Message;


/**
 * <p>定义并处理网络消息发送</p>
 * @author admin
 */
public abstract class User extends GameObject {
	
	protected User(String Id) {
		super(Id);
		this.isFlush = new AtomicBoolean(false);
	}
	
	/** 玩家通道*/
	@UnSave protected Channel channel;

	/** 是否需要flush*/
	@UnSave private AtomicBoolean isFlush;
	
	@UnSave private CallBack<SendAble> callBack;
	
	
	/** 角色昵称*/
	public abstract String getName();

	/** 场景*/
	public abstract String getSceneId();
	
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	/**
	 * <p>发送给自己</p>
	 * @param message 消息
	 */
	public void send(SendAble sendAble) {
		if (Objects.nonNull(callBack)) {
			callBack.called(sendAble);
			return;
		}
		
		// TODO
		Message message = Message.allocate(sendAble.SUFFIX(), sendAble);
		message.setChannel(channel);
		channel.write(sendAble.toBuffer());
		
		isFlush.compareAndSet(false, true);
		Logger.debug("send without Flush message:{}", message);
	}
	
	/**
	 * <p>发送给自己</p>
	 * @param message 消息
	 */
	public void sendAndFlush(SendAble sendAble) {
		if (Objects.nonNull(callBack)) {
			callBack.called(sendAble);
			return;
		}
		
		Message message = Message.allocate(sendAble.SUFFIX(), sendAble);
		message.setChannel(channel);
		channel.writeAndFlush(message);
		Logger.debug("send and Flush message:{}", message);
	}

	/**
	 * <p>推送消息</p>
	 */
	public void flush() {
		if (isFlush.get()) {
			channel.flush();
			isFlush.set(false);
		}
	}
	
	public void closeConn() {
		if (Objects.nonNull(channel)) {
			channel.close();
		}
	}
	
	public void setCallBack(CallBack<SendAble> callBack) {
		this.callBack = callBack;
	}
	
	@Override
	public int getCachedSize() {
		return 42 + super.getCachedSize();
	}

}
