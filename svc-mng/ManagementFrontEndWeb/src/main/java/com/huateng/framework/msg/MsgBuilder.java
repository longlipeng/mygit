package com.huateng.framework.msg;

import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.OperationResult;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.constant.Const;

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

public class MsgBuilder {
	static Logger logger = Logger.getLogger(MsgBuilder.class);

	// 这个标识用来区分不同格式的报文转换, 为将来扩展做准备, 将来可根据需要设置这个标志位
	// 用法, 如果xmlFileIndex=1, 当前请求是制卡文件生成, 那么系统将读取MakeCard_[index].xml这个文件
	static int xmlFileIndex = 1;

	/**
	 * 获取报文转换xml文件, 调用报文转换方法
	 * 
	 * @param txnCode
	 *            交易代码
	 * @param req
	 *            java对象
	 * @return
	 * @throws BizServiceException
	 */
	public static List build(String txnCode, Object req)
			throws BizServiceException {

		String xmlName = getXmlName(txnCode) + "_" + xmlFileIndex + ".xml";
		String packetName = getPacketName(txnCode);
		return PacketBuilder.build(req, xmlName, packetName);
	}

	/**
	 * 获取报文转换xml文件, 调用报文转换方法
	 * 
	 * @param txnCode
	 *            交易代码
	 * @param pack
	 *            byte数组
	 * @return
	 * @throws BizServiceException
	 */
	public static OperationResult parse(String txnCode, byte[] pack)
			throws BizServiceException {
		OperationResult oprRst = new OperationResult();
		String xmlName = getXmlName(txnCode) + "_" + xmlFileIndex + ".xml";
		String packetName = getPacketName(txnCode);
		oprRst.setDetailvo(getRespVO(txnCode));
		return PacketBuilder.parse(pack, xmlName, packetName, oprRst);
	}

	/**
	 * 根据交易代码查找报文转换xml文件名
	 * 
	 * @param txnCode
	 *            交易代码
	 * @return 用于报文转换的xml文件名
	 */
	public static String getXmlName(String txnCode) {
		String xmlName = "";
		if (txnCode.equals(Const.TXNCODE_ACNTMNG)
				|| txnCode.equals(Const.TXNCODE_MAKE_END)
				|| txnCode.equals(Const.TXNCODE_MAKE_MAG_CARD)
				|| txnCode.equals(Const.TXNCODE_MAKE_IC_CARD)
				|| txnCode.equals(ConstCode.TXNCODE_DECRYPT_RESPONSE_FILE)
				|| txnCode.equals(Const.TXNCODE_MAKE_IC_CARD_PIN)) {

			xmlName = Const.FILE_NAME_MAKECARD;
		} else if (txnCode.equals(ConstCode.TXNCODE_ENQUIRY_BALANCE)
				|| txnCode.equals(ConstCode.TXNCODE_CONSUME)
				|| txnCode.equals(ConstCode.TXNCODE_AUTHORIZATION)
				|| txnCode.equals(ConstCode.TXNCODE_AUTHORIZATION1)
				|| txnCode.equals(ConstCode.TXNCODE_RESETPASSWORD)
				|| txnCode.equals(ConstCode.TXNCODE_DEFRAY)
				|| txnCode.equals(ConstCode.TXNCODE_RETURNPURCHASE)) {
			xmlName = Const.FILE_NAME_ISO8583;
		} else if (txnCode.equals(ConstCode.TXNCODE_ACTIVE)
				|| txnCode.equals(ConstCode.TXNCODE_RECHARGE)
				|| txnCode.equals(ConstCode.TXNCODE_RECHARGE_DISPOSABLE)
				|| txnCode.equals(ConstCode.TXNCODE_RECHARGE_DISPOSABLE_CANCEL)
				|| txnCode.equals(ConstCode.TXNCODE_ACCOUNTADJUST)
				|| txnCode.equals(ConstCode.TXNCODE_ACCOUNTADJUST1)
				|| txnCode.equals(ConstCode.TXNCODE_CVV)
				|| txnCode.equals(ConstCode.FRTCODE_TRANSFER)
				|| txnCode.equals(ConstCode.INTEGER_EXCHANGE)) {
			xmlName = Const.FILE_NAME_ACTIVE;
		} else if (txnCode.equals(ConstCode.PUBLIC_SERVICE)) {
			xmlName = Const.FILE_NAME_PUBLICSERVICE;
		}
		return xmlName;
	}

