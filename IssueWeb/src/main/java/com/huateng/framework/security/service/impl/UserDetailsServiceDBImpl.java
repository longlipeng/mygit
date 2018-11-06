package com.huateng.framework.security.service.impl;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.LockedException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.security.model.User;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.webservice.service.WebServiceClientService;

/**
 * UserDetailService的实现
 * 
 * @author liming.feng
 * 
 */
public class UserDetailsServiceDBImpl  implements UserDetailsService {
	
	private Logger logger = Logger.getLogger(UserDetailsServiceDBImpl.class);
	
	/**
	 * 向后台服务取得数据的service
	 */
	private WebServiceClientService webServiceClientService;

	/**
	 * 实现接口类中的方法
	 * 验证用户名和密码，并取得其权限
	 */
	@SuppressWarnings("unchecked")
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException,LockedException {

		try {
			OperationCtx operationCtx = new OperationCtx();
			operationCtx.setTxncode(ConstCode.USER_SERVICE_FIND_USER);
			OperationRequest operationRequest = new OperationRequest();
			operationRequest.setTxnvo(userName);
			OperationResult operationResult = webServiceClientService.process(operationCtx,
					operationRequest);
			
			if((userName+"userName error!").equals(operationResult.getErrMessage())){
			    throw new UsernameNotFoundException("用户名不存在!");
			}else if((userName+"Invalid User!").equals(operationResult.getErrMessage())){
			    throw new  LockedException("无效的用户！");
			}
			
			
			UserDTO userDTO = (UserDTO) operationResult.getDetailvo();
			
			
			/**
			 * 锁定用户实现
			 */
			if (Integer.parseInt(userDTO.getLockedState()) < 6) {
				operationCtx = new OperationCtx();
				operationCtx.setTxncode(ConstCode.USER_SERVICE_UPDATEUSER);
				UserDTO userLockDTO = new UserDTO();
				userLockDTO.setUserId(userDTO.getUserId());
				userLockDTO.setLockedState((Integer.parseInt(userDTO
						.getLockedState()) + 1)
						+ "");
				operationRequest = new OperationRequest();
				operationRequest.setTxnvo(userLockDTO);
				operationResult = webServiceClientService.process(operationCtx,
						operationRequest);
			} else {
				throw new LockedException("用户被锁定请联系管理人员!");
			}
			if (Integer.parseInt(userDTO.getLockedState()) == 6) {
				throw new LockedException("用户被锁定请联系管理人员!");
			}
			User user = new User();
			
					
			ReflectionUtil.copyProperties(userDTO, user);		
			return user;
		} catch (BizServiceException e) {
			this.logger.error(e.getMessage());
			throw new UsernameNotFoundException(e.getErrorMessage());
		} catch (LockedException e) {
			this.logger.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new UsernameNotFoundException("请求出错！");
		}
	}

	/**
	 * @return
	 */
	public WebServiceClientService getWebServiceClientService() {
		return webServiceClientService;
	}

	/**
	 * @param webServiceClientService
	 */
	public void setWebServiceClientService(WebServiceClientService webServiceClientService) {
		this.webServiceClientService = webServiceClientService;
	}
}