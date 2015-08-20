package com.whty.innersocket.accept;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: DemoPublic 
 *
 * Description:demo
 * 
 * Copyright: Copyright (c) 2015-7-29 下午4:03:39 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
@XmlType(propOrder = { "sCode", "termCode" })
public class A_DemoPublic {

	public A_DemoPublic() {
		// TODO Auto-generated constructor stub
	}

	private java.lang.String sCode; // 服务代码
	private java.lang.String termCode; // 终端编号	
	
	@XmlElement(name = "SCode")
	public java.lang.String getsCode() {
		return sCode;
	}
	@XmlElement(name = "TermCode")
	public java.lang.String getTermCode() {
		return termCode;
	}
	
	public void setsCode(java.lang.String sCode) {
		this.sCode = sCode;
	}
	public void setTermCode(java.lang.String termCode) {
		this.termCode = termCode;
	}	
	
}
