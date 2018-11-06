package com.huateng.univer.servicefeerule.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.servicefeerule.dto.ServiceFeeRuleDTO;
import com.allinfinance.univer.servicefeerule.dto.ServiceFeeRuleQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SerivceFeeRuleDAO;
import com.huateng.framework.ibatis.model.SerivceFeeRule;
import com.huateng.framework.ibatis.model.SerivceFeeRuleExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.servicefeerule.biz.service.ServiceFeeRuleService;

public class ServiceFeeRuleServiceImpl implements ServiceFeeRuleService {
	Logger logger = Logger.getLogger(ServiceFeeRuleServiceImpl.class);
	/** 分页DAO */
	private PageQueryDAO pageQueryDAO;
	/** 公共dao */
	private CommonsDAO commonsDAO;
	private SerivceFeeRuleDAO serviceFeeRuleDAO;

	public PageDataDTO query(ServiceFeeRuleQueryDTO dto)
			throws BizServiceException {
		try {
			return pageQueryDAO.query("ServiceFeeRule.selectRulesByDTOs", dto);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询计算规则失败！");
		}
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public void insert(ServiceFeeRuleDTO dto) throws BizServiceException {
		try {
			if (checkName(dto) > 0) {
				throw new BizServiceException("不能添加相同名字的计算规则!");
			}
			List<ServiceFeeRuleDTO> serviceFeeRuleList = dto
					.getServiceFeeRuleDTOList();
			List<SerivceFeeRule> ruleList = new ArrayList<SerivceFeeRule>();
			String ruleNo = commonsDAO
					.getNextValueOfSequence("TB_SERIVCE_FEE_RULE");
			int i = 1;
			for (ServiceFeeRuleDTO ruleDTO : serviceFeeRuleList) {
				SerivceFeeRule rule = new SerivceFeeRule();
				ReflectionUtil.copyProperties(ruleDTO, rule);
				rule.setRuleNo(ruleNo);
				rule.setRuleStep(String.valueOf(i++));
				rule.setState(DataBaseConstant.RULE_STATE_NEW);
				rule.setCreateTime(DateUtil.getCurrentTime());
				rule.setCreateUser(dto.getLoginUserId());
				rule.setModifyTime(DateUtil.getCurrentTime());
				rule.setModifyUser(dto.getLoginUserId());
				ruleList.add(rule);
			}
			commonsDAO.batchInsert(
					"TB_SERIVCE_FEE_RULE.abatorgenerated_insert", ruleList);
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("新增计算规则失败！");
		}
	}

	public List<ServiceFeeRuleDTO> edit(ServiceFeeRuleDTO dto)
			throws BizServiceException {
		try {
			List<ServiceFeeRuleDTO> serviceFeeRuleDTOList = new ArrayList<ServiceFeeRuleDTO>();
			SerivceFeeRuleExample example = new SerivceFeeRuleExample();
			example.createCriteria().andRuleNoEqualTo(dto.getRuleNo())
					.andStateEqualTo(DataBaseConstant.RULE_STATE_NEW);
			example.setOrderByClause("RULE_STEP");
			List<SerivceFeeRule> ruleList = serviceFeeRuleDAO
					.selectByExample(example);
			for (SerivceFeeRule rule : ruleList) {
				ServiceFeeRuleDTO ruleDTO = new ServiceFeeRuleDTO();
				ReflectionUtil.copyProperties(rule, ruleDTO);
				ruleDTO.setAmountStart(String.valueOf(new BigDecimal(ruleDTO
						.getAmountStart()).divide(new BigDecimal(100))));
				ruleDTO.setAmountEnd(String.valueOf(new BigDecimal(ruleDTO
						.getAmountEnd()).divide(new BigDecimal(100))));
				ruleDTO.setFeeMin(String.valueOf(new BigDecimal(ruleDTO
						.getFeeMin()).divide(new BigDecimal(100))));
				ruleDTO.setFeeMax(String.valueOf(new BigDecimal(ruleDTO
						.getFeeMax()).divide(new BigDecimal(100))));
				serviceFeeRuleDTOList.add(ruleDTO);
			}
			return serviceFeeRuleDTOList;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("编辑计算规则失败！");
		}
	}

	public SerivceFeeRuleDAO getServiceFeeRuleDAO() {
		return serviceFeeRuleDAO;
	}

	public void setServiceFeeRuleDAO(SerivceFeeRuleDAO serviceFeeRuleDAO) {
		this.serviceFeeRuleDAO = serviceFeeRuleDAO;
	}

	public void update(ServiceFeeRuleDTO dto) throws BizServiceException {
		try {
			if (checkName(dto) > 0) {
				throw new BizServiceException("不能更新为已存在的规则名称!");
			}
			SerivceFeeRuleExample example = new SerivceFeeRuleExample();
			example.createCriteria().andRuleNoEqualTo(dto.getRuleNo());
			List<SerivceFeeRule> ruleList = serviceFeeRuleDAO
					.selectByExample(example);
			if (ruleList != null) {
				commonsDAO
						.batchDelete(
								"TB_SERIVCE_FEE_RULE.abatorgenerated_deleteByPrimaryKey",
								ruleList);
			}
			List<ServiceFeeRuleDTO> dtoList = dto.getServiceFeeRuleDTOList();
			List<SerivceFeeRule> feeRuleList = new ArrayList<SerivceFeeRule>();
			for (ServiceFeeRuleDTO o : dtoList) {
				SerivceFeeRule rule = new SerivceFeeRule();
				ReflectionUtil.copyProperties(o, rule);
				rule.setState(DataBaseConstant.RULE_STATE_NEW);
				rule.setCreateTime(DateUtil.getCurrentTime());
				rule.setCreateUser(dto.getLoginUserId());
				rule.setModifyTime(DateUtil.getCurrentTime());
				rule.setModifyUser(dto.getLoginUserId());
				feeRuleList.add(rule);
			}
			commonsDAO.batchInsert(
					"TB_SERIVCE_FEE_RULE.abatorgenerated_insert", feeRuleList);
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新计算规则失败！");
		}
	}

	public void modify(ServiceFeeRuleDTO dto) throws BizServiceException {
		try {
			List<ServiceFeeRuleDTO> dtoList = dto.getServiceFeeRuleDTOList();
			List<SerivceFeeRule> enableList = new ArrayList<SerivceFeeRule>();
			for (ServiceFeeRuleDTO o : dtoList) {
				SerivceFeeRuleExample example = new SerivceFeeRuleExample();
				example.createCriteria().andRuleNoEqualTo(o.getRuleNo())
						.andStateEqualTo(DataBaseConstant.RULE_STATE_NEW);
				List<SerivceFeeRule> ruleList = serviceFeeRuleDAO
						.selectByExample(example);
				enableList.addAll(ruleList);
			}
			for (SerivceFeeRule rule : enableList) {
				rule.setState(DataBaseConstant.RULE_STATE_ENABLE);
			}
			commonsDAO
					.batchUpdate(
							"TB_SERIVCE_FEE_RULE.abatorgenerated_updateByPrimaryKeySelective",
							enableList);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("启用计算规则失败！");
		}

	}

	public void delete(ServiceFeeRuleDTO dto) throws BizServiceException {
		try {
			List<ServiceFeeRuleDTO> dtoList = dto.getServiceFeeRuleDTOList();
			List<SerivceFeeRule> enableList = new ArrayList<SerivceFeeRule>();
			for (ServiceFeeRuleDTO o : dtoList) {
				SerivceFeeRuleExample example = new SerivceFeeRuleExample();
				example.createCriteria().andRuleNoEqualTo(o.getRuleNo())
						.andStateEqualTo(DataBaseConstant.RULE_STATE_NEW);
				List<SerivceFeeRule> ruleList = serviceFeeRuleDAO
						.selectByExample(example);
				enableList.addAll(ruleList);
			}
			for (SerivceFeeRule rule : enableList) {
				rule.setState(DataBaseConstant.RULE_STATE_DELETE);
			}
			commonsDAO
					.batchUpdate(
							"TB_SERIVCE_FEE_RULE.abatorgenerated_updateByPrimaryKeySelective",
							enableList);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除计算规则失败！");
		}
	}

	public int checkName(ServiceFeeRuleDTO dto) {
		List<String> stateList = new ArrayList<String>();
		stateList.add(DataBaseConstant.RULE_STATE_NEW);
		stateList.add(DataBaseConstant.RULE_STATE_ENABLE);
		List<SerivceFeeRule> ruleList = new ArrayList<SerivceFeeRule>();
		SerivceFeeRuleExample example = new SerivceFeeRuleExample();
		if (dto.getRuleNo() == null || "".equals(dto.getRuleNo())) {
			example.createCriteria().andStateIn(stateList).andRuleNameEqualTo(
					dto.getRuleName()).andEntityIdEqualTo(dto.getEntityId());
		} else {
			example.createCriteria().andStateIn(stateList).andRuleNameEqualTo(
					dto.getRuleName()).andRuleNoNotEqualTo(dto.getRuleNo())
					.andEntityIdEqualTo(dto.getEntityId());
		}
		ruleList = serviceFeeRuleDAO.selectByExample(example);
		if (ruleList == null) {
			return 0;
		} else {
			return ruleList.size();
		}
	}
}