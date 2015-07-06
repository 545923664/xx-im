package com.jzwl.mobile.config.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzwl.base.service.BaseService;
import com.jzwl.mobile.config.dao.DiagramDao;
import com.jzwl.mobile.config.service.DiagramService;

@Service("diagramService")
public class DiagramServiceImpl extends BaseService implements DiagramService {
	
	@Override
	public boolean addAppinfo(Map<String, Object> map) {
		boolean flag = diagramDao.queryAppinfo(map.get("name").toString()); 
		if(!flag){
			return diagramDao.addAppinfo(map);
		}
		return false;
		
	}

	@Override
	public boolean delAppinfo(Map<String, Object> map) {
		return diagramDao.delAppinfo(map);
	}

	@Override
	public boolean updateAppinfo(Map<String, Object> map) {
		return diagramDao.updateAppinfo(map);
	}

	@Autowired
	private DiagramDao diagramDao;//业务DAO

	@Override
	public boolean addDiagramInfo(Map<String, Object> map) {
		boolean flag = diagramDao.queryDiagram(map.get("nodename").toString(),map.get("name").toString()); 
		if(!flag){
			return diagramDao.addDiagramInfo(map);
		}
		return false;
	}

	@Override
	public boolean delDiagramInfo(Map<String, Object> map) {
		return diagramDao.delDiagramInfo(map);
	}

	@Override
	public boolean updateDiagramInfo(Map<String, Object> map) {
		return diagramDao.updateDiagramInfo(map);
	}

	@Override
	public boolean addBannerdata(Map<String, Object> map) {
		boolean flag = diagramDao.queryBannerdata(map.get("newsid").toString()); 
		if(!flag){
			return diagramDao.addBannerdata(map);
		}
		return false;
	}

	@Override
	public boolean delBannerdata(Map<String, Object> map) {
		return diagramDao.delBannerdata(map);
	}
	
	/**
	 * bannerdata 修改轮转图信息
	 * @param map
	 * @return
	 */
	@Override
	public boolean updateBannerdata(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return diagramDao.updateBannerdata(map);
	}

	@Override
	public boolean addShareinfo(Map<String, Object> map) {
		boolean flag = diagramDao.queryShareinfo(map.get("name").toString()); 
		if(!flag){
			return diagramDao.addShareinfo(map);
		}
		return false;
	}

	@Override
	public boolean delShareinfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return diagramDao.delShareinfo(map);
	}

	@Override
	public boolean updateShareinfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return diagramDao.updateShareinfo(map);
	}

	@Override
	public boolean addZxinfo(Map<String, Object> map) {
		boolean flag = diagramDao.queryZxinfo(map.get("name").toString()); 
		if(!flag){
			return diagramDao.addZxinfo(map);
		}
		return false;
	}

	@Override
	public boolean delZxinfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return diagramDao.delZxinfo(map);
	}

	@Override
	public boolean updateZxinfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return diagramDao.updateZxinfo(map);
	}

	@Override
	public boolean addEvaluationinfo(Map<String, Object> map) {
		boolean flag = diagramDao.queryEvaluationinfo(map.get("name").toString()); 
		if(!flag){
			return diagramDao.addEvaluationinfo(map);
		}
		return false;
	}

	@Override
	public boolean delEvaluationinfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return diagramDao.delEvaluationinfo(map);
	}

	@Override
	public boolean updateEvaluationinfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return diagramDao.updateEvaluationinfo(map);
	}

	@Override
	public boolean addCardtype(Map<String, Object> map) {
		boolean flag = diagramDao.queryCardtype(map.get("name").toString()); 
		if(!flag){
			return diagramDao.addCardtype(map);
		}
		return false;
	}

	@Override
	public boolean delCardtype(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return diagramDao.delCardtype(map);
	}

	@Override
	public boolean updateCardtype(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return diagramDao.updateCardtype(map);
	}

	@Override
	public boolean addFreeinfo(Map<String, Object> map) {
		boolean flag = diagramDao.queryFreeinfo(map.get("name").toString()); 
		if(!flag){
			return diagramDao.addFreeinfo(map);
		}
		return false;
	}

	@Override
	public boolean delFreeinfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return diagramDao.delFreeinfo(map);
	}

	@Override
	public boolean updateFreeinfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return diagramDao.updateFreeinfo(map);
	}

	@Override
	public boolean addVersioninfo(Map<String, Object> map) {
		boolean flag = diagramDao.queryVersioninfo(map.get("name").toString()); 
		if(!flag){
			return diagramDao.addVersioninfo(map);
		}
		return false;
	}

	@Override
	public boolean delVersioninfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return diagramDao.delVersioninfo(map);
	}

	@Override
	public boolean updateVersioninfo(Map<String, Object> map) { 
		// TODO Auto-generated method stub
		return diagramDao.updateVersioninfo(map);
	}
	
}
