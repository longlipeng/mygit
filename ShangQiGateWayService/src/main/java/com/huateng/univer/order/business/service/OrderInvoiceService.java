/**
 * Classname OrderInvoiceService.java
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
 *		Administrator		2013-3-26
 * =============================================================================
 */

package com.huateng.univer.order.business.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.invoice.dto.OrderInvoiceInfoDTO;
import com.allinfinance.univer.seller.invoice.dto.OrderInvoiceInfoQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.exception.BizServiceException;
/**
 * @author Administrator
 *
 */
public interface OrderInvoiceService {

	public PageDataDTO initInvoiceInfo(OrderInvoiceInfoQueryDTO orderInvoiceInfoQueryDTO) throws BizServiceException;
	/**
	 * 获取所有销售人员名单 
	 */
	public List<UserDTO> initSaleManName(SellOrderInputDTO sellOrderInputDTO) throws BizServiceException;
	/**
	 * 新增发票初始化
	 */
	public OrderInvoiceInfoDTO addOrderInvoiceInit(SellOrderDTO sellOrderDTO)throws BizServiceException;
	
	/**
	 * 新增发票
	 */
	public OrderInvoiceInfoDTO addOrderInvoice(OrderInvoiceInfoDTO orderInvoiceInfoDTO)throws BizServiceException;
	/**
	 * 根据id查找发票信息 
	 */
	public OrderInvoiceInfoDTO	queryInvoiceinfoById(OrderInvoiceInfoDTO orderInvoiceInfoDTO)throws BizServiceException;
	
	/**
	 * 发票明细
	 */
	public OrderInvoiceInfoDTO orderInvoiceDetail(OrderInvoiceInfoDTO orderInvoiceInfoDTO) throws BizServiceException;
	/**
	 * 编辑发票
	 */
	public int modifyInvoiceInfo(OrderInvoiceInfoDTO orderInvoiceInfoDTO) throws BizServiceException;
	/**
	 * 开票
	 */
	public void updateInvoice(OrderInvoiceInfoDTO orderInvoiceInfoDTO)throws BizServiceException;
	/**
	 * 取消
	 */
	public void cancelInvoice(OrderInvoiceInfoDTO orderInvoiceInfoDTO)throws BizServiceException;
	
}
