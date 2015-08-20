package com.whty.utils.common;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: MapObject 
 *
 * Description:map对象，用于向页面提供json数据，不用把对象中所有的属性都设置到json中去
 * 
 * Copyright: Copyright (c) 2015年8月11日 下午2:08:09 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class MapObject {
	private String key;
	private String value;
	public MapObject(){}
	public MapObject(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
