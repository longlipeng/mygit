package com.allinfinance.univer.seller.order.dto;

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 订单流程dto
 * @author xxl
 *
 */
public class SellOrderFlowDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7900724026723079130L;
	private String orderFlowId;
    private String entityId;
    private String orderId;
    private String orderFlowNode;
    private String operateType;
    private String memo;
    private String createUser;
    private String createTime;
    private String modifyUser;
    private String modifyTime;
    private String dataState;
    
    
	public String getOrderFlowId() {
		return orderFlowId;
	}
	public void setOrderFlowId(String orderFlowId) {
		this.orderFlowId = orderFlowId;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderFlowNode() {
		return orderFlowNode;
	}
	public void setOrderFlowNode(String orderFlowNode) {
		this.orderFlowNode = orderFlowNode;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getDataState() {
		return dataState;
	}
	public void setDataState(String dataState) {
		this.dataState = dataState;
	}
    
    
}
