package com.huateng.univer.system.role;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.framework.dto.RoleDatePurviewDTO;
import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.role.dto.RoleDTO;
import com.allinfinance.univer.system.role.dto.RoleQueryDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;

public class RoleAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	/**
	 * 菜单列表
	 */
	private String menuList;
	/**
	 * 新的菜单列表
	 */
	private String nmenuList;
	/**
	 * 查询分页数据DTO
	 */
	private PageDataDTO pageDataDTO = new PageDataDTO();
	/**
	 * 角色信息DTO
	 */
	private RoleDTO roleDTO = new RoleDTO();
	/**
	 * 角色数据DTO
	 */
	private RoleDatePurviewDTO roleDatePurviewDTO = new RoleDatePurviewDTO();
	/**
	 * 角色查询条件DTO
	 */
	private RoleQueryDTO roleQueryDTO = new RoleQueryDTO();
	/**
	 * 原ID集
	 */
	private String resourceIds;
	/**
	 * 角色ID集
	 */
	private List<String> choose = new ArrayList<String>();

	/**
	 * 查询菜单信息
	 */
	@SuppressWarnings("unchecked")
	public String selectResource() throws Exception {
		UserDTO userDTO=new UserDTO();
		userDTO.setEntityId(getUser().getEntityId());
		List<ResourceDTO> resourceDTOs = (List<ResourceDTO>) sendService(
				ConstCode.ROLE_SERVICE_SELECTRESOURCE,userDTO).getDetailvo();
		JSONArray jsonObject = JSONArray.fromObject(resourceDTOs);
		menuList = jsonObject.toString();
		/*issuerGroupDTOs = (List<IssuerGroupDTO>) sendService(
				ConstCode.ISSUERGROUP_SERVICE_SELECT, null).getDetailvo();*/
		
		return "add";

	}

	/**
	 * 角色信息初始化
	 */
	public String init() throws Exception {
		roleQueryDTO = new RoleQueryDTO();
		return inquery();
	}

	/**
	 * 查询角色信息
	 */
	public String inquery() throws Exception {
		ListPageInit(null, roleQueryDTO);
		if(roleQueryDTO.isQueryAll()){
			roleQueryDTO.setQueryAll(false);
			roleQueryDTO.setRowsDisplayed(Integer.parseInt(SystemInfo.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
		}
		/*issuerGroupDTOs = (List<IssuerGroupDTO>) sendService(
				ConstCode.ISSUERGROUP_SERVICE_SELECT, null).getDetailvo();*/
		pageDataDTO = (PageDataDTO) sendService(ConstCode.ROLE_SERVICE_INQUERY,
				roleQueryDTO).getDetailvo();

		return "list";
	}

	/**
	 * 添加角色信息
	 */
	public String insertRole() throws Exception {
		if (!",".equals(resourceIds)) {
			roleDTO.setResourceIds(resourceIds);
		}

		//roleDTO.setRoleDatePurviewDTO(roleDatePurviewDTO);
		sendService(ConstCode.ROLE_SERVICE_INSERT, roleDTO);
		if (hasActionErrors()) {
			return INPUT;
		}
		addActionMessage("添加角色信息成功！");
		return inquery();
	}

	@SuppressWarnings("unchecked")
	public void validateInsertRole() throws Exception {
		UserDTO userDTO=new UserDTO();
		userDTO.setEntityId(getUser().getEntityId());
		List<ResourceDTO> resourceDTOs = (List<ResourceDTO>) sendService(
				ConstCode.ROLE_SERVICE_SELECTRESOURCE, userDTO).getDetailvo();
		JSONArray jsonObject = JSONArray.fromObject(resourceDTOs);
		menuList = jsonObject.toString();
	}

	/**
	 * 加载角色信息编辑页面
	 */
	@SuppressWarnings("unchecked")
	public String load() throws Exception {
		/*issuerGroupDTOs = (List<IssuerGroupDTO>) sendService(
				ConstCode.ISSUERGROUP_SERVICE_SELECT, null).getDetailvo();*/
		String id = "";
		if (getRequest().getParameter("id") != null) {
			id = getRequest().getParameter("id");
		}
		
		if (roleDTO.getRoleId() == null) {
			roleDTO.setRoleId(id);
		}
		roleDTO = (RoleDTO) sendService(ConstCode.ROLE_SERVICE_VIEW, roleDTO)
				.getDetailvo();
		UserDTO userDTO=new UserDTO();
		userDTO.setEntityId(getUser().getEntityId());
		List<ResourceDTO> resourceDTOs = (List<ResourceDTO>) sendService(
				ConstCode.ROLE_SERVICE_SELECTRESOURCE, userDTO).getDetailvo();
		JSONArray jsonArray = JSONArray.fromObject(resourceDTOs);
		menuList = jsonArray.toString();
		jsonArray = JSONArray.fromObject(roleDTO.getResourceDTOs());
		nmenuList = jsonArray.toString();
		roleDatePurviewDTO = roleDTO.getRoleDatePurviewDTO();
		if (hasErrors()) {
			return this.inquery();
		}
		return "edit";
	}

	/**
	 * 更新角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateRole() throws Exception {
		if (!",".equals(resourceIds)) {
			roleDTO.setResourceIds(resourceIds);
		}
		roleDTO.setModifyUser(getUser().getUserId());
		roleDTO.setRoleDatePurviewDTO(roleDatePurviewDTO);
		sendService(ConstCode.ROLE_SERVICE_UPDATE, roleDTO);
		if (hasErrors()) {
			return load();
		} else {
			addActionMessage("角色信息修改成功！");
		}
		return inquery();
	}
	
	@SuppressWarnings("unchecked")
	public void validateUpdateRole() throws Exception {
		if(this.hasFieldErrors()){
		String id = "";
		if (getRequest().getParameter("id") != null) {
			id = getRequest().getParameter("id");
		}	
		if (roleDTO.getRoleId() == null) {
			roleDTO.setRoleId(id);
		}
			roleDTO = (RoleDTO) sendService(ConstCode.ROLE_SERVICE_VIEW, roleDTO)
				.getDetailvo();
			UserDTO userDTO=new UserDTO();
			userDTO.setEntityId(getUser().getEntityId());
			List<ResourceDTO> resourceDTOs = (List<ResourceDTO>) sendService(
				ConstCode.ROLE_SERVICE_SELECTRESOURCE, userDTO).getDetailvo();
			JSONArray jsonArray = JSONArray.fromObject(resourceDTOs);
			menuList = jsonArray.toString();
			jsonArray = JSONArray.fromObject(roleDTO.getResourceDTOs());
			nmenuList = jsonArray.toString();
			roleDatePurviewDTO = roleDTO.getRoleDatePurviewDTO();
		}
	}

	/**
	 * 调用角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String viewRole() throws Exception {
		/*issuerGroupDTOs = (List<IssuerGroupDTO>) sendService(
				ConstCode.ISSUERGROUP_SERVICE_SELECT, null).getDetailvo();*/
		String id = getRequest().getParameter("id");
		roleDTO = new RoleDTO();
		roleDTO.setRoleId(id);
		roleDTO = (RoleDTO) sendService(ConstCode.ROLE_SERVICE_VIEW, roleDTO)
				.getDetailvo();
		UserDTO userDTO=new UserDTO();
		userDTO.setEntityId(getUser().getEntityId());
		List<ResourceDTO> resourceDTOs = (List<ResourceDTO>) sendService(
				ConstCode.ROLE_SERVICE_SELECTRESOURCE, userDTO).getDetailvo();
		JSONArray jsonArray = JSONArray.fromObject(resourceDTOs);
		menuList = jsonArray.toString();
		jsonArray = JSONArray.fromObject(roleDTO.getResourceDTOs());
		nmenuList = jsonArray.toString();
		roleDatePurviewDTO = roleDTO.getRoleDatePurviewDTO();
		if (hasErrors()) {
			return "list";
		}
		return "view";
	}

	/**
	 * 删除角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteRole() throws Exception {
		List<String> roleIds = new ArrayList<String>();

		for (String id : choose) {
			String[] ids=id.split(",");
			roleIds.add(ids[0]);
		}
		roleDTO = new RoleDTO();
		roleDTO.setRoleIds(roleIds);
		sendService(ConstCode.ROLE_SERVICE_DELETE, roleDTO);
		if (hasErrors()) {
			return inquery();
		} else {
			addActionMessage("删除成功！");
		}
		return inquery();
	}

	/**
	 * 公用查询角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String inqueryRole() throws Exception {
		if (roleQueryDTO == null) {
			roleQueryDTO = new RoleQueryDTO();

		}
		String id = getRequest().getParameter("id");
		if (!"".equals(id) && id != null) {
			roleQueryDTO.setUserId(id);
		}
		ListPageInit(null, roleQueryDTO);
		/*issuerGroupDTOs = (List<IssuerGroupDTO>) sendService(
				ConstCode.ISSUERGROUP_SERVICE_SELECT, null).getDetailvo();*/
		pageDataDTO = (PageDataDTO) sendService(ConstCode.ROLE_SERVICE_INQUERY,
				roleQueryDTO).getDetailvo();
		return "list";
	}

	public String getMenuList() {
		return menuList;
	}

	public void setMenuList(String menuList) {
		this.menuList = menuList;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public RoleDTO getRoleDTO() {
		return roleDTO;
	}

	public void setRoleDTO(RoleDTO roleDTO) {
		this.roleDTO = roleDTO;
	}

	public RoleQueryDTO getRoleQueryDTO() {
		return roleQueryDTO;
	}

	public void setRoleQueryDTO(RoleQueryDTO roleQueryDTO) {
		this.roleQueryDTO = roleQueryDTO;
	}

	public int getTotalRows() {
		return pageDataDTO.getTotalRecord();
	}

	public RoleDatePurviewDTO getRoleDatePurviewDTO() {
		return roleDatePurviewDTO;
	}

	public void setRoleDatePurviewDTO(RoleDatePurviewDTO roleDatePurviewDTO) {
		this.roleDatePurviewDTO = roleDatePurviewDTO;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getNmenuList() {
		return nmenuList;
	}

	public void setNmenuList(String nmenuList) {
		this.nmenuList = nmenuList;
	}

	public List<String> getChoose() {
		return choose;
	}

	public void setChoose(List<String> choose) {
		this.choose = choose;
	}
}
