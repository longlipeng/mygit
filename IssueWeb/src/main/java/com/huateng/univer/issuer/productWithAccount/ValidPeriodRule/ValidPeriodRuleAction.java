package com.huateng.univer.issuer.productWithAccount.ValidPeriodRule;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.product.ValidPeriodRuleDspDTO;
import com.allinfinance.univer.issuer.dto.product.ValidPeriodRuleInfDTO;
import com.allinfinance.univer.issuer.dto.product.ValidPeriodRuleQueryDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;

/**
  *@author Yifeng.Shi , Cathy.Wang  2011-07-25
  */

public class ValidPeriodRuleAction extends BaseAction {
	private Logger logger = Logger.getLogger(ValidPeriodRuleAction.class);
	private static final long serialVersionUID = 7037246557260651867L;
	private PageDataDTO pageDataDTO=new PageDataDTO();
	private ValidPeriodRuleDspDTO validPeriodRuleDspDTO=new ValidPeriodRuleDspDTO();
	private ValidPeriodRuleInfDTO validPeriodRuleInfDTO=new ValidPeriodRuleInfDTO();
	private ValidPeriodRuleQueryDTO validPeriodRuleQueryDTO=new ValidPeriodRuleQueryDTO();
	private List<String> ruleStep;
	private List<String> amountMin;
	private List<String> amountMax;
	private List<String> delayMonth;
	private List<String> choose;
	private String rechargeDelayMonth;
	private String poundage;
	private String month;
	private int totalRows = 0;
	private List<String> ruleInfId;
	private List<String> dataState;
	
	public List<String> getRuleInfId() {
		return ruleInfId;
	}

	public void setRuleInfId(List<String> ruleInfId) {
		this.ruleInfId = ruleInfId;
	}

	public List<String> getDataState() {
		return dataState;
	}

	public void setDataState(List<String> dataState) {
		this.dataState = dataState;
	}
	
	public List<String> getChoose() {
		return choose;
	}

	public void setChoose(List<String> choose) {
		this.choose = choose;
	}

	public List<String> getRuleStep() {
		return ruleStep;
	}

	public void setRuleStep(List<String> ruleStep) {
		this.ruleStep = ruleStep;
	}

	public List<String> getAmountMin() {
		return amountMin;
	}

	public void setAmountMin(List<String> amountMin) {
		this.amountMin = amountMin;
	}

	public List<String> getAmountMax() {
		return amountMax;
	}

	public void setAmountMax(List<String> amountMax) {
		this.amountMax = amountMax;
	}

	public List<String> getDelayMonth() {
		return delayMonth;
	}

