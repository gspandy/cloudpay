package com.whty.server;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: NettyService 
 *
 * Description:服务的配置
 * 
 * Copyright: Copyright (c) 2015年8月11日 下午2:04:13 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class NettyService {
	public String connectionTimeout;
	public String port;
	public String protocol;
	public String format;
	public String chanlno;
	public String uri;
	public String getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(String connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getChanlno() {
		return chanlno;
	}
	public void setChanlno(String chanlno) {
		this.chanlno = chanlno;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
