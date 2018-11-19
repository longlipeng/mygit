package com.huateng.univer.servicefeerule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.tagext.PageData;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.framework.dto.PageQueryDTO;
import com.allinfinance.univer.servicefeerule.dto.ServiceFeeRuleDTO;
import com.allinfinance.univer.servicefeerule.dto.ServiceFeeRuleQueryDTO;
import com.huateng.framework.action.BaseAction;

public class ServiceFeeRuleAction extends BaseAction{
     private ServiceFeeRuleDTO serviceFeeRuleDTO;
	 private ServiceFeeRuleQueryDTO serviceFeeRuleQueryDTO;
	 private PageQueryDTO pageQueryDTO;
	 private PageDataDTO pageDataDTO;
	 private String type;
	 private int totalRows;
	 private List<String> feeRuleIdList;
	 private List<ServiceFeeRuleDTO> serviceFeeRuleDTOList;
	    private List<String> serviceFeeRuleDTORuleStep;
	    private List<String> serviceFeeRuleDTORateType;
	    private List<String> serviceFeeRuleDTORateValue;
	    private List<String> serviceFeeRuleDTOFeeMin;
	    private List<String> serviceFeeRuleDTOFeeMax;
		private List<String> serviceFeeRuleDTOAmountStart;
	    private List<String> serviceFeeRuleDTOAmountEnd;
	 public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String query(){
		if(serviceFeeRuleQueryDTO==null){
			serviceFeeRuleQueryDTO=new ServiceFeeRuleQueryDTO();
		} 
		serviceFeeRuleQueryDTO.setEntityId(getUser().getEntityId());
		this.pageDataDTO=(PageDataDTO) sendService("7777100001",serviceFeeRuleQueryDTO).getDetailvo();
		if(pageDataDTO!=null){
			totalRows=pageDataDTO.getTotalRecord();
		}
		if(hasErrors()){
			return "input";
		}
		return "list";
	 }
	public String add(){
	   return "add";
	}
	public String editInit(){
		serviceFeeRuleDTO=new ServiceFeeRuleDTO();
		serviceFeeRuleDTO.setRuleNo(feeRuleIdList.get(0));
		serviceFeeRuleDTOList=(List<ServiceFeeRuleDTO>) sendService("7777100003",serviceFeeRuleDTO).getDetailvo();
		serviceFeeRuleDTO=serviceFeeRuleDTOList.get(0);
		serviceFeeRuleDTO.setServiceFeeRuleDTOListSize(serviceFeeRuleDTOList.size());
		getRequest().setAttribute("size", serviceFeeRuleDTOList.size());
		if(hasErrors()){
			return "input";
		}
		return "editInit";
	}
	public String insert(){
		serviceFeeRuleDTOList=new ArrayList<ServiceFeeRuleDTO>();
		String ruleName=serviceFeeRuleDTO.getRuleName();
		String ruleDesc=serviceFeeRuleDTO.getRuleDesc();
		for(int i=0;i<serviceFeeRuleDTORuleStep.size();i++){
			ServiceFeeRuleDTO dto=new ServiceFeeRuleDTO();
			dto.setRuleStep(serviceFeeRuleDTORuleStep.get(i));
			dto.setRuleName(ruleName);
			dto.setRuleDesc(ruleDesc);
			dto.setRateValue(serviceFeeRuleDTORateValue.get(i));
			String amountStart=serviceFeeRuleDTOAmountStart.get(i);
			String amountEnd=serviceFeeRuleDTOAmountEnd.get(i);
			String feeMin=serviceFeeRuleDTOFeeMin.get(i);
			String feeMax=serviceFeeRuleDTOFeeMax.get(i);
			if(feeMin!=null && !feeMin.equals("")){
				dto.setFeeMin(String.valueOf(new BigDecimal(feeMin).multiply(new BigDecimal(100))));
			}else{
				dto.setFeeMin("0");
			}
			if(feeMax!=null&&!feeMin.equals("")){
				dto.setFeeMax(String.valueOf(new BigDecimal(feeMax).multiply(new BigDecimal(100))));
			}else{
				dto.setFeeMax("0");
			}
			dto.setAmountStart(String.valueOf(new BigDecimal(amountStart).multiply(new BigDecimal(100))));
			dto.setAmountEnd(String.valueOf(new BigDecimal(amountEnd).multiply(new BigDecimal(100))));
			dto.setEntityId(getUser().getEntityId());
			serviceFeeRuleDTOList.add(dto);
		}
		serviceFeeRuleDTO.setEntityId(getUser().getEntityId());
		serviceFeeRuleDTO.setServiceFeeRuleDTOList(serviceFeeRuleDTOList);
		sendService("7777100002", serviceFeeRuleDTO);
		if(hasErrors()){
			return "input";
		}
		return query();
	}
	public String update(){
		serviceFeeRuleDTOList=new ArrayList<ServiceFeeRuleDTO>();
		String ruleNo=serviceFeeRuleDTO.getRuleNo();
		String ruleName=serviceFeeRuleDTO.getRuleName();
		String caclType=serviceFeeRuleDTO.getCalcType();
		String ruleDesc=serviceFeeRuleDTO.getRuleDesc();
		for(int i=0;i<serviceFeeRuleDTORuleStep.size();i++){
			ServiceFeeRuleDTO dto=new ServiceFeeRuleDTO();
			dto.setRuleNo(ruleNo);
			dto.setRuleStep(serviceFeeRuleDTORuleStep.get(i));
			dto.setRuleName(ruleName);
			dto.setRuleDesc(ruleDesc);
			dto.setCalcType(caclType);
			dto.setRateValue(serviceFeeRuleDTORateValue.get(i));
			String amountStart=serviceFeeRuleDTOAmountStart.get(i);
			String amountEnd=serviceFeeRuleDTOAmountEnd.get(i);
			String feeMin=serviceFeeRuleDTOFeeMin.get(i);
			String feeMax=serviceFeeRuleDTOFeeMax.get(i);
			if(feeMin!=null && !feeMin.equals("")){
				dto.setFeeMin(String.valueOf(new BigDecimal(feeMin).multiply(new BigDecimal(100))));
			}else{
				dto.setFeeMin("0");
			}
			if(feeMax!=null&&!feeMin.equals("")){
				dto.setFeeMax(String.valueOf(new BigDecimal(feeMax).multiply(new BigDecimal(100))));
			}else{
				dto.setFeeMax("0");
			}
			dto.setAmountStart(String.valueOf(new BigDecimal(amountStart).multiply(new BigDecimal(100))));
			dto.setAmountEnd(String.valueOf(new BigDecimal(amountEnd).multiply(new BigDecimal(100))));
			dto.setEntityId(getUser().getEntityId());
			serviceFeeRuleDTOList.add(dto);
		}
		serviceFeeRuleDTO.setEntityId(getUser().getEntityId());
		serviceFeeRuleDTO.setServiceFeeRuleDTOList(serviceFeeRuleDTOList);
		sendService("7777100004", serviceFeeRuleDTO);
		if(hasErrors()){
			return "input";
		}
		return query();
	}
	public String view(){
		serviceFeeRuleDTOList=(List<ServiceFeeRuleDTO>) sendService("7777100003",serviceFeeRuleDTO).getDetailvo();
		serviceFeeRuleDTO=serviceFeeRuleDTOList.get(0);
		if(hasErrors()){
			return "input";
		}
		return "view";
	}
	public String enable(){
		serviceFeeRuleDTO=new ServiceFeeRuleDTO();
		serviceFeeRuleDTOList=new ArrayList<ServiceFeeRuleDTO>();
		for(String str:feeRuleIdList){
			ServiceFeeRuleDTO dto=new ServiceFeeRuleDTO();
			dto.setRuleNo(str);
			serviceFeeRuleDTOList.add(dto);
		}
		serviceFeeRuleDTO.setServiceFeeRuleDTOList(serviceFeeRuleDTOList);
		sendService("7777100005", serviceFeeRuleDTO);
		if(hasErrors()){
			return "input";
		}
		return query();
	}
	public String delete(){
		serviceFeeRuleDTO=new ServiceFeeRuleDTO();
		serviceFeeRuleDTOList=new ArrayList<ServiceFeeRuleDTO>();
		for(String str:feeRuleIdList){
			ServiceFeeRuleDTO dto=new ServiceFeeRuleDTO();
			dto.setRuleNo(str);
			serviceFeeRuleDTOList.add(dto);
		}
		serviceFeeRuleDTO.setServiceFeeRuleDTOList(serviceFeeRuleDTOList);
		sendService("7777100006", serviceFeeRuleDTO);
		if(hasErrors()){
			return "input";
		}
		return query();
	}

