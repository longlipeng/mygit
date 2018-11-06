package com.huateng.univer.order.business.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.cardcompany.CardCompanyDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderMakeCardDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.AccBatchFileDAO;
import com.huateng.framework.ibatis.dao.CardCompanyDAO;
import com.huateng.framework.ibatis.dao.CardSerialNumberDAO;
import com.huateng.framework.ibatis.dao.ProductCardBinDAO;
import com.huateng.framework.ibatis.dao.ProductDAO;
import com.huateng.framework.ibatis.dao.ProductServiceDAO;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.model.AccBatchFile;
import com.huateng.framework.ibatis.model.AccBatchFileKey;
import com.huateng.framework.ibatis.model.CardCompany;
import com.huateng.framework.ibatis.model.CardCompanyExample;
import com.huateng.framework.ibatis.model.Product;
import com.huateng.framework.ibatis.model.ProductCardBin;
import com.huateng.framework.ibatis.model.ProductService;
import com.huateng.framework.ibatis.model.ProductServiceExample;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderCardListExample;
import com.huateng.framework.ibatis.model.SellOrderFlow;
import com.huateng.framework.util.ConfigMakeCard;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.FtpUtil;
import com.huateng.framework.util.LUHNGenerator;
import com.huateng.framework.util.SFtpUtil;
import com.huateng.framework.util.StringUtil;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
import com.huateng.univer.batchfile.action.BatchFileActionInterface;
import com.huateng.univer.issuer.cardcompany.service.CardCompanyService;
import com.huateng.univer.issuer.order.biz.bo.StockOrderCommonService;
import com.huateng.univer.issuer.product.dao.ProdAccLayPackServiceDAO;
import com.huateng.univer.issuer.productcardbin.service.ProductCardBinService;
import com.huateng.univer.order.business.bo.OrderBO;
import com.huateng.univer.order.business.service.OrderCardListService;
import com.huateng.univer.order.business.service.OrderMakeCardService;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * 订单制卡文件下载service
 * 
 * @author xxl
 * 
 */
public class OrderMakeCardServiceImpl implements OrderMakeCardService {

	Logger logger = Logger.getLogger(this.getClass());
	private PageQueryDAO pageQueryDAO;
	private CommonsDAO commonsDAO;
	private BaseDAO baseDAO;
	private CardCompanyService cardCompanyService;
	private SellOrderDAO sellOrderDAO;
	private SellOrderCardListDAO sellOrderCardListDAO;
	private CardSerialNumberDAO cardSerialNumberDAO;
	private ProductDAO productDAO;
	private ProductServiceDAO productServiceDAO;
	private ProductCardBinDAO productCardBinDAO;
	private CardCompanyDAO cardCompanyDAO;
	private ProdAccLayPackServiceDAO prodAccLayPackServiceDAO;
	private StockOrderCommonService stockOrderCommonService;
	private OrderCardListService orderCardListService;
	private ProductCardBinService productCardBinService;
	private Java2ACCBusinessServiceImpl java2ACCBusinessService;
	private OrderBO orderBO;
	private BatchFileActionInterface fileBatchService;
	private AccBatchFileDAO accBatchFileDAO;

