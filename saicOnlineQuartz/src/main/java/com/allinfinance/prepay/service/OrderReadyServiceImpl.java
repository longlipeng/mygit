package com.allinfinance.prepay.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.AccCardInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.EntityStockMapper;
import com.allinfinance.prepay.mapper.svc_mng.ProductMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderCardListMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderListMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderOrigCardListMapper;
import com.allinfinance.prepay.model.AccCardInfo;
import com.allinfinance.prepay.model.AccCardInfoExample;
import com.allinfinance.prepay.model.EntityStock;
import com.allinfinance.prepay.model.EntityStockExample;
import com.allinfinance.prepay.model.Product;
import com.allinfinance.prepay.model.ProductExample;
import com.allinfinance.prepay.model.SellOrder;
import com.allinfinance.prepay.model.SellOrderCardList;
import com.allinfinance.prepay.model.SellOrderCardListExample;
import com.allinfinance.prepay.model.SellOrderExample;
import com.allinfinance.prepay.model.SellOrderList;
import com.allinfinance.prepay.model.SellOrderListExample;
import com.allinfinance.prepay.order.business.bo.OrderBO;
import com.allinfinance.prepay.order.business.bo.OrderBaseQueryBO;
import com.allinfinance.prepay.utils.DataBaseConstant;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.OrderConst;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;

@Service
public class OrderReadyServiceImpl implements OrderReadyService {
	private static Logger logger = Logger.getLogger(OrderReadyServiceImpl.class);
	@Autowired
	private OrderBaseQueryBO orderBaseQueryBO;
	@Autowired
	private OrderBO orderBO;
	@Autowired
	private SellOrderCardListMapper sellOrderCardListMapper;
	@Autowired
	private AccCardInfoMapper accCardInfoMapper;
	@Autowired
	private EntityStockMapper entityStockMapper;
	@Autowired
	private EntityStockService entityStockService;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private SellOrderOrigCardListMapper sellOrderOrigCardListMapper;
	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private SellOrderListMapper sellOrderListMapper;
	@Autowired
	private SellOrderMapper sellOrderMapper;
	
