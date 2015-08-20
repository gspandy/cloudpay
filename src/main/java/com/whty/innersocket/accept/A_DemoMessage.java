package com.whty.innersocket.accept;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: DemoMessage 
 *
 * Description:demo
 * 
 * Copyright: Copyright (c) 2015-7-29 下午4:01:30 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
@XmlRootElement(name = "Message")
@XmlType(propOrder = {"pubic"})
public class A_DemoMessage {

	public A_DemoMessage() {
		// TODO Auto-generated constructor stub
	}

	private A_DemoPublic pubic;

	@XmlElement(name = "Public")
	public A_DemoPublic getPubic() {
		return pubic;
	}

	public void setPubic(A_DemoPublic pubic) {
		this.pubic = pubic;
	}
	
	
}
