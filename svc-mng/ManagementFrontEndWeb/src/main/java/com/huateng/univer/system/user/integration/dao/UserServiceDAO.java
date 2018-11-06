package com.huateng.univer.system.user.integration.dao;

import java.util.List;

import com.allinfinance.framework.dto.RoleDatePurviewDTO;
import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.ibatis.dao.UserDAO;

public interface UserServiceDAO extends UserDAO {

	/**
	 * 根据用户ID 返回用户信息，包括发卡机构，发卡机构组等信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserDTO findUser(String userName) throws Exception;

	/**
	 * 根据用户ID 取得该用户下所有受保护资源
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<ResourceDTO> getAllResourceByUserId(UserDTO userDTO)
			throws Exception;

	/**
	 * 根据用户ID取得该用户下的所有角色数据权限信息
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<RoleDatePurviewDTO> getMaxRoleDatePurview(Integer userId)
			throws Exception;

	/***
	 * 根据用户实体ID，取得所属实体ID下所有的子实体
	 * 
	 * @param userDTO
	 * @return
	 * @throws Exception
	 */
	public UserDTO getAllUnit(UserDTO userDTO) throws Exception;
}
