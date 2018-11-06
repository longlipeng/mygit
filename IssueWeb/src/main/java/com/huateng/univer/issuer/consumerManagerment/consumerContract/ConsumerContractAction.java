package com.huateng.univer.issuer.consumerManagerment.consumerContract;

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
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.SystemInfo;

public class ConsumerContractAction extends BaseAction {
	private Logger logger = Logger.getLogger(ConsumerContractAction.class);
	private ConsumerContractDTO consumerContractDTO;
	private ConsumerContractQueryDTO consumerContractQueryDTO;
	private PageDataDTO pageDataDTO;
	private Integer totalRows;
	private String actionName;
	private AccTypeContractDTO accTypeContractDTO;
	private String consumerContractId;
	private String merchantName;
	private List<String> consumerContractIdList;
	private String contractTypeStr;
	 private List<String>  accTypeContractIdList;
	 private String[] transIds;

	

	public String[] getTransIds() {
		return transIds;
	}

	public void setTransIds(String[] transIds) {
		this.transIds = transIds;
	}

	public List<String> getAccTypeContractIdList() {
		return accTypeContractIdList;
	}

	public void setAccTypeContractIdList(List<String> accTypeContractIdList) {
		this.accTypeContractIdList = accTypeContractIdList;
	}

	public String getContractTypeStr() {
		return contractTypeStr;
	}

	public void setContractTypeStr(String contractTypeStr) {
		this.contractTypeStr = contractTypeStr;
	}

	public List<String> getConsumerContractIdList() {
		return consumerContractIdList;
	}

