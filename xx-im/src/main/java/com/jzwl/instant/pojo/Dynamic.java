package com.jzwl.instant.pojo;

import java.util.HashSet;
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

	private String status;

	private String text;

	private int picCount;

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

}


