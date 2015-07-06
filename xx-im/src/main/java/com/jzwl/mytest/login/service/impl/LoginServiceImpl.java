package com.jzwl.mytest.login.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzwl.base.service.BaseService;
import com.jzwl.mytest.login.dao.LoginDao;
import com.jzwl.mytest.login.service.LoginService;

/**
 * 登录service接口实现类
 * 
 * @ClassName: LoginServiceImpl
 * @author: zhang guo yu
 * @date: 2015-1-21 下午2:54:43
 * @version: 1.0.0
 */
@Service("loginService")
public class LoginServiceImpl extends BaseService implements LoginService {
	
	@Autowired
	private LoginDao loginDao;
	/**
	 * 通过用户名获取用户账户信息
	 * @param userName 用户名
	 * */
	public Map<String,Object> getUserInfoByUserName(String userName){
		return loginDao.getUserInfoByUserName(userName);
	}
	
	/**
	 * 通过用户名获取用户角色权限信息
	 * @param userName 用户名
	 * */
	public List<Map<String, Object>> getUserRolesInfoByUserName(String userName){
		return loginDao.getUserRolesInfoByUserName(userName);
	}
}
