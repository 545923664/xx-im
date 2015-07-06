package com.jzwl.mytest.demo.service;

/**
 * 范例SERVICE接口，定义处理业务逻辑的接口方法
 * 
 * @ClassName: DemoService
 * @description: 通过看这个例子，让大家更快掌握这个框架
 * @author: zhang guo yu
 * @date: 2015-1-21 下午2:54:43
 * @version: 1.0.0
 */
public interface DemoService {
	/**
	 * demo业务方法1
	 * */
	public boolean testTransaction() throws Exception;
	
	/**
	 * demo方法，测试数据方法
	 * */
	public void getData();

}
