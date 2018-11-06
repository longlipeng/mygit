package com.allinfinance.prepay.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.EntityInvoiceAddressMapper;
import com.allinfinance.prepay.mapper.svc_mng.InvoiceCompanyMapper;
import com.allinfinance.prepay.model.InvoiceCompany;
import com.allinfinance.prepay.model.InvoiceCompanyExample;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;

@Service
public class InvoiceCompanyServiceImpl implements InvoiceCompanyService {

	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private InvoiceCompanyMapper invoiceCompanyMapper;
	@Override
	public List<InvoiceCompanyDTO> inquery(String entityId)
			throws BizServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(InvoiceCompanyDTO invoiceCompanyDTO)
			throws BizServiceException {
		try {
			this.updateDefaultFlag(invoiceCompanyDTO);

			InvoiceCompany invoiceCompany = new InvoiceCompany();
			ReflectionUtil.copyProperties(invoiceCompanyDTO, invoiceCompany);

			String id = commonsDAO.getNextValueOfSequence("TB_INVOICE_COMPANY");
			invoiceCompany.setInvoiceCompanyId(id);
			invoiceCompany.setDataState("1");
			invoiceCompany.setCreateTime(DateUtil.getCurrentTime());
			invoiceCompany.setModifyTime(DateUtil.getCurrentTime());

			invoiceCompanyMapper.insert(invoiceCompany);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加发票公司信息失败！");
		}

	}

	@Override
	public void updateByPrimaryKey(InvoiceCompany invoiceCompany)
			throws BizServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<InvoiceCompany> selectByEntityId(String entityId)
			throws BizServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void updateDefaultFlag(InvoiceCompanyDTO invoiceCompanyDTO) {
		if ("1".equals(invoiceCompanyDTO.getDefaultFlag())) {
			InvoiceCompanyExample example = new InvoiceCompanyExample();
			example.createCriteria().andEntityIdEqualTo(
					invoiceCompanyDTO.getEntityId()).andDefaultFlagEqualTo("1")
					.andDataStateEqualTo("1");

			InvoiceCompany invc = new InvoiceCompany();
			invc.setDefaultFlag("0");

			invoiceCompanyMapper.updateByExampleSelective(invc, example);
		}
	}

}
