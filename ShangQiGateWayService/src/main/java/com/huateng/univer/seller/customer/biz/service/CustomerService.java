package com.huateng.univer.seller.customer.biz.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.shangqi.gateway.dto.CustomerUpdateForGatewayDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.customer.CustomerQueryDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 客户服务接口
 */
public interface CustomerService {

	/**
	 * 删除客户
	 */
	public void deleteCustomer(CustomerDTO customerDTO)
			throws BizServiceException;

	/**
	 * 初始化客户
	 */
	public CustomerDTO initCustomer(CustomerDTO customerDTO)
			throws BizServiceException;

	/**
	 * 查询客户
	 */
	public PageDataDTO inqueryCustomer(CustomerQueryDTO customerQueryDTO)
			throws BizServiceException;

	/**
	 * 添加插入客户
	 */
	public CustomerDTO insertOrderCustomer(CustomerDTO customerDTO)
			throws BizServiceException;

	/**
	 * 快速添加订单客户
	 */
	public CustomerDTO insertCustomer(CustomerDTO customerDTO)
			throws BizServiceException;

	/**
	 * 修改客户状态
	 */
	public void modifyState(CustomerDTO customerDTO) throws BizServiceException;

	/**
	 * 修改更新客户
	 */
	public void updateCustomer(CustomerDTO customerDTO)
			throws BizServiceException;

	/**
	 * 查看客户
	 */
	public CustomerDTO viewCustomer(CustomerDTO customerDTO)
			throws BizServiceException;

	/**
	 * 导入客户信息
	 */
	public void importFile(CustomerDTO customerDTO) throws BizServiceException;

	/**
	 * 获取客户信息
	 * 
	 * @param fatherEntityId
	 * @return
	 * @throws BizServiceException
	 */
	public CustomerDTO getCustomerById(String entityId)
			throws BizServiceException;

	/**
	 * 获取散户客户
	 * 
	 * @param entityId
	 * @return
	 * @throws BizServiceException
	 */
	public CustomerDTO selectRetailCustomer(String entityId)
			throws BizServiceException;
	
	//查询客户和部门信息
	public CustomerDTO customerDepartmentInquery(CustomerDTO customerDTO) throws BizServiceException;
	
	public void updateCustomerAndCardholder(CustomerDTO customerDTO,CardholderDTO cardholderDTO)
			throws BizServiceException;
	/**
	 * 门户客户信息补录
	 */
	public void updateCustomerForGateway(CustomerUpdateForGatewayDTO dto)
			throws BizServiceException;
}


