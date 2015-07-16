package com.jzwl.instant.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class L {

	private final static Logger LOGGER = LoggerFactory.getLogger(L.class);

	/**
	 * 打印输出
	 */
	public static void out(String res) {

		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win")) {
			System.out.println(res);
		}else{
			LOGGER.info(res);
		}


	}
}
