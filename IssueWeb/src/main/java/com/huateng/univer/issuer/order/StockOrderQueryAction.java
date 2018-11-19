package com.huateng.univer.issuer.order;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;

/**
 * 制卡订单查询action
 * 
 * @author xxl
 * 
 */
public class StockOrderQueryAction extends StockOrderBaseAction {

	private Logger logger = Logger.getLogger(StockOrderQueryAction.class);
	private static final long serialVersionUID = 3754498681137573167L;
	private String actionMethodName;
	protected void initActionName() {
		actionName = "stockOrderQueryAction";
		actionMethodName = "view";
	}

	protected void inqueryInit() {
		// 订单类型： 
//		sellOrderQueryDTO
//				.setOrderTypeArray(OrderConst.ORDER_TYPE_ORDER_MAKE_CARD + ","
//						+ OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN
//						+ ","
//						+ OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
//						+ ","
//						+ OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
//						+ ","
//						+ OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN);
		
		
	}
	/**
	 * 打印订单出入库凭证查询
	 * */
	public String queryForOrderStockPrint(){
		
		sellOrderDTO=(SellOrderDTO)sendService(ConstCode.SELL_ORDER_INQUERY_PRINT_STOCK,
				  sellOrderDTO).getDetailvo();
		if (hasErrors()) {
			try {
				return list();
			} catch (Exception e) {
				this.logger.error(e.getMessage());
			}
		}
		initActionName();
		actionMethodName="list";
		if(sellOrderDTO.getStockCertFlag().equals("entStock")){
			return "stockPrintEnt";
		}else if(sellOrderDTO.getStockCertFlag().equals("outStock")){
			return "stockPrintOut";
		}
		return "";
	}

	public String getActionMethodName() {
		return actionMethodName;
	}

	public void setActionMethodName(String actionMethodName) {
		this.actionMethodName = actionMethodName;
	}
	
}
