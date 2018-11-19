package com.huateng.univer.cardinfor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.shangqi.gateway.dto.ApplyAndBindCardDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.OrderConst;
import com.huateng.univer.cardmail.CardMailAction;

public class CardInformationAction extends BaseAction{
	
	private static final long serialVersionUID = 6186195710898743566L;

	private Logger logger = Logger.getLogger(CardInformationAction.class);

	protected ApplyAndBindCardDTO applyAndBindCardDTO = new ApplyAndBindCardDTO();
	 
	 @SuppressWarnings("unchecked")
	    protected List<?> cardInfos = new ArrayList();
	 
	 protected int cardInfo_totalRows = 0;
	 
	 protected String actionName;
	 
	 protected String actionMethodName;
	public ApplyAndBindCardDTO getApplyAndBindCardDTO() {
		return applyAndBindCardDTO;
	}

	public void setApplyAndBindCardDTO(ApplyAndBindCardDTO applyAndBindCardDTO) {
		this.applyAndBindCardDTO = applyAndBindCardDTO;
	}

	public List<?> getCardInfos() {
		return cardInfos;
	}

	public void setCardInfos(List<?> cardInfos) {
		this.cardInfos = cardInfos;
	}

	public int getCardInfo_totalRows() {
		return cardInfo_totalRows;
	}

	public void setCardInfo_totalRows(int cardInfo_totalRows) {
		this.cardInfo_totalRows = cardInfo_totalRows;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionMethodName() {
		return actionMethodName;
	}

	public void setActionMethodName(String actionMethodName) {
		this.actionMethodName = actionMethodName;
	}
	/**
	 * 
	 */
	private String buyOrderFlag;
	private String exFlag;
	
	
	public String getExFlag() {
       return exFlag;
   }

   public void setExFlag(String exFlag) {
       this.exFlag = exFlag;
   }

/*	protected void addCondition() {
	
		
	}

	protected void dealWithSellOrderInputDTO() {
	
		
	}*/

	/*public String edit() {
		
		return null;
	}*/

	protected void init() {	
		actionName= "cardMailQueryAction";
		actionMethodName="button";
	}
	protected void initOrderType() {	
	}
	
	protected String operation;
	
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	/*public String list(){
		try{
			 ListPageInit("cardInfo", applyAndBindCardDTO);
			 *//***
			  * 按订单ID的倒序排序
			  *//*
			 if(isEmpty(applyAndBindCardDTO.getSortFieldName())){
				 applyAndBindCardDTO.setSort("desc");
				 applyAndBindCardDTO.setApplyStatus("0");
				 applyAndBindCardDTO.setSortFieldName("ID_NO");
				 applyAndBindCardDTO.setEntityId(this.getUser().getEntityId());
			 }
			 Object detailvo = sendService(ConstCode.CARD_MAIL_QUERY_AT_SELL_CONFIRM, applyAndBindCardDTO).getDetailvo();
			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.CARD_MAIL_QUERY_AT_SELL_CONFIRM, applyAndBindCardDTO).getDetailvo();
			 cardInfos = (List<Map<String, Object>>)result.getData();
			 cardInfo_totalRows = result.getTotalRecord();
		}catch(Exception e){
			e.printStackTrace();
			this.logger.error(e.getMessage());
		}
		return "list";
	}*/
	
	public String list(ApplyAndBindCardDTO applyAndBindCardDTO){
		try{
			 ListPageInit("cardInfo", applyAndBindCardDTO);
			 //applyAndBindCardDTO.setOrderState(OrderConst.ORDER_STATE_UNCERTAIN);
			 /***
			  * 按时间升序排序
			  */
			 if(isEmpty(applyAndBindCardDTO.getSortFieldName())){
				 applyAndBindCardDTO.setSort("asc");
				 applyAndBindCardDTO.setApplyStatus("0");
				 applyAndBindCardDTO.setSortFieldName("APPLY_DATE_SECONDS");
				 applyAndBindCardDTO.setEntityId(this.getUser().getEntityId());
			 }
			 //Object detailvo = sendService(ConstCode.CARD_INFO_QUERY_AT_SELL_CONFIRM, applyAndBindCardDTO).getDetailvo();
			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.CARD_INFO_QUERY_AT_SELL_CONFIRM, applyAndBindCardDTO).getDetailvo();
			 cardInfos = (List<Map<String, Object>>)result.getData();
			 cardInfo_totalRows = result.getTotalRecord();
		}catch(Exception e){
			e.printStackTrace();
			this.logger.error(e.getMessage());
		}
		return "list";
	}
	
