package com.jzwl.mytest.login.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwl.base.dao.BaseDAO;
import com.jzwl.common.constant.BusinessConstant;
import com.jzwl.mytest.login.dao.LoginDao;

/**
 * 登录DAO接口实现类
 * 
 * @ClassName: LoginDaoImpl
 * @author: zhang guo yu
 * @date: 2015-1-21 下午2:54:43
 * @version: 1.0.0
 */
@Repository("loginDao")
public class LoginDaoImpl implements LoginDao {

	@Autowired
	private BaseDAO baseDAO;//dao基类接口

	/**
	 * 通过用户名获取用户账户信息
	 * 
	 * @param userName
	 *            用户名
	 * */
	public Map<String, Object> getUserInfoByUserName(String userName) {
		String sql = " select username,password from tbuser where username = ? ";
		return baseDAO.queryForMap(BusinessConstant.BUSINESS_LOGIN_MAP + userName, sql, new Object[] { userName });
	}

	/**
	 * 通过用户名获取用户角色权限信息
	 * 
	 * @param userName
	 *            用户名
	 * */
	public List<Map<String, Object>> getUserRolesInfoByUserName(String userName) {
		String sql = " select r.roleid,r.rolename from tbrole r,tbuserrole ur,tbuser u where r.roleid = ur.roleid and ur.userid = u.userid and u.username = ?  ";
		return baseDAO.queryForList(BusinessConstant.BUSINESS_LOGIN_LIST + userName + "list", sql, new Object[] { userName });
	}
	
}
