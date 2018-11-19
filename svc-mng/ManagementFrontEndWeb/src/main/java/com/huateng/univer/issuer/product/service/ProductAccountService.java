package com.huateng.univer.issuer.product.service;

import com.allinfinance.univer.issuer.dto.product.ProdServiceDTO;
import com.huateng.framework.exception.BizServiceException;

public interface ProductAccountService {
	/**
	 * 添加产品服务信息
	 * 
	 * @param prodAcctypeDto
	 * 
	 * @throws BizServiceException
	 */
	public void inserProductAcctype(ProdServiceDTO prodAcctypeDto)
			throws BizServiceException;

	/**
	 * 删除产品服务信息
	 * 
	 * @param prodAcctypeDto
	 * 
	 * @throws BizServiceException
	 */
	public void deleteProductAcctype(ProdServiceDTO prodAcctypeDto)
			throws BizServiceException;

	/**
	 * 查询产品服务信息
	 * 
	 * @param prodAcctypeDto
	 * @return ProdAcctypeDTO
	 * @throws BizServiceException
	 */
	public ProdServiceDTO viewProductAcctype(ProdServiceDTO prodAcctypeDto)
			throws BizServiceException;

	/**
	 * 修改产品服务信息
	 * 
	 * @param prodAcctypeDto
	 * 
	 * @throws BizServiceException
	 */
	public void updateProductAcctype(ProdServiceDTO prodAcctypeDto)
			throws BizServiceException;
}
