package com.jzwl.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jzwl.base.dao.BaseDAO;
import com.jzwl.base.service.BaseService;
import com.jzwl.base.service.BaseWriteService;
import com.jzwl.common.page.PageObject;

/**
 * 写service实现类用来提供最基本的数据库事务性写操作，不含任何业务逻辑
 * @author zhang guo yu
 * @version 1.0.0
 * @since 2015-02-01
 * */
@SuppressWarnings("unchecked")
@Service("baseWriteService")
public class BaseWriteServiceImpl extends BaseService implements
		BaseWriteService {

	@Autowired
	private BaseDAO baseDAO;//dao基类接口
	
	/**
	 * 修改指定服务器的ID信息
	 * 
	 * @param args
	 *            执行SQL需要的参数
	 * @return boolean
	 */
	public boolean updateIdInfo(Object[] args){
		String sql = " update idtable set laststr = ? ,newid = ? where ipandport = ? ";
		return baseDAO.executeCommand(sql, args);
	}
	
	/**
	 * 插入指定服务器的ID信息
	 * 
	 * @param args
	 *            执行SQL需要的参数
	 * @return boolean
	 */
	public boolean insertIdInfo(Object[] args){
		String sql = " insert into idtable ( ipandport,laststr,newid ) values(?,?,?) ";
		return baseDAO.executeCommand(sql, args);
	}
	
	/**
	 * 获取指定服务器的ID信息
	 * 
	 * @param args
	 *            执行SQL需要的参数
	 * @return Map
	 */
	public Map queryForIdInfo(String args){
		String sql = " select * from idtable where ipandport = :ipandport ";
		Map map = new HashMap();
		map.put("ipandport", args);
		return baseDAO.queryForMap(sql, map);
	}

	/**
	 * 获取指定页的记录,每页显示的条数使用默认值
	 * 
	 * @param sql
	 *            要执行的sql
	 * @param currentPage
	 *            当前页
	 * @return PageObject
	 */
	public PageObject queryForMPageList(String sql, Object[] args, Map req) {
		return baseDAO.queryForMPageList(sql, args, req);
	}

	/**
	 * 如果cacheName缓存集中的CacheKey有值，返回CacheKey的内容，否则执行SQL查询，返回List。
	 * reWrite为true时强制覆写缓存
	 * 
	 *
	 *
	 * @param CacheKey
	 *            缓存的key值
	 * @param reWrite
	 *            缓存是否强制覆写
	 * @param sql
	 *            执行sql的内容
	 * @param args
	 *            执行sql的参数
	 * @return List类型的数据集
	 */
	public List queryForList(String CacheKey,
			boolean reWrite, String sql, Object[] args) {
		return baseDAO.queryForList(CacheKey, reWrite, sql, args);
	}

	/**
	 * 如果cacheName缓存集中的CacheKey有值，返回CacheKey的内容，否则执行SQL查询，返回List，
	 * reWrite为true时强制覆写缓存
	 * 
	 *
	 *
	 * @param CacheKey
	 *            缓存的key值
	 * @param reWrite
	 *            缓存是否覆写
	 * @param sql
	 *            执行sql的内容
	 * @param map
	 *            执行sql的参数
	 * @return List类型的数据集
	 */
	public List queryForList(String CacheKey,
			boolean reWrite, String sql, Map map) {
		return baseDAO.queryForList(CacheKey, reWrite, sql, map);
	}

	/**
	 * 如果cacheName缓存集中的CacheKey有值，返回CacheKey的内容，否则执行SQL查询，返回List。如果缓存对象中有值，不会覆写
	 * 
	 *
	 *
	 * @param CacheKey
	 *            缓存的key值
	 * @param sql
	 *            执行sql的内容
	 * @param args
	 *            执行sql的参数
	 * @return List类型的数据集
	 */
	public List queryForList(String CacheKey, String sql,
			Object[] args) {
		return baseDAO.queryForList(CacheKey, sql, args);
	}

	/**
	 * 如果cacheName缓存集中的CacheKey有值，返回CacheKey的内容，否则执行SQL查询，返回List。如果缓存对象中有值，不会覆写
	 * 
	 *
	 *
	 * @param CacheKey
	 *            缓存的key值
	 * @param sql
	 *            执行sql的内容
	 * @param map
	 *            执行sql的参数
	 * @return List类型的数据集
	 */
	public List queryForList(String CacheKey, String sql,
			Map map) {
		return baseDAO.queryForList(CacheKey, sql, map);
	}

	/**
	 * 如果cacheName缓存集中的CacheKey有值，返回CacheKey的内容，否则执行SQL查询，返回List。如果缓存对象中有值，不会覆写
	 * 
	 *
	 *
	 * @param CacheKey
	 *            缓存的key值
	 * @param sql
	 *            执行sql的内容
	 * @return List类型的数据集
	 */
	public List queryForList(String CacheKey, String sql) {
		return baseDAO.queryForList(CacheKey, sql);
	}

	/**
	 * 如果cacheName缓存集中的CacheKey有值，返回CacheKey的内容，否则执行SQL查询，返回Map。
	 * reWrite为true时强制覆写缓存
	 * 
	 *
	 *
	 * @param CacheKey
	 *            缓存的key值
	 * @param reWrite
	 *            缓存是否覆写
	 * @param sql
	 *            执行sql的内容
	 * @param args
	 *            执行sql的参数
	 * @return Map类型的数据集
	 */
	public Map queryForMap(String CacheKey, boolean reWrite,
			String sql, Object[] args) {
		return baseDAO.queryForMap(CacheKey, reWrite, sql, args);
	}

	/**
	 * 如果cacheName缓存集中的CacheKey有值，返回CacheKey的内容，否则执行SQL查询，返回Map。
	 * reWrite为true时强制覆写缓存
	 * 
	 *
	 *
	 * @param CacheKey
	 *            缓存的key值
	 * @param reWrite
	 *            缓存是否覆写
	 * @param sql
	 *            执行sql的内容
	 * @param map
	 *            执行sql的参数
	 * @return Map类型的数据集
	 */
	public Map queryForMap(String CacheKey, boolean reWrite,
			String sql, Map map) {
		return baseDAO.queryForMap(CacheKey, reWrite, sql, map);
	}

	/**
	 * 如果cacheName缓存集中的CacheKey有值，返回CacheKey的内容，否则执行SQL查询，返回Map。如果缓存对象中有值，不会覆写
	 * 
	 *
	 *
	 * @param CacheKey
	 *            缓存的key值
	 * @param sql
	 *            执行sql的内容
	 * @param args
	 *            执行sql的参数
	 * @return Map类型的数据集
	 */
	public Map queryForMap(String CacheKey, String sql,
			Object[] args) {
		return baseDAO.queryForMap(CacheKey, sql, args);
	}

	/**
	 * 如果cacheName缓存集中的CacheKey有值，返回CacheKey的内容，否则执行SQL查询，返回Map。如果缓存对象中有值，不会覆写
	 * 
	 *
	 *
	 * @param CacheKey
	 *            缓存的key值
	 * @param sql
	 *            执行sql的内容
	 * @param map
	 *            执行sql的参数
	 * @return Map类型的数据集
	 */
	public Map queryForMap(String CacheKey, String sql,
			Map map) {
		return baseDAO.queryForMap(CacheKey, sql, map);
	}

	/**
	 * 如果cacheName缓存集中的CacheKey有值，返回CacheKey的内容，否则执行SQL查询，返回Map。如果缓存对象中有值，不会覆写
	 * 
	 *
	 *
	 * @param CacheKey
	 *            缓存的key值
	 * @param sql
	 *            执行sql的内容
	 * @param args
	 *            执行sql的参数
	 * @return Map类型的数据集
	 */
	public Map queryForMap(String CacheKey, String sql) {
		return baseDAO.queryForMap(CacheKey, sql);
	}

//	/**
//	 * 插入一条记录,并且返回当前主键
//	 * 
//	 * @param sql
//	 *            执行sql的内容
//	 * @return
//	 */
//	public long getJdbcSqlServerInsertBackId(String sql) {
//		return baseDAO.getJdbcSqlServerInsertBackId(sql);
//	}
//
//	/**
//	 * 插入一条记录,并且返回当前主键
//	 * 
//	 * @param sql
//	 *            执行sql的内容
//	 * @return
//	 */
//	public long getJdbcSqlServerInsertBackId(String sql, Object[] args) {
//		return baseDAO.getJdbcSqlServerInsertBackId(sql, args);
//	}
//
//	/**
//	 * 插入一条记录,并且返回当前主键
//	 * 
//	 * @param sql
//	 *            执行sql的内容
//	 * @return
//	 */
//	public long getJdbcSqlServerInsertBackId(String sql, Map paramMap) {
//		return baseDAO.getJdbcSqlServerInsertBackId(sql, paramMap);
//	}

	/**
	 * 获取指定页的记录
	 * 
	 * @param sql
	 *            要执行的sql
	 * @param args
	 *            要执行sql的参数
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条记录
	 * @return PageObject
	 */
	public PageObject queryForPageList(String sql, Object[] args,
			int currentPage, int pageSize) {
		return baseDAO.queryForPageList(sql, args, currentPage, pageSize);
	}

	/**
	 * 查询单个记录
	 * 
	 * @param sql
	 *            执行sql的内容
	 * @param args
	 *            执行sql的参数
	 * @return
	 */
	public Map queryForMap(String sql, Object[] args) {
		return baseDAO.queryForMap(sql, args);
	}

	/**
	 * 查询单个记录
	 * 
	 * @param sql
	 *            执行sql的内容
	 * @return
	 */
	public Map queryForMap(String sql) {
		return baseDAO.queryForMap(sql);
	}
	
	/**
	 * 查询一组记录
	 * @param sql
	 *            执行sql的内容
	 * @return
	 */
	public List queryForList(String sql){
		return baseDAO.queryForList(sql);
	}

	/**
	 * 查询单个记录
	 * 
	 * @param sql
	 *            执行sql的内容
	 * @param map
	 *            执行sql的参数
	 * @return
	 */
	public Map queryForMap(String sql, Map map) {
		return baseDAO.queryForMap(sql, map);
	}

	/**
	 * 获取指定页的记录,每页显示的条数使用默认值
	 * 
	 * @param sql
	 *            要执行的sql
	 * @param map
	 *            要执行sql的参数
	 * @param currentPage
	 *            当前页
	 * @return PageObject
	 */
	public PageObject queryForNamedPageList(String sql, Map map, int currentPage) {
		return baseDAO.queryForNamedPageList(sql, map, currentPage);
	}

	/**
	 * 获取指定页的记录,每页显示的条数使用默认值
	 * 
	 * @param sql
	 *            要执行的sql
	 * @param currentPage
	 *            当前页
	 * @return PageObject
	 */
	public PageObject queryForPageList(String sql, int currentPage) {
		return baseDAO.queryForPageList(sql, currentPage);
	}

	/**
	 * 获取指定页的记录,每页显示的条数使用设定值
	 * 
	 * @param sql
	 *            要执行的sql
	 * @param map
	 *            执行参数
	 * @param currentPage
	 *            当前页
	 * @param pageSize
	 *            每页显示的条数
	 * @return PageObject
	 */
	public PageObject queryForNamedPageList(String sql, Map map,
			int currentPage, int pageSize) {
		return baseDAO.queryForNamedPageList(sql, map, currentPage, pageSize);
	}

	/**
	 * 获取指定页的记录,每页显示的条数使用默认值
	 * 
	 * @param sql
	 *            要执行的sql
	 * @param map
	 *            要执行sql的参数
	 * @param currentPage
	 *            当前页
	 * @return PageObject
	 */
	public PageObject queryForPageList(String sql, Object[] args,
			int currentPage) {
		return baseDAO.queryForPageList(sql, args, currentPage);
	}

	/**
	 * 执行SQL，获取查询到的所有记录
	 * 
	 * @param sql
	 *            执行sql的内容
	 * @param map
	 *            执行sql的参数
	 * @return
	 */
	public List getNamedRecordSet(String sql, Map map)
			throws DataAccessException {
		return baseDAO.getNamedRecordSet(sql, map);
	}

	/**
	 * 执行SQL，获取查询到的第1行记录
	 * 
	 * @param sql
	 *            执行sql的内容
	 * @param map
	 *            执行sql的参数
	 * @return
	 */
	public Map getNamedFirstRowValue(String sql, Map map)
			throws DataAccessException {
		return baseDAO.getNamedFirstRowValue(sql, map);
	}

	/**
	 * 执行SQL，执行insert,update,delete操作
	 * 
	 * @param sql
	 *            执行sql的内容
	 * @param map
	 *            执行sql的参数
	 * @return
	 */
	public boolean executeNamedCommand(String sql, Map map)
			throws DataAccessException {
		return baseDAO.executeNamedCommand(sql, map);
	}

	/**
	 * 执行SQL，获取查询到的记录总数
	 * 
	 * @param sql
	 *            执行sql的内容
	 * @param map
	 *            执行sql的参数
	 * @return
	 */
	public long getNamedRecordCount(String sql, Map map)
			throws DataAccessException {
		return baseDAO.getNamedRecordCount(sql, map);
	}

	/**
	 * 执行SQL，查询是否存在记录
	 * 
	 * @param sql
	 *            执行sql的内容
	 * @param map
	 *            执行sql的参数
	 * @return
	 */
	public boolean findNamedSQL(String sql, Map map) throws DataAccessException {
		return baseDAO.findNamedSQL(sql, map);
	}

	/**
	 * 执行SQL，获取查询到的所有记录
	 * 
	 * @param sql
	 *            执行sql的内容
	 * @param args
	 *            执行sql的参数
	 * @return
	 */
	public List getRecordSet(String sql, Object[] args)
			throws DataAccessException {
		return baseDAO.getRecordSet(sql, args);
	}

	/**
	 * 执行SQL，获取查询到的第1行记录
	 * 
	 * @param sql
	 *            执行sql的内容
	 * @param args
	 *            执行sql的参数
	 * @return
	 */
	public Map getFirstRowValue(String sql, Object[] args)
			throws DataAccessException {
		return baseDAO.getFirstRowValue(sql, args);
	}

	/**
	 * 执行SQL，执行insert,update,delete操作
	 * 
	 * @param sql
	 *            执行sql的内容
	 * @param args
	 *            执行sql的参数
	 * @return
	 */
	public boolean executeCommand(String sql, Object[] args)
			throws DataAccessException {
		return baseDAO.executeCommand(sql, args);
	}

	/**
	 * 执行SQL，获取查询到的记录总数
	 * 
	 * @param sql
	 *            执行sql的内容
	 * @param args
	 *            执行sql的参数
	 * @return
	 */
	public long getRecordCount(String sql, Object[] args)
			throws DataAccessException {
		return baseDAO.getRecordCount(sql, args);
	}

	/**
	 * 执行SQL，查询是否存在记录
	 * 
	 * @param sql
	 *            执行sql的内容
	 * @param args
	 *            执行sql的参数
	 * @return
	 */
	public boolean findSQL(String sql, Object[] args)
			throws DataAccessException {
		return baseDAO.findSQL(sql, args);
	}
	
	/**
	 * 执行SQL，获取查询到的记录总数
	 * @param sql
	 *            要执行的sql
	 * @return
	 */
	public long getRecordCount(String sql) throws DataAccessException{
		return baseDAO.getRecordCount(sql);
	}
}
