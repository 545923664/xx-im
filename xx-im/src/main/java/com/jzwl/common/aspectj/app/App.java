package com.jzwl.common.aspectj.app;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验APP的http + json接入请求的合法性切点类
 * @author zhang guo yu
 * @version 1.0.0
 * @since 2015-02-01
 * */
@Target({ElementType.TYPE, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
public @interface App {
}
