package com.huateng.univer.order.business.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.stock.dto.EntityStockDTO;
import com.allinfinance.univer.issuer.dto.product.ProdFaceValueDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderQueryDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.EntityDTO;
import com.allinfinance.univer.seller.order.dto.SellBuyOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerAcctypeContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.allinfinance.univer.system.sysparam.dto.SystemParameterDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.DictInfoConstants;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.BankDAO;
import com.huateng.framework.ibatis.dao.IssuerDAO;
import com.huateng.framework.ibatis.dao.ProdFaceValueDAO;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderListDAO;
import com.huateng.framework.ibatis.dao.SellOrderOrigCardListDAO;
import com.huateng.framework.ibatis.dao.SellerDAO;
import com.huateng.framework.ibatis.model.ProdFaceValue;
import com.huateng.framework.ibatis.model.ProdFaceValueExample;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.ibatis.model.SellOrderListExample;
import com.huateng.framework.ibatis.model.SellOrderOrigCardList;
import com.huateng.framework.ibatis.model.SellOrderOrigCardListExample;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.StringUtil;
import com.huateng.hstserver.constants.RspCodeMap;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.gatewayService.Java2TXNBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
import com.huateng.univer.entitybaseinfo.bank.biz.service.BankService;
import com.huateng.univer.entitybaseinfo.department.biz.service.DepartmentService;
import com.huateng.univer.issuer.order.biz.bo.StockOrderCommonService;
import com.huateng.univer.issuer.product.dao.ProdAccLayPackServiceDAO;
import com.huateng.univer.issuer.product.service.ProductService;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.order.business.bo.OrderCountTotalPrice;
import com.huateng.univer.order.business.service.OrderService;
import com.huateng.univer.seller.cardholder.biz.service.CardholderService;
import com.huateng.univer.seller.customer.biz.service.CustomerService;
import com.huateng.univer.seller.seller.biz.service.SellerService;
import com.huateng.univer.system.sysparam.biz.service.SystemParameterService;

public class OrderServiceImpl implements OrderService {

	private Logger logger = Logger.getLogger(OrderServiceImpl.class);

	private OrderBaseQueryBO orderBaseQueryBO;

	private StockOrderCommonService stockOrderCommonService;

	private CustomerService customerService;

	private SellOrderDAO sellOrderDAO;

	private CommonsDAO commonsDAO;

	private ProductService productService;

	private DepartmentService departmentService;

	protected BaseDAO baseOrderDAO;

	private SellOrderListDAO sellOrderListDAO;

	private OrderCountTotalPrice orderCountTotalPrice;

	private ProdAccLayPackServiceDAO prodAccLayPackServiceDAO;

	private SellerService sellerService;

	private PageQueryDAO pageQueryDAO;

	private OrderBO orderBO;

	private SellerDAO sellerDAO;

	private IssuerDAO issuerDAO;

	private SystemParameterService systemParameterService;

	private BankService bankService;

	private BankDAO bankDAO;

	private SellOrderCardListDAO sellOrderCardListDAO;
	private SellOrderCardList sellOrderCardList = new SellOrderCardList();
	private SellOrderCardListDTO sellOrderCardListDTO = new SellOrderCardListDTO();
	private SellOrderOrigCardListDAO  sellOrderOrigCardListDAO;
	
	private Java2TXNBusinessServiceImpl java2TXNBusinessService;
	
	private Java2ACCBusinessServiceImpl java2ACCBusinessService;
	private CardholderService cardholderService;
	
	private ProdFaceValueDAO ProdFaceValueDAO;

	public Java2ACCBusinessServiceImpl getJava2ACCBusinessService() {
		return java2ACCBusinessService;
	}

	public void setJava2ACCBusinessService(
			Java2ACCBusinessServiceImpl java2accBusinessService) {
		java2ACCBusinessService = java2accBusinessService;
	}

	public Java2TXNBusinessServiceImpl getJava2TXNBusinessService() {
		return java2TXNBusinessService;
	}

	public void setJava2TXNBusinessService(
			Java2TXNBusinessServiceImpl java2txnBusinessService) {
		java2TXNBusinessService = java2txnBusinessService;
	}

	public BankDAO getBankDAO() {
		return bankDAO;
	}

	public void setBankDAO(BankDAO bankDAO) {
		this.bankDAO = bankDAO;
	}

