package com.jzwl.instant.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.FormatJsonResult;
import com.jzwl.instant.pojo.UserInfo;
import com.jzwl.instant.service.FileService;
import com.jzwl.instant.service.SendService;
import com.jzwl.instant.service.SessionService;
import com.jzwl.instant.service.UserService;
import com.jzwl.instant.util.JsonTool;
import com.jzwl.instant.util.N;

@Controller
@RequestMapping("/user")
public class UserController {

	Logger log = LoggerFactory.getLogger(UserController.class);

	private Gson gson = new Gson();

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	@Autowired
	private SendService sendService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private FileService fileService;
	@Autowired
	private UserService userService;

	/**
	 * 注册
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/register")
	public void register(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = null;

		try {

			String account = request.getParameter("account");// 账号
			String nickname = request.getParameter("nickname");// 昵称
			String password = request.getParameter("password");
			String password2 = request.getParameter("password2");

			if (null != account && null != password) {

				// 检查账号是否合法
				if (account.length() < 5) {

					fjr = new FormatJsonResult(0, "逗比，长度太短", "t", null, null);
					JsonTool.printMsg(response, gson.toJson(fjr));
					return;

				}

				if (!password.equals(password2)) {
					fjr = new FormatJsonResult(0, "逗比，两次密码不一致", "t", null, null);
					JsonTool.printMsg(response, gson.toJson(fjr));
					return;

				}

				// 检查是否存在
				boolean isExist = userService.accountIsExist(account);

				if (isExist) {
					fjr = new FormatJsonResult(0, "账号已经存在", "t", null, null);
					JsonTool.printMsg(response, gson.toJson(fjr));
					return;
				}

				// 创建账号
				String username = userService.createAccount(account, password,
						nickname);

				if (null != username) {

					fjr = new FormatJsonResult(1, username, "t", null, null);
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

	/**
	 * 登陆
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/login")
	public void login(HttpServletRequest request, HttpServletResponse response) {

		FormatJsonResult fjr = null;

		try {

			String account = request.getParameter("account");// 账号
			String password = request.getParameter("password");

			if (null != account && null != password) {

				UserInfo user = userService
						.findUserByAccount(account, password);

				if (null != user) {

					Map<String, Object> map = new HashMap<String, Object>();

					map.put("user", user);
					map.put("userinfo", user);

					fjr = new FormatJsonResult(1, user.getUsername(), "", null,
							map);

				} else {

					fjr = new FormatJsonResult(0, "逗x账号或密码错误", "t", null, null);
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

	/**
	 * 关闭连接
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/closeConnect")
	public void closeConnect(HttpServletRequest request,
			HttpServletResponse response) {
		FormatJsonResult fjr = new FormatJsonResult();

		fjr.setFlag(0);
		fjr.setCtrl("t");

		try {

			String username = request.getParameter("username");// 发起人
			if (null != username) {

				sessionService.disConnect(username);

				fjr.setFlag(1);
				fjr.setCtrl("");
				fjr.setMessage("连接已经断开");

				sendService.sendSystemOnlineInfoMessage(
						SendService.sys_off_brocast, username);

				JsonTool.printMsg(response, gson.toJson(fjr));

			} else {
				fjr.setMessage("用户名为空，断开毛线连接！");
				JsonTool.printMsg(response, gson.toJson(fjr));
			}

		} catch (Exception e) {
			fjr.setMessage(e.getMessage());

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}

	/**
	 * 上传头像
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/uploadAvatar")
	public void uploadAvatar(HttpServletRequest request,
			HttpServletResponse response) {
		FormatJsonResult fjr = null;

		try {

			String username = request.getParameter("username");// 发起人

			String fileName = request.getParameter("fileName");

			if (null != username && null != fileName) {

				String accessUrl = fileService.uploadPic(request, username,
						fileName);

				if (null != accessUrl) {

					// 更新用户头像

					boolean flag = userService.updateUserAvatar(username,
							accessUrl);

					if (flag) {

						fjr = new FormatJsonResult(1, accessUrl, "t", null,
								null);
					} else {
						fjr = new FormatJsonResult(0, "更新头像失败", "t", null, null);
					}

				} else {
					fjr = new FormatJsonResult(0, "上传失败", "t", null, null);
				}

			} else {

				fjr = new FormatJsonResult(0, "参数错误", "t", null, null);

			}

			JsonTool.printMsg(response, gson.toJson(fjr));

		} catch (Exception e) {
			fjr.setMessage(e.getMessage());

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}

	/**
	 * 查看用户个人信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getUserInfo")
	public void getUserInfo(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = null;

		try {

			Map<String, Object> map = new HashMap<String, Object>();

			String username = request.getParameter("username");// 账号

			if (N.isNotNull(username)) {

				UserInfo user = userService.getUserInfo(username);

				if (null != user) {
					map.put("user", user);
				} else {

					user = userService.getUserInfoFromDB(username);

					if (null != user) {
						map.put("user", user);
						map.put("userinfo", user);
					}
				}

				if (null != user) {
					fjr = new FormatJsonResult(1, user.getUsername(), "", null,
							map);
				} else {

					fjr = new FormatJsonResult(0, "用户不存在", "t", null, null);
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

	@RequestMapping(value = "/searchUser")
	public void searchUser(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = null;

		try {

			List<Object> list = new ArrayList<Object>();

			String userNickName = request.getParameter("userNickName");// 昵称

			if (N.isNotNull(userNickName)) {

				List<UserInfo> searchUserList = userService
						.searchUserByNickName(userNickName);

				list.addAll(searchUserList);

				fjr = new FormatJsonResult(1, "搜索列表", "t", list, null);

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
