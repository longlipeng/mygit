package com.huateng.univer.issuer.product.dao;

import java.util.List;

import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageDTO;
import com.allinfinance.univer.issuer.dto.product.ProdServiceDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;

public interface ProdAccLayPackServiceDAO {
	/*
	 * @获得产品关联下的账户信息
	 */
	public List<ServiceDTO> getProdAcctypeDTOs(ProductDTO productDto);

	/*
	 * @获得产品关联下的卡面信息
	 */
	public List<CardLayoutDTO> getCardLayoutDTOs(ProductDTO productDto);

	/*
	 * @获得产品关联下的包装信息
	 */
	public List<PackageDTO> getProdPackageDTOs(ProductDTO productDto);

	/*
	 * @获得营销机构下的产品信息
	 */
	public List<ProductDTO> getProductDTOs(String sellId);

	public ProdServiceDTO getProductServiceByProIdAndServiceId(
			ProdServiceDTO prodAcctypeDto);

}