	public BankService getBankService() {
		return bankService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public SystemParameterService getSystemParameterService() {
		return systemParameterService;
	}

	public void setSystemParameterService(
			SystemParameterService systemParameterService) {
		this.systemParameterService = systemParameterService;
	}

	public StockOrderCommonService getStockOrderCommonService() {
		return stockOrderCommonService;
	}

	public void setStockOrderCommonService(
			StockOrderCommonService stockOrderCommonService) {
		this.stockOrderCommonService = stockOrderCommonService;
	}

	public SellOrderCardListDAO getSellOrderCardListDAO() {
		return sellOrderCardListDAO;
	}

	public void setSellOrderCardListDAO(
			SellOrderCardListDAO sellOrderCardListDAO) {
		this.sellOrderCardListDAO = sellOrderCardListDAO;
	}

	public SellOrderCardList getSellOrderCardList() {
		return sellOrderCardList;
	}
	
	

	public ProdFaceValueDAO getProdFaceValueDAO() {
		return ProdFaceValueDAO;
	}

	public void setProdFaceValueDAO(ProdFaceValueDAO prodFaceValueDAO) {
		ProdFaceValueDAO = prodFaceValueDAO;
	}

	public void setSellOrderCardList(SellOrderCardList sellOrderCardList) {
		this.sellOrderCardList = sellOrderCardList;
	}

	public SellOrderCardListDTO getSellOrderCardListDTO() {
		return sellOrderCardListDTO;
	}

	public void setSellOrderCardListDTO(
			SellOrderCardListDTO sellOrderCardListDTO) {
		this.sellOrderCardListDTO = sellOrderCardListDTO;
	}

	@Override
	public PageDataDTO queryOrder(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException {
		try {
			return orderBaseQueryBO.querySellOrder(sellOrderQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单信息异常!");
		}
	}

	@Override
	public SellOrderInputDTO initAdd(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException {
		try {

			SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
			/**
			 * 如果是充值订单直接进入充值订单初始化函数不执行以下操作
			 */
			if (OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER
					.equals(sellOrderDTO.getOrderType())) {
				return initCreditOrder(sellOrderInputDTO);
			}
			/**
			 * 如果是销售记名卡订单
			 */
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrderDTO.getOrderType())) {
				sellOrderInputDTO.setSaleUserList(orderBaseQueryBO
						.getSaleUserList(sellOrderInputDTO));
				/* 获取订单处理方银行信息 3:营销机构 */
				sellOrderDTO.setLstBankDTO(bankService.inquery(sellOrderDTO
						.getProcessEntityId()));
				if (StringUtil.isNotEmpty(sellOrderDTO.getFirstEntityId())) {
					CustomerDTO customerDTO = new CustomerDTO();
					customerDTO.setEntityId(sellOrderDTO.getFirstEntityId());
					customerDTO.setFatherEntityId(sellOrderDTO
							.getProcessEntityId());
					customerDTO = customerService.viewCustomer(customerDTO);
					sellOrderInputDTO.setCustomerDTO(customerDTO);
					sellOrderDTO.setDefaultEntityId(sellOrderInputDTO
							.getDefaultEntityId());
					List<ProductDTO> productDTOs_need = orderBaseQueryBO
							.getProductByContractAndOrderType(sellOrderDTO,
									DataBaseConstant.SELL_CONTRACT_CUSTOMER);
					if (null == productDTOs_need) {
						return sellOrderInputDTO;
					}

					/***
			 *
			 */
					List<ProductDTO> productDTOs = productDTOs_need;
					/*
					 * 注销必须为记名卡的限制
					 */
					// for (ProductDTO productDTO_temp:productDTOs_need){
					//									
					// if(DictInfoConstants.SIGN_ORDER_MUST.equals(productDTO_temp.getOnymousStat())){
					// productDTOs.add(productDTO_temp);
					// }
					// }

					/***
					 * 由于产品的面额并没有除以100在这里将面额值进行转换
					 */
					if (productDTOs.size() > 0) {
						for (ProductDTO temp : productDTOs) {
							List<ProdFaceValueDTO> prodFaceValueList = temp
									.getProdFaceValueDTO();
							for (ProdFaceValueDTO faceValueDTO : prodFaceValueList) {
								faceValueDTO.setFaceValue(Amount
										.getReallyAmount(faceValueDTO
												.getFaceValue()));
							}

						}
					}
					ProductDTO productDTO_choose = new ProductDTO();
					sellOrderInputDTO.setProductDTOs(productDTOs);
					if (productDTOs.size() > 0) {
						// if (StringUtil.isEmpty(sellOrderDTO.getProductId()))
						// {
						productDTO_choose = productDTOs.get(0);

						// } else {
						for (ProductDTO productDTO : productDTOs) {
							if (productDTO.getProductId().equals(
									sellOrderDTO.getProductId())) {
								productDTO_choose = productDTO;
								break;
							}
						}
						// }
					}
					sellOrderInputDTO.setProductDTO(productDTO_choose);

					/***
					 * 设置卡有效日期，当前时间加上卡有效日期
					 */
					if (productDTO_choose.getValidityPeriod() != null) {
						Date cardValidityPeriod = DateUtil.countCardValidate(
								new Date(), Integer.parseInt(productDTO_choose
										.getValidityPeriod()));
						sellOrderDTO.setValidityPeriod(DateUtil
								.dateToSting_(cardValidityPeriod));
					}
					sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
				}
			}
			/**
			 * 如果是销售匿名卡订单
			 */
			else if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(sellOrderDTO
							.getOrderType())) {
				sellOrderInputDTO.setSaleUserList(orderBaseQueryBO
						.getSaleUserList(sellOrderInputDTO));
				/* 获取订单处理方银行信息 3:营销机构 */
				sellOrderDTO.setLstBankDTO(bankService.inquery(sellOrderDTO
						.getProcessEntityId()));
				if (StringUtil.isNotEmpty(sellOrderDTO.getFirstEntityId())) {
					CustomerDTO customerDTO = new CustomerDTO();
					customerDTO.setEntityId(sellOrderDTO.getFirstEntityId());
					customerDTO.setFatherEntityId(sellOrderDTO
							.getProcessEntityId());
					customerDTO = customerService.viewCustomer(customerDTO);
					sellOrderInputDTO.setCustomerDTO(customerDTO);
					sellOrderDTO.setDefaultEntityId(sellOrderInputDTO
							.getDefaultEntityId());
					List<ProductDTO> productDTOs_need = orderBaseQueryBO
							.getProductByContractAndOrderType(sellOrderDTO,
									DataBaseConstant.SELL_CONTRACT_CUSTOMER);
					if (null == productDTOs_need) {
						return sellOrderInputDTO;
					}

					List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
					/***
					 * 如果产品是记名则删除
					 */
					for (ProductDTO productDTO_temp : productDTOs_need) {
						if (!DictInfoConstants.SIGN_ORDER_MUST
								.equals(productDTO_temp.getOnymousStat())) {
							productDTOs.add(productDTO_temp);
						}
					}

					ProductDTO productDTO_choose = new ProductDTO();
					sellOrderInputDTO.setProductDTOs(productDTOs);
					if (productDTOs.size() > 0) {
						if (StringUtil.isEmpty(sellOrderDTO.getProductId())) {
							//去除记名库存卡
//							List<ProductDTO> productDTOsByNoName = new ArrayList<ProductDTO>();
//							for(ProductDTO p :productDTOs){
//								if(!"3".equals(p.getOnymousStat())){
//									productDTOsByNoName.add(p);
//								}
//							}
//							for(ProductDTO productDTOByNoName : productDTOsByNoName){
//								for (ProductDTO productDTO : productDTOs) {
//									if (productDTO.getProductId().equals(
//											productDTOByNoName.getProductId())) {
//										productDTO_choose = productDTO;
//										break;
//									}
//								}
//							}
							productDTO_choose = productDTOs.get(0);
						} else {
							for (ProductDTO productDTO : productDTOs) {
								if (productDTO.getProductId().equals(
										sellOrderDTO.getProductId())) {
									productDTO_choose = productDTO;
									break;
								}
							}
						}
					}
					sellOrderInputDTO.setProductDTO(productDTO_choose);
					sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
				}
			}
			return sellOrderInputDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("初始化订单信息失败");
		}
	}

	@Override
	public SellOrderInputDTO orderInsert(SellOrderDTO sellOrderDTO)
	throws BizServiceException {
		return insertSerllOrder(sellOrderDTO);
		
	}
	
	
	// 添加订单
	@Override
	public SellOrderInputDTO insertSerllOrder(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			SellOrder sellOrder = new SellOrder();
			ReflectionUtil.copyProperties(sellOrderDTO, sellOrder);
			/**
			 *获取订单ID
			 */
			sellOrder.setOrderId(commonsDAO
					.getNextValueOfSequence("TB_SELL_ORDER"));
			/***
			 *设置相关信息
			 */
			/***
			 * 将所有的金额字段在这里*100
			 */
			// 包装费
			if (StringUtil.isNotEmpty(sellOrder.getPackageFee())) {
				sellOrder.setPackageFee(Amount.getDataBaseAmount(sellOrder
						.getPackageFee()));
			}
			// 面额
			if (StringUtil.isNotEmpty(sellOrder.getFaceValue())) {
				sellOrder.setFaceValue(Amount.getDataBaseAmount(sellOrder
						.getFaceValue()));
			}
			// 年费
			if (StringUtil.isNotEmpty(sellOrder.getAnnualFee())) {
				sellOrder.setAnnualFee(Amount.getDataBaseAmount(sellOrder
						.getAnnualFee()));
			}
			// 卡费
			if (StringUtil.isNotEmpty(sellOrder.getCardIssueFee())) {
				sellOrder.setCardIssueFee(Amount.getDataBaseAmount(sellOrder
						.getCardIssueFee()));
			}
			// 快递费
			if (StringUtil.isNotEmpty(sellOrder.getDeliveryFee())) {
				sellOrder.setDeliveryFee(Amount.getDataBaseAmount(sellOrder
						.getDeliveryFee()));
			}
			// 附加费
			if (StringUtil.isNotEmpty(sellOrder.getAdditionalFee())) {
				sellOrder.setAdditionalFee(Amount.getDataBaseAmount(sellOrder
						.getAdditionalFee()));
			}
			// 折扣费
			if (StringUtil.isNotEmpty(sellOrder.getDiscountFee())) {
				sellOrder.setDiscountFee(Amount.getDataBaseAmount(sellOrder
						.getDiscountFee()));
			}
			sellOrder.setOrderDate(DateUtil.getFormatTime(sellOrder
					.getOrderDate()));
			//发票日期
			if (StringUtil.isNotEmpty(sellOrder.getInvoiceDate())) {
				sellOrder.setInvoiceDate(DateUtil.getFormatTime(sellOrder
						.getInvoiceDate()));
			}
			if (StringUtil.isNotEmpty(sellOrder.getValidityPeriod())) {
				sellOrder.setValidityPeriod(DateUtil.getFormatTime(sellOrder
						.getValidityPeriod()));
			}

			// 预计充值日期
			if (StringUtil.isNotEmpty(sellOrder.getForecastCreditDate())) {
				sellOrder.setForecastCreditDate(DateUtil
						.getFormatTime(sellOrder.getForecastCreditDate()));
			}

			sellOrder.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			sellOrder.setCreateTime(DateUtil.getCurrentTime());
			sellOrder.setCreateUser(sellOrderDTO.getLoginUserId());
			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());

			sellOrderDAO.insert(sellOrder);
			// 添加订单流程信息
			sellOrderDTO.setOrderId(sellOrder.getOrderId());
			stockOrderCommonService.addNewOrderFlow(sellOrderDTO,
					OrderConst.ORDER_FLOW_INPUT,
					OrderConst.ORDER_FLOW_OPRATION_ADD);

			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {

				orderCountTotalPrice
						.countTotalPriceForSellOrderUnsign(sellOrder
								.getOrderId());

			} else if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrderDTO.getOrderType())) {

				orderCountTotalPrice.countTotalPriceForSellOrdersign(sellOrder
						.getOrderId());

			} else if (OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER
					.equals(sellOrder.getOrderType())) {
				orderCountTotalPrice.countTotalPriceForCreditOrder(sellOrder
						.getOrderId());
			} else if (OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD.equals(sellOrder
					.getOrderType())) {
				orderCountTotalPrice
						.countTotalPriceForChangeCardOrder(sellOrder
								.getOrderId());
			} else if (OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(sellOrder
					.getOrderType())) {
				orderCountTotalPrice.countPriceForRansomOrder(sellOrder
						.getOrderId());
			}
			SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();
			sellOrderDTO.setOrderId(sellOrder.getOrderId());

			sellOrderInputDTO.getSellOrderCardListQueryDTO().setOrderId(
					sellOrderDTO.getOrderId());
			sellOrderInputDTO.getSellOrderListQueryDTO().setOrderId(
					sellOrderDTO.getOrderId());
			sellOrderInputDTO.getSellOrderCardListQueryDTO().setOrderType(
					sellOrderDTO.getOrderType());
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);

			sellOrderInputDTO.setDefaultEntityId(sellOrderDTO
					.getDefaultEntityId());
			if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {
				return editBuyOrderDTO(sellOrderInputDTO);
			} else if (OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD
					.equals(sellOrderDTO.getOrderType())) {
				sellOrderInputDTO.getSellOrderOrigCardListQueryDTO()
						.setOrderId(sellOrderDTO.getOrderId());
				return editChangeCardOrderDTO(sellOrderInputDTO);
			} else if (OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(sellOrderDTO
					.getOrderType())) {
				sellOrderInputDTO.getSellOrderOrigCardListQueryDTO()
						.setOrderId(sellOrderDTO.getOrderId());
				return updateRansomOrderDTO(sellOrderInputDTO);
			}

			return editSellOrderDTO(sellOrderInputDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("新增销售订单失败");
		}
	}

	@Override
	public SellOrderInputDTO getCardholderList(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException {
		try {
			SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();

			CustomerDTO customerDTO = new CustomerDTO();
			/***
			 * 获取客户下部门
			 */
			customerDTO.setDepartmentList(departmentService
					.inquery(sellOrderDTO.getFirstEntityId()));
			/**
			 * 获取客户下的持卡人
			 * 
			 */
			sellOrderInputDTO.getCardholderQueryDTO().setDefaultEntityId(
					sellOrderDTO.getFirstEntityId());
			sellOrderInputDTO.setCardholderList(orderBaseQueryBO
					.queryCardholderlist(sellOrderInputDTO
							.getCardholderQueryDTO()));

			sellOrderInputDTO.setCustomerDTO(customerDTO);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询持卡人信息失败");
		}
		return sellOrderInputDTO;
	}

	@Override
	public SellOrderInputDTO insertCardholderList(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException {
		try {

			// 判断订单最大卡数量是否超限。
			String exitCardHolderString = orderBaseQueryBO
					.getSellOrderCardListByOrderId(sellOrderInputDTO
							.getSellOrderDTO().getOrderId());
			Integer cardHolderCount = Integer.parseInt(exitCardHolderString)
					+ sellOrderInputDTO.getOrderListStr().length;
			/* 取出系统既定的订单卡数量上限 */
            SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
            systemParameterDTO.setParameterCode(SystemInfoConstants.ORDER_CARD_MAXIMUM);
            Integer maximum = Integer.parseInt(systemParameterService.viewSystemParamter(
                            systemParameterDTO).getParameterValue());
			if (cardHolderCount > maximum) {
			    logger.error("订单持卡人数量不能大于系统既定的最大值:" + maximum);
				throw new BizServiceException("订单持卡人数量不能大于系统既定的最大值:"
						+ maximum);
			}
			List<SellOrderCardList> sellOrderCardList_list = new ArrayList<SellOrderCardList>();
			for (String cardholderId : sellOrderInputDTO.getOrderListStr()) {

				CardholderDTO cardholderDTO = orderBaseQueryBO
						.getCardholderById(cardholderId);
				SellOrderCardList sellOrderCardList = new SellOrderCardList();
				sellOrderCardList
						.setOrderCardListId(commonsDAO
								.getNextValueOfSequenceBySequence("SEQ_SELL_ORDER_CARD_LIST"));
				sellOrderCardList.setCardholderId(cardholderDTO
						.getCardholderId());
				sellOrderCardList.setOrderId(sellOrderInputDTO
						.getSellOrderDTO().getOrderId());
				sellOrderCardList.setLastName(cardholderDTO.getLastName());
				sellOrderCardList.setFirstName(cardholderDTO.getFirstName());
				sellOrderCardList.setExternalId(cardholderDTO.getExternalId());
				// sellOrderCardList.setExternalId(cardholderDTO.getEntityId());
				sellOrderCardList.setCreateTime(DateUtil.getCurrentTime());
				sellOrderCardList.setCreateUser(sellOrderInputDTO
						.getLoginUserId());
				sellOrderCardList.setModifyTime(DateUtil.getCurrentTime());
				sellOrderCardList.setModifyUser(sellOrderInputDTO
						.getLoginUserId());
				sellOrderCardList
						.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
				sellOrderCardList_list.add(sellOrderCardList);
			}
			commonsDAO.batchInsert(
					"TB_SELL_ORDER_CARD_LIST.abatorgenerated_insert",
					sellOrderCardList_list);
			orderCountTotalPrice
					.countTotalPriceForSellOrdersign(sellOrderInputDTO
							.getSellOrderDTO().getOrderId());
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("新增订单明细失败");
		}
		return sellOrderInputDTO;
	}

	@Override
	public SellOrderInputDTO editSellOrderDTO(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException {
		SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();

		try {
			sellOrderInputDTO.setSaleUserList(orderBaseQueryBO
					.getSaleUserList(sellOrderInputDTO));

			sellOrderDTO = orderBaseQueryBO.viewSellOrderDTO(sellOrderDTO);

			sellOrderDTO.setOldPaymentTerm(sellOrderDTO.getPaymentTerm());

			CustomerDTO customerDTO = sellOrderInputDTO.getCustomerDTO();

			customerDTO.setEntityId(sellOrderDTO.getFirstEntityId());

			customerDTO.setFatherEntityId(sellOrderDTO.getProcessEntityId());

			customerDTO = customerService.viewCustomer(customerDTO);

			sellOrderInputDTO.setCustomerDTO(customerDTO);

			ProductDTO productDTO = sellOrderInputDTO.getProductDTO();

			productDTO.setProductId(sellOrderDTO.getProductId());

			productDTO = productService.viewProduct(productDTO);

			sellOrderInputDTO.setProductDTO(productDTO);

			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrderDTO.getOrderType())) {
				/**
				 * 获取订单卡明细
				 */
				sellOrderDTO.setOrderCardList(orderBaseQueryBO
						.queryCardListByOrderbyId(sellOrderInputDTO
								.getSellOrderCardListQueryDTO()));
			} else if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrderDTO.getOrderType())

					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {
				/***
				 * 获取订单明细
				 */
				sellOrderDTO.setOrderList(orderBaseQueryBO
						.queryOrderListByOrderbyId(sellOrderInputDTO
								.getSellOrderListQueryDTO()));
			} else if (OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD
					.equals(sellOrderDTO.getOrderType())) {

			}
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取订单信息失败!");
		}
		return sellOrderInputDTO;
	}

	// 检查导入持卡人信息是否正确
	@Override
	public String checkCreditCardholderCardList(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException {
		String result = "";
		try {
			SellOrderCardListDTO card = orderBaseQueryBO
					.getCardImage(sellOrderInputDTO
							.getSellOrderCardListQueryDTO().getCardNo());
			if (null == card ) {
				result = "卡号："
						+ sellOrderInputDTO.getSellOrderCardListQueryDTO()
								.getCardNo() + "有误！";
				return result;
			}
			if (!sellOrderInputDTO.getProductDTO().getProductId().equals(
					card.getProductId())) {
				result = "卡号："
						+ sellOrderInputDTO.getSellOrderCardListQueryDTO()
								.getCardNo() + " 不是"
						+ sellOrderInputDTO.getProductDTO().getProductName()
						+ "产品下的卡！";
				return result;
			}
			if (null != sellOrderInputDTO.getSellOrderCardListQueryDTO()
					.getCardholderId()
					&& !"".equals(sellOrderInputDTO
							.getSellOrderCardListQueryDTO().getCardholderId())) {
				if (!card.getCardholderId().equals(
						sellOrderInputDTO.getSellOrderCardListQueryDTO()
								.getCardholderId())) {
					result = "卡号："
							+ sellOrderInputDTO.getSellOrderCardListQueryDTO()
									.getCardNo() + "与持卡人号不匹配！";
					return result;
				}
			}
			if (null != sellOrderInputDTO.getSellOrderCardListQueryDTO()
					.getFirstName()
					&& !"".equals(sellOrderInputDTO
							.getSellOrderCardListQueryDTO().getFirstName())) {
				if (!card.getFirstName().equals(
						sellOrderInputDTO.getSellOrderCardListQueryDTO()
								.getFirstName())) {
					result = "卡号："
							+ sellOrderInputDTO.getSellOrderCardListQueryDTO()
									.getCardNo() + "与持卡人名称不匹配！";
					return result;
				}
			}
			if (null != sellOrderInputDTO.getSellOrderCardListQueryDTO()
					.getExternalId()
					&& !"".equals(sellOrderInputDTO
							.getSellOrderCardListQueryDTO().getExternalId())) {
				if (null != card.getExternalId()
						&& !"".equals(card.getExternalId())) {
					if (!card.getExternalId().equals(
							sellOrderInputDTO.getSellOrderCardListQueryDTO()
									.getExternalId())) {
						result = "卡号："
								+ sellOrderInputDTO
										.getSellOrderCardListQueryDTO()
										.getCardNo() + "与持卡人工号不匹配！";
						return result;
					}
				}
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("检查导入文件失败");
		}
		return result;
	}

	@Override
	public void deleteOrderList(SellOrderListDTO sellOrderListDTO)
			throws BizServiceException {
		try {
			deleteSellOrderCardList(sellOrderListDTO);
			orderCountTotalPrice
					.countTotalPriceForSellOrdersign(sellOrderListDTO
							.getOrderId());
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除订单明细信息失败!");
		}
	}

	@Override
	public SellOrderInputDTO getProductStock(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException {
		try {
			// 取客户订单相关信息
			SellOrderDTO sellOrderDTO = orderBaseQueryBO
					.viewSellOrderDTO(sellOrderInputDTO.getSellOrderDTO());
			// 取库存信息
			EntityStockDTO entityStockDTO = new EntityStockDTO();
			String productId = sellOrderDTO.getProductId();
			entityStockDTO.setQueryAll(true);
			entityStockDTO.setEntityId(sellOrderInputDTO.getDefaultEntityId());
			entityStockDTO.setProductId(productId);
			entityStockDTO.setFunctionRoleId("3");
			
			
			/**
			 * 根据订单查找到相关的合同
			 */
			List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {
				productDTOs = orderBaseQueryBO.getProductByContract(
						sellOrderDTO, DataBaseConstant.SELL_CONTRACT_CUSTOMER);
			} else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {
				//2015-01-05 xiehao 添加 
				entityStockDTO.setFunctionRoleId("2");
				productDTOs = orderBaseQueryBO.getProductByContract(
						sellOrderDTO, DataBaseConstant.SELL_CONTRACT_ISSUER);

			} else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {
				productDTOs = orderBaseQueryBO.getProductByContract(
						sellOrderDTO, DataBaseConstant.SELL_CONTRACT_SELLER);
			}

			if (null == productDTOs || productDTOs.size() <= 0) {
				throw new BizServiceException("库存没有相关产品!");
			}

			ProductDTO productDTO = new ProductDTO();
			for (ProductDTO productDTO_temp : productDTOs) {
				if (sellOrderDTO.getProductId().equals(
						productDTO_temp.getProductId())) {
					productDTO = productDTO_temp;
					break;
				}
			}

			sellOrderInputDTO.setProductDTO(productDTO);

			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);

			PageDataDTO pageDataDTO = orderBaseQueryBO
					.getProductStockByDTO(entityStockDTO);

			sellOrderInputDTO.setProductStocks(pageDataDTO);

			return sellOrderInputDTO;
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询库存信息失败!");
		}
	}

	@Override
	public void insertOrderListForSellOrderUnsign(
			SellOrderListDTO sellOrderListDTO) throws BizServiceException {
		try {
			/* 判断该该面额的明细是否已存在当前订单 */
			checkFaceValueExist(sellOrderListDTO);
			/* 检测订单所有明细卡数量总和是否超过系统最大值 */
			checkOrderCardTotalThanMaximum(sellOrderListDTO);

			SellOrderList sellOrderList = new SellOrderList();

			ReflectionUtil.copyProperties(sellOrderListDTO, sellOrderList);

			sellOrderList.setOrderListId(commonsDAO
					.getNextValueOfSequence("TB_SELL_ORDER_LIST"));

			// if(StringUtil.isNotEmpty(sellOrderListDTO.getCardAmount())){
			// sellOrderList.setPackageFee(Amount.getDataBaseAmount(sellOrderListDTO.getCardAmount()));
			// }

			if (StringUtil.isNotEmpty(sellOrderListDTO.getPackageFee())) {
				sellOrderList.setPackageFee(Amount
						.getDataBaseAmount(sellOrderListDTO.getPackageFee()));
			}

			if (StringUtil.isNotEmpty(sellOrderListDTO.getCardIssueFee())) {
				sellOrderList.setCardIssueFee(Amount
						.getDataBaseAmount(sellOrderListDTO.getCardIssueFee()));
			}

			if (StringUtil.isNotEmpty(sellOrderListDTO.getFaceValue())) {
				sellOrderList.setFaceValue(Amount
						.getDataBaseAmount(sellOrderListDTO.getFaceValue()));
			}
			sellOrderList.setCreateTime(DateUtil.getCurrentTime());
			if (StringUtil.isNotEmpty(sellOrderListDTO.getValidityPeriod())) {
				sellOrderList.setValidityPeriod(DateUtil
						.getFormatTime(sellOrderListDTO.getValidityPeriod()));
			}

			sellOrderList.setCreateUser(sellOrderListDTO.getLoginUserId());

			sellOrderList.setModifyTime(DateUtil.getCurrentTime());

			sellOrderList.setModifyUser(sellOrderListDTO.getLoginUserId());

			sellOrderList.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			sellOrderListDAO.insert(sellOrderList);
			orderCountTotalPrice
					.countTotalPriceForSellOrderUnsign(sellOrderListDTO
							.getOrderId());

		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("插入明细失败!");
		}

	}

	/***
	 * 删除订单明细
	 */
	@Override
	public void deleteOrderListForSellOrderUnsign(
			SellOrderListDTO sellOrderListDTO) throws BizServiceException {

		List<SellOrderList> sellOrderList_list = new ArrayList<SellOrderList>();
		try {
			for (String temp_orderListId : sellOrderListDTO.getOrderListIdStr()) {
				SellOrderList sellOrderList = new SellOrderList();
				sellOrderList.setOrderListId(temp_orderListId);
				sellOrderList.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				sellOrderList_list.add(sellOrderList);
			}
			baseOrderDAO.batchUpdate("TB_SELL_ORDER_LIST",
					"abatorgenerated_updateByPrimaryKeySelective",
					sellOrderList_list);
			/**
			 * 计算金额
			 */
			orderCountTotalPrice
					.countTotalPriceForSellOrderUnsign(sellOrderListDTO
							.getOrderId());
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除订单明细失败!");
		}
	}

	/**
	 * 编辑订单
	 */
	@Override
	public void updateSellOrder(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			SellOrder sellOrder = new SellOrder();
			ReflectionUtil.copyProperties(sellOrderDTO, sellOrder);

			// 包装费
			if (StringUtil.isNotEmpty(sellOrder.getPackageFee())) {
				sellOrder.setPackageFee(Amount.getDataBaseAmount(sellOrder
						.getPackageFee()));
			}
			// 面额
			if (StringUtil.isNotEmpty(sellOrder.getFaceValue())) {
				sellOrder.setFaceValue(Amount.getDataBaseAmount(sellOrder
						.getFaceValue()));
			}
			// 年费
			if (StringUtil.isNotEmpty(sellOrder.getAnnualFee())) {
				sellOrder.setAnnualFee(Amount.getDataBaseAmount(sellOrder
						.getAnnualFee()));
			}
			// 卡费
			if (StringUtil.isNotEmpty(sellOrder.getCardIssueFee())) {
				sellOrder.setCardIssueFee(Amount.getDataBaseAmount(sellOrder
						.getCardIssueFee()));
			}
			// 快递费
			if (StringUtil.isNotEmpty(sellOrder.getDeliveryFee())) {
				sellOrder.setDeliveryFee(Amount.getDataBaseAmount(sellOrder
						.getDeliveryFee()));
			}
			// 附加费
			if (StringUtil.isNotEmpty(sellOrder.getAdditionalFee())) {
				sellOrder.setAdditionalFee(Amount.getDataBaseAmount(sellOrder
						.getAdditionalFee()));
			}
			// 折扣费
			if (StringUtil.isNotEmpty(sellOrder.getDiscountFee())) {
				sellOrder.setDiscountFee(Amount.getDataBaseAmount(sellOrder
						.getDiscountFee()));
			}
			// 订单日期
			sellOrder.setOrderDate(DateUtil.getFormatTime(sellOrder
					.getOrderDate()));
			// 发票日期
			sellOrder.setInvoiceDate(DateUtil.getFormatTime(sellOrder
					.getInvoiceDate()));
			// 有效日期
			if (StringUtil.isNotEmpty(sellOrder.getValidityPeriod())) {
				sellOrder.setValidityPeriod(DateUtil.getFormatTime(sellOrder
						.getValidityPeriod()));
			}
			// 预计充值日期
			if (StringUtil.isNotEmpty(sellOrder.getForecastCreditDate())) {
				sellOrder.setForecastCreditDate(DateUtil
						.getFormatTime(sellOrder.getForecastCreditDate()));
			}

			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());
			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
			/**
			 * 更新总金额，总数量 如果是匿名订单更新总数量，总金额
			 */
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
					.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
							.equals(sellOrder.getOrderType())) {
				orderCountTotalPrice
						.countTotalPriceForSellOrderUnsign(sellOrderDTO
								.getOrderId());
			} else if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
					.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
							.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
							.equals(sellOrder.getOrderType())) {
				orderCountTotalPrice
						.countTotalPriceForSellOrdersign(sellOrderDTO
								.getOrderId());
			} else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
					.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
							.equals(sellOrder.getOrderType())) {
				orderCountTotalPrice.countTotalPriceForBuyOrderSign(sellOrder);
			} else if (OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER
					.equals(sellOrder.getOrderType())) {
				orderCountTotalPrice.countTotalPriceForCreditOrder(sellOrderDTO
						.getOrderId());
			} else if (OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD.equals(sellOrder
					.getOrderType())) {
				orderCountTotalPrice
						.countTotalPriceForChangeCardOrder(sellOrder
								.getOrderId());
			} else if (OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(sellOrder
					.getOrderType())) {
				orderCountTotalPrice.countPriceForRansomOrder(sellOrder
						.getOrderId());
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("编辑订单失败!");

		}
	}

	@Override
	public SellOrderListDTO editOrderListForSellOrderUnsign(
			SellOrderListDTO sellOrderListDTO) throws BizServiceException {
		try {
			SellOrderList sellOrderList = sellOrderListDAO
					.selectByPrimaryKey(sellOrderListDTO.getOrderListId());
			/**
			 * 通过产品ID找到卡面
			 */
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductId(sellOrderListDTO.getProductId());

			ReflectionUtil.copyProperties(sellOrderList, sellOrderListDTO);

			if (StringUtil.isNotEmpty(sellOrderListDTO.getCardIssueFee())) {
				sellOrderListDTO.setCardIssueFee(Amount
						.getReallyAmount(sellOrderListDTO.getCardIssueFee()));
			}
			if (StringUtil.isNotEmpty(sellOrderListDTO.getPackageFee())) {
				sellOrderListDTO.setPackageFee(Amount
						.getReallyAmount(sellOrderListDTO.getPackageFee()));
			}
			if (StringUtil.isNotEmpty(sellOrderListDTO.getFaceValue())) {
				sellOrderListDTO.setFaceValue(Amount
						.getReallyAmount(sellOrderListDTO.getFaceValue()));
			}
			/**
			 * 日期转换显示
			 */
			if (sellOrderListDTO.getValidityPeriod() != null
					&& !"".equals(sellOrderListDTO.getValidityPeriod())) {
				sellOrderListDTO.setValidityPeriod(DateUtil
						.dbFormatToDateFormat(sellOrderListDTO
								.getValidityPeriod()));
			}
			sellOrderListDTO.setProductDTO(productService
					.viewProduct(productDTO));
			return sellOrderListDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取订单明细信息失败!");
		}
	}

	/***
	 * 对于采购匿名订单与客户订单都是调用这个方法
	 */
	@Override
	public void updateOrderListForSellOrderUnsign(
			SellOrderListDTO sellOrderListDTO) throws BizServiceException {
		try {
			checkOrderCardTotalThanMaximum(sellOrderListDTO);
			checkFaceValueExist(sellOrderListDTO);
			SellOrderList sellOrderList = new SellOrderList();
			ReflectionUtil.copyProperties(sellOrderListDTO, sellOrderList);

			sellOrderList.setPackageFee(Amount.getDataBaseAmount(sellOrderList
					.getPackageFee()));

			sellOrderList.setCardIssueFee(Amount
					.getDataBaseAmount(sellOrderList.getCardIssueFee()));

			sellOrderList.setFaceValue(Amount.getDataBaseAmount(sellOrderList
					.getFaceValue()));
			if (!"".equals(sellOrderList.getValidityPeriod().trim())
					&& sellOrderList.getValidityPeriod() != null)
				sellOrderList.setValidityPeriod(DateUtil
						.getFormatTime(sellOrderList.getValidityPeriod()));

			sellOrderListDAO.updateByPrimaryKeySelective(sellOrderList);

			orderCountTotalPrice
					.countTotalPriceForSellOrderUnsign(sellOrderList
							.getOrderId());

		} catch (BizServiceException bse) {
			logger.warn(bse.getMessage());
			throw bse;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新订单明细信息失败");
		}

	}

	public OrderBaseQueryBO getOrderBaseQueryBO() {
		return orderBaseQueryBO;
	}

	public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
		this.orderBaseQueryBO = orderBaseQueryBO;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public BaseDAO getBaseOrderDAO() {
		return baseOrderDAO;
	}

	public void setBaseOrderDAO(BaseDAO baseOrderDAO) {
		this.baseOrderDAO = baseOrderDAO;
	}

	public SellOrderListDAO getSellOrderListDAO() {
		return sellOrderListDAO;
	}

	public void setSellOrderListDAO(SellOrderListDAO sellOrderListDAO) {
		this.sellOrderListDAO = sellOrderListDAO;
	}

	public OrderCountTotalPrice getOrderCountTotalPrice() {
		return orderCountTotalPrice;
	}

	public void setOrderCountTotalPrice(
			OrderCountTotalPrice orderCountTotalPrice) {
		this.orderCountTotalPrice = orderCountTotalPrice;
	}

	public ProdAccLayPackServiceDAO getProdAccLayPackServiceDAO() {
		return prodAccLayPackServiceDAO;
	}

	public void setProdAccLayPackServiceDAO(
			ProdAccLayPackServiceDAO prodAccLayPackServiceDAO) {
		this.prodAccLayPackServiceDAO = prodAccLayPackServiceDAO;
	}

	/**
	 * 编辑采购订单
	 */
	@Override
	public SellOrderInputDTO editBuyOrderDTO(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException {
		try {
			SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();

			sellOrderDTO = orderBaseQueryBO.viewSellOrderDTO(sellOrderDTO);

			/***
			 * 如果是订单处理
			 */
			if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {

				sellOrderDTO.setProcessEntityName(orderBaseQueryBO
						.getIssuerNameByEntityId(sellOrderDTO
								.getProcessEntityId()));
			} else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {
				sellOrderDTO.setProcessEntityName(orderBaseQueryBO
						.getSellNameByEntityId(sellOrderDTO
								.getProcessEntityId()));
			}

			SellerDTO sellerDTO = sellOrderInputDTO.getSellerDTO();

			sellerDTO.setEntityId(sellOrderDTO.getFirstEntityId());

			sellerDTO = sellerService.getSellerByEntityId(sellerDTO);

			sellOrderInputDTO.setSellerDTO(sellerDTO);

			ProductDTO productDTO = sellOrderInputDTO.getProductDTO();

			productDTO.setProductId(sellOrderDTO.getProductId());

			productDTO = productService.viewProduct(productDTO);

			sellOrderInputDTO.setProductDTO(productDTO);
			/**
			 * 如果是记名订单，则查找卡明细
			 */
			if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
							.equals(sellOrderDTO.getOrderType())) {
				/**
				 * 获取订单卡明细
				 */
				sellOrderDTO.setOrderCardList(orderBaseQueryBO
						.queryBuyOrderCardListByOrderbyId(sellOrderInputDTO
								.getSellOrderCardListQueryDTO()));
				/**
				 * 如果是匿名订单，则查找订单明细
				 */
			} else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
					.equals(sellOrderDTO.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellOrderDTO.getOrderType())) {
				/***
				 * 获取订单明细
				 */
				sellOrderDTO.setOrderList(orderBaseQueryBO
						.queryOrderListByOrderbyId(sellOrderInputDTO
								.getSellOrderListQueryDTO()));
			}
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
			return sellOrderInputDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取采购订单信息失败");
		}
	}

	public SellerService getSellerService() {
		return sellerService;
	}

	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	/**
	 * 初始化采购订单信息 即登录用户所属实体相关的信息
	 */
	@Override
	public SellOrderInputDTO initBuyOrderAdd(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException {
		try {

			SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();

			/**
			 * 如果产品不为空 通过产品ID，合同 买方查找对合同卖方
			 */
			if (StringUtil.isNotEmpty(sellOrderDTO.getProductId())) {

				SellBuyOrderDTO sellBuyOrderDTO = new SellBuyOrderDTO();

				sellBuyOrderDTO.setDefaultEntityId(sellOrderDTO
						.getFirstEntityId());
				sellBuyOrderDTO.setProductId(sellOrderDTO.getProductId());
				List<SellerContractDTO> sellContractDTOList = orderBaseQueryBO
						.getContractDTOForBuyOrder(sellBuyOrderDTO);

				List<EntityDTO> entityDTOList = new ArrayList<EntityDTO>();

				for (SellerContractDTO dto : sellContractDTOList) {
					EntityDTO entityDTO = new EntityDTO();
					entityDTO.setEntityName(dto.getContractSellerName());
					entityDTO.setEntityId(dto.getContractSeller());
					entityDTOList.add(entityDTO);
				}

				sellOrderInputDTO.setEntityDTOList(entityDTOList);

				SellerContractDTO sellContractDTO = new SellerContractDTO();

				if (StringUtil.isEmpty(sellOrderDTO.getProcessEntityId())) {
					if (sellContractDTOList.size() > 0) {
						sellContractDTO = sellContractDTOList.get(0);
						/***
						 * 如果
						 */
						// IssuerExample issuerExample=new IssuerExample();
						// issuerExample.createCriteria().andEntityIdEqualTo(sellContractDTO.getContractSeller());
						// List<Issuer>
						// issuerList=issuerDAO.selectByExample(issuerExample);
						// if(null != issuerList && issuerList.size()>0){
						// sellOrderDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN);
						// }else{
						// SellerExample sellerExample=new SellerExample();
						// sellerExample.createCriteria().andEntityIdEqualTo(sellContractDTO.getContractSeller());
						// List<Seller>
						// sellersList=sellerDAO.selectByExample(sellerExample);
						// if(null != sellersList && sellersList.size()>0){
						// sellOrderDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN);
						// }
						// }
						// 如果合同状态为1，则表示是发卡机构和营销机构的合同
						if (DataBaseConstant.SELL_CONTRACT_ISSUER
								.equals(sellContractDTO.getContractType())) {
							sellOrderDTO
									.setOrderType(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN);
							// 如果合同状态为2，则表示是营销机构和营销机构的合同
						} else if (DataBaseConstant.SELL_CONTRACT_SELLER
								.equals(sellContractDTO.getContractType())) {
							sellOrderDTO
									.setOrderType(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN);
						}
					}
					/**
					 * 说明对方选中了一个上级机构，那么取得上级机构的信息
					 */
				} else {
					for (SellerContractDTO dto : sellContractDTOList) {
						if (sellOrderDTO.getProcessEntityId().equals(
								dto.getContractSeller())) {
							sellContractDTO = dto;
						}
					}
				}
				if (StringUtil.isNotEmpty(sellContractDTO.getDeliveryFee())) {
					sellOrderDTO.setDeliveryFee(Amount
							.getReallyAmount(sellContractDTO.getDeliveryFee()));
				} else {
					sellOrderDTO.setDeliveryFee("0");
				}
				if (StringUtil.isNotEmpty(sellContractDTO.getCardFee())) {
					sellOrderDTO.setCardIssueFee(Amount
							.getReallyAmount(sellContractDTO.getCardFee()));
				} else {
					sellOrderDTO.setCardIssueFee("0");
				}
				if (StringUtil.isNotEmpty(sellContractDTO.getAnnualFee())) {
					sellOrderDTO.setAnnualFee(Amount
							.getReallyAmount(sellContractDTO.getAnnualFee()));
				} else {
					sellOrderDTO.setAnnualFee("0");
				}
			}

			SellerDTO sellerDTO = new SellerDTO();

			sellerDTO.setEntityId(sellOrderInputDTO.getDefaultEntityId());

			sellerDTO = sellerService.getSellerByEntityId(sellerDTO);

			sellOrderInputDTO.setSellerDTO(sellerDTO);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("初始化信息失败");
		}
		return sellOrderInputDTO;
	}

	@Override
	public List<EntityStockDTO> getCurrentStockDTOByEntity(String entityId)
			throws BizServiceException {
		try {
			// java2C.sendTpaService("vMakecard", new
			// HashMap(),Const.JAVA2C_NORMAL,false);
			return orderBaseQueryBO.getCurrentStock(entityId);
		} catch (Exception e) {
			throw new BizServiceException("获取库存信息失败!");
		}
	}

	@Override
	public List<ProductDTO> getProdByContractForBuyOrderUnsign(String entityId)
			throws BizServiceException {
		try {
			return orderBaseQueryBO
					.getProdByContractForBuyOrderUnsign(entityId);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取产品信息失败!");
		}
	}

	public SellOrderInputDTO initCreditOrder(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException {
		try {
			SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
			CustomerDTO customerDTO = sellOrderInputDTO.getCustomerDTO();
			if (StringUtil.isNotEmpty(sellOrderDTO.getFirstEntityId())
					&& StringUtil.isNotEmpty(sellOrderDTO.getProcessEntityId())) {
				customerDTO.setEntityId(sellOrderDTO.getFirstEntityId());
				customerDTO
						.setFatherEntityId(sellOrderDTO.getProcessEntityId());
				customerDTO = customerService.viewCustomer(customerDTO);
			}

			/**
			 * 获取销售人员
			 * TODO
			 */
			sellOrderInputDTO.setSaleUserList(orderBaseQueryBO
					.getSaleUserList(sellOrderInputDTO));
			/* 获取订单处理方银行信息 3:营销机构 */
			sellOrderDTO.setLstBankDTO(bankService.inquery(sellOrderDTO
					.getProcessEntityId()));
			sellOrderInputDTO.setCustomerDTO(customerDTO);

			if (StringUtil.isNotEmpty(sellOrderDTO.getFirstEntityId())) {
				SellerContractDTO dto = new SellerContractDTO();

				dto.setContractBuyer(sellOrderDTO.getFirstEntityId());
				dto.setContractSeller(sellOrderInputDTO.getDefaultEntityId());
				List<SellerAcctypeContractDTO> sellerAcctypeContractDTOList = orderBaseQueryBO
						.getFeeForCreditOrder(dto);
				List<ProductDTO> productDTOs = orderBaseQueryBO
						.getProductByContract(sellOrderDTO,
								DataBaseConstant.SELL_CONTRACT_CUSTOMER);
				//去除不记名卡的卡产品
				if(productDTOs!=null){
					
					List<ProductDTO> productsSign = new ArrayList<ProductDTO>();
					
					for(int i = 0 ; i<productDTOs.size() ; i++){
						if(!productDTOs.get(i).getOnymousStat().trim().equals("2"))
							productsSign.add(productDTOs.get(i));
					}
					productDTOs = productsSign ;
				}
				sellOrderInputDTO.setProductDTOs(productDTOs);

				ProductDTO productDTO = new ProductDTO();

				if (null != productDTOs && productDTOs.size() > 0) {
					if (StringUtil.isEmpty(sellOrderDTO.getProductId())) {
						productDTO = productDTOs.get(0);
					} else {
						for (ProductDTO temp_product : productDTOs) {
							if (sellOrderDTO.getProductId().equals(
									temp_product.getProductId())) {
								productDTO = temp_product;
							}
						}
					}
				}
				sellOrderInputDTO.setProductDTO(productDTO);
				sellOrderDTO.setProductId(productDTO.getProductId());

				if (null != productDTO.getServices()
						&& productDTO.getServices().size() > 0) {
					if (StringUtil.isEmpty(sellOrderDTO.getServiceId())) {
						sellOrderDTO.setServiceId(productDTO.getServices().get(
								0).getServiceId());
					}
				}

				for (SellerAcctypeContractDTO acctypeContractDTO : sellerAcctypeContractDTOList) {
					if (null != sellOrderDTO.getProductId()
							&& sellOrderDTO.getProductId().equals(
									acctypeContractDTO.getProductId())
							&& sellOrderDTO.getServiceId().equals(
									acctypeContractDTO.getAcctypeId())) {
						sellOrderDTO.setServiceFee(acctypeContractDTO.getFee());
					}
				}
				sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("初始化充值订单信息失败!");
		}
		return sellOrderInputDTO;
	}

//	@SuppressWarnings("unchecked")
	@Override
	public PageDataDTO getCustomerCard(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException {
		PageDataDTO pageDataDTO = null;
		try {
			/** 分页查询符合条件的卡号 */
			sellOrderInputDTO.getSellOrderCardListQueryDTO().setIsLastRowNum(
					"all");
			pageDataDTO = orderBaseQueryBO
					.getCardNoByOrderType(sellOrderInputDTO
							.getSellOrderCardListQueryDTO());

			/** 结果不为空时，取符合条件(去除分页)的首张和末张卡号 */
//			if (pageDataDTO != null && pageDataDTO.getData() != null
//					&& pageDataDTO.getData().size() > 0) {
//				sellOrderInputDTO.getSellOrderCardListQueryDTO()
//						.setIsLastRowNum("min");
//				sellOrderInputDTO.getSellOrderCardListQueryDTO().setQueryAll(
//						true);
//				PageDataDTO minCardDataDTO = orderBaseQueryBO
//						.getCardNoByOrderType(sellOrderInputDTO
//								.getSellOrderCardListQueryDTO());
//				List<HashMap<String, String>> lstDataHashMaps = (List<HashMap<String, String>>) pageDataDTO
//						.getData();
//				lstDataHashMaps.add((HashMap<String, String>) minCardDataDTO
//						.getData().get(0));
//				sellOrderInputDTO.getSellOrderCardListQueryDTO()
//						.setIsLastRowNum("max");
//				sellOrderInputDTO.getSellOrderCardListQueryDTO().setQueryAll(
//						true);
//				PageDataDTO maxCardDataDTO = orderBaseQueryBO
//						.getCardNoByOrderType(sellOrderInputDTO
//								.getSellOrderCardListQueryDTO());
//				lstDataHashMaps.add((HashMap<String, String>) maxCardDataDTO
//						.getData().get(0));
//				pageDataDTO.setData(lstDataHashMaps);
//				pageDataDTO.setTotalRecord(pageDataDTO.getTotalRecord() + 2);
//			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取客户卡片信息失败!");
		}
		return pageDataDTO;
	}

	@Override
	public void insertCreditCardlist(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException {
		try {
			SellOrderCardListDTO sellOrderCardListDTO = new SellOrderCardListDTO();

			sellOrderCardListDTO.setCardNoArray(sellOrderInputDTO
					.getCardNoArray());

			sellOrderCardListDTO.setStartCardNo(sellOrderInputDTO
					.getStartCardNo());

			sellOrderCardListDTO.setEndCardNo(sellOrderInputDTO.getEndCardNo());

			sellOrderCardListDTO.setIsCurCustomer(sellOrderInputDTO.getIsCurCustomer());
			sellOrderCardListDTO.setFirstEntityId(sellOrderInputDTO
					.getSellOrderDTO().getFirstEntityId());

			sellOrderCardListDTO.setProcessEntityId(sellOrderInputDTO
					.getSellOrderDTO().getProcessEntityId());

			sellOrderCardListDTO.setCreditAmount(Amount
					.getDataBaseAmountForTwoFloat(sellOrderInputDTO.getCreditAmount()));

			sellOrderCardListDTO.setOrderId(sellOrderInputDTO.getSellOrderDTO()
					.getOrderId());

			sellOrderCardListDTO.setCreateTime(DateUtil.getCurrentTime());
			sellOrderCardListDTO.setModifyTime(DateUtil.getCurrentTime());

			sellOrderCardListDTO.setCreateUser(sellOrderInputDTO
					.getLoginUserId());

			sellOrderCardListDTO.setModifyUser(sellOrderInputDTO
					.getLoginUserId());

			orderBO.insertCreditOrderCardList(sellOrderCardListDTO);

			orderCountTotalPrice
					.countTotalPriceForCreditOrder(sellOrderInputDTO
							.getSellOrderDTO().getOrderId());

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加客户卡片信息失败!");
		}

	}

	// 导入充值订单的持卡人信息
	@Override
	public void insertCreditCardholderList(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException {
		try {
			if (null != sellOrderInputDTO.getSellOrderCardListQueryDTOs()
					&& sellOrderInputDTO.getSellOrderCardListQueryDTOs().size() > 0) {
				for (int i = 0; i < sellOrderInputDTO
						.getSellOrderCardListQueryDTOs().size(); i++) {
					sellOrderCardListDTO
							.setOrderCardListId(commonsDAO
									.getNextValueOfSequenceBySequence("SEQ_SELL_ORDER_CARD_LIST"));
					sellOrderCardListDTO.setOrderId(sellOrderInputDTO
							.getSellOrderDTO().getOrderId());
					sellOrderCardListDTO.setCardholderId(sellOrderInputDTO
							.getSellOrderCardListQueryDTOs().get(i)
							.getCardholderId());
					sellOrderCardListDTO.setFirstName(sellOrderInputDTO
							.getSellOrderCardListQueryDTOs().get(i)
							.getFirstName());
					sellOrderCardListDTO.setExternalId(sellOrderInputDTO
							.getSellOrderCardListQueryDTOs().get(i)
							.getExternalId());
					sellOrderCardListDTO
							.setCardNo(sellOrderInputDTO
									.getSellOrderCardListQueryDTOs().get(i)
									.getCardNo());
					sellOrderCardListDTO.setCreditAmount(Amount
							.getDataBaseAmount(sellOrderInputDTO
									.getSellOrderCardListQueryDTOs().get(i)
									.getCreditAmount()));
					sellOrderCardListDTO.setCreateTime(DateUtil
							.getCurrentTime());
					sellOrderCardListDTO.setModifyTime(DateUtil
							.getCurrentTime());
					sellOrderCardListDTO.setCreateUser(sellOrderInputDTO
							.getLoginUserId());
					sellOrderCardListDTO.setModifyUser(sellOrderInputDTO
							.getLoginUserId());
					ReflectionUtil.copyProperties(sellOrderCardListDTO,
							sellOrderCardList);
					sellOrderCardList.setDataState("1");
					sellOrderCardListDAO.insert(sellOrderCardList);
				}
			}
			orderCountTotalPrice
					.countTotalPriceForCreditOrder(sellOrderInputDTO
							.getSellOrderDTO().getOrderId());

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加充值订单明细失败");
		}
	}

	@Override
	public void deleteCreditCardlist(SellOrderListDTO sellOrderListDTO)
			throws BizServiceException {
		try {
			/***
			 * 删除记名，计算订单总金额
			 */
			deleteSellOrderCardList(sellOrderListDTO);
			orderCountTotalPrice.countTotalPriceForCreditOrder(sellOrderListDTO
					.getOrderId());
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除充值订单明细失败");
		}

	}
	public OrderBO getOrderBO() {
		return orderBO;
	}

	public void setOrderBO(OrderBO orderBO) {
		this.orderBO = orderBO;
	}

	public SellerDAO getSellerDAO() {
		return sellerDAO;
	}

	public void setSellerDAO(SellerDAO sellerDAO) {
		this.sellerDAO = sellerDAO;
	}

	public IssuerDAO getIssuerDAO() {
		return issuerDAO;
	}

	public void setIssuerDAO(IssuerDAO issuerDAO) {
		this.issuerDAO = issuerDAO;
	}

	public void deleteSellOrderCardList(SellOrderListDTO sellOrderListDTO)
			throws Exception {
		List<SellOrderCardList> orderCardList_list = new ArrayList<SellOrderCardList>();
		for (String orderListId : sellOrderListDTO.getOrderListIdStr()) {
			SellOrderCardList sellOrderCardList = new SellOrderCardList();
			sellOrderCardList.setDataState(DataBaseConstant.DATA_STATE_DELETE);
			sellOrderCardList.setOrderCardListId(orderListId);
			orderCardList_list.add(sellOrderCardList);
		}
		baseOrderDAO.batchUpdate("TB_SELL_ORDER_CARD_LIST",
				"abatorgenerated_updateByPrimaryKeySelective",
				orderCardList_list);
	}

	/**
	 * @author Yifeng.Shi
	 * @serialData 2011-10-26
	 * @param SellOrderListDTO
	 * @return null
	 * */
	public void checkOrderCardTotalThanMaximum(SellOrderListDTO sellOrderListDTO)
			throws BizServiceException {
		try {
			if (null == sellOrderListDTO
					|| sellOrderListDTO.getCardAmount().equals("")) {
				return;
			}
			SellOrderListExample orderListExample = new SellOrderListExample();
			if (null != sellOrderListDTO.getOrderListId()
					&& !sellOrderListDTO.getOrderListId().equals("")) {
				orderListExample.createCriteria().andOrderIdEqualTo(
						sellOrderListDTO.getOrderId()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL)
						.andOrderListIdNotEqualTo(
								sellOrderListDTO.getOrderListId());
			} else {
				orderListExample.createCriteria().andOrderIdEqualTo(
						sellOrderListDTO.getOrderId()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL);
			}
			List<SellOrderList> orderLists = sellOrderListDAO
					.selectByExample(orderListExample);
			int existCount = 0;
			/* 当前订单明细的卡数量 */
			existCount += Integer.parseInt(sellOrderListDTO.getCardAmount());
			/* 循环得出当前订单已存在的订单明细卡数量总和 */
			for (SellOrderList tempList : orderLists) {
				existCount += Integer.parseInt(tempList.getCardAmount());
			}
			/* 取出系统既定的订单卡数量上限 */
			SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
			systemParameterDTO
					.setParameterCode(SystemInfoConstants.ORDER_CARD_MAXIMUM);
			Integer maximum = Integer
					.parseInt(systemParameterService.viewSystemParamter(
							systemParameterDTO).getParameterValue());
			/* 当前订单明细卡数量+已存在的订单明细卡数量>系统既定订单卡数量上限 */
			if (existCount > maximum) {
				throw new BizServiceException("订单所有明细卡数量总和超过系统既定最大值" + maximum);
			}
		} catch (BizServiceException bse) {
			throw bse;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("检测订单明细卡数量总和异常");
		}
	}

	public void checkFaceValueExist(SellOrderListDTO sellOrderListDTO)
			throws BizServiceException {
		try {
			if (null == sellOrderListDTO
					|| sellOrderListDTO.getCardAmount().equals("")) {
				return;
			}

			/* 查询当前订单类型 */
			SellOrder currentOrder = sellOrderDAO
					.selectByPrimaryKey(sellOrderListDTO.getOrderId());
			if (null == currentOrder) {
				throw new Exception("找不到订单号:" + sellOrderListDTO.getOrderId());
			}
			/* 查询当前订单是否存在与当前操作的订单明细相同面额类型和相同面额的有效订单明细 */
			SellOrderListExample orderListExample = new SellOrderListExample();
			if (currentOrder.getOrderType().equals(
					OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN)
					|| currentOrder
							.getOrderType()
							.equals(
									OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN)) {
				if (null != sellOrderListDTO.getOrderListId()
						&& !sellOrderListDTO.getOrderListId().equals("")) {
					orderListExample.createCriteria().andOrderIdEqualTo(
							sellOrderListDTO.getOrderId()).andDataStateEqualTo(
							DataBaseConstant.DATA_STATE_NORMAL)
							.andOrderListIdNotEqualTo(
									sellOrderListDTO.getOrderListId());
				} else {
					orderListExample.createCriteria().andOrderIdEqualTo(
							sellOrderListDTO.getOrderId()).andDataStateEqualTo(
							DataBaseConstant.DATA_STATE_NORMAL);
				}
			} else {
				if (null != sellOrderListDTO.getOrderListId()
						&& !sellOrderListDTO.getOrderListId().equals("")) {
					orderListExample.createCriteria().andOrderIdEqualTo(
							sellOrderListDTO.getOrderId())
							.andFaceValueTypeEqualTo(
									sellOrderListDTO.getFaceValueType())
							.andFaceValueEqualTo(
									Amount.getDataBaseAmount(sellOrderListDTO
											.getFaceValue()))
							.andDataStateEqualTo(
									DataBaseConstant.DATA_STATE_NORMAL)
							.andOrderListIdNotEqualTo(
									sellOrderListDTO.getOrderListId());
				} else {
					orderListExample.createCriteria().andOrderIdEqualTo(
							sellOrderListDTO.getOrderId())
							.andFaceValueTypeEqualTo(
									sellOrderListDTO.getFaceValueType())
							.andFaceValueEqualTo(
									Amount.getDataBaseAmount(sellOrderListDTO
											.getFaceValue()))
							.andDataStateEqualTo(
									DataBaseConstant.DATA_STATE_NORMAL);
				}
			}
			List<SellOrderList> lstResult = sellOrderListDAO
					.selectByExample(orderListExample);
			if (null == lstResult || lstResult.size() == 0) {
				return;
			} else if (null != lstResult && lstResult.size() > 0) {
				if (currentOrder.getOrderType().equals(
						OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN)
						|| currentOrder
								.getOrderType()
								.equals(
										OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN)) {
					throw new BizServiceException("当前订单已存在有效明细,请不要重复添加");
				}
				throw new BizServiceException("当前面额已经在本订单产生了明细,请不要重复添加");
			}
		} catch (BizServiceException bse) {
			logger.warn(bse.getMessage());
			throw bse;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("检测订单明细面额失败");
		}

	}

	@Override
	public SellOrderDTO getSellOrderByKey(String orderId)
			throws BizServiceException {
		SellOrder sellorder = sellOrderDAO.selectByPrimaryKey(orderId);
		SellOrderDTO dto = new SellOrderDTO();
		ReflectionUtil.copyProperties(sellorder, dto);
		return dto;
	}

	/***
	 * 删除换卡订单明细
	 */
	@Override
	public void deleteOrderListForChangeCard(SellOrderListDTO sellOrderListDTO)
			throws BizServiceException {

		List<SellOrderList> sellOrderList_list = new ArrayList<SellOrderList>();
		try {
			for (String temp_orderListId : sellOrderListDTO.getOrderListIdStr()) {
				SellOrderList sellOrderList = new SellOrderList();
				sellOrderList.setOrderListId(temp_orderListId);
				sellOrderList.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				sellOrderList.setModifyTime(DateUtil.getCurrentTime24());
				sellOrderList.setModifyUser(sellOrderListDTO.getLoginUserId());
				sellOrderList_list.add(sellOrderList);
			}
			baseOrderDAO.batchUpdate("TB_SELL_ORDER_LIST",
					"abatorgenerated_updateByPrimaryKeySelective",
					sellOrderList_list);
			/**
			 * 计算金额
			 */
			orderCountTotalPrice
					.countTotalPriceForChangeCardOrder(sellOrderListDTO
							.getOrderId());
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除订单明细失败!");
		}
	}

	/***
	 * 删除订单原卡明细
	 */
	@Override
	public void deleteChangeCardOrderOrigCard(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
			throws BizServiceException {

		List<SellOrderOrigCardList> origCardLists = new ArrayList<SellOrderOrigCardList>();
		try {
			for (String temp_orderListId : sellOrderOrigCardListDTO
					.getOrigcardListIds()) {
				SellOrderOrigCardList origCardList = new SellOrderOrigCardList();
				origCardList.setOrigcardListId(temp_orderListId);
				origCardList.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				origCardList.setModifyTime(DateUtil.getCurrentTime24());
				origCardList.setModifyUser(sellOrderOrigCardListDTO
						.getLoginUserId());
				origCardLists.add(origCardList);
			}
			baseOrderDAO.batchUpdate("TB_SELL_ORDER_ORIGCARD_LIST",
					"abatorgenerated_updateByExampleSelective", origCardLists);
			/**
			 * 计算金额
			 */
			orderCountTotalPrice
					.countTotalPriceForChangeCardOrder(sellOrderOrigCardListDTO
							.getOrderId());
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除订单明细失败!");
		}
	}

	@Override
	public void insertOrderListForChangeCardOrder(
			SellOrderListDTO sellOrderListDTO) throws BizServiceException {
		try {

			SellOrderListExample example = new SellOrderListExample();
			example.createCriteria().andOrderIdEqualTo(sellOrderListDTO.getOrderId())
			.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			int num = sellOrderListDAO.countByExample(example);
			if(num>=1){
				throw new BizServiceException("只允许添加一条新卡明细信息");
			}
			
			SellOrderList sellOrderList = new SellOrderList();
			ReflectionUtil.copyProperties(sellOrderListDTO, sellOrderList);
			if(sellOrderList.getFaceValueType()==null||"".equals(sellOrderList.getFaceValueType())){
				ProdFaceValueExample ex=new ProdFaceValueExample();
				ex.createCriteria().andProductIdEqualTo(sellOrderListDTO.getProductId()).andDataStateEqualTo("1");
				List<ProdFaceValue> faceVal=ProdFaceValueDAO.selectByExample(ex);
				
				if(faceVal.size()>0){
					sellOrderList.setFaceValueType(faceVal.get(0).getFaceValueType());
				}
			}
			
			sellOrderList.setOrderListId(commonsDAO
					.getNextValueOfSequence("TB_SELL_ORDER_LIST"));

			if (StringUtil.isNotEmpty(sellOrderListDTO.getFaceValue())) {
				DecimalFormat df = new DecimalFormat("#");//格式化小数  
				String faceValue=df.format(Double.valueOf(Amount
						.getDataBaseAmount(sellOrderListDTO.getFaceValue())));
				sellOrderList.setFaceValue(faceValue);
			}
			sellOrderList.setCreateTime(DateUtil.getCurrentTime());
			if (StringUtil.isNotEmpty(sellOrderListDTO.getValidityPeriod())) {
				sellOrderList.setValidityPeriod(DateUtil
						.getFormatTime(sellOrderListDTO.getValidityPeriod()));
			}

			sellOrderList.setCreateUser(sellOrderListDTO.getLoginUserId());

			sellOrderList.setModifyTime(DateUtil.getCurrentTime());

			sellOrderList.setModifyUser(sellOrderListDTO.getLoginUserId());

			sellOrderList.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			sellOrderListDAO.insert(sellOrderList);
			orderCountTotalPrice
					.countTotalPriceForChangeCardOrder(sellOrderListDTO
							.getOrderId());

		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("插入明细失败!");
		}

	}

	/**
	 * 换卡订单获取
	 * */
//	public SellOrderInputDTO getProductStockForChangeCard(
//			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException {
//		try {
//			// 取客户订单相关信息
//			SellOrderDTO sellOrderDTO = orderBaseQueryBO
//					.viewChangeCardOrderDTO(sellOrderInputDTO.getSellOrderDTO());
//			// 取库存信息
//			EntityStockDTO entityStockDTO = new EntityStockDTO();
//			entityStockDTO.setQueryAll(true);
//			entityStockDTO.setEntityId(sellOrderInputDTO.getDefaultEntityId());
//			/**
//			 * 根据订单查找到相关的合同
//			 */
//			List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
//			List<ProductDTO> allowProducts = new ArrayList<ProductDTO>();
//			productDTOs = orderBaseQueryBO.getProductByContract(sellOrderDTO,
//					DataBaseConstant.SELL_CONTRACT_CUSTOMER);
//			for (ProductDTO productDTO : productDTOs) {
//				if (productDTO.getOnymousStat().equals(
//						OrderConst.PRODUCT_ONYMOUS_STAT_CAN)
//						|| productDTO.getOnymousStat().equals(
//								OrderConst.PRODUCT_ONYMOUS_STAT_NO)) {
//					allowProducts.add(productDTO);
//				}
//			}
//			if (null == allowProducts || allowProducts.size() <= 0) {
//				throw new BizServiceException("库存没有相关产品!");
//			}
//			String tempProductId = "";
//			for (ProductDTO tempProductDTO : allowProducts) {
//				tempProductId = tempProductId + tempProductDTO.getProductId()
//						+ ",";
//			}
//			entityStockDTO.setProductIds(tempProductId.substring(0,
//					tempProductId.length() - 1));
//			sellOrderInputDTO.setProductDTOs(allowProducts);
//
//			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
//
//			PageDataDTO pageDataDTO = orderBaseQueryBO
//					.getProductStockForChangeCard(entityStockDTO);
//
//			sellOrderInputDTO.setProductStocks(pageDataDTO);
//
//			return sellOrderInputDTO;
//		} catch (BizServiceException ex) {
//			throw ex;
//		} catch (Exception e) {
//			this.logger.error(e.getMessage());
//			throw new BizServiceException("查询库存信息失败!");
//		}
//	}

	
	@Override
	public SellOrderInputDTO getProductStockForChangeCard(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException {
		try {
			// 取客户订单相关信息
			SellOrderDTO sellOrderDTO = orderBaseQueryBO
					.viewChangeCardOrderDTO(sellOrderInputDTO.getSellOrderDTO());
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductId(sellOrderDTO.getProductId());
			productDTO = productService.viewProduct(productDTO);
			
			
			//获取库存中的产品
			EntityStockDTO entityStockDTO = new EntityStockDTO();
			entityStockDTO.setQueryAll(true);
			entityStockDTO.setEntityId(sellOrderInputDTO.getDefaultEntityId());
			entityStockDTO.setProductId(sellOrderDTO.getProductId());
			PageDataDTO pageDataDTO = orderBaseQueryBO
			.getProductStockForChangeCard(entityStockDTO);	
			if (null == pageDataDTO) {
				throw new BizServiceException("库存没有相关产品!");
			}
			
			sellOrderInputDTO.setProductDTO(productDTO);
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
			sellOrderInputDTO.setProductStocks(pageDataDTO);
		}catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询库存信息失败!");
		}
		return sellOrderInputDTO;
	}
	
	@Override
	public SellOrderInputDTO editChangeCardOrderDTO(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException {
		SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();

		try {
			sellOrderInputDTO.setSaleUserList(orderBaseQueryBO
					.getSaleUserList(sellOrderInputDTO));

			sellOrderDTO = orderBaseQueryBO
					.viewChangeCardOrderDTO(sellOrderDTO);
			sellOrderDTO.setOldPaymentTerm(sellOrderDTO.getPaymentTerm());
			CustomerDTO customerDTO = sellOrderInputDTO.getCustomerDTO();

			customerDTO.setEntityId(sellOrderDTO.getFirstEntityId());

			customerDTO.setFatherEntityId(sellOrderDTO.getProcessEntityId());

			customerDTO = customerService.viewCustomer(customerDTO);

			sellOrderInputDTO.setCustomerDTO(customerDTO);

			// ProductDTO productDTO = sellOrderInputDTO.getProductDTO();
			//
			// productDTO.setProductId(sellOrderDTO.getProductId());
			//
			// productDTO = productService.viewProduct(productDTO);
			//
			// sellOrderInputDTO.setProductDTO(productDTO);

			/* 换卡订单 取出订单原有卡列表、新卡明细列表 */

			/* 原卡卡列表 */
			sellOrderDTO.setOrigCardList(orderBaseQueryBO
					.queryOrigCardListByOrderbyId(sellOrderInputDTO
							.getSellOrderOrigCardListQueryDTO()));

			/* 新卡明细 */
			sellOrderDTO.setOrderList(orderBaseQueryBO
					.queryChangeCardOrderListByOrderbyId(sellOrderInputDTO
							.getSellOrderListQueryDTO()));
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取订单信息失败!");
		}
		return sellOrderInputDTO;
	}

	@Override
	public SellOrderListDTO editOrderListForChangeCardOrder(
			SellOrderListDTO sellOrderListDTO) throws BizServiceException {
		try {
			SellOrderList sellOrderList = sellOrderListDAO
					.selectByPrimaryKey(sellOrderListDTO.getOrderListId());
			/**
			 * 通过产品ID找到卡面
			 */
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductId(sellOrderList.getProductId());

			ReflectionUtil.copyProperties(sellOrderList, sellOrderListDTO);

			if (StringUtil.isNotEmpty(sellOrderListDTO.getCardIssueFee())) {
				sellOrderListDTO.setCardIssueFee(Amount
						.getReallyAmount(sellOrderListDTO.getCardIssueFee()));
			}
			if (StringUtil.isNotEmpty(sellOrderListDTO.getPackageFee())) {
				sellOrderListDTO.setPackageFee(Amount
						.getReallyAmount(sellOrderListDTO.getPackageFee()));
			}
			if (StringUtil.isNotEmpty(sellOrderListDTO.getFaceValue())) {
				sellOrderListDTO.setFaceValue(Amount
						.getReallyAmount(sellOrderListDTO.getFaceValue()));
			}
			/**
			 * 日期转换显示
			 */
			if (sellOrderListDTO.getValidityPeriod() != null
					&& !"".equals(sellOrderListDTO.getValidityPeriod())) {
				sellOrderListDTO.setValidityPeriod(DateUtil
						.dbFormatToDateFormat(sellOrderListDTO
								.getValidityPeriod()));
			}
			sellOrderListDTO.setProductDTO(productService
					.viewProduct(productDTO));
			return sellOrderListDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取订单明细信息失败!");
		}
	}

	/***
	 * 更新换卡订单的订单明细
	 */
	@Override
	public void updateOrderListForChangeCardOrder(
			SellOrderListDTO sellOrderListDTO) throws BizServiceException {
		try {
			SellOrderList sellOrderList = new SellOrderList();
			ReflectionUtil.copyProperties(sellOrderListDTO, sellOrderList);
			sellOrderList.setPackageFee(Amount.getDataBaseAmount(sellOrderList
					.getPackageFee()));
			sellOrderList.setCardIssueFee(Amount
					.getDataBaseAmount(sellOrderList.getCardIssueFee()));
			sellOrderList.setFaceValue(Amount.getDataBaseAmount(sellOrderList
					.getFaceValue()));
			if (!"".equals(sellOrderList.getValidityPeriod())
					&& sellOrderList.getValidityPeriod() != null)
				sellOrderList.setValidityPeriod(DateUtil
						.getFormatTime(sellOrderList.getValidityPeriod()));

			sellOrderListDAO.updateByPrimaryKeySelective(sellOrderList);

			orderCountTotalPrice
					.countTotalPriceForChangeCardOrder(sellOrderList
							.getOrderId());

		} catch (BizServiceException bse) {
			logger.warn(bse.getMessage());
			throw bse;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新订单明细信息失败");
		}

	}

	//批量查询持卡人信息
	@Override
	public void insertChangeCardOrderOrigCard(SellOrderDTO sellOrderDTO) throws BizServiceException {
		try {			
			SellOrderOrigCardListExample example = new SellOrderOrigCardListExample();
			example.createCriteria().andOrderIdEqualTo(sellOrderDTO.getOrderId())
			.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			int num = sellOrderOrigCardListDAO.countByExample(example);
			if(num>=1){
				throw new BizServiceException("只允许添加一条原卡明细信息");
			}
			List<String> cardNoList = orderBaseQueryBO
					.getOrigCardListByChangeOrder(sellOrderDTO);
			if(null==cardNoList||cardNoList.size()<=0){
				throw new BizServiceException("该卡产品与订单中的产品不符!");				
			}
			//上面的方法无法判断卡产品与订单中的产品是否一致，写了新方法
			List<String> productIds = orderBaseQueryBO
					.getCheckProductIdByChangeOrder(sellOrderDTO);
			if(null==productIds||productIds.size()<=0){
				throw new BizServiceException("该卡产品与订单中的产品不符!");				
			}
			
			if (null != cardNoList && cardNoList.size() > 0) {
				/** 处理卡号列表，以逗号分隔 */
				String cardNoString = cardNoList.toString().replace("[", "").replace("]", "").replace("{", "").replace("}", "")
						.replace(" ", "").replace("cardNo=", "");
				
				AccPackageDTO accPackageDTO=new AccPackageDTO();
				
				//TxnPackageDTO txnPackageDTO=new TxnPackageDTO();
				//TXN
				accPackageDTO.setTxnCode("M040");
				accPackageDTO.setCardNo(cardNoString);
				//起始行
				accPackageDTO.setBegin_row("1");
				//结束行
				accPackageDTO.setEnd_row(String.valueOf(cardNoList.size()));
				
				accPackageDTO=java2ACCBusinessService.insertChangeCardOrderOrigCard(accPackageDTO);
				
				String rspCode=accPackageDTO.getRespCode();
				// XXX by wpf 代码需要修改 根据 卡片激活状态修改入库状态
				// 不同的订单也会使用这个方法.所以赎回订单批量加入卡.将不使用这个方法 .
				if (rspCode.equals("00")) {
					/** 报文返回状态为成功 */
					logger.debug("vCardHolderBatInq success!");
					List<SellOrderOrigCardList> list = new ArrayList<SellOrderOrigCardList>();
					//String result = (String) map1.get(CFunctionConstant.OTHERDATA);
					String result=accPackageDTO.getOtherData();
					String[] records = result.split("\\|");
					if (null !=result && !"".equals(result) && records != null && records.length > 0) {
						for (int i = 0; i < records.length; i++) {
							if("".equals(records[i].trim())){
								break;
							}
							SellOrderOrigCardList origCard = new SellOrderOrigCardList();
							List<CardholderQueryDTO> cardholders = cardholderService
							.getCardHolderByCardNo(cardNoList.get(0));
							if(null != cardholders && cardholders.size()>0){
								origCard.setCardholderId(cardholders.get(0).getCardholderId());
								logger.debug("CardHolderName:" + cardholders.get(0).getFirstName());
								origCard.setFirstName(cardholders.get(0).getFirstName());
							}
							String[] fields = records[i].split("\\^");
							
							origCard.setCardNo(fields[0]);
							origCard.setProductId(fields[1]);
							origCard.setProductName(fields[2]);
							origCard.setValidityPeriod(fields[5]);
							origCard.setAmount(fields[6]);
							origCard.setOrigcardListId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER_ORIGCARD_LIST"));
							origCard.setCreateUser(sellOrderDTO.getLoginUserId());
							origCard.setCreateTime(DateUtil.getCurrentTime24());
							origCard.setModifyUser(sellOrderDTO.getLoginUserId());
							origCard.setModifyTime(DateUtil.getCurrentTime24());
							origCard.setOrderId(sellOrderDTO.getOrderId());
							origCard.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
							origCard.setCardSate(OrderConst.ORIG_CARD_STAT_UNCHECK);
							list.add(origCard);
						}
						baseOrderDAO.batchInsert("TB_SELL_ORDER_ORIGCARD_LIST","abatorgenerated_insert", list);
						orderCountTotalPrice.countTotalPriceForChangeCardOrder(sellOrderDTO.getOrderId());
					}else{
						throw new BizServiceException("添加明细异常");
					}
				} else if (rspCode.equals("96")) {
					logger.debug("begin search company cardholder info!");
					List<SellOrderOrigCardList> list = new ArrayList<SellOrderOrigCardList>();
					SellOrderOrigCardList origCard = new SellOrderOrigCardList(); // c端会获取企业持卡人信息失败.企业持卡人信息在java端获取
					List<CardholderQueryDTO> cardholders = cardholderService.getCardHolderByCardNo(cardNoList.get(0));
					if (null != cardholders && cardholders.size() > 0) {
						origCard.setCardholderId(cardholders.get(0).getCardholderId());
						logger.debug("CardHolderName:" + cardholders.get(0).getFirstName());
						origCard.setFirstName(cardholders.get(0).getFirstName());
					}
					List<HashMap> maps = (List<HashMap>) commonsDAO.queryForList("PRODUCT.selectProductInfoByCardNo",
							cardNoList.get(0));
					if (maps == null || maps.size() == 0) {
						logger.debug("未查到对应卡的的产品信息!卡号:");
						throw new Exception("未查到对应卡的的产品信息" + cardNoList.get(0));
					}
					HashMap<String, String> map = maps.get(0);
					origCard.setCardNo(map.get("cardNO"));
					origCard.setProductId(map.get("id"));
					origCard.setProductName(map.get("name"));
					origCard.setValidityPeriod(map.get("validityperiod"));
					origCard.setAmount(map.get("amount"));
					origCard.setOrigcardListId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER_ORIGCARD_LIST"));
					origCard.setCreateUser(sellOrderDTO.getLoginUserId());
					origCard.setCreateTime(DateUtil.getCurrentTime24());
					origCard.setModifyUser(sellOrderDTO.getLoginUserId());
					origCard.setModifyTime(DateUtil.getCurrentTime24());
					origCard.setOrderId(sellOrderDTO.getOrderId());
					origCard.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
					origCard.setCardSate(OrderConst.ORIG_CARD_STAT_UNCHECK);
					list.add(origCard);
					baseOrderDAO.batchInsert("TB_SELL_ORDER_ORIGCARD_LIST", "abatorgenerated_insert", list);
					orderCountTotalPrice.countTotalPriceForChangeCardOrder(sellOrderDTO.getOrderId());

				} else {
					throw new BizServiceException("添加明细异常" + rspCode);
				}
			}

		} 
		catch (BizServiceException e1) {
			e1.printStackTrace();
			throw e1;
		}catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加客户卡片信息失败!");
		}

	}
	
	@Override
	public SellOrderInputDTO updateRansomOrderDTO(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException {
		SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();

		try {
			sellOrderInputDTO.setSaleUserList(orderBaseQueryBO
					.getSaleUserList(sellOrderInputDTO));

			sellOrderDTO = orderBaseQueryBO
					.viewChangeCardOrderDTO(sellOrderDTO);

			CustomerDTO customerDTO = sellOrderInputDTO.getCustomerDTO();

			customerDTO.setEntityId(sellOrderDTO.getFirstEntityId());

			customerDTO.setFatherEntityId(sellOrderDTO.getProcessEntityId());

			customerDTO = customerService.viewCustomer(customerDTO);

			sellOrderInputDTO.setCustomerDTO(customerDTO);

			/* 换卡订单 取出订单原有卡列表、新卡明细列表 */

			/* 原卡卡列表 */
			sellOrderDTO.setOrigCardList(orderBaseQueryBO
					.queryOrigCardListByOrderbyId(sellOrderInputDTO
							.getSellOrderOrigCardListQueryDTO()));
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizServiceException("获取订单信息失败!");
		}
		return sellOrderInputDTO;
	}

	@Override
	public void insertRansomOrderOrigCard(
			SellOrderOrigCardListQueryDTO sellOrderOrigCardListQueryDTO)
			throws BizServiceException {
		try {
			Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
			List<String> cardNoList = orderBaseQueryBO
					.getOrigCardList(sellOrderOrigCardListQueryDTO);
			if (null != cardNoList && cardNoList.size() > 0) {
				/** 处理卡号列表，以逗号分隔 */
				String cardNoString = cardNoList.toString().replace("[", "")
						.replace("]", "").replace("{", "").replace("}", "")
						.replace(" ", "").replace("cardNo=", "");
//				HashMap map = new HashMap();
//				map.put(CFunctionConstant.TXN_TYPE, "M040");
//				map.put(CFunctionConstant.RESV2, cardNoString);
//				map.put(CFunctionConstant.SWT_TXN_DATE, "1");
//				map.put(CFunctionConstant.SWT_TXN_TIME, String.valueOf(cardNoList.size()));
//				logger.debug("before send port to vCardHolderBatInq --M040");
//				
//				
//				OperationResult or = java2C.sendTpService("vCardHolderBatInq",
//						map, Const.JAVA2C_BIG_AMT, false);
				
				AccPackageDTO accPackageDTO=new AccPackageDTO();
				
				accPackageDTO.setTxnCode("M040");
				accPackageDTO.setCardNo(cardNoString);
				//起始行
				accPackageDTO.setBegin_row("1");
				//结束行
				accPackageDTO.setEnd_row(String.valueOf(cardNoList.size()));
				
				accPackageDTO=java2ACCBusinessService.insertChangeCardOrderOrigCard(accPackageDTO);
				
				
				String rspCode=accPackageDTO.getRespCode();
				

				logger.debug("vCardHolderBatInq return:" + rspCode);
				if (rspCode.equals("00")) {
					/** 报文返回状态为成功 */
					logger.debug("vCardHolderBatInq success!");
					List<SellOrderOrigCardList> list = new ArrayList<SellOrderOrigCardList>();
					String result=accPackageDTO.getOtherData();
					
					String[] records = result.split("\\|");
					if (null !=result && !"".equals(result) && records != null && records.length > 0) {
						for (int i = 0; i < records.length; i++) {
							if("".equals(records[i].trim())){
								break;
							}
							String[] fields = records[i].split("\\^");
							SellOrderOrigCardList origCard = new SellOrderOrigCardList();
							origCard.setCardNo(fields[0]);
							origCard.setProductId(fields[1]);
							origCard.setProductName(fields[2]);
							origCard.setCardholderId(fields[3]);
							origCard.setFirstName(fields[4]);
							origCard.setValidityPeriod(fields[5]);
							origCard.setAmount(fields[6]);
							origCard.setOrigcardListId(commonsDAO
											.getNextValueOfSequence("TB_SELL_ORDER_ORIGCARD_LIST"));
							origCard
									.setCreateUser(sellOrderOrigCardListQueryDTO
											.getLoginUserId());
							origCard.setCreateTime(DateUtil.getCurrentTime24());
							origCard
									.setModifyUser(sellOrderOrigCardListQueryDTO
											.getLoginUserId());
							origCard.setModifyTime(DateUtil.getCurrentTime24());
							origCard.setOrderId(sellOrderOrigCardListQueryDTO
									.getOrderId());
							origCard
									.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
							origCard
									.setCardSate(OrderConst.ORIG_CARD_STAT_UNCHECK);
							list.add(origCard);
						}
						baseOrderDAO.batchInsert("TB_SELL_ORDER_ORIGCARD_LIST",
								"abatorgenerated_insert", list);
						orderCountTotalPrice
								.countPriceForRansomOrder(sellOrderOrigCardListQueryDTO
										.getOrderId());
					}else {
						if (rspCodeMap.containsKey(rspCode)) {
							throw new BizServiceException(rspCodeMap.get(rspCode));
						}
					}
				}
			}else{
				throw new BizServiceException("无满足的卡");
			}
		} catch (BizServiceException bizServiceException) {
			logger.error(bizServiceException.getMessage(), bizServiceException);
			throw bizServiceException;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BizServiceException("添加客户卡片信息失败!");
		}

	}

	/***
	 * 删除订单原卡明细
	 */
	@Override
	public void deleteRansomOrderOrigCard(
			SellOrderOrigCardListDTO sellOrderOrigCardListDTO)
			throws BizServiceException {

		List<SellOrderOrigCardList> origCardLists = new ArrayList<SellOrderOrigCardList>();
		try {
			for (String temp_orderListId : sellOrderOrigCardListDTO
					.getOrigcardListIds()) {
				SellOrderOrigCardList origCardList = new SellOrderOrigCardList();
				origCardList.setOrigcardListId(temp_orderListId);
				origCardList.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				origCardList.setModifyTime(DateUtil.getCurrentTime24());
				origCardList.setModifyUser(sellOrderOrigCardListDTO
						.getLoginUserId());
				origCardLists.add(origCardList);
			}
			baseOrderDAO.batchUpdate("TB_SELL_ORDER_ORIGCARD_LIST",
					"abatorgenerated_updateByExampleSelective", origCardLists);
			/**
			 * 计算金额
			 */
			orderCountTotalPrice
					.countPriceForRansomOrder(sellOrderOrigCardListDTO
							.getOrderId());
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除订单明细失败!");
		}
	}

	public SellOrderOrigCardListDAO getSellOrderOrigCardListDAO() {
		return sellOrderOrigCardListDAO;
	}

	public void setSellOrderOrigCardListDAO(
			SellOrderOrigCardListDAO sellOrderOrigCardListDAO) {
		this.sellOrderOrigCardListDAO = sellOrderOrigCardListDAO;
	}

	public CardholderService getCardholderService() {
		return cardholderService;
	}

	public void setCardholderService(CardholderService cardholderService) {
		this.cardholderService = cardholderService;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

}
