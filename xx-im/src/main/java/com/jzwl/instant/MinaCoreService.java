package com.jzwl.instant;

import java.util.HashMap;
import java.util.Set;

import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.instant.pojo.AixinMessage;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.util.InstantConstant;

public class MinaCoreService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(MinaCoreService.class);

	private Gson gson = new Gson();

	private MongoService mongoService;

	public MinaCoreService(MongoService mongoService) {
		this.mongoService = mongoService;
	}

	/**
	 * relogin
	 * 
	 * @param session
	 * @param msg
	 */
	public void relogin(IoSession session, MyMessage msg) {
		try {

			// username is UUID
			String username = msg.getUsername();

			if (null != username) {
				IoSession oldSession = SessionManager.usersMap.get(username);

				if (null != oldSession) {
					SessionManager.usersMap.remove(username);
					if (oldSession.isConnected()) {
						oldSession.close(true);
					}
				}

				SessionManager.usersMap.put(username, session);

				msg.setMessage("success");
				msg.setTousername(username);
				msg.setUsername("#system#");

				this.controlChatMessage(msg);
				this.pushLeaveMessage(null, session);

			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}

	}

	/**
	 * exit
	 * 
	 * @param session
	 * @param msg
	 */
	public void loginout(IoSession session, MyMessage msg) {
		try {
			String username = msg.getUsername();

			if (null != username
					&& SessionManager.usersMap.containsKey(username)) {
				IoSession oldSession = SessionManager.usersMap.get(username);

				if (null != oldSession) {
					SessionManager.usersMap.remove(username);
					if (oldSession.isConnected()) {
						oldSession.close(true);
						oldSession = null;
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * close connect for username[key]
	 * 
	 * @param session
	 * @param msg
	 */
	public void closeConnect4username(String username) {
		try {
			if (null != username
					&& SessionManager.usersMap.containsKey(username)) {
				IoSession oldSession = SessionManager.usersMap.get(username);

				if (null != oldSession) {
					SessionManager.usersMap.remove(username);
					if (oldSession.isConnected()) {
						oldSession.close(true);
						oldSession = null;
					}
				}

			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * save the client's message to datebase
	 * 
	 * @param aixinMessageService
	 * @param msg
	 */
	public void saveMessage(MyMessage msg) {
		this.saveMessage(msg, false);
	}

	/**
	 * save the client's message to datebase
	 * 
	 * @param aixinMessageService
	 * @param msg
	 */
	public void saveMessage(MyMessage msg, boolean sendFlag) {
		try {

			if (null != msg) {
				mongoService.save(InstantConstant.mongodb_message, msg);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * save the client's message to datebase
	 * 
	 * @param aixinMessageService
	 * @param msg
	 */
	public void updateMessage(MyMessage msg) {
		try {

			// String aixinMessageId = msg.getMessage();
			// if (null != aixinMessageId) {
			// final AixinMessage aixinMessage = aixinMessageService
			// .get(aixinMessageId);
			//
			// if (null != aixinMessage) {
			// // mean: the message is recieve by client
			// aixinMessage.setMsgStatus("3");
			//
			// new Thread(new Runnable() {
			// @Override
			// public void run() {
			// aixinMessageService.update(aixinMessage);
			// }
			// }).start();
			// }
			// }

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * push leave message to login user
	 * 
	 * @param aixinMessageService
	 * @param session
	 */
	public void pushLeaveMessage(Object aixinMessageService, IoSession session) {
		try {

			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("msgStatus", "2");
			param.put("tranType", "chat");

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * send message to every body
	 * 
	 * @param message
	 */
	public void broadcast(String message) {
		try {
			synchronized (SessionManager.usersMap) {
				Set<String> keys = SessionManager.usersMap.keySet();
				int i = 0;
				for (String key : keys) {
					IoSession session = SessionManager.usersMap.get(key);
					if (session.isConnected()) {
						session.write(message);
						System.out.println(++i + "[send key=]" + key
								+ "[message=]" + message);
					} else {
						System.out.println("[send faile key=]" + key
								+ "[session]=" + session.toString()
								+ " is close");
					}

				}

			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * send message to send msg'user the msg is revieve
	 * 
	 * @param message
	 */
	public void sendCallBack(IoSession session, MyMessage msg) {
		try {

			if (null != msg.getMsgid()) {
				MyMessage callBackMessage = new MyMessage();
				callBackMessage.setModel(InstantConstant.CHAT_SEND_ECHO);
				callBackMessage.setDate(System.currentTimeMillis() + "");
				callBackMessage.setMessage(msg.getMsgid());
				callBackMessage.setUsername("#system#");
				callBackMessage.setTousername(msg.getUsername());

				this.controlChatMessage(callBackMessage);

			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * send message to destion user(single chat )
	 * 
	 * @param myMessage
	 */
	public void controlChatMessage(final MyMessage myMessage) {
		try {
			String toUserName = myMessage.getTousername();

			if (null != toUserName) {

				if (null != toUserName
						&& SessionManager.usersMap.containsKey(toUserName)) {

					final IoSession session = SessionManager.usersMap
							.get(toUserName);

					if (null != session && session.isConnected()) {
						final String chatJson = gson.toJson(myMessage);
						LOGGER.error("[send message to]" + toUserName);

						new Thread(new Runnable() {
							@Override
							public void run() {
								WriteFuture writeResult = session
										.write(chatJson);
								writeResult.addListener(new IoFutureListener() {
									public void operationComplete(
											IoFuture future) {
										WriteFuture wfuture = (WriteFuture) future;
										if (wfuture.isWritten()) {// 写入成功
											LOGGER.error("【ok】" + chatJson);
											saveMessage(myMessage, true);
										} else {// 写入失败，自行进行处理
											LOGGER.error("【fail】" + chatJson);
											saveMessage(myMessage, false);
										}

									}
								});
							}
						}).start();

					} else {
						LOGGER.error("[send faile toUserName=]" + toUserName
								+ "[controlChatMessage]=" + session
								+ " is close");
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
