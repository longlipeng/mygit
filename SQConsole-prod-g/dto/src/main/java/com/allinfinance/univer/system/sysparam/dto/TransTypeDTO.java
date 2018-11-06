package com.allinfinance.univer.system.sysparam.dto;

import java.io.Serializable;

public class TransTypeDTO implements Serializable {	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6033836879475956814L;
	private String transId;
    private String transName;
    private String transCode;
    private String dataState;
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getTransName() {
		return transName;
	}
	public void setTransName(String transName) {
		this.transName = transName;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getDataState() {
		return dataState;
	}
	public void setDataState(String dataState) {
		this.dataState = dataState;
	}
    
    
}
