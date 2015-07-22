package com.jzwl.instant.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.FormatJsonResult;
import com.jzwl.instant.pojo.GroupInfo;
import com.jzwl.instant.pojo.UserInfo;
import com.jzwl.instant.service.FriendService;
import com.jzwl.instant.service.GroupService;
import com.jzwl.instant.service.LocationService;
import com.jzwl.instant.service.SendService;
import com.jzwl.instant.service.SessionService;
import com.jzwl.instant.service.UserService;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.JsonTool;
import com.jzwl.instant.util.N;

@Controller
@RequestMapping("/chat")
public class ChatController {

	Logger log = LoggerFactory.getLogger(ChatController.class);

	private Gson gson = new Gson();

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private FriendService friendService;

	@Autowired
	private SendService sendService;
	@Autowired
	private LocationService locationService;

	/**
	 * 获取在线用户系信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getOnlineInfo")
	public void getOnlineInfo(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = null;

		Set<String> friends = new HashSet<String>();

		// 好友列表
		List<UserInfo> firendList = new ArrayList<UserInfo>();
		// 群列表
		List<GroupInfo> groupList = new ArrayList<GroupInfo>();

		try {

			String username = request.getParameter("username");

			UserInfo me = userService.getUser(username);

			if (N.isNotNull(username)) {
				// 先获取好友|群
				if (null != username) {
					friends = friendService.getFriendList(username);
					groupList = userService.getUserGroupInfo(username);
				}

				// 设置好友属性
				if (null != friends) {

					for (String fid : friends) {

						UserInfo user = userService.getUserInfoFromDB(fid);

						if (null != user) {

							// 设置为朋友
							user.setIsFriend("1");

							// 设置在线状态
							if (sessionService
									.isAvaible(SessionService.usersMap.get(user
											.getUsername()))) {
								user.setIsOnline("1");
							} else {
								user.setIsOnline("0");
							}

							// 设置距离
							Map<String, Double> me_loc = me.getLoc();
							Map<String, Double> friend_loc = user.getLoc();

							if (null != me_loc && null != friend_loc) {

								if (me_loc.containsKey("lng")
										&& me_loc.containsKey("lat")
										&& friend_loc.containsKey("lng")
										&& friend_loc.containsKey("lat")) {

									double distance = locationService
											.getDistance(me_loc.get("lng"),
													me_loc.get("lat"),
													friend_loc.get("lng"),
													friend_loc.get("lat"));

									user.setDistance(distance);

								}

							}

							firendList.add(user);

						}
					}
				}

				Map<String, Object> res = new HashMap<String, Object>();

				res.put("user", firendList);
				res.put("group", groupList);

				fjr = new FormatJsonResult(1, "", "", null, res);

			} else {

				fjr = new FormatJsonResult(0, "参数错误", "t", null, null);

			}

			JsonTool.printMsg(response, gson.toJson(fjr));

		} catch (Exception e) {

			fjr = new FormatJsonResult(0, e.getMessage(), "t", null, null);

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}

	/**
	 * 获取远程配置参数
	 */
	@RequestMapping(value = "/getClientConfig")
	public void getClientConfig(HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, String> config = new HashMap<String, String>();
		config.put("serverAddress", IC.server_connect_address);// 服务器连接地址
		config.put("username", "");// 用户标示符
		config.put("nickname", "小明");// 用户昵称

		JsonTool.printMsg(response, gson.toJson(config));

	}

}
