package com.huateng.univer.system.role.biz.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.role.dto.RoleDTO;
import com.allinfinance.univer.system.role.dto.RoleQueryDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.exception.BizServiceException;

public interface RoleService {
	/**
	 * 菜单信息查询
	 * 
	 * @return
	 * @throws BizServiceException
	 */
	public List<ResourceDTO> selectResource(UserDTO userDTO)
			throws BizServiceException;

	/**
	 * 全菜单信息查询
	 * 
	 * @return
	 * @throws BizServiceException
	 */
	public List<ResourceDTO> getAllResource() throws BizServiceException;

	/**
	 * 角色信息查询
	 * 
	 * @param roleQueryDTO
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inqueryRole(RoleQueryDTO roleQueryDTO)
			throws BizServiceException;

	/**
	 * 添加角色信息
	 * 
	 * @param roleDTO
	 * @throws BizServiceException
	 */
	public void insertRole(RoleDTO roleDTO) throws BizServiceException;

	/**
	 * 更新角色信息
	 * 
	 * @param roleDTO
	 * @throws BizServiceException
	 */
	public void updateRole(RoleDTO roleDTO) throws BizServiceException;

	/**
	 * 调用角色信息
	 * 
	 * @param roleDTO
	 * @return
	 * @throws BizServiceException
	 */
	public RoleDTO viewRole(RoleDTO roleDTO) throws BizServiceException;

	/**
	 * 删除角色信息
	 * 
	 * @param roleDTO
	 * @throws BizServiceException
	 */
	public void deleteRole(RoleDTO roleDTO) throws BizServiceException;

	/**
	 * 调用角色共用方法
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public List<ResourceDTO> selectIssueResource(UserDTO dto)
			throws BizServiceException;
}
