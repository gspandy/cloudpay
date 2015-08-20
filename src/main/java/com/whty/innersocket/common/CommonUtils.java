package com.whty.innersocket.common;

public class CommonUtils {
	
	
	public static final String VERSION = "1.0.0";
	public static final String APPTYPE = "M";
	public static final String SECUCHECKTYPE_NOCHECK = "0";//
	public static final String TRANSCODE_ORGALLOT = "9001";
	public static final String TRANSCODE_ORGBALQUERY = "9002";
	public static final String BAOWENENCODING = "GBK";   
	public static final String SUCCESS_CODE = "00";   
	public static final String RECVERINSTID = "999999999";
	public static final String MISC = "";
	public static final String CURRCODE="156";
	public static final String CASHEXF_0="0";
	public static final String FILEGETSTA = "03";
	public static final String FILEDEALSTA = "00";
	
	
	
	  /**
		 * 字符串填充
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
		 * 替换报文头中的交易返回码和交易描述的共通封装方法
		 */
		public static String replaceHeadTag(String fromOrg, String startTag,String endTag,String newReplaceValue){
			
			String oldStr = fromOrg.substring(fromOrg.indexOf(startTag), fromOrg.indexOf(endTag)+endTag.length());
			String newStr = startTag+newReplaceValue+endTag;					
			fromOrg = fromOrg.replace(oldStr, newStr);
			return fromOrg;
			
		}
}
