package com.whty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.http.NettyHttpServlet;
import com.whty.utils.xml.WebApp;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: HttpServerInboundHandler 
 *
 * Description:http协议,json格式
 * 
 * Copyright: Copyright (c) 2015年8月6日 下午4:03:20 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class HttpJsonServerInboundHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	public static Logger logger = LoggerFactory.getLogger(HttpJsonServerInboundHandler.class);
	
	private HttpContent content;
	private NettyHttpServlet httpServlet;
	private String contentType;
	private String clientUri;
	private String jsonStr;
	private String contentTypeProtocolText;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
			FullHttpRequest request) throws Exception {
		
		contentType =  request.headers().get("Content-Type");
		contentTypeProtocolText = contentType.split(";")[0];
		clientUri = request.retain().getUri();
		logger.info("请求上送的clientUri,contentType,contentTypeProtocolText[{}]", new String(clientUri+","+contentType+","+contentTypeProtocolText));

		ByteBuf buf = request.content();
		int limit = buf.capacity();
		byte[] byt = new byte[limit];
		buf.readBytes(byt);		
		jsonStr = new String(byt);
		
		httpServlet = WebApp.getNettyHttpServletByUri(clientUri);
		if (httpServlet == null) {
			logger.info("URL[{}]没有找到匹配的业务处理类", clientUri);//业务中转
			return;
		}	
		
		FullHttpResponse response = httpServlet.serviceHttp(request, content, jsonStr,contentTypeProtocolText);
		ctx.write(response);
    	ctx.flush();		
	}
	
}
