package com.allinfinance.univer.system.role.dto;

import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.framework.dto.RoleDatePurviewDTO;

public class RoleDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roleId;
	private String roleName;
	private String roleComment;
	private String createUser;
	private String modifyUser;
	private List<ResourceDTO> resourceDTOs;
	private List<String> roleIds;
	private RoleDatePurviewDTO roleDatePurviewDTO;
	private String resourceIds;

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleComment() {
		return roleComment;
	}

	public void setRoleComment(String roleComment) {
		this.roleComment = roleComment;
	}

	public List<ResourceDTO> getResourceDTOs() {
		return resourceDTOs;
	}

	public void setResourceDTOs(List<ResourceDTO> resourceDTOs) {
		this.resourceDTOs = resourceDTOs;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public RoleDatePurviewDTO getRoleDatePurviewDTO() {
		return roleDatePurviewDTO;
	}

	public void setRoleDatePurviewDTO(RoleDatePurviewDTO roleDatePurviewDTO) {
		this.roleDatePurviewDTO = roleDatePurviewDTO;
	}

}
