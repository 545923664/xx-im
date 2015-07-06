package com.jzwl.instant.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.jzwl.instant.MinaCoreService;
import com.jzwl.instant.SessionManager;
import com.jzwl.instant.UserManager;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.pojo.UserInfo;
import com.jzwl.instant.util.ChinaNameUtil;
import com.jzwl.instant.util.InstantConstant;
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
			Set<String> keys = SessionManager.usersMap.keySet();

			ArrayList<UserInfo> list = new ArrayList<UserInfo>();

			for (String username : keys) {

				IoSession session = SessionManager.usersMap.get(username);

				if (SessionManager.isAvaible(session)) {

					UserInfo user = UserManager.getUserInfo(redisService,
							username);

					if (null != user) {
						list.add(user);
					}

				}

			}

			String json = gson.toJson(list);
			JsonTool.printMsg(response, json);
			log.info(json);

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
		config.put("serverAddress", InstantConstant.server_connect_address);// 服务器连接地址
		config.put("username", System.currentTimeMillis() + "");// 用户标示符
		// config.put("nickname", ChinaNameUtil.getSimpleName());// 用户昵称
		config.put("nickname", "guest");// 用户昵称

		JsonTool.printMsg(response, gson.toJson(config));

	}

	@RequestMapping(value = "/getOnlineUserInfoList")
	public void getOnlineUserList(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Set<String> keys = SessionManager.usersMap.keySet();

			ArrayList<UserInfo> list = new ArrayList<UserInfo>();

			for (String key : keys) {
				UserInfo user = new UserInfo();
				user.setUsername(key);

				if (null != user.getUsername()) {

					user.setUserNickName("----");

				}

				list.add(user);
			}

			String json = JsonTool.getListJson(list, list.size(), null, null);
			JsonTool.printMsg(response, json);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/sendMsg.html")
	public void sendMsg(HttpServletRequest request, HttpServletResponse response) {
		try {
			String sendMsg = request.getParameter("sendMsg");
			String tousername = request.getParameter("tousername");

			if (null != tousername && null != sendMsg) {
				MyMessage myMessage = new MyMessage();
				myMessage.setModel(InstantConstant.BROCAST);
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
