package com.webservice.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.apache.xml.utils.IntVector;

import com.allinfinance.shangqi.gateway.dto.AccountConsumDTO;
import com.allinfinance.shangqi.gateway.dto.AccountQueryDTO;
import com.allinfinance.shangqi.gateway.dto.RecordConsumeDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.servlet.InitSystemServlet;
import com.huateng.framework.util.ConfigPosp;
import com.huateng.hstserver.constants.RspCodeMap;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
import com.webservice.util.Constants;
import com.webservice.util.HttpPostData;
import com.webservice.util.ParseToXML;
import com.webservice.util.PropertyLoad;
import com.webservice.util.SocketSend;
import com.webservice.util.XmlDom;

public class SendToPospServiceImpl implements SendToPospService {

	private Logger logger = Logger.getLogger(SendToPospServiceImpl.class);
	private AccountQueryDTO accountQueryDTO = new AccountQueryDTO();
	private Java2ACCBusinessServiceImpl java2ACCBusinessService;

	// 中石油查询账户余额
	@Override
	public AccountQueryDTO accountBalanceQuery(AccountConsumDTO stockCountDTO)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("中石油余额查询开始！");
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("SCode", "1021");// 服务码
		map.put("TermCode", ConfigPosp.getCardAccptrTermnlId());// 终端代码
		map.put("MechtCode", ConfigPosp.getCardAccptrId());// 商户代码
		map.put("TxnTlr", stockCountDTO.getLoginUserId());// 交易操作员号
		map.put("ChanlTxnNum", stockCountDTO.getSysTraceAuditNum());// 门户的流水号
		map.put("ChanlTime", stockCountDTO.getChanlTime());// 门户的时间
		map.put("BatchNo", "000001");// 批次号
		map.put("MsgType", "0200");// 消息类型
		map.put("PrimaryAcctNum", stockCountDTO.getCardNo());// 主账号
		map.put("ProcessingCode", "310000");// 交易处理码
		map.put("SysTraceAuditNum", stockCountDTO.getSysTraceAuditNum());// 流水号
		map.put("PosEntryModeCode", "051");// 服务点输入方式码
		map.put("CardSeqId", "000");// 卡片序列号
		map.put("PosCondCode", "00");// 服务点条件码
		map.put("CardAccptrTermnlId", ConfigPosp.getCardAccptrTermnlId());// 终端号
		map.put("CardAccptrId", ConfigPosp.getCardAccptrId());// 商户号
		map.put("CurrcyCodeTrans", "156");// 交易货币代码
		map.put("sPinData", stockCountDTO.getSPinData());// 个人标识码数据
		map.put("SecRelatdCtrlInfo", "2600000000000000");// 安全控制信息
		map.put("EmvVal", "0000000000");// IC卡数据域sss
		String xml = ParseToXML.converterPayPalm(map);
		logger.info("余额查询往posp发送的报文：" + xml);
		String msg = SocketSend.SendToPOSP(xml, ConfigPosp.getPort());
		logger.info("余额查询posp返回的报文：" + msg);
		if ("E3".equals(msg)) {
			throw new BizServiceException("posp通讯异常！");
		}
		Map<String, String> Xmlmap = XmlDom.parse(msg);
		// 交易响应码(“00” 成功)
		String frontRspCode = Xmlmap.get("FrontRspCode");

