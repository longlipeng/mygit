package com.huateng.univer.system.sysparam.biz.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.sysparam.dto.SystemParameterDTO;
import com.allinfinance.univer.system.sysparam.dto.SystemParameterQueryDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 系统参数service
 */
public interface SystemParameterService {

	public List<SystemParameterDTO> getSystemParameter()
			throws BizServiceException;

	public PageDataDTO inquerySystemParamter(
			SystemParameterQueryDTO systemParameterQueryDTO)
			throws BizServiceException;

	public SystemParameterDTO viewSystemParamter(
			SystemParameterDTO systemParameterDTO) throws BizServiceException;

	public void updateSystemParamter(SystemParameterDTO systemParameterDTO)
			throws BizServiceException;

}
