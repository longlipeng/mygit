package com.huateng.univer.issuer.productPackage.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface ProductPackageService {

	/**
	 * 查询包装信息
	 * 
	 * @param PackAgeQueryDTO
	 * @return PageDataDTO
	 * @throws BizServiceException
	 */
	public PageDataDTO inqueryPackAge(PackageQueryDTO PackAgeQueryDTO)
			throws BizServiceException;

	/**
	 * 查询包装信息
	 * 
	 * @param PackAgeQueryDTO
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO selectPackage(PackageQueryDTO PackAgeQueryDTO)
			throws BizServiceException;

	/**
	 * 初始化包装信息
	 * 
	 * @return PackAgeDTO
	 * @throws BizServiceException
	 */
	public PackageDTO initPackage() throws BizServiceException;

	/**
	 * 调用包装信息
	 * 
	 * @param packAgeDTO
	 * @return PackAgeDTO
	 * @throws BizServiceException
	 */
	public PackageDTO viewPackage(PackageDTO packageDTO)
			throws BizServiceException;

	/**
	 * 增加包装信息
	 * 
	 * @param packAgeDTO
	 * 
	 * @throws BizServiceException
	 */
	public void insertPackage(PackageDTO packageDTO) throws BizServiceException;

	/**
	 * 修改包装信息
	 * 
	 * @param packAgeDTO
	 * 
	 * @throws BizServiceException
	 */
	public void updatePackage(PackageDTO packageDTO) throws BizServiceException;

	/**
	 * 删除包装信息
	 * 
	 * @param packAgeDTO
	 * 
	 * @throws BizServiceException
	 */
	public void deletePackage(PackageDTO packageDTO) throws BizServiceException;

	/**
	 * 修改包装信息状态
	 * 
	 * @param packAgeDTO
	 * 
	 * @throws BizServiceException
	 */
	public void modifyStatePackage(PackageDTO packageDTO)
			throws BizServiceException;

	/**
	 * 是否能修改包装信息
	 * 
	 */
	public PackageDTO EditViewPackage(PackageDTO packageDTO)
			throws BizServiceException;
}
