package org.itas.core;

import java.io.IOException;
import java.util.Objects;

import org.itas.common.Logger;
import org.itas.core.dispatch.Handle;
import org.itas.core.net.Message;

/**
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014-3-13
 */
public abstract class Dispatch {
	
	/** 处理钩子*/
	private Hook hook;

	public Dispatch() {
	}

	/**
	 * <p>绑定事件处理</p>
	 * @param pack 事件处理者所在包
	 * @throws Exception
	 */
	public abstract void bind(String pack) throws Exception;
	
	/**
	 * <p>解除事件处理绑定</p>
	 */
	public abstract void unBind();
	
	/**
	 * <p>根据协前一半获取事件处理类</p>
	 * @param clazzHead 协议前一半
	 * @return 时间处理者
	 */
	protected abstract Handle getHandle(short clazzHead);

	/**
	 * <p>事件分配</p>
	 * @param user  玩家
	 * @param event  事件对象
	 * @param message  客户端消息
	 */
	protected abstract void dispatchEvent(User user, Handle event, Message message) throws Exception;

	public Hook getHook() {
		return hook;
	}

	public void setHook(Hook hook) {
		this.hook = hook;
	}
	
	
	public void dispatch(User user, Message message) throws IOException {
		Handle event = getHandle(message.getClazz());
		if (Objects.isNull(event)) {
			Logger.error("error protocol heard:head={}, clazz={}, method={}",
                new Object[]{message.getHexHead(), message.getHexClazz(), message.getHexMethod()});
			return;
		}
		
		if (Objects.isNull(user)) {
			throw new IOException("unkown user to dispatch");
		}
		
		onExecute(user, event, message);
	}
	
	private void onExecute(final User user, Handle event, Message message) {
		try  {
			synchronized (user) {
				if (Objects.nonNull(hook)){
					hook.current().onBeforeExecute(message);
				}
				
				dispatchEvent(user, event, message);
				
				if (Objects.nonNull(hook)) {
					hook.current().onAfterExecute(message);
				}
			}
		} catch (Throwable e) {
			Logger.error("[event:" + event.getClass().getSimpleName() + ", heard:" + message.getClazz() + "]", e);
		} 
	}
}
