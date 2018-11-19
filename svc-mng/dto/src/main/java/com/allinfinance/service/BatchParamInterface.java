package com.allinfinance.service;

/**
 * 用来设置或者获取批量文件的相关字段信息
 * @author flypal
 *
 */
public interface BatchParamInterface {
	
	/**
	 * 用来获取DTO中所反应的交易金额，如果DTO中没有金额，则返回0
	 * @return
	 */
	String calcAmt();
	
	/**
	 * 设置流水号字段，流水号为string
	 */
	void setSeq(String seq);
	
	/**
	 * 获取存放的流水号字段
	 */
	String fetchSeq();

}
