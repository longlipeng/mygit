package com.allinfinance.prepay.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.ActBatchMapper;
import com.allinfinance.prepay.mapper.svc_mng.BatchFileDetailMapper;
import com.allinfinance.prepay.mapper.svc_mng.BatchSumMapper;
import com.allinfinance.prepay.mapper.svc_mng.CardRelAccountMapper;
import com.allinfinance.prepay.mapper.svc_mng.CardholderRelAccMapper;
import com.allinfinance.prepay.mapper.svc_mng.EntityStockMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderCardListMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderMapper;
import com.allinfinance.prepay.model.ActBatch;
import com.allinfinance.prepay.model.BatchFileDetail;
import com.allinfinance.prepay.model.BatchSum;
import com.allinfinance.prepay.model.CardRelAccount;
import com.allinfinance.prepay.model.CardholderRelAcc;
import com.allinfinance.prepay.model.CardholderRelAccExample;
import com.allinfinance.prepay.model.EntityStock;
import com.allinfinance.prepay.model.EntityStockExample;
import com.allinfinance.prepay.model.MngAccountInfoExample;
import com.allinfinance.prepay.model.SellOrder;
import com.allinfinance.prepay.model.SellOrderCardList;
import com.allinfinance.prepay.model.SellOrderCardListExample;
import com.allinfinance.prepay.model.SellOrderExample;
import com.allinfinance.prepay.order.business.bo.OrderBO;
import com.allinfinance.prepay.order.business.bo.OrderBaseQueryBO;
import com.allinfinance.prepay.socket.SocketSend;
import com.allinfinance.prepay.utils.Config;
import com.allinfinance.prepay.utils.DataBaseConstant;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.OrderConst;
import com.allinfinance.prepay.utils.ParseToXML;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.prepay.utils.ResponseCode;
import com.allinfinance.prepay.utils.XmlDom;
import com.allinfinance.service.BatchParamInterface;
import com.allinfinance.univer.businessbatch.dto.ActBatchDTO;
import com.allinfinance.univer.businessbatch.dto.BatchFileDTO;
import com.allinfinance.univer.businessbatch.dto.BatchFileDetailDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;

@Service
public class SubmitOrderServiceImpl implements SubmitOrderService {

	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private OrderBO orderBO;
	@Autowired
	private EntityStockMapper entityStockMapper;
	@Autowired
	private SellOrderMapper sellOrderMapper;
	@Autowired
	private EntityStockService entityStockService;
	@Autowired
	private OrderBaseQueryBO orderBaseQueryBO;
	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private BatchFileDetailMapper batchFileDetailMapper;
	@Autowired
	private BatchSumMapper batchSumMapper;
	@Autowired
	private ActBatchMapper actBatchMapper;
	@Autowired
	private CardRelAccountMapper cardRelAccountMapper;
	@Autowired
	private SellOrderCardListMapper sellOrderCardListMapper;
	@Autowired
	private CardholderRelAccMapper cardholderRelAccMapper;
	@Override
	public void submitOrderAtInput(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException {
		// TODO Auto-generated method stub
		try {
			orderBO.orderFlowNexNode(sellOrderInputDTO.getOrderId(),
					OrderConst.ORDER_STATE_UNCERTAIN,
					sellOrderInputDTO.getLoginUserId(),
					sellOrderInputDTO.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, "",
					OrderConst.ORDER_FLOW_INPUT);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败");
		}

	}

	@Override
	public void submitOrderAtConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			// TODO Auto-generated method stub
			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_ORDER_READY,
					sellOrderDTO.getLoginUserId(),
					sellOrderDTO.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
					sellOrderDTO.getOperationMemo(),
					OrderConst.ORDER_FLOW_CONFIRMATION);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败");
		}
	}

	@Override
	public void submitOrderAtReady(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		// TODO Auto-generated method stub
		try {
			if(OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD.equals(sellOrderDTO.getOrderType())){
				
				//原有卡销户
				EntityStock entityStcok = new EntityStock();
				entityStcok.setCardNo(sellOrderDTO.getCardNo());
				entityStcok.setEntityId(sellOrderDTO.getProcessEntityId());
				entityStcok.setStockState(OrderConst.CARD_STOCK_DESTORY);
				entityStcok.setModifyTime(DateUtil.getCurrentTime24());
				entityStcok.setModifyUser(sellOrderDTO.getLoginUserId());
				EntityStockExample ex=new EntityStockExample();
				ex.createCriteria().andCardNoEqualTo(entityStcok.getCardNo());
				entityStockMapper.updateByExampleSelective(entityStcok, ex);
				entityStockService.addStockOperaterRecord(sellOrderDTO.getCardNo(),
						sellOrderDTO.getOrderId(), sellOrderDTO
								.getDefaultEntityId(),"3",
						OrderConst.ORDER_STATE_ORDER_READY,
						OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
						OrderConst.CARD_STOCK_DESTORY, sellOrderDTO
								.getLoginUserId());
			}
			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_ORDER_SEND,
					sellOrderDTO.getLoginUserId(),
					sellOrderDTO.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
					sellOrderDTO.getOperationMemo(),
					OrderConst.ORDER_FLOW_READY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败!");
		}

	}

	@Override
	public void submitOrderAtHandOut(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_ORDER_WAIT_SEND_CONFIRM,
					sellOrderDTO.getLoginUserId(),
					sellOrderDTO.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
					sellOrderDTO.getOperationMemo(), OrderConst.ORDER_FLOW_SEND);
			EntityStockExample ex = new EntityStockExample();
			ex.createCriteria()
					.andEntityIdEqualTo(sellOrderDTO.getDefaultEntityId())
					.andCardNoEqualTo(sellOrderDTO.getCardNo());
			EntityStock entityStock = new EntityStock();
			entityStock.setStockState("3");
			entityStockMapper.updateByExampleSelective(entityStock, ex);
			//卡户绑定   --现在版本废除 卡户绑定关系
