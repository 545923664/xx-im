package com.jzwl.instant;

import org.apache.mina.core.session.IoSession;

import com.google.gson.Gson;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.pojo.UserInfo;
import com.jzwl.instant.util.InstantConstant;
import com.jzwl.instant.util.L;

/**
 * 管理用户信息
 * 
 * @author xx
 * 
 */
public class UserManager {

	private static Gson gson = new Gson();

	/**
	 * 写入用户信息
	 * 
	 * @param redisService
	 * @param username
	 * @param newSession
	 */
	public static void setUserInfo(RedisService redisService, String username,
			IoSession session, MyMessage message) {

		if (SessionManager.isAvaible(session)) {

			String nickname = message.getExtValue("nickname");
			String connectAddress = session.getRemoteAddress().toString();

			UserInfo user = new UserInfo();

			user.setUsername(username);
			user.setUserNickName(nickname);
			user.setConnectAddress(connectAddress);
			user.setSessionID(session.getId());

			String res = gson.toJson(user);

			redisService.set(InstantConstant.user_info_key + username, res,
					InstantConstant.MIN_1 * 10);

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
		String res = redisService.get(InstantConstant.user_info_key + username);
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
			L.out("**错误**"+res);
			e.printStackTrace();
			return null;
		}

		return null;
	}
}
