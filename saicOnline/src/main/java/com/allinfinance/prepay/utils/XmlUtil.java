package com.allinfinance.prepay.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtil {
	
	public static Map cardMap = new HashMap<String, String>();
	
	private static Map baseFieldMap = new HashMap<String, String>(){{
		put("TXN_CODE", "���״���");
		put("MSG_TYPE", "��������");
		put("TXN_TIME", "����ʱ��");
		put("REQSEQNO", "������ˮ��");
		put("RESPSEQNO", "Ӧ����ˮ��");
		put("RANDOM", "�����");
		put("CHANNEL", "������");
		put("ISSUER_ID", "����ID");
		put("TERM_ID", "�ն˺�");
		put("MCHNT_CD", "�̻���");
		put("RESP_CODE", "������");
		put("BRANCH_ID", "��������");
	}};
	
	
	
	public static Document parseFromAccept(Element root) {
		List<Element> childList = root.elements();
		Document newDocument = DocumentHelper.createDocument();
		Element newElement = newDocument.addElement("DATA");
		for(Element element : childList){
			List<Element> el = element.elements();
			if(el.size()==0){
				newElement.addElement(element.getName());
				newElement.element(element.getName()).addText(element.getText());
				continue;
			}
			for(Element e : el){
				List<Element> e2 = e.elements();
				Element newElemente = newElement.addElement(e.getName());
				if(e2.size()==0){
					newElement.element(e.getName()).addText(e.getText());
					continue;
				}
				
				for(Element ee : e2){
					newElemente.addElement(ee.getName());
					newElemente.element(ee.getName()).addText(ee.getText());
				}
			}
		}
		return newDocument;

	}
	
	
	public static String parseToSend(String xmlResp) {
		Document RespDocument = null;
		try {
			RespDocument = DocumentHelper.parseText(xmlResp);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String xmlHead="<?xml version='1.0' encoding='gbk'?>";
		Element root = RespDocument.getRootElement();
		Document newDocument = DocumentHelper.createDocument();
		Element packageElement = newDocument.addElement("PACKAGE");
		Element baseElement = packageElement.addElement("BASE");
		Element macElement = packageElement.addElement("MAC");
		boolean isCreateData = false;
		Element dataElement = null;
		
		List<Element> childList = root.elements();
		for(Element element : childList){
			if(baseFieldMap.containsKey(element.getName())){
				baseElement.addElement(element.getName());
				baseElement.element(element.getName()).addText(element.getText());
			}else if(element.getName().trim().equals("MAC")){
				macElement.addText(element.getText());
			}
			else{
				if(!isCreateData){
					dataElement = packageElement.addElement("DATA");
					isCreateData = true;
				}
				List<Element> el = element.elements();
				
				Element newElemente = dataElement.addElement(element.getName());
				if(el.size()==0){
					dataElement.element(element.getName()).addText(element.getText());
					continue;
				}
				for(Element ee : el){
					newElemente.addElement(ee.getName());
					newElemente.element(ee.getName()).addText(ee.getText());
				}
//				dataElement.addElement(element.getName());
//				dataElement.element(element.getName()).addText(element.getText());
			}
		}
		
		return xmlHead+packageElement.asXML();
	}
//	public static void main(String[] args) {
//		System.out.println(parseToSend("<DATA><TXN_CODE>P021</TXN_CODE><MSG_TYPE>1</MSG_TYPE><TXN_TIME>20160426111111</TXN_TIME><CHANNEL>50000001</CHANNEL><ISSUER_ID>00000002</ISSUER_ID><TERM_ID>1111</TERM_ID><MCHNT_CD>1111</MCHNT_CD><CARD_NO>9088810000000000022</CARD_NO><CARD_PWD>309EBFBA52511E14</CARD_PWD><MAC></MAC></DATA>"));
//	}
	
}
