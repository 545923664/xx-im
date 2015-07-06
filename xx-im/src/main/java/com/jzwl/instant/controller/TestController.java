package com.jzwl.instant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jzwl.base.controller.BaseController;
import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;

@Controller
@RequestMapping("/test")
// url
public class TestController extends BaseController {

	private String key = "queue";

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;
	

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
		
		System.out.println(mongoService.find("school").size());
		

	}
}
