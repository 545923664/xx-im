package com.jzwl.instant;

import java.util.Set;

import org.apache.mina.core.session.IoSession;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.pojo.UserInfo;
import com.jzwl.instant.util.DateUtil;
import com.jzwl.instant.util.InstantConstant;
import com.jzwl.instant.util.L;

/**
 * 发送消息
 * 
 * @author Administrator
 * 
 */
public class SendMessage {

	public static final String sys_on_brocast = "sys_on_brocast";
	public static final String sys_off_brocast = "sys_off_brocast";

	private static int send_faile_max_time = 3;

	private static Gson gson = new Gson();

	/**
	 * 发送队列中的消息
	 * 
	 * @param json
	 * @param redisService
	 * @param mongoService
	 */
	public static void sendCacheMessage(String json, RedisService redisService,
			MongoService mongoService) {

		try {

			boolean sendFlag = false;

			MyMessage msg = gson.fromJson(json, MyMessage.class);
			String username = msg.getUsername();
			String toUsername = msg.getTousername();
			String model = msg.getModel();

			String destUsername;

			if (InstantConstant.LOGIN.equals(model)
					|| InstantConstant.PING.equals(model)) {// 登陆|ping发给自己
				destUsername = username;
			} else {// 其他发给好友
				destUsername = toUsername;
			}

			sendFlag = SessionManager.write(destUsername, json);
			L.out(">>>>>>>>>>>>>>>>>>>" + json);
			L.out(">>>>>>>>>>>>>>>>>>>" + sendFlag);

			// 解决没有发送成功的消息
			if (!model.equals(InstantConstant.LOGIN) && !sendFlag) {
				String mid = msg.getMsgid();
				String key = InstantConstant.send_faile_times_key + "." + mid;

				String sendFaileTimes = redisService.get(key);

				if (null != sendFaileTimes) {
					int times = Integer.parseInt(sendFaileTimes);

					if (times > send_faile_max_time) {// 超过上限不予发送
						MessageManager.saveLeavelMessage(mongoService, msg);
						L.out("发送失败" + send_faile_max_time + "次");
					} else {// 发送失败下一次登陆继续给发
						times++;
						redisService
								.set(key, times + "", InstantConstant.DAY_1);
						MessageManager.joinSendQueue(redisService, json);
					}

				} else {// 初次发送失败
					redisService.set(key, 1 + "", InstantConstant.DAY_1);
					MessageManager.joinSendQueue(redisService, json);

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
	public static void sendSystemMessage(RedisService redisService,
			String sysMessageType, String username) {
		try {

			if (sysMessageType.equals(sys_on_brocast)
					|| sysMessageType.equals(sys_off_brocast)) {// 上线通知
				// 模拟改用户给在线的没一个人发一条消息

				UserInfo user = UserManager.getUserInfo(redisService, username);

				if (null != user) {
					String sysMessage = "用户[";

					if (sysMessageType.equals(sys_on_brocast)) {// on
						sysMessage = sysMessage + user.getUserNickName()
								+ "]上线了,ID是" + user.getUsername();
					} else if (sysMessageType.equals(sys_off_brocast)) {
						sysMessage = sysMessage + user.getUserNickName()
								+ "],ID是" + username + "掉线滚犊子了！";
					}

					Set<String> usernames = SessionManager.usersMap.keySet();

					for (String toUsername : usernames) {
						if (!toUsername.equals(username)) {
							IoSession toUserSession = SessionManager
									.getSession(toUsername);

							if (SessionManager.isAvaible(toUserSession)) {
								MyMessage message = new MyMessage();

								message.setModel(InstantConstant.CHAT);
								message.setMessageType("0");
								message.setUsername(username);
								message.setTousername(toUsername);
								message.setMessage(sysMessage);
								message.setDate(DateUtil.getDate());

								MessageManager.joinSendQueue(redisService,
										gson.toJson(message));

							}
						}
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 发送提示类
	 * 
	 * @param session
	 * @param myMessage
	 */
	public static void sendErrTip(IoSession session) {
		MyMessage message = new MyMessage();

		message.setModel(InstantConstant.ERROR);
		message.setMessage("传输格式错误");
		message.setDate(DateUtil.getDate());

		session.write(gson.toJson(message));

	}
}
