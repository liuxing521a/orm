package org.itas.core.dispatch;

import org.itas.common.Logger;
import org.itas.core.Hook;

public class HookDispatch implements Hook {
	
	/** 开始时间*/
	private long beginTime;
	
	private static final ThreadLocal<Hook> current = ThreadLocal.withInitial(HookDispatch::new);
	
    @Override
	public Hook current() {
		return current.get();
	}
    
	@Override
	public void onBeforeExecute(Object o) {
		this.beginTime = System.nanoTime();
		Logger.debug("Message recived:{}", o);
	}

	@Override
	public void onAfterExecute(Object o) {
		Logger.debug("Method excuteTime:{}, method:{}", (System.nanoTime() - beginTime), o);
	}

}
