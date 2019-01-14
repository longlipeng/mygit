package com.allinfinance.prepay.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class BytesTransUtil {
	
	/**
	 * ��ȡbyte�����е�һ����
	 * @param bytes ��������
	 * @param start ��ʼλ�ôӵ�ǰλ��ʼ
	 * @param length ����
	 * @return 
	 */
	public static byte[] cutByteArray(byte[] bytes, int start, int length){
		if(start+length>bytes.length){
			return null;
		}
		byte[] b = new byte[length];
		for(int i=0;i<length;i++){
			b[i] = bytes[i+start];
		}
		return b;
	}
	
	public static void main(String[] args) {
		String rep = "11111222223333344444123455432112345543211234554321";
		byte[] hex = null;
		try {
			hex = Hex.decodeHex(rep.toCharArray());
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		System.out.println("---------------");
		System.out.println(Hex.encodeHex(BytesTransUtil.cutByteArray(hex, 5, 15), false));
	}

}
