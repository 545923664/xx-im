package com.jzwl.instant.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.GroupInfo;
import com.jzwl.instant.service.GroupService;
import com.jzwl.instant.service.UserService;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.Util;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 群管理
 * 
 * @author Administrator
 * 
 */
@Component
public class GroupServiceImpl implements GroupService {

	private Gson gson = new Gson();

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	@Autowired
	private UserService userService;

	/**
	 * 创建群
	 * 
	 * @param username
	 * @param mongoService
	 */
	public String createGroup(String username, String groupDesc) {
		try {

			String gid = System.currentTimeMillis() + "";

			GroupInfo group = new GroupInfo();
			group.setGid(gid);
			group.setMid(username);// 群主
			group.setMaxCount(20);

			if (null != groupDesc && groupDesc.length() > 0) {
				group.setDesc(groupDesc);
			} else {
				group.setDesc("[" + username + "]的群");
			}

			group.setCreateDate(Util.DateFormat(new Date()));

			Set<String> member = new HashSet<String>();

			member.add(username);

			group.setMember(member);// 成员
			mongoService.save(IC.mongodb_groupinfo, group);

			// 更新个人群信息
			userService.addUserGroups(username, gid);

			return gid;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 加入群
	 * 
	 * @param username
	 * @param gid
	 * @param mongoService
	 * @return
	 */
	public boolean JoinGroup(String username, String gid) {

		try {

			GroupInfo group = getGroupInfo(gid);

			Set<String> member = group.getMember();

			if (null == member) {
				member = new HashSet<String>();
			}

			member.add(username);

			group.setMember(member);

			Map<String, Object> cond = new HashMap<String, Object>();

			cond.put("gid", gid);

			Map<String, Object> updateValue = new HashMap<String, Object>();

			updateValue.put("member", group.getMember());

			// 群成员加入用户
			mongoService.update(IC.mongodb_groupinfo, cond, updateValue);
			// 用户信息用加入该群
			userService.addUserGroups(username, gid);

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 退出群
	 * 
	 * @param username
	 * @param gid
	 * @param mongoService
	 * @return
	 */
	public boolean quiteGroup(String username, String gid) {

		try {

			GroupInfo group = getGroupInfo(gid);

			if (null != group) {
				Set<String> member = group.getMember();

				if (null == member) {
					member = new HashSet<String>();
				}

				member.remove(username);

				group.setMember(member);

				Map<String, Object> cond = new HashMap<String, Object>();

				cond.put("gid", gid);

				Map<String, Object> updateValue = new HashMap<String, Object>();

				updateValue.put("member", group.getMember());

				// 群成员减员
				mongoService.update(IC.mongodb_groupinfo, cond, updateValue);

				// 用户信息删除该群
				userService.delUserGroups(username, gid);

			}

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 解散群
	 * 
	 * @param username
	 * @param gid
	 * @param mongoService
	 * @return
	 */
	public boolean disbandGroup(String username, String gid, GroupInfo group) {

		try {

			if (null != group) {

				String mid = group.getMid();

				Set<String> member = group.getMember();

				if (mid.equals(username)) {

					// 删除群
					mongoService.del(IC.mongodb_groupinfo, new BasicDBObject(
							"gid", group.getGid()));

					// 用户信息删除该群
					for (String uid : member) {
						userService.delUserGroups(uid, gid);
					}

				} else {
					return false;
				}

			}

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 验证是否已经是群成员
	 * 
	 * @param username
	 * @param destUsername
	 * @param mongoService
	 * @return
	 */
	public boolean checkIsGroupMember(String username, Set<String> member) {

		try {

			for (String mid : member) {
				if (mid.equals(username)) {
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}

	/**
	 * 获取群信息
	 * 
	 * @param gid
	 * @param mongoService
	 * @return
	 */
	public GroupInfo getGroupInfo(String gid) {
		try {

			Map<String, Object> cond = new HashMap<String, Object>();

			cond.put("gid", gid);
			List<DBObject> list = mongoService.findList(IC.mongodb_groupinfo,
					cond);

			if (null != list && list.size() > 0) {
				DBObject obj = list.get(0);

				if (null != obj) {
					obj.removeField("_id");
					String json = gson.toJson(obj);

					GroupInfo group = gson.fromJson(json, GroupInfo.class);

					return group;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}

	/**
	 * 获取所有的群
	 * 
	 * @param mongoService
	 * @return
	 */
	public List<GroupInfo> getAllGroups() {
		try {

			List<GroupInfo> groupList = new ArrayList<GroupInfo>();

			List<DBObject> list = mongoService.find(IC.mongodb_groupinfo);

			for (DBObject obj : list) {
				if (null != obj) {
					obj.removeField("_id");
					String json = gson.toJson(obj);

					GroupInfo group = gson.fromJson(json, GroupInfo.class);

					if (null != group) {
						groupList.add(group);
					}
				}
			}

			return groupList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
