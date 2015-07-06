package com.jzwl.common.jms;

import java.io.Serializable;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 消息队列发送类，系统全部的异步消息，统一由这里发出
 * 
 * @ClassName: MessageSender
 * @author: zhang guo yu
 * @date: 2015-1-21 下午2:54:43
 * @version: 1.0.0
 */
public class MessageSender {
	@Autowired
	private JmsTemplate jmsTemplate;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	/**
	 * 发送对象类型的消息，目前设计只支持Map类型的参数
	 * Map其中必须含有operationType字段(需要在JmsConstant中注册)，标识是哪个业务操作
	 * @param message 消息对象，
	 *
	 * */
	public void sendMessage(final Map message) {
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage textMessage = session.createObjectMessage((Serializable) message);
				return textMessage;
			}
		});
	}

}
