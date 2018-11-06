package com.huateng.univer.seller.sellercontract.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.sellercontract.dto.SellerAcctypeContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerProductContractDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellAcctypeContractDAO;
import com.huateng.framework.ibatis.dao.SellProdContractDAO;
import com.huateng.framework.ibatis.model.SellAcctypeContract;
import com.huateng.framework.ibatis.model.SellAcctypeContractExample;
import com.huateng.framework.ibatis.model.SellProdContract;
import com.huateng.framework.ibatis.model.SellProdContractExample;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.seller.sellercontract.biz.service.SellerProductContractService;

public class SellerProductContractServiceImpl implements
		SellerProductContractService {
	Logger logger = Logger.getLogger(this.getClass());
	private SellProdContractDAO sellProdContractDAO;
	private SellAcctypeContractDAO sellAcctypeContractDAO;
	private CommonsDAO commonsDAO;
	private BaseDAO baseDAO;

	public List<SellerProductContractDTO> inquery(String sellerContractId)
			throws BizServiceException {
		try {
			SellProdContractExample example = new SellProdContractExample();
			example.createCriteria().andSellContractIdEqualTo(sellerContractId)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<SellProdContract> proList = sellProdContractDAO
					.selectByExample(example);
			List<SellerProductContractDTO> proDTOList = new ArrayList<SellerProductContractDTO>();

			for (SellProdContract pro : proList) {
				SellerProductContractDTO proDto = new SellerProductContractDTO();
				ReflectionUtil.copyProperties(pro, proDto);
				// 关联营销合同服务明细
				proDto.setAccDTOs(getAcctypeContractDTOs(proDto));
				proDTOList.add(proDto);
			}
			return proDTOList;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询营销合同产品明细失败！");
		}
	}

	private List<SellerAcctypeContractDTO> getAcctypeContractDTOsForView(
			SellerProductContractDTO proDto) throws Exception {
		SellAcctypeContractExample example = new SellAcctypeContractExample();
		example.createCriteria().andSellContractIdEqualTo(
				proDto.getSellContractId()).andProductIdEqualTo(
				proDto.getProductId());

		List<SellAcctypeContract> acctypeContracts = sellAcctypeContractDAO
				.selectByExample(example);
		List<SellerAcctypeContractDTO> acctypeContractDTOs = new ArrayList<SellerAcctypeContractDTO>();
		if (null != acctypeContracts && acctypeContracts.size() > 0) {
			for (SellAcctypeContract acc : acctypeContracts) {
				SellerAcctypeContractDTO dto = new SellerAcctypeContractDTO();
				ReflectionUtil.copyProperties(acc, dto);
				acctypeContractDTOs.add(dto);
			}

		}
		return acctypeContractDTOs;
	}

	private List<SellerAcctypeContractDTO> getAcctypeContractDTOs(
			SellerProductContractDTO proDto) throws Exception {
		return baseDAO.queryForList("SELLERCONTRACT.selectSellAcctypeContract",
				proDto);
	}

	public SellerProductContractDTO view(
			SellerProductContractDTO sellerProductContractDTO)
			throws BizServiceException {

		try {
			// SellProdContract productContract =
			// sellProdContractDAO.selectByPrimaryKey(sellerProductContractDTO.getId());
			sellerProductContractDTO = (SellerProductContractDTO) baseDAO
					.queryForObject("SELLERCONTRACT.selectProdContractForView",
							sellerProductContractDTO.getId());

			// 关联营销合同服务明细
			sellerProductContractDTO
					.setAccDTOs(getAcctypeContractDTOs(sellerProductContractDTO));

			return sellerProductContractDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看营销合同产品明细失败！");
		}

	}

	/** 查看营销合同服务明细 */
	public SellerAcctypeContractDTO viewAcctypeContract(
			SellerAcctypeContractDTO sellerAcctypeContractDTO)
			throws BizServiceException {
		try {
			SellAcctypeContract acctypeContract = sellAcctypeContractDAO
					.selectByPrimaryKey(sellerAcctypeContractDTO.getId());

			ReflectionUtil.copyProperties(acctypeContract,
					sellerAcctypeContractDTO);
			return sellerAcctypeContractDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看营销合同服务明细失败！");
		}
	}

	/** 添加营销合同产品明细 */
	public void insert(SellerProductContractDTO sellerProductContractDTO)
			throws BizServiceException {
		try {
			// 判断产品在合同中是否已存在
			SellProdContractExample example = new SellProdContractExample();
			example.createCriteria().andProductIdEqualTo(
					sellerProductContractDTO.getProductId())
					.andSellContractIdEqualTo(
							sellerProductContractDTO.getSellContractId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);

			if (sellProdContractDAO.countByExample(example) > 0) {
				throw new BizServiceException("产品："
						+ sellerProductContractDTO.getProductId()
						+ " 在合同中已存在，不能重复添加！");
			}
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
			productContract.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			// 添加营销合同产品明细
			sellProdContractDAO.insert(productContract);

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
			List<SellAcctypeContract> accList = new ArrayList<SellAcctypeContract>();
			for (SellerAcctypeContractDTO accDTO : accDTOs) {
				SellAcctypeContract acc = new SellAcctypeContract();
				ReflectionUtil.copyProperties(accDTO, acc);
				acc.setId(commonsDAO
						.getNextValueOfSequence("TB_SELL_ACCTYPE_CONTRACT"));

				acc.setCreateUser(sellerProductContractDTO.getLoginUserId());
				acc.setCreateTime(DateUtil.getCurrentTime());
				acc.setModifyUser(sellerProductContractDTO.getLoginUserId());
				acc.setModifyTime(DateUtil.getCurrentTime());
				acc.setSellContractId(sellerProductContractDTO
						.getSellContractId());
				accList.add(acc);
			}
			commonsDAO.batchUpdate(
					"TB_SELL_ACCTYPE_CONTRACT.abatorgenerated_insert", accList);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加营销合同帐户明细失败！");
		}
	}

	public void update(SellerProductContractDTO sellerProductContractDTO)
			throws BizServiceException {
		try {
			SellProdContract productContract = new SellProdContract();
			ReflectionUtil.copyProperties(sellerProductContractDTO,
					productContract);

			productContract.setCardFee(Amount.getDataBaseAmount(productContract
					.getCardFee()));
			productContract.setAnnualFee(Amount
					.getDataBaseAmount(productContract.getAnnualFee()));

			productContract.setModifyTime(DateUtil.getCurrentTime());
			productContract.setModifyUser(sellerProductContractDTO
					.getLoginUserId());

			sellProdContractDAO.updateByPrimaryKeySelective(productContract);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新帐户合同产品明细失败！");
		}
	}

	public void updateSellerService(
			SellerAcctypeContractDTO sellerAcctypeContractDTO)
			throws BizServiceException {
		try {
			SellAcctypeContract acctypeContract = new SellAcctypeContract();
			ReflectionUtil.copyProperties(sellerAcctypeContractDTO,
					acctypeContract);
			acctypeContract.setModifyTime(DateUtil.getCurrentTime());
			acctypeContract.setModifyUser(sellerAcctypeContractDTO
					.getLoginUserId());

			sellAcctypeContractDAO.updateByPrimaryKeySelective(acctypeContract);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新营销合同帐户明细失败！");
		}

	}

	public void delete(SellerProductContractDTO sellerProductContractDTO)
			throws BizServiceException {
		try {
			SellProdContract productContract = new SellProdContract();
			ReflectionUtil.copyProperties(sellerProductContractDTO,
					productContract);
			productContract.setModifyTime(DateUtil.getCurrentTime());
			productContract.setModifyUser(sellerProductContractDTO
					.getLoginUserId());
			productContract.setDataState(DataBaseConstant.DATA_STATE_DELETE);

			sellProdContractDAO.updateByPrimaryKeySelective(productContract);

			// 删除该合同对应的服务合同
			SellAcctypeContractExample example = new SellAcctypeContractExample();
			example.createCriteria().andSellContractIdEqualTo(
					sellerProductContractDTO.getSellContractId())
					.andProductIdEqualTo(
							sellerProductContractDTO.getProductId());

			List<SellAcctypeContract> accContracts = sellAcctypeContractDAO
					.selectByExample(example);
			if (null != accContracts && accContracts.size() > 0) {
				commonsDAO
						.batchDelete(
								"TB_SELL_ACCTYPE_CONTRACT.abatorgenerated_deleteByPrimaryKey",
								accContracts);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除合同产品明细失败！");
		}
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public SellProdContractDAO getSellProdContractDAO() {
		return sellProdContractDAO;
	}

	public void setSellProdContractDAO(SellProdContractDAO sellProdContractDAO) {
		this.sellProdContractDAO = sellProdContractDAO;
	}

	public SellAcctypeContractDAO getSellAcctypeContractDAO() {
		return sellAcctypeContractDAO;
	}

	public void setSellAcctypeContractDAO(
			SellAcctypeContractDAO sellAcctypeContractDAO) {
		this.sellAcctypeContractDAO = sellAcctypeContractDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

}
