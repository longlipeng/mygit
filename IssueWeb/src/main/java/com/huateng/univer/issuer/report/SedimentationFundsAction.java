package com.huateng.univer.issuer.report;




import net.sf.json.JSONObject;



import com.allinfinance.service.issueOperation.fundSubsideSum.dto.FundSubsideSumDTO;
import com.huateng.univer.report.action.NewIreportAction;




/**
 * 营销机构——发行运营汇总報表
 */
public class SedimentationFundsAction extends NewIreportAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;

	private FundSubsideSumDTO fundSubsideSumDTO=new FundSubsideSumDTO();
	



/*	private Integer totalRows=0;
	
	
	

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
		
	}*/
	
	

	public String inQuery() throws Exception{
		fundSubsideSumDTO.setReportName("sedimentation_funds");
		fundSubsideSumDTO.setReportType("xls");
		fundSubsideSumDTO.setIssuerId(getUser().getEntityId());
		fundSubsideSumDTO.setIssuerName(getUser().getIssuerName());
		fundSubsideSumDTO.setReportFileName("沉淀资金报表");
		return "list";
	}
	
	public FundSubsideSumDTO getFundSubsideSumDTO() {
		return fundSubsideSumDTO;
	}

	public void setFundSubsideSumDTO(FundSubsideSumDTO fundSubsideSumDTO) {
		this.fundSubsideSumDTO = fundSubsideSumDTO;
	}

	@Override
	protected JSONObject getJSONOBJect() {
		return JSONObject.fromObject(fundSubsideSumDTO);
	}


}
