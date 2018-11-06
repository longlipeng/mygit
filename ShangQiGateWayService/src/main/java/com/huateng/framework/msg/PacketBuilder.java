package com.huateng.framework.msg;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.allinfinance.framework.dto.OperationResult;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.constant.Const;
import com.huateng.framework.util.Crypt;
import com.huateng.framework.util.StringUtils;

/**
 * <p>
 * Title: Accor
 * </p>
 * 
 * <p>
 * Description:Accor Project 1nd Edition
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author YY
 * @version 1.0
 */

public class PacketBuilder {
	static final String VISABLE = "1";
	static final String NOT_VISABLE = "0";
	static final String STR_ZERO = "0";
	static final String LEFT_ZERO = "L0";
	static final String RIGHT_ZERO = "R0";

	static Logger logger = Logger.getLogger(PacketBuilder.class);

	private static byte[] blankarray = null;

	private static byte[] getBlankArray() {
		if (blankarray == null) {
			blankarray = new byte[Const.PACKET_LEN];
			Arrays.fill(blankarray, (byte) ' ');
		}
		return blankarray;
	}

	/**
	 * @description 把Object对象转换成byte数组
	 * @param req
	 * @param xmlname
	 *            xml文件名
	 * @param packetname
	 *            packet名
	 * @return List对象，包括3个元素，1.转换后的byte数组 2.发送数据字节数 3.接收数据字节数
	 * @throws BizServiceException
	 */
	public static List build(Object req, String xmlname, String packetname)
			throws BizServiceException {
		List resultList = new ArrayList();
		// 报文长度
		int megLen = 0;
		// 发送长度
		int sendLen = 0;
		String sendLenVisable = NOT_VISABLE;
		// 接收长度
		int recvLen = 0;
		String recvLenVisable = NOT_VISABLE;
		// 准备一个byte缓冲区
		ByteBuffer bf = ByteBuffer.allocate(Const.PACKET_LEN);
		// 总字节数
		int lenTotal = 0;
		bf.put(getBlankArray());

		// 初始时候写入位置为0
		int pos = 0;
		try {
			// 读取用于转换的xml文件
			Document doc = PacketConfig.getDocument(xmlname);

			// 找到文件中用于转换的<packet>标签，位置存在index中
			int index = 0;
			List packets = doc.selectNodes("/root/packet");
			for (int i = 0; i < packets.size(); i++) {
				index = i;
				if (((Element) packets.get(i)).attributeValue("name").equals(
						packetname)) {
					break;
				}
			}
			// 如果没有找到转换<packet> 抛出异常
			if (index == packets.size()) {
				String err = "can not find the packet [" + packetname
						+ "] in xml file [" + xmlname + "]";
				logger.error(err);
				throw new BizServiceException(err);
			}

			Element packet = (Element) packets.get(index);
			/*
			 * //从<request>标签中取得发送长度 List requestList =
			 * packet.selectNodes("request"); Element request =
			 * (Element)requestList.get(0); String sSendLen =
			 * request.attributeValue("length"); if (!(sSendLen == null ||
			 * sSendLen.trim().length() == 0)){ sendLen =
			 * Integer.parseInt(sSendLen); }
			 */
			// 从<response>标签中取得接收长度
			List responseList = packet.selectNodes("response/field");
			Element response = (Element) responseList.get(0);
			String sRecvLenTmp = response.attributeValue("value");
			logger.debug("response length=" + sRecvLenTmp);
			String sRecvLenVisable = response.attributeValue("visable");
			if (sRecvLenVisable == null || sRecvLenVisable.equals(VISABLE)) {
				recvLenVisable = VISABLE;
				recvLen = Integer.parseInt(response.attributeValue("length"));
			} else {
				if (!(sRecvLenTmp == null || sRecvLenTmp.trim().length() == 0)) {
					recvLen = Integer.parseInt(sRecvLenTmp);
				}
			}

			// 获得是否有mac域的标志
			List packetHead = packet.selectNodes("request");
			Element eleHead = (Element) packetHead.get(0);
			String needMac = eleHead.attributeValue("mac");

			List lst = packet.selectNodes("request/field");

			// 报文长度
			FieldDef fdLen = new FieldDef();
			// 第一个<field>标签存放报文的总长度, visable属性表示报文总长度是否需要附加在报文最前面,
			// 0-不添加 1-添加
			for (int i = 0; i < lst.size(); i++) {
				Element ele = (Element) lst.get(i);

				String ref = ele.attributeValue("ref");
				// logger.info("ref=" + ref);

				// <field>标签中type属性不能为空
				String type = ele.attributeValue("type");
				if (type == null || type.trim().length() == 0) {
					String err = "attribute 'type' of element 'field' can not be empty.";
					logger.error(err);
					throw new BizServiceException(err);
				}

				// <field>标签中length属性不能为空
				String slen = ele.attributeValue("length");
				if (slen == null || slen.trim().length() == 0) {
					String err = "attribute 'length' of element 'field' can not be empty.";
					logger.error(err);
					throw new BizServiceException(err);
				}
				int len = Integer.parseInt(slen);

				// <field>标签中是否需要添加长度位标识, lenType是长度位的长度, 例如lenType=2 表示
				// 此域前面需要添加2个字符(1个字节)的长度位
				String sLenType = ele.attributeValue("lenType");
				int lenType = 0;
				if (sLenType != null) {
					lenType = Integer.parseInt(sLenType);
				}
				// 补位标志
				String sSupply = ele.attributeValue("supply");
				if (sSupply != null) {
					if (sSupply.length() < 2) {
						throw new BizServiceException(
								"if supply is not empty, at least 2 chars");
					}
				}

				String sVisable = ele.attributeValue("visable");

				String val = ele.attributeValue("value");
				String value = "";
				// 得到数据
				if (i != 0) {
					if (val == null || val.trim().length() == 0) {
						// <field>标签中ref属性和value属性不能同时为空
						if (ref == null || ref.trim().length() == 0) {
							String err = "attribute 'ref' & 'value' of element 'field' can not be empty at the same time.";
							logger.error(err);
							throw new BizServiceException(err);
						}
						value = BeanUtils.getProperty(req, ref);
					} else {
						value = val;
					}
				} else {

					if (!(value == null || value.trim().length() == 0)) {
						megLen = len;
						value = megLen + "";
					} else {
						megLen = -1;
						value = "0";
					}

					// 记录报文长度的所有特性
					fdLen.setLength(slen);
					fdLen.setSupply(sSupply);
					fdLen.setType(type);
					fdLen.setValue(value);
					fdLen.setVisable(sVisable);
				}
				logger.debug("[field " + i + "] = " + value);

				// 如果value超过最大长度，截取最大长度的字符串
				if (value.length() > len) {
					logger.debug("[field " + i + "] = " + value
							+ " too long, cut off");
					value = value.substring(0, len);
					logger.debug("[field " + i + "] = " + value);
				}

				// 校验数据
				try {
					// 如果数据是null，抛出异常
					if (value == null) {
						String err = ref + " is null";
						logger.error(err);
						throw new BizServiceException(err);
					}

					// 如果需要补位
					if (sSupply != null) {
						value = format(value, sSupply, len);
					}
				} catch (BizServiceException err) {
					throw err;
				} catch (Exception e) {
					String err = "check data exception [" + ref + "]=" + value;
					logger.error(err);
					throw new BizServiceException(err);
				}

				// 如果这个域需要出现在报文中, 把它append到bf
				if (sVisable == null || sVisable.equals(VISABLE)) {
					// append长度域到bf
					if (lenType != 0) {
						String tmpLen = fillLen(value, lenType, type);
						logger.debug(tmpLen);
						appends(bf, tmpLen, Const.CODE_BCD, pos);
						pos = pos + tmpLen.length() / 2;
					}

					// append数据域到bf
					if (type.toUpperCase().equals(Const.CODE_BCD)) {
						if (value.length() % 2 != 0) {
							value = value + "0";
						}
						appends(bf, value, type, pos);
						pos = pos + value.length() / 2;
					} else if (type.toUpperCase().equals(Const.CODE_ASCII)
							|| type.toUpperCase().equals(Const.CODE_BINARY)) {
						appends(bf, value, type, pos);
						pos = pos + len;
					}
				}
				lenTotal = pos;

			}

			// 不定长报文，补充长度域
			if (megLen == -1 && fdLen.getVisable().equals(VISABLE)) {
				String type = fdLen.getType();
				int len = Integer.parseInt(fdLen.getLength());
				int totalLen = lenTotal - Integer.parseInt(fdLen.getLength());
				String value = format(totalLen + "", fdLen.getSupply(), len);

				// append数据域到bf
				logger.debug("append head:" + value);
				if (type.toUpperCase().equals(Const.CODE_BCD)) {
					if (value.length() % 2 != 0) {
						value = "0" + value;
					}
					appends(bf, value, type, 0);

				} else if (type.toUpperCase().equals(Const.CODE_ASCII)
						|| type.toUpperCase().equals(Const.CODE_BINARY)) {
					appends(bf, value, type, 0);

				}
			}

			logger.debug("lenTotal=" + lenTotal);
			bf.position(0);
			byte[] rst = new byte[lenTotal];
			bf.get(rst, 0, lenTotal);

			// 如果需要添加mac域
			if (!(needMac == null || needMac.equals("0"))) {
				byte[] macText = new byte[lenTotal - 21];
				bf.position(13);
				bf.get(macText, 0, macText.length);

				// Log.printBytes(macText, "");
				byte[] mac = Crypt.calMAC(macText, Crypt.getMacKey());
				// Log.printBytes(mac, "");
				logger.debug(new String(mac));
				System.arraycopy(mac, 0, rst, rst.length - 8, 8);
			}

			resultList.add(rst);

		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("unhandled exception." + ex.getMessage());
			throw new BizServiceException(ex.toString());
		}

		// logger.debug("lenTotal=" + lenTotal);
		// bf.position(0);
		// byte[] rst = new byte[lenTotal];
		// bf.get(rst, 0, lenTotal);

		// resultList.add(rst);
		resultList.add(sendLenVisable);
		resultList.add(Integer.valueOf(sendLen));
		resultList.add(recvLenVisable);
		resultList.add(Integer.valueOf(recvLen));
		return resultList;
	}

