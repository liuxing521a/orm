package org.itas.core.net;


import org.itas.buffer.SendAble;
import org.itas.common.Utils.Objects;
import org.itas.core.CallBack;
import org.itas.core.User;


public final class IoUtil {

	/** 世界域*/
	private static final GameWorld WOLRD = new GameWorld(2048);

	public static void onLogin(User user, CallBack<User> call) {
		User current = WOLRD.enterWorld(user);
		if (Objects.nonNull(current)) {
			call.called(current);
		}
	}

	public static void onLogout(User user) {
		User current = WOLRD.getUser(user.getId());
		
		if (user == current) {
			WOLRD.exitWorld(user);
		}
	}

    public static void send(String userId, SendAble message) {
    	WOLRD.send(userId, message);
    }

    public static void sendWorld(SendAble message) {
    	WOLRD.sendWorld(message);
    }

    public static void flushWorld() {
    	WOLRD.flushWorld();
    }

    public static void sendAndFlushWorld(SendAble message) {
    	WOLRD.sendAndFlushWorld(message);
    }
	
	public static void callBackWorld(CallBack<User> back) {
		WOLRD.callBackWorld(back);
	}
	
	
	public static void sendMuilt(SendAble message, Iterable<String> userList) {
		WOLRD.sendMuilt(message, userList);
	}

	public static void flushMuilt(Iterable<String> userList) {
		WOLRD.flushMuilt(userList);
	}

	public static void sendAndFlushMuilt(SendAble message, Iterable<String> userList) {
		WOLRD.sendAndFlushMuilt(message, userList);
	}
	
	public static void sendScene(String sceneId, SendAble message) {
		WOLRD.sendScene(sceneId, message);
	}

	public static void flushScene(String sceneId, SendAble message) {
		WOLRD.flushScene(sceneId);
	}

	public static void sendAndFlushScene(String sceneId, SendAble message) {
		WOLRD.sendAndFlushScene(sceneId, message);
	}
	
	public static void callBackScene(String sceneId, CallBack<User> back) {
		WOLRD.callBackScene(sceneId, back);
	}

	public static void flushScene(String sceneId) {
		WOLRD.flushScene(sceneId);
	}
	
	public static void enterScene(String sceneId, User user) {
		WOLRD.enterScene(sceneId, user);
	}

	public static void exitScene(String sceneId, User user) {
		WOLRD.exitScene(sceneId, user);
	}
	
	public static void enterWorld(User user) {
		WOLRD.enterWorld(user);
	}

	public static void exitWorld(User user) {
		WOLRD.exitWorld(user);
	}
	
	public static String getUserId(String nickName) {
		return WOLRD.getUserId(nickName);
	}

	public static User getUser(String userId) {
		return WOLRD.getUser(userId);
	}
	
	public static boolean isOnLine(String userId) {
		return WOLRD.isOnLine(userId);
	}

	private IoUtil() {
		throw new RuntimeException("can not newInstance");
	}
}
