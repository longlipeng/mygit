package com.huateng.univer.issuer.report;

import net.sf.json.JSONObject;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.report.dto.SellerSellDetailDTO;
import com.allinfinance.univer.seller.seller.dto.SellerQueryDTO;
import com.huateng.univer.report.action.NewIreportAction;

/**
 * 营销运营明细報表
 */
public class SellerSellDetailAction extends NewIreportAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;

	private SellerQueryDTO sellerQueryDTO = new SellerQueryDTO();

	private PageDataDTO pageDataDTO = new PageDataDTO();

	private int totalRows = 0;

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	private SellerSellDetailDTO sellerSellDetailDTO = new SellerSellDetailDTO();


	public String inQuery() throws Exception {
		sellerSellDetailDTO.setReportName("seller_sell_detail");
		sellerSellDetailDTO.setReportType("xls");
		sellerSellDetailDTO.setIssuerId(getUser().getEntityId());
		sellerSellDetailDTO.setIssuerName(getUser().getIssuerName());
		sellerSellDetailDTO.setReportFileName("营销运营明细表");
		
		return "list";
	}

	@Override
	protected JSONObject getJSONOBJect() {
		if("all".equals(sellerSellDetailDTO.getQueryType())||"issue".equals(sellerSellDetailDTO.getQueryType())){
			sellerSellDetailDTO.setSellerId(sellerSellDetailDTO.getIssuerId());
			sellerSellDetailDTO.setSellerName(sellerSellDetailDTO.getIssuerName());
		}
		return JSONObject.fromObject(sellerSellDetailDTO);
	}

	public String list() throws Exception {
		ListPageInit(null, sellerQueryDTO);
		pageDataDTO = (PageDataDTO) sendService(ConstCode.SELLER_SELECT_ORDER,
				sellerQueryDTO).getDetailvo();
		if (null != pageDataDTO && pageDataDTO.getData().size() > 0) {
			totalRows = pageDataDTO.getTotalRecord();
		} else {
			totalRows = 0;
		}
		return "list";
	}

	public SellerQueryDTO getSellerQueryDTO() {
		return sellerQueryDTO;
	}

	public void setSellerQueryDTO(SellerQueryDTO sellerQueryDTO) {
		this.sellerQueryDTO = sellerQueryDTO;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public SellerSellDetailDTO getSellerSellDetailDTO() {
		return sellerSellDetailDTO;
	}

	public void setSellerSellDetailDTO(SellerSellDetailDTO sellerSellDetailDTO) {
		this.sellerSellDetailDTO = sellerSellDetailDTO;
	}
}
