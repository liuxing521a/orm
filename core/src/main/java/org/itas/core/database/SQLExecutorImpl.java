package org.itas.core.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.itas.common.ItasException;
import org.itas.core.Builder;
import org.itas.core.Ioc;
import org.itas.core.Pool.DBPool;
import org.itas.core.SQLExecutor;
import org.itas.core.util.AutoClose;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

class SQLExecutorImpl implements SQLExecutor, AutoClose {

	private static DBPool dbPool;

	static {
		dbPool = Ioc.getInstance(DBPool.class);
	}

	private SQLExecutorImpl() {
	}

	public int execute(String sql) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dbPool.getConnection();
			ps = conn.prepareStatement(sql);

			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new ItasException(e);
		} finally {
			close(ps, conn);
		}
	}

	public int execute(String sql, Object[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dbPool.getConnection();
			ps = buildStatement(conn, sql, args);

			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new ItasException(e);
		} finally {
			close(ps, conn);
		}
	}

	@Override
	public int execute(Called<PreparedStatement> back)
	throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = dbPool.getConnection();
			ps = back.called(conn);

			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new ItasException(e);
		} finally {
			close(ps, conn);
		}
	}

	@Override
	public int[] executeBatch(String[] sqlArray) {
		Connection conn = null;
		Statement ps = null;
		try {
			conn = dbPool.getConnection();
			ps = conn.createStatement();

			for (String sql : sqlArray) {
				ps.addBatch(sql);
			}

			return ps.executeBatch();
		} catch (SQLException e) {
			throw new ItasException(e);
		} finally {
			close(ps, conn);
		}
	}

	@Override
	public int[] executeBatch(String sql, Object[][] args) {
		Connection conn = null;
		Statement ps = null;
		try {
			conn = dbPool.getConnection();
			ps = buildStatement(conn, sql, args);

			return ps.executeBatch();
		} catch (SQLException e) {
			throw new ItasException(e);
		} finally {
			close(ps, conn);
		}
	}

	@Override
	public int[] executeBatch(Called<Statement> back) 
	throws Exception {
		Connection conn = null;
		Statement ps = null;
		try {
			conn = dbPool.getConnection();
			ps = back.called(conn);

			return ps.executeBatch();
		} catch (SQLException e) {
			throw new ItasException(e);
		} finally {
			close(ps, conn);
		}
	}

	@Override
	public <T> T queryForObject(RowMapper<T> mapper, Called<PreparedStatement> back)
	throws Exception {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = dbPool.getConnection();
			ps = back.called(conn);
			rs = ps.executeQuery();

			return rs.next() ? mapper.mapRow(rs) : null;
		} catch (SQLException e) {
			throw new ItasException(e);
		} finally {
			close(rs, ps, conn);
		}
	}

	@Override
	public <T> T queryForObject(RowMapper<T> mapper, String sql, Object[] args) {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = dbPool.getConnection();
			ps = buildStatement(conn, sql, args);
			rs = ps.executeQuery();

			return rs.next() ? mapper.mapRow(rs) : null;
		} catch (SQLException e) {
			throw new ItasException(e);
		} finally {
			close(rs, ps, conn);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T[] queryForArray(RowMapper<T> mapper, String sql, Object[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = dbPool.getConnection();
			ps = buildStatement(conn, sql, args);
			rs = ps.executeQuery();
			final int maxRow = getRowCount(rs);

			Object[] datas = new Object[maxRow];
			int index = 0;
			while (rs.next()) {
				datas[index++] = mapper.mapRow(rs);
			}

			return (T[]) datas;
		} catch (SQLException e) {
			throw new ItasException(e);
		} finally {
			close(conn, ps, rs);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T[] queryForArray(RowMapper<T> mapper, Called<PreparedStatement> back) 
	throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = dbPool.getConnection();
			ps = back.called(conn);
			rs = ps.executeQuery();
			final int maxRow = getRowCount(rs);

			Object[] datas = new Object[maxRow];
			int index = 0;
			while (rs.next()) {
				datas[index++] = mapper.mapRow(rs);
			}

			return (T[]) datas;
		} catch (SQLException e) {
			throw new ItasException(e);
		} finally {
			close(conn, ps, rs);
		}
	}

	@Override
	public Set<String> tableColumns(String tableName) 
	throws SQLException {
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet rs = null;
		try {
			conn = dbPool.getConnection();
			DatabaseMetaData data = conn.getMetaData();
			ResultSet result = data.getTables(null, "%", "%", new String[] {"TABLE", "VIEW" });
			if (!result.next()) {
				throw new SQLException("unkown database meta info...");
			}

			String dbName = result.getString("TABLE_CAT");

			String sql = "SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_NAME = ? AND TABLE_SCHEMA = ?;";
			ppst = conn.prepareStatement(sql);
			ppst.setString(1, tableName);
			ppst.setString(2, dbName);
			rs = ppst.executeQuery();

			Set<String> columns = new HashSet<String>();
			while (rs.next()) {
				columns.add(rs.getString("column_name"));
			}

			return columns;
		} finally {
			close(conn, ppst, rs);
		}
	}

	private PreparedStatement buildStatement(Connection conn, String sql, Object[] args) 
	throws SQLException {
		sql = expandArrayParams(sql, args);
		PreparedStatement ps = conn.prepareStatement(sql);
		if (args.length > 0) {
			setBindVariable(ps, 1, args);
		}
		
		return ps;
	}

	private PreparedStatement buildStatement(Connection conn, String sql, Object[][] args) 
	throws SQLException {
		sql = expandArrayParams(sql, args);
		PreparedStatement ps = conn.prepareStatement(sql);
		for (Object[] arg : args) {
			setBindVariable(ps, 1, arg);
		}
		
		return ps;
	}

	private int setBindVariable(PreparedStatement statement, int startIndex, Object param) 
	throws SQLException {
		int index = startIndex;
		if (param instanceof Iterable) {
			for (Object o : (Iterable<?>) param) {
				index = setBindVariable(statement, index, o);
			}
			index -= 1;
		} else if (param instanceof Object[]) {
			for (Object o : (Object[]) param) {
				index = setBindVariable(statement, index, o);
			}
			index -= 1;
		} else {
			statement.setObject(index, param);
		}

		return index + 1;
	}

	private String expandArrayParams(String query, Object[] params) {
		if (params.length < 1) {
			return query;
		}

		Pattern p = Pattern.compile("\\?");
		Matcher m = p.matcher(query);
		StringBuffer result = new StringBuffer();
		int i = 0;

		while (m.find()) {
			m.appendReplacement(result, marks(params[i]));
			i += 1;
		}

		m.appendTail(result);
		return result.toString();
	}

	private String marks(Object param) {
		if (param instanceof Iterable) {
			Iterable<?> iter = ((Iterable<?>) param);
			Iterable<String> markIter = Iterables.transform(iter,
					new Function<Object, String>() {
						@Override
						public String apply(Object input) {
							return "?";
						}
					});

			return Joiner.on(',').join(markIter);
		}

		return "?";
	}

	private int getRowCount(ResultSet rs) throws SQLException {
		try {
			rs.last();
			return rs.getRow();
		} finally {
			rs.first();
		}
	}

	public static SQLExecutorImplBulder newBuilder() {
		return new SQLExecutorImplBulder();
	}

	public static class SQLExecutorImplBulder implements Builder {

		private SQLExecutorImplBulder() {
		}

		@Override
		public SQLExecutorImpl builder() {
			return new SQLExecutorImpl();
		}

	}

}