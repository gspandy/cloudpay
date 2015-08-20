package com.whty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

import java.util.List;

import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.utils.file.Resource;
import com.whty.utils.xml.Dom4jTool;
import com.whty.utils.xml.WebApp;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: Runabble 
 *
 * Description:
 * 
 * Copyright: Copyright (c) 2015年8月14日 上午10:50:30 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class Runabble {
public static Logger logger = LoggerFactory.getLogger(Runabble.class);
public static int PORT = 80;
public static String SERVICE_URI = "";

public static final String PROTOCOL_HTTP = "HTTP/1.1";
public static final String PROTOCOL_HTTPS = "HTTPS";
public static final String PROTOCOL_TCP = "TCP";

public static final String FORMAT_SOAP = "SOAP";
public static final String FORMAT_JSON = "JSON";
public static final String FORMAT_XML = "XML";
public static final String FORMAT_FIX = "FIXED";

/**用于分配处理业务线程的线程组个数 */	
private static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors()*2;	//默认	
/** 业务出现线程大小*/	
private static final int BIZTHREADSIZE = 1000;
/**
 * 初始化协议端口
 */
public  List<NettyService> initServer(){
	Dom4jTool<NettyService>   tool = new Dom4jTool<NettyService>();
	NettyService  nettyServer = new NettyService();
	List<NettyService> list = tool.readXML(Resource.getResourcePath("server.xml"), nettyServer);
	return list;
}  
/**
 * 
 * @param protocol
 * @param format
 * @param port
 * @param service_uri
 */
public void run(final String protocol,final String format, int port,final String service_uri){

	logger.info("[{}]通信服务正在启动.......", protocol+","+format+","+String.valueOf(port)+","+service_uri);
	
	WebApp.init();//初始化业务配置	


	//配置NIO线程组	
	EventLoopGroup bossGroup = new NioEventLoopGroup();	
	final EventLoopGroup workerGroup = new NioEventLoopGroup();
	try {	
				//服务器辅助启动类配置
				ServerBootstrap server = new ServerBootstrap();
				server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(
						new ChannelInitializer<SocketChannel>() {
							@Override
							protected void initChannel(SocketChannel ch) throws Exception {
								
								if(PROTOCOL_HTTP.equals(protocol) && FORMAT_SOAP.equals(format)){
									//server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
									ch.pipeline().addLast(new HttpResponseEncoder());
									// server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
									ch.pipeline().addLast(new HttpRequestDecoder());
									//业务handler
									ch.pipeline().addLast(new HttpSoapServerInboundHandler());
	
								}else if(PROTOCOL_HTTP.equals(protocol) && FORMAT_JSON.equals(format)){
									// server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
									ch.pipeline().addLast("http-decoder1",new HttpRequestDecoder());
									//将同一个http请求或响应的多个消息对象变成一个 fullHttpRequest完整的消息对象
									ch.pipeline().addLast("http-aggregator1",new HttpObjectAggregator(65536));
									//server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
									ch.pipeline().addLast("http-encoder1",new HttpResponseEncoder());
									//处理大数据流,比如一个1G大小的文件
									ch.pipeline().addLast("http-chunked1",new ChunkedWriteHandler());
									//业务handler
									ch.pipeline().addLast("jsonserverhandler",new HttpJsonServerInboundHandler());
									
								}else if(PROTOCOL_HTTPS.equals(protocol) && FORMAT_SOAP.equals(format)){
								try{	
									 boolean SSL = System.getProperty("ssl") != null; 
									 SslContext sslCtx = null;  
									if (SSL) {             
									  SelfSignedCertificate ssc = new SelfSignedCertificate();
									  sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());         
									} else {             
										  sslCtx = null;         
									} 
									
									//server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
									ch.pipeline().addLast(new HttpResponseEncoder());
									// server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
									ch.pipeline().addLast(new HttpRequestDecoder());
									//业务handler
									ch.pipeline().addLast(new HttpSoapServerInboundHandler());
									
								} catch (SSLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}   
									
								}else if(PROTOCOL_HTTPS.equals(protocol) && FORMAT_JSON.equals(format)){
									
									
								}else if(PROTOCOL_TCP.equals(protocol) && FORMAT_XML.equals(format)){
//									ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));   
//									ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4));   
									ch.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));   
									ch.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));   
									ch.pipeline().addLast(new TcpXmlServerInboundHandler());
									
									
									
								}else if(PROTOCOL_TCP.equals(protocol) && FORMAT_FIX.equals(format)){
									
									
								}
							}
				}).option(ChannelOption.SO_BACKLOG, 1024).childOption(ChannelOption.SO_KEEPALIVE, true);
				
				//绑定端口 同步等待绑定成功
				ChannelFuture f = server.bind(port).sync();
				logger.info("[{}]通信服务启动成功并监听端口.......", protocol+","+format+","+port+","+service_uri);
				//等待服务器端监听端口关闭
				f.channel().closeFuture().sync();
//				f.addListener(new ChannelFutureListener() {								
//					public void operationComplete(ChannelFuture future) throws Exception {					
//						if(future.isSuccess()){						
//							System.out.println("client connected");					
//						}else{						
//								System.out.println("server attemp failed");						
//								future.cause().printStackTrace();					
//						}									
//						}			
//					});			
//				f.channel().closeFuture().sync();		
				
			} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}finally{
			//释放线程资源
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}	
}
}
