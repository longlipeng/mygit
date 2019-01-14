package com.allinfinance.prepay.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class XmlDom {

	public static Map parse2(String xmlDoc) {     
	    // 创建一个新的字符串     
	    StringReader xmlString = new StringReader(xmlDoc);     
	    // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入     
	    InputSource source = new InputSource(xmlString);     
	    // 创建一个新的SAXBuilder     
	    SAXBuilder saxb = new SAXBuilder();     
	    Map map = new HashMap();
	    
	    try {     
	       
	        // 通过输入源构造一个Document     
	        Document doc = saxb.build(source);     
	        // 取的根元素     
	        Element root = doc.getRootElement();     
	    
	        // 得到根元素所有子元素的集合     
	        List node = root.getChildren();     
	        Element et = null;     
	        for (int i = 0; i < node.size(); i++) {     
	            et = (Element) node.get(i);// 循环依次得到子元素     
	            List subNode = et.getChildren(); // 得到内层子节点         
	            map.put(et .getName(), et.getText()); // 装入到Map中     
	                
	    
	            // Map获取到值时才装入     
	        }
	    } catch (JDOMException e) {     
	        e.printStackTrace();     
	    } catch (IOException e) {     
	        e.printStackTrace();     
	    }     
	    return map; 

	}
	
}
