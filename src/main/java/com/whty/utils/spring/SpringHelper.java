package com.whty.utils.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.whty.utils.common.AssertUtils;

/**    
 * @Title: Spring辅助类 (参考springside)
 * @Description: 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出ApplicaitonContext. 
 * @author zhangyudong
 * @company 武汉天喻信息产业股份有限公司
 * @date 2015年5月14日 上午11:53:03  
 * @version V1.0    
 */
public class SpringHelper implements ApplicationContextAware, DisposableBean{

	private static Logger logger = LoggerFactory.getLogger(SpringHelper.class);

	private static ApplicationContext applicationContext = null;
	
	static {
		if(applicationContext == null){		
			applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		}
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 清除SpringContextHolder中的ApplicationContext为Null.
	 */
	public static void clearHolder() {
		logger.debug("清除SpringHelper中的ApplicationContext:" + applicationContext);
		applicationContext = null;
	}

	/**
	 * 实现ApplicationContextAware接口, 注入Context到静态变量中.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		logger.debug("注入ApplicationContext到SpringHelper:" + applicationContext);

		if (SpringHelper.applicationContext != null) {
			logger.warn("SpringHelper中的ApplicationContext被覆盖, 原有ApplicationContext为:"
					+ SpringHelper.applicationContext);
		}

		SpringHelper.applicationContext = applicationContext; //NOSONAR
	}

	/**
	 * 实现DisposableBean接口, 在Context关闭时清理静态变量.
	 */
	@Override
	public void destroy() throws Exception {
		SpringHelper.clearHolder();
	}

	/**
	 * 检查ApplicationContext不为空.
	 */
	private static void assertContextInjected() {
		AssertUtils.state(applicationContext != null,
				"applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringHelper.");
	}
}
