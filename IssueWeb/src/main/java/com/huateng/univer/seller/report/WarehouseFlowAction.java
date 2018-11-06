package com.huateng.univer.seller.report;

import net.sf.json.JSONObject;

import com.allinfinance.univer.report.dto.WarehouseFlowDTO;
import com.huateng.univer.report.action.NewIreportAction;

public class WarehouseFlowAction extends NewIreportAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;

	private WarehouseFlowDTO warehouseFlowDTO = new WarehouseFlowDTO();

	private Integer totalRows = 0;

	public Integer getTotalRows() {

		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public String execute() throws Exception {
		if (getUser().getEntityId().equals("0")) {
			this.addActionError("用户未登录请重新登录");
			return "error";
		} else {
			return "input";
		}

	}

	public String inQuery() throws Exception {
		warehouseFlowDTO.setReportName("WarehouseFlow");
		warehouseFlowDTO.setReportType("xls");
		warehouseFlowDTO.setIssuerId(getUser().getEntityId());
		warehouseFlowDTO.setIssuerName(getUser().getSellerName());
		warehouseFlowDTO.setReportFileName("库存流水报表");
		return "list";
	}

	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(warehouseFlowDTO);
	}

	public WarehouseFlowDTO getWarehouseFlowDTO() {
		return warehouseFlowDTO;
	}

	public void setWarehouseFlowDTO(WarehouseFlowDTO warehouseFlowDTO) {
		this.warehouseFlowDTO = warehouseFlowDTO;
	}

}
