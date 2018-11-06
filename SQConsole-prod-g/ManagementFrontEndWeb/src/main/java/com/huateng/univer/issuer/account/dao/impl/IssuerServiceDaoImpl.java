package com.huateng.univer.issuer.account.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.univer.issuer.dto.issuer.IssuerDTO;
import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceQueryDTO;
import com.huateng.univer.issuer.account.dao.IssuerServiceDao;

/**
 * 获取发行机构和子发行机构（只限两级父子关系）
 * 
 * @author fengfeng.shi
 * 
 */
public class IssuerServiceDaoImpl extends SqlMapClientDaoSupport implements
		IssuerServiceDao {

	@SuppressWarnings("unchecked")
	public List<ServiceQueryDTO> getIssuerInfoList(ServiceQueryDTO serviceDTO) {
		List<ServiceQueryDTO> issuerList = getSqlMapClientTemplate()
				.queryForList("SERVICE.selectIssuerInfoList", serviceDTO);
		return issuerList;
	}

	@SuppressWarnings("unchecked")
	public List<ProductQueryDTO> getIssuerProductInfoList(
			ProductQueryDTO productQueryDTO) {
		List<ProductQueryDTO> issuerList = getSqlMapClientTemplate()
				.queryForList("PRODUCT.selectIssuerInfoList", productQueryDTO);
		return issuerList;
	}

	@SuppressWarnings("unchecked")
	public List<IssuerDTO> getAllocateOrgan(String entityId) {
		List<IssuerDTO> issuerDTOs = getSqlMapClientTemplate().queryForList(
				"STOCKCARD.selectAllocateOrgan", entityId);
		return issuerDTOs;
	}

}
