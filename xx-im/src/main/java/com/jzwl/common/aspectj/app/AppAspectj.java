package com.jzwl.common.aspectj.app;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.jzwl.base.dal.strategy.IDynamicDataSource;
import com.jzwl.base.service.BaseReadService;
import com.jzwl.common.constant.BusinessConstant;
import com.jzwl.common.constant.GlobalConstant;
import com.jzwl.common.log.SystemLog;
import com.jzwl.common.secret.SecretMaker;
/**
 * 防止重复提交的token切点校验方法类
 * @author zhang guo yu
 * @version 1.0.0
 * @since 2015-02-01
 * */
@Aspect
@Component
public class AppAspectj {
	
	// @Autowired
	// private CacheUtil cacheUtil;//缓存工具类
	
	@Autowired
	private SystemLog systemLog;//日志工具类
	
	@Autowired
	private IDynamicDataSource dynamicDataSource;//用于进行数据源的切换
	
	@Autowired
	private BaseReadService baseReadService;//基类读service
	
	/**
	 * 在执行APP对应的方法之前，先进行系统安全的访问校验
	 * 系统安全校验，包括四大参数，缺一不可：
	 *     v                移动端使用服务端的API版本号
	 *     user_key         移动端唯一用户标识
	 *     access_token     移动端合法校验token
	 *     timestamp        移动端当前时间戳，格式为yyyy-MM-dd HH:mm:ss，例如：2011-06-16 13:23:30
	 * @author zhang guo yu
	 * @since 2011-10-18
	 * */
	@Before("@annotation(app)")
    public void before(JoinPoint jp,App app) {
		Object[] obj = jp.getArgs();
		HttpServletRequest request = (HttpServletRequest) obj[0];
		//HttpServletResponse response = (HttpServletResponse) obj[1];
		String v = request.getParameter("v");//移动端使用服务端的api版本号(避免因为服务端升级，移动端没有升级，导致移动端不可用)
		String user_key = request.getParameter("user_key");//移动端访问服务端的唯一标识
		String access_token = request.getParameter("access_token");//访问校验token
		//访问时间戳,格式为yyyy-MM-dd HH:mm:ss，例如：2011-06-16 13:23:30
		//这个参数目前暂时还没有用到，先预留起来，未来会用作token有效时间的进一步强化
		String timestamp = request.getParameter("timestamp");
		//检查系统级别合法性参数，如果参数不全则直接提示APP访问非法
		if(StringUtils.isEmpty(v) || StringUtils.isEmpty(user_key) || StringUtils.isEmpty(access_token) || StringUtils.isEmpty(timestamp)){
			request.setAttribute(BusinessConstant.BUSINESS_APP_CHECK,GlobalConstant.Global_APP_FAILURE);
		}else{//如果参数都合适，那么开始校验
			//如果token是算法合法的，则继续验证下一步
			if(access_token.equals(SecretMaker.MD5(GlobalConstant.Global_APP_SECRET + user_key))){
				//优先从缓存中获取值
				String cacheStr =null;// cacheUtil.get(BusinessConstant.BUSINESS_APP_CHECK + user_key);
				if(cacheStr != null){//如果缓存命中，则使用缓存的值进行校验
					if(access_token.equals(cacheStr)){//如果缓存的值和access_token一致，则访问合法
						request.setAttribute(BusinessConstant.BUSINESS_APP_CHECK,GlobalConstant.Global_APP_SUCCESS);
					}else{//如果缓存的值和access_token不一致，则访问非法
						request.setAttribute(BusinessConstant.BUSINESS_APP_CHECK,GlobalConstant.Global_APP_FAILURE);
					}
				}else{//如果缓存里没有命中，则查询数据库
					dynamicDataSource.balanceReadDataSource(null, null);//匹配一下读数据源
					Map argMap = new HashMap();//使用用户标识，从数据库中查询出该用户对应的访问码
					argMap.put("usercode", user_key);
					Map resMap = baseReadService.getNamedFirstRowValue("select * from tbappsecret where usercode =:usercode ", argMap);
					if(resMap == null || resMap.get("usersecret") == null){//如果从数据库中没有取到当前用户对应的访问码，则判定app访问失败
						request.setAttribute(BusinessConstant.BUSINESS_APP_CHECK,GlobalConstant.Global_APP_FAILURE);
					}else{//如果数据库中存在该用户的访问码
						cacheStr = String.valueOf(resMap.get("usersecret"));
						//先将该访问码存入缓存，节约下一次该用户的走库时间
//						cacheUtil.put(BusinessConstant.BUSINESS_APP_CHECK + user_key,cacheStr);
						if(access_token.equals(cacheStr)){//如果数据库中查出的值和access_token一致，则访问合法
							request.setAttribute(BusinessConstant.BUSINESS_APP_CHECK,GlobalConstant.Global_APP_SUCCESS);
						}else{//如果数据库中查出的值和access_token不一致，则访问非法
							request.setAttribute(BusinessConstant.BUSINESS_APP_CHECK,GlobalConstant.Global_APP_FAILURE);
						}
					}
				}
			}else{//如果token非法，则直接提示APP访问非法
				request.setAttribute(BusinessConstant.BUSINESS_APP_CHECK,GlobalConstant.Global_APP_FAILURE);
			}
		}
    }
}
