/**
 * Classname ServiceSubject.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		wpf		2012-10-17
 * =============================================================================
 */

package com.huateng.service;

import java.util.Map;
/**
 * @author wpf
 * 
 */
public class ServiceSubject {
	private Map<String, BizService> serviceMap;
	private Map<String, String> reportNameMap;

	public Map<String, BizService> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(Map<String, BizService> serviceMap) {
		this.serviceMap = serviceMap;
	}

	public Map<String, String> getReportNameMap() {
		return reportNameMap;
	}

	public void setReportNameMap(Map<String, String> reportNameMap) {
		this.reportNameMap = reportNameMap;
	}
}
