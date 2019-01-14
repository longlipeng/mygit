package com.huateng.framework.webservice.service.impl;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.webservice.ManageService;
import com.huateng.framework.webservice.service.WebServiceClientService;
import com.huateng.framework.util.XstreamDateConverter;
import com.thoughtworks.xstream.XStream;


/**
 * <p>
 * Title: Accor
 * </p>
 * 
 * <p>
 * Description:Accor Project 1nd Edition
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author YY
 * @version 1.0
 */

public class WebServiceClientServiceImpl implements WebServiceClientService {
	Logger logger = Logger.getLogger(WebServiceClientServiceImpl.class);
	protected static final String NEED = "1";
	protected static final String NOTNEED = "0";
	protected String needPwdCheck;
	protected String needRecord;

	private ManageService manageService;

	public ManageService getManageService() {
		return manageService;
	}

	public void setManageService(ManageService manageService) {
		this.manageService = manageService;
	}

	/**
	 * 
	 */
	public OperationResult process(OperationCtx ctx, OperationRequest req)
			throws BizServiceException {
		String strCtx = getReqXmlStr(ctx);
		String strReq = getReqXmlStr(req);

		OperationResult operationResult = new OperationResult();
		try {
			String strRsp = manageService.sendServece(strCtx, strReq);
			operationResult = getRspToXml(strRsp);
		} catch (Exception e) {
			throw new BizServiceException("无法连接到后台服务器");
		}
		return operationResult;
	}

	/**
	 * 获得一个用户请求对象的xml字符�?
	 * 
	 * @param obj
	 * @return
	 */
	public String getReqXmlStr(Object obj) {

		XStream smReq = new XStream();
		smReq.registerConverter(new XstreamDateConverter());
		smReq.aliasPackage("REQ", "com.huateng.");
		smReq.registerConverter(new XstreamDateConverter());
		return smReq.toXML(obj);
	}
	/**
	 * 将一个xml字符串转换为OperationResult对象
	 * @param strRsp
	 * @return
	 */
	public OperationResult getRspToXml(String strRsp) {
		OperationResult operationResult = null;
		
		try {
			XStream smRsp = new XStream();
			smRsp.registerConverter(new XstreamDateConverter());
			smRsp.alias("OperationResult", OperationResult.class);
			smRsp.aliasPackage("RSP", "com.huateng.");
			operationResult = (OperationResult) smRsp
					.fromXML(strRsp);
		} catch (Exception e)  {
			this.logger.error(e.getMessage());
		}
		return operationResult;
	}

}
