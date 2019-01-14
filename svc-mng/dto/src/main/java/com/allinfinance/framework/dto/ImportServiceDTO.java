package com.allinfinance.framework.dto;

import java.util.List;
import java.util.Map;

import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerProductContractDTO;

public class ImportServiceDTO extends BaseDTO{
	
	private SellerContractDTO sellerContractDTO;
	
	
	private SellerProductContractDTO sellerProductContractDTO;
	
	private SellOrderInputDTO sellOrderInputDTO;
	
	private List<ImportServiceDTO> importServiceDtoList;
	
	private Map<String, String> map;
	private List<String> list;
	private String[] orderListInfo;
	
	
	
	
	

	

	public String[] getOrderListInfo() {
		return orderListInfo;
	}


	public void setOrderListInfo(String[] orderListInfo) {
		this.orderListInfo = orderListInfo;
	}


	public List<String> getList() {
		return list;
	}


	public void setList(List<String> list) {
		this.list = list;
	}


	public Map<String, String> getMap() {
		return map;
	}


	public void setMap(Map<String, String> map) {
		this.map = map;
	}


	public List<ImportServiceDTO> getImportServiceDtoList() {
		return importServiceDtoList;
	}


	public void setImportServiceDtoList(List<ImportServiceDTO> importServiceDtoList) {
		this.importServiceDtoList = importServiceDtoList;
	}


	public SellOrderInputDTO getSellOrderInputDTO() {
		return sellOrderInputDTO;
	}


	public void setSellOrderInputDTO(SellOrderInputDTO sellOrderInputDTO) {
		this.sellOrderInputDTO = sellOrderInputDTO;
	}


	public SellerContractDTO getSellerContractDTO() {
		return sellerContractDTO;
	}


	public void setSellerContractDTO(SellerContractDTO sellerContractDTO) {
		this.sellerContractDTO = sellerContractDTO;
	}


	public SellerProductContractDTO getSellerProductContractDTO() {
		return sellerProductContractDTO;
	}


	public void setSellerProductContractDTO(
			SellerProductContractDTO sellerProductContractDTO) {
		this.sellerProductContractDTO = sellerProductContractDTO;
	}
	
	

}
