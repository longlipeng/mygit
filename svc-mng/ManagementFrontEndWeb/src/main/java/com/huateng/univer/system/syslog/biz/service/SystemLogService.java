package com.huateng.univer.system.syslog.biz.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.syslog.SystemLogDTO;
import com.allinfinance.univer.system.syslog.SystemLogQueryDTO;
import com.huateng.framework.exception.BizServiceException;

public interface SystemLogService {

	public PageDataDTO inquerySystemLog(SystemLogQueryDTO systemLogQueryDTO)
			throws BizServiceException;

	public SystemLogDTO loadSysLog(SystemLogDTO systemLogDTO)
			throws BizServiceException;
}
