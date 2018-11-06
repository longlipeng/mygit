package com.huateng.univer.seller.sellorder;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.allinfinance.univer.system.dictinfo.dto.DictInfoDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.SystemInfo;
/***
 * 录入订单状态
 * @author dawn
 *
 */
public class OrderInputAction extends OrderBaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(OrderInputAction.class);
	private String buyOrderFlag;
	

	@Override
	@SuppressWarnings("unchecked")
	public String add() {
//		System.out.println("进入添加订单");
		//----added begin 2015/7/1 
		sellOrderDTO = new SellOrderDTO();
		//----added end 2015/7/1 
		sellerContractQueryDTO.setContractBuyer(getUser().getEntityId());
		List<SellerContractDTO>  sellerContractDTOList= (List<SellerContractDTO>)sendService(ConstCode.CONTRACT_SERVICE_INQUERY_MASTERPALTE,
				 sellerContractQueryDTO).getDetailvo();
		if (null != sellerContractDTOList && sellerContractDTOList.size() > 0) {
			String expiryDate = sellerContractDTOList.get(0).getExpiryDate();
			String sysDate = DateUtil.getCurrentDateStr();
//			System.out.println("合同失效日期:" + expiryDate);
//			System.out.println("当前系统日期:" + DateUtil.getCurrentDateStr());
			if(Integer.parseInt(sysDate) > Integer.parseInt(expiryDate)) {
				this.addActionError("当前机构与上级发行机构合同已过期!");
				return list();
			}
		}
		List<DictInfoDTO> dictInfoList =  SystemInfo.getDictInfo().get(SystemInfoConstants.SELL_ORDER_TYPE);
	
		/**
		 * 在营销机构删除制卡订单
		 */
		List<DictInfoDTO> saleOrderList = new ArrayList<DictInfoDTO>();
		for (DictInfoDTO dictInfoDTO:dictInfoList){
			if (// OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN.equals(dictInfoDTO.getDictCode())
			OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN.equals(dictInfoDTO.getDictCode())
					||OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN.equals(dictInfoDTO.getDictCode())
					// ||OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN.equals(dictInfoDTO.getDictCode())
					||OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN.equals(dictInfoDTO.getDictCode())
					||OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN.equals(dictInfoDTO.getDictCode())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					||OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD.equals(dictInfoDTO.getDictCode())
					|| OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(dictInfoDTO.getDictCode())
					){
				saleOrderList.add(dictInfoDTO);	
			}
		}
		this.setOrderTypeList(saleOrderList);
		return "add";
	}
	
	@Override
	public String list(){
		try{
			 ListPageInit("sellOrder", sellOrderQueryDTO);
			 sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
			 /***
			  * 按订单ID的倒序排序
			  */
			 if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
				 sellOrderQueryDTO.setSort("desc");
				 sellOrderQueryDTO.setSortFieldName("orderId");
			 }
			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.ORDER_INQUERY_AT_SELL_INPUT,
					  sellOrderQueryDTO).getDetailvo();
			 sellOrders = result.getData();
			 sellOrder_totalRows = result.getTotalRecord();
			 sellOrderQueryDTO.setContractState(initContractState());
			 if(null==sellOrderQueryDTO.getContractState() || "".equals(sellOrderQueryDTO.getContractState())){
				 this.addActionError("该机构未与上级机构签合同不能添加订单！！");
			 }
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "list";
	}
	
	
	public String listBuyOrder(){
		try{
			 ListPageInit("sellOrder", sellOrderQueryDTO);
			 sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
			 /***
			  * 按订单ID的倒序排序
			  */
			 if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
				 sellOrderQueryDTO.setSort("desc");
				 sellOrderQueryDTO.setSortFieldName("orderId");
			 }
			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.ORDER_INQUERY_AT_BUY_INPUT,
					  sellOrderQueryDTO).getDetailvo();
			 sellOrders = result.getData();
			 sellOrder_totalRows = result.getTotalRecord();
			 String contractState=initContractState();
			 if(contractState==null||contractState.equals("")){
				 contractState="99999";
			 }
			 sellOrderQueryDTO.setContractState(contractState);
				
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "listBuyOrder";
	}
	
	@SkipValidation
	public String cancel(){
		try{
			sellOrderInputDTO.setEc_choose(ec_choose);
			sendService(ConstCode.SELL_ORDER_CANCEL_AT_INPUT,sellOrderInputDTO).getDetailvo();
			sellOrderQueryDTO.setContractState(initContractState());
				
			if(!this.hasErrors()){
				this.addActionMessage("取消订单成功!");
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		if("1".equals(buyOrderFlag)){
			return listBuyOrder();
		}else {
			return list();
		}
	}
	
	@SkipValidation
	public String submit(){
		try{
			sellOrderInputDTO.setEc_choose(ec_choose);
			sendService(ConstCode.SELL_ORDER_SUBMIT_AT_INPUT,sellOrderInputDTO).getDetailvo();
			sellOrderQueryDTO.setContractState(initContractState());
				
			if(!this.hasErrors()){
				this.addActionMessage("提交订单成功");
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		if("1".equals(buyOrderFlag)){
			return listBuyOrder();
		}else {
			return list();
		}
		
	}
	
	

	@Override
	protected void addCondition() {
	
	}

	@Override
	protected void init() {
		/***
		 * 订单状态为录入
		 */
		sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
	}

	@Override
	protected void dealWithSellOrderInputDTO() {
	}

	@Override
	protected void initOrderType() {
		
		
	}

	@Override
	public String edit() {
		
		return null;
	}

	public String getBuyOrderFlag() {
		return buyOrderFlag;
	}

	public void setBuyOrderFlag(String buyOrderFlag) {
		this.buyOrderFlag = buyOrderFlag;
	}
	
	
	/***
	 * 添加
	 * 采购订单
	 * @return
	 */
	

	
	
}
