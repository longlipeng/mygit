package com.huateng.univer.cardinfor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.shangqi.gateway.dto.ApplyAndBindCardDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.OrderConst;
import com.huateng.univer.seller.sellorder.OrderConfirmAction;

/**
 * 
 * @author dawn
 *
 */
public class CardInformation extends BaseAction {

	/**
	 * 订单确认
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(CardInformation.class);

	protected ApplyAndBindCardDTO applyAndBindCardDTO = new ApplyAndBindCardDTO();

	@SuppressWarnings("unchecked")
	protected List<?> cardInfos = new ArrayList();

	protected int cardInfo_totalRows = 0;

	protected String actionName;

	protected String actionMethodName;

	/**
	 * 
	 */
	private String message;
	private String buyOrderFlag;
	private String exFlag;

	public String getExFlag() {
		return exFlag;
	}

	public void setExFlag(String exFlag) {
		this.exFlag = exFlag;
	}

	protected void addCondition() {

	}

	protected void dealWithSellOrderInputDTO() {

	}

	public String edit() {

		return null;
	}

	protected void init() {
		actionName = "cardInformationQueryAction";
		actionMethodName = "button";
	}

	protected void initOrderType() {
	}

	public String list() {
		try {
			ListPageInit("cardInfo", applyAndBindCardDTO);
			// applyAndBindCardDTO.setOrderState(OrderConst.ORDER_STATE_UNCERTAIN);
			// /***
			// * 按订单ID的倒序排序
			// */
			// if(isEmpty(applyAndBindCardDTO.getSortFieldName())){
			// applyAndBindCardDTO.setSort("desc");
			// applyAndBindCardDTO.setApplyStatus("0");
			// applyAndBindCardDTO.setSortFieldName("ID_NO");
			// applyAndBindCardDTO.setEntityId(this.getUser().getEntityId());
			// }
			if (applyAndBindCardDTO.getStartDate() != null) {
				String startDate = applyAndBindCardDTO.getStartDate();
				String str1 = startDate.replaceAll("-", "");
				applyAndBindCardDTO.setStartDate(str1);
			}
			if (applyAndBindCardDTO.getEndDate() != null) {
				String endDate = applyAndBindCardDTO.getEndDate();
				String str2 = endDate.replaceAll("-", "");
				applyAndBindCardDTO.setEndDate(str2);
			}

			/***
			 * 按时间排序
			 */
			if (isEmpty(applyAndBindCardDTO.getSortFieldName())) {
				applyAndBindCardDTO.setSort("asc");
				applyAndBindCardDTO.setApplyStatus("0");
				// applyAndBindCardDTO.setSortFieldName("applyDateSeconds");
				applyAndBindCardDTO.setSortFieldName("APPLY_DATE_SECONDS");
				applyAndBindCardDTO.setEntityId(this.getUser().getEntityId());
			}

			// Object detailvo =
			// sendService(ConstCode.CARD_INFO_QUERY_AT_SELL_CONFIRM,
			// applyAndBindCardDTO).getDetailvo();
			PageDataDTO result = (PageDataDTO) sendService(
					ConstCode.CARD_INFO_QUERY_AT_SELL_CONFIRM,
					applyAndBindCardDTO).getDetailvo();
			cardInfos = (List<Map<String, Object>>) result.getData();
			cardInfo_totalRows = result.getTotalRecord();
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage());
		}
		return "list";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBuyOrderFlag() {
		return buyOrderFlag;
	}

	public void setBuyOrderFlag(String buyOrderFlag) {
		this.buyOrderFlag = buyOrderFlag;
	}

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
}
