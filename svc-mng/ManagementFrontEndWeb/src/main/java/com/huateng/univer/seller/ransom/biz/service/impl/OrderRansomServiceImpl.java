package com.huateng.univer.seller.ransom.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.ransom.OrderRansomDTO;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.seller.ransom.biz.service.OrderRansomService;

public class OrderRansomServiceImpl implements OrderRansomService {
	private Logger logger = Logger.getLogger(OrderRansomServiceImpl.class);
	private PageQueryDAO pageQueryDAO;
	private SellOrderDAO sellOrderDAO;
	private CommonsDAO commonsDAO;

	private BaseDAO baseDAO;

	public OrderRansomDTO add(OrderRansomDTO dto) throws BizServiceException {

		return null;
	}

	/*
	 * add(); 分为三个部分.
	 * 
	 * 第一部分.主要操作订单信息. 第二部分.实名信息. // 增加.实名信息只需要在订单中加入经办人ID即可. 第三部分.订单明细.
	 * 
	 * 细化.
	 */

	/**
	 * 
	 <<<<<<<<<<<信息 订单号 订单日期 客户号 客户名称 营销机构 卡余额总计（元） 服务费率（%） 服务费总计（元） 其他退回费用（元）
	 * 总费用（元） 创建人 创建日期 订单状态 订单来源 备注
	 */
	public OrderRansomDTO addOrder(OrderRansomDTO dto)
			throws BizServiceException {

		try {
			String id = commonsDAO.getNextValueOfSequence("TB_SELL_ORDER");

			SellOrder o = new SellOrder();
			BeanUtils.copyProperties(dto.getSellOrderDTO(), o);
			o.setOrderDate(dto.getSellOrderDTO().getOrderDate().replaceAll("-",
					""));
			o.setOrderId(id);
			o.setCreateUser(dto.getLoginUserId());
			o.setModifyUser(dto.getLoginUserId());
			o.setCreateTime(DateUtil.getCurrentTime());
			o.setModifyTime(DateUtil.getCurrentTime());
			o.setOrderType("70000001");
			o.setOrderState("1");
			o.setDataState("1");
			sellOrderDAO.insert(o);
			return dto;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("录入赎回订单失败");
		}
	}

	/**
	 * <<<<<<<<<<<<订单明细信息 原订单号 卡号 产品名称 卡内余额（元） 服务费率（%） 其他退回费用（元） 赎回金额（元） 付款账号
	 * 收款账号 支付方式 支付明细
	 */
	public OrderRansomDTO addOrderDetail(OrderRansomDTO dto) {

		return null;
	}

	public List<Map<String, String>> viewCardList(OrderRansomDTO dto) {

		return null;
	}

	/**
	 * 查找一个法人信息
	 * 
	 * @param dto
	 * @return
	 * @throws BizServiceException
	 */
	public CustomerDTO getCustomerDTO(OrderRansomDTO dto)
			throws BizServiceException {
		try {
			CustomerDTO cd = (CustomerDTO) baseDAO.queryForObject(
					"ransomOrder.getCustomer", dto);
			return cd;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("法人信息查询失败");
		}
	}

	public OrderRansomDTO cancel(OrderRansomDTO dto) throws BizServiceException {

		return null;
	}

	public PageDataDTO queryPage(OrderRansomDTO dto) throws BizServiceException {
		try {
			return pageQueryDAO.query("ransomOrder.ransomPageSql", dto);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("赎回订单查询失败");
		}

	}

	public OrderRansomDTO submit(OrderRansomDTO dto) throws BizServiceException {

		try {
			SellOrder so = sellOrderDAO.selectByPrimaryKey(dto.getOrderId());
			// so.setOrderState(dto.geto);
			so.setOrderState(dto.getOrderState());
			so.setModifyTime(DateUtil.getCurrentTime());
			so.setModifyUser(dto.getLoginUserId());
			sellOrderDAO.updateByPrimaryKey(so);
			return dto;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			String state = "";
			if (dto.getOrderState().equals("2")) {
				state = "提交";
			} else if (dto.getOrderState().equals("0")) {
				state = "取消";
			} else {
				throw new BizServiceException("订单修改状态不正常");
			}
			throw new BizServiceException("赎回订单" + state + "失败");
		}

	}

	public OrderRansomDTO update(OrderRansomDTO dto) throws BizServiceException {

		return null;
	}

	public SellOrderDTO getOrder(OrderRansomDTO dto) throws BizServiceException {
		SellOrder order = sellOrderDAO.selectByPrimaryKey(dto.getOrderId());
		SellOrderDTO sodto = new SellOrderDTO();
		BeanUtils.copyProperties(order, sodto);
		return sodto;
	}

	public PageDataDTO orderList(OrderRansomDTO dto) throws BizServiceException {
		try {
			return pageQueryDAO.query("ransomOrder.ransomOrderList", dto);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			return null;
		}
	}

	public SellOrderDTO view(OrderRansomDTO dto) throws BizServiceException {
		SellOrderDTO sodto = getOrder(dto);
		sodto.setOrderList(orderList(dto));
		return sodto;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

}
