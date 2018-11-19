package com.huateng.univer.consumer.mchntcontract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumercontract.dto.AccTypeContractDTO;
import com.allinfinance.univer.consumercontract.dto.ConsumerContractDTO;
import com.allinfinance.univer.consumercontract.dto.ConsumerContractQueryDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.util.DateUtil;

/**
 * 商户合同管理
 * @author zengfenghua
 *
 */
public class MchntContractManagementAction extends BaseAction {
	
	private Logger logger = Logger.getLogger(MchntContractManagementAction.class);
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConsumerContractDTO consumerContractDTO;
    private ConsumerContractQueryDTO consumerContractQueryDTO;
    private PageDataDTO pageDataDTO;
    private Integer totalRows;
    private String actionName;
    private AccTypeContractDTO accTypeContractDTO;
   
	private String consumerContractId;
    private String merchantName;
    private List<String> accTypeContractIdList;
    private List<String> consumerContractIdList;
    private String contractTypeStr;
    private String[] acctypeInf;
	public String[] getAcctypeInf() {
		return acctypeInf;
	}
	public void setAcctypeInf(String[] acctypeInf) {
		this.acctypeInf = acctypeInf;
	}
	public String getContractTypeStr() {
		return contractTypeStr;
	}
	public void setContractTypeStr(String contractTypeStr) {
		this.contractTypeStr = contractTypeStr;
	}
	
