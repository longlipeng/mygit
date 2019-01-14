package com.huateng.framework.security.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.huateng.framework.security.model.User;
import com.huateng.framework.util.ReDTOs;
/**
 * spring securety
 * @author wpf
 *
 */
public class UserDetailsServiceDBTestImpl implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		

		User user = (User) ReDTOs.getDTO(User.class, false);
		user.setUserId("10000");
		user.setUserName("testUser");
		user.setUserName("testPass");
		return user;
	}

}
