/**
 * Base64Coder.java - 2011
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
package com.huateng.framework.util.base64zip.impl;

import java.io.IOException;

import com.huateng.framework.util.base64zip.IBase64Coder;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

/**
 * Description: 
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2011
 * 
 */
public class Base64Coder implements IBase64Coder {


	/* (non-Javadoc)
	 * @see api.commcommponent.msgprocess.IBase64Coder#decode(byte[])
	 */
	/**
	 * 解码
	 */
	public byte[] decode(byte[] inputByte) throws IOException {
		

		//String ds = new String( inputByte , "utf-8").trim();
		//return BASE64DecoderStream.decode(ds.getBytes("utf-8"));
		return BASE64DecoderStream.decode(inputByte);
	}

	/* (non-Javadoc)
	 * @see api.commcommponent.msgprocess.IBase64Coder#encode(byte[])
	 */
	/**
	 * 编码
	 */
	public byte[] encode(byte[] inputByte) throws IOException {
		

		//String es = new String( inputByte );
		//return com.sun.mail.util.BASE64EncoderStream.encode(es.getBytes());
		return BASE64EncoderStream.encode(inputByte);
	}

}
