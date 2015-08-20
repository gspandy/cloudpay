package com.whty.utils.common;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.FastDateFormat;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: DateUtils 
 *
 * Description:日期处理
 * 
 * Copyright: Copyright (c) 2015-7-29 上午9:44:57 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class DateUtils {
	/** 默认日期格式 */
	public static final String DATESHOWFORMAT = "yyyy-MM-dd";
	public static final String DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIMENOSPLITFORMAT = "yyyyMMddHHmmss";
	public static final String DATESHORTFORMAT = "yyyyMMdd";
	public static final String DATEMONTHFORMAT = "yyyyMM";
	public static final String TIMESHORTFORMAT = "HHmmss";
		

	
	/**
	 * 得到指定日期指定格式的字符串
	 * @param format
	 * @return
	 */
	public static String foramtDay(Date date,String format){
		if(StringUtils.isEmpty(format)){
			format= DATESHORTFORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 得到昨天的日期
	 * @return YYYYMMDD
	 */
	public static String getYesterdayDate(String format){
		if(StringUtils.isEmpty(format)){
			format= DATESHORTFORMAT;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		return new SimpleDateFormat(format).format(cal.getTime());
	}
	
	
	
	public static java.sql.Date convertSQLDate(String date) {
		try {
			if(date == null){
				return null;
			}
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			//return new java.sql.Date(sdf.parse(date).getTime());
			FastDateFormat formatDate = FastDateFormat.getInstance(DATESHORTFORMAT);
			return new java.sql.Date(((java.sql.Date)formatDate.parseObject(date)).getTime());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 计算两个日期的间隔天数
	 * 
	 * @param startDate
	 *            开始时间，如：2008-12-03 11:00:00
	 * @param endDate
	 *            结束时间，如：2009-12-31 11:00:00
	 * @return long 间隔天数(long) 
	 */
	public static long getBetweenDays(Date startDate, Date endDate) {
		if (endDate == null || startDate == null){
			return -1;
		}
		Long days = endDate.getTime() - startDate.getTime();
		days = days/(1000*60*60*24);
		return days;
	}
	
	/**
	 * 验证输入的文本信息日期是否合
	 * 
	 * @return
	 */
	public static Date isDate(String dateStr) {
		String date_format_1 = "yyyy/MM/dd";
		String date_format_2 = "yyyy-MM-dd";
		String date_format_3 = "yyyyMMdd";
		String date_format_4 = "yyyy.MM.dd";
		String[] date_format = { date_format_1, date_format_2, date_format_3, date_format_4 };
		for (int i = 0; i < date_format.length; i++) {
			Date tempDate = isDate(dateStr,date_format[i]);
			if(null != tempDate){
				return tempDate;
			}
		}
		return null;
	}
	/**
	 * 验证输入的文本信息日期是否合
	 * 
	 * @return
	 */
	public static Date isDate(String dateStr,String patternString) {
		if(StringUtils.isEmpty(patternString)){
			patternString= DATESHORTFORMAT;
		}
		try {
			//SimpleDateFormat formatDate = new SimpleDateFormat(patternString);
			//formatDate.setLenient(false);
			FastDateFormat formatDate = FastDateFormat.getInstance(patternString);
			ParsePosition pos = new java.text.ParsePosition(0);
			Date tempDate = (Date) formatDate.parseObject(dateStr, pos);
			tempDate.getTime();
			return tempDate;
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * date 转String
	 * @param pattern
	 * @return
	 */
	public static String pareDate(Date date,String pattern){
		if(null == date){
			return null;
		}
		if(StringUtils.isEmpty(pattern)){
			pattern= DATESHORTFORMAT;
		}
		return DateFormatUtils.format(date, pattern);
	}

    /**
     * @param srcDate
     * @return
     */
    public static String formatDateStr(String srcDate) {
        if(StringUtils.isEmpty(srcDate)) {
            return "";
        }
        if(srcDate.length() >= 14) {
            return srcDate.substring(0, 4) + "-" + srcDate.substring(4, 6) + "-" + srcDate.substring(6, 8) + " "
                    + srcDate.substring(8, 10) + ":" + srcDate.substring(10, 12) + ":" + srcDate.substring(12, 14);
        } else if(srcDate.length() >= 8) {
            return srcDate.substring(0, 4) + "-" + srcDate.substring(4, 6) + "-" + srcDate.substring(6, 8);
        }
        return srcDate;
    }
    
    /**
     * 获取交易日期字符串 8位 yyyyMMdd
     * @param date
     * @return
     */
    public static String getTxnDateStr(Date date){
    	return pareDate(date, DATESHORTFORMAT);
    }
    
    /**
     * 获取交易时间字符串 6位 HHmmss 24小时制
     * @param date
     * @return
     */
    public static String getTxnTimeStr(Date date){
    	return pareDate(date, TIMESHORTFORMAT);
    }
    
    /**
     * 获取交易时间戳字符串 14位 yyyyMMddHHmmss 24小时制
     * @return
     */
    public static String getCurrentTxnTimestamp(){
    	return pareDate(new Date(), DATETIMENOSPLITFORMAT);
    }
    
}
