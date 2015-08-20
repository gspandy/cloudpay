package com.whty.tcpxmltest;

import java.io.UnsupportedEncodingException;

import com.whty.utils.common.CommonFunction;

public class TestLength {

	public static void main(String[] args) throws UnsupportedEncodingException {

		String sendXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><trade><t1>t1标签</t1><t2><t3>t3标签</t3></t2></trade>";
		String sendXmlByteLength = CommonFunction.fillString(Integer.toHexString(sendXml.getBytes("UTF-8").length), '0', 4, false);//10进制转换为16进制
		System.out.println(sendXmlByteLength);
	}

}
