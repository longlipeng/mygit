package com.huateng.univer.issuer.account.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface AccountService {

	/**
	 * 查询服务信息
	 * 
	 * @param accTypeQueryDTO
	 * @return PageDataDTO
	 * @throws BizServiceException
	 * */
	public PageDataDTO inqueryAccType(ServiceQueryDTO accTypeQueryDTO)
			throws BizServiceException;

	/**
	 * 初始化账户信息
	 * 
	 * @return ServiceDTO
	 * @throws BizServiceException
	 * */
	public ServiceDTO initAccType() throws BizServiceException;

	/**
	 * 调用账户信息
	 * 
	 * @param ServiceDTO
	 * @return ServiceDTO
	 * @throws BizServiceException
	 * */
	public ServiceDTO viewAccType(ServiceDTO accTypeDTO)
			throws BizServiceException;

	/**
	 * 增加账户信息
	 * 
	 * @param ServiceDTO
	 * 
	 * @throws BizServiceException
	 * */
	public void insertAccType(ServiceDTO accTypeDTO) throws BizServiceException;

	/**
	 * 修改账户信息
	 * 
	 * @param accTypeDTO
	 * 
	 * @throws BizServiceException
	 * */
	public void updateAccType(ServiceDTO accTypeDTO) throws BizServiceException;

	/**
	 * 删除账户信息
	 * 
	 * @param accTypeDTO
	 * 
	 * @throws BizServiceException
	 * */
	public void deleteAccType(ServiceDTO accTypeDTO) throws BizServiceException;

	/**
	 * 修改账户信息状态
	 * 
	 * @param accTypeDTO
	 * 
	 * @throws BizServiceException
	 * */
	public void modifyStateAccType(ServiceDTO accTypeDTO)
			throws BizServiceException;

	/**
	 * 获取发行机构
	 * 
	 * @param serviceDTO
	 * @return
	 * @throws BizServiceException
	 */
	public List<ServiceQueryDTO> getIssuerInfoList(ServiceQueryDTO serviceDTO)
			throws BizServiceException;

	/**
	 * 是否能编辑账户
	 * 
	 */
	public ServiceDTO EditViewAccType(ServiceDTO accTypeDTO)
			throws BizServiceException;
}
