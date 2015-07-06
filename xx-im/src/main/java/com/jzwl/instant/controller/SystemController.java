package com.jzwl.instant.controller;

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
import com.jzwl.instant.MessageManager;
import com.jzwl.instant.SessionManager;
import com.jzwl.instant.pojo.FormatJsonResult;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.util.InstantConstant;
import com.jzwl.instant.util.JsonTool;

@Controller
@RequestMapping("/system")
public class SystemController {

	Logger log = LoggerFactory.getLogger(SystemController.class);

	private Gson gson = new Gson();

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

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

			String username = request.getParameter("username");//发起人
			String userNickName = request.getParameter("user_nickname");

			String destUsername = request.getParameter("dest_username");//接收人
			String destNickName= request.getParameter("dest_nickname");
			if (null != username && null != destUsername) {

				IoSession destSession = SessionManager.getSession(destUsername);

				if (SessionManager.isAvaible(destSession)) {

					MyMessage message = new MyMessage();

					message.setModel(InstantConstant.SYS);

					message.setUsername(username);

					message.setTousername(destUsername);

					message.setMessage(destNickName+"你好[" + userNickName + "]要加您为好友是否同意");

					message.putExtKey("action", "addfriend");

					MessageManager.joinSendQueue(redisService,
							gson.toJson(message));

					fjr.setFlag(1);
					fjr.setCtrl("t");
					fjr.setMessage("已经发送了请求，等待对方验证");

					JsonTool.printMsg(response, gson.toJson(fjr));

				} else {
					fjr.setFlag(0);
					fjr.setCtrl("t");
					fjr.setMessage("对方不在线或者已经失去连接，请稍后再试");

					JsonTool.printMsg(response, gson.toJson(fjr));
				}

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
	 * 请求添加对方为好友
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/agreeFriend")
	public void agreeFriend(HttpServletRequest request,
			HttpServletResponse response) {
		FormatJsonResult fjr = new FormatJsonResult();
		try {

			String username = request.getParameter("username");//接收人
			String userNickName = request.getParameter("user_nickname");
			String destUsername = request.getParameter("dest_username");//发起人
			String destNickName= request.getParameter("dest_nickname");
			
			String isAgreen=request.getParameter("is_agree");

			if (null != username && null != destUsername) {

				IoSession destSession = SessionManager.getSession(destUsername);

				if (SessionManager.isAvaible(destSession)) {

					MyMessage message = new MyMessage();

					message.setModel(InstantConstant.SYS);

					message.setUsername(username);

					message.setTousername(destUsername);

					message.putExtKey("action", "agreefriend");

					fjr.setFlag(1);
					fjr.setCtrl("t");

					if("1".equals(isAgreen)){//同意
						message.setMessage(destNickName+"你好[" + userNickName + "]同意了您的好友邀请，现在你们可以聊天了");
						fjr.setMessage("您已经同意了"+destNickName+"的申请");
						
						//建立好友关系
						
						
					}else{
						message.setMessage(destNickName+"你好[" + userNickName + "]没有同意你的好友申请");
						fjr.setMessage("您已拒绝了"+destNickName+"的申请");
					}

					MessageManager.joinSendQueue(redisService,
							gson.toJson(message));
					JsonTool.printMsg(response, gson.toJson(fjr));

				} else {
					fjr.setFlag(0);
					fjr.setCtrl("t");
					fjr.setMessage("对方不在线或者已经失去连接，请稍后再试");

					JsonTool.printMsg(response, gson.toJson(fjr));
				}

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
