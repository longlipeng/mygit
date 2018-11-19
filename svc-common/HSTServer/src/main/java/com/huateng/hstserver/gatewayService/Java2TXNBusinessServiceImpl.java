package com.huateng.hstserver.gatewayService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.biz.BizMessageObj;
import com.huateng.hstserver.communicate.mina.comm.server.client.ManagedAsyn2SynClient;
import com.huateng.hstserver.config.HSTProperties;
import com.huateng.hstserver.constants.HSTConstants;
import com.huateng.hstserver.exception.BizServiceException;
import com.huateng.hstserver.frameworkUtil.Amount;
import com.huateng.hstserver.frameworkUtil.Const;
import com.huateng.hstserver.model.TxnPackageDTO;


public class Java2TXNBusinessServiceImpl {
	private Logger logger = Logger.getLogger(Java2TXNBusinessServiceImpl.class);
	private CommMessage sendMsg = new CommMessage();
	private ManagedAsyn2SynClient managedAsyn2SynClient;
	private Java2CCommonServiceImpl java2CCommonService;

	/**
	 * 收单网关 消费登记
	 * 
	 * @param commonDTO
	 * @return
	 * @throws BizServiceException
	 */
	public TxnPackageDTO payGateWayConsumeRegister(TxnPackageDTO txnPackageDTO)
			throws BizServiceException {
		logger.info("############支 付 网 关 消 费 登 记############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType("G120");
			// 渠道号
			obj.setChannel(HSTProperties.getString("TXN_CHNL_ID"));
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			// 服务名
			obj.setServiceName(HSTConstants.VTXN);
			// 收单机构号
			obj.setDataHead(txnPackageDTO.getConsumerCode());
			// 商户号
			obj.setInnerMerchantNo(txnPackageDTO.getMerchantCode());
			// 门店号的值取决于商户是否传过来，如果没传过来，则内部传特殊的门店号：999999。
			if (StringUtils.isNotBlank(txnPackageDTO.getShopCode())) {
				obj.setInnerPosNo(txnPackageDTO.getShopCode());
			} else {
				obj.setInnerPosNo("999999");
			}
			// 订单号
			obj.setTrack2(txnPackageDTO.getOrderId());
			// 交易日期
			obj.setSwtTxnDate(txnPackageDTO.getTxnDate());
			// 交易时间
			obj.setSwtTxnTime(txnPackageDTO.getTxnTime());
			// 交易金额
			obj.setTxnAmount(txnPackageDTO.getAmount());
			// 交易币种
			obj.setSwtBatchNo(txnPackageDTO.getCurType());
			// 订货人姓名
			if (StringUtils.isNotBlank(txnPackageDTO.getCardHolder())) {
				obj.setFilePath(txnPackageDTO.getCardHolder());
			}
			// 商品信息
			if (StringUtils.isNotBlank(txnPackageDTO.getProductInfo())) {
				obj.setResv2(txnPackageDTO.getProductInfo());
			}
			// 附加信息
			if (StringUtils.isNotBlank(txnPackageDTO.getRemark())) {
				obj.setResv3(txnPackageDTO.getRemark());
			}
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			try {
				sendMsg = managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			obj = (BizMessageObj) sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);

			String allRecord = obj.getResv1();
			logger.info("allRecord:" + allRecord);
			// G120^700000000000001^999999^2011101017304101^20111010^173041^1002^156^^^^
			if (!"".equals(allRecord)) {
				// 解析所有的记录 ，以“|”截取
				String[] records = allRecord.split("\\|");
				logger.info("recordsLength: " + records.length + " records：  "
						+ records);
				if (records != null && records.length > 0) {
					String record = records[0];
					logger.info(record);
					String[] field = record.split("\\^", -1);
					txnPackageDTO.setRspCode(respCode);
					txnPackageDTO.setTxnType(field[0]);
					txnPackageDTO.setMerchantCode(field[1]);
					txnPackageDTO.setShopCode(field[2]);
					txnPackageDTO.setOrderId(field[3]);
					txnPackageDTO.setTxnDate(field[4]);
					txnPackageDTO.setTxnTime(field[5]);
					txnPackageDTO.setAmount(field[6]);
					txnPackageDTO.setCurType(field[7]);
					txnPackageDTO.setCardHolder(field[8]);
					txnPackageDTO.setProductInfo(field[9]);
					txnPackageDTO.setRemark(field[10]);
					/** 只有登记成功的才返回以下几个域 **/
					if (Const.RESPONSE_SUCCESS_CODE.equals(respCode)) {
						txnPackageDTO.setMerchantName(field[11]);
						txnPackageDTO.setConsumerCode(field[12]);
						txnPackageDTO.setMerchantURL(field[13]);
						txnPackageDTO.setSequenceNo(field[14]);
						txnPackageDTO.setSettleDate(field[15]);
					}
				}
			} else {
				txnPackageDTO.setRspCode(respCode);
			}
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return txnPackageDTO;
	}

	/**
	 * 支付网关 消费确认
	 * 
	 * @param commonDTO
	 * @return
	 * @throws BizServiceException
	 */
	public TxnPackageDTO payGateWayConsumeConfirm(TxnPackageDTO txnPackageDTO)
			throws BizServiceException {
		logger.info("############支 付 网 关 消 费 确 认############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType("G220");
			// 渠道号
			obj.setChannel(HSTProperties.getString("TXN_CHNL_ID"));
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			// 服务名
			obj.setServiceName(HSTConstants.VTXN);
			// 收单机构
			obj.setDataHead(txnPackageDTO.getConsumerCode());
			// 商户代码
			obj.setInnerMerchantNo(txnPackageDTO.getMerchantCode());
			// 门店号的值取决于商户是否传过来，如果没传过来，则内部传特殊的门店号：999999。
			if (StringUtils.isNotBlank(txnPackageDTO.getShopCode())) {
				obj.setInnerPosNo(txnPackageDTO.getShopCode());
			} else {
				obj.setInnerPosNo("999999");
			}
			// 订单号
			obj.setTrack2(txnPackageDTO.getOrderId());
			// 交易日期
			obj.setSwtTxnDate(txnPackageDTO.getTxnDate());
			// 交易时间
			obj.setSwtTxnTime(txnPackageDTO.getTxnTime());
			// 交易金额
			if (StringUtils.isNotBlank(txnPackageDTO.getAmount())) {
				obj.setTxnAmount(txnPackageDTO.getAmount());
			}
			// 交易币种
			if (StringUtils.isNotBlank(txnPackageDTO.getCurType())) {
				obj.setSwtBatchNo(txnPackageDTO.getCurType());
			} else {
				obj.setSwtBatchNo("156");
			}
			// 支付渠道
			if (StringUtils.isNotBlank(txnPackageDTO.getPayChannel())) {
				obj.setOriSwtbatchNo(txnPackageDTO.getPayChannel());
			}
			// 交易账户
			if (StringUtils.isNotBlank(txnPackageDTO.getAccount())) {
				obj.setAccType(txnPackageDTO.getAccount());
			}
			// 交易卡号
			if (StringUtils.isNotBlank(txnPackageDTO.getCardNO())) {
				obj.setCardNo(txnPackageDTO.getCardNO());
			}
			// 支付密码
			if (StringUtils.isNotBlank(txnPackageDTO.getAccount())
					&& StringUtils.isNotBlank(txnPackageDTO.getCardNO())
					&& StringUtils.isNotBlank(txnPackageDTO.getPin())) {
				String payPassword = java2CCommonService.encPwdJNKY(txnPackageDTO.getCardNO(), txnPackageDTO.getPin(), HSTConstants.SYS_TXN);
				try {
					obj.setPinTxn(payPassword);
				} catch (Exception e) {
					logger
							.debug("############entry paypassword error!################");
					logger.error(e.getMessage());
				}
			}
			// 交易状态
			if (StringUtils.isNotBlank(txnPackageDTO.getTxnState())) {
				obj.setExpDate(txnPackageDTO.getTxnState());
			}
			// 订货人姓名
			if (StringUtils.isNotBlank(txnPackageDTO.getCardHolder())) {
				obj.setFilePath(txnPackageDTO.getCardHolder());
			}
			// 商品信息
			if (StringUtils.isNotBlank(txnPackageDTO.getProductInfo())) {
				obj.setResv2(txnPackageDTO.getProductInfo());
			}
			// 附加信息
			if (StringUtils.isNotBlank(txnPackageDTO.getRemark())) {
				obj.setResv3(txnPackageDTO.getRemark());
			}
			// 商户名称
			if (StringUtils.isNotBlank(txnPackageDTO.getMerchantCode())) {
				obj.setResv4(txnPackageDTO.getMerchantCode());
			}
			// 收单机构
			if (StringUtils.isNotBlank(txnPackageDTO.getConsumerCode())) {
				obj.setDataHead(txnPackageDTO.getConsumerCode());
			}
			// 商户后台URL
			if (StringUtils.isNotBlank(txnPackageDTO.getMerchantURL())) {
				obj.setTrack3(txnPackageDTO.getMerchantURL());
			}
			// 中心流水号
			if (StringUtils.isNotBlank(txnPackageDTO.getSequenceNo())) {
				obj.setReferenceNo(txnPackageDTO.getSequenceNo());
			}
			// 清算日期
			if (StringUtils.isNotBlank(txnPackageDTO.getSettleDate())) {
				obj.setSwtSettleDate(txnPackageDTO.getSettleDate());
			}
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			try {
				sendMsg = managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			obj = (BizMessageObj) sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			String allRecord = obj.getResv1();
			logger.debug("allRecord:" + allRecord);

			if (!"".equals(allRecord)) {
				// 解析所有的记录 ，以“|”截取
				String[] records = allRecord.split("\\|");
				logger.debug("recordsLength: " + records.length + " records：  "
						+ records);
				if (records != null && records.length > 0) {
					String record = records[0];
					logger.info(record);
					String[] field = record.split("\\^", -1);
					txnPackageDTO.setRspCode(respCode);
					txnPackageDTO.setTxnType(field[0]);
					txnPackageDTO.setMerchantCode(field[1]);
					txnPackageDTO.setShopCode(field[2]);
					txnPackageDTO.setOrderId(field[3]);
					txnPackageDTO.setTxnDate(field[4]);
					txnPackageDTO.setTxnTime(field[5]);
					txnPackageDTO.setAmount(field[6]);
					txnPackageDTO.setCurType(field[7]);
					txnPackageDTO.setPayChannel(field[8]);
					txnPackageDTO.setAccount(field[9]);
					txnPackageDTO.setCardNO(field[10]);
					txnPackageDTO.setTxnState(field[11]);
					txnPackageDTO.setCardHolder(field[12]);
					txnPackageDTO.setProductInfo(field[13]);
					txnPackageDTO.setRemark(field[14]);
					txnPackageDTO.setMerchantName(field[15]);
					txnPackageDTO.setConsumerCode(field[16]);
					txnPackageDTO.setMerchantURL(field[17]);
					/** 只有登记成功的才返回以下几个域 **/
					if (Const.RESPONSE_SUCCESS_CODE.equals(respCode)) {
						txnPackageDTO.setSequenceNo(field[18]);
					}
					txnPackageDTO.setSettleDate(field[19]);
				}

			} else {
				txnPackageDTO.setRspCode(respCode);
			}
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return txnPackageDTO;
	}

	/**
	 * 支付网关 充值
	 * 
	 * @param commonDTO
	 * @return
	 * @throws BizServiceException
	 */
	public TxnPackageDTO payGateWayReCharge(TxnPackageDTO txnPackageDTO)
			throws BizServiceException {
		logger.info("############支 付 网 关 充 值############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType("G320");
			// 渠道号
			obj.setChannel(HSTProperties.getString("TXN_CHNL_ID"));
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			// 服务名
			obj.setServiceName(HSTConstants.VTXN);
			// 收单机构
			obj.setDataHead(txnPackageDTO.getConsumerCode());
			// 商户代码
			obj.setInnerMerchantNo(txnPackageDTO.getMerchantCode());
			// 门店号的值取决于商户是否传过来，如果没传过来，则内部传特殊的门店号：999999。
			if (StringUtils.isNotBlank(txnPackageDTO.getShopCode())) {
				obj.setInnerPosNo(txnPackageDTO.getShopCode());
			} else {
				obj.setInnerPosNo("999999");
			}
			// 订单号
			obj.setTrack2(txnPackageDTO.getOrderId());
			// 交易日期
			obj.setSwtTxnDate(txnPackageDTO.getTxnDate());
			// 交易时间
			obj.setSwtTxnTime(txnPackageDTO.getTxnTime());
			// 交易金额
			if (StringUtils.isNotBlank(txnPackageDTO.getAmount())) {
				obj.setTxnAmount(txnPackageDTO.getAmount());
			}
			// 交易币种
			if (StringUtils.isNotBlank(txnPackageDTO.getCurType())) {
				obj.setSwtBatchNo(txnPackageDTO.getCurType());
			} else {
				obj.setSwtBatchNo("156");
			}
			// 支付渠道
			if (StringUtils.isNotBlank(txnPackageDTO.getPayChannel())) {
				obj.setOriSwtbatchNo(txnPackageDTO.getPayChannel());
			}
			// 交易账户
			if (StringUtils.isNotBlank(txnPackageDTO.getAccount())) {
				obj.setAccType(txnPackageDTO.getAccount());
			}
			// 交易卡号
			if (StringUtils.isNotBlank(txnPackageDTO.getCardNO())) {
				obj.setCardNo(txnPackageDTO.getCardNO());
			}
			// 支付密码
			if (StringUtils.isNotBlank(txnPackageDTO.getAccount())
					&& StringUtils.isNotBlank(txnPackageDTO.getCardNO())
					&& StringUtils.isNotBlank(txnPackageDTO.getPin())) {
				String payPassword = java2CCommonService.encPwdJNKY(txnPackageDTO.getCardNO(), txnPackageDTO.getPin(), HSTConstants.SYS_TXN);
				try {
					obj.setPinTxn(payPassword);
				} catch (Exception e) {
					logger
							.debug("############entry paypassword error!################");
					logger.error(e.getMessage());
				}
			}
			// 交易状态
			if (StringUtils.isNotBlank(txnPackageDTO.getTxnState())) {
				obj.setExpDate(txnPackageDTO.getTxnState());
			}
			// 订货人姓名
			if (StringUtils.isNotBlank(txnPackageDTO.getCardHolder())) {
				obj.setFilePath(txnPackageDTO.getCardHolder());
			}
			// 商品信息
			if (StringUtils.isNotBlank(txnPackageDTO.getProductInfo())) {
				obj.setResv2(txnPackageDTO.getProductInfo());
			}
			// 附加信息
			if (StringUtils.isNotBlank(txnPackageDTO.getRemark())) {
				obj.setResv3(txnPackageDTO.getRemark());
			}
			// 商户名称
			if (StringUtils.isNotBlank(txnPackageDTO.getMerchantCode())) {
				obj.setResv4(txnPackageDTO.getMerchantCode());
			}
			// 收单机构
			if (StringUtils.isNotBlank(txnPackageDTO.getConsumerCode())) {
				obj.setDataHead(txnPackageDTO.getConsumerCode());
			}
			// 商户后台URL
			if (StringUtils.isNotBlank(txnPackageDTO.getMerchantURL())) {
				obj.setTrack3(txnPackageDTO.getMerchantURL());
			}
			// 中心流水号
			if (StringUtils.isNotBlank(txnPackageDTO.getSequenceNo())) {
				obj.setReferenceNo(txnPackageDTO.getSequenceNo());
			}
			// 清算日期
			if (StringUtils.isNotBlank(txnPackageDTO.getSettleDate())) {
				obj.setSwtSettleDate(txnPackageDTO.getSettleDate());
			}
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			try {
				sendMsg = managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			obj = (BizMessageObj) sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			logger.info("respCode：" + respCode);
			String allRecord = obj.getResv1();
			logger.debug("allRecord:" + allRecord);

			/** 反充值的时候填写 **/
			String orisequenceNo = obj.getOriSwtflowNo();
			String oriseltDate = obj.getSwtSettleDate();

			if (!"".equals(allRecord)) {
				// 解析所有的记录 ，以“|”截取
				String[] records = allRecord.split("\\|");
				logger.debug("recordsLength: " + records.length + " records：  "
						+ records);
				if (records != null && records.length > 0) {
					String record = records[0];
					logger.debug(record);
					String[] field = record.split("\\^", -1);
					txnPackageDTO.setRspCode(respCode);
					txnPackageDTO.setTxnType(field[0]);
					txnPackageDTO.setMerchantCode(field[1]);
					txnPackageDTO.setShopCode(field[2]);
					txnPackageDTO.setOrderId(field[3]);
					txnPackageDTO.setTxnDate(field[4]);
					txnPackageDTO.setTxnTime(field[5]);
					txnPackageDTO.setAmount(field[6]);
					txnPackageDTO.setCurType(field[7]);
					txnPackageDTO.setAccount(field[8]);
					txnPackageDTO.setCardNO(field[9]);
					txnPackageDTO.setCardHolder(field[10]);
					txnPackageDTO.setProductInfo(field[11]);
					txnPackageDTO.setRemark(field[12]);
					txnPackageDTO.setMerchantName(field[13]);
					txnPackageDTO.setConsumerCode(field[14]);
					txnPackageDTO.setMerchantURL(field[15]);
					/** 只有登记成功的才返回以下几个域 **/
					if (Const.RESPONSE_SUCCESS_CODE.equals(respCode)) {
						txnPackageDTO.setSequenceNo(field[16]);
						txnPackageDTO.setSettleDate(field[17]);
					} else if ("94".equals(respCode)) {
						/** 反充值的时候如果respCode=94,则中心流水号和清算日期传如下两域 **/
						txnPackageDTO.setSequenceNo(orisequenceNo);
						txnPackageDTO.setSettleDate(oriseltDate);
					}
				}
			} else {
				txnPackageDTO.setRspCode(respCode);
			}
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return txnPackageDTO;
	}

	/**
	 * 支付网关 交易查询
	 * @param txnPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public TxnPackageDTO payGateWayConsumeInquery(TxnPackageDTO txnPackageDTO)
			throws BizServiceException {
		logger.info("############支 付 网 关 交 易 查 询############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType("G100");
			//渠道号
			obj.setChannel(HSTProperties.getString("TXN_CHNL_ID"));
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			//服务名
			obj.setServiceName(HSTConstants.VTXN);
			//原交易类型
			obj.setCvv2(txnPackageDTO.getOldTxnType());
			//收单机构
			obj.setDataHead(txnPackageDTO.getConsumerCode());
			//商户代码
			obj.setInnerMerchantNo(txnPackageDTO.getMerchantCode());
			//门店号的值取决于商户是否传过来，如果没传过来，则内部传特殊的门店号：999999。
			if (StringUtils.isNotBlank(txnPackageDTO.getShopCode())) {
				obj.setInnerPosNo(txnPackageDTO.getShopCode());
			} else {
				obj.setInnerPosNo("999999");
			}
			//订单号
			obj.setTrack2(txnPackageDTO.getOrderId());
			//交易日期
			obj.setSwtTxnDate(txnPackageDTO.getTxnDate());
			//交易时间
			obj.setSwtTxnTime(txnPackageDTO.getTxnTime());
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			try {
				sendMsg = managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			obj = (BizMessageObj)sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			String allRecord = obj.getResv1();
			logger.debug("allRecord:" + allRecord);

			if (!"".equals(allRecord)) {
				// 解析所有的记录 ，以“|”截取
				String[] records = allRecord.split("\\|");
				logger.debug("recordsLength: " + records.length + " records：  "
						+ records);
				if (records != null && records.length > 0) {
					for (int i = 0; i < records.length; i++) {
						String record = records[i];
						logger.debug(record);
						String[] field = record.split("\\^", -1);
						txnPackageDTO.setRspCode(respCode);
						txnPackageDTO.setTxnType(field[0]);
						txnPackageDTO.setOldTxnType(field[1]);
						txnPackageDTO.setMerchantCode(field[2]);
						txnPackageDTO.setShopCode(field[3]);
						txnPackageDTO.setOrderId(field[4]);
						txnPackageDTO.setTxnDate(field[5]);
						txnPackageDTO.setTxnTime(field[6]);
						/** 只有登记成功的才返回以下几个域 **/
						if (Const.RESPONSE_SUCCESS_CODE.equals(respCode)) {
							txnPackageDTO.setAmount(field[7]);
							txnPackageDTO.setCurType(field[8]);
							txnPackageDTO.setCardNO(field[9]);
							txnPackageDTO.setCardHolder(field[10]);
							txnPackageDTO.setProductInfo(field[11]);
							txnPackageDTO.setRemark(field[12]);
							txnPackageDTO.setMerchantName(field[13]);
							txnPackageDTO.setConsumerCode(field[14]);
							txnPackageDTO.setMerchantURL(field[15]);
							txnPackageDTO.setSequenceNo(field[16]);
							txnPackageDTO.setSettleDate(field[17]);
						}
					}
				}
			} else {
				txnPackageDTO.setRspCode(respCode);
			}
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return txnPackageDTO;
	}

	
	/**
	 *  转出卡转账接口
	 * @param commonDTO
	 * @return
	 * @throws BizServiceException
	 */
	public TxnPackageDTO CardTransferOut(TxnPackageDTO txnPackageDTO)
			throws BizServiceException {
		logger.info("############转 出 卡 转 账############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(txnPackageDTO.getTxnType());
			obj.setChannel(HSTProperties.getString("TXN_CHNL_ID"));
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.VTXN);
			logger.info(obj.getTxnType() + "*"  + obj.getServiceName() + "*" + obj.getPackageNo() + "*" + obj.getChannel());
			//转出卡号
			obj.setCardNo(txnPackageDTO.getOutCard());
			//转出卡账户
			obj.setAccType(txnPackageDTO.getOutAccount());
			//商户号
			obj.setInnerMerchantNo(txnPackageDTO.getMerchantCode());
			//转账金额
			obj.setTxnAmount(txnPackageDTO.getAmount());
			obj.setCardHolderFee(txnPackageDTO.getServiceFee());
			//转出卡密码
			/*if (StringUtils.isNotBlank(txnPackageDTO.getOutCard())
					&& StringUtils.isNotBlank(txnPackageDTO
							.getPin())) {
				String password = java2CCommonService.encPwdJNKY(txnPackageDTO.getOutCard(), txnPackageDTO.getPin(), HSTConstants.SYS_TXN);
				obj.setPinTxn(password);
			}*/
			//暂用，正常下应该放开上面的注释
			obj.setPinTxn("99999999");
			//暂用 end
			sendMsg.setMessageObject(obj);
			logger.info("OBJ:--" + sendMsg.toString());
			// 发送报文 JAVA-->C
			try {
				logger.info("OBJ:--enter try :" + managedAsyn2SynClient.toString());
				sendMsg=managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error("OBJ:--enter try :xxxxxxxxxxxxxxxxxx" );
				logger.error(e.getMessage());
				e.printStackTrace();
				throw new BizServiceException("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			obj = (BizMessageObj)sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			String allRecord = obj.getSwtFlowNo();
			logger.debug("allRecord:" + allRecord);

			txnPackageDTO.setRspCode(respCode);
			if (!"".equals(allRecord)) {
				if (Const.RESPONSE_SUCCESS_CODE.equals(respCode)) {
					txnPackageDTO.setTxnNo(allRecord);
				}
			}
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return txnPackageDTO;
	}

	// 转入卡转账接口
	public TxnPackageDTO CardTransferIn(TxnPackageDTO txnPackageDTO)
			throws BizServiceException {
		logger.info("############转 入卡 转 账############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(txnPackageDTO.getTxnType());
			obj.setChannel(HSTProperties.getString("TXN_CHNL_ID"));
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.VTXN);
			//转出卡号
			obj.setTrack2(txnPackageDTO.getOutCard());
			//转出卡账户
			obj.setInnerPosNo(txnPackageDTO.getOutAccount());
			//转入卡号
			obj.setCardNo(txnPackageDTO.getIntoCard());
			//转入卡账户
			obj.setAccType(txnPackageDTO.getIntoAccount());
			//商户号
			obj.setInnerMerchantNo(txnPackageDTO.getMerchantCode());
			//转账金额
			obj.setTxnAmount(txnPackageDTO.getAmount());
			//原转出交易流水
			obj.setReferenceNo(txnPackageDTO.getTxnNo());
			obj.setCardHolderFee(txnPackageDTO.getServiceFee());
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			try {
				sendMsg=managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			obj = (BizMessageObj)sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			txnPackageDTO.setRspCode(respCode);
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return txnPackageDTO;
	}

	/**
	 *  单卡消费接口
	 * @param txnPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public TxnPackageDTO cardConsume(TxnPackageDTO txnPackageDTO)
			throws BizServiceException {
		logger.info("############单卡 消 费############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType("M220");
			obj.setChannel(HSTProperties.getString("TXN_CHNL_ID"));
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.VTXN);
			//交易流水号
			obj.setTrack2(txnPackageDTO.getSequenceNo());
			//卡号
			obj.setCardNo(txnPackageDTO.getCardNO());
			//消费金额
			obj.setTxnAmount(txnPackageDTO.getAmount());
			//商户号
			obj.setInnerMerchantNo(txnPackageDTO.getMerchantCode());
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			logger.info("begin java------->c");
			try {
				sendMsg=managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			logger.info("begin c------->java");
			obj=(BizMessageObj)sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			String allRecord = obj.getBalance();
			logger.debug("allRecord:" + allRecord);
			String accountCount = allRecord.substring(0, 2);
			if ("00".equals(respCode)) {
				for (int i = 0; i < accountCount.length(); i++) {
					txnPackageDTO.setAmount(allRecord.substring(10, 22));// 账户余额
				}
				logger.debug("单卡 消 费成 功！");
			}
			txnPackageDTO.setRspCode(respCode);

		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return txnPackageDTO;
	}

	/**
	 *  单卡消费冲正接口
	 * @param commonDTO
	 * @return
	 * @throws BizServiceException
	 */
	public TxnPackageDTO cardReverse(TxnPackageDTO txnPackageDTO)
			throws BizServiceException {
		logger.info("############消 费 冲 正############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType("M230");
			obj.setChannel(HSTProperties.getString("TXN_CHNL_ID"));
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.VTXN);
			//原交易流水号
			obj.setReferenceNo(txnPackageDTO.getOrigTxnNo());
			//交易流水号
			obj.setSwtFlowNo(txnPackageDTO.getTxnNo());
			//卡号
			obj.setCardNo(txnPackageDTO.getCardNO());
			//消费金额
			obj.setTxnAmount(txnPackageDTO.getAmount());
			//商户号
			obj.setInnerMerchantNo(txnPackageDTO.getMerchantCode());
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			logger.info("begin java------->c");
			try {
				sendMsg=managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			logger.info("begin c------->java");
			obj=(BizMessageObj)sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			String allRecord = obj.getBalance();
			logger.debug("allRecord:" + allRecord);
			if ("00".equals(respCode)) {
				logger.debug("消 费 冲 正 成 功！");
				txnPackageDTO.setRspCode(respCode);
			} else {
				txnPackageDTO.setRspCode(respCode);
			}
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return txnPackageDTO;
	}

	// 交易查询接口（针对单卡消费）
	public TxnPackageDTO queryTransation(TxnPackageDTO txnPackageDTO)
			throws BizServiceException {
		logger.info("############交 易 查 询############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType("M240");
			obj.setChannel(HSTProperties.getString("TXN_CHNL_ID"));
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.VTXN);
			//原交易流水号
			obj.setReferenceNo(txnPackageDTO.getOrigTxnNo());
			//商户号
			obj.setInnerMerchantNo(txnPackageDTO.getMerchantCode());
			//交易日期
			obj.setSwtTxnDate(txnPackageDTO.getTxnTime());
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			logger.info("begin java------->c");
			try {
				sendMsg = managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			logger.info("begin c------->java");
			obj = (BizMessageObj)sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			String allRecord = obj.getResv1();
			logger.debug("allRecord:" + allRecord);
			if ("00".equals(respCode)) {
				if (!"".equals(allRecord)) {
					// 解析所有的记录 ，以“|”截取
					String[] records = allRecord.split("\\|");
					logger.debug("recordsLength: " + records.length
							+ " records：  " + records);
					if (records != null && records.length > 0) {
						String record = records[0];
						logger.info(record);
						String[] field = record.split("\\^", -1);
						txnPackageDTO.setOldTxnType(field[0]);
						txnPackageDTO.setRemark(field[1]);
						txnPackageDTO.setAmount(field[2]);
						txnPackageDTO.setTxnNo(field[3]);
						txnPackageDTO.setTxnDate(field[4]);
					}
				}
				txnPackageDTO.setRspCode(respCode);
			} else {
				txnPackageDTO.setRspCode(respCode);
			}
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return txnPackageDTO;
	}
	
	/**
	 * 卡账务调整
	 * @param TxnPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public TxnPackageDTO adjustmentAccounts(TxnPackageDTO txnPackageDTO)
			throws BizServiceException {
		logger.info("############账务调整############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(txnPackageDTO.getTxnCode());
			//原交易流水号
			obj.setOriSwtflowNo(txnPackageDTO.getOrigTxnNo());
			//调账金额
			obj.setTxnAmount(txnPackageDTO.getAmount());
			//调账标志
			obj.setPinQuiry(txnPackageDTO.getFlag());
			//卡号
			obj.setCardNo(txnPackageDTO.getCardNO());
			//服务费
			obj.setCardHolderFee(txnPackageDTO.getSrvFee());
			//原交易类型
			obj.setAccType(txnPackageDTO.getOldTxnType());
			//调整类型
			obj.setPinQuiryNew(txnPackageDTO.getAdjustType());
			//渠道
			obj.setChannel(HSTProperties.getString("TXN_CHNL_ID"));
			//包号
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			//服务名
			obj.setServiceName(HSTConstants.VTXN);
			//网关渠道
			obj.setOriSwtbatchNo(txnPackageDTO.getPayChnl());
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			try {
				sendMsg=managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			obj = (BizMessageObj)sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			txnPackageDTO.setRspCode(respCode);
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return txnPackageDTO;
	}
	
	/**
	 * 卡片交易查询
	 * @param TxnPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public TxnPackageDTO cardTradeQuery(TxnPackageDTO txnPackageDTO)
			throws BizServiceException {
		logger.info("############卡片交易查询############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			String requestResv2 = "S010" + "|" + txnPackageDTO.getCardNO() + "|"
			+ txnPackageDTO.getFlag() + "|" + txnPackageDTO.getTxnType() + "|"
			+ txnPackageDTO.getStart_dt() + "|" + txnPackageDTO.getEnd_dt()+ "|"
			+ txnPackageDTO.getStart_row() + "|"
			+ txnPackageDTO.getEnd_row();
			//渠道
			obj.setChannel(HSTProperties.getString("TXN_CHNL_ID"));
			//包号
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			//服务名
			obj.setServiceName(HSTConstants.CARD_TXN_QUERY);
			
			obj.setResv2(requestResv2);
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			try {
				sendMsg=managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			obj = (BizMessageObj)sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			
			txnPackageDTO.setRspCode(respCode);
			if ("00".equals(respCode)) {
				List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
				String result = obj.getOtherdata();				
				if (result != null && !"".equals(result)) {
					list=resolve(result);
				}
				
				txnPackageDTO.setList(list);
				txnPackageDTO.setTotalRow(obj.getRecTxnDate());
				txnPackageDTO.setRealRow(obj.getRecTxnTime());
				logger.debug("查看卡交易成功");
			} else {
				throw new BizServiceException("查询交易失败,错误码:" + respCode);
			}
			logger.debug("respCode：" + respCode);
			txnPackageDTO.setRspCode(respCode);
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return txnPackageDTO;
	}

	private List<Map<?, ?>> resolve(String result){
		List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
		String[] records = result.split("\\|");
		if(null!=records && records.length>0){
			for (int i = 0; i < records.length; i++) {
				String[] fields = records[i].split("\\^");
				HashMap map2 = new HashMap();
				map2.put("txnId", fields[0]);
				map2.put("sysSeqNum", fields[1]);
				map2.put("cardNo", fields[2]);
				map2.put("merchantId", fields[3]);
				map2.put("merchantName", fields[4]);
				map2.put("shopId", fields[5]);
				map2.put("shopName", fields[6]);
				map2.put("txnType", fields[7]);
				//map2.put("txnTypeString", ReflectionUtil.getMap().get(fields[7]));
				//根据接口文档此处txnType返回值，默认写"卡交易查询"
				map2.put("txnTypeString", Const.getMap().get(fields[7]));
				map2.put("txnAmt", new BigDecimal(fields[8]).divide(new BigDecimal(100)).toString());
				map2.put("txnState", fields[9]);
				map2.put("txnDate", fields[10]);
				map2.put("txnFee", fields[11]);
				map2.put("cardAccpTermId", fields[12]);
				logger.debug("fields 13:"+fields[13]);
				logger.debug("fields 14:"+fields[14]);
				map2.put("totalBalance", Amount.getReallyAmount(fields[13]));
				map2.put("balance", Amount.getReallyAmount(fields[14]));
				list.add(map2);
			}
		}
		return list;

	}
	
	/**
	 * 商户交易查询
	 * @param TxnPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	@SuppressWarnings("unchecked")
	public TxnPackageDTO queryMerchantTxn(TxnPackageDTO txnPackageDTO)
			throws BizServiceException {
		logger.info("############商户交易查询############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			String selectCondition = txnPackageDTO.getTxnCode() + "|"
			+ txnPackageDTO.getFlag() + "|"
			+ txnPackageDTO.getConsumerCode() + "|"
			+ txnPackageDTO.getMerchantCode() + "|"
			+ txnPackageDTO.getShopCode() + "|"
			+ txnPackageDTO.getTermId() + "|"
			+ txnPackageDTO.getCardNO() + "|"
			+ txnPackageDTO.getTxnType() + "|"
			+ txnPackageDTO.getStart_dt() + "|"
			+ txnPackageDTO.getEnd_dt() + "|" + txnPackageDTO.getStart_row() + "|"
			+ txnPackageDTO.getEnd_row();
			
			//渠道
			obj.setChannel(HSTProperties.getString("TXN_CHNL_ID"));
			//包号
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			//服务名
			obj.setServiceName(HSTConstants.MCHNT_TXN_QUERY);
			
			obj.setResv2(selectCondition);
			
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			try {
				sendMsg=managedAsyn2SynClient.sendMessage(sendMsg);
				
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			obj = (BizMessageObj)sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			
			txnPackageDTO.setRspCode(respCode);
			if ("00".equals(respCode)) {
				List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
				String result = obj.getOtherdata();
				String[] records = result.split("\\|");
				if (result != null && !"".equals(result) 
						&& records != null && records.length > 0) {
					for (int i = 0; i < records.length; i++) {
						String record = records[i];
						String[] fields = record.split("\\^");
						Map map2 = new HashMap();
						map2.put("tranCode", fields[1]);
						map2.put("cardNo", fields[2].substring(2));
						map2.put("merchantName", fields[3]);
						map2.put("shopName", fields[4]);
						map2.put("postcode", fields[5]);
						//以前的代码根据TxnCode返回中文，得不到任何值，之间写“商户交易查询”
						//map2.put("tranType", ReflectionUtil.getMap().get(fields[6]));
						map2.put("tranType", Const.getMap().get(fields[6]));
						map2.put("tranFee", fields[7]);
						map2.put("respCode",fields[8].trim().equals("1") ? "成功" : fields[8].trim().equals("0") ? "失败" : fields[8]);
						map2.put("amtTranFee", fields[9]);
						map2.put("tranTime", fields[10]);
						list.add(map2);
					}

				}
				
				txnPackageDTO.setList(list);
				txnPackageDTO.setTotalRow(obj.getRecTxnDate());
				txnPackageDTO.setRealRow(obj.getRecTxnTime());
				logger.debug("查看卡交易成功");
			} 
			logger.debug("respCode：" + respCode);
			txnPackageDTO.setRspCode(respCode);
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return txnPackageDTO;
	}
	
	
	
	/**
	 *  重新加载卡路由信息
	 * @param TxnPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public TxnPackageDTO loadCardRoute(TxnPackageDTO txnPackageDTO)
			throws BizServiceException {
		logger.info("############重新加载卡路由信息############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(txnPackageDTO.getTxnCode());
			obj.setDataHead(txnPackageDTO.getConsumerCode());
			obj.setTrack2(txnPackageDTO.getBinValue());
			obj.setInnerPosNo(txnPackageDTO.getOperaterId());
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.RELOAD_SHM_PROC);
			
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			try {
				sendMsg=managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			obj = (BizMessageObj)sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			txnPackageDTO.setRspCode(respCode);			
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw new BizServiceException("加载卡路由信息失败");
		}
		return txnPackageDTO;
	}
	
	/**
	 * WEBSERVICE 方式退款
	 * @param TxnPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public TxnPackageDTO wsRefund(TxnPackageDTO txnPackageDTO)
			throws BizServiceException {
		logger.info("############WEBSERVICE 方式退款############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType("G300");
			//** 渠道号 **//
			obj.setChannel(HSTProperties.getString("TXN_CHNL_ID"));
			//** 收单机构 **//
			obj.setDataHead(txnPackageDTO.getConsumerCode());
			//** 商户代码 **//
			obj.setInnerMerchantNo(txnPackageDTO.getMerchantCode());
			//** 门店号的值取决于商户是否传过来，如果没传过来，则内部传特殊的门店号：999999。 **//
			//** 在这里并不传门店号 **//
			obj.setInnerPosNo(txnPackageDTO.getShopCode());
			//** 订单号 **//
			obj.setTrack2(txnPackageDTO.getOrderId());
			//** 交易日期 **//
			obj.setSwtTxnDate(txnPackageDTO.getTxnDate());
			//** 交易时间 **//
			obj.setSwtTxnTime(txnPackageDTO.getTxnTime());
			//** 交易金额 **//
			if (txnPackageDTO.getAmount() != null) {
				obj.setTxnAmount(txnPackageDTO.getAmount());
			}
			//** 交易币种 **//
			obj.setSwtBatchNo(txnPackageDTO.getCurType());
			//** 原交易金额 **//
			obj.setOriTxnAmount(txnPackageDTO.getOrigAmount());
			//** 中心流水号 **//
			obj.setReferenceNo(txnPackageDTO.getSequenceNo());			
			
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.VTXN);
			//退货渠道标志  0是网上退货申请  1是pos退货申请
			obj.setOriRecBatchNo(txnPackageDTO.getFlag());
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			try {
				sendMsg=managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			obj = (BizMessageObj)sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			txnPackageDTO.setRspCode(obj.getRespCode());
			
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw new BizServiceException("退款调用失败");
		}
		return txnPackageDTO;
	}
	
	/**
	 * WEBSERVICE 方式退款
	 * @param TxnPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public TxnPackageDTO wsQuery(TxnPackageDTO txnPackageDTO)
			throws BizServiceException {
		logger.info("############方式查询退货############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			
			// 组装报文
			obj.setTxnType("G100");
			//** 渠道号 **//
			obj.setChannel(HSTProperties.getString("TXN_CHNL_ID"));
			//** 原交易类型 **//
			obj.setCvv2(txnPackageDTO.getOldTxnType());
			//** 收单机构 **//
			obj.setDataHead(txnPackageDTO.getConsumerCode());
			//** 商户代码 **//
			obj.setInnerMerchantNo(txnPackageDTO.getMerchantCode());
			//** 门店号的值取决于商户是否传过来，如果没传过来，则内部传特殊的门店号：999999。 **//
			obj.setInnerPosNo(txnPackageDTO.getShopCode());
			//** 订单号 **//
			obj.setTrack2(txnPackageDTO.getOrderId());
			//** 交易日期 **//
			obj.setSwtTxnDate(txnPackageDTO.getTxnDate());
			//** 交易时间 **//
			obj.setSwtTxnTime(txnPackageDTO.getTxnTime());
			
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.VTXN);
			
			sendMsg.setMessageObject(obj);
			// 发送报文 JAVA-->C
			try {
				sendMsg=managedAsyn2SynClient.sendMessage(sendMsg);
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			// 解析返回值C-->JAVA
			obj = (BizMessageObj)sendMsg.getMessageObject();
			String respCode = obj.getRespCode();
			txnPackageDTO.setRspCode(obj.getRespCode());
			
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw new BizServiceException("退款查询失败");
		}
		return txnPackageDTO;
	}
	
	
	
	public CommMessage getSendMsg() {
		return sendMsg;
	}

	public void setSendMsg(CommMessage sendMsg) {
		this.sendMsg = sendMsg;
	}

	public ManagedAsyn2SynClient getManagedAsyn2SynClient() {
		return managedAsyn2SynClient;
	}

	public void setManagedAsyn2SynClient(
			ManagedAsyn2SynClient managedAsyn2SynClient) {
		this.managedAsyn2SynClient = managedAsyn2SynClient;
	}

	public Java2CCommonServiceImpl getJava2CCommonService() {
		return java2CCommonService;
	}

	public void setJava2CCommonService(Java2CCommonServiceImpl java2cCommonService) {
		java2CCommonService = java2cCommonService;
	}

	
}
