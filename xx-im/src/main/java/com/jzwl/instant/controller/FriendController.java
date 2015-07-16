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
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.service.impl.FriendServiceImpl;
import com.jzwl.instant.service.impl.GroupServiceImpl;
import com.jzwl.instant.service.impl.MessageServiceImpl;
import com.jzwl.instant.service.impl.SendServiceImpl;
import com.jzwl.instant.service.impl.SessionServiceImpl;
import com.jzwl.instant.service.impl.UserServiceImpl;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.JsonTool;

@Controller
@RequestMapping("/friend")
public class FriendController {

	Logger log = LoggerFactory.getLogger(FriendController.class);

	private Gson gson = new Gson();

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	@Autowired
	private GroupServiceImpl groupServiceImpl;
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private SessionServiceImpl sessionServiceImpl;
	@Autowired
	private FriendServiceImpl friendServiceImpl;
	@Autowired
	private MessageServiceImpl messageServiceImpl;
	@Autowired
	private SendServiceImpl sendServiceImpl;

	/**
	 * 请求添加对方为好友
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/addFriend")
	public void addFriend(HttpServletRequest request,
			HttpServletResponse response) {
		FormatJsonResult fjr = new FormatJsonResult();
		try {

			String username = request.getParameter("username");// 发起人
			String userNickName = request.getParameter("user_nickname");

			String destUsername = request.getParameter("dest_username");// 接收人
			String destNickName = request.getParameter("dest_nickname");
			if (null != username && null != destUsername) {

				MyMessage message = new MyMessage();

				message.setModel(IC.SYS);

				message.setUsername(username);

				message.setTousername(destUsername);

				message.setMessage(destNickName + "你好[" + userNickName
						+ "]要加您为好友是否同意");

				message.putExtKey("action", IC.ACTION_ADD_FRIEND);

				messageServiceImpl.joinSendQueue(gson.toJson(message));

				fjr.setFlag(1);
				fjr.setCtrl("t");
				fjr.setMessage("已经发送了请求，等待对方验证");

				JsonTool.printMsg(response, gson.toJson(fjr));

			} else {

				fjr.setFlag(0);
				fjr.setCtrl("t");
				fjr.setMessage("参数错误");

				JsonTool.printMsg(response, gson.toJson(fjr));
			}

		} catch (Exception e) {

			fjr.setFlag(0);
			fjr.setCtrl("t");
			fjr.setMessage(e.getMessage());

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}

	/**
	 * 解除好友关系
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/delFriend")
	public void delFriend(HttpServletRequest request,
			HttpServletResponse response) {
		FormatJsonResult fjr = null;

		try {

			String username = request.getParameter("username");// 发起人
			String userNickName = request.getParameter("user_nickname");

			String destUsername = request.getParameter("dest_username");// 目标
			String destNickName = request.getParameter("dest_nickname");

			if (null != username && null != destUsername) {

				MyMessage message = new MyMessage();

				message.setModel(IC.SYS);

				message.setUsername(username);

				message.setTousername(destUsername);

				message.setMessage(destNickName + "你好[" + userNickName
						+ "]已经把你这个逗比删除了！");

				message.putExtKey("action", IC.ACTION_DEL_FRIEND);

				messageServiceImpl.joinSendQueue(gson.toJson(message));

				// 删除好友关系
				friendServiceImpl.delFriend(username, destUsername);
				// 双向
				friendServiceImpl.delFriend(destUsername, username);

				fjr = new FormatJsonResult(1, "目标已删除", "t", null, null);

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
	 * 同意加对方为好友
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/agreeFriend")
	public void agreeFriend(HttpServletRequest request,
			HttpServletResponse response) {
		FormatJsonResult fjr = new FormatJsonResult();
		try {

			String username = request.getParameter("username");// 接收人
			String userNickName = request.getParameter("user_nickname");
			String destUsername = request.getParameter("dest_username");// 发起人
			String destNickName = request.getParameter("dest_nickname");

			String isAgreen = request.getParameter("is_agree");

			if (null != username && null != destUsername) {

				MyMessage message = new MyMessage();

				message.setModel(IC.SYS);

				message.setUsername(username);

				message.setTousername(destUsername);

				fjr.setFlag(1);
				fjr.setCtrl("t");

				if ("1".equals(isAgreen)) {// 同意

					message.putExtKey("action", IC.ACTION_AGREE_FRIEND);

					message.setMessage(destNickName + "你好[" + userNickName
							+ "]同意了您的好友邀请，现在你们可以聊天了");
					fjr.setMessage("您已经同意了" + destNickName + "的申请");

					// 建立好友关系
					friendServiceImpl.addFriend(username, destUsername);
					// 双向
					friendServiceImpl.addFriend(destUsername, username);

				} else {// 拒绝

					message.putExtKey("action", IC.ACTION_NOT_AGREE_FRIEND);

					message.setMessage(destNickName + "你好[" + userNickName
							+ "]没有同意你的好友申请");
					fjr.setMessage("您已拒绝了" + destNickName + "的申请");
				}

				messageServiceImpl.joinSendQueue(gson.toJson(message));

				JsonTool.printMsg(response, gson.toJson(fjr));

			} else {

				fjr.setFlag(0);
				fjr.setCtrl("t");
				fjr.setMessage("参数错误");

				JsonTool.printMsg(response, gson.toJson(fjr));
			}

		} catch (Exception e) {

			fjr.setFlag(0);
			fjr.setCtrl("t");
			fjr.setMessage(e.getMessage());

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}
}
