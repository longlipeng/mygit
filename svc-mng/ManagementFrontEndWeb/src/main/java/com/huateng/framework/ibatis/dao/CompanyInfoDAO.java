package com.huateng.framework.ibatis.dao;

import com.allinfinance.univer.seller.cardholder.dto.CompanyInfoDTO;
import com.huateng.framework.ibatis.model.CompanyInfo;
import com.huateng.framework.ibatis.model.CompanyInfoExample;
import com.huateng.framework.ibatis.model.CompanyInfoKey;

import java.util.List;

public interface CompanyInfoDAO {

    /**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_COMPANY_INFO
	 * @ibatorgenerated  Thu Aug 31 13:29:40 CST 2017
	 */
	int countByExample(CompanyInfoExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_COMPANY_INFO
	 * @ibatorgenerated  Thu Aug 31 13:29:40 CST 2017
	 */
	int deleteByExample(CompanyInfoExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_COMPANY_INFO
	 * @ibatorgenerated  Thu Aug 31 13:29:40 CST 2017
	 */
	int deleteByPrimaryKey(CompanyInfoKey key);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_COMPANY_INFO
	 * @ibatorgenerated  Thu Aug 31 13:29:40 CST 2017
	 */
	void insert(CompanyInfo record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_COMPANY_INFO
	 * @ibatorgenerated  Thu Aug 31 13:29:40 CST 2017
	 */
	void insertSelective(CompanyInfo record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_COMPANY_INFO
	 * @ibatorgenerated  Thu Aug 31 13:29:40 CST 2017
	 */
	List selectByExample(CompanyInfoExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_COMPANY_INFO
	 * @ibatorgenerated  Thu Aug 31 13:29:40 CST 2017
	 */
	CompanyInfo selectByPrimaryKey(CompanyInfoKey key);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_COMPANY_INFO
	 * @ibatorgenerated  Thu Aug 31 13:29:40 CST 2017
	 */
	int updateByExampleSelective(CompanyInfo record, CompanyInfoExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_COMPANY_INFO
	 * @ibatorgenerated  Thu Aug 31 13:29:40 CST 2017
	 */
	int updateByExample(CompanyInfo record, CompanyInfoExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_COMPANY_INFO
	 * @ibatorgenerated  Thu Aug 31 13:29:40 CST 2017
	 */
	int updateByPrimaryKeySelective(CompanyInfo record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table TB_COMPANY_INFO
	 * @ibatorgenerated  Thu Aug 31 13:29:40 CST 2017
	 */
	int updateByPrimaryKey(CompanyInfo record);

	public List<CompanyInfoDTO> selectByCusCardholderIdNo(CompanyInfoDTO companyInfoDTO);

    public List<CompanyInfoDTO> selectOtherCusCardholderByIdNo(CompanyInfoDTO companyInfoDTO);
}