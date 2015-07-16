package com.jzwl.instant.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.UserInfo;
import com.jzwl.instant.service.FriendService;
import com.jzwl.instant.service.UserService;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.L;

@Component
public class FriendServiceImpl implements FriendService {

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	@Autowired
	private UserService userService;

	/**
	 * 验证是否是好友
	 * 
	 * @param username
	 * @param destUsername
	 * @param mongoService
	 * @return
	 */
	public boolean checkIsFriend(Set<String> friends, String destUsername) {

		if (null != friends && friends.size() > 0) {
			for (String uid : friends) {
				if (uid.equals(destUsername)) {
					return true;
				}
			}
		}

		return false;

	}

	/**
	 * 添加好友
	 * 
	 * @param username
	 * @param friendUsername
	 * @param mongoService
	 */
	public void addFriend(String username, String friendUsername) {
		try {

			UserInfo user = userService.getUserInfoFromDB(username);

			Set<String> friends = user.getFriends();

			if (null == friends) {
				friends = new HashSet<String>();
			}

			boolean isFriend = checkIsFriend(friends, friendUsername);

			if (!isFriend) {

				friends.add(friendUsername);

				user.setFriends(friends);

				Map<String, Object> cond = new HashMap<String, Object>();
				cond.put("username", username);

				Map<String, Object> updateValue = new HashMap<String, Object>();
				updateValue.put("friends", user.getFriends());

				mongoService.update(IC.mongodb_userinfo, cond, updateValue);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除好友
	 * 
	 * @param username
	 * @param friendUsername
	 * @param mongoService
	 */
	public void delFriend(String username, String friendUsername) {
		try {

			UserInfo user = userService.getUserInfoFromDB(username);

			if (null != user) {
				Set<String> friends = user.getFriends();

				if (null == friends) {
					friends = new HashSet<String>();
				}

				boolean isFriend = checkIsFriend(friends, friendUsername);

				if (isFriend) {

					friends.remove(friendUsername);

					user.setFriends(friends);

					Map<String, Object> cond = new HashMap<String, Object>();
					cond.put("username", username);

					Map<String, Object> updateValue = new HashMap<String, Object>();
					updateValue.put("friends", user.getFriends());

					mongoService.update(IC.mongodb_userinfo, cond, updateValue);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取好友列表
	 * 
	 * @param username
	 * @param mongoService
	 * @return
	 */
	public Set<String> getFriendList(String username) {

		try {

			UserInfo user = userService.getUserInfoFromDB(username);

			if (null != user) {

				if (null != user.getFriends()) {

					return user.getFriends();

				} else {

					Set<String> friends = new HashSet<String>();

					user.setFriends(friends);

					return user.getFriends();
				}
			}

			L.out(user + "");

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}

}
