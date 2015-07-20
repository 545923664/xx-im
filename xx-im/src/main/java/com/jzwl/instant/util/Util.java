package com.jzwl.instant.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

public class Util {
	// Create an 24 byte UUID
	protected static int count = 0;

	public static synchronized String getRandomUUId() {
		return UUID.randomUUID().toString();
	}

	public static void main2(String[] args) {
		// System.out.println(getUUID());
		// System.out.println(System.currentTimeMillis());
		// System.out.println((Math.random()*1000+"").split("\\.")[0]);

		Random fu = new Random();
		Random yu = new Random();
		for (int i = 1; i < 500; i++) {
			int a = fu.nextInt((97) + 25);
			int b = yu.nextInt((97) + 25);
			if ((a == 97) || (a == 101) || (a == 105) || (a == 111)
					|| (a == 117)) {
				if ((b != 97) || (b != 101) || (b != 105) || (b != 111)
						|| (b != 117)) {
					if (b >= 97) {
						System.out.print((char) b);
						System.out.print((char) a);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println(stringToMD5("112233"));

	}

	/**
	 * 生成24位UUID
	 * 
	 * @return UUID 24bit string
	 */
	public static synchronized String getUUID() {
		count++;
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssssss");
		if (count > 9) {
			count = 0;
		}
		Long uuid = new Long(formatter.format(currentTime) + count);
		return uuid.toString();
	}

	public static synchronized long getLongUUID() {
		count++;
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssssss");
		if (count > 9) {
			count = 0;
		}
		Long uuid = new Long(formatter.format(currentTime) + count);
		return uuid;
	}

	protected static int countOrd = 0;

	/**
	 * 生成24位UUID
	 * 
	 * @return UUID 24bit string
	 */
	public static synchronized String getOrderUUID() {
		countOrd++;
		long time = System.currentTimeMillis();
		if (countOrd > 9) {
			countOrd = 0;
		}
		String uuid = time + countOrd + "";
		return uuid;
	}

	/**
	 * null值的改变
	 * 
	 * @param in
	 *            指定字符丄1�7
	 * @return 如果指定字符串为null,返回"",否则返回本身
	 */
	public static String chgNull(String in) {
		String out = null;
		out = in;
		if (out == null || (out.trim()).equals("null")) {
			return "";
		} else {
			return out.trim();
		}
	}

	/**
	 * double类型取小数点后面几位
	 * 
	 * @param val
	 *            指定double型数孄1�7
	 * @param precision
	 *            取前几位
	 * @return 转换后的double数字
	 */
	public static Double roundDouble(double val, int precision) {
		Double ret = null;
		try {
			double factor = Math.pow(10, precision);
			ret = Math.floor(val * factor + 0.5) / factor;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	/***************************************************************************
	 * getPartOfValue取得以个字符串中的一部分
	 * 
	 * @value 被截取的字符丄1�7(value 仅限数字字符丄1�7)
	 * @mark 丄1�7仄1�7么样的符号为截取表准
	 * @flag true 表式截取前一段，false 表式截取后段
	 */
	public static int getPartOfValue(String value, String mark, boolean flag) {
		String temp = "";
		if (flag) {
			temp = value.substring(0, value.indexOf(mark));
		} else {
			temp = value.substring(value.indexOf(mark) + 1);
		}
		return Integer.parseInt(temp);
	}

	/**
	 * 浮点型数据截取函敄1�7
	 * 
	 * @param fl
	 *            原数捄1�7
	 * @param num
	 *            要保留的小数位数（如果�1�7�小于等亄1�7�则默认保留两位小数＄1�7
	 * @return
	 */
	public static float floatcut(float fl, int num) {

		if (num <= 0) {
			num = 2;
		}
		BigDecimal b = new BigDecimal(fl);
		float f = b.setScale(num, BigDecimal.ROUND_HALF_UP).floatValue();
		return f;
	}

	/**
	 * 判断丄1�7个对豄1�7 是不是为null or ""
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(String obj) {
		boolean falg = false;
		if (null != obj && !"".equals(obj)) {
			falg = true;
		}
		return falg;
	}

	/**
	 * 判断丄1�7个List是否为null,并且是否有�1�7�1�7
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isNull(List<?> list) {
		boolean flag = false;
		if (null != list && list.size() > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 功能:返回当前登录的用户对豄1�7
	 * <p>
	 * 作�1�7�文齐辉 2013幄1�7�1�7旄1�7 下午12:22:55
	 * 
	 * @param request
	 */

	/**
	 * * 字符串转换到时间格式 *
	 * 
	 * @param dateStr
	 *            霄1�7要转换的字符丄1�7 *
	 * @param formatStr
	 *            霄1�7要格式的目标字符丄1�7 举例 yyyy-MM-dd *
	 * @return Date 返回转换后的时间 *
	 * @throws ParseException
	 *             转换异常
	 */
	public static Date StringToDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 格式化日朄1�7
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String DatetoString(Date date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * 格式化日朄1�7
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String DateFormat(Date date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateFormat.format(date);
	}

	public static String DatetoStringYMD(Date date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	/**
	 * 格式化日朄1�7
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String DateFormatSimple(Date date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	/**
	 * 日期格式匄1�7(默认丄1�7 yyyy-MM-dd)
	 * 
	 * @param date
	 * @return
	 */
	public static Date formatDate(Date time) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = null;
		try {
			date2 = formatDate.parse(DateFormat(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date2;
	}

	public static String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		fileSizeString = df.format((double) fileS / 1024)/* + "K" */;
		return fileSizeString;
	}

	/**
	 * 功能:验证是否是数孄1�7
	 * <p>
	 * 作�1�7�文齐辉 2013幄1�7朄1�7�1�7 下午6:21:36
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 功能:md5加密
	 * <p>
	 * 作�1�7�文齐辉 2013-11-12 上午09:55:11
	 * 
	 * @param str
	 * @return
	 */
	public static String stringToMD5(String str) {
		return DigestUtils.md5Hex(str);
	}

	/**
	 * 功能:取公共参敄1�7
	 * 
	 * @param request
	 */
	public static Map<String, Object> getCommonParams(HttpServletRequest request) {

		int start = 1;
		int limit = 20;
		String id = null;

		String sat = request.getParameter("start");
		String lmt = request.getParameter("limit");
		id = request.getParameter("id");

		if (null != sat && !"".equals(sat)) {
			start = Integer.parseInt(sat);
		}

		if (start < 1) {
			start = 1;
		}

		if (null != lmt && !"".equals(lmt)) {
			limit = Integer.parseInt(lmt);
		}

		if (limit < 1) {
			limit = 20;
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("start", start);
		parameters.put("limit", limit);
		parameters.put("id", id);

		return parameters;

	}

	/**
	 * 生成爱信号
	 * 
	 * @return
	 */
	public static String getAsId() {
		Random ran = new Random(System.currentTimeMillis());
		String mId = String.valueOf(ran.nextLong());
		String realId = mId.substring(mId.length() - 8, mId.length());
		return String.valueOf(Long.parseLong(realId) + 10000000);
	}

	/**
	 * 判断一个Map对象 是不是为null
	 * 
	 * @param map
	 * @return 不为空返回true
	 */
	public static boolean isNull(Map<Object, Object> map) {
		boolean flag = false;
		if (map != null && map.size() > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 获取格式化后的当前日期
	 * 
	 * @return
	 */
	public static String getCurrDate() {
		Date currDate = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(currDate);
	}

	/**
	 * 获取按照指定样式格式化后的当前日期
	 * 
	 * @return
	 */
	public static String getCurrDate(SimpleDateFormat sf) {
		Date currDate = new Date();
		return sf.format(currDate);
	}

	/**
	 * 生成六位随机数
	 * 
	 * @return
	 */
	public static String getRandom() {
		Random r = new Random();
		int random = r.nextInt(999999);
		if (random > 100000) {
			return String.valueOf(random);
		} else {
			return getRandom();
		}
	}

	/**
	 * 获取服务器Session
	 * 
	 * @param request
	 * @return 服务器Session
	 */
	public static HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}

	/**
	 * 验证字符串是否是手机号
	 * 
	 * @param username
	 * @return
	 */
	public static boolean isMobileNo(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 获取群号
	 * 
	 * @return
	 */
	public static String getGroupId() {
		Random ran = new Random(System.currentTimeMillis());
		String mId = String.valueOf(ran.nextLong());
		String realId = mId.substring(mId.length() - 7, mId.length());
		return String.valueOf(Long.parseLong(realId) + 1000000);
	}

}