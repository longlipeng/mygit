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
	    // ����һ���µ��ַ���     
	    StringReader xmlString = new StringReader(xmlDoc);     
	    // �����µ�����ԴSAX ��������ʹ�� InputSource ������ȷ����ζ�ȡ XML ����     
	    InputSource source = new InputSource(xmlString);     
	    // ����һ���µ�SAXBuilder     
	    SAXBuilder saxb = new SAXBuilder();     
	    Map map = new HashMap();
	    
	    try {     
	       
	        // ͨ������Դ����һ��Document     
	        Document doc = saxb.build(source);     
	        // ȡ�ĸ�Ԫ��     
	        Element root = doc.getRootElement();     
	    
	        // �õ���Ԫ��������Ԫ�صļ���     
	        List node = root.getChildren();     
	        Element et = null;     
	        for (int i = 0; i < node.size(); i++) {     
	            et = (Element) node.get(i);// ѭ�����εõ���Ԫ��     
	            List subNode = et.getChildren(); // �õ��ڲ��ӽڵ�         
	            map.put(et .getName(), et.getText()); // װ�뵽Map��     
	                
	    
	            // Map��ȡ��ֵʱ��װ��     
	        }
	    } catch (JDOMException e) {     
	        e.printStackTrace();     
	    } catch (IOException e) {     
	        e.printStackTrace();     
	    }     
	    return map; 

	}
	
}
