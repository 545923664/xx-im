package com.jzwl.instant;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.L;

/**
 * 处理消息
 * 
 * @author Administrator
 * 
 */
public class RecieveMessage {

	private static Gson gson = new Gson();

	// 接受并保存消息
	public static void receive(String json, RedisService redisService,
			MongoService mongoService) {

		try {
			MyMessage msg = gson.fromJson(json, MyMessage.class);
			String username = msg.getUsername();
			String model = msg.getModel();

			if (IC.LOGIN.equals(model)) {// 加入IM

				msg.setMessage("success");

				String res = gson.toJson(msg);

				MessageManager.joinSendQueue(redisService, res);

			} else if (IC.CHAT.equals(model)) {// 聊天

				String fromGroupId = msg.getFromGroupId();

				// is group
				if (null != fromGroupId && !"".equals(fromGroupId.trim())) {

					// List<String> userIdList = new ArrayList<String>();
					// group

					// minaCoreService.controlChatMessage(msg);

				} else {
					// is single
					MessageManager.joinSendQueue(redisService, json);
				}

			} else if (IC.PING.equals(model)) {// ping

				msg.setMessage("pong");

				MessageManager.joinSendQueue(redisService, gson.toJson(msg));

			} else if (IC.RELOGIN.equals(model)) {// 断线重连
				// if client user'session is close and relogin
				// 1->find old session and close and replace the old session
				// minaCoreService.relogin(session, msg);

				// minaCoreService.relogin(aixinMessageService, session, msg);

			} else if (IC.LOGIN_OUT.equals(model)) {// 踢出IM
				// if client user exit server will close the session
				// minaCoreService.loginout(session, msg);

			} else if (IC.CHAT_REC_ECHO.equals(model)) {
				// if a client user recieve a msg need send a mina to server
				// update the message status is 3
				// minaCoreService.updateMessage(msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
			L.out(e.getMessage());
		}

	}

}
