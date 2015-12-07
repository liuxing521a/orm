package org.itas.core.net;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.itas.buffer.SendAble;
import org.itas.common.Logger;
import org.itas.core.CallBack;
import org.itas.core.User;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * <p>游戏中</p>
 * @author liuzhen
 */
public final class GameWorld {

	/** 读写锁*/
	private final ReadWriteLock lock;
	/** 场景所有玩家的session*/
	private final Map<String, User> user_group;
	/** 玩家名映射玩家Id*/
	private final BiMap<String, String> userid_name_group;
	/**城市中玩家*/
	private final Map<String, GameScene> scenes;

	public GameWorld(int size) {
		this.user_group = new HashMap<>(size);
		this.scenes = new HashMap<String, GameScene>();
		this.userid_name_group = HashBiMap.create(size);
		this.lock = new ReentrantReadWriteLock();
	}
	
	public void send(String userId, SendAble sendAble) {
		lock.readLock().lock();
		try  {
			User user = user_group.get(userId);
			if (Objects.nonNull(user)) {
				user.send(sendAble);
				Logger.debug("send without flush message:{}", sendAble);
			} else {
				Logger.debug("send without flush message:userId={} not online", userId);
			}
		} finally  {
			lock.readLock().unlock();
		}
	}
	
	public void sendAndFlush(String userId, SendAble message) {
		lock.readLock().lock();
		try  {
			User user = user_group.get(userId);
			if (Objects.nonNull(user)) {
				user.sendAndFlush(message);
				Logger.debug("sendAndFlush message:{}", message);
			} else {
				Logger.debug("sendAndFlush message:userId={} not online", userId);
			}
		} finally  {
			lock.readLock().unlock();
		}
	}
	
	public void flush(String userId, Message message) {
		lock.readLock().lock();
		try  {
			User user = user_group.get(userId);
			if (Objects.nonNull(user)) {
				user.flush();
				Logger.debug("flush message:{}", message);
			} else {
				Logger.debug("flush message:userId={} not online", userId);
			}
		} finally  {
			lock.readLock().unlock();
		}
	}
	
	public void sendWorld(SendAble message)  {
		lock.readLock().lock();
		try  {
			for (User user: user_group.values()) {
				user.send(message);
			}
			
			Logger.debug("send without flush to world, message = {}", message);
		} finally  {
			lock.readLock().unlock();
		}
	}
	
	public void sendAndFlushWorld(SendAble message)  {
		lock.readLock().lock();
		try  {
			for (User user: user_group.values()) {
				user.sendAndFlush(message);
			}
			
			Logger.debug("send and flush to world, message = {}", message);
		} finally  {
			lock.readLock().unlock();
		}
	}
	
	public void flushWorld()  {
		lock.readLock().lock();
		try  {
			for (User user: user_group.values()) {
				user.flush();
			}
			
			Logger.debug("flush to world");
		} finally  {
			lock.readLock().unlock();
		}
	}

	/**
	 * <p>域中指定玩家外所有人发消息</p>
	 * @param message 发送消息内容
	 * @param userList 域中不发送消息者
	 */
	public void callBackWorld(CallBack<User> back) {
		lock.readLock().lock();
		try {
			for (User user: user_group.values()) {
				back.called(user);
			}
		} finally {
			lock.readLock().unlock();
		}
	}
	
	/**
	 * <p>域中所有人发送消息</p>
	 * @param String 域Id
	 * @param message 发送消息内容
	 */
	public void sendScene(String sceneId, SendAble message)  {
		getScene(sceneId).sendAll(message);
	}
	
	/**
	 * <p>域中所有人发送消息</p>
	 * @param String 域Id
	 * @param message 发送消息内容
	 */
	public void sendAndFlushScene(String sceneId, SendAble message)  {
		getScene(sceneId).sendAndFlushAll(message);
	}
	/**
	 * <p>域中所有人发送消息</p>
	 * @param String 域Id
	 * @param message 发送消息内容
	 */
	public void flushScene(String sceneId)  {
		getScene(sceneId).flushAll();
	}

	/**
	 * <p>域中指定玩家外所有人发消息</p>
	 * @param String 域Id
	 * @param userList 域中不发送消息者
	 * @param message 发送消息内容
	 */
	public void callBackScene(String sceneId, CallBack<User> back) {
		getScene(sceneId).callBackScene(back);
	}
	
