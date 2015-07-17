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
}
