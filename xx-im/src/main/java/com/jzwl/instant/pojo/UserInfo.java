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

	/**
	 * 默认不在线1=在线 0不在线
	 * 
	 */
	private String isOnline = "0";
	
	/**
	 * 默认不是好友 1=是好友 0=不是
	 */
	private String isFriend="0";

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

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

	public String getIsFriend() {
		return isFriend;
	}

	public void setIsFriend(String isFriend) {
		this.isFriend = isFriend;
	}
	
	

}
