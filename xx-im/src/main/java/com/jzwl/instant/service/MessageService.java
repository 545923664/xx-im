package com.jzwl.instant.service;

import com.jzwl.instant.pojo.MyMessage;

/**
 * 管理所有的消息CRUD 4 redis or monggo
 * 
 * @author xx
 * 
 */
public interface MessageService {

	/**
	 * 保存离线消息
	 * 
	 * @param mongoService
	 * @param msg
	 */
	public void saveLeavelMessage(MyMessage msg);

	// 发送曾经离线的消息
	public void sendLeaveMessage(String username);

	// 清理离线
	public void clearLeaveMessage(MyMessage msg);

	/**
	 * 加入发送队列
	 * 
	 * @param mongoService
	 * @param msg
	 */
	public void joinSendQueue(String json);

	/**
	 * 获取下一个发送的消息
	 * 
	 * @param redisService
	 * @return
	 */
	public String getNextSendMessage();

	/**
	 * 加入预处理队列
	 * 
	 * @param redisService
	 * @param json
	 */
	public void joinCtrlQueue(String json);

	/**
	 * 获取一下个预处理消息
	 * 
	 * @param redisService
	 * @param json
	 * @return
	 */
	public String getNextCtrlMessage();
}
