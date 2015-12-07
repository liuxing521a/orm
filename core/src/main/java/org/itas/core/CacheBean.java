package org.itas.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Set;

import org.itas.common.Logger;
import org.itas.common.collection.CircularQueue;
import org.itas.core.GameBase.DataStatus;
import org.itas.core.SQLExecutor.Called;
import org.itas.core.SQLExecutor.RowMapper;
import org.itas.core.Syner.GameBaseSyner;
import org.itas.core.cache.Cache;

final class CacheBean implements GameBaseSyner {
	
	private static SQLExecutor excuter;

	static {
		excuter = Ioc.getInstance(SQLExecutor.class);
	}
	
	private final Class<?> clazz;
	private final GameBase module;
	private final Cache<String, GameBase> cached;
	private final CircularQueue<GameBase> insertQueue;
	private final CircularQueue<GameBase> updateQueue;
	private final CircularQueue<GameBase> deleteQueue;

	private CacheBean(
			GameBase module,
			Cache<String, GameBase> cached,
			CircularQueue<GameBase> insertQueue,
			CircularQueue<GameBase> updateQueue,
			CircularQueue<GameBase> deleteQueue) {
		this.module = module;
		this.clazz = module.getClass();
		this.cached = cached;
		this.insertQueue = insertQueue;
		this.updateQueue = updateQueue;
		this.deleteQueue = deleteQueue;
	}
	
	public Class<?> clazz() {
		return clazz;
	}
	
	public String prefix() {
		return module.prefix();
	}
	
	public GameBase module() {
		return module;
	}
	
	public boolean contains(String Id) {
		return cached.containsKey(Id);
	}
	
	public GameBase get(String Id) {
		GameBase gameBase = cached.get(Id);
		if (Objects.nonNull(gameBase)) {
			return gameBase;
		}
		
		synchronized (Id.intern()) {
			return loadFromDB(Id);
		}
	}
	
	public void cache(GameBase o) {
		cached.putIfAbsent(o.getId(), o);
	}
	
	public GameBase remove(String Id) {
		return cached.remove(Id);
	}
	
	@Override
	public void addInsert(GameBase GameBase) {
		synchronized (insertQueue) {
			insertQueue.push(GameBase);
		}
	}

	@Override
	public void addUpdate(GameBase GameBase) {
		synchronized (updateQueue) {
			updateQueue.push(GameBase);
		}
	}

	@Override
	public void addDelete(GameBase GameBase) {
		synchronized (deleteQueue) {
			deleteQueue.push(GameBase);
		}
	}
	
	public GameBase newInstance(String Id) {
		return module.clone(Id);
	}
	
	private GameBase loadFromDB(String Id) {
		try {
			GameBase gameObject = newInstance(Id);
			if (load(gameObject)) {
				cache(gameObject);
			}

			return cached.get(Id);
		} catch (Exception e) {
			Logger.error("", e);
			return null;
		}
	}
	
	private boolean load(GameBase o) throws Exception {
		GameBase rs = excuter.queryForObject(
			new RowMapper<GameBase>() {
				@Override
				public GameBase mapRow(ResultSet rs) throws SQLException {
					o.doFill(rs);
					return o;
				}
			},

			new Called<PreparedStatement>() {
				@Override 
				public PreparedStatement called(Connection conn) throws Exception {
					PreparedStatement ppst = conn.prepareStatement(o.selectSQL());
					ppst.setString(1, o.getId());
					return ppst;
				}
			});
		
		return rs == o;
	}
	
	void doInsert() throws Exception {
		final int size = insertQueue.size();
		if (size == 0) {
			return;
		}
		
		excuter.executeBatch(
			new Called<Statement>() {
				@Override
				public PreparedStatement called(Connection conn) throws Exception {
					PreparedStatement ppst = conn.prepareStatement(module.insertSQL());
	
					GameBase data;
					for (int i = 0; i < size; ) {
						synchronized (insertQueue) {
							data = insertQueue.pop();
						}
	
						if (data.getDataStatus() == DataStatus.news) {
							data.doStatement(ppst, DataStatus.news);
							i ++;
						}
	
						if ((i & 0xFF) == 0xFF && ((i + 1) != size)) {
							ppst.executeBatch();// 每255个执行一次,避免一次性太多造成性能下降
						}
					}
	
					return ppst;
				}
		});
	}

	void doUpdate() throws Exception {
		final int size = updateQueue.size();
		if (size == 0) {
			return;
		}
		
		excuter.executeBatch(
			new Called<Statement>() {
				@Override
				public PreparedStatement called(Connection conn) throws Exception {
					PreparedStatement ppst = conn.prepareStatement(module.updateSQL());

					GameBase data;
					for (int i = 0; i < size; ) {
						synchronized (updateQueue) {
							data = updateQueue.pop();
						}

						if (data.getDataStatus() == DataStatus.modify) {
							data.doStatement(ppst, DataStatus.modify);
							i ++;
						}

						if ((i & 0xFF) == 0xFF && ((i + 1) != size)) {
							ppst.executeBatch();// 每255个执行一次,避免一次性太多造成性能下降
						}
					}

					return ppst;
				}
			});
	}

	void doDelete() throws Exception {
		final int size = deleteQueue.size();
		if (size == 0) {
			return;
		}
		
		excuter.executeBatch(
			new Called<Statement>() {
				@Override
				public PreparedStatement called(Connection conn) throws Exception {
					PreparedStatement ppst = conn.prepareStatement(module.updateSQL());

					GameBase data;
					for (int i = 0; i < size; ) {
						synchronized (deleteQueue) {
							data = deleteQueue.pop();
						}

						if (data.getDataStatus() == DataStatus.destory) {
							data.doStatement(ppst, DataStatus.destory);
							 i ++;
						}

						if ((i & 0xFF) == 0xFF && ((i + 1) != size)) {
							ppst.executeBatch();// 每255个执行一次,避免一次性太多造成性能下降
						}
					}

					return ppst;
				}
			});
	}
	
	void doCreate(Statement statement) throws SQLException {
		module.doCreate(statement);
	}
	
	void doAlter(Statement statement) throws SQLException {
		Set<String> clumns = excuter.tableColumns(module.tableName());
		module.doAlter(statement, clumns);
	}
	
}
