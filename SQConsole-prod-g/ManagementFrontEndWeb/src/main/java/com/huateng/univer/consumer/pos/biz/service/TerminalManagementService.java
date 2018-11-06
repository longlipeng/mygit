package com.huateng.univer.consumer.pos.biz.service;

import java.util.Map;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.outerPos.OuterPosInfoDTO;
import com.allinfinance.univer.consumer.pos.dto.PosBrandInfoDTO;
import com.allinfinance.univer.consumer.pos.dto.PosBrandInfoQueryDTO;
import com.allinfinance.univer.consumer.pos.dto.PosInfoDTO;
import com.allinfinance.univer.consumer.pos.dto.PosInfoQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface TerminalManagementService {
	public PageDataDTO inquery(PosInfoQueryDTO posInfoQueryDTO)
			throws BizServiceException;

	public Map selectPrmVersion(PosInfoDTO posInfDTO)
			throws BizServiceException;

	public PageDataDTO inqueryPosBrand(PosBrandInfoQueryDTO posBrandInfQueryDTO)
			throws BizServiceException;

	public void addPosBrand(PosBrandInfoDTO posBrandInfDTO)
			throws BizServiceException;

	public PosBrandInfoDTO loadPosBrand(PosBrandInfoDTO posBrandInfDTO)
			throws BizServiceException;

	public void updatePosBrand(PosBrandInfoDTO posBrandInfDTO)
			throws BizServiceException;

	public void insertposInf(PosInfoDTO posInfDTO) throws BizServiceException;

	public PosInfoDTO viewposInf(PosInfoDTO posInfDTO)
			throws BizServiceException;

	public void modifyposInf(PosInfoDTO posInfDTO) throws BizServiceException;

	public void deleteposInf(PosInfoDTO posInfDTO) throws BizServiceException;

	public void insertOuterPosInf(OuterPosInfoDTO outerPosInfoDTO)
			throws BizServiceException;

	public OuterPosInfoDTO viewOuterposInf(OuterPosInfoDTO outerPosInfoDTO)
			throws BizServiceException;

	public void modifyOuterposInf(OuterPosInfoDTO outerPosInfoDTO)
			throws BizServiceException;

	public void deleteOuterposInf(OuterPosInfoDTO outerPosInfoDTO)
			throws BizServiceException;

	public PageDataDTO inqueryPos(PosInfoQueryDTO posInfoQueryDTO)
			throws BizServiceException;

	public PageDataDTO inqueryOuterpos(PosInfoQueryDTO posInfoQueryDTO)
			throws BizServiceException;
}