//			SellOrderCardListExample orderList=new SellOrderCardListExample();
//			orderList.createCriteria().andOrderIdEqualTo(sellOrderDTO.getOrderId())
//				.andCardNoEqualTo(sellOrderDTO.getCardNo()).andDataStateEqualTo("1");
//			List<SellOrderCardList> cardlist=sellOrderCardListMapper.selectByExample(orderList);
//			CardholderRelAccExample CardholderRelEx=new CardholderRelAccExample();
//			CardholderRelEx.createCriteria().andCardhodlerIdEqualTo(cardlist.get(0).getCardholderId())
//				.andDataStateEqualTo("1");
//			List<CardholderRelAcc> cardholderRelAcclist=cardholderRelAccMapper.selectByExample(CardholderRelEx);
//			CardRelAccount cardRelAccount=new CardRelAccount();
//			cardRelAccount.setAccountNo(cardholderRelAcclist.get(0).getAccountNo());
//			cardRelAccount.setAccType(cardholderRelAcclist.get(0).getAccType());
//			cardRelAccount.setCardNo(sellOrderDTO.getCardNo());
//			cardRelAccount.setCreateTime(DateUtil.getCurrentTime());
//			cardRelAccount.setCreateUser(Config.getUserId());
//			cardRelAccount.setUpdateTime(DateUtil.getCurrentTime());
//			cardRelAccount.setUpdateUser(Config.getUserId());
//			cardRelAccount.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
//			cardRelAccountMapper.insert(cardRelAccount);
			SellOrder sellOrder = new SellOrder();
			ReflectionUtil.copyProperties(sellOrderDTO, sellOrder);
			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());
			SellOrderExample orderEx = new SellOrderExample();
			orderEx.createCriteria().andOrderIdEqualTo(
					sellOrderDTO.getOrderId());
			sellOrderMapper.updateByExampleSelective(sellOrder, orderEx);
			// 记录库存操作日志
			entityStockService.addStockOperaterRecord(sellOrderDTO.getCardNo(),
					sellOrderDTO.getOrderId(),
					sellOrderDTO.getDefaultEntityId(), "3",
					OrderConst.ORDER_STATE_ORDER_SEND,
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
					OrderConst.CARD_STOCK_OUT, sellOrderDTO.getLoginUserId());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizServiceException("修改状态失败！");
		}

	}

	public void submitOrder(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {

			// 记录订单流程
			logger.debug("==>before write orderFlow!");
			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_ORDER_SEND_CONFIRM,
					sellOrderDTO.getLoginUserId(),
					sellOrderDTO.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
					sellOrderDTO.getOperationMemo(),
					OrderConst.ORDER_FLOW_SEND_CONFIRM);
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("提交订单失败 ");
		}

	}
	

	public void submitOrderSendConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			SellOrder sellOrder = new SellOrder();
			/* sellOrder.setOrderId(sellOrderDTO.getOrderId()); */
			sellOrder.setBatchNo(sellOrderDTO.getBatchNo());
			sellOrder.setInitActStat(sellOrderDTO.getInitActStat());
			if (null != sellOrderDTO.getOrderState()
					&& !"".equals(sellOrderDTO.getOrderState())) {
				sellOrder.setOrderState(sellOrderDTO.getOrderState());
			}
			SellOrderExample ex = new SellOrderExample();
			ex.createCriteria().andOrderIdEqualTo(sellOrderDTO.getOrderId());
			sellOrderMapper.updateByExampleSelective(sellOrder, ex);
			
			/*// 记录订单流程
			logger.debug("==>before write orderFlow!");
			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
				OrderConst.ORDER_STATE_ORDER_SEND_CONFIRM,
				sellOrderDTO.getLoginUserId(),
				sellOrderDTO.getDefaultEntityId(),
				OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
				sellOrderDTO.getOperationMemo(),
				OrderConst.ORDER_FLOW_ORDER_IMMDIATELY_CREDIT);*/
		} catch (Exception e) {

			this.logger.error(e.getMessage());
			throw new BizServiceException("更新订单状态失败");
		}

	}

	@Override
	public void submitOrderAtSendConfirm(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		if(null!=sellOrderDTO.getOrderType()&&!"".equals(sellOrderDTO.getOrderType())
				&&OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN.equalsIgnoreCase(sellOrderDTO.getOrderType())){
			try {
				//不记名卡记录流程
				orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
						OrderConst.ORDER_STATE_ORDER_SEND_CONFIRM, sellOrderDTO
								.getLoginUserId(), sellOrderDTO
								.getDefaultEntityId(),
						OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION, sellOrderDTO
								.getOperationMemo(),
						OrderConst.ORDER_FLOW_SEND_CONFIRM);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BizServiceException("订单配送确定失败");
			}
		}
		else{
			List<SellOrderListDTO> list = getOrderDetail(sellOrderDTO);
			insertBatchInfo(sellOrderDTO.getOrderId(),list,"03");
	//		Map<String, String> map = new LinkedHashMap<String, String>();
	//		String rspCode ="";
	//		if(sellOrderDTO.getCvn2().equals(Config.getChannel())){
	//			map.put("TxnCode", "J011");
	//			map.put("cardNo", sellOrderDTO.getCardNo());
	//			map.put("channel", sellOrderDTO.getCvn2());
	//			map.put("txnAmt",sellOrderDTO.getBatchNo());
	//			map.put("branchId", sellOrderDTO.getInvoiceDate());
	//			map.put("reqseqNo", sellOrderDTO.getRefOrder());
	//			map.put("userId", Config.getUserId());
	//			String xml = ParseToXML.converterXML(map);
	//			String msg = SocketSend.SendToCore(xml);
	//			Map<String, String> Xmlmap = XmlDom.parse2(msg);
	//			rspCode = Xmlmap.get("RESP_CODE");
	//		}else{
	//			 map.put("TxnCode","P001");//交易码
	//			 map.put("cardNo",sellOrderDTO.getCardNo());//卡号
	//			 map.put("channel",sellOrderDTO.getCvn2());//报文
	//			 String xml=ParseToXML.converterXML(map);
	//			 String msg=SocketSend.SendToCore(xml);
	//			 Map<String, String> Xmlmap=XmlDom.parse2(msg);
	//			 rspCode=Xmlmap.get("RESP_CODE");
	//		}
	//		if (rspCode.equals("00")) {
				
				//订单激活
	//			insertActive(sellOrderDTO);
	//		}else{
	//			throw new BizServiceException("订单配送确定失败！");
	//		}
		}
		
		submitOrder(sellOrderDTO);
		submitOrderSendConfirm(sellOrderDTO);
	}
	
	@Override
	public void submitOrderAtSendConfirmByRecharge(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		submitOrder(sellOrderDTO);
		submitOrderSendConfirm(sellOrderDTO);
	}
	@Override
	public void insertActive(SellOrderDTO sellOrderDTO) throws BizServiceException{
		//修改订单激活状态
		SellOrder sellOrder = new SellOrder();
		sellOrder.setOrderId(sellOrderDTO.getOrderId());
		sellOrder.setModifyTime(DateUtil.getCurrentTime());
		// 订单修改人
		sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());
		sellOrder.setInitActStat(DataBaseConstant.ORDER_ACT_STATE_ACT);
		/* 订单支付信息修改 */
		sellOrder.setPayChannel(sellOrderDTO.getPayChannel());
		sellOrder.setPayDetails(sellOrderDTO.getPayDetails());

		/* 订单银行信息修改 */
		sellOrder.setIntoBankId(sellOrderDTO.getIntoBankId());
		SellOrderExample ex=new SellOrderExample();
		ex.createCriteria().andOrderIdEqualTo(sellOrderDTO.getOrderId());
		sellOrderMapper.updateByExampleSelective(sellOrder, ex);
		//激活
		List<SellOrderListDTO> list = getOrderDetail(sellOrderDTO);
		insertBatchInfo(sellOrderDTO.getOrderId(),list,"05");
		//记录订单流程
		try {
			orderBO.orderFlow(sellOrderDTO.getOrderId(),
					sellOrderDTO.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
					OrderConst.ORDER_FLOW_ORDER_ACTIVE, "", "0000");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizServiceException("记录流程错");
		}
	}
	
	public void insertBatchInfo(String orderId,
			List<SellOrderListDTO> batchList,String batchType) throws BizServiceException {
		BatchFileDTO batchFileDTO = new BatchFileDTO();
		try {
			batchFileDTO.setBatchType(batchType);
			batchFileDTO.setOrderId(orderId);
			batchFileDTO.setBatchNo(commonsDAO
					.getNextValueOfSequence("TB_BATCH_FILE"));
			batchFileDTO.setSmltDate(DateUtil.getCurrentDateStr());
			batchFileDTO.setBatchState("01");
			long totAmt = 0;

			List<BatchFileDetailDTO> batchFiles = new ArrayList<BatchFileDetailDTO>();
			for (BatchParamInterface obj : batchList) {
				BatchFileDetailDTO batchFileDetailDTO = new BatchFileDetailDTO();
				batchFileDetailDTO.setTxnSeq(commonsDAO
						.getNextValueOfSequence("TB_TXN_SEQ"));
				batchFileDetailDTO.setBatchNO(batchFileDTO.getBatchNo());
				batchFileDetailDTO.setSmltDate(batchFileDTO.getSmltDate());
				batchFileDetailDTO
						.setTxnState("00");
				batchFileDetailDTO.setBatchState(BatchFileDTO.BATCH_STATE_INIT);
				batchFileDetailDTO.setTxnAmt(obj.calcAmt()); // 通过接口获取金额字段
				obj.setSeq(batchFileDetailDTO.getTxnSeq()); // 通过接口设置流水号字段
				batchFileDetailDTO.setBatchType(batchFileDTO.getBatchType());
				batchFiles.add(batchFileDetailDTO);

				long amt = Long.parseLong(batchFileDetailDTO.getTxnAmt());
				totAmt += amt;

			}
			if(batchType.equals("05")){
				insertBatch(batchList);
			}else if(batchType.equals("03")){
				insertBatchs(batchList);
			}
			
			
			BatchFileDetail batchFileDetail=new BatchFileDetail();
			ReflectionUtil.copyProperties(batchFiles.get(0), batchFileDetail);
			batchFileDetail.setBatchNo(batchFiles.get(0).getBatchNO());
			batchFileDetailMapper.insert(batchFileDetail);
//			databaseDAO.batchInsert("BATCH_DETAIL.insertBatchDetail",
//					batchFiles);

			// batchFileDTO.setTotAmt("");
			batchFileDTO.setTotCnt(batchList.size() + "");
			batchFileDTO.setSucAmt("0");
			batchFileDTO.setSucCnt("0");
			batchFileDTO.setFailAmt("0");
			batchFileDTO.setFailCnt("0");
			batchFileDTO.setTotAmt(totAmt + "");
			batchFileDTO.setCreateTime(DateUtil.getCurrentTime());
			insertBatchFileSum(batchFileDTO);
			
			/**
			 * 是否需要进行约束
			 */
//				TblOrderBatchConstrain record = new TblOrderBatchConstrain();
//				record.setOrderId(orderId);
//				record.setBatchNo(batchFileDTO.getBatchNo());
//				record.setCreateTime(DateUtil.getCurrentDateStr());
//				tblOrderBatchConstrainDAO.insert(record);

		} catch (BizServiceException b) {
			logger.error(b.getMessage());
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("批量充值失败");
		}
	}
	
	public void insertBatch(List<? extends BatchParamInterface> businessList)
			throws BizServiceException {
//		List<ActBatchDTO> list = new ArrayList<ActBatchDTO>();
//		for (BatchParamInterface obj : businessList) {
			SellOrderListDTO dto = (SellOrderListDTO) businessList.get(0);
			ActBatch actBatchDTO = new ActBatch();
			actBatchDTO.setTxnType("0003");
			actBatchDTO.setTxnSeq(dto.fetchSeq());
			actBatchDTO.setSettleDt(DateUtil.getCurrentDateStr());
			actBatchDTO.setTxnAmt("0");
			actBatchDTO.setTxnState(BatchFileDetailDTO.TXN_STATE_INIT);
			actBatchDTO.setCardNo(dto.getCardNo());
			actBatchDTO.setCardState("1");
			actBatchDTO.setRecCrtTs(DateUtil.getCurrentTime());
			actBatchDTO.setUpdCrtTs(DateUtil.getCurrentTime());
			actBatchMapper.insert(actBatchDTO);
			
//			list.add(actBatchDTO);
//		}
			
			
//		databaseDAO.batchInsert("ACT.insertActTxn", list);
	}
	
	public void insertBatchs(List<? extends BatchParamInterface> businessList)
			throws BizServiceException {
//		List<ActBatchDTO> list = new ArrayList<ActBatchDTO>();
//		for (BatchParamInterface obj : businessList) {
			SellOrderListDTO dto = (SellOrderListDTO) businessList.get(0);
			BatchFileDetailDTO batchFileDetailDTO = new BatchFileDetailDTO();
			batchFileDetailDTO.setTxnSeq(dto.fetchSeq());
			batchFileDetailDTO.setSmltDate(DateUtil.getCurrentDateStr());
			batchFileDetailDTO.setBatchType("03");
			batchFileDetailDTO.setTxnAmt(dto.getFaceValue());
			batchFileDetailDTO.setTxnState(BatchFileDetailDTO.TXN_STATE_INIT);
			batchFileDetailDTO.setCardNo(dto.getCardNo());
			batchFileDetailDTO.setAccType(dto.getServiceId());
			batchFileDetailDTO.setExpDate(dto.getValidityPeriod());
			batchFileDetailDTO.setActFlag(dto.getActFlag());
			batchFileDetailDTO.setRecCrtTs(DateUtil.getCurrentTime());
			batchFileDetailDTO.setRecUpdTs(DateUtil.getCurrentTime());
			batchFileDetailMapper.insertRechargeActTxn(batchFileDetailDTO);
			
//			list.add(actBatchDTO);
//		}
			
			
//		databaseDAO.batchInsert("ACT.insertActTxn", list);
	}
	
	private void insertBatchFileSum(BatchFileDTO batchFileDto)
			throws BizServiceException {
		try {
			BatchSum sum = new BatchSum();
			BeanUtils.copyProperties(sum, batchFileDto);
			batchSumMapper.insert(sum);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("批量处理失败");
		}
	}
	
	// 获取订单详细信息
		private List<SellOrderListDTO> getOrderDetail(SellOrderDTO sellOrderDTO)
				throws BizServiceException {
			List<SellOrderListDTO> list;
			try {
				SellOrderExample sellOrderExample = new SellOrderExample();
				sellOrderExample.createCriteria().andOrderIdEqualTo(
						sellOrderDTO.getOrderId()).andDataStateEqualTo("1");

				logger.debug("==>befor Recharge!");
				if (sellOrderDTO.getInitActStat() == null
						|| sellOrderDTO.getInitActStat().equals("")) {
					sellOrderDTO.setInitActStat("0");
				}
				
				List<SellOrderListDTO> sellOrderList = orderBaseQueryBO
						.getSellSignCardListByOrderId(sellOrderDTO.getOrderId());
				list = new ArrayList<SellOrderListDTO>();
				for (SellOrderListDTO sellOrderListdto : sellOrderList) {
					sellOrderListdto.setActFlag(sellOrderDTO.getInitActStat());
					list.add(sellOrderListdto);
				}
			} catch (BizServiceException b) {
				throw b;
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				throw new BizServiceException("获取订单详细信息失败！");
			}
			return list;
		}

	@Override
	public void confirmAtOrderPayment(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		sellOrderDTO.setPaymentState("2");
		sellOrderDTO.setModifyTime(DateUtil.getCurrentTime());
		sellOrderDTO.setModifyUser(sellOrderDTO.getLoginUserId());
		SellOrder sellOrder = new SellOrder();
		ReflectionUtil.copyProperties(sellOrderDTO, sellOrder);
		sellOrder.setOrderState(null);

		SellOrderExample ex = new SellOrderExample();
		ex.createCriteria().andOrderIdEqualTo(sellOrderDTO.getOrderId());
		sellOrderMapper.updateByExampleSelective(sellOrder, ex);
		// sellOrderDAO.updateByPrimaryKeySelective(sellOrder);

		orderBO.orderFlow(sellOrderDTO.getOrderId(),
				sellOrderDTO.getDefaultEntityId(),
				OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
				OrderConst.ORDER_FLOW_ORDER_PAYMENT, sellOrderDTO.getMemo(),
				sellOrderDTO.getModifyUser());

	}

	@Override
	public void submitOrderForPayment(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		// TODO Auto-generated method stub
		// 记录订单流程
		logger.debug("==>before write orderFlow!");
		orderBO.orderFlow(sellOrderDTO.getOrderId(),
				sellOrderDTO.getDefaultEntityId(),
				OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
				OrderConst.ORDER_FLOW_ORDER_SUBMIT,
				sellOrderDTO.getOperationMemo(), sellOrderDTO.getLoginUserId());

		this.updateOrderState(sellOrderDTO);
	}

	// 更新订单状态
	private void updateOrderState(SellOrderDTO sellOrderDTO) {
		SellOrder sellOrder = new SellOrder();
//		sellOrder.setOrderId(sellOrderDTO.getOrderId());
		sellOrder.setModifyTime(DateUtil.getCurrentTime());
		// 订单修改人
		sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());
		/* 订单支付信息修改 */
		sellOrder.setPayChannel(sellOrderDTO.getPayChannel());
		sellOrder.setPayDetails(sellOrderDTO.getPayDetails());
		/* 订单银行信息修改 */
		sellOrder.setIntoBankId(sellOrderDTO.getIntoBankId());
		sellOrder.setPaymentState("1");
		SellOrderExample ex = new SellOrderExample();
		ex.createCriteria().andOrderIdEqualTo(sellOrderDTO.getOrderId());
		sellOrderMapper.updateByExampleSelective(sellOrder, ex);
//		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
	}

}
