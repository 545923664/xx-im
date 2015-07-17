package com.jzwl.instant.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.mina.core.session.IoSession;
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
import com.jzwl.instant.service.SendService;
import com.jzwl.instant.service.SessionService;
import com.jzwl.instant.service.UserService;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.JsonTool;

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

	/**
	 * 获取在线用户列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getOnlineUserList")
	public void getOnlineuser(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			String uid = request.getParameter("uid");

			Set<String> keys = SessionService.usersMap.keySet();

			Map<String, UserInfo> temp = new HashMap<String, UserInfo>();

			Set<String> friendIDList = new HashSet<String>();

			// 先获取好友
			if (null != uid) {
				friendIDList = friendService.getFriendList(uid);
			}

			for (String fid : friendIDList) {
				UserInfo user = userService.getUserInfoFromDB(fid);

				if (null != user) {
					user.setIsFriend("1");
					temp.put(fid, user);
				}
			}

			// 获取在线用户[未来会删掉]
			for (String username : keys) {

				IoSession session = SessionService.usersMap.get(username);

				if (sessionService.isAvaible(session)) {

					if (null != temp.get(username)) {// 已经是好友则添加在线状态
						UserInfo user = temp.get(username);
						user.setIsOnline("1");
						temp.put(username, user);
					} else {// 不是好友说明在线状态即可
						UserInfo user = userService.getUserInfo(username);

						if (null != user) {
							user.setIsOnline("1");
							temp.put(username, user);
						}
					}

				}

			}

			// 合并
			Set<String> ids = temp.keySet();

			ArrayList<UserInfo> list = new ArrayList<UserInfo>();

			for (String username : ids) {
				list.add(temp.get(username));
			}

			String json = gson.toJson(list);
			JsonTool.printMsg(response, json);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取在线系想你
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getOnlineInfo")
	public void getOnlineInfo(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = new FormatJsonResult();

		try {

			String username = request.getParameter("username");

			Set<String> keys = SessionService.usersMap.keySet();

			Map<String, UserInfo> temp = new HashMap<String, UserInfo>();

			Set<String> friendIDList = new HashSet<String>();

			List<GroupInfo> groupList = new ArrayList<GroupInfo>();

			// 先获取好友|群
			if (null != username) {
				friendIDList = friendService.getFriendList(username);
				groupList = userService.getUserGroupInfo(username);
			}

			if (null != friendIDList) {

				for (String fid : friendIDList) {

					UserInfo user = userService.getUserInfoFromDB(fid);

					if (null != user) {
						user.setIsFriend("1");
						temp.put(fid, user);
					}
				}
			}

			// 获取在线用户[未来会删掉]
			for (String uid : keys) {

				IoSession session = SessionService.usersMap.get(uid);

				if (sessionService.isAvaible(session)) {

					if (null != temp.get(uid)) {// 已经是好友则添加在线状态
						UserInfo user = temp.get(uid);
						user.setIsOnline("1");
						temp.put(uid, user);
					} else {// 不是好友说明在线状态即可
						UserInfo user = userService.getUserInfo(uid);

						if (null != user) {
							user.setIsOnline("1");
							temp.put(uid, user);
						}
					}

				}

			}

			// 合并
			Set<String> ids = temp.keySet();

			// 好友列表
			List<UserInfo> userList = new ArrayList<UserInfo>();

			for (String uid : ids) {
				userList.add(temp.get(uid));
			}

			Map<String, Object> res = new HashMap<String, Object>();

			res.put("user", userList);
			res.put("group", groupList);

			fjr.setFlag(1);
			fjr.setCtrl("");
			fjr.setMap(res);

			JsonTool.printMsg(response, gson.toJson(fjr));

		} catch (Exception e) {
			fjr.setFlag(0);
			fjr.setCtrl("t");
			fjr.setMessage(e.getMessage());

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
		config.put("username", "5459");// 用户标示符
		config.put("nickname", "5459");// 用户昵称

		JsonTool.printMsg(response, gson.toJson(config));

	}

	@RequestMapping(value = "/test")
	public void test(HttpServletRequest request, HttpServletResponse response) {
		try {
			Set<String> keys = SessionService.usersMap.keySet();

			ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			for (String key : keys) {
				Map<String, Object> map = new HashMap<String, Object>();

				map.put(key, SessionService.usersMap.get(key).isConnected());

				list.add(map);
			}

			String json = gson.toJson(list);

			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out;
			out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
			log.info(json);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
