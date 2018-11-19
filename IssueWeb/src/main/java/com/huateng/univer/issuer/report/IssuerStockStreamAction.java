package com.huateng.univer.issuer.report;


import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.IssuerStockStreamDTO;
import com.huateng.univer.report.action.NewIreportAction;




/**
 * 营销机构的库存流水報表
 */
public class IssuerStockStreamAction extends NewIreportAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;




	
	
	private IssuerStockStreamDTO issuerStockStreamDTO = new IssuerStockStreamDTO();
	


	public IssuerStockStreamDTO getIssuerStockStreamDTO() {
		return issuerStockStreamDTO;
	}

	public void setIssuerStockStreamDTO(IssuerStockStreamDTO issuerStockStreamDTO) {
		this.issuerStockStreamDTO = issuerStockStreamDTO;
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
			this.addActionError("当前为一个发卡组，请选择一个发卡机构");
			return "error";
		}
		else
		{
			return "input";
		}
		
	}
	public String inQuery() throws Exception{
		issuerStockStreamDTO.setReportName("issuer_stockstream_ireport");
		issuerStockStreamDTO.setReportType("xls");
		issuerStockStreamDTO.setIssuerId(getUser().getEntityId());
		issuerStockStreamDTO.setIssuerName(getUser().getIssuerName());
		issuerStockStreamDTO.setReportFileName("库存流水表");
		return "list";
	}

	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(issuerStockStreamDTO);
	}
	


	
	

}
