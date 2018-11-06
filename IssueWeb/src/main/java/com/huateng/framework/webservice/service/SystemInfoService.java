package com.huateng.framework.webservice.service;

import com.huateng.framework.exception.BizServiceException;

/**
 * 系统信息service
 * 
 * @author jianji.dai
 * 
 */

public interface SystemInfoService {

	/**
	 * 初始化字典数据
	 */
	public void initDictInfo() throws BizServiceException;

	public void initEntityDictInfo() throws BizServiceException;
	
	/**
	 * 初始化系统参数
	 */
	public void initSystemParameter() throws BizServiceException;
	
	/**
	 * 初始化实体系统参数
	 */
	public void initEntitySystemParameter() throws BizServiceException;

	/**
	 * 初始化系统数据（包括系统参数，字典数据）
	 */
	public void initSystemInfo() throws BizServiceException;


}
