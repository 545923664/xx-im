package com.jzwl.instant.pojo;

import java.util.Map;
import java.util.Set;

/**
 * 服务号消息体
 * 
 * @author Administrator
 * 
 */
public class ServiceMessage {

	private Map<String, String> banner;

	private Set<Map<String, String>> items;

	public Map<String, String> getBanner() {
		return banner;
	}

	public void setBanner(Map<String, String> banner) {
		this.banner = banner;
	}

	public Set<Map<String, String>> getItems() {
		return items;
	}

	public void setItems(Set<Map<String, String>> items) {
		this.items = items;
	}

}
