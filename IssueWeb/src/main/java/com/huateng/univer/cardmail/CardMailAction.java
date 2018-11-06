package com.huateng.univer.cardmail;

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
import com.huateng.univer.cardinfor.CardInformationAction;
import com.huateng.univer.issuer.order.StockOrderBaseAction;

public class CardMailAction extends BaseAction{
	
	private static final long serialVersionUID = 6186195710898743566L;
	private Logger logger = Logger.getLogger(CardMailAction.class);

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

	public String list(){
		try{
			 ListPageInit("cardInfo", applyAndBindCardDTO);
			 /***
			  * 按订单ID的倒序排序
			  */
			 if(isEmpty(applyAndBindCardDTO.getSortFieldName())){
				 applyAndBindCardDTO.setSort("desc");
				 applyAndBindCardDTO.setApplyStatus("1");
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
			 applyAndBindCardDTO.setEntityId(this.getUser().getEntityId());
			 applyAndBindCardDTO.setApplyStatus(OrderConst.CARD_MAIL_DAIYOUJI);
			 OperationResult opr = sendService(ConstCode.CARD_MAIL_PASS, applyAndBindCardDTO);
			if(!this.hasErrors()){
				this.addActionMessage("提交订单成功!");
			}
		}catch(Exception e){
			e.printStackTrace();
			this.logger.error(e.getMessage());
		}
			return list();
	}
	
	/*审核退回*/
	public String sendBack(){
		try{
			applyAndBindCardDTO.setEntityId(this.getUser().getEntityId());
			applyAndBindCardDTO.setApplyStatus(OrderConst.CARD_INFO_QUERY_CARD);
			OperationResult opr  = sendService(ConstCode.CARD_INFO_REJECT, applyAndBindCardDTO);
			 System.out.println("返回的状态：++++++++++"+opr.getTxnstate());
			 System.out.println("返回的信息：++++++++++"+opr.getDetailvo());
			if(!this.hasErrors()){
				this.addActionMessage("退回订单成功!");
			}
		}catch(Exception e){
			e.printStackTrace();
			this.logger.error(e.getMessage());
		}
			return list();
	}
	
	public String getBuyOrderFlag() {
		return buyOrderFlag;
	}

	public void setBuyOrderFlag(String buyOrderFlag) {
		this.buyOrderFlag = buyOrderFlag;
	}
}