	public void setDelayMonth(List<String> delayMonth) {
		this.delayMonth = delayMonth;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public ValidPeriodRuleDspDTO getValidPeriodRuleDspDTO() {
		return validPeriodRuleDspDTO;
	}

	public void setValidPeriodRuleDspDTO(ValidPeriodRuleDspDTO validPeriodRuleDspDTO) {
		this.validPeriodRuleDspDTO = validPeriodRuleDspDTO;
	}

	public ValidPeriodRuleInfDTO getValidPeriodRuleInfDTO() {
		return validPeriodRuleInfDTO;
	}

	public void setValidPeriodRuleInfDTO(ValidPeriodRuleInfDTO validPeriodRuleInfDTO) {
		this.validPeriodRuleInfDTO = validPeriodRuleInfDTO;
	}

	public ValidPeriodRuleQueryDTO getValidPeriodRuleQueryDTO() {
		return validPeriodRuleQueryDTO;
	}

	public void setValidPeriodRuleQueryDTO(
			ValidPeriodRuleQueryDTO validPeriodRuleQueryDTO) {
		this.validPeriodRuleQueryDTO = validPeriodRuleQueryDTO;
	}
	

	public String getRechargeDelayMonth() {
		return rechargeDelayMonth;
	}

	public void setRechargeDelayMonth(String rechargeDelayMonth) {
		this.rechargeDelayMonth = rechargeDelayMonth;
	}

	public String getPoundage() {
		return poundage;
	}

	public void setPoundage(String poundage) {
		this.poundage = poundage;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	/*
	 * 有效期规则集合查询
	 * */
	public String query() throws Exception{
		ListPageInit(null, validPeriodRuleQueryDTO);
		validPeriodRuleQueryDTO.setEntityId(getUser().getEntityId());
		pageDataDTO=(PageDataDTO)sendService(ConstCode.VALID_PERIOD_RULE_QUERY, validPeriodRuleQueryDTO).getDetailvo();
		if(pageDataDTO!=null){
			totalRows=pageDataDTO.getTotalRecord();
		}
		if(hasActionErrors()){
			return INPUT;
		}
		return "list";
	}
	
	/*
	 * load ValidPeriodRuleDspDTO.validPeriodRuleInfDTOs
	 * */
	public void load(){
		List<ValidPeriodRuleInfDTO> validPeriodRuleInfDTOs=new ArrayList<ValidPeriodRuleInfDTO>();
		if(null != ruleStep && ruleStep.size()>0){
			logger.debug("ruleStep.size:"+ruleStep.size());
			for(int i=0;i<ruleStep.size();i++){
				ValidPeriodRuleInfDTO validPeriodRuleInfDTO=new ValidPeriodRuleInfDTO();
				validPeriodRuleInfDTO.setRuleInfId(ruleInfId.get(i));
				validPeriodRuleInfDTO.setMinAmount(amountMin.get(i));
				validPeriodRuleInfDTO.setMaxAmount(amountMax.get(i));
				validPeriodRuleInfDTO.setDelayMonth(delayMonth.get(i));
				validPeriodRuleInfDTO.setDataState(dataState.get(i));
				validPeriodRuleInfDTOs.add(validPeriodRuleInfDTO);
			}
			validPeriodRuleDspDTO.setValidPeriodRuleInfDTOs(validPeriodRuleInfDTOs);
		}
	}
	
	/*
	 * 有效期规则集合查询
	 * */
	public String list() throws Exception{
		
		return query();
	}
	
	
	/*
	 * 查看规则详细信息
	 * */
	public String view() throws Exception{
		logger.debug("ruleDspId:"+validPeriodRuleDspDTO.getRuleDspId());
		validPeriodRuleQueryDTO=(ValidPeriodRuleQueryDTO)sendService(ConstCode.VALID_PERIOD_RULE_VIEW, validPeriodRuleDspDTO).getDetailvo();
		validPeriodRuleDspDTO=validPeriodRuleQueryDTO.getValidPeriodRuleDspDTO();
		if("2".equals(validPeriodRuleDspDTO.getTransType())){
			rechargeDelayMonth=validPeriodRuleQueryDTO.getList().get(0).getDelayMonth();
		}else if("3".equals(validPeriodRuleDspDTO.getTransType())){
			month=validPeriodRuleQueryDTO.getList().get(0).getDelayMonth();
			poundage=Amount.getReallyAmount(validPeriodRuleQueryDTO.getList().get(0).getValidPoundage());
		}
		getRequest().setAttribute("size", validPeriodRuleQueryDTO.getList().size());
		if(hasActionErrors()){
			query();
			return "list";
		}else{
		   return "view";
		}
	}
	
	/*
	 * 转向规则添加
	 * */
	public String reAdd() throws Exception{
		validPeriodRuleDspDTO.setTransType("1");
		return "add";
	}
	
	/*
	 * 添加规则
	 * */
	public String insert() throws Exception{
		checkInputValid();
		validPeriodRuleDspDTO.setEntityId(getUser().getEntityId());
		validPeriodRuleDspDTO.setCreateUser(getUser().getUserId());
		validPeriodRuleDspDTO.getValidPeriodRuleInfDTO().setTransType(validPeriodRuleDspDTO.getTransType());
		if("1".equals(validPeriodRuleDspDTO.getTransType())){
			load();		
			if(hasActionErrors()|| hasFieldErrors() || hasErrors()){
				validPeriodRuleQueryDTO.setList(validPeriodRuleDspDTO.getValidPeriodRuleInfDTOs());
				getRequest().setAttribute("size", validPeriodRuleQueryDTO.getList().size());
				return "add";
			}			
		}else if("2".equals(validPeriodRuleDspDTO.getTransType())){
			validPeriodRuleDspDTO.getValidPeriodRuleInfDTO().setDelayMonth(rechargeDelayMonth);			
		}else{
			validPeriodRuleDspDTO.setBranchMark("1");
			validPeriodRuleDspDTO.getValidPeriodRuleInfDTO().setDelayMonth(month);
			validPeriodRuleDspDTO.getValidPeriodRuleInfDTO().setValidPoundage(Amount.getDataBaseAmountForTwoFloat(poundage));
		}		
		if(hasActionErrors()|| hasFieldErrors() || hasErrors()){
			return "add";
		}
		sendService(ConstCode.VALID_PERIOD_RULE_INSERT, validPeriodRuleDspDTO);
		if(!hasActionErrors() && !hasErrors()){
			addActionMessage("添加规则成功");
			query();
			return "list";
		}else{
			if("1".equals(validPeriodRuleDspDTO.getTransType())){
				validPeriodRuleQueryDTO.setList(validPeriodRuleDspDTO.getValidPeriodRuleInfDTOs());
				getRequest().setAttribute("size", validPeriodRuleQueryDTO.getList().size());			
			}
			return "add";
		}
	}
	
	/*
	 * 转向规则编辑
	 * */
	public String reEdit() throws Exception{
		view();
		this.getSession().put("transType", validPeriodRuleDspDTO.getTransType());
		logger.info(this.getSession().get("transType"));
		if(!hasActionErrors() && !hasErrors()){
			return "edit";
		}else{
			query();
			return "list";
			
		}
	}
	/*
	 * 编辑规则
	 * */
	public String update()throws Exception{
		checkInputValid();
		validPeriodRuleDspDTO.setModifyUser(getUser().getUserId());
		validPeriodRuleDspDTO.getValidPeriodRuleInfDTO().setTransType(validPeriodRuleDspDTO.getTransType());
		logger.info(this.getSession().get("transType"));
		if(null!=this.getSession().get("transType")){
			validPeriodRuleDspDTO.setOldTransType(this.getSession().get("transType").toString());
		}
		if("1".equals(validPeriodRuleDspDTO.getTransType())){
			load();		
			if(hasActionErrors()|| hasFieldErrors() || hasErrors()){
				validPeriodRuleQueryDTO.setList(validPeriodRuleDspDTO.getValidPeriodRuleInfDTOs());
				getRequest().setAttribute("size", validPeriodRuleQueryDTO.getList().size());
				return "edit";
			}
		}else if("2".equals(validPeriodRuleDspDTO.getTransType())){
			validPeriodRuleDspDTO.getValidPeriodRuleInfDTO().setDelayMonth(rechargeDelayMonth);			
		}else{
			validPeriodRuleDspDTO.getValidPeriodRuleInfDTO().setDelayMonth(month);
			validPeriodRuleDspDTO.getValidPeriodRuleInfDTO().setValidPoundage(Amount.getDataBaseAmountForTwoFloat(poundage));
		}		
		if(hasActionErrors()|| hasFieldErrors() || hasErrors()){
			return "edit";
		}
		sendService(ConstCode.VALID_PERIOD_RULE_UPDATE, validPeriodRuleDspDTO);
		if(!hasActionErrors() && !hasErrors()){
			addActionMessage("编辑规则成功");
			query();
			return "list";
		}else{
			if("1".equals(validPeriodRuleDspDTO.getTransType())){
				validPeriodRuleQueryDTO.setList(validPeriodRuleDspDTO.getValidPeriodRuleInfDTOs());
				getRequest().setAttribute("size", validPeriodRuleQueryDTO.getList().size());
			}
			return "edit";
		}
	}
	
	/*
	 * 启用规则
	 * */
	public String enabled()throws Exception{		
		List<ValidPeriodRuleDspDTO> ruleDspList=new ArrayList<ValidPeriodRuleDspDTO>();
		ValidPeriodRuleDspDTO validPeriodRuleDspDTO;
		for(String ruleDsp:choose){
			validPeriodRuleDspDTO=new ValidPeriodRuleDspDTO();
			String[] list=ruleDsp.split(",");
			validPeriodRuleDspDTO.setRuleState(DataBaseConstant.RULE_STATE_ENABLED);
			validPeriodRuleDspDTO.setRuleDspId(list[0]);
			validPeriodRuleDspDTO.setTransType(list[1]);
			validPeriodRuleDspDTO.setOldTransType(list[1]);
			validPeriodRuleDspDTO.setModifyTime(DateUtil.getCurrentTime24());
			validPeriodRuleDspDTO.setModifyUser(getUser().getUserId());
			ruleDspList.add(validPeriodRuleDspDTO);
		}
		sendService(ConstCode.VALID_PERIOD_RULE_OPERATE, ruleDspList);
		if(!hasActionErrors() && !hasErrors()){
			addActionMessage("启用规则成功");
		}
		query();
		return "list";
	}
	
	/*
	 * 删除规则
	 * */
	public String delete()throws Exception{
		List<ValidPeriodRuleDspDTO> ruleDspList=new ArrayList<ValidPeriodRuleDspDTO>();
		ValidPeriodRuleDspDTO validPeriodRuleDspDTO;
		for(String ruleDsp:choose){
			validPeriodRuleDspDTO=new ValidPeriodRuleDspDTO();
			String[] list=ruleDsp.split(",");
			validPeriodRuleDspDTO.setRuleState(DataBaseConstant.RULE_STATE_DELETE);
			validPeriodRuleDspDTO.setRuleDspId(list[0]);
			validPeriodRuleDspDTO.setTransType(list[1]);
			validPeriodRuleDspDTO.setOldTransType(list[1]);
			validPeriodRuleDspDTO.setModifyTime(DateUtil.getCurrentTime24());
			validPeriodRuleDspDTO.setModifyUser(getUser().getUserId());
			ruleDspList.add(validPeriodRuleDspDTO);
		}
		sendService(ConstCode.VALID_PERIOD_RULE_OPERATE, ruleDspList);
		if(!hasActionErrors() && !hasErrors()){
			addActionMessage("删除规则成功");
		}
		query();
		return "list";
	}
	private void checkInputValid(){
		if(null == validPeriodRuleDspDTO.getRuleName() || validPeriodRuleDspDTO.getRuleName().trim().equals("") || validPeriodRuleDspDTO.getRuleName().length()>8){
			addFieldError("validPeriodRuleDspDTO.ruleName", "规则名称格式错误(不能超过8个汉字)");
		}
		if(null == validPeriodRuleDspDTO.getDeadLine() || validPeriodRuleDspDTO.getDeadLine().trim().equals("")){
			addFieldError("validPeriodRuleDspDTO.deadLine", "请选择截止日期(大于当前日期)");
		}else if(!DateUtil.string2date((validPeriodRuleDspDTO.getDeadLine())).after(DateUtil.getCurrentDate())){
			addFieldError("validPeriodRuleDspDTO.deadLine", "截止日期必须大于当前日期");
		}
		if(null != validPeriodRuleDspDTO.getRuleDsp()){
			if(validPeriodRuleDspDTO.getRuleDsp().length()>=100){
				addFieldError("validPeriodRuleDspDTO.ruleDsp", "只能输入100字以内的描述信息");
			}
		}
		
	}
}
