package com.allinfinance.univer.system.user.dto;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.framework.dto.RoleDatePurviewDTO;
import com.allinfinance.univer.consumer.dto.ConsumerDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;
import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.role.dto.RoleDTO;
import com.allinfinance.univer.tenant.dto.TenantDTO;

public class UserDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7798993125812645839L;
	/**
	 * 用户号
	 */
	private String userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 
	 */
	private String oldUserPassword;
	/**
	 * 用户密码
	 */
	private String userPassword;
	/**
	 * 用户组标记 1为发卡机构组用户 0为发卡机构用户
	 */
	private Short groupUserFlag;
	

	
	/**
	 * 发卡机构组名称
	 */
	private String issuerGroupName;

	/**
	 * 该用户允许访问的受保护资源
	 */
	private List<ResourceDTO> resourceDTOList;
	
	private String entityId;
	
	private String isSaleFlage;
	private Object[] isSale;
	
	private String createUser;
	
	private String modifyUser;
	
	private String userState;
	
	private String lockedState;
	
    private String passwordRecords;
    private RoleDatePurviewDTO roleDatePurviewDTO;
    
    private String email;
    
    private String departmentId;
    
    private String oldUserAuthPassword;
	/**
	 * 用户授权密码
	 */
	private String userAuthPassword;
	
	private String resetMark;//密码是否被重置
	
    
    
    public String getResetMark() {
		return resetMark;
	}

	public void setResetMark(String resetMark) {
		this.resetMark = resetMark;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/***
     * 用户所属一级机构
     */
    
    private List<TenantDTO> tenantDTOList = new ArrayList<TenantDTO>();
    /**
     * 用户所属发卡机构
     */
    private List<IssuerDTO>  issuerDTOList= new ArrayList<IssuerDTO>();
    
    /***
     * 用户所属收单机构
     */
    
    private List<ConsumerDTO>  consumerDTOList = new ArrayList<ConsumerDTO>();
    /**
     * 用户所属营销机构 
     */
    private List<SellerDTO> serllerDTOList = new ArrayList<SellerDTO>();
    
    /***
     * 收单机构名称
     */
    private String consumerName;
    
    /***
     * 发卡机构名称
     */
    private String issuerName;
    /**
     * 营销机构名称
     */
    private String sellerName;
    /***
     * 一级机构名称
     */
    private String tenantName;
    
    private String userEmail;
	private List<String> functionRoleId;
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	private List<RoleDTO> roleDTOs;
	private List<String> userIds;
	public List<RoleDTO> getRoleDTOs() {
		return roleDTOs;
	}

	public void setRoleDTOs(List<RoleDTO> roleDTOs) {
		this.roleDTOs = roleDTOs;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
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

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setIsSaleFlage(String isSaleFlage) {
		this.isSaleFlage = isSaleFlage;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public void setLockedState(String lockedState) {
		this.lockedState = lockedState;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public String getIssuerGroupName() {
		return issuerGroupName;
	}

	public void setIssuerGroupName(String issuerGroupName) {
		this.issuerGroupName = issuerGroupName;
	}

	





	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Short getGroupUserFlag() {
		return groupUserFlag;
	}

	public void setGroupUserFlag(Short groupUserFlag) {
		this.groupUserFlag = groupUserFlag;
	}

	

	

	public String getUserId() {
		return userId;
	}

	public String getIsSaleFlage() {
		return isSaleFlage;
	}

	public String getUserState() {
		return userState;
	}

	public String getLockedState() {
		return lockedState;
	}

	public List<ResourceDTO> getResourceDTOList() {
		return resourceDTOList;
	}

	public void setResourceDTOList(List<ResourceDTO> resourceDTOList) {
		this.resourceDTOList = resourceDTOList;
	}

	public String getOldUserPassword() {
		return oldUserPassword;
	}

	public void setOldUserPassword(String oldUserPassword) {
		this.oldUserPassword = oldUserPassword;
	}

	
	public String getPasswordRecords() {
		return passwordRecords;
	}

	public void setPasswordRecords(String passwordRecords) {
		this.passwordRecords = passwordRecords;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public List<TenantDTO> getTenantDTOList() {
		return tenantDTOList;
	}

	public void setTenantDTOList(List<TenantDTO> tenantDTOList) {
		this.tenantDTOList = tenantDTOList;
	}

	public List<IssuerDTO> getIssuerDTOList() {
		return issuerDTOList;
	}

	public void setIssuerDTOList(List<IssuerDTO> issuerDTOList) {
		this.issuerDTOList = issuerDTOList;
	}

	public List<ConsumerDTO> getConsumerDTOList() {
		return consumerDTOList;
	}

	public void setConsumerDTOList(List<ConsumerDTO> consumerDTOList) {
		this.consumerDTOList = consumerDTOList;
	}

	public RoleDatePurviewDTO getRoleDatePurviewDTO() {
		return roleDatePurviewDTO;
	}

	public void setRoleDatePurviewDTO(RoleDatePurviewDTO roleDatePurviewDTO) {
		this.roleDatePurviewDTO = roleDatePurviewDTO;
	}

	public List<SellerDTO> getSerllerDTOList() {
		return serllerDTOList;
	}

	public void setSerllerDTOList(List<SellerDTO> serllerDTOList) {
		this.serllerDTOList = serllerDTOList;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public List<String> getFunctionRoleId() {
		return functionRoleId;
	}

	public void setFunctionRoleId(List<String> functionRoleId) {
		this.functionRoleId = functionRoleId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOldUserAuthPassword() {
		return oldUserAuthPassword;
	}

	public void setOldUserAuthPassword(String oldUserAuthPassword) {
		this.oldUserAuthPassword = oldUserAuthPassword;
	}

	public String getUserAuthPassword() {
		return userAuthPassword;
	}

	public void setUserAuthPassword(String userAuthPassword) {
		this.userAuthPassword = userAuthPassword;
	}

	public Object[] getIsSale() {
		return isSale;
	}

	public void setIsSale(Object[] isSale) {
		this.isSale = isSale;
	}

	
	

}
