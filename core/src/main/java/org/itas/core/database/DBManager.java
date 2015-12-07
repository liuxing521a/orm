package org.itas.core.database;

import org.itas.core.Builder;
import org.itas.core.Pool.DBPool;
import org.itas.core.SQLExecutor;
import org.itas.core.Service.OnBinder;
import org.itas.core.Service.OnShutdown;
import org.itas.core.Service.OnStartUP;

import com.google.inject.Binder;
import com.typesafe.config.Config;

public final class DBManager implements OnBinder, OnStartUP, OnShutdown {

	private final DBPoolImpl dbPool;

	private final SynerThreadImpl syner;

	private DBManager(long interval, Config config) {
		dbPool = DBPoolImpl.newBilder()
					.setConfig(config)
					.builder();

		syner = SynerThreadImpl.newBuilder()
					.setInterval(interval)
					.builder();
	}

	@Override
	public void bind(Binder binder) {
		binder.bind(DBPool.class).toInstance(dbPool);

		binder.bind(SQLExecutor.class).toInstance(
				SQLExecutorImpl.newBuilder().builder());
	}

	@Override
	public void onShutdown() throws Exception {
		syner.onShutdown();
		dbPool.onShutdown();
	}

	@Override
	public void onStartUP() throws Exception {
		dbPool.onStartUP();
		syner.createTable();
		syner.alterTable();
		syner.onStartUP();
	}

	public static DataBaseManagerBuilder newBuilder() {
		return new DataBaseManagerBuilder();
	}

	public static class DataBaseManagerBuilder implements Builder {

		private long interval;
		private Config config;

		private DataBaseManagerBuilder() {

		}

		public DataBaseManagerBuilder setInterval(long interval) {
			this.interval = interval;
			return this;
		}

		public DataBaseManagerBuilder setConfig(Config config) {
			this.config = config;
			return this;
		}

		@Override
		public DBManager builder() {
			return new DBManager(interval, config);
		}

	}

}
