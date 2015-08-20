package com.whty.utils.xml;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.whty.server.NettyService;

/**
 * 解析xml文件
 */
public class Dom4jTool<T>{
	//将xml报文转换为javaBean
	@SuppressWarnings("unchecked")
	public List<T> readXML(String xmlPath,T t) {
        List<T> list = new ArrayList<T>();//创建list集合   
        try {   
            File f = new File(xmlPath);//读取文件   
            SAXReader reader = new SAXReader();   
            Document doc = reader.read(f);//dom4j读取   
            Element root = doc.getRootElement();//获得根节点   
            Element foo;//二级节点   
            Field[] properties = t.getClass().getDeclaredFields();//获得实例的属性   
            //实例的set方法   
            Method setmeth;
            Iterator<Element> iter = root.elementIterator(t.getClass().getSimpleName());
            while(iter.hasNext()) {
            	//遍历t.getClass().getSimpleName()节点   
                foo = (Element) iter.next();//下一个二级节点   
                t=(T)t.getClass().newInstance();//获得对象的新的实例   
                for (int j = 0; j < properties.length; j++) {//遍历所有孙子节点   
                    //实例的set方法   
                      setmeth = t.getClass().getMethod(   
                            "set"  
                                    + properties[j].getName().substring(0, 1)   
                                            .toUpperCase()   
                                    + properties[j].getName().substring(1),properties[j].getType());   
                  //properties[j].getType()为set方法入口参数的参数类型(Class类型)   
                    setmeth.invoke(t, foo.elementText(properties[j].getName()));//将对应节点的值存入   
                }   
                list.add(t);   
            }   
        } catch (Exception e) {
            e.printStackTrace();   
        }   
        return list; 
	}   
	 
	public static void main(String[] args){
		Dom4jTool<NettyService> tool = new Dom4jTool<NettyService>();
		NettyService server = new NettyService();
		List<NettyService> list=tool.readXML(tool.getClass().getClassLoader().getResource("server.xml").getPath(), server);
		System.out.println(list);
	}
}
