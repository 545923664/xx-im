package com.jzwl.instant.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.ServiceAccount;
import com.jzwl.instant.pojo.ServiceButton;
import com.jzwl.instant.service.ServiceAccountService;
import com.jzwl.instant.util.IC;
import com.mongodb.DBObject;

@Component
public class ServiceAccountServiceImpl implements ServiceAccountService {

	private Gson gson = new Gson();

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	/**
	 * 添加服务号
	 * 
	 * @param serviceName
	 * @param serviceAvatar
	 * @param functonDesc
	 * @param serviceMaster
	 * @param serviceButton
	 */
	public boolean addServiceAccount(String serviceName, String serviceAvatar,
			String functonDesc, String serviceMaster,
			Set<ServiceButton> serviceButtons) {
		try {

			ServiceAccount account = new ServiceAccount();

			account.setSid(System.currentTimeMillis() + "");
			account.setServiceName(serviceName);
			account.setServiceAvatar(serviceAvatar);
			account.setFunctionDesc(functonDesc);
			account.setServiceMaster(serviceMaster);

			account.setServiceButtons(serviceButtons);

			account.setFocus(new HashSet<String>());

			mongoService.save(IC.mongodb_service, account);

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 获取所有服务号
	 * @return
	 */
	public List<ServiceAccount> getAllServiceAccountList() {

		List<ServiceAccount> res = new ArrayList<ServiceAccount>();

		try {

			List<DBObject> list = mongoService.find(IC.mongodb_service);

			for (DBObject obj : list) {
				obj.removeField("_id");

				String json = gson.toJson(obj);

				ServiceAccount account = gson.fromJson(json,
						ServiceAccount.class);

				res.add(account);

			}
			
			return res;

		} catch (Exception e) {
			e.printStackTrace();
			
			return res;
		}

	}

}
