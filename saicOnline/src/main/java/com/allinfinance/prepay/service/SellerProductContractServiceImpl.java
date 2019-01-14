package com.allinfinance.prepay.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.SellAcctypeContractMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellProdContractMapper;
import com.allinfinance.prepay.model.SellAcctypeContract;
import com.allinfinance.prepay.model.SellAcctypeContractExample;
import com.allinfinance.prepay.model.SellProdContract;
import com.allinfinance.prepay.model.SellProdContractExample;
import com.allinfinance.prepay.utils.Amount;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.univer.seller.sellercontract.dto.SellerAcctypeContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerProductContractDTO;

@Service
public class SellerProductContractServiceImpl implements
		SellerProductContractService {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private SellProdContractMapper sellProdContractMapper;
	@Autowired
	private SellAcctypeContractMapper sellAcctypeContractMapper;

	@Override
	public void insert(SellerProductContractDTO sellerProductContractDTO)
			throws BizServiceException {
		try {

			SellProdContract productContract = new SellProdContract();
			ReflectionUtil.copyProperties(sellerProductContractDTO,
					productContract);
			productContract.setId(commonsDAO
					.getNextValueOfSequence("TB_SELL_PROD_CONTRACT"));
			productContract.setCardFee(Amount.getDataBaseAmount(productContract
					.getCardFee()));
			productContract.setAnnualFee(Amount
					.getDataBaseAmount(productContract.getAnnualFee()));

			productContract.setCreateUser(sellerProductContractDTO
					.getLoginUserId());
			productContract.setCreateTime(DateUtil.getCurrentTime());
			productContract.setModifyUser(sellerProductContractDTO
					.getLoginUserId());
			productContract.setModifyTime(DateUtil.getCurrentTime());
			productContract.setDataState("1");
			// 添加营销合同产品明细
			sellProdContractMapper.insert(productContract);

			// 添加营销合同服务明细
			this.insertSellerAcctypeContract(sellerProductContractDTO);
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加营销合同产品明细失败！");
		}

	}

	/** 添加营销合同服务明细 */
	private void insertSellerAcctypeContract(
			SellerProductContractDTO sellerProductContractDTO) throws Exception {
		try {
			List<SellerAcctypeContractDTO> accDTOs = sellerProductContractDTO
					.getAccDTOs();
			SellAcctypeContract acc = new SellAcctypeContract();
			SellerAcctypeContractDTO accDTO = accDTOs.get(0);
			ReflectionUtil.copyProperties(accDTO, acc);

			acc.setId(commonsDAO
					.getNextValueOfSequence("TB_SELL_ACCTYPE_CONTRACT"));

			acc.setCreateUser(sellerProductContractDTO.getLoginUserId());
			acc.setCreateTime(DateUtil.getCurrentTime());
			acc.setModifyUser(sellerProductContractDTO.getLoginUserId());
			acc.setModifyTime(DateUtil.getCurrentTime());
			acc.setSellContractId(sellerProductContractDTO.getSellContractId());
			sellAcctypeContractMapper.insert(acc);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加营销合同帐户明细失败！");
		}
	}

}
