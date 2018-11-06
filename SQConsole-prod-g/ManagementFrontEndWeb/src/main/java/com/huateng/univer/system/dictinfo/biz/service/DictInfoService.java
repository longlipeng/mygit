package com.huateng.univer.system.dictinfo.biz.service;

import java.util.List;
import java.util.Map;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.dictinfo.dto.DictInfoDTO;
import com.allinfinance.univer.system.dictinfo.dto.DictInfoQueryDTO;
import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoDTO;
import com.huateng.framework.exception.BizServiceException;

public interface DictInfoService {

	/**
	 * 用一个map将所有字典表的信息返回给调用者 map的key由字典类型确定,每个key对应一个List
	 * List中存放当前类型的字典信息,类型为DictInfoDTO
	 * 
	 * @return
	 */
	public Map<String, List<DictInfoDTO>> getDictInfo();

	public Map<String, Map<String, List<EntityDictInfoDTO>>> getEntityDictInfo();

	public PageDataDTO inquery(DictInfoQueryDTO dictInfoQueryDTO)
			throws BizServiceException;

	public void add(DictInfoDTO dictInfoDTO) throws BizServiceException;

	public DictInfoDTO view(DictInfoDTO dictInfoDTO) throws BizServiceException;

	public void edit(DictInfoDTO dictInfoDTO) throws BizServiceException;

	public void del(DictInfoDTO dictInfoDTO) throws BizServiceException;

	public void overload() throws BizServiceException;
}
