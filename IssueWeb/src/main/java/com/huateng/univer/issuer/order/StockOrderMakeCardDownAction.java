package com.huateng.univer.issuer.order;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.univer.seller.order.dto.SellOrderMakeCardDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.exception.BizServiceException;

/**
 * 制卡订单 制卡文件生成 action
 * 
 * @author xxl
 * 
 */
public class StockOrderMakeCardDownAction extends StockOrderBaseAction {
	private Logger logger = Logger.getLogger(StockOrderMakeCardDownAction.class);
	private static final long serialVersionUID = 8230117276336842499L;

	
	private String companyId;
	
	protected void initActionName() {
		actionName = "stockOrderMakeCardDownAction";
	}
	protected void inqueryInit() {
		// 订单类型： 制卡订单+下级机构记名采购订单
		sellOrderQueryDTO
				.setOrderTypeArray(OrderConst.ORDER_TYPE_ORDER_MAKE_CARD + ","
						+ OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN + ","
						+ OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN);
		// 订单状态：3制卡文件待生成 + 处理中 + 处理失败
		sellOrderQueryDTO
				.setOrderStateArray(OrderConst.ORDER_STATE_CARDFILE_DOWNLOAD+","+OrderConst.ORDER_STATE_OPEN_ACCOUNT+","+OrderConst.ORDER_STATE_ORDER_PROCESSING+","+OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL);


	}

	/**
	 * 制卡开户
	 * @return
	 * @throws Exception
	 */
	public String openAccount() throws Exception{
		sellOrderDTO.setCardCompanyId(companyId);
		try {
			this.sendService(ConstCode.MAKECARD_OPEN_ACCOUNT, sellOrderDTO);
			if (!hasErrors()) {
				addActionMessage("提交制卡订单成功！");
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败");
		}
		return list();
	}
	
	
	/**
	 * 选择制卡商
	 */
	public String choiceCardCompany() throws Exception {
		sellOrderMakeCardDTO = (SellOrderMakeCardDTO) sendService(
				ConstCode.MAKECARD_COMPANY_CHOICE, sellOrderMakeCardDTO)
				.getDetailvo();

		getRequest()
				.setAttribute(
						"cardCompanyData",
						JSONArray.fromObject(sellOrderMakeCardDTO
								.getCardCompanyList()));
		return "choiceCardCompany";
	}

	/**
	 * 发报文生成制卡文件
	 */
	public String downMakeCardFile() throws Exception {
		try {
			sellOrderMakeCardDTO.setCardCompanyId(companyId);
			sellOrderMakeCardDTO.setOrderId(sellOrderDTO.getOrderId());
			this
					.sendService(ConstCode.MAKECARD_FILE_DOWN,
							sellOrderMakeCardDTO);
			if(!hasErrors()){
				this.addActionMessage("生成制卡文件成功！");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return list();
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

}
