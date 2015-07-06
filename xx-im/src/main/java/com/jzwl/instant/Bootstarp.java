package com.jzwl.instant;

import com.jzwl.base.service.MongoService;
import com.jzwl.base.service.RedisService;
import com.jzwl.instant.util.L;

public class Bootstarp {

	static boolean ctrl_flag = true;

	private static long lastCtrlTime;
	private static long currentCtrlTime;
	private static long space_stop_time = 1000 * 60;
	private static int red_time = 0;
	private static int red_stop_time = 5;
	private static long push_leave_delay = 5000;

	// 启动入口
	public static void start(final RedisService redisService,
			final MongoService mongoService, long delay) {
		try {
			L.out("*******************************[xx] begin Bootstarp ");

			// 确保在容器启动后执行
			Thread.sleep(delay);

			while (ctrl_flag) {
				Thread.sleep(100);

				final String nextCtrlMessage = MessageManager
						.getNextCtrlMessage(redisService);

				if (null != nextCtrlMessage) {
					new Thread() {
						@Override
						public void run() {
							RecieveMessage.receive(nextCtrlMessage,
									redisService, mongoService);

						}
					}.start();

				}

				final String nextSendMessage = MessageManager
						.getNextSendMessage(redisService);

				if (null != nextSendMessage) {
					new Thread() {
						@Override
						public void run() {
							SendMessage.sendCacheMessage(nextSendMessage,
									redisService, mongoService);

						}
					}.start();

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			L.out(e.getMessage());

		}

	}
}
