package com.jzwl.instant.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.jzwl.instant.FriendManager;
import com.jzwl.instant.MinaCoreService;
import com.jzwl.instant.SessionManager;
import com.jzwl.instant.UserManager;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.pojo.UserInfo;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.JsonTool;
import com.jzwl.instant.util.Util;

@Controller
@RequestMapping("/chat")
public class ChatController {

	Logger log = LoggerFactory.getLogger(ChatController.class);

	private Gson gson = new Gson();

	private MinaCoreService minaCoreService = new MinaCoreService(null);

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

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

			Set<String> keys = SessionManager.usersMap.keySet();

			Map<String, UserInfo> temp = new HashMap<String, UserInfo>();

			List<String> friendIDList = new ArrayList<String>();

			// 先获取好友
			if (null != uid) {
				friendIDList = FriendManager.getFriendList(uid, mongoService);
			}

			for (String fid : friendIDList) {
				UserInfo user = UserManager
						.getUserInfoFromDB(mongoService, fid);

				if (null != user) {
					user.setIsFriend("1");
					temp.put(fid, user);
				}
			}

			// 获取在线用户
			for (String username : keys) {

				IoSession session = SessionManager.usersMap.get(username);

				if (SessionManager.isAvaible(session)) {

					if (null != temp.get(username)) {//已经是好友则添加在线状态
						UserInfo user = temp.get(username);
						user.setIsOnline("1");
						temp.put(username, user);
					} else {//不是好友说明在线状态即可
						UserInfo user = UserManager.getUserInfo(redisService,
								username);

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
	 * 获取远程配置参数
	 */
	@RequestMapping(value = "/getClientConfig")
	public void getClientConfig(HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, String> config = new HashMap<String, String>();
		config.put("serverAddress", IC.server_connect_address);// 服务器连接地址
		config.put("username", System.currentTimeMillis() + "");// 用户标示符
		// config.put("nickname", ChinaNameUtil.getSimpleName());// 用户昵称
		config.put("nickname", "guest");// 用户昵称

		JsonTool.printMsg(response, gson.toJson(config));

	}


	@RequestMapping(value = "/sendMsg.html")
	public void sendMsg(HttpServletRequest request, HttpServletResponse response) {
		try {
			String sendMsg = request.getParameter("sendMsg");
			String tousername = request.getParameter("tousername");

			if (null != tousername && null != sendMsg) {
				MyMessage myMessage = new MyMessage();
				myMessage.setModel(IC.BROCAST);
				myMessage.setMessage(sendMsg);
				myMessage.setTousername(tousername);
				myMessage.setUsername("#system#");

				myMessage.setDate(Util.getCurrDate());

				minaCoreService.controlChatMessage(myMessage);
			}

			String json = JsonTool.getObjectJson(true, "success");
			JsonTool.printMsg(response, json);

		} catch (Exception e) {
			JsonTool.printMsg(response, "发送失败");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/closeConnect")
	public void closeConnect(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String tousername = request.getParameter("username");

			if (null != tousername) {
				minaCoreService.closeConnect4username(tousername);
			}

		} catch (Exception e) {
			JsonTool.printMsg(response, "发送失败");
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/test")
	public void test(HttpServletRequest request, HttpServletResponse response) {
		try {
			Set<String> keys = SessionManager.usersMap.keySet();

			ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			for (String key : keys) {
				Map<String, Object> map = new HashMap<String, Object>();

				map.put(key, SessionManager.usersMap.get(key).isConnected());

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
