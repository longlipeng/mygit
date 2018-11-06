package com.huateng.univer.seller.customer.biz.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.customer.CertifincateValidityQueryDTO;
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
	 * 根据身份证号获取客户信息
	 * 
	 * @param idNo
	 * @return
	 * @throws BizServiceException
	 */
	public List<CustomerDTO> getCustomerByIdNo(CustomerDTO customerDTO)
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

	/**
	 * 查询企业客户
	 */
	public PageDataDTO inqueryCus(CustomerQueryDTO customerQueryDTO) throws BizServiceException;

	/**
	 * 查询个人客户
	 */
	public PageDataDTO inqueryPer(CustomerQueryDTO customerQueryDTO) throws BizServiceException;

	// 将审核通过的客户信息同步至CIM
	public void syncToCRM(CustomerDTO dto) throws Exception;
	//检查企业证件号是否重复
	public void  checkLicense(CustomerDTO dto) throws BizServiceException;
	//查询过期证件号
	public PageDataDTO queryOutdateCertifincate(CertifincateValidityQueryDTO dto) throws BizServiceException ;
}


