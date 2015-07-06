package com.jzwl.common.init.infoputer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.jzwl.base.dal.strategy.IDynamicDataSource;
import com.jzwl.base.service.BaseReadService;
import com.jzwl.common.init.infoholder.SystemInfoInitAndHolder;

/**
 * 系统信息字典表初始化类，用于在JVM启动时，加载数据库字典表信息到全局MAP中备用
 * 
 * @ClassName: SystemInfoInitBySql
 * @author: zhang guo yu
 * @date: 2015-1-21 下午2:54:43
 * @version: 1.0.0
 */
public class SystemInfoInitBySql implements
		ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private BaseReadService baseReadService;// 基类读service

	@Autowired
	private IDynamicDataSource dynamicDataSource;// 用于进行数据源的切换

	/**
	 * jvm服务启动的时候，初始化SQL字典表相关常量
	 * */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext ap = event.getApplicationContext();
		baseReadService = (BaseReadService) ap.getBean("baseReadService");// 获取基类读service
		dynamicDataSource = (IDynamicDataSource) ap
				.getBean("dynamicDataSource");// 获取数据源切换工具类
		Properties properties = new Properties();
		try {
			// 根据路径，抓取SQL配置文件
			String path = new File(SystemInfoInitBySql.class.getClassLoader()
					.getResource("system_info_init_by_sql.properties").toURI())
					.getPath();
			InputStream inputStream = new BufferedInputStream(
					new FileInputStream(new File(path)));
			properties.load(inputStream);
			inputStream.close(); // 关闭流
			// 循环配置文件，执行每条SQL
			// type：1，返回结果是list；2，返回结果是map；3，返回结果集合条数long；可拓展
			Enumeration<?> enu = properties.propertyNames();
			while (enu.hasMoreElements()) {
				String key = String.valueOf(enu.nextElement());// 配置文件中，K
				String sqlAndType = properties.getProperty(key);// 配置文件中，V
				String sql = sqlAndType.split(",")[1];// 需要执行的sql
				String type = sqlAndType.split(",")[0];// SQL的查询返回值的类型：1，list；2，map；3，list‘s
														// size (long)；可拓展
				//init(key, sql, type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化每一个sql对应的系统字典值
	 * */
	public void init(String key, String sql, String type) {
		dynamicDataSource.balanceReadDataSource(null, null);// 职能切换一下读数据源
		// 根据sql类型，执行对应方法并保存结果
		if ("1".equals(type)) {// list
			List list = baseReadService.queryForList(sql);
			SystemInfoInitAndHolder.setSystemInfo(key, list);
		} else if ("2".equals(type)) {// map
			Map map = baseReadService.queryForMap(sql);
			SystemInfoInitAndHolder.setSystemInfo(key, map);
		} else if ("3".equals(type)) {// long
			long value = baseReadService.getRecordCount(sql);
			SystemInfoInitAndHolder.setSystemInfo(key, value);
		}
	}

}
