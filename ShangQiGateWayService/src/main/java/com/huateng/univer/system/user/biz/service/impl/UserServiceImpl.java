package com.huateng.univer.system.user.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.role.dto.RoleDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.allinfinance.univer.system.user.dto.UserQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.ConsumerDAO;
import com.huateng.framework.ibatis.dao.IssueResourceDAO;
import com.huateng.framework.ibatis.dao.IssuerDAO;
import com.huateng.framework.ibatis.dao.SellerDAO;
import com.huateng.framework.ibatis.dao.TenantDAO;
import com.huateng.framework.ibatis.dao.UserRoleDAO;
import com.huateng.framework.ibatis.model.Consumer;
import com.huateng.framework.ibatis.model.ConsumerExample;
import com.huateng.framework.ibatis.model.IssueResourceExample;
import com.huateng.framework.ibatis.model.Issuer;
import com.huateng.framework.ibatis.model.IssuerExample;
import com.huateng.framework.ibatis.model.Role;
import com.huateng.framework.ibatis.model.Seller;
import com.huateng.framework.ibatis.model.SellerExample;
import com.huateng.framework.ibatis.model.Tenant;
import com.huateng.framework.ibatis.model.TenantExample;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.ibatis.model.UserExample;
import com.huateng.framework.ibatis.model.UserRoleExample;
import com.huateng.framework.ibatis.model.UserRoleKey;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.system.role.biz.service.RoleService;
import com.huateng.univer.system.role.integration.dao.RoleServiceDAO;
import com.huateng.univer.system.user.biz.service.UserService;
import com.huateng.univer.system.user.integration.dao.UserServiceDAO;

public class UserServiceImpl implements UserService {

	Logger logger = Logger.getLogger(UserServiceImpl.class);

	private PageQueryDAO pageQueryDAO;

	private CommonsDAO commonsDAO;

	private UserRoleDAO userRoleDAO;

	private RoleServiceDAO roleDAO;

	private RoleService roleService;

	private UserServiceDAO userServiceDAO;
	private IssuerDAO issuerDAO;
	private SellerDAO sellerDAO;
	private ConsumerDAO consumerDAO;
	private TenantDAO tennatDAO;
	private IssueResourceDAO issueResourceDAO;

