package com.huateng.univer.order.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.univer.seller.order.dto.CardNoSectionDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderFlowDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.DictInfoConstants;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.EntityDictInfoDAO;
import com.huateng.framework.ibatis.dao.SellOrderFlowDAO;
import com.huateng.framework.ibatis.model.EntityDictInfo;
import com.huateng.framework.ibatis.model.EntityDictInfoExample;
import com.huateng.framework.ibatis.model.SellOrderFlow;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.issuer.order.biz.bo.StockOrderCommonService;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.order.business.query.SellOrderQuery;
import com.huateng.univer.order.business.service.SellOrderPrintCertService;

public class SellOrderPrintCertServiceImpl implements SellOrderPrintCertService {
	public Logger logger = Logger
			.getLogger(SellOrderPrintCertServiceImpl.class);
	private OrderBaseQueryBO orderBaseQueryBO;
	private StockOrderCommonService stockOrderCommonService;
	private SellOrderFlowDAO sellOrderFlowDAO;
	private SellOrderQuery sellOrderQuery;
	private EntityDictInfoDAO entityDictInfoDAO;

	public SellOrderDTO printStockCert(SellOrderDTO dto)
			throws BizServiceException {
		logger.debug("enter printStockCert");
		SellOrderDTO resultOrder = null;
		try {
			String orderType = dto.getOrderType();
			if (!(orderType
					.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN) || orderType
					.equals(OrderConst.ORDER_TYPE_ORDER_MAKE_CARD))) {
				throw new BizServiceException("只能打印售卡订单、采购订单和制卡订单");
			}

			/* 查询订单基本信息 */
			if (orderType.equals(OrderConst.ORDER_TYPE_ORDER_MAKE_CARD)) {
				SellOrderDTO tempOrderDTO = new SellOrderDTO();
				tempOrderDTO.setOrderId(dto.getOrderId());
				tempOrderDTO.setDynamicFirstEntityTable("TB_ISSUER");
				tempOrderDTO.setDynamicProcessEntityTable("TB_ISSUER");
				tempOrderDTO.setDynamicFirstEntitycolumn("T6.ISSUER_NAME");
				tempOrderDTO.setDynamicprocessEntitycolumn("T7.ISSUER_NAME");
				resultOrder = stockOrderCommonService
						.getSellOrderDTOByDto(tempOrderDTO);
			} else {
				resultOrder = orderBaseQueryBO.getSellOrderDTO(dto);
			}
			SellOrderFlow flowExample = new SellOrderFlow();
			flowExample.setOrderId(dto.getOrderId());
			List<CardNoSectionDTO> sectionList = new ArrayList<CardNoSectionDTO>();
			CardNoSectionDTO tempSectionDTO = new CardNoSectionDTO();
			/* 制卡订单，必须入库完成才可以打印，且是打印入库凭证 */
			if (orderType.equals(OrderConst.ORDER_TYPE_ORDER_MAKE_CARD)) {
				if (!resultOrder.getOrderState().equals(
						OrderConst.ORDER_STATE_ORDER_STOCK)) {
					throw new BizServiceException("订单:"
							+ resultOrder.getOrderId() + "未入库完成，不能打印");
				}
				flowExample
						.setOrderFlowNode(OrderConst.ORDER_FLOW_STOCK_ACCEPT);
				flowExample
						.setOperateType(OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION);

				tempSectionDTO.setCardNoSection(sellOrderQuery
						.opeCardNoSection(resultOrder.getOrderId(), orderType));
				tempSectionDTO.setCardQuantity(resultOrder.getCardQuantity());
				tempSectionDTO.setFaceValue(resultOrder.getFaceValue());
				tempSectionDTO.setValidPeriod(resultOrder.getValidityPeriod());
				sectionList.add(tempSectionDTO);
				resultOrder.setCreateUser(resultOrder.getCreateUserName());
				resultOrder.setStockCertFlag("entStock");
			}

			/* 销售订单，必须是配送状态后，打印出库凭证 */
			else if (orderType
					.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN)
					|| orderType
							.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN)) {
				if (!(resultOrder.getOrderState().equals(
						OrderConst.ORDER_STATE_ORDER_WAIT_SEND_CONFIRM) || resultOrder
						.getOrderState().equals(
								OrderConst.ORDER_STATE_ORDER_SEND_CONFIRM))) {
					throw new BizServiceException("订单:"
							+ resultOrder.getOrderId() + "未配送出库，不能打印");
				}
				flowExample.setOrderFlowNode(OrderConst.ORDER_FLOW_SEND);
				flowExample
						.setOperateType(OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION);
				resultOrder.setCreateUser(resultOrder.getSaleManName());
				resultOrder.setStockCertFlag("outStock");
				if (orderType
						.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN)
						|| orderType
								.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN)
						|| orderType
								.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN)) {
					tempSectionDTO.setCardNoSection(sellOrderQuery
							.opeCardNoSection(resultOrder.getOrderId(),
									orderType));
					tempSectionDTO.setCardQuantity(resultOrder
							.getCardQuantity());
					tempSectionDTO.setFaceValue(resultOrder.getFaceValue());
					tempSectionDTO.setValidPeriod(resultOrder
							.getValidityPeriod());
					sectionList.add(tempSectionDTO);
				} else {
					List<SellOrderList> tempList = orderBaseQueryBO
							.getSellOrderListByOrderId(resultOrder.getOrderId());
					sectionList = sellOrderQuery
							.opeCardNoSectionForUnsign(tempList);
				}
			}

			/* 采购订单 */
			else {

				/* ①采购方为本机构，则为入库订单，必须状态为入库完成 */
				if (resultOrder.getFirstEntityId().equals(
						dto.getDefaultEntityId())
						&& !resultOrder.getOrderState().equals(
								OrderConst.ORDER_STATE_ORDER_STOCK)) {
					throw new BizServiceException("订单:"
							+ resultOrder.getOrderId() + "未入库完成，不能打印");
				} else {
					flowExample
							.setOrderFlowNode(OrderConst.ORDER_FLOW_BRANCH_ACCEPT);
					flowExample
							.setOperateType(OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION);

					SellOrderFlow tempFlow = new SellOrderFlow();
					tempFlow.setOrderId(dto.getOrderId());
					tempFlow.setOrderFlowNode(OrderConst.ORDER_FLOW_SEND);
					tempFlow
							.setOperateType(OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION);
					SellOrderFlowDTO flowDTO = sellOrderFlowDAO
							.getOrderFlowDTOForPrint(tempFlow);
					if (flowDTO == null) {
						resultOrder.setCreateUser("");
					} else {
						resultOrder.setCreateUser(flowDTO.getCreateUser());
					}
					resultOrder.setStockCertFlag("entStock");
				}

				/* ②处理方为本机构，则为出库订单，必须状态为配送之后的状态 */
				if (resultOrder.getProcessEntityId().equals(
						dto.getDefaultEntityId())
						&& !(resultOrder.getOrderState().equals(
								OrderConst.ORDER_STATE_ORDER_BRANCH_ACCEPT) || resultOrder
								.getOrderState().equals(
										OrderConst.ORDER_STATE_ORDER_STOCK))) {
					throw new BizServiceException("订单:"
							+ resultOrder.getOrderId() + "未配送出库，不能打印");
				} else if (resultOrder.getProcessEntityId().equals(
						dto.getDefaultEntityId())) {
					flowExample.setOrderFlowNode(OrderConst.ORDER_FLOW_SEND);
					flowExample
							.setOperateType(OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION);

					resultOrder.setStockCertFlag("outStock");
				}

				if (orderType
						.equals(OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN)
						|| orderType
								.equals(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN)
						|| orderType
								.equals(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN)) {
					tempSectionDTO.setCardNoSection(sellOrderQuery
							.opeCardNoSection(resultOrder.getOrderId(),
									orderType));
					tempSectionDTO.setCardQuantity(resultOrder
							.getCardQuantity());
					tempSectionDTO.setFaceValue(resultOrder.getFaceValue());
					tempSectionDTO.setValidPeriod(resultOrder
							.getValidityPeriod());
					sectionList.add(tempSectionDTO);
				} else {
					List<SellOrderList> tempList = orderBaseQueryBO
							.getSellOrderListByOrderId(resultOrder.getOrderId());
					sectionList = sellOrderQuery
							.opeCardNoSectionForUnsign(tempList);
				}
			}
			resultOrder.setOrderFlowDTO(sellOrderFlowDAO
					.getOrderFlowDTOForPrint(flowExample));

			resultOrder.setCardNoSectionDTOList(sectionList);

			resultOrder.setPrintDate(DateUtil.getStringDate());

			EntityDictInfoExample ediExample = new EntityDictInfoExample();
			if (resultOrder.getStockCertFlag().equals("entStock")) {
				ediExample
						.createCriteria()
						.andDictTypeCodeEqualTo(
								DictInfoConstants.CERT_PRINT_ENT_STOCK)
						.andEntityIdEqualTo(dto.getDefaultEntityId())
						.andDictStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			} else if (resultOrder.getStockCertFlag().equals("outStock")) {
				ediExample
						.createCriteria()
						.andDictTypeCodeEqualTo(
								DictInfoConstants.CERT_PRINT_OUT_STOCK)
						.andEntityIdEqualTo(dto.getDefaultEntityId())
						.andDictStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			}
			List<EntityDictInfo> ediList = entityDictInfoDAO
					.selectByExample(ediExample);
			if (ediList != null && ediList.size() != 0) {
				resultOrder.setBuyStatement(ediList.get(0).getDictName());
			}
		} catch (BizServiceException bse) {
			throw bse;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询凭证信息失败");
		}
		logger.debug("out printStockCert");
		return resultOrder;
	}

	public OrderBaseQueryBO getOrderBaseQueryBO() {
		return orderBaseQueryBO;
	}

	public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
		this.orderBaseQueryBO = orderBaseQueryBO;
	}

	public StockOrderCommonService getStockOrderCommonService() {
		return stockOrderCommonService;
	}

	public void setStockOrderCommonService(
			StockOrderCommonService stockOrderCommonService) {
		this.stockOrderCommonService = stockOrderCommonService;
	}

	public SellOrderFlowDAO getSellOrderFlowDAO() {
		return sellOrderFlowDAO;
	}

	public void setSellOrderFlowDAO(SellOrderFlowDAO sellOrderFlowDAO) {
		this.sellOrderFlowDAO = sellOrderFlowDAO;
	}

	public SellOrderQuery getSellOrderQuery() {
		return sellOrderQuery;
	}

	public void setSellOrderQuery(SellOrderQuery sellOrderQuery) {
		this.sellOrderQuery = sellOrderQuery;
	}

	public EntityDictInfoDAO getEntityDictInfoDAO() {
		return entityDictInfoDAO;
	}

	public void setEntityDictInfoDAO(EntityDictInfoDAO entityDictInfoDAO) {
		this.entityDictInfoDAO = entityDictInfoDAO;
	}

}
