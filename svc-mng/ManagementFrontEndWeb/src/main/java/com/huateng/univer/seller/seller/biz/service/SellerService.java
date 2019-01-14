package com.huateng.univer.seller.seller.biz.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;
import com.allinfinance.univer.seller.seller.dto.SellerQueryDTO;
import com.allinfinance.univer.seller.seller.dto.TreeNodeDTO;
import com.huateng.framework.exception.BizServiceException;

public interface SellerService {

	public PageDataDTO inquery(SellerQueryDTO sellerQueryDTO)
			throws BizServiceException;

	public PageDataDTO inqueryForSelf(SellerQueryDTO sellerQueryDTO)
			throws BizServiceException;

	public SellerDTO viewSeller(SellerDTO sellerDTO) throws BizServiceException;

	public SellerDTO initSeller(SellerDTO sellerDTO) throws BizServiceException;

	public SellerDTO insertSeller(SellerDTO sellerDTO)
			throws BizServiceException;

	public void updateSeller(SellerDTO sellerDTO) throws BizServiceException;

	public void delSeller(SellerDTO sellerDTO) throws BizServiceException;

	public SellerDTO getSellerByEntityId(SellerDTO sellerDTO)
			throws BizServiceException;

	public PageDataDTO queryEntityId(SellerQueryDTO sellerQueryDTO)
			throws BizServiceException;

	public SellerDTO configEntityId(SellerQueryDTO sellerQueryDTO)
			throws BizServiceException;

	public PageDataDTO inqueryorSelf(SellerQueryDTO sellerQueryDTO)
			throws BizServiceException;

	public PageDataDTO selectOrder(SellerQueryDTO sellerQueryDTO)

	throws BizServiceException;
	
	public List<SellerQueryDTO> getSellerList(SellerQueryDTO sellerQueryDTO)
	throws BizServiceException;
	
	/**
     * 
     * 建树形菜单
     * 
     *
     * @param entityId
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public List<TreeNodeDTO> buildTree(String entityId) 
        throws BizServiceException;
}
