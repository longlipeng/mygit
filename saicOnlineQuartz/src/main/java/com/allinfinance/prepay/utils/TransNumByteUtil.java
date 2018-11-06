package com.allinfinance.prepay.utils;

/**
 * 编码符号工具类 
 * @author king
 *
 */
public class TransNumByteUtil {
	
	/**
	 * 二进制编码
	 * @param data 十进制数字(int)
	 * @return 十六进制字符串
	 */
	public static String transB(int data){
		String hex;
		hex = Integer.toHexString(data);
		return hex.length()%2==0?hex:"0"+hex;
	}
	
	/**
	 * 压缩数字编码
	 * @param con 待压缩内容  6123567890123
	 * @param size 压缩后大小  8
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
	 * 数字编码 
	 * @param con 待压缩内容 123456
	 * @param size 压缩后大小  4
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
	 * 截取byte数组中的一部分
	 * @param bytes 完整数据
	 * @param start 开始位置从当前位开始
	 * @param length 长度
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
