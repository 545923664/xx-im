package com.jzwl.common.cache;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 浏览器缓存管理的过滤器类，用于打开  / 关闭浏览器缓存
 * @author zhang guo yu
 * @version 1.0.0
 * @since 2015-02-01
 * */
public class CacheFilter implements Filter {

	private FilterConfig filterConfig; 
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,  
            FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletResponse response = (HttpServletResponse) res;  
        HttpServletRequest request = (HttpServletRequest) req;  
        
        String typeFlag = filterConfig.getInitParameter("typeFlag");//缓存开关标识
        
        if("1".equals(typeFlag)){//不缓存
        	//浏览器不缓存页面&数据信息
            response.setDateHeader("Expires", -1);  
            response.setHeader("Cache-Control", "no-cache");  
            response.setHeader("Pragma", "no-cache");  
        }else if("0".equals(typeFlag)){//缓存
        	//设置浏览器页面&数据过期缓存  
            String date = filterConfig.getInitParameter("date");//缓存时间
            response.setDateHeader("Expires", System.currentTimeMillis()+Long.valueOf(date)*1000);
        }
        chain.doFilter(request, response);// 执行目标方法 
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
	
}
