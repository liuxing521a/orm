package org.itas.core.net;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.itas.buffer.SendAble;
import org.itas.common.Logger;
import org.itas.core.CallBack;
import org.itas.core.User;

/**
 * <p>游戏中场景</p>
 * @author liuzhen
 */
final class GameScene {

	/** 场景Id*/
	private final String sceneId;
	/** 读写锁*/
	private final ReadWriteLock lock;
	/** 场景所有玩家的channel*/
	private final Map<String, User> channels;

	GameScene(String sceneId) {
		this.sceneId = sceneId;
		this.lock = new ReentrantReadWriteLock();
		this.channels = new HashMap<>(1024);
	}
	
	/**
	 * <p>获取域唯一标识</p>
	 * @return 域唯一标识
	 */
	public String getSceneId() {
		return sceneId;
	}
	
	/**
	 * <p>域中所有人发送消息</p>
	 * @param message 发送消息内容
	 */
	public void sendAll(SendAble message)  {
		lock.readLock().lock();
		try  {
			for (Entry<String, User> entry : channels.entrySet()) {
				entry.getValue().send(message);
			}
			Logger.debug("send without flush to scene:sceneId={}, msg={}",  sceneId,  message);
		} finally  {
			lock.readLock().unlock();
		}
	}
	
	/**
	 * <p>域中所有人发送消息</p>
	 * @param message 发送消息内容
	 */
	public void sendAndFlushAll(SendAble message)  {
		lock.readLock().lock();
		try  {
			for (Entry<String, User> entry : channels.entrySet()) {
				entry.getValue().sendAndFlush(message);
			}
			
			Logger.debug("send and flush to scene:sceneId={}, msg={}",  sceneId,  message);
		} finally  {
			lock.readLock().unlock();
		}
	}
	/**
	 * <p>域中所有人发送消息</p>
	 * @param SendAble 发送消息内容
	 */
	public void flushAll()  {
		lock.readLock().lock();
		try  {
			for (Entry<String, User> entry : channels.entrySet()) {
				entry.getValue().flush();
			}
			
			Logger.debug("flush all to scene:sceneId={}",  sceneId);
		} finally  {
			lock.readLock().unlock();
		}
	}

	/**
	 * <p>域中指定玩家外所有人发消息</p>
	 * @param userList 域中不发送消息者
	 */
	public void callBackScene(CallBack<User> back) {
		lock.readLock().lock();
		try {
			for (Entry<String, User> entry : channels.entrySet()) {
				back.called(entry.getValue());
			}

			Logger.debug("call back to scene:sceneId={}", sceneId);
		} finally {
			lock.readLock().unlock();
		}
	}
	
	/**
	 * <p>进入该域</p>
	 * @param channel
	 */
	public void enterScene(User user) {
		lock.writeLock().lock();
		try {
			channels.put(user.getId(), user);
		} finally  {
			lock.writeLock().unlock();
		}
	}

	/**
	 * <p>退出该域</p>
	 * @param userId 退出该域玩家Id
	 */
	public void exitScene(User user)  {
		lock.writeLock().lock();
		try  {
			channels.remove(user.getId());
		} finally {
			lock.writeLock().unlock();
		}
	}
	
}
