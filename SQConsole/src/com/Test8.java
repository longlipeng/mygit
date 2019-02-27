package com;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Test8 {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		//SAX解析xml文件
		SAXReader reader = new SAXReader();
	    
		File file = new File("E:\\book.xml");
		//从xml文件获取数据
	    Document document = reader.read(file);
	    //获取根元素节点
	    Element root = document.getRootElement();
//	    System.out.println(root.getName());
	    //获取当前元素节点下一级的全部子元素
	    List<Element> childElements = root.elements();
	    
	    for (Element element : childElements) {
//	    	String name = element.getName();
//	    	String text = element.getText();
//	    	System.out.println(name + "=" + text);
	    	String id = element.attributeValue("id");
    		System.out.println(id);
	    	List<Element> childElements1 = element.elements();
	    	for (Element element2 : childElements1) {
	    		//获取当前元素节点名称
//	    		String name1 = element2.getName();
	    		//获取当前元素节点的值
//	    		String text1 = element2.getText();
//	    		System.out.println(name1 + "=" + text1);
	    		//获取当前元素节点的属性值
	    		String name = element2.attributeValue("name");
	    		System.out.println(name);
			}
		}
	    
	    PrintWriter pw = new PrintWriter(file);
	    pw.print("1");
	    pw.write("2");
	    //刷新
	    pw.flush();
	    pw.close();
	}
	
}
