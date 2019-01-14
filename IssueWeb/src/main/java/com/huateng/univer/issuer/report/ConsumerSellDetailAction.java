package com.huateng.univer.issuer.report;


import java.util.HashMap;
import java.util.Map;



import com.allinfinance.univer.report.IreportDTO;
import com.huateng.framework.action.BaseAction;




/**
 * 收单运营汇总報表
 */
public class ConsumerSellDetailAction extends BaseAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;
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
			this.addActionError("当前为一个发卡组，请选择一个发卡机构");
			return "error";
		}
		else
		{
			return "input";
		}
		
	}
	public String inQuery() throws Exception{
		ireportDTO.setReportName("consumer_sell_detail");
		ireportDTO.setReportType("xls");
		ireportDTO.setIssuerId(getUser().getEntityId());
		ireportDTO.setIssuerName(getUser().getIssuerName());
		ireportDTO.setReportFileName("收单运营明细表");
		return "list";
	}
	

	

}
