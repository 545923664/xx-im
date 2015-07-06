//package com.jzwl.common.session;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
//
///**
// * session包装类，本类的存在，是为了灵活切换集中式session与分布式session
// * 程序中用到session的地方，请统一使用本类的方法
// * 本类可提供方法拓展，便于更好的统一规划与使用session
// * @author zhang guo yu
// * @version 1.0.0
// * @since 2015-02-01
// * */
//public class SessionHolder {
//	
//	/**
//	 * 向session中插入K / V
//	 * @param key   K名称
//	 * @param value V数值
//	 * */
//	public static void setValueToSession(String key,Object value){
//		Subject currentUser = SecurityUtils.getSubject();
//		Session session = currentUser.getSession();
//		session.setAttribute(key, value);
//	}
//	
//	/**
//	 * 从session中获取对应K的V
//	 * @param key   K名称
//	 * @return value V数值
//	 * */
//	public static Object getValueFromSession(String key){
//		Subject currentUser = SecurityUtils.getSubject();
//		Session session = currentUser.getSession();
//		return session.getAttribute(key);
//	}
//}
