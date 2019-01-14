package com.huateng.framework.msg;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.ConfigSocket;

/**
 * <p>
 * Title: Accor
 * </p>
 * 
 * <p>
 * Description:Accor Project 1nd Edition
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author YY
 * @version 1.0
 */

public class MessageSender {
	static Logger logger = Logger.getLogger(MessageSender.class);

	/**
	 * socket发送请求并接收应答
	 * 
	 * @param txnCode
	 *            交易代码
	 * @param req
	 *            请求数据存放对象
	 * @return
	 */
	public static OperationResult send(String txnCode, Object req)
			throws BizServiceException {
		try {

			// 转换请求对象
			ArrayList arr = (ArrayList) MsgBuilder.build(txnCode, req);

			// 报文byte数组
			byte[] tmp = (byte[]) arr.get(0);

			// 发送报文最前端是否带有长度域
			String sendLenVisable = (String) arr.get(1);

			// 如果发送报文最前端带有长度域, sendLen表示长度域的长度;
			// 如果发送报文最前端不带长度域, sendLen表示数据域的总长度
			int sendLen = ((Integer) arr.get(2)).intValue();

			// 接收报文最前端是否带有长度域
			String recvLenVisable = (String) arr.get(3);

			// 如果接收报文最前端带有长度域, recvLen表示长度域的长度;
			// 如果接收报文最前端不带长度域, recvLen表示数据域的总长度
			int recvLen = ((Integer) arr.get(4)).intValue();
			logger.debug("ip:" + getIP(txnCode));
			logger.debug("port:" + getPort(txnCode));
			// 发送报文并获得应答
			byte[] rst = SendMessage.send(tmp, sendLenVisable, sendLen,
					recvLenVisable, recvLen, getIP(txnCode), getPort(txnCode));
			logger.debug("server end" + rst.length);
			/*
			 * for(int i = 0; i < rst.length; i++){
			 * logger.debug(Integer.toHexString(rst[i]) + " "); }
			 */

			// 把应答byte数组存储到java对象
			OperationResult oprRst = MsgBuilder.parse(txnCode, rst);
			return oprRst;

		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error(e.toString());
			throw new BizServiceException(e.toString());
		}
	}

	public static List multSend(List requestList) throws BizServiceException {
		String txnCodeTmp = "";
		// 准备一个返回结果列表
		List rspList = new ArrayList();

		// 首先判断批量传输的参数是否为空
		if (requestList == null || requestList.size() == 0) {
			String err = "multSend request is null";
			throw new BizServiceException(err);
		}

		List sendList = new ArrayList();
		List recvList = new ArrayList();

		// 把要发送的对象转换成byte数组
		for (int i = 0; i < requestList.size(); i++) {
			OperationRequest opr;
			try {
				// requestList中每一个元素都必须是OperationRequest对象
				try {
					opr = (OperationRequest) requestList.get(i);
				} catch (ClassCastException e) {
					String err = "multSend request must be list of OperationRequest.";
					logger.error(err);
					throw new BizServiceException(err + e.toString());
				}

				// 每一个OperationRequest对象中的txnCode和txnVO都不能为空
				String txnCode = opr.getTxnCode();
				txnCodeTmp = txnCode;
				Object obj = opr.getTxnvo();
				if (txnCode == null || txnCode.trim().length() == 0) {
					String err = "the [" + i + "]  request txnCode is empty.";
					logger.error(err);
					throw new BizServiceException(err);
				}
				if (txnCode == null || txnCode.trim().length() == 0) {
					String err = "the [" + i + "]  request txnCode is empty.";
					logger.error(err);
					throw new BizServiceException(err);
				}

				// 转换请求对象
				ArrayList arr = (ArrayList) MsgBuilder.build(txnCode, obj);

				// 添加到发送列表
				sendList.add(arr);
				OperationResult oprRst = new OperationResult();
				oprRst.setReserved(txnCode);
				recvList.add(oprRst);
			} catch (BizServiceException ex) {
				throw ex;
			} catch (Exception ex) {
				logger.error("unhandled exception." + ex.toString());
			}
		}

		// 调用批量发送接口

		// 发送当前请求
		ArrayList arrRst;
		logger.debug("ip:" + getIP(txnCodeTmp));
		logger.debug("port:" + getPort(txnCodeTmp));
		try {
			arrRst = (ArrayList) SendMessage.multSend(sendList,
					getIP(txnCodeTmp), getPort(txnCodeTmp));
		} catch (BizServiceException e) {
			logger.error("SendMessage.multSend Exception."
					+ e.getErrorMessage());
			throw e;
		}

		// 批量处理应答报文
		if (arrRst == null || arrRst.size() == 0) {
			String err = "SendMessage.multSend return empty";
			logger.error(err);
			throw new BizServiceException(err);
		}

		if (arrRst.size() != sendList.size()) {
			String err = "received numbers[" + arrRst.size()
					+ "] != send numbers[" + sendList.size() + "]";
			logger.error(err);
			throw new BizServiceException(err);
		}

		for (int i = 0; i < arrRst.size(); i++) {
			byte[] tmp = (byte[]) arrRst.get(i);
			OperationResult oprRst = (OperationResult) recvList.get(i);
			oprRst = MsgBuilder.parse(oprRst.getReserved(), tmp);

			rspList.add(oprRst);

		}

		return rspList;
	}

