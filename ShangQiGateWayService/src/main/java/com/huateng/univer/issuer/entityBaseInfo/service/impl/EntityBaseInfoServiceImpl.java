package com.huateng.univer.issuer.entityBaseInfo.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.DeliveryContactDAO;
import com.huateng.framework.ibatis.dao.EntityContactDAO;
import com.huateng.framework.ibatis.dao.EntityDeliveryDAO;
import com.huateng.framework.ibatis.dao.EntityDepartmentDAO;
import com.huateng.framework.ibatis.dao.EntityInvoiceAddressDAO;
import com.huateng.framework.ibatis.dao.InvoiceCompanyDAO;
import com.huateng.framework.ibatis.model.DeliveryContact;
import com.huateng.framework.ibatis.model.DeliveryContactExample;
import com.huateng.framework.ibatis.model.EntityContact;
import com.huateng.framework.ibatis.model.EntityContactExample;
import com.huateng.framework.ibatis.model.EntityDelivery;
import com.huateng.framework.ibatis.model.EntityDeliveryExample;
import com.huateng.framework.ibatis.model.EntityDepartment;
import com.huateng.framework.ibatis.model.EntityDepartmentExample;
import com.huateng.framework.ibatis.model.EntityInvoiceAddress;
import com.huateng.framework.ibatis.model.EntityInvoiceAddressExample;
import com.huateng.framework.ibatis.model.InvoiceCompany;
import com.huateng.framework.ibatis.model.InvoiceCompanyExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.issuer.entityBaseInfo.service.EntityBaseInfoService;

public class EntityBaseInfoServiceImpl implements EntityBaseInfoService {

	private EntityDepartmentDAO departmentDAO;

	private InvoiceCompanyDAO invoiceCompanyDAO;

	private EntityInvoiceAddressDAO invoiceAddressDAO;

	private EntityDeliveryDAO deliveryDAO;

	private DeliveryContactDAO deliveryContractDAO;

	private EntityContactDAO contractDAO;

	private CommonsDAO commonsDAO;

	public DeliveryContactDAO getDeliveryContractDAO() {
		return deliveryContractDAO;
	}

	public void setDeliveryContractDAO(DeliveryContactDAO deliveryContractDAO) {
		this.deliveryContractDAO = deliveryContractDAO;
	}

	public EntityContactDAO getContractDAO() {
		return contractDAO;
	}

	public void setContractDAO(EntityContactDAO contractDAO) {
		this.contractDAO = contractDAO;
	}

	public EntityDeliveryDAO getDeliveryDAO() {
		return deliveryDAO;
	}

	public void setDeliveryDAO(EntityDeliveryDAO deliveryDAO) {
		this.deliveryDAO = deliveryDAO;
	}

	public EntityInvoiceAddressDAO getInvoiceAddressDAO() {
		return invoiceAddressDAO;
	}

