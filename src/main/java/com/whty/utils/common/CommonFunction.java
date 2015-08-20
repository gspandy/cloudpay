package com.whty.utils.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: CommonFunction 
 *
 * Description:常用工具类
 * 
 * Copyright: Copyright (c) 2015-7-23 下午2:28:32 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class CommonFunction {

	public static boolean isEmpty(final String value) {
		return value == null || value.trim().length() == 0
				|| "null".endsWith(value);
	}

	/**
	 * 填补字符串
	 * 
	 * @param str
	 * @param fill
	 * @param len
	 * @param isEnd
	 * @return
	 */
	public static String fillString(String str, char fill, int len,
			boolean isEnd) {
		int fillLen = len - str.getBytes().length;
		if (len <= 0) {
			return str;
		}
		for (int i = 0; i < fillLen; i++) {
			if (isEnd) {
				str += fill;
			} else {
				str = fill + str;
			}
		}
		return str;
	}

	/**
	 * 填补字符串(中文字符扩充)
	 * 
	 * @param str
	 * @param fill
	 * @param len
	 * @param isEnd
	 * @return
	 */
	public static String fillStringForChinese(String str, char fill, int len,
			boolean isEnd) {
		int num = 0;
		Pattern p = Pattern.compile("^[\u4e00-\u9fa5]");
		for (int i = 0; i < str.length(); i++) {
			Matcher m = p.matcher(str.substring(i, i + 1));
			if (m.find()) {
				num++;
			}
		}
		int fillLen = len - (str.length() + num);
		if (len <= 0) {
			return str;
		}
		for (int i = 0; i < fillLen; i++) {
			if (isEnd) {
				str += fill;
			} else {
				str = fill + str;
			}
		}
		return str;
	}
}