	private static String format(String str, String formatType, int len) {
		String ll = "";
		String rr = "";
		int initLen = str.length();
		try {
			if (formatType.substring(0, 1).toUpperCase().equals("L")) {
				ll = formatType.substring(1, 2);
			} else if (formatType.substring(0, 1).toUpperCase().equals("R")) {
				rr = formatType.substring(1, 2);
			}

			for (int i = initLen; i < len; i++) {
				str = ll + str + rr;
			}
		} catch (Exception e) {
			String err = "format string [" + str + "] exception. "
					+ e.toString();
			logger.error(err);
		}
		return str;
	}

	/***
	 * 计算str字符串的长度，按照字符串的格式存放这个长度数值，并且存放长度是大于lenType的最小偶数 例如，str="12345",
	 * lenType=3, str的长度是5，大于lenType的最小偶数是4，于是返回0005
	 * 
	 * @param str
	 * @param lenType
	 * @return
	 */
	static String fillLen(String str, int lenType, String type) {
		String rst = "";
		int len = str.length();
		/*
		 * if (type.equals(Const.CODE_BCD)){ len = len / 2; }
		 */
		int lenlen = String.valueOf(len).length();
		int filledLen = 0;
		String slen = String.valueOf(len);

		/*
		 * if (type.equals(Const.CODE_BCD)){ //filledLen = (lenType - lenlen +
		 * 2) / 2; filledLen = (lenType - lenlen) / 2; }else
		 * if(type.equals(Const.CODE_ASCII)){ filledLen = lenType - lenlen;
		 * }else{ filledLen = 0; }
		 */
		filledLen = lenType - lenlen;

		logger.debug("filledLen=" + filledLen);
		if (filledLen > 0) {
			for (int i = 0; i < filledLen; i++) {
				slen = "0" + slen;
			}
			if (slen.length() % 2 != 0) {
				slen = "0" + slen;
			}

		}
		rst = slen;

		return rst;
	}

