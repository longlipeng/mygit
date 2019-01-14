package com.huateng.univer.issuer.issuercontract.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyAcctypeContractDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyProdContractDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.LoyaltyAcctypeContractDAO;
import com.huateng.framework.ibatis.dao.LoyaltyProdContractDAO;
import com.huateng.framework.ibatis.model.LoyaltyAcctypeContract;
import com.huateng.framework.ibatis.model.LoyaltyAcctypeContractExample;
import com.huateng.framework.ibatis.model.LoyaltyProdContract;
import com.huateng.framework.ibatis.model.LoyaltyProdContractExample;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.issuer.issuercontract.service.LoyaltyProdContractService;

public class LoyaltyProdContractServiceImpl implements
		LoyaltyProdContractService {
	Logger logger = Logger.getLogger(this.getClass());
	private LoyaltyProdContractDAO loyaltyProdContractDAO;
	private LoyaltyAcctypeContractDAO loyaltyAcctypeContractDAO;

	public LoyaltyAcctypeContractDAO getLoyaltyAcctypeContractDAO() {
		return loyaltyAcctypeContractDAO;
	}

	public void setLoyaltyAcctypeContractDAO(
			LoyaltyAcctypeContractDAO loyaltyAcctypeContractDAO) {
		this.loyaltyAcctypeContractDAO = loyaltyAcctypeContractDAO;
	}

	public LoyaltyProdContractDAO getLoyaltyProdContractDAO() {
		return loyaltyProdContractDAO;
	}

	public void setLoyaltyProdContractDAO(
			LoyaltyProdContractDAO loyaltyProdContractDAO) {
		this.loyaltyProdContractDAO = loyaltyProdContractDAO;
	}

	public List<LoyaltyProdContractDTO> inquery(String issuerContractId)
			throws BizServiceException {
		try {
			LoyaltyProdContractExample example = new LoyaltyProdContractExample();
			example.createCriteria().andLoyaltyContractIdEqualTo(
					issuerContractId).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL);
			List<LoyaltyProdContract> proList = loyaltyProdContractDAO
					.selectByExample(example);

			List<LoyaltyProdContractDTO> proDTOList = new ArrayList<LoyaltyProdContractDTO>();

			for (LoyaltyProdContract pro : proList) {
				LoyaltyProdContractDTO proDto = new LoyaltyProdContractDTO();
				ReflectionUtil.copyProperties(pro, proDto);
				// 关联发行机构合同服务明细
				proDto.setAccDTOs(getAcctypeContractDTOs(proDto));
				proDTOList.add(proDto);
			}
			return proDTOList;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询发行机构合同产品明细失败！");
		}
	}

	private List<LoyaltyAcctypeContractDTO> getAcctypeContractDTOs(
			LoyaltyProdContractDTO proDto) {
		LoyaltyAcctypeContractExample example = new LoyaltyAcctypeContractExample();
		example.createCriteria().andLoyaltyContractIdEqualTo(
				proDto.getLoyaltyContractId()).andProductIdEqualTo(
				proDto.getProductId());

		List<LoyaltyAcctypeContract> acctypeContracts = loyaltyAcctypeContractDAO
				.selectByExample(example);
		List<LoyaltyAcctypeContractDTO> acctypeContractDTOs = new ArrayList<LoyaltyAcctypeContractDTO>();
		if (null != acctypeContracts && acctypeContracts.size() > 0) {
			for (LoyaltyAcctypeContract acc : acctypeContracts) {
				LoyaltyAcctypeContractDTO dto = new LoyaltyAcctypeContractDTO();
				ReflectionUtil.copyProperties(acc, dto);
				acctypeContractDTOs.add(dto);
			}
		}
		return acctypeContractDTOs;
	}

}
