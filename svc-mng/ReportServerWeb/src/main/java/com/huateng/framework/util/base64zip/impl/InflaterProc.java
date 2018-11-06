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
package com.huateng.framework.util.base64zip.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.ZipInputStream;
import org.apache.log4j.Logger;
import com.huateng.framework.util.base64zip.IInflaterProc;

/**
 * Description: 
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2011
 * 
 */
public class InflaterProc implements IInflaterProc {
	private static Logger logger = Logger.getLogger(InflaterProc.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see api.commcommponent.msgprocess.IInflaterProc#inflater()
	 */
	static final int BUFFER = 128;

	public InputStream inflater(InputStream inputStream) throws IOException {
		int compressedDataLength = 0;
		byte[] inputByte = new byte[inputStream.available()];
		int read_len = inputStream.read(inputByte, 0, inputStream.available());
		if (read_len != inputStream.available()) {
			throw new IOException("Inflacter read input stream error!\n");
		} else {
			Inflater decompresser = new Inflater();
			decompresser.setInput(inputByte, 0, inputByte.length);
			byte[] result = new byte[inputByte.length * 10];
			try {
				compressedDataLength = decompresser.inflate(result);
				decompresser.end();
			} catch (DataFormatException ex) {
				logger.error(ex.getMessage());
			}
			if (compressedDataLength > 0) {
				return new ByteArrayInputStream(result, 0, result.length);
			} else {
				throw new IOException("Inflater decompress data error!\n");
			}
		}
	}

	public byte[] inflater(byte[] inputByte) throws IOException {
		int compressedDataLength = 0;
		Inflater compresser = new Inflater(false);
		compresser.setInput(inputByte, 0, inputByte.length);
		ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
		byte[] result = new byte[1024];
		try {
			while (!compresser.finished()) {
				compressedDataLength = compresser.inflate(result);
				if (compressedDataLength == 0) {
					break;
				}
				o.write(result, 0, compressedDataLength);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		} finally {
			o.close();
		}
		compresser.end();
		// logger.info("decompressed data length:"+compressedDataLength);
		return o.toByteArray();
	}

	/**
	 * 
	 * @param inputByte
	 * @return
	 * @throws IOException
	 */
	public byte[] UnZip(byte[] inputByte) throws IOException {

		ByteArrayOutputStream dest = null;
		// BufferedOutputStream out = null;
		ByteArrayInputStream bis = new ByteArrayInputStream(inputByte);
		ZipInputStream zis = new ZipInputStream(bis);
		logger.info("zis available:" + zis.available());
		zis.getNextEntry();
		int count;
		byte data[] = new byte[BUFFER];
		dest = new ByteArrayOutputStream();
		// out = new BufferedOutputStream( dest , BUFFER );

		try {
			while ((count = zis.read(data, 0, BUFFER)) != -1) {
				// out.write(data, 0, count);
				dest.write(data, 0, count);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		} finally {
			dest.close();
		}
		zis.close();
		logger.info("data:\t" + dest.toString());
		return dest.toByteArray();

	}

}
