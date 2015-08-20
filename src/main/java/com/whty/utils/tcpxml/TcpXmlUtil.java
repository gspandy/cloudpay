package com.whty.utils.tcpxml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
/**
 * 
 * Title: CloudPay Platform
 * 
 * ClassName: TcpXmlUtil 
 *
 * Description:
 * 
 * Copyright: Copyright (c) 2015年8月19日 下午3:56:23 
 * 
 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
 * 
 * @author  zhangyudong
 * 
 * @version 1.0
 */
public class TcpXmlUtil {
	
	public static HashMap<String,String> map = new HashMap<String,String>();
    /**
     * 
     * @param xmlStr
     * @throws DocumentException 
     */
    public static void parseXmlStr(String xmlStr) throws DocumentException{
    	System.out.println("解析之前的报文:"+xmlStr);
    	Element root = getRoot(xmlStr);
    	readXml(root);
    }
    /**
     * 
     * @param xmlStr
     * @param placeHolder
     * @param value
     * @throws DocumentException
     */
    public static String wapperXmlStr(String xmlStr,String placeHolder[][]) throws DocumentException{
    	
    	int rowLength = placeHolder.length;//行长度
    	int columnLength = placeHolder[0].length;//列长度
    	for(int i = 0;i<rowLength;i++){
    		xmlStr = xmlStr.replaceAll(placeHolder[i][0], placeHolder[i][columnLength-1]);
    	}
    	System.out.println("封装后的报文:"+xmlStr);
    	return xmlStr;
    }
    /**
     * 
     * @param xmlStr
     * @return
     * @throws DocumentException
     */
	public static Element getRoot(String xmlStr) throws DocumentException{
		Document document;
		document = DocumentHelper.parseText(xmlStr);
		Element root=document.getRootElement();
		return root;
	}
	/**
	 * 
	 * @param element
	 */
	public static void readXml(Element element){
		if(element.elements().size()==0){
			System.out.println(element.getName()+"*="+element.getText());
			return;
		}
		
		for (@SuppressWarnings("unchecked")
		Iterator<Element> iter=element.elementIterator();iter.hasNext();) {
			Element ele=iter.next();
//			if(ele.getParent().getName().equalsIgnoreCase("BODY")){
//				System.out.println("-------------------------body的直接子元素:"+ele.getName());
//			}
//			System.out.println(ele.getName()+"%="+ele.getText().trim());
			map.put(ele.getNamespacePrefix()+":"+ele.getName().trim(), ele.getText().trim());
			if(ele.elements().size()!=0){
//				System.out.println(("element "+ele.getName()+" has "+ele.elements().size())+(ele.elements().size()==1?" subTag it is ":" subTags there are"));
				readXml(ele);
				
			}
						
		}
		
		
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
