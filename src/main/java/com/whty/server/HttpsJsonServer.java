package com.whty.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: HttpsJsonServer 
 *
 * Description:HTTPS JSON
 * 
 * Copyright: Copyright (c) 2015年8月17日 下午11:06:58 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class HttpsJsonServer {
public static Logger logger = LoggerFactory.getLogger(HttpsJsonServer.class);
public static final String PROTOCOL_HTTPS = Runabble.PROTOCOL_HTTPS;
public static final String FORMAT_JSON = Runabble.FORMAT_JSON;

public static void main(String[] args){
	Runabble  server = new Runabble();
	NettyService ret = new NettyService();
	//初始化配置参数
	 List<NettyService> confList = server.initServer();
		for(NettyService nettyService : confList){
			if(PROTOCOL_HTTPS.equals(nettyService.getProtocol()) && FORMAT_JSON.equals(nettyService.getFormat())){
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
	server.run(PROTOCOL_HTTPS,FORMAT_JSON,Integer.parseInt(ret.getPort()),ret.getUri());
}
}
