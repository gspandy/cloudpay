package com.whty.utils.xml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whty.http.NettyHttpServlet;
import com.whty.http.WebAppServlet;
import com.whty.http.WebAppServletMapping;
import com.whty.utils.file.Resource;
/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: WebApp 
 *
 * Description:
 * 
 * Copyright: Copyright (c) 2015年8月10日 下午1:37:18 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class WebApp {
	public static List<WebAppServlet> webAppServletList;
	public static List<WebAppServletMapping> webAppServletMappingList;
	
	//所有的httpServlet类集合
	public static Map<String,NettyHttpServlet> mapHttpServlet = new HashMap<String,NettyHttpServlet>();
	
	static{
		init();
	}
	
	//初始化读取web.xml文件
	public static void init(){
		String webXmlPath = Resource.getResourcePath("web.xml");
		
		Dom4jTool<WebAppServletMapping> webAppServletMappingTool = new Dom4jTool<WebAppServletMapping>();
		webAppServletMappingList = webAppServletMappingTool.readXML(webXmlPath, new WebAppServletMapping());
		
		Dom4jTool<WebAppServlet> webAppServletTool = new Dom4jTool<WebAppServlet>();
		webAppServletList = webAppServletTool.readXML(webXmlPath, new WebAppServlet());
		for(WebAppServlet webAppServlet:webAppServletList){
			try {
				NettyHttpServlet httpServlet = (NettyHttpServlet) Class.forName(webAppServlet.getServletClass()).newInstance();
				if(webAppServlet.getIntParam()!=null){
					httpServlet.init(webAppServlet.getIntParam());
				}
				mapHttpServlet.put(webAppServlet.getServletName(), httpServlet);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据servletName获取Servlet对象
	 * @param servletName
	 * @return
	 */
	public static NettyHttpServlet getNettyHttpServlet(String servletName){
		return mapHttpServlet.get(servletName);
	}
	
	/**
	 * 根据URi获取servlet对象
	 * @param uri
	 * @return
	 */
	public static NettyHttpServlet getNettyHttpServletByUri(String uri){
		 WebAppServletMapping webAppServletMapping = getWebAppServletMapping(uri);
		 if(webAppServletMapping!=null){
			 return getNettyHttpServlet(webAppServletMapping.getServletName());
		 }
		 return null;
	}
	
	/**
	 * 通过servletName查找Servlet
	 * @param servletName
	 * @return
	 */
	public static WebAppServlet getWebAppServlet(String servletName){
		for(WebAppServlet app:webAppServletList){
			if(app.getServletName().equals(servletName)){
				return app;
			}
		}
		System.out.println("-------------没有找到"+servletName+"对应的类文件-------------");
		return null;
	}
	
	/**
	 * 通过URL查找匹配的WebAppServlet
	 * @param url
	 * @return
	 */
	public static WebAppServletMapping getWebAppServletMapping(String url){
		for(WebAppServletMapping app:webAppServletMappingList){
			if(app.getUrlPattern().equals(url)){
				return app;
			}
		}
		System.out.println("-------------没有找到匹配的"+url+"-------------");
		return null;
	}
}
