package com.huateng.univer.stockcard;

import java.math.BigDecimal;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.shangqi.gateway.dto.AccountConsumDTO;
import com.allinfinance.shangqi.gateway.dto.ApplyAndBindCardDTO;
import com.allinfinance.univer.stockcard.dto.StockCardNoDTO;
import com.allinfinance.univer.stockcard.dto.StockCardQueryDTO;
import com.huateng.framework.action.BaseAction;

public class StockCardAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4889286505164689166L;
	private StockCardQueryDTO stockCardQueryDTO;
	private StockCardNoDTO stockCardNoDTO;
	private PageDataDTO pageDataDTO;
	private int totalRows;
	private String selectCardNoList;
	
	
	private static final BigDecimal mut = new BigDecimal(100);

	public String query() {
		if (stockCardQueryDTO == null) {
			stockCardQueryDTO = new StockCardQueryDTO();
		}
		stockCardQueryDTO.setEntityId(getUser().getEntityId());
		//库存查询时候区分角色
		String functionRoleId = getRequest().getParameter("role");
		if(functionRoleId != null){			
			stockCardQueryDTO.setFunctionRoleId(functionRoleId);
		}
		
		try{
			if (stockCardQueryDTO.getFaceValue() != null && stockCardQueryDTO.getFaceValue().trim().length() != 0){
				stockCardQueryDTO.setFaceValue(new BigDecimal(stockCardQueryDTO.getFaceValue()).multiply(mut).toString());
			}
		}catch(Exception ex){
			this.addActionError("面额格式输入错误, 小数点后最多只能2位");
		}

		this.pageDataDTO = (PageDataDTO) sendService(
				ConstCode.STOCK_CARD_QUERY, stockCardQueryDTO).getDetailvo();
//		this.pageDataDTO = (PageDataDTO) sendService(
//				"201508042", dto).getDetailvo();
		
		try{
			if (stockCardQueryDTO.getFaceValue() != null && stockCardQueryDTO.getFaceValue().trim().length() != 0){
				stockCardQueryDTO.setFaceValue(new BigDecimal(stockCardQueryDTO.getFaceValue()).divide(mut).toString());
			}
		}catch(Exception ex){
			this.addActionError("面额格式输入错误, 小数点后最多只能2位");
		}
		
		if (hasErrors()) {
			return "input";
		}
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		return "list";
	}
	
	public String cardListView() {
		try {
			ListPageInit("cardList", stockCardNoDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (stockCardNoDTO == null) {
			stockCardNoDTO = new StockCardNoDTO();
		}
		stockCardNoDTO.setEntityId(getUser().getEntityId());
		//库存查询时候区分角色
		String functionRoleId = getRequest().getParameter("role");
		String productId = getRequest().getParameter("productId");
		if(functionRoleId != null){			
			stockCardNoDTO.setFunctionRoleId(functionRoleId);
		}
		if(productId != null){			
			stockCardNoDTO.setProductId(productId);
		}
		
		this.pageDataDTO = (PageDataDTO) sendService(
				ConstCode.CARDNO_BY_PRODUCTID, stockCardNoDTO).getDetailvo();
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		return "cardNo";
		
	}
	
	/**
	 * 锁卡、解锁
	 * @return
	 */
	public String updateStockState(){
		if(selectCardNoList==null||selectCardNoList.trim().equals("")){
			addActionError("请选择卡号!");
		}else{
			String newStockState = stockCardNoDTO.getStockState();
			stockCardNoDTO.setCardNoList(selectCardNoList);
			stockCardNoDTO = (StockCardNoDTO) sendService(ConstCode.UPDATE_CARD_STOCK_STATE, stockCardNoDTO).getDetailvo();
			if(stockCardNoDTO ==null){
				addActionError("所修改状态异常！");
				return cardListView();
			}
			if(stockCardNoDTO.getCardNoList()!=null){
				String errorCard = stockCardNoDTO.getCardNo();
				stockCardNoDTO.setCardNo(null);
				if(newStockState.equals("1")){
					addActionError("卡号"+errorCard+"已为激活状态不能重复解除锁定!");
				}else if(newStockState.equals("6")){
					addActionError("卡号"+errorCard+"已为锁定状态不能重复锁定!");
				}
				
			}
			
			if(!hasActionErrors()){
				addActionMessage("修改成功!");
				return cardListView();
			}
			
		}
		return cardListView();
	}
	
	
	
	

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public StockCardQueryDTO getStockCardQueryDTO() {
		return stockCardQueryDTO;
	}

	public void setStockCardQueryDTO(StockCardQueryDTO stockCardQueryDTO) {
		this.stockCardQueryDTO = stockCardQueryDTO;
	}

	public StockCardNoDTO getStockCardNoDTO() {
		return stockCardNoDTO;
	}

	public void setStockCardNoDTO(StockCardNoDTO stockCardNoDTO) {
		this.stockCardNoDTO = stockCardNoDTO;
	}

	public String getSelectCardNoList() {
		return selectCardNoList;
	}

	public void setSelectCardNoList(String selectCardNoList) {
		this.selectCardNoList = selectCardNoList;
	}
	
	

}
