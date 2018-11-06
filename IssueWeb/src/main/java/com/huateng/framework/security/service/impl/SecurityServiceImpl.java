package com.huateng.framework.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.huateng.framework.security.service.SecurityService;
import com.huateng.framework.webservice.service.WebServiceClientService;

/**
 * 安全Service
 * 
 * @author liming.feng
 * 
 */
// @Service("securityManager")
public class SecurityServiceImpl implements SecurityService {
	
	private Logger logger = Logger.getLogger(SecurityServiceImpl.class);
	
	/**
	 * 向后台服务取得数据的service
	 */
	private WebServiceClientService webServiceClientService;
	
	/* (non-Javadoc)
	 * @see com.huateng.framework.security.service.SecurityService#loadUrlAuthorities()
	 */
	@SuppressWarnings("unchecked")
	public List<ResourceDTO> loadUrlAuthorities() {
		
		List<ResourceDTO> urlAuthorities = new ArrayList<ResourceDTO>();
		
		/** 取得所有受保护的url及其允许访问的角色 */
		try {
//			OperationCtx operationCtx = new OperationCtx();
//			operationCtx.setTxncode(ConstCode.ROLE_SERVICE_GET_ALL_RESOURCE);
//			OperationRequest operationRequest = new OperationRequest();
//
//			OperationResult operationResult = webServiceClientService.process(operationCtx,
//					operationRequest);
//			urlAuthorities =(List<ResourceDTO>)operationResult.getDetailvo();
		
		} catch(Exception e) {
			this.logger.error(e.getMessage());
		}
		return urlAuthorities;
	}

	/**
	 * @return
	 */
	public WebServiceClientService getWebServiceClientService() {
		return webServiceClientService;
	}

	/**
	 * @param webServiceClientService
	 */
	public void setWebServiceClientService(
			WebServiceClientService webServiceClientService) {
		this.webServiceClientService = webServiceClientService;
	}
}