	public SellOrderMakeCardDTO getCardCompanyList(
			SellOrderMakeCardDTO sellOrderMakeCardDTO)
			throws BizServiceException {
		try {
			CardCompanyExample example = new CardCompanyExample();
			example.createCriteria().andEntityIdEqualTo(
					sellOrderMakeCardDTO.getDefaultEntityId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
					.andCardCompanyStateEqualTo(
							DataBaseConstant.DATA_STATE_NORMAL);
			List<CardCompanyDTO> cardCompanyDTOs = cardCompanyService
					.getCardCompanyDTOsByExample(example);

			sellOrderMakeCardDTO.setCardCompanyList(cardCompanyDTOs);

			return sellOrderMakeCardDTO;
		} catch (Exception e) {
			this.logger.error("获取制卡商失败！");
			throw new BizServiceException("获取制卡商失败！");
		}

	}

	
	/**
	 * 开户
	 */
	public void submitOrderForOpenAccountWithoutForUpdate(SellOrderDTO sellOrderDTO) throws BizServiceException{
		try {
			logger.debug("开始制卡开户");
			//准备批量制卡数据，并发送报文
			List<SellOrderListDTO> sellOrderList = cardReady(sellOrderDTO);
			String batchNo = null;
			try {
				batchNo = fileBatchService.fileBatch("businessMakeCard",sellOrderDTO
						.getOrderId(), sellOrderList);
			} catch (Exception e) {
				this.logger.error(e.getMessage());
			}
			logger.debug("Activate BatchNo :" + batchNo);
			SellOrderDTO sellOrder = new SellOrderDTO();
			sellOrder.setOrderId(sellOrderDTO.getOrderId());
			if(null!=batchNo && !"".equals(batchNo)){
				sellOrder.setBatchNo(batchNo);
				//更新订单状态
				updateOrderState(sellOrder);	
			}else{
				sellOrder.setOrderState(OrderConst.ORDER_STATE_CARDFILE_DOWNLOAD);
				//回滚订单状态
				updateOrderState(sellOrder);	
			}
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error("制卡开户,提交订单失败!!");
			throw new BizServiceException("提交订单失败 ");
		}
	}
	
	//制卡开户时，锁定订单，并更新订单状态为处理中
	public SellOrderDTO zhikaForUpdate(SellOrderDTO sellOrderDTO) throws BizServiceException{
		try {
			SellOrderDTO dto = orderBO.selectForUpdate(sellOrderDTO.getOrderId());
			//2014-12-09修改为常量
			//订单状态 3：制卡文件待生成
			//订单状态34：处理失败
			/*if(!"3".equals(dto.getOrderState()) && !"34".equals(dto.getOrderState())){
				throw new BizServiceException("订单已提交");
			}*/
			if(!OrderConst.ORDER_STATE_CARDFILE_DOWNLOAD.equals(dto.getOrderState()) 
					&& !OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL.equals(dto.getOrderState())){
				throw new BizServiceException("订单已提交");
			}
			SellOrder sellOrder = new SellOrder();
			sellOrder.setOrderId(dto.getOrderId());
			sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_PROCESSING);
			sellOrder.setCardCompanyId(sellOrderDTO.getCardCompanyId());
			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);		
			
			return dto;
		} catch (BizServiceException b) {			
			throw b;
		} catch (Exception e) {	
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新订单状态失败");
		}
	}
	
	//生成卡号
	private List<SellOrderListDTO> cardReady(SellOrderDTO sellOrderDTO) throws BizServiceException{
		List<SellOrderListDTO> listDTOs = null;
		try {
			logger.debug("开始生成卡号");
			String loginUserId = sellOrderDTO.getLoginUserId();
			String entityId = sellOrderDTO.getDefaultEntityId();
			String orderId = sellOrderDTO.getOrderId();
			SellOrder sellOrder = stockOrderCommonService
			.getSellOrderForUpdate(orderId);
			Product product = productDAO.selectByPrimaryKey(sellOrder
					.getProductId());
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductId(sellOrder.getProductId());
			List<ServiceDTO> prodAccDTOList = prodAccLayPackServiceDAO
				.getProdAcctypeDTOs(productDTO);			
			List<String> cardNoList = null;
			List<SellOrderCardList> cardLists = null;
			if (OrderConst.ORDER_TYPE_ORDER_MAKE_CARD.equals(sellOrder
					.getOrderType())) {
				// 生成卡号
				cardNoList = this.generateCardNoForStockList(
						sellOrder, entityId, product);
			} else {
				
				cardLists = orderCardListService.getOrderCardList(orderId);
				// 生成卡号
				cardNoList = this.generateCardNoForSignOrder(cardLists,
						sellOrder, entityId, product);			
			}
			// 查询卡片信息
			listDTOs=this.getCardInfo(sellOrder, cardNoList,cardLists, product,
					prodAccDTOList,loginUserId);
			
			orderBO.orderFlowNexNode(sellOrderDTO.getOrderId(),
					OrderConst.ORDER_STATE_ORDER_PROCESSING,
					sellOrderDTO.getLoginUserId(),
					sellOrderDTO.getDefaultEntityId(),
					OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
					sellOrderDTO.getOperationMemo(),
					OrderConst.ORDER_FLOW_MAKECARD_MAKE);
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡号生成失败");
		}
		return listDTOs;
	}
	
	/**
	 * 为记名采购订单生产卡号
	 */
	private List<String> generateCardNoForSignOrder(
			List<SellOrderCardList> cardLists, SellOrder sellOrder,
			String entityId, Product product) throws Exception {
		List<String> cardNoList = new ArrayList<String>();
		ProductCardBin productCardBin = (ProductCardBin) baseDAO
				.queryForObject("PRODUCT.getProductCardBinSerialNumberForLock",
						product.getProductId());
		if (null == productCardBin) {
		    logger.error("产品：" + product.getProductId()
                    + "没有卡BIN,请为产品添加卡BIN！");
			throw new BizServiceException("产品：" + product.getProductId()
					+ "没有卡BIN,请为产品添加卡BIN！");
		} else if (!productCardBin.getEffect().equals("1")) {
		    logger.error("产品：" + product.getProductId()
                    + "没有生效的卡BIN！");
			throw new BizServiceException("产品：" + product.getProductId()
					+ "没有生效的卡BIN！");
		}
		String cardBin = productCardBin.getCardBin();
		String serialNumber = productCardBin.getSerialNumber();
		if (StringUtil.isEmpty(serialNumber)) {
			serialNumber = "0";
		}
		Integer serialNumberAdd = new Integer(serialNumber);
		for (SellOrderCardList cardList : cardLists) {
			serialNumberAdd += 1;
			String resultString = cardBin
					+ this.getFormatStr(serialNumberAdd.toString(), "0", 8,
							true);
			String cardNo = LUHNGenerator.generate(resultString);
			cardNoList.add(cardNo);
		}
		// 更新卡流水表流水号
		productCardBin.setSerialNumber(serialNumberAdd.toString());
		productCardBinDAO.updateByPrimaryKeySelective(productCardBin);
		return cardNoList;
	}
	
	private List<SellOrderListDTO> getCardInfo(SellOrder sellOrder,
			List<String> cardNoList,List<SellOrderCardList> cardList, Product product,
			List<ServiceDTO> prodAccDTOList,String loginUserId)
			throws Exception {
		// 账户
		StringBuffer accountBuffer = new StringBuffer();
		for (ServiceDTO accDto : prodAccDTOList) {
			ProductServiceExample example = new ProductServiceExample();
			example.createCriteria()
					.andProductIdEqualTo(product.getProductId())
					.andServiceIdEqualTo(accDto.getServiceId());
			example.setOrderByClause("REL_ID ASC");
			List<ProductService> productServices = productServiceDAO
					.selectByExample(example);
			ProductService productService = productServices.get(0);
		
			// 账户类型(服务ID)
			String accType = accDto.getServiceId();
			accountBuffer.append(getFormatStr(accType, " ", 8, false));
			// 有效期
			String expiryDate = accDto.getExpiryDate();
			accountBuffer.append(getFormatStr(expiryDate, " ", 8, false));
			// pos单笔消费上限(12)
			String maxTxn = productService.getMaxTxnAmt();
			accountBuffer.append(getFormatStr(maxTxn, " ", 12, false));
			// pos日累计上限(12)
			String maxDayTxn = productService.getMaxDayTxnAmt();
			accountBuffer.append(getFormatStr(maxDayTxn, " ", 12, false));
			// web单笔消费上限(12)
			String webMaxTxn = productService.getWebMaxTxnAmt();
			accountBuffer.append(getFormatStr(webMaxTxn, " ", 12, false));
			// web日累计上限(12)
			String webMaxDayTxn = productService.getWebMaxDayTxnAmt();
			accountBuffer.append(getFormatStr(webMaxDayTxn, " ", 12, false));			
			// 金额
			String amt = "";
			accountBuffer.append(getFormatStr(amt, " ", 12, false));
			// 可透支金额
			String maxamt = "";
			accountBuffer.append(getFormatStr(maxamt, " ", 12, false));
			// 分隔符
			accountBuffer.append(";");
		}

		List<SellOrderListDTO> list = new ArrayList<SellOrderListDTO>();		
		// 写入卡号+账户
		for (int i = 0 ; i < cardNoList.size() ; i++) {
			SellOrderListDTO sellOrderListDTO = new SellOrderListDTO();
			sellOrderListDTO.setOrderId(sellOrder.getOrderId());
			sellOrderListDTO.setUserId(loginUserId);
			sellOrderListDTO.setMakeCardType(sellOrder.getOrderType());
			// 卡号(19)
			sellOrderListDTO.setCardNo(cardNoList.get(i));
			// 8位有效期
			sellOrderListDTO.setExpDate(sellOrder.getValidityPeriod());
			// 验密
			sellOrderListDTO.setPinStat(product.getPinStat());
			// 无pin金额上限
			sellOrderListDTO.setNoPinLimit(product.getNoPinLimit());
			// 产品标识
			sellOrderListDTO.setProductId(product.getProductId());
			// 持卡人标识
			String cardholderFlag = "";
			if(null!=cardList && cardList.size() > 0){
				cardholderFlag = cardList.get(i).getCardholderId();
			}
			sellOrderListDTO.setCardHolderId(cardholderFlag);
			// 打印公司名
			// String companyName = (String)
			// baseDAO.queryForObject("CUSTOMER.selectCustomerPrintNameByCardHolder",cardholderFlag);
			String companyName = "";
			sellOrderListDTO.setCompanyName(companyName);
			// 打印姓名
			String firstName = "";
			if(null!=cardList && cardList.size() > 0){
				firstName = cardList.get(i).getFirstName();
			}
			sellOrderListDTO.setCardHolderName(firstName);
			// 充值次数
			String rechargTime = product.getRechargeTimes();
			if (StringUtil.isEmpty(rechargTime)) {
				rechargTime = "99999";
			}
			sellOrderListDTO.setRechargeTimes(rechargTime);
			// 消费次数
			String consumerTime = product.getConsumerTimes();
			if (StringUtil.isEmpty(consumerTime)) {
				consumerTime = "99999";
			}
			sellOrderListDTO.setConsumerTimes(consumerTime);
			// 面额
			sellOrderListDTO.setFaceValue(sellOrder.getFaceValue());
			// 所属机构
			sellOrderListDTO.setEntityId(product.getEntityId());
			
			// 重复添加账户信息
			sellOrderListDTO.setAccountInfo(accountBuffer.toString());
			list.add(sellOrderListDTO);

		}
		return list;
	}
	
	/**
	 * 更新订单批次号、激活状态和订单状态
	 * @param sellOrderDTO
	 */
	public void updateOrderState(SellOrderDTO sellOrderDTO) throws BizServiceException{
		try {
			SellOrder sellOrder = new SellOrder();
			sellOrder.setOrderId(sellOrderDTO.getOrderId());
			sellOrder.setBatchNo(sellOrderDTO.getBatchNo());
			sellOrder.setInitActStat(sellOrderDTO.getInitActStat());
			if(null!=sellOrderDTO.getOrderState() && !"".equals(sellOrderDTO.getOrderState())){
				sellOrder.setOrderState(sellOrderDTO.getOrderState());
			}
			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新订单状态失败");
		}
	}
	
	
	/**
	 * 生成制卡文件
	 */
	public SellOrderMakeCardDTO downMakeCardFile(
			SellOrderMakeCardDTO sellOrderMakeCardDTO)
			throws BizServiceException {
		try {

			long time = System.currentTimeMillis();
			logger.debug("开始生成制卡文件");
			String loginUserId = sellOrderMakeCardDTO.getLoginUserId();
			String entityId = sellOrderMakeCardDTO.getDefaultEntityId();
			String cardCompanyId = sellOrderMakeCardDTO.getCardCompanyId();
			String orderId = sellOrderMakeCardDTO.getOrderId();
			// SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(orderId);
			// SellOrder sellOrder =
			// stockOrderCommonService.getSellOrderById(orderId);
			SellOrder sellOrder = stockOrderCommonService
					.getSellOrderForUpdate(orderId);
			CardCompany cardCompany = cardCompanyDAO
					.selectByPrimaryKey(cardCompanyId);
			Product product = productDAO.selectByPrimaryKey(sellOrder
					.getProductId());
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductId(sellOrder.getProductId());
			List<ServiceDTO> prodAccDTOList = prodAccLayPackServiceDAO
					.getProdAcctypeDTOs(productDTO);
			List<SellOrderCardList> cardLists = null;

			// 库存订单要生产卡号卡明细，
			if (OrderConst.ORDER_TYPE_ORDER_MAKE_CARD.equals(sellOrder
					.getOrderType())) {
				// 生成卡号
				List<String> cardNoList = this.generateCardNoForStockList(
						sellOrder, entityId, product);
				// 记录订单卡明细
				cardLists = this.insertOrderCardList(sellOrder, cardNoList,
						product, loginUserId);
				// cardLists = orderCardListService.getOrderCardList(orderId);
			} else {
				List<SellOrderCardList> failCardLists = new ArrayList<SellOrderCardList>();
				cardLists = orderCardListService.getOrderCardList(orderId);
				for (SellOrderCardList cardList : cardLists) {
					if (OrderConst.MAKE_CARD_STATE_FAILTURE.equals(cardList
							.getCardState())) {
						failCardLists.add(cardList);
					}
				}
				// 制卡失败被退回的订单
				if (failCardLists.size() > 0) {
					cardLists = this.generateCardNoForCardList(failCardLists,
							sellOrder, entityId, product);
				} else {
					// 新定的制卡
					cardLists = this.generateCardNoForCardList(cardLists,
							sellOrder, entityId, product);
				}
			}

			String makeCardFileName = this.getMakeCardFileName(product,
					orderId, loginUserId);

			// 写入制卡文件
			this.writeMakeCardFile(sellOrder, cardLists, product,
					prodAccDTOList, makeCardFileName);

			// 发送制卡文件通知报文
			this.sendMakeCard(product, cardCompany, makeCardFileName);

			// 记录订单
			sellOrder.setOrderState(OrderConst.ORDER_STATE_CARDGFILE_MAKING);
			sellOrder.setCardCompanyId(cardCompanyId);
			sellOrder.setModifyUser(loginUserId);
			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
			logger.debug(" make card total time:"
					+ ((System.currentTimeMillis() - time) / 1000)
					+ " seconds...");
			// 记录订单流程
			// 添加订单流程信息
			SellOrderFlow orderFlow = new SellOrderFlow();
			orderFlow.setOrderFlowId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));
			orderFlow.setOrderId(sellOrder.getOrderId());
			orderFlow.setEntityId(sellOrderMakeCardDTO.getDefaultEntityId());
			orderFlow.setOrderFlowNode(OrderConst.ORDER_FLOW_MAKECARD_MAKE);
			orderFlow
					.setOperateType(OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION);
			orderFlow.setCreateTime(DateUtil.getCurrentTime());
			orderFlow.setModifyTime(DateUtil.getCurrentTime());
			orderFlow.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			orderFlow.setCreateUser(sellOrderMakeCardDTO.getLoginUserId());
			orderFlow.setModifyUser(sellOrderMakeCardDTO.getLoginUserId());
			stockOrderCommonService.addOrderFlow(orderFlow);
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("生成制卡文件失败！");
		}
		return sellOrderMakeCardDTO;

	}

	private String getMakeCardFileName(Product product, String orderId,
			String loginUserId) throws Exception {

		// 5位平台号+10位sequence
		String serialNumber = commonsDAO
				.getNextValueOfSequence("TB_ORDER_BATCH_FILE");
		String platformNumber = ConfigMakeCard.getPlatfromNo();
		String batchNumber = platformNumber
				+ getFormatStr(serialNumber, "0", 10, true);

		// 记录订单制卡文件批次号
		stockOrderCommonService.insertOrUpdateOrderBatchFile(batchNumber,
				orderId, loginUserId);
		String fileName = batchNumber + ".dat";

		return fileName;
	}

	/**
	 * 获取c生成完成的制卡文件
	 */
	public SellOrderMakeCardDTO makeMakeCardFile(
			SellOrderMakeCardDTO sellOrderMakeCardDTO)
			throws BizServiceException {

		try {
			// 订单
			String orderId = sellOrderMakeCardDTO.getOrderId();
			SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(orderId);

			// String downFileName = "xxxxxxxxxxxxxxx.txt";
			String fileName = this.getFileName(sellOrder);			
			if (null == fileName || StringUtil.isEmpty(fileName)) {
				throw new BizServiceException("获取制卡反馈文件名失败！");
			}
			String downFileName = fileName;
			//String downFileNameKey = downFileName + "_key";
			downFileName = downFileName.trim();
			//downFileNameKey = downFileNameKey.trim();
			Map<String, List<byte[]>> fileMap = new HashMap<String, List<byte[]>>();
			List<byte[]> byteList = this.ftpGetMakeCardFile(downFileName);
			//List<byte[]> byteListKey = this.ftpGetMakeCardFile(downFileNameKey);
			fileMap.put(downFileName, byteList);
			//fileMap.put(downFileNameKey, byteListKey);
			sellOrderMakeCardDTO.setFileData(fileMap);

			// 记录订单
			sellOrder.setIsCreateCardFile(sellOrderMakeCardDTO.getLoginUserId());
			sellOrder.setModifyUser(sellOrderMakeCardDTO.getLoginUserId());
			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
			// 修改卡列表为制卡中
			/*
			 * if (OrderConst.ORDER_TYPE_ORDER_MAKE_CARD.equals(sellOrder
			 * .getOrderType())) { this.setStockOrderCardListState(
			 * OrderConst.MAKE_CARD_STATE_MAKING, orderId); } else { // 采购订单
			 * List<SellOrderCardList> orderCardLists = orderCardListService
			 * .getOrderCardList(orderId); this.setBuyOrderCardListState(
			 * OrderConst.MAKE_CARD_STATE_MAKING, orderCardLists); }
			 */
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("下载制卡文件失败！");
		}
		return sellOrderMakeCardDTO;
	}

	public String getFileName(SellOrder sellOrder) throws BizServiceException {
		String fileName = "" ;
		AccBatchFileKey key = new AccBatchFileKey();
		key.setOrderId(sellOrder.getOrderId());
		key.setBatchNo(sellOrder.getBatchNo());
		AccBatchFile accBatchFile = accBatchFileDAO.selectByPrimaryKey(key);
		if(null != accBatchFile && "03".equals(accBatchFile.getFileStat().trim())){
			if(Integer.parseInt(accBatchFile.getTotCnt().trim()) == Integer.parseInt(accBatchFile.getSucCnt().trim())){
				fileName = accBatchFile.getFileName();				
			}
		}
		return fileName;
	}
	
	
	/**
	 * 获取c生成完成的卡PIN文件
	 */
	public SellOrderMakeCardDTO makeCardPINFile(
			SellOrderMakeCardDTO sellOrderMakeCardDTO)
			throws BizServiceException {

		try {
			// 订单
			String orderId = sellOrderMakeCardDTO.getOrderId();
			SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(orderId);
			if (StringUtil.isEmpty(sellOrder.getIsCreateCardFile())
					|| OrderConst.FLAG_NO.equals(sellOrder
							.getIsCreateCardFile())) {
				throw new BizServiceException("订单：" + orderId
						+ "没有生成过制卡文件，请先下载制卡文件！");
			}

			String downFileName = this.sendMakeCardMake(orderId) + "_key";
			Map<String, List<byte[]>> fileMap = new HashMap<String, List<byte[]>>();
			List<byte[]> byteList = this.ftpGetMakeCardFile(downFileName);
			fileMap.put(downFileName, byteList);
			sellOrderMakeCardDTO.setFileData(fileMap);

			// 记录订单
			sellOrder.setIsCreatePinFile(sellOrderMakeCardDTO.getLoginUserId());
			sellOrder.setModifyUser(sellOrderMakeCardDTO.getLoginUserId());
			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("生成卡PIN文件失败！");
		}
		return sellOrderMakeCardDTO;
	}

	/**
	 * 发送制卡文件下载报文
	 * 
	 * @throws BizServiceException
	 */
	public String sendMakeCardMake(String orderId) throws BizServiceException {
		try {
			AccPackageDTO accPackageDTO=new AccPackageDTO();
			String backFileName = null;
			String batchNumber = stockOrderCommonService.getOrderFileBatchNumbert(orderId);
			accPackageDTO.setTxnDate(DateUtil.getCurrentDateStr());
			accPackageDTO.setTxnTime(DateUtil.getCurrenTime24());
			accPackageDTO.setBatchNo(batchNumber);
			// String txnCode = ConfigMakeCard.getTxnCodeBackFile();
			
			
			accPackageDTO=java2ACCBusinessService.sendMakeCardMessage(accPackageDTO);
			//响应码:"查询成功:00
			String resCode=accPackageDTO.getRespCode();
			if (!Const.RESPONSE_CODE.equals(resCode)) {
				throw new BizServiceException("查询报文响应失败！");
			}
			
			// 制卡处理状态：1，2，3
			String makeCardState = accPackageDTO.getMakeCardState();
			if (Const.MAKECARD_STATE_PROCESSING.equals(makeCardState)) {
				throw new BizServiceException("订单制卡中，请稍后再试！");
			} else if (Const.MAKECARD_STATE_FAILED.equals(makeCardState)) {
				/*
				 * 制卡文件处理失败时,重新发送制卡通知
				 * */
				repeatMakeCardOnDownload(orderId, batchNumber);
				throw new BizServiceException("订单制卡中，请稍后再试！");
			} else if (Const.MAKECARD_STATE_FINISH.equals(makeCardState)) {
				// 文件名
				backFileName = accPackageDTO.getFilePath();
			} else {
				throw new BizServiceException("查询报文响应失败！");
			}
			
			//之前的代码
/*			String backFileName = null;
			String batchNumber = stockOrderCommonService.getOrderFileBatchNumbert(orderId);
			HashMap map = new HashMap();
			map.put(CFunctionConstant.SWT_TXN_DATE, DateUtil.getCurrentDateStr());
			map.put(CFunctionConstant.SWT_TXN_TIME, DateUtil.getCurrenTime24());
			map.put(CFunctionConstant.REFERENCE_NO, batchNumber);
			String txnCode = ConfigMakeCard.getTxnCodeBackFile();
			OperationResult result = sendMakeCardMessage(txnCode, map, false);
			String txnstate = java2C.getTxnState(result);
			if (!Const.TXN_STATE_SUCCESS.equals(txnstate)) {
				logger.debug(txnstate);
				throw new BizServiceException("发送制卡查询报文失败！");
			}
			Map resultMap = java2C.getResultMap(result);
			// 响应码:"查询成功:00
			String resCode = java2C.getResCode(resultMap);
			logger.debug("返回结果：" + resCode);
			if (!Const.RESPONSE_CODE.equals(resCode)) {
				throw new BizServiceException("查询报文响应失败！");
			}
			// 制卡处理状态：1，2，3
			String makeCardState = (String) resultMap.get(CFunctionConstant.AUTH_CODE);
			if (Const.MAKECARD_STATE_PROCESSING.equals(makeCardState)) {
				throw new BizServiceException("订单制卡中，请稍后再试！");
			} else if (Const.MAKECARD_STATE_FAILED.equals(makeCardState)) {
				*//**
				 * 制卡文件处理失败时,重新发送制卡通知
				 * *//*
				repeatMakeCardOnDownload(orderId, batchNumber);
				throw new BizServiceException("订单制卡中，请稍后再试！");
			} else if (Const.MAKECARD_STATE_FINISH.equals(makeCardState)) {
				// 文件名
				backFileName = (String) resultMap
						.get(CFunctionConstant.CARD_NO);
			} else {
				throw new BizServiceException("查询报文响应失败！");
			}*/

			return backFileName;
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询报文响应失败！");
		}
	}

	/**
	 * 在制卡文件处理失败时，重新发送制卡通知报文，重制卡片
	 */

	private void repeatMakeCardOnDownload(String orderId, String batchNumber)
			throws BizServiceException, Exception {
		try {

			/**
			 * 由于已生成过制卡文件,依据订单号查询制卡商公钥
			 * */
			SellOrder sellOrder = stockOrderCommonService
					.getSellOrderById(orderId);
			String cardCompanyIdString = "";
			if (null != sellOrder && null != sellOrder.getCardCompanyId()) {
				cardCompanyIdString = sellOrder.getCardCompanyId();
			}
			CardCompany cardCompany = null;
			if (null != cardCompanyIdString && !cardCompanyIdString.equals("")) {
				cardCompany = cardCompanyDAO.selectByPrimaryKey(cardCompanyIdString);
			}
			Product product = new Product();
			if (null != sellOrder && null != sellOrder.getProductId()) {
				String productId = sellOrder.getProductId();
				product = productDAO.selectByPrimaryKey(productId);
			}
			/**
			 * 生成制卡文件名,并发送制卡通知报文
			 * */
			String makeCardFileName = batchNumber + ".dat";
			sendMakeCard(product, cardCompany, makeCardFileName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BizServiceException("下载制卡文件失败");
		}
	}

	/**
	 * 发送制卡文件通知报文
	 */
	private String sendMakeCard(Product product, CardCompany cardCompany,
			String makeCardFileName) throws Exception {

		
		AccPackageDTO accPackageDTO=new AccPackageDTO();
		// 交易类型
		accPackageDTO.setTxnCode(ConfigMakeCard.getTxnTypeFile());
		// 中心交易日期
		accPackageDTO.setTxnDate(DateUtil.getCurrentDateStr());
		// 中心交易时间
		accPackageDTO.setTxnTime(DateUtil.getCurrenTime24());
		// 中心清算日期
		accPackageDTO.setSettleDate(DateUtil.getCurrentDateStr());
		//渠道
		accPackageDTO.setChannel("00");
		// 制卡文件名
		accPackageDTO.setFilePath(getFormatStr(makeCardFileName," ", 50, false));
		
		String cardCompanyKeyString = "";
		if (null != product ) {
			if ("1".equals(product.getCardType())) {
				if (null != cardCompany
						&& null != cardCompany.getIcFileFormat()) {
					cardCompanyKeyString = cardCompany.getIcFileFormat();
				}
			} else {
				if (null != cardCompany
						&& null != cardCompany.getMsFileFormat()) {
					cardCompanyKeyString = cardCompany.getMsFileFormat();
				}
			}
		}
		
		accPackageDTO.setCardSeuriyStr(cardCompanyKeyString);
		
		accPackageDTO.setRespCode(getFormatStr("", " ", 2, false));
	
		
		accPackageDTO=java2ACCBusinessService.sendMakeCard(accPackageDTO);
		
		//以前的代码***********************************
		/*
		HashMap map = new HashMap();
		// 交易类型
		map.put(CFunctionConstant.TXN_TYPE, ConfigMakeCard.getTxnTypeFile());
		// 中心交易日期
		map.put(CFunctionConstant.SWT_TXN_DATE, DateUtil.getCurrentDateStr());
		// 中心交易时间
		map.put(CFunctionConstant.SWT_TXN_TIME, DateUtil.getCurrenTime24());
		// 中心清算日期
		map.put(CFunctionConstant.SWT_SETTLE_DATE, DateUtil.getCurrentDateStr());
		// 渠道
		map.put(CFunctionConstant.CHANNEL, "00");
		//之前就被注释***************
		// 中心批次号
		// map.put(CFunctionConstant.SWT_BATCH_NO, "000000");
		// 中心流水号
		// map.put(CFunctionConstant.SWT_FLOW_NO, "000000");
		//**************************
		// 制卡文件名
		map.put(CFunctionConstant.FILE_PATH, getFormatStr(makeCardFileName," ", 50, false));
		// 公钥
		String cardCompanyKeyString = "";
		if (null != product && !"".equals(product)) {
			if ("1".equals(product.getCardType())) {
				if (null != cardCompany
						&& null != cardCompany.getIcFileFormat()) {
					cardCompanyKeyString = cardCompany.getIcFileFormat();
				}
			} else {
				if (null != cardCompany
						&& null != cardCompany.getMsFileFormat()) {
					cardCompanyKeyString = cardCompany.getMsFileFormat();
				}
			}
		}
		map.put(CFunctionConstant.RESV1, getFormatStr(cardCompanyKeyString,"0", 512, false));
		// 应答码
		map.put(CFunctionConstant.RESP_CODE, getFormatStr("", " ", 2, false));

		// 服务
		String txnCode = ConfigMakeCard.getTxnCodeFile();
		// 发送报文
		OperationResult result = sendMakeCardMessageAsyn(txnCode, map, true);
		
		Map resultMap = java2C.getResultMap(result);
		String txnstate = java2C.getTxnState(result);
		*/
		String txnstate=accPackageDTO.getRespCode();
		if (!Const.RESPONSE_CODE.equals(txnstate)) {
		    logger.error("发送制卡通知报文失败！返回的值（RespCode）：" + txnstate);
			throw new BizServiceException("发送制卡通知报文失败！");
		}

		String makeCardBackFileName = accPackageDTO.getFilePath();
		String dataString = accPackageDTO.getTxnDate();
		logger.debug("文件：" + makeCardBackFileName);
		logger.debug("日期：" + dataString);
		return makeCardBackFileName;
	}

	/**
	 * 同步
	 */
//	private OperationResult sendMakeCardMessage(String txnCode,
//			Map<?, ?> parameterMap, boolean transfile) throws Exception {
//		return java2C.sendTpService(txnCode, parameterMap, Const.JAVA2C_NORMAL,
//				transfile);
//	}

	/**
	 * 异步
	 */
//	private OperationResult sendMakeCardMessageAsyn(String txnCode,
//			Map<?, ?> parameterMap, boolean transfile) throws Exception {
//		return java2C.sendTpaService(txnCode, parameterMap,
//				Const.JAVA2C_NORMAL, transfile);
//	}

	/**
	 * 为制卡订单生产卡号
	 */
	private List<String> generateCardNoForStockList(SellOrder sellOrder,
			String entityId, Product product) throws Exception {
		// 订单-->产品-->卡PIN-->卡流水+卡BIN
		// 卡BIN
		// String cardBin = this.getCardPin(sellOrder.getProductId());

		// CardSerialNumber cardSerialNumber =
		// getCardSerialNumber(cardBin,entityId);
		// 原始卡流水号
		// String serialNumber = cardSerialNumber.getSerialNumber();
		// String issuerCode = cardSerialNumber.getIssuerCode();
	    logger.info("为制卡订单生产卡号");
		ProductCardBin productCardBin = (ProductCardBin) baseDAO
				.queryForObject("PRODUCT.getProductCardBinSerialNumberForLock",
						product.getProductId());
		if (null == productCardBin) {
		    logger.error("产品：" + product.getProductId()
                    + "没有卡BIN,请为产品添加卡BIN！");
			throw new BizServiceException("产品：" + product.getProductId()
					+ "没有卡BIN,请为产品添加卡BIN！");
		} else if (!productCardBin.getEffect().equals("1")) {
		    logger.error("产品：" + product.getProductId()
                    + "没有生效的卡BIN！");
			throw new BizServiceException("产品：" + product.getProductId()
					+ "没有生效的卡BIN！");
		}
		String cardBin = productCardBin.getCardBin();
		String serialNumber = productCardBin.getSerialNumber();
		if (StringUtil.isEmpty(serialNumber)) {
			serialNumber = "0";
		}
		List<String> cardNoList = new ArrayList<String>();
		// 卡片数量
		Integer cardQuantity = this.getCardQuentity(sellOrder);

		Integer serialNumberAdd = new Integer(serialNumber);
		// String formatStr = "";
		// // 流水号固定10位，不足后补0
		// formatStr = this.getFormatStr(formatStr, "0", 10, false);
		// DecimalFormat df = new DecimalFormat(formatStr);
		logger.info("--生成卡号开始---");
		for (int i = 0; i < cardQuantity; i++) {
			// 生成卡号：机构号2+卡BIN+卡流水号+1位校验
			// String cardNo = LUHNGenerator.generate(issuerCode + cardBin +
			// df.format(++serialNumberAdd).toString());
			serialNumberAdd += 1;
			String resultString = cardBin
					+ this.getFormatStr(serialNumberAdd.toString(), "0", 8,
							true);
			String cardNo = LUHNGenerator.generate(resultString);
			logger.info(cardNo);
			cardNoList.add(cardNo);
		}
		logger.info("--生成卡号结束---");
		// 更新卡流水表流水号
		// cardSerialNumber.setSerialNumber(serialNumberAdd.toString());
		// cardSerialNumberDAO.updateByPrimaryKeySelective(cardSerialNumber);
		productCardBin.setSerialNumber(serialNumberAdd.toString());
		// 使产品卡BIN生效
		// if(!"1".equals(productCardBin.getEffect())){
		// productCardBin.setEffect("1");
		// }
		productCardBinDAO.updateByPrimaryKeySelective(productCardBin);
		return cardNoList;
	}

	/**
	 * 为记名采购订单生产卡号
	 */
	private List<SellOrderCardList> generateCardNoForCardList(
			List<SellOrderCardList> cardLists, SellOrder sellOrder,
			String entityId, Product product) throws Exception {
		List<SellOrderCardList> orderCardLists = new ArrayList<SellOrderCardList>();
		// ProductCardBin productCardBin =
		// productCardBinService.getProductCardBinForLock(product.getProductId());
		ProductCardBin productCardBin = (ProductCardBin) baseDAO
				.queryForObject("PRODUCT.getProductCardBinSerialNumberForLock",
						product.getProductId());
		if (null == productCardBin) {
		    logger.error("产品：" + product.getProductId()
                    + "没有卡BIN,请为产品添加卡BIN！");
			throw new BizServiceException("产品：" + product.getProductId()
					+ "没有卡BIN,请为产品添加卡BIN！");
		} else if (!productCardBin.getEffect().equals("1")) {
		    logger.error("产品：" + product.getProductId()
                    + "没有生效的卡BIN！");
			throw new BizServiceException("产品：" + product.getProductId()
					+ "没有生效的卡BIN！");
		}
		String cardBin = productCardBin.getCardBin();
		String serialNumber = productCardBin.getSerialNumber();
		if (StringUtil.isEmpty(serialNumber)) {
			serialNumber = "0";
		}
		Integer serialNumberAdd = new Integer(serialNumber);
		// String formatStr = "";
		// formatStr = this.getFormatStr(formatStr, "0", 8, false);
		// DecimalFormat df = new DecimalFormat(formatStr);
		for (SellOrderCardList cardList : cardLists) {
			serialNumberAdd += 1;
			String resultString = cardBin
					+ this.getFormatStr(serialNumberAdd.toString(), "0", 8,
							true);
			String cardNo = LUHNGenerator.generate(resultString);
			cardList.setCardNo(cardNo);
			// 2011-04-29 Yifeng.Shi加入 记名订单明细的充值金额
			cardList.setCreditAmount(sellOrder.getFaceValue());
			cardList.setCardState(OrderConst.MAKE_CARD_STATE_MAKING);
			orderCardLists.add(cardList);
		}
		// 更新卡流水表流水号
		// cardSerialNumber.setSerialNumber(serialNumberAdd.toString());
		// cardSerialNumberDAO.updateByPrimaryKeySelective(cardSerialNumber);
		productCardBin.setSerialNumber(serialNumberAdd.toString());
		// 使产品卡BIN生效
		// if(!"1".equals(productCardBin.getEffect())){
		// productCardBin.setEffect("1");
		// }
		productCardBinDAO.updateByPrimaryKeySelective(productCardBin);
		commonsDAO
				.batchUpdate(
						"TB_SELL_ORDER_CARD_LIST.abatorgenerated_updateByPrimaryKeySelective",
						orderCardLists);
		return orderCardLists;
	}

	// private CardSerialNumber getCardSerialNumber(String cardBin,
	// String entityId) {
	// CardSerialNumber cardSerialNumber = new CardSerialNumber();
	// cardSerialNumber.setIssuerId(entityId);
	// cardSerialNumber.setCardBin(cardBin);
	// // 获取并锁定卡流水表
	// cardSerialNumber = (CardSerialNumber) baseDAO.queryForObject(
	// "STOCK_ORDER.getCardSerialNumberForLock", cardSerialNumber);
	// return cardSerialNumber;
	// }

	/**
	 * 获取卡片数量
	 */
	private Integer getCardQuentity(SellOrder sellOrder) {
		Integer cardQuantity = 0;
		SellOrderCardListExample example = new SellOrderCardListExample();
		example.createCriteria().andOrderIdEqualTo(sellOrder.getOrderId())
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
				.andCardStateEqualTo(OrderConst.MAKE_CARD_STATE_FAILTURE);
		int cardCount = sellOrderCardListDAO.countByExample(example);
		if (cardCount < 1) {
			// 新订单生成所有的卡号
			cardQuantity = new Integer(sellOrder.getCardQuantity());
		} else {
			// 被退回的订单只生成制卡失败的卡号
			cardQuantity = cardCount;
			// 重新制卡前 删除之前制卡失败的记录
			SellOrderCardList cardList = new SellOrderCardList();
			cardList.setDataState(DataBaseConstant.DATA_STATE_DELETE);
			sellOrderCardListDAO.updateByExampleSelective(cardList, example);
		}

		return cardQuantity;
	}

	/**
	 * 根据卡号 添加订单卡明细数据
	 */
	private List<SellOrderCardList> insertOrderCardList(SellOrder sellOrder,
			List<String> cardNoList, Product product, String loginUserId)
			throws Exception {

		List<SellOrderCardList> orderCardLists = new ArrayList<SellOrderCardList>();
		for (String cardNo : cardNoList) {
			SellOrderCardList orderCardList = new SellOrderCardList();
			orderCardList
					.setOrderCardListId(commonsDAO
							.getNextValueOfSequenceBySequence("SEQ_SELL_ORDER_CARD_LIST"));

			orderCardList.setOrderId(sellOrder.getOrderId());
			orderCardList.setCardNo(cardNo);
			orderCardList.setCardState(OrderConst.MAKE_CARD_STATE_MAKING);
			orderCardList.setCardPinState(product.getPinStat());
			orderCardList.setCreateUser(loginUserId);
			orderCardList.setCreateTime(DateUtil.getCurrentTime());
			orderCardList.setModifyUser(loginUserId);
			orderCardList.setModifyTime(DateUtil.getCurrentTime());
			orderCardList.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			orderCardLists.add(orderCardList);
		}

		// 记录订单明细
		commonsDAO.batchInsert(
				"TB_SELL_ORDER_CARD_LIST.abatorgenerated_insert",
				orderCardLists);
		return orderCardLists;
	}

	/**
	 * 返回符合格式的字符串
	 */
	public String getFormatStr(String str, String appendStr, int length,
			boolean appendBefore) throws Exception {
		if (null == str) {
			str = "";
		}
		if (null == appendStr) {
			appendStr = "0";
		}
		int circleNum = length - str.length();
		StringBuffer buffer = new StringBuffer(str);
		if (circleNum < 0) {
			str = str.substring(length);
		} else {
			for (int i = 0; i < circleNum; i++) {
				if (appendBefore) {
					buffer.insert(0, appendStr);
				} else {
					buffer.append(appendStr);
				}
			}
		}
		// logger.debug(str.length());
		// logger.debug(str);
		return buffer.toString();

	}

	/**
	 * 写入制卡文件
	 */
	private void writeMakeCardFile(SellOrder sellOrder,
			List<SellOrderCardList> orderCardLists, Product product,
			List<ServiceDTO> prodAccDTOList, String makeCardFileName)
			throws Exception {
		// 账户
		StringBuffer accountBuffer = new StringBuffer();
		for (ServiceDTO accDto : prodAccDTOList) {
			ProductServiceExample example = new ProductServiceExample();
			example.createCriteria()
					.andProductIdEqualTo(product.getProductId())
					.andServiceIdEqualTo(accDto.getServiceId());
			example.setOrderByClause("REL_ID ASC");
			List<ProductService> productServices = productServiceDAO
					.selectByExample(example);
			ProductService productService = productServices.get(0);

			// 分隔符
			accountBuffer.append(";");
			// 账户类型(服务ID)
			String accType = accDto.getServiceId();
			accountBuffer.append(getFormatStr(accType, " ", 8, false));
			// 有效期
			String expiryDate = accDto.getExpiryDate();
			accountBuffer.append(getFormatStr(expiryDate, " ", 8, false));
			// pos单笔消费上限(12)
			String maxTxn = productService.getMaxTxnAmt();
			accountBuffer.append(getFormatStr(maxTxn, " ", 12, false));
			// pos日累计上限(12)
			String maxDayTxn = productService.getMaxDayTxnAmt();
			accountBuffer.append(getFormatStr(maxDayTxn, " ", 12, false));
			// web单笔消费上限(12)
			String webMaxTxn = productService.getWebMaxTxnAmt();
			accountBuffer.append(getFormatStr(webMaxTxn, " ", 12, false));
			// web日累计上限(12)
			String webMaxDayTxn = productService.getWebMaxDayTxnAmt();
			accountBuffer.append(getFormatStr(webMaxDayTxn, " ", 12, false));
			// 可透支金额
			String maxamt = "";
			accountBuffer.append(getFormatStr(maxamt, " ", 12, false));
			// 金额
			String amt = "";
			accountBuffer.append(getFormatStr(amt, " ", 12, false));
		}

		StringBuffer buffer = new StringBuffer();
		// 写入卡号+账户
		for (SellOrderCardList orderCardList : orderCardLists) {
			// 卡号(19)
			buffer.append(getFormatStr(orderCardList.getCardNo(), " ", 19,
					false));
			// 8位有效期
			String valDate = sellOrder.getValidityPeriod();
			buffer.append(getFormatStr(valDate, " ", 8, false));
			// 验密
			String pinStat = product.getPinStat();
			buffer.append(getFormatStr(pinStat, " ", 1, false));
			// 无pin金额上限
			String noPinamt = product.getNoPinLimit();
			buffer.append(getFormatStr(noPinamt, " ", 12, false));
			// 产品标识
			String productFlag = product.getProductId();
			buffer.append(getFormatStr(productFlag, " ", 8, false));
			// 持卡人标识
			String cardholderFlag = orderCardList.getCardholderId();
			buffer.append(getFormatStr(cardholderFlag, " ", 10, false));
			// 打印公司名
			// String companyName = (String)
			// baseDAO.queryForObject("CUSTOMER.selectCustomerPrintNameByCardHolder",cardholderFlag);
			String companyName = "";
			buffer.append(getFormatStr(companyName, " ", 64, false));
			// 打印姓名
			String firstName = orderCardList.getFirstName();
			int countLength = 0;
			if (null != firstName && !firstName.equals("")) {
				countLength = firstName.getBytes().length - firstName.length();
			}
			buffer
					.append(getFormatStr(firstName, " ", 64 - countLength,
							false));
			// 充值次数
			String rechargTime = product.getRechargeTimes();
			if (StringUtil.isEmpty(rechargTime)) {
				rechargTime = "99999";
			}
			buffer.append(getFormatStr(rechargTime, " ", 5, false));
			// 消费次数
			String consumerTime = product.getConsumerTimes();
			if (StringUtil.isEmpty(consumerTime)) {
				consumerTime = "99999";
			}
			buffer.append(getFormatStr(consumerTime, " ", 5, false));
			// 面额
			String faceValue = sellOrder.getFaceValue();
			buffer.append(getFormatStr(faceValue, " ", 12, false));
			// 所属机构
			String entityId = product.getEntityId();
			buffer.append(getFormatStr(entityId, " ", 32, false));

			// 重复添加账户信息
			buffer.append(accountBuffer);

			buffer.append("\n");
		}

		// ftp制卡文件
		this.ftpPutMakeCardFile(buffer.toString(), makeCardFileName);
	}

	/**
	 * 将生成的制卡文件ftp到指定位置
	 */
	private void ftpPutMakeCardFile(String fileStr, String fileName)
			throws Exception {

		String uploadFilePath = ConfigMakeCard.getUploadFilePath();

		BufferedWriter bw = null;
		logger.debug("file[" + uploadFilePath + fileName + "]");
		File tempFile = new File(uploadFilePath + fileName);


		// 生成文件
		try {
			tempFile.createNewFile();
			bw = new BufferedWriter(new FileWriter(tempFile));
			bw.write(fileStr);
			bw.flush();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			logger.error("写入制卡文件失败！");
			throw new BizServiceException("写入制卡文件失败！");
		} finally {
			if (null != bw) {
				bw.close();
			}
			// java.lang.Runtime rt = java.lang.Runtime.getRuntime();
			// rt.exec("chmod 754 " + tempFile);
		}

	}

	public List<byte[]> ftpGetMakeCardFile(String downFileName)
			throws Exception {
		if (StringUtil.isNotEmpty(downFileName)) {
			downFileName = downFileName.trim();
		}
		List<byte[]> byteList = null;
		String downloadFilePath = ConfigMakeCard.getDownloadFilePath();
		System.out.println("取得文件路径"+downloadFilePath+downFileName);
		logger.debug("file[" + downloadFilePath + downFileName + "]");
		BufferedInputStream bs = null;
		//File tempFile = new File(downloadFilePath + downFileName);
		try {
			//if (tempFile == null || "".equals(tempFile.toString())) {
				//throw new BizServiceException("无效文件路径");
			//}
			 //byteList = FtpUtil.downFileStream(ConfigMakeCard.getIp(), ConfigMakeCard.getPort(), ConfigMakeCard.getUserName(),
			//		 ConfigMakeCard.getPassword(),downloadFilePath, downFileName);
			String localPath=downloadFilePath+downFileName;
			SFtpUtil.download(ConfigMakeCard.getIp(),Integer.parseInt(ConfigMakeCard.getPort()), ConfigMakeCard.getUserName(), ConfigMakeCard.getPassword(),
					ConfigMakeCard.getRomtePath(),downFileName, downloadFilePath+downFileName);
			File tempFile = new File(downloadFilePath+downFileName);
			
			if (tempFile == null || "".equals(tempFile.toString())) {
				throw new BizServiceException("无效文件路径");
			}
			
			byteList = new ArrayList<byte[]>();
			long len = tempFile.length();
			byte[] bytes = new byte[(int) len];
			bs = new BufferedInputStream(new FileInputStream(tempFile));
			int r = bs.read(bytes);
			if (r != len) {
				throw new BizServiceException("文件读取错误");
			}
			byteList.add(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage());
			throw new BizServiceException("读取文件失败");
		} finally {
			if (null != bs) {
				bs.close();
			}
		}
		return byteList;
	}

	public SellOrderMakeCardDTO downPinFile(
			SellOrderMakeCardDTO sellOrderMakeCardDTO)
			throws BizServiceException {
		return null;
	}

	public void flowOperate(SellOrderMakeCardDTO sellOrderMakeCardDTO)
			throws BizServiceException {

	}

	public PageDataDTO inqueryOrderForCardCompany(
			SellOrderQueryDTO sellOrderQueryDTO) throws BizServiceException {
		return null;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public CardCompanyService getCardCompanyService() {
		return cardCompanyService;
	}

	public void setCardCompanyService(CardCompanyService cardCompanyService) {
		this.cardCompanyService = cardCompanyService;
	}

	public SellOrderCardListDAO getSellOrderCardListDAO() {
		return sellOrderCardListDAO;
	}

	public void setSellOrderCardListDAO(
			SellOrderCardListDAO sellOrderCardListDAO) {
		this.sellOrderCardListDAO = sellOrderCardListDAO;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public CardSerialNumberDAO getCardSerialNumberDAO() {
		return cardSerialNumberDAO;
	}

	public void setCardSerialNumberDAO(CardSerialNumberDAO cardSerialNumberDAO) {
		this.cardSerialNumberDAO = cardSerialNumberDAO;
	}

	public CardCompanyDAO getCardCompanyDAO() {
		return cardCompanyDAO;
	}

	public void setCardCompanyDAO(CardCompanyDAO cardCompanyDAO) {
		this.cardCompanyDAO = cardCompanyDAO;
	}

	public ProdAccLayPackServiceDAO getProdAccLayPackServiceDAO() {
		return prodAccLayPackServiceDAO;
	}

	public void setProdAccLayPackServiceDAO(
			ProdAccLayPackServiceDAO prodAccLayPackServiceDAO) {
		this.prodAccLayPackServiceDAO = prodAccLayPackServiceDAO;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	public ProductServiceDAO getProductServiceDAO() {
		return productServiceDAO;
	}

	public void setProductServiceDAO(ProductServiceDAO productServiceDAO) {
		this.productServiceDAO = productServiceDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public StockOrderCommonService getStockOrderCommonService() {
		return stockOrderCommonService;
	}

	public void setStockOrderCommonService(
			StockOrderCommonService stockOrderCommonService) {
		this.stockOrderCommonService = stockOrderCommonService;
	}

	public OrderCardListService getOrderCardListService() {
		return orderCardListService;
	}

	public void setOrderCardListService(
			OrderCardListService orderCardListService) {
		this.orderCardListService = orderCardListService;
	}

	public ProductCardBinDAO getProductCardBinDAO() {
		return productCardBinDAO;
	}

	public void setProductCardBinDAO(ProductCardBinDAO productCardBinDAO) {
		this.productCardBinDAO = productCardBinDAO;
	}

	public ProductCardBinService getProductCardBinService() {
		return productCardBinService;
	}

	public void setProductCardBinService(
			ProductCardBinService productCardBinService) {
		this.productCardBinService = productCardBinService;
	}
	
	public Java2ACCBusinessServiceImpl getJava2ACCBusinessService() {
		return java2ACCBusinessService;
	}

	public void setJava2ACCBusinessService(Java2ACCBusinessServiceImpl java2accBusinessService) {
		java2ACCBusinessService = java2accBusinessService;
	}
	public OrderBO getOrderBO() {
		return orderBO;
	}


	public void setOrderBO(OrderBO orderBO) {
		this.orderBO = orderBO;
	}


	public BatchFileActionInterface getFileBatchService() {
		return fileBatchService;
	}


	public void setFileBatchService(BatchFileActionInterface fileBatchService) {
		this.fileBatchService = fileBatchService;
	}


	public AccBatchFileDAO getAccBatchFileDAO() {
		return accBatchFileDAO;
	}


	public void setAccBatchFileDAO(AccBatchFileDAO accBatchFileDAO) {
		this.accBatchFileDAO = accBatchFileDAO;
	}


/*public static void main(String[] args) throws Exception {
		try {
		String str1="D:\\";
		String str2="000010000002691.dat_rsp";
		System.out.println(str1+str2);
		OrderMakeCardServiceImpl ss = new OrderMakeCardServiceImpl();
		ss.download("10.250.3.10",22,"svc_acc","svc_acc","/tmp/","000010000002691.dat_rsp",str1+str2);
	} catch (JSchException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SftpException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}*/
	
}
