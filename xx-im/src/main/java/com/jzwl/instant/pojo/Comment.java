package com.jzwl.instant.pojo;

/**
 * 评论
 * 
 * @author xx
 * 
 */
public class Comment {

	private String cid;
	private String pid;

	private String did;

	private String fromUid;
	private String fromUserNcikName;
	private String fromUserAvatar;

	private String toUid;
	private String toUserNickName;
	private String toUserAvatar;

	private String message;

	private long commentDate;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getFromUid() {
		return fromUid;
	}

	public void setFromUid(String fromUid) {
		this.fromUid = fromUid;
	}

	public String getToUid() {
		return toUid;
	}

	public void setToUid(String toUid) {
		this.toUid = toUid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(long commentDate) {
		this.commentDate = commentDate;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getFromUserNcikName() {
		return fromUserNcikName;
	}

	public void setFromUserNcikName(String fromUserNcikName) {
		this.fromUserNcikName = fromUserNcikName;
	}

	public String getFromUserAvatar() {
		return fromUserAvatar;
	}

	public void setFromUserAvatar(String fromUserAvatar) {
		this.fromUserAvatar = fromUserAvatar;
	}

	public String getToUserNickName() {
		return toUserNickName;
	}

	public void setToUserNickName(String toUserNickName) {
		this.toUserNickName = toUserNickName;
	}

	public String getToUserAvatar() {
		return toUserAvatar;
	}

	public void setToUserAvatar(String toUserAvatar) {
		this.toUserAvatar = toUserAvatar;
	}

}
