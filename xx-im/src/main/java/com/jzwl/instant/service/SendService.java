package com.jzwl.instant.service;

import org.apache.mina.core.session.IoSession;

import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.ServiceMessage;

/**
 * 发送消息
 * 
 * @author Administrator
 * 
 */
public interface SendService {

	public final String sys_on_brocast = "sys_on_brocast";
	public final String sys_off_brocast = "sys_off_brocast";

	/**
	 * 发送队列中的消息
	 * 
	 * @param json
	 * @param redisService
	 * @param mongoService
	 */
	public void sendCacheMessage(String json);

	/**
	 * 发送系统用户在线消息
	 * 
	 * @param redisService
	 * @param sysMessageType
	 * @param username
	 */
	public void sendSystemOnlineInfoMessage(String sysMessageType,
			String username);

	/**
	 * 新成员加入群通知
	 * 
	 * @param joinUsername
	 * @param gid
	 * @param mongoService
	 * @param redisService
	 */
	public void sendGroupNotifiMessage(String joinUsername, String gid);

	/**
	 * 发送群通知（公共）
	 * 
	 * @param username
	 * @param gid
	 * @param message
	 */
	public void sendGroupNotifiMessage(String username, String gid,
			String info, MongoService mongoService, RedisService redisService);

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
			String info, MongoService mongoService, RedisService redisService);

	/**
	 * 发送提示类
	 * 
	 * @param session
	 * @param myMessage
	 */
	public void sendErrTip(IoSession session);

	/**
	 * 发送群提示类
	 * 
	 * @param session
	 * @param myMessage
	 */
	public void sendGroupErrTip(IoSession session, String info);

	public void sendServiceMessage(String username,
			ServiceMessage serviceMessage);
}
