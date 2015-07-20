package com.jzwl.instant.service;

import java.util.List;
import java.util.Set;

import com.mongodb.DBObject;

/**
 * 动态
 * 
 * @author xx
 * 
 */
public interface DynamicService {

	/**
	 * 获取个人动态
	 * 
	 * @param uid
	 * @return
	 */
	public List<DBObject> getDynamicList(String uid);

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
			int picCount, Set<String> picUrls);

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
			String toUid, String message);

}
