package com.huateng.univer.system.systemParameter.biz.service;

import java.util.List;

import com.allinfinance.univer.system.sysparam.dto.SystemParameterDTO;

/**
 * 系统参数service
 * 
 * 
 */
public interface SystemParameterService {

	/**
	 * 获得所有系统参数
	 * 
	 * @return
	 */
	public List<SystemParameterDTO> getSystemParameter();

}
