package com.jzwl.base.dal.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jzwl.base.dal.holder.DataSourceNumberKeeper;
import com.jzwl.base.dal.holder.DbContextHolder;

/**
 * 读写策略类
 * 
 * @author zhang guo yu
 * @since 2013-11-12
 * @version 1.0.0
 * @see JDK1.6
 * 
 */
@Component("dynamicDataSource")
public class DynamicDataSourceImpl implements IDynamicDataSource {

	@Autowired
	private DataSourceNumberKeeper dataSourceNumberKeeper;// 读写数据源的持有类

	/**
	 * 根据策略，获取读数据源（目前暂时不提供轮转算法）
	 * 
	 * @param spliteType  轮转业务类型，用于进行Y轴切换
	 * @param spliteKey   轮转KEY主键值，用于进行Z轴切换
	 * @author zhang guo yu
	 * @since 2013-11-12
	 * @version 1.0.0
	 * @return
	 */
	@Override
	public void balanceReadDataSource(String spliteType,String spliteKey) {
		String dataSourceBeanName = "readDataSource-1";
		//do something
		DbContextHolder.setDbType(dataSourceBeanName);
	}

	/**
	 * 根据策略，获取写数据源（目前暂时不提供轮转算法）
	 * 
	 * @param spliteType  轮转业务类型，用于进行Y轴切换
	 * @param spliteKey   轮转KEY主键值，用于进行Z轴切换
	 * @author zhang guo yu
	 * @since 2013-11-12
	 * @version 1.0.0
	 * @return
	 */
	@Override
	public void balanceWriteDataSource(String spliteType, String spliteKey) {
		String dataSourceBeanName = "writeDataSource-1";
		//do something
		DbContextHolder.setDbType(dataSourceBeanName);
	}

}
