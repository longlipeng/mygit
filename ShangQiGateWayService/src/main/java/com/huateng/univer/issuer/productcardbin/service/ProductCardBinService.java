package com.huateng.univer.issuer.productcardbin.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.product.ProductCardBinDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.ProductCardBin;

/**
 * 产品卡BIN service
 * 
 * @author xxl
 * 
 */
public interface ProductCardBinService {

	public List<ProductCardBinDTO> getCardBinByProductId(String productId)
			throws BizServiceException;

	public PageDataDTO choiceProductCardBin1(ProductQueryDTO productQueryDTO)
			throws BizServiceException;

	public ProductDTO choiceProductCardBin(ProductDTO productDTO)
			throws BizServiceException;

	public void insert(ProductCardBinDTO productCardBinDTO)
			throws BizServiceException;

	public void update(ProductCardBinDTO productCardBinDTO)
			throws BizServiceException;

	public void updateSerialNumber(ProductCardBin productCardBin)
			throws BizServiceException;

	public void delete(ProductCardBinDTO productCardBinDTO)
			throws BizServiceException;

	public ProductCardBinDTO view(ProductCardBinDTO productCardBinDTO)
			throws BizServiceException;

	public void modifyDefaultState(ProductCardBinDTO productCardBinDTO)
			throws BizServiceException;

	public ProductCardBin getProductCardBinForLock(String productId)
			throws BizServiceException;
}
