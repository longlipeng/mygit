/**
 * Classname RansomOrderService.java
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
 *		wpf		2012-12-17
 * =============================================================================
 */

package com.huateng.univer.order.business.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListDTO;
import com.huateng.framework.exception.BizServiceException;

/**
 * @author wpf
 * 
 */
public interface RansomOrderService {
	/**
	 * 赎回订单接收查询
	 * 
	 * @param sellOrderOrigCardListDTO
	 * @return
	 * @throws BizServiceException
	 */
	public SellOrderOrigCardListDTO inqueryPageList(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
			throws BizServiceException;
	
	/**
     * 根据卡号查询赎回卡的信息
     * 
     * @param sellOrderOrigCardListDTO
     * @return
     * @throws BizServiceException
     */
    public PageDataDTO checkQueryPageList(SellOrderOrigCardListDTO 
            sellOrderOrigCardListDTO)
            throws BizServiceException;

	/**
	 * 查询卡列表
	 * 
	 * @param sellOrderOrigCardListDTO
	 * @return
	 * @throws BizServiceException
	 */
	@Deprecated
	public PageDataDTO inqueryOrderCardList(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
			throws BizServiceException;

	/**
	 * 验卡
	 * 
	 * @param sellOrderOrigCardListDTO
	 * @return
	 * @throws BizServiceException
	 */
	public SellOrderOrigCardListDTO insertCheckCard(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
			throws BizServiceException;

	/**
	 * 赎回订单入库
	 * 
	 * @param sellOrderDTO
	 * @return
	 * @throws BizServiceException
	 */
	public void submitOrder(SellOrderDTO sellOrderDTO)
			throws BizServiceException;

	/**
	 * 修改赎回订单明细中的卡状态
	 * 
	 * @param sellOrderOrigCardListDTO
	 * @throws BizServiceException
	 */
	public void modifyOrigCardListCardState(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
			throws BizServiceException;
	
	public void submitOrderForUpdate(SellOrderDTO sellOrderDTO)
	throws BizServiceException;
	
	/**
	 * 
	 * 插入原卡 <br>
	 * 〈功能详细描述〉
	 *
	 * @param sellOrderOrigCardListDTO
	 * @return
	 * @throws BizServiceException
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public SellOrderOrigCardListDTO insertCheckCardNew (
            SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
            throws BizServiceException;
}
