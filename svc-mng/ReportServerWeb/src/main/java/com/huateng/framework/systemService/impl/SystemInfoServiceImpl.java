package com.huateng.framework.systemService.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.systemService.DictInfoService;
import com.huateng.framework.systemService.EntitySystemParameterService;
import com.huateng.framework.systemService.SystemInfoService;
import com.huateng.framework.util.SystemInfo;

public class SystemInfoServiceImpl implements SystemInfoService, Runnable {

	private DictInfoService dictInfoService;
	private EntitySystemParameterService entitySystemParameterService;
	Logger logger = Logger.getLogger(SystemInfoServiceImpl.class);

	public void getParameters() {
		Map<String, Map<String, String>> entityParameters = entitySystemParameterService
				.getAllEntitySystemParameter();
		SystemInfo.setEntityParameters(entityParameters);
	}

	public void getDictInfo() {
		Map<String, Map<String, Map<String, String>>> entityDictInfo = dictInfoService
				.getAllDictInfo();
		SystemInfo.setEntityDictInfo(entityDictInfo);
	}

	/**
	 * 初始化系统数据（包括系统参数，字典数据）
	 */
	public void initSystemInfo() throws BizServiceException {
		getParameters();
		getDictInfo();
	}

	public void run() {
		logger.debug("initSystemInfo start ........");

		try {
			logger.debug("  ...........  ");
			initSystemInfo();
			logger.debug("initSystemInfo success");
		} catch (BizServiceException e) {
			logger.debug("initSystemInfo faild"+e.getMessage());
		}

		logger.debug("initSystemInfo end");
	}

	public DictInfoService getDictInfoService() {
		return dictInfoService;
	}

	public void setDictInfoService(DictInfoService dictInfoService) {
		this.dictInfoService = dictInfoService;
	}

	public EntitySystemParameterService getEntitySystemParameterService() {
		return entitySystemParameterService;
	}

	public void setEntitySystemParameterService(
			EntitySystemParameterService entitySystemParameterService) {
		this.entitySystemParameterService = entitySystemParameterService;
	}

}
