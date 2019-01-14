package com.huateng.univer.issuer.report;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;







import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerQueryDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.allinfinance.univer.report.dto.IssuerStockSumDTO;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;
import com.huateng.univer.report.action.NewIreportAction;

/**
 * 发行机构的库存汇总报表
 */
public class IssuerStockSumAction extends NewIreportAction {
	private Logger logger = Logger.getLogger(IssuerStockSumAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;

	private IssuerQueryDTO issuerQueryDTO = new IssuerQueryDTO();
	private PageDataDTO pageDataDTO = new PageDataDTO();

	private IssuerStockSumDTO issuerStockSumDTO = new IssuerStockSumDTO();
	
	private List<ProductDTO> productDTOs=new ArrayList<ProductDTO>();
	
	public List<ProductDTO> getProductDTOs() {
		return productDTOs;
	}

	public void setProductDTOs(List<ProductDTO> productDTOs) {
		this.productDTOs = productDTOs;
	}

	public IssuerQueryDTO getIssuerQueryDTO() {
		return issuerQueryDTO;
	}

	public void setIssuerQueryDTO(IssuerQueryDTO issuerQueryDTO) {
		this.issuerQueryDTO = issuerQueryDTO;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	private Integer totalRows = 0;

	public Integer getTotalRows() {

		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public String execute() throws Exception {
		if (getUser().getEntityId().equals("0")) {
			this.addActionError("当前为一个发卡组，请选择一个发卡机构");
			return "error";
		} else {
			return "input";
		}

	}

	public String inQuery() throws Exception {
		issuerStockSumDTO.setReportName("issuer_stocksum_ireport");
		issuerStockSumDTO.setReportType("xls");
		issuerStockSumDTO.setIssuerId(getUser().getEntityId());
		issuerStockSumDTO.setIssuerName(getUser().getIssuerName());
		issuerStockSumDTO.setReportFileName("库存汇总表");
		ProductQueryDTO productQueryDTO=new ProductQueryDTO();
//		productQueryDTO.setEntityId(getUser().getEntityId());
		productQueryDTO.setQueryAll(true);
		productDTOs = (List<ProductDTO>)((PageDataDTO)sendService(
				"1002010000",productQueryDTO)
				.getDetailvo()).getData();
		
		return "list";
	}

	// 查询包含自身的发行机构
	public String issuerChoose() throws Exception {
		try {
			ListPageInit(null, issuerQueryDTO);
			if (issuerQueryDTO.isQueryAll()) {
				issuerQueryDTO.setQueryAll(false);
				issuerQueryDTO
						.setRowsDisplayed(Integer
								.parseInt((SystemInfo
										.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM))));
			}
			// 需修改
			issuerQueryDTO.setEntityId(getUser().getEntityId());
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.SELLER_SERVICE_ISSUERORSELF, issuerQueryDTO)
					.getDetailvo();

			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (hasErrors()) {
				return INPUT;
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "choose";
	}

	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(issuerStockSumDTO);
	}

	public IssuerStockSumDTO getIssuerStockSumDTO() {
		return issuerStockSumDTO;
	}

	public void setIssuerStockSumDTO(IssuerStockSumDTO issuerStockSumDTO) {
		this.issuerStockSumDTO = issuerStockSumDTO;
	}

	
}
