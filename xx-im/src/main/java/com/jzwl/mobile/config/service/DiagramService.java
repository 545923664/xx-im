package com.jzwl.mobile.config.service;

import java.util.Map;

public interface DiagramService {
	
	/**
	 * 添加启动图信息
	 * @param map
	 * @return
	 */
	public boolean addDiagramInfo(Map<String,Object> map);
	
	/**
	 * 删除启动图信息
	 * @param map
	 * @return
	 */
	public boolean delDiagramInfo(Map<String,Object> map);
	
	/**
	 * 修改启动图信息
	 * @param map
	 * @return
	 */
	public boolean updateDiagramInfo(Map<String,Object> map);
	
	/**
	 * 添加 轮转图
	 * @param map
	 * @return
	 */
	public boolean addBannerdata(Map<String,Object> map);
	
	/**
	 * 删除轮转图
	 * @param map
	 * @return
	 */
	public boolean delBannerdata(Map<String,Object> map);
	
	public boolean updateBannerdata(Map<String,Object> map);
	
	public boolean addAppinfo(Map<String,Object> map);
	
	public boolean delAppinfo(Map<String,Object> map);
	
	public boolean updateAppinfo(Map<String,Object> map);
	
	public boolean addShareinfo(Map<String,Object> map);
	
	public boolean delShareinfo(Map<String,Object> map);
	
	public boolean updateShareinfo(Map<String,Object> map);
	
	public boolean addZxinfo(Map<String,Object> map);
	
	public boolean delZxinfo(Map<String, Object> map);
	
	public boolean updateZxinfo(Map<String,Object> map);
	
	public boolean addEvaluationinfo(Map<String,Object> map);
	
	public boolean delEvaluationinfo(Map<String, Object> map);
	
	public boolean updateEvaluationinfo(Map<String,Object> map);
	
	public boolean addCardtype(Map<String, Object> map);
	
	public boolean delCardtype(Map<String, Object> map);
	
	public boolean updateCardtype(Map<String,Object> map);
	
	public boolean addFreeinfo(Map<String, Object> map);
	
	public boolean delFreeinfo(Map<String,Object> map);
	
	public boolean updateFreeinfo(Map<String, Object> map);
	
	public boolean addVersioninfo(Map<String, Object> map);
	
	public boolean delVersioninfo(Map<String, Object> map);
	
	public boolean updateVersioninfo(Map<String, Object> map);
}
