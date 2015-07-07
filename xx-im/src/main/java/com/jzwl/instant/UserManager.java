package com.jzwl.instant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.pojo.UserInfo;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.L;
import com.mongodb.DBObject;

/**
 * 管理用户信息
 * 
 * @author xx
 * 
 */
public class UserManager {

	private static Gson gson = new Gson();
	
	

	/**
	 * 保存用户信息到数据库
	 * 
	 * @param mongoService
	 * @param username
	 * @param user
	 */
	public static void saveUserInfoToDB(MongoService mongoService,
			String username, UserInfo user) {
		try {
			mongoService.save(IC.mongodb_userinfo, user, "username", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 从数据库获取用户信息
	 * @param mongoService
	 * @param username
	 * @return
	 */
	public static UserInfo getUserInfoFromDB(MongoService mongoService,
			String username) {
		try {

			if (null != username) {

				Map<String, Object> cond = new HashMap<String, Object>();
				cond.put("username", username);

				List<DBObject> list = mongoService.findList(IC.mongodb_userinfo,
						cond);

				if (null != list && list.size() > 0) {
					DBObject obj = list.get(0);

					obj.removeField("_id");

					String json = gson.toJson(obj);

					UserInfo user = gson.fromJson(json, UserInfo.class);

					if (null != user) {
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
	public static void setUserInfo(RedisService redisService,
			MongoService mongoService, String username, IoSession session,
			MyMessage message) {

		if (SessionManager.isAvaible(session)) {

			String nickname = message.getExtValue("nickname");
			String connectAddress = session.getRemoteAddress().toString();

			UserInfo user = new UserInfo();

			user.setUsername(username);
			user.setUserNickName(nickname);
			user.setConnectAddress(connectAddress);
			user.setSessionID(session.getId());

			saveUserInfoToDB(mongoService, username, user);

			String res = gson.toJson(user);
			redisService.set(IC.user_info_key + username, res, IC.MIN_1 * 10);

		}
	}

	/**
	 * 通过username获取用户信息
	 * 
	 * @param redisService
	 * @param username
	 * @return
	 */
	public static UserInfo getUserInfo(RedisService redisService,
			String username) {
		String res = redisService.get(IC.user_info_key + username);
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
}