	public void setInvoiceAddressDAO(EntityInvoiceAddressDAO invoiceAddressDAO) {
		this.invoiceAddressDAO = invoiceAddressDAO;
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

	public EntityDepartmentDAO getDepartmentDAO() {
		return departmentDAO;
	}

	public void setDepartmentDAO(EntityDepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	public void insertInvoiceCompanyInfo(InvoiceCompany invoiceCompany)
			throws BizServiceException {
		String id = commonsDAO.getNextValueOfSequence("TB_INVOICE_COMPANY");
		invoiceCompany.setInvoiceCompanyId(id);
		invoiceCompany.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
		invoiceCompany.setCreateTime(DateUtil.getCurrentTime());
		invoiceCompany.setModifyTime(DateUtil.getCurrentTime());
		if (invoiceCompany.getDefaultFlag().toString().equals("1")) {
			invoiceCompany.setDefaultFlag("1");
			invoiceCompanyDAO.insert(invoiceCompany);
			InvoiceCompanyExample ex = new InvoiceCompanyExample();
			List<String> s = new ArrayList<String>();
			s.add(id);
			InvoiceCompany invc = new InvoiceCompany();
			invc.setDefaultFlag("0");
			ex.createCriteria().andInvoiceCompanyIdNotIn(s)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			invoiceCompanyDAO.updateByExampleSelective(invc, ex);
		} else {
			invoiceCompany.setDefaultFlag("0");
			invoiceCompanyDAO.insert(invoiceCompany);
		}

	}

	public void insertInvoiceAddressInfo(EntityInvoiceAddress invoiceAddress)
			throws BizServiceException {
		String id = commonsDAO
				.getNextValueOfSequence("TB_ENTITY_INVOICE_ADDRESS");
		invoiceAddress.setInvoiceAddressId(id);
		invoiceAddress.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
		invoiceAddress.setCreateTime(DateUtil.getCurrentTime());
		invoiceAddress.setModifyTime(DateUtil.getCurrentTime());
		if (invoiceAddress.getDefaultFlag().equals("1")) {
			invoiceAddress.setDefaultFlag("1");
			invoiceAddressDAO.insert(invoiceAddress);
			EntityInvoiceAddressExample ex = new EntityInvoiceAddressExample();
			List<String> s = new ArrayList<String>();
			s.add(id);
			EntityInvoiceAddress Address = new EntityInvoiceAddress();
			Address.setDefaultFlag("0");
			ex.createCriteria().andInvoiceAddressIdNotIn(s)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			invoiceAddressDAO.updateByExampleSelective(Address, ex);
		} else {
			invoiceAddress.setDefaultFlag("0");
			invoiceAddressDAO.insert(invoiceAddress);
		}

	}

	public EntityDelivery insertDeliveryInfo(EntityDelivery entityDelivery)
			throws BizServiceException {
		String id = commonsDAO.getNextValueOfSequence("TB_ENTITY_DELIVERY");
		entityDelivery.setDeliveryId(id);
		entityDelivery.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
		entityDelivery.setCreateTime(DateUtil.getCurrentTime());
		entityDelivery.setModifyTime(DateUtil.getCurrentTime());
		if (entityDelivery.getDefaultFlag().equals("1")) {
			entityDelivery.setDefaultFlag("1");
			deliveryDAO.insert(entityDelivery);
			EntityDeliveryExample ex = new EntityDeliveryExample();
			List<String> s = new ArrayList<String>();
			s.add(id);
			EntityDelivery delivery = new EntityDelivery();
			delivery.setDefaultFlag("0");
			ex.createCriteria().andDeliveryIdNotIn(s).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL);
			deliveryDAO.updateByExampleSelective(delivery, ex);
		} else {
			entityDelivery.setDefaultFlag("0");
			deliveryDAO.insert(entityDelivery);
		}
		return entityDelivery;

	}

	public void insertDeliveryContact(DeliveryContact deliveryContact)
			throws BizServiceException {
		String id = commonsDAO.getNextValueOfSequence("TB_DELIVERY_CONTACT");
		deliveryContact.setDeliveryContactId(id);
		deliveryContact.setCreateTime(DateUtil.getCurrentTime());
		deliveryContact.setModifyTime(DateUtil.getCurrentTime());
		deliveryContact.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
		if ("1".equals(deliveryContact.getDefaultFlag())) {
			DeliveryContactExample example = new DeliveryContactExample();
			DeliveryContact d = new DeliveryContact();
			List<String> ids = new ArrayList<String>();
			ids.add(id);
			d.setDefaultFlag("0");
			example.createCriteria().andDeliveryContactIdNotIn(ids)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			deliveryContractDAO.updateByExampleSelective(d, example);

		} else {
			deliveryContact.setDefaultFlag("0");
		}

		deliveryContractDAO.insert(deliveryContact);
	}

	public void insertContactInfo(EntityContact entityContact)
			throws BizServiceException {
		String id = commonsDAO.getNextValueOfSequence("TB_ENTITY_CONTACT");
		entityContact.setContactId(id);
		entityContact.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
		entityContact.setCreateTime(DateUtil.getCurrentTime());
		entityContact.setModifyTime(DateUtil.getCurrentTime());

		if (entityContact.getDefaultFlag().equals("1")) {
			entityContact.setDefaultFlag("1");
			contractDAO.insert(entityContact);
			EntityContactExample ex = new EntityContactExample();
			List<String> s = new ArrayList<String>();
			s.add(id);
			EntityContact contact = new EntityContact();
			contact.setDefaultFlag("0");
			ex.createCriteria().andContactIdNotIn(s).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL);
			contractDAO.updateByExampleSelective(contact, ex);

		} else {
			entityContact.setDefaultFlag("0");
			contractDAO.insert(entityContact);
		}

	}

	public void insertDepartMentInfo(EntityDepartment entityDepartment)
			throws BizServiceException {
		String id = commonsDAO.getNextValueOfSequence("TB_ENTITY_DEPARTMENT");
		entityDepartment.setDepartmentId(id);
		entityDepartment.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
		entityDepartment.setCreateTime(DateUtil.getCurrentTime());
		entityDepartment.setModifyTime(DateUtil.getCurrentTime());
		if (entityDepartment.getDefaultFlag().equals("1")) {
			entityDepartment.setDefaultFlag("1");
			departmentDAO.insert(entityDepartment);
			EntityDepartmentExample ex = new EntityDepartmentExample();
			List<String> s = new ArrayList<String>();
			s.add(id);
			EntityDepartment department = new EntityDepartment();
			department.setDefaultFlag("0");
			ex.createCriteria().andDepartmentIdNotIn(s).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL);
			departmentDAO.updateByExampleSelective(department, ex);
		} else {
			entityDepartment.setDefaultFlag("0");
			departmentDAO.insert(entityDepartment);
		}

	}

}
