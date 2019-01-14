package com.huateng.univer.issuer.validPeriodRule.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.product.ValidPeriodRuleDspDTO;
import com.allinfinance.univer.issuer.dto.product.ValidPeriodRuleInfDTO;
import com.allinfinance.univer.issuer.dto.product.ValidPeriodRuleQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.ValidRuleDspDAO;
import com.huateng.framework.ibatis.dao.ValidRuleInfDAO;
import com.huateng.framework.ibatis.model.ValidRuleDsp;
import com.huateng.framework.ibatis.model.ValidRuleDspExample;
import com.huateng.framework.ibatis.model.ValidRuleInf;
import com.huateng.framework.ibatis.model.ValidRuleInfExample;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.issuer.validPeriodRule.dao.ValidPeriodRuleServiceDAO;
import com.huateng.univer.issuer.validPeriodRule.service.ValidPeriodRuleService;

public class ValidPeriodRuleServiceImpl implements ValidPeriodRuleService {

	Logger logger = Logger.getLogger(ValidPeriodRuleServiceImpl.class);
	private PageQueryDAO pageQueryDAO;
	private CommonsDAO commonsDAO;
	private ValidRuleDspDAO validRuleDspDAO;
	private ValidRuleInfDAO validRuleInfDAO;
	private ValidPeriodRuleInfDTO validPeriodRuleInfDTO = new ValidPeriodRuleInfDTO();
	private ValidPeriodRuleDspDTO validPeriodRuleDspDTO = new ValidPeriodRuleDspDTO();
	private ValidPeriodRuleQueryDTO validPeriodRuleQueryDTO = new ValidPeriodRuleQueryDTO();
	private ValidPeriodRuleServiceDAO validPeriodRuleServiceDAO;

	public ValidPeriodRuleServiceDAO getValidPeriodRuleServiceDAO() {
		return validPeriodRuleServiceDAO;
	}

	public void setValidPeriodRuleServiceDAO(
			ValidPeriodRuleServiceDAO validPeriodRuleServiceDAO) {
		this.validPeriodRuleServiceDAO = validPeriodRuleServiceDAO;
	}

	public ValidPeriodRuleInfDTO getValidPeriodRuleInfDTO() {
		return validPeriodRuleInfDTO;
	}

	public void setValidPeriodRuleInfDTO(
			ValidPeriodRuleInfDTO validPeriodRuleInfDTO) {
		this.validPeriodRuleInfDTO = validPeriodRuleInfDTO;
	}

	public ValidPeriodRuleDspDTO getValidPeriodRuleDspDTO() {
		return validPeriodRuleDspDTO;
	}

	public void setValidPeriodRuleDspDTO(
			ValidPeriodRuleDspDTO validPeriodRuleDspDTO) {
		this.validPeriodRuleDspDTO = validPeriodRuleDspDTO;
	}

	public ValidPeriodRuleQueryDTO getValidPeriodRuleQueryDTO() {
		return validPeriodRuleQueryDTO;
	}

	public void setValidPeriodRuleQueryDTO(
			ValidPeriodRuleQueryDTO validPeriodRuleQueryDTO) {
		this.validPeriodRuleQueryDTO = validPeriodRuleQueryDTO;
	}

	public ValidRuleDspDAO getValidRuleDspDAO() {
		return validRuleDspDAO;
	}

	public void setValidRuleDspDAO(ValidRuleDspDAO validRuleDspDAO) {
		this.validRuleDspDAO = validRuleDspDAO;
	}

	public ValidRuleInfDAO getValidRuleInfDAO() {
		return validRuleInfDAO;
	}

