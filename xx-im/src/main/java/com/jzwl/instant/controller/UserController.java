package com.jzwl.instant.controller;

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
import com.jzwl.instant.service.impl.SendServiceImpl;
import com.jzwl.instant.service.impl.SessionServiceImpl;
import com.jzwl.instant.util.JsonTool;

@Controller
@RequestMapping("/user")
public class UserController {

	Logger log = LoggerFactory.getLogger(UserController.class);

	private Gson gson = new Gson();

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	@Autowired
	private SendServiceImpl sendServiceImpl;
	@Autowired
	private SessionServiceImpl sessionServiceImpl;

	/**
	 * 请求添加对方为好友
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/closeConnect")
	public void closeConnect(HttpServletRequest request,
			HttpServletResponse response) {
		FormatJsonResult fjr = new FormatJsonResult();

		fjr.setFlag(0);
		fjr.setCtrl("t");

		try {

			String username = request.getParameter("username");// 发起人
			if (null != username) {

				sessionServiceImpl.disConnect(username);

				fjr.setFlag(1);
				fjr.setCtrl("");
				fjr.setMessage("连接已经断开");

				sendServiceImpl.sendSystemOnlineInfoMessage(
						sendServiceImpl.sys_off_brocast, username);

				JsonTool.printMsg(response, gson.toJson(fjr));

			} else {
				fjr.setMessage("用户名为空，断开毛线连接！");
				JsonTool.printMsg(response, gson.toJson(fjr));
			}

		} catch (Exception e) {
			fjr.setMessage(e.getMessage());

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}

}
