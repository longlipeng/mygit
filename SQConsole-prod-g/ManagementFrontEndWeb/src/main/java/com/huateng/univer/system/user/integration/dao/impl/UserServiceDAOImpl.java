package com.huateng.univer.system.user.integration.dao.impl;

import java.util.List;

import com.allinfinance.framework.dto.RoleDatePurviewDTO;
import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.UserDAOImpl;
import com.huateng.univer.system.user.integration.dao.UserServiceDAO;

public class UserServiceDAOImpl extends UserDAOImpl implements UserServiceDAO {

	/**
	 * 根据用户ID，返回用户信息。
	 */
	public UserDTO findUser(String userName) throws Exception {

		UserDTO userDTO = (UserDTO) this.getSqlMapClientTemplate()
				.queryForObject("USER.findUser", userName);
		if (userDTO == null) {
			throw new BizServiceException(userName + "userName error!");
		}

		if ("0".equals(userDTO.getUserState())) {
			throw new BizServiceException(userName + "Invalid User!");
		}

		return userDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.accor.user.integration.dao.UserServiceDAO#getAllResourceByUserId
	 * (java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<ResourceDTO> getAllResourceByUserId(UserDTO userDTO)
			throws Exception {

		List<ResourceDTO> resourceDTOList = (List<ResourceDTO>) this
				.getSqlMapClientTemplate().queryForList(
						"RESOURCE.getResourceByUserId", userDTO);
		return resourceDTOList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.accor.user.integration.dao.UserServiceDAO#getMaxRoleDatePurview
	 * (java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<RoleDatePurviewDTO> getMaxRoleDatePurview(Integer userId)
			throws Exception {

		List<RoleDatePurviewDTO> roleDatePurviewDTOList = (List<RoleDatePurviewDTO>) this
				.getSqlMapClientTemplate().queryForList(
						"USER.getRoleDatePurviewByUserId", userId);
		return roleDatePurviewDTOList;
	}

	/***
	 * 根据用户所属实体ID，将它子类的实体ID查出
	 */
	@SuppressWarnings("unchecked")
	public UserDTO getAllUnit(UserDTO userDTO) throws Exception {
		if (!isEmpty(userDTO.getIssuerName())) {
			userDTO
					.setIssuerDTOList((this.getSqlMapClientTemplate()
							.queryForList("USER.findNextIssuer", userDTO
									.getEntityId())));
		}

		if (!isEmpty(userDTO.getSellerName())) {
			userDTO
					.setSerllerDTOList(this.getSqlMapClientTemplate()
							.queryForList("USER.findNextSeller",
									userDTO.getEntityId()));
		}
		return userDTO;
	}

	public boolean isEmpty(String str) {
		if ("".equals(str) || str == null) {
			return true;
		} else {
			return false;
		}
	}
}
