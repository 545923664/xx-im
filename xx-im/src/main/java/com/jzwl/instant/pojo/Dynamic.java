package com.jzwl.instant.pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 动态
 * 
 * @author xx
 * 
 */
public class Dynamic {

	private String did;

	private String uid;

	private String userNickName;

	private String userAvatar;

	private String status;

	private String text;

	/**
	 * 点赞的个数
	 */
	private int zanCount;
	/**
	 * 点赞的人id
	 */
	private Set<String> zanUsers;

	/**
	 * 点赞的人用户信息
	 */
	private Map<String, String> zanUsersInfo = new HashMap<String, String>();

	/**
	 * 图片个数
	 */
	private int picCount;

	/**
	 * 图片路径
	 */
	private Set<String> picUrls = new HashSet<String>();

	private long publishDate;

	private Set<Comment> comments = new HashSet<Comment>();

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getPicCount() {
		return picCount;
	}

	public void setPicCount(int picCount) {
		this.picCount = picCount;
	}

	public Set<String> getPicUrls() {
		return picUrls;
	}

	public void setPicUrls(Set<String> picUrls) {
		this.picUrls = picUrls;
	}

	public long getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(long publishDate) {
		this.publishDate = publishDate;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public int getZanCount() {
		return zanCount;
	}

	public void setZanCount(int zanCount) {
		this.zanCount = zanCount;
	}

	public Set<String> getZanUsers() {
		return zanUsers;
	}

	public void setZanUsers(Set<String> zanUsers) {
		this.zanUsers = zanUsers;
	}

	public Map<String, String> getZanUsersInfo() {
		return zanUsersInfo;
	}

	public void setZanUsersInfo(Map<String, String> zanUsersInfo) {
		this.zanUsersInfo = zanUsersInfo;
	}

}
