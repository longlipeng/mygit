package com.huateng.hstserver.gatewayService;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.biz.BizMessageObj;
import com.huateng.hstserver.communicate.mina.comm.server.client.ManagedAsyn2SynClient;
import com.huateng.hstserver.constants.HSTConstants;
import com.huateng.hstserver.exception.BizServiceException;
import com.huateng.hstserver.model.StlPackageDTO;

public class Java2STLBusinessServiceImpl {
	private Logger logger = Logger.getLogger(Java2TXNBusinessServiceImpl.class);
	private CommMessage sendMsg = new CommMessage();
	private ManagedAsyn2SynClient managedAsyn2SynClient;

	/**
	 * 结算单预览
	 * 
	 * @param settleQueryDTO
	 * @return
	 * @throws BizServiceException
	 */
	// public StlPackageDTO previewSettle(StlPackageDTO stlPackageDTO) throws
	// BizServiceException{
	// try {
	//			
	// Map map = new HashMap();
	// map.put(CFunctionConstant.TXN_TYPE, "9001");
	// map.put(CFunctionConstant.DATA_HEAD, settleQueryDTO
	// .getSettleObject1());
	// logger.debug(map.get(CFunctionConstant.DATA_HEAD));
	// map.put(CFunctionConstant.INNER_MERCHANT_NO, settleQueryDTO
	// .getSettleObject2());
	// map.put(CFunctionConstant.FILE_PATH, settleQueryDTO
	// .getSettleObject2Name());
	// map.put(CFunctionConstant.CHANNEL, settleQueryDTO.getSettleState());
	// map.put(CFunctionConstant.SWT_TXN_DATE, settleQueryDTO
	// .getSettleStartDate());
	// map.put(CFunctionConstant.SWT_SETTLE_DATE, settleQueryDTO
	// .getSettleEndDate());
	// map.put(CFunctionConstant.TXN_AMOUNT, settleQueryDTO.getMinAmt());
	// map.put(CFunctionConstant.BALANCE, settleQueryDTO.getMaxAmt());
	// map.put(CFunctionConstant.REC_TXN_DATE, String
	// .valueOf(settleQueryDTO.getFirstCursorPosition()));
	// map.put(CFunctionConstant.REC_TXN_TIME, String
	// .valueOf(settleQueryDTO.getLastCursorPosition()));
	// OperationResult or;
	// try {
	// or = java2C.sendTpService("vBillProc", map,
	// Const.JAVA2C_BIG_AMT, false);
	// } catch (Exception e) {
	// 
	// logger.error(e.getMessage());
	// throw new BizServiceException("后台未启动！！");
	// }
	// HashMap map1 = (HashMap) or.getDetailvo();
	// String rspCode = (String) map1.get(CFunctionConstant.RESP_CODE);
	// logger.debug("rspCode:" + rspCode);
	// if ("00".equals(rspCode)) {
	// List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
	// String result = (String) map1.get(CFunctionConstant.OTHERDATA);
	// String[] records = result.split("\\|");
	// if (result != "" && records != null && records.length > 0) {
	// for (int i = 0; i < records.length; i++) {
	// String record = records[i];
	// String[] fields = record.split("\\^");
	// Map map2 = new HashMap();
	// map2.put("settleObject1", fields[0]);
	//
	// map2.put("merchantId", fields[1]);
	// /*
	// * MerchantExample example = new MerchantExample();
	// * example
	// * .createCriteria().andEntityIdEqualTo(fields[1]);
	// * List<Merchant> merchantList = merchantServiceDAO
	// * .selectByExample(example); map2.put("merchantId",
	// * merchantList.get(0).getMerchantCode());
	// */
	// MerchantExample example = new MerchantExample();
	// example.createCriteria().andEntityIdEqualTo(fields[1]);
	// List<Merchant> merchantList = merchantServiceDAO
	// .selectByExample(example);
	// map2.put("merchantCode", merchantList.get(0)
	// .getMerchantCode());
	// map2.put("merchantName", fields[2]);
	// map2.put("settStartDate", fields[3]);
	// map2.put("settleEndDate", fields[4]);
	// map2.put("amtDesc", Amount.getReallyAmount(fields[5]));
	// map2.put("contractId", fields[6]);
	//
	// logger.debug(map2.get("settStartDate"));
	// list.add(map2);
	// }
	// }
	// if ((String) map1.get(CFunctionConstant.REC_TXN_TIME) != "") {
	// pageDataDTO.setTotalRecord(Integer.parseInt(map1.get(
	// CFunctionConstant.REC_TXN_TIME).toString()));
	// }
	// pageDataDTO.setData(list);
	// } else if ("03".equals(rspCode)) {
	// throw new BizServiceException("无效商户！！");
	// } else if ("96".equals(rspCode)) {
	// throw new BizServiceException("未找到消费合同");
	// } else if ("07".equals(rspCode)) {
	// throw new BizServiceException("交易代码有误");
	// } else {
	// throw new BizServiceException("系统错误!错误码:" + rspCode);
	// }
	//
	// } catch (BizServiceException b) {
	// throw b;
	// } catch (Exception e) {
	// logger.error(e.getMessage());
	// logger.error(e);
	// }
	// return pageDataDTO;
	// }

