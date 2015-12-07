package org.itas.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

/**
 * SQL执行器
 * @author liu(liuxing521a@gmail.com)
 * @crateTime 2015年4月16日上午10:59:41
 */
public interface SQLExecutor {

	public int execute(String sql) throws Exception;
	
	public int execute(String sql, Object[] args) throws Exception;
	
	public int execute(Called<PreparedStatement> statement) throws Exception;

	public int[] executeBatch(String[] sql) throws Exception;

	public int[] executeBatch(String sql, Object[][] args) throws Exception;

	public int[] executeBatch(Called<Statement> statement) throws Exception;

	public <T> T queryForObject(
		RowMapper<T> mapper, String sql, Object[] args) throws Exception;

	public <T> T queryForObject(
		RowMapper<T> mapper, Called<PreparedStatement> statement) throws Exception;

	public <T> T[] queryForArray(
		RowMapper<T> mapper, String sql, Object[] args) throws Exception;

	public <T> T[] queryForArray(
		RowMapper<T> mapper, Called<PreparedStatement> statement) throws Exception;
	
	public Set<String> tableColumns(String tableName) throws SQLException;
	
	public interface RowMapper<T> {

	  public T mapRow(ResultSet rs) throws SQLException;

	}
	
	interface Called<R extends Statement> {
		R called(Connection conn) throws Exception;
	}
}
