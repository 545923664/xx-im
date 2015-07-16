package com.jzwl.instant;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.pojo.UserInfo;
import com.jzwl.instant.service.BootstarpService;
import com.jzwl.instant.service.MessageService;
import com.jzwl.instant.service.SendService;
import com.jzwl.instant.service.SessionService;
import com.jzwl.instant.service.UserService;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.L;

public class MinaServerHandler extends IoHandlerAdapter {

	Logger log = LoggerFactory.getLogger(MinaServerHandler.class);

	private Gson gson = new Gson();

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	@Autowired
	private BootstarpService bootstarpService;

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private SendService sendService;

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
				int userStatus = sessionService.check(username);

				String statusUserInfo = "";
				if (userStatus == 0) {
					statusUserInfo = "当前正常";
				} else if (userStatus == 1) {
					statusUserInfo = "当前不存在";
				} else if (userStatus == 2) {
					statusUserInfo = "当前断线";
				}
				L.out("<<<<<<<<<<<<<<<<<<<<<<<<<" + statusUserInfo);

				if (IC.LOGIN.equals(model)) {// 登陆模块
					// 加入服务器
					sessionService.join(username, currentSession, msg);
					// 登陆
					messageService.joinCtrlQueue(json);

				} else if (IC.CHAT.equals(model)) {// 聊天模块
					// 检查状态
					if (userStatus != 0) {// 需要重新加入
						sessionService.join(username, currentSession, msg);
						// 登陆完成后推送离线
						messageService.sendLeaveMessage(username);
					}

					String toUserName = msg.getTousername();

					int toUserStatus = sessionService.check(toUserName);

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
						messageService.joinCtrlQueue(json);

					} else {// 对方不可用，保存离线
						messageService.saveLeavelMessage(msg);

					}
				} else if (IC.GROUP_CHAT.equals(model)) {
					// 检查状态
					if (userStatus != 0) {// 需要重新加入
						sessionService.join(username, currentSession, msg);
						// 登陆完成后推送离线
						messageService.sendLeaveMessage(username);
					}

					String fromGroupId = msg.getExtValue("fromGroupId");

					if (null != fromGroupId) {
						messageService.joinCtrlQueue(json);
					} else {
						sendService.sendGroupErrTip(currentSession,
								"sx fromGroupId is null ! send your sister!");
					}

				} else if (IC.PING.equals(model)) {// ping
					messageService.joinCtrlQueue(json);
				} else if (IC.SYS.equals(model)) {// 系统信息：添加好友等等
					// http发送 暂时不做处理
				}

			} else {
				sendService.sendErrTip(currentSession);
			}

		} catch (Exception e) {
			log.error("<<<mina server handler exc >>>" + e.getMessage());
			sendService.sendErrTip(currentSession);
		}

	}

	@Override
	public void exceptionCaught(IoSession session, Throwable e) {
		L.out("exceptionCaught|" + session.toString());

		String address = session.getRemoteAddress().toString();

		if (null != address) {
			UserInfo user = userService.getUserInfoFromDBByAddress(address);

			if (null != user) {
				sendService.sendSystemOnlineInfoMessage(
						SendService.sys_off_brocast, user.getUsername());

			}
		}

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		L.out("sessionClosed|" + session.toString());

		String address = session.getRemoteAddress().toString();

		if (null != address) {
			UserInfo user = userService.getUserInfoFromDBByAddress(address);

			if (null != user) {
				sendService.sendSystemOnlineInfoMessage(
						sendService.sys_off_brocast, user.getUsername());

			}
		}

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		// 开始轮训
		bootstarpService.busy();
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		super.sessionIdle(session, status);
		// 暂停轮训
		bootstarpService.idle();

	}

}