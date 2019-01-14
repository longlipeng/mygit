package com.webservice.util;

import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;



public class CommonFunction {

	public static boolean isEmpty(final String value) {
		return value == null || value.trim().length() == 0
				|| "null".endsWith(value);
	}

	/**
	 * 填补字符串
	 * 
	 * @param str
	 * @param fill
	 * @param len
	 * @param isEnd
	 * @return
	 */
	public static String fillString(String str, char fill, int len,
			boolean isEnd) {
		// int fillLen = 0;
		// if (StringUtil.isNull(str)) {
		// fillLen = len;
		// str = "";
		// } else {
		// fillLen = len - str.getBytes().length;
		// }
		int fillLen = len - str.getBytes().length;
		if (len <= 0) {
			return str;
		}
		for (int i = 0; i < fillLen; i++) {
			if (isEnd) {
				str += fill;
			} else {
				str = fill + str;
			}
		}
		return str;
	}

	/**
	 * 填补字符串(中文字符扩充)
	 * 
	 * @param str
	 * @param fill
	 * @param len
	 * @param isEnd
	 * @return
	 */
	public static String fillStringForChinese(String str, char fill, int len,
			boolean isEnd) {
		int num = 0;
		Pattern p = Pattern.compile("^[\u4e00-\u9fa5]");
		for (int i = 0; i < str.length(); i++) {
			Matcher m = p.matcher(str.substring(i, i + 1));
			if (m.find()) {
				num++;
			}
		}
		int fillLen = len - (str.length() + num);
		if (len <= 0) {
			return str;
		}
		for (int i = 0; i < fillLen; i++) {
			if (isEnd) {
				str += fill;
			} else {
				str = fill + str;
			}
		}
		return str;
	}

	/**
	 * 
	 * @param cardPhyType
	 *            20:CPU,以外:M1
	 * @return
	 */
	public static boolean isCpuCardType(String cardPhyType) {
		if("20".equals(cardPhyType)){
			return true;
		}
		return false;
	}
	/**
	 * webservice层拦截的错误需要组装成收单后台一样的格式的XML报文，返回给外部 PARAERR = "E2";//参数错误 SCODEERR
	 * = "E1";//服务代码错误 SYSERR = "E3";//参数错误
	 * 
	 * @param toOutStr
	 * @return
	 */
	public static String wsErrProcess(String toOutStr) {
		if (Constants.PARAERR.equals(toOutStr)
				|| Constants.SCODEERR.equals(toOutStr)
				|| Constants.SYSERR.equals(toOutStr)) {
			// 组装错误处理的XML报文
			try {
				BodySendToOutWsErr body = new BodySendToOutWsErr();
				body.setFrontRspCode(toOutStr);
				body.setReplyMsg(wsErrProcessCodeToMsg(toOutStr));// 错误代码转义
				SendToOutWsErr entity = new SendToOutWsErr();
				entity.setBody(body);

				JAXBContext context = JAXBContext
						.newInstance(new Class[]{SendToOutWsErr.class});
				Marshaller mar = context.createMarshaller();
				// mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				mar.setProperty(Marshaller.JAXB_ENCODING, Constants.XMLENCODING);
				mar.setProperty(Marshaller.JAXB_FRAGMENT, true);

				StringWriter writer = new StringWriter();
				mar.marshal(entity, writer);
				String sendXml = writer.toString();

				return sendXml;

			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				return e.getMessage();

			}

		} else {
			return toOutStr;// 不需要组装,收单后台已经组装好了
		}

	}
	/**
	 * 
	 * @param wsErrCode
	 * @return
	 */
	public static String wsErrProcessCodeToMsg(String wsErrCode) {
		String wsErrCodeToMsg = wsErrCode;

		switch (getWsErrProcessCode(wsErrCode)) {
			case E1 :
				wsErrCodeToMsg = "交易规定的服务代码错误";
				break;
			case E2 :
				wsErrCodeToMsg = "交易上送的参数错误";
				break;
			case E3 :
				wsErrCodeToMsg = "系统异常";
				break;
			default :
				wsErrCodeToMsg = "未知错误";
				break;

		}

		return wsErrCodeToMsg;

	}

	/**
	 * 
	 * Title:
	 * 
	 * ClassName: WsErrProcessCode
	 * 
	 * Description:
	 * 
	 * Copyright: Copyright (c) 2014-9-16 下午12:50:10
	 * 
	 * Company: Wuhan Tianyu Information Industry Co.,Ltd.
	 * 
	 * @author zhangyudong
	 * 
	 * @version 1.0
	 */
	public enum WsErrProcessCode {
		E1, E2, E3;
	}

	public static WsErrProcessCode getWsErrProcessCode(String msgCode) {

		WsErrProcessCode res = WsErrProcessCode.E3;
		if (Constants.SCODEERR.equals(msgCode)) {
			res = WsErrProcessCode.E1;
		} else if (Constants.PARAERR.equals(msgCode)) {
			res = WsErrProcessCode.E2;
		} else if (Constants.SYSERR.equals(msgCode)) {
			res = WsErrProcessCode.E3;
		}

		return res;

	}
}
