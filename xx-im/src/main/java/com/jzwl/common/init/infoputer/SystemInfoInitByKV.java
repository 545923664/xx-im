package com.jzwl.common.init.infoputer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.jzwl.common.init.infoholder.SystemInfoInitAndHolder;

/**
 * 系统信息字典表初始化类，用于在JVM启动时，加载配置文件中的KV信息到全局MAP中备用
 * 
 * @ClassName: SystemInfoInitBySql
 * @author: zhang guo yu
 * @date: 2015-1-21 下午2:54:43
 * @version: 1.0.0
 */
public class SystemInfoInitByKV implements
		ApplicationListener<ContextRefreshedEvent> {

	/**
	 * jvm服务启动的时候，初始化配置文件中的KV信息相关常量
	 * */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Properties properties = new Properties();
		try {
			// 根据路径，抓取KV配置文件
			String path = new File(SystemInfoInitByKV.class.getClassLoader()
					.getResource("system_info_init_by_kv.properties").toURI())
					.getPath();
			InputStream inputStream = new BufferedInputStream(
					new FileInputStream(new File(path)));
			properties.load(inputStream);
			inputStream.close(); // 关闭流
			// 循环配置文件，加载每个KV
			Enumeration<?> enu = properties.propertyNames();
			while (enu.hasMoreElements()) {
				String key = String.valueOf(enu.nextElement());// 配置文件中，K
				String value = properties.getProperty(key);// 配置文件中，V
				SystemInfoInitAndHolder.setSystemInfo(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
