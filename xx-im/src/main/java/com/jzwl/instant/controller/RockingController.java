package com.jzwl.instant.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.jzwl.instant.pojo.FormatJsonResult;
import com.jzwl.instant.pojo.UserInfo;
import com.jzwl.instant.service.FriendService;
import com.jzwl.instant.service.GroupService;
import com.jzwl.instant.service.MessageService;
import com.jzwl.instant.service.RockingService;
import com.jzwl.instant.service.SendService;
import com.jzwl.instant.service.SessionService;
import com.jzwl.instant.service.UserService;
import com.jzwl.instant.util.JsonTool;

@Controller
@RequestMapping("/rocking")
public class RockingController {

	Logger log = LoggerFactory.getLogger(RockingController.class);

	private Gson gson = new Gson();

	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private FriendService friendService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private SendService sendService;

	@Autowired
	private RockingService rockingService;

	/**
	 * 解散群
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/rocking")
	public void rocking(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = null;

		try {

			String username = request.getParameter("username");// 群id

			if (null != username) {

				List<UserInfo> rockList = rockingService
						.getRockingUserList(username);

				List<Object> list = new ArrayList<Object>();

				list.addAll(rockList);

				fjr = new FormatJsonResult(1, "", "t", list, null);

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
}
