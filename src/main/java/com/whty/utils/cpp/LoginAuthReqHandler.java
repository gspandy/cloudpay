package com.whty.utils.cpp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(buildLoginReq());
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		CppMessage message = (CppMessage)msg;
		if(message.getHeader() != null && message.getHeader().getType() == (byte)2){
			System.out.println("Received from server response");
		}
		ctx.fireChannelRead(msg);
	}

	private CppMessage buildLoginReq() {
		CppMessage message = new CppMessage();
		Header header = new Header();
		header.setType((byte)1);
		message.setHeader(header);
		message.setBody("It is request");
		return message;
	}
	
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	}
}