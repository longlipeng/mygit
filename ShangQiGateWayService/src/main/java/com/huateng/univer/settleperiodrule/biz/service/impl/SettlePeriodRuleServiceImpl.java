package com.huateng.univer.settleperiodrule.biz.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.settleperiodrule.dto.SettlePeriodRuleDTO;
import com.allinfinance.univer.settleperiodrule.dto.SettlePeriodRuleQueryDTO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SettlePeriodRuleDAO;
import com.huateng.framework.ibatis.model.SettlePeriodRule;
import com.huateng.framework.ibatis.model.SettlePeriodRuleExample;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.settleperiodrule.Constants.SettleConstants;
import com.huateng.univer.settleperiodrule.biz.service.SettlePeriodRuleService;

public class SettlePeriodRuleServiceImpl implements SettlePeriodRuleService {
    Logger logger = Logger.getLogger(SettlePeriodRuleServiceImpl.class);
    /** 分页DAO */
    private PageQueryDAO pageQueryDAO;
    /** 公共dao */
    private CommonsDAO commonsDAO;
    private SettlePeriodRuleDAO settlePeriodRuleDAO;

    public void insert(SettlePeriodRuleDTO dto) throws BizServiceException {
        try {/*
              * if(checkName(dto)>0){ throw new BizServiceException("不能添加相同的规则名！"); }
              */
            // 当为分段结算时，后台拼接结算点
            if (dto.getRuleType().equals(SettleConstants.RULE_TYPE_THREE)) {
                dto = getsettleDateBufDTO(dto);
            }
            SettlePeriodRule rule = new SettlePeriodRule();
            ReflectionUtil.copyProperties(dto, rule);
            rule.setRuleNo(commonsDAO.getNextValueOfSequence("TB_SETTLE_PERIOD_RULE"));
            if (null != rule.getAmountMin() || !"".equals(rule.getAmountMin().trim())) {
                rule.setAmountMin(Amount.getDataBaseAmount(rule.getAmountMin()));
            }
            rule.setCreateTime(DateUtil.getCurrentDateStr());
            rule.setCreateUser(dto.getLoginUserId());
            rule.setModifyTime(DateUtil.getCurrentDateStr());
            rule.setModifyUser(dto.getLoginUserId());
            rule.setState("1");
            settlePeriodRuleDAO.insert(rule);
        } catch (BizServiceException e1) {
            throw e1;

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("添加结算周期规则失败！");
        }

    }

