package com.jzwl.common.constant;

/**
 * mongodb的常量类，
 * 里边有一级类型和数据状态类型
 * 各个业务模块，还需要在BusinessConstant定义具体二级业务模块名称，才能去对应的使用mongodb模板进行CRUD操作
 * 本类内前缀MONGO_
 * @author zhang guo yu
 * @version 1.0.0
 * @since 2015-02-01
 * */
public class MongodbConstant {
	
	/**
	 * 一级类型，测试
	 */
	public static final String MONGO_FIRST_TEST = "mongo_test";
	
	/**
	 * 一级类型，日志
	 */
	public static final String MONGO_FIRST_LOG = "mongo_log";
	
	/**
	 * 一级类型，业务模块
	 */
	public static final String MONGO_FIRST_BUSS = "mongo_buss";
	
	/**
	 * 正常数据，未被删除
	 */
	public static final int MONGO_DELETEFLAG_ALIVE = 1;
	
	/**
	 * 数据已被删除
	 */
	public static final int MONGO_DELETEFLAG_DELETED = 0;
	
}
