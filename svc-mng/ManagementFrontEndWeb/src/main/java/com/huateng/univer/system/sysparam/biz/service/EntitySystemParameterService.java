package com.huateng.univer.system.sysparam.biz.service;

import java.util.List;
import java.util.Map;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.pos.dto.PosInfoDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterQueryDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.EntitySystemParameter;

/**
 * 实体系统参数
 * 
 * @author xxl
 * 
 */
public interface EntitySystemParameterService {

	public PageDataDTO inqueryEntitySystemParamter(
			EntitySystemParameterQueryDTO entitySystemParameterQueryDTO)
			throws BizServiceException;

	public void insertEntitySysParam(String entityId, String userId)
			throws BizServiceException;

	public void insertEntitySystemParameter(String entityId,
			String fatherEntityId, String userId) throws BizServiceException;

	public EntitySystemParameterDTO viewEntitySystemParamter(
			EntitySystemParameterDTO entitySystemParameterDTO)
			throws BizServiceException;

	public void updateEntitySystemParamter(
			EntitySystemParameterDTO entitySystemParameterDTO)
			throws BizServiceException;

	public Map<String, List<EntitySystemParameterDTO>> getEntitySystemParameter();

	public String getFatherEntityId(PosInfoDTO posInfoDTO)
			throws BizServiceException;

}
