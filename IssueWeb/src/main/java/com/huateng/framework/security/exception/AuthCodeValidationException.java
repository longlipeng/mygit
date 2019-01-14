package com.huateng.framework.security.exception;

import org.springframework.security.AuthenticationException;

public class AuthCodeValidationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3426248563816728565L;

	public AuthCodeValidationException(String msg) {
		super(msg);
	}
}
