package com.allinfinance.prepay.utils;

/**
 * ������Ź����� 
 * @author king
 *
 */
public class TransNumByteUtil {
	
	/**
	 * �����Ʊ���
	 * @param data ʮ��������(int)
	 * @return ʮ�������ַ���
	 */
	public static String transB(int data){
		String hex;
		hex = Integer.toHexString(data);
		return hex.length()%2==0?hex:"0"+hex;
	}
	
	/**
	 * ѹ�����ֱ���
	 * @param con ��ѹ������  6123567890123
	 * @param size ѹ�����С  8
	 * @return 61 23 56 78 90 12 3F FF
	 */
	public static String transCn(String con, int size){
		size = size*2;
		if(con.length()>size){
			return null;
		}
		while(con.length()<size){
			con+="F";
		}
		return con;
	}
	
	/**
	 * ���ֱ��� 
	 * @param con ��ѹ������ 123456
	 * @param size ѹ�����С  4
	 * @return 00 12 34 56
	 */
	public static String transN(String con, int size){
		size = size*2;
		if(con.length()>size){
			return null;
		}
		while(con.length()<size){
			con="0"+con;
		}
		return con;
	}
	
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
		System.out.println(transB(65537));
		System.out.println(transCn("12342", 3));
		System.out.println(transN("12342", 4));
	}

}
