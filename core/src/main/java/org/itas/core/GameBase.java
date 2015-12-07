package org.itas.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.itas.common.ItasException;
import org.itas.common.Pair;
import org.itas.core.Pool.DataPool;
import org.itas.core.Syner.GameBaseSyner;
import org.itas.core.annotation.Primary;
import org.itas.core.annotation.UnSave;
import org.itas.core.cache.CacheAble;
import org.itas.core.util.ItContainer;

abstract class GameBase implements CacheAble {
	
	private static final DataPool pool;
	
	static {
		pool = Ioc.getInstance(DataPool.class);
	}
	
	enum DataStatus {
		unload,		// 未加载
		load,		// 无状态数据
		news,		// 新数据
		modify,		// 修改数据
		destory,	// 销毁数据
		expired,	// 被逐出
	}
	
	protected GameBase(String Id) {
		this.Id = Id;
		this.status = DataStatus.unload;
		this.syner = pool.getSyner(this.getClass());
	}
	
	/**唯一Id*/
	@Primary protected String Id;
	
	/**修改时间 */
	protected Timestamp updateTime;
	
	/**创建时间 */
	protected Timestamp createTime;
	
	/** 对象当前状态 */
	@UnSave private volatile DataStatus status;
	
	@UnSave private GameBaseSyner syner;
	
	
	
	/**
	 * id前缀，以次来区分一个字符串id属于那个对象
	 */
	protected abstract String prefix();
	
	void initialized() {
		setDataStatus(DataStatus.news);
		syner.addInsert(this);
	}
	
	public final String getId() {
		return Id;
	}
	
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(long millis) {
		this.updateTime.setTime(millis);
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	protected void modify() {
		if (getDataStatus() == DataStatus.load) {
			setDataStatus(DataStatus.modify);
			syner.addUpdate(this);
		}
	}

	public void destroy() {
		if (getDataStatus() != DataStatus.destory) {
			setDataStatus(DataStatus.destory);
			syner.addDelete(this);
		}
	}

	void setDataStatus(DataStatus status) {
		this.status = status;
	}
	
	DataStatus getDataStatus() {
		return status;
	}

	final void doStatement(PreparedStatement statement, DataStatus status) 
	throws SQLException {
		switch (status) {
		case news: 
			doInsert(statement);
			statement.addBatch();
			setDataStatus(DataStatus.load);
			break;
		case modify:
			doUpdate(statement);
			statement.addBatch();
			setDataStatus(DataStatus.load);
			break;
		case destory: 
			doDelete(statement);
			statement.addBatch();
			break;
		default: 
			throw new ItasException(
				String.format("%s unkown data status:%s", this.getClass().getName(), status));
		}
	}
	
	final void doResultSet(ResultSet result) throws SQLException {
		if (getDataStatus() == DataStatus.unload) {
			doFill(result);
			setDataStatus(DataStatus.load);
		}
	}
	
	protected String tableName() {
		throw new ItasException("getTableName must @Override");
	}
	
	protected String selectSQLArray() {
		throw new ItasException("selectSQLArray must Override");
	}
	
	protected String selectSQL() {
		throw new ItasException("selectSQL must Override");
	}
	
	protected String insertSQL() {
		throw new ItasException("insertSQL must Override");
	}
	
	protected String updateSQL() {
		throw new ItasException("updateSQL must Override");
	}
	
	protected String deleteSQL() {
		throw new ItasException("deleteSQL must Override");
	}
	
	protected void doCreate(Statement statement) {
		throw new ItasException("doCreate must Override");
	}
	
	protected void doAlter(Statement statement, Set<String> excludeColums) 
	throws java.sql.SQLException {
		throw new ItasException("doAlter must Override");
	}
	
	protected void doInsert(PreparedStatement statement) 
	throws java.sql.SQLException {
		throw new ItasException("doInsert must Override");
	}

	protected void doUpdate(PreparedStatement statement) 
	throws java.sql.SQLException {
		throw new ItasException("doUpdate must Override");
	}
	
	protected void doDelete(PreparedStatement statement) 
		throws java.sql.SQLException {
		throw new ItasException("doDelete must Override");
	}
	
	protected void doFill(ResultSet result) 
	throws java.sql.SQLException {
		throw new ItasException("doFill must Override");
	}

	public GameBase clone(String Id) {
		throw new ItasException("clone(String Id) must Override");
	}
	
	protected char parseChar(String text) {
		return ItContainer.parseChar(text);
	}

	protected String parseString(String text) {
		return ItContainer.parseString(text);
	}

	protected String[] parseArray(String content) {
		return ItContainer.parseArray(content);
	}

	protected String[][] parseDoubleArray(String content) {
		return ItContainer.parseDoubleArray(content);
	}

	protected Pair<String, String>[] parsePair(String content) {
		return ItContainer.parsePair(content);
	}

	protected String toString(Collection<?> c) {
		return ItContainer.toString(c);
	}

	protected String toString(Map<?, ?> m) {
		return ItContainer.toString(m);
	}

	protected String toString(Object[] array) {
		return ItContainer.toString(array);
	}

	protected String toString(Object[][] array) {
		return ItContainer.toString(array);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
		if (o.getClass() == this.getClass()) {
			return Id.equals(Id);
		}
		
		return false;
	}
	
	@Override
	public final int hashCode() {
		return 31 + Id.hashCode();
	}
	
	public int getCachedSize() {
		return 86;
	}
	
	public final <T extends GameBase> Simple<T> simple() {
		return new Simple<T>(Id);
	}
	
}
