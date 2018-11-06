/**
 * Classname OrderInvoiceServiceImpl.java
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

package com.huateng.univer.order.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;
import com.allinfinance.univer.seller.invoice.dto.OrderInvoiceInfoDTO;
import com.allinfinance.univer.seller.invoice.dto.OrderInvoiceInfoQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.OrderInvoiceDAO;
import com.huateng.framework.ibatis.dao.OrderInvoiceInfoDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.model.OrderInvoiceInfo;
import com.huateng.framework.ibatis.model.OrderInvoiceInfoExample;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.entitybaseinfo.invoicecompany.biz.service.InvoiceCompanyService;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.order.business.service.OrderInvoiceService;

/**
 * @author lfr
 * 
 */
public class OrderInvoiceServiceImpl implements OrderInvoiceService {
	private Logger logger = Logger.getLogger(this.getClass());
	private OrderInvoiceInfoDAO orderInvoiceInfoDAO;
	private SellOrderDAO sellOrderDAO;
	private BaseDAO baseOrderDAO;
	private PageQueryDAO pageQueryDAO;
	private OrderInvoiceDAO orderInvoiceDAO;
	private CommonsDAO commonsDAO;
	private InvoiceCompanyService invoiceCompanyService;
	private OrderBaseQueryBO orderBaseQueryBO;

	//发票录入初始化
	public OrderInvoiceInfoDTO addOrderInvoiceInit(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		OrderInvoiceInfoDTO orderInvoiceInfoDTO = new OrderInvoiceInfoDTO();
		try {
			String orderId = sellOrderDTO.getOrderId();
			//是否已经录入发票
			OrderInvoiceInfoExample orderInvoiceInfoExample = new OrderInvoiceInfoExample();
			orderInvoiceInfoExample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andOrderIdEqualTo(
							orderId);
			int count = orderInvoiceInfoDAO.countByExample(orderInvoiceInfoExample);
			if (count > 0){
				throw new BizServiceException("发票信息已经录入过，请不要再次录入!");
			}
			
			//获取订单信息
			SellOrderDTO resultOrderDTO = (SellOrderDTO) baseOrderDAO
			.queryForObject("getSellOrderInfoById", sellOrderDTO
					.getOrderId());
			
			if(resultOrderDTO == null){
				throw new BizServiceException("只有销售订单、充值订单、赎回订单、换卡订单可以录入发票！");
			}
			
			//从客户信息中获取默认发票公司名称
			List<InvoiceCompanyDTO>  invoiceCompany= invoiceCompanyService
					.inquery(resultOrderDTO.getFirstEntityId());
			if(invoiceCompany != null && invoiceCompany.size()>0){
				InvoiceCompanyDTO invoiceCompanyDTO = invoiceCompany.get(0);
				orderInvoiceInfoDTO.setInvoiceTitle(invoiceCompanyDTO.getInvoiceCompanyName());
			}
			
			orderInvoiceInfoDTO.setOrderId(resultOrderDTO.getOrderId());
			orderInvoiceInfoDTO.setTotalPrice(resultOrderDTO.getTotalPrice());
			orderInvoiceInfoDTO.setCustomerName(resultOrderDTO.getFirstEntityName());
			//发票抬头

			return orderInvoiceInfoDTO;
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("发票录入初始化失败！");
		}
		
	}
	
	//发票录入
	public OrderInvoiceInfoDTO addOrderInvoice(OrderInvoiceInfoDTO orderInvoiceInfoDTO)
			throws BizServiceException {
		OrderInvoiceInfo orderInvoiceInfo = new OrderInvoiceInfo();
		try {		
			ReflectionUtil.copyProperties(orderInvoiceInfoDTO, orderInvoiceInfo);
			String seqOrderInvoice = commonsDAO.getNextValueOfSequenceBySequence("SEQ_ORDER_INVOICE");
			orderInvoiceInfo.setInvoiceId(seqOrderInvoice);
			orderInvoiceInfo.setInvoiceAmount(Amount
					.getDataBaseAmount(orderInvoiceInfoDTO.getTotalPrice()));
			//客户预计取票日期	
			String sEDateStr = orderInvoiceInfoDTO.getCustomerExpectedDate();
			if(!StringUtils.isBlank(sEDateStr)) {
			    Date today = DateUtil.getCurrentDate();
			    Date customerExpectedDate = DateUtil.string2date(sEDateStr);
			    if(customerExpectedDate.before(today)) {
			        throw new BizServiceException("客户预计取票日期小于当前时间！");
			    }
			    orderInvoiceInfo.setCustomerExpectedDate(DateUtil
                        .StringDate(sEDateStr));  
			}
			//发票状态：未开票
			orderInvoiceInfo.setInvoiceState(DataBaseConstant.INVOICE_NOT_MAKE);		
			orderInvoiceInfo.setCreateUser(orderInvoiceInfoDTO.getLoginUserId());
			orderInvoiceInfo.setCreateTime(DateUtil.getCurrentTime());
			orderInvoiceInfo.setModifyUser(orderInvoiceInfoDTO.getLoginUserId());
			orderInvoiceInfo.setModifyTime(DateUtil.getCurrentTime());
			orderInvoiceInfo.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			//插入发票信息
			orderInvoiceInfoDAO.insert(orderInvoiceInfo);
			return orderInvoiceInfoDTO;
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("发票录入失败！");
		}
	}
	


