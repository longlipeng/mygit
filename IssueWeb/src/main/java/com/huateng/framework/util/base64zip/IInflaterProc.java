/**
 * CommFactory.java - 2011
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

import java.io.InputStream;
import java.io.IOException;

/**
 * Description: 
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2011
 * 
 */
public interface IInflaterProc {

	public static final String CHARACTERSET = "UTF-8";
	public abstract InputStream inflater(InputStream inputStream) throws IOException;
	public abstract byte[] inflater(byte[] inputByte) throws IOException;
	public abstract byte[] UnZip(byte[] inputByte) throws IOException;
}
