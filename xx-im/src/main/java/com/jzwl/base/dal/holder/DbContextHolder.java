package com.jzwl.base.dal.holder;

/**
 * 读写数据源线程切换类
 * 
 * @author zhang guo yu
 * @since 2013-8-23
 * @version 1.0.0
 * @see JDK1.6
 * 
 */
public class DbContextHolder {
	private static final ThreadLocal contextHolder = new ThreadLocal();

	public static void setDbType(String dbType) {
		contextHolder.set(dbType);
	}

	public static String getDbType() {
		return (String) contextHolder.get();
	}

	public static void clearDbType() {
		contextHolder.remove();
	}
}
