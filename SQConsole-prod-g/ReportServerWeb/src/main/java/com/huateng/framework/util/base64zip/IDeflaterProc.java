/**
 * IDeflaterProc.java - 2011
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
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Description: 
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2011
 * 
 */
public interface IDeflaterProc {

	public static final String CHARACTERSET = "UTF-8";

	public abstract byte[] deflater(byte[] inputByte) throws IOException;
	public abstract PipedOutputStream deflater(PipedInputStream inStream,
			PipedOutputStream outStream) throws IOException;

}
