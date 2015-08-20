package com.whty.http;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.whty.utils.xml.InitParam;

public abstract class NettyHttpServlet {
	
	/**
	 * 初始化参数  
	 * @param param  
	 */
	public void init(InitParam param){}
	
	/**
	 * 
	 * 业务处理方法
	 * 
	 * @param servletrequest
	 * @param httpContent
	 * @param str
	 * @param protocolText
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public FullHttpResponse serviceHttp(HttpRequest servletrequest, HttpContent httpContent,String str,String protocolText) throws UnsupportedEncodingException{
		//封装成NettyHttpRequest
		NettyHttpRequest nettyRequest = new NettyHttpRequest();
		nettyRequest.setProtocolVersion(servletrequest.getProtocolVersion().text());
		nettyRequest.setProtocolText(protocolText);
		nettyRequest.setUri(servletrequest.getUri());
		nettyRequest.setHttpHeaders(servletrequest.headers());
        QueryStringDecoder decoderQuery = new QueryStringDecoder(servletrequest.getUri());
        Map<String, List<String>> uriAttributes = decoderQuery.parameters();
        nettyRequest.setParameters(uriAttributes);
        
        nettyRequest.setMethod(servletrequest.getMethod());
        nettyRequest.setContent(str);//上送的soap,json报文
        
        NettyHttpResponse nettyResponse = new NettyHttpResponse();   
        nettyResponse.setHttpVersion(HTTP_1_1);
        nettyResponse.setContentType(CONTENT_TYPE);
        nettyResponse.setContentLength(CONTENT_LENGTH);
        if (HttpHeaders.isKeepAlive(servletrequest)) {
        	nettyResponse.setConnection(CONNECTION);
        }
       // servletrequest.headers().get("CONTENT_TYPE").;
        //调用具体的业务处理
        processHttp(nettyRequest, nettyResponse); 

       FullHttpResponse response = new DefaultFullHttpResponse(nettyResponse.getHttpVersion(),
                 OK, Unpooled.wrappedBuffer(nettyResponse.getContent().getBytes("UTF-8")));
         
        Map<String,String> header = nettyResponse.getHeader();
        Set<String> keys = header.keySet();
        for(String key : keys){
        	String value = header.get(key);
        	response.headers().set(key, value);
        }
        response.headers().set(nettyResponse.getContentType(), protocolText);
        response.headers().set(nettyResponse.getContentLength(),response.content().readableBytes());
        if (HttpHeaders.isKeepAlive(servletrequest)) {
            response.headers().set(nettyResponse.getConnection(), Values.KEEP_ALIVE);
        }
        return response;
	}
	
	public String serviceTcp(String txnCode,int length,String xmlStr) throws UnsupportedEncodingException{
        //调用具体的业务处理
        return processTcp(txnCode, length,xmlStr);
	}
	/** 
	 * 具体业务处理
	 * @param nettyRequest
	 * @param nettyHttpResponse  
	 */
	public abstract void processHttp(NettyHttpRequest nettyRequest, NettyHttpResponse nettyHttpResponse);
	
	public abstract String processTcp(String txnCode,int length,String xmlStr);

}
