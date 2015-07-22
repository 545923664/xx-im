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
import com.jzwl.instant.pojo.Comment;
import com.jzwl.instant.pojo.Dynamic;
import com.jzwl.instant.pojo.UserInfo;
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
	public List<Dynamic> getDynamicList(String uid) {

		List<Dynamic> res = new ArrayList<Dynamic>();

		try {

			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("uid", uid);

			List<DBObject> list = mongoService.findList(IC.mongodb_dynamic,
					cond);

			for (DBObject obj : list) {
				obj.removeField("_id");

				String json = gson.toJson(obj);

				Dynamic dynamic = gson.fromJson(json, Dynamic.class);

				if (null != dynamic) {

					String dynamic_uid = dynamic.getUid();

					UserInfo dynamic_user = userService.getUser(dynamic_uid);

					dynamic.setUserAvatar(dynamic_user.getAvatar());
					dynamic.setUserNickName(dynamic_user.getUserNickName());

					// 评论附加属性
					Set<Comment> comments = getCommentsByDid(dynamic.getDid());

					for (Comment comment : comments) {

						String comment_from_uid = comment.getFromUid();

						UserInfo comment_from_user = userService
								.getUser(comment_from_uid);

						comment.setFromUserAvatar(comment_from_user.getAvatar());
						comment.setFromUserNcikName(comment_from_user
								.getUserNickName());

						String comment_to_uid = comment.getToUid();
						UserInfo comment_to_user = userService
								.getUser(comment_to_uid);

						comment.setToUserAvatar(comment_to_user.getAvatar());
						comment.setToUserNickName(comment_to_user
								.getUserNickName());

					}

					dynamic.setComments(comments);

					// 点赞 附加属性
					if (null == dynamic.getZanUsers()) {
						dynamic.setZanUsers(new HashSet<String>());
					}

					Set<String> zanUsers = dynamic.getZanUsers();

					for (String zanUid : zanUsers) {

						if (null == dynamic.getZanUsersInfo()) {
							dynamic.setZanUsersInfo(new HashMap<String, String>());
						}

						dynamic.getZanUsersInfo().put(zanUid,
								userService.getUserNickName(zanUid));

					}

					res.add(dynamic);

				}

			}

			return res;

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
	 * 获取一条动态
	 * 
	 * @param did
	 * @return
	 */
	public Dynamic getDynamic(String did) {
		try {

			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("did", did);

			List<DBObject> list = mongoService.findList(IC.mongodb_dynamic,
					cond);

			if (null != list && list.size() > 0) {
				DBObject obj = list.get(0);

				obj.removeField("_id");

				String json = gson.toJson(obj);

				Dynamic dynamic = gson.fromJson(json, Dynamic.class);

				if (null != dynamic) {

					return dynamic;

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 点赞
	 * 
	 * @param uid
	 * @param did
	 * @return
	 */
	public boolean zan(String uid, String did) {

		try {

			Dynamic dynamic = getDynamic(did);

			if (null != dynamic) {

				Map<String, Object> cond = new HashMap<String, Object>();
				cond.put("did", did);


				if (null == dynamic.getZanUsers()) {
					dynamic.setZanUsers(new HashSet<String>());
				}

				dynamic.getZanUsers().add(uid);

				Map<String, Object> updateValue = new HashMap<String, Object>();
				updateValue.put("zanUsers", dynamic.getZanUsers());
				updateValue.put("zanCount", dynamic.getZanUsers().size());

				mongoService.update(IC.mongodb_dynamic, cond, updateValue);

				return true;
			}

			return false;

		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

	}

	/**
	 * 取消点赞
	 * 
	 * @param uid
	 * @param did
	 * @return
	 */
	public boolean cancelZan(String uid, String did) {

		try {

			Dynamic dynamic = getDynamic(did);

			if (null != dynamic) {

				Map<String, Object> cond = new HashMap<String, Object>();
				cond.put("did", did);

				if (null == dynamic.getZanUsers()) {
					dynamic.setZanUsers(new HashSet<String>());
				}

				dynamic.getZanUsers().remove(uid);

				Map<String, Object> updateValue = new HashMap<String, Object>();
				updateValue.put("zanUsers", dynamic.getZanUsers());
				updateValue.put("zanCount", dynamic.getZanUsers().size());

				mongoService.update(IC.mongodb_dynamic, cond, updateValue);

				return true;
			}

			return false;

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

				Comment comment = new Comment();

				comment.setCid(System.currentTimeMillis() + "");
				comment.setDid(did);

				if (N.isNotNull(pid)) {
					comment.setPid(pid);
				} else {
					comment.setPid("#");// 普通
				}

				comment.setFromUid(fromUid);
				comment.setToUid(toUid);
				comment.setMessage(message);
				comment.setCommentDate(System.currentTimeMillis());

				mongoService.save(IC.mongodb_dynamic_comment, comment);

				return true;

			}

			return false;

		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}

	}

	/**
	 * 获取动态对应的评论
	 * 
	 * @param did
	 * @return
	 */
	public Set<Comment> getCommentsByDid(String did) {

		Set<Comment> comments = new HashSet<Comment>();

		try {

			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("did", did);

			List<DBObject> list = mongoService.findList(
					IC.mongodb_dynamic_comment, cond);

			for (DBObject obj : list) {
				obj.removeField("_id");

				String json = gson.toJson(obj);

				Comment comment = gson.fromJson(json, Comment.class);

				comments.add(comment);
			}

			return comments;

		} catch (Exception e) {
			e.printStackTrace();
			return comments;
		}

	}
}
