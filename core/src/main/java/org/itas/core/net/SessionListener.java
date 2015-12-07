package org.itas.core.net;

import io.netty.channel.Channel;

import java.util.EventListener;


/**
 * <p>session监听器</p>
 * 主要监听session被创建 或者被销毁事件
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014-3-17
 */
public interface SessionListener extends EventListener {
	
	/**
	 *<p>session被关闭</p> 
	 * @param session 被关闭的session
	 */
	public void onSessionConnected(Channel session);
	
	/**
	 * <p>连接后token被发送</p> 
	 * @param session
	 * @param buf
	 */
	public void onTokenReceived(Message message);
	
	/**
	 * <p>session被创建</p>
	 * @param session 被创建的session
	 */
	public void onSessionDestroyed(Channel session);
}
