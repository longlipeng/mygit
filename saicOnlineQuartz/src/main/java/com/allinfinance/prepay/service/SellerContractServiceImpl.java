package com.allinfinance.prepay.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.SellContractMapper;
import com.allinfinance.prepay.model.SellContract;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.prepay.utils.StringUtil;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;

@Service
public class SellerContractServiceImpl implements SellerContractService {

	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private SellContractMapper sellContractMapper;
	@Override
	public SellerContractDTO insert(SellerContractDTO sellerContractDTO)
			throws BizServiceException {
		try {
			

			SellContract sellContract = new SellContract();
			ReflectionUtil.copyProperties(sellerContractDTO, sellContract);

			sellContract.setSellContractId(commonsDAO
					.getNextValueOfSequence("TB_SELL_CONTRACT"));
			sellContract.setExpiryDate("29991231");
			sellContract.setCreateTime(DateUtil.getCurrentTime());
			sellContract.setCreateUser(sellerContractDTO.getLoginUserId());
			sellContract.setModifyTime(DateUtil.getCurrentTime());
			sellContract.setModifyUser(sellerContractDTO.getLoginUserId());
			sellContract.setDataState("1");
			sellContract.setClearTp(sellerContractDTO.getClearTp());
			sellContract.setContractType("3");
			sellContractMapper.insert(sellContract);
			ReflectionUtil.copyProperties(sellContract, sellerContractDTO);
			/*
			 * // 添加服务营销机构服务合同 sellerContractDTO.setSellContractId(sellContract
			 * .getSellContractId());
			 * insertSellerAcctypeContract(sellerContractDTO);
			 */
			return sellerContractDTO;
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加合同失败！");
		}
	}

}
