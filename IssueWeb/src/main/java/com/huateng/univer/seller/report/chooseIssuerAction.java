package com.huateng.univer.seller.report;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.seller.dto.SellerQueryDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractQueryDTO;
import com.huateng.framework.action.BaseAction;

public class chooseIssuerAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2290099802808406149L;
	private Integer totalRows=0;
	private SellerQueryDTO sellerQueryDTO=new SellerQueryDTO();
	private PageDataDTO pageDataDTO=new PageDataDTO();
	
	public SellerQueryDTO getSellerQueryDTO() {
		return sellerQueryDTO;
	}

	public void setSellerQueryDTO(SellerQueryDTO sellerQueryDTO) {
		this.sellerQueryDTO = sellerQueryDTO;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public String choiceIssuer() throws Exception{
		ListPageInit(null, sellerQueryDTO);
	        sellerQueryDTO.setEntityId(getUser().getEntityId());
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.SELLER_CONTRACT_SERVICE_ISSUER,
					sellerQueryDTO).getDetailvo();

		if (null != pageDataDTO && pageDataDTO.getData().size() > 0) {
			totalRows = pageDataDTO.getTotalRecord();
		} else {
			totalRows = 0;
		}
		return "issuerChoice";
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

}
