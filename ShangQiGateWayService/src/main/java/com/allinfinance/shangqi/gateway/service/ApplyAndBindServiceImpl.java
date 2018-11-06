package com.allinfinance.shangqi.gateway.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.shangqi.gateway.dao.ApplyAndBindDao;
import com.allinfinance.shangqi.gateway.dto.ApplyAndBindCardDTO;
import com.allinfinance.shangqi.gateway.dto.EquityAccountTranChinaDTO;
import com.allinfinance.univer.cardmanagement.dto.CardManagementDTO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.util.ConfigMakeCard;
import com.huateng.framework.util.ConfigPosp;
import com.huateng.framework.util.DateUtil;
import com.huateng.hstserver.constants.RspCodeMap;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
import com.huateng.univer.cardmanage.biz.service.CardManageService;
import com.webservice.util.Constants;
import com.webservice.util.HttpPostData;
import com.webservice.util.JsonUtil;
import com.webservice.util.SocketSend;

public class ApplyAndBindServiceImpl implements ApplyAndBindService {
	Logger logger = Logger.getLogger(TxnqueryServiceImpl.class);
	private CommonsDAO commonsDAO;
	private ApplyAndBindDao applyAndBindCardDao;
	private CardManageService cardManageService;
	private List<ApplyAndBindCardDTO> cardHolderMsg;
	private PageQueryDAO pageQueryDAO;
	private Java2ACCBusinessServiceImpl java2ACCBusinessService;

	public Java2ACCBusinessServiceImpl getJava2ACCBusinessService() {
		return java2ACCBusinessService;
	}