	public UserDTO findUser(String userName) throws BizServiceException {

		try {
			// 取得用户信息
			UserDTO userDTO = userServiceDAO.findUser(userName);
			UserDTO userDTO_tmp = new UserDTO();
			userDTO_tmp.setEntityId(userDTO.getEntityId());
			userDTO_tmp.setUserId(userDTO.getUserId());
			// add zfh 判断用户是哪种机构
			String entityId = userDTO.getEntityId();
			List<String> functionRoleId = new ArrayList<String>();
			List<String> isSale = new ArrayList<String>();
			// 商业管理平台必须加入
			functionRoleId.add("0");
			TenantExample example0 = new TenantExample();
			example0.createCriteria().andEntityIdEqualTo(entityId)
					.andDataStateEqualTo("1");
			List<Tenant> tenantList = tennatDAO.selectByExample(example0);
			if (tenantList != null && tenantList.size() > 0) {
				functionRoleId.add("1");
				isSale.add("0");
			}
			IssuerExample example1 = new IssuerExample();
			example1.createCriteria().andEntityIdEqualTo(entityId)
					.andDataStateEqualTo("1");
			List<Issuer> issuerList = issuerDAO.selectByExample(example1);
			if (issuerList != null && issuerList.size() > 0) {
				functionRoleId.add("2");
				isSale.add("1");
			}
			SellerExample example2 = new SellerExample();
			example2.createCriteria().andEntityIdEqualTo(entityId)
					.andDataStateEqualTo("1");
			List<Seller> sellerList = sellerDAO.selectByExample(example2);
			if (sellerList != null && sellerList.size() > 0) {
				functionRoleId.add("3");
				isSale.add("2");
			}
			ConsumerExample example3 = new ConsumerExample();
			example3.createCriteria().andEntityIdEqualTo(entityId)
					.andDataStateEqualTo("1");
			List<Consumer> consumerList = consumerDAO.selectByExample(example3);
			if (consumerList != null && consumerList.size() > 0) {
				functionRoleId.add("4");
				isSale.add("3");
			}
			userDTO.setFunctionRoleId(functionRoleId);
			userDTO_tmp.setIsSale(isSale.toArray());
			// 根据用户角色取得用户资源信息

			IssueResourceExample example = new IssueResourceExample();
			example.createCriteria().andIssueIdEqualTo(
					userDTO_tmp.getEntityId()).andRankIn(
					isSale);
			if (issueResourceDAO.selectByExample(example).size() > 0) {
				userDTO.setResourceDTOList(roleDAO
						.getUserResourceRole(userDTO_tmp));

			} else {
				userDTO.setResourceDTOList(userServiceDAO
						.getAllResourceByUserId(userDTO));
			}

			// userServiceDAO.getAllUnit(userDTO);

			// 根据用户ID取得所有该用户下全部角色数据权限表信息
			// List<RoleDatePurviewDTO> list = userServiceDAO
			// .getMaxRoleDatePurview(userDTO.getUserId());
			// RoleDatePurviewDTO roleDatePurviewDTO = new RoleDatePurviewDTO();
			// for (RoleDatePurviewDTO r : list) {
			//				
			// // 取角色数据权限信息中最大的 费率修改
			// roleDatePurviewDTO.setModifyRate(getMaxValue(roleDatePurviewDTO
			// .getModifyRate(), r.getModifyRate()));
			// // 取角色数据权限信息中最大的 订单等级
			// roleDatePurviewDTO.setOrderPriority(getMaxValue(
			// roleDatePurviewDTO.getOrderPriority(), r
			// .getOrderPriority()));
			// // 取角色数据权限信息中最大的 支付方式
			// roleDatePurviewDTO
			// .setPaymentTerm(getMaxValue(roleDatePurviewDTO
			// .getPaymentTerm(), r.getPaymentTerm()));
			// // 取角色数据权限信息中最大的 销售订单折扣费
			// roleDatePurviewDTO
			// .setDiscountFee(getMaxValue(roleDatePurviewDTO
			// .getDiscountFee(), r.getDiscountFee()));
			// // 取角色数据权限信息中最大的 结算单手续费修改
			// roleDatePurviewDTO.setModifyServiceFee(getMaxValue(
			// roleDatePurviewDTO.getModifyServiceFee(), r
			// .getModifyServiceFee()));
			// // 取角色数据权限信息中最大的 确定结算单手续费修改
			// roleDatePurviewDTO.setModifyServiceFee2(getMaxValue(
			// roleDatePurviewDTO.getModifyServiceFee2(), r
			// .getModifyServiceFee2()));
			// //取角色数据权限信息中最大的 敏感信息修改
			// roleDatePurviewDTO.setSensitiveData(getMaxValue(
			// roleDatePurviewDTO.getSensitiveData(),
			// r.getSensitiveData()));
			// }
			// userDTO.setRoleDatePurviewDTO(roleDatePurviewDTO);

			return userDTO;
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查找用户信息失败~！");
		}

	}

	public PageDataDTO inquery(UserQueryDTO userQueryDTO)
			throws BizServiceException {
		try {
			if (userQueryDTO.getDefaultEntityId() != null
					&& !"".equals(userQueryDTO.getDefaultEntityId())) {
				userQueryDTO.setEntityId(userQueryDTO.getDefaultEntityId());
			}
			PageDataDTO pageDataDTO = pageQueryDAO.query("USER.selectUser",
					userQueryDTO);
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询用户信息失败~！");
		}
	}

	public UserDTO viewUser(UserDTO userDTO) throws BizServiceException {
		try {
			userDTO = getUserById(userDTO.getUserId());

			UserRoleExample userRoleExample = new UserRoleExample();
			userRoleExample.createCriteria().andUserIdEqualTo(
					userDTO.getUserId());
			List<UserRoleKey> userRoles = userRoleDAO
					.selectByExample(userRoleExample);
			List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
			for (UserRoleKey ur : userRoles) {
				Role role = roleDAO.selectByPrimaryKey(ur.getRoleId());
				RoleDTO roleDTO = new RoleDTO();
				ReflectionUtil.copyProperties(role, roleDTO);
				roleDTOs.add(roleDTO);
			}
			userDTO.setRoleDTOs(roleDTOs);
			return userDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查找用户信息失败~！");
		}
	}