	/**
	 * 查询列表
	 * 
	 * @param orderInvoiceInfoQueryDTO
	 * @return TODO 跳转发票管理 初始化list
	 */
	public PageDataDTO initInvoiceInfo(
			OrderInvoiceInfoQueryDTO orderInvoiceInfoQueryDTO)
			throws BizServiceException {
		try {
			return pageQueryDAO.query("INVOICE.queryInvoice",
					orderInvoiceInfoQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单失败!");
		}
	}

	public List<UserDTO> initSaleManName(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException {
		List<UserDTO> saleMans = new ArrayList<UserDTO>();
		try {
			saleMans = orderBaseQueryBO.getSaleUserList(sellOrderInputDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取销售人员名单失败!");
		}
		return saleMans;
	}


	/**
	 * 根据id查询发票信息 TODO
	 */
	public OrderInvoiceInfoDTO queryInvoiceinfoById(
			OrderInvoiceInfoDTO orderInvoiceInfoDTO) throws BizServiceException {
		try {
			return orderInvoiceDAO.queryInvoiceInfoById(orderInvoiceInfoDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询发票信息失败!");
		}
	}
	
	/**
	 * 开票明细
	 */
	public OrderInvoiceInfoDTO orderInvoiceDetail(
			OrderInvoiceInfoDTO orderInvoiceInfoDTO) throws BizServiceException {
		OrderInvoiceInfoDTO dto = new OrderInvoiceInfoDTO();
		try {
            OrderInvoiceInfo orderInvoiceInfo = orderInvoiceInfoDAO.selectByPrimaryKey(orderInvoiceInfoDTO.getInvoiceId());
			dto.setInvoiceId(orderInvoiceInfo.getInvoiceId());
			dto.setOrderId(orderInvoiceInfo.getOrderId());
			dto.setInvoiceTitle(orderInvoiceInfo.getInvoiceTitle());
			dto.setInvoiceAmount(orderInvoiceInfo.getInvoiceAmount());
			dto.setInvoiceType(orderInvoiceInfo.getInvoiceType());
			dto.setInvoiceProj(orderInvoiceInfo.getInvoiceProj());
			String customerExpectedDate = orderInvoiceInfo.getCustomerExpectedDate();
			if(!StringUtils.isBlank(customerExpectedDate)) {
				dto.setCustomerExpectedDate(DateUtil.formatStringDate(customerExpectedDate));
			}
			String invoiceDate = orderInvoiceInfo.getInvoiceDate();
			if(!StringUtils.isBlank(invoiceDate)) {
				dto.setInvoiceDate(DateUtil.formatStringDate(invoiceDate));
			}
			dto.setInvoiceCode(orderInvoiceInfo.getInvoiceCode());
			dto.setSendoutWay(orderInvoiceInfo.getSendoutWay());
			dto.setInvoiceCustomerAddress(orderInvoiceInfo.getInvoiceCustomerAddress());
			dto.setUserId(orderInvoiceInfo.getUserId());
			dto.setComment(orderInvoiceInfo.getComment());
			dto.setInvoiceState(orderInvoiceInfo.getInvoiceState());
			dto.setCreateUser(orderInvoiceInfo.getCreateUser());
			String createTime = orderInvoiceInfo.getCreateTime();
			if(!StringUtils.isBlank(createTime)) {
				dto.setCreateTime(DateUtil.formatStringDate(createTime));
			}
			dto.setModifyUser(orderInvoiceInfo.getModifyUser());
			String modifyTime = orderInvoiceInfo.getModifyTime();
			if(!StringUtils.isBlank(modifyTime) ){
				dto.setModifyTime(modifyTime);
			}
			dto.setDataState(orderInvoiceInfo.getDataState());
			//根据用户ID查询用户名
			UserDTO userDTO = (UserDTO) baseOrderDAO.queryForObject(
					"getUserInfoById", orderInvoiceInfo.getUserId());
			if(null == userDTO) {
				throw new BizServiceException("没有这个用户");
			}
			dto.setUserName(userDTO.getUserName());
			//获取订单信息
			SellOrderDTO resultOrderDTO = (SellOrderDTO) baseOrderDAO
			.queryForObject("getSellOrderInfoById", orderInvoiceInfo.getOrderId());
			
			if(null == resultOrderDTO){
				throw new BizServiceException("只有销售订单和充值订单可以录入发票！");
			}
			dto.setTotalPrice(resultOrderDTO.getTotalPrice());
			dto.setCustomerName(resultOrderDTO.getFirstEntityName());
			
			dto.setBillingSubject(orderInvoiceInfo.getBillingSubject());
			dto.setBillingDate(DateUtil.formatStringDate(orderInvoiceInfo.getBillingDate()));
			dto.setInvoiceNumber(orderInvoiceInfo.getInvoiceNumber());
			return dto;
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("发票明细查询失败！");
		}
	}



	public int modifyInvoiceInfo(OrderInvoiceInfoDTO orderInvoiceInfoDTO)
			throws BizServiceException {
		logger.info("-------------service----------------");
		OrderInvoiceInfo orderInvoiceInfo = new OrderInvoiceInfo(); 
		ReflectionUtil.copyProperties(orderInvoiceInfoDTO, orderInvoiceInfo);
		return orderInvoiceInfoDAO.updateByPrimaryKeySelective(orderInvoiceInfo);
	}
	
	/**
	 * 开票确定
	 */
	public void updateInvoice(OrderInvoiceInfoDTO orderInvoiceInfoDTO)
			throws BizServiceException {
		try {
			OrderInvoiceInfo orderInvoiceInfo = orderInvoiceInfoDAO.selectByPrimaryKey(orderInvoiceInfoDTO.getInvoiceId());
			String invoiceState = orderInvoiceInfo.getInvoiceState();
			if(DataBaseConstant.INVOICE_NOT_MAKE.equals(invoiceState)) {
				OrderInvoiceInfo dto = new OrderInvoiceInfo();
				dto.setInvoiceId(orderInvoiceInfoDTO.getInvoiceId());
				dto.setInvoiceState(DataBaseConstant.INVOICE_HAS_MAKE);
				dto.setBillingSubject(orderInvoiceInfoDTO.getBillingSubject());
				String invoiceDate = orderInvoiceInfoDTO.getInvoiceDate();
				if(!StringUtils.isBlank(invoiceDate)) {
				    dto.setInvoiceDate(DateUtil.StringDate(invoiceDate));
				}	
				dto.setBillingDate(DateUtil.getCurrentDateStr());
				dto.setInvoiceCode(orderInvoiceInfoDTO.getInvoiceCode());
				dto.setInvoiceNumber(orderInvoiceInfoDTO.getInvoiceNumber());
				orderInvoiceInfoDAO.updateByPrimaryKeySelective(dto);
			}
			else if(DataBaseConstant.INVOICE_HAS_MAKE.equals(invoiceState)) {
				throw new BizServiceException("此订单已开票");
			}
			else if(DataBaseConstant.INVOICE_CANCEL_MAKE.equals(invoiceState)) {
				throw new BizServiceException("此订单已取消");
			}	
		} catch (BizServiceException ex) {
			throw ex;
	
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("开票失败");
		}
	}
	
	/**
	 * 取消开票
	 */
	public void cancelInvoice(OrderInvoiceInfoDTO orderInvoiceInfoDTO)
		throws BizServiceException {
		try {
			OrderInvoiceInfo orderInvoiceInfo = orderInvoiceInfoDAO.selectByPrimaryKey(orderInvoiceInfoDTO.getInvoiceId());
			String invoiceState = orderInvoiceInfo.getInvoiceState();
			if(DataBaseConstant.INVOICE_NOT_MAKE.equals(invoiceState)) {
				OrderInvoiceInfo dto = new OrderInvoiceInfo();
				dto.setInvoiceId(orderInvoiceInfoDTO.getInvoiceId());
				dto.setInvoiceState(DataBaseConstant.INVOICE_CANCEL_MAKE);
				orderInvoiceInfoDAO.updateByPrimaryKeySelective(dto);
			}
			else if(DataBaseConstant.INVOICE_HAS_MAKE.equals(invoiceState)) {
				throw new BizServiceException("此订单已开票");
			}else if(DataBaseConstant.INVOICE_CANCEL_MAKE.equals(invoiceState)) {
				throw new BizServiceException("此订单已取消");
			}	
		} catch (BizServiceException ex) {
			throw ex;
		
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("取消开票失败");
		}
	}
	
	public OrderInvoiceDAO getOrderInvoiceDAO() {
		return orderInvoiceDAO;
	}

	public void setOrderInvoiceDAO(OrderInvoiceDAO orderInvoiceDAO) {
		this.orderInvoiceDAO = orderInvoiceDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}
	public OrderInvoiceInfoDAO getOrderInvoiceInfoDAO() {
		return orderInvoiceInfoDAO;
	}


	public void setOrderInvoiceInfoDAO(OrderInvoiceInfoDAO orderInvoiceInfoDAO) {
		this.orderInvoiceInfoDAO = orderInvoiceInfoDAO;
	}

    public BaseDAO getBaseOrderDAO() {
		return baseOrderDAO;
	}


	public void setBaseOrderDAO(BaseDAO baseOrderDAO) {
		this.baseOrderDAO = baseOrderDAO;
	}
	
	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public InvoiceCompanyService getInvoiceCompanyService() {
		return invoiceCompanyService;
	}

	public void setInvoiceCompanyService(InvoiceCompanyService invoiceCompanyService) {
		this.invoiceCompanyService = invoiceCompanyService;
	}

	public OrderBaseQueryBO getOrderBaseQueryBO() {
		return orderBaseQueryBO;
	}

	public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
		this.orderBaseQueryBO = orderBaseQueryBO;
	}
	
}