	/**
	 * 根据交易代码查找报文转换的xml文件中的<packet名称
	 * 
	 * @param txnCode
	 *            交易代码
	 * @return 用于报文转换的xml文件中的<packet名称
	 */
	public static String getPacketName(String txnCode) {
		String packetName = "";
		if (txnCode.equals(Const.TXNCODE_ACNTMNG)) {
			packetName = Const.PACKET_NAME_MAKEKEY;
		} else if (txnCode.equals(Const.TXNCODE_MAKE_MAG_CARD)) {
			packetName = Const.PACKET_NAME_MAKE_MG_CARD;
		} else if (txnCode.equals(Const.TXNCODE_MAKE_IC_CARD)) {
			packetName = Const.PACKET_NAME_MAKE_IC_CARD;
		} else if (txnCode.equals(Const.TXNCODE_MAKE_IC_CARD_PIN)) {
			packetName = Const.PACKET_NAME_MAKE_IC_CARD_PIN;
		} else if (txnCode.equals(Const.TXNCODE_MAKE_END)) {
			packetName = Const.PACKET_NAME_MAKE_END;
		} else if (txnCode.equals(ConstCode.TXNCODE_ENQUIRY_BALANCE)) {
			packetName = Const.PACKET_NAME_ENQUIRY_BALANCE;
		} else if (txnCode.equals(ConstCode.TXNCODE_CONSUME)) {
			packetName = Const.PACKET_NAME_CONSUME;
		} else if (txnCode.equals(ConstCode.TXNCODE_ACTIVE)) {
			packetName = Const.PACKET_NAME_ACTIVE;
		} else if (txnCode.equals(ConstCode.TXNCODE_RECHARGE)) {
			packetName = Const.PACKET_NAME_RECHARGE;
		} else if (txnCode.equals(ConstCode.TXNCODE_ACCOUNTADJUST)) {
			packetName = Const.PACKET_NAME_ACCOUNTADJUST;
		} else if (txnCode.equals(ConstCode.TXNCODE_RESETPASSWORD)) {
			packetName = Const.PACKET_NAME_RESETPASSWORD;
		} else if (txnCode.equals(ConstCode.TXNCODE_AUTHORIZATION)) {
			packetName = Const.PACKET_NAME_AUTHORIZATION;
		} else if (txnCode.equals(ConstCode.TXNCODE_AUTHORIZATION1)) {
			packetName = Const.PACKET_NAME_AUTHORIZATION1;
		} else if (txnCode.equals(ConstCode.TXNCODE_RECHARGE_DISPOSABLE)) {
			packetName = Const.PACKET_NAME_RECHARGE_DIPOSABLE;
		} else if (txnCode.equals(ConstCode.TXNCODE_RECHARGE_DISPOSABLE_CANCEL)) {
			packetName = Const.PACKET_NAME_RECHARGE_DIPOSABLE_CANCEL;
		} else if (txnCode.equals(ConstCode.TXNCODE_DECRYPT_RESPONSE_FILE)) {
			packetName = Const.PACKET_NAME_DECRYPT;
		} else if (txnCode.equals(ConstCode.TXNCODE_ACCOUNTADJUST1)) {
			packetName = Const.PACKET_NAME_ACCOUNTADJUST1;
		} else if (txnCode.equals(ConstCode.TXNCODE_CVV)) {
			packetName = Const.PACKET_NAME_CVV;
		} else if (txnCode.equals(ConstCode.TXNCODE_DEFRAY)) {
			packetName = Const.PACKET_NAME_DEFRAY;
		} else if (txnCode.equals(ConstCode.TXNCODE_RETURNPURCHASE)) {
			packetName = Const.PACKET_NAME_RETURNPURCHASE;
		} else if (txnCode.equals(ConstCode.FRTCODE_TRANSFER)) {
			packetName = Const.PACKET_NAME_TRANSFER;
		} else if (txnCode.equals(ConstCode.INTEGER_EXCHANGE)) {
			packetName = Const.PACKET_NAME_EXCHANGE;
		} else if (txnCode.equals(ConstCode.PUBLIC_SERVICE)) {
			packetName = Const.PACKET_NAME_PUBLICSERVICE;
		}
		return packetName;
	}

	/**
	 * 根据交易代码获取一个java对象
	 * 
	 * @param txnCode
	 * @return
	 */
	public static Object getRespVO(String txnCode) {

		if (txnCode.equals(Const.TXNCODE_ACNTMNG)
				|| txnCode.equals(Const.TXNCODE_MAKE_END)
				|| txnCode.equals(Const.TXNCODE_MAKE_MAG_CARD)
				|| txnCode.equals(Const.TXNCODE_MAKE_IC_CARD)
				|| txnCode.equals(ConstCode.TXNCODE_DECRYPT_RESPONSE_FILE)
				|| txnCode.equals(Const.TXNCODE_MAKE_IC_CARD_PIN)) {

			return new CardMakeBean();
		} else if (txnCode.equals(ConstCode.TXNCODE_ENQUIRY_BALANCE)
				|| txnCode.equals(ConstCode.TXNCODE_CONSUME)
				|| txnCode.equals(ConstCode.TXNCODE_AUTHORIZATION)
				|| txnCode.equals(ConstCode.TXNCODE_AUTHORIZATION1)
				|| txnCode.equals(ConstCode.TXNCODE_RESETPASSWORD)
				|| txnCode.equals(ConstCode.TXNCODE_DEFRAY)
				|| txnCode.equals(ConstCode.TXNCODE_RETURNPURCHASE)) {

			return new ISO8583Bean();
		} else if (txnCode.equals(ConstCode.TXNCODE_ACTIVE)
				|| txnCode.equals(ConstCode.TXNCODE_RECHARGE)
				|| txnCode.equals(ConstCode.TXNCODE_RECHARGE_DISPOSABLE)
				|| txnCode.equals(ConstCode.TXNCODE_RECHARGE_DISPOSABLE_CANCEL)
				|| txnCode.equals(ConstCode.TXNCODE_ACCOUNTADJUST)
				|| txnCode.equals(ConstCode.TXNCODE_ACCOUNTADJUST1)
				|| txnCode.equals(ConstCode.TXNCODE_CVV)) {

			return new RechargeBean();
		} else if (txnCode.equals(ConstCode.FRTCODE_TRANSFER)) {
			return new TransferBean();
		} else if (txnCode.equals(ConstCode.INTEGER_EXCHANGE)) {
			return new ExchangeBean();
		} else if (txnCode.equals(ConstCode.PUBLIC_SERVICE)) {
			return new PublicServiceBean();
		}

		return null;
	}

}
