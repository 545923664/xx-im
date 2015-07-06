package com.jzwl.instant.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.jzwl.instant.util.Util;

/**
 * mina transport VO mina 协议
 * 
 * @author Xin.Xu
 * 
 */
public class MyMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	public MyMessage() {
		this.msgid = Util.getUUID();
	}

	private String msgid;

	/**
	 * send用户名
	 */
	private String username;

	/**
	 * 来自哪个群
	 * 
	 */
	private String fromGroupId;

	/**
	 * 用户名
	 */
	private String tousername;

	/**
	 * 密码
	 */
	private String password;
	/**
	 * 类型[login|relogin|chat|brocast|logout]
	 */
	private String model;

	/**
	 * 消息类型[text|amr|pic][0 1 2]
	 */
	private String messageType;

	/**
	 * 聊天类型[单聊0；群聊1]
	 */
	private String chatType;

	/**
	 * 消息内容
	 */
	private String message;
	/**
	 * 消息时间
	 */
	private String date;

	/**
	 * 扩展字段
	 */
	private Map<String, String> ext = new HashMap<String, String>();

	public void putExtKey(String key, String value) {
		if (null != this.ext) {
			this.ext.put(key, value);
		}
	}

	public void putExtKeys(Map<String, String> keys) {
		if (null != this.ext) {
			this.ext.putAll(keys);
		}
	}

	public boolean hasExtKey(String key) {
		if (null != this.ext && this.ext.size() > 0) {
			if (this.ext.containsKey(key)) {
				return true;
			}

		}

		return false;
	}

	public String getExtValue(String key) {
		if (this.hasExtKey(key)) {
			return this.ext.get(key);
		} else {
			return null;
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTousername() {
		return tousername;
	}

	public void setTousername(String tousername) {
		this.tousername = tousername;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getFromGroupId() {
		return fromGroupId;
	}

	public void setFromGroupId(String fromGroupId) {
		this.fromGroupId = fromGroupId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getChatType() {
		return chatType;
	}

	public void setChatType(String chatType) {
		this.chatType = chatType;
	}

	public Map<String, String> getExt() {
		return ext;
	}

	public void setExt(Map<String, String> ext) {
		this.ext = ext;
	}

}