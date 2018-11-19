package com.huateng.univer.servicefeerule.biz.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.servicefeerule.dto.CaclDspDTO;
import com.allinfinance.univer.servicefeerule.dto.CaclInfDTO;
import com.allinfinance.univer.servicefeerule.dto.CaclInfQueryDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.CaclInf;

public interface CaclInfService {
	public PageDataDTO query(CaclInfQueryDTO dto) throws BizServiceException;

	public CaclDspDTO insert(CaclDspDTO dto) throws BizServiceException;

	public void update(CaclInfQueryDTO dto) throws BizServiceException;

	public CaclInfQueryDTO queryBydiscCd(CaclInfQueryDTO caclInfQueryDTO)
			throws BizServiceException;

	public CaclInfDTO insertInf(CaclInfDTO dto) throws BizServiceException;

	public void delete(CaclInfDTO dto) throws BizServiceException;

	public int getByEntityId(String entityId) throws BizServiceException;

	public String isHave(String discCd) throws BizServiceException;

	public void insertInit(String userId) throws BizServiceException;
}
