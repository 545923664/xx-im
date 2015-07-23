package com.jzwl.instant.service;

import java.util.List;
import java.util.Set;

import com.jzwl.instant.pojo.ServiceAccount;
import com.jzwl.instant.pojo.ServiceButton;

/**
 * 管理用户信息
 * 
 * @author xx
 * 
 */
public interface ServiceAccountService {

	/**
	 * 添加服务号
	 * 
	 * @param serviceName
	 * @param serviceAvatar
	 * @param functonDesc
	 * @param serviceMaster
	 * @param serviceButton
	 * @return
	 */
	public boolean addServiceAccount(String serviceName, String serviceAvatar,
			String functonDesc, String serviceMaster,
			Set<ServiceButton> serviceButtons);

	/**
	 * 获取所有服务号
	 * 
	 * @return
	 */
	public List<ServiceAccount> getAllServiceAccountList();

	
	/**
	 * 处理服务号请求
	 * 
	 * @param sid
	 * @param bid
	 * @param value
	 */
	public void ctrlServiceAccountRequest(String sid, String bid, String value,
			String uid);
}
