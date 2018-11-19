package com.huateng.univer.entitybaseinfo.invoicecompany.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.InvoiceCompanyDAO;
import com.huateng.framework.ibatis.model.InvoiceCompany;
import com.huateng.framework.ibatis.model.InvoiceCompanyExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.entitybaseinfo.invoicecompany.biz.service.InvoiceCompanyService;

/**
 * 发票公司service
 * 
 * @author xxl
 * 
 */
public class InvoiceCompanyServiceImpl implements InvoiceCompanyService {

	Logger logger = Logger.getLogger(this.getClass());
	private CommonsDAO commonsDAO;
	private InvoiceCompanyDAO invoiceCompanyDAO;

	public List<InvoiceCompanyDTO> inquery(String entityId)
			throws BizServiceException {
		try {
			InvoiceCompanyExample example = new InvoiceCompanyExample();
			example.createCriteria().andEntityIdEqualTo(entityId)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			example.setOrderByClause("default_flag desc");
			List<InvoiceCompany> list = invoiceCompanyDAO
					.selectByExample(example);
			List<InvoiceCompanyDTO> dtoList = new ArrayList<InvoiceCompanyDTO>();

			for (InvoiceCompany e : list) {
				InvoiceCompanyDTO dto = new InvoiceCompanyDTO();
				ReflectionUtil.copyProperties(e, dto);
				dtoList.add(dto);
			}

			return dtoList;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询发票公司信息失败！");
		}
	}
	
	public List<InvoiceCompany> selectByEntityId(String entityId)
			throws BizServiceException {
		try {
			InvoiceCompanyExample example = new InvoiceCompanyExample();
			example.createCriteria().andEntityIdEqualTo(entityId)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			example.setOrderByClause("default_flag desc");
			List<InvoiceCompany> list = invoiceCompanyDAO
					.selectByExample(example);

			return list;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询发票公司信息失败！");
		}
	}

	private void updateDefaultFlag(InvoiceCompanyDTO invoiceCompanyDTO) {
		if ("1".equals(invoiceCompanyDTO.getDefaultFlag())) {
			InvoiceCompanyExample example = new InvoiceCompanyExample();
			example.createCriteria().andEntityIdEqualTo(
					invoiceCompanyDTO.getEntityId()).andDefaultFlagEqualTo("1")
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);

			InvoiceCompany invc = new InvoiceCompany();
			invc.setDefaultFlag("0");

			invoiceCompanyDAO.updateByExampleSelective(invc, example);
		}
	}

	public void insert(InvoiceCompanyDTO invoiceCompanyDTO)
			throws BizServiceException {
		try {
			this.updateDefaultFlag(invoiceCompanyDTO);

			InvoiceCompany invoiceCompany = new InvoiceCompany();
			ReflectionUtil.copyProperties(invoiceCompanyDTO, invoiceCompany);

			String id = commonsDAO.getNextValueOfSequence("TB_INVOICE_COMPANY");
			invoiceCompany.setInvoiceCompanyId(id);
			invoiceCompany.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			invoiceCompany.setCreateTime(DateUtil.getCurrentTime());
			invoiceCompany.setModifyTime(DateUtil.getCurrentTime());

			invoiceCompanyDAO.insert(invoiceCompany);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加发票公司信息失败！");
		}
	}

	public void delete(InvoiceCompanyDTO invoiceCompanyDTO)
			throws BizServiceException {
		try {
			List<InvoiceCompanyDTO> dtoList = invoiceCompanyDTO
					.getInvoiceCompanyDTO();
			List<InvoiceCompany> departmentList = new ArrayList<InvoiceCompany>();
			for (InvoiceCompanyDTO dto : dtoList) {
				InvoiceCompany company = new InvoiceCompany();
				ReflectionUtil.copyProperties(dto, company);
				company.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				company.setModifyUser(invoiceCompanyDTO.getLoginUserId());
				company.setModifyTime(DateUtil.getCurrentTime());

				departmentList.add(company);
			}
			commonsDAO
					.batchUpdate(
							"TB_INVOICE_COMPANY.abatorgenerated_updateByPrimaryKeySelective",
							departmentList);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除发票公司失败！");
		}
	}
	
	public void updateByPrimaryKey(InvoiceCompany invoiceCompany)
			throws BizServiceException{
		invoiceCompanyDAO.updateByPrimaryKey(invoiceCompany);
	}
	

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public InvoiceCompanyDAO getInvoiceCompanyDAO() {
		return invoiceCompanyDAO;
	}

	public void setInvoiceCompanyDAO(InvoiceCompanyDAO invoiceCompanyDAO) {
		this.invoiceCompanyDAO = invoiceCompanyDAO;
	}

}
