package com.jzwl.instant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.service.BootstarpService;
import com.jzwl.instant.service.MessageService;
import com.jzwl.instant.service.RecieveService;
import com.jzwl.instant.service.SendService;
import com.jzwl.instant.util.IC;
import com.jzwl.instant.util.L;

@Component
public class BootstarpServiceImpl implements BootstarpService {

	public static boolean ctrl_flag = true;

	public static long wait_sec = 50;

	@Autowired
	private RedisService redisService;
	@Autowired
	private MongoService mongoService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private SendService sendService;
	@Autowired
	private RecieveService recieveService;

	// 启动入口
	public void start(long delay) {
		try {
			L.out("*******************************[xx] begin Bootstarp ");

			// 确保在容器启动后执行
			Thread.sleep(delay);

			while (ctrl_flag) {
				Thread.sleep(wait_sec);

				final String nextCtrlMessage = messageService
						.getNextCtrlMessage();

				if (null != nextCtrlMessage) {
					new Thread() {
						@Override
						public void run() {
							recieveService.receive(nextCtrlMessage);

						}
					}.start();

				}

				final String nextSendMessage = messageService
						.getNextSendMessage();

				if (null != nextSendMessage) {
					new Thread() {
						@Override
						public void run() {
							sendService.sendCacheMessage(nextSendMessage);

						}
					}.start();

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			L.out(e.getMessage());

		}

	}

	public void busy() {
		wait_sec = 0;
		L.out("|||||||||||开始忙碌||||||||||" + wait_sec);
	}

	public void idle() {

		if (wait_sec >= IC.maxidle) {
			wait_sec = IC.maxidle;
		} else {
			wait_sec = wait_sec + wait_sec + 10;
		}

		L.out("|||||||||||空闲中||||||||||" + wait_sec);
	}
}
