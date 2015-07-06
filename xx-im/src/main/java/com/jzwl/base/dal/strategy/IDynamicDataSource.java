package com.jzwl.base.dal.strategy;

/**
 * 读写策略接口
 * 
 * @author zhang guo yu
 * @since 2013-11-12
 * @version 1.0.0
 * @see JDK1.6
 * 
 */
public interface IDynamicDataSource {

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
	public void balanceReadDataSource(String spliteType,String spliteKey);

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
	public void balanceWriteDataSource(String spliteType,String spliteKey);
}
