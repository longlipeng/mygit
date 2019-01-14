package com.allinfinance.prepay.utils;

import org.apache.commons.codec.binary.Hex;

public class MacEcbUtils {
	public static byte[] IV = new byte[8];

	public static byte byteXOR(byte src, byte src1) {
		return (byte) ((src & 0xFF) ^ (src1 & 0xFF));
	}

	public static byte[] bytesXOR(byte[] src, byte[] src1) {
		int length = src.length;
		if (length != src1.length) {
			return null;
		}
		byte[] result = new byte[length];
		for (int i = 0; i < length; i++) {
			result[i] = byteXOR(src[i], src1[i]);
		}
		return result;
	}

	/**
	 * 准备MAC计算数据
	 * @param Input 数据
	 * @return
	 */
	public static String macXOR(byte[] Input) {
		int length = Input.length;
		int x = length % 8;
		int addLen = 0;
		if (x != 0) {
			addLen = 8 - length % 8;
		}
		int pos = 0;
		byte[] data = new byte[length + addLen];
		System.arraycopy(Input, 0, data, 0, length);
		byte[] oper1 = new byte[8];
		System.arraycopy(data, pos, oper1, 0, 8);
		pos += 8;
		for (int i = 1; i < data.length / 8; i++) {
			byte[] oper2 = new byte[8];
			System.arraycopy(data, pos, oper2, 0, 8);
			byte[] t = bytesXOR(oper1, oper2);
			oper1 = t;
			pos += 8;
		}
		String result = Hex.encodeHexString(oper1).toUpperCase();
		//System.out.println("异或结果->" + result);
		return result;
	}

	public static byte[] hexToBytes(String hexString) {
		char[] hex = hexString.toCharArray();
		// 转rawData长度减半
		int length = hex.length / 2;
		byte[] rawData = new byte[length];
		for (int i = 0; i < length; i++) {
			// 先将hex转10进位数值
			int high = Character.digit(hex[i * 2], 16);
			int low = Character.digit(hex[i * 2 + 1], 16);
			// 將第一個值的二進位值左平移4位,ex: 00001000 => 10000000 (8=>128)
			// 然后与第二个值的二进位值作联集ex: 10000000 | 00001100 => 10001100 (137)
			int value = (high << 4) | low;
			// 与FFFFFFFF作补集
			if (value > 127) {
				value -= 256;
			}
			// 最后转回byte就OK
			rawData[i] = (byte) value;
		}
		return rawData;
	}

}