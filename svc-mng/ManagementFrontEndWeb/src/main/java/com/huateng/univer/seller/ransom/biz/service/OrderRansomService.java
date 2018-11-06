package com.huateng.univer.seller.ransom.biz.service;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.ransom.OrderRansomDTO;
import com.huateng.framework.exception.BizServiceException;

public interface OrderRansomService {

	/**
	 * 赎回订单页面查询
	 * 
	 * @param dto
	 * @throws BizServiceException
	 * @return
	 */
	public PageDataDTO queryPage(OrderRansomDTO dto) throws BizServiceException;

	/**
	 * 添加订单
	 * 
	 * @param dto
	 * @throws BizServiceException
	 * @return
	 */
	public OrderRansomDTO add(OrderRansomDTO dto) throws BizServiceException;

	/**
	 * 修改订单
	 * 
	 * @param dto
	 * @throws BizServiceException
	 * @return
	 */
	public OrderRansomDTO update(OrderRansomDTO dto) throws BizServiceException;

	/**
	 * 查看订单
	 * 
	 * @param dto
	 * @throws BizServiceException
	 * @return
	 */
	public SellOrderDTO view(OrderRansomDTO dto) throws BizServiceException;

	/**
	 * 取消
	 * 
	 * @param dto
	 * @throws BizServiceException
	 * @return
	 */
	public OrderRansomDTO cancel(OrderRansomDTO dto) throws BizServiceException;

	/**
	 * 提交
	 * 
	 * @param dto
	 * @throws BizServiceException
	 * @return
	 */
	public OrderRansomDTO submit(OrderRansomDTO dto) throws BizServiceException;

	/**
	 * 订单明细
	 * 
	 * @param dto
	 * @throws BizServiceException
	 * @return
	 */
	public PageDataDTO orderList(OrderRansomDTO dto) throws BizServiceException;

	/**
	 * 返回一个订单内容
	 * 
	 * @param dto
	 * @throws BizServiceException
	 * @return
	 */
	public SellOrderDTO getOrder(OrderRansomDTO dto) throws BizServiceException;

}
