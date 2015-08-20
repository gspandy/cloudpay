package com.whty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.http.NettyHttpServlet;
import com.whty.utils.xml.WebApp;

public class TcpXmlServerInboundHandler extends ChannelInboundHandlerAdapter {
	public static Logger logger = LoggerFactory.getLogger(TcpXmlServerInboundHandler.class);
	private String xmlStr;
	private NettyHttpServlet tcpServlet;


	/**
	 * msg格式:交易码(固定8位)+报文长度(固定4位,16进制的字符)+xml报文
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg instanceof String) {
			xmlStr = (String)msg;
			logger.info("tcp接收到的报文为:[{}]", xmlStr);
			//消息格式解析
			if(xmlStr != null && xmlStr.length() >= 16){
				String nettyLength = xmlStr.substring(0,4);
				String txnCode = xmlStr.substring(4,12);
				String length =  xmlStr.substring(12, 16);
				String content =  xmlStr.substring(16);
				tcpServlet = WebApp.getNettyHttpServletByUri(txnCode);
				if (tcpServlet == null) {
					logger.info("根据交易码[{}]没有找到匹配的业务处理类", txnCode);//业务中转
					return;
				}
				
				String response = tcpServlet.serviceTcp(txnCode, Integer.parseInt(length, 16), content);
				//应答消息
				//ByteBuf resp = Unpooled.copiedBuffer(response.getBytes());
				ctx.write(response);
	        	ctx.flush();
				
			}
				
			
		}else if(msg instanceof ByteBuf ) {
			ByteBuf buff = (ByteBuf) msg;
			byte[] bytes = new byte[buff.readableBytes()];
			System.out.println("readableBytes="+buff.readableBytes());
			buff.readBytes(bytes);
			String xmlStr = new String(bytes);				 
			logger.info("tcp接收到的报文为:[{}]", xmlStr);
			//消息格式解析
			if(xmlStr != null && xmlStr.length() >= 16){
				String nettyLength = xmlStr.substring(0,4);
				String txnCode = xmlStr.substring(4,12);
				String length =  xmlStr.substring(12, 16);
				String content =  xmlStr.substring(16);
				tcpServlet = WebApp.getNettyHttpServletByUri(txnCode);
				if (tcpServlet == null) {
					logger.info("根据交易码[{}]没有找到匹配的业务处理类", txnCode);//业务中转
					return;
				}
				
				String response = tcpServlet.serviceTcp(txnCode, Integer.parseInt(length, 16), content);
				//应答消息
				//ByteBuf resp = Unpooled.copiedBuffer(response.getBytes());
				ctx.write(response);
	        	ctx.flush();
				
			}else{
				logger.error("报文长度有问题!");
			}
		} else {
			logger.error("error object ,must transfer a String");
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	        ctx.flush();
	    }
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		logger.warn("Unexpected exception from downstream.", cause);
		ctx.close();
	}
}
