package com.allinfinance.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.mapper.GetDataMapper;
import com.allinfinance.model.GetCardInfo;
import com.allinfinance.model.HistoryCardInfo;
import com.allinfinance.model.RechargeTxnInfo;
import com.allinfinance.model.SingleTransactionInfo;
import com.allinfinance.service.GetDataService;
@Service
public class GetDataServiceImpl implements GetDataService {
	private Logger logger=LoggerFactory.getLogger(GetDataServiceImpl.class);
@Autowired
GetDataMapper getDataMapper;
	@Override
	public String selectGetIsRegisterCardInfo(Map<String, Object> condition) {	
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		String endTime=dateFormat.format(new Date());
		long startTime=Long.parseLong(endTime)-1;//前一天
//		condition.put("endTime", endTime);
		condition.put("startTime", startTime);
		condition.put("orderType", "10000011");//订单类型为 个人记名卡
		condition.put("orderState", "32");//订单状态为配送成功的	
		List<GetCardInfo>list= getDataMapper.selectGetCardInfo(condition);
		StringBuilder builder=new StringBuilder();  
		String lineSeparator = System.getProperty("line.separator"); 	//从当前系统中获取换行符
		lineSeparator="\r\n";
		for (GetCardInfo info : list) {
			String seriaNo=info.getSerialNo();//流水号 数据库订单id+TB_SELL_ORDER_CARD_LIST表的ORDER_CARD_LIST_ID组成 		    
			String cardMon=info.getCardMon();//卡本金
			String cardNo=info.getCardNo();//卡号
			String expiryDate=info.getExpiryDate();//卡有效期止
			String applyTime=info.getApplyTime();//领卡时间售卡时间
			if(cardMon==null||cardMon.equals("")){
				cardMon="0";
			}
			builder.append("0").append("||").append(seriaNo).append("||").append(cardNo).append("||").append(cardNo)
			.append("||").append(cardMon).append("||").append(cardMon).append("||").append(applyTime)
			.append("||").append(applyTime.substring(0, 8)).append("||").append(expiryDate).append("||").append("1")
			.append("||||||||||||||||||").append(lineSeparator);
			;
		}				
		 return builder.toString();
	}
	@Override
	public String selectGetNotIsRegisterCardInfo(Map<String, Object> condition) {
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		String endTime=dateFormat.format(new Date());
		long startTime=Long.parseLong(endTime)-1;//前一天
//		condition.put("endTime", endTime);
		condition.put("startTime", startTime);
		condition.put("orderType", "10000012");//订单类型为 个人不记名卡
		condition.put("orderState", "32");//订单状态为配送成功的	
		List<GetCardInfo>list= getDataMapper.selectGetCardInfo(condition);
		StringBuilder builder=new StringBuilder();  
		String lineSeparator = System.getProperty("line.separator"); 	//从当前系统中获取换行符
		lineSeparator="\r\n";
		for (GetCardInfo info : list) {
			String seriaNo=info.getSerialNo();//流水号 数据库订单id+TB_SELL_ORDER_CARD_LIST表的ORDER_CARD_LIST_ID组成 		    
			String cardMon=info.getCardMon();//卡本金
			String cardNo=info.getCardNo();//卡号
			String expiryDate=info.getExpiryDate();//卡有效期止
			String applyTime=info.getApplyTime();//领卡时间售卡时间
			if(cardMon==null||cardMon.equals("")){
				cardMon="0";
			}
			builder.append("0").append("||").append(seriaNo).append("||").append(cardNo).append("||").append(cardNo)
			.append("||").append(cardMon).append("||").append(cardMon).append("||").append(applyTime)
			.append("||").append(applyTime.substring(0, 8)).append("||").append(expiryDate).append("||").append("0")
			.append("||||||||||||||||||").append(lineSeparator);
			;
		}				
		 return builder.toString();		
	}
	@Override
	public String selectSingleTxnInfo(Map<String, Object> condition) {
		String lineSeparator = System.getProperty("line.separator"); 
		lineSeparator="\r\n";
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		String endTime=dateFormat.format(new Date());
		long startTime=Long.parseLong(endTime)-1;//前一天txnDate
		condition.put("txnCode", "'0002','0004','0005','7225'");//所有充值的交易码
		condition.put("txnDate", startTime);
		//condition.put("txnDate", 20170321);
		List<RechargeTxnInfo> list=getDataMapper.selectSingleTxnInfo(condition);
		StringBuilder builder=new StringBuilder();
		for (RechargeTxnInfo info : list) {
			String txnType=info.getTxnType();
			String onymoumState=info.getOnymoumState();//是否是记名卡2不记名 3 记名
			String serialNo=info.getSerialNo();
			String cardNo=info.getCardNo();
			String cardBalance=info.getCardBalance();
			String chargeTime=info.getChargeTime();
			String chargeMon=info.getCardMon();
			String beginDate=info.getBeginDate();
			String expiryDate=info.getExpiryDate();
			if(chargeMon==null||chargeMon.trim().equals("")){
				chargeMon="0";
			}else{
				chargeMon=String.valueOf((Double.parseDouble(chargeMon)/100));
			}
			builder.append("1").append("||").append(serialNo).append("||").append(cardNo).append("||").append(cardNo)
			.append("||").append(cardBalance).append("||").append(cardBalance).append("||").append(chargeTime)
			.append("||").append(chargeMon).append("||").append(chargeMon).append("||").append("0").append("||||||");
			if(txnType.equals("0002")&&onymoumState.equals("2")){//如果是首次充值不记名卡
				builder.append("0").append("||").append("").append("||||");
			}else
			if(txnType.equals("0002")&&onymoumState.equals("3")){//如果是首次充值记名卡
				builder.append("1").append("||").append("").append("||||");
			}else{
				builder.append("").append("||").append("").append("||||");				
			}
			builder.append(lineSeparator);
			
		}
		return builder.toString();
	}
	@Override
	public String selectTransaction(Map<String, Object> condition) {
		String lineSeparator = System.getProperty("line.separator"); 
		lineSeparator="\r\n";
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		String endTime=dateFormat.format(new Date());
		long startTime=Long.parseLong(endTime)-1;//前一天txnDate
		condition.put("txnCode", "'3105','7005','7125','7105','P001','5205','1306'");//所有正向交易码
		condition.put("txnDate", startTime);
		List<SingleTransactionInfo> list=getDataMapper.selectTransaction(condition);//查询正向交易
		StringBuilder builder=new StringBuilder();
		for (SingleTransactionInfo info : list) {
			String txnType=info.getTxnType();
			String serialNo=info.getSerialNo();
			String cardNo=info.getCardNo();
			String tranBalance=info.getTranBalance();
			String tranTime=info.getTranTime();
			String cardBalance=info.getCardBalance();
			if(tranBalance==null||tranBalance.trim().equals("")){
				tranBalance="0";
			}else{
				tranBalance=String.valueOf((Double.parseDouble(tranBalance)/100));
			}
			builder.append("2").append("||").append(serialNo).append("||")
			.append(cardNo).append("||").append(cardNo).append("||").append(cardBalance).append("||")
			.append(cardBalance).append("||").append(tranTime).append("||").append(tranBalance).append("||")
			.append(tranBalance).append("||").append("0").append("||||||||");	
			builder.append(lineSeparator);
		}
		condition.put("txnCode", "'1105','0006','1305'");//所有负向交易码  1105    0006  1305   
		list=getDataMapper.selectTransaction(condition);//查询负向交易
		for (SingleTransactionInfo info : list) {
			String txnType=info.getTxnType();
			String serialNo=info.getSerialNo();
			String cardNo=info.getCardNo();
			String tranBalance=info.getTranBalance();
			String tranTime=info.getTranTime();
			String cardBalance=info.getCardBalance();
			if(tranBalance==null||tranBalance.trim().equals("")){
				tranBalance="0";
			}else{
				tranBalance=String.valueOf((Double.parseDouble(tranBalance)/100));
			}
			builder.append("2").append("||").append(serialNo).append("||")
			.append(cardNo).append("||").append(cardNo).append("||").append(cardBalance).append("||")
			.append(cardBalance).append("||").append(tranTime).append("||").append(tranBalance).append("||")
			.append(tranBalance).append("||").append("1").append("||||||||");	
			builder.append(lineSeparator);
		}
		return builder.toString();
	}
	@Override
	public String getHistoryCard() {
		StringBuilder history=new StringBuilder();
		Map<String, Object> condition=new HashMap<String,Object>();
		condition.put("orderType", "'10000011','10000012'");//订单类型
		condition.put("startDate", "20180101");//开始时间
		condition.put("endDate", "20180621");//结束时间
		List<HistoryCardInfo> list= getDataMapper.selectHistory(condition);
	
		for (HistoryCardInfo historyCardInfo : list) {
			try{
			String accBal=historyCardInfo.getAccBal();
			String validDate=historyCardInfo.getValidDate();
			validDate=validDate.substring(0,4)+"/"+validDate.substring(4,6)+"/"+validDate.substring(6,8);
			String createDate=historyCardInfo.getCreateTime();
			createDate=createDate.substring(0,4)+"/"+createDate.substring(4,6)+"/"+createDate.substring(6,8);
			String cardNo=historyCardInfo.getCardNo();
			String orderType=historyCardInfo.getOrderType();
			
		history.append(cardNo).append("||").append(cardNo).append("||").append(cardNo).append("||");
		if("10000012".equals(orderType)){//个人不记名卡
			history.append("0").append("||").append("0").append("||").append(accBal).append("||")
			.append(createDate).append("||").append(accBal).append("||||||||||").append(createDate)
			.append("||").append(validDate).append("||||||");
		}
		if("10000011".equals(orderType)){//个人记名卡
			history.append("1").append("||").append("0").append("||").append(accBal).append("||")
			.append(createDate).append("||").append(accBal).append("||||||||||").append(createDate)
			.append("||").append(validDate).append("||||||||||||");
		}
		history.append("\r\n");	
			}catch (Exception e) {
				logger.error("数据出错"+historyCardInfo.toString());
			}
		}
		return history.toString();
	}
	

}
