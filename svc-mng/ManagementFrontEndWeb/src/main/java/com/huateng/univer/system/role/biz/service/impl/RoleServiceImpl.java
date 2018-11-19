package com.huateng.univer.system.role.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.role.dto.RoleDTO;
import com.allinfinance.univer.system.role.dto.RoleQueryDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.ConsumerDAO;
import com.huateng.framework.ibatis.dao.IssueResourceDAO;
import com.huateng.framework.ibatis.dao.IssuerDAO;
import com.huateng.framework.ibatis.dao.ResourceDAO;
import com.huateng.framework.ibatis.dao.RoleResourceDAO;
import com.huateng.framework.ibatis.dao.SellerDAO;
import com.huateng.framework.ibatis.dao.TenantDAO;
import com.huateng.framework.ibatis.dao.UserRoleDAO;
import com.huateng.framework.ibatis.model.Consumer;
import com.huateng.framework.ibatis.model.ConsumerExample;
import com.huateng.framework.ibatis.model.IssueResourceExample;
import com.huateng.framework.ibatis.model.Issuer;
import com.huateng.framework.ibatis.model.IssuerExample;
import com.huateng.framework.ibatis.model.Resource;
import com.huateng.framework.ibatis.model.Role;
import com.huateng.framework.ibatis.model.RoleExample;
import com.huateng.framework.ibatis.model.RoleResourceExample;
import com.huateng.framework.ibatis.model.RoleResourceKey;
import com.huateng.framework.ibatis.model.Seller;
import com.huateng.framework.ibatis.model.SellerExample;
import com.huateng.framework.ibatis.model.Tenant;
import com.huateng.framework.ibatis.model.TenantExample;
import com.huateng.framework.ibatis.model.UserRoleExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.StringUtil;
import com.huateng.univer.system.role.biz.service.RoleService;
import com.huateng.univer.system.role.integration.dao.RoleServiceDAO;
import com.huateng.univer.system.user.integration.dao.UserServiceDAO;

public class RoleServiceImpl implements RoleService {
	Logger logger = Logger.getLogger(RoleServiceImpl.class);
	/**
	 * 角色信息操作DAO
	 */
	private RoleServiceDAO roleDAO;
	/**
	 * 公共操作DAO
	 */
	private CommonsDAO commonsDAO;
	/**
	 * 页面查询DAO
	 */
	private PageQueryDAO pageQueryDAO;
	/**
	 * 角色菜单DAO
	 */
	private RoleResourceDAO roleResourceDAO;

	private UserRoleDAO userRoleDAO;

	private ResourceDAO resourceDAO;

	private UserServiceDAO userServiceDAO;
	private IssuerDAO issuerDAO;
	private SellerDAO sellerDAO;
	private ConsumerDAO consumerDAO;
	private TenantDAO tennatDAO;
	private IssueResourceDAO issueResourceDAO;

	public IssueResourceDAO getIssueResourceDAO() {
		return issueResourceDAO;
	}

	public void setIssueResourceDAO(IssueResourceDAO issueResourceDAO) {
		this.issueResourceDAO = issueResourceDAO;
	}

	public List<ResourceDTO> selectResource(UserDTO userDTO)
			throws BizServiceException {
		try {
			// add zfh 判断用户是哪种机构
			String entityId = userDTO.getEntityId();
			List<String> functionRoleId = new ArrayList<String>();
			List<String> isSale = new ArrayList<String>();
			functionRoleId.add("0");
			TenantExample example0 = new TenantExample();
			isSale.add("0");
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
			userDTO.setIsSale(isSale.toArray());
			
			IssueResourceExample example = new IssueResourceExample();
			example.createCriteria().andIssueIdEqualTo(userDTO.getEntityId())
					.andRankIn(isSale);
			List<ResourceDTO> resources = new ArrayList<ResourceDTO>();
			if (issueResourceDAO.selectByExample(example).size() > 0) {
				resources = roleDAO.getIssueResourceRole(userDTO);
			} else {
				resources = roleDAO.getResourceDTOs();
			}

			List<ResourceDTO> resourceList = new ArrayList<ResourceDTO>();
			if (resources != null && resources.size() > 0) {
				for (ResourceDTO rdto : resources) {
					if (functionRoleId.contains(rdto.getFunctionRoleId())) {
						resourceList.add(rdto);
					}
				}
			}

			return resourceList;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询菜单信息失败~！");
		}

	}

	public List<ResourceDTO> getAllResource() throws BizServiceException {
		try {
			List<ResourceDTO> resources = roleDAO.getAllResourceDTO();
			return resources;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询菜单信息失败~！");
		}

	}