		if (!frontRspCode.equals("00")) {
			String code = frontRspCode.substring(0, 1);
			String reMsg = frontRspCode;
			if (code.equals("Z")) {
				String errorMsg = Xmlmap.get("ReplyMsg").substring(4);
				reMsg = reMsg + errorMsg;
			}
			throw new BizServiceException(reMsg);
		} else {
			accountQueryDTO.setCardNo(Xmlmap.get("CardNo"));
			accountQueryDTO.setBalance(Xmlmap.get("PettyAcctAmt"));// 卡备用金余额
			accountQueryDTO.setCardBalance(Xmlmap.get("AcctAmt"));// 卡余额
			accountQueryDTO
					.setCardLoyaltyBalance(Xmlmap.get("AcctIntegration"));// 卡积分
			accountQueryDTO.setCardStatus(Xmlmap.get("CardState"));
			accountQueryDTO.setCardType(Xmlmap.get("CardType"));
			accountQueryDTO.setFullName(Xmlmap.get("Name"));
			accountQueryDTO.setLoyaltyBalance(Xmlmap
					.get("PettyAcctIntegration"));// 卡备用金积分
			logger.info("中石油余额查询结束！");
			return accountQueryDTO;
		}

	}

	// 中石油账户转充
	@Override
	public void accountConsum(AccountConsumDTO stockCountDTO) throws Exception {
		// TODO Auto-generated method stub
		if (null == stockCountDTO.getOperationType()||"".equals(stockCountDTO.getOperationType())) {
			throw new BizServiceException("operationType字段不能为空！");
		}
		if (stockCountDTO.getOperationType().equals("1")) {
			stockCountDTO = canteenRecharge(stockCountDTO);
			if (stockCountDTO.getOperationType() != "00") {
				throw new BizServiceException(stockCountDTO.getOperationType());
			}

		} else {
			logger.info("中石油账户转充开始！");
			AccountQueryDTO QueryDTO = new AccountQueryDTO();
			// 查询余额
			// QueryDTO=this.accountBalanceQuery(stockCountDTO);
			// String yue=QueryDTO.getCardBalance();
			// //获取总金额 （余额+充值金额）
			// double
			// zje=Double.valueOf(yue)+Double.valueOf(stockCountDTO.getMoney());
			// if(zje>5000){
			// throw new BizServiceException("总金额大于5000！");
			// }
			// 填充金额 例：0.09 转 000000000009
			/*String money = stockCountDTO.getMoney();
			if (money.length() < 12) {
				if (isBigDecimal(money)) {
					DecimalFormat df = new DecimalFormat("#");
					money = df.format(Double.valueOf(money) * 100);
				} else {
					money = String.valueOf(Integer.parseInt(money) * 100);
				}
				int len = money.length();
				len = 12 - len;
				for (int i = 0; i < len; i++) {
					money = "0" + money;
				}
			}*/
			BigDecimal bigDecimal=new BigDecimal( stockCountDTO.getMoney());
			BigDecimal num=new BigDecimal("100");
			String money = String.format("%012d",bigDecimal.multiply(num).intValue());
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("SCode", "1601");// 服务码
			map.put("TermCode", ConfigPosp.getCardAccptrTermnlId());// 终端代码
			map.put("MechtCode", ConfigPosp.getCardAccptrId());// 商户代码
			map.put("TxnTlr", stockCountDTO.getLoginUserId());// 交易操作员号
			map.put("ChanlTxnNum", stockCountDTO.getTraceAuditNum());// 门户的流水号
			map.put("ChanlTime", stockCountDTO.getChanlTime());// 门户的时间
			map.put("BatchNo", "000001");// 批次号
			map.put("AmtTrans", money);// 转充金额
			map.put("MsgType", "0200");// 消息类型
			map.put("PrimaryAcctNum", stockCountDTO.getCardNo());// 主账号
			map.put("ProcessingCode", "000000");// 交易处理码
			map.put("SysTraceAuditNum", stockCountDTO.getTraceAuditNum());// 流水号
			map.put("SQssn", stockCountDTO.getTraceAuditNum());// 流水号
			map.put("PosEntryModeCode", "051");// 服务点输入方式码
			map.put("CardSeqId", "000");// 卡片序列号
			map.put("PosCondCode", "00");// 服务点条件码
			map.put("CardAccptrTermnlId", ConfigPosp.getCardAccptrTermnlId());// 终端号
			map.put("CardAccptrId", ConfigPosp.getCardAccptrId());// 商户号
			map.put("CurrcyCodeTrans", "156");// 交易货币代码
			map.put("sPinData", stockCountDTO.getSPinData());// 个人标识码数据
			// map.put("sPinData","");
			map.put("SecRelatdCtrlInfo", "2600000000000000");// 安全控制信息
			map.put("sPhoneNo", stockCountDTO.getPhone());// 手机号
			map.put("EmvVal", "0000000000");// IC卡数据域
			String xml = ParseToXML.converterPayPalm(map);
			logger.info("账户转充往posp发送的报文：" + xml);
			String remsg = SocketSend.SendToPOSP(xml, ConfigPosp.getPort());
			logger.info("账户转充posp返回的报文：" + remsg);
			if ("E3".equals(remsg)) {
				throw new BizServiceException("posp通讯异常！");
			}
			Map<String, String> Xmlmap = XmlDom.parse(remsg);
			// 交易响应码(“00” 成功)
			String frontRspCode = Xmlmap.get("FrontRspCode");

			if (!frontRspCode.equals("00")) {
				String code = frontRspCode.substring(0, 1);
				String reMsg = frontRspCode;
				if (code.equals("Z")) {
					String errorMsg = Xmlmap.get("ReplyMsg").substring(4);
					reMsg = reMsg + errorMsg;
				}
				throw new BizServiceException(reMsg);

			}
			logger.info("中石油账户转充结束！");
		}

	}

	// 消费记录查询
	@Override
	public RecordConsumeDTO queryConsume(RecordConsumeDTO recordConsumeDTO)
			throws Exception {
		logger.info("查询消费交易记录开始！");
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("txncode", recordConsumeDTO.getTxnCode());// 交易码
		map.put("oprId", recordConsumeDTO.getOprId());// 用户id
		map.put("mac", recordConsumeDTO.getMac());// mac校验数据
		map.put("cardNo", recordConsumeDTO.getCardNo());// 卡号
		map.put("starDate", recordConsumeDTO.getStarDate());// 查询起始日期
		map.put("enDate", recordConsumeDTO.getEnDate());// 查询终止日期
		map.put("startNum", recordConsumeDTO.getStartNum());// 起始条目编号
		map.put("endNum", recordConsumeDTO.getEndNum());// 结束条目编号
		String xml = ParseToXML.converterXML(map);
		logger.info("消费记录查询往posp发送的报文：" + xml);
		String msg = SocketSend.SendToPOSPForRecordConsume(xml,
				ConfigPosp.getRecordConsumePort());
		logger.info("消费记录查询posp返回的报文：" + msg);
		if ("E3".equals(msg)) {
			throw new BizServiceException("posp通讯异常！");
		}
		Map<String, String> Xmlmap = XmlDom.parse2(msg);
		// 交易响应码(“00” 成功)
		String frontRspCode = Xmlmap.get("respCode");
		if (!frontRspCode.equals("00")) {
			throw new BizServiceException("查询消费交易记录失败！");
		} else {
			RecordConsumeDTO dto = new RecordConsumeDTO();
			dto.setTxnCode(Xmlmap.get("txncode"));
			// dto.setOprId(Xmlmap.get("oprId"));
			dto.setSumNum(Xmlmap.get("sumNum"));
			dto.setCardNo(Xmlmap.get("cardNo"));
			String realNum = Xmlmap.get("realNum");
			int count = Integer.parseInt(realNum);
			String[] str = new String[count];
			for (int i = 0; i < count; i++) {
				int num = i + 1;
				str[i] = Xmlmap.get("num" + num);
			}
			dto.setData(str);
			logger.info("查询消费交易记录结束！");
			return dto;
		}

	}

	// 食堂充值
	public AccountConsumDTO canteenRecharge(AccountConsumDTO stockCountDTO)
			throws Exception {
		// TODO Auto-generated method stub
		// 填充金额 例：0.09 转 000000000009
		/*String money = stockCountDTO.getMoney();
		if (money.length() < 12) {
			if (isBigDecimal(money)) {
				DecimalFormat df = new DecimalFormat("#");
				money = df.format(Double.valueOf(money) * 100);
			} else {
				money = String.valueOf(Integer.parseInt(money) * 100);
			}
			int len = money.length();
			len = 12 - len;
			for (int i = 0; i < len; i++) {
				money = "0" + money;
			}
		}*/
		
		BigDecimal bigDecimal=new BigDecimal( stockCountDTO.getMoney());
		BigDecimal num=new BigDecimal("100");
		String money = String.format("%012d",bigDecimal.multiply(num).intValue());
		logger.info("食堂转充开始！");
		// AccPackageDTO dto=new AccPackageDTO();
		// dto.setTxnCode("S32");
		// dto.setPassword(stockCountDTO.getSPinData());
		// dto.setCardNo(stockCountDTO.getCardNo());
		// dto.setProductId(stockCountDTO.getDefaultEntityId());
		// dto.setCallBack(stockCountDTO.getPhone());//手机号借用字段CallBack
		// String [] balances=new String []{money};//充值金额
		// dto.setBalances(balances);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("TxnCode", "S340");// 交易码
		map.put("SPinData", stockCountDTO.getSPinData());// 密码
		map.put("CardNo", stockCountDTO.getCardNo());// 卡号
		map.put("DefaultEntityId", stockCountDTO.getDefaultEntityId());// 产品ID
		map.put("Phone", stockCountDTO.getPhone());// 电话号码
		map.put("TxnAmt", money);// c充值金额
		map.put("Channel", "70000001");// 渠道号
		map.put("LoginUserId", stockCountDTO.getLoginUserId());// 操作员ID
		map.put("TraceAuditNum", stockCountDTO.getTraceAuditNum());// 门户流水号
		map.put("chanlTime", stockCountDTO.getChanlTime());// 交易日期
		// dto= java2ACCBusinessService.queryCardBalance(dto);
		String xml = ParseToXML.converterXML(map);
		logger.info("食堂充值往核心发送的报文：" + xml);
		String msg = SocketSend.SendToCore(xml, ConfigPosp.getCorePort());
		logger.info("食堂充值核心返回的报文：" + msg);
		Map<String, String> Xmlmap = XmlDom.parse2(msg);
		String rspCode = Xmlmap.get("RespCode");
		stockCountDTO.setOperationType(rspCode);
		if (rspCode.equals("00")) {
			stockCountDTO.setMoney(Xmlmap.get("Balances"));
		}
		return stockCountDTO;

	}

	// 商城消费
	public AccountConsumDTO consumeMall(AccountConsumDTO stockCountDTO)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			//交易金额转换  例：0.09 转 000000000009
			BigDecimal bigDecimal=new BigDecimal( stockCountDTO.getMoney());
			BigDecimal num=new BigDecimal("100");
			String money = String.format("%012d",bigDecimal.multiply(num).intValue());
			logger.info("商城消费开始！");
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("SCode", "1101");// 服务码
			map.put("TermCode", ConfigPosp.getMallAccptrTermnlId());// 终端代码
			map.put("MechtCode", ConfigPosp.getMallAccptrId());// 商户代码
			map.put("TxnTlr", stockCountDTO.getLoginUserId());// 交易操作员号
			map.put("ChanlTxnNum", stockCountDTO.getTraceAuditNum());// 门户的流水号
			map.put("ChanlTime", stockCountDTO.getChanlTime());// 门户的时间
			map.put("BatchNo", "000001");// 批次号
			map.put("MsgType", "0200");// 交易码
			map.put("PrimaryAcctNum", stockCountDTO.getCardNo());// 卡号
			map.put("ProcessingCode", "000000");// 交易处理码
			map.put("AmtTrans", money);// 交易金额
			String traceAuditNum=stockCountDTO.getTraceAuditNum().substring(
					stockCountDTO.getTraceAuditNum().length()-6);
			map.put("SysTraceAuditNum", traceAuditNum);// 流水号
			map.put("PosEntryModeCode", "051");// 服务点输入方式码
			map.put("CardSeqId", "000");// 卡片序列号
			map.put("PosCondCode", "00");// 服务点条件码
			map.put("CardAccptrTermnlId", ConfigPosp.getMallAccptrTermnlId());// 终端号
			map.put("CardAccptrId", ConfigPosp.getMallAccptrId());// 商户号
			map.put("CurrcyCodeTrans", "156");// 交易货币代码
			map.put("sPinData", stockCountDTO.getSPinData());// 个人标识码数据
			map.put("SecRelatdCtrlInfo", "2600000000000000");// 安全控制信息
			map.put("EmvVal", "0000000000");// IC卡数据域

			// dto= java2ACCBusinessService.queryCardBalance(dto);
			String xml = ParseToXML.converterPayPalm(map);
			logger.info("商城消费往posp发送的报文：" + xml);
			String remsg="";
			try {
				remsg = SocketSend.SendToPOSP(xml, ConfigPosp.getPort());
			} catch (Exception e) {
				// TODO: handle exception
				throw new BizServiceException("posp通讯异常！");
			}
			if(remsg.equals("E3")){
				throw new BizServiceException("posp通讯异常！");
			}
			logger.info("商城消费posp返回的报文：" + remsg);
			Map<String, String> Xmlmap = XmlDom.parse(remsg);
			String frontRspCode = Xmlmap.get("FrontRspCode");// 返回码
			String chanlTxnNum = Xmlmap.get("RspTxnNum");// 返回流水
			String retrivlRefNum=Xmlmap.get("RetrivlRefNum"); //检索参考号
			stockCountDTO.setReturnCode(frontRspCode);
			stockCountDTO.setChanlTxnNum(chanlTxnNum);
			stockCountDTO.setRetrivlRefNum(retrivlRefNum);
			logger.info("商城消费结束！");
			return stockCountDTO;
		} catch (Exception e) {
			// TODO: handle exception
			throw new BizServiceException("处理异常！");
		}
		

	}
	
		// 商城退货申请
		public AccountConsumDTO consumeMallCancel(AccountConsumDTO stockCountDTO)
				throws Exception {
			// TODO Auto-generated method stub
			try {
				//交易金额转换  例：0.09 转 000000000009
				BigDecimal bigDecimal=new BigDecimal( stockCountDTO.getMoney());
				BigDecimal num=new BigDecimal("100");
				String money = String.format("%012d",bigDecimal.multiply(num).intValue());
				logger.info("商城退货申请开始！");
				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put("SCode", "5051");// 服务码
				map.put("TermCode", ConfigPosp.getMallAccptrTermnlId());// 终端代码
				map.put("MechtCode", ConfigPosp.getMallAccptrId());// 商户代码
				map.put("TxnTlr", stockCountDTO.getLoginUserId());// 交易操作员号
				map.put("ChanlTxnNum", stockCountDTO.getTraceAuditNum());// 门户的流水号
				map.put("ChanlTime", stockCountDTO.getChanlTime());// 门户的时间
				map.put("BatchNo", "000001");// 批次号
				map.put("MsgType", "0220");// 交易码
				map.put("PrimaryAcctNum", stockCountDTO.getCardNo());// 卡号
				map.put("ProcessingCode", "200000");// 交易处理码
				map.put("AmtTrans", money);// 交易金额
				String traceAuditNum=stockCountDTO.getTraceAuditNum().substring(
						stockCountDTO.getTraceAuditNum().length()-6);
				map.put("SysTraceAuditNum", traceAuditNum);// 流水号
				map.put("sDateLocalTrans",stockCountDTO.getsDateLocalTrans());//原交易日期
				map.put("RetrivlRefNum",stockCountDTO.getRetrivlRefNum());//原交易的检索参考号
				map.put("CardAccptrTermnlId", ConfigPosp.getMallAccptrTermnlId());// 终端号
				map.put("CardAccptrId", ConfigPosp.getMallAccptrId());// 商户号

				String xml = ParseToXML.converterPayPalm(map);
				logger.info("商城退货申请往posp发送的报文：" + xml);
				String remsg ="";
				try {
					remsg = SocketSend.SendToPOSP(xml, ConfigPosp.getPort());
				} catch (Exception e) {
					// TODO: handle exception
					throw new BizServiceException("posp通讯异常！");
					}
				if(remsg.equals("E3")){
					throw new BizServiceException("posp通讯异常！");
				}
				logger.info("商城退货申请posp返回的报文：" + remsg);
				Map<String, String> Xmlmap = XmlDom.parse(remsg);
				String frontRspCode = Xmlmap.get("FrontRspCode");// 返回码
				String chanlTxnNum = Xmlmap.get("RspTxnNum");// 返回流水
				stockCountDTO.setReturnCode(frontRspCode);
				stockCountDTO.setChanlTxnNum(chanlTxnNum);
				logger.info("商城退货申请结束！");
				return stockCountDTO;
			} catch (Exception e) {
				// TODO: handle exception
				throw new BizServiceException("处理异常！");
			}
			

		}
