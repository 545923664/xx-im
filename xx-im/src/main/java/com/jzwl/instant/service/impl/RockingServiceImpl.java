package com.jzwl.instant.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.UserInfo;
import com.jzwl.instant.service.RockingService;
import com.jzwl.instant.service.UserService;
import com.jzwl.instant.util.IC;

/**
 * 摇一摇
 * 
 * @author Administrator
 * 
 */
@Component
public class RockingServiceImpl implements RockingService {

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	@Autowired
	private UserService userService;

	private Gson gson = new Gson();

	@Override
	public List<UserInfo> getRockingUserList(String username) {

		List<UserInfo> list = new ArrayList<UserInfo>();

		try {
			if (null != username) {

				String userKey = IC.user_rocking_key + username;

				UserInfo user = userService.getUserInfoFromDB(username);

				if (null != user) {

					// 2分钟
					redisService.set(userKey, gson.toJson(user), IC.MIN_1 * 2);

					Set<String> keys = redisService.Setkeys(IC.user_rocking_key
							+ "*");

					for (String key : keys) {

						if (null != key && !key.equals(userKey)) {

							String destUserName = key.replace(
									IC.user_rocking_key, "");

							UserInfo destUser = userService
									.getUserInfo(destUserName);

							if (null != destUser) {
								list.add(destUser);
							}

						}

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}

		return list;
	}
}
