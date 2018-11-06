package com.allinfinance.prepay.utils;

import java.util.HashMap;

public class ErrorCodeUtil {
	private static HashMap<String, String> errs = new HashMap<String, String>();
	private static HashMap<String, String> errs2 = new HashMap<String, String>();
	private static HashMap<String, String> errs3 = new HashMap<String, String>();
	private static HashMap<String, String> errs4 = new HashMap<String, String>();

	private static void init() {
		errs.put("00", "执行成功!");
		errs.put("01", "确认错误/输入密钥奇偶校验错误警告。");
		errs.put("02", "密钥长度错误(不符合算法要求).");
		errs.put("04", "无效密钥类型错误.");
		errs.put("05", "无效密钥长度标识.");
		errs.put("10", "源密钥奇偶校验错.");
		errs.put("11", "目的密钥奇偶校验错.");
		errs.put("12", "用户存储区域的内容无效。 重启或掉电或重写.");
		errs.put("13", "主密钥奇偶校验错.");
		errs.put("14", "LMK对 02 -03 加密下的 PIN失效.");
		errs.put("15", "无效的输入数据(无效的格式,无效的字符或者没有提供足够的数据).");
		errs.put("16", "控制台或打印机没有准备好或者没有连接.");
		errs.put("17", "HSM不在授权状态,或者能清除PIN输出,或者两种情况都有.");
		errs.put("18", "没有装载文档格式定义.");
		errs.put("19", "指定的Diebold表无效.");
		errs.put("20", "PIN块没有包含效值.");
		errs.put("21", "无效的索引值,或者/块数导致了溢出.");
		errs.put("22", "无效的账号.");
		errs.put("23", "无效的PIN块格式代码.");
		errs.put("24", "PIN长度小于4或大于12.");
		errs.put("25", "十进制表错误.");
		errs.put("26", "无效的密钥方案.");
		errs.put("27", "不匹配的密钥长度.");
		errs.put("28", "无效的密钥类型.");
		errs.put("29", "密钥函数被禁止.");
		errs.put("30", "参考数无效.");
		errs.put("31", "没有足够的请求入口以提供批量处理.");
		errs.put("33", "LMK密钥转换存储区被破坏.");
		errs.put("40", "无效的固件校验和.");
		errs.put("41", "内部的硬件/软件错:RAM已坏,无效的错误代码,等等.");
		errs.put("42", "DES错误.");
		errs.put("46", "超过最大模长.");
		errs.put("47", "DSP错误:报告给管理员.");
		errs.put("49", "私钥错误:报告给管理员.");
		errs.put("60", "无此命令.");
		errs.put("62", "发送报文长度过长");
		errs.put("74", "无效摘要信息语法(仅哈希模式).");
		errs.put("75", "无效公钥/私钥对.");
		errs.put("76", "公钥长度错误.");
		errs.put("77", "明文数据块错误.");
		errs.put("78", "私钥长度错误.");
		errs.put("79", "哈希算法对象标识错误.");
		errs.put("80", "数据长度错误,MAC数据(或其它数据)的长度大于或小指定的长度.");
		errs.put("81", "证书偏移值与长度错.");
		errs.put("82", "索引号不在范围内.");
		errs.put("85", "公钥不存在.");
		errs.put("90", "HSM接收的请求信息中数据奇偶校验错.");
		errs.put("91", "纵向冗余校验(LRC)字符不匹配对输入数据所计算出的值(当HSM接收到一个透明的异步包时)。 .");
		errs.put("92", "计数值(命令/数据域)不在有效范围内,或者不正确.(当 HSMHSMHSM接收到一个透明的异步包时)");
	}
	public static void init2(){
		errs2.put("00", "无错误");
		errs2.put("03", "无效公钥编码类型");
		errs2.put("04", "长度错误");
		errs2.put("05", "无效密钥类型");
		errs2.put("06", "公共指数长度错误");
		errs2.put("08", "所提供的公共指数为偶校验");
		errs2.put("13", "LMK错误:报告给管理员");
		errs2.put("15", "输入数据错");
		errs2.put("17", "不在授权状态");
		errs2.put("47", "DSP错误:报告给管理员");
		errs2.put("62", "发送报文长度过长");
	}
	public static void init3(){
		errs3.put("00", "无错误");
		errs3.put("03", "无效密钥索引");
		errs3.put("04", "私钥存储空间不足");
		errs3.put("13", "LMK错误；报告给管理员");
		errs3.put("15", "输入数据错");
		errs3.put("49", "私钥错误；报告给管理员");
		errs3.put("78", "私钥长度错误");	
		errs3.put("62", "发送报文长度过长");
	}
	
	//arqc
	public static void init4(){
		errs4.put("00", "无错误");
		errs4.put("01", "ARQC/TC/AAC校验失败");
		errs4.put("04", "模式标志错");
		errs4.put("05", "未定义的方案ID");
		errs4.put("10", "MK-AC奇偶校验错");
		errs4.put("12", "用户存储区没有装载密钥");
		errs4.put("13", "私钥长度错误");	
		errs4.put("15", "LMK错误");
		errs4.put("52", "非法的B/H选择");
		errs4.put("80", "数据长度错");	
		errs4.put("81", "PAN长度错");
	}

	public static String getErrString(String err) {
		if (errs.isEmpty())
			init();
		return errs.get(err) == null ? err : errs.get(err);
	}
	/**
	 * 产生制动指数RSA报错
	 * @param err 错误码
	 * @return 错误信息
	 */
	public static String getErrString2(String err) {
		if (errs2.isEmpty())
			init2();
		return errs2.get(err) == null ? err : errs2.get(err);
	}
	
	/**
	 * 装载RSA私钥报错
	 * @param err 错误码
	 * @return 错误信息
	 */
	public static String getErrString3(String err) {
		if (errs3.isEmpty())
			init3();
		return errs3.get(err) == null ? err : errs3.get(err);
	}
	
	
	/**
	 * arqc报错
	 * @param err 错误码
	 * @return 错误信息
	 */
	public static String getErrString4(String err) {
		if (errs4.isEmpty())
			init4();
		return errs4.get(err) == null ? err : errs4.get(err);
	}
	
	public static void main(String[] args) {
		System.out.println(ErrorCodeUtil.getErrString("00"));
	}
}
