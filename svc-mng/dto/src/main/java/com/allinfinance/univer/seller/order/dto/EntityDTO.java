package com.allinfinance.univer.seller.order.dto;

import com.allinfinance.framework.dto.BaseDTO;
/**
 * 实体
 * @author dawn
 *
 */
public class EntityDTO extends BaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String entityId;
	
	public String entityName;

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	
}
