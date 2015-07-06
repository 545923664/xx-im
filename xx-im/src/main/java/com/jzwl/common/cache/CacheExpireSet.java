package com.jzwl.common.cache;

import java.util.Map;

import com.jzwl.common.constant.GlobalConstant;

/**
 * 各个缓存集合中的数据的超时管理类
 * @author zhang guo yu
 * @version 1.0.0
 * @since 2015-02-01
 * */
public class CacheExpireSet {
	
	private Map map;//超时时间持有Map，因为是单例模式，所以不用static
	
	public CacheExpireSet(Map map){
		this.map = map;
	}
	
	/**
	 * 获取某个缓存集合的超时数据，如果没有配置，则默认半小时过期（-1表示永远不过期）
	 * @param cacheName 缓存集合名称
	 * */
	public long getExpireTime(String cacheName){
		Object ob = map.get(cacheName);
		if(ob != null){
			return (Long) map.get(cacheName);
		}
		return GlobalConstant.Global_CACHETIMEOUT;
	}
	
}