	@Override
	public void cardReady(OrderReadyDTO orderReadyDTO)
			throws BizServiceException {
		try {
			// 记名销售订单卡片准备
			if (orderReadyDTO.getOrderType() != null
								&&  orderReadyDTO.getOrderType()
										.equals(
												OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN)){
			List<SellOrderCardList> orderCardList = orderBaseQueryBO
					.getSellOrderCardListsByOrderId(orderReadyDTO.getOrderId());
			orderCardListReady(orderCardList, orderReadyDTO);
			}else if(orderReadyDTO.getOrderType() != null
					&& orderReadyDTO.getOrderType().equals(
							OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD)){
				// 换卡订单卡片准备
				SellOrderList orderList = orderBaseQueryBO
						.getSellOrderListByPrimaryKey(orderReadyDTO.getOrderId());
				orderReadyDTO.setProductId(orderList.getProductId());
				orderListReadyForChangeOrder(orderList, orderReadyDTO);
			}
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BizServiceException("准备失败");
		}

	}
	@Override
	public void cardSegmentReady(OrderReadyDTO orderReadyDTO)
			throws BizServiceException {
		try {
			
			// 匿名销售订单卡片准备
			SellOrderList orderList = orderBaseQueryBO
					.getSellOrderListByPrimaryKey(orderReadyDTO
							.getOrderListId());
			if (orderList.getCardAmount() != null
					&& orderList.getRealAmount() != null) {
				Integer reallyAmount = 0;
				reallyAmount = Integer.parseInt(orderList.getCardAmount())
						- Integer.parseInt(orderList.getRealAmount());
				if (reallyAmount == 0) {
					throw new BizServiceException("该明细已准备完成，请不要重复准备 ");
				}
			}
			orderListReady(orderList, orderReadyDTO);
			
			/***
			 * 更新订单的实际数量
			 */
			
			SellOrderCardListExample sellOrderCardListEx=new SellOrderCardListExample();
			sellOrderCardListEx.createCriteria().andOrderIdEqualTo(orderReadyDTO.getOrderId())
			.andCardNoIsNotNull().andCardNoEqualTo("").andDataStateEqualTo("1");
			
			int cardQuantity =sellOrderCardListMapper.countByExample(sellOrderCardListEx);
			

			SellOrder sellOrder = new SellOrder();
			sellOrder.setOrderId(orderReadyDTO.getOrderId());

			sellOrder.setRealCardQuantity(cardQuantity+"");

			sellOrder.setModifyTime(DateUtil.getCurrentTime());

			sellOrder.setModifyUser(orderReadyDTO.getLoginUserId());
			SellOrderExample orderEx=new SellOrderExample();
			orderEx.createCriteria().andOrderIdEqualTo(orderReadyDTO.getOrderId());
			sellOrderMapper.updateByExampleSelective(sellOrder, orderEx);
//			orderBO.updateByPrimaryKeySelective(sellOrder);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("系统异常");
		}

	}
	public void orderListReady(SellOrderList sellOrderList,
			OrderReadyDTO orderReadyDTO) throws Exception {
		List<SellOrderCardList> sellOrderCardList = new ArrayList<SellOrderCardList>();
		if (OrderConst.FACE_VALUE_TYPE_NOT_STATIC.equals(sellOrderList
				.getFaceValueType())) {
			throw new Exception("不记名在不支持不固定面额");
		} else if (OrderConst.FACE_VALUE_TYPE_STATIC.equals(sellOrderList
				.getFaceValueType())) {
			orderReadyDTO.setOrderListId(sellOrderList.getOrderListId());
			sellOrderCardList = entityStockMapper.selectCardDetailFororderReadyByFixDetail(orderReadyDTO);
		}
		Integer reallyAmount = 0;
		try {
			reallyAmount = Integer.parseInt(sellOrderList.getRealAmount());
		} catch (NumberFormatException e) {
			reallyAmount = 0;
		}
		List<String> cardNoList = new ArrayList<String>();
		EntityStockExample enStock=new EntityStockExample();
		for (SellOrderCardList sellOrderCardList_temp : sellOrderCardList) {
			reallyAmount++;
			EntityStock entityStock = new EntityStock();

			entityStock.setCardNo(sellOrderCardList_temp.getCardNo());
			cardNoList.add(sellOrderCardList_temp.getCardNo());
			entityStock.setEntityId(orderReadyDTO.getProcessEntityId());
			entityStock.setStockState(OrderConst.CARD_STOCK_READY_OUT);
			sellOrderCardList_temp.setCreateTime(DateUtil.getCurrentTime());
			sellOrderCardList_temp
					.setCreateUser(orderReadyDTO.getLoginUserId());
			sellOrderCardList_temp.setOrderId(sellOrderList.getOrderId());
			sellOrderCardList_temp.setOrderListId(sellOrderList
					.getOrderListId());
			sellOrderCardList_temp.setOrderListId(sellOrderList
					.getOrderListId());
			sellOrderCardList_temp
					.setCardState(OrderConst.MAKE_CARD_STATE_SUCCESS);

			sellOrderCardList_temp.setModifyTime(DateUtil.getCurrentTime());
			sellOrderCardList_temp
					.setModifyUser(orderReadyDTO.getLoginUserId());
			sellOrderCardList_temp
					.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
//			entityStockList.add(entityStock);
			// 记录库存操作日志
			sellOrderCardListMapper.insert(sellOrderCardList_temp);
			enStock.createCriteria().andCardNoEqualTo(entityStock.getCardNo());
			entityStockMapper.updateByExample(entityStock, enStock);
			 entityStockService.addStockOperaterRecord(sellOrderCardList_temp.getCardNo(), orderReadyDTO
					.getOrderId(), orderReadyDTO.getProcessEntityId(),
					"3", OrderConst.ORDER_STATE_ORDER_READY,
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
					OrderConst.CARD_STOCK_OPERATE_TYPE_READY_OUT, orderReadyDTO
							.getLoginUserId());
		}

		

		sellOrderList.setRealAmount(reallyAmount.toString());
		sellOrderList.setModifyTime(DateUtil.getCurrentTime());
		sellOrderList.setModifyUser(orderReadyDTO.getLoginUserId());
		SellOrderListExample example =new SellOrderListExample();
		example.createCriteria().andOrderListIdEqualTo(sellOrderList.getOrderListId())
				.andDataStateEqualTo("1");
		sellOrderListMapper.updateByExampleSelective(sellOrderList, example);
	}
	
