package com.allinfinance.prepay.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.EntityInvoiceAddressMapper;
import com.allinfinance.prepay.model.EntityInvoiceAddress;
import com.allinfinance.prepay.model.EntityInvoiceAddressExample;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;

@Service
public class InvoiceAddressServiceImpl implements InvoiceAddressService {

	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private EntityInvoiceAddressMapper invoiceAddressMapper;
	@Override
	public List<InvoiceAddressDTO> inquery(String entityId)
			throws BizServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(InvoiceAddressDTO invoiceAddressDTO)
			throws BizServiceException {
		try {

			this.updateDefaultFlag(invoiceAddressDTO);

			EntityInvoiceAddress invoiceAddress = new EntityInvoiceAddress();
			ReflectionUtil.copyProperties(invoiceAddressDTO, invoiceAddress);

			String id = commonsDAO
					.getNextValueOfSequence("TB_ENTITY_INVOICE_ADDRESS");
			invoiceAddress.setInvoiceAddressId(id);
			invoiceAddress.setDataState("1");
			invoiceAddress.setCreateTime(DateUtil.getCurrentTime());
			invoiceAddress.setModifyTime(DateUtil.getCurrentTime());

			invoiceAddressMapper.insert(invoiceAddress);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加发票地址信息失败！");
		}

	}

	@Override
	public void updateByPrimaryKey(InvoiceAddressDTO invoiceAddressDTO)
			throws BizServiceException {
		try {
			EntityInvoiceAddress record = new EntityInvoiceAddress();
			EntityInvoiceAddressExample ex=new EntityInvoiceAddressExample();
			ex.createCriteria().andInvoiceAddressIdEqualTo(invoiceAddressDTO.getInvoiceAddressId());
			ReflectionUtil.copyProperties(invoiceAddressDTO, record);
			invoiceAddressMapper.updateByExample(record, ex);
		} catch (Exception e) {
			
		}

	}
	
	private void updateDefaultFlag(InvoiceAddressDTO invoiceAddressDTO) {
		if ("1".equals(invoiceAddressDTO.getDefaultFlag())) {
			EntityInvoiceAddressExample example = new EntityInvoiceAddressExample();
			example.createCriteria().andEntityIdEqualTo(
					invoiceAddressDTO.getEntityId()).andDefaultFlagEqualTo("1")
					.andDataStateEqualTo("1");

			EntityInvoiceAddress address = new EntityInvoiceAddress();
			address.setDefaultFlag("0");

			invoiceAddressMapper.updateByExampleSelective(address, example);
		}
	}

}
