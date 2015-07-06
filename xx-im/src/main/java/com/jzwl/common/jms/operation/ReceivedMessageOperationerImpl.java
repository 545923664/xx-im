package com.jzwl.common.jms.operation;

import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jzwl.base.dal.strategy.IDynamicDataSource;
import com.jzwl.base.service.BaseWriteService;
import com.jzwl.common.constant.JmsConstant;

/**
 * 消息队列接收处理实现类，系统全部的异步消息，统一由这里处理，通过message中的operationType字段来区分是哪个业务操作
 * 
 * @ClassName: ReceiveMessageListener
 * @author: zhang guo yu
 * @date: 2015-1-21 下午2:54:43
 * @version: 1.0.0
 */
@Component("receivedMessageOperationer")
public class ReceivedMessageOperationerImpl implements ReceivedMessageOperationer {

	@Autowired
	private BaseWriteService baseWriteService;//基类写service
	
	@Autowired
	private IDynamicDataSource dynamicDataSource;//用于进行数据源的切换
	
	/**
	 * 处理接收消息，目前设计只支持Map对象类型
	 * 可以通过message中的operationType字段来区分是哪个业务操作
	 * */
	@Override
	public void doBusinessOperation(Map map) {
		String operationType = ObjectUtils.toString(map.get("operationType"),"");
		if(JmsConstant.JMS_ID.equals(operationType)){//如果是ID生成器
			dynamicDataSource.balanceWriteDataSource(null, null);//轮转切换数据源
			baseWriteService.updateIdInfo(new Object[]{map.get("laststr").toString(),map.get("newid").toString(),map.get("ipandport").toString()});
		}
		//根据operationType的其他值，展开其他业务分支
	}

}
