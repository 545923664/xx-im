package com.jzwl.mobile.config.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwl.base.dao.BaseDAO;
import com.jzwl.mobile.config.dao.DiagramDao;
@Repository("diagramDao")
public class DiagramDaoImpl implements DiagramDao {
	
	@Autowired
	private BaseDAO baseDAO;//dao基类，操作数据库
	
	@Override
	public boolean addDiagramInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String sql="insert into activecardinfo(zhname,nodename,name,value) value(?,?,?,?)";
		Object[] args={map.get("zhname"),map.get("nodename"),map.get("name"),map.get("value")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean updateBannerdata(Map<String, Object> map) {
		String sql = "update bannerdata set newsid=?,picurl=?,catid=?,catname=? where id=?";
		Object[] args={map.get("newsid"),map.get("picurl"),map.get("catid"),map.get("catname"),map.get("id")};
		
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean delDiagramInfo(Map<String, Object> map) {
		String sql="delete from activecardinfo where id=?";
		Object[] args={map.get("id")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean addAppinfo(Map<String, Object> map) {
		String sql="insert into appinfo(name,value) value(?,?)";
		Object[] args={map.get("name"),map.get("value")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean updateAppinfo(Map<String, Object> map) {
		String sql = "update appinfo set name=?,value=? where id=?";
		Object[] args={map.get("name"),map.get("value"),map.get("id")};
		
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean delAppinfo(Map<String, Object> map) {
		String sql="delete from appinfo where id=?";
		Object[] args={map.get("id")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean updateDiagramInfo(Map<String, Object> map) {
		String sql = "update activecardinfo set zhname=?,nodename=?,name=?,value=? where id=?";
		Object[] args={map.get("zhname"),map.get("nodename"),map.get("name"),map.get("value"),map.get("id")};
		
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean addBannerdata(Map<String, Object> map) {
		
		String sql="insert into bannerdata(newsid,picurl,catid,catname) value(?,?,?,?)";
		Object[] args={map.get("newsid"),map.get("picurl"),map.get("catid"),map.get("catname")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean delBannerdata(Map<String, Object> map) {
		String sql="delete from bannerdata where id=?";
		Object[] args={map.get("id")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean addShareinfo(Map<String, Object> map) {
		String sql="insert into shareinfo(name,value) value(?,?)";
		Object[] args={map.get("name"),map.get("value")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean delShareinfo(Map<String, Object> map) {
		String sql="delete from shareinfo where id=?";
		Object[] args={map.get("id")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean updateShareinfo(Map<String, Object> map) {
		String sql = "update shareinfo set name=?,value=? where id=?";
		Object[] args={map.get("name"),map.get("value"),map.get("id")};
		
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean addZxinfo(Map<String, Object> map) {
		String sql="insert into zxinfo(name,value) value(?,?)";
		Object[] args={map.get("name"),map.get("value")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean delZxinfo(Map<String, Object> map) {
		String sql="delete from zxinfo where id=?";
		Object[] args={map.get("id")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean updateZxinfo(Map<String, Object> map) {
		String sql = "update zxinfo set name=?,value=? where id=?";
		Object[] args={map.get("name"),map.get("value"),map.get("id")};
		
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean addEvaluationinfo(Map<String, Object> map) {
		String sql="insert into evaluationinfo(name,value) value(?,?)";
		Object[] args={map.get("name"),map.get("value")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean delEvaluationinfo(Map<String, Object> map) {
		String sql="delete from evaluationinfo where id=?";
		Object[] args={map.get("id")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean updateEvaluationinfo(Map<String, Object> map) {
		String sql = "update evaluationinfo set name=?,value=? where id=?";
		Object[] args={map.get("name"),map.get("value"),map.get("id")};
		
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean addCardtype(Map<String, Object> map) {
		String sql="insert into cardType(name,value) value(?,?)";
		Object[] args={map.get("name"),map.get("value")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean delCardtype(Map<String, Object> map) {
		String sql="delete from cardType where id=?";
		Object[] args={map.get("id")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean updateCardtype(Map<String, Object> map) {
		String sql = "update cardType set name=?,value=? where id=?";
		Object[] args={map.get("name"),map.get("value"),map.get("id")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean addFreeinfo(Map<String, Object> map) {
		String sql="insert into freeinfo(name,value) value(?,?)";
		Object[] args={map.get("name"),map.get("value")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean delFreeinfo(Map<String, Object> map) {
		String sql="delete from freeinfo where id=?";
		Object[] args={map.get("id")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean updateFreeinfo(Map<String, Object> map) {
		String sql = "update freeinfo set name=?,value=? where id=?";
		Object[] args={map.get("name"),map.get("value"),map.get("id")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean addVersioninfo(Map<String, Object> map) {
		String sql="insert into versioninfo(name,value) value(?,?)";
		Object[] args={map.get("name"),map.get("value")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean delVersioninfo(Map<String, Object> map) {
		String sql="delete from versioninfo where id=?";
		Object[] args={map.get("id")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean updateVersioninfo(Map<String, Object> map) {
		String sql = "update versioninfo set name=?,value=? where id=?";
		Object[] args={map.get("name"),map.get("value"),map.get("id")};
		return baseDAO.executeCommand(sql, args);
	}

	@Override
	public boolean queryDiagram(String nodename,String name) {
		String sql = "select id,zhname,nodename,name,value from activecardinfo where nodename=? and name=?";
		Object[] args = {nodename,name};
		long l = baseDAO.getRecordCount(sql, args);
		if(l>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean queryBannerdata(String name) {
		String sql = "select id,newsid,picurl,catid,catname from bannerdata where newsid=?";
		Object[] args = {name};
		long l = baseDAO.getRecordCount(sql, args);
		if(l>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean queryAppinfo(String name) {
		String sql = "select id,name,value from appinfo where name=?";
		Object[] args = {name};
		long l = baseDAO.getRecordCount(sql, args);
		if(l>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean queryShareinfo(String name) {
		String sql = "select id,name,value from shareinfo where name=?";
		Object[] args = {name};
		long l = baseDAO.getRecordCount(sql, args);
		if(l>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean queryZxinfo(String name) {
		String sql = "select id,name,value from zxinfo where name=?";
		Object[] args = {name};
		long l = baseDAO.getRecordCount(sql, args);
		if(l>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean queryEvaluationinfo(String name) {
		String sql = "select id,name,value from evaluationinfo where name=?";
		Object[] args = {name};
		long l = baseDAO.getRecordCount(sql, args);
		if(l>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean queryCardtype(String name) {
		String sql = "select id,name,value from cardtype where name=?";
		Object[] args = {name};
		long l = baseDAO.getRecordCount(sql, args);
		if(l>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean queryFreeinfo(String name) {
		String sql = "select id,name,value from freeinfo where name=?";
		Object[] args = {name};
		long l = baseDAO.getRecordCount(sql, args);
		if(l>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean queryVersioninfo(String name) {
		String sql = "select id,name,value from versioninfo where name=?";
		Object[] args = {name};
		long l = baseDAO.getRecordCount(sql, args);
		if(l>0){
			return true;
		}
		return false;
	}

}