	/**
	 * 结算单预览
	 * 
	 * @param stlPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public StlPackageDTO previewSettle(StlPackageDTO stlPackageDTO)
			throws BizServiceException {
		try {
			logger.info("############结算单预览############");

			// 组装报文

			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(stlPackageDTO.getTxnCode());
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			// 服务名
			obj.setServiceName(HSTConstants.BILL_PROC);
			// 实体ID
			obj.setDataHead(stlPackageDTO.getSettleObject1());
			// 商户编号
			obj.setInnerMerchantNo(stlPackageDTO.getMerchantCode());
			// 商户名称
			obj.setFilePath(stlPackageDTO.getMerchantName());
			// 渠道
			obj.setChannel(stlPackageDTO.getChannel());
			// 结算单开始日期
			obj.setSwtTxnDate(stlPackageDTO.getStartDate());
			// 结算单结束日期
			obj.setSwtSettleDate(stlPackageDTO.getEndDate());
			// 最小结算金额
			obj.setTxnAmount(stlPackageDTO.getMinAmt());
			// 最大结算金额
			obj.setBalance(stlPackageDTO.getMaxAmt());
			// 结算开始日期
			obj.setRecTxnDate(stlPackageDTO.getStartDate());
			// 结算结束日期
			obj.setRecTxnTime(stlPackageDTO.getEndDate());
			// 开始行
			obj.setRecTxnDate(stlPackageDTO.getStartRow());
			// 结束行
			obj.setRecTxnTime(stlPackageDTO.getEndRow());

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
			stlPackageDTO.setOtherData(obj.getOtherdata());
			stlPackageDTO.setRspCode(respCode);

			stlPackageDTO.setRow_num(obj.getRecTxnDate());
			stlPackageDTO.setRow_tot(obj.getRecTxnTime());
		} catch (BizServiceException e) {
			
			logger.error(e.getMessage());
			throw e;
		}
		return stlPackageDTO;
	}

	/**
	 * 结算单查询
	 * 
	 * @param stlPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public StlPackageDTO querySettle(StlPackageDTO stlPackageDTO)
			throws BizServiceException {
		try {
			logger.info("############结算单查询############");

			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(stlPackageDTO.getTxnCode());
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			// 服务名
			obj.setServiceName(HSTConstants.BILL_PROC);
			// 结算单号
			obj.setPinTxn(stlPackageDTO.getSettleId());
			// 结算单状态
			obj.setPinTxnNew(stlPackageDTO.getSettleState());
			// 上级机构
			obj.setDataHead(stlPackageDTO.getSettleObject1());
			// 商户编号
			obj.setInnerMerchantNo(stlPackageDTO.getMerchantCode());
			// 商户名称
			obj.setFilePath(stlPackageDTO.getMerchantName());
			// 结算单开始日期
			obj.setSwtTxnDate(stlPackageDTO.getStartDate());
			// 结算单结束日期
			obj.setSwtSettleDate(stlPackageDTO.getEndDate());
			// 最小结算金额
			obj.setTxnAmount(stlPackageDTO.getMinAmt());
			// 最大结算金额
			obj.setBalance(stlPackageDTO.getMaxAmt());
			// 结算开始日期
			obj.setRecTxnDate(stlPackageDTO.getStartDate());
			// 结算结束日期
			obj.setRecTxnTime(stlPackageDTO.getEndDate());
			// 开始行
			obj.setRecTxnDate(stlPackageDTO.getStartRow());
			// 结束行
			obj.setRecTxnTime(stlPackageDTO.getEndRow());

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
			stlPackageDTO.setOtherData(obj.getOtherdata());
			stlPackageDTO.setRspCode(respCode);

			stlPackageDTO.setRow_num(obj.getRecTxnDate());
			stlPackageDTO.setRow_tot(obj.getRecTxnTime());
		} catch (BizServiceException e) {
			
			logger.error(e.getMessage());
			throw e;
		}
		return stlPackageDTO;
	}

	/**
	 * 生成结算单
	 * 
	 * @param stlPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public StlPackageDTO generateSettle(StlPackageDTO stlPackageDTO)
			throws BizServiceException {
		try {
			logger.info("############生成结算单############");

			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(stlPackageDTO.getTxnCode());
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			// 服务名
			obj.setServiceName(HSTConstants.BILL_PROC);
			// 实体ID
			obj.setDataHead(stlPackageDTO.getSettleId());
			// 商户编号
			obj.setInnerMerchantNo(stlPackageDTO.getMerchantCode());
			obj.setSwtTxnDate(stlPackageDTO.getSettle_dt_pre());
			obj.setSwtSettleDate(stlPackageDTO.getSettle_dt_next());
			obj.setInnerPosNo(stlPackageDTO.getUserid());
			//商户合同号
			obj.setPinTxn(stlPackageDTO.getContractId());

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
			stlPackageDTO.setRspCode(respCode);
		} catch (BizServiceException e) {
			
			logger.error(e.getMessage());
			throw e;
		}

		return stlPackageDTO;
	}

	/**
	 * 生成结算单明细
	 * 
	 * @param stlPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public StlPackageDTO settleDetail(StlPackageDTO stlPackageDTO)
			throws BizServiceException {
		try {
			logger.info("############生成结算单明细############");

			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(stlPackageDTO.getTxnCode());
			obj.setServiceName(HSTConstants.BILL_PROC);
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			// 结算单ID
			obj.setPinTxn(stlPackageDTO.getSettleId());

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
			stlPackageDTO.setRspCode(respCode);
			stlPackageDTO.setOtherData(obj.getOtherdata());
		} catch (BizServiceException e) {
			
			logger.error(e.getMessage());
			throw e;
		}
		return stlPackageDTO;
	}

	/**
	 * 结算单确认
	 * 
	 * @param stlPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public StlPackageDTO confirmSettle(StlPackageDTO stlPackageDTO)
			throws BizServiceException {
		try {
			logger.info("############结算单确认############");

			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(stlPackageDTO.getTxnCode());
			obj.setServiceName(HSTConstants.BILL_PROC);
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setPinTxn(stlPackageDTO.getSettleId());
			obj.setPinTxnNew(stlPackageDTO.getSettleState());
			obj.setInnerPosNo(stlPackageDTO.getUserid());

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
			stlPackageDTO.setRspCode(respCode);
		} catch (BizServiceException e) {
			
			logger.error(e.getMessage());
			throw e;
		}
		return stlPackageDTO;
	}

	/**
	 * 结算单付款确认
	 * 
	 * @param stlPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public StlPackageDTO settlePaymentConfirm(StlPackageDTO stlPackageDTO)
			throws BizServiceException {
		try {
			logger.info("############结算单付款确认############");

			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(stlPackageDTO.getTxnCode());
			obj.setServiceName(HSTConstants.BILL_PROC);
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setPinTxn(stlPackageDTO.getSettleId());
			obj.setInnerPosNo(stlPackageDTO.getUserid());

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
			stlPackageDTO.setRspCode(respCode);
		} catch (BizServiceException e) {
			
			logger.error(e.getMessage());
			throw e;
		}
		return stlPackageDTO;
	}

	/**
	 * 修改结算日期
	 * 
	 * @param stlPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public StlPackageDTO changeSettleDate(StlPackageDTO stlPackageDTO)
			throws BizServiceException {
		try {
			logger.info("############修改结算日期############");

			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(stlPackageDTO.getTxnCode());
			obj.setServiceName(HSTConstants.BILL_PROC);
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setDataHead(stlPackageDTO.getSettleId());
			obj.setInnerMerchantNo(stlPackageDTO.getMerchantCode());
			obj.setSwtSettleDate(stlPackageDTO.getSettle_dt_next());
			obj.setInnerPosNo(stlPackageDTO.getUserid());
			//商户合同号
			obj.setPinTxn(stlPackageDTO.getContractId());

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
			stlPackageDTO.setRspCode(respCode);
			stlPackageDTO.setStartDate(obj.getSwtTxnDate());
		} catch (BizServiceException e) {
			
			logger.error(e.getMessage());
			throw e;
		}
		return stlPackageDTO;
	}

	/**
	 * 取消结算单
	 * 
	 * @param stlPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public StlPackageDTO cancelSettle(StlPackageDTO stlPackageDTO)
			throws BizServiceException {
		try {
			logger.info("############取消结算单############");

			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(stlPackageDTO.getTxnCode());
			obj.setServiceName(HSTConstants.BILL_PROC);
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));

			obj.setPinTxn(stlPackageDTO.getSettleId());
			obj.setInnerPosNo(stlPackageDTO.getUserid());

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
			stlPackageDTO.setRspCode(respCode);
		} catch (BizServiceException e) {
			
			logger.error(e.getMessage());
			throw e;
		}
		return stlPackageDTO;
	}

	/**
	 * 修改手续费
	 * 
	 * @param stlPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public StlPackageDTO settleChangeFee(StlPackageDTO stlPackageDTO)
			throws BizServiceException {
		try {
			logger.info("############修改手续费############");

			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(stlPackageDTO.getTxnCode());
			obj.setServiceName(HSTConstants.BILL_PROC);
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));

			obj.setPinTxn(stlPackageDTO.getSettleId());
			obj.setInnerPosNo(stlPackageDTO.getUserid());
			obj.setReferenceNo(stlPackageDTO.getFee_offset());

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
			stlPackageDTO.setRspCode(respCode);
		} catch (BizServiceException e) {
			
			logger.error(e.getMessage());
			throw e;
		}
		return stlPackageDTO;
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

}
