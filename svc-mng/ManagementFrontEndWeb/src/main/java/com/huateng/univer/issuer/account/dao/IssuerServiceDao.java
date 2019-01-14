package com.huateng.univer.issuer.account.dao;

import java.util.List;

import com.allinfinance.univer.issuer.dto.issuer.IssuerDTO;
import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceQueryDTO;

public interface IssuerServiceDao {

	public List<ServiceQueryDTO> getIssuerInfoList(ServiceQueryDTO serviceDTO);

	public List<ProductQueryDTO> getIssuerProductInfoList(
			ProductQueryDTO productQueryDTO);

	public List<IssuerDTO> getAllocateOrgan(String entityId);

}