	public String listBuyOrder(){
		try{
			 ListPageInit("cardInfo", applyAndBindCardDTO);
			 String idNo = applyAndBindCardDTO.getIdNo();
			 String idType = applyAndBindCardDTO.getIdType();
			 applyAndBindCardDTO.setEntityId(this.getUser().getEntityId());
			 applyAndBindCardDTO = (ApplyAndBindCardDTO) sendService(ConstCode.CARD_INFO_CONFIRM_AT_SELL_CONFIRM,
					 applyAndBindCardDTO).getDetailvo();
		}catch(Exception e){
			e.printStackTrace();
			this.logger.error(e.getMessage());
		}
		return "listBuyOrder";
	}
	
	/*审核通过*/
	public String submit(){
		try{
			
			String s2 = new String(applyAndBindCardDTO.getCardholderComment().getBytes("ISO-8859-1"),"UTF-8");
			applyAndBindCardDTO.setCardholderComment(s2);
			
			if (applyAndBindCardDTO.getCardholderComment().length()>50) {
				addActionError("长度不能超过50位");
				return "";
			}
			applyAndBindCardDTO.setEntityId(this.getUser().getEntityId());
			
			/*String corpCredType = applyAndBindCardDTO.getIdType();//客户证件类型
			String corpCredId = applyAndBindCardDTO.getIdNo();//客户证件号
			if (corpCredType.equals("1")) {
				String substring = corpCredId.substring(17, 18);
				if(!Character.isDigit(substring.charAt(0))){//不是数字
					if(!Character.isUpperCase(substring.charAt(0))){ //小写
						applyAndBindCardDTO.setIdNo(corpCredId.substring(0, 17)+"X");
					}
				}
			}*/
			
			OperationResult opr = sendService(ConstCode.CARD_INFO_PASS, applyAndBindCardDTO);
			if(!this.hasErrors()){
				this.addActionMessage("审核完成!");
			}
		}catch(Exception e){
			e.printStackTrace();
			this.logger.error(e.getMessage());
		}
		applyAndBindCardDTO = new ApplyAndBindCardDTO();
			return list(new ApplyAndBindCardDTO());
	}
	
	/*审核退回*/
	public String sendBack(){
		try{
			
			if (applyAndBindCardDTO.getCardholderComment() == null || applyAndBindCardDTO.getCardholderComment() == "") {
				addActionError("请输入拒绝理由");
				return "";
			}
			String s2 = new String(applyAndBindCardDTO.getCardholderComment().getBytes("ISO-8859-1"),"UTF-8");
			applyAndBindCardDTO.setCardholderComment(s2);
			
			if (applyAndBindCardDTO.getCardholderComment().length()>50) {
				addActionError("长度不能超过50位");
				return "";
			}
			
			/*String corpCredType = applyAndBindCardDTO.getIdType();//客户证件类型
			String corpCredId = applyAndBindCardDTO.getIdNo();//客户证件号
			if (corpCredType.equals("1")) {
				String substring = corpCredId.substring(17, 18);
				if(!Character.isDigit(substring.charAt(0))){//不是数字
					if(!Character.isUpperCase(substring.charAt(0))){ //小写
						applyAndBindCardDTO.setIdNo(corpCredId.substring(0, 17)+"X");
					}
				}
			}*/
			
			applyAndBindCardDTO.setEntityId(this.getUser().getEntityId());
			applyAndBindCardDTO.setApplyStatus(OrderConst.CARD_INFO_QUERY_CARD);
			OperationResult opr  = sendService(ConstCode.CARD_INFO_REJECT, applyAndBindCardDTO);
			if(!this.hasErrors()){
				this.addActionMessage("退回订单成功!");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			this.logger.error(e.getMessage());
		}
		applyAndBindCardDTO = new ApplyAndBindCardDTO();
		return list(new ApplyAndBindCardDTO());
	}
	
	public String getBuyOrderFlag() {
		return buyOrderFlag;
	}

	public void setBuyOrderFlag(String buyOrderFlag) {
		this.buyOrderFlag = buyOrderFlag;
	}	
}
