package com.allinfinance.shangqi.gateway.dto;

import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;

public class BindCardToZhongShiYou {
	private OperationRequest  req;
	private OperationCtx ctx;
	private OperationResult result;
	public OperationRequest getReq() {
		return req;
	}
	public void setReq(OperationRequest req) {
		this.req = req;
	}
	public OperationCtx getCtx() {
		return ctx;
	}
	public void setCtx(OperationCtx ctx) {
		this.ctx = ctx;
	}
	public OperationResult getResult() {
		return result;
	}
	public void setResult(OperationResult result) {
		this.result = result;
	}
	
	
}
