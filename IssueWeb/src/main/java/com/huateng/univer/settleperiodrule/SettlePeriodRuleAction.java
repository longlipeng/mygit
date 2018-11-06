package com.huateng.univer.settleperiodrule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.settleperiodrule.dto.SettlePeriodRuleDTO;
import com.allinfinance.univer.settleperiodrule.dto.SettlePeriodRuleQueryDTO;
import com.huateng.framework.action.BaseAction;


public class SettlePeriodRuleAction extends BaseAction{
    /**
     */
    private static final long serialVersionUID = 1L;
    Logger logger = Logger.getLogger(SettlePeriodRuleAction.class);
    private SettlePeriodRuleDTO settlePeriodRuleDTO=new SettlePeriodRuleDTO();  
    private String type;
    private SettlePeriodRuleQueryDTO settlePeriodRuleQueryDTO;
	private PageDataDTO pageDataDTO;
	private int totalRows;
	private List<String> feeRuleIdList;
	private String ruleNo;
	public List<String> getFeeRuleIdList() {
		return feeRuleIdList;
	}
	public void setFeeRuleIdList(List<String> feeRuleIdList) {
		this.feeRuleIdList = feeRuleIdList;
	}
	public SettlePeriodRuleQueryDTO getSettlePeriodRuleQueryDTO() {
		return settlePeriodRuleQueryDTO;
	}
	public void setSettlePeriodRuleQueryDTO(
			SettlePeriodRuleQueryDTO settlePeriodRuleQueryDTO) {
		this.settlePeriodRuleQueryDTO = settlePeriodRuleQueryDTO;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String add(){
       return "add";
    }
	public void conver(){
		if("2".equals(settlePeriodRuleDTO.getFlagWeekend())){
			settlePeriodRuleDTO.setFlagMonthend("1");
		}
		if("2".equals(settlePeriodRuleDTO.getFlagWeekend()) || "3".equals(settlePeriodRuleDTO.getFlagWeekend())){
			settlePeriodRuleDTO.setFlagWeekend(null);
		}
	}
	public void disconver(){
		if("1".equals(settlePeriodRuleDTO.getFlagMonthend())){
			settlePeriodRuleDTO.setFlagWeekend("2");
		}
		if(settlePeriodRuleDTO.getAmountMin()!=null && !"".equals(settlePeriodRuleDTO.getAmountMin().trim())){
			settlePeriodRuleDTO.setFlagWeekend("3");
		}
		if(settlePeriodRuleDTO.getFlagWeekend()==null){
			settlePeriodRuleDTO.setFlagWeekend("9");
		}
		if(settlePeriodRuleDTO.getDatePerMonth()==null){
			settlePeriodRuleDTO.setDatePerMonth("0");
		}
	}
	public String insert(){
	  //	settlePeriodRuleDTO.setRuleType(type);
		//conver();
		settlePeriodRuleDTO.setEntityId(getUser().getEntityId());
		sendService(ConstCode.PERIODRULE_SERVICE_INSERT,settlePeriodRuleDTO);
        if (hasErrors()) {
            return "input";
        }
        addActionMessage("新增结算周期规则成功！");
		return query();
	}
	
	public String query(){
		if(settlePeriodRuleQueryDTO==null){
			settlePeriodRuleQueryDTO=new SettlePeriodRuleQueryDTO();
		}
		settlePeriodRuleQueryDTO.setEntityId(getUser().getEntityId());
		this.pageDataDTO=(PageDataDTO) sendService(ConstCode.PERIODRULE_SERVICE_QUERY,settlePeriodRuleQueryDTO).getDetailvo();
		 if (hasErrors()) {
	            return "input";
	      }
		 if(pageDataDTO!=null){   
				totalRows=pageDataDTO.getTotalRecord();
			}
		return "list";
	}
	public String editInit(){
		//settlePeriodRuleDTO =new SettlePeriodRuleDTO();
		settlePeriodRuleDTO.setRuleNo(feeRuleIdList.get(0));
		if(settlePeriodRuleDTO.getRuleNo()!=null){
			settlePeriodRuleDTO=(SettlePeriodRuleDTO) sendService(ConstCode.PERIODRULE_SERVICE_TOUPDATE,settlePeriodRuleDTO).getDetailvo();
		}
		//disconver();
		return "editInit";
	}
	public String update(){
		//conver();
		settlePeriodRuleDTO.setEntityId(getUser().getEntityId());
		settlePeriodRuleDTO.setState("1");
		sendService(ConstCode.PERIODRULE_SERVICE_UPDATE, settlePeriodRuleDTO).getDetailvo();
		 if (hasErrors()) {
	            return "input";
	      }
		 addActionMessage("更新结算周期规则成功！ ");
		return query();
	}
	public String delete(){
		SettlePeriodRuleDTO settleDTO=new SettlePeriodRuleDTO();
		List<SettlePeriodRuleDTO> settleDTOList=new ArrayList<SettlePeriodRuleDTO>();
		for(String no:feeRuleIdList){
			SettlePeriodRuleDTO dto=new SettlePeriodRuleDTO();
			dto.setRuleNo(no);
			settleDTOList.add(dto);
		}
		settleDTO.setSettlePeriodRuleDTOList(settleDTOList);
		sendService(ConstCode.PERIODRULE_SERVICE_DELETE, settleDTO);
		if(hasErrors()){
			return "input";
		}
		addActionMessage("删除结算周期规则成功！ ");
		return query();
	}
	public String enable(){
		SettlePeriodRuleDTO settleDTO=new SettlePeriodRuleDTO();
		List<SettlePeriodRuleDTO> settleDTOList=new ArrayList<SettlePeriodRuleDTO>();
		for(String no:feeRuleIdList){
			SettlePeriodRuleDTO dto=new SettlePeriodRuleDTO();
			dto.setRuleNo(no);
			settleDTOList.add(dto);
		}
		settleDTO.setSettlePeriodRuleDTOList(settleDTOList);
		sendService(ConstCode.PERIODRULE_SERVICE_ENABLE, settleDTO);
		if(hasErrors()){
			return "input";
		}
		addActionMessage("启用结算周期规则成功！ ");
		return query();
	}
	public String contractView(){
		
		settlePeriodRuleDTO=new SettlePeriodRuleDTO();	
	    settlePeriodRuleDTO.setRuleNo(ruleNo);
	    settlePeriodRuleDTO=(SettlePeriodRuleDTO) sendService(ConstCode.PERIODRULE_SERVICE_VIEW, settlePeriodRuleDTO).getDetailvo();
	    if(hasErrors()){
			return "input";
		}
	    if(settlePeriodRuleDTO.getAmountMin()!=null && !"".equals(settlePeriodRuleDTO.getAmountMin().trim())){
			settlePeriodRuleDTO.setAmountMin(String.valueOf(Integer.valueOf(settlePeriodRuleDTO.getAmountMin())/100));
		}
		return "contractView";
	}
	public String view(){
	    settlePeriodRuleDTO=new SettlePeriodRuleDTO();	
	    settlePeriodRuleDTO.setRuleNo(ruleNo);
	    settlePeriodRuleDTO=(SettlePeriodRuleDTO) sendService(ConstCode.PERIODRULE_SERVICE_VIEW, settlePeriodRuleDTO).getDetailvo();
	    if(hasErrors()){
			return "input";
		}
	    if(settlePeriodRuleDTO.getAmountMin()!=null && !"".equals(settlePeriodRuleDTO.getAmountMin().trim())){
			settlePeriodRuleDTO.setAmountMin(String.valueOf(Integer.valueOf(settlePeriodRuleDTO.getAmountMin())/100));
		}
		return "view";
	}
	public void validateInsert(){
		if("".equals(settlePeriodRuleDTO.getRuleName().trim())){
			this.addFieldError("settlePeriodRuleDTO.ruleName","必须添加规则名称!");
		}
		if("1".equals(settlePeriodRuleDTO.getRuleType())){
			if(settlePeriodRuleDTO.getPeriodType()==null || "".equals(settlePeriodRuleDTO.getPeriodType().trim())){
				this.addFieldError("settlePeriodRuleDTO.periodType","周期单位必须选择！");
			}
			if(settlePeriodRuleDTO.getPeriod()==null || "".equals(settlePeriodRuleDTO.getPeriod().trim())){
				this.addFieldError("settlePeriodRuleDTO.period","周期必须填写！");
			}else if("0".equals(settlePeriodRuleDTO.getPeriod())){
				this.addFieldError("settlePeriodRuleDTO.period","周期不能为0！");
			}else if(!settlePeriodRuleDTO.getPeriod().matches("^[0-9]*[1-9][0-9]*$")){
				this.addFieldError("settlePeriodRuleDTO.period","周期必须是不为0的正整数！");
			}
	    }else if("0".equals(settlePeriodRuleDTO.getRuleType())){
	    	if("".equals(settlePeriodRuleDTO.getAmountMin().trim())|| null==settlePeriodRuleDTO.getAmountMin()){
	    		this.addFieldError("settlePeriodRuleDTO.amountMin","可结金额必须填写！");
	    	}else{
		    	if(!settlePeriodRuleDTO.getAmountMin().matches("^[0-9]*[1-9][0-9]*$")){
			    	this.addFieldError("settlePeriodRuleDTO.amountMin","可结金额必须为正整数!");
			    }		    		
	    	}
	    }else if("3".equals(settlePeriodRuleDTO.getRuleType())){
	        if("".equals(settlePeriodRuleDTO.getPartAmount().trim())|| null==settlePeriodRuleDTO.getPartAmount()){
	            this.addFieldError("settlePeriodRuleDTO.partAmount", "分段数不能为空!");
	        }
	    }else{
	    	if(settlePeriodRuleDTO.getPeriodType()==null || "".equals(settlePeriodRuleDTO.getPeriodType().trim())){
				this.addFieldError("settlePeriodRuleDTO.periodType","周期单位必须选择！");
			}
			if(settlePeriodRuleDTO.getPeriod()==null || "".equals(settlePeriodRuleDTO.getPeriod().trim())){
				this.addFieldError("settlePeriodRuleDTO.period","周期必须填写！");
			}else if("0".equals(settlePeriodRuleDTO.getPeriod())){
				this.addFieldError("settlePeriodRuleDTO.period","周期不能为0！");
			}else if(!settlePeriodRuleDTO.getPeriod().matches("^[0-9]*[1-9][0-9]*$")){
				this.addFieldError("settlePeriodRuleDTO.period","周期必须是不为0的正整数！");
			}
			if("".equals(settlePeriodRuleDTO.getAmountMin().trim())|| null==settlePeriodRuleDTO.getAmountMin()){
	    		this.addFieldError("settlePeriodRuleDTO.amountMin","可结金额必须填写！");
	    	}else{
		    	if(!settlePeriodRuleDTO.getAmountMin().matches("^[0-9]*[1-9][0-9]*$")){
			    	this.addFieldError("settlePeriodRuleDTO.amountMin","可结金额必须为正整数!");
			    }		    		
	    	}
	    }
	}
	public void validateUpdate(){
		if("".equals(settlePeriodRuleDTO.getRuleName().trim())){
			this.addFieldError("settlePeriodRuleDTO.ruleName","必须添加规则名称!");
		}
		if("1".equals(settlePeriodRuleDTO.getRuleType())){
			if(settlePeriodRuleDTO.getPeriodType()==null || "".equals(settlePeriodRuleDTO.getPeriodType().trim())){
				this.addFieldError("settlePeriodRuleDTO.periodType","周期单位必须选择！");
			}
			if(settlePeriodRuleDTO.getPeriod()==null || "".equals(settlePeriodRuleDTO.getPeriod().trim())){
				this.addFieldError("settlePeriodRuleDTO.period","周期必须填写");
			}else if("0".equals(settlePeriodRuleDTO.getPeriod())){
				this.addFieldError("settlePeriodRuleDTO.period","周期不能为0！");
			}else if(!settlePeriodRuleDTO.getPeriod().matches("^[0-9]*[1-9][0-9]*$")){
				this.addFieldError("settlePeriodRuleDTO.period","周期必须是不为0的正整数！");
			}
	    }else if("0".equals(settlePeriodRuleDTO.getRuleType())){
	    	if("".equals(settlePeriodRuleDTO.getAmountMin().trim())|| null==settlePeriodRuleDTO.getAmountMin()){
	    		this.addFieldError("settlePeriodRuleDTO.amountMin","可结金额必须填写！");
	    	}else{
		    	if(!settlePeriodRuleDTO.getAmountMin().matches("^[0-9]*[1-9][0-9]*$")){
			    	this.addFieldError("settlePeriodRuleDTO.amountMin","可结金额必须为正整数!");
			    }		    		
	    	}
	    }else if("3".equals(settlePeriodRuleDTO.getRuleType())){
            if("".equals(settlePeriodRuleDTO.getPartAmount().trim())|| null==settlePeriodRuleDTO.getPartAmount()){
                this.addFieldError("settlePeriodRuleDTO.partAmount", "分段数不能为空!");
            }
	    }
         else{
	    	if(settlePeriodRuleDTO.getPeriodType()==null || "".equals(settlePeriodRuleDTO.getPeriodType().trim())){
				this.addFieldError("settlePeriodRuleDTO.periodType","周期单位必须选择！");
			}
			if(settlePeriodRuleDTO.getPeriod()==null || "".equals(settlePeriodRuleDTO.getPeriod().trim())){
				this.addFieldError("settlePeriodRuleDTO.period","周期必须填写");
			}else if("0".equals(settlePeriodRuleDTO.getPeriod())){
				this.addFieldError("settlePeriodRuleDTO.period","周期不能为0！");
			}else if(!settlePeriodRuleDTO.getPeriod().matches("^[0-9]*[1-9][0-9]*$")){
				this.addFieldError("settlePeriodRuleDTO.period","周期必须是不为0的正整数！");
			}
			if("".equals(settlePeriodRuleDTO.getAmountMin().trim())|| null==settlePeriodRuleDTO.getAmountMin()){
	    		this.addFieldError("settlePeriodRuleDTO.amountMin","可结金额必须填写！");
	    	}else{
		    	if(!settlePeriodRuleDTO.getAmountMin().matches("^[0-9]*[1-9][0-9]*$")){
			    	this.addFieldError("settlePeriodRuleDTO.amountMin","可结金额必须为正整数!");
			    }		    		
	    	}
	    }
	}
	public SettlePeriodRuleDTO getSettlePeriodRuleDTO() {
		return settlePeriodRuleDTO;
	}
	public void setSettlePeriodRuleDTO(SettlePeriodRuleDTO settlePeriodRuleDTO) {
		this.settlePeriodRuleDTO = settlePeriodRuleDTO;
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
	public String getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}
	
	
	
}
