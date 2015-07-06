package com.jzwl.instant.pojo;


public class AixinMessage {

	private String id;

	private String fromUserId;
	private String fromGroupId;// 可选
	private String toUserId;
	private String toGroupId;// 可选
	private String tranType;// system|single 单聊| group 群聊
	private String msgType;// text 普通文本 | file 文件
	private String msgTextContent;// 文本部分
	private String msgFileUrl;// 文件地址

	/**
	 * 1代表在客户端未发送成功 2 代表客户端已经发送成功 2代表在服务端未推送成功 3 代表服务端已经推送成功
	 */
	private String msgStatus;//

	private String sendDate;// 时分秒
	private String recieveDate;// 时分秒

	private String createDate;// 入库时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getFromGroupId() {
		return fromGroupId;
	}

	public void setFromGroupId(String fromGroupId) {
		this.fromGroupId = fromGroupId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getToGroupId() {
		return toGroupId;
	}

	public void setToGroupId(String toGroupId) {
		this.toGroupId = toGroupId;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgTextContent() {
		return msgTextContent;
	}

	public void setMsgTextContent(String msgTextContent) {
		this.msgTextContent = msgTextContent;
	}

	public String getMsgFileUrl() {
		return msgFileUrl;
	}

	public void setMsgFileUrl(String msgFileUrl) {
		this.msgFileUrl = msgFileUrl;
	}

	public String getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getRecieveDate() {
		return recieveDate;
	}

	public void setRecieveDate(String recieveDate) {
		this.recieveDate = recieveDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
