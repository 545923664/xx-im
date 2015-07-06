package com.jzwl.mytest.demo.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jzwl.base.dao.BaseDAO;
import com.jzwl.common.page.PageObject;
import com.jzwl.mytest.demo.dao.DemoDao;

/**
 * 范例DAO实现
 * 
 * @ClassName: DemoDaoImpl
 * @description: 通过看这个例子，让大家更快掌握这个框架
 * @author: zhang guo yu
 * @date: 2015-1-21 下午2:54:43
 * @version: 1.0.0
 */
@Repository("demoDao")
public class DemoDaoImpl implements DemoDao {
	@Autowired
	private BaseDAO baseDAO;//dao基类，操作数据库

	/**
	 * demo方法1
	 * */
	@Override
	public boolean myStepOne(int id)throws Exception {
		String sql = " delete from mytest where id = " + id;
		baseDAO.executeCommand(sql, null);
		//int i = 1/0;//测试事务
		return true;
	}

	/**
	 * demo方法2
	 * */
	@Override
	public boolean myStepTwo(int id)throws Exception {
		String sql = " insert into mytest (id,name) values (:id,:name)";
		Map map = new HashMap();
		map.put("id", id);
		map.put("name", id);
		baseDAO.executeNamedCommand(sql, map);
		return true;
	}
	
	/**
	 * demo方法，测试数据方法
	 * */
	public void getData(){
		String sql2 = " select * from mytest where name like ? order by id asc";
		Map map = new HashMap();
		map.put("start", 2);
		map.put("limit", 4);
		PageObject  po = baseDAO.queryForMPageList(sql2, new Object[]{"%1%"},map);
		List lt = po.getDatasource();
		System.out.println("-------------------------");
		if(lt != null && lt.size() > 0){
			for (int i = 0; i < lt.size(); i++) {
				Map mp = (Map) lt.get(i);
				System.out.println(mp.get("name").toString());
			}
		}else{
			System.out.println("null");
		}
		System.out.println("-------------------------");
		System.out.println("TotalCount:" + po.getTotalCount());
		System.out.println("AbsolutePage:" + po.getAbsolutePage());
	}
}