	public void orderCardListReady(List<SellOrderCardList> sellOrderCardList,
			OrderReadyDTO orderReadyDTO) throws Exception {
		List<String> cardNoList = Arrays.asList(orderReadyDTO.getCardNoArray());

		SellOrderCardList sellOrderCardList_temp = sellOrderCardList.get(0);
		EntityStock entityStock = new EntityStock();
		/*
		 * entityStock.setCardNo(cardNoList.get(i));
		 * entityStock.setEntityId(orderReadyDTO.getProcessEntityId());
		 */
		entityStock.setStockState(OrderConst.CARD_STOCK_READY_OUT);

		SellOrderCardList sellOrderCardList_temp2 = new SellOrderCardList();
		sellOrderCardList_temp2.setCardholderId(sellOrderCardList_temp
				.getCardholderId());
		sellOrderCardList_temp2.setOrderCardListId(sellOrderCardList_temp
				.getOrderCardListId());
		sellOrderCardList_temp2
				.setCardState(OrderConst.MAKE_CARD_STATE_SUCCESS);
		sellOrderCardList_temp2.setCardNo(cardNoList.get(0));
		sellOrderCardList_temp2.setModifyTime(DateUtil.getCurrentTime());
		sellOrderCardList_temp2.setModifyUser(orderReadyDTO.getLoginUserId());
		SellOrderCardListExample sellOrderEx = new SellOrderCardListExample();
		sellOrderEx.createCriteria().andOrderCardListIdEqualTo(
				sellOrderCardList_temp2.getOrderCardListId());

		sellOrderCardListMapper.updateByExampleSelective(
				sellOrderCardList_temp2, sellOrderEx);
		AccCardInfo acc = new AccCardInfo();
		acc.setCardholderId(sellOrderCardList_temp2.getCardholderId());
		AccCardInfoExample accEx = new AccCardInfoExample();
		accEx.createCriteria().andCardNoEqualTo(
				sellOrderCardList_temp2.getCardNo());
		accCardInfoMapper.updateByExampleSelective(acc, accEx);
		EntityStockExample stockEx = new EntityStockExample();
		stockEx.createCriteria().andCardNoEqualTo(cardNoList.get(0));
		entityStockMapper.updateByExampleSelective(entityStock, stockEx);
		/*
		 * orderBO.batchUpdateSellOrderCardList(sellOrderCardLists_temp);
		 * orderBO.batchUpdateAccCardInfo(sellOrderCardLists_temp);
		 * orderBO.updateEntityStockByPrimaryKey(entityStockList);
		 */

		// 记录库存操作日志
		 entityStockService.addStockOperaterRecord(cardNoList.get(0), orderReadyDTO
		 .getOrderId(), orderReadyDTO.getProcessEntityId(),
		 "3", OrderConst.ORDER_STATE_ORDER_READY,
		 OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
		 OrderConst.CARD_STOCK_OPERATE_TYPE_READY_OUT, orderReadyDTO
		 .getLoginUserId());

	}
	
