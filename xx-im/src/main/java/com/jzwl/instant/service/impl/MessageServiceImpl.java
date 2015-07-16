package com.jzwl.instant.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.service.MessageService;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.L;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 管理所有的消息CRUD 4 redis or monggo
 * 
 * @author xx
 * 
 */
@Component
public class MessageServiceImpl implements MessageService {

	private Gson gson = new Gson();

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	/**
	 * 保存离线消息
	 * 
	 * @param mongoService
	 * @param msg
	 */
	public void saveLeavelMessage(MyMessage msg) {

		String model = msg.getModel();

		if (model.equals(IC.CHAT) || model.equals(IC.GROUP_CHAT)) {// 单 群

			String tip = msg.getExtValue("tip");// 提示类辅助消息不离线

			if (null == tip || !tip.equals("1")) {// tip 类不放入离线

				msg.putExtKey("leavel", "1");// 代表离线
				mongoService.save(IC.mongodb_leavel_message, msg);

			} else {
				L.out("<><>丢失<><>" + gson.toJson(msg));
			}
		} else if (model.equals(IC.SYS)) {// 系统消息

			msg.putExtKey("leavel", "1");// 代表离线
			mongoService.save(IC.mongodb_leavel_message, msg);
		}

	}

	// 发送曾经离线的消息
	public void sendLeaveMessage(String username) {
		try {
			// 将发送失败的消息再次发送
			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("tousername", username);
			// cond.put("model", IC.CHAT);//只要是离线都推送

			List<DBObject> leavelMessageList = mongoService.findList(
					IC.mongodb_leavel_message, cond);

			for (DBObject dbObject : leavelMessageList) {

				String json = gson.toJson(dbObject.toMap());

				joinSendQueue(json);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 清理离线
	public void clearLeaveMessage(MyMessage msg) {
		try {
			mongoService.del(IC.mongodb_leavel_message, new BasicDBObject(
					"msgid", msg.getMsgid()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 加入发送队列
	 * 
	 * @param mongoService
	 * @param msg
	 */
	public void joinSendQueue(String json) {
		redisService.in(IC.send_message_queus_key, json);

	}

	/**
	 * 获取下一个发送的消息
	 * 
	 * @param redisService
	 * @return
	 */
	public String getNextSendMessage() {
		return redisService.out(IC.send_message_queus_key);

	}

	/**
	 * 加入预处理队列
	 * 
	 * @param redisService
	 * @param json
	 */
	public void joinCtrlQueue(String json) {
		redisService.in(IC.recieve_message_queus_key, json);

	}

	/**
	 * 获取一下个预处理消息
	 * 
	 * @param redisService
	 * @param json
	 * @return
	 */
	public String getNextCtrlMessage() {
		return redisService.out(IC.recieve_message_queus_key);
	}

}
