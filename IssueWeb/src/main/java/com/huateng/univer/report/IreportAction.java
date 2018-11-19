package com.huateng.univer.report;

import java.net.URLEncoder;

import com.allinfinance.univer.report.IreportDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.util.Config;

/**
 * 报表处理
 */
public class IreportAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6911509068002612132L;

	// 报表所需要的参数
	private IreportDTO ireportDTO;

	public IreportDTO getIreportDTO() {
		return ireportDTO;
	}

	public void setIreportDTO(IreportDTO ireportDTO) {
		this.ireportDTO = ireportDTO;
	}

	public String viewReport() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(ireportDTO.getReportName());
		sb.append(";" + ireportDTO.getReportType());
		sb.append(";" + ireportDTO.getIssuerId());
		sb.append(";" + ireportDTO.getIssuerName());
		sb.append(";" + ireportDTO.getReportFileName());
		for (int i = 0; i < ireportDTO.getParValue().length; i++) {
			sb.append(";" + ireportDTO.getParValue()[i]);
		}
		getResponse().sendRedirect(
				//http://localhost:7002/ReportServerWeb/viewReport.action
				Config.getIreportURL().replace(".action", "Old.action")
						+ "?par=" + URLEncoder.encode(sb.toString(), "UTF-8"));
		// setUrl("http://130.251.9.207:8080/ReportServerTestWeb/viewReport.action?par='report1,我我'");
		return NONE;
	}

}
