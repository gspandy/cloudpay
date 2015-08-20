package com.whty.utils.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: JsonUtil 
 *
 * Description:
 * 
 * Copyright: Copyright (c) 2015年8月11日 下午2:09:27 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class JsonUtil {
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	
   /**
     * 
     * @param jsonStr
     * @param placeHolder
     * @param value
     * @throws 
     */
    public static String wapperJsonStr(String jsonStr,String placeHolder[][]){
    	int rowLength = placeHolder.length;//行长度
    	int columnLength = placeHolder[0].length;//列长度
    	for(int i = 0;i<rowLength;i++){
    		jsonStr = jsonStr.replaceAll(placeHolder[i][0], placeHolder[i][columnLength-1]);
    	}
    	return jsonStr;
    }
    
	/**
	 * 处理只有key,value这种的json串
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String,String>  readSimpleJson2Map(String jsonStr){
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		HashMap<String,String> map = new HashMap<String,String>();
		map =	(HashMap<String,String>)JSONObject.toBean(jsonObj);
		if(logger.isDebugEnabled()){
			Iterator<Entry<String, String>> iter = map.entrySet().iterator();
			while(iter.hasNext()){
				Entry<String, String> entry = iter.next();
				System.out.println("key["+entry.getKey()+"],"+"value["+entry.getValue()+"]");
			}
		}
		return map;
		
	}
	
	/**
	 * 处理只有复杂json串
	 * @param jsonStr 
	 * @param objs 复杂的数据类型
	 * @return
	 */
//	@SuppressWarnings("unchecked")
//	public Map<String,?>  readComPexJson2Map(String jsonStr,Object... objs){
//		if(logger.isDebugEnabled()){				
//			System.out.println("objs.length:"+objs.length);
//		}
//		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
//		
//		for(int i =0;i<objs.length;i++){
//			//objs[i].getClass().
//		}
//		Map<String,Class<?>> clazzMap = new HashMap<String,Class<?>>();
//		clazzMap.put("array", String[].class);
//		//clazzMap.put("array", Sudent.class);
//		Map<String,?> mapbean = (Map<String, ?>) JSONObject.toBean(jsonObj,Map.class,clazzMap);
//	
//		return mapbean;
//		
//	}
	
	/**
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static List<Map<String, Object>> parseJSON2List(String jsonStr){
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> it = jsonArr.iterator();
		while(it.hasNext()){
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}
	
   /**
    * 
    * @param jsonStr
    * @return
    */
	public static Map<String, Object> parseJSON2Map(String jsonStr){
		Map<String, Object> map = new HashMap<String, Object>();
		//最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for(Object k : json.keySet()){
			Object v = json.get(k); 
			//如果内层还是数组的话，继续解析
			if(v instanceof JSONArray){
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
				@SuppressWarnings("unchecked")
				Iterator<JSONObject> it = ((JSONArray)v).iterator();
				while(it.hasNext()){
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}
	
	/**
	 * java 读取文件转换成字符串
	 * @param src
	 * @return
	 */
	public static String readFromFile(File src) {
        try {
        	InputStreamReader isr = new InputStreamReader(new FileInputStream(src), "UTF-8");
            @SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();
            String content;
            while((content = bufferedReader.readLine() )!=null){
                stringBuilder.append(content);
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    } 

}
