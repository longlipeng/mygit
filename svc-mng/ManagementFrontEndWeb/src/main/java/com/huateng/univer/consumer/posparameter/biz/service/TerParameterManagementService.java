package com.huateng.univer.consumer.posparameter.biz.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.pos.dto.PosParameterDTO;
import com.allinfinance.univer.consumer.pos.dto.PosParameterQueryDTO;
import com.allinfinance.univer.consumer.pos.dto.posParameterValueDTO;
import com.huateng.framework.exception.BizServiceException;

public interface TerParameterManagementService {

	public List<posParameterValueDTO> queryCardBinList(
			posParameterValueDTO posParameterValueDTO)
			throws BizServiceException;

	public PageDataDTO inquery(PosParameterQueryDTO sysParameterQueryDTO)
			throws BizServiceException;

	public void insert(PosParameterDTO sysParameterDTO)
			throws BizServiceException;

	public PosParameterDTO view(PosParameterDTO sysParameterDTO)
			throws BizServiceException;

	public void update(PosParameterDTO sysParameterDTO)
			throws BizServiceException;

	public void delete(PosParameterDTO sysParameterDTO)
			throws BizServiceException;

	public String commPrm(PosParameterDTO sysParameterDTO)
			throws BizServiceException;

	public void insertCardBin(PosParameterDTO sysParameterDTO)
			throws BizServiceException;

	public void insertIc(PosParameterDTO sysParameterDTO)
			throws BizServiceException;
}
