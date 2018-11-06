package com.huateng.univer.businessbatch.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.allinfinance.service.BatchParamInterface;
import com.allinfinance.univer.businessbatch.dto.BatchFileDTO;
import com.allinfinance.univer.businessbatch.dto.MakeCardBatchDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.huateng.framework.common.service.CommonService;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.DataBaseDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.AccBatchFileDAO;
import com.huateng.framework.ibatis.dao.CardCompanyDAO;
import com.huateng.framework.ibatis.dao.ProductDAO;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.model.AccBatchFile;
import com.huateng.framework.ibatis.model.AccBatchFileExample;
import com.huateng.framework.ibatis.model.BatchSum;
import com.huateng.framework.ibatis.model.CardCompany;
import com.huateng.framework.ibatis.model.Product;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderCardListExample;
import com.huateng.framework.util.ConfigMakeCard;
import com.huateng.framework.util.DateUtil;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
import com.huateng.univer.issuer.order.biz.bo.StockOrderCommonService;



public class MakeCardBatchServiceImpl extends BasicBatchServiceImpl {
	private Logger logger = Logger.getLogger(MakeCardBatchServiceImpl.class);
	private DataBaseDAO databaseDAO;
	private SellOrderCardListDAO sellOrderCardListDAO;
	private SellOrderDAO sellOrderDAO;
	private AccBatchFileDAO accBatchFileDAO;
	private CommonService commonService;
	private StockOrderCommonService stockOrderCommonService;
	private static final String BATCH_TYPE = "04";
	private Java2ACCBusinessServiceImpl java2ACCBusinessService;
	private CardCompanyDAO cardCompanyDAO;
	private ProductDAO productDAO;
	
	@Override
	public void insertBatch(List<? extends BatchParamInterface> businessList)
			throws BizServiceException {
		List<MakeCardBatchDTO> list = new ArrayList<MakeCardBatchDTO>();
		for (BatchParamInterface param : businessList) {
			SellOrderListDTO dto = (SellOrderListDTO) param;
			MakeCardBatchDTO make = new MakeCardBatchDTO();
			// 直接利用cp属性
			BeanUtils.copyProperties(dto, make);
			make.setTxnSeq(dto.fetchSeq());
			make.setTxnState("0");
			make.setTxnAmt(dto.getFaceValue());
			make.setSvrCode("201");
			make.setCardType("00000");
			make.setLyDate("0000000000000");
			make.setRecCrtTs(DateUtil.getCurrentTime());
			make.setSmltDate(DateUtil.getCurrentDateStr());
			make.setTxnType("0001");
			list.add(make);
		}
		databaseDAO.batchInsert("MAKE_CARD.insertMakeCardTxn", list);
		if (OrderConst.ORDER_TYPE_ORDER_MAKE_CARD.equals(list.get(0)
				.getMakeCardType())) {
			// 添加订单卡明细数据
			insertOrderCardList(list);
		} else {
			updateOrderCardList(list);
		}
	}

	/**
	 * 制卡订单 根据卡号 添加订单卡明细数据
	 */
	private void insertOrderCardList(List<MakeCardBatchDTO> list)
			throws BizServiceException {
		try {
			List<SellOrderCardList> orderCardLists = new ArrayList<SellOrderCardList>();
			for (MakeCardBatchDTO batchFileDetailDTO : list) {
				SellOrderCardList orderCardList = new SellOrderCardList();
				orderCardList
						.setOrderCardListId(databaseDAO
								.getNextValueOfSequenceBySequence("SEQ_SELL_ORDER_CARD_LIST"));

				orderCardList.setOrderId(batchFileDetailDTO.getOrderId());
				orderCardList.setCardNo(batchFileDetailDTO.getCardNo());
				orderCardList.setCardState(OrderConst.MAKE_CARD_STATE_MAKING);
				orderCardList.setCardPinState(batchFileDetailDTO.getPinStat());
				orderCardList.setCreateUser(batchFileDetailDTO.getUserId());
				orderCardList.setCreateTime(DateUtil.getCurrentTime());
				orderCardList.setModifyUser(batchFileDetailDTO.getUserId());
				orderCardList.setModifyTime(DateUtil.getCurrentTime());
				orderCardList.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

				orderCardLists.add(orderCardList);
			}

			// 记录订单明细
			databaseDAO.batchInsert(
					"TB_SELL_ORDER_CARD_LIST.abatorgenerated_insert",
					orderCardLists);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加订单卡明细数据失败");
		}
	}

