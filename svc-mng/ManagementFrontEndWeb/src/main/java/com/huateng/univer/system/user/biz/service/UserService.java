package com.huateng.univer.system.user.biz.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.allinfinance.univer.system.user.dto.UserQueryDTO;
import com.huateng.framework.exception.BizServiceException;

/*
 *用户信息管理
 */
public interface UserService {
	public UserDTO checkUserName(UserDTO userDTO) throws BizServiceException;

	/**
	 * 查询相关姓名的用户信息
	 * 
	 * @param userName
	 * @return
	 * @throws BizServiceException
	 */
	public UserDTO findUser(String userName) throws BizServiceException;

	/**
	 * 查询用户信息
	 * 
	 * @param userQueryDTO
	 * @return
	 * @throws BizServiceException
	 */
	public PageDataDTO inquery(UserQueryDTO userQueryDTO)
			throws BizServiceException;

	/**
	 * 调用用户信息
	 * 
	 * @param userDTO
	 * @return
	 * @throws BizServiceException
	 */
	public UserDTO viewUser(UserDTO userDTO) throws BizServiceException;

	public UserDTO getUserById(String userId) throws BizServiceException;

	public void insertUserForEntity(String entityId, String userId,
			String userName, String password, String email, String operateUser)
			throws BizServiceException;

	/**
	 * 添加用户信息
	 * 
	 * @param userDTO
	 * @throws BizServiceException
	 */
	public UserDTO insertUser(UserDTO userDTO) throws BizServiceException;

	/**
	 * 添加用户的角色信息
	 * 
	 * @param userDTO
	 * @throws BizServiceException
	 */
	public void addRole(UserDTO userDTO) throws BizServiceException;

	/**
	 * 删除用户的角色信息
	 * 
	 * @param userDTO
	 * @throws BizServiceException
	 */
	public void deleteRole(UserDTO userDTO) throws BizServiceException;

	/**
	 * 修改用户信息
	 * 
	 * @param userDTO
	 * @throws BizServiceException
	 */
	public void updateUser(UserDTO userDTO) throws BizServiceException;

	/**
	 * 删除用户信息
	 * 
	 * @param userDT0
	 * @throws BizServiceException
	 */
	public void deleteUser(UserDTO userDT0) throws BizServiceException;

	public void modifyPassword(UserDTO userDTO) throws BizServiceException;
}
