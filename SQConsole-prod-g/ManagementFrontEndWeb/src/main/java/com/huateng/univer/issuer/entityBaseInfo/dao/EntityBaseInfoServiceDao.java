package com.huateng.univer.issuer.entityBaseInfo.dao;

import java.util.List;

import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterDTO;
import com.huateng.framework.exception.BizServiceException;

public interface EntityBaseInfoServiceDao {
	/**
	 * 获取部门信息
	 * 
	 * @param issuerDTO
	 * @return
	 * @throws BizServiceException
	 */
	public List<DepartmentDTO> getDepartmentInfo(IssuerDTO issuerDTO)
			throws BizServiceException;

	/**
	 * 获取实体发票地址信息列表
	 * 
	 * @param issuerDTO
	 * @return
	 * @throws BizServiceException
	 */
	public List<InvoiceAddressDTO> getInvoiceAddressInfo(IssuerDTO issuerDTO)
			throws BizServiceException;

	/**
	 * 获取发票公司名信息列表
	 * 
	 * @param issuerDTO
	 * @return
	 * @throws BizServiceException
	 */
	public List<InvoiceCompanyDTO> getInvoiceCompanyInfo(IssuerDTO issuerDTO)
			throws BizServiceException;

	/**
	 * 获取联系人信息列表
	 * 
	 * @param issuerDTO
	 * @return
	 * @throws BizServiceException
	 */
	public List<ContactDTO> getContractInfo(IssuerDTO issuerDTO)
			throws BizServiceException;

	/**
	 * 获取快递点信息列表
	 * 
	 * @param issuerDTO
	 * @return
	 * @throws BizServiceException
	 */
	public List<DeliveryPointDTO> getDeliveryPointInfo(IssuerDTO issuerDTO)
			throws BizServiceException;

	/**
	 * 获取快递点联系人信息列表
	 * 
	 * @param issuerDTO
	 * @return
	 * @throws BizServiceException
	 */
	public List<DeliveryRecipientDTO> getDeliveryReceptInfo(
			DeliveryRecipientDTO dto) throws BizServiceException;

	/**
	 * 获取密钥信息列表
	 * 
	 * 
	 */
	public List<EntitySystemParameterDTO> getEntitySystemParameter(
			EntitySystemParameterDTO dto) throws BizServiceException;

	/**
	 * 获取银行信息
	 */
	public List<BankDTO> getBankInfo(IssuerDTO issuerDTO)
			throws BizServiceException;
}
