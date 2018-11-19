package com.huateng.univer.seller.report;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.report.dto.WarehouseSummaryDTO;
import com.allinfinance.univer.seller.seller.dto.SellerQueryDTO;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;
import com.huateng.univer.report.action.NewIreportAction;

public class WarehouseSummaryAction extends NewIreportAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;
	private SellerQueryDTO sellerQueryDTO = new SellerQueryDTO();
	private List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
	private PageDataDTO pageDataDTO = new PageDataDTO();
	private Integer totalRows = 0;

	private WarehouseSummaryDTO warehouseSummaryDTO = new WarehouseSummaryDTO();

	@SuppressWarnings("unchecked")
	public String inQuery() throws Exception {
		warehouseSummaryDTO.setReportName("WarehouseSummary");
		warehouseSummaryDTO.setReportType("xls");
		warehouseSummaryDTO.setIssuerId(getUser().getEntityId());
		warehouseSummaryDTO.setIssuerName(getUser().getSellerName());
		warehouseSummaryDTO.setReportFileName("库存汇总报表");
		productDTOs = (List<ProductDTO>) sendService("1002010004",
				getUser().getEntityId()).getDetailvo();
		return "list";
	}

	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(warehouseSummaryDTO);
	}

	// 查询包含自身的营销机构
	public String choiceSellerOrSelf() throws Exception {
		ListPageInit(null, sellerQueryDTO);
		sellerQueryDTO.setEntityId(getUser().getEntityId());
		if (sellerQueryDTO.isQueryAll()) {
			sellerQueryDTO.setQueryAll(false);
			sellerQueryDTO
					.setRowsDisplayed(Integer
							.parseInt(SystemInfo
									.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
		}
		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.SELLER_SERVICE_INQUERYANDSELF, sellerQueryDTO)
				.getDetailvo();

		if (pageDataDTO.getData().size() > 0) {
			totalRows = pageDataDTO.getTotalRecord();
		} else {
			totalRows = 0;
		}
		return "choice";
	}

	public List<ProductDTO> getProductDTOs() {
		return productDTOs;
	}

	public void setProductDTOs(List<ProductDTO> productDTOs) {
		this.productDTOs = productDTOs;
	}

	public Integer getTotalRows() {

		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public WarehouseSummaryDTO getWarehouseSummaryDTO() {
		return warehouseSummaryDTO;
	}

	public void setWarehouseSummaryDTO(WarehouseSummaryDTO warehouseSummaryDTO) {
		this.warehouseSummaryDTO = warehouseSummaryDTO;
	}

}
