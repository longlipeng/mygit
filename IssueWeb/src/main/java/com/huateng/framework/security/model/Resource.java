package com.huateng.framework.security.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.role.dto.RoleDTO;

/**
 * @author liming.feng
 *
 */
public class Resource extends ResourceDTO {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8223466686552551998L;
	
    /**
     * @return
     */
    public String getRoleAuthorities() {
		List<String> roleAuthorities = new ArrayList<String>();
		for (RoleDTO role : this.getRoleDTOList()) {
			roleAuthorities.add(role.getRoleName());
		}
		return StringUtils.join(roleAuthorities.toArray(new String[0]), ",");
	}
}