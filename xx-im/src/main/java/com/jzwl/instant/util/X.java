package com.jzwl.instant.util;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

public class X {

	/**
	 * 是否不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str) {

		if (null != str && str.length() > 0) {
			return true;
		}

		return false;

	}

	public static boolean isNull(String str) {
		return !isNotNull(str);
	}

	public static String get(HttpServletRequest request, String paramName) {

		try {

			if (null != request && isNotNull(paramName)) {
				String value = request.getParameter(paramName);

				if (isNotNull(value)) {
					value = URLDecoder.decode(value, "utf-8");
					return value;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return null;

	}

}
