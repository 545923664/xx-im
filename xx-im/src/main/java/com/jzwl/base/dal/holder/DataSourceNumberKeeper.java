package com.jzwl.base.dal.holder;

import org.springframework.stereotype.Component;

/**
 * 全局读写数据源持有类
 * 
 * @author zhang guo yu
 * @since 2013-8-23
 * @version 1.0.0
 * @see JDK1.6
 * 
 */
@Component("dataSourceNumberKeeper")
public class DataSourceNumberKeeper {

	public static int readNumber;// 读数据源总数(全局)
	public static int writeNumber;// 写数据源总数(全局)

	private int readDataSourceNumber;// 读数据源总数(注入用)
	private int writeDataSourceNumber;// 写数据源总数(注入用)

	private static DataSourceNumberKeeper dataSourceNumberKeeper;

	public void init() {
		dataSourceNumberKeeper = this;
		dataSourceNumberKeeper.readNumber = this.readDataSourceNumber;
		dataSourceNumberKeeper.writeNumber = this.writeDataSourceNumber;
	}

	public int getReadDataSourceNumber() {
		return readDataSourceNumber;
	}

	public void setReadDataSourceNumber(int readDataSourceNumber) {
		this.readDataSourceNumber = readDataSourceNumber;
	}

	public int getWriteDataSourceNumber() {
		return writeDataSourceNumber;
	}

	public void setWriteDataSourceNumber(int writeDataSourceNumber) {
		this.writeDataSourceNumber = writeDataSourceNumber;
	}

}
