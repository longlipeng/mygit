package com.huateng.univer.issuer.order;

import com.huateng.framework.constant.OrderConst;

/**
 * 订单配送action
 * 
 * @author xxl
 * 
 */
public class StockOrderSendAction extends StockOrderBaseAction {

	private static final long serialVersionUID = -8873789943369087467L;

	protected void initActionName() {
		actionName = "stockOrderSendAction";
	}
	
	protected void inqueryInit() {
		// 订单类型： 制卡订单+下级机构记名采购订单
		sellOrderQueryDTO
				.setOrderTypeArray(
						OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN
						+ ","
						+ OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
						+ ","
						+ OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
						+ ","
						+ OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN);;
		// 订单状态：29订单配送
		sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_ORDER_SEND);
	}
	

}
