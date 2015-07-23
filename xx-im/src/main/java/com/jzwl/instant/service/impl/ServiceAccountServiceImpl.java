package com.jzwl.instant.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.ServiceAccount;
import com.jzwl.instant.pojo.ServiceButton;
import com.jzwl.instant.pojo.ServiceMessage;
import com.jzwl.instant.service.SendService;
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

	@Autowired
	private SendService sendService;

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

			account.setSid(IC.serviceSid);
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
	 * 
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

	/**
	 * 处理服务号请求
	 * 
	 * @param sid
	 * @param bid
	 * @param value
	 */
	public void ctrlServiceAccountRequest(String sid, String bid, String value,
			String uid) {

		try {

			if (sid.equals(IC.serviceSid)) {

				if (bid.equals("201")) {// 点击试试

					// 给该用户发送一个推送

					ServiceMessage sm = new ServiceMessage();

					Map<String, String> banner = new HashMap<String, String>();

					banner.put("picUrl", "https://www.baidu.com/img/bdlogo.png");
					banner.put("text", "最上面的大图");
					banner.put("link", "https://www.baidu.com");

					sm.setBanner(banner);

					Set<Map<String, String>> set = new HashSet<Map<String, String>>();

					for (int i = 0; i < 3; i++) {
						Map<String, String> item = new HashMap<String, String>();

						item.put("picUrl",
								"https://www.baidu.com/img/bdlogo.png");
						item.put("text", "图"+i);
						item.put("link", "https://www.baidu.com");

						set.add(item);
					}

					sm.setItems(set);

					sendService.sendServiceMessage(uid, sm);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
