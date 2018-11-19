package com.huateng.univer.issuer.issuercontract.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyAcctypeContractDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyContractDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyContractQueryDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyProdContractDTO;
import com.huateng.framework.exception.BizServiceException;

public interface LoyaltyContractService {
	/**
	 * 查询发行机构合同信息
	 * 
	 * @param loyaltyContractQueryDTO
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inqueryIssuerContract(
			LoyaltyContractQueryDTO loyaltyContractQueryDTO)
			throws BizServiceException;

	/**
	 * 添加发行机构合同
	 * 
	 * @param loyaltyContractDTO
	 * @return
	 * @throws BizServiceException
	 */

	public LoyaltyContractDTO insertIssuerContract(
			LoyaltyContractDTO loyaltyContractDTO) throws BizServiceException;

	/**
	 * 编辑发行机构合同
	 * 
	 * @param loyaltyContractDTO
	 * @return
	 * @throws BizServiceException
	 */

	public LoyaltyContractDTO editIssuerContract(
			LoyaltyContractDTO loyaltyContractDTO) throws BizServiceException;

	/**
	 * 添加发行机构产品和服务明细
	 * 
	 * @param sellerProductContractDTO
	 * @throws BizServiceException
	 */
	public void insetProductAndService(
			LoyaltyProdContractDTO loyaltyProdContractDTO)
			throws BizServiceException;

	/**
	 * 编辑发行机构产品明细
	 * 
	 * @param sellerProductContractDTO
	 * @throws BizServiceException
	 */
	public void editIssuerContractProduct(
			LoyaltyProdContractDTO loyaltyProdContractDTO)
			throws BizServiceException;

	/**
	 *查看发行机构产品明细
	 * 
	 * @param sellerProductContractDTO
	 * @throws BizServiceException
	 */
	public LoyaltyProdContractDTO viewIssuerContractProduct(
			LoyaltyProdContractDTO loyaltyProdContractDTO)
			throws BizServiceException;

	/**
	 * 编辑发行机构服务明细
	 * 
	 * @param sellerProductContractDTO
	 * @throws BizServiceException
	 */
	public void editIssuerContractService(
			LoyaltyAcctypeContractDTO loyaltyAcctypeContractDTO)
			throws BizServiceException;

	/**
	 * 更新发行机构合同
	 * 
	 * @param sellerProductContractDTO
	 * @throws BizServiceException
	 */
	public void updateIssuerContractService(
			LoyaltyContractDTO loyaltyContractDTO) throws BizServiceException;

}