	/**
	 * 加入 记名订单明细的充值金额
	 */
	private void updateOrderCardList(List list) throws BizServiceException {
		try {
			List<MakeCardBatchDTO> batchFileList = (List<MakeCardBatchDTO>) list;
			//查找采购订单的原销售订单
			String orderId =(String)databaseDAO.queryForObject("STOCK_ORDER_CARD_LIST", "selectOrigOrderByBuyOrderId", batchFileList.get(0).getOrderId());
			SellOrderCardListExample example = new SellOrderCardListExample();
			example.createCriteria().andOrderIdEqualTo(orderId);
			List<SellOrderCardList> temp1 = sellOrderCardListDAO.selectByExample(example);
			List<SellOrderCardList> orderCardLists = new ArrayList<SellOrderCardList>();
			for (int i=0;i<temp1.size();i++) {
				SellOrderCardList orderCardList = new SellOrderCardList();
				orderCardList.setOrderCardListId(temp1.get(i).getOrderCardListId());
				orderCardList.setCardNo(batchFileList.get(i).getCardNo());
				orderCardList.setCreditAmount(batchFileList.get(i).getTxnAmt());
				orderCardList.setCardState(OrderConst.MAKE_CARD_STATE_MAKING);
				orderCardLists.add(orderCardList);
			}
			
			databaseDAO
					.batchUpdate(
							"TB_SELL_ORDER_CARD_LIST.abatorgenerated_updateByPrimaryKeySelective",
							orderCardLists);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加订单卡明细数据失败");
		}
	}

	@Override
	public void commMessage(BatchFileDTO batchFileDTO)
			throws BizServiceException {
		try {
			AccPackageDTO accPackageDTO = new AccPackageDTO();
			accPackageDTO.setTxnCode(Const.TXN_TYPE_CODE_MAKE_CARD);
			accPackageDTO.setTxnDate(DateUtil.getCurrentDateStr());
			accPackageDTO.setTxnTime(DateUtil.getCurrenTime24());
			accPackageDTO.setBatchNo(batchFileDTO.getBatchNo());
			// 批次信息 批次类型|总比数+'|'+总金额+'|'+剩余笔数+'|'+剩余金额
			accPackageDTO.setBatchFileInfo(BATCH_TYPE + "|"
					+ batchFileDTO.getTotCnt() + "|" + batchFileDTO.getTotAmt()
					+ "|" + 0 + "|" + 0);
			accPackageDTO = java2ACCBusinessService
					.sendRechargeMessage(accPackageDTO);
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("");
		}
	}

	@Override
	public String getBatchType() {
		return BATCH_TYPE;
	}

	/**
	 * 检查制卡文件是否制卡成功
	 * 
	 * @param sellorder
	 * @return true: 制卡成功 else : false
	 */
	private boolean checkMakeCardFileState(SellOrder sellorder) {
		AccBatchFileExample accBatchFileExample = new AccBatchFileExample();
		accBatchFileExample.createCriteria().andOrderIdEqualTo(
				sellorder.getOrderId());
		List<AccBatchFile> accBatchFiles = accBatchFileDAO
				.selectByExample(accBatchFileExample);
		if (accBatchFiles == null || accBatchFiles.size() <= 0) {
			// 查不到数据，重新插入新的数据
			try {
				insertMakeCardInfo(sellorder);
			} catch (BizServiceException e) {
				this.logger.error(e.getMessage());
			}
			return false;
		}
		/* 文件状态 00:初始 01:待处理 02:处理中 03:结束 */
		// 文件处理结束，插入订单流程
		if("03".equals(accBatchFiles.get(0).getFileStat())){
			if(Long.parseLong(accBatchFiles.get(0).getTotCnt().trim())== Long.parseLong(accBatchFiles.get(0).getSucCnt().trim())){
				sellorder.setOrderState(OrderConst.ORDER_STATE_CARDGFILE_MAKING);
			}else{
				sellorder.setOrderState(OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL);
			}
			sellorder.setModifyTime(DateUtil.getCurrentTime());
			sellOrderDAO.updateByPrimaryKeySelective(sellorder);
			return true;
		}
		return false;
	}

