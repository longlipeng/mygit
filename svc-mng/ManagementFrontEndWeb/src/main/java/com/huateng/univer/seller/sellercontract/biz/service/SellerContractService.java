package com.huateng.univer.seller.sellercontract.biz.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.seller.dto.SellerQueryDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractQueryDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 营销机构合同service
 * 
 * @author xxl
 * 
 */
public interface SellerContractService {

	public PageDataDTO inquery(SellerContractQueryDTO sellerContractQueryDTO)
			throws BizServiceException;

	public PageDataDTO inqueryProduct(
			SellerContractQueryDTO sellerContractQueryDTO)
			throws BizServiceException;

	public PageDataDTO insertProduct(
			SellerContractQueryDTO sellerContractQueryDTO)
			throws BizServiceException;

	public PageDataDTO inqueryProductForIssuer(
			SellerContractQueryDTO sellerContractQueryDTO)
			throws BizServiceException;

	public PageDataDTO inqueryProductForIssuerContract(
			SellerContractQueryDTO sellerContractQueryDTO)
			throws BizServiceException;

	public SellerContractDTO view(SellerContractDTO sellerContractDTO)
			throws BizServiceException;

	public SellerContractDTO insert(SellerContractDTO sellerContractDTO)
			throws BizServiceException;

	public void update(SellerContractDTO sellerContractDTO)
			throws BizServiceException;

	public void delete(SellerContractDTO sellerContractDTO)
			throws BizServiceException;

	public PageDataDTO insertIssuer(SellerQueryDTO sellerQueryDTO)
			throws BizServiceException;

	public PageDataDTO inqueryMasterPlate(
			SellerContractQueryDTO sellerContractQueryDTO)
			throws BizServiceException;

	public void insertByMasterplate(CustomerDTO customerDTO)
			throws BizServiceException;

	public List<SellerContractDTO> inqueryContract(
			SellerContractQueryDTO sellerContractQueryDTO)
			throws BizServiceException;
}
