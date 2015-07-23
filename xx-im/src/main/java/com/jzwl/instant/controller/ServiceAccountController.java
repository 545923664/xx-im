package com.jzwl.instant.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.instant.pojo.FormatJsonResult;
import com.jzwl.instant.pojo.ServiceAccount;
import com.jzwl.instant.pojo.ServiceButton;
import com.jzwl.instant.service.FileService;
import com.jzwl.instant.service.ServiceAccountService;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.JsonTool;
import com.jzwl.instant.util.X;

@Controller
@RequestMapping("/serviceAccount")
public class ServiceAccountController {

	Logger log = LoggerFactory.getLogger(ServiceAccountController.class);

	@Autowired
	private MongoService mongoService;

	@Autowired
	private ServiceAccountService serviceAccountService;

	@Autowired
	private FileService fileService;

	private Gson gson = new Gson();

	@RequestMapping(value = "/addSystemAccount")
	public void addSystemAccount(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = null;

		try {

			String serviceName = X.get(request, "serviceName");
			String functonDesc = X.get(request, "functonDesc");
			String serviceMaster = X.get(request, "serviceMaster");
			String serviceAvatar = "";

			if (X.isNotNull(serviceName) && X.isNotNull(functonDesc)
					&& X.isNotNull(serviceMaster)) {

				Set<String> picUrls = fileService.uploadPics(request,
						IC.systemUid);

				if (null != picUrls && picUrls.size() > 0) {
					serviceAvatar = picUrls.iterator().next();
				}

				boolean flag = serviceAccountService.addServiceAccount(
						serviceName, serviceAvatar, functonDesc, serviceMaster,
						getSystemServiceAccountButton());

				if (flag) {

					fjr = new FormatJsonResult(1, "创建成功", "t", null, null);

				} else {

					fjr = new FormatJsonResult(0, "创建失败", "t", null, null);
				}

			} else {
				fjr = new FormatJsonResult(0, "参数错误", "t", null, null);
			}

			JsonTool.printMsg(response, gson.toJson(fjr));

		} catch (Exception e) {

			fjr = new FormatJsonResult(0, e.getMessage(), "t", null, null);

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/serviceAccountClick")
	public void serviceAccountClick(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = null;

		try {
			String sid = request.getParameter("sid");

			String uid = request.getParameter("uid");

			String bid = request.getParameter("bid");

			String value = request.getParameter("value");

			if (X.isNotNull(sid) && X.isNotNull(uid) && X.isNotNull(bid)
					&& X.isNotNull(value)) {

				serviceAccountService.ctrlServiceAccountRequest(sid, bid,
						value, uid);
				
				fjr = new FormatJsonResult(1, "请稍后", "t", null, null);

			} else {
				fjr = new FormatJsonResult(0, "参数错误", "t", null, null);
			}

			JsonTool.printMsg(response, gson.toJson(fjr));

		} catch (Exception e) {

			fjr = new FormatJsonResult(0, e.getMessage(), "t", null, null);

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}

	/**
	 * 获取所有服务号
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getAllServiceAccount")
	public void getAllServiceAccount(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = null;

		try {

			List<ServiceAccount> accountLis = serviceAccountService
					.getAllServiceAccountList();

			List<Object> list = new ArrayList<Object>();
			list.addAll(accountLis);

			fjr = new FormatJsonResult(1, "", "t", list, null);

			JsonTool.printMsg(response, gson.toJson(fjr));

		} catch (Exception e) {

			fjr = new FormatJsonResult(0, e.getMessage(), "t", null, null);

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}

	/**
	 * 创建系统服务号
	 * 
	 * @return
	 */
	private Set<ServiceButton> getSystemServiceAccountButton() {

		Set<ServiceButton> btns = new HashSet<ServiceButton>();

		ServiceButton btn_1 = new ServiceButton();

		btn_1.setBid("10");
		btn_1.setPid("#");
		btn_1.setName("功能展示");
		btn_1.setValue("10");
		btn_1.setLocation("L");
		btn_1.setType("menu");
		btn_1.setUrl("#");

		ServiceButton btn_1_1 = new ServiceButton();

		btn_1_1.setBid("101");
		btn_1_1.setPid("10");
		btn_1_1.setName("百度");
		btn_1_1.setValue("101");
		btn_1_1.setLocation("L");
		btn_1_1.setType("btn");
		btn_1_1.setUrl("http://www.baidu.com");

		ServiceButton btn_1_2 = new ServiceButton();

		btn_1_2.setBid("102");
		btn_1_2.setPid("10");
		btn_1_2.setName("新浪");
		btn_1_2.setValue("102");
		btn_1_2.setLocation("L");
		btn_1_2.setType("btn");
		btn_1_2.setUrl("http://www.sina.com");

		btns.add(btn_1);
		btns.add(btn_1_1);
		btns.add(btn_1_2);

		ServiceButton btn_2 = new ServiceButton();

		btn_2.setBid("20");
		btn_2.setPid("#");
		btn_2.setName("系统帮助");
		btn_2.setValue("20");
		btn_2.setLocation("C");
		btn_2.setType("menu");
		btn_2.setUrl("#");

		ServiceButton btn_2_1 = new ServiceButton();

		btn_2_1.setBid("201");
		btn_2_1.setPid("20");
		btn_2_1.setName("点击试试");
		btn_2_1.setValue("201");
		btn_2_1.setLocation("C");
		btn_2_1.setType("btn");
		btn_2_1.setUrl("#");

		btns.add(btn_2);
		btns.add(btn_2_1);

		ServiceButton btn_3 = new ServiceButton();

		btn_3.setBid("30");
		btn_3.setPid("#");
		btn_3.setName("根+按钮");
		btn_3.setValue("30");
		btn_3.setLocation("R");
		btn_3.setType("btn");
		btn_3.setUrl("http://www.sxw100.com");

		btns.add(btn_3);

		return btns;

	}

}