	/**
	 * 把字符串附加在ByteBuffer后面
	 * 
	 * @param bf
	 * @param str
	 * @param codeType
	 * @param pos
	 */
	static void appends(ByteBuffer bf, String str, String codeType, int pos) {
		if (codeType.toUpperCase().equals(Const.CODE_ASCII)) {
			// byte[] bts = str.getBytes();
			Charset charset = Charset.forName("GB18030");
			ByteBuffer byteBuffer = charset.encode(str);
			bf.position(pos);
			bf.put(byteBuffer);
			// char[] chars = str.toCharArray();
			// for (int i = 0; i < chars.length; i++){
			// bf.position(pos);
			// byte[] byteTemp = new String(chars, i, 1).getBytes();
			// bf.put(byteTemp);
			// pos += byteTemp.length;
			// }

			return;
		} else if (codeType.toUpperCase().equals(Const.CODE_BCD)) {
			/*
			 * for (int i = 0; i < str.length()/2; i++){ byte a = (byte)(0xff &
			 * Integer.parseInt(str.substring(i*2, i*2+2) ,16));
			 * //logger.debug("" + a); bf.put(pos + i, a); }
			 */
			char[] chars = str.toCharArray();
			for (int i = 0; i < chars.length / 2; i++) {
				byte a = (byte) (0xff & Integer.parseInt("" + chars[i * 2]
						+ chars[i * 2 + 1], 16));
				bf.put(pos + i, a);
			}
			return;
		} else if (codeType.toUpperCase().equals(Const.CODE_BINARY)) {
			char[] chars = str.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				bf.position(pos);
				bf.put((byte) chars[i]);
				pos++;
			}
		}
	}

	/**
	 * 从ByteBuffer中截取字符串
	 * 
	 * @param bf
	 * @param pos
	 * @param len
	 * @param type
	 * @return
	 */
	public static String getString(ByteBuffer bf, int pos, int len, String type) {
		String rst = "";
		// logger.debug("getString(bf, "+ pos + ", " + len + ",)");
		try {
			if (type.toUpperCase().equals(Const.CODE_ASCII)) {
				// logger.debug("bf.array().length=" + bf.array().length);
				rst = new String(bf.array(), pos, len).trim();
			} else if (type.toUpperCase().equals(Const.CODE_BCD)) {
				StringBuffer temp = new StringBuffer(len * 2);
				byte[] tmp = new byte[len];
				for (int i = 0; i < len; i++) {

					bf.position(pos);
					bf.get(tmp, 0, len);

					/*
					 * temp.append((byte)((bf.array()[pos + i]& 0xf0)>>>4));
					 * temp.append((byte)(bf.array()[pos + i]& 0x0f));
					 */
				}
				rst = StringUtils.bytesToHexString(tmp);

			} else if (type.toUpperCase().equals(Const.CODE_BINARY)) {
				rst = new String(bf.array(), pos, len, "iso8859_1");
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
		}
		return rst;

	}

	/**
	 * 把byte数组转换存储到java对象中
	 * 
	 * @param pack
	 * @param xmlname
	 *            转换关系文件名
	 * @param packetname
	 *            转换关系packet名
	 * @param rsp
	 * @return
	 * @throws BizServiceException
	 */
	public static OperationResult parse(byte[] pack, String xmlname,
			String packetname, OperationResult rsp) throws BizServiceException {
		logger.debug("parse packet");
		ByteBuffer bf = ByteBuffer.wrap(pack);
		int pos = 0;
		StringBuffer sb = new StringBuffer();

		// bitmap
		String bitmap = "";
		// has bitmap
		boolean hasBitMap = false;

		try {
			// 读取用于转换的xml文件
			Document doc = PacketConfig.getDocument(xmlname);

			// 找到文件中用于转换的<packet>标签，位置存在index中
			int index = 0;
			List packets = doc.selectNodes("/root/packet");
			for (int i = 0; i < packets.size(); i++) {
				index = i;
				if (((Element) packets.get(i)).attributeValue("name").equals(
						packetname)) {
					break;
				}
			}
			// 如果没有找到转换<packet> 抛出异常
			if (index == packets.size()) {
				String err = "can not find the packet [" + packetname
						+ "] in xml file [" + xmlname + "]";
				logger.error(err);
				throw new BizServiceException(err);
			}

			Element packet = (Element) packets.get(index);
			List lst = packet.selectNodes("response/field");
			for (int i = 1; i < lst.size(); i++) {
				Element ele = (Element) lst.get(i);
				// <field>标签中type属性不能为空
				String type = ele.attributeValue("type");
				if (type == null || type.trim().length() == 0) {
					String err = "attribute 'type' of element 'field' can not be empty.";
					logger.error(err);
					throw new BizServiceException(err);
				}

				// <field>标签中length属性不能为空
				String slen = ele.attributeValue("length");
				if (slen == null || slen.trim().length() == 0) {
					String err = "attribute 'length' of element 'field' can not be empty.";
					logger.error(err);
					throw new BizServiceException(err);
				}

				String ref = ele.attributeValue("ref");
				logger.debug("ref=" + ref);

				// 从第二域开始需要根据bitmap判断当前域是否出现在报文中
				if ((i > 4) && hasBitMap) {
					if (bitmap.charAt(Integer.parseInt(ref.substring(5)) - 1) == '0') {
						logger.debug(ref + " is not appeared!");
						continue;
					}
				}

				// 长度域的值
				int lenVal;

				// 长度域占用字节数
				int lenType = 0;

				// 值域占用字节数
				int dataLenByte = 0;

				// 值域长度
				int dataLen;

				// 从<field>标签的length属性中获取值域的长度
				dataLen = Integer.parseInt(slen);

				// 从<field>标签的lenType属性中获取长度域的长度并根据BCD编码格式计算出长度域占用字节数
				String sLenType = ele.attributeValue("lenType");
				if (sLenType != null) {
					lenType = (Integer.parseInt(sLenType) + 1) / 2;
				}

				// 如果有长度域，长度域的值就是值域的长度
				if (lenType != 0) {
					String lenStr = getString(bf, pos, lenType, Const.CODE_BCD);
					logger.debug("length=" + lenStr);
					dataLen = Integer.parseInt(lenStr);
					pos = pos + lenType;
				}
				logger.debug("dataLen=" + dataLen);
				// 计算值域占用字节数
				if (type.toUpperCase().equals(Const.CODE_ASCII)
						|| type.toUpperCase().equals(Const.CODE_BINARY)) {
					// 对于ASCII格式，值域的长度就是值域占用的字节数
					dataLenByte = dataLen;
				} else if (type.toUpperCase().equals(Const.CODE_BCD)) {
					// 对于BCD格式，值域的长度加1再除以2，就是值域占用的字节数
					dataLenByte = (dataLen + 1) / 2;
				}

				String sStr = "";
				if (ref != null) {
					sStr = getString(bf, pos, dataLenByte, type);
					/* BeanUtils.setProperty(resp.getDetailvo(), ref, sStr); */
					logger.debug("(" + i + ") " + sStr);
					// logger.debug("" + sStr.length());

					/*
					 * //格式化str char[] tmp_chars = sStr.toCharArray();
					 * logger.debug("+++++++++" + tmp_chars.length); String str
					 * = ""; for (int j = 0; j < dataLen; j++){ str = str +
					 * Integer.toHexString(tmp_chars[j]); }
					 */

/*					if (lenType != 0) {
						// sb.append(sStr.substring(0, dataLen));
						// BeanUtils.setProperty(rsp.getDetailvo(), ref,
						// sStr.substring(0, dataLen));
						sb.append(sStr);
						BeanUtils.setProperty(rsp.getDetailvo(), ref, sStr);
					} else {*/
						sb.append(sStr);
						BeanUtils.setProperty(rsp.getDetailvo(), ref, sStr);
					//}
				}
				pos = pos + dataLenByte;
				if (ref.equals("bitMap")) {
					bitmap = StringUtils.toBinString(StringUtils
							.toByteArray(sStr));
					hasBitMap = true;
					logger.debug("bitmap=" + bitmap);
				}
			}

			// 获得是否有mac域的标志
			List packetHead = packet.selectNodes("response");
			Element eleHead = (Element) packetHead.get(0);
			String needMac = eleHead.attributeValue("mac");

			// 需要校验mac
			if (!(needMac == null || needMac.trim().length() == 0)) {
				bf.position(11);
				byte[] macText = new byte[pack.length - 19];
				bf.get(macText, 0, macText.length);
				byte[] mac = Crypt.calMAC(macText, Crypt.getMacKey());
				// Log.printBytes(mac, "");

				byte[] macInPacket = new byte[8];
				bf.position(pack.length - 8);
				bf.get(macInPacket, 0, 8);

				for (int i = 0; i < mac.length; i++) {
					if (mac[i] != macInPacket[i]) {
						String err = "check mac err!";
						logger.error(err);
						throw new BizServiceException(err);
					}
				}
			}

		} catch (java.lang.NoSuchFieldError ex) {
			logger.error("field of bean not consist with xml setting");
			throw new BizServiceException("系统繁忙（前后台报文转换）");
		} catch (java.lang.StringIndexOutOfBoundsException ex) {
			logger.error("postion:" + pos + ex.getMessage());
			throw new BizServiceException("系统繁忙（后台返回报文异常）");
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Error in parse recv packet"+ex.getMessage());
			throw new BizServiceException("Error in parse recv packet"
					+ ex.getMessage());
		}
		return rsp;
	}
}

