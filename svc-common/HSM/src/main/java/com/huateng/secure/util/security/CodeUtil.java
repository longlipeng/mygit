/*
 * @(#)CodeUtil.java V0.1
 * 
 * Project: cupsecure
 * 
 * Modify Information:
 * =============================================================================
 * Author Date Description ------------ ----------
 * --------------------------------------------------- Jack 2005-11-4
 * 
 * Copyright Notice:
 * =============================================================================
 * Copyright (c) 2001-2005 Beijing HiTRUST Technology Co., Ltd. 1808 Room,
 * Science & Technology Building, No. 9 South Street, Zhong Guan Cun, Haidian
 * District, Beijing ,100081, China All rights reserved.
 * 
 * This software is the confidential and proprietary information of Beijing
 * HiTRUST Technology Co., Ltd. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with HiTRUST.
 * 
 * Warning:
 * =============================================================================
 */

package com.huateng.secure.util.security;

import com.huateng.secure.util.exception.SecException;

/**
 * 
 * <p>
 * <strong> This class name was CodeUtil </strong>
 * </p>
 * 
 * @author Lay
 * @date 2010-5-11 03:48:03
 * @version 1.0
 */
public class CodeUtil {
	public CodeUtil() {
		super();
	}

	/**
	 * pin--->pinBlock �������ʺ�??
	 * 
	 * @param aPin
	 * @return
	 * @throws CUPSException
	 */
	public synchronized static byte[] pin2PinBlock(String aPin)
			throws SecException {
		int tTemp = 1;

		int tPinLen = aPin.length();
		if (tPinLen > 12 || tPinLen < 4) {
			throw new SecException("Fail Code" + SecException.ERR_SEC_CODE_7000);
		}
		byte[] tByte = new byte[8];
		try {
			if (tPinLen > 9) {
				tByte[0] = (byte) Integer.parseInt(new Integer(tPinLen)
						.toString(), 10);
			}
			else {
				tByte[0] = (byte) Integer.parseInt(new Integer(tPinLen)
						.toString(), 16);
			}
			if (tPinLen % 2 == 0) {
				for (int i = 0; i < tPinLen;) {
					String a = aPin.substring(i, i + 2);
					tByte[tTemp] = (byte) Integer.parseInt(a, 16);
					if (i == (tPinLen - 2)) {
						if (tTemp < 7) {
							for (int x = (tTemp + 1); x < 8; x++) {
								tByte[x] = (byte) 0xff;
							}
						}
					}
					tTemp++;
					i = i + 2;
				}
			}
			else {
				for (int i = 0; i < tPinLen - 1;) {
					String a;
					a = aPin.substring(i, i + 2);
					tByte[tTemp] = (byte) Integer.parseInt(a, 16);
					if (i == (tPinLen - 3)) {
						String b = aPin.substring(tPinLen - 1) + "F";
						tByte[tTemp + 1] = (byte) Integer.parseInt(b, 16);
						if ((tTemp + 1) < 7) {
							for (int x = (tTemp + 2); x < 8; x++) {
								tByte[x] = (byte) 0xff;
							}
						}
					}
					tTemp++;
					i = i + 2;
				}
			}
		} catch (Exception e) {
			// LogWriter.error(SecException.CS_EXC_MSG_8003+e.getMessage());
			throw new SecException(SecException.ERR_SEC_CODE_7001
					+ e.getMessage());
		}
		return tByte;
	}

	/**
	 * Pin--->PinBlock, �����ʺ�??
	 * 
	 * @param pin
	 * @param aCardNO
	 * @return
	 * @throws SecException
	 */
	public synchronized static byte[] pin2PinBlockWithCardNO(String aPin,
			String aCardNO) throws SecException {
		byte[] tPinByte = CodeUtil.pin2PinBlock(aPin);
		if (aCardNO.length() == 11) {
			aCardNO = "00" + aCardNO;
		}
		else if (aCardNO.length() == 12) {
			aCardNO = "0" + aCardNO;
		}
		byte[] tPanByte = CodeUtil.formatPan(aCardNO);
		byte[] tByte = new byte[8];
		for (int i = 0; i < 8; i++) {
			tByte[i] = (byte) (tPinByte[i] ^ tPanByte[i]);
		}
		return tByte;
	}

	/**
	 * PinBlock--->Pin, �����ʺ�
	 * 
	 * @param pin
	 * @param aCardNO
	 * @return
	 * @throws SecException
	 */
	public synchronized static byte[] pinBlock2PinWithCardNO(byte[] tPinByte,
			String aCardNO) throws SecException {

		if (aCardNO.length() == 11) {
			aCardNO = "00" + aCardNO;
		}
		else if (aCardNO.length() == 12) {
			aCardNO = "0" + aCardNO;
		}
		byte[] tPanByte = CodeUtil.formatPan(aCardNO);
		byte[] tByte = new byte[8];
		for (int i = 0; i < 8; i++) {
			tByte[i] = (byte) (tPinByte[i] ^ tPanByte[i]);
		}
		return tByte;
	}

