package com.whty.http;

import com.whty.utils.xml.InitParam;

public class WebAppServlet {
	private String servletName;
	private String servletClass;
	private InitParam intParam;
	public String getServletName() {
		return servletName;
	}
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}
	public String getServletClass() {
		return servletClass;
	}
	public void setServletClass(String servletClass) {
		this.servletClass = servletClass;
	}
	public InitParam getIntParam() {
		return intParam;
	}
	public void setIntParam(InitParam intParam) {
		this.intParam = intParam;
	}
}
