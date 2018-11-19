/**
 * IBase64Coder.java - 2011
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

/**
 * Description: 
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2011
 * 
 */
public interface IBase64Coder {
	
	public static final String ENCODEINGS = "base64";
	public static final String DECODINGS = "base64";
	
	//decode
	public abstract byte[] decode(byte[] inputByte) throws IOException;
	//encode
	public abstract byte[] encode(byte[] inputByte) throws IOException;
	
}
