package com.huateng.univer.issuer.txnManagement;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.txnManagement.CardRouteDTO;
import com.huateng.framework.exception.BizServiceException;

public interface TxnManagementService {
	/**
	 * 查看卡bin路由信息
	 * @param cardRouteDTO
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inqueryCardRoute(CardRouteDTO cardRouteDTO) throws BizServiceException;
	
	/**
	 * 查找本机构及外系统机构号
	 * @param cardRouteDTO
	 * @return
	 * @throws BizServiceException
	 */
	public List<CardRouteDTO> getIssuerList(CardRouteDTO cardRouteDTO) throws BizServiceException;
	
	/**
	 * 添加
	 * @param cardRouteDTO
	 * @throws BizServiceException
	 */
	public void cardRouteAdd(CardRouteDTO cardRouteDTO) throws BizServiceException;
	/**
	 * 删除
	 * @param cardRouteDTO
	 * @throws BizServiceException
	 */
	public void cardRouteDel(CardRouteDTO cardRouteDTO) throws BizServiceException;
}