	/**
	 * Pin--->PinBlock, �����ʺ�??
	 * 
	 * @param pin
	 * @param aCardNO
	 * @return
	 * @throws SecException
	 */
	public synchronized static byte[] PinBlock2PinWithCardNO(byte[] aPinBlock,
			String aCardNO) throws SecException {
		// byte[] tPinByte = CodeUtil.pin2PinBlock(aPin);
		if (aCardNO.length() == 11) {
			aCardNO = "00" + aCardNO;
		}
		else if (aCardNO.length() == 12) {
			aCardNO = "0" + aCardNO;
		}
		byte[] tPanByte = CodeUtil.formatPan(aCardNO);
		byte[] tByte = new byte[8];
		for (int i = 0; i < 8; i++) {
			tByte[i] = (byte) (aPinBlock[i] ^ tPanByte[i]);
		}
		return tByte;
	}

	/**
	 * ������֧��Pin-->PinBlock??
	 * 
	 * @param aPin
	 * @return
	 * @throws SecException
	 */
	public synchronized static byte[] inetPwd2Block(String aPin)
			throws SecException {
		int tPinLen = aPin.length();
		if (tPinLen > 20 || tPinLen < 6) {
			throw new SecException("Fail Code "
					+ SecException.ERR_SEC_CODE_7000);
		}
		byte[] tByte = new byte[24];
		try {
			if (tPinLen < 10) {
				tByte[0] = (byte) 48;
				tByte[1] = (byte) (48 + tPinLen);
			}
			else if (tPinLen < 20) {
				tByte[0] = (byte) 49;
				tByte[1] = (byte) (48 + tPinLen - 10);
			}
			else if (tPinLen == 20) {
				tByte[0] = (byte) 50;
				tByte[1] = (byte) 48;
			}
			for (int i = 2; i < (tPinLen + 2); i++) {
				tByte[i] = (byte) new Character(aPin.charAt(i - 2)).hashCode();
				if (i == (tPinLen + 1)) {
					if (i < 23) {
						int temp = tPinLen + 2;
						for (temp = tPinLen + 2; temp < 24; temp++) {
							tByte[temp] = (byte) 0xff;
						}
					}
				}
			}
		} catch (Exception e) {
			// LogWriter.error(SecException.CS_EXC_MSG_8003+e.getMessage());
			throw new SecException(SecException.ERR_SEC_CODE_7001
					+ e.getMessage());
		}
		return tByte;
	}

	/**
	 * 
	 * @param aIndex
	 * @return
	 */
	public synchronized static String formatIndex(int aIndex) {
		String tString = null;
		if (aIndex >= 0 && aIndex < 10) {
			tString = "00" + new Integer(aIndex).toString();
		}
		else if (aIndex >= 10 && aIndex < 100) {
			tString = "0" + new Integer(aIndex).toString();
		}
		else if (aIndex >= 100 && aIndex <= 1000) {
			tString = new Integer(aIndex).toString();
		}
		else {
			return null;
		}
		return tString;
	}

	/**
	 * 
	 * @param aLen
	 * @return String
	 */
	public synchronized static String formatLength(int aLen) {
		String tString = null;
		if (aLen >= 0 && aLen < 10) {
			tString = "000" + new Integer(aLen).toString();
		}
		else if (aLen >= 10 && aLen < 100) {
			tString = "00" + new Integer(aLen).toString();
		}
		else if (aLen >= 100 && aLen <= 1000) {
			tString = "0" + new Integer(aLen).toString();
		}
		else if (aLen >= 1000 && aLen <= 10000) {
			tString = new Integer(aLen).toString();
		}
		else {
			return null;
		}
		return tString;
	}

	/**
	 * 
	 * @param aLen
	 * @return String
	 */
	public synchronized static String formatLength(int aLen, int fmtlen) {
		String aLenString = String.valueOf(aLen);
		if (aLenString.length() > fmtlen) {
			return aLenString;
		}
		String tString = "";
		for (int i = 0; i < fmtlen - aLenString.length(); i++) {
			tString = tString + "0";
		}

		return tString + aLenString;
	}

