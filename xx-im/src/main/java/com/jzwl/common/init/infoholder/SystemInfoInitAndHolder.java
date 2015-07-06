package com.jzwl.common.init.infoholder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 系统信息初始化持有类
 * 
 * @ClassName: SystemInfoInitAndHolder
 * @author: zhang guo yu
 * @date: 2015-1-21 下午2:54:43
 * @version: 1.0.0
 */
public class SystemInfoInitAndHolder {
	/**
	 * 系统信息全局MAP
	 * */
	private static ConcurrentMap<String, Object> systemInfoHolderMap = new ConcurrentHashMap<String, Object>();
	
	/**
	 * JVM启动初始化时，注入系统信息
	 * @param key        系统信息键
	 * @param value      系统信息值
	 * */
	public static void setSystemInfo(String key,Object value){
		systemInfoHolderMap.put(key, value);
	}
	
	/**
	 * 业务程序中，通过key获取全局系统信息
	 * @param key      系统信息键
	 * @return Object  系统信息
	 * */
	public static Object getSystemInfo(String key){
		return systemInfoHolderMap.get(key);
	}
}
