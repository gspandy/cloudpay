package com.whty.utils.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.utils.file.Resource;

/** 
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: Constants 
 *
 * Description:
 * 
 * Copyright: Copyright (c) 2015年8月10日 上午11:53:22 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class Constants {
	public static Logger logger = LoggerFactory.getLogger(Constants.class);
	
	public static final String XMLBAOWENHEADER = "<?xml version=\"1.0\" encoding=\"GB18030\"?>";// XML头

	
	private static final String CONFIG_FILE = "config.properties";//根目录下配置文件名称
		
	private static Properties prop = new Properties();
	
	static{
		logger.info("Constants初始化开始");
		loadConfig();
		logger.info("Constants初始化结束");
	}
	
	public static String SOCKET_ENCODING; 
	
	public static String SOCKET_IP; 
	public static String SOCKET_PORT; 
	public static String SOCKET_TIMEOUT; 
	public static String SERVER_OSNAME; 
    public static  int PAGE_SIZE=10;
	
	/**  
	 * 系统运行模式： 0-开发测试模式 1-生产模式  
	 */  	
	public static String SYSTEM_RUN_MODEL; 
	//应答文件模板
	public static String RSP_TO_UNIONPAY_TPLPATH; 
	public static String RSP_TO_POHNEAPP_TPLPATH;
	public static String RSP_TO_INNERCORE_TPLPATH;
	
	/**  
	 *  从指定文件中加载配置信息 
	 */
	public static void loadConfig(){
		logger.info("配置文件加载开始");
		try {
			String path = Resource.getResourcePath(CONFIG_FILE);
			prop.load(new FileInputStream(path));
			
			//赋值
			SOCKET_IP = prop.getProperty("socket_ip");
			SOCKET_PORT= prop.getProperty("socket_port");
			SOCKET_TIMEOUT= prop.getProperty("socket_timeout");
			SOCKET_ENCODING= prop.getProperty("socket_encoding");
			SERVER_OSNAME = prop.getProperty("server_osname");
			SYSTEM_RUN_MODEL = prop.getProperty("system.run.model");
			PAGE_SIZE = Integer.valueOf(prop.getProperty("page.size"));
			RSP_TO_UNIONPAY_TPLPATH = prop.getProperty("rsp.to.unionpay.tplpath");
			RSP_TO_POHNEAPP_TPLPATH = prop.getProperty("rsp.to.phoneapp.tplpath");
			RSP_TO_INNERCORE_TPLPATH = prop.getProperty("rsp.to.innercore.tplpath");
			logger.info("共计加载配置{}项", prop.size());
		} catch (FileNotFoundException e) {
			logger.error("config.properties配置文件未找到");
		} catch (IOException e) {
			logger.error("config.properties配置文件读取失败");
		}
		logger.info("配置文件加载完成");
	}

	public static final int TXN_SEQNO_LEN= 10;// 流水号seq的长度
}