	/**
	 * <p>给指定用户组发信息</p>
	 * @param userList 指定用户组
	 * @param message 消息
	 */
	public void sendMuilt(SendAble message, Iterable<String> userList) {
		lock.readLock().lock();
		try {
			User user;
			for (String userId : userList) {
				user = user_group.get(userId);
				if (Objects.nonNull(user)) {
					user.send(message);
				}
			}
			
			Logger.debug("send whiout flush to muilt:{}", Objects.toString(userList));
		} finally {
			lock.readLock().unlock();
		}
	}
	
	public void sendAndFlushMuilt(SendAble message, Iterable<String> userList) {
		lock.readLock().lock();
		try {
			User user;
			for (String userId : userList) {
				user = user_group.get(userId);
				if (Objects.nonNull(user)) {
					user.send(message);
				}
			}
			
			Logger.debug("send and flush to muilt userList:{}", Objects.toString(userList));
		} finally {
			lock.readLock().unlock();
		}
	}
	
	public void flushMuilt(Iterable<String> userList) {
		lock.readLock().lock();
		try {
			User user;
			for (String userId : userList) {
				user = user_group.get(userId);
				if (Objects.nonNull(user)) {
					user.flush();
				}
			}
			
			Logger.debug("flush to muil userList:{}", Objects.toString(userList));
		} finally {
			lock.readLock().unlock();
		}
	}
	
	/**
	 * <p>进入该域</p>
	 * @param session
	 */
	public void enterScene(String sceneId, User user) {
		getScene(sceneId).enterScene(user);
	}
	
	/**
	 * <p>退出该域</p>
	 * @param userId 退出该域玩家Id
	 */
	public void exitScene(String sceneId, User user)  {
		getScene(sceneId).exitScene(user);
	}
	
	/**
	 * <p>进入该域</p>
	 * @param session
	 */
	public User enterWorld(User user) {
		lock.writeLock().lock();
		try {
			userid_name_group.forcePut(user.getId(), user.getName());
			return user_group.put(user.getId(), user);
		} finally  {
			lock.writeLock().unlock();
		}
	}

	/**
	 * <p>退出该域</p>
	 * @param userId 退出该域玩家Id
	 */
	public void exitWorld(User user)  {
		lock.writeLock().lock();
		try  {
			userid_name_group.remove(user.getId());
			user_group.remove(user.getId());
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * <p>涉及该域列表处理</p>
	 * @param callBack 回调参数
	 */
	public void calbackWorld(CallBack<User> back) {
		lock.readLock().lock();
		try  {
			for (User user: user_group.values()) {
				back.called(user);
			}
		} finally {
			lock.readLock().unlock();
		}
	}
	
	
	/**
	 * <p>获取域</p>
	 * @param sceneId 域Id
	 * @return String 域
	 */
	public GameScene getScene(String sceneId)  {
		synchronized (scenes)  {
			GameScene scene = scenes.get(sceneId);
			
			if (scene == null)  {
				scenes.put(sceneId, scene = new GameScene(sceneId));
			}
			
			return scene;
		}
	}
	
	/**
	 * <p>根据玩家Id获取sessin</p>
	 * @param userId 玩家Id
	 * @return session
	 */
	public User getUser(String userId) {
		lock.readLock().lock();
		try  {
			return user_group.get(userId);
		} finally {
			lock.readLock().unlock();
		}
	}
	
	/**
	 * <p>根据玩家昵称获取玩家Id</p>
	 * @param userName 玩家昵称
	 * @return Integer 玩家Id
	 */
	public String getUserId(String userName) {
		lock.readLock().lock();
		try  {
			return userid_name_group.inverse().get(userName);
		} finally {
			lock.readLock().unlock();
		}
	}
	
	/**
	 * <p>判断玩家是否在线</p>
	 * @param userId 玩家Id
	 * @return true:在线; false:不在线
	 */
	public boolean isOnLine(String userId) {
		lock.readLock().lock();
		try  {
			return user_group.containsKey(userId);
		} finally  {
			lock.readLock().unlock();
		}
	}
	
	
}
