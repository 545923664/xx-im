package com.jzwl.base.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.jzwl.base.dao.BaseDAO;
import com.jzwl.common.constant.GlobalConstant;
import com.jzwl.common.log.SystemLog;
import com.jzwl.common.page.PageObject;

/**
 * dao接口oracle实现，承担所有公共DAO的API实现
 * @author zhang guo yu
 * @version 1.0.0
 * @since 2015-02-01
 * */
@SuppressWarnings("all")
public class BaseOracleDAOImpl extends NamedParameterJdbcDaoSupport implements
		BaseDAO {

	// @Autowired
	// private CacheUtil cacheUtil;//缓存工具类

	@Autowired
	private SystemLog systemLog;//日志工具类

	@Override
	public PageObject queryForMPageList(String sql, Object[] args, Map req) {
		int totalCount = 1;
		int start = NumberUtils.toInt(ObjectUtils.toString(req.get("start"),
				"1"));
		int limit = NumberUtils.toInt(ObjectUtils.toString(req.get("limit"),
				GlobalConstant.Global_PAGESIZE + ""));
		String sort = ObjectUtils.toString(req.get("sort"));
		String dir = ObjectUtils.toString(req.get("dir"));

		StringBuffer buildsql = new StringBuffer();
		buildsql.append("SELECT * FROM (");
		buildsql.append("	SELECT ROWNUM TEMP_NUM,TEMP.* FROM (");
		if (sort != null && !"".equals(sort)) {
			String pa = "ORDER BY (,|\\.|\\w| )+( ASC| DESC)?( NULLS LAST)?( NULLS FIRST)?\\S?$";
			String re = " ORDER BY " + sort + " " + dir;
			Matcher matcher = Pattern.compile(pa, Pattern.CASE_INSENSITIVE)
					.matcher(sql);
			if (matcher.find()) {
				buildsql.append(matcher.replaceFirst(re));
			}
		} else {
			buildsql.append(sql);
		}
		buildsql.append("	) TEMP WHERE ROWNUM<="
				+ String.valueOf(start + limit));
		buildsql.append(") WHERE TEMP_NUM>" + String.valueOf(start));

		systemLog.debugLog(getClass(), buildsql.toString() + " : args is "
				+ StringUtils.join(args, ","));
		
		PageObject page = new PageObject();
		try {
			List list = super.getJdbcTemplate().queryForList(buildsql.toString(), args);

			String countsql = "SELECT COUNT(*) FROM (" + sql + ")";
			totalCount = super.getJdbcTemplate().queryForInt(countsql, args);

			page.setDatasource(list);
			page.setTotalCount(totalCount);

			int _absolutePage = (int) Math.ceil(totalCount * 1.0 / limit);
			page.setAbsolutePage(_absolutePage);

			int _currentPage = start / limit + 1;
			page.setCurrentPage(_currentPage);
		} catch (Exception e) {
			systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
		}

		return page;
	}

	@Override
	public List queryForList(String CacheKey,
			boolean reWrite, String sql, Object[] args) {
		systemLog.debugLog(getClass(),
				sql + " : args is " + StringUtils.join(args, ","));
		List list =null;// cacheUtil.get(CacheKey, List.class);
		if (reWrite || list == null) {

			try {
				list = super.getJdbcTemplate().queryForList(sql, args);
//				cacheUtil.put(CacheKey, list);
			} catch (Exception e) {
				systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
			}
		}
		return list;
	}

	@Override
	public List queryForList(String CacheKey,
			boolean reWrite, String sql, Map map) {
		systemLog.debugLog(getClass(),
				sql.toString() + " : args is " + map.toString());
		List list = null;//cacheUtil.get(CacheKey, List.class);
		if (reWrite || list == null) {
			try {
				list = super.getNamedParameterJdbcTemplate().queryForList(sql,
						map);
//				cacheUtil.put(CacheKey, list);
			} catch (Exception e) {
				systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
			}
		}
		return list;
	}

	@Override
	public List queryForList(String CacheKey, String sql,
			Object[] args) {
		return queryForList(CacheKey, false, sql, args);
	}

	@Override
	public List queryForList(String CacheKey, String sql,
			Map map) {
		return queryForList(CacheKey, false, sql, map);
	}

	@Override
	public List queryForList(String CacheKey, String sql) {
		systemLog.debugLog(getClass(), sql + " : args is no value");
		List list =null;// cacheUtil.get(CacheKey, List.class);
		if (list == null) {
			try {
				list = super.getJdbcTemplate().queryForList(sql);
//				cacheUtil.put(CacheKey, list);
			} catch (Exception e) {
				systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
			}
		}
		return list;
	}

	@Override
	public Map queryForMap(String CacheKey, boolean reWrite,
			String sql, Object[] args) {
		systemLog.debugLog(getClass(), sql.toString() + " : args is "
				+ StringUtils.join(args, ","));
		Map map = null;//cacheUtil.get(CacheKey, Map.class);
		if (reWrite || map == null) {
			try {
				map = super.getJdbcTemplate().queryForMap(sql, args);
//				cacheUtil.put(CacheKey, map);
			} catch (IncorrectResultSizeDataAccessException e) {
				systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
				map = null;
			}
		}
		return map;
	}

	@Override
	public Map queryForMap(String CacheKey, boolean reWrite,
			String sql, Map args) {
		systemLog.debugLog(getClass(), sql.toString() + " : args is "
				+ args.toString());
		Map map = null;//cacheUtil.get(CacheKey, Map.class);
		if (reWrite || map == null) {
			try {
				map = super.getNamedParameterJdbcTemplate().queryForMap(sql,
						args);
//				cacheUtil.put(CacheKey, map);
			} catch (IncorrectResultSizeDataAccessException e) {
				systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
				map = null;
			}
		}
		return map;
	}

	@Override
	public Map queryForMap(String CacheKey, String sql,
			Object[] args) {
		return queryForMap(CacheKey, false, sql, args);
	}

	@Override
	public Map queryForMap(String CacheKey, String sql,
			Map map) {
		return queryForMap(CacheKey, false, sql, map);
	}

	@Override
	public Map queryForMap(String CacheKey, String sql) {
		systemLog.debugLog(getClass(), sql.toString()
				+ " : args is no value");
		Map map = null;//cacheUtil.get(CacheKey, Map.class);
		if (map == null) {
			try {
				map = super.getJdbcTemplate().queryForMap(sql);
//				cacheUtil.put(CacheKey, map);
			} catch (IncorrectResultSizeDataAccessException e) {
				systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
				map = null;
			}
		}
		return map;
	}

//	@Override
//	public long getJdbcSqlServerInsertBackId(final String sql) {
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		getJdbcTemplate().update(new PreparedStatementCreator() {
//			public PreparedStatement createPreparedStatement(Connection conn)
//					throws SQLException {
//				PreparedStatement ps = conn.prepareStatement(sql,
//						Statement.RETURN_GENERATED_KEYS);
//				return ps;
//			}
//		}, keyHolder);
//		return keyHolder.getKey().intValue();
//	}
//
//	@Override
//	public long getJdbcSqlServerInsertBackId(final String sql,
//			final Object[] args) {
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		getJdbcTemplate().update(new PreparedStatementCreator() {
//
//			public PreparedStatement createPreparedStatement(Connection conn)
//					throws SQLException {
//				PreparedStatement ps = conn.prepareStatement(sql,
//						Statement.RETURN_GENERATED_KEYS);
//				for (int i = 0; i < args.length; i++) {
//					ps.setObject(i + 1, args[i]);
//				}
//				return ps;
//			}
//		}, keyHolder);
//		return keyHolder.getKey().intValue();
//	}
//
//	@Override
//	public long getJdbcSqlServerInsertBackId(String sql, Map paramMap) {
//		Connection con = null;
//		PreparedStatement st = null;
//		ResultSet res = null;
//		StringBuffer innserSql = new StringBuffer();
//		innserSql.append("SELECT LAST_INSERT_ID() ID ");
//		try {
//			con = getJdbcTemplate().getDataSource().getConnection();
//			con.setAutoCommit(false);
//			super.getNamedParameterJdbcTemplate().update(sql.toString(),
//					paramMap);
//			st = con.prepareStatement(innserSql.toString());
//			res = st.executeQuery();
//			con.commit();
//			if (res.next()) {
//				return res.getInt("ID");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			try {
//				con.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//		} finally {
//			try {
//				con.setAutoCommit(true);
//				res.close();
//				st.close();
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return 0;
//	}

	@Override
	public PageObject queryForPageList(String sql, Object[] args,
			int currentPage, int pageSize) {
		int totalCount = 1;
		int absolutePage = 1;

		int endIndex = pageSize * currentPage;
		int startIndex = pageSize * (currentPage - 1);

		StringBuffer buildsql = new StringBuffer();
		if (currentPage == -1) {
			buildsql.append(sql);
		} else {
			buildsql.append("select * from (");
			buildsql.append("	select rownum temp_num,temp.* from (");
			buildsql.append(sql);
			buildsql.append("	) temp where rownum<=" + endIndex);
			buildsql.append(") where temp_num>" + startIndex);
		}

		systemLog.debugLog(getClass(), buildsql.toString() + " : args is "
				+ StringUtils.join(args, ","));

		List list = super.getJdbcTemplate().queryForList(buildsql.toString(),args);

		String countsql = "select count(*) from (" + sql + ")";
		totalCount = super.getJdbcTemplate().queryForInt(countsql, args);

		if (totalCount == 0)
			absolutePage = 1;
		else
			absolutePage = (int) Math.ceil(totalCount / (pageSize * 1.0));

		PageObject page = new PageObject();
		page.setDatasource(list);
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		page.setTotalCount(totalCount);
		page.setAbsolutePage(absolutePage);
		return page;
	}

	@Override
	public Map queryForMap(String sql, Object[] args) {
		systemLog.debugLog(getClass(),
				sql + " : args is " + StringUtils.join(args, ","));
		List list = super.getJdbcTemplate().queryForList(sql, args);
		if (list != null && list.size() > 0) {
			return (Map) list.get(0);
		}
		return new HashMap();
	}

	@Override
	public Map queryForMap(String sql) {
		systemLog.debugLog(getClass(), sql + " : args is no value");
		List list = super.getJdbcTemplate().queryForList(sql);
		if (list != null && list.size() > 0) {
			return (Map) list.get(0);
		}
		return new HashMap();
	}

	@Override
	public Map queryForMap(String sql, Map map) {
		systemLog.debugLog(getClass(), sql + " : args is " + map.toString());
		List list = super.getNamedParameterJdbcTemplate()
				.queryForList(sql, map);
		if (list != null && list.size() > 0) {
			return (Map) list.get(0);
		}
		return new HashMap();
	}

	@Override
	public PageObject queryForNamedPageList(String sql, Map map, int currentPage) {
		int pageSize = GlobalConstant.Global_PAGESIZE;
		return queryForNamedPageList(sql, map, currentPage, pageSize);
	}

	@Override
	public PageObject queryForPageList(String sql, int currentPage) {
		int pageSize = GlobalConstant.Global_PAGESIZE;
		return queryForPageList(sql, null, currentPage, pageSize);
	}

	@Override
	public PageObject queryForNamedPageList(String sql, Map map,
			int currentPage, int pageSize) {
		int totalCount = 1;
		int absolutePage = 1;

		int endIndex = pageSize * currentPage;
		int startIndex = pageSize * (currentPage - 1);

		StringBuffer buildsql = new StringBuffer();
		if (currentPage == -1) {
			buildsql.append(sql);
		} else {
			buildsql.append("select * from (");
			buildsql.append("	select rownum temp_num,temp.* from (");
			buildsql.append(sql);
			buildsql.append("	) temp where rownum<=" + endIndex);
			buildsql.append(") where temp_num>" + startIndex);
		}
		
		systemLog.debugLog(getClass(), sql + " : args is " + map.toString());

		List list = super.getNamedParameterJdbcTemplate().queryForList(buildsql.toString(), map);

		String countsql = "select count(*) from (" + sql + ")";
		totalCount = super.getNamedParameterJdbcTemplate().queryForInt(countsql, map);

		if (totalCount == 0)
			absolutePage = 1;
		else
			absolutePage = (int) Math.ceil(totalCount / (pageSize * 1.0));

		PageObject page = new PageObject();
		page.setDatasource(list);
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		page.setTotalCount(totalCount);
		page.setAbsolutePage(absolutePage);
		return page;
	}

	@Override
	public PageObject queryForPageList(String sql, Object[] args,
			int currentPage) {
		int pageSize = GlobalConstant.Global_PAGESIZE;
		return queryForPageList(sql, args, currentPage, pageSize);
	}

	@Override
	public List getNamedRecordSet(String sql, Map map)
			throws DataAccessException {
		List returnList = null;
		systemLog.debugLog(getClass(), sql + " : args is " + map.toString());
		try {
			returnList = getNamedParameterJdbcTemplate().queryForList(sql, map);
		} catch (Exception e) {
			systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
		}
		return returnList;
	}

	@Override
	public Map getNamedFirstRowValue(String sql, Map map)
			throws DataAccessException {
		Map returnMap = null;
		systemLog.debugLog(getClass(), sql + " : args is " + map.toString());
		try {
			List list = getNamedParameterJdbcTemplate().queryForList(sql, map);
			if (list.size() > 0) {
				returnMap = (Map) list.get(0);
			}
		} catch (Exception e) {
			systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
		}
		return returnMap;
	}

	@Override
	public boolean executeNamedCommand(String sql, Map map)
			throws DataAccessException {
		boolean returnValue = false;
		systemLog.debugLog(getClass(), sql + " : args is " + map.toString());
		try {
			getNamedParameterJdbcTemplate().update(sql, map);
			returnValue = true;
		} catch (DataAccessException e) {
			systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
			throw e;
		}
		return returnValue;
	}

	@Override
	public long getNamedRecordCount(String sql, Map map)
			throws DataAccessException {
		int returnValue = 0;
		systemLog.debugLog(getClass(), sql + " : args is " + map.toString());
		try {
			List list = getNamedParameterJdbcTemplate().queryForList(sql, map);
			returnValue = list.size();
		} catch (DataAccessException e) {
			systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
			throw e;
		}
		return returnValue;
	}

	@Override
	public boolean findNamedSQL(String sql, Map map) throws DataAccessException {
		return getNamedRecordCount(sql, map) > 0;
	}

	@Override
	public List getRecordSet(String sql, Object[] args)
			throws DataAccessException {
		List returnList = null;
		systemLog.debugLog(getClass(),
				sql + " : args is " + StringUtils.join(args, ","));
		try {
			returnList = getJdbcTemplate().queryForList(sql, args);
		} catch (DataAccessException e) {
			systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
			throw e;
		}
		return returnList;
	}

	@Override
	public Map getFirstRowValue(String sql, Object[] args)
			throws DataAccessException {
		Map returnMap = null;
		systemLog.debugLog(getClass(),
				sql + " : args is " + StringUtils.join(args, ","));
		try {
			List list = getJdbcTemplate().queryForList(sql, args);
			if (list.size() > 0) {
				returnMap = (Map) list.get(0);
			}
		} catch (DataAccessException e) {
			systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
			throw e;
		}
		return returnMap;
	}

	@Override
	public boolean executeCommand(String sql, Object[] args)
			throws DataAccessException {
		boolean returnValue = false;
		systemLog.debugLog(getClass(),
				sql + " : args is " + StringUtils.join(args, ","));
		try {
			getJdbcTemplate().update(sql, args);
			returnValue = true;
		} catch (DataAccessException e) {
			systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
			throw e;
		}
		return returnValue;
	}

	@Override
	public long getRecordCount(String sql, Object[] args)
			throws DataAccessException {
		long returnValue = 0;
		systemLog.debugLog(getClass(),
				sql + " : args is " + StringUtils.join(args, ","));
		try {
			List list = getJdbcTemplate().queryForList(sql, args);
			returnValue = list.size();
		} catch (DataAccessException e) {
			systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
			throw e;
		}
		return returnValue;
	}

	@Override
	public boolean findSQL(String sql, Object[] args)
			throws DataAccessException {
		return getRecordCount(sql, args) > 0;
	}

	@Override
	public List queryForList(String sql) {
		systemLog.debugLog(getClass(), sql + " : args is no value");
		List list = super.getJdbcTemplate().queryForList(sql);
		if (list != null && list.size() > 0) {
			return list;
		}
		return new ArrayList();
	}

	@Override
	public long getRecordCount(String sql) throws DataAccessException {
		long returnValue = 0;
		systemLog.debugLog(getClass(), sql + " : args is no value");
		try {
			List list = getJdbcTemplate().queryForList(sql);
			returnValue = list.size();
		} catch (DataAccessException e) {
			systemLog.errorLog(getClass(), "--", "--", "--", "--", "--", e);
			throw e;
		}
		return returnValue;
	}

}
