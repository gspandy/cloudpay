package com.whty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.http.NettyHttpServlet;
import com.whty.utils.xml.WebApp;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: HttpSoapServerInboundHandler 
 *
 * Description:http协议,soap格式的业务处理类
 * 
 * Copyright: Copyright (c) 2015年8月6日 下午4:03:20 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class HttpSoapServerInboundHandler extends ChannelInboundHandlerAdapter {
	public static Logger logger = LoggerFactory.getLogger(HttpSoapServerInboundHandler.class);

	private HttpRequest request;
	private HttpContent content;
	private NettyHttpServlet httpServlet;
	private String totalLength;
	private String contentType;
	private String contentTypeProtocolText;
	//private String contentTypeCharSet;
	private String contentTypeAction;
	private String contentTypeActionValue;
	private String clientUri;
	private String clientHost;
	private String clientIp;
	private String clientPort;
	private StringBuffer soapStr;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {		
		/**
		 * 因为Netty接收内容，当内容总长度超过1024后会分段接收
		 * netty的request消息格式：HttpRequest+HttpContent1+.......
		 * Content-Length 代表netty接收报文的总长度
		 */
		// 首次建立连接
		if (msg instanceof HttpRequest) {
			request = (HttpRequest) msg;
			//解析request参数						
			totalLength =  request.headers().get("Content-Length");
			contentType =  request.headers().get("Content-Type");
			contentTypeProtocolText = contentType.split(";")[0];
			//contentTypeCharSet = contentType.split(";")[1];
			contentTypeAction = contentType.split(";")[2];//根据action做业务分发
			contentTypeActionValue = contentTypeAction.split("=")[1].replaceAll("\"", "");
			clientUri = request.getUri();
			clientHost = request.headers().get("Host");
			clientIp  = clientHost.split(":")[0];
//			clientPort = clientHost.split(":")[1];			
//			if(logger.isDebugEnabled()){
//			Iterator<Entry<String, String>> iter = request.headers().iterator();
//			while(iter.hasNext()){
//				Entry<String, String> entry =  iter.next();
//				System.out.println("key["+entry.getKey()+"],"+"value["+entry.getValue()+"]");
//			}}
			logger.info("请求上送的clientUri,clientIp,clientPort[{}]", new String(clientUri+","+clientIp+","+clientPort));
			
			URI serviceBaseUrl = new URI(clientUri);
			logger.info("请求服务的地址baseurl,contentTypeProtocol,action[{}]", serviceBaseUrl+","+contentTypeProtocolText+","+contentTypeActionValue);
			httpServlet = WebApp.getNettyHttpServletByUri(contentTypeActionValue);
			if (httpServlet == null) {
				logger.info("URL[{}]没有找到匹配的业务处理类", contentTypeActionValue);//业务中转
			}			
			return;
		} else if (msg instanceof HttpContent) {//单次循环读取HttpContent
			content = (HttpContent) msg;			
		}
		
		if (httpServlet == null) {
			return;
		}
		
		//每次读取HttpContent后进行处理
		ByteBuf buf = content.content();	
		int limit = buf.capacity();//单次包接收的总长度
		if (limit != 0) {
			if (soapStr == null) {//第一次接受包的时候构建存储buffer
				byte[] byt = new byte[limit];
				buf.readBytes(byt);
				soapStr = new StringBuffer(new String(byt, 0, byt.length));
			} else {
				byte[] byt = new byte[limit];
				buf.readBytes(byt);
				soapStr.append(new String(byt));// 拼接后面接收到的内容
			}
			// 当接收到的内容长度等于发送的最大长度后就开始执行具体的业务逻辑
			if (soapStr != null && soapStr.toString().getBytes().length == Integer.parseInt(totalLength)) {
				logger.info("请求报文全部[{}]", new String(soapStr));
				//soap报文处理+具体业务逻辑处理+netty的FullHttpResponse组装
//				if(!realPort.equals(clientPort)){
//					FullHttpResponse response = httpServlet.serviceErr(request, content, soapStr.toString(),"99",clientPort+" is error![OK:"+realPort+"]","soap");
//					ctx.write(response);
//		        	ctx.flush();
//				}else{
					FullHttpResponse response = httpServlet.serviceHttp(request, content, soapStr.toString(),contentTypeProtocolText);
					ctx.write(response);
		        	ctx.flush();
//	        	}
			}
		} else {
//			if(!realPort.equals(clientPort)){
//				FullHttpResponse response = httpServlet.serviceErr(request, content, ((soapStr == null) ? null : soapStr.toString()),"99",clientPort+" is error![OK:"+realPort+"]","soap");
//				ctx.write(response);
//				ctx.flush();
//			}else{
				FullHttpResponse response = httpServlet.serviceHttp(request, content, ((soapStr == null) ? null : soapStr.toString()),contentTypeProtocolText);
				ctx.write(response);
				ctx.flush();
//			}
		}
	}
	
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	logger.error(cause.getMessage());
        ctx.close();
    }
}