	private void orderListReadyForChangeOrder(SellOrderList sellOrderList,
			OrderReadyDTO orderReadyDTO) throws Exception {
		ProductExample example = new ProductExample();
		example.createCriteria().andProductIdEqualTo(orderReadyDTO.getProductId()).andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
		List<Product> products = productMapper.selectByExample(example);
		CardholderDTO cardholderDTO = new CardholderDTO();
		if(null != products && products.size()>0){
			//如果订单中的产品是记名库存卡 则取原卡的持卡人信息
			if("3".equals(products.get(0).getOnymousStat())){
				cardholderDTO = getOrigCardHolder(orderReadyDTO.getOrderId());
			}			
		}
		
		List<SellOrderCardList> sellOrderCardList = new ArrayList<SellOrderCardList>();
//		if (OrderConst.FACE_VALUE_TYPE_NOT_STATIC.equals(sellOrderList
//				.getFaceValueType())) {
//			orderReadyDTO.setOrderListId(sellOrderList.getOrderListId());
//			sellOrderCardList = baseOrderDAO.queryForList(
//					"selectCardDetailFororderReadyByNotFixDetail",
//					orderReadyDTO);
//		} else if (OrderConst.FACE_VALUE_TYPE_STATIC.equals(sellOrderList
//				.getFaceValueType())) {
//			orderReadyDTO.setOrderListId(sellOrderList.getOrderListId());
//			sellOrderCardList = baseOrderDAO.queryForList(
//					"selectCardDetailFororderReadyByFixDetail", orderReadyDTO);
//		}
		List<String> cardNoList = new ArrayList<String>();
		SellOrderCardList sellOrderCardList_temp =new SellOrderCardList();
		SellOrderCardList sellOrderCardList_temp2 = new SellOrderCardList();
//		List<EntityStock> entityStockList = new ArrayList<EntityStock>();
			EntityStock entityStock = new EntityStock();
			entityStock.setCardNo(orderReadyDTO.getCardNoArray()[0]);
			cardNoList.add(orderReadyDTO.getCardNoArray()[0]);
			entityStock.setEntityId(orderReadyDTO.getProcessEntityId());
			entityStock.setStockState(OrderConst.CARD_STOCK_READY_OUT);
			if(null!=cardholderDTO && !"".equals(cardholderDTO.getCardholderId())){
				sellOrderCardList_temp2.setCardholderId(cardholderDTO.getCardholderId());
				sellOrderCardList_temp2.setFirstName(cardholderDTO.getFirstName());
			}
			sellOrderCardList_temp2
			.setOrderCardListId(commonsDAO
					.getNextValueOfSequenceBySequence("SEQ_SELL_ORDER_CARD_LIST"));
			sellOrderCardList_temp2.setCreateTime(DateUtil.getCurrentTime());
			sellOrderCardList_temp2
					.setCreateUser(orderReadyDTO.getLoginUserId());
			sellOrderCardList_temp2.setOrderId(sellOrderList.getOrderId());
			sellOrderCardList_temp2.setOrderListId(sellOrderList
					.getOrderListId());
			sellOrderCardList_temp2.setCardNo(cardNoList.get(0));
			sellOrderCardList_temp2
					.setCardState(OrderConst.MAKE_CARD_STATE_SUCCESS);

			sellOrderCardList_temp2.setModifyTime(DateUtil.getCurrentTime());
			sellOrderCardList_temp2
					.setModifyUser(orderReadyDTO.getLoginUserId());
			sellOrderCardList_temp2
					.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			sellOrderCardListMapper.insert(
					sellOrderCardList_temp2);
			AccCardInfo acc = new AccCardInfo();
			acc.setCardholderId(sellOrderCardList_temp2.getCardholderId());
			AccCardInfoExample accEx = new AccCardInfoExample();
			accEx.createCriteria().andCardNoEqualTo(
					sellOrderCardList_temp2.getCardNo());
			accCardInfoMapper.updateByExampleSelective(acc, accEx);
			EntityStockExample stockEx = new EntityStockExample();
			stockEx.createCriteria().andCardNoEqualTo(cardNoList.get(0));
			entityStockMapper.updateByExampleSelective(entityStock, stockEx);
//		orderBO.batchInsertSellOrderCardList(sellOrderCardList);
//		orderBO.batchUpdateAccCardInfo(sellOrderCardList);
//		orderBO.updateEntityStockByPrimaryKey(entityStockList);

		// 记录库存操作日志
		entityStockService.addStockOperaterRecord(cardNoList.get(0), orderReadyDTO
				.getOrderId(), orderReadyDTO.getProcessEntityId(),
				"3", OrderConst.ORDER_STATE_ORDER_READY,
				OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
				OrderConst.CARD_STOCK_OPERATE_TYPE_READY_OUT, orderReadyDTO
						.getLoginUserId());

	}
	
	private CardholderDTO getOrigCardHolder(String orderId){		
		CardholderDTO cardholder = new CardholderDTO();
		List<CardholderDTO> cardHolders = sellOrderOrigCardListMapper.getOrigCardHolderForChangeOrder(orderId);
		if(null!=cardHolders && cardHolders.size()>0){
			cardholder=cardHolders.get(0);
		}
		return cardholder;
}

}
