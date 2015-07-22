package com.jzwl.instant.service;

import java.util.List;

import com.jzwl.instant.pojo.UserInfo;

/**
 * 位置管理
 * 
 * @author Administrator
 * 
 */
public interface LocationService {

	/**
	 * 附件的人
	 * 
	 * @param mongoService
	 * @return
	 */
	public List<UserInfo> getNearUserList(String username, double lng,
			double lat);

	/**
	 * 计算距离
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public double getDistance(double lng1, double lat1, double lng2, double lat2);
}
