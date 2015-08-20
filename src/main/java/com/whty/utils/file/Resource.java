package com.whty.utils.file;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.utils.common.Constants;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: Resource 
 *
 * Description:获取资源文件采用统一的方法，这是因为在正式部署情况下需要从应用目录的conf目录下加载配置文件
 * 
 * Copyright: Copyright (c) 2015年8月11日 下午2:09:05 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class Resource {
	private static Logger logger = LoggerFactory.getLogger(Resource.class);
	/**
	 * 获取资源的路径
	 * @param resourceName
	 * @return
	 */
	public static String getResourcePath(String resourceName){
		String path = Resource.class.getClassLoader().getResource(resourceName).getPath();
		if(path.indexOf(".jar")==-1){
			return path;
		}
		int index= path.indexOf("lib");
		path = path.substring(0,index)+"conf"+File.separator+resourceName;
		if(path.startsWith("file")){
			System.out.println("SERVER_OSNAME:"+Constants.SERVER_OSNAME);
			path = path.substring(5);//linux 5  WINDOWS 6			
		}
		System.out.println("path----->"+path);
		return path;
	}
	/**
	 * 
	 * @param resourceName
	 * @return
	 */
	public static String getRspToUnionPayTplResourcePath(String resourceName){
		String path = Resource.class.getClassLoader().getResource(Constants.RSP_TO_UNIONPAY_TPLPATH+resourceName).getPath();
		if(logger.isDebugEnabled()){
			System.out.println("path----->"+path);
		}
		return path;
	}
	
	/**
	 * 
	 * @param resourceName
	 * @return
	 */
	public static String getRspToPhoneAppTplResourcePath(String resourceName){
		String path = Resource.class.getClassLoader().getResource(Constants.RSP_TO_POHNEAPP_TPLPATH+resourceName).getPath();
		if(logger.isDebugEnabled()){
			System.out.println("path----->"+path);
		}
		return path;
	}
	
	public static String getRspToInnerCoreTplResourcePath(String resourceName){
		String path = Resource.class.getClassLoader().getResource(Constants.RSP_TO_INNERCORE_TPLPATH+resourceName).getPath();
		if(logger.isDebugEnabled()){
			System.out.println("path----->"+path);
		}
		return path;
	}
	
	public static void main(String[] args){
		String path = "file://D:/project/smscode/START/lib/smsp1.0.jar!/server.xml";
		int index= path.indexOf("lib");
		path = path.substring(0,index)+"conf"+File.separator+"server.xml";
		if(path.startsWith("file")){
			path = path.substring(6);
		}
		System.out.println("path----->"+path);
	}
}
