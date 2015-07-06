package com.jzwl.mobile.config.dao;

import java.util.Map;

public interface DiagramDao {
	/**
	 * 添加启动图信息
	 * @param map
	 * @return
	 */
	public boolean addDiagramInfo(Map<String,Object> map);
	
	public boolean delDiagramInfo(Map<String,Object> map);
	
	public boolean updateDiagramInfo(Map<String,Object> map);
	
	public boolean queryDiagram(String nodename,String name);
	
	public boolean addBannerdata(Map<String,Object> map);
	
	public boolean delBannerdata(Map<String,Object> map);
	
	public boolean updateBannerdata(Map<String,Object> map);
	
	public boolean queryBannerdata(String name);
	
	public boolean addAppinfo(Map<String,Object> map);
	
	public boolean delAppinfo(Map<String,Object> map);
	
	public boolean updateAppinfo(Map<String,Object> map);
	
	public boolean queryAppinfo(String name);
	
	public boolean addShareinfo(Map<String,Object> map);
	
	public boolean delShareinfo(Map<String,Object> map);
	
	public boolean queryShareinfo(String name);
	
	public boolean updateShareinfo(Map<String,Object> map);
	
	public boolean addZxinfo(Map<String,Object> map);
	
	public boolean delZxinfo(Map<String, Object> map);
	
	public boolean updateZxinfo(Map<String,Object> map);
	
	public boolean queryZxinfo(String name);
	
	public boolean addEvaluationinfo(Map<String, Object> map);
	
	public boolean delEvaluationinfo(Map<String, Object> map);
	
	public boolean updateEvaluationinfo(Map<String, Object> map);
	
	public boolean queryEvaluationinfo(String name);
	
	public boolean addCardtype(Map<String, Object> map);
	
	public boolean delCardtype(Map<String, Object> map);
	
	public boolean updateCardtype(Map<String, Object> map);
	
	public boolean queryCardtype(String name);
	
	public boolean addFreeinfo(Map<String, Object> map);
	
	public boolean delFreeinfo(Map<String, Object> map);
	
	public boolean updateFreeinfo(Map<String, Object> map);
	
	public boolean queryFreeinfo(String name);
	
	public boolean addVersioninfo(Map<String, Object> map);
	
	public boolean delVersioninfo(Map<String, Object> map);
	
	public boolean updateVersioninfo(Map<String, Object> map);
	
	public boolean queryVersioninfo(String name);
}