	private static String getIP(String txnCode) {
		String ip = "";
		if (txnCode.equals(ConstCode.TXNCODE_ACNTMNG)
				|| txnCode.equals(ConstCode.TXNCODE_MAKE_MAG_CARD)
				|| txnCode.equals(ConstCode.TXNCODE_MAKE_IC_CARD)
				|| txnCode.equals(ConstCode.TXNCODE_MAKE_IC_CARD_PIN)
				|| txnCode.equals(ConstCode.TXNCODE_MAKE_END)) {
			return ConfigSocket.getSocketIPPOSP();
		} else if (txnCode.equals(ConstCode.TXNCODE_RECHARGE)
				|| txnCode.equals(ConstCode.TXNCODE_RECHARGE_DISPOSABLE)
				|| txnCode.equals(ConstCode.TXNCODE_RECHARGE_DISPOSABLE_CANCEL)
				|| txnCode.equals(ConstCode.TXNCODE_DECRYPT_RESPONSE_FILE)
				|| txnCode.equals(ConstCode.TXNCODE_ACCOUNTADJUST)
				|| txnCode.equals(ConstCode.TXNCODE_ACCOUNTADJUST1)
				|| txnCode.equals(ConstCode.TXNCODE_CVV)
				|| txnCode.equals(ConstCode.TXNCODE_ACTIVE)
				|| txnCode.equals(ConstCode.FRTCODE_TRANSFER)
				|| txnCode.equals(ConstCode.INTEGER_EXCHANGE)) {
			return ConfigSocket.getSocketIPRecharge();
		} else if (txnCode.equals(ConstCode.PUBLIC_SERVICE)) {
			return ConfigSocket.getSocketIPPublicService();
		} else {
			return ConfigSocket.getSocketIPACCT();
		}
	}

	private static String getPort(String txnCode) {
		String port = "";
		if (txnCode.equals(ConstCode.TXNCODE_ACNTMNG)
				|| txnCode.equals(ConstCode.TXNCODE_MAKE_MAG_CARD)
				|| txnCode.equals(ConstCode.TXNCODE_MAKE_IC_CARD)
				|| txnCode.equals(ConstCode.TXNCODE_MAKE_IC_CARD_PIN)
				|| txnCode.equals(ConstCode.TXNCODE_MAKE_END)
				|| txnCode.equals(ConstCode.TXNCODE_DECRYPT_RESPONSE_FILE)
				|| txnCode.equals(ConstCode.TXNCODE_CVV)) {
			return ConfigSocket.getSocketPortPOSP();
		} else if (txnCode.equals(ConstCode.TXNCODE_RECHARGE)
				|| txnCode.equals(ConstCode.TXNCODE_RECHARGE_DISPOSABLE)
				|| txnCode.equals(ConstCode.TXNCODE_RECHARGE_DISPOSABLE_CANCEL)

				|| txnCode.equals(ConstCode.TXNCODE_ACCOUNTADJUST)
				|| txnCode.equals(ConstCode.TXNCODE_ACCOUNTADJUST1)

				|| txnCode.equals(ConstCode.TXNCODE_ACTIVE)
				|| txnCode.equals(ConstCode.FRTCODE_TRANSFER)
				|| txnCode.equals(ConstCode.INTEGER_EXCHANGE)) {
			return ConfigSocket.getSocketPortRecharge();
		} else if (txnCode.equals(ConstCode.PUBLIC_SERVICE)) {
			return ConfigSocket.getSocketPortPublicService();
		} else {
			return ConfigSocket.getSocketPortACCT();
		}
	}
}