	public UserDTO getUserById(String userId) throws BizServiceException {
		try {
			UserDTO userDTO = new UserDTO();
			User user = userServiceDAO.selectByPrimaryKey(userId);
			if (null != user) {
				ReflectionUtil.copyProperties(user, userDTO);
				return userDTO;
			} else {
				return null;
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查找用户信息失败~！");
		}

	}

	public UserDTO checkUserName(UserDTO userDTO) throws BizServiceException {
		try {
			UserDTO user = null;
			UserExample userExample = new UserExample();
			userExample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andUserNameEqualTo(
					userDTO.getUserName());
			List<User> users = userServiceDAO.selectByExample(userExample);
			if (null != users && users.size() > 0) {
				user = new UserDTO();
				ReflectionUtil.copyProperties(users.get(0), user);
			}

			return user;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("检查用户名失败！");
		}
	}

	public UserDTO insertUser(UserDTO userDTO) throws BizServiceException {
		UserDTO resultDto = new UserDTO();
		try {

			UserExample userExample = new UserExample();
			userExample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andUserNameEqualTo(
					userDTO.getUserName());
			List<User> users = userServiceDAO.selectByExample(userExample);
			if (null != users && users.size() > 0) {
				throw new BizServiceException(userDTO.getUserName()
						+ " 用户名称已存在!");
			}

			User user = new User();

			ReflectionUtil.copyProperties(userDTO, user);
			user.setUserId(commonsDAO.getNextValueOfSequence("TB_USER"));
			user.setEntityId(userDTO.getDefaultEntityId());
			user.setCreateTime(DateUtil.getCurrentTime());
			user.setCreateUser(userDTO.getLoginUserId());
			user.setModifyTime(DateUtil.getCurrentTime());
			user.setModifyUser(userDTO.getLoginUserId());
			user.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			userServiceDAO.insert(user);

			user = userServiceDAO.selectByPrimaryKey(user.getUserId());
			ReflectionUtil.copyProperties(user, resultDto);

		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加用户信息失败~！");
		}
		return resultDto;
	}

	public void insertUserForEntity(String entityId, String userId,
			String userName, String password, String email, String operateUser)
			throws BizServiceException {
		try {
			User user = new User();
			user.setEntityId(entityId);
			user.setUserId(userId);
			user.setUserName(userName);
			user.setUserPassword(password);
			user.setEmail(email);
			user.setUserState("1");
			user.setLockedState("0");
			user.setCreateUser(operateUser);
			user.setModifyUser(operateUser);
			user.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			user.setCreateTime(DateUtil.getCurrentDateStr());
			user.setModifyTime(DateUtil.getCurrentDateStr());
			// 是否销售标识，默认为是：1
			user.setIsSaleFlage("1");
			userServiceDAO.insert(user);
			// 给这个用户一个默认的角色
			List<UserRoleKey> userRole = new ArrayList<UserRoleKey>();
			UserRoleKey key = new UserRoleKey();
			key.setUserId(userId);
			// 默认的是角色9
			key.setRoleId("9");
			userRole.add(key);
			commonsDAO.batchInsert("TB_REL_USER_ROLE.abatorgenerated_insert",
					userRole);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加默认用户失败~！");
		}

	}

	public void updateUser(UserDTO userDTO) throws BizServiceException {
		try {
			if (userDTO.getUserId().equals(userDTO.getLoginUserId())) {
				if (null != userDTO.getUserState()
						&& userDTO.getUserState().equals("0")) {
					throw new BizServiceException("提交失败,用户无法将自身帐户设为无效");
				}
			}
			if (userDTO.getUserId().equals(userDTO.getLoginUserId())) {
				if (null != userDTO.getLockedState()
						&& userDTO.getLockedState().equals("6")) {
					throw new BizServiceException("提交失败,用户无法将自身帐户锁定");
				}
			}
			User user = new User();
			ReflectionUtil.copyProperties(userDTO, user);
			user.setModifyTime(DateUtil.getCurrentTime());
			user.setModifyUser(userDTO.getLoginUserId());
			userServiceDAO.updateByPrimaryKeySelective(user);
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新用户信息失败！");
		}
	}

	public void deleteUser(UserDTO userDTO) throws BizServiceException {
		try {
			List<String> userIds = userDTO.getUserIds();
			for (String userId : userIds) {
				// 判断其传过来的ID是否为当前管理员ID
				if (userId.equals(userDTO.getLoginUserId())) {
					throw new BizServiceException("注销失败,自己不能注销自己!");
				}

				User u = userServiceDAO.selectByPrimaryKey(userId);
				if ("admin".equals(u.getUserName())) {
					throw new BizServiceException("管理员admin不能注销");
				}

				// 注销用户
				User user = new User();
				user.setUserId(userId);
				// 删除用户时，改用户的datastate和userstate都为0；
				user.setUserState(DataBaseConstant.DATA_STATE_DELETE);
				user.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				userServiceDAO.updateByPrimaryKeySelective(user);

				// 删除用户对应的角色
				UserRoleExample example = new UserRoleExample();
				example.createCriteria().andUserIdEqualTo(userId);
				userRoleDAO.deleteByExample(example);
			}

		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除用户信息失败~！");
		}
	}

	/** 用户自己修改密码 */
	public void modifyPassword(UserDTO userDTO) throws BizServiceException {
		try {
			User user = userServiceDAO.selectByPrimaryKey(userDTO.getUserId());
			if (user != null) {
				if (!(user.getUserPassword().equals(userDTO
						.getOldUserPassword()))) {
					throw new BizServiceException("原来密码不正确");
				} else {
					if (user.getPasswordRecords() != null
							&& !user.getPasswordRecords().equals("")) {
						StringTokenizer str = new StringTokenizer(user
								.getPasswordRecords(), ",");
						int count = 0;
						while (str.hasMoreTokens()) {
							if (str.nextToken().equals(
									userDTO.getUserPassword())) {
								throw new BizServiceException("新密码不能为前四次使用过的密码");
							}
							count++;
						}
						StringBuffer stringBuf = new StringBuffer("");
						str = new StringTokenizer(user.getPasswordRecords(),
								",");
						int temp = 1;
						if (count == 4) {
							while (str.hasMoreTokens()) {
								if (temp != 1) {
									stringBuf.append(str.nextToken() + ",");
								} else {
									str.nextToken();
									temp++;
								}
								temp++;
							}
						} else {
							while (str.hasMoreTokens()) {
								stringBuf.append(str.nextToken() + ",");
							}
						}

						stringBuf.append(userDTO.getUserPassword() + ",");
						user.setPasswordRecords(stringBuf.toString());
						user.setUserPassword(userDTO.getUserPassword());
						userServiceDAO.updateByPrimaryKeySelective(user);
					} else {
						user
								.setPasswordRecords(userDTO.getUserPassword()
										+ ",");
						user.setUserPassword(userDTO.getUserPassword());
						userServiceDAO.updateByPrimaryKeySelective(user);
					}
				}
			}

		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("修改密码失败~！");
		}
	}

	public void addRole(UserDTO userDTO) throws BizServiceException {
		try {
			List<UserRoleKey> userRoles = new ArrayList<UserRoleKey>();
			for (RoleDTO role : userDTO.getRoleDTOs()) {
				UserRoleKey userRole = new UserRoleKey();
				userRole.setUserId(userDTO.getUserId());
				userRole.setRoleId(role.getRoleId());
				userRoles.add(userRole);
			}
			commonsDAO.batchInsert("TB_REL_USER_ROLE.abatorgenerated_insert",
					userRoles);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加角色信息失败~！");
		}
	}

	public void deleteRole(UserDTO userDTO) throws BizServiceException {
		try {
			List<RoleDTO> roleDTOs = userDTO.getRoleDTOs();
			List<UserRoleKey> userRoles = new ArrayList<UserRoleKey>();
			for (RoleDTO roleDTO : roleDTOs) {
				UserRoleKey userRole = new UserRoleKey();
				userRole.setUserId(userDTO.getUserId());
				userRole.setRoleId(roleDTO.getRoleId());
				userRoles.add(userRole);
			}
			commonsDAO.batchDelete(
					"TB_REL_USER_ROLE.abatorgenerated_deleteByPrimaryKey",
					userRoles);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除角色信息失败~！");
		}
	}

	/**
	 * 取两个值中最大的一个
	 * 
	 * @param now
	 * @param compare
	 * @return
	 */
	public Integer getMaxValue(Integer now, Integer compare) {

		if (compare == null)
			return now;
		if (now == null)
			return compare;

		if (now.compareTo(compare) < 0) {
			return compare;
		}
		return now;
	}

	public UserServiceDAO getUserServiceDAO() {
		return userServiceDAO;
	}

	public void setUserServiceDAO(UserServiceDAO userServiceDAO) {
		this.userServiceDAO = userServiceDAO;
	}

	public RoleServiceDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleServiceDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public UserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

	public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public IssuerDAO getIssuerDAO() {
		return issuerDAO;
	}

	public void setIssuerDAO(IssuerDAO issuerDAO) {
		this.issuerDAO = issuerDAO;
	}

	public SellerDAO getSellerDAO() {
		return sellerDAO;
	}

	public void setSellerDAO(SellerDAO sellerDAO) {
		this.sellerDAO = sellerDAO;
	}

	public ConsumerDAO getConsumerDAO() {
		return consumerDAO;
	}

	public void setConsumerDAO(ConsumerDAO consumerDAO) {
		this.consumerDAO = consumerDAO;
	}

	public TenantDAO getTennatDAO() {
		return tennatDAO;
	}

	public void setTennatDAO(TenantDAO tennatDAO) {
		this.tennatDAO = tennatDAO;
	}

	public IssueResourceDAO getIssueResourceDAO() {
		return issueResourceDAO;
	}

	public void setIssueResourceDAO(IssueResourceDAO issueResourceDAO) {
		this.issueResourceDAO = issueResourceDAO;
	}

}
