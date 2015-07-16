package com.jzwl.instant.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.FormatJsonResult;
import com.jzwl.instant.pojo.GroupInfo;
import com.jzwl.instant.pojo.MyMessage;
import com.jzwl.instant.pojo.UserInfo;
import com.jzwl.instant.service.impl.FriendServiceImpl;
import com.jzwl.instant.service.impl.GroupServiceImpl;
import com.jzwl.instant.service.impl.MessageServiceImpl;
import com.jzwl.instant.service.impl.SendServiceImpl;
import com.jzwl.instant.service.impl.SessionServiceImpl;
import com.jzwl.instant.service.impl.UserServiceImpl;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.JsonTool;

@Controller
@RequestMapping("/group")
public class GroupController {

	Logger log = LoggerFactory.getLogger(GroupController.class);

	private Gson gson = new Gson();

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	@Autowired
	private GroupServiceImpl groupServiceImpl;
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private SessionServiceImpl sessionServiceImpl;
	@Autowired
	private FriendServiceImpl friendServiceImpl;
	@Autowired
	private MessageServiceImpl messageServiceImpl;
	@Autowired
	private SendServiceImpl sendServiceImpl;

	/**
	 * 创建群
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/createGroup")
	public void createGroup(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = new FormatJsonResult();

		try {

			String username = request.getParameter("username");// 发起人
			String groupCode = request.getParameter("groupcode");// 五位数密码

			if (null != username && null != groupCode) {

				String gid = groupServiceImpl.createGroup(username);

				if (null != gid) {
					// 三分钟有效
					redisService.set(IC.user_create_group_key + groupCode, gid,
							IC.MIN_1 * 3);

					fjr.setFlag(1);
					fjr.setCtrl("t");
					fjr.setMessage("你创建的群已经成功，群号为[" + gid + "],让别人输入口令【"
							+ groupCode + "】来激活吧！");

				} else {
					fjr.setFlag(0);
					fjr.setCtrl("t");
					fjr.setMessage("创建失败");

				}

				JsonTool.printMsg(response, gson.toJson(fjr));

			} else {

				fjr.setFlag(0);
				fjr.setCtrl("t");
				fjr.setMessage("参数错误");

				JsonTool.printMsg(response, gson.toJson(fjr));
			}

		} catch (Exception e) {

			fjr.setFlag(0);
			fjr.setCtrl("t");
			fjr.setMessage(e.getMessage());

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}

	/**
	 * 申请加入群
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/wantJoinGroup")
	public void wantJoinGroup(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = new FormatJsonResult();

		try {

			String username = request.getParameter("username");// 加入者
			String groupCode = request.getParameter("groupcode");// 五位数密码

			if (null != username && null != groupCode) {

				String gid = redisService.get(IC.user_create_group_key
						+ groupCode);

				if (null != gid) {

					boolean flag = groupServiceImpl.JoinGroup(username, gid);

					if (flag) {
						fjr.setFlag(1);
						fjr.setCtrl("t");
						fjr.setMessage("你已经加入群【" + gid + "】，可以聊天了");

						// 通知其他群成员
						sendServiceImpl.sendGroupNotifiMessage(username, gid);

					} else {
						fjr.setFlag(0);
						fjr.setCtrl("t");
						fjr.setMessage("你已经加入该群，或者该群已经解散");
					}

				} else {
					fjr.setFlag(0);
					fjr.setCtrl("t");
					fjr.setMessage("你输入的口令已经过期");

				}

				JsonTool.printMsg(response, gson.toJson(fjr));

			} else {

				fjr.setFlag(0);
				fjr.setCtrl("t");
				fjr.setMessage("参数错误");

				JsonTool.printMsg(response, gson.toJson(fjr));
			}

		} catch (Exception e) {

			fjr.setFlag(0);
			fjr.setCtrl("t");
			fjr.setMessage(e.getMessage());

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}

	/**
	 * 申请加入群
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/applyJoinGroup")
	public void applyJoinGroup(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = new FormatJsonResult();

		try {

			String username = request.getParameter("username");// 加入者
			String gid = request.getParameter("gid");// gid

			if (null != username && null != gid) {

				GroupInfo group = groupServiceImpl.getGroupInfo(gid);

				if (null != group) {
					Set<String> member = group.getMember();

					boolean isMember = groupServiceImpl.checkIsGroupMember(
							username, member);

					if (isMember) {
						fjr.setFlag(0);
						fjr.setCtrl("t");
						fjr.setMessage("逗比！你已经是群成员了");
					} else {

						String mid = group.getMid();

						MyMessage message = new MyMessage();

						message.setModel(IC.SYS);

						message.setUsername(username);

						message.setTousername(mid);

						message.setMessage("逗比"
								+ userServiceImpl.getUserNickName(mid) + "你好["
								+ userServiceImpl.getUserNickName(username)
								+ "]要加您的群" + group.getDesc() + "是否同意");

						message.putExtKey("action", IC.ACTION_APPLY_GROUP);

						message.putExtKey("gid", gid);

						messageServiceImpl.joinSendQueue(gson.toJson(message));

						fjr.setFlag(1);
						fjr.setCtrl("t");
						fjr.setMessage("你的申请已经发给群猪了，请耐心等待！");

					}
				} else {
					fjr.setFlag(0);
					fjr.setCtrl("t");
					fjr.setMessage("逗比！该群压根不存在！");
				}

				JsonTool.printMsg(response, gson.toJson(fjr));

			} else {

				fjr.setFlag(0);
				fjr.setCtrl("t");
				fjr.setMessage("参数错误");

				JsonTool.printMsg(response, gson.toJson(fjr));
			}

		} catch (Exception e) {

			fjr.setFlag(0);
			fjr.setCtrl("t");
			fjr.setMessage(e.getMessage());

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}

	/**
	 * 同意加群申请
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/agreeApplyJoinGroup")
	public void agreeApplyJoinGroup(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr;

		try {

			String applyUsername = request.getParameter("username");// 想加入的人的人

			String gid = request.getParameter("gid");// gid
			String isAgreen = request.getParameter("is_agree");

			if (null != applyUsername && null != gid && null != isAgreen) {

				GroupInfo group = groupServiceImpl.getGroupInfo(gid);

				if (null != group) {

					if ("1".equals(isAgreen)) {// 同意

						Set<String> member = group.getMember();

						boolean isMember = groupServiceImpl.checkIsGroupMember(
								applyUsername, member);

						if (isMember) {
							fjr = new FormatJsonResult(0, "逗比！那个傻缺已经是群成员了",
									"t", null, null);
						} else {

							boolean flag = groupServiceImpl.JoinGroup(
									applyUsername, gid);

							if (flag) {

								String mid = group.getMid();

								MyMessage message = new MyMessage();

								message.setModel(IC.SYS);
								message.putExtKey("action",
										IC.ACTION_AGREE_GROUP);

								message.setUsername(mid);
								message.setTousername(applyUsername);

								message.setMessage("逗比" + applyUsername + "你好["
										+ mid + "]同意你加入群" + group.getDesc());

								messageServiceImpl.joinSendQueue(gson
										.toJson(message));

								// 通知其他群成员
								sendServiceImpl.sendGroupNotifiMessage(
										applyUsername, gid);

								fjr = new FormatJsonResult(1, "已同意", "t", null,
										null);

							} else {

								fjr = new FormatJsonResult(0, "加入失败", "t",
										null, null);

							}

						}

					} else {// 拒绝

						MyMessage message = new MyMessage();

						message.setModel(IC.SYS);
						message.putExtKey("action", IC.ACTION_NOT_AGREE_GROUP);

						message.setUsername(group.getMid());
						message.setTousername(applyUsername);

						message.setMessage("屌丝" + applyUsername + "你好["
								+ group.getMid() + "]拒绝你加入群" + group.getDesc());

						messageServiceImpl.joinSendQueue(gson.toJson(message));

						fjr = new FormatJsonResult(1, "已拒绝", "t", null, null);
					}

				} else {
					fjr = new FormatJsonResult(0, "逗比！该群压根不存在！", "t", null,
							null);
				}

				JsonTool.printMsg(response, gson.toJson(fjr));

			} else {

				fjr = new FormatJsonResult(0, "参数错误", "t", null, null);

				JsonTool.printMsg(response, gson.toJson(fjr));
			}

		} catch (Exception e) {

			fjr = new FormatJsonResult(0, e.getMessage(), "t", null, null);

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}

	/**
	 * 搜索群
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/searchGroup")
	public void searchGroup(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = new FormatJsonResult();

		try {

			String gid = request.getParameter("gid");// 群id

			if (null != gid) {

				List<Object> list = new ArrayList<Object>();

				if ("all".equals(gid)) {

					List<GroupInfo> tempList = groupServiceImpl
							.getAllGroups(mongoService);

					list.addAll(tempList);

				} else {
					GroupInfo group = groupServiceImpl.getGroupInfo(gid);

					if (null != group) {
						list.add(group);
					}
				}

				fjr.setFlag(1);
				fjr.setCtrl("");
				fjr.setList(list);

			} else {

				fjr.setFlag(0);
				fjr.setCtrl("t");
				fjr.setMessage("参数错误");

			}

			JsonTool.printMsg(response, gson.toJson(fjr));
		} catch (Exception e) {

			fjr.setFlag(0);
			fjr.setCtrl("t");
			fjr.setMessage(e.getMessage());

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}

	/**
	 * 获取群成员
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getGroupMember")
	public void getGroupMember(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = new FormatJsonResult();

		try {

			String gid = request.getParameter("gid");// 群id

			if (null != gid) {

				List<Object> list = new ArrayList<Object>();

				Map<String, Object> groupInfo = new HashMap<String, Object>();

				GroupInfo group = groupServiceImpl.getGroupInfo(gid);

				if (null != group) {

					groupInfo.put("group", group);

					Set<String> member = group.getMember();

					for (String uid : member) {

						UserInfo user = userServiceImpl.getUserInfoFromDB(uid);

						if (null != user) {
							list.add(user);
						}

					}

				}

				fjr.setFlag(1);
				fjr.setCtrl("");
				fjr.setMap(groupInfo);
				fjr.setList(list);

			} else {

				fjr.setFlag(0);
				fjr.setCtrl("t");
				fjr.setMessage("参数错误");

			}

			JsonTool.printMsg(response, gson.toJson(fjr));
		} catch (Exception e) {

			fjr.setFlag(0);
			fjr.setCtrl("t");
			fjr.setMessage(e.getMessage());

			JsonTool.printMsg(response, gson.toJson(fjr));
			e.printStackTrace();
		}
	}

	/**
	 * 退出群
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/quiteGroup")
	public void quiteGroup(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = null;

		try {

			String gid = request.getParameter("gid");// 群id
			String username = request.getParameter("username");// 群id

			if (null != gid && null != username) {

				boolean flag = groupServiceImpl.quiteGroup(username, gid);

				if (flag) {
					// 发送群通知
					sendServiceImpl.sendGroupNotifiMessage(username, gid, username
							+ "退出此群", mongoService, redisService);

					fjr = new FormatJsonResult(1, "退出成功", "t", null, null);
				} else {
					fjr = new FormatJsonResult(0, "退出失败", "t", null, null);
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
	 * 解散群
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/diabandGroup")
	public void diabandGroup(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = null;

		try {

			String gid = request.getParameter("gid");// 群id
			String username = request.getParameter("username");// 群id

			if (null != gid && null != username) {

				GroupInfo group = groupServiceImpl.getGroupInfo(gid);

				if (null != group) {

					boolean flag = groupServiceImpl.disbandGroup(username, gid,
							group);

					if (flag) {
						Set<String> member = group.getMember();

						// 发送个人系统通知
						for (String uid : member) {
							sendServiceImpl.sendGroupMemberNotifiMessage(uid, gid,
									"群" + group.getDesc() + "已经解散成功",
									mongoService, redisService);
						}

						fjr = new FormatJsonResult(1, "解散成功", "t", null, null);
					} else {
						fjr = new FormatJsonResult(0, "解散失败", "t", null, null);
					}
				} else {
					fjr = new FormatJsonResult(0, "群不存在", "t", null, null);
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
}