	public ServiceFeeRuleDTO getServiceFeeRuleDTO() {
		return serviceFeeRuleDTO;
	}
	public void setServiceFeeRuleDTO(ServiceFeeRuleDTO serviceFeeRuleDTO) {
		this.serviceFeeRuleDTO = serviceFeeRuleDTO;
	}
	public ServiceFeeRuleQueryDTO getServiceFeeRuleQueryDTO() {
		return serviceFeeRuleQueryDTO;
	}
	public void setServiceFeeRuleQueryDTO(
			ServiceFeeRuleQueryDTO serviceFeeRuleQueryDTO) {
		this.serviceFeeRuleQueryDTO = serviceFeeRuleQueryDTO;
	}
	public PageQueryDTO getPageQueryDTO() {
		return pageQueryDTO;
	}
	public void setPageQueryDTO(PageQueryDTO pageQueryDTO) {
		this.pageQueryDTO = pageQueryDTO;
	}
	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}
	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public List<ServiceFeeRuleDTO> getServiceFeeRuleDTOList() {
		return serviceFeeRuleDTOList;
	}
	public void setServiceFeeRuleDTOList(
			List<ServiceFeeRuleDTO> serviceFeeRuleDTOList) {
		this.serviceFeeRuleDTOList = serviceFeeRuleDTOList;
	}
	public List<String> getServiceFeeRuleDTORuleStep() {
		return serviceFeeRuleDTORuleStep;
	}
	public void setServiceFeeRuleDTORuleStep(List<String> serviceFeeRuleDTORuleStep) {
		this.serviceFeeRuleDTORuleStep = serviceFeeRuleDTORuleStep;
	}
	public List<String> getServiceFeeRuleDTORateType() {
		return serviceFeeRuleDTORateType;
	}
	public void setServiceFeeRuleDTORateType(List<String> serviceFeeRuleDTORateType) {
		this.serviceFeeRuleDTORateType = serviceFeeRuleDTORateType;
	}
	public List<String> getServiceFeeRuleDTORateValue() {
		return serviceFeeRuleDTORateValue;
	}
	public void setServiceFeeRuleDTORateValue(
			List<String> serviceFeeRuleDTORateValue) {
		this.serviceFeeRuleDTORateValue = serviceFeeRuleDTORateValue;
	}
	public List<String> getServiceFeeRuleDTOFeeMin() {
		return serviceFeeRuleDTOFeeMin;
	}
	public void setServiceFeeRuleDTOFeeMin(List<String> serviceFeeRuleDTOFeeMin) {
		this.serviceFeeRuleDTOFeeMin = serviceFeeRuleDTOFeeMin;
	}
	public List<String> getServiceFeeRuleDTOFeeMax() {
		return serviceFeeRuleDTOFeeMax;
	}
	public void setServiceFeeRuleDTOFeeMax(List<String> serviceFeeRuleDTOFeeMax) {
		this.serviceFeeRuleDTOFeeMax = serviceFeeRuleDTOFeeMax;
	}
	public List<String> getServiceFeeRuleDTOAmountStart() {
		return serviceFeeRuleDTOAmountStart;
	}
	public void setServiceFeeRuleDTOAmountStart(
			List<String> serviceFeeRuleDTOAmountStart) {
		this.serviceFeeRuleDTOAmountStart = serviceFeeRuleDTOAmountStart;
	}
	public List<String> getServiceFeeRuleDTOAmountEnd() {
		return serviceFeeRuleDTOAmountEnd;
	}
	public void setServiceFeeRuleDTOAmountEnd(
			List<String> serviceFeeRuleDTOAmountEnd) {
		this.serviceFeeRuleDTOAmountEnd = serviceFeeRuleDTOAmountEnd;
	}
	public List<String> getFeeRuleIdList() {
		return feeRuleIdList;
	}
	public void setFeeRuleIdList(List<String> feeRuleIdList) {
		this.feeRuleIdList = feeRuleIdList;
	}
	
	 
}
