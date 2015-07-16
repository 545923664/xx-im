package com.jzwl.instant.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.jzwl.instant.pojo.GroupInfo;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.service.GroupService;
import com.jzwl.instant.service.MessageService;
import com.jzwl.instant.service.RecieveService;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.L;

/**
 * 处理消息
 * 
 * @author Administrator
 * 
 */
@Component
public class RecieveServiceImpl implements RecieveService {

	private static Gson gson = new Gson();

	@Autowired
	private GroupService groupService;

	@Autowired
	private MessageService messageService;

	// 接受并保存消息
	public void receive(String json) {

		try {
			MyMessage msg = gson.fromJson(json, MyMessage.class);
			String username = msg.getUsername();
			String model = msg.getModel();

			if (IC.LOGIN.equals(model)) {// 加入IM

				msg.setMessage("success");

				String res = gson.toJson(msg);

				messageService.joinSendQueue(res);
				// 登陆完成后推送离线
				messageService.sendLeaveMessage(username);

			} else if (IC.CHAT.equals(model)) {// 单聊
				messageService.joinSendQueue(json);

			} else if (IC.GROUP_CHAT.equals(model)) {// 群聊
				// 进行分发
				String fromGroupId = msg.getExtValue("fromGroupId");

				GroupInfo group = groupService.getGroupInfo(fromGroupId);

				Set<String> member = group.getMember();

				for (String destUsername : member) {

					if (!username.equals(destUsername)) {// 不能发给自己

						msg.setTousername(destUsername);

						messageService.joinSendQueue(gson.toJson(msg));
					}

				}

			} else if (IC.PING.equals(model)) {// ping

				msg.setMessage("pong");

				messageService.joinSendQueue(gson.toJson(msg));

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
