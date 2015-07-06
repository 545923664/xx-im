package com.jzwl.mytest.login.dao;

import java.util.List;
import java.util.Map;

/**
 * 登录DAO接口
 * 
 * @ClassName: LoginDao
 * @author: zhang guo yu
 * @date: 2015-1-21 下午2:54:43
 * @version: 1.0.0
 */
public interface LoginDao {
	/**
	 * 通过用户名获取用户账户信息
	 * @param userName 用户名
	 * */
	public Map<String,Object> getUserInfoByUserName(String userName);
	
	/**
	 * 通过用户名获取用户角色权限信息
	 * @param userName 用户名
	 * */
	public List<Map<String, Object>> getUserRolesInfoByUserName(String userName);
}
