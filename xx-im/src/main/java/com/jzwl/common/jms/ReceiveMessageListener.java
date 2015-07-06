package com.jzwl.common.jms;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.jzwl.common.jms.operation.ReceivedMessageOperationer;

/**
 * 消息队列接收类，系统全部的异步消息，统一由这里接收，然后根据逻辑分支进行处理
 * 
 * @ClassName: ReceiveMessageListener
 * @author: zhang guo yu
 * @date: 2015-1-21 下午2:54:43
 * @version: 1.0.0
 */
public class ReceiveMessageListener implements MessageListener {

	@Autowired
	private ReceivedMessageOperationer receivedMessageOperationer;//处理各个JMS逻辑的接口（以后业务和数据多了，可以拆分多个JMS服务和接口，就不用这样共用了）

	/**
	 * 接收消息，目前设计只支持Map对象类型
	 * 可以通过message中的operationType字段来区分是哪个业务操作
	 * */
	public void onMessage(Message message) {
		ObjectMessage obj = (ObjectMessage) message;
		try {
			Object objParams = obj.getObject();
			Map map = (Map) objParams;
			receivedMessageOperationer.doBusinessOperation(map);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
