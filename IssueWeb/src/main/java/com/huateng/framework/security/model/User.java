package com.huateng.framework.security.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;

import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;

/**
 * @author liming.feng
 *
 */
public class User extends UserDTO implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7699325946748831225L;

	/**
	 * 基于安全认证时使用的缓存数组
	 */
	private GrantedAuthority[] grantedAuthority;

	/**
	 * 重写父类方法，返回一个当前登录用户允许访问的所有url的数组，用于安全认证
	 */
	public GrantedAuthority[] getAuthorities() {

		// 建立一个缓存，内容是当前用户可以访问的所有受保护的资源
		if (grantedAuthority == null) {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			Map<String, String> grantedAuthorityMap = new HashMap<String, String>();

			// 遍历这个角色下的资源
			Iterator<ResourceDTO> resourceIte = getResourceDTOList().iterator();
			while (resourceIte.hasNext()) {
				ResourceDTO resource = resourceIte.next();

				// 该资源是节点或者其他原因url为空，忽略
				if (resource.getResourceUrl() == null)
					continue;

				// 过滤url相同的资源
				if (!grantedAuthorityMap.containsKey(resource.getResourceUrl())) {
					grantedAuthorities.add(new GrantedAuthorityImpl(resource
							.getResourceUrl()));
					grantedAuthorityMap.put(resource.getResourceUrl(), resource
							.getResourceName());
				}
			}

			// 把得到的结果放到缓存
			grantedAuthority = grantedAuthorities
					.toArray(new GrantedAuthority[grantedAuthorityMap.size()]);
			grantedAuthorityMap.clear();
		}

		return grantedAuthority;
	}

	/**
	 * 当前登录用户允许访问的所有url的数组，用","分割组成字符串
	 * 
	 * @return
	 */
	public String getAuthoritiesString() {
		List<String> authorities = new ArrayList<String>();
		for (GrantedAuthority authority : getAuthorities()) {
			authorities.add(authority.getAuthority());
		}
		return StringUtils.join(authorities.toArray(new String[0]), ",");
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#getUsername()
	 */
	public String getUsername() {
		return this.getUserName();
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#isAccountNonExpired()
	 */
	public boolean isAccountNonExpired() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#isAccountNonLocked()
	 */
	public boolean isAccountNonLocked() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#isEnabled()
	 */
	public boolean isEnabled() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.userdetails.UserDetails#getPassword()
	 */
	public String getPassword() {
		return this.getUserPassword();
	}

	/**
	 * @return
	 */
	public GrantedAuthority[] getGrantedAuthority() {
		return grantedAuthority;
	}

	/**
	 * @param grantedAuthority
	 */
	public void setGrantedAuthority(GrantedAuthority[] grantedAuthority) {
		this.grantedAuthority = grantedAuthority;
	}
}