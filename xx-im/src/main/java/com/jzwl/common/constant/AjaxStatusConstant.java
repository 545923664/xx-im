package com.jzwl.common.constant;

/**
 * ajax状态常量类，模块前缀AJAX_STATUS_
 * 统一标记ajax的json返回串的状态（json串内，必须携带ajax_status字段，用来判定本次ajax操作是否成功）
 * @author zhang guo yu
 * @version 1.0.0
 * @since 2015-02-01
 * */
public class AjaxStatusConstant {

	/**
	 * ajax操作成功
	 */
	public static final String AJAX_STATUS_SUCCESS = "ajax_status_success";
	
	/**
	 * ajax操作失败
	 */
	public static final String AJAX_STATUS_FAILURE = "ajax_status_failure";
	
	
	/**
	 * ajax操作超时
	 */
	public static final String AJAX_STATUS_TIMEOUT = "ajax_status_timeout";
}
