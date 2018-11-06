package com.huateng.univer.issuer.entityBaseInfo.service;

import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.DeliveryContact;
import com.huateng.framework.ibatis.model.EntityContact;
import com.huateng.framework.ibatis.model.EntityDelivery;
import com.huateng.framework.ibatis.model.EntityDepartment;
import com.huateng.framework.ibatis.model.EntityInvoiceAddress;
import com.huateng.framework.ibatis.model.InvoiceCompany;

public interface EntityBaseInfoService {
	/**
	 * 插入发票公司名称列表
	 * 
	 * @param invoiceCompany
	 * @throws BizServiceException
	 */
	public void insertInvoiceCompanyInfo(InvoiceCompany invoiceCompany)
			throws BizServiceException;

	/**
	 * 插入发票地址信息列表
	 * 
	 * @param invoiceCompany
	 * @throws BizServiceException
	 */
	public void insertInvoiceAddressInfo(EntityInvoiceAddress invoiceAddress)
			throws BizServiceException;

	/**
	 * 插入快递点信息列表
	 * 
	 * @param invoiceCompany
	 * @throws BizServiceException
	 */
	public EntityDelivery insertDeliveryInfo(EntityDelivery entityDelivery)
			throws BizServiceException;

	/**
	 * 插入快递点联系人信息
	 * 
	 * @param deliveryContact
	 * @throws BizServiceException
	 */
	public void insertDeliveryContact(DeliveryContact deliveryContact)
			throws BizServiceException;

	/**
	 * 插入联系人信息列表
	 * 
	 * @param invoiceCompany
	 * @throws BizServiceException
	 */
	public void insertContactInfo(EntityContact entityContact)
			throws BizServiceException;

	/**
	 * 插入部门信息列表
	 * 
	 * @param invoiceCompany
	 * @throws BizServiceException
	 */
	public void insertDepartMentInfo(EntityDepartment entityDepartment)
			throws BizServiceException;

}
