package com.jzwl.instant.service;

import java.util.List;
import java.util.Set;

import com.jzwl.instant.pojo.GroupInfo;

/**
 * 群管理
 * 
 * @author Administrator
 * 
 */
public interface GroupService {

	/**
	 * 创建群
	 * 
	 * @param username
	 * @param mongoService
	 */
	public String createGroup(String username, String groupDesc);

	/**
	 * 加入群
	 * 
	 * @param username
	 * @param gid
	 * @param mongoService
	 * @return
	 */
	public boolean JoinGroup(String username, String gid);

	/**
	 * 退出群
	 * 
	 * @param username
	 * @param gid
	 * @param mongoService
	 * @return
	 */
	public boolean quiteGroup(String username, String gid);

	/**
	 * 解散群
	 * 
	 * @param username
	 * @param gid
	 * @param mongoService
	 * @return
	 */
	public boolean disbandGroup(String username, String gid, GroupInfo group);

	/**
	 * 验证是否已经是群成员
	 * 
	 * @param username
	 * @param destUsername
	 * @param mongoService
	 * @return
	 */
	public boolean checkIsGroupMember(String username, Set<String> member);

	/**
	 * 获取群信息
	 * 
	 * @param gid
	 * @param mongoService
	 * @return
	 */
	public GroupInfo getGroupInfo(String gid);

	/**
	 * 获取所有的群
	 * 
	 * @param mongoService
	 * @return
	 */
	public List<GroupInfo> getAllGroups();
}
