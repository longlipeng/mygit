/**
 * DeflacterProc.java - 2011
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.zip.Deflater;

import com.huateng.framework.util.base64zip.IDeflaterProc;

/**
 * Description: 
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2011
 * 
 */
public class DeflacterProc implements IDeflaterProc {

	/**
	 * 
	 */
	public PipedOutputStream deflater(PipedInputStream inStream, PipedOutputStream outStream)
			throws IOException {
		byte[] inputByte = new byte[inStream.available()];
		// int read_len = inStream.read(inputByte, 0, inStream.available());
		Deflater compresser = new Deflater();
		compresser.setInput(inputByte, 0, inputByte.length);
		byte[] result = new byte[inputByte.length];
		while (!compresser.finished()) {
			// int compressedDataLength =
			compresser.deflate(result);
		}
		compresser.end();
		outStream.write(result, 0, result.length);
		return outStream;
	}

	/**
	 * 
	 */
	public byte[] deflater(byte[] inputByte) throws IOException {
		int compressedDataLength = 0;
		Deflater compresser = new Deflater();
		compresser.setInput(inputByte);
		compresser.finish();
		ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
		byte[] result = new byte[1024];
		try {
			while (!compresser.finished()) {
				compressedDataLength = compresser.deflate(result);
				o.write(result, 0, compressedDataLength);
			}
		} finally {
			o.close();
		}
		// logger.info("compressed data length:"+compressedDataLength);
		compresser.end();

		return o.toByteArray();

	}

}
