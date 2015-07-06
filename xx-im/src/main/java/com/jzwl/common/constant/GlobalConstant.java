package com.jzwl.common.constant;

/**
 * 全局常量类，全局常量定义在此,前缀Global_
 * @author zhang guo yu
 * @version 1.0.0
 * @since 2015-02-01
 * */
public class GlobalConstant {
	
	/**
	 * 当前session中的用户User标识
	 */
	public static final String Global_SESSION_USER = "global_session_user";
	
	/**
	 * 初始化缓存的字典表信息标识前缀
	 */
	public static final String Global_INFO_CACHE_INIT = "global_info_cache_init";
	
	/**
	 * 分页显示的时候，每页显示多少条记录
	 * */
	public static final int Global_PAGESIZE = 20;
	/**
	 * 分页显示的时候，默认显示第几页
	 * */
	public static final int Global_PAGENUM = 1;
	
	/**
	 * 缓存超时时间（单位：秒）
	 * */
	public static final int Global_CACHETIMEOUT = 1800;
	
	/**
	 * 用户重复提交判定标识
	 */
	public static final String Global_SUBMIT = "global_submit";
	
	/**
	 * 用户操作正常提交
	 */
	public static final String Global_SUBMIT_SUCCESS = "1";
	
	/**
	 * 用户操作重复提交
	 */
	public static final String Global_SUBMIT_FAILURE = "0";
	
	/**
	 * app访问合法
	 */
	public static final String Global_APP_SUCCESS = "1";
	
	/**
	 * app访问非法
	 */
	public static final String Global_APP_FAILURE = "0";
	
	
	/**
	 * app访问代码端密钥
	 */
	public static final String Global_APP_SECRET = "fjkl?cxv@fds0-=f34";
}
