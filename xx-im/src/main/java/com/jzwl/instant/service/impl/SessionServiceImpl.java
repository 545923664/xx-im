package com.jzwl.instant.service.impl;

import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.service.SendService;
import com.jzwl.instant.service.SessionService;
import com.jzwl.instant.service.UserService;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.L;

/**
 * 管理所有会话
 * 
 * @author xx
 * 
 */
@Component
public class SessionServiceImpl implements SessionService {

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	@Autowired
	private UserService userService;

	@Autowired
	private SendService sendService;


	// 检查session是否可用
	public boolean isAvaible(IoSession session) {

		if (null != session && session.isConnected()) {
			return true;
		}

		return false;
	}

	// 加入状态
	public void join(String username, IoSession newSession, MyMessage msg) {

		usersMap.put(username, newSession);

		// 保存用户信息
		userService.setUserInfo(username, newSession, msg);

		if (IC.ON_OFF_LINE_BROCAST) {
			sendService.sendSystemOnlineInfoMessage(SendService.sys_on_brocast,
					username);
		}

		L.out("<<<<<<<<<<<<<<<<<<<<<<<<<加入成功");
	}

	// 断线
	public void disConnect(String username) {
		IoSession dirtySession = getSession(username);
		if (isAvaible(dirtySession)) {
			L.out(">>>>>>>>>>>>>>>>>>>需要断开一个可用session"
					+ dirtySession.toString());
			dirtySession.close(true);
			dirtySession = null;
			usersMap.remove(username);
		}
	}

	// 通过username查找session
	public IoSession getSession(String username) {
		if (usersMap.containsKey(username)) {
			return usersMap.get(username);
		} else {
			return null;
		}

	}

	// 检查session状态
	// 0=正常
	// 1=不存在
	// 2=存在不可用
	public int check(String username) {
		if (usersMap.containsKey(username)) {// 已经连接成
			IoSession oldSession = usersMap.get(username);
			if (!isAvaible(oldSession)) {// 检查是否可用
				return 2;
			} else {
				return 0;
			}

		} else {// 不存在
			return 1;
		}

	}

	/**
	 * 最终发送出口
	 * 
	 * @param username
	 * @param session
	 * @param txt
	 * @return boolean
	 */
	public boolean write(String username, String txt) {
		try {

			IoSession session = getSession(username);

			if (isAvaible(session)) {

				WriteFuture future = session.write(txt);

				future.awaitUninterruptibly(); // 等待发送数据操作完成

				if (future.isWritten()) {
					// 数据已经被成功发送
					return true;
				} else {
					// 数据发送失败
					L.out(">>>>>>>>>>>>>>>>>>>判断发送失败");
					return false;
				}
			} else {
				L.out(">>>>>>>>>>>>>>>>>>>write faile");
				return false;
			}

		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * 设置session属性
	 * 
	 * @param session
	 * @param username
	 */
	public void setSessionAttribute(IoSession session, String username) {
		if (null != session) {
			session.setAttribute("username", username);
		}
	}

	public boolean isPing(IoSession session, String username) {
		MyMessage msg = new MyMessage();

		msg.setModel(IC.PING);
		msg.setMessage("ping");

		return false;
	}

}