	/* 暂时不在后台插入订单流程节点 */
	// private boolean insertOrderFlow(SellOrder sellOrder) {
	// orderBO.orderFlowNexNode(sellOrder.getOrderId(),
	// OrderConst.ORDER_STATE_CARDGFILE_MAKING,
	// sellOrder.getLoginUserId(),
	// sellOrder.getDefaultEntityId(),
	// OrderConst.ORDER_FLOW_OPRATION_CONFIRMATION,
	// sellOrder.getOperationMemo(),
	// OrderConst.ORDER_FLOW_MAKECARD_MAKE);
	//
	// return false;
	// }

	@Override
	protected boolean orderStateChange(BatchSum sum) {
		// 查询订单信息
		SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(sum
				.getOrderId());
		// FIXME 请cathy童鞋确认这个判断
		// 如果定单状态为已经完成状态，则直接返回true
		if (OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL.equals(sellOrder
				.getOrderState())
				|| OrderConst.ORDER_STATE_CARDGFILE_MAKING.equals(sellOrder
						.getOrderState())) {
			return true;
		}
		// 如果是开户成功状态，则检查制卡文件是否已经生成
		if (OrderConst.ORDER_STATE_OPEN_ACCOUNT.equals(sellOrder
				.getOrderState())) {
			return checkMakeCardFileState(sellOrder);
		}

		// 如果以上皆不是，则说明尚未走入制卡文件生成流程
		// 检查批次状态
		if (Long.parseLong(sum.getFailCnt().trim()) > 0
				|| Long.parseLong(sum.getSucCnt().trim())
						+ Long.parseLong(sum.getFailCnt().trim()) != Long
						.parseLong(sum.getTotCnt().trim())) {
			// 批次处理失败
			sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL);
			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
			return true;
		}

		// 插入制卡文件
		try {
			insertMakeCardInfo(sellOrder);
		} catch (BizServiceException e) {
			this.logger.error(e.getMessage());
		}

		// 批次处理成功
		sellOrder.setOrderState(OrderConst.ORDER_STATE_OPEN_ACCOUNT);
		sellOrder.setModifyTime(DateUtil.getCurrentTime());
		sellOrderDAO.updateByPrimaryKeySelective(sellOrder);

