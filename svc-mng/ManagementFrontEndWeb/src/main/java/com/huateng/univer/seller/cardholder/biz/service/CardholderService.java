package com.huateng.univer.seller.cardholder.biz.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderQueryDTO;
import com.allinfinance.univer.seller.cardholder.dto.CheckPeopleInfoDTO;
import com.allinfinance.univer.seller.cardholder.dto.CompanyInfoDTO;
import com.allinfinance.univer.seller.cardholder.dto.IdCardInfoDTO;
import com.allinfinance.univer.seller.cardholder.dto.PictureInfoQueryDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * 持卡人service
 * 
 * @author xxl
 * 
 */
public interface CardholderService {

	/**
	 * 查看持卡人
	 */
	public CardholderDTO view(CardholderDTO cardholderDTO)
			throws BizServiceException;

	/**
	 * 查询持卡人
	 */
	public PageDataDTO inquery(CardholderQueryDTO cardholderQueryDTO)
			throws BizServiceException;

	/**
	 * 查询持卡人
	 */
	public PageDataDTO queryCardHolder(CardholderQueryDTO cardholderQueryDTO)
			throws BizServiceException;

	/**
	 * 查询没有关联到orderList表的持卡人
	 */
	public CardholderDTO inqueryCardholderWithOrderList(
			CardholderQueryDTO cardholderQueryDTO) throws BizServiceException;

	/**
	 * 添加持卡人
	 */
	public void insert(CardholderDTO cardholderDTO) throws BizServiceException;

	public CardholderDTO insertCardholderDTO(CardholderDTO cardholderDTO)
			throws BizServiceException;

	/**
	 * 导入持卡人
	 */
	public CardholderDTO insertCardholder(CardholderDTO cardholderDTO)
			throws BizServiceException;

	/**
	 * 修改更新个人持卡人
	 */
	public void update(CardholderDTO cardholderDTO) throws BizServiceException;

	/**
	 * 管理持卡人新客户
	 */
	public void updateCust(CardholderDTO cardholderDTO)
			throws BizServiceException;

	/**
	 * 删除持卡人
	 */
	public void delete(CardholderDTO cardholderDTO) throws BizServiceException;

	/**
	 * 通过卡号查询配送完成订单中的持卡人信息
	 * */
	public List<CardholderQueryDTO> getCardHolderByCardNo(String cardNo)
			throws BizServiceException;

	/**
	 * 记录记名库存卡的持卡人信息，并将持卡人信息与持卡人信息进行关联
	 */
	public void insertSignCardHolder(CardholderDTO cardholderDTO)
			throws BizServiceException;

	public CardholderDTO insertCardholderReturnIds(CardholderDTO cardholderDTO)
			throws BizServiceException;
	
	public PageDataDTO inqueryByCheck(CardholderQueryDTO cardholderQueryDTO)
			throws BizServiceException;
	/**
	 * 持卡人信息审核
	 */
	public void checkUpdate(CardholderDTO cardholderDTO) throws BizServiceException;
	/**
	 * 根据持卡人身份证查询持卡人
	 * @param cardholderDTO
	 * @return
	 * @throws BizServiceException
	 */
	public List<CardholderDTO> selectByCardholderIdNo(CardholderDTO cardholderDTO) throws BizServiceException;
	/**
	 * 查其他持卡人是否有在用此身份证号
	 * @param cardholderDTO
	 * @return
	 * @throws BizServiceException
	 */
	public List<CardholderDTO> checkOtherIdNo(CardholderDTO cardholderDTO) throws BizServiceException;
	
	/**
	 * 查询证件照片
	 * 
	 * @param cardholderDTO
	 * @return
	 * @throws BizServiceException
	 */
	public IdCardInfoDTO getIdImageInfo(String  idNo)
			throws BizServiceException;
	
	/***********************************新增区域******************************************/
	/**
         * 查询持卡人列表
         */
        public PageDataDTO inqueryShow(CardholderQueryDTO cardholderQueryDTO)
                        throws BizServiceException;
        
        /**
         * 添加个人持卡人
         * @param cardholderDTO
         * @throws BizServiceException
         */
        public CardholderDTO saveCardholder(CardholderDTO cardholderDTO) throws BizServiceException; 
        
        /**
         * 添加企业持卡人
         */
        public CompanyInfoDTO insertcustomer(CompanyInfoDTO companyInfoDTO) throws BizServiceException;
        
        /**
         * 根据持卡人身份证查询企业持卡人
         * @param cardholderDTO
         * @return
         * @throws BizServiceException
         */
        
        public List<CompanyInfoDTO> selectByCusCardholderIdNo(CompanyInfoDTO companyInfoDTO) throws BizServiceException;
        /**
         * 查其他持卡人是否有在用此身份证号
         * @param cardholderDTO
         * @return
         * @throws BizServiceException
         */
        
        public List<CompanyInfoDTO> checkOtherCusIdNo(CompanyInfoDTO companyInfoDTO) throws BizServiceException;
        
        /**
         * 查看企业持卡人信息
         */
        
        public CompanyInfoDTO cusView(CompanyInfoDTO companyInfoDTO)
                throws BizServiceException;
        /**
         * 更新企业持卡人
         */
        public void updateCus(CompanyInfoDTO companyInfoDTO) throws BizServiceException;
        
        /**
         * 查看个人持卡人审核信息
         */
        public CardholderDTO checkPerView(CardholderDTO cardholderDTO)
                        throws BizServiceException;
        
        /**
         * 查看企业持卡人审核信息
         */
        public CompanyInfoDTO checkCusView(CompanyInfoDTO companyInfoDTO)
                throws BizServiceException;
        
        /**
         * 更新个人持卡人审核信息
         */
        public void checkPerUpdate(CardholderDTO cardholderDTO) throws BizServiceException;
        
        /**
         * 更新企业持卡人审核信息
         */
        public void checkCusUpdate(CompanyInfoDTO companyInfoDTO) throws BizServiceException;
        
        /**
         * 根据企业证件类型判断是否重复
         */
        public void  checkLicense(CompanyInfoDTO companyInfoDTO) throws BizServiceException;
	
        /**
         * 删除企业持卡人
         */
        public void deleteCus(CompanyInfoDTO companyInfoDTO) throws BizServiceException;
        
        /**
         * 查询客户信息
         */
        public PageDataDTO query(CardholderQueryDTO cardholderQueryDTO)
                        throws BizServiceException;
        
        /**
         * 持卡人补录信息审核查询
         * @param pictureInfoQueryDTO
         * @return
         * @throws BizServiceException
         */
        public PageDataDTO informationAudit(PictureInfoQueryDTO pictureInfoQueryDTO)
    			throws BizServiceException;
        
        /**
         * 补录信息审核
         * @param pictureInfoQueryDTO
         * @throws BizServiceException
         */
        public void auditCheck(CheckPeopleInfoDTO checkPeopleInfoDTO)
        		throws BizServiceException ;
        /***********************************新增区域******************************************/
	
}
