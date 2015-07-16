package com.jzwl.common.init.Spring;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.service.BootstarpService;

public class SpringInit implements InitializingBean, DisposableBean {

	@Autowired
	private RedisService redisService;

	@Autowired
	private MongoService mongoService;

	@Autowired
	private BootstarpService bootstarpService;

	public void InitAndDestroySeqBean() {
		System.out.println("执行InitAndDestroySeqBean: 构造方法");
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("执行InitAndDestroySeqBean: postConstruct");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("执行InitAndDestroySeqBean: afterPropertiesSet");
	}

	public void initMethod() {
		System.out.println("执行InitAndDestroySeqBean: init-method");

		// 开始处理消息
		new Thread() {
			@Override
			public void run() {
				bootstarpService.start(10000l);

			}
		}.start();

	}

	@PreDestroy
	public void preDestroy() {
		System.out.println("执行InitAndDestroySeqBean: preDestroy");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("执行InitAndDestroySeqBean: destroy");
	}

	public void destroyMethod() {
		System.out.println("执行InitAndDestroySeqBean: destroy-method");
	}

}