// public static String toBinString (byte[] bytes) {
// StringBuffer buf = new StringBuffer();
// for (int j=0; j< bytes.length; j++){
// for (int i=7; i>=0;i--){
// if (((1 << i) & bytes[j]) != 0){
// buf.append("1");
// }else{
// buf.append("0");
// }
// }
// }
// return buf.toString();
// }

/**
 * 把十六进制的字符串转换为byte[]
 * 
 * @param hexStr
 *            十六进制的字符串
 * @return
 */
// public static byte[] toByteArray(String hexStr) {
// byte[] bytes = null;

// if (hexStr != null) {
// if (hexStr.startsWith("0x") || hexStr.startsWith("0X")) {
/*
 * hexStr = hexStr.substring(2); } int len = hexStr.length();
 * 
 * if (len % 2 != 0) { throw new IllegalStateException("invalid hex string: " +
 * hexStr); }
 * 
 * len = len / 2; bytes = new byte[len]; int idx = 0;
 * 
 * for (int i = 0; i < len; i++) { bytes[i] = (byte) (0xff &
 * Integer.parseInt(hexStr.substring( idx, idx + 2), 16)); idx += 2; } }
 * 
 * return bytes == null ? new byte[0] : bytes; }
 * 
 * private static final String bytesToHexString(byte[] bArray) { StringBuffer sb
 * = new StringBuffer(bArray.length); String sTemp; for (int i = 0; i <
 * bArray.length; i++) { sTemp = Integer.toHexString(0xFF & bArray[i]); if
 * (sTemp.length() < 2) sb.append(0); sb.append(sTemp.toUpperCase()); } return
 * sb.toString(); } }
 */
