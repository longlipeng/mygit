package com.allinfinance.univer.settleperiodrule.dto;

import com.allinfinance.framework.dto.BaseDTO;

import java.util.List;
public class SettlePeriodRuleDTO extends BaseDTO{
	 private String ruleNo;
	 private String ruleName;
	 private String ruleDesc;
	 private String ruleType;
	 private String entityId;
	 private String state;
	 private String period;
	 private String periodType;
	 private String amountMin;
	 private String flagWeekend;
	 private String flagMonthend;
	 private String datePerMonth;
	 private String appointPeriod;
	 private String appointDay;
	 /**
	  * 分段数
	  */
	 private String partAmount;
	 /**
	  * 结算时间点1
	  */
	 private String settleOne;
	 /**
	  * 结算点2
	  */
	 private String settleTwo;
	 /**
      * 结算点3
      */
	 private String settleThree;
	 /**
      * 结算点4
      */
	 private String settleFour;
	 /**
	  * 分段结算日期Buf
	  */
	 private String settleDateBuf;
	
    /**
     * @return the settleDateBuf
     */
    public String getSettleDateBuf() {
        return settleDateBuf;
    }
    /**
     * @param settleDateBuf the settleDateBuf to set
     */
    public void setSettleDateBuf(String settleDateBuf) {
        this.settleDateBuf = settleDateBuf;
    }
    private List<SettlePeriodRuleDTO> settlePeriodRuleDTOList;
	public String getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getPeriodType() {
		return periodType;
	}
	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	public String getAmountMin() {
		return amountMin;
	}
	public void setAmountMin(String amountMin) {
		this.amountMin = amountMin;
	}
	public String getFlagWeekend() {
		return flagWeekend;
	}
	public void setFlagWeekend(String flagWeekend) {
		this.flagWeekend = flagWeekend;
	}
	public String getFlagMonthend() {
		return flagMonthend;
	}
	public void setFlagMonthend(String flagMonthend) {
		this.flagMonthend = flagMonthend;
	}
	public String getDatePerMonth() {
		return datePerMonth;
	}
	public void setDatePerMonth(String datePerMonth) {
		this.datePerMonth = datePerMonth;
	}
	public List<SettlePeriodRuleDTO> getSettlePeriodRuleDTOList() {
		return settlePeriodRuleDTOList;
	}
	public void setSettlePeriodRuleDTOList(
			List<SettlePeriodRuleDTO> settlePeriodRuleDTOList) {
		this.settlePeriodRuleDTOList = settlePeriodRuleDTOList;
	}
	public String getAppointPeriod() {
		return appointPeriod;
	}
	public void setAppointPeriod(String appointPeriod) {
		this.appointPeriod = appointPeriod;
	}
	public String getAppointDay() {
		return appointDay;
	}
	public void setAppointDay(String appointDay) {
		this.appointDay = appointDay;
	}
	 /**
     * @return the partAmount
     */
    public String getPartAmount() {
        return partAmount;
    }
    /**
     * @param partAmount the partAmount to set
     */
    public void setPartAmount(String partAmount) {
        this.partAmount = partAmount;
    }
    /**
     * @return the settleOne
     */
    public String getSettleOne() {
        return settleOne;
    }
    /**
     * @param settleOne the settleOne to set
     */
    public void setSettleOne(String settleOne) {
        this.settleOne = settleOne;
    }
    /**
     * @return the settleTwo
     */
    public String getSettleTwo() {
        return settleTwo;
    }
    /**
     * @param settleTwo the settleTwo to set
     */
    public void setSettleTwo(String settleTwo) {
        this.settleTwo = settleTwo;
    }
    /**
     * @return the settleThree
     */
    public String getSettleThree() {
        return settleThree;
    }
    /**
     * @param settleThree the settleThree to set
     */
    public void setSettleThree(String settleThree) {
        this.settleThree = settleThree;
    }
    /**
     * @return the settleFour
     */
    public String getSettleFour() {
        return settleFour;
    }
    /**
     * @param settleFour the settleFour to set
     */
    public void setSettleFour(String settleFour) {
        this.settleFour = settleFour;
    }
	 
}
