package com.huateng.univer.issuer.entityBaseInfo.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

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
import com.huateng.univer.issuer.entityBaseInfo.dao.EntityBaseInfoServiceDao;

public class EntityBaseInfoServiceDaoImpl extends SqlMapClientDaoSupport
		implements EntityBaseInfoServiceDao {

	@SuppressWarnings("unchecked")
	public List<DepartmentDTO> getDepartmentInfo(IssuerDTO issuerDTO)
			throws BizServiceException {
		List<DepartmentDTO> departmentDTOs = (List<DepartmentDTO>) getSqlMapClientTemplate()
				.queryForList("ISSUER.selectDepartment", issuerDTO);
		return departmentDTOs;
	}

	@SuppressWarnings("unchecked")
	public List<InvoiceAddressDTO> getInvoiceAddressInfo(IssuerDTO issuerDTO)
			throws BizServiceException {
		List<InvoiceAddressDTO> addressDTOs = (List<InvoiceAddressDTO>) getSqlMapClientTemplate()
				.queryForList("ISSUER.selectAddress", issuerDTO);
		return addressDTOs;
	}

	@SuppressWarnings("unchecked")
	public List<InvoiceCompanyDTO> getInvoiceCompanyInfo(IssuerDTO issuerDTO)
			throws BizServiceException {
		List<InvoiceCompanyDTO> companyDTOs = (List<InvoiceCompanyDTO>) getSqlMapClientTemplate()
				.queryForList("ISSUER.selectCompany", issuerDTO);
		return companyDTOs;
	}

	@SuppressWarnings("unchecked")
	public List<ContactDTO> getContractInfo(IssuerDTO issuerDTO)
			throws BizServiceException {
		List<ContactDTO> contractDTOs = (List<ContactDTO>) getSqlMapClientTemplate()
				.queryForList("ISSUER.selectContract", issuerDTO);
		return contractDTOs;
	}

	@SuppressWarnings("unchecked")
	public List<DeliveryPointDTO> getDeliveryPointInfo(IssuerDTO issuerDTO)
			throws BizServiceException {
		List<DeliveryPointDTO> deliveryPointDTO = (List<DeliveryPointDTO>) getSqlMapClientTemplate()
				.queryForList("ISSUER.selectDeliveryPoint", issuerDTO);
		return deliveryPointDTO;
	}

	@SuppressWarnings("unchecked")
	public List<BankDTO> getBankInfo(IssuerDTO issuerDTO)
			throws BizServiceException {
		List<BankDTO> BankDTO = (List<BankDTO>) getSqlMapClientTemplate()
				.queryForList("ISSUER.selectBank", issuerDTO);
		return BankDTO;
	}

	@SuppressWarnings("unchecked")
	public List<DeliveryRecipientDTO> getDeliveryReceptInfo(
			DeliveryRecipientDTO dto) throws BizServiceException {
		List<DeliveryRecipientDTO> deliveryPointDTO = (List<DeliveryRecipientDTO>) getSqlMapClientTemplate()
				.queryForList("ISSUER.selectDeliveryRescept", dto);
		return deliveryPointDTO;
	}

	/**
	 * 获得所以的密钥
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<EntitySystemParameterDTO> getEntitySystemParameter(
			EntitySystemParameterDTO dto) throws BizServiceException {
		List<EntitySystemParameterDTO> entitySystemParameterDTO = (List<EntitySystemParameterDTO>) getSqlMapClientTemplate()
				.queryForList(
						"SYSTEMPARAMETER.selectEntitySystemParameterByDTO", dto);
		return entitySystemParameterDTO;
	}

}