//		退货申请状态查询
		public AccountConsumDTO queryConsumeMallCancelState(AccountConsumDTO stockCountDTO)
				throws Exception {
			logger.info("商城退货状态查询开始！");
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("SCode", "5021");// 服务码
			map.put("TermCode", ConfigPosp.getMallAccptrTermnlId());// 终端代码
			map.put("MechtCode", ConfigPosp.getMallAccptrId());// 商户代码
			map.put("TxnTlr", stockCountDTO.getLoginUserId());// 交易操作员号
			map.put("ChanlTxnNum", stockCountDTO.getTraceAuditNum());// 门户的流水号
			map.put("ChanlTime", stockCountDTO.getChanlTime());// 门户的时间
			map.put("BatchNo", "000001");// 批次号
			map.put("MsgType", "0200");// 交易码
			map.put("PrimaryAcctNum", stockCountDTO.getCardNo());// 卡号
			map.put("ProcessingCode", "310000");// 交易处理码
			map.put("OrgSSN", stockCountDTO.getSysTraceAuditNum());// 退货申请流水号
			map.put("CardAccptrTermnlId", ConfigPosp.getMallAccptrTermnlId());// 终端号
			map.put("CardAccptrId", ConfigPosp.getMallAccptrId());// 商户号
			String xml = ParseToXML.converterPayPalm(map);
			logger.info("商城退货状态查询往posp发送的报文：" + xml);
			String remsg="";
			try {
				remsg = SocketSend.SendToPOSP(xml, ConfigPosp.getPort());
			} catch (Exception e) {
				// TODO: handle exception
				throw new BizServiceException("posp通讯异常！");
			}
			if(remsg.equals("E3")){
				throw new BizServiceException("posp通讯异常！");
			}
			logger.info("商城退货状态查询posp返回的报文：" + remsg);
			Map<String, String> Xmlmap = XmlDom.parse(remsg);
			String frontRspCode = Xmlmap.get("FrontRspCode");// 返回码
			String txnReplyMsg = Xmlmap.get("TxnReplyMsg");// 退款状态原因描述
			String cardNo = Xmlmap.get("CardNo");// 卡号
			String txnAmt = Xmlmap.get("TxnAmt");// 交易金额
			String txnRspCode = Xmlmap.get("TxnRspCode");// 退款状态
			stockCountDTO.setReturnCode(frontRspCode);
			stockCountDTO.setCardNo(cardNo);
			stockCountDTO.setMoney(txnAmt);
			stockCountDTO.setTxnReplyMsg(txnReplyMsg);
			stockCountDTO.setTxnRspCode(txnRspCode);
			logger.info("商城退货状态查询结束！");
			return stockCountDTO;
			
		}
	// public void delCard(Map<String, Object> map){
	// try {
	// String dataXml=HttpPostData.getRemoteInfo(map, "DelCard");
	// logger.info("解绑中石油返回："+dataXml);
	// } catch (BizServiceException e) {
	// //{"Errcode":701,"Errmsg":"该卡已解绑","Result":{"Fied1":"1111","Fied2":"1111"},"Method_Id":"003B"}
	// logger.info("中石油解绑失败！");
	// e.printStackTrace();
	//
	// }
	// }

	// 判断是否是小数
	public static boolean isBigDecimal(String value) {
		double n = Double.parseDouble(value);
		int temp;
		double i;
		temp = (int) n;
		i = n - temp;
		if (i == 0) {
			return false;
		} else
			return true;
	}

	public AccountQueryDTO getAccountQueryDTO() {
		return accountQueryDTO;
	}

	public void setAccountQueryDTO(AccountQueryDTO accountQueryDTO) {
		this.accountQueryDTO = accountQueryDTO;
	}

	public Java2ACCBusinessServiceImpl getJava2ACCBusinessService() {
		return java2ACCBusinessService;
	}

	public void setJava2ACCBusinessService(
			Java2ACCBusinessServiceImpl java2accBusinessService) {
		java2ACCBusinessService = java2accBusinessService;
	}

}
