package com.jzwl.instant.pojo;

import java.util.HashSet;
import java.util.Set;

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
	private String isFriend = "0";

	/**
	 * 经度
	 */
	private double lng;

	/**
	 * 维度
	 */
	private double lat;

	/**
	 * 好友列表
	 */
	private Set<String> friends = new HashSet<String>();

	/**
	 * 拥有的群（创建|加入）
	 */
	private Set<String> groups = new HashSet<String>();

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

	public Set<String> getGroups() {
		return groups;
	}

	public void setGroups(Set<String> groups) {
		this.groups = groups;
	}

	public Set<String> getFriends() {
		return friends;
	}

	public void setFriends(Set<String> friends) {
		this.friends = friends;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}
	
	

}
