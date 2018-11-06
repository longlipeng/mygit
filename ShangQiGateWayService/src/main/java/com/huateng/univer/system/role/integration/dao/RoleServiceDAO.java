package com.huateng.univer.system.role.integration.dao;

import java.util.List;

import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.ibatis.dao.RoleDAO;

public interface RoleServiceDAO extends RoleDAO {
	List<ResourceDTO> getResourceDTOs();

	List<ResourceDTO> getAllResourceDTO();

	List<ResourceDTO> getIssueResourceDTOs(UserDTO user);

	List<ResourceDTO> getIssueResourceRole(UserDTO user);

	List<ResourceDTO> getUserResourceRole(UserDTO user);
}
