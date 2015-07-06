package com.jzwl.mytest.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzwl.base.service.BaseService;
import com.jzwl.mytest.demo.dao.DemoDao;
import com.jzwl.mytest.demo.service.DemoService;

/**
 * 范例SERVICE实现，实现处理业务逻辑的接口方法
 * 
 * @ClassName: DemoServiceImpl
 * @description: 通过看这个例子，让大家更快掌握这个框架
 * @author: zhang guo yu
 * @date: 2015-1-21 下午2:54:43
 * @version: 1.0.0
 */
@Service("demoService")
public class DemoServiceImpl extends BaseService implements DemoService {
	@Autowired
	private DemoDao demoDao;//业务DAO

	/**
	 * demo业务方法1
	 * */
	public boolean testTransaction()throws Exception {
		int a = 20;
		int m = 10;
		for (int i = 0; i < a; i++) {//业务分支1
			demoDao.myStepTwo(i);
		}
		for (int i = 0; i < m; i++) {//业务分支2
			demoDao.myStepOne(i);
		}
		//int o = 1 / 0;//测试事务
		return true;
	}
	
	/**
	 * demo方法，测试数据方法
	 * */
	public void getData(){
		demoDao.getData();
	}
}
