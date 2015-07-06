package com.jzwl.common.log;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.jzwl.common.id.IdFactory;

/**
 * 记录日志的基类，提供debug，error和过程中的记录日志方法
 * 
 * @author zhang guo yu
 * @version 1.0
 * @since 2014-12-24
 * */
@Component("systemLog")
public class SystemLog {
	
//	@Autowired
//	private MongoTemplate mongoTemplate;//mongodb模板，将过程日志放入mongodb
	
	@Autowired
	private IdFactory idFactory;//ID生成器，记录日志时需要进行排序，方便切分
	
	/**
	 * 记录调试信息，比如运行的SQL，或者其他
	 * 
	 * @param className      记录日志的类
	 * @param message        详细日志信息
	 * */
	public void debugLog(Class className,String message){
		Logger log = Logger.getLogger(className);
		if(log.isDebugEnabled()){
			log.debug("MyDebug------>" + message + "<------MyDebug");
		}
	}
	
	/**
	 * 记录运行时信息，比如用户操作痕迹
	 *  
	 * @param className      记录日志的类
	 * @param userName       当前操作用户名(若无法提供，则给字符串：-- )
	 * @param userAlias      当前操作中文名(若无法提供，则给字符串：-- )
	 * @param modelName      记录的哪个模块（每个模块（甚至是页面）要统一名称，统一在BusinessConstant中声明定义，若无法提供，则给字符串：-- ）
	 * @param operationType  操作类型：查询；删除；修改；新增；统计分析；数据导出；数据导入；调用外部接口；开发/测试调试（如果操作类型不足，请在OperationConstant中进行查询或添加，要保证统一）
	 * @param message        详细日志信息
	 * */
	public void infoLog(Class className,String userName,String userAlias,String modelName,String operationType,String message){
		long id = idFactory.nextId();//生成日志ID
		String ip = System.getProperty("localaddress");//获取当前服务器IP地址
		int port = Integer.parseInt(System.getProperty("localport"));//获取当前服务器端口
		Logger log = Logger.getLogger(className);
		if(log.isInfoEnabled()){
			log.info(">>>>>>[" + ip + ":" + port + "]" + "--->" + "(" + userName + "-" + userAlias + ")" + "--->" + "{" + modelName + "}" + "--->" + "#" + operationType + "#" + "--->" + "*" + message + "*");
		}
		//SystemLogPojo systemLogPojo = new SystemLogPojo(id,className,userName,userAlias,ip,port,modelName,operationType,message);
		//mongoTemplate.save(systemLogPojo);//用户行为日志，暂时放入mongodb中去，后期也可以考虑放入文件系统或者数据库，便于对接大数据
	}
	
	/**
	 * 记录报错信息并打印错误日志，用在catch里边
	 *  
	 * @param className      记录日志的类
	 * @param userName       当前操作用户名(若无法提供，则给字符串：-- )
	 * @param userAlias      当前操作中文名(若无法提供，则给字符串：-- )
	 * @param modelName      记录的哪个模块（每个模块（甚至是页面）要统一名称，统一在BusinessConstant中声明定义，若无法提供，则给字符串：-- ）
	 * @param operationType  操作类型：查询；删除；修改；新增；统计分析；数据导出；数据导入；调用外部接口；开发/测试调试（如果操作类型不足，请在OperationConstant中进行查询或添加，要保证统一）
	 * @param message        详细日志信息
	 * @param t              错误信息
	 * */
	public void errorLog(Class className,String userName,String userAlias,String modelName,String operationType,String message,Exception t){
		String ip = System.getProperty("localaddress");//获取当前服务器IP地址
		int port = 8080;//Integer.parseInt(System.getProperty("localport"));//获取当前服务器端口
		Logger log = Logger.getLogger(className);
		log.error(">>>>>>[" + ip + ":" + port + "]" + "--->" + "(" + userName + "-" + userAlias + ")" + "--->" + "{" + modelName + "}" + "--->" + "#" + operationType + "#" + "--->" + "*" + message + "*",t);
	}
	
}
