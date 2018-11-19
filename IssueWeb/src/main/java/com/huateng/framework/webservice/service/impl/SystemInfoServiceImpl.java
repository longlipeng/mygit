package com.huateng.framework.webservice.service.impl;

import java.util.List;
import java.util.Map;

import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.univer.system.sysparam.dto.SystemParameterDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.SystemInfo;
import com.huateng.framework.webservice.service.SystemInfoService;
import com.huateng.framework.webservice.service.WebServiceClientService;

public class SystemInfoServiceImpl implements SystemInfoService {

	/**
	 * 初始化字典数据
	 */
	@SuppressWarnings("unchecked")
	public void initDictInfo() throws BizServiceException {

			OperationCtx operationCtx = new OperationCtx();
			operationCtx.setTxncode("4001000000");
			OperationRequest operationRequest = new OperationRequest();
			OperationResult operationResult = webServiceClientService.process(operationCtx, operationRequest);
			Map dictInfo = (Map) operationResult.getDetailvo();
			SystemInfo.setDictInfo(dictInfo);

	}

	/**
	 * 初始化系统参数
	 */
	@SuppressWarnings("unchecked")
	public void initSystemParameter() throws BizServiceException {

			OperationCtx operationCtx = new OperationCtx();
			operationCtx.setTxncode("4001000002");
			OperationRequest operationRequest = new OperationRequest();
			OperationResult operationResult = webServiceClientService.process(operationCtx, operationRequest);
			List<SystemParameterDTO> systemParameters = (List<SystemParameterDTO>) operationResult.getDetailvo();
			SystemInfo.setParameters(systemParameters);
		
	}

	/**
	 * 初始化实体系统参数
	 */
	
	@SuppressWarnings("unchecked")
	public void initEntityDictInfo() throws BizServiceException{
	
			OperationCtx operationCtx = new OperationCtx();
			operationCtx.setTxncode("4001000010");
			OperationRequest operationRequest = new OperationRequest();
			OperationResult operationResult = webServiceClientService.process(operationCtx, operationRequest);
			Map entityIdDictInfo = (Map) operationResult.getDetailvo();
			SystemInfo.setEntityDictInfo(entityIdDictInfo);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initEntitySystemParameter() throws BizServiceException {
	
			OperationCtx operationCtx = new OperationCtx();
			operationCtx.setTxncode("4001000020");
			OperationRequest operationRequest = new OperationRequest();
			OperationResult operationResult = webServiceClientService.process(operationCtx, operationRequest);
			Map entitySystemParameterMap = (Map) operationResult.getDetailvo();
			SystemInfo.setEntityParameters(entitySystemParameterMap);
		
	}
	
	
	/**
	 * 初始化系统数据（包括系统参数，字典数据）
	 */
	public void initSystemInfo() throws BizServiceException {
		if(SystemInfo.getDictInfo()==null){
			initDictInfo();
		}
		if(SystemInfo.getEntityDictInfo()==null){
			initEntityDictInfo();
		}
		if(SystemInfo.getParameters()==null){
			initSystemParameter();
		}
		if(SystemInfo.getEntityParameters()==null){
			initEntitySystemParameter();
		}
	}

	private WebServiceClientService webServiceClientService;

	public WebServiceClientService getWebServiceClientService() {
		return webServiceClientService;
	}

	public void setWebServiceClientService(WebServiceClientService webServiceClientService) {
		this.webServiceClientService = webServiceClientService;
	}



}
