package com.whty.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: TcpXmlServer 
 *
 * Description:TCP XML 
 * 
 * Copyright: Copyright (c) 2015年8月18日 下午2:31:37 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class TcpXmlServer {
public static Logger logger = LoggerFactory.getLogger(TcpXmlServer.class);
public static final String PROTOCOL_TCP = Runabble.PROTOCOL_TCP;
public static final String FORMAT_XML = Runabble.FORMAT_XML;

public static void main(String[] args){
	Runabble  server = new Runabble();
	NettyService ret = new NettyService();
	//初始化配置参数
	 List<NettyService> confList = server.initServer();
		for(NettyService nettyService : confList){
			if(PROTOCOL_TCP.equals(nettyService.getProtocol()) && FORMAT_XML.equals(nettyService.getFormat())){
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
	server.run(PROTOCOL_TCP,FORMAT_XML,Integer.parseInt(ret.getPort()),ret.getUri());
}
}
