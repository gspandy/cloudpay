package com.whty.server;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ReadSoap {
	public static void main(String[] args) throws DocumentException {
		String path="soapTemplate1.xml";
		Element root=ReadSoap.getRoot(path);
		ReadSoap.readSoap(root);
		
		 
		
	}
	
	public static Element getRoot(String path) throws DocumentException{
		Document document;
		SAXReader reader=new SAXReader();
		document=reader.read(new File(path));
		Element root=document.getRootElement();
		return root;
	}
	
	public static void readSoap(Element element){
		if(element.elements().size()==0){
			System.out.println(element.getName()+"*="+element.getText());
			return;
		}
		
		for (Iterator<Element> iter=element.elementIterator();iter.hasNext();) {
			Element ele=iter.next();
			if(ele.getParent().getName().equalsIgnoreCase("BODY")){
				System.out.println("-------------------------body的直接子元素:"+ele.getName());
			}
			System.out.println(ele.getName()+"%="+ele.getText().trim());
			if(ele.elements().size()!=0){
				System.out.println(("element "+ele.getName()+" has "+ele.elements().size())+(ele.elements().size()==1?" subTag it is ":" subTags there are"));
				readSoap(ele);
				
			}
			
			
			
		}
		
		
	}
	
}