	public void setValidRuleInfDAO(ValidRuleInfDAO validRuleInfDAO) {
		this.validRuleInfDAO = validRuleInfDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public PageDataDTO queryValidPeriodRule(
			ValidPeriodRuleQueryDTO validPeriodRuleQueryDTO)
			throws BizServiceException {
		try {
			logger.debug("enter into method : queryValidPeriodRule");
			PageDataDTO pageDataDTO = pageQueryDAO
					.query("VALIDRULEDSP.selectValidRuleList",
							validPeriodRuleQueryDTO);
			logger.debug("out of method : queryValidPeriodRule,result size="
					+ pageDataDTO.getData().size());
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询有效期规则失败");
		}

	}

	public List<ValidRuleDsp> check(ValidPeriodRuleDspDTO validPeriodRuleDspDTO) {
		ValidRuleDspExample example = new ValidRuleDspExample();
		example.createCriteria().andRuleNameEqualTo(
				validPeriodRuleDspDTO.getRuleName().trim()).andEntityIdEqualTo(
				validPeriodRuleDspDTO.getEntityId());
		List<ValidRuleDsp> validRuleDsps = validRuleDspDAO
				.selectByExample(example);
		return validRuleDsps;
	}

	public void insert(ValidPeriodRuleDspDTO validPeriodRuleDspDTO)
			throws BizServiceException {
		try {
			logger.debug("enter into method:insert");
			List<ValidRuleDsp> validRuleDsps = check(validPeriodRuleDspDTO);
			ValidRuleDsp validRuleDsp = new ValidRuleDsp();
			/** 截止日期格式转换 */
			validPeriodRuleDspDTO.setDeadLine(DateUtil
					.getFormatTime(validPeriodRuleDspDTO.getDeadLine()));

			/** 添加有效期规则描述信息 */
			ReflectionUtil.copyProperties(validPeriodRuleDspDTO, validRuleDsp);
			if (null != validRuleDsps && validRuleDsps.size() > 0) {
				ValidRuleDsp ruleDsp = new ValidRuleDsp();
				for (int i = 0; i < validRuleDsps.size(); i++) {
					ruleDsp = validRuleDsps.get(i);
					if (ruleDsp.getTransType().equals(
							validPeriodRuleDspDTO.getTransType())) {
						throw new BizServiceException("有效期规则:"
								+ validPeriodRuleDspDTO.getRuleName()
								+ " 已存在相同的交易类型,请检查");
					}
				}
				validRuleDsp.setRuleDspId(ruleDsp.getRuleDspId());
			} else {
				validRuleDsp.setRuleDspId(commonsDAO
						.getNextValueOfSequence("TB_VALID_RULE_DSP"));
			}
			validRuleDsp.setRuleState(DataBaseConstant.RULE_STATE_NEW);
			validRuleDsp.setCreateTime(DateUtil.getCurrentTime24());
			validRuleDsp.setCreateUser(validPeriodRuleDspDTO.getCreateUser());
			validRuleDsp.setModifyTime(DateUtil.getCurrentTime24());
			validRuleDsp.setModifyUser(validPeriodRuleDspDTO.getCreateUser());
			validRuleDspDAO.insert(validRuleDsp);
			logger.debug("insert validRuleDsp into TB_VALID_DSP & ruleDspId:"
					+ validRuleDsp.getRuleDspId());

			ValidRuleInf validRuleInf;
			List<ValidRuleInf> ruleInfList = new ArrayList<ValidRuleInf>();
			if ("1".equals(validPeriodRuleDspDTO.getTransType())) {
				/** 添加有效期规则金额信息 */
				for (ValidPeriodRuleInfDTO ruleInfDTO : validPeriodRuleDspDTO
						.getValidPeriodRuleInfDTOs()) {
					validRuleInf = new ValidRuleInf();
					validRuleInf.setRuleDspId(validRuleDsp.getRuleDspId());
					validRuleInf.setRuleInfId(commonsDAO
							.getNextValueOfSequence("TB_VALID_RULE_INF"));
					validRuleInf.setMinAmount(Amount
							.getDataBaseAmountForTwoFloat(ruleInfDTO
									.getMinAmount()));
					validRuleInf.setMaxAmount(Amount
							.getDataBaseAmountForTwoFloat(ruleInfDTO
									.getMaxAmount()));
					validRuleInf.setTransType(validPeriodRuleDspDTO
							.getTransType());
					validRuleInf
							.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
					validRuleInf.setDelayMonth(ruleInfDTO.getDelayMonth());
					ruleInfList.add(validRuleInf);
					logger.debug("a new validRuleInf ruleInfId="
							+ validRuleInf.getRuleInfId());
				}
			} else {
				validRuleInf = new ValidRuleInf();
				ReflectionUtil.copyProperties(validPeriodRuleDspDTO
						.getValidPeriodRuleInfDTO(), validRuleInf);
				validRuleInf.setRuleDspId(validRuleDsp.getRuleDspId());
				validRuleInf.setRuleInfId(commonsDAO
						.getNextValueOfSequence("TB_VALID_RULE_INF"));
				validRuleInf.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
				ruleInfList.add(validRuleInf);
				logger.debug("a new validRuleInf ruleInfId="
						+ validRuleInf.getRuleInfId());
			}
			commonsDAO.batchInsert("TB_VALID_RULE_INF.abatorgenerated_insert",
					ruleInfList);
			logger.debug("insert validRuleInf into TB_VALID_Inf & count:"
					+ ruleInfList.size());
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加有效期规则失败");
		}
		logger.debug("out of method insert");
	}

	public void update(ValidPeriodRuleDspDTO validPeriodRuleDspDTO)
			throws BizServiceException {
		logger.debug("enter into method update");
		try {
			/** 截止日期格式转换 */
			validPeriodRuleDspDTO.setDeadLine(DateUtil
					.getFormatTime(validPeriodRuleDspDTO.getDeadLine()));

			/** 更改规则描述信息 */
			ValidRuleDsp validRuleDsp = new ValidRuleDsp();
			ReflectionUtil.copyProperties(validPeriodRuleDspDTO, validRuleDsp);
			validRuleDsp.setModifyTime(DateUtil.getCurrentTime24());
			validRuleDsp.setModifyUser(validPeriodRuleDspDTO.getModifyUser());
			validRuleDspDAO.updateByPrimaryKeySelective(validRuleDsp);
			logger.debug("update a validRuleDsp ruleDspId="
					+ validPeriodRuleDspDTO.getRuleDspId());

			ValidRuleInf validRuleInf;
			List<ValidRuleInf> updateRuleInfList = new ArrayList<ValidRuleInf>();

			if ("1".equals(validPeriodRuleDspDTO.getTransType())) {
				/** 更改规则金额信息 */
				/** 添加有效期规则金额信息 */
				for (ValidPeriodRuleInfDTO ruleInfDTO : validPeriodRuleDspDTO
						.getValidPeriodRuleInfDTOs()) {
					validRuleInf = new ValidRuleInf();
					validRuleInf.setRuleDspId(validPeriodRuleDspDTO
							.getRuleDspId());
					validRuleInf.setRuleInfId(commonsDAO
							.getNextValueOfSequence("TB_VALID_RULE_INF"));
					validRuleInf.setMinAmount(Amount
							.getDataBaseAmountForTwoFloat(ruleInfDTO
									.getMinAmount()));
					validRuleInf.setMaxAmount(Amount
							.getDataBaseAmountForTwoFloat(ruleInfDTO
									.getMaxAmount()));
					validRuleInf.setTransType(validPeriodRuleDspDTO
							.getTransType());
					validRuleInf.setDelayMonth(ruleInfDTO.getDelayMonth());
					validRuleInf
							.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
					updateRuleInfList.add(validRuleInf);
				}
			} else {
				validRuleInf = new ValidRuleInf();
				ReflectionUtil.copyProperties(validPeriodRuleDspDTO
						.getValidPeriodRuleInfDTO(), validRuleInf);
				validRuleInf.setRuleDspId(validRuleDsp.getRuleDspId());
				validRuleInf.setRuleInfId(commonsDAO
						.getNextValueOfSequence("TB_VALID_RULE_INF"));
				validRuleInf.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
				updateRuleInfList.add(validRuleInf);
				logger.debug("a new validRuleInf ruleInfId="
						+ validRuleInf.getRuleInfId());
			}
			ValidRuleInfExample ruleInfExample = new ValidRuleInfExample();
			ruleInfExample.createCriteria().andRuleDspIdEqualTo(
					validPeriodRuleDspDTO.getRuleDspId()).andTransTypeEqualTo(
					validPeriodRuleDspDTO.getOldTransType());
			validRuleInfDAO.deleteByExample(ruleInfExample);
			logger.debug("delete validRuleInf from TB_VALID_Inf where DspId="
					+ validPeriodRuleDspDTO.getRuleDspId());
			if (null != updateRuleInfList && updateRuleInfList.size() > 0) {
				commonsDAO.batchInsert(
						"TB_VALID_RULE_INF.abatorgenerated_insert",
						updateRuleInfList);
				logger.debug("insert validRuleInf into TB_VALID_Inf & count:"
						+ updateRuleInfList.size());
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("编辑有效期规则失败");
		}
		logger.debug("out of method update");
	}

	public void operate(List<ValidPeriodRuleDspDTO> ruleDspList)
			throws BizServiceException {
		try {
			logger.debug("enter into method operate");
			List<ValidRuleDsp> dsp = new ArrayList<ValidRuleDsp>();
			ValidRuleDsp validRuleDsp;
			if (null != ruleDspList && ruleDspList.size() > 0) {
				for (int i = 0; i < ruleDspList.size(); i++) {
					validRuleDsp = new ValidRuleDsp();
					ReflectionUtil.copyProperties(ruleDspList.get(i),
							validRuleDsp);
					dsp.add(validRuleDsp);
				}
			}
			commonsDAO
					.batchUpdate(
							"TB_VALID_RULE_DSP.abatorgenerated_updateByPrimaryKeySelective",
							dsp);
			logger.debug("update validRuleDsp from   TB_VALID_Dsp  count:"
					+ dsp.size());
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("操作有效期规则失败");
		}
		logger.debug("out of method operate");
	}

	public ValidPeriodRuleQueryDTO view(ValidPeriodRuleDspDTO dsp)
			throws BizServiceException {
		logger.debug("enter into method view");
		try {
			// ValidRuleDsp
			// validRuleDsp=validRuleDspDAO.selectByPrimaryKey(dsp.getRuleDspId());
			validPeriodRuleDspDTO = validPeriodRuleServiceDAO
					.getValidPeriodRuleDspDTO(dsp);
			// ReflectionUtil.copyProperties(validRuleDsp,validPeriodRuleDspDTO);
			validPeriodRuleQueryDTO
					.setValidPeriodRuleDspDTO(validPeriodRuleDspDTO);
			logger.debug("validPeriodRUleDspDTO:" + validPeriodRuleDspDTO);

			ValidRuleInfExample example = new ValidRuleInfExample();
			example.createCriteria().andRuleDspIdEqualTo(dsp.getRuleDspId())
					.andTransTypeEqualTo(dsp.getTransType())
					.andDataStateEqualTo("1");
			example.setOrderByClause("RULE_INF_ID");
			List<ValidRuleInf> validRuleInfs = validRuleInfDAO
					.selectByExample(example);
			List<ValidPeriodRuleInfDTO> validPeriodRuleInfDTOs = new ArrayList<ValidPeriodRuleInfDTO>();
			if (null != validRuleInfs && validRuleInfs.size() > 0) {
				for (int i = 0; i < validRuleInfs.size(); i++) {
					if ("1".equals(validRuleInfs.get(i).getTransType())) {
						ValidPeriodRuleInfDTO validPeriodRuleInfDTO = new ValidPeriodRuleInfDTO();
						ReflectionUtil.copyProperties(validRuleInfs.get(i),
								validPeriodRuleInfDTO);
						validPeriodRuleInfDTO.setMinAmount(Amount
								.getReallyAmount(validPeriodRuleInfDTO
										.getMinAmount()));
						validPeriodRuleInfDTO.setMaxAmount(Amount
								.getReallyAmount(validPeriodRuleInfDTO
										.getMaxAmount()));
						validPeriodRuleInfDTOs.add(validPeriodRuleInfDTO);
						logger.debug("validPeriodRUleInfDTO:"
								+ validPeriodRuleInfDTO);
					} else {
						ValidPeriodRuleInfDTO validPeriodRuleInfDTO = new ValidPeriodRuleInfDTO();
						ReflectionUtil.copyProperties(validRuleInfs.get(i),
								validPeriodRuleInfDTO);
						validPeriodRuleInfDTOs.add(validPeriodRuleInfDTO);
						logger.debug("validPeriodRUleInfDTO:"
								+ validPeriodRuleInfDTO);
					}
				}
				logger.debug("validPeriodRUleInfDTO count :"
						+ validRuleInfs.size());
			}
			validPeriodRuleQueryDTO.setList(validPeriodRuleInfDTOs);
		} catch (Exception e) {
			logger.debug(e);
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看有效期规则失败");
		}
		logger.debug("out of method view");
		return validPeriodRuleQueryDTO;
	}
}
