package com.huateng.univer.system.dictinfo.biz.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoDTO;
import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface EntityDictInfoService {

	/**
	 * 用一个map将所有字典表的信息返回给调用者 map的key由字典类型确定,每个key对应一个List
	 * List中存放当前类型的字典信息,类型为EntityDictInfoDTO
	 * 
	 * @return
	 */
	//public Map<String, List<EntityDictInfoDTO>> getEntityDictInfo(
		//	String entityId) throws BizServiceException;

	public PageDataDTO inquery(EntityDictInfoQueryDTO entityDictInfoQueryDTO)
			throws BizServiceException;

	/**
	 * 为每个实体添加一套初始的系统参数
	 */
	public void insertEntityDictInfo(String entityId)
			throws BizServiceException;

	public void insert(EntityDictInfoDTO dictInfoDTO)
			throws BizServiceException;

	public EntityDictInfoDTO view(EntityDictInfoDTO dictInfoDTO)
			throws BizServiceException;

	public void edit(EntityDictInfoDTO dictInfoDTO) throws BizServiceException;

	public void overload() throws BizServiceException;

	public void del(EntityDictInfoDTO dictInfoDTO) throws BizServiceException;

}
