package com.jzwl.instant;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.util.InstantConstant;
import com.jzwl.instant.util.L;

public class MinaServerHandler extends IoHandlerAdapter {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(MinaServerHandler.class);

	Logger log = LoggerFactory.getLogger(MinaServerHandler.class);

	private Gson gson = new Gson();

	private int test = 0;

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	@Override
	public void messageReceived(IoSession currentSession, Object message) {
		try {
			String json = message.toString();

			L.out("<<<<<<<<<<<<<<<<<<<<<<<<<" + json);

			MyMessage msg = gson.fromJson(json, MyMessage.class);

			String username = msg.getUsername();
			String model = msg.getModel();

			if (null != username && null != model) {// 判断登陆状态

				// 检查发送者session状态
				int userStatus = SessionManager.check(username);

				String statusUserInfo = "";
				if (userStatus == 0) {
					statusUserInfo = "当前正常";
				} else if (userStatus == 1) {
					statusUserInfo = "当前不存在";
				} else if (userStatus == 2) {
					statusUserInfo = "当前断线";
				}
				L.out("<<<<<<<<<<<<<<<<<<<<<<<<<" + statusUserInfo);

				if (InstantConstant.LOGIN.equals(model)) {// 登陆模块
					// 加入服务器
					SessionManager.join(redisService, username, currentSession,
							msg);
					// 登陆需要插队到最前面
					MessageManager.joinCtrlQueue(redisService, json);
					// 登陆完成后推送离线
					MessageManager.sendLeaveMessage(username, redisService,
							mongoService);
				} else if (InstantConstant.CHAT.equals(model)) {// 聊天模块
					// 检查状态
					if (userStatus != 0) {// 需要重新加入
						SessionManager.join(redisService, username,
								currentSession, msg);
						// 登陆完成后推送离线
						MessageManager.sendLeaveMessage(username, redisService,
								mongoService);
					}

					String toUserName = msg.getTousername();

					int toUserStatus = SessionManager.check(toUserName);

					String statusToUserInfo = "";
					if (toUserStatus == 0) {
						statusToUserInfo = "对方正常";
					} else if (toUserStatus == 1) {
						statusToUserInfo = "对方不存在";
					} else if (toUserStatus == 2) {
						statusToUserInfo = "对方断线";
					}

					L.out("<<<<<<<<<<<<<<<<<<<<<<<<<" + statusToUserInfo);

					if (toUserStatus == 0) {
						MessageManager.joinCtrlQueue(redisService, json);

					} else {// 对方不可用，保存离线
						MessageManager.saveLeavelMessage(mongoService, msg);

					}
				} else if (InstantConstant.PING.equals(model)) {// ping
					MessageManager.joinCtrlQueue(redisService, json);
				} else if (InstantConstant.SYS.equals(model)) {//系统信息：添加好友等等
					
				}

			} else {
				SendMessage.sendErrTip(currentSession);
			}

		} catch (Exception e) {
			log.error("<<<Exception>>>" + e.getMessage());
		}

	}

	@Override
	public void exceptionCaught(IoSession session, Throwable e) {
		L.out("***************异常" + session.toString());

		Object username_att = session.getAttribute("username");

		if (null != username_att) {
			String username = username_att.toString();

			SendMessage.sendSystemMessage(redisService,
					SendMessage.sys_off_brocast, username);

		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		L.out("***************关闭" + session.toString());
		Object username_att = session.getAttribute("username");

		if (null != username_att) {
			String username = username_att.toString();

			SendMessage.sendSystemMessage(redisService,
					SendMessage.sys_off_brocast, username);

		}

	}

}