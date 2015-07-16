package com.jzwl.instant.service;

import org.apache.mina.core.session.IoSession;

import com.jzwl.instant.pojo.MyMessage;

/**
 * 管理所有会话
 * 
 * @author xx
 * 
 */
public interface SessionService {

	// 检查session是否可用
	public boolean isAvaible(IoSession session);

	// 加入状态
	public void join(String username, IoSession newSession, MyMessage msg);

	// 断线
	public void disConnect(String username);

	// 通过username查找session
	public IoSession getSession(String username);

	// 检查session状态
	// 0=正常
	// 1=不存在
	// 2=存在不可用
	public int check(String username);

	/**
	 * 最终发送出口
	 * 
	 * @param username
	 * @param session
	 * @param txt
	 * @return boolean
	 */
	public boolean write(String username, String txt);

	/**
	 * 设置session属性
	 * 
	 * @param session
	 * @param username
	 */
	public void setSessionAttribute(IoSession session, String username);

	public boolean isPing(IoSession session, String username);
}
