package com.huateng.framework.systemService;

import com.huateng.framework.exception.BizServiceException;

/**
 * 系统信息service
 * 
 * @author jianji.dai
 * 
 */

public interface SystemInfoService {

	/**
	 * 初始化系统数据（包括系统参数，字典数据）
	 */
	public void initSystemInfo() throws BizServiceException;
}
