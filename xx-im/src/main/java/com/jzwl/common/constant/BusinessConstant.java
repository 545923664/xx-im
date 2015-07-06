package com.jzwl.common.constant;

/**
 * 业务模块常量类，
 * 各个业务模块，需要先在此定义业务模块常量名称，才能去对应的使用各种操作，模块前缀BUSINESS_
 *            业务模块下的二级业务标识常量名称，前缀BUSINESS_XXX_（主要用于本业务模块进行redis缓存和mongodb存储）
 * @author zhang guo yu
 * @version 1.0.0
 * @since 2015-02-01
 * */
public class BusinessConstant {
	
	/**
	 * app访问校验（用于redis）
	 */
	public static final String BUSINESS_APP_CHECK = "business_app_check";
	
	/**
	 * 登录模块map（用于redis）
	 */
	public static final String BUSINESS_LOGIN_MAP = "business_login_map";
	
	/**
	 * 登录模块list（用于redis）
	 */
	public static final String BUSINESS_LOGIN_LIST = "business_login_list";
	
	/**
	 * demo模块（用于redis）
	 */
	public static final String BUSINESS_DEMO = "business_demo";
	
	/**
	 * demo模块--列表（用于mongodb标识各个业务模块二级类型）
	 */
	public static final String BUSINESS_DEMO_LIST = "business_demo_list";
	
}
