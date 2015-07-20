package com.jzwl.instant.service;

import java.util.List;

import org.apache.mina.core.session.IoSession;

import com.jzwl.instant.pojo.GroupInfo;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.pojo.UserInfo;

/**
 * 管理用户信息
 * 
 * @author xx
 * 
 */
public interface UserService {

	/**
	 * 判断账号是否存在
	 * 
	 * @param account
	 * @return
	 */
	public boolean accountIsExist(String account);

	/**
	 * 创建账号
	 * 
	 * @param account
	 * @param password
	 * @param nickname
	 * @return
	 */
	public String createAccount(String account, String password, String nickname);

	/**
	 * 通过账号获取用户信息
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	public UserInfo findUserByAccount(String account, String password);

	/**
	 * 登录时保存用户信息到数据库
	 * 
	 * @param mongoService
	 * @param username
	 * @param user
	 */
	public void saveUserInfoToDB(String username, UserInfo user);

	/**
	 * 从数据库获取用户信息
	 * 
	 * @param mongoService
	 * @param username
	 * @return
	 */
	public UserInfo getUserInfoFromDB(String username);

	public UserInfo getUserInfoFromDBByAddress(String address);

	/**
	 * 写入用户信息
	 * 
	 * @param redisService
	 * @param username
	 * @param newSession
	 */
	public void setUserInfo(String username, IoSession session,
			MyMessage message);

	/**
	 * 缓存用户信息
	 * 
	 * @param redisService
	 * @param username
	 * @param newSession
	 */
	public void setUserInfo(String username, UserInfo user);

	/**
	 * 通过username获取用户信息
	 * 
	 * @param redisService
	 * @param username
	 * @return
	 */
	public UserInfo getUserInfo(String username);

	/**
	 * 更新群信息（创建|加入）
	 * 
	 * @param mongoService
	 * @param username
	 * @param gid
	 */
	public void addUserGroups(String username, String gid);

	/**
	 * 删除用户群数量
	 * 
	 * @param mongoService
	 * @param username
	 * @param gid
	 */
	public void delUserGroups(String username, String gid);

	/**
	 * 获取用户的群信息
	 * 
	 * @param mongoService
	 * @param username
	 * @return
	 */
	public List<GroupInfo> getUserGroupInfo(String username);

	/**
	 * 获取昵称
	 * 
	 * @param username
	 * @return
	 */
	public String getUserNickName(String username);

	/**
	 * 更新用户头像
	 * 
	 * @param username
	 * @return
	 */
	public boolean updateUserAvatar(String username, String avatar);
}
