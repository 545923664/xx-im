package com.jzwl.instant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.util.InstantConstant;
import com.mongodb.DBObject;

/**
 * 管理所有的消息CRUD 4 redis or monggo
 * 
 * @author xx
 * 
 */
public class MessageManager {

	private static Gson gson = new Gson();

	/**
	 * 保存离线消息
	 * 
	 * @param mongoService
	 * @param msg
	 */
	public static void saveLeavelMessage(MongoService mongoService,
			MyMessage msg) {
		mongoService.save(InstantConstant.mongodb_leavel_message, msg);
	}

	// 发送曾经离线的消息
	public static void sendLeaveMessage(String username,
			RedisService redisService, MongoService mongoService) {
		try {
			// 将发送失败的消息再次发送
			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("tousername", username);
			cond.put("model", InstantConstant.CHAT);

			List<DBObject> leavelMessageList = mongoService.findOne(
					InstantConstant.mongodb_leavel_message, cond);

			for (DBObject dbObject : leavelMessageList) {

				String json = gson.toJson(dbObject.toMap());

				joinSendQueue(redisService, json);

				mongoService.del(InstantConstant.mongodb_leavel_message,
						dbObject);
			}
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
	public static void joinSendQueue(RedisService redisService, String json) {
		redisService.in(InstantConstant.send_message_queus_key, json);

	}


	/**
	 * 获取下一个发送的消息
	 * 
	 * @param redisService
	 * @return
	 */
	public static String getNextSendMessage(RedisService redisService) {
		return redisService.out(InstantConstant.send_message_queus_key);

	}

	/**
	 * 加入预处理队列
	 * 
	 * @param redisService
	 * @param json
	 */
	public static void joinCtrlQueue(RedisService redisService, String json) {
		redisService.in(InstantConstant.recieve_message_queus_key, json);

	}

	/**
	 * 获取一下个预处理消息
	 * 
	 * @param redisService
	 * @param json
	 * @return
	 */
	public static String getNextCtrlMessage(RedisService redisService) {
		return redisService.out(InstantConstant.recieve_message_queus_key);
	}

}
