package com.whty.utils.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: DescAnnotation 
 *
 * Description:字段注解
 * 
 * Copyright: Copyright (c) 2015年8月11日 下午2:08:50 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
@Target(ElementType.FIELD)  
@Retention (RetentionPolicy.RUNTIME)   
@Documented
public @interface DescAnnotation {
	//描叙
	String description();
}