    public PageDataDTO query(SettlePeriodRuleQueryDTO dto) throws BizServiceException {
        try {
            PageDataDTO pd = pageQueryDAO.query("SETTLERULE.selectSettleRuleByDTO", dto);
            return pd;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询结算周期规则失败！");
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

    public SettlePeriodRuleDAO getSettlePeriodRuleDAO() {
        return settlePeriodRuleDAO;
    }

    public void setSettlePeriodRuleDAO(SettlePeriodRuleDAO settlePeriodRuleDAO) {
        this.settlePeriodRuleDAO = settlePeriodRuleDAO;
    }

    public SettlePeriodRuleDTO editInit(SettlePeriodRuleDTO dto) throws BizServiceException {
        try {
            SettlePeriodRule rule = settlePeriodRuleDAO.selectByPrimaryKey(dto.getRuleNo());
            if (null != rule.getAmountMin() && !"".equals(rule.getAmountMin().trim())) {
                rule.setAmountMin(Amount.getReallyAmount(rule.getAmountMin()));
            }
            SettlePeriodRuleDTO ruleDTO = new SettlePeriodRuleDTO();
            ReflectionUtil.copyProperties(rule, ruleDTO);
            // 分段结算时，拆分settle_date_buf字符串，获取前台信息
            if (ruleDTO.getRuleType() != null && SettleConstants.RULE_TYPE_THREE.equals(ruleDTO.getRuleType())) {
                ruleDTO = resolveDateBufDTO(ruleDTO);
            }
            return ruleDTO;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("编辑周期规则失败！");
        }
    }

    public void update(SettlePeriodRuleDTO dto) throws BizServiceException {
        try { /*
               * if(checkName(dto)>0){ throw new BizServiceException("不能更新为已存在的规则名！"); }
               */
            // 当为分段结算时，后台拼接结算点
            if (dto.getRuleType().equals(SettleConstants.RULE_TYPE_THREE)) {
                dto = getsettleDateBufDTO(dto);
            }
            SettlePeriodRule rule = settlePeriodRuleDAO.selectByPrimaryKey(dto.getRuleNo());
            ReflectionUtil.copyProperties(dto, rule);
            if (null != rule.getAmountMin() || !"".equals(rule.getAmountMin().trim())) {
                rule.setAmountMin(Amount.getDataBaseAmount(rule.getAmountMin()));
            }
            rule.setModifyUser(dto.getLoginUserId());
            rule.setModifyTime(DateUtil.getCurrentDateStr());
            settlePeriodRuleDAO.updateByPrimaryKey(rule);
        }// catch(BizServiceException e1){
        // throw e1;

        // }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("更新周期规则失败！");
        }
    }

    public void delete(SettlePeriodRuleDTO dto) throws BizServiceException {
        try {
            List<SettlePeriodRuleDTO> dtoList = dto.getSettlePeriodRuleDTOList();
            List<SettlePeriodRule> ruleList = new ArrayList<SettlePeriodRule>();
            for (SettlePeriodRuleDTO ruleDTO : dtoList) {
                SettlePeriodRule rule = new SettlePeriodRule();
                rule.setRuleNo(ruleDTO.getRuleNo());
                rule.setState("0");
                ruleList.add(rule);
            }
            commonsDAO.batchUpdate("TB_SETTLE_PERIOD_RULE.abatorgenerated_updateByPrimaryKeySelective", ruleList);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("删除周期规则失败！");
        }
    }

    public void modify(SettlePeriodRuleDTO dto) throws BizServiceException {
        try {
            List<SettlePeriodRuleDTO> dtoList = dto.getSettlePeriodRuleDTOList();
            List<SettlePeriodRule> ruleList = new ArrayList<SettlePeriodRule>();
            for (SettlePeriodRuleDTO ruleDTO : dtoList) {
                SettlePeriodRule rule = new SettlePeriodRule();
                rule.setRuleNo(ruleDTO.getRuleNo());
                rule.setState("2");
                ruleList.add(rule);
            }
            commonsDAO.batchUpdate("TB_SETTLE_PERIOD_RULE.abatorgenerated_updateByPrimaryKeySelective", ruleList);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("启用周期规则失败！");
        }

    }

    public SettlePeriodRuleDTO view(SettlePeriodRuleDTO dto) throws BizServiceException {
        try {
            SettlePeriodRule rule = settlePeriodRuleDAO.selectByPrimaryKey(dto.getRuleNo());
            ReflectionUtil.copyProperties(rule, dto);
            // 分段结算时，拆分settle_date_buf字符串，获取前台信息
            if (dto.getRuleType() != null && SettleConstants.RULE_TYPE_THREE.equals(dto.getRuleType())) {
                dto = resolveDateBufDTO(dto);
            }
            return dto;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查看周期规则失败！");
        }
    }

    public int checkName(SettlePeriodRuleDTO dto) throws BizServiceException {
        List<String> strList = new ArrayList<String>();
        strList.add("1");
        strList.add("2");
        SettlePeriodRuleExample example = new SettlePeriodRuleExample();
        if (dto.getRuleNo() == null || "".equals(dto.getRuleNo())) {
            example.createCriteria().andEntityIdEqualTo(dto.getEntityId()).andRuleNameEqualTo(dto.getRuleName())
                    .andStateIn(strList);
        } else {
            example.createCriteria().andRuleNoNotEqualTo(dto.getRuleNo()).andEntityIdEqualTo(dto.getEntityId())
                    .andRuleNameEqualTo(dto.getRuleName()).andStateIn(strList);
        }
        return settlePeriodRuleDAO.selectByExample(example).size();
    }

    // 计算得首次结算日期
    public String getSettleDateNext(String contractStartDate, String ruleNo) throws BizServiceException {
        String settleDateNext = "";
        try {
            SettlePeriodRule settlePeriodRule = settlePeriodRuleDAO.selectByPrimaryKey(ruleNo);
            if (null != settlePeriodRule) {
                // 周期类型 1、固定 0、非固定 2.混合
                if ("1".equals(settlePeriodRule.getRuleType()) || "2".equals(settlePeriodRule.getRuleType())) {
                    // 周期单位 D 按天数 W 按周数 M 按月数
                    // 如果指定周期和指定日 为空，首次结算日期为：合同生效日期+周期-1
                    if ("D".equals(settlePeriodRule.getPeriodType())) {
                        if (null == settlePeriodRule.getAppointPeriod()
                                || "".equals(settlePeriodRule.getAppointPeriod())) {
                            settleDateNext = DateUtil.date2String(DateUtil.addDay(DateUtil
                                    .string2Dateyyyymmdd(contractStartDate), Integer.parseInt(settlePeriodRule
                                    .getPeriod()) - 1));
                        } else {
                            // 若指定周期为0 则 首次结算日期为：当天
                            if ("0".equals(settlePeriodRule.getAppointPeriod())) {
                                settleDateNext = contractStartDate;
                            } else {
                                settleDateNext = DateUtil.date2String(DateUtil.addDay(DateUtil
                                        .string2Dateyyyymmdd(contractStartDate), settlePeriodRule.getAppointPeriod()));
                            }
                        }
                    } else if ("W".equals(settlePeriodRule.getPeriodType())) {
                        // 周期
                        int period = Integer.parseInt(settlePeriodRule.getPeriod()) * 7;
                        if ((null == settlePeriodRule.getAppointPeriod() || "".equals(settlePeriodRule
                                .getAppointPeriod()))
                                && (null == settlePeriodRule.getAppointDay() || "".equals(settlePeriodRule
                                        .getAppointDay()))) {
                            settleDateNext = DateUtil.date2String(DateUtil.addDay(DateUtil
                                    .string2Dateyyyymmdd(contractStartDate), period - 1));
                        } else {
                            // 取得合同生效日当天是周几
                            Calendar c = Calendar.getInstance();
                            c.setTime(DateUtil.string2Dateyyyymmdd(contractStartDate));
                            int week = c.get(Calendar.DAY_OF_WEEK) - 1;
                            int appointPeriod = settlePeriodRule.getAppointPeriod() * 7;
                            int appointDay = settlePeriodRule.getAppointDay();
                            // 若 指定周期为0 and 指定日小于等于合同生效日 的星期 ， 则
                            // 首次结算日期为：合同生效日期+周期
                            if ("0".equals(settlePeriodRule.getAppointPeriod()) && appointDay == 0) {
                                settleDateNext = DateUtil.date2String(DateUtil.addDay(DateUtil
                                        .string2Dateyyyymmdd(contractStartDate), 7 - week));
                            } else if (0 == settlePeriodRule.getAppointPeriod() && appointDay < week) {
                                settleDateNext = DateUtil.date2String(DateUtil.addDay(DateUtil
                                        .string2Dateyyyymmdd(contractStartDate), period - (week - appointDay)));
                            } else if ("0".equals(settlePeriodRule.getAppointPeriod()) && appointDay == week) {
                                settleDateNext = contractStartDate;
                            } else {
                                settleDateNext = DateUtil.date2String(DateUtil.addDay(DateUtil
                                        .string2Dateyyyymmdd(contractStartDate), appointPeriod + (appointDay - week)));
                            }
                        }
                    } else {
                        if ((null == settlePeriodRule.getAppointPeriod() || "".equals(settlePeriodRule
                                .getAppointPeriod()))
                                && (null == settlePeriodRule.getAppointDay() || "".equals(settlePeriodRule
                                        .getAppointDay()))) {
                            Date s = DateUtil.addMonth(DateUtil.string2Dateyyyymmdd(contractStartDate), Integer
                                    .parseInt(settlePeriodRule.getPeriod()));
                            settleDateNext = DateUtil.date2String(DateUtil.addDay(s, -1));
                        } else {
                            Calendar c = Calendar.getInstance();
                            c.setTime(DateUtil.string2Dateyyyymmdd(contractStartDate));
                            int day = c.get(Calendar.DAY_OF_MONTH);
                            int appointDay = settlePeriodRule.getAppointDay();
                            // 若 指定周期为0 and 指定日小于等于合同生效日 ， 则 首次结算日期为：合同生效日期+周期
                            // 的指定日
                            if ("0".equals(settlePeriodRule.getAppointPeriod()) && appointDay < day && appointDay != 0) {
                                Date s = DateUtil.addMonth(DateUtil.string2Dateyyyymmdd(contractStartDate), Integer
                                        .parseInt(settlePeriodRule.getPeriod()));
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(s);
                                calendar.set(Calendar.DAY_OF_MONTH, appointDay);
                                settleDateNext = DateUtil.date2String(calendar.getTime());
                            } else if ("0".equals(settlePeriodRule.getAppointPeriod()) && appointDay == day) {
                                settleDateNext = contractStartDate;
                            } else {
                                Date s = DateUtil.addMonth(DateUtil.string2Dateyyyymmdd(contractStartDate),
                                        settlePeriodRule.getAppointPeriod());
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(s);
                                // 如果 指定日大于当月的天数 则取 当月的最后一天
                                if (DateUtil.setMonthLastDay(s).getDate() < appointDay || appointDay == 0) {
                                    settleDateNext = DateUtil.date2String(DateUtil.countCardValidate(s, 0));
                                } else {
                                    calendar.set(Calendar.DAY_OF_MONTH, appointDay);
                                    settleDateNext = DateUtil.date2String(calendar.getTime());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {

            this.logger.error(e.getMessage());
            throw new BizServiceException("添加首次结算日失败");
        }
        return settleDateNext;
    }

    /**
     * 添加默认四条结算周期规则 分别为：每天结算，周日结算（结算周一到周日的交易），周四结算（结算上周五到本周四的交易），月末结算（结算1号到月末的交易）。 这四条初始数据状态为已启用
     */

    public void initInsert(String entityId, String userId) throws BizServiceException {
        List<SettlePeriodRule> settlePeriodRules = new ArrayList<SettlePeriodRule>();
        try {
            SettlePeriodRule rule1 = new SettlePeriodRule();
            rule1.setRuleNo(commonsDAO.getNextValueOfSequence("TB_SETTLE_PERIOD_RULE"));
            rule1.setRuleName("每天结算");
            rule1.setRuleType("1");
            rule1.setEntityId(entityId);
            rule1.setState("2");
            rule1.setPeriod("1");
            rule1.setPeriodType("D");
            rule1.setCreateUser(userId);
            rule1.setCreateTime(DateUtil.getCurrentDateStr());
            rule1.setModifyUser(userId);
            rule1.setModifyTime(DateUtil.getCurrentDateStr());
            settlePeriodRules.add(rule1);
            SettlePeriodRule rule2 = new SettlePeriodRule();
            rule2.setRuleNo(commonsDAO.getNextValueOfSequence("TB_SETTLE_PERIOD_RULE"));
            rule2.setRuleName("周日结算");
            rule2.setRuleType("1");
            rule2.setEntityId(entityId);
            rule2.setState("2");
            rule2.setPeriod("1");
            rule2.setPeriodType("W");
            rule2.setAppointPeriod(0);
            rule2.setAppointDay(0);
            rule2.setCreateUser(userId);
            rule2.setCreateTime(DateUtil.getCurrentDateStr());
            rule2.setModifyUser(userId);
            rule2.setModifyTime(DateUtil.getCurrentDateStr());
            settlePeriodRules.add(rule2);
            SettlePeriodRule rule3 = new SettlePeriodRule();
            rule3.setRuleNo(commonsDAO.getNextValueOfSequence("TB_SETTLE_PERIOD_RULE"));
            rule3.setRuleName("周四结算");
            rule3.setRuleType("1");
            rule3.setEntityId(entityId);
            rule3.setState("2");
            rule3.setPeriod("1");
            rule3.setPeriodType("W");
            rule3.setAppointPeriod(0);
            rule3.setAppointDay(4);
            rule3.setCreateUser(userId);
            rule3.setCreateTime(DateUtil.getCurrentDateStr());
            rule3.setModifyUser(userId);
            rule3.setModifyTime(DateUtil.getCurrentDateStr());
            settlePeriodRules.add(rule3);
            SettlePeriodRule rule4 = new SettlePeriodRule();
            rule4.setRuleNo(commonsDAO.getNextValueOfSequence("TB_SETTLE_PERIOD_RULE"));
            rule4.setRuleName("月末结算");
            rule4.setRuleType("1");
            rule4.setEntityId(entityId);
            rule4.setState("2");
            rule4.setPeriod("1");
            rule4.setPeriodType("M");
            rule4.setAppointPeriod(0);
            rule4.setAppointDay(0);
            rule4.setCreateUser(userId);
            rule4.setCreateTime(DateUtil.getCurrentDateStr());
            rule4.setModifyUser(userId);
            rule4.setModifyTime(DateUtil.getCurrentDateStr());
            settlePeriodRules.add(rule4);
            commonsDAO.batchInsert("TB_SETTLE_PERIOD_RULE.abatorgenerated_insert", settlePeriodRules);
        } catch (Exception e) {

            this.logger.error(e.getMessage());
            throw new BizServiceException("添加初始数据失败");
        }
    }

    /**
     * 
     * 拼接分段结算周期字符串<br>
     * 当分段结算时，拼接前台传入的时间参数
     * 
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public SettlePeriodRuleDTO getsettleDateBufDTO(SettlePeriodRuleDTO dto) throws BizServiceException {

        List<String> list = new ArrayList<String>();

        // 根据分段数添加日期值
        if (dto.getPartAmount() != null && !"".equals(dto.getPartAmount())) {
            String partAmount = dto.getPartAmount();
            int i = 0;
            if (partAmount != null && !"".equals(partAmount)) {
                i = Integer.parseInt(partAmount);
            }
            if (i == 2) {
                list.add(dto.getSettleOne());
            } else if (i == 3) {
                list.add(dto.getSettleOne());
                list.add(dto.getSettleTwo());
            } else if (i == 4) {
                list.add(dto.getSettleOne());
                list.add(dto.getSettleTwo());
                list.add(dto.getSettleThree());
            } else if (i == 5) {
                list.add(dto.getSettleOne());
                list.add(dto.getSettleTwo());
                list.add(dto.getSettleThree());
                list.add(dto.getSettleFour());
            }
            // 排序
            Collections.sort(list);
            StringBuffer settleDateBuf = new StringBuffer();
            for (String str : list) {
                settleDateBuf.append(str);
                settleDateBuf.append("|");
            }
            if (settleDateBuf.length() > 0) {
                settleDateBuf.delete(settleDateBuf.length() - 1, settleDateBuf.length());
            }
            dto.setSettleDateBuf(settleDateBuf.toString().trim());
            return dto;
        } else {
            this.logger.error("页面数据传入后台失败");
            throw new BizServiceException("页面数据传入后台失败");
        }
    }

    /**
     * 
     * 拆分 settle_Date_Buf<br>
     * 〈功能详细描述〉
     * 
     * @param dto
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public SettlePeriodRuleDTO resolveDateBufDTO(SettlePeriodRuleDTO dto) throws BizServiceException {
        String settleDateBuf = dto.getSettleDateBuf();
        if (settleDateBuf != null && settleDateBuf.length() > 0) {
            String[] date = settleDateBuf.split("\\|");
            int i = date.length;
            dto.setPartAmount(String.valueOf(i + 1));
            if (i == 1) {
                dto.setSettleOne(date[0]);
            } else if (i == 2) {
                dto.setSettleOne(date[0]);
                dto.setSettleTwo(date[1]);
            } else if (i == 3) {
                dto.setSettleOne(date[0]);
                dto.setSettleTwo(date[1]);
                dto.setSettleThree(date[2]);
            } else if (i == 4) {
                dto.setSettleOne(date[0]);
                dto.setSettleTwo(date[1]);
                dto.setSettleThree(date[2]);
                dto.setSettleFour(date[3]);
            }
        } else {
            dto.setPartAmount("1");
        }
        return dto;
    }

}
