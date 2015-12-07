package org.itas.core.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.itas.core.Builder;
import org.itas.core.DoubleException;
import org.itas.core.Pool.DBPool;
import org.itas.core.Service.OnShutdown;
import org.itas.core.Service.OnStartUP;
import org.itas.core.dbpool.BoneCP;
import org.itas.core.dbpool.BoneCPConfig;

import com.typesafe.config.Config;

final class DBPoolImpl implements DBPool, OnStartUP, OnShutdown {

	/** 连接池 */
	private BoneCP dbPool;

	private final BoneCPConfig config;

	private DBPoolImpl(Config config) {
		this.config = new BoneCPConfig();

		this.config.setJdbcUrl(config.getString("url"));
		this.config.setUsername(config.getString("userName"));
		this.config.setPassword(config.getString("password"));
		this.config.setAcquireRetryAttempts(Short.MAX_VALUE);
		this.config.setPartitionCount(config.getInt("partitionCount"));
		this.config.setAcquireIncrement(config.getInt("acquireIncrement"));
		this.config.setMinConnectionsPerPartition(config.getInt("minConnPerPart"));
		this.config.setMaxConnectionsPerPartition(config.getInt("maxConnPerPart"));
		this.config.setPoolAvailabilityThreshold(config.getInt("poolAvailabilityThreshold"));
		this.config.setAcquireRetryDelay(8000);
		this.config.setLogStatementsEnabled(true);
	}

	@Override
	public synchronized void onStartUP() throws Exception {
		checkNull();
		dbPool = new BoneCP(config);
	}

	@Override
	public synchronized void onShutdown() throws Exception {
		if (dbPool != null) {
			dbPool.shutdown();
			dbPool = null;
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dbPool.getConnection();
	}

	private void checkNull() {
		if (dbPool != null) {
			throw new DoubleException("dbpool init more once times...");
		}
	}

	public static DBPoolImplBuilder newBilder() {
		return new DBPoolImplBuilder();
	}

	public static class DBPoolImplBuilder implements Builder {

		private Config config;

		private DBPoolImplBuilder() {
		}

		public DBPoolImplBuilder setConfig(Config config) {
			this.config = config;
			return this;
		}

		@Override
		public DBPoolImpl builder() {
			return new DBPoolImpl(this.config);
		}
	}

}
