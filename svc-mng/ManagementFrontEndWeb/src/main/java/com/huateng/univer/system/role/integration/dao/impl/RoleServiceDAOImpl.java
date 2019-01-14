package com.huateng.univer.system.role.integration.dao.impl;

import java.util.List;

import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.ibatis.dao.RoleDAOImpl;
import com.huateng.univer.system.role.integration.dao.RoleServiceDAO;

public class RoleServiceDAOImpl extends RoleDAOImpl implements RoleServiceDAO {
	@SuppressWarnings("unchecked")
	public List<ResourceDTO> getResourceDTOs() {
		try {
			List<ResourceDTO> resourceDTOs = (List<ResourceDTO>) getSqlMapClientTemplate()
					.queryForList("ROLE.selectResource");
			return resourceDTOs;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ResourceDTO> getAllResourceDTO() {
		try {
			List<ResourceDTO> resourceDTOs = (List<ResourceDTO>) getSqlMapClientTemplate()
					.queryForList("ROLE.selectResourceAll");
			return resourceDTOs;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ResourceDTO> getIssueResourceDTOs(UserDTO user) {
		try {
			List<ResourceDTO> resourceDTOs = (List<ResourceDTO>) getSqlMapClientTemplate()
					.queryForList("ROLE.selectIssueResource", user);
			return resourceDTOs;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ResourceDTO> getIssueResourceRole(UserDTO user) {
		try {
			List<ResourceDTO> resourceDTOs = (List<ResourceDTO>) getSqlMapClientTemplate()
					.queryForList("ROLE.selectIssueResourceRole", user);
			return resourceDTOs;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ResourceDTO> getUserResourceRole(UserDTO user) {
		try {
			List<ResourceDTO> resourceDTOs = (List<ResourceDTO>) getSqlMapClientTemplate()
					.queryForList("ROLE.userIssueResourceRole", user);
			return resourceDTOs;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return null;
	}
}
