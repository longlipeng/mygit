package com.huateng.framework.main.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.security.model.User;
import com.huateng.framework.webservice.service.SystemInfoService;
import com.opensymphony.xwork2.ActionContext;

public class MainMenuAction extends BaseAction {
	private Logger logger = Logger.getLogger(MainMenuAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 197472254608787730L;
	
	private List<ResourceDTO> resourceDTOList = new ArrayList<ResourceDTO>();
	
	private String jsonList;

	private String resetMark;
	
	/**
	 * 初始化菜单
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String initMenu() {
		ActionContext ac=ActionContext.getContext();
		getSession().put("WW_TRANS_I18N_LOCALE","zh_CN");
		resourceDTOList = new ArrayList<ResourceDTO>();
		
		Map<String,ResourceDTO> urlMap = new HashMap<String,ResourceDTO>();
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		
		try {
			
			systemInfoService.initSystemInfo();
			
			Object urlAuthorities = ServletActionContext.getServletContext().getAttribute("urlAuthorities");
			
			// 开发模式下登录时 取得全部受保护资源，生产模式下容器启动时取得全部受保护资源
			if (urlAuthorities == null || ((List<ResourceDTO>)urlAuthorities).size() == 0) {
				/** 取得所有受保护的url */
				OperationResult result = sendService(
						ConstCode.ROLE_SERVICE_GET_ALL_RESOURCE, null);
				urlAuthorities = result.getDetailvo();
				ServletActionContext.getServletContext().setAttribute("urlAuthorities", urlAuthorities);
			}
			
			// 根据用户的角色信息选择显示的菜单
			User user = this.getUser();
			//检查用户密码是否被重置
			UserDTO userInfo=(UserDTO)sendService(ConstCode.GET_USERINFO_BY_ID,user.getUserId()).getDetailvo();
			resetMark=userInfo.getResetMark();
			for (ResourceDTO r : user.getResourceDTOList()) {
				if (!urlMap.containsKey(r.getResourceId())) {
					urlMap.put(r.getResourceId(), r);
					resourceDTOList.add(r);
					if (r.getResourceUrl() != null)
					grantedAuthorities.add(new GrantedAuthorityImpl(r
							.getResourceUrl()));
					logger.debug(r.getResourceUrl());
				}
			}
			// 同时把该用户允许访问的受保护资源存在用户缓存中供安全认证时使用
			user.setGrantedAuthority(grantedAuthorities
					.toArray(new GrantedAuthority[grantedAuthorities.size()]));
			jsonList = JSONArray.fromObject(resourceDTOList).toString();
			
			/**
			 * 验证通过后，将LOCKED_STATE置0
			 */
				
			UserDTO userDTO=new UserDTO();
			userDTO.setUserId(user.getUserId());
			userDTO.setLockedState("0");
			sendService(ConstCode.USER_SERVICE_UPDATEUSER,userDTO);
		} catch (Exception e) {
			//this.logger.error(e.getMessage());
			try {
				this.getResponse().sendRedirect(this.getRequest().getContextPath()+"/j_spring_security_logout");
				return null;
			} catch (IOException e1) {
				logger.error(e1.getMessage());
			}
		}
	//	this.getRequest().setAttribute("issuerGroup", this.getIssuerGroupLst());
		this.getRequest().getSession().setAttribute("userLoginMark", "1");
		if (this.getRequest().getSession().getAttribute("callCenter") != null) {
			if (this.getRequest().getSession().getAttribute("callCenter").equals(true)) {
				this.getRequest().getSession().removeAttribute("callCenter");
				return "callCenter";
			}
		}
		
		
		
		return "list";
	}

	/**
	 * 改变发卡机构或者发卡机构组 如果当前用户选择的是组 则当前用户信息中IssuerId的值为0 否则为当前用户选择的IssuerId
	 * 
	 * @param issuerId
	 */

//	public void changeIssuer() throws Exception{
//		HttpSession session=getRequest().getSession();
//
//		if(session.isNew()){
//			try {
//				getResponse().getWriter().print("true");
//			} catch (IOException e) {				
//				this.logger.error(e.getMessage());
//			}
//		}
//		
//		
//		User user = this.getUser();
//		String issuerFlag = issuerId.substring(0, 1);
//		if (issuerFlag.equals("0")) {
//			user.setIssuerId(new Integer(0));
//		} else {
//			String issuer = issuerId.substring(1);
//			int issuerId=new Integer(issuer);
//			user.setIssuerId(issuerId);
//			for(int i=0;i<getIssuerLst().size();i++){
//				if(getIssuerLst().get(i).getIssuerId()==issuerId){
//					user.setIssuerName(getIssuerLst().get(i).getIssuerName());
//					break;
//				}
//			}
//		}
//	}

	private SystemInfoService systemInfoService;

	String issuerId;
	
	

	public String getResetMark() {
		return resetMark;
	}

	public void setResetMark(String resetMark) {
		this.resetMark = resetMark;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public SystemInfoService getSystemInfoService() {
		return systemInfoService;
	}

	public void setSystemInfoService(SystemInfoService systemInfoService) {
		this.systemInfoService = systemInfoService;
	}

	public List<ResourceDTO> getResourceDTOList() {
		return resourceDTOList;
	}

	public void setResourceDTOList(List<ResourceDTO> resourceDTOList) {
		this.resourceDTOList = resourceDTOList;
	}

	public String getJsonList() {
		return jsonList;
	}

	public void setJsonList(String jsonList) {
		this.jsonList = jsonList;
	}
}
