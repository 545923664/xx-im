package com.jzwl.instant.pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserInfo {

	/**
	 * 用户唯一标示符
	 */
	private String username;

	/**
	 * 登陆账号
	 */
	private String account;

	
	/**
	 * 密码
	 */
	private String password;

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
	 * 坐标
	 */
	private Map<String, Double> loc = new HashMap<String, Double>();

	/**
	 * 好友列表
	 */
	private Set<String> friends = new HashSet<String>();

	/**
	 * 拥有的群（创建|加入）
	 */
	private Set<String> groups = new HashSet<String>();

	/**
	 * 和目标的距离
	 */
	private double distance;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 设置经纬度
	 * 
	 * @param lng
	 * @param lat
	 */
	public void setLoction(double lng, double lat) {
		this.loc.put("lng", lng);
		this.loc.put("lat", lat);
	}

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

	public Map<String, Double> getLoc() {

		if (null != this.loc) {
			return loc;
		} else {
			return new HashMap<String, Double>();
		}
	}

	public void setLoc(Map<String, Double> loc) {
		this.loc = loc;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
