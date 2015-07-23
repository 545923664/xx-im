package com.jzwl.instant.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.jzwl.base.controller.BaseController;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.util.IC;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Controller
@RequestMapping("/test")
// url
public class TestController extends BaseController {

	private Gson gson = new Gson();

	private String key = "queue";

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	private final static double PI = 3.14159265358979323; // 圆周率
	private final static double R = 6371229; // 地球的半径

	@RequestMapping("/redis")
	public void redis() {
		String key = "queue";

		System.out.println("------------IN---------------");
		for (int i = 0; i < 5; i++) {
			String uid = "u" + i;
			System.out.println(uid);
			redisService.in(key, uid);
		}

		System.out.println("------------OUT---------------");
		long length = redisService.length(key);
		for (long i = 0; i < length; i++) {
			String uid = redisService.out(key);
			System.out.println(uid);
		}

		// ------------PUSH---------------
		key = "stack";
		int len = 5;
		System.out.println("------------PUSH---------------");
		for (int i = 0; i < len; i++) {
			String uid = "u" + System.currentTimeMillis();
			System.out.println(uid);
			redisService.push(key, uid);
		}

		length = redisService.length(key);

		// ------------POP---------------
		System.out.println("------------POP---------------");
		for (long i = 0; i < length; i++) {
			String uid = redisService.pop(key);
			System.out.println(uid);
		}

	}

	@RequestMapping("/mongo")
	public void mongo() {

//		System.out.println(mongoService.find("school").size());
//
//		Map<String, Object> cond = new LinkedHashMap<String, Object>();
//
//		Map<String, Object> loc = new LinkedHashMap<String, Object>();
//
//		Map<String, Double> location = new HashMap<String, Double>();
//		location.put("lng", 116.30);
//		location.put("lat", 39.98);
//
//		loc.put("$near", location);
//		loc.put("$maxDistance", 37);
//
//		cond.put("loc", loc);
//
//		System.out.println(gson.toJson(cond));
//
//		List<DBObject> findList = mongoService.findList(IC.mongodb_userinfo,
//				cond);
//
//		for (DBObject dbObject : findList) {
//			System.out.println(dbObject);
//
//		}
//
//		System.out.println(getDistance(116.3104622, 39.97691306, 116.308901,
//				39.983375));
//		
//		
//		
//
//		String userNickName="ba";
//		
//		cond.clear();
//		Pattern pattern = Pattern.compile("^.*" + userNickName+ ".*$", Pattern.CASE_INSENSITIVE); 
//		
//		cond.put("userNickName", pattern);
//
//		System.out.println(mongoService.findList(IC.mongodb_userinfo, cond).toString());
//		
		
		
		
		
		
		

	}

	public double getDistance(double lng1, double lat1, double lng2, double lat2) {
		double x, y;
		double distance;
		x = (lng2 - lng1) * PI * R * Math.cos(((lat1 + lat2) / 2) * PI / 180)
				/ 180;
		y = (lat2 - lat1) * PI * R / 180;
		distance = ((int) Math.hypot(x, y)) / 1000.00;
		return distance;
	}
}
