package com.jzwl.instant.pojo;

public class UserInfo {

	/**
	 * 用户唯一标示符
	 */
	private String username;

	/**
	 * 用户昵称
	 */
	private String userNickName;

	/**
	 * 连接地址
	 */
	private String connectAddress;

	private long sessionID;

	public long getSessionID() {
		return sessionID;
	}

	public void setSessionID(long sessionID) {
		this.sessionID = sessionID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getConnectAddress() {
		return connectAddress;
	}

	public void setConnectAddress(String connectAddress) {
		this.connectAddress = connectAddress;
	}

}
