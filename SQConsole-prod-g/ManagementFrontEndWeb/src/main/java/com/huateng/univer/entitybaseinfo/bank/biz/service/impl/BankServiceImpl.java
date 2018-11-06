package com.huateng.univer.entitybaseinfo.bank.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.entity.dto.BankDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.Bank;
import com.huateng.framework.ibatis.model.BankExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.entitybaseinfo.bank.biz.dao.BankServiceDAO;
import com.huateng.univer.entitybaseinfo.bank.biz.service.BankService;

//银行账户信息
public class BankServiceImpl implements BankService {
	Logger logger = Logger.getLogger(this.getClass());
	private BankServiceDAO bankDAO;
	private CommonsDAO commonsDAO;

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public BankServiceDAO getBankDAO() {
		return bankDAO;
	}

	public void setBankDAO(BankServiceDAO bankDAO) {
		this.bankDAO = bankDAO;
	}

	public void delete(BankDTO bankDTO) throws BizServiceException {
		try {
			List<BankDTO> bankDTOs = bankDTO.getBankDTOs();
			List<Bank> banks = new ArrayList<Bank>();
			for (BankDTO bankDto : bankDTOs) {
				Bank bank = new Bank();
				ReflectionUtil.copyProperties(bankDto, bank);
				bank.setModifyUser(bankDTO.getLoginUserId());
				bank.setModifyTime(DateUtil.getCurrentTime());
				bank.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				banks.add(bank);
			}
			commonsDAO.batchUpdate(
					"TB_BANK.abatorgenerated_updateByPrimaryKeySelective",
					banks);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除银行账户信息失败");
		}
	}

	public List<BankDTO> inquery(String entityId, String type)
			throws BizServiceException {
		List<BankDTO> dtoList = new ArrayList<BankDTO>();
		try {
			BankExample bankEx = new BankExample();
			bankEx.createCriteria().andEntityIdEqualTo(entityId)
					.andBankTypeEqualTo(type).andDataStateEqualTo(
							DataBaseConstant.DATA_STATE_NORMAL);
			List<Bank> bankList = bankDAO.selectByExample(bankEx);
			for (Bank e : bankList) {
				BankDTO dto = new BankDTO();
				ReflectionUtil.copyProperties(e, dto);
				dtoList.add(dto);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询银行账户信息失败");
		}
		return dtoList;
	}

	public void insert(BankDTO bankDTO) throws BizServiceException {
		try {
			Bank bank = new Bank();
			ReflectionUtil.copyProperties(bankDTO, bank);
			if ("1".equals(bank.getAccountFlag())) {
				bank.setAccountFlag("0");
				bankDAO.updateAccountFlag(bank);
			} else {
				BankExample be = new BankExample();
				be
						.createCriteria()
						.andEntityIdEqualTo(bank.getEntityId())
						.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
				if (bankDAO.countByExample(be) == 0) {
					bank.setAccountFlag("1");
				}
			}
			String id = commonsDAO.getNextValueOfSequence("TB_Bank");
			String operateUser = bankDTO.getLoginUserId();
			bank.setBankId(id);
			bank.setAccountFlag(bankDTO.getAccountFlag());
			bank.setCreateUser(operateUser);
			bank.setCreateTime(DateUtil.getCurrentTime());
			bank.setModifyUser(operateUser);
			bank.setModifyTime(bank.getCreateTime());
			bank.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			bankDAO.insert(bank);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加银行账户信息失败");
		}
	}

	public List<BankDTO> inquery(String entityId) throws BizServiceException {
		List<BankDTO> dtoList = new ArrayList<BankDTO>();
		try {
			BankExample bankEx = new BankExample();
			bankEx.createCriteria().andEntityIdEqualTo(entityId)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			List<Bank> bankList = bankDAO.selectByExample(bankEx);
			for (Bank e : bankList) {
				BankDTO dto = new BankDTO();
				ReflectionUtil.copyProperties(e, dto);
				dtoList.add(dto);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询银行账户信息失败");
		}
		return dtoList;
	}

	/*
	 * 查看银行信息
	 */
	public BankDTO view(BankDTO dto) throws BizServiceException {
		try {
			Bank bank = bankDAO.selectByPrimaryKey(dto.getBankId());
			BankDTO resultDto = new BankDTO();
			ReflectionUtil.copyProperties(bank, resultDto);
			return resultDto;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看银行信息失败！");
		}
	}

	/**
	 * 所更新或新增的银行账户如果是默认账户，则把其他银行账户设为非默认
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public void updateAccountFlag(Bank bank) {
		// 如果提交的银行账户是设为默认的，则把已有的默认银行账户更新为非默认
		if ("1".equals(bank.getAccountFlag())) {
			bank.setAccountFlag("0");
			bankDAO.updateAccountFlag(bank);
		} else {
			BankExample be = new BankExample();
			be.createCriteria().andEntityIdEqualTo(bank.getEntityId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			if (bankDAO.countByExample(be) == 0) {
				bank.setAccountFlag("1");
			}
		}
	}

	/*
	 * 更新银行信息
	 */
	public void update(BankDTO dto) throws BizServiceException {
		try {
			Bank bank = new Bank();
			ReflectionUtil.copyProperties(dto, bank);
			bank.setModifyUser(dto.getLoginUserId());
			bank.setModifyTime(DateUtil.getCurrentTime());
			updateAccountFlag(bank);
			bank.setAccountFlag(dto.getAccountFlag());
			bankDAO.updateByPrimaryKeySelective(bank);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新银行信息失败！");
		}
	}

}