	public void setJava2ACCBusinessService(
			Java2ACCBusinessServiceImpl java2accBusinessService) {
		java2ACCBusinessService = java2accBusinessService;
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

	public CardManageService getCardManageService() {
		return cardManageService;
	}

	public void setCardManageService(CardManageService cardManageService) {
		this.cardManageService = cardManageService;
	}

	public ApplyAndBindDao getApplyAndBindCardDao() {
		return applyAndBindCardDao;
	}

	public void setApplyAndBindCardDao(ApplyAndBindDao applyAndBindCardDao) {
		this.applyAndBindCardDao = applyAndBindCardDao;
	}

	/* 查询库存 */
	public String inqueryStockCount(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		int count = -1;
		try {
			stockCountDTO.setStockStatus("1");
			count = applyAndBindCardDao.StockCardCountQuery(
					"ApplyAndBindCard.selectCountByDTO", stockCountDTO);
			// cardActive(stockCountDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BizServiceException("查询库存卡数量失败！");

		}
		logger.info("卡类型" + stockCountDTO.getProductId() + "的库存数量为" + count);
		if (count == 0) {
			return "01"; // 没有库存
		} else if (count > 0) {
			return "00"; // 有库存
		} else {
			return "02";// 其他错误
		}
	}

	/* 从库存中获得一张卡 */
	public String getCard(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		String cardNo = null;
		try {

			List<EntityStock> list = applyAndBindCardDao.getStockCards(
					"ApplyAndBindCard.selectCardsByDTO", stockCountDTO);
			if (list.size() > 0) {
				logger.info("返回获得卡信息：" + list.get(0).getCardNo());
				cardNo = list.get(0).getCardNo();
			} else {
				throw new BizServiceException("库存不足！");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cardNo;
	}

	/* 检查持卡人信息 */
	public String checkCardHolderMsg(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		String checkStatus = "";
		try {
			this.cardHolderMsg = applyAndBindCardDao.getCardHolderMsg(
					"ApplyAndBindCard.selectCardsHoderByDTO", stockCountDTO);
			if (cardHolderMsg != null && cardHolderMsg.size() > 0) {
				ApplyAndBindCardDTO cardHolder = cardHolderMsg.get(0);
				if (/*cardHolder.getFirstName().equals(
						stockCountDTO.getFirstName())
						&&*/ cardHolder.getIdType().equals(
								stockCountDTO.getIdType())
						&& cardHolder.getIdNo().equals(stockCountDTO.getIdNo())) {
					checkStatus = "00";// 持卡人信息检查通过
				} else {
					checkStatus = "01";// 持卡人信息有误
				}
			} else {
				checkStatus = "02"; // 持卡人不存在
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checkStatus;
	}

	/* 卡片激活 */
	public String cardActive(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		CardManagementDTO dto = new CardManagementDTO();
		dto.setTransferOutCard(stockCountDTO.getCardNo());
		dto.setServiceFee(stockCountDTO.getServiceFee());
		dto.setCardNo(stockCountDTO.getCardNo());
		dto.setCvv2(stockCountDTO.getCvn2());

		return cardManageService.activateCard(dto);
	}

	@Override
	public ApplyAndBindCardDTO bindCard(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		String status = this.getCardStatus(stockCountDTO.getCardNo());
		String[] str =status.split(",");
		String cardNo = stockCountDTO.getCardNo().trim().substring(16);
		logger.debug("卡的注销状态："+str[0]);
		logger.debug("卡的激活状态："+str[1]);
		if (str[0].equals("1")) {
			throw new BizServiceException("卡已注销！");
		}
		
		String checkCardHolderMsg = this.checkCardHolderMsg(stockCountDTO);
		if (checkCardHolderMsg.equals("01")) {
			throw new BizServiceException("持卡人信息错误！");
		} else if (checkCardHolderMsg.equals("02")) {
			throw new BizServiceException("持卡人信息错误！");
		} else {
//			if (cardNo.equals("368")) {
//				// 获取4位随机数
//				//String biaoshi = String.valueOf((int) (Math.random() * 9000));
//				// 将数据转成中石油需要的json格式
//				Map<String, Object> mapz = new HashMap<String, Object>();
//				String card_No=stockCountDTO.getCardNo();
//				card_No=card_No.substring(0,16);
//				mapz.put("Method_Id", "001A");
//				mapz.put("User_Name", stockCountDTO.getFirstName());
//				mapz.put("Id_Type", stockCountDTO.getIdType());
//				mapz.put("Id_Num", stockCountDTO.getIdNo());
//				mapz.put("Card_Num", card_No);
//				mapz.put("Phone", stockCountDTO.getCardholderMobile());
//				mapz.put("Email", stockCountDTO.getCardholderEmail());
//				mapz.put("Gender", stockCountDTO.getCardholderGender());
//				mapz.put("Province",stockCountDTO.getProvince() );
//				mapz.put("City", stockCountDTO.getCity());
//				mapz.put("User_Id", "");
//				mapz.put("DataSource_id", "CNPC707717");
//				
//				mapz.put("Fied1", "");
//				mapz.put("Fied2", stockCountDTO.getCardNo());
//				//JSONArray obj = JSONArray.fromObject(mapz);
//				//String xmlData=ParseToXML.converterPayPalm(mapz, Constants.Method_BindingCard);
//				
//				// 往中石油发送报文
//				String dataXml=HttpPostData.getRemoteInfo(mapz, Constants.Method_BindingCard);
//				
//				try {
//					int errCode = JsonUtil.getErrcode(dataXml);
//					String errMsg=JsonUtil.getErrmsg(dataXml);
//					//Map maps = JsonConvert.parseJSON2Map(jsonS);
//					if (errCode!=200) {
//						throw new BizServiceException(errMsg);
//					}
//					// 把中石油返回的报文发给POSP -------------开始-------------------
//					dataXml=JsonUtil.getResult(dataXml);
//					SocketSend.SendToPOSP(dataXml,ConfigPosp.getSendPort());
//					// 把中石油返回的报文发给POSP -------------结束-------------------
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			//}
			/*如果卡已经被激活直接返回持卡人信息*/
			if(str[1].equals("1")){
				return this.cardHolderMsg.get(0);
			}
			String activeSta = this.cardActive(stockCountDTO);
			logger.debug("卡激活状态为：" + activeSta);
			if (activeSta.equals("00")) {
				return this.cardHolderMsg.get(0);
			} else {
				throw new BizServiceException("卡激活错误！");
			}

		}

	}
	
	
	
	/**
	 * 中石油权益账户交易
	 * @author liuyang
	 * @param stockCountDTO
	 * @return
	 * @throws BizServiceException
	 */
	public String equityAccountTranChina(EquityAccountTranChinaDTO  stockCountDTO) throws BizServiceException{
		logger.info("=============中石油权益账户交易接口开始调用=============");
		String msgs="";
		Map<String, Object> mapz = new HashMap<String, Object>();
		String card_No=stockCountDTO.getCardNo();
		card_No=card_No.substring(0,16);//中石油的卡号为16位
		mapz.put("Method_Id", "001A");//业务id
		mapz.put("User_Id", "");//用户编号
		mapz.put("Card_Num", card_No);//卡号
		mapz.put("Info_Type", stockCountDTO.getInfoType());//信息类别  100简要信息 200消费明细 300充值明细400 积分累积明细500 圈存明细
		mapz.put("startTime",stockCountDTO.getStartTime());//开始时间
		mapz.put("endTime", stockCountDTO.getEndTime());//结束时间
		mapz.put("Fied1", stockCountDTO.getCardholderMobile()+stockCountDTO.getIdType()+stockCountDTO.getIdNo());//备注字段1
		mapz.put("Fied2", stockCountDTO.getCardNo());//备注字段2
		

		JSONObject jsonObject = JSONObject.fromObject(mapz);
		String jsonStr=String.valueOf(jsonObject);
		//在报文的最后加上交易码
		jsonStr=jsonStr+"M001";
		logger.info("往posp发送，进行报文加密：" + jsonStr);
		//往posp发送，进行报文加密
		String msg=SocketSend.SendToPOSP(jsonStr,ConfigPosp.getSendPort());
		if(msg.equals("M00240")){
			throw new BizServiceException("缺少userId！");
		}
		if("E3".equals(msg)){
			throw new BizServiceException("posp通讯异常！");
		}
		// 往中石油发送报文
		String dataXml=HttpPostData.getRemoteInfo(msg, Constants.Method_GetCardNoInfo);
		logger.info("中石油权益账户交易的报文：" + dataXml);
		//往posp发送，进行报文解密,报文最后加上交易码
		dataXml=dataXml+"M002";
		msgs=SocketSend.SendToPOSP(dataXml,ConfigPosp.getSendPort());
		logger.info("中石油权益账户交易接口返回数据："+msgs);
		if("E3".equals(msgs)){
			throw new BizServiceException("posp通讯异常！");
		}
		return msgs;
	}
	
	/**
	 * 中石油绑卡 加密后
	 */
	public void bindCardToPetroChina(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		
		String status = this.getCardStatus(stockCountDTO.getCardNo());
		String[] aa=status.split(",");
		status=aa[0];
		String cardNo = stockCountDTO.getCardNo().trim().substring(0,5);
		if (status.equals("1")) {
			throw new BizServiceException("卡已注销！");
		}
		String checkCardHolderMsg = this.checkCardHolderMsg(stockCountDTO);
		if (checkCardHolderMsg.equals("01")) {
			throw new BizServiceException("持卡人信息错误！");
		} else if (checkCardHolderMsg.equals("02")) {
			throw new BizServiceException("持卡人信息错误！");
		} else {
			String [] cardBins=ConfigMakeCard.getCardBin().split(",");
			if(cardNo.equals(cardBins[0])||cardNo.equals(cardBins[1])){
		Map<String, Object> mapz = new HashMap<String, Object>();
		String card_No=stockCountDTO.getCardNo();
		card_No=card_No.substring(0,16);
		mapz.put("Method_Id", "001A");
		mapz.put("User_Name", stockCountDTO.getFirstName());
		mapz.put("Id_Type", stockCountDTO.getIdType());
		mapz.put("Id_Num", stockCountDTO.getIdNo());
		mapz.put("Card_Num", card_No);
		mapz.put("Phone", stockCountDTO.getCardholderMobile());
		mapz.put("Email", stockCountDTO.getCardholderEmail());
		mapz.put("Gender", stockCountDTO.getCardholderGender());
		mapz.put("Province",stockCountDTO.getProvince());
		mapz.put("City",stockCountDTO.getCity());
		mapz.put("User_Id", "");
		mapz.put("DataSource_id", ConfigPosp.getDataSourceId());
		mapz.put("Fied1", stockCountDTO.getCardholderMobile()+stockCountDTO.getIdType()+stockCountDTO.getIdNo());
		mapz.put("Fied2", stockCountDTO.getCardNo());
//		mapz.put("Method_Id", "001A");
//		mapz.put("User_Name", "个人九");
//		mapz.put("Id_Type", "1");
//		mapz.put("Id_Num", "37082919620507292X");
//		mapz.put("Card_Num", "9088800000011084");
//		mapz.put("Phone", "18201709379");
//		mapz.put("Email", "");
//		mapz.put("Gender", "");
//		mapz.put("Province","");
//		mapz.put("City","");
//		mapz.put("User_Id", "");
//		mapz.put("DataSource_id", ConfigPosp.getDataSourceId());
//		mapz.put("Fied1", "");
//		mapz.put("Fied2", "9088800000011084");
		//JSONArray obj = JSONArray.fromObject(mapz);
		//String xmlData=ParseToXML.converterPayPalm(mapz, Constants.Method_BindingCard);
		JSONObject jsonObject = JSONObject.fromObject(mapz);
		String jsonStr=String.valueOf(jsonObject);
		//在报文的最后加上交易码
		jsonStr=jsonStr+"001A";
		/**现在不需要判断是不是二次绑卡
		 * if(null==stockCountDTO.getOldCardNo()||stockCountDTO.getOldCardNo().equals("")){
			jsonStr=jsonStr+"001A";
		}else{ 
			jsonStr=jsonStr+"001C";
		}*/
		
		logger.info("往posp发送，进行报文加密：" + jsonStr);
		//往posp发送，进行报文加密
		String msg=SocketSend.SendToPOSP(jsonStr,ConfigPosp.getSendPort());
		/*if(msg.equals("001D40")){
			throw new BizServiceException("老卡未绑过卡！");
		}*/
		if("E3".equals(msg)){
			throw new BizServiceException("posp通讯异常！");
		}
		//msg=msg.substring(4);
		// 往中石油发送报文
		String dataXml=HttpPostData.getRemoteInfo(msg, Constants.Method_BindingCard);
		logger.info("中石油绑卡返回的报文：" + dataXml);
		//往posp发送，进行报文解密,报文最后加上交易码
		dataXml=dataXml+"001B";
		String msgs=SocketSend.SendToPOSP(dataXml,ConfigPosp.getSendPort());
		if("E3".equals(msgs)){
			throw new BizServiceException("posp通讯异常！");
		}
		
		String rspHeader = msgs.substring(0,4);
		
		if("001C".equalsIgnoreCase(rspHeader)){
			
			 dataXml=HttpPostData.getRemoteInfo(msgs.substring(4), Constants.Method_BindingCard);
			
			logger.info("二次绑卡中石油绑卡返回的报文：" + dataXml);
			//往posp发送，进行报文解密,报文最后加上交易码
			dataXml=dataXml+"001B";
			msgs=SocketSend.SendToPOSP(dataXml,ConfigPosp.getSendPort());
			if("E3".equals(msgs)){
				throw new BizServiceException("posp通讯异常！");
			}
		}
		else 
		{
			logger.info("posp解密后报文是：" + msgs);
		}		
		try {
			int errCode = JsonUtil.getErrcode(msgs);
			String errMsg=JsonUtil.getErrmsg(msgs);
			//Map maps = JsonConvert.parseJSON2Map(jsonS);
//			System.out.println("errCode:"+errCode+",errMsg:"+errMsg);
			if (errCode!=200) {
				logger.error("errCode：" + errCode+"===errMsg:"+errMsg);
				throw new BizServiceException(errMsg);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizServiceException("报文解析失败！");
		}
		}else{
			throw new BizServiceException("非联名卡！不能进行帮卡！");
		}
		}
		
	}
	
	/**
	 * 中石油绑卡 加密前
	 */
//	public void bindCardToPetroChina(ApplyAndBindCardDTO stockCountDTO)
//			throws BizServiceException {
//		
//		String status = this.getCardStatus(stockCountDTO.getCardNo());
//		String[] aa=status.split(",");
//		status=aa[0];
//		String cardNo = stockCountDTO.getCardNo().trim().substring(0,5);
//		if (status.equals("1")) {
//			throw new BizServiceException("卡已注销！");
//		}
//		String checkCardHolderMsg = this.checkCardHolderMsg(stockCountDTO);
//		if (checkCardHolderMsg.equals("01")) {
//			throw new BizServiceException("持卡人信息错误！");
//		} else if (checkCardHolderMsg.equals("02")) {
//			throw new BizServiceException("持卡人信息错误！");
//		} else {
//			if(cardNo.equals(ConfigMakeCard.getCardBin())){
//		Map<String, Object> mapz = new HashMap<String, Object>();
//		String card_No=stockCountDTO.getCardNo();
//		card_No=card_No.substring(0,16);
//		mapz.put("Method_Id", "001A");
//		mapz.put("User_Name", stockCountDTO.getFirstName());
//		mapz.put("Id_Type", stockCountDTO.getIdType());
//		mapz.put("Id_Num", stockCountDTO.getIdNo());
//		mapz.put("Card_Num", card_No);
//		mapz.put("Phone", stockCountDTO.getCardholderMobile());
//		mapz.put("Email", stockCountDTO.getCardholderEmail());
//		mapz.put("Gender", stockCountDTO.getCardholderGender());
//		mapz.put("Province",stockCountDTO.getProvince());
//		mapz.put("City",stockCountDTO.getCity());
//		mapz.put("User_Id", "");
//		mapz.put("DataSource_id", "CNPC707717");
//		
//		mapz.put("Fied1", "");
//		mapz.put("Fied2", stockCountDTO.getCardNo());
//		//JSONArray obj = JSONArray.fromObject(mapz);
//		//String xmlData=ParseToXML.converterPayPalm(mapz, Constants.Method_BindingCard);
//		
//		// 往中石油发送报文
//		
//		String dataXml=HttpPostData.getRemoteInfo(mapz, Constants.Method_BindingCard);
//		logger.info("中石油绑卡返回的报文：" + dataXml);
//		try {
//			int errCode = JsonUtil.getErrcode(dataXml);
//			String errMsg=JsonUtil.getErrmsg(dataXml);
//			//Map maps = JsonConvert.parseJSON2Map(jsonS);
//			if (errCode!=200) {
//				logger.error("errCode：" + errCode+"===errMsg:"+errMsg);
//				throw new BizServiceException(errMsg);
//			}
//			// 把中石油返回的报文发给POSP -------------开始-------------------
//			
//			dataXml=JsonUtil.getResult(dataXml);
//			logger.info("向POSＰ发送的报文：" + dataXml);
//			//解绑map
//			JsonUtil ju=new JsonUtil();
////			Map<String, Object> map = new HashMap<String, Object>();
////			map.put("Method_Id", "003A");
////			map.put("User_Id", ju.getUserId(dataXml));
////			map.put("Card_Num", card_No);
////			map.put("Fied1", "");
////			map.put("Fied2", stockCountDTO.getCardNo());
//			String msg=SocketSend.SendToPOSP(dataXml,ConfigPosp.getSendPort());
//			logger.info("POSP返回的报文：" + msg);
//			
//			String sta=ju.getStatus(msg);
//			if(!sta.equals("1")){
//				throw new BizServiceException("帮卡失败！");
//			}
//			// 把中石油返回的报文发给POSP -------------结束-------------------
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		}else{
//			throw new BizServiceException("非联名卡！不能进行帮卡！");
//		}
//		}
//		
//		
//	}

	public String getCardStatus(String CardNo) throws BizServiceException {
		AccPackageDTO accPackageDTO = new AccPackageDTO();
		accPackageDTO.setTxnCode("S000");
		accPackageDTO.setCardNo(CardNo);
		Map<String, String> rspCodeMap = RspCodeMap.getRspCodeMap();
		try {
			accPackageDTO = java2ACCBusinessService.queryCard(accPackageDTO);
		} catch (com.huateng.hstserver.exception.BizServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizServiceException("");
		}
		if (!"00".equals(accPackageDTO.getRespCode())) {
			if (rspCodeMap.containsKey(accPackageDTO.getRespCode())) {
				throw new BizServiceException(rspCodeMap.get(accPackageDTO
						.getRespCode()));
			}
		}

		return accPackageDTO.getOff()+","+accPackageDTO.getActiveState();

	}

	/* 插入持卡人信息 */
	public void insertApplyCardMsg(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		if (stockCountDTO.getProductId() == null
				|| stockCountDTO.getEntityId() == null
				|| stockCountDTO.getFirstName() == null
				|| stockCountDTO.getIdType() == null
				|| stockCountDTO.getIdNo() == null
				|| stockCountDTO.getRecipient_addr() == null
				|| stockCountDTO.getRecipient_name() == null
				|| stockCountDTO.getRecipient_phone() == null) {
			throw new BizServiceException("输入信息有误！");
		}
		try {
			PageDataDTO pageDataDTO=pageQueryDAO.query("ApplyAndBindCard.CheckApplyMsg",
					stockCountDTO);
			if(pageDataDTO.getData().size()>0){
				throw new BizServiceException("申请已提交，请勿重复提交！");
			}
			applyAndBindCardDao.insertApplyCard(
					"ApplyAndBindCard.insert_ApplyAndBindInfo", stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizServiceException("数据库插入错");
		}

	}
	/* 更新持卡人信息 */
	public void updateApplyCardMsg(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		if (stockCountDTO.getProductId() == null
				|| stockCountDTO.getEntityId() == null
				|| stockCountDTO.getFirstName() == null
				|| stockCountDTO.getIdType() == null
				|| stockCountDTO.getIdNo() == null
				|| stockCountDTO.getRecipient_addr() == null
				|| stockCountDTO.getRecipient_name() == null
				|| stockCountDTO.getRecipient_phone() == null) {
			throw new BizServiceException("输入信息有误！");
		}
		try {
			applyAndBindCardDao.update("ApplyAndBindCard.update_ApplyMsgInfo",
					stockCountDTO);
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			throw new BizServiceException("数据库更新错");
		}

	}

	/* 更新库存状态 */
	public void updateStockState(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		try {
			applyAndBindCardDao.update("ApplyAndBindCard.updateStockStatus",
					stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void ApplyCard(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
//		ApplyAndBindCardDTO applyAndBindCard = null;
//		try {
//			/* 查看是否已经申请过 */
//			applyAndBindCard = applyAndBindCardDao.getApplyAndBindCard(
//					"ApplyAndBindCard.lookApplyStatus", stockCountDTO);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		/*
		 * String stock = this.inqueryStockCount(stockCountDTO);
		 * if(stock.equals("01")){ throw new BizServiceException("库存不足"); }else
		 * if(stock.equals("02")){ throw new BizServiceException("其他错误"); }else{
		 */
		stockCountDTO.setApplyStatus("0");// 审核中
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String applyDate = format.format(new Date());
		stockCountDTO.setStartDate(applyDate);
//		
//		SimpleDateFormat formatSeconds = new SimpleDateFormat("yyyyMMddHHmmss");
//		String applyDateSeconds = formatSeconds.format(new Date());
//		stockCountDTO.setApplyDateSeconds(applyDateSeconds);
		
//		if (applyAndBindCard == null) {
		
		
		/*String corpCredType = stockCountDTO.getIdType();//客户证件类型
		String corpCredId = stockCountDTO.getIdNo();//客户证件号
		if (corpCredType.equals("1")) {
			String substring = corpCredId.substring(17, 18);
			if(!Character.isDigit(substring.charAt(0))){//不是数字
				if(!Character.isUpperCase(substring.charAt(0))){ //小写
					stockCountDTO.setIdNo(corpCredId.substring(0, 17)+"X");
				}
			}
		}*/
		stockCountDTO.setIdNo(stockCountDTO.getIdNo().toUpperCase());
		
		insertApplyCardMsg(stockCountDTO);/* 如果没有申请过插入数据库 */
//		} else {
//			updateApplyCardMsg(stockCountDTO);/* 申请过，则修改申请状态 */
//		}
		// }

	}

	@Override
	public PageDataDTO CheckApplyMsg(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException {
		/*
		 * PageDataDTO dto = new PageDataDTO(); try { List<ApplyAndBindCardDTO>
		 * list=applyAndBindCardDao.getApplyAndBindCardDTOList(
		 * "ApplyAndBindCard.listForCheckApplyMsg", stockCountDTO);
		 * dto.setData(list); } catch (SQLException e) { // TODO Auto-generated
		 * catch block throw new BizServiceException("查询审核卡信息错！"); }
		 */
		try {
			return pageQueryDAO.query("ApplyAndBindCard.listForCheckApplyMsg",
					stockCountDTO);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询审核信息失败");
		}

	}

	@Override
	public String lookApplyStatus(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		ApplyAndBindCardDTO applyAndBindCard = null;
		try {
			applyAndBindCard = applyAndBindCardDao.getApplyAndBindCard(
					"ApplyAndBindCard.lookApplyStatus", stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BizServiceException("查询审核状态错！");
		}
		if (applyAndBindCard == null) {
			return "";
		} else {
			return applyAndBindCard.getApplyStatus();
		}

	}

	public String getServiceId(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		String id = null;
		try {
			id = applyAndBindCardDao.getID("ApplyAndBindCard.queryServiceId",
					stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	public String getCardLayoutId(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		String id = null;
		try {
			id = applyAndBindCardDao.getID("ApplyAndBindCard.queryLayoutId",
					stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	public void insertCardHolderAndOrderMsg(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		/*
		 * Cardholder cardholder = new Cardholder();
		 * cardholder.setEntityId(stockCountDTO.getEntityId());
		 * 根据机构号，证件类型，证件号查找申请的信息 ApplyAndBindCardDTO singlePersonMsg =
		 * this.singlePersonMsg(stockCountDTO);
		 * cardholder.setCardholderBirthday(
		 * singlePersonMsg.getCardholderBirthday());
		 * cardholder.setCardholderComment
		 * (singlePersonMsg.getCardholderComment());
		 * cardholder.setCardholderGender
		 * (singlePersonMsg.getCardholderGender());
		 * cardholder.setCardholderFunction
		 * (singlePersonMsg.getCardholderFunction());
		 * cardholder.setCardholderMobile
		 * (singlePersonMsg.getCardholderMobile());
		 * cardholder.setCardholderEmail(singlePersonMsg.getCardholderEmail());
		 * cardholder
		 * .setCardholderSalutation(singlePersonMsg.getCardholderSalutation());
		 * cardholder
		 * .setCardholderSegment(singlePersonMsg.getCardholderSegment());
		 * cardholder.setFirstName(singlePersonMsg.getFirstName());
		 * cardholder.setLastName(singlePersonMsg.getLastName());
		 * cardholder.setIdType(stockCountDTO.getIdType());
		 * cardholder.setIdNo(stockCountDTO.getIdNo());
		 * //cardholder.setCheckState(""); cardholder.setCardholderState("1");
		 * cardholder.setDataState("1");
		 * cardholder.setCardholderId(commonsDAO.getNextValueOfSequence
		 * ("TB_CARDHOLDER")); cardholder.setCreateUser("0000");
		 * cardholder.setModifyUser("0000"); Date date = new Date(); String
		 * time=
		 * (date.getYear()+1900)+""+(date.getMonth()+1)+""+date.getDate()+""
		 * +date.getHours()+""+date.getMinutes()+""+date.getSeconds();
		 * cardholder.setModifyTime(time); cardholder.setCreateTime(time);
		 * 生成持卡人信息 try { applyAndBindCardDao.insertCardHolderMsg(
		 * "TB_CARDHOLDER.abatorgenerated_insert", cardholder); } catch
		 * (SQLException e) { // TODO Auto-generated catch block
		 * logger.error(e.getMessage()); throw new
		 * BizServiceException("插入持卡人信息错"); } 生成订单信息 SellOrder sellOrder = new
		 * SellOrder();
		 * sellOrder.setOrderId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER"
		 * )); sellOrder.setOrderType("10000011"); //Date date = new Date();
		 * StringBuffer sb = new StringBuffer(); sb.append(date.getYear()+1900);
		 * if(date.getMonth()<10){ sb.append("0"+(date.getMonth()+1)); }else{
		 * sb.append(date.getMonth()+1); } if(date.getDate()<0){
		 * sb.append("0"+date.getDate()); }else{ sb.append(date.getDate()); }
		 * 
		 * sellOrder.setOrderDate(sb.toString());
		 * sellOrder.setFirstEntityId(stockCountDTO.getEntityId());
		 * sellOrder.setProcessEntityId(stockCountDTO.getEntityId());
		 * sellOrder.setProductId(stockCountDTO.getProductId());
		 * //sellOrder.setValidityPeriod("");//卡片的失效日期
		 * sellOrder.setCardLayoutId(this.getCardLayoutId(stockCountDTO));//卡面ID
		 * sellOrder.setSaleMan("0000"); sellOrder.setDeliveryMeans("1");
		 * sellOrder.setAnnualFee("0");//年费 sellOrder.setCardIssueFee("0");//卡费
		 * sellOrder.setDeliveryFee("0");//送货费
		 * sellOrder.setPaymentTerm("2");//激活前
		 * sellOrder.setPaymentDelay("0");//付款延期天数
		 * sellOrder.setPaymentState("1"); sellOrder.setDiscountFee("0");//折扣费
		 * sellOrder.setAdditionalFee("0");//附加费
		 * sellOrder.setTotalPrice("0");//总费用 sellOrder.setFaceValueType("1");
		 * sellOrder.setFaceValue("0");
		 * sellOrder.setServiceId(this.getServiceId(stockCountDTO));//账户类型
		 * sellOrder.setOrderSource("3");//门户录入生成 sellOrder.setOrderState("32");
		 * sellOrder.setCardQuantity("1");//门户申请卡数量为1
		 * sellOrder.setRealCardQuantity("1");//实际卡数量1
		 * sellOrder.setDataState("1"); sellOrder.setCreateTime(time);
		 * sellOrder.setCreateUser("0000"); sellOrder.setModifyTime(time);
		 * sellOrder.setModifyUser("0000");
		 * 
		 * try { applyAndBindCardDao.insertSellOrderMsg(
		 * "TB_SELL_ORDER.abatorgenerated_insert", sellOrder); } catch
		 * (SQLException e) { // TODO Auto-generated catch block
		 * logger.error(e.getMessage()); throw new
		 * BizServiceException("插入持卡人信息错"); }
		 * 
		 * 生成订单卡关联信息 SellOrderCardList soc = new SellOrderCardList();
		 * soc.setOrderCardListId
		 * (commonsDAO.getNextValueOfSequenceBySequence("SEQ_SELL_ORDER_CARD_LIST"
		 * )); soc.setCardholderId(cardholder.getCardholderId());
		 * soc.setOrderId(sellOrder.getOrderId());
		 * soc.setFirstName(cardholder.getFirstName());
		 * soc.setLastName(cardholder.getLastName());
		 * soc.setCardState("3");//制卡成功 String card =
		 * this.getCard(stockCountDTO); soc.setCardNo(card);
		 * soc.setCreateTime(time); soc.setCreateUser("0000");
		 * soc.setModifyTime(time); soc.setModifyUser("0000");
		 * soc.setDataState("1");
		 */

		// applyAndBindCardDao.insertSellOrderCardList("TB_SELL_ORDER_CARD_LIST.abatorgenerated_insert",
		// soc);
		// stockCountDTO.setCardNo(card);
		// stockCountDTO.setStockStatus("3");
		stockCountDTO.setApplyStatus("1");// 审核通过
		// this.updateStockState(stockCountDTO);//更新在库状态
		this.ApplyRefuse(stockCountDTO);// 更新审核信息
	}

	public synchronized void applyCard(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		/*
		 * String count = this.inqueryStockCount(stockCountDTO);
		 * if(count.equals("00")){
		 * this.insertCardHolderAndOrderMsg(stockCountDTO); }else{
		 * logger.error("库存检查返回码："+count); throw new
		 * BizServiceException("库存不足！"); }
		 */
		this.insertCardHolderAndOrderMsg(stockCountDTO);

	}

	@Override
	public void CheckMsg(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		this.applyCard(stockCountDTO);

	}

	@Override
	public PageDataDTO mailMessages(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {

		// return pageQueryDAO.query("ApplyAndBindCard.listForCheckApplyMsg",
		// stockCountDTO);
		try {
			return pageQueryDAO.query("ApplyAndBindCard.listForCheckApplyMsg",
					stockCountDTO);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询邮寄信息失败");
		}

	}

	@Override
	public void MakeSureMail(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		try {
			applyAndBindCardDao.update(
					"ApplyAndBindCard.update_ApplyAndBindInfo", stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void ApplyRefuse(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		try {
			applyAndBindCardDao.update(
					"ApplyAndBindCard.update_ApplyAndBindInfo", stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public ApplyAndBindCardDTO getCardHolder(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		ApplyAndBindCardDTO dto = null;
		try {
			List<ApplyAndBindCardDTO> cardHolderMs = applyAndBindCardDao
					.getCardHolderMsg("ApplyAndBindCard.selectCardsHoderByDTO",
							stockCountDTO);
			if (cardHolderMs.size() == 0 || cardHolderMs.size() > 1) {
				throw new BizServiceException("卡不存在或卡号错！");
			} else {
				dto = cardHolderMs.get(0);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dto;
	}

	@Override
	public ApplyAndBindCardDTO onlineOrInline(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		// ApplyAndBindCardDTO dto=null;

		try {
			String id = applyAndBindCardDao.getID(
					"ApplyAndBindCard.queryOnlineOrInline", stockCountDTO);
			if (id == null) {
				throw new BizServiceException("卡号不存在！");
			}
			stockCountDTO.setUserId(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stockCountDTO;
	}

	@Override
	public List<String> cardNos(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		List<String> cardNos = new ArrayList<String>();
		List<ApplyAndBindCardDTO> list = null;
		try {
			list = applyAndBindCardDao.getApplyAndBindCardDTOList(
					"ApplyAndBindCard.queryCardNosByCardHolder", stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list == null || list.size() == 0) {
			throw new BizServiceException("持卡人下无卡！");
		} else {
			for (int i = 0; i < list.size(); i++) {
				cardNos.add(list.get(i).getCardNo());
			}
		}
		return cardNos;
	}

	@Override
	public ApplyAndBindCardDTO singlePersonMsg(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		ApplyAndBindCardDTO dto = null;
		try {
			List<ApplyAndBindCardDTO> applyAndBindCardDTOList = applyAndBindCardDao
					.getApplyAndBindCardDTOList(
							"ApplyAndBindCard.SinglePersonMessage",
							stockCountDTO);
			if (applyAndBindCardDTOList != null
					&& applyAndBindCardDTOList.size() != 0) {
				dto = applyAndBindCardDTOList.get(0);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public ApplyAndBindCardDTO lookApplyStatusForWeiXin(
			ApplyAndBindCardDTO stockCountDTO) throws BizServiceException {
		ApplyAndBindCardDTO applyAndBindCard = null;
		try {
			applyAndBindCard = applyAndBindCardDao.getApplyAndBindCard(
					"ApplyAndBindCard.lookApplyStatus", stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BizServiceException("查询审核状态错！");
		}

		return applyAndBindCard;
	}
}
