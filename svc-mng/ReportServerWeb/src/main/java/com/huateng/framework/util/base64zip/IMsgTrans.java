/**
 * IMsgTrans.java - 2011
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
public interface IMsgTrans {
	
	public abstract byte[] Deflater_Encoder(byte[] inByte) throws IOException;
	public abstract byte[] Decoder_Inflater(byte[] outByte) throws IOException;
	
}
