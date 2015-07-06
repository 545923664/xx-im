package com.jzwl.common.aspectj.token;

import java.lang.annotation.*;

/**
 * 防止重复提交的token切点类
 * @author zhang guo yu
 * @version 1.0.0
 * @since 2015-02-01
 * */
@Target({ElementType.TYPE, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
public @interface Token {
	/**
	 * 生成token
	 * */
    boolean save() default false;
    
    /**
     * 验证并销毁token
     * */
    boolean remove() default false;
}
