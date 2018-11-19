package com.huateng.univer.order.business.query.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.AcceptOrderDTO;
import com.allinfinance.univer.seller.order.dto.CardNoSectionDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.SellBuyOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCompositeDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.PackageDAO;
import com.huateng.framework.ibatis.dao.ProductDAO;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.model.Package;
import com.huateng.framework.ibatis.model.PackageExample;
import com.huateng.framework.ibatis.model.Product;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderCardListExample;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.ibatis.model.SellOrderOrigCardList;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.entitybaseinfo.contact.biz.service.ContactService;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.order.business.query.SellOrderQuery;
import com.huateng.univer.order.business.service.SellOrderPaymentService;
import com.huateng.univer.seller.customer.biz.service.CustomerService;
import com.huateng.univer.seller.seller.biz.service.SellerService;
import com.huateng.univer.seller.sellercontract.biz.service.SellerProductContractService;

public class SellOrderQueryImpl implements SellOrderQuery {

	private OrderBaseQueryBO orderBaseQueryBO;
	private SellerProductContractService sellerProductContractService;
	private ProductDAO productDAO;
	private SellerService sellerService;
	private CustomerService customerService;
	private PackageDAO packageDAO;
	private ContactService contactService;
	private SellOrderPaymentService sellOrderPaymentService;
	private SellOrderCardListDAO sellOrderCardListDAO;
	private BaseDAO baseDAO;

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public SellOrderPaymentService getSellOrderPaymentService() {
		return sellOrderPaymentService;
	}

	public void setSellOrderPaymentService(
			SellOrderPaymentService sellOrderPaymentService) {
		this.sellOrderPaymentService = sellOrderPaymentService;
	}

