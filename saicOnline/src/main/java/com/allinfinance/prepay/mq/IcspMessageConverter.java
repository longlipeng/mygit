package com.allinfinance.prepay.mq;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import com.allinfinance.prepay.message.BasicMessage;
import com.allinfinance.prepay.utils.XmlName;
import com.allinfinance.prepay.utils.XmlUtil;

public class IcspMessageConverter implements MessageConverter
{

	private static final String msgCLassPkg = "com.allinfinance.prepay.message.";
	private Logger logger = Logger.getLogger(getClass());
	@Override
	public Message toMessage(Object object, MessageProperties messageProperties)
			throws MessageConversionException
	{
		if(object != null)
		{
			BasicMessage message = (BasicMessage)object;
			String xmlResp = message.toXml();
			//String xmlResp = object.toString();
			xmlResp = XmlUtil.parseToSend(xmlResp);
			Message resp = null;
			try {
				resp = new Message(xmlResp.getBytes("gbk"), messageProperties);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return resp;
		}
		return null;
	}

	@Override
	public Object fromMessage(Message message)
			throws MessageConversionException
	{
		try
		{
			Document document = DocumentHelper.parseText(new String(message.getBody()));
			Element root = document.getRootElement();
			
			Document newDocument = XmlUtil.parseFromAccept(root);
			
//			System.out.println("newDocument:"+new String(newDocument.asXML().getBytes()));
//			System.out.println("message:"+new String(message.getBody()));
			
			Element transCodeNode = root.element("BASE").element(XmlName.TRANCODE);
			if (transCodeNode == null) 
			{
				logger.error("报文解析失败解析失败：" + XmlName.TRANCODE);
				return null;
			}
			Class<?> clazz = Class.forName(msgCLassPkg+"MessageSync"+transCodeNode.getTextTrim()+"Req");
			Method parser = clazz.getMethod("parseXml", String.class, Class.class);
			BasicMessage req = (BasicMessage)(parser.invoke(null, new String(newDocument.asXML().getBytes()), clazz));//message.getBody() 多带了一层base  data，想办法去掉这一层，直接package下就是需要的变量和值。
//			BasicMessage req = (BasicMessage)BasicMessage.parseXml(Class.forName(msgCLassPkg+"MessageICS"+transCodeNode.getTextTrim()+"Req"), 
//					new String(message.getBody()));
			return req;
		} 
		catch (ClassNotFoundException e) 
		{
			logger.error("非法的交易码", e);
			throw new MessageConversionException("非法的交易码", e);
		}
		catch (DocumentException e)
		{
			logger.error("报文解析错误", e);
			throw new MessageConversionException("报文解析错误", e);
		} catch (IllegalAccessException e)
		{
			logger.error("报文解析错误", e);
			throw new MessageConversionException("报文解析错误", e);
		} catch (IllegalArgumentException e)
		{
			logger.error("报文解析错误", e);
			throw new MessageConversionException("报文解析错误", e);
		} catch (InvocationTargetException e)
		{
			logger.error("报文解析错误", e);
			throw new MessageConversionException("报文解析错误", e);
		} catch (NoSuchMethodException e)
		{
			logger.error("报文解析错误", e);
			throw new MessageConversionException("报文解析错误", e);
		} catch (SecurityException e)
		{
			logger.error("报文解析错误", e);
			throw new MessageConversionException("报文解析错误", e);
		}
	}
}
