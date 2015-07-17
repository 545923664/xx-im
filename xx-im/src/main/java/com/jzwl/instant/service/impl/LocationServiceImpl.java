package com.jzwl.instant.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.jzwl.base.service.MongoService;
import com.jzwl.instant.pojo.UserInfo;
import com.jzwl.instant.service.LocationService;
import com.jzwl.instant.service.MessageService;
import com.jzwl.instant.service.UserService;
import com.jzwl.instant.util.IC;
import com.mongodb.DBObject;

/**
 * 
 * 
 * @author Administrator
 * 
 */
@Component
public class LocationServiceImpl implements LocationService {

	private static Gson gson = new Gson();

	@Autowired
	private MongoService mongoService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	private final static double PI = 3.14159265358979323; // 圆周率
	private final static double R = 6371229; // 地球的半径

	@Override
	public List<UserInfo> getNearUserList(String username, double lng,
			double lat) {

		List<UserInfo> res = new ArrayList<UserInfo>();

		try {

			Map<String, Double> location = new HashMap<String, Double>();
			location.put("lng", lng);
			location.put("lat", lat);

			Map<String, Object> loc = new LinkedHashMap<String, Object>();
			loc.put("$near", location);
			loc.put("$maxDistance", 30);

			Map<String, Object> cond = new LinkedHashMap<String, Object>();
			cond.put("loc", loc);

			List<DBObject> list = mongoService.findList(IC.mongodb_userinfo,
					cond);

			for (DBObject obj : list) {

				obj.removeField("_id");

				String json = gson.toJson(obj);

				UserInfo user = gson.fromJson(json, UserInfo.class);

				if (null != user && !user.getUsername().equals(username)) {// 排除自己

					userService.setUserInfo(user.getUsername(), user);

					double dest_lng = user.getLoc().get("lng");
					double dest_lat = user.getLoc().get("lat");

					double dest_distance = getDistance(dest_lng, dest_lat, lng,
							lat);

					user.setDistance(dest_distance);

					res.add(user);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return res;
		}

		// 附加距离

		return res;
	}

	/**
	 * 计算距离
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	private double getDistance(double lng1, double lat1, double lng2, double lat2) {
		double x, y;
		double distance;
		x = (lng2 - lng1) * PI * R * Math.cos(((lat1 + lat2) / 2) * PI / 180)
				/ 180;
		y = (lat2 - lat1) * PI * R / 180;
		distance = ((int) Math.hypot(x, y)) / 1000.00;
		return distance;
	}

}
