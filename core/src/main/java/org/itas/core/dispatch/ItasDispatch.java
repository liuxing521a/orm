package org.itas.core.dispatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.itas.common.Utils.ClassUtils;
import org.itas.common.Utils.Objects;
import org.itas.core.Dispatch;
import org.itas.core.DoubleException;
import org.itas.core.User;
import org.itas.core.net.Message;

public class ItasDispatch extends Dispatch {

	/**  事件处理者*/
	private Map<Short, Handle> dispatchMap;
	
			
	static {
	}

	public ItasDispatch() {
		this.dispatchMap = new HashMap<Short, Handle>();
	}

	@Override
	public void bind(String pack) throws Exception  {
		List<Class<?>> clazzes = ClassUtils.loadClazz(Handle.class, pack);
		
		Handle event, old;
		for (Class<?> clazz : clazzes) {
			event = ClassUtils.newInstance(clazz);
			
			old = dispatchMap.put(event.getEventNum(), event);
			if (Objects.nonNull(old)) {
				throw new DoubleException(
						"[clazz=" + old.getClass().getName() + "],[protoclHead=" + Integer.toHexString(event.getEventNum()) + "]");
			}
		}
	}

	@Override
	public void unBind() {
		dispatchMap = null;
	}

	@Override
	protected Handle getHandle(short clazzHead) {
		return dispatchMap.get(clazzHead);
	}

	@Override
	protected void dispatchEvent(User user, Handle event, Message message) throws Exception {
		event.dispatch(user, message);
	}
}
