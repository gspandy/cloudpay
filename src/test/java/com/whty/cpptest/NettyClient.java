package com.whty.cpptest;

import com.whty.utils.cpp.CppMessageDecoder;
import com.whty.utils.cpp.CppMessageEncoder;
import com.whty.utils.cpp.LoginAuthReqHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class NettyClient {
	public void connect(String remoteServer, int port) throws Exception {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup)
					.channel(NioSocketChannel.class)
					.handler(new ChildChannelHandler());
			
			ChannelFuture f = b.connect(remoteServer,port).sync();
			System.out.println("Netty time Client connected at port " + port);
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	public static class ChildChannelHandler extends
			ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// -8表示lengthAdjustment，让解码器从0开始截取字节，并且包含消息头
			ch.pipeline().addLast(new CppMessageDecoder(1024 * 1024, 0, 4, 0, 0));
			ch.pipeline().addLast(new LoginAuthReqHandler());
			ch.pipeline().addLast(new CppMessageEncoder());
			
		}

	}
	
	public static void main(String[] args){
		try {
			new NettyClient().connect("127.0.0.1", 9080);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}