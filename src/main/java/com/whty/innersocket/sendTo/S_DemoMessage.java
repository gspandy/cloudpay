package com.whty.innersocket.sendTo;

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
public class S_DemoMessage {

	public S_DemoMessage() {
		// TODO Auto-generated constructor stub
	}

	private S_DemoPublic pubic;

	@XmlElement(name = "Public")
	public S_DemoPublic getPubic() {
		return pubic;
	}

	public void setPubic(S_DemoPublic pubic) {
		this.pubic = pubic;
	}
	
	
}
