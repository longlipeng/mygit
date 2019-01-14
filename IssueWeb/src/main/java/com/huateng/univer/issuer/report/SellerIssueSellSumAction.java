package com.huateng.univer.issuer.report;

import net.sf.json.JSONObject;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.report.dto.SellerIssueSellSumDTO;
import com.allinfinance.univer.seller.seller.dto.SellerQueryDTO;
import com.huateng.univer.report.action.NewIreportAction;

/**
 * 营销机构——发行运营汇总報表
 */
public class SellerIssueSellSumAction extends NewIreportAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;

	private SellerQueryDTO sellerQueryDTO = new SellerQueryDTO();

	private PageDataDTO pageDataDTO = new PageDataDTO();

	private Integer totalRows = 0;

	private SellerIssueSellSumDTO sellerIssueSellSumDTO = new SellerIssueSellSumDTO();
	
	public String inQuery() throws Exception {
		sellerIssueSellSumDTO.setReportName("issue_sell_sum");
		sellerIssueSellSumDTO.setReportType("xls");
		sellerIssueSellSumDTO.setSellerId(getUser().getEntityId());
		sellerIssueSellSumDTO.setSellerName(getUser().getSellerName());
		sellerIssueSellSumDTO.setReportFileName("发行运营汇总表");
		return "list";
	}

	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(sellerIssueSellSumDTO);
	}

	public String list() throws Exception {
		ListPageInit(null, sellerQueryDTO);
		sellerQueryDTO.setEntityId(getUser().getEntityId());
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

	public SellerIssueSellSumDTO getSellerIssueSellSumDTO() {
		return sellerIssueSellSumDTO;
	}

	public void setSellerIssueSellSumDTO(SellerIssueSellSumDTO sellerIssueSellSumDTO) {
		this.sellerIssueSellSumDTO = sellerIssueSellSumDTO;
	}

	public Integer getTotalRows() {

		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

}
