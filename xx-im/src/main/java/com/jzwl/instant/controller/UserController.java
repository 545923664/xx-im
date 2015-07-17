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
import com.jzwl.instant.service.FileService;
import com.jzwl.instant.service.SendService;
import com.jzwl.instant.service.SessionService;
import com.jzwl.instant.service.UserService;
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
	private SendService sendService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private FileService fileService;
	@Autowired
	private UserService userService;

	/**
	 * 关闭连接
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

				sessionService.disConnect(username);

				fjr.setFlag(1);
				fjr.setCtrl("");
				fjr.setMessage("连接已经断开");

				sendService.sendSystemOnlineInfoMessage(
						SendService.sys_off_brocast, username);

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

	/**
	 * 上传头像
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/uploadAvatar")
	public void uploadAvatar(HttpServletRequest request,
			HttpServletResponse response) {
		FormatJsonResult fjr = null;

		try {

			String username = request.getParameter("username");// 发起人

			String fileName = request.getParameter("fileName");

			if (null != username && null != fileName) {

				String accessUrl = fileService.uploadUserAvatar(mongoService,
						request, username, fileName);

				if (null != accessUrl) {

					// 更新用户头像

					boolean flag = userService.updateUserAvatar(username,
							accessUrl);

					if (flag) {

						fjr = new FormatJsonResult(1, accessUrl, "t", null,
								null);
					} else {
						fjr = new FormatJsonResult(0, "更新头像失败", "t", null, null);
					}

				} else {
					fjr = new FormatJsonResult(0, "上传失败", "t", null, null);
				}

			} else {

				fjr = new FormatJsonResult(0, "参数错误", "t", null, null);

			}

			JsonTool.printMsg(response, gson.toJson(fjr));

		} catch (Exception e) {
			fjr.setMessage(e.getMessage());

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}
}
