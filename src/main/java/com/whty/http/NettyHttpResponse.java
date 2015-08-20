package com.whty.http;

import io.netty.handler.codec.http.HttpVersion;

import java.util.HashMap;
import java.util.Map;

public class NettyHttpResponse {
	private HttpVersion httpVersion;
	private String content;
	private String contentType;
	private String contentLength;
	private String connection;
	
	private Map<String,String> header = new HashMap<String,String>();
	
	public HttpVersion getHttpVersion() {
		return httpVersion;
	}
	public void setHttpVersion(HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContentLength() {
		return contentLength;
	}
	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}
	
	public void addHeader(String key,String value){
		this.header.put(key, value);
	}
	public Map<String, String> getHeader() {
		return header;
	}
}
