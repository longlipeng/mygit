package com.huateng.univer.seller.report;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.report.IreportDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractQueryDTO;
import com.huateng.framework.action.BaseAction;

public class WarehouseDetailAction extends BaseAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;



private SellerContractQueryDTO sellerContractQueryDTO =new SellerContractQueryDTO();
	private PageDataDTO pageDataDTO=new PageDataDTO();
	
	private IreportDTO ireportDTO=new IreportDTO();
	
	
	public SellerContractQueryDTO getSellerContractQueryDTO() {
		return sellerContractQueryDTO;
	}

	public void setSellerContractQueryDTO(
			SellerContractQueryDTO sellerContractQueryDTO) {
		this.sellerContractQueryDTO = sellerContractQueryDTO;
	}

	public IreportDTO getIreportDTO() {
		return ireportDTO;
	}

	public void setIreportDTO(IreportDTO ireportDTO) {
		this.ireportDTO = ireportDTO;
	}

	private Integer totalRows=0;
	
	
	

	public Integer getTotalRows() {
		
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	

	public String choiceProduct() throws Exception{
		ListPageInit(null, sellerContractQueryDTO);
		sellerContractQueryDTO.setDefaultEntityId(getUser().getEntityId());
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.SELLER_CONTRACT_SERVICE_PRODUCT,
					sellerContractQueryDTO).getDetailvo();

		if (null != pageDataDTO && pageDataDTO.getData().size() > 0) {
			totalRows = pageDataDTO.getTotalRecord();
		} else {
			totalRows = 0;
		}
		return "productChoice";
	}


	public String execute() throws Exception{
		if(getUser().getEntityId().equals("0"))
		{
			this.addActionError("褰撳墠涓轰竴涓彂鍗＄粍锛岃閫夋嫨涓�涓彂鍗℃満鏋�");
			return "error";
		}
		else
		{
			return "input";
		}
		
	}
	public String inQuery() throws Exception{
		ireportDTO.setReportName("WarehouseFlow");
		ireportDTO.setReportType("xls");
		ireportDTO.setIssuerId(getUser().getEntityId());
		ireportDTO.setIssuerName(getUser().getSellerName());
		ireportDTO.setReportFileName("库存详细报表");
		return "list";
	}
}
