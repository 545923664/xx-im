package com.jzwl.base.dal.holder;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 切换数据源类
 * 
 * @author zhang guo yu
 * @since 2013-8-23
 * @version 1.0.0
 * @see JDK1.6
 * 
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	//切换当前线程的数据源
	@Override
	protected Object determineCurrentLookupKey() {
		return DbContextHolder.getDbType();
	}
}
