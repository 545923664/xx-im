package com.jzwl.mytest.demo.dao;

/**
 * 范例DAO接口
 * 
 * @ClassName: DemoDao
 * @description: 通过看这个例子，让大家更快掌握这个框架
 * @author: zhang guo yu
 * @date: 2015-1-21 下午2:54:43
 * @version: 1.0.0
 */
public interface DemoDao {
	
	/**
	 * demo方法1
	 * */
	public boolean myStepOne(int id) throws Exception;

	/**
	 * demo方法2
	 * */
	public boolean myStepTwo(int id) throws Exception;
	
	/**
	 * demo方法，测试数据方法
	 * */
	public void getData();
}
