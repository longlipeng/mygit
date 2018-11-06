package com.huateng.univer.seller.sellercontract.biz.service;

import java.util.List;

import com.allinfinance.univer.seller.sellercontract.dto.SellerAcctypeContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerProductContractDTO;
import com.huateng.framework.exception.BizServiceException;

public interface SellerProductContractService {

	public List<SellerProductContractDTO> inquery(String sellerContractId)
			throws BizServiceException;

	public SellerProductContractDTO view(
			SellerProductContractDTO sellerProductContractDTO)
			throws BizServiceException;

	public void insert(SellerProductContractDTO sellerProductContractDTO)
			throws BizServiceException;

	public void update(SellerProductContractDTO sellerProductContractDTO)
			throws BizServiceException;

	// 更新营销合同服务明细
	public void updateSellerService(
			SellerAcctypeContractDTO sellerAcctypeContractDTO)
			throws BizServiceException;

	// 查看营销合同服务明细
	public SellerAcctypeContractDTO viewAcctypeContract(
			SellerAcctypeContractDTO sellerAcctypeContractDTO)
			throws BizServiceException;

	public void delete(SellerProductContractDTO sellerProductContractDTO)
			throws BizServiceException;
}
