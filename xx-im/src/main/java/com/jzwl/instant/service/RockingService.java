package com.jzwl.instant.service;

import java.util.List;

import com.jzwl.instant.pojo.UserInfo;

/**
 * 群管理
 * 
 * @author Administrator
 * 
 */
public interface RockingService {

	/**
	 * 摇一摇
	 * 
	 * @param mongoService
	 * @return
	 */
	public List<UserInfo> getRockingUserList(String username);
}
