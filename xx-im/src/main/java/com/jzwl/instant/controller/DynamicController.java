package com.jzwl.instant.controller;

import java.util.ArrayList;
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
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.pojo.Dynamic;
import com.jzwl.instant.pojo.FormatJsonResult;
import com.jzwl.instant.service.DynamicService;
import com.jzwl.instant.service.FileService;
import com.jzwl.instant.service.SendService;
import com.jzwl.instant.service.SessionService;
import com.jzwl.instant.service.UserService;
import com.jzwl.instant.util.JsonTool;
import com.jzwl.instant.util.X;

@Controller
@RequestMapping("/dynamic")
public class DynamicController {

	Logger log = LoggerFactory.getLogger(DynamicController.class);

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
	@Autowired
	private DynamicService dynamicService;

	/**
	 * 获取动态列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getDynamicList")
	public void getDynamicList(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = null;

		try {

			String uid = request.getParameter("uid");

			if (X.isNotNull(uid)) {

				List<Dynamic> list = dynamicService.getDynamicList(uid);

				List<Object> res = new ArrayList<Object>();

				res.addAll(list);

				fjr = new FormatJsonResult(1, "", "", res, null);

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
	 * 发布动态
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/publishDynamic")
	public void publishDynamic(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = null;

		try {

			String uid = request.getParameter("uid");// username
			String text = X.get(request, "text");// 发表的文字

			String status = "a";// 默认只有文字

			if (X.isNotNull(uid) && X.isNotNull(text)) {

				Set<String> picUrls = fileService.uploadPics(request, uid);

				if (null != picUrls) {

					if (X.isNotNull(text) && picUrls.size() > 0) {
						status = "ab";
					}

					if (X.isNotNull(text) && picUrls.size() == 0) {
						status = "a";
					}

					if (X.isNull(text) && picUrls.size() > 0) {
						status = "b";
					}

					boolean publish_flag = dynamicService.publishDynamic(uid,
							status, text, picUrls.size(), picUrls);

					if (publish_flag) {

						fjr = new FormatJsonResult(1, picUrls.toString(), "t",
								null, null);

					} else {

						fjr = new FormatJsonResult(0, "发布动态失败", "t", null, null);
					}

				} else {
					fjr = new FormatJsonResult(0, "上传动态失败", "t", null, null);
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
	 * 发布评论
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/publishComment")
	public void publishComment(HttpServletRequest request,
			HttpServletResponse response) {

		FormatJsonResult fjr = null;

		try {

			String did = request.getParameter("did");// 动态id
			String pid = request.getParameter("pid");// 父动态id 可空
			String fromUid = request.getParameter("fromUid");// 谁评论着
			String toUid = request.getParameter("toUid");// 被评论者
			String message = X.get(request, "message");// 评论内容

			if (X.isNotNull(did) && X.isNotNull(fromUid)
					&& X.isNotNull(fromUid) && X.isNotNull(toUid)
					&& X.isNotNull(message)) {

				boolean flag = dynamicService.publishComment(did, pid, fromUid,
						toUid, message);

				if (flag) {
					fjr = new FormatJsonResult(1, "评论成功", "t", null, null);

				} else {
					fjr = new FormatJsonResult(0, "评论失败", "t", null, null);

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

	@RequestMapping(value = "/zan")
	public void zan(HttpServletRequest request, HttpServletResponse response) {

		FormatJsonResult fjr = null;

		try {

			String type = request.getParameter("type");// 点赞or取消点赞
			String did = request.getParameter("did");// 动态id
			String uid = request.getParameter("uid");// 点赞的人

			if (X.isNotNull(type) && X.isNotNull(did) && X.isNotNull(uid)) {

				boolean flag = false;

				if (type.equals("1")) {// 点赞
					flag = dynamicService.zan(uid, did);
				} else {// 0 取消点赞
					flag = dynamicService.cancelZan(uid, did);
				}

				if (flag) {

					fjr = new FormatJsonResult(1, "成功", "t", null, null);

				} else {
					fjr = new FormatJsonResult(0, "失败", "t", null, null);

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
