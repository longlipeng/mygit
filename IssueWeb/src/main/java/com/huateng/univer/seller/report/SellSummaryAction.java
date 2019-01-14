package com.huateng.univer.seller.report;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.report.IreportDTO;
import com.allinfinance.univer.seller.seller.dto.SellerQueryDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;

public class SellSummaryAction  extends BaseAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;
	private SellerQueryDTO sellerQueryDTO = new SellerQueryDTO();
	private List<ProductDTO> productDTOs=new ArrayList<ProductDTO>();
	
	public List<ProductDTO> getProductDTOs() {
		return productDTOs;
	}

	public void setProductDTOs(List<ProductDTO> productDTOs) {
		this.productDTOs = productDTOs;
	}

	private PageDataDTO pageDataDTO = new PageDataDTO();
	
	
	private IreportDTO ireportDTO=new IreportDTO();
	
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
		ireportDTO.setReportName("seller_seller_summary");
		ireportDTO.setReportType("xls");
		ireportDTO.setIssuerId(getUser().getEntityId());
		ireportDTO.setIssuerName(getUser().getSellerName());
		ireportDTO.setReportFileName("营销运营汇总报表");
		
		return "list";
	}
	
	
}
