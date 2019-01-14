package com.huateng.framework.webservice;

import java.io.Serializable;

/**
 * @author liming.feng
 *
 */
public class ContextResp implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private boolean success = true;
	
	private String result = "";
	
	public Object getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result  = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
