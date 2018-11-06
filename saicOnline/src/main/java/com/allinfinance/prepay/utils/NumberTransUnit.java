package com.allinfinance.prepay.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class NumberTransUnit {

	/**
	 * ����ͷ��Ϣ
	 * @param len ���ĳ���
	 * @return 4λ�����ȵ�HEX
	 */
	public static byte[] msgHead(int len) {
		
		if (len < 0 || len > 65535){
			IndexOutOfBoundsException e= new IndexOutOfBoundsException();
			throw e;
		}
		byte[] head = null;
		String hlen = toHex(len, 4);
		try {

			head = Hex.decodeHex(hlen.toCharArray());
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		return head;
	}

	/**
	 * ������ת����ָ�������ַ���
	 * @param dataLen ����  ex:1
	 * @param len ת�����ַ�������  ex:4
	 * @return ex:0001
	 */
	public static String dataLen(int dataLen, int len) {
		String lenstr = "" + dataLen;
		while (lenstr.length() < len) {
			lenstr = "0" + lenstr;
		}
		return lenstr;
	}
	
	/**
	 * ������תΪָ�����ȵ�16����(ǰ��0)
	 * @param num ����
	 * @param len ����
	 * @return
	 */
	public static String toHex(int num,int len) {
		String hlen = Integer.toHexString(num).toUpperCase();
		while (hlen.length() < len) {
			hlen = "0" + hlen;
		}
		return hlen;
	}
	
	public static void main(String[] args) {
		System.out.println(Hex.encodeHex(NumberTransUnit.msgHead(5), false));
		System.out.println(dataLen(1, 4));
		System.out.println(toHex(128, 2));
		//16����ת10����
		System.out.println(Integer.valueOf("1F", 16));
		
		int modsize = 1408/8+1;
		if(modsize>=128){
			modsize++;
		}
		modsize+=2;
		int pubsize = modsize+"03".length()+2;
		if(pubsize>=128){
			pubsize++;
		}
		pubsize+=2;
		System.out.println(pubsize);
	}

}
