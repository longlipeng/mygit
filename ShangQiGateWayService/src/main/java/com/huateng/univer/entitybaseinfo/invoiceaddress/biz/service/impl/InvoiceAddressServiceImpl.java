package com.huateng.univer.entitybaseinfo.invoiceaddress.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.EntityInvoiceAddressDAO;
import com.huateng.framework.ibatis.model.EntityInvoiceAddress;
import com.huateng.framework.ibatis.model.EntityInvoiceAddressExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.entitybaseinfo.invoiceaddress.biz.service.InvoiceAddressService;

/**
 * 发票地址service
 * 
 * @author xxl
 * 
 */
public class InvoiceAddressServiceImpl implements InvoiceAddressService {

	Logger logger = Logger.getLogger(this.getClass());
	private CommonsDAO commonsDAO;
	private EntityInvoiceAddressDAO invoiceAddressDAO;

	public List<InvoiceAddressDTO> inquery(String entityId)
			throws BizServiceException {
		try {
			EntityInvoiceAddressExample example = new EntityInvoiceAddressExample();
			example.createCriteria().andEntityIdEqualTo(entityId)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			example.setOrderByClause("default_flag desc");
			List<EntityInvoiceAddress> list = invoiceAddressDAO
					.selectByExample(example);
			List<InvoiceAddressDTO> dtoList = new ArrayList<InvoiceAddressDTO>();

			for (EntityInvoiceAddress e : list) {
				InvoiceAddressDTO dto = new InvoiceAddressDTO();
				ReflectionUtil.copyProperties(e, dto);
				dtoList.add(dto);
			}

			return dtoList;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询发票地址信息失败！");
		}
	}
	
	public void updateByPrimaryKey(InvoiceAddressDTO invoiceAddressDTO)
			throws BizServiceException{
		EntityInvoiceAddress record = new EntityInvoiceAddress();
		ReflectionUtil.copyProperties(invoiceAddressDTO, record);
		invoiceAddressDAO.updateByPrimaryKey(record);
	}
	public void updateByPrimaryKeySelective(InvoiceAddressDTO invoiceAddressDTO)
			throws BizServiceException{
		EntityInvoiceAddress record = new EntityInvoiceAddress();
		ReflectionUtil.copyProperties(invoiceAddressDTO, record);
		invoiceAddressDAO.updateByPrimaryKeySelective(record);
	}

	private void updateDefaultFlag(InvoiceAddressDTO invoiceAddressDTO) {
		if ("1".equals(invoiceAddressDTO.getDefaultFlag())) {
			EntityInvoiceAddressExample example = new EntityInvoiceAddressExample();
			example.createCriteria().andEntityIdEqualTo(
					invoiceAddressDTO.getEntityId()).andDefaultFlagEqualTo("1")
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);

			EntityInvoiceAddress address = new EntityInvoiceAddress();
			address.setDefaultFlag("0");

			invoiceAddressDAO.updateByExampleSelective(address, example);
		}
	}

	public void insert(InvoiceAddressDTO invoiceAddressDTO)
			throws BizServiceException {
		try {

			this.updateDefaultFlag(invoiceAddressDTO);

			EntityInvoiceAddress invoiceAddress = new EntityInvoiceAddress();
			ReflectionUtil.copyProperties(invoiceAddressDTO, invoiceAddress);

			String id = commonsDAO
					.getNextValueOfSequence("TB_ENTITY_INVOICE_ADDRESS");
			invoiceAddress.setInvoiceAddressId(id);
			invoiceAddress.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			invoiceAddress.setCreateTime(DateUtil.getCurrentTime());
			invoiceAddress.setModifyTime(DateUtil.getCurrentTime());

			invoiceAddressDAO.insert(invoiceAddress);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加发票地址信息失败！");
		}

	}

	public void delete(InvoiceAddressDTO invoiceAddressDTO)
			throws BizServiceException {
		try {
			List<InvoiceAddressDTO> dtoList = invoiceAddressDTO
					.getInvoiceAddressDTO();
			List<EntityInvoiceAddress> departmentList = new ArrayList<EntityInvoiceAddress>();
			for (InvoiceAddressDTO dto : dtoList) {
				EntityInvoiceAddress address = new EntityInvoiceAddress();
				ReflectionUtil.copyProperties(dto, address);
				address.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				address.setModifyUser(invoiceAddressDTO.getLoginUserId());
				address.setModifyTime(DateUtil.getCurrentTime());

				departmentList.add(address);

			}
			commonsDAO
					.batchUpdate(
							"TB_ENTITY_INVOICE_ADDRESS.abatorgenerated_updateByPrimaryKeySelective",
							departmentList);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除发票地址失败！");
		}
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public EntityInvoiceAddressDAO getInvoiceAddressDAO() {
		return invoiceAddressDAO;
	}

	public void setInvoiceAddressDAO(EntityInvoiceAddressDAO invoiceAddressDAO) {
		this.invoiceAddressDAO = invoiceAddressDAO;
	}

}
