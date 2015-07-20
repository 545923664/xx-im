package com.jzwl.instant.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.GroupInfo;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.pojo.UserInfo;
import com.jzwl.instant.service.GroupService;
import com.jzwl.instant.service.SessionService;
import com.jzwl.instant.service.UserService;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.L;
import com.jzwl.instant.util.Util;
import com.mongodb.DBObject;

/**
 * 管理用户信息
 * 
 * @author xx
 * 
 */
@Component
public class UserServiceImpl implements UserService {

	private Gson gson = new Gson();

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private SessionService sessionService;

	/**
	 * 判断账号是否存在
	 * 
	 * @param account
	 * @return
	 */
	public boolean accountIsExist(String account) {

		try {

			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("account", account);

			List<DBObject> list = mongoService.findList(IC.mongodb_userinfo,
					cond);

			if (null != list && list.size() > 0) {// 存在
				return true;
			} else {// 不存在
				return false;
			}

		} catch (Exception e) {
			return true;
		}

	}

	/**
	 * 创建账号
	 * 
	 * @param account
	 * @param password
	 * @param nickname
	 * @return
	 */
	public String createAccount(String account, String password, String nickname) {

		try {

			boolean isExist = accountIsExist(account);

			if (!isExist) {

				UserInfo user = new UserInfo();

				user.setAccount(account);
				user.setPassword(Util.stringToMD5(password));
				user.setUserNickName(nickname);
				user.setUsername(System.currentTimeMillis() + "");

				mongoService.save(IC.mongodb_userinfo, user);

				return user.getUsername();

			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * 通过账号获取用户信息
	 * @param account
	 * @param password
	 * @return
	 */
	public UserInfo findUserByAccount(String account, String password) {

		try {

			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("account", account);
			cond.put("password", Util.stringToMD5(password));
			
			List<DBObject> list = mongoService.findList(IC.mongodb_userinfo,
					cond);

			if (null != list && list.size() > 0) {// 存在
				
				DBObject obj = list.get(0);

				obj.removeField("_id");

				String json = gson.toJson(obj);

				UserInfo user = gson.fromJson(json, UserInfo.class);

				if (null != user) {

					setUserInfo(user.getUsername(), user);

					return user;
				}
			} else {// 不存在
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * 登录时保存用户信息到数据库
	 * 
	 * @param mongoService
	 * @param username
	 * @param user
	 */
	public void saveUserInfoToDB(String username, UserInfo user) {
		try {

			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("username", username);

			List<DBObject> list = mongoService.findList(IC.mongodb_userinfo,
					cond);

			if (null != list && list.size() > 0) {// 修改
				Map<String, Object> updateValue = new HashMap<String, Object>();
				updateValue.put("connectAddress", user.getConnectAddress());
				updateValue.put("sessionID", user.getSessionID());
				updateValue.put("userNickName", user.getUserNickName());
				updateValue.put("loc", user.getLoc());

				mongoService.update(IC.mongodb_userinfo, cond, updateValue);
			} else {// 增加
				mongoService.save(IC.mongodb_userinfo, user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 从数据库获取用户信息
	 * 
	 * @param mongoService
	 * @param username
	 * @return
	 */
	public UserInfo getUserInfoFromDB(String username) {
		try {

			if (null != username) {

				Map<String, Object> cond = new HashMap<String, Object>();
				cond.put("username", username);

				List<DBObject> list = mongoService.findList(
						IC.mongodb_userinfo, cond);

				if (null != list && list.size() > 0) {
					DBObject obj = list.get(0);

					obj.removeField("_id");

					String json = gson.toJson(obj);

					UserInfo user = gson.fromJson(json, UserInfo.class);

					if (null != user) {

						setUserInfo(username, user);

						return user;
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}

	public UserInfo getUserInfoFromDBByAddress(String address) {
		try {

			if (null != address) {

				Map<String, Object> cond = new HashMap<String, Object>();
				cond.put("connectAddress", address);

				List<DBObject> list = mongoService.findList(
						IC.mongodb_userinfo, cond);

				if (null != list && list.size() > 0) {
					DBObject obj = list.get(0);

					obj.removeField("_id");

					String json = gson.toJson(obj);

					UserInfo user = gson.fromJson(json, UserInfo.class);

					if (null != user) {

						setUserInfo(user.getUsername(), user);

						return user;
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}

	/**
	 * 写入用户信息
	 * 
	 * @param redisService
	 * @param username
	 * @param newSession
	 */
	public void setUserInfo(String username, IoSession session,
			MyMessage message) {

		if (sessionService.isAvaible(session)) {

			String nickname = message.getExtValue("nickname");
			String connectAddress = session.getRemoteAddress().toString();

			UserInfo user = new UserInfo();

			user.setUsername(username);
			user.setUserNickName(nickname);
			user.setConnectAddress(connectAddress);
			user.setSessionID(session.getId());

			String str_lng = message.getExtValue("lng");
			String str_lat = message.getExtValue("lat");

			if (null != str_lng && str_lng.length() > 1) {
				double lng = Double.parseDouble(str_lng);
				user.getLoc().put("lng", lng);
			}

			if (null != str_lat && str_lat.length() > 1) {
				double lat = Double.parseDouble(str_lat);
				user.getLoc().put("lat", lat);
			}

			// 持久化写入用户信息
			saveUserInfoToDB(username, user);

			String res = gson.toJson(user);
			redisService.set(IC.user_simple_info_key + username, res,
					IC.MIN_1 * 10);

		}
	}

	/**
	 * 缓存用户信息
	 * 
	 * @param redisService
	 * @param username
	 * @param newSession
	 */
	public void setUserInfo(String username, UserInfo user) {

		if (null != user) {
			String res = gson.toJson(user);
			redisService.set(IC.user_simple_info_key + username, res,
					IC.MIN_1 * 10);
		}

	}

	/**
	 * 通过username获取用户信息
	 * 
	 * @param redisService
	 * @param username
	 * @return
	 */
	public UserInfo getUserInfo(String username) {
		String res = redisService.get(IC.user_simple_info_key + username);
		try {
			if (null != username) {

				if (null != res) {
					UserInfo user = gson.fromJson(res, UserInfo.class);

					if (null != user) {
						return user;
					}

				}

			}
		} catch (Exception e) {
			L.out("**错误**" + res);
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * 更新群信息（创建|加入）
	 * 
	 * @param mongoService
	 * @param username
	 * @param gid
	 */
	public void addUserGroups(String username, String gid) {

		try {

			UserInfo user = getUserInfoFromDB(username);

			if (null != user) {
				Set<String> groups = user.getGroups();

				if (null == groups) {
					groups = new HashSet<String>();
				}

				groups.add(gid);

				user.setGroups(groups);

				Map<String, Object> cond = new HashMap<String, Object>();
				cond.put("username", username);

				Map<String, Object> updateValue = new HashMap<String, Object>();
				updateValue.put("groups", user.getGroups());

				mongoService.update(IC.mongodb_userinfo, cond, updateValue);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除用户群数量
	 * 
	 * @param mongoService
	 * @param username
	 * @param gid
	 */
	public void delUserGroups(String username, String gid) {

		try {

			UserInfo user = getUserInfoFromDB(username);

			if (null != user) {
				Set<String> groups = user.getGroups();

				if (null == groups) {
					groups = new HashSet<String>();
				}

				groups.remove(gid);

				user.setGroups(groups);

				Map<String, Object> cond = new HashMap<String, Object>();
				cond.put("username", username);

				Map<String, Object> updateValue = new HashMap<String, Object>();
				updateValue.put("groups", user.getGroups());

				mongoService.update(IC.mongodb_userinfo, cond, updateValue);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取用户的群信息
	 * 
	 * @param mongoService
	 * @param username
	 * @return
	 */
	public List<GroupInfo> getUserGroupInfo(String username) {

		List<GroupInfo> list = new ArrayList<GroupInfo>();

		try {
			UserInfo user = getUserInfoFromDB(username);

			if (null != user) {
				Set<String> groups = user.getGroups();

				for (String gid : groups) {
					GroupInfo group = groupService.getGroupInfo(gid);

					if (null != group) {
						list.add(group);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return list;

		}
		return list;

	}

	public String getUserNickName(String username) {

		try {

			UserInfo user = getUserInfo(username);

			if (null != user) {
				String nickname = user.getUserNickName();

				if (null != nickname) {
					return nickname;
				}
			}

			return "游客";

		} catch (Exception e) {
			e.printStackTrace();
			return "游客";
		}

	}

	@Override
	public boolean updateUserAvatar(String username, String avatar) {

		try {

			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("username", username);

			List<DBObject> list = mongoService.findList(IC.mongodb_userinfo,
					cond);

			if (null != list && list.size() > 0) {// 是否存在该用户

				Map<String, Object> updateValue = new HashMap<String, Object>();
				updateValue.put("avatar", avatar);

				mongoService.update(IC.mongodb_userinfo, cond, updateValue);

				return true;

			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
