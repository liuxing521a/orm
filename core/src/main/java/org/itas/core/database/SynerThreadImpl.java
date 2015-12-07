package org.itas.core.database;

import org.itas.common.ItasException;
import org.itas.common.Logger;
import org.itas.core.Builder;
import org.itas.core.Service.OnShutdown;
import org.itas.core.Service.OnStartUP;
import org.itas.core.Syner;

/**
 * <p>环写数据库线程</p>
 * 
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014-3-17
 */
final class SynerThreadImpl extends Syner implements OnShutdown, OnStartUP {

	private SynerThreadImpl(long interval) {
		super(interval);
		isFlag = false;
		worker = new Thread(this, "database-syner-thread");
	}

	/** 线程运行标记 */
	private boolean isFlag;

	/** 执行线程 */
	private final Thread worker;

	@Override
	public void onStartUP() {
		if (worker.isAlive()) {
			throw new ItasException("thread is alive");
		}

		isFlag = false;
		worker.start();
	}

	@Override
	public void onShutdown() {
		isFlag = true;
	}

	@Override
	public synchronized void run() {
		while (!isFlag) {
			try {
				worker.wait(super.synDatabase());
			} catch (InterruptedException exception) {
				// do nothing
			} catch (Throwable e) {
				Logger.error("", e);
			}
		}
	}

	public static SynerThreadImplBuilder newBuilder() {
		return new SynerThreadImplBuilder();
	}

	public static class SynerThreadImplBuilder implements Builder {

		private long interval;

		private SynerThreadImplBuilder() {
		}

		public SynerThreadImplBuilder setInterval(long interval) {
			this.interval = interval;
			return this;
		}

		@Override
		public SynerThreadImpl builder() {
			return new SynerThreadImpl(interval);
		}
	}
}
