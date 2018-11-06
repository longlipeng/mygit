package com.huateng.univer.system.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.role.dto.RoleDTO;
import com.allinfinance.univer.system.role.dto.RoleQueryDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.allinfinance.univer.system.user.dto.UserQueryDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.MD5EncryptAlgorithm;
import com.huateng.framework.util.SystemInfo;

public class UserAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(UserAction.class);
	/** 查询分页数据DTO */
	private PageDataDTO pageDataDTO = new PageDataDTO();
	/** 用户信息DTO */
	private UserDTO userDTO = new UserDTO();
	/** 用户信息查询条件DTO */
	private UserQueryDTO userQueryDTO = new UserQueryDTO();
	/** 角色信息ID */
	private List<String> rolebox = new ArrayList<String>();
	/** 用户信息ID */
	private List<String> choose = new ArrayList<String>();
	/** 密码参数 */
	private String password;
	/** 确认密码参数 */
	private String repassword;
	/** 授权密码参数 */
	private String authPassword;
	/** 确认授权密码参数 */
	private String authRepassword;
	private RoleQueryDTO roleQueryDTO = new RoleQueryDTO();

	/** 用户信息查询方法 */
	public String list() throws Exception {

		ListPageInit(null, userQueryDTO);
		if (userQueryDTO.isQueryAll()) {
			userQueryDTO.setQueryAll(false);
			userQueryDTO
					.setRowsDisplayed(Integer
							.parseInt(SystemInfo
									.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
		}
		pageDataDTO = (PageDataDTO) sendService(ConstCode.USER_SERVICE_INQUERY,
				userQueryDTO).getDetailvo();
		return "list";
	}

	/**
	 * ajax查看用户名是否重复
	 */
	public void checkUserName() {

		try {
			userDTO = (UserDTO) this.sendService(
					ConstCode.USER_NAME_REPEAT_CHECK, userDTO).getDetailvo();
			getResponse().setContentType("application/json; charset=utf-8");
			getResponse().setCharacterEncoding("utf-8");
			getResponse().getWriter().println(
					JSONObject.fromObject(userDTO).toString());
			getResponse().getWriter().close();
//			if (null == userDTO) {
//				this.addFieldError("sellerDTO.userName", "用户名 "+userDTO.getUserName()+" 已存在！");
//			}
		} catch (IOException e) {
			this.logger.error(e.getMessage());
		}
	}

	/** 跳转添加页面 */
	public String add() throws Exception {
		return "add";
	}

	/** 添加用户信息 */

	public void validateInsert() throws Exception {
		if (userDTO.getUserName().length() > userDTO.getUserName().trim()
				.length()) {
			this.addFieldError("userDTO.userName", "用户前后不能有空格");
		}
		checkPassword();
	}

	public String insert() throws Exception {
		userDTO.setUserPassword(MD5EncryptAlgorithm.md5(password));
		userDTO.setPasswordRecords(MD5EncryptAlgorithm.md5(password) + ",");
		userDTO = (UserDTO) sendService(ConstCode.USER_SERVICE_INSERT, userDTO)
				.getDetailvo();
		if (hasActionErrors()) {
			return INPUT;
		}
		addActionMessage("用户信息添加成功！");
		return "edit";
	}

	/**
	 * 加载编辑页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String load() throws Exception {
		if (userDTO.getUserId() == null) {
			String id = getRequest().getParameter("id");
			userDTO.setUserId(id);
			getRequest().setAttribute("login", getUser().getUserId());
		}
		userDTO = (UserDTO) sendService(ConstCode.USER_SERVICE_VIEW, userDTO)
				.getDetailvo();
		return "edit";
	}

	/**
	 * 加载显示页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String view() throws Exception {
		if (userDTO.getUserId() == null) {
			String id = getRequest().getParameter("id");
			userDTO.setUserId(id);
		}
		userDTO = (UserDTO) sendService(ConstCode.USER_SERVICE_VIEW, userDTO)
				.getDetailvo();
		return "view";
	}

	/**
	 * 更新用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		userDTO.setLoginUserId(getUser().getUserId());
		sendService(ConstCode.USER_SERVICE_UPDATEUSER, userDTO);
		if (hasErrors()) {
			return load();
		}
		addActionMessage("用户信息修改成功！");
		return list();
	}

	public void validateUpdate() throws Exception {

	}

	/** 删除用户信息 */
	public String delete() throws Exception {
		List<String> userIds = new ArrayList<String>();
		for (String id : choose) {
			userIds.add(id);
		}
		userDTO = new UserDTO();
		userDTO.setUserIds(userIds);
		String userName = (String) getSession().get(
				"SPRING_SECURITY_LAST_USERNAME");
		if (userName == null) {
			getResponse().sendRedirect("login.jsp");
			return null;

		}
		userDTO.setUserName(userName);
		sendService(ConstCode.USER_SERVICE_DELETEUSER, userDTO);

		if (!hasActionErrors()) {
			addActionMessage("用户信息注销成功！");
		}
		return list();
	}

	/**
	 * 添加角色信息
	 */
	public String addRole() throws Exception {
		// sendService(ConstCode.USER_SERVICE_UPDATEUSER, userDTO);

		String roleId = getRequest().getParameter("id");
		String[] roleIds = roleId.split(",");
		List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
		for (String id : roleIds) {
			if (id != null && !"".equals(id)) {
				RoleDTO roleDTO = new RoleDTO();
				roleDTO.setRoleId(id);
				roleDTOs.add(roleDTO);
			}
		}
		userDTO.setCreateUser(getUser().getUserId());
		userDTO.setRoleDTOs(roleDTOs);
		sendService(ConstCode.USER_SERVICE_ADDROLE, userDTO);
		if (!hasActionErrors()) {
			addActionMessage("角色信息添加成功！");
		}
		return load();
	}

	/**
	 * 删除角色信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delRole() throws Exception {
		/*
		 * userDTO.setModifyUser(getUser().getUserId());
		 * sendService(ConstCode.USER_SERVICE_UPDATEUSER, userDTO);
		 */

		List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
		for (String id : rolebox) {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setRoleId(id);
			roleDTOs.add(roleDTO);
		}
		userDTO.setRoleDTOs(roleDTOs);
		sendService(ConstCode.USER_SERVICE_DELETEROLE, userDTO);
		if (!hasActionErrors()) {
			addActionMessage("角色信息删除成功！");
		}
		return load();
	}

	/**
	 * 管理员修改密码时加载用户信息(用户编号，名称)
	 * 
	 * @return
	 * @throws
	 */
	public String loadUser() throws Exception {
		load();
		if (hasErrors()) {
			return "edit";
		}
		return "edit";
	}
	/**
	 * 用户修改授权密码时加载用户信息(用户编号，名称)
	 * 
	 * @return
	 * @throws
	 */
	public String loadAuthUser() throws Exception {
		load();
		if (hasErrors()) {
			return "edit";
		}
		return "edit";
	}

	/**
	 * 用户自己修改密码
	 */
	public String selfLoad() throws Exception {
		userDTO.setUserId(getUser().getUserId());
		return "edit";
	}

	/**
	 * 用户自己修改密码
	 */
	public void validateModifyPasswordBySelf() {
		if ("".equals(userDTO.getOldUserPassword())) {
			this.addFieldError("userDTO.oldUserPassword", "原密码不能空");
		}
		checkPassword();
	}

	public String modifyPasswordBySelf() throws Exception {
		userDTO.setOldUserPassword(MD5EncryptAlgorithm.md5(userDTO
				.getOldUserPassword()));
		userDTO.setUserPassword(MD5EncryptAlgorithm.md5(password));
		sendService(ConstCode.USER_SERVICE_MODIFYPASSWORD, userDTO);
		if (hasErrors()) {
			return selfLoad();
		} else {
			addActionMessage("密码修改成功!");
		}
		return "success";
	}

	/**
	 * 系统管理员修改用户密码
	 * 
	 * @return
	 * @throws
	 */
	public String modifyPassword() throws Exception {
		userDTO.setModifyUser(getUser().getUserId());
		userDTO.setUserPassword(MD5EncryptAlgorithm.md5(password));
		sendService(ConstCode.USER_SERVICE_UPDATEUSER, userDTO);
		if (hasActionErrors()) {
			return INPUT;
		}
		addActionMessage("用户密码修改成功!");
		return list();

	}
	/**
	 * 修改用户授权密码
	 * 
	 * @return
	 * @throws
	 */
	public String modifyAuthPassword() throws Exception {
		userDTO.setModifyUser(getUser().getUserId());
		userDTO.setUserAuthPassword(MD5EncryptAlgorithm.md5(authPassword));
		sendService(ConstCode.USER_SERVICE_UPDATEUSER, userDTO);
		if (hasActionErrors()) {
			return INPUT;
		}
		addActionMessage("用户授权密码修改成功!");
		return list();

	}

	public void validateModifyPassword() {
		checkPassword();
	}
	public void validateModifyAuthPassword() {
		checkAuthPassword();
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public UserQueryDTO getUserQueryDTO() {
		return userQueryDTO;
	}

	public void setUserQueryDTO(UserQueryDTO userQueryDTO) {
		this.userQueryDTO = userQueryDTO;
	}

	public int getTotalRows() {
		return pageDataDTO.getTotalRecord();
	}

	public List<String> getRolebox() {
		return rolebox;
	}

	public void setRolebox(List<String> rolebox) {
		this.rolebox = rolebox;
	}

	public List<String> getChoose() {
		return choose;
	}

	public void setChoose(List<String> choose) {
		this.choose = choose;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getAuthPassword() {
		return authPassword;
	}

	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}

	public String getAuthRepassword() {
		return authRepassword;
	}

	public void setAuthRepassword(String authRepassword) {
		this.authRepassword = authRepassword;
	}

	public RoleQueryDTO getRoleQueryDTO() {
		return roleQueryDTO;
	}

	public void setRoleQueryDTO(RoleQueryDTO roleQueryDTO) {
		this.roleQueryDTO = roleQueryDTO;
	}

	public void checkPassword() {
		if ("".equals(password)) {
			this.addFieldError("password", "必须由大写字母、小写字母和数字组成(7-20)");
			return;
		}
		if ("".equals(repassword)) {
			this.addFieldError("repassword", "确认密码不能为空");
			return;
		}
		if (password != null) {
			/*if (!password.matches("[^\\s]{7,20}")) {
				this.addFieldError("password", "必须由字母和数字组成开头为字母不包含空格(7-20)");
				return;
			}*/
			if (!password.matches("(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{7,20}")) {
				this.addFieldError("password", "必须由大写字母、小写字母和数字组成不包含空格(7-20)");
				return;
			}
		}
		if (password != null && repassword != null) {
			if (!password.matches("(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{7,20}")) {
				this.addFieldError("password", "必须由大写字母、小写字母和数字组成不包含空格(7-20)");
				return;
			}
			if (!password.equals(repassword)) {
				this.addFieldError("repassword", "两次密码不相同");
				return;
			}
		}
	}
	public void checkAuthPassword() {
		if ("".equals(authPassword)) {
			this.addFieldError("authPassword", "必须由字母和数字组成开头为字母(7-20)");
			return;
		}
		if ("".equals(authRepassword)) {
			this.addFieldError("authRepassword", "确认密码不能为空");
			return;
		}
		if (authPassword != null) {
			if (!authPassword.matches("[^\\s]{7,20}")) {
				this.addFieldError("authPassword", "必须由字母和数字组成开头为字母不包含空格(7-20)");
				return;
			}
		}
		if (authPassword != null && authRepassword != null) {
			if (!authPassword.matches("^[a-zA-Z](?=.*?\\d)[a-zA-Z0-9]{6,19}")) {
				this.addFieldError("authPassword", "必须由字母和数字组成开头为字母(7-20)");
				return;
			}
			if (!authPassword.equals(authRepassword)) {
				this.addFieldError("authRepassword", "两次密码不相同");
				return;
			}
		}
	}
	
	/** 跳转验证授权密码页面 */
	public String inputAuthPassword() throws Exception {
		userDTO.setUserId(getUser().getUserId());
		userDTO.setUserName(getUser().getUserName());
		return "input";
	}

	/** 检查授权密码 */

	public String inspectAuthPassword() throws Exception {
		if (userDTO.getUserId() == null) {
			String id = getRequest().getParameter("id");
			userDTO.setUserId(id);
		}
		userDTO = (UserDTO) sendService(ConstCode.USER_SERVICE_VIEW, userDTO)
				.getDetailvo();
		if(null!=userDTO && null!=userDTO.getUserAuthPassword() && userDTO.getUserAuthPassword().equals(MD5EncryptAlgorithm.md5(authPassword)))
		{
			return "edit";
		}
		    addActionMessage("授权密码不正确，请重新输入或选择其他支付方式。");
		    return "input";
	}

}