	public Integer getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
	public String inquiry(){
		 try{
			 if (consumerContractQueryDTO == null) {
				consumerContractQueryDTO = new ConsumerContractQueryDTO();
			}
			consumerContractQueryDTO.setContractSeller(getUser().getEntityId());
			ListPageInit(null,consumerContractQueryDTO);
	 		
			pageDataDTO = (PageDataDTO)sendService("2002050100",consumerContractQueryDTO).getDetailvo();
			
			if(pageDataDTO==null){
				pageDataDTO = new PageDataDTO();
			}
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
	        if (hasErrors()) {
	            return "input";
	        }
	        
		 }catch(Exception e){
			this.logger.error(e.getMessage());
		 }
			return "list";
		
	}
	public String add(){
		consumerContractDTO=new ConsumerContractDTO();
		consumerContractDTO.setContractSeller(getUser().getEntityId());
		consumerContractDTO.setContractType(DataBaseConstant.CONTRACT_MERCHANT);
		consumerContractDTO.setContractStartDateDate(new Date());
		return "add";
	}
	 public String insert(){
    	 consumerContractDTO.setContractStartDate(DateUtil.date2String(consumerContractDTO.getContractStartDateDate()));
    	 consumerContractDTO.setContractEndDate(DateUtil.date2String(consumerContractDTO.getContractEndDateDate()));
    	 ConsumerContractDTO inputDTO=consumerContractDTO;
    	 consumerContractDTO.setContractType(DataBaseConstant.CONTRACT_MERCHANT);
    	 if(consumerContractDTO.getContractStartDateDate().compareTo(DateUtil.getCurrentDate())<0){
    		 addActionError("合同有效期开始日不能小于系统日期！"); 
    		 return "input";
    	 }
    	 consumerContractDTO=(ConsumerContractDTO) sendService("2002050200", consumerContractDTO).getDetailvo();
    	 if(hasErrors()){
    		 consumerContractDTO=inputDTO;
    		 return "input";
    	 }
    	 editCommon();
//    	 //修改商户信息状态为：审核中 ——2011年11月9日
//    	  MerchantDTO merchantDTO = new MerchantDTO();
//    	  merchantDTO.setEntityId(consumerContractDTO.getContractBuyer());
//    	  merchantDTO.setFatherEntityId(consumerContractDTO.getContractSeller());
//    	  //获取对应的商户实体并进行修改其状态
//    	 merchantDTO = (MerchantDTO) sendService("800888880002",merchantDTO).getDetailvo();
    	 addActionMessage("新增商户合同成功!");
    	 if(null!=consumerContractDTO.getSettleDateNext()&&!"".equals(consumerContractDTO.getSettleDateNext())){
    		 String next=DateUtil.formatStringDate(consumerContractDTO.getSettleDateNext());
    		 addActionError("首次结算截止日期为"+next+"!");
    	 }
    	 return "edit";
     }
	 public String edit(){
		 editCommon();
		 return "edit";
	 }	
	 public void editCommon(){
		 if(null==consumerContractDTO){
			 consumerContractDTO=new ConsumerContractDTO();
		 }
		 if(null != consumerContractIdList && consumerContractIdList.size()>0){
			 String[] splitArray=consumerContractIdList.get(0).split(",");
			 consumerContractDTO.setConsumerContractId(splitArray[0]);
			 //consumerContractDTO.setContractType(splitArray[2]);
		 }
		 
		 if(("".equals(consumerContractDTO.getConsumerContractId()))||(consumerContractDTO.getConsumerContractId()==null)){
			 
			 consumerContractDTO.setConsumerContractId(consumerContractId);
		 }
		 consumerContractDTO.setContractSeller(getUser().getEntityId());
		 consumerContractDTO=(ConsumerContractDTO) sendService("20111116002", consumerContractDTO).getDetailvo();
		 consumerContractDTO=(ConsumerContractDTO) sendService(ConstCode.MERCHANT_ACCTYPE_CONTRACT_QUERY, consumerContractDTO).getDetailvo();
		 consumerContractDTO.setContractStartDateDate(DateUtil.string2date(DateUtil.dbFormatToDateFormat(consumerContractDTO.getContractStartDate())));
		 consumerContractDTO.setContractEndDateDate(DateUtil.string2date(DateUtil.dbFormatToDateFormat(consumerContractDTO.getContractEndDate())));
		 /*合同开始日期大于当前日期，则可以修改周期规则 1：可以修改，0：不可以修改*/
		 if(consumerContractDTO.getContractStartDateDate().after(DateUtil.getCurrentDate())){
			 consumerContractDTO.setRuleEditFlag("1");
		 }else{
			 consumerContractDTO.setRuleEditFlag("0");
		 }
	 }
	 public String update(){
		 try{
			 consumerContractDTO.setContractStartDate(DateUtil.date2String(consumerContractDTO.getContractStartDateDate()));
	    	 consumerContractDTO.setContractEndDate(DateUtil.date2String(consumerContractDTO.getContractEndDateDate()));
	    	 Date date1=consumerContractDTO.getContractStartDateDate();
	    	 Date date2=consumerContractDTO.getContractEndDateDate();
	    	 consumerContractDTO.setContractType(DataBaseConstant.CONTRACT_MERCHANT);
	    	 if(consumerContractDTO.getContractEndDateDate().compareTo(DateUtil.getCurrentDate())<0){
	    		 addActionError("合同结束日期不能小于系统日期！"); 
	    		 return "input";
	    	 }
			 sendService("2002051701",consumerContractDTO);
			 if(hasErrors()){
				 editCommon();
				 consumerContractDTO.setContractStartDateDate(date1);
				 consumerContractDTO.setContractEndDateDate(date2);
				 return "edit";
			 }
			 addActionMessage("提交合同成功！");		
		 }catch(Exception e){
			 this.logger.error(e.getMessage());
		 }
		 return inquiry();
	 }
	 public String delete(){
		    String str=consumerContractIdList.get(0);
		    String[] s=str.split(",");
		    consumerContractDTO=new ConsumerContractDTO();
		    consumerContractDTO.setConsumerContractId(s[0]);
		    consumerContractDTO.setContractBuyer(s[1]);
			sendService("2002051702",consumerContractDTO);
	        if (hasErrors()) {
	        	return inquiry();
	        }
	        if (!hasActionErrors()) {
	            addActionMessage("删除合同成功！");
	        }
			return inquiry();
	 }
	 public String view(){
		 consumerContractDTO=(ConsumerContractDTO) sendService("2002051700", consumerContractDTO).getDetailvo();
		 consumerContractDTO=(ConsumerContractDTO)sendService(ConstCode.MERCHANT_ACCTYPE_CONTRACT_QUERY,
					consumerContractDTO).getDetailvo();
		 consumerContractDTO.setContractStartDate(DateUtil.dbFormatToDateFormat(consumerContractDTO.getContractStartDate()));
		 consumerContractDTO.setContractEndDate(DateUtil.dbFormatToDateFormat(consumerContractDTO.getContractEndDate()));
		 if(hasErrors()){
			 this.inquiry();
			 return "input";
		 }
		 return "view";
	 }
	 public String addAcctypeContract(){
		    accTypeContractDTO.setConstractType(contractTypeStr);
			this.accTypeContractDTO = (AccTypeContractDTO)sendService("2002051500", accTypeContractDTO).getDetailvo();
	        if (hasErrors()) {
	            return "input";
	        }
			return "addAcctypeContract";
	 }
	 public String insertAcctypeContract(){
		
			// consumerContractDTO=new ConsumerContractDTO();
		 if(null==accTypeContractDTO){
			 accTypeContractDTO =new AccTypeContractDTO();
		 }
		     if(null!=acctypeInf){
		    	 if(acctypeInf.length > 0){
		    		 
		    		 acctypeInf=acctypeInf[0].split(",");
		    		 
		    	 }
		    	 if(acctypeInf.length ==1){
		    	   
		    		 if(null!=acctypeInf[0])accTypeContractDTO.setAcctypeId(acctypeInf[0]);
		    	 }
		    	 else if(acctypeInf.length ==2){
		    		 if(null!=acctypeInf[0])accTypeContractDTO.setAcctypeId(acctypeInf[0]);
		    		 if(null!=acctypeInf[1])accTypeContractDTO.setAcctypeContractId(acctypeInf[1]);
		    	 }
		    	 
		    	
		     }
		 accTypeContractDTO.setConstractType(consumerContractDTO.getContractType());
		 accTypeContractDTO.setConsumerContractId(consumerContractDTO.getConsumerContractId());
		 accTypeContractDTO.setRuleNo(this.getRequest().getParameter("rule"));
		 accTypeContractDTO.setContractBuyer(consumerContractDTO.getContractBuyer());
		 sendService("2002051600", accTypeContractDTO);
		 
		
		 if(this.hasActionErrors()){
			 
			 return "input";
		 } 
		 editCommon();
		 addActionMessage("新增服务合同成功!");
		 return "insertSuccess";
	 }
	 public String editAcctypeContract(){
		 try{
			 String accTypeContractId=accTypeContractIdList.get(0);
			 accTypeContractDTO.setAcctypeContractId(accTypeContractId);
			 accTypeContractDTO=(AccTypeContractDTO) sendService("2002051800",accTypeContractDTO).getDetailvo();
			 if(hasErrors()){
				 return "input";
			 }
		 }catch(Exception e){
			 this.logger.error(e.getMessage());
		 }	
		 return "editAcctypeContract";
	 }
	 public String updateAcctypeContract(){
		 try{
			 sendService("2002051900",accTypeContractDTO);
			 if(hasErrors()){
				 return "input";
			 }
		 }catch(Exception e){
			 this.logger.error(e.getMessage());
		 }	
		 addActionMessage("更新服务合同成功!");
		 return "blank";
	 }
	 public String deleteAcctypeContract(){
		 try{
			 List<AccTypeContractDTO> dtoList=new ArrayList<AccTypeContractDTO>();
			 for(String str:accTypeContractIdList){
				 AccTypeContractDTO dto=new AccTypeContractDTO();
				 dto.setAcctypeContractId(str);
				 dtoList.add(dto);
			 }
			 accTypeContractDTO=new AccTypeContractDTO();
			 accTypeContractDTO.setAccTypeContractDTOList(dtoList);
			 sendService("2002052100",accTypeContractDTO);
		 }catch(Exception e){
			 this.logger.error(e.getMessage());
		 }
		 addActionMessage("删除服务合同成功!");
		 return "blank";
	 }
	 public void validateUpdate(){
		 if(null ==consumerContractDTO.getContractStartDateDate()){
			 addFieldError("consumerContractDTO.contractStartDateDate", "合同有效期开始日期必须选择");
		 }
//		 if(consumerContractDTO.getContractStartDateDate().compareTo(date)<0){
//			 addFieldError("consumerContractDTO.contractStartDateDate", "合同有效期开始日期不能早于当前日期");
//		 }
		 if(consumerContractDTO.getContractEndDateDate()!=null ){
			 if( !consumerContractDTO.getContractStartDateDate().before(consumerContractDTO.getContractEndDateDate()))
			 addFieldError("consumerContractDTO.contractEndDateDate", "合同有效期结束日期必须晚于开始日期");
		 }
//		 if(consumerContractDTO.getContractEndDateDate()!=null &&
//				 consumerContractDTO.getContractEndDateDate().compareTo(date)<0){
//			 addFieldError("consumerContractDTO.contractEndDateDate", "合同有效期结束日期不能早于当前日期");
//		 }
//		 if((consumerContractDTO.getContractStartDateDate().compareTo(date)<0) || 
//				 (consumerContractDTO.getContractEndDateDate()!=null && 
//				 consumerContractDTO.getContractEndDateDate().before(consumerContractDTO.getContractStartDateDate()))||
//				 (consumerContractDTO.getContractEndDateDate()!=null &&
//				 consumerContractDTO.getContractEndDateDate().compareTo(date)<0)){
//			 Date date1=consumerContractDTO.getContractStartDateDate();
//			 Date date2=consumerContractDTO.getContractEndDateDate();
//			 editCommon();
//			 consumerContractDTO.setContractStartDateDate(date1);
//			 consumerContractDTO.setContractEndDateDate(date2);
//		 }
	 }
	 public void validateInsert(){
		 if(consumerContractDTO.getContractBuyer()==null || "".equals(consumerContractDTO.getContractBuyer().trim())){
			 addFieldError("consumerContractDTO.contractBuyer", "商户必须选择");
		 }
		 if(consumerContractDTO.getRuleNo()==null || "".equals(consumerContractDTO.getRuleNo().trim())){
			 addFieldError("consumerContractDTO.ruleNo", "结算周期规则必须选择");
		 }
		 dateCheck();
	 }
	 public void dateCheck(){
		 if(null == consumerContractDTO.getContractStartDateDate()){
			 this.addFieldError("consumerContractDTO.contractStartDateDate", "合同有效期开始日期必须选择");
		 }
//		 if(consumerContractDTO.getContractStartDateDate().compareTo(date)<0){
//			 addFieldError("consumerContractDTO.contractStartDateDate", "合同有效期开始日期不能早于当前日期");
//		 }
		 if(consumerContractDTO.getContractEndDateDate()!=null){
			 if(! consumerContractDTO.getContractStartDateDate().before(consumerContractDTO.getContractEndDateDate())){
				 addFieldError("consumerContractDTO.contractEndDateDate", "合同有效期结束日期必须晚于开始日期");
			 }
		 }
//		 if(consumerContractDTO.getContractEndDateDate()!=null &&
//				 consumerContractDTO.getContractEndDateDate().compareTo(date)<0){
//			 addFieldError("consumerContractDTO.contractEndDateDate", "合同有效期结束日期不能早于当前日期");
//		 }
	 }
	public ConsumerContractDTO getConsumerContractDTO() {
		return consumerContractDTO;
	}
	public void setConsumerContractDTO(ConsumerContractDTO consumerContractDTO) {
		this.consumerContractDTO = consumerContractDTO;
	}
	public ConsumerContractQueryDTO getConsumerContractQueryDTO() {
		return consumerContractQueryDTO;
	}
	public void setConsumerContractQueryDTO(
			ConsumerContractQueryDTO consumerContractQueryDTO) {
		this.consumerContractQueryDTO = consumerContractQueryDTO;
	}
	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}
	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public AccTypeContractDTO getAccTypeContractDTO() {
		return accTypeContractDTO;
	}
	public void setAccTypeContractDTO(AccTypeContractDTO accTypeContractDTO) {
		this.accTypeContractDTO = accTypeContractDTO;
	}
	public String getConsumerContractId() {
		return consumerContractId;
	}
	public void setConsumerContractId(String consumerContractId) {
		this.consumerContractId = consumerContractId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public List<String> getAccTypeContractIdList() {
		return accTypeContractIdList;
	}
	public void setAccTypeContractIdList(List<String> accTypeContractIdList) {
		this.accTypeContractIdList = accTypeContractIdList;
	}
	public List<String> getConsumerContractIdList() {
		return consumerContractIdList;
	}
	public void setConsumerContractIdList(List<String> consumerContractIdList) {
		this.consumerContractIdList = consumerContractIdList;
	}
}