	public void setConsumerContractIdList(List<String> consumerContractIdList) {
		this.consumerContractIdList = consumerContractIdList;
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

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String query() throws Exception {
		if (consumerContractQueryDTO == null) {
			consumerContractQueryDTO = new ConsumerContractQueryDTO();
		}
		ListPageInit(null, consumerContractQueryDTO);
		if (consumerContractQueryDTO.isQueryAll()) {
			consumerContractQueryDTO.setQueryAll(false);
			consumerContractQueryDTO
					.setRowsDisplayed(Integer
							.parseInt(SystemInfo
									.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
		}
		consumerContractQueryDTO.setContractSeller(getUser().getEntityId());
		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.CONSUMER_CONTRACT_INQUERY, consumerContractQueryDTO)
				.getDetailvo();
		if (null != pageDataDTO && pageDataDTO.getTotalRecord() > 0) {
			totalRows = pageDataDTO.getTotalRecord();
		} else {
			totalRows = 0;
		}
		return "list";
	}

	/**
	 * 转向合同添加页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String reAdd() throws Exception {
		consumerContractDTO = new ConsumerContractDTO();
		consumerContractDTO.setContractSeller(getUser().getEntityId());
		consumerContractDTO.setContractType(DataBaseConstant.CONTRACT_CONSUMER);
		consumerContractDTO.setContractStartDateDate(new Date());
		return "readd";
	}

	/**
	 * 添加收单机构合同
	 * 
	 * @return
	 * @throws Exception
	 */
	public String insertConsumer() throws Exception {
		consumerContractDTO.setContractStartDate(DateUtil
				.date2String(consumerContractDTO.getContractStartDateDate()));
		consumerContractDTO.setContractEndDate(DateUtil
				.date2String(consumerContractDTO.getContractEndDateDate()));
		consumerContractDTO = (ConsumerContractDTO) sendService("2002050200",
				consumerContractDTO).getDetailvo();
		if (hasErrors()) {
			reAdd();
			return "input";
		}
		addActionMessage("新增收单机构合同成功!");

		return "edit";
	}
	public void validateInsertConsumer(){
		if(consumerContractDTO.getContractStartDateDate().before(new Date())){
			this.addActionError("合同的开始日期必须大于当天日期！");
		}
		if (null != consumerContractDTO.getContractEndDateDate()) {
			
			if (!consumerContractDTO.getContractStartDateDate().before(
					consumerContractDTO.getContractEndDateDate())) {
				this.addActionError("合同结束日期必须大于合同开始日期!");
			}
		}
	}
	/**
	 * 编辑收单机构合同
	 */
	public String editConsumerContract() throws Exception {
		 if(null==consumerContractDTO){
		consumerContractDTO = new ConsumerContractDTO();
		 }
		 if(null != consumerContractIdList && consumerContractIdList.size()>0){
		String[] splitArray = consumerContractIdList.get(0).split(",");
		consumerContractDTO.setConsumerContractId(splitArray[0]);
		 }
		consumerContractDTO = (ConsumerContractDTO) sendService("2002051700",
				consumerContractDTO).getDetailvo();
		 consumerContractDTO.setContractStartDateDate(DateUtil.string2date(DateUtil.dbFormatToDateFormat(consumerContractDTO.getContractStartDate())));
		 consumerContractDTO.setContractEndDateDate(DateUtil.string2date(DateUtil.dbFormatToDateFormat(consumerContractDTO.getContractEndDate())));
		return "edit";
	}

//	 public String editConsumerContract()throws Exception{
//	 consumerContractDTO=new ConsumerContractDTO();
//			 
//	 consumerContractDTO.setContractSeller(getUser().getEntityId());
//	 consumerContractDTO.setContractType(DataBaseConstant.CONTRACT_CONSUMER);
//	 consumerContractDTO=(ConsumerContractDTO) sendService("2002051700",
//	 consumerContractDTO).getDetailvo();
//	 consumerContractDTO.setContractStartDateDate(DateUtil.string2date(DateUtil.dbFormatToDateFormat(consumerContractDTO.getContractStartDate())));
//	 consumerContractDTO.setContractEndDateDate(DateUtil.string2date(DateUtil.dbFormatToDateFormat(consumerContractDTO.getContractEndDate())));
//	 addActionMessage("编辑账户合同信息成功!");
//	 return "edit";
//	 }
	/**
	 * 查看收单机构合同信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String viewCinsumerContract() throws Exception {
		consumerContractDTO = (ConsumerContractDTO) sendService("2002051700",
				consumerContractDTO).getDetailvo();
		consumerContractDTO.setContractStartDate(DateUtil
				.dbFormatToDateFormat(consumerContractDTO
						.getContractStartDate()));
		consumerContractDTO
				.setContractEndDate(DateUtil
						.dbFormatToDateFormat(consumerContractDTO
								.getContractEndDate()));
		if (hasErrors()) {
			return "input";
		}
		return "edit";
	}

	/**
	 * 更新收单机构信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateCinsumerContract() throws Exception {
		try {
			consumerContractDTO
					.setContractStartDate(DateUtil
							.date2String(consumerContractDTO
									.getContractStartDateDate()));
			consumerContractDTO.setContractEndDate(DateUtil
					.date2String(consumerContractDTO.getContractEndDateDate()));
			consumerContractDTO
					.setContractType(DataBaseConstant.CONTRACT_CONSUMER);
			List<String> transId=new ArrayList<String>();
			if(null!=transIds){
			for(String tranId : transIds){
				transId.add(tranId);
				
			}
			consumerContractDTO.setTransId(transId);
			}
			sendService("2002051701", consumerContractDTO);		
			if (hasErrors()) {
				return "input";
			}
			addActionMessage("提交合同成功！");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return query();
	}

	public void validateUpdateCinsumerContract() {
		if (null != consumerContractDTO.getContractEndDateDate()) {
			if (!consumerContractDTO.getContractStartDateDate().before(
					consumerContractDTO.getContractEndDateDate())) {
				this.addActionError("合同结束日期必须大于合同开始日期!");
			}
		}
	}

	public String addAcctypeContract() {
		accTypeContractDTO.setConstractType(contractTypeStr);
		this.accTypeContractDTO = (AccTypeContractDTO) sendService(
				"2002051500", accTypeContractDTO).getDetailvo();
		if (hasErrors()) {
			return "input";
		}
		return "addAcctypeContract";
	}
	 public String insertAcctypeContract(){
		 sendService("2002051600", accTypeContractDTO);
		 if(hasErrors()){
			 return "input";
		 }
		 addActionMessage("新增服务合同成功!");
		 return "blank";
	 }
	 public String editAcctypeContract(){
		 try{
			 //String accTypeContractId=accTypeContractIdList.get(0);
			 String accTypeContractId = accTypeContractIdList.get(0).split(",")[0];
			 String consumerContractId = accTypeContractIdList.get(0).split(",")[1];
			 accTypeContractDTO.setAcctypeContractId(accTypeContractId);
			 accTypeContractDTO.setConsumerContractId(consumerContractId);
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
				 dto.setAcctypeContractId(str.split(",")[0]);
				 dtoList.add(dto);
			 }
			 accTypeContractDTO=new AccTypeContractDTO();
			 accTypeContractDTO.setAccTypeContractDTOList(dtoList);
			 sendService("2002052100",accTypeContractDTO);	
			 editConsumerContract();
		 }catch(Exception e){
			 this.logger.error(e.getMessage());
		 }
		 addActionMessage("删除服务合同成功!");
		 return "edit";
	 }
	/**
	 * 删除收单机构合同信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteConsumerContract() {

		consumerContractDTO = new ConsumerContractDTO();
		String[] idArrayString = new String[] {};
		if (null != consumerContractIdList && consumerContractIdList.size() > 0) {
			idArrayString = consumerContractIdList.get(0).split(",");
		}
		consumerContractDTO.setConsumerContractId(idArrayString[0]);
		consumerContractDTO.setContractBuyer(idArrayString[1]);

		sendService("2002051702", consumerContractDTO).getDetailvo();

		return null;
	}

}
