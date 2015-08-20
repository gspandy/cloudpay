package com.whty.utils.cpp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {


	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		CppMessage message = (CppMessage)msg;
		if(message.getHeader() != null && message.getHeader().getType() == (byte)1){
			System.out.println("Login is OK");
			String body = (String)message.getBody();
			System.out.println("Recevied message body from client is " + body);
		}
		ctx.writeAndFlush(buildLoginResponse((byte)3));
	}

	private CppMessage buildLoginResponse(byte result) {
		CppMessage message = new CppMessage();
		Header header = new Header();
		header.setType((byte)2);
		message.setHeader(header);
		message.setBody(result);
		return message;
	}
	
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	}
}