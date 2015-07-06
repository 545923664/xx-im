package com.jzwl.common.id;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.jzwl.base.dal.strategy.IDynamicDataSource;
import com.jzwl.base.service.BaseWriteService;
import com.jzwl.common.constant.JmsConstant;
import com.jzwl.common.jms.MessageSender;

/**
 * ID生成器，在此生成全局唯一ID
 * 
 * @author zhang guo yu
 * @version 1.0.0
 * @since 2015-02-01
 * */
public class IdFactory implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private BaseWriteService baseWriteService;//基类写service，用于服务初始化的时候，进行ID策略计算
	
	@Autowired
	private IDynamicDataSource dynamicDataSource;//用于进行数据源的切换

//	@Autowired
	private MessageSender messageSender;// 消息队列发出类，用于异步更新ID规则

	private static AtomicLong uniqueId = null;// ID迭代生成类，是线程安全的，而且扛高并发。100万次生成，耗费8秒多

	private int idAddStep;// 数据库ID更新的步长

	private static String Atlaststr;// 当前JVM的ID后缀

	private static String IpAndPort;// 当前JVM的IP和服务端口

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext ap = event.getApplicationContext();
		baseWriteService = (BaseWriteService) ap.getBean("baseWriteService");
//		messageSender = (MessageSender) ap.getBean("messageSender");
		init();
	}

	/**
	 * jvm服务启动的时候，初始化ID生成器
	 * */
	public void init() {
		if (uniqueId != null) {// 避免多次初始化
			return;
		}
		String ip = System.getProperty("localaddress");// 获取当前服务器IP地址
		String port = System.getProperty("localport");// 获取当前服务器端口
		Atlaststr =  System.getProperty("addstr");// 获取当前服务器的后缀
		if (ip != null && port != null && Atlaststr != null) {
			dynamicDataSource.balanceWriteDataSource(null, null);//轮转切换数据源
			Map map = baseWriteService.queryForIdInfo(ip + ":" + port);// 从数据库中获取对应服务器的ID生成策略
			IpAndPort = ip + ":" + port;// 构建主机及端口的查询条件信息
			if (map.get("newid") != null) {// 如果对应ID生成策略存在，则更新策略，防止由于上次以外宕机造成ID混乱
				String newid = String.valueOf(map.get("newid"));
				long tempId = Long.parseLong(newid);
				tempId = tempId + 1;// ID生成策略的计数+1，这里是关键保证
				Object[] insertArgs = new Object[] { Atlaststr, tempId,
						ip + ":" + port };
				baseWriteService.updateIdInfo(insertArgs);
				uniqueId = new AtomicLong(tempId * idAddStep);
			} else {// 如果对应策略不存在，则根据IP和端口来新建ID生成策略
				Object[] insertArgs = new Object[] { ip + ":" + port,
						Atlaststr, 1 };
				baseWriteService.insertIdInfo(insertArgs);
				uniqueId = new AtomicLong(1 * idAddStep);
			}
		}
	}

	/**
	 * 获取全局唯一的ID
	 * */
	public long nextId() {
		if (uniqueId == null) {// 在单元测试的时候，临时给定ID初始值为0
			uniqueId = new AtomicLong(0);
		}
		long tempId = uniqueId.incrementAndGet();// 获取并发计数器的最新值
		long id = tempId;
		if(StringUtils.isNotBlank(Atlaststr)){//兼容单元测试和正式程序
			id = Long.parseLong(tempId + "" + Atlaststr);// 加入当前服务器的后缀，得出最终返回的long型ID
			if (tempId % idAddStep == 0) {// 如果计数累加到了一定数量，需要更新当前服务器的ID计数量，避免因为宕机造成ID混乱
				Map message = new HashMap();
				message.put("operationType", JmsConstant.JMS_ID);
				message.put("ipandport", IpAndPort);
				message.put("laststr", Atlaststr);
				message.put("newid", (tempId / idAddStep));
				messageSender.sendMessage(message);// 使用消息队列来更新，避免高并发时的时间延迟
			}
		}
		return id;
	}

	public void setIdAddStep(int idAddStep) {
		this.idAddStep = idAddStep;
	}

	public void setBaseWriteService(BaseWriteService baseWriteService) {
		this.baseWriteService = baseWriteService;
	}

	public void setMessageSender(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

}
