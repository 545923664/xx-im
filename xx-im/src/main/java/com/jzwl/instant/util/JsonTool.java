package com.jzwl.instant.util;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * json工具
 * 
 * @author xx
 * 
 */
public class JsonTool {

	private static final Logger logger = LoggerFactory
			.getLogger(JsonTool.class);

	/**
	 * @生成集合的json串，适用于R
	 * @param list
	 * @param count
	 * @param listName
	 * @param countName
	 * @return
	 */
	public static String getListJson(List<?> list, long count, String listName,
			String countName) {

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		if (null == listName || "".equals(listName.trim())) {
			listName = "list";
		}
		if (null == countName || "".equals(countName.trim())) {
			countName = "totalProperty";
		}
		map.put(listName, list);
		map.put(countName, count);

		Gson gson = new Gson();
		String json = gson.toJson(map);

		return json;
	}

	/**
	 * @返回单个对象的json串，适用于CU
	 * @param obj
	 * @param objName
	 * @return
	 */
	public static String getObjectJson(Object obj, String objName) {

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		if (null == objName || "".equals(objName.trim())) {
			objName = "obj";
		}
		map.put(objName, obj);
		map.put("success", true);

		Gson gson = new Gson();
		String json = gson.toJson(map);

		return json;
	}

	/**
	 * @返回霄1�7要过滤相关类型和属�1�7�的json丄1�7
	 * @param list
	 * @param count
	 * @param listName
	 * @param countName
	 * @param exclusionClass
	 * @param exclusionField
	 * @return
	 */
	public static String getContextJson(List<?> list, long count,
			String listName, String countName, final Class<?> exclusionClass,
			final String exclusionField) {

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		if (null == listName || "".equals(listName.trim())) {
			listName = "list";
		}
		if (null == countName || "".equals(countName.trim())) {
			countName = "totalProperty";
		}
		map.put(listName, list);
		map.put(countName, count);

		// 创建丄1�7个带过滤条件的gson对象
		Gson contextGson = new GsonBuilder().setExclusionStrategies(
				new ExclusionStrategy() {
					// 过滤的属怄1�7
					@Override
					public boolean shouldSkipField(FieldAttributes attr) {
						// 这里，如果返回true就表示此属�1�7�要过滤，否则就输出
						boolean flag = false;
						if (null != exclusionField
								&& exclusionField.contains("|" + attr.getName()
										+ "|")) {
							flag = true;
						}

						return flag;
					}

					// 过滤的类
					@Override
					public boolean shouldSkipClass(Class<?> clazz) {
						// 这里，如果返回true就表示此类要过滤，否则就输出
						boolean flag = false;
						if (null != exclusionClass && clazz == exclusionClass) {
							flag = true;
						}
						return flag;
					}
				}).create();

		String json = contextGson.toJson(map);
		return json;
	}

	/**
	 * @输出json
	 * @param response
	 * @param json
	 */
	public static void printMsg(HttpServletResponse response, String json) {
		response.setContentType("text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("<<<<<<异常>>>>>--" + e.getMessage());
		}
	}


}
