package com.huateng.univer.system.syslog;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.syslog.SystemLogDTO;
import com.allinfinance.univer.system.syslog.SystemLogQueryDTO;
import com.allinfinance.univer.system.sysparam.dto.SystemParameterDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.SystemInfo;

public class SystemLogAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private PageDataDTO pageDataDTO = new PageDataDTO();
	private SystemLogQueryDTO systemLogQueryDTO = new SystemLogQueryDTO();
	private SystemLogDTO systemLogDTO = new SystemLogDTO();
	private SystemParameterDTO systemParameterDTO = new SystemParameterDTO();

	private String inputPage;
	private Integer totalRows = 0;

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	/**
	 * 系统参数信息初始化
	 * 
	 * @return
	 * @throws
	 */
	public String init() throws Exception {
		systemLogQueryDTO = new SystemLogQueryDTO();
		return inquery();
	}

	/**
	 * 查询系统日志信息
	 */
	public String inquery() throws Exception {

		ListPageInit(null, systemLogQueryDTO);
		if (systemLogQueryDTO.isQueryAll()) {
			systemLogQueryDTO.setQueryAll(false);
			systemLogQueryDTO
					.setRowsDisplayed(Integer
							.parseInt(SystemInfo
									.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
		}

		if (systemLogQueryDTO.getSortFieldName() == null
				|| "".equals(systemLogQueryDTO.getSortFieldName())) {
			systemLogQueryDTO.setSortFieldName("operationTime");
			systemLogQueryDTO.setSort("desc");
		}

		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.SYSTEMLOG_SERVICE_INQUERY, systemLogQueryDTO)
				.getDetailvo();
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		return "list";
	}

	/**
	 * 查询系统参数信息
	 */

	public String loadSysLog() throws Exception {
		systemLogDTO = (SystemLogDTO) sendService(
				ConstCode.SYSTEMLOG_SERVICE_VIEW, systemLogDTO).getDetailvo();
		if (systemLogDTO != null && systemLogDTO.getOperationTime() != null
				&& !"".equals(systemLogDTO.getOperationTime())) {
			systemLogDTO.setOperationTime(DateUtil
					.formatStringDate(systemLogDTO.getOperationTime()));
		}
		if (this.hasErrors()) {
			return inquery();
		}
		return "view";
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public SystemLogQueryDTO getSystemLogQueryDTO() {
		return systemLogQueryDTO;
	}

	public void setSystemLogQueryDTO(SystemLogQueryDTO systemLogQueryDTO) {
		this.systemLogQueryDTO = systemLogQueryDTO;
	}

	public SystemLogDTO getSystemLogDTO() {
		return systemLogDTO;
	}

	public void setSystemLogDTO(SystemLogDTO systemLogDTO) {
		this.systemLogDTO = systemLogDTO;
	}

	public String getInputPage() {
		return inputPage;
	}

	public void setInputPage(String inputPage) {
		this.inputPage = inputPage;
	}

	public SystemParameterDTO getSystemParameterDTO() {
		return systemParameterDTO;
	}

	public void setSystemParameterDTO(SystemParameterDTO systemParameterDTO) {
		this.systemParameterDTO = systemParameterDTO;
	}

}
