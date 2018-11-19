package com.huateng.univer.issuer.product.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageDTO;
import com.allinfinance.univer.issuer.dto.product.ProdServiceDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.huateng.univer.issuer.product.dao.ProdAccLayPackServiceDAO;

public class ProdAccLayPackServiceDAOImpl extends SqlMapClientDaoSupport
		implements ProdAccLayPackServiceDAO {

	@SuppressWarnings("unchecked")
	public List<ServiceDTO> getProdAcctypeDTOs(ProductDTO productDto) {
		List<ServiceDTO> accTypeDTOs = (List<ServiceDTO>) getSqlMapClientTemplate()
				.queryForList("PRODUCT.selectAcctype", productDto);
		return accTypeDTOs;
	}

	public ProdServiceDTO getProductServiceByProIdAndServiceId(
			ProdServiceDTO prodAcctypeDto) {
		ProdServiceDTO productService = (ProdServiceDTO) getSqlMapClientTemplate()
				.queryForObject("PRODUCT.getProductService", prodAcctypeDto);
		return productService;
	}

	@SuppressWarnings("unchecked")
	public List<CardLayoutDTO> getCardLayoutDTOs(ProductDTO productDto) {
		List<CardLayoutDTO> cardLayoutDTOs = (List<CardLayoutDTO>) getSqlMapClientTemplate()
				.queryForList("PRODUCT.selectCardLayOut", productDto);
		return cardLayoutDTOs;
	}

	@SuppressWarnings("unchecked")
	public List<PackageDTO> getProdPackageDTOs(ProductDTO productDto) {
		List<PackageDTO> packageDTOs = (List<PackageDTO>) getSqlMapClientTemplate()
				.queryForList("PRODUCT.selectPackage", productDto);
		return packageDTOs;
	}

	@SuppressWarnings("unchecked")
	public List<ProductDTO> getProductDTOs(String sellId) {
		List<ProductDTO> productDTOs = (List<ProductDTO>) getSqlMapClientTemplate()
				.queryForList("PRODUCT.selectProductsBySellId", sellId);
		return productDTOs;
	}

}
