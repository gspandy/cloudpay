package com.whty.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: HttpJsonServer 
 *
 * Description:http  json传输
 * 
 * Copyright: Copyright (c) 2015年8月12日 上午9:52:58 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class HttpJsonServer {
public static Logger logger = LoggerFactory.getLogger(HttpJsonServer.class);
public static final String PROTOCOL_HTTP = Runabble.PROTOCOL_HTTP;
public static final String FORMAT_JSON = Runabble.FORMAT_JSON;

public static void main(String[] args){
	Runabble  server = new Runabble();
	NettyService ret = new NettyService();
	//初始化配置参数
	 List<NettyService> confList = server.initServer();
		for(NettyService nettyService : confList){
			if(PROTOCOL_HTTP.equals(nettyService.getProtocol()) && FORMAT_JSON.equals(nettyService.getFormat())){
			ret.setChanlno(nettyService.getChanlno());
			ret.setConnectionTimeout(nettyService.getConnectionTimeout());
			ret.setFormat(nettyService.getFormat());
			ret.setPort(nettyService.getPort());
			ret.setProtocol(nettyService.getProtocol());
			ret.setUri(nettyService.getUri());
			}
		}
		logger.info("********************[CHANLNO:"+ret.getChanlno()+","+"PORT:"+ret.getPort()+","+"SERVICE_URI:"+ret.getUri()+"]初始化完成！************************");
	//启动服务
	server.run(PROTOCOL_HTTP,FORMAT_JSON,Integer.parseInt(ret.getPort()),ret.getUri());
}
}
