/**
 * Classname OrderRechargeActionInterface.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		wanglu		2013-1-14
 * =============================================================================
 */

package com.huateng.univer.order.business.action;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * @author wanglu
 *
 */
public interface OrderRechargeActionInterface {
	public void submitOrderImmdiatelyCredit(SellOrderDTO sellOrderDTO)
	throws BizServiceException;
}
