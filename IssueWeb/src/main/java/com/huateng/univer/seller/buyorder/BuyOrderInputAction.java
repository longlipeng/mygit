package com.huateng.univer.seller.buyorder;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.univer.seller.sellorder.OrderBaseAction;

public class BuyOrderInputAction extends OrderBaseAction {
	private Logger logger = Logger.getLogger(BuyOrderInputAction.class);
	private static final long serialVersionUID = 8998293075225323171L;

	@Override
	protected void addCondition() {

	}

	@Override
	public String list() {
		try {
			ListPageInit("sellOrder", sellOrderQueryDTO);
			sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
			/***
			 * 按订单ID的倒序排序
			 */
			if (isEmpty(sellOrderQueryDTO.getSortFieldName())) {
				sellOrderQueryDTO.setSort("desc");
				sellOrderQueryDTO.setSortFieldName("orderId");
			}
			PageDataDTO result = (PageDataDTO) sendService(
					ConstCode.ORDER_INQUERY_AT_BUY_INPUT, sellOrderQueryDTO)
					.getDetailvo();
			sellOrders = result.getData();
			sellOrder_totalRows = result.getTotalRecord();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";
	}

	@Override
	protected void dealWithSellOrderInputDTO() {

	}

	@Override
	public String edit() {
		return null;
	}

	@Override
	protected void init() {

	}

	@Override
	protected void initOrderType() {

	}

}
