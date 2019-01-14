package com.huateng.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

public class JaxbUtil {
	 /**
     * JavaBean转换成xml
     * 
     * @param obj
     * @param encoding
     * @return
     */
    public static String convertToXml(Object obj) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //注意jdk版本
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(baos, (String) marshaller.getProperty(Marshaller.JAXB_ENCODING));
            xmlStreamWriter.writeStartDocument((String) marshaller.getProperty(Marshaller.JAXB_ENCODING), "1.0");
            marshaller.marshal(obj, xmlStreamWriter);
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.close();
            return new String(baos.toString("UTF-8"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * xml转换成JavaBean
     * 
     * @param xml
     * @param c
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T converyToJavaBean(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
    /**
     * 字符左补零
     * @param code
     * @param len
     * @return
     */
    public static String codeAddOne(String code, int len){
        Integer intHao = Integer.parseInt(code);
//        intHao++;
        String strHao = intHao.toString();
        while (strHao.length() < len) {
            strHao = "0" + strHao;
        }
        return strHao;
    }

}
