package com.jzwl.common.log;

import java.util.Date;

import com.jzwl.base.pojo.BasePojo;
import com.jzwl.common.constant.MongodbConstant;

/**
 * 记录日志的POJO，继承BasePojo，用来对象化操作mongodb，保存过程日志
 * 
 * @author zhang guo yu
 * @version 1.0
 * @since 2014-12-24
 * */
public class SystemLogPojo extends BasePojo {

	private String className;//涉及到的类名
	private String userName;//涉及到的用户名
	private String userAlias;//涉及到的用户中文名
	private String serverIp;//涉及到的服务器IP
	private int serverPort;//涉及到的服务器端口
	private String modelName;//涉及到的业务模块名称(在BusinessConstant必须要有声明)
	private String operationType;//涉及到的操作类型(在OperationConstant必须要有声明)
	private String message;//涉及到的具体描述信息
	
	public SystemLogPojo(){
		
	}
	
	public SystemLogPojo(long id,Class className,String userName,String userAlias,String serverIp,int serverPort,String modelName,String operationType,String message){
		this.id = id;
		this.operationType = MongodbConstant.MONGO_FIRST_TEST;
		this.createUserName = userName;
		this.updateUserName = userName;
		Date date = new Date();
		this.createTime = date;
		this.lastModifyTime = date;
		this.className = className.getName();
		this.userName = userName;
		this.userAlias = userAlias;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.modelName = modelName;
		this.operationType = operationType;
		this.message = message;
		this.deleteFlag = 1;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAlias() {
		return userAlias;
	}

	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
