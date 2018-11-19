package com.allinfinance.prepay.utils;

public class AccountCode {
	
	
	//账户用途，上汽多用途0001 ，上汽单用途 0002
	public final static String account_category = "0001";
	//	BBB  表示2位顺序号+1位账户类型，顺序号初始设为01,1-个人账户
	public final static String account_personal_type = "011";
	//BBB  表示2位顺序号+1位账户类型，顺序号初始设为01,2-企业账户 
	public final static String account_company_type = "012";
	//BBB  表示2位顺序号+1位账户类型，顺序号初始设为01,3-内部账户 
	public final static String account_private_type = "013";
	//币种 人民币
	public final static String account_currency = "156";	 
}
