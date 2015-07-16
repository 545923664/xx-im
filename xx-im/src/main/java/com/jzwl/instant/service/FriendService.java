package com.jzwl.instant.service;

import java.util.Set;

public interface FriendService {

	/**
	 * 验证是否是好友
	 * 
	 * @param username
	 * @param destUsername
	 * @param mongoService
	 * @return
	 */
	public boolean checkIsFriend(Set<String> friends, String destUsername);

	/**
	 * 添加好友
	 * 
	 * @param username
	 * @param friendUsername
	 * @param mongoService
	 */
	public void addFriend(String username, String friendUsername);

	/**
	 * 删除好友
	 * 
	 * @param username
	 * @param friendUsername
	 * @param mongoService
	 */
	public void delFriend(String username, String friendUsername);

	/**
	 * 获取好友列表
	 * 
	 * @param username
	 * @param mongoService
	 * @return
	 */
	public Set<String> getFriendList(String username);

}
