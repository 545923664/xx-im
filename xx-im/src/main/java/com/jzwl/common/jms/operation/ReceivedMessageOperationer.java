package com.jzwl.common.jms.operation;

import java.util.Map;

/**
 * 消息队列接收处理接口，系统全部的异步消息，统一由这里处理，通过message中的operationType字段来区分是哪个业务操作
 * 
 * @ClassName: ReceiveMessageListener
 * @author: zhang guo yu
 * @date: 2015-1-21 下午2:54:43
 * @version: 1.0.0
 */
public interface ReceivedMessageOperationer {
	
	/**
	 * 处理接收消息，目前设计只支持Map对象类型
	 * 可以通过message中的operationType字段来区分是哪个业务操作
	 * */
	public void doBusinessOperation(Map map);
	
}