	/**
	 * 
	 * @param aPan
	 * @return
	 * @throws SecException
	 */
	private synchronized static byte[] formatPan(String aPan)
			throws SecException {
		int tPanLen = aPan.length();
		byte[] tByte = new byte[8];
		if (tPanLen < 13 || tPanLen > 19) {
			// LogWriter.error(SecException.CS_EXC_MSG_8000);
			throw new SecException("Fail Code" + SecException.ERR_SEC_CODE_7000);
		}
		int temp = tPanLen - 13;
		try {
			tByte[0] = (byte) 0x00;
			tByte[1] = (byte) 0x00;
			for (int i = 2; i < 8; i++) {
				String a = aPan.substring(temp, temp + 2);
				tByte[i] = (byte) Integer.parseInt(a, 16);
				temp = temp + 2;
			}
		} catch (Exception e) {
			// LogWriter.error(SecException.CS_EXC_MSG_8003+e.getMessage());
			throw new SecException(SecException.ERR_SEC_CODE_7001
					+ e.getMessage());
		}
		return tByte;
	}

	/**
	 * ����������ʮ���������תΪbyte[]����
	 * 
	 * @param aByte
	 * @return
	 */
	public synchronized static byte[] Byte2byte(byte[] aByte) {
		String tStr = "";
		byte[] tByte = new byte[24];
		int tIndex = 0;
		for (int i = 0; i < aByte.length; i++) {
			tStr = tStr + Character.toString((char) aByte[i]);
		}
		for (int m = 0; m < (tStr.length() - 1);) {
			String temp = tStr.substring(m, m + 2);
			tByte[tIndex] = (byte) Integer.parseInt(temp, 16);
			m = m + 2;
			tIndex = tIndex + 1;
		}
		return tByte;
	}

	/**
	 * �������ʮ���������תΪbyte[]����
	 * 
	 * @param aByte
	 * @return
	 */
	public synchronized static byte[] Byte2byteWithCardNO(byte[] aByte) {
		String tStr = "";
		byte[] tByte = new byte[8];
		int tIndex = 0;
		for (int i = 0; i < 16; i++) {
			tStr = tStr + Character.toString((char) aByte[i]);
		}
		for (int m = 0; m < 15;) {
			String temp = tStr.substring(m, m + 2);
			tByte[tIndex] = (byte) Integer.parseInt(temp, 16);
			m = m + 2;
			tIndex = tIndex + 1;
		}
		return tByte;
	}

	public static int formatDataLen(byte[] aByte) {
		String tStr = new String(aByte);
		for (; tStr.charAt(0) != '0';) {
			tStr = tStr.substring(1, tStr.length());
		}
		return Integer.parseInt(tStr);
	}

	public static byte[] formatData(byte[] aByte) {
		byte[] tByte = new byte[128];
		System.arraycopy(aByte, 0, tByte, 0, aByte.length);

		return tByte;

	}

	/**
	 * @param b
	 *            source byte array
	 * @param offset
	 *            starting offset
	 * @param len
	 *            number of bytes in destination (processes len*2)
	 * @return byte[len]
	 */
	public static byte[] hex2byte(byte[] b, int offset, int len) {
		byte[] d = new byte[len];
		for (int i = 0; i < len * 2; i++) {
			int shift = i % 2 == 1 ? 0 : 4;
			d[i >> 1] |= Character.digit((char) b[offset + i], 16) << shift;
		}
		return d;
	}

	/**
	 * @param s
	 *            source string (with Hex representation)
	 * @return byte array
	 */
	public static byte[] hex2byte(String s) {
		return hex2byte(s.getBytes(), 0, s.length() >> 1);
	}

	public static String byte2hex(byte[] b) // ������ת�ַ�
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	public static boolean Str2Hex(byte in[], byte out[], int len) {
		byte asciiCode[] = { 10, 11, 12, 13, 14, 15 };
		if (len > in.length)
			return false;
		if (len % 2 != 0)
			return false;
		byte temp[] = new byte[len];
		for (int i = 0; i < len; i++)
			if (in[i] >= 48 && in[i] <= 57)
				temp[i] = (byte) (in[i] - 48);
			else if (in[i] >= 65 && in[i] <= 70)
				temp[i] = asciiCode[in[i] - 65];
			else if (in[i] >= 97 && in[i] <= 102)
				temp[i] = asciiCode[in[i] - 97];
			else
				return false;

		for (int i = 0; i < len / 2; i++)
			out[i] = (byte) (temp[2 * i] * 16 + temp[2 * i + 1]);

		return true;
	}

	public static boolean Hex2Str(byte in[], byte out[], int len) {
		byte asciiCode[] = { 65, 66, 67, 68, 69, 70 };
		if (len > in.length)
			return false;
		byte temp[] = new byte[2 * len];
		for (int i = 0; i < len; i++) {
			temp[2 * i] = (byte) ((in[i] & 0xf0) / 16);
			temp[2 * i + 1] = (byte) (in[i] & 0xf);
		}

		for (int i = 0; i < 2 * len; i++)
			if (temp[i] <= 9 && temp[i] >= 0)
				out[i] = (byte) (temp[i] + 48);
			else
				out[i] = asciiCode[temp[i] - 10];

		return true;
	}
}
