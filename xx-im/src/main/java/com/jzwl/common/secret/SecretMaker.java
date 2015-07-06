package com.jzwl.common.secret;

import java.security.MessageDigest;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

/**
 * 全局加密类，系统各种加密方法（MD5，AES,RSA,BASE64）汇总在此类中
 * 
 * @author zhang guo yu
 * @version 1.0.0
 * @since 2015-02-01
 * */
public class SecretMaker {

	/**
	 * 抓取随机UUID
	 * 
	 * @author zhang guo yu
	 * @return uuid
	 * @since 2011-10-18
	 * */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		String uuid = s.substring(0, 8) + s.substring(9, 13)
				+ s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
		return uuid;
	}

	/**
	 * 字符串MD5加密
	 * 
	 * @author zhang guo yu
	 * @param sourceString
	 *            待加密字符串
	 * @return stringMD5
	 * @throws Exception
	 * @since 2011-10-18
	 * */
	public static final String MD5(String sourceString) {
		String stringMD5 = "";
		try {
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
					'9', 'a', 'b', 'c', 'd', 'e', 'f' };
			if (StringUtils.isNotBlank(sourceString)) {
				byte[] strTemp = sourceString.getBytes();
				MessageDigest mdTemp = MessageDigest.getInstance("MD5");
				mdTemp.update(strTemp);
				byte[] md = mdTemp.digest();
				int j = md.length;
				char str[] = new char[j * 2];
				int k = 0;
				for (int i = 0; i < j; i++) {
					byte byte0 = md[i];
					str[k++] = hexDigits[byte0 >>> 4 & 0xf];
					str[k++] = hexDigits[byte0 & 0xf];
				}
				stringMD5 = new String(str);
			}
		} catch (Exception e) {
			stringMD5 = "error";
		}
		return stringMD5;
	}

}
