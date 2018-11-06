package com.huateng.framework.security.service;

import java.util.List;

import com.allinfinance.univer.system.role.dto.ResourceDTO;

/**
 * 安全Service
 * 
 * @author liming.feng
 */
public interface SecurityService {

	/**
	 * 读取url的访问权限
	 * 
	 * @return
	 */
	public List<ResourceDTO> loadUrlAuthorities();

}
