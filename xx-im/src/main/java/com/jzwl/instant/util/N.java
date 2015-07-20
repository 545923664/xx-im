package com.jzwl.instant.util;

public class N {

	/**
	 * 是否不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str) {
		
		if (null != str && str.length() > 0) {
			return true;
		}

		return false;

	}
}
