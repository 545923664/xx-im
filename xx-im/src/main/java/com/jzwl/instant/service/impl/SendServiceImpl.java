package com.jzwl.instant.service.impl;

import java.util.Set;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.pojo.ServiceMessage;
import com.jzwl.instant.pojo.UserInfo;
import com.jzwl.instant.service.MessageService;
import com.jzwl.instant.service.SendService;
import com.jzwl.instant.service.SessionService;
import com.jzwl.instant.service.UserService;
import com.jzwl.instant.util.DateUtil;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.L;

/**
 * 发送消息
 * 
 * @author Administrator
 * 
 */
@Component
public class SendServiceImpl implements SendService {

	public final String sys_on_brocast = "sys_on_brocast";
	public final String sys_off_brocast = "sys_off_brocast";

	private static int send_faile_max_time = 2;

	private static Gson gson = new Gson();

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private MessageService messageService;

	/**
	 * 发送队列中的消息
	 * 
	 * @param json
	 * @param redisService
	 * @param mongoService
	 */
	public void sendCacheMessage(String json) {

		try {

			boolean sendFlag = false;

			MyMessage msg = gson.fromJson(json, MyMessage.class);
			String username = msg.getUsername();
			String toUsername = msg.getTousername();
			String model = msg.getModel();
			String isLeavel = msg.getExtValue("leavel");// 是否是离线

			String destUsername;

			if (IC.LOGIN.equals(model) || IC.PING.equals(model)) {// 登陆|ping发给自己
				destUsername = username;
			} else {// 其他发给好友|群聊
				destUsername = toUsername;
			}

			// /////////////真正发送//////////////////////////////////
			sendFlag = sessionService.write(destUsername, json);
			// /////////////真正发送//////////////////////////////////

			L.out(">>>>>>>>>>>>>>>>>>>" + json);
			L.out(">>>>>>>>>>>>>>>>>>>" + sendFlag);

			// 解决没有发送成功的消息
			if (!model.equals(IC.LOGIN) && !sendFlag) {
				String mid = msg.getMsgid();

				if (null != mid && mid.length() > 5) {
					String key = IC.send_faile_times_key + "." + mid;

					String sendFaileTimes = redisService.get(key);

					if (null != sendFaileTimes) {
						int times = Integer.parseInt(sendFaileTimes);

						if (times > send_faile_max_time) {// 超过上限不予发送

							// 如果此消息已经属于离线则不再保存
							if (null == isLeavel || !"".equals(1)) {// 不是离线则保存
								messageService.saveLeavelMessage(msg);
							}

							L.out("发送失败" + send_faile_max_time + "次");
						} else {// 发送失败下一次登陆继续给发
							times++;
							redisService.set(key, times + "", IC.DAY_1);
							messageService.joinSendQueue(json);
						}

					} else {// 初次发送失败
						redisService.set(key, 1 + "", IC.DAY_1);
						messageService.joinSendQueue(json);

					}

				} else {
					L.out(">>>>>>>>>>>>>>>>>>>废弃" + json);
				}

			} else if (!model.equals(IC.LOGIN) && sendFlag) {// 发送成功
				if (null != isLeavel && "1".equals(isLeavel)) {
					// 清理离线
					messageService.clearLeaveMessage(msg);

				}
			}

		} catch (Exception e) {
			L.out(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 发送系统用户在线消息
	 * 
	 * @param redisService
	 * @param sysMessageType
	 * @param username
	 */
	/**
	 * @param redisService
	 * @param sysMessageType
	 * @param username
	 */
	public void sendSystemOnlineInfoMessage(String sysMessageType,
			String username) {
		try {

			if (sysMessageType.equals(sys_on_brocast)
					|| sysMessageType.equals(sys_off_brocast)) {// 上线通知
				// 模拟改用户给在线的没一个人发一条消息

				UserInfo user = userService.getUserInfoFromDB(username);

				if (null != user) {
					String sysMessage = "用户[";

					if (sysMessageType.equals(sys_on_brocast)) {// on
						sysMessage = sysMessage + user.getUserNickName()
								+ "]上线了,ID是" + user.getUsername();
					} else if (sysMessageType.equals(sys_off_brocast)) {
						sysMessage = sysMessage + user.getUserNickName()
								+ "],ID是" + username + "掉线滚犊子了！";
					}

					Set<String> friends = user.getFriends();

					for (String toUsername : friends) {

						MyMessage message = new MyMessage();

						message.setModel(IC.CHAT);
						message.setMessageType("0");
						message.setUsername(username);
						message.setTousername(toUsername);
						message.setMessage(sysMessage);
						message.setDate(DateUtil.getDate());
						message.putExtKey("tip", "1");// 提示类

						messageService.joinSendQueue(gson.toJson(message));

					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 新成员加入群通知
	 * 
	 * @param joinUsername
	 * @param gid
	 * @param mongoService
	 * @param redisService
	 */
	public void sendGroupNotifiMessage(String joinUsername, String gid) {

		String info = "新人加入" + joinUsername;

		sendGroupNotifiMessage(joinUsername, gid, info, mongoService,
				redisService);

	}

	/**
	 * 发送群通知（公共）
	 * 
	 * @param username
	 * @param gid
	 * @param message
	 */
	public void sendGroupNotifiMessage(String username, String gid,
			String info, MongoService mongoService, RedisService redisService) {

		MyMessage message = new MyMessage();

		message.setModel(IC.GROUP_CHAT);
		message.setMessageType("0");
		message.setUsername(username);
		message.setMessage(info);
		message.setDate(DateUtil.getDate());

		message.putExtKey("fromGroupId", gid);

		message.putExtKey("tip", "1");// 提示类

		messageService.joinCtrlQueue(gson.toJson(message));

	}

	/**
	 * 发送群公告到个人
	 * 
	 * @param username
	 * @param gid
	 * @param info
	 * @param mongoService
	 * @param redisService
	 */
	public void sendGroupMemberNotifiMessage(String username, String gid,
			String info, MongoService mongoService, RedisService redisService) {

		MyMessage message = new MyMessage();

		message.setModel(IC.CHAT);
		message.setMessageType("0");
		message.setUsername(username);
		message.setMessage(info);
		message.setDate(DateUtil.getDate());
		message.putExtKey("tip", "1");// 提示类

		messageService.joinSendQueue(gson.toJson(message));

	}

	/**
	 * 发送提示类
	 * 
	 * @param session
	 * @param myMessage
	 */
	public void sendErrTip(IoSession session) {

		String info = "sx format is error";

		sendGroupErrTip(session, info);
	}

	/**
	 * 发送群提示类
	 * 
	 * @param session
	 * @param myMessage
	 */
	public void sendGroupErrTip(IoSession session, String info) {

		MyMessage message = new MyMessage();

		message.setModel(IC.ERROR);
		message.setMessage(info);
		message.setDate(DateUtil.getDate());
		message.putExtKey("tip", "1");// 提示类

		session.write(gson.toJson(message));

	}

	/**
	 * 发送服务消息
	 * 
	 * @param username
	 * @param serviceMessage
	 */
	public void sendServiceMessage(String username,
			ServiceMessage serviceMessage) {

		MyMessage message = new MyMessage();

		message.setModel(IC.SERVICE_CHAT);
		message.setMessageType("3");
		message.setUsername(IC.systemUid);
		message.setTousername(username);
		message.setMessage("service");
		message.setDate(DateUtil.getDate());

		message.setObj(serviceMessage);

		String res = gson.toJson(message);
		messageService.joinSendQueue(res);

		L.out(res);

	}
}
