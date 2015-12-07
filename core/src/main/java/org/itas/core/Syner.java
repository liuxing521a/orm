package org.itas.core;

import java.sql.Connection;
import java.sql.Statement;

import org.itas.common.Utils.TimeUtil;
import org.itas.core.SQLExecutor.Called;

public abstract class Syner implements Runnable {

	private static SQLExecutor excuter;
	private static DataBaseSyner syner;

	static {
		excuter = Ioc.getInstance(SQLExecutor.class);
		syner = Ioc.getInstance(DataBaseSyner.class);
	}

	protected Syner(long interval) {
		this.interval = interval;
		this.lastTime = TimeUtil.systemTime();
	}

	/** 上次同步数据库时间 */
	private long lastTime;

	/** 同步间隔时间 */
	private final long interval;

	/**
	 * 做同步数据库操作，只有当当前时间和上次同步时间比较大于间隔时间才会同步
	 * @return 同步间隔时间
	 * @throws Exception
	 */
	public long synDatabase() {
		if (isPresistentAble()) {
			syner.synDatabase();
			lastTime = TimeUtil.systemTime();
		}

		return interval;
	}

	/**
	 * 强制同步数据库
	 * @throws Exception
	 */
	public void forceSynDatabase() {
		syner.synDatabase();
	}

	public void createTable() throws Exception {
		excuter.executeBatch(
			new Called<Statement>() {
				@Override
				public Statement called(Connection conn) throws Exception {
					Statement statement = conn.createStatement();
					syner.createTable(statement);
					return statement;
				}
			});
	}
	
	public void alterTable() throws Exception {
		excuter.executeBatch(
			new Called<Statement>() {
				@Override
				public Statement called(Connection conn) throws Exception {
					Statement statement = conn.createStatement();
					syner.alterTable(statement);
					return statement;
				}
			});
	}
	
	private boolean isPresistentAble() {
		return TimeUtil.systemTime() - lastTime > interval;
	}

	interface GameBaseSyner {

		abstract void addInsert(GameBase GameBase);

		abstract void addUpdate(GameBase GameBase);

		abstract void addDelete(GameBase GameBase);
	}

	interface DataBaseSyner {

		abstract void createTable(Statement statement) throws Exception;

		abstract void alterTable(Statement statement) throws Exception;
		
		abstract void synDatabase();
	}

}