	public PageDataDTO inqueryRole(RoleQueryDTO roleQueryDTO)
			throws BizServiceException {
		try {
			if (roleQueryDTO.getDefaultEntityId() != null
					&& !"".equals(roleQueryDTO.getDefaultEntityId())) {
				roleQueryDTO.setEntityId(roleQueryDTO.getDefaultEntityId());
			}
			ConsumerExample example3 = new ConsumerExample();
			example3.createCriteria().andEntityIdEqualTo(
					roleQueryDTO.getDefaultEntityId()).andDataStateEqualTo("1");
			List<Consumer> consumerList = consumerDAO.selectByExample(example3);
			PageDataDTO pageDataDTO;
			if (consumerList != null && consumerList.size() > 0) {
				pageDataDTO = pageQueryDAO.query("ROLE.selectRoleConsumer",
						roleQueryDTO);
			} else {
				pageDataDTO = pageQueryDAO.query("ROLE.selectRole",
						roleQueryDTO);
			}
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询角色信息失败~！");
		}

	}

	public void insertRole(RoleDTO roleDTO) throws BizServiceException {
		try {
			RoleExample roleExample = new RoleExample();
			roleExample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andRoleNameEqualTo(
					roleDTO.getRoleName()).andEntityIdEqualTo(roleDTO.getDefaultEntityId());
			if (roleDAO.selectByExample(roleExample).size() > 0) {
				throw new BizServiceException(roleDTO.getRoleName()
						+ " 角色名已存在此营销机构下!");
			}

			String resourceIdStr = roleDTO.getResourceIds();
			List<RoleResourceKey> roleResources = new ArrayList<RoleResourceKey>();
			String id = commonsDAO
					.getNextValueOfSequence("TB_REL_ROLE_RESOURCE");
			if (StringUtil.isNotEmpty(resourceIdStr)) {
				String[] resourceIds = resourceIdStr.split(",");
				for (String resourceId : resourceIds) {
					if (StringUtil.isNotEmpty(resourceId)) {
						RoleResourceKey roleResource = new RoleResourceKey();
						roleResource.setRoleId(id);
						roleResource.setResourceId(resourceId);
						roleResources.add(roleResource);
					}
				}
				commonsDAO.batchInsert(
						"TB_REL_ROLE_RESOURCE.abatorgenerated_insert",
						roleResources);
			}

			Role role = new Role();
			ReflectionUtil.copyProperties(roleDTO, role);

			role.setRoleId(id);
			role.setEntityId(roleDTO.getDefaultEntityId());
			role.setCreateTime(DateUtil.getCurrentTime());
			role.setCreateUser(roleDTO.getLoginUserId());
			role.setModifyTime(DateUtil.getCurrentTime());
			role.setModifyUser(roleDTO.getLoginUserId());
			role.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			roleDAO.insert(role);

		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加角色信息失败~！");
		}
	}

	public void updateRole(RoleDTO roleDTO) throws BizServiceException {
		try {
			RoleResourceExample rre = new RoleResourceExample();
			rre.createCriteria().andRoleIdEqualTo(roleDTO.getRoleId());
			roleResourceDAO.deleteByExample(rre);

			String resourceIdStr = roleDTO.getResourceIds();
			List<RoleResourceKey> roleResources = new ArrayList<RoleResourceKey>();
			if (StringUtil.isNotEmpty(resourceIdStr)) {
				String[] resourceIds = resourceIdStr.split(",");
				for (String resourceId : resourceIds) {
					if (StringUtil.isNotEmpty(resourceId)) {
						RoleResourceKey roleResource = new RoleResourceKey();
						roleResource.setRoleId(roleDTO.getRoleId());
						roleResource.setResourceId(resourceId);
						roleResources.add(roleResource);
					}
				}
				commonsDAO.batchInsert(
						"TB_REL_ROLE_RESOURCE.abatorgenerated_insert",
						roleResources);
			}

			// List<RoleResourceKey> roleResources = new
			// ArrayList<RoleResourceKey>();
			// List<ResourceDTO> resourceDtos = roleDTO.getResourceDTOs();
			// if (resourceDtos != null) {
			// for (ResourceDTO rsd : resourceDtos) {
			// RoleResourceKey roleResource = new RoleResourceKey();
			// roleResource.setRoleId(roleDTO.getRoleId());
			// roleResource.setResourceId(rsd.getResourceId());
			//					
			// roleResources.add(roleResource);
			// }
			// commonsDAO.batchInsert(
			// "TB_REL_ROLE_RESOURCE.abatorgenerated_insert",
			// roleResources);
			// }

			Role role = new Role();
			ReflectionUtil.copyProperties(roleDTO, role);
			// role.setEntityId(roleDTO.getDefaultEntityId());
			role.setCreateTime(DateUtil.getCurrentTime());
			role.setCreateUser(roleDTO.getLoginUserId());
			role.setModifyTime(DateUtil.getCurrentTime());
			role.setModifyUser(role.getModifyUser());
			roleDAO.updateByPrimaryKeySelective(role);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("修改角色信息失败~！");
		}
	}

