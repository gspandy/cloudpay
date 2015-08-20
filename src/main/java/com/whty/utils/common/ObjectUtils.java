package com.whty.utils.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.utils.common.annotation.DescAnnotation;

/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: ObjectUtils 
 *
 * Description:Object工具类
 * 
 * Copyright: Copyright (c) 2015年8月11日 下午2:08:24 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class ObjectUtils {
	private static Logger log = LoggerFactory.getLogger(ObjectUtils.class);
	/**
	 * 得到类名
	 * @param fullClassName 全路径名，如com.ideal.util.test
	 * @return
	 */
	public static String getObjectName(String fullClassName){
		String result = "";
		if(fullClassName.contains(".")){
			int firstIndex = fullClassName.lastIndexOf(".");
			int lastIndex = fullClassName.length();
			if(fullClassName.contains("$")){
				lastIndex = fullClassName.indexOf("$");
			}
			result = fullClassName.substring(firstIndex + 1, lastIndex);
		}else{
			result = fullClassName;
		}
		return result;
	}
	/**
	 * 方法名首字母大写
	 */
	public static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
	/**
	 * 把对象的属性装载入MAP
	 * @param map
	 * @return
	 */
	public static String convertMapToString(Map map){
		Set set = map.entrySet();
		List<MapObject> infoList = new ArrayList<MapObject>();
		String value = null;
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			if(entry.getValue() != null){
				MapObject obj = new MapObject();
				obj.setKey(String.valueOf(entry.getKey()));
				value = String.valueOf(entry.getValue());
				//防止长度过长导致数据存储的时候会报错
				if(value != null && !"".equals(value) && value.length() > 200){
					value = value.substring(0,200);
				}
				obj.setValue(value);
				infoList.add(obj);
			}
		}
		return JSONArray.fromObject(infoList).toString();
	}
	/**
	 * 比较2个Map中的值(用于修改操作时)
	 */
	public static String[] comparisonMap(Map oldMap, Map targetMap) {
		String[] result = new String[2];
		Set oldSet = oldMap.entrySet();
		Set targetSet = targetMap.entrySet();
		Object targetKey = null;
		String targetValue = null;
		String oldValue = null;
		List<MapObject> oldInfoList = new ArrayList<MapObject>();
		List<MapObject> targetList = new ArrayList<MapObject>();
		for (Iterator iterator = targetSet.iterator(); iterator.hasNext();) {
			Map.Entry targetObject = (Map.Entry) iterator.next();
			targetKey = targetObject.getKey();
			targetValue = String.valueOf(targetObject.getValue());
			oldValue = String.valueOf(oldMap.get(targetKey));
			//只有不同的时候才记录日志
			if ((targetValue != null && !targetValue.equals(oldValue) && !"null".equals(oldValue)) || (targetValue == null && !"null".equals(oldValue)) || ("null".equals(oldValue) && !"".equals(targetValue))) {
				MapObject oldObj = new MapObject();
				oldObj.setKey(String.valueOf(targetKey));
				
				if(oldValue != null && !"".equals(oldValue) && oldValue.length() > 200){
					oldValue = oldValue.substring(0, 200);
				}
				oldObj.setValue(oldValue);
				oldInfoList.add(oldObj);
				MapObject targetObj = new MapObject();
				targetObj.setKey(String.valueOf(targetKey));
				if(targetValue != null && !"".equals(targetValue) && targetValue.length() > 200){
					targetValue = targetValue.substring(0, 200);
				}
				targetObj.setValue(targetValue);
				targetList.add(targetObj);
			}
		}
		result[0] = JSONArray.fromObject(oldInfoList).toString();
		result[1] = JSONArray.fromObject(targetList).toString();
		return result;
	}

	/**
	 * 打印对象中的属性
	 * @param obj
	 */
	public static void printObjectValue(Object obj) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Object object = AopTargetUtils.getTarget(obj);
			if (object != null) {
				Class<?> cls = object.getClass();
				Field[] fields = cls.getDeclaredFields();
				Method method = null;
				Object val = null;
				String typeName = null;
				for (Field field : fields) {
					typeName = field.getGenericType().toString();
					if (typeName.equals("class java.lang.String")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (String) method.invoke(object);
					} else if (typeName.equals("class java.lang.Integer")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Integer) method.invoke(object);
					} else if (typeName.equals("class java.lang.Double")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Double) method.invoke(object);
					} else if (typeName.equals("class java.util.Date")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Date) method.invoke(object);
					} else if (typeName.equals("class java.lang.Short")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Short) method.invoke(object);
					} else if (typeName.equals("class java.lang.Long")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Long) method.invoke(object);
					} else if (typeName.equals("boolean")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Boolean) method.invoke(object);
					}
					if (val != null) {
						map.put(field.getName(), val);
					}
				}
			} else {
				log.debug("该对象为空");
			}
			Set set = map.entrySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				log.debug(entry.getKey() + ":" + entry.getValue());
			}
		} catch (SecurityException e) {
			log.error("Exception", e);
		} catch (IllegalArgumentException e) {
			log.error("Exception", e);
		} catch (NoSuchMethodException e) {
			log.error("Exception", e);
		} catch (IllegalAccessException e) {
			log.error("Exception", e);
		} catch (InvocationTargetException e) {
			log.error("Exception", e);
		} catch (Exception e) {
			log.error("Exception", e);
		}
	}
	/**
	 * 得到对象中的属性并转化成MAP
	 */
	public static Map<String,Object> getObjectValue(Object object) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if (object != null) {
				Class<?> cls = object.getClass();
				Field[] fields = cls.getDeclaredFields();
				Method method = null;
				Object val = null;
				String typeName = null;
				for (Field field : fields) {
					typeName = field.getGenericType().toString();
					if (typeName.equals("class java.lang.String")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (String) method.invoke(object);
					} else if (typeName.equals("class java.lang.Integer")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Integer) method.invoke(object);
					} else if (typeName.equals("class java.lang.Double")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Double) method.invoke(object);
					} else if (typeName.equals("class java.util.Date")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Date) method.invoke(object);
					} else if (typeName.equals("class java.lang.Short")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Short) method.invoke(object);
					} else if (typeName.equals("class java.lang.Long")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Long) method.invoke(object);
					} else if (typeName.equals("boolean")) {
						method = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						val = (Boolean) method.invoke(object);
					}
					if (val != null) {
						DescAnnotation descAnnotation = field.getAnnotation(DescAnnotation.class);
						if(descAnnotation != null){
							map.put(descAnnotation.description(), val);
						}else{
							map.put(field.getName(), val);
						}
					}
				}
			} else {
				log.info("该对象为空");
			}
		} catch (SecurityException e) {
			log.error("Exception", e);
		} catch (IllegalArgumentException e) {
			log.error("Exception", e);
		} catch (NoSuchMethodException e) {
			log.error("Exception", e);
		} catch (IllegalAccessException e) {
			log.error("Exception", e);
		} catch (InvocationTargetException e) {
			log.error("Exception", e);
		} catch (Exception e) {
			log.error("Exception", e);
		}
		return map;
	}
}