		return false;

	}

	private void insertMakeCardInfo(SellOrder sellOrderDTO)
			throws BizServiceException {
		AccBatchFile accBatchFile = new AccBatchFile();
		// 生成制卡文件名
		// 不传操作员ID
		Product product = productDAO.selectByPrimaryKey(sellOrderDTO.getProductId());
		
		String makeCardFileName = this.getMakeCardFileName(product,
				sellOrderDTO.getOrderId(), sellOrderDTO.getModifyUser());
		//获得制卡商密钥
		String companyKey = getCompanyKey(sellOrderDTO);
		accBatchFile.setFileType("01");
		accBatchFile.setOrderId(sellOrderDTO.getOrderId());
		accBatchFile.setProductId(sellOrderDTO.getProductId());
		// 01：初始状态
		accBatchFile.setFileStat("01");
		accBatchFile.setFileName(makeCardFileName);
		accBatchFile.setBatchNo(sellOrderDTO.getBatchNo());
		accBatchFile.setTotCnt(sellOrderDTO.getCardQuantity());
		accBatchFile.setRsvd2(companyKey);
		accBatchFile.setRecCrtTs(DateUtil.getCurrentTime());
		accBatchFile.setUpdCrtTs(DateUtil.getCurrentTime());
		accBatchFileDAO.insert(accBatchFile);
	}

	/**
	 * 生成制卡文件名
	 * 
	 * @param product
	 * @param orderId
	 * @param loginUserId
	 * @return
	 * @throws Exception
	 */
	private String getMakeCardFileName(Product product, String orderId,
			String loginUserId) throws BizServiceException {

		Date date = new Date();
		String year = (date.getYear()+1900+"").substring(2);
		//拿到8位的发行机构号
		//String entityId = product.getEntityId();
		//拿到卡的类型1 IC卡 2词条卡
		/*String cardType = "0"+product.getCardType();
		//拿到卡的属性 1 记名个性化卡  2 不记名卡 3 记名库存卡
		String onmouseStat = product.getOnymousStat();
		if(onmouseStat.equals("1")||onmouseStat.equals("3")){
			onmouseStat="001";
		}else if(onmouseStat.equals("2")){
			onmouseStat ="002";
		}else{
			
				try {
					throw new Exception("取得卡是否记名错误");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}*/
		//取八位的机构号的后三位作为卡类型中的机构标识
		//String entityFlag = entityId.substring(5);
		StringBuffer filename = new StringBuffer();
		filename.append(product.getEntityId());
		//modify by wanglei 记名卡和不记名卡的制卡文件名称不一样
		if(product.getOnymousStat().trim().equals("2")){
			filename.append("-PBOCZ");
		}else{
			filename.append("-PBOCY");
		}
		filename.append("0000"+product.getProductId());
		filename.append("N-");
		filename.append(year);
		int month =(date.getMonth()+1);
		String tmp=month+"";
		if(month<10){
			tmp="0"+month;
		}
		filename.append(tmp);
		int day=date.getDate();
		String tmpDay=day+"";
		if(day<10){
			tmpDay="0"+tmpDay;
		}
		filename.append(tmpDay);
		filename.append("-");
		String serialNumber = commonService
				.getNextValueOfSequence("TB_ORDER_BATCH_FILE");
		//String platformNumber = ConfigMakeCard.getPlatfromNo();
		//String batchNumber = platformNumber
				//+ getFormatStr(serialNumber, "0", 4, true);
		filename.append(getFormatStr(serialNumber, "0", 4, true));
		// 记录订单制卡文件批次号
		stockOrderCommonService.insertOrUpdateOrderBatchFile(filename.toString(),
				orderId, loginUserId);
		String fileName = filename.toString();
		this.logger.info("制卡文件名："+filename);
		return fileName;
	}

	/**
	 * 获得制卡商公钥
	 * @param sellOrder
	 * @return
	 */
	private String getCompanyKey(SellOrder sellOrder){
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
		return cardCompanyKeyString;
	}
	
	/**
	 * 返回符合格式的字符串
	 */
	private String getFormatStr(String str, String appendStr, int length,
			boolean appendBefore) {
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
		return buffer.toString();
	}

	public DataBaseDAO getDatabaseDAO() {
		return databaseDAO;
	}

	public void setDatabaseDAO(DataBaseDAO databaseDAO) {
		this.databaseDAO = databaseDAO;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public Java2ACCBusinessServiceImpl getJava2ACCBusinessService() {
		return java2ACCBusinessService;
	}

	public void setJava2ACCBusinessService(
			Java2ACCBusinessServiceImpl java2accBusinessService) {
		java2ACCBusinessService = java2accBusinessService;
	}

	public AccBatchFileDAO getAccBatchFileDAO() {
		return accBatchFileDAO;
	}

	public void setAccBatchFileDAO(AccBatchFileDAO accBatchFileDAO) {
		this.accBatchFileDAO = accBatchFileDAO;
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

	public void setSellOrderCardListDAO(SellOrderCardListDAO sellOrderCardListDAO) {
		this.sellOrderCardListDAO = sellOrderCardListDAO;
	}

	public CardCompanyDAO getCardCompanyDAO() {
		return cardCompanyDAO;
	}

	public void setCardCompanyDAO(CardCompanyDAO cardCompanyDAO) {
		this.cardCompanyDAO = cardCompanyDAO;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

}