	public ContactService getContactService() {
		return contactService;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	public PackageDAO getPackageDAO() {
		return packageDAO;
	}

	public void setPackageDAO(PackageDAO packageDAO) {
		this.packageDAO = packageDAO;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public SellerService getSellerService() {
		return sellerService;
	}

	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	Logger logger = Logger.getLogger(SellOrderQueryImpl.class);

	public PageDataDTO query(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException {
		try {
			return orderBaseQueryBO.queryOrderBySeller(sellOrderQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单失败!");
		}

	}

	public PageDataDTO queryOrderActivate(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException {
		try {
			return orderBaseQueryBO.queryOrderActivate(sellOrderQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单失败!");
		}

	}
	
	/**
	 * 
	 * 功能描述: <br>
	 * 查找非现金、银行卡满足条件的待激活的订单
	 *
	 * @return PageDataDTO
	 */
    public PageDataDTO queryOrderActivateEx(SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
        try {
            return orderBaseQueryBO.queryOrderActivateEx(sellOrderQueryDTO);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询订单失败!");
        }

    }

	public OrderBaseQueryBO getOrderBaseQueryBO() {
		return orderBaseQueryBO;
	}

	public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
		this.orderBaseQueryBO = orderBaseQueryBO;
	}

	public PageDataDTO queryOrderAtSellInput(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException {
		try {
			return orderBaseQueryBO.queryOrderAtSellInput(sellOrderQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单失败");
		}
	}

	public PageDataDTO queryOrderAtBuyInput(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException {
		try {
			return orderBaseQueryBO.queryOrderAtBuyInput(sellOrderQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单失败");
		}
	}

	public PageDataDTO queryOrderAtReadyHandOut(
			SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
		try {
			return orderBaseQueryBO
					.queryOrderAtSellerHandOutReady(sellOrderQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单失败");
		}
	}

	public PageDataDTO queryOrderAtConfrim(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException {
		try {
			return orderBaseQueryBO.queryOrderAtSellConfirm(sellOrderQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单失败");
		}
	}

	public PageDataDTO queryOrderAtSellConfrim(
			SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
		try {
			return orderBaseQueryBO.queryOrderAtSellConfirm(sellOrderQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单失败");
		}
	}

	public PageDataDTO queryOrderAtBuyConfrim(
			SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
		try {
			return orderBaseQueryBO.queryOrderAtBuyConfirm(sellOrderQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单失败");
		}
	}

	public PageDataDTO queryOrderForOrderAccept(
			SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
		try {
			return orderBaseQueryBO.queryOrderForOrderAccept(sellOrderQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单失败");
		}
	}

	public PageDataDTO queryOrderAtImmediatelyCredit(
			SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
		try {
			return orderBaseQueryBO
					.queryOrderAtImmediatelyCredit(sellOrderQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单失败");
		}
	}

	public PageDataDTO queryOrderAtOrderPayment(
			SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
		try {
			return orderBaseQueryBO.queryOrderAtOrderPayment(sellOrderQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单失败");
		}
	}

	/***
	 * 订单配送确认节点
	 */
	public PageDataDTO queryOrderAtSendConfirm(
			SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
		try {
			return orderBaseQueryBO.queryOrderAtSendConfirm(sellOrderQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单失败!");
		}
	}

	public SellOrderCompositeDTO getSellOrderDTO(
			SellOrderCompositeDTO sellOrderCompositeDTO)
			throws BizServiceException {
		try {
			SellOrderDTO sellOrderDTO = sellOrderCompositeDTO.getSellOrderDTO();
			sellOrderCompositeDTO.setSellOrderDTO(orderBaseQueryBO
					.getSellOrderDTO(sellOrderCompositeDTO.getSellOrderDTO()));
			//perFlag=1 表示订单提交或退回
			if("1".equals(sellOrderDTO.getPerFlag()) 
					&& (OrderConst.ORDER_STATE_CREDIT_SUCCESS.equals(sellOrderCompositeDTO.getSellOrderDTO().getOrderState())
				    ||  OrderConst.ORDER_STATE_ORDER_SEND_CONFIRM.equals(sellOrderCompositeDTO.getSellOrderDTO().getOrderState())
				    ||  OrderConst.ORDER_STATE_ORDER_RANSOM_STOCK.equals(sellOrderCompositeDTO.getSellOrderDTO().getOrderState())
					||  OrderConst.ORDER_STATE_ORDER_PROCESSING.equals(sellOrderCompositeDTO.getSellOrderDTO().getOrderState()) 
//					||  OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL.equals(sellOrderCompositeDTO.getSellOrderDTO().getOrderState())
					)){
				throw new BizServiceException("订单已提交，不能做提交或退回操作");
			}
			// if充值前&&订单未支付
//			if ("1".equals(sellOrderDTO.getPerFlag()) && OrderConst.ORDER_STATE_WAITTING_CREDIT.equals(sellOrderCompositeDTO.getSellOrderDTO().getOrderState()) && "3".equals(sellOrderCompositeDTO.getSellOrderDTO().getPaymentTerm())
//					&& !OrderConst.ORDER_PAY_STATE_PAID.equals(sellOrderCompositeDTO.getSellOrderDTO()
//							.getPaymentState())) {
//				// 返回，充值失败，定单未支付
//				throw new BizServiceException("充值失败，订单未支付");
//			}
			//充值前订单未支付时，不能提交，可以退回
			if ("submit".equals(sellOrderDTO.getOperation()) && OrderConst.ORDER_STATE_WAITTING_CREDIT.equals(sellOrderCompositeDTO.getSellOrderDTO().getOrderState()) && "3".equals(sellOrderCompositeDTO.getSellOrderDTO().getPaymentTerm())
					&& !OrderConst.ORDER_PAY_STATE_PAID.equals(sellOrderCompositeDTO.getSellOrderDTO()
							.getPaymentState())) {
				throw new BizServiceException("充值失败，订单未支付");
			}
			sellOrderDTO = sellOrderCompositeDTO.getSellOrderDTO();
			if (null != sellOrderDTO.getPackageId()
					&& !"".equals(sellOrderDTO.getPackageId())) {
				PackageExample example = new PackageExample();
				example.createCriteria().andPackageIdEqualTo(
						sellOrderDTO.getPackageId());
				List<Package> packages = (List<Package>) packageDAO
						.selectByExample(example);
				if (null != packages && packages.size() > 0) {
					sellOrderCompositeDTO.getSellOrderDTO().setPackageName(
							packages.get(0).getPackageName());
				}
			}
			// 初始化信息
			if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {
				SellerDTO sellerDTO = sellOrderCompositeDTO.getSellerDTO();

				sellerDTO.setEntityId(sellOrderDTO.getFirstEntityId());

				sellerDTO = sellerService.getSellerByEntityId(sellerDTO);

				sellOrderCompositeDTO.setSellerDTO(sellerDTO);
			}
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {
				sellOrderDTO = orderBaseQueryBO.viewSellOrderDTO(sellOrderDTO);
				CustomerDTO customerDTO = sellOrderCompositeDTO
						.getCustomerDTO();

				customerDTO.setEntityId(sellOrderDTO.getFirstEntityId());

				customerDTO
						.setFatherEntityId(sellOrderDTO.getProcessEntityId());

				customerDTO = customerService.viewCustomer(customerDTO);

				sellOrderCompositeDTO.setCustomerDTO(customerDTO);
			}
			if (OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD.equals(sellOrderDTO
					.getOrderType())) {
				sellOrderDTO = orderBaseQueryBO
						.viewChangeCardOrderDTO(sellOrderDTO);
				/**
				 * 查看原有卡信息
				 */
				sellOrderCompositeDTO.setSellOrderOrigCardList(orderBaseQueryBO
						.queryOrigCardListByOrderbyId(sellOrderCompositeDTO
								.getSellOrderOrigCardListQueryDTO()));
				/**
				 * 查看明细
				 */
				sellOrderCompositeDTO
						.setSellOrderList(orderBaseQueryBO
								.queryChangeCardOrderListByOrderbyId(sellOrderCompositeDTO
										.getSellOrderListQueryDTO()));
				CustomerDTO customerDTO = sellOrderCompositeDTO
						.getCustomerDTO();

				customerDTO.setEntityId(sellOrderDTO.getFirstEntityId());

				customerDTO.setFatherEntityId(sellOrderDTO.getProcessEntityId());

				customerDTO = customerService.viewCustomer(customerDTO);

				sellOrderCompositeDTO.setCustomerDTO(customerDTO);
				sellOrderCompositeDTO.setSellOrderDTO(sellOrderDTO);
			}
			if (OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(sellOrderDTO
					.getOrderType())) {
				/**
				 * 查看原有卡信息
				 */
				sellOrderCompositeDTO.setSellOrderOrigCardList(orderBaseQueryBO
						.queryOrigCardListByOrderbyId(sellOrderCompositeDTO
								.getSellOrderOrigCardListQueryDTO()));
				CustomerDTO customerDTO = sellOrderCompositeDTO
						.getCustomerDTO();

				customerDTO.setEntityId(sellOrderDTO.getFirstEntityId());

				customerDTO
						.setFatherEntityId(sellOrderDTO.getProcessEntityId());

				customerDTO = customerService.viewCustomer(customerDTO);

				sellOrderCompositeDTO.setCustomerDTO(customerDTO);
			}
			// 配送确认做激活确认的
			if ("1".equals(sellOrderCompositeDTO.getSellOrderDTO().getInitActStat()) && (OrderConst.ORDER_PAY_STATE_PAID.equals(sellOrderDTO
					.getPaymentState())
					|| (OrderConst.ORDER_PAY_STATE_UNPAID.equals(sellOrderDTO
							.getPaymentState()) && "4".equals(sellOrderDTO
							.getPaymentTerm())))
							
					//订单付款审核    如果订单产品需要激活、支付方式为激活前 在配送完成后未激活的订单做激活操作
					|| "1".equals(sellOrderCompositeDTO.getSellOrderDTO().getInitActStat())
					&& "2".equals(sellOrderDTO.getPaymentTerm())
					&& "32".equals(sellOrderDTO.getOrderState())
					&& OrderConst.ORDER_PAY_STATE_CONFIRM.equals(sellOrderDTO.getPaymentState())){
				sellOrderCompositeDTO.getSellOrderDTO().setInitActStat("1");
			} else {
				sellOrderCompositeDTO.getSellOrderDTO().setInitActStat("0");
			}
			if (!OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD.equals(sellOrderDTO
					.getOrderType())
					&& !OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(sellOrderDTO
							.getOrderType())) {
				/**
				 * 查看明细
				 */
				sellOrderCompositeDTO.setSellOrderList(orderBaseQueryBO
						.queryOrderListByOrderbyId(sellOrderCompositeDTO
								.getSellOrderListQueryDTO()));
			}

			/**
			 * 查看卡明细
			 */
			if (!OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(sellOrderDTO
					.getOrderType())) {
				sellOrderCompositeDTO.setSellOrderCardList(orderBaseQueryBO
						.queryCardListByOrderbyId(sellOrderCompositeDTO
								.getSellOrderCardListQueryDTO()));
			}
			/**
			 * 查看卡接收明细
			 */
			if(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
			        .equals(sellOrderDTO.getOrderType())
			        ||OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN.
			        equals(sellOrderDTO.getOrderType())){
			    sellOrderCompositeDTO.setSellOrderReceCardList(orderBaseQueryBO
			            .getOrderReceiveListPageData(sellOrderCompositeDTO
			                    .getOrderReceiveCardListQueryDTO()));
			}
			/**
			 * 查看接收卡号段明细
			 */
			if(!OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(sellOrderDTO
                    .getOrderType())){
                sellOrderCompositeDTO.setAcceptOrderList(orderBaseQueryBO
                        .queryAcceptOrderByOrderId(sellOrderCompositeDTO
                                .getAcceptOrderDTO()));
			}
			/* 查看订单支付明细 */
			sellOrderCompositeDTO
					.setSellOrderPaymentList(sellOrderPaymentService
							.queryPage(sellOrderCompositeDTO
									.getSellOrderPaymentQueryDTO()));
			
			/**
			 * 查看流程记录
			 */
			sellOrderCompositeDTO.setSellOrderFlowList(orderBaseQueryBO
					.queryOrderFlowByOrderId(sellOrderCompositeDTO
							.getSellOrderFlowQueryDTO()));

			return sellOrderCompositeDTO;
			}catch(BizServiceException b){
			throw b;
		}catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看订单失败");
		}

	}

	public PageDataDTO getCardForSellOrderReady(OrderReadyDTO orderReadyDTO)
			throws BizServiceException {
		try {
			return orderBaseQueryBO.getCardForSellOrderReady(orderReadyDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取卡信息失败!");
		}
	}

	public SellBuyOrderDTO getContractForBuyOrder(
			SellBuyOrderDTO sellBuyOrderDTO) throws BizServiceException {
		try {
			sellBuyOrderDTO.setContractDTOs(orderBaseQueryBO
					.getContractDTOForBuyOrder(sellBuyOrderDTO));
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellBuyOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellBuyOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellBuyOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(sellBuyOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellBuyOrderDTO.getOrderType())) {
				sellBuyOrderDTO.setSellOrderListDTOs(orderBaseQueryBO
						.getOrderListForBuyOrderInsert(sellBuyOrderDTO));
			}
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellBuyOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellBuyOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellBuyOrderDTO.getOrderType())) {
				sellBuyOrderDTO.setSellOrderListDTOs(orderBaseQueryBO
						.getOrderListForBuyOrderInsert(sellBuyOrderDTO));
			}
			return sellBuyOrderDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取订单明细信息失败！");
		}

	}

	/**
	 * 代发卡合同和处理机构查询
	 * 
	 * @author Yifeng.Shi by 2011-03-14
	 * 
	 */
	public SellBuyOrderDTO getContractForBuySellOrder(
			SellBuyOrderDTO sellBuyOrderDTO) throws BizServiceException {
		try {
			Product product = productDAO.selectByPrimaryKey(sellBuyOrderDTO
					.getProductId());
			if (product.getProductDefineIssuer().equals(
					sellBuyOrderDTO.getDefaultEntityId())) {
				throw new BizServiceException("不能为本机构定义的产品生成采购订单");
			}
			if (product.getProductDefineIssuer().equals(product.getEntityId())) {
				throw new BizServiceException("产品类型必须为代发卡产品");
			}

			sellBuyOrderDTO.setLoyaltyContractDTO(orderBaseQueryBO
					.getContractDTOForSellBuyOrder(sellBuyOrderDTO));
			logger.debug(sellBuyOrderDTO.getOrderType());
			if (OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
					.equals(sellBuyOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(sellBuyOrderDTO.getOrderType())) {
				sellBuyOrderDTO.setSellOrderListDTOs(orderBaseQueryBO
						.getOrderListForBuyOrderInsert(sellBuyOrderDTO));
			}
			return sellBuyOrderDTO;
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取订单明细信息失败！");
		}

	}

	/**
	 * 购卡、换卡订单打印凭证信息查询
	 * 
	 * @author Yifeng.Shi
	 * @since 2012-02-06
	 * */
	public SellOrderDTO queryOrderPrintCert(SellOrderDTO orderDTO)
			throws BizServiceException {
		try {
			String orderTypeString = orderDTO.getOrderType();
			String orderIdString = orderDTO.getOrderId();
			/* 订单类型为售卡(不记名、记名、换卡、赎回，个人，企业，散户) */
			if (!(orderTypeString
					.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN)
					|| orderTypeString
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN)
					|| orderTypeString
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN)
					|| orderTypeString
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN)
					|| orderTypeString
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN)
					|| orderTypeString
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN)
					|| orderTypeString
							.equals(OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD)
					|| orderTypeString
							.equals(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER) || orderTypeString
					.equals(OrderConst.ORDER_TYPE_ORDER_RANSOM))) {
				throw new BizServiceException("只能打印客户购卡订单、换卡订单、充值订单、赎回订单");
			}
			/* 查询凭证中订单基本信息 */
			SellOrderDTO opeOrder = null;
			if (orderTypeString.equals(OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD)) {
				opeOrder = orderBaseQueryBO
						.getChangeOrderForCertPrint(orderIdString);
			} else if (orderTypeString
					.equals(OrderConst.ORDER_TYPE_ORDER_RANSOM)) {
				opeOrder= new SellOrderDTO();
				// 返回订单信息
				Map<String, String> map = orderBaseQueryBO
						.getRansomSellOrderPrintDetail(orderIdString);
				if (map == null || map.size() == 0) {
					throw new BizServiceException("赎回订单信息查询失败,请检查订单状态");
				}
				opeOrder.setOrderType(orderTypeString);
				opeOrder.setProcessEntityName(map.get("issueName"));
				opeOrder.setFirstEntityName(map.get("custormerName"));
				opeOrder.setInvoiceAddresses(map.get("customerAddress"));
				opeOrder.setCusContactWay(map.get("customerTelephone"));
				opeOrder.setOrderDate(map.get("orderDate"));
				opeOrder.setTotalPrice(map.get("totalPrice"));
				opeOrder.setOrigCardQuantity(map.get("origCardQuantity"));
				opeOrder.setOrigCardTotalAmt(map.get("origCardTotalAmt"));
				opeOrder.setAdditionalFee(map.get("additionalFee"));
				opeOrder.setPackageFee(map.get("packageFee"));
				opeOrder.setContactId(map.get("contactId"));
				opeOrder.setOrderState(map.get("orderState"));
				opeOrder.setOrderId(orderIdString);
				
			} else if (orderTypeString
					.equals(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER)) {
				opeOrder = orderBaseQueryBO
						.getCreditOrderForCertPrint(orderIdString);
			} else {
				opeOrder = orderBaseQueryBO
						.getSellOrderForCertPrint(orderIdString);
			}
			/* 查询订单经办人 */
			ContactDTO tempContactDTO = new ContactDTO();
			tempContactDTO.setContactId(opeOrder.getContactId() == null ? ""
					: opeOrder.getContactId());
			tempContactDTO = contactService.viewContact(tempContactDTO);
			if (tempContactDTO.getPapersNo() == null
					|| tempContactDTO.getPapersNo().equals("")) {
				tempContactDTO.setPapersNo("");
			} else if (tempContactDTO.getPapersNo().length() > 4) {
				tempContactDTO.setPapersNo("**"
						+ tempContactDTO.getPapersNo().substring(2,
								tempContactDTO.getPapersNo().length() - 2)
						+ "**");
			}
			opeOrder.setContactDTO(tempContactDTO);
			opeOrder.setPrintDate(DateUtil.getStringDate());
			if (opeOrder.getOrderType().equals(
					OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER)) {
				if (!(opeOrder.getOrderState().equals(
						OrderConst.ORDER_STATE_WAITTING_CREDIT) || opeOrder
						.getOrderState().equals(
								OrderConst.ORDER_STATE_CREDIT_SUCCESS))) {
					throw new BizServiceException("只能打印审核后的充值订单");
				}
			}
			/* 订单状态为准备完成后的 */
			else if (!(opeOrder.getOrderState().equals(
					OrderConst.ORDER_STATE_ORDER_SEND_CONFIRM)
					|| opeOrder.getOrderState().equals(
							OrderConst.ORDER_STATE_ORDER_WAIT_SEND_CONFIRM)
					|| opeOrder.getOrderState().equals(
							OrderConst.ORDER_STATE_ORDER_SEND) || opeOrder
					.getOrderState().equals(OrderConst.ORDER_STATE_ORDER_STOCK))
					&& !OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(opeOrder
							.getOrderType())) {
				throw new BizServiceException("只能打印准备完成后的客户购卡订单、换卡订单");
			}

			/* 统计购卡总金额 */
			if (orderTypeString
					.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN)
					|| orderTypeString
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN)
					|| orderTypeString
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN)) {
				opeOrder.setNewCardTotalAmt(String.valueOf((Integer
						.parseInt(opeOrder.getFaceValue()) * Integer
						.parseInt(opeOrder.getCardQuantity()))));
				opeOrder.setOrderCardListDTOList(sellOrderCardListDAO
						.selectCardDetailForSignOrderByOrderId(orderIdString));
			}
			/* 换卡订单原卡产品名和新卡产品名 */
			else if (orderTypeString
					.equals(OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD)) {
				/* 原卡产品名称 */
				opeOrder.setOrigProdName(orderBaseQueryBO
						.getOrigCardProdNames(orderIdString));
				opeOrder
						.setOrigCardNoSections(opeOrigCardNoSection(orderIdString));
				/* 新卡产品名称、购卡总额 */
				List<Map<String, String>> tempList = orderBaseQueryBO
						.getOrderListForCertPrint(orderIdString);
				String tempProdName = "";
				String tempFaceValue = "";
				String tempCardAmount = "";
				BigDecimal tempCardTotalAmount = new BigDecimal("0");
				for (Map<String, String> tempMap : tempList) {
					tempProdName += tempMap.get("productName") + "、";
					tempFaceValue = tempMap.get("faceValue");
					tempCardAmount = tempMap.get("cardAmount");
					BigDecimal faceValueDecimal = new BigDecimal(tempFaceValue)
							.divide(new BigDecimal("100"));
					BigDecimal quantityDecimal = new BigDecimal(tempCardAmount);
					tempCardTotalAmount = faceValueDecimal
							.multiply(quantityDecimal);
				}
				opeOrder.setProductName(tempProdName.substring(0, tempProdName
						.length() - 1));
				opeOrder.setNewCardTotalAmt(tempCardTotalAmount.toString());
				opeOrder.setCardNoSections(opeCardNoSection(opeOrder.getOrderId(),opeOrder.getOrderType()));
			} else if (orderTypeString
					.equals(OrderConst.ORDER_TYPE_ORDER_RANSOM)) {
				// 卡号段查询
				opeOrder.setOrigCardNoSections(opeOrigCardNoSection(orderIdString));
				
			}else if (orderTypeString
					.equals(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER)) {
				/* 充值订单 统计订单购卡总金额 */
				opeOrder.setNewCardTotalAmt(orderBaseQueryBO
						.getCreditOrderTotalCreditAmount(orderIdString));
				opeOrder
						.setCardNoSectionDTOs(opeCardNoSectionForCreditOrder(orderIdString));
			} else {
				/* 匿名订单 */
				/* 统计订单购卡总金额 */
				List<SellOrderList> tempList = orderBaseQueryBO
						.getSellOrderListByOrderId(orderIdString);
				BigDecimal tempCardTotalAmount = new BigDecimal("0");
				for (SellOrderList tempMap : tempList) {
					BigDecimal faceValueDecimal = new BigDecimal(tempMap
							.getFaceValue()).divide(new BigDecimal("100"));
					BigDecimal quantityDecimal = new BigDecimal(tempMap
							.getCardAmount());
					tempCardTotalAmount = faceValueDecimal
							.multiply(quantityDecimal);
				}
				opeOrder.setNewCardTotalAmt(tempCardTotalAmount.toString());
				/* 生成连续、断续卡号 */
				opeOrder
						.setCardNoSectionDTOList(opeCardNoSectionForUnsign(tempList));
			}
			opeOrder.setOrderPaymentDTOList(sellOrderPaymentService
					.queryOrderPayments(opeOrder.getOrderId()));
			return opeOrder;
		} catch (BizServiceException bse) {
			throw bse;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new BizServiceException("查询订单信息失败");
		}
	}

	/**
	 * 凭证打印生成连续、断续卡号信息
	 * */
	@SuppressWarnings("unchecked")
	public List<String> opeCardNoSection(String orderId, String orderType)
			throws Exception {
		List<SellOrderCardList> opeList = new ArrayList<SellOrderCardList>();
		/* 排序 */
		if (orderType
				.equals(OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN)
				|| orderType
						.equals(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN)
				|| orderType
						.equals(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN)) {
			SellOrderCardListQueryDTO queryDTO = new SellOrderCardListQueryDTO();
			queryDTO.setOrderId(orderId);
			List<String> oldOrderIdOrderList = (List<String>)baseDAO.queryForList("STOCK_ORDER_CARD_LIST.selectOrigOrderByBuyOrderId", orderId);
            if(oldOrderIdOrderList.size() == 0){
                queryDTO.setOldOrderList(null);
            }
            else{
                queryDTO.setOldOrderList((String[])oldOrderIdOrderList.toArray(new String[oldOrderIdOrderList.size()])); 
            }
			opeList = (List<SellOrderCardList>) baseDAO.queryForList(
					"STOCK_ORDER_CARD_LIST.selectOrderCardListIterator",
					queryDTO);
		} else {
			opeList = orderBaseQueryBO
					.getSellOrderCardListDetailByOrderId(orderId);
		}
		List<String> tempCardNoList = new ArrayList<String>();
		List<String> resultList = new ArrayList<String>();
		String preCardNoString = "";
		String afterCardNoString = "";
		for (SellOrderCardList temp : opeList) {
			afterCardNoString = temp.getCardNo();
			if (tempCardNoList.size() == 0) {
				tempCardNoList.add(afterCardNoString);
			} else {
				preCardNoString = tempCardNoList.get(tempCardNoList.size() - 1);
				BigDecimal preCardNo = new BigDecimal(preCardNoString
						.substring(0, preCardNoString.length() - 1));
				BigDecimal aftCardNO = new BigDecimal(afterCardNoString
						.substring(0, afterCardNoString.length() - 1));
				if (aftCardNO.subtract(preCardNo).equals(new BigDecimal("1"))) {
					tempCardNoList.add(afterCardNoString);
				} else {
					if (tempCardNoList.size() == 1) {
						resultList.add("******"
								+ tempCardNoList.get(0).substring(6,
										tempCardNoList.get(0).length() - 1)
								+ "*");
					} else {
						resultList
								.add("******"
										+ tempCardNoList.get(0)
												.substring(
														6,
														tempCardNoList.get(0)
																.length() - 1)
										+ "*"
										+ "——"
										+ tempCardNoList
												.get(tempCardNoList.size() - 1)
												.substring(
														tempCardNoList
																.get(
																		tempCardNoList
																				.size() - 1)
																.length() - 6,
														tempCardNoList
																.get(
																		tempCardNoList
																				.size() - 1)
																.length() - 1)
										+ "*");
					}
					tempCardNoList = new ArrayList<String>();
					tempCardNoList.add(afterCardNoString);
				}
			}
			if (opeList.indexOf(temp) == opeList.size() - 1) {
				if (tempCardNoList.size() == 1) {
					resultList.add("******"
							+ tempCardNoList.get(0).substring(6,
									tempCardNoList.get(0).length() - 1) + "*");
				} else {
					resultList.add("******"
							+ tempCardNoList.get(0).substring(6,
									tempCardNoList.get(0).length() - 1)
							+ "*"
							+ "——"
							+ tempCardNoList.get(tempCardNoList.size() - 1)
									.substring(
											tempCardNoList.get(
													tempCardNoList.size() - 1)
													.length() - 6,
											tempCardNoList.get(
													tempCardNoList.size() - 1)
													.length() - 1) + "*");
				}
				tempCardNoList = new ArrayList<String>();
			}
		}
		return resultList;

	}

	/**
	 * 凭证打印生成连续、断续原卡卡号信息
	 * */
	public List<String> opeOrigCardNoSection(String orderId) throws Exception {

		/* 排序 */
		List<SellOrderOrigCardList> opeList = orderBaseQueryBO
				.getSellOrderOrigCardList(orderId);
		List<String> tempCardNoList = new ArrayList<String>();
		List<String> resultList = new ArrayList<String>();
		String preCardNoString = "";
		String afterCardNoString = "";
		for (SellOrderOrigCardList temp : opeList) {
			afterCardNoString = temp.getCardNo();
			if (tempCardNoList.size() == 0) {
				tempCardNoList.add(afterCardNoString);
			} else {
				preCardNoString = tempCardNoList.get(tempCardNoList.size() - 1);
				BigDecimal preCardNo = new BigDecimal(preCardNoString
						.substring(0, preCardNoString.length() - 1));
				BigDecimal aftCardNO = new BigDecimal(afterCardNoString
						.substring(0, afterCardNoString.length() - 1));
				if (aftCardNO.subtract(preCardNo).equals(new BigDecimal("1"))) {
					tempCardNoList.add(afterCardNoString);
				} else {
					if (tempCardNoList.size() == 1) {
						resultList.add("******"+tempCardNoList.get(0).substring(6,tempCardNoList.get(0).length()-1)+"*");
					} else {
						resultList.add("******"+tempCardNoList.get(0).substring(6,tempCardNoList.get(0).length()-1)+"*"+"——"+tempCardNoList.get(tempCardNoList.size()-1).substring(tempCardNoList.get(tempCardNoList.size()-1).length()-6, tempCardNoList.get(tempCardNoList.size()-1).length()-1)+"*");
					}
					tempCardNoList = new ArrayList<String>();
					tempCardNoList.add(afterCardNoString);
				}
			}
			if (opeList.indexOf(temp) == opeList.size() - 1) {
				if (tempCardNoList.size() == 1) {
					resultList.add("******"+tempCardNoList.get(0).substring(6,tempCardNoList.get(0).length()-1)+"*");
				} else {
					resultList.add("******"+tempCardNoList.get(0).substring(6,tempCardNoList.get(0).length()-1)+"*"+"——"+tempCardNoList.get(tempCardNoList.size()-1).substring(tempCardNoList.get(tempCardNoList.size()-1).length()-6, tempCardNoList.get(tempCardNoList.size()-1).length()-1)+"*");
				}
				tempCardNoList = new ArrayList<String>();
			}
		}
		return resultList;

	}

	/**
	 * 凭证打印生成连续、断续卡号信息
	 * */
	public List<CardNoSectionDTO> opeCardNoSectionForUnsign(
			List<SellOrderList> orderLists) throws Exception {
		List<CardNoSectionDTO> sectionList = new ArrayList<CardNoSectionDTO>();
		CardNoSectionDTO sectionDTO = null;
		for (SellOrderList tempOrderList : orderLists) {
			sectionDTO = new CardNoSectionDTO();

			/* 赋值 面额 */
			sectionDTO
					.setFaceValue(new BigDecimal(tempOrderList.getFaceValue())
							.divide(new BigDecimal("100")).toString());

			/* 赋值 卡数量 */
			sectionDTO.setCardQuantity(tempOrderList.getCardAmount());

			/* 赋值 失效日期 */
			sectionDTO.setValidPeriod(DateUtil.formatStringDate(tempOrderList
					.getValidityPeriod()));

			/* 检索当前订单明细下的卡信息 */
			SellOrderCardListExample example = new SellOrderCardListExample();
			example.createCriteria().andOrderIdEqualTo(
					tempOrderList.getOrderId()).andOrderListIdEqualTo(
					tempOrderList.getOrderListId()).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL);
			List<SellOrderCardList> opeList = sellOrderCardListDAO
					.selectByExample(example);

			List<String> tempCardNoList = new ArrayList<String>();
			List<String> resultList = new ArrayList<String>();
			String preCardNoString = "";
			String afterCardNoString = "";
			for (SellOrderCardList temp : opeList) {
				afterCardNoString = temp.getCardNo();
				if (tempCardNoList.size() == 0) {
					tempCardNoList.add(afterCardNoString);
				} else {
					preCardNoString = tempCardNoList
							.get(tempCardNoList.size() - 1);
					BigDecimal preCardNo = new BigDecimal(preCardNoString
							.substring(0, preCardNoString.length() - 1));
					BigDecimal aftCardNO = new BigDecimal(afterCardNoString
							.substring(0, afterCardNoString.length() - 1));
					if (aftCardNO.subtract(preCardNo).equals(
							new BigDecimal("1"))) {
						tempCardNoList.add(afterCardNoString);
					} else {
						if (tempCardNoList.size() == 1) {
							resultList.add("******"
									+ tempCardNoList.get(0).substring(6,
											tempCardNoList.get(0).length() - 1)
									+ "*");
						} else {
							resultList
									.add("******"
											+ tempCardNoList.get(0).substring(
													6,
													tempCardNoList.get(0)
															.length() - 1)
											+ "*"
											+ "——"
											+ tempCardNoList
													.get(
															tempCardNoList
																	.size() - 1)
													.substring(
															tempCardNoList
																	.get(
																			tempCardNoList
																					.size() - 1)
																	.length() - 6,
															tempCardNoList
																	.get(
																			tempCardNoList
																					.size() - 1)
																	.length() - 1)
											+ "*");
						}
						tempCardNoList = new ArrayList<String>();
						tempCardNoList.add(afterCardNoString);
					}
				}
				if (opeList.indexOf(temp) == opeList.size() - 1) {
					if (tempCardNoList.size() == 1) {
						resultList.add("******"
								+ tempCardNoList.get(0).substring(6,
										tempCardNoList.get(0).length() - 1)
								+ "*");
					} else {
						resultList
								.add("******"
										+ tempCardNoList.get(0)
												.substring(
														6,
														tempCardNoList.get(0)
																.length() - 1)
										+ "*"
										+ "——"
										+ tempCardNoList
												.get(tempCardNoList.size() - 1)
												.substring(
														tempCardNoList
																.get(
																		tempCardNoList
																				.size() - 1)
																.length() - 6,
														tempCardNoList
																.get(
																		tempCardNoList
																				.size() - 1)
																.length() - 1)
										+ "*");
					}
					tempCardNoList = new ArrayList<String>();
				}

			}
			sectionDTO.setCardNoSection(resultList);
			resultList = new ArrayList<String>();
			sectionList.add(sectionDTO);
		}
		return sectionList;

	}

	/**
	 * 凭证打印充值订单生成连续、断续卡号信息
	 * */
	public List<CardNoSectionDTO> opeCardNoSectionForCreditOrder(String orderId)
			throws Exception {
		List<CardNoSectionDTO> cardNoSectionDTOs = new ArrayList<CardNoSectionDTO>();
		List<CardNoSectionDTO> dTOs = orderBaseQueryBO
				.getCreditAmountSection(orderId);
		if (null != dTOs && dTOs.size() > 0) {
			for (CardNoSectionDTO dto : dTOs) {
				CardNoSectionDTO cardNoSectionDTO = new CardNoSectionDTO();
				cardNoSectionDTO.setFaceValue(Amount.getReallyAmount(dto
						.getFaceValue()));
				cardNoSectionDTO.setCardQuantity(dto.getCardQuantity());
				/* 根据充值金额 分段卡号 */
				SellOrderCardListExample example = new SellOrderCardListExample();
				example
						.createCriteria()
						.andOrderIdEqualTo(orderId)
						.andCreditAmountEqualTo(dto.getFaceValue())
						.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
				List<SellOrderCardList> opeList = sellOrderCardListDAO
						.selectByExample(example);

				List<String> tempCardNoList = new ArrayList<String>();
				List<String> resultList = new ArrayList<String>();
				String preCardNoString = "";
				String afterCardNoString = "";
				for (SellOrderCardList temp : opeList) {
					afterCardNoString = temp.getCardNo();
					if (tempCardNoList.size() == 0) {
						tempCardNoList.add(afterCardNoString);
					} else {
						preCardNoString = tempCardNoList.get(tempCardNoList
								.size() - 1);
						BigDecimal preCardNo = new BigDecimal(preCardNoString
								.substring(0, preCardNoString.length() - 1));
						BigDecimal aftCardNO = new BigDecimal(afterCardNoString
								.substring(0, afterCardNoString.length() - 1));
						if (aftCardNO.subtract(preCardNo).equals(
								new BigDecimal("1"))) {
							tempCardNoList.add(afterCardNoString);
						} else {
							if (tempCardNoList.size() == 1) {
								resultList.add("******"
										+ tempCardNoList.get(0)
												.substring(
														6,
														tempCardNoList.get(0)
																.length() - 1)
										+ "*");
							} else {
								resultList
										.add("******"
												+ tempCardNoList
														.get(0)
														.substring(
																6,
																tempCardNoList
																		.get(0)
																		.length() - 1)
												+ "*"
												+ "——"
												+ tempCardNoList
														.get(
																tempCardNoList
																		.size() - 1)
														.substring(
																tempCardNoList
																		.get(
																				tempCardNoList
																						.size() - 1)
																		.length() - 6,
																tempCardNoList
																		.get(
																				tempCardNoList
																						.size() - 1)
																		.length() - 1)
												+ "*");
							}
							tempCardNoList = new ArrayList<String>();
							tempCardNoList.add(afterCardNoString);
						}
					}
					if (opeList.indexOf(temp) == opeList.size() - 1) {
						if (tempCardNoList.size() == 1) {
							resultList.add("******"
									+ tempCardNoList.get(0).substring(6,
											tempCardNoList.get(0).length() - 1)
									+ "*");
						} else {
							resultList
									.add("******"
											+ tempCardNoList.get(0).substring(
													6,
													tempCardNoList.get(0)
															.length() - 1)
											+ "*"
											+ "——"
											+ tempCardNoList
													.get(
															tempCardNoList
																	.size() - 1)
													.substring(
															tempCardNoList
																	.get(
																			tempCardNoList
																					.size() - 1)
																	.length() - 6,
															tempCardNoList
																	.get(
																			tempCardNoList
																					.size() - 1)
																	.length() - 1)
											+ "*");
						}
						tempCardNoList = new ArrayList<String>();
					}

				}
				cardNoSectionDTO.setCardNoSection(resultList);
				resultList = new ArrayList<String>();
				cardNoSectionDTOs.add(cardNoSectionDTO);
			}
		}
		return cardNoSectionDTOs;
	}

	/* (non-Javadoc)
     * @see com.huateng.univer.order.business.query.SellOrderQuery#accept()
     */
    @Override
    public void accept(AcceptOrderDTO acceptOrderDTO) throws Exception {
       orderBaseQueryBO.accept(acceptOrderDTO);
    }

    /* (non-Javadoc)
     * @see com.huateng.univer.order.business.query.SellOrderQuery#delete()
     */
    @Override
    public void delete(AcceptOrderDTO acceptOrderDTO) throws Exception {
          orderBaseQueryBO.delete(acceptOrderDTO);
    }

    /* (non-Javadoc)
     * @see com.huateng.univer.order.business.query.SellOrderQuery#deleteAll()
     */
    @Override
    public void deleteAll(AcceptOrderDTO acceptOrderDTO) throws Exception {
        orderBaseQueryBO.deleteAll(acceptOrderDTO);
    }

    public SellerProductContractService getSellerProductContractService() {
		return sellerProductContractService;
	}

	public void setSellerProductContractService(
			SellerProductContractService sellerProductContractService) {
		this.sellerProductContractService = sellerProductContractService;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	public SellOrderCardListDAO getSellOrderCardListDAO() {
		return sellOrderCardListDAO;
	}

	public void setSellOrderCardListDAO(
			SellOrderCardListDAO sellOrderCardListDAO) {
		this.sellOrderCardListDAO = sellOrderCardListDAO;
	}

}