	public RoleDTO viewRole(RoleDTO roleDTO) throws BizServiceException {
		try {
			Role role = roleDAO.selectByPrimaryKey(roleDTO.getRoleId());
			ReflectionUtil.copyProperties(role, roleDTO);

			RoleResourceExample rre = new RoleResourceExample();
			rre.createCriteria().andRoleIdEqualTo(roleDTO.getRoleId());
			List<ResourceDTO> resourceDTOs = new ArrayList<ResourceDTO>();
			List<RoleResourceKey> roleResources = roleResourceDAO
					.selectByExample(rre);
			for (RoleResourceKey roleResourceKey : roleResources) {

				Resource resource = resourceDAO
						.selectByPrimaryKey(roleResourceKey.getResourceId());
				ResourceDTO resourceDTO = new ResourceDTO();
				if (resource == null)
					continue;
				ReflectionUtil.copyProperties(resource, resourceDTO);
				resourceDTOs.add(resourceDTO);
			}
			roleDTO.setResourceDTOs(resourceDTOs);

			return roleDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("调用角色信息失败~！");
		}

	}

	public void deleteRole(RoleDTO roleDTO) throws BizServiceException {
		try {
			for (String id : roleDTO.getRoleIds()) {
				UserRoleExample ure = new UserRoleExample();
				ure.createCriteria().andRoleIdEqualTo(id);
				int count = userRoleDAO.countByExample(ure);
				if (count > 0) {
					throw new BizServiceException("删除的角色存在用户，无法删除！");
				}
				Role role = new Role();
				role.setRoleId(id);
				role.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				roleDAO.updateByPrimaryKeySelective(role);
				// 删除角色与资源的关联
				RoleResourceExample rre = new RoleResourceExample();
				rre.createCriteria().andRoleIdEqualTo(id);
				roleResourceDAO.deleteByExample(rre);
			}
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除角色信息失败~！");
		}

	}

	public List<ResourceDTO> selectIssueResource(UserDTO dto)
			throws BizServiceException {

		IssueResourceExample example = new IssueResourceExample();
		example.createCriteria().andIssueIdEqualTo(dto.getEntityId())
				.andRankEqualTo(dto.getIsSaleFlage());
		List<ResourceDTO> resources = new ArrayList<ResourceDTO>();
		if (issueResourceDAO.selectByExample(example).size() > 0) {
			resources = roleDAO.getIssueResourceRole(dto);
		} else {
			resources = roleDAO.getResourceDTOs();
		}
		//客户要求需要二级发行机构
		//remove(resources,dto);
		List<ResourceDTO> resourceList = new ArrayList<ResourceDTO>();
		if (resources != null && resources.size() > 0) {			
			for (ResourceDTO rdto : resources) {
				if (dto.getFunctionRoleId().contains(rdto.getFunctionRoleId())) {
					resourceList.add(rdto);
				}
			}
		}
		return resourceList;

	}
	
	//删除发行机构管理下级发行机构
	private void remove(List<ResourceDTO> resources,UserDTO dto){
		if("00000000".equals(dto.getEntityId())){
			List<String> list = new ArrayList<String>();
			List<ResourceDTO> resourceList = new ArrayList<ResourceDTO>();
			list.add("30500");
			list.add("30501");
			list.add("30502");
			list.add("30503");
			list.add("30504");
			list.add("30601");
			list.add("30602");
			list.add("30603");
			for(ResourceDTO rdto : resources){
				for(int i=0;i<list.size();i++){
					if(rdto.getResourceId().contains(list.get(i))){
						resourceList.add(rdto);
						break;
					}
				}
			}
			resources.removeAll(resourceList);
		}
	}

	public RoleServiceDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleServiceDAO roleDAO) {
		this.roleDAO = roleDAO;
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

	public RoleResourceDAO getRoleResourceDAO() {
		return roleResourceDAO;
	}

	public void setRoleResourceDAO(RoleResourceDAO roleResourceDAO) {
		this.roleResourceDAO = roleResourceDAO;
	}

	public ResourceDAO getResourceDAO() {
		return resourceDAO;
	}

	public void setResourceDAO(ResourceDAO resourceDAO) {
		this.resourceDAO = resourceDAO;
	}

	public UserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

	public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	public UserServiceDAO getUserServiceDAO() {
		return userServiceDAO;
	}

	public void setUserServiceDAO(UserServiceDAO userServiceDAO) {
		this.userServiceDAO = userServiceDAO;
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

}
