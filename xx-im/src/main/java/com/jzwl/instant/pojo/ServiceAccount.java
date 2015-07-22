package com.jzwl.instant.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * 服务号
 * 
 * @author xx
 * 
 */
public class ServiceAccount {

	/**
	 * id
	 */
	private String sid;

	/**
	 * 服务号名称
	 */
	private String serviceName;

	/**
	 * 服务号头像
	 */
	private String serviceAvatar;

	/**
	 * 功能描述
	 */
	private String functionDesc;

	/**
	 * 账号主体
	 */
	private String serviceMaster;

	/**
	 * 按钮信息
	 */
	private Set<ServiceButton> serviceButtons;

	/**
	 * 关注人信息
	 */
	private Set<String> focus = new HashSet<String>();

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceAvatar() {
		return serviceAvatar;
	}

	public void setServiceAvatar(String serviceAvatar) {
		this.serviceAvatar = serviceAvatar;
	}

	public String getFunctionDesc() {
		return functionDesc;
	}

	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}

	public String getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(String serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public Set<ServiceButton> getServiceButtons() {
		return serviceButtons;
	}

	public void setServiceButtons(Set<ServiceButton> serviceButtons) {
		this.serviceButtons = serviceButtons;
	}

	public Set<String> getFocus() {
		return focus;
	}

	public void setFocus(Set<String> focus) {
		this.focus = focus;
	}

}
