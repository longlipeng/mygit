package com.huateng.framework.util;

import java.security.MessageDigest;

/**
 * Enrypt MD5 algorithm
 * 
 * @author runtao.yuan
 * 
 */
public class MD5EncryptAlgorithm {
	
	/**
	 * MD5 Message-Digest Algorithm
	 * 
	 * @param str
	 * @return
	 */
	public final static String md5(String str) {
		try {
			byte[] strtemp = str.getBytes();
			MessageDigest mdtemp = MessageDigest.getInstance("MD5");
			mdtemp.update(strtemp);
			byte[] md = mdtemp.digest();
			return byteToHEX(md);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * convert bytes to hex string
	 * 
	 * @param bytes
	 * @return
	 */
	private static String byteToHEX(byte[] bytes) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f' };

		StringBuffer sb = new StringBuffer();
		char[] ob = new char[2];
		for (int i = 0; i < bytes.length; i++) {
			byte byte0 = bytes[i];
			ob[0] = Digit[(byte0 >>> 4) & 0X0F];
			ob[1] = Digit[byte0 & 0X0F];
			sb.append(ob);
		}
		return sb.toString();
	}
}
