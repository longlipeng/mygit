package com.huateng.univer.issuer.order;

import com.huateng.framework.constant.OrderConst;

/**
 * 库存订单action
 * 
 * @author xxl
 * 
 */
public class StockOrderConfirmAction extends StockOrderBaseAction {

	private static final long serialVersionUID = 6186195710898743566L;

	protected void inqueryInit() {
		// 订单类型： 制卡订单+下级机构采购订单
		sellOrderQueryDTO
				.setOrderTypeArray("'"+OrderConst.ORDER_TYPE_ORDER_MAKE_CARD +"'"+ ","
						+ "'"+OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN +"'"
						+ ","
						+ "'"+OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN+"'"
						+ ","
						+ "'"+OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN+"'"
						+ ","
						+ "'"+OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN+"'");
		// 订单状态：2待审核+23待审批

		sellOrderQueryDTO
				.setOrderStateArray("'"+OrderConst.ORDER_STATE_ORDER_CONFIRM_BY_HIGHER+"'"
						+ "," 
						+ "'"+OrderConst.ORDER_STATE_UNCERTAIN+"'");
	}

	@Override
	protected void initActionName() {
		actionName = "stockOrderConfirmAction";
	}

}
