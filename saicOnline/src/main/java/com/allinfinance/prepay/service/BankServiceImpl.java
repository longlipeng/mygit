package com.allinfinance.prepay.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.BankMapper;
import com.allinfinance.prepay.model.Bank;
import com.allinfinance.prepay.model.BankExample;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.univer.entity.dto.BankDTO;

@Service
public class BankServiceImpl implements BankService {

	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private BankMapper bankMapper;
	@Override
	public List<BankDTO> inquery(String entityId) throws BizServiceException {
		List<BankDTO> dtoList = new ArrayList<BankDTO>();
		try {
			BankExample bankEx = new BankExample();
			bankEx.createCriteria().andEntityIdEqualTo(entityId)
					.andDataStateEqualTo("1");
			List<Bank> bankList = bankMapper.selectByExample(bankEx);
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
	@Override
	public List<BankDTO> inquery(String entityId, String type)
			throws BizServiceException {
		List<BankDTO> dtoList = new ArrayList<BankDTO>();
		try {
			BankExample bankEx = new BankExample();
			bankEx.createCriteria().andEntityIdEqualTo(entityId)
					.andBankTypeEqualTo(type).andDataStateEqualTo("");
			List<Bank> bankList = bankMapper.selectByExample(bankEx);
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

}
