/**
 * MsgTransUtil.java - 2010-9-16
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 *
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring Project
 *
 * Modification History:
 * =============================================================================
 *   Author         Date          Description
 *   ------------ ---------- ---------------------------------------------------
 *
 * =============================================================================
 */
package com.huateng.framework.util.base64zip;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.log4j.Logger;

import com.huateng.framework.util.base64zip.impl.Base64Coder;
import com.huateng.framework.util.base64zip.impl.DeflacterProc;
import com.huateng.framework.util.base64zip.impl.InflaterProc;

/**
 * Description: 报文转换工具类
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010.
 * 
 */
public class MsgTransUtil {

	static Logger log = Logger.getLogger(MsgTransUtil.class);

	/**
	 * 解压缩处理器
	 */
	private static final DeflacterProc deflacter = new DeflacterProc();

	/**
	 * 压缩处理器
	 */
	private static final InflaterProc inflater = new InflaterProc();

	/**
	 * BASE64编解码器
	 */
	private static final Base64Coder base64Coder = new Base64Coder();

	public MsgTransUtil() {
	}

	/**
	 * 压缩编码
	 * 
	 * @param inputByte
	 * @return
	 * @throws IOException
	 */
	public static byte[] deflateEncode(byte[] inputByte) throws IOException {
		try {
			if (inputByte == null || inputByte.length == 0) {
				log.error("压缩编码异常:输入不能为空指针!");
			}
			byte[] tmpByte = deflacter.deflater(inputByte);
			return base64Coder.encode(tmpByte);
		} catch (IOException ioex) {
			log.error("压缩编码异常:IO异常!", ioex);
			throw ioex;
		}

	}

	/**
	 * 解码解压缩
	 * 
	 * @param inputByte
	 * @return
	 * @throws IOException
	 */
	public static byte[] decodeInflate(byte[] inputByte) throws IOException {
		try {
			if (inputByte == null || inputByte.length == 0) {
				throw new IOException("解码解压缩异常:输入不能为空!");
			}
			byte[] tmpByte = base64Coder.decode(inputByte);
			return inflater.inflater(tmpByte);
		} catch (IOException ioex) {
			log.error("解码解压缩异常异常:IO异常!", ioex);
			return null;
		}
	}

	/**
	 * 解码通过http传输过的字符串
	 * 
	 * @param inputStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlEncode(String inputStr) throws UnsupportedEncodingException {
		return URLDecoder.decode(inputStr, "UTF-8");
	}

	/**
	 * 对输入的byte[]进行BASE64编码
	 * 
	 * @param inByte
	 * @return
	 * @throws IOException
	 */
	public static byte[] BASE64_ENCODE(byte[] inByte) throws IOException {
		if (null == inByte) {
			return Constants.BLANK_BYTES;
		}
		return base64Coder.encode(inByte);
	}

	/**
	 * 对输入的byte[]进行BASE64解码
	 * 
	 * @param inByte
	 * @return
	 * @throws IOException
	 */
	public static byte[] BASE64_DECODE(byte[] inByte) throws IOException {
		if (null == inByte) {
			return Constants.BLANK_BYTES;
		}
		return base64Coder.decode(inByte);

	}

}
