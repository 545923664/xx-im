package com.jzwl.common.constant;

/**
 * 操作类型常量类，
 * 在记录日志的时候，需要明确日志操作类型是什么
 * 其他需要进行操作类型状态区分的时候，也要先使用该类进行类型注册,前缀OPERATION_
 * @author zhang guo yu
 * @version 1.0.0
 * @since 2015-02-01
 * */
public class OperationConstant {

	/**
	 * 查询操作
	 */
	public static final String OPERATION_SELECT = "operation_select";
	
	/**
	 * 删除操作
	 */
	public static final String OPERATION_DELETE = "operation_delete";
	
	/**
	 * 修改操作
	 */
	public static final String OPERATION_UPDATE = "operation_update";
	
	/**
	 * 新增操作
	 */
	public static final String OPERATION_ADD = "operation_add";
	
	/**
	 * 统计分析操作
	 */
	public static final String OPERATION_ANALYSE = "operation_analyse";
	
	/**
	 * 数据导出操作
	 */
	public static final String OPERATION_EXPORT = "operation_export";
	
	/**
	 * 数据导入操作
	 */
	public static final String OPERATION_IMPORT = "operation_import";
	
	/**
	 * 调用外部接口操作
	 */
	public static final String OPERATION_OUTUSE = "operation_outuse";
	
	/**
	 * 开发/测试调试操作
	 */
	public static final String OPERATION_DEVANDTEST = "operation_devandtest";
	
}
