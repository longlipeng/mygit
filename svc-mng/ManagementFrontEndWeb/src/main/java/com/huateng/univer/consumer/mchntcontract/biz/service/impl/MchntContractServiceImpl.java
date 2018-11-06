package com.huateng.univer.consumer.mchntcontract.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumercontract.dto.ConsumerContractDTO;
import com.allinfinance.univer.consumercontract.dto.ConsumerContractQueryDTO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.consumer.mchntcontract.biz.service.MchntContractService;
import com.huateng.univer.consumer.mchntcontract.integration.dao.MchntContractServiceDAO;

/**
 * 
 * 
 * @author zengfenghua
 * @since 1.0
 */
public class MchntContractServiceImpl implements MchntContractService {

	/**
     *
     */
	Logger logger = Logger.getLogger(MchntContractServiceImpl.class);

	private PageQueryDAO pageQueryDAO;

	private MchntContractServiceDAO mchntContractServiceDAO;

	/**
	 * 
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inquiryMchntContract(ConsumerContractQueryDTO dto)
			throws BizServiceException {
		try {
			return pageQueryDAO.query("MCHNT_CONTRACT.selectMchntContractDTO",
					dto);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询商户合同失败！");
		}
	}

	public List<ConsumerContractDTO> inqueryContractById(String entityId)
			throws BizServiceException {
		List<ConsumerContractDTO> dtoList = new ArrayList<ConsumerContractDTO>();
		try {
			List<ConsumerContractDTO> contractList = mchntContractServiceDAO
					.selectById(entityId);
			for (ConsumerContractDTO e : contractList) {
				ConsumerContractDTO dto = new ConsumerContractDTO();
				ReflectionUtil.copyProperties(e, dto);
				dtoList.add(dto);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询合同信息失败");
		}
		return dtoList;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public MchntContractServiceDAO getMchntContractServiceDAO() {
		return mchntContractServiceDAO;
	}

	public void setMchntContractServiceDAO(
			MchntContractServiceDAO mchntContractServiceDAO) {
		this.mchntContractServiceDAO = mchntContractServiceDAO;
	}

}