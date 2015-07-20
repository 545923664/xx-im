package com.jzwl.instant.service.impl;

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
import com.jzwl.instant.pojo.Comment;
import com.jzwl.instant.pojo.Dynamic;
import com.jzwl.instant.service.DynamicService;
import com.jzwl.instant.service.UserService;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.N;
import com.jzwl.instant.util.Util;
import com.mongodb.DBObject;

/**
 * 动态
 * 
 * @author Administrator
 * 
 */
@Component
public class DynamicServiceImpl implements DynamicService {

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	@Autowired
	private UserService userService;

	private Gson gson = new Gson();

	/**
	 * 获取个人动态
	 * 
	 * @param uid
	 * @return
	 */
	public List<DBObject> getDynamicList(String uid) {

		try {

			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("uid", uid);

			List<DBObject> list = mongoService.findList(IC.mongodb_dynamic,
					cond);

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 发布动态
	 * 
	 * @param uid
	 * @param status
	 * @param text
	 * @param picCount
	 * @param picUrls
	 * @return
	 */
	public boolean publishDynamic(String uid, String status, String text,
			int picCount, Set<String> picUrls) {

		try {

			Dynamic dynamic = new Dynamic();

			dynamic.setDid(Util.getUUID());
			dynamic.setUid(uid);
			dynamic.setUserNickName(userService.getUserNickName(uid));
			dynamic.setStatus(status);
			dynamic.setText(text);
			dynamic.setPicCount(picCount);
			dynamic.setPicUrls(picUrls);
			dynamic.setPublishDate(System.currentTimeMillis());

			mongoService.save(IC.mongodb_dynamic, dynamic);

			return true;

		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

	}

	/**
	 * 发布评论
	 * 
	 * @param did
	 * @param pid
	 * @param fromUid
	 * @param toUid
	 * @param message
	 * @return
	 */
	public boolean publishComment(String did, String pid, String fromUid,
			String toUid, String message) {

		try {

			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("did", did);

			List<DBObject> list = mongoService.findList(IC.mongodb_dynamic,
					cond);

			if (null != list && list.size() > 0) {

				DBObject obj = list.get(0);

				if (null != obj) {
					obj.removeField("_id");
					String json = gson.toJson(obj);

					Dynamic dynamic = gson.fromJson(json, Dynamic.class);

					if (null == dynamic.getComments()) {

						Set<Comment> comments = new HashSet<Comment>();

						dynamic.setComments(comments);
					}

					Comment comment = new Comment();

					comment.setCid(System.currentTimeMillis() + "");
					comment.setDid(dynamic.getDid());

					if (N.isNotNull(pid)) {
						comment.setPid(pid);
					} else {
						comment.setPid("#");// 普通
					}

					comment.setFromUid(fromUid);
					comment.setToUid(toUid);
					comment.setMessage(message);
					comment.setCommentDate(System.currentTimeMillis());

					dynamic.getComments().add(comment);

					// 更新
					Map<String, Object> updateValue = new HashMap<String, Object>();
					updateValue.put("comments", dynamic.getComments());

					mongoService.update(IC.mongodb_dynamic, cond, updateValue);

				}
			}

			return false;

		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

	}
}
