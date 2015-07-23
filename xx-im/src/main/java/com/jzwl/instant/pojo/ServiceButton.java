package com.jzwl.instant.pojo;
/**
 * 服务号按钮
 * @author xx
 *
 */
public class ServiceButton {

	private String name;//按钮名称【显示】

	private String value;// 与name对应

	private String location;// L C R 左中右

	private String bid;//id
	
	private String pid;// #根 101 子节点

	private String type;// menu btn

	private String url;//link url

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}
	
	

}
