package com.jzwl.instant.service;


/**
 * 处理消息
 * 
 * @author Administrator
 * 
 */
public interface RecieveService {

	// 接受并保存消息
	public void receive(String json);

}
