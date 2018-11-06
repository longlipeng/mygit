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
	 * ׼��MAC��������
	 * @param Input ����
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
		//System.out.println("�����->" + result);
		return result;
	}

	public static byte[] hexToBytes(String hexString) {
		char[] hex = hexString.toCharArray();
		// תrawData���ȼ���
		int length = hex.length / 2;
		byte[] rawData = new byte[length];
		for (int i = 0; i < length; i++) {
			// �Ƚ�hexת10��λ��ֵ
			int high = Character.digit(hex[i * 2], 16);
			int low = Character.digit(hex[i * 2 + 1], 16);
			// ����һ��ֵ�Ķ��Mλֵ��ƽ��4λ,ex: 00001000 => 10000000 (8=>128)
			// Ȼ����ڶ���ֵ�Ķ���λֵ������ex: 10000000 | 00001100 => 10001100 (137)
			int value = (high << 4) | low;
			// ��FFFFFFFF������
			if (value > 127) {
				value -= 256;
			}
			// ���ת��byte��OK
			rawData[i] = (byte) value;
		}
		return rawData;
	}

}