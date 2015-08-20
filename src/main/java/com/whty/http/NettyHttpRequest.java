package com.whty.http;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;

import java.util.List;
import java.util.Map;

/**
 * 封装的Netty HttpRequest
 *
 */
public class NettyHttpRequest {
	private HttpPostRequestDecoder decoder;
	private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);
	private boolean readingChunks;
	private String protocolVersion;
	private String protocolText;
	private String uri;
	private HttpHeaders httpHeaders;
	private HttpMethod method;
	private Map<String, List<String>> parameters;
	
	private String content;
	public HttpPostRequestDecoder getDecoder() {
		return decoder;
	}
	public void setDecoder(HttpRequest servletrequest) {
		this.decoder = new HttpPostRequestDecoder(factory, servletrequest);
	}
	public boolean isReadingChunks() {
		return readingChunks;
	}
	public void setReadingChunks(boolean readingChunks) {
		this.readingChunks = readingChunks;
	}
	public String getProtocolVersion() {
		return protocolVersion;
	}
	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public HttpHeaders getHttpHeaders() {
		return httpHeaders;
	}
	public void setHttpHeaders(HttpHeaders httpHeaders) {
		this.httpHeaders = httpHeaders;
	}
	public HttpMethod getMethod() {
		return method;
	}
	public void setMethod(HttpMethod method) {
		this.method = method;
	}
	public void setParameters(Map<String, List<String>> parameters) {
		this.parameters = parameters;
	}
	
	public Object getParameter(Object key){
		if(parameters!=null){
			return parameters.get(key);
		}
		return null;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getHeader(String key){
		if(this.httpHeaders==null){
			return null;
		}
		return this.httpHeaders.get(key);
	}
	public String getProtocolText() {
		return protocolText;
	}
	public void setProtocolText(String protocolText) {
		this.protocolText = protocolText;
	}
	
	
}
