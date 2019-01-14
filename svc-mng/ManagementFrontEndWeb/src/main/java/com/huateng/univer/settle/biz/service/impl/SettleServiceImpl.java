package com.huateng.univer.settle.biz.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.settle.dto.SettleDTO;
import com.allinfinance.univer.settle.dto.SettleQueryDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.ConsumerDAO;
import com.huateng.framework.ibatis.model.Merchant;
import com.huateng.framework.ibatis.model.MerchantExample;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.ConfigMakeCard;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.Log;
import com.huateng.framework.util.StringUtil;
import com.huateng.hstserver.config.HSTProperties;
import com.huateng.hstserver.gatewayService.Java2STLBusinessServiceImpl;
import com.huateng.hstserver.model.StlPackageDTO;
import com.huateng.univer.consumer.merchant.integration.dao.MerchantServiceDAO;
import com.huateng.univer.settle.biz.service.SettleService;

public class SettleServiceImpl implements SettleService {
	Logger logger = Logger.getLogger(SettleServiceImpl.class);

	private StlPackageDTO stlPackageDTO = new StlPackageDTO();
	String respCode = "";
	String allRecord = "";
	
	private Java2STLBusinessServiceImpl java2STLBusinessService;
	

	


	public Java2STLBusinessServiceImpl getJava2STLBusinessService() {
		return java2STLBusinessService;
	}

	public void setJava2STLBusinessService(
			Java2STLBusinessServiceImpl java2stlBusinessService) {
		java2STLBusinessService = java2stlBusinessService;
	}

	/** 商户服务DAO */
	private MerchantServiceDAO merchantServiceDAO;
	private ConsumerDAO consumerDAO;

	public MerchantServiceDAO getMerchantServiceDAO() {
		return merchantServiceDAO;
	}

	public void setMerchantServiceDAO(MerchantServiceDAO merchantServiceDAO) {
		this.merchantServiceDAO = merchantServiceDAO;
	}

	public ConsumerDAO getConsumerDAO() {
		return consumerDAO;
	}

	public void setConsumerDAO(ConsumerDAO consumerDAO) {
		this.consumerDAO = consumerDAO;
	}
	
	
	public StlPackageDTO getStlPackageDTO() {
		return stlPackageDTO;
	}

	public void setStlPackageDTO(StlPackageDTO stlPackageDTO) {
		this.stlPackageDTO = stlPackageDTO;
	}

	//结算单查询
	public StlPackageDTO send(SettleQueryDTO settleQueryDTO, String settleState)throws BizServiceException {
		
		StlPackageDTO stlPackageDTO=new StlPackageDTO();
		
		stlPackageDTO.setTxnCode("9007");
		//结算单号
		stlPackageDTO.setSettleId(settleQueryDTO.getSettleId());
		//结算单状态
		stlPackageDTO.setSettleState(settleState);
		//上级机构
		stlPackageDTO.setSettleObject1(settleQueryDTO.getDefaultEntityId());
		//商户编号
		stlPackageDTO.setMerchantCode(settleQueryDTO.getSettleObject2());
		//商户名称
		stlPackageDTO.setMerchantName(settleQueryDTO.getSettleObject2Name());
		//结算单开始日期
		stlPackageDTO.setStartDate( DateUtil.getFormatTime(settleQueryDTO.getSettleStartDate()));
		//结算单结束日期
		stlPackageDTO.setEndDate(DateUtil.getFormatTime(settleQueryDTO.getSettleEndDate()));
		//最小结算金额
		stlPackageDTO.setMinAmt(settleQueryDTO.getMinAmt());
		//最大结算金额
		stlPackageDTO.setMaxAmt(settleQueryDTO.getMaxAmt());
		//开始行
		stlPackageDTO.setStartRow( String.valueOf(settleQueryDTO.getFirstCursorPosition()));
		//结束行
		stlPackageDTO.setEndRow(String.valueOf(settleQueryDTO.getLastCursorPosition()));
		Log.printObject(settleQueryDTO);
		try{
			stlPackageDTO=java2STLBusinessService.querySettle(stlPackageDTO);
			
			respCode = stlPackageDTO.getRspCode();
			logger.debug("respCode：" + respCode);
			if ("00".equals(respCode)) {
				//原来代码是累加allRecord,不知道为什么这么做，暂时先改成之间赋值
				//allRecord += stlPackageDTO.getOtherData();
				allRecord = stlPackageDTO.getOtherData();
			}
		} catch (Exception e) {
		    this.logger.error("结算单查询失败！  "+"   交易编码（TxnCode）:" + 
		            "9007" + "  结算单号(SettleId):" + settleQueryDTO.getSettleId());
			this.logger.error(e.getMessage());
		}
		
	    //以前的代码
	/*	Map map = new HashMap();

<<<<<<< .mine
		try {
			map.put(CFunctionConstant.TXN_TYPE, "9007");
			map.put(CFunctionConstant.PIN_TXN, settleQueryDTO.getSettleId());
			map.put(CFunctionConstant.PIN_TXN_NEW, settleState);
			map.put(CFunctionConstant.DATA_HEAD, settleQueryDTO.getDefaultEntityId());
			map.put(CFunctionConstant.INNER_MERCHANT_NO, settleQueryDTO.getSettleObject2());
			map.put(CFunctionConstant.FILE_PATH, settleQueryDTO.getSettleObject2Name());
			map.put(CFunctionConstant.SWT_TXN_DATE, DateUtil.getFormatTime(settleQueryDTO.getSettleStartDate()));
			map.put(CFunctionConstant.SWT_SETTLE_DATE, DateUtil.getFormatTime(settleQueryDTO.getSettleEndDate()));
			map.put(CFunctionConstant.TXN_AMOUNT, settleQueryDTO.getMinAmt());
			map.put(CFunctionConstant.BALANCE, settleQueryDTO.getMaxAmt());
			map.put(CFunctionConstant.REC_TXN_DATE, String.valueOf(settleQueryDTO.getFirstCursorPosition()));
			map.put(CFunctionConstant.REC_TXN_TIME, String.valueOf(settleQueryDTO.getLastCursorPosition()));
			Log.printObject(settleQueryDTO);
			OperationResult or;
			try {
				or = java2C.sendTpService("vBillProc", map,Const.JAVA2C_BIG_AMT, false);
			} catch (Exception e) {
				
				this.logger.error(e.getMessage());
				throw new BizServiceException("C后台未启动！！");
			}
			map1 = (HashMap) or.getDetailvo();
			respCode = (String) map1.get(CFunctionConstant.RESP_CODE);
			logger.debug("respCode：" + respCode);
			if ("00".equals(respCode)) {
				allRecord += (String) map1.get(CFunctionConstant.OTHERDATA);
			}
		} catch (BizServiceException e) {
			throw e;
		}*/
		return stlPackageDTO;

//		try {
//			map.put(CFunctionConstant.TXN_TYPE, "9007");
//			map.put(CFunctionConstant.PIN_TXN, settleQueryDTO.getSettleId());
//			map.put(CFunctionConstant.PIN_TXN_NEW, settleState);
//			map.put(CFunctionConstant.DATA_HEAD, settleQueryDTO
//					.getDefaultEntityId());
//			map.put(CFunctionConstant.INNER_MERCHANT_NO, settleQueryDTO
//					.getSettleObject2());
//			map.put(CFunctionConstant.FILE_PATH, settleQueryDTO
//					.getSettleObject2Name());
//			map.put(CFunctionConstant.SWT_TXN_DATE, DateUtil
//					.getFormatTime(settleQueryDTO.getSettleStartDate()));
//			map.put(CFunctionConstant.SWT_SETTLE_DATE, DateUtil
//					.getFormatTime(settleQueryDTO.getSettleEndDate()));
//			map.put(CFunctionConstant.TXN_AMOUNT, settleQueryDTO.getMinAmt());
//			map.put(CFunctionConstant.BALANCE, settleQueryDTO.getMaxAmt());
//			map.put(CFunctionConstant.REC_TXN_DATE, String
//					.valueOf(settleQueryDTO.getFirstCursorPosition()));
//			map.put(CFunctionConstant.REC_TXN_TIME, String
//					.valueOf(settleQueryDTO.getLastCursorPosition()));
//			Log.printObject(settleQueryDTO);
//			OperationResult or;
//			try {
//				or = java2C.sendTpService("vBillProc", map,
//						Const.JAVA2C_BIG_AMT, false);
//			} catch (Exception e) {
//				
//				this.logger.error(e.getMessage());
//				throw new BizServiceException("C后台未启动！！");
//			}
//			map1 = (HashMap) or.getDetailvo();
//			respCode = (String) map1.get(CFunctionConstant.RESP_CODE);
//			logger.debug("respCode：" + respCode);
//			if ("00".equals(respCode)) {
//				allRecord += (String) map1.get(CFunctionConstant.OTHERDATA);
//			}
//		} catch (BizServiceException e) {
//			throw e;
//		}


	}

	// 结算单查询
	public PageDataDTO querySettleList(SettleQueryDTO settleQueryDTO)
			throws BizServiceException {
		PageDataDTO pageDataDTO = new PageDataDTO();
		// 商户号作为查询条件时，界面原输入商户的entity_id，现改为输入merchant_code进行查询
		if (null != settleQueryDTO.getSettleObject2()
				&& !"".equals(settleQueryDTO.getSettleObject2())) {
			MerchantExample exa = new MerchantExample();
			exa.createCriteria().andMerchantCodeEqualTo(
					settleQueryDTO.getSettleObject2())
					        .andFatherEntityIdEqualTo(settleQueryDTO.getDefaultEntityId());
			List<Merchant> mList = merchantServiceDAO.selectByExample(exa);
			if (mList.size() > 0) {
				settleQueryDTO.setSettleObject2(mList.get(0).getEntityId());
			} else {
				settleQueryDTO.setSettleObject2("");
			}
		}
		try {
			allRecord = "";
			List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
			StlPackageDTO stlPackageDTO=this.send(settleQueryDTO, settleQueryDTO.getSettleState());
			respCode=stlPackageDTO.getRspCode();
			allRecord=stlPackageDTO.getOtherData();
			if ("00".equals(respCode)) {
				logger.debug("allRecord:" + allRecord);
				if (!"".equals(allRecord)) {
					String[] records = allRecord.split("\\|");
					int size = records.length;
					logger.debug("records" + records + "  结算单有" + size + "条记录");
					if (records != null && size > 0) {
						for (int i = 0; i < size; i++) {
							String record = records[size-1-i];
							logger.debug(record);
							String[] field = record.split("\\^");
							Map<String,String> map2 = new HashMap<String,String>();
							map2.put("settleId", field[0]);
							map2.put("settleObject2", field[1]);
							map2.put("settleObject2Name", field[2]);
							map2.put("settleStartDate", DateUtil.formatStringDate(field[3]));
							map2.put("settleEndDate", DateUtil.formatStringDate(field[4]));	
							map2.put("settleAmt", Amount.getReallyAmount(field[5]));
							map2.put("txnFee", Amount.getReallyAmount(field[6]));
//							map2.put("oldFee", Amount.getReallyAmount(field[7]));
							//退货金额
							map2.put("refundFee", Amount.getReallyAmount(field[7]));
							//交易金额
							map2.put("txnAmt", Amount.getReallyAmount(field[8]));
							map2.put("settleState", field[9]);
							MerchantExample example = new MerchantExample();
							example.createCriteria().andEntityIdEqualTo(field[1])
							        .andFatherEntityIdEqualTo(settleQueryDTO.getDefaultEntityId());
							List<Merchant> merchantList = merchantServiceDAO.selectByExample(example);
							map2.put("merchantCode", merchantList.get(0).getMerchantCode());
							
							//消费总金额
							BigDecimal txnAmt = new BigDecimal(Amount.getReallyAmount(field[8]));
							BigDecimal refundFee = new BigDecimal(Amount.getReallyAmount(field[7]));
							txnAmt = txnAmt.add(refundFee);
							String consumeAmt = txnAmt.toString();
							map2.put("consumeAmt", consumeAmt);
							list.add(map2);
						}
					}
				}
				/*if ((String) map1.get(CFunctionConstant.REC_TXN_TIME) != "") {
					pageDataDTO.setTotalRecord(Integer.parseInt((String) map1
							.get(CFunctionConstant.REC_TXN_TIME)));
				}*/
				if (null != stlPackageDTO.getRow_tot() && !"".equals(stlPackageDTO.getRow_tot())) {
						pageDataDTO.setTotalRecord(Integer.parseInt(stlPackageDTO.getRow_tot()));
				}
			
				pageDataDTO.setData(list);
			} else if ("05".equals(respCode)) {
				throw new BizServiceException("行数错误");
			} else if ("07".equals(respCode)) {
				throw new BizServiceException("交易代码有误");
			}

			// List<Map<?,?>> list = new ArrayList<Map<?,?>>();
			// for(int i=1;i<50;i++){
			// Map map=new HashMap();
			// map.put("settleId", "999"+i);
			// map.put("settleObject2","888"+i);
			// map.put("settleObject2Name", "商户"+i);
			// map.put("settleStartDate","2010-12-12");
			// map.put("settleEndDate", "2010-12-30");
			// map.put("settleAmt", "200"+i);
			// map.put("txnFee", "100"+i);
			// map.put("oldFee", "230"+i);
			// map.put("txnAmt", "43"+i);
			// map.put("settleState", "1");
			// list.add(map);
			// }
			// pageDataDTO.setTotalRecord(50);
			// pageDataDTO.setData(list);

		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return pageDataDTO;
	}


	public List<MerchantDTO> previewSettleInit(String consumerId)
			throws BizServiceException {
		List<MerchantDTO> merchantdtos = (List<MerchantDTO>) consumerDAO
				.selectMerchantByconsumer(consumerId);
		return merchantdtos;
	}

	// 结算单预览
	public PageDataDTO previewSettle(SettleQueryDTO settleQueryDTO)
			throws BizServiceException {
		PageDataDTO pageDataDTO = new PageDataDTO();
		try {
			StlPackageDTO stlPackageDTO=new StlPackageDTO();
			
			stlPackageDTO.setTxnCode("9001");
			//实体ID
			stlPackageDTO.setSettleObject1(settleQueryDTO.getSettleObject1());
			//商户编号
			stlPackageDTO.setMerchantCode(settleQueryDTO.getSettleObject2());
			//商户名称
			stlPackageDTO.setMerchantName(settleQueryDTO.getSettleObject2Name());
			//渠道
			stlPackageDTO.setChannel(HSTProperties.getString("STL_CHNL_ID"));
			//结算单开始日期
			stlPackageDTO.setStartDate( DateUtil.getFormatTime(settleQueryDTO.getSettleStartDate()));
			//结算单结束日期
			stlPackageDTO.setEndDate(DateUtil.getFormatTime(settleQueryDTO.getSettleEndDate()));
			//最小结算金额
			stlPackageDTO.setMinAmt(settleQueryDTO.getMinAmt());
			//最大结算金额
			stlPackageDTO.setMaxAmt(settleQueryDTO.getMaxAmt());
			//开始行
			stlPackageDTO.setStartRow( String.valueOf(settleQueryDTO.getFirstCursorPosition()));
			//结束行
			stlPackageDTO.setEndRow(String.valueOf(settleQueryDTO.getLastCursorPosition()));
			
			String rspCode="";
			try{
				stlPackageDTO=java2STLBusinessService.previewSettle(stlPackageDTO);
				rspCode=stlPackageDTO.getRspCode();
			} catch (Exception e) {
				this.logger.error(e.getMessage());
			}
			
			if ("00".equals(rspCode)) {
				List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
				String result = stlPackageDTO.getOtherData();
				String[] records = result.split("\\|");
				if (null != result && !"".equals(result) && records != null && records.length > 0) {
					for (int i = 0; i < records.length; i++) {
						String record = records[i];
						String[] fields = record.split("\\^");
						Map<String,String> map2 = new HashMap<String,String>();
						map2.put("settleObject1", fields[0]);
						map2.put("merchantId", fields[1]);
						/*
						 * MerchantExample example = new MerchantExample();
						 * example
						 * .createCriteria().andEntityIdEqualTo(fields[1]);
						 * List<Merchant> merchantList = merchantServiceDAO
						 * .selectByExample(example); map2.put("merchantId",
						 * merchantList.get(0).getMerchantCode());
						 */
						MerchantExample example = new MerchantExample();
						example.createCriteria().andEntityIdEqualTo(fields[1])
						        .andFatherEntityIdEqualTo(settleQueryDTO.getDefaultEntityId());
						List<Merchant> merchantList = merchantServiceDAO.selectByExample(example);
						map2.put("merchantCode", merchantList.get(0).getMerchantCode());
						map2.put("merchantName", fields[2]);
						map2.put("settStartDate", fields[3]);
						map2.put("settleEndDate", fields[4]);
						map2.put("amtDesc", Amount.getReallyAmount(fields[5]));
						map2.put("contractId", fields[6]);

						logger.debug(map2.get("settStartDate"));
						list.add(map2);
					}
				}
				if (null !=stlPackageDTO.getRow_tot() && !"".equals(stlPackageDTO.getRow_tot())) {
					pageDataDTO.setTotalRecord(Integer.parseInt(stlPackageDTO.getRow_tot()));
				}
				pageDataDTO.setData(list);
			} else if ("03".equals(rspCode)) {
				throw new BizServiceException("无效商户！！");
			} else if ("96".equals(rspCode)) {
				throw new BizServiceException("未找到消费合同");
			} else if ("07".equals(rspCode)) {
				throw new BizServiceException("交易代码有误");
			} else {
				throw new BizServiceException("系统错误!错误码:" + rspCode);
			}

		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			// FIXME by wpf 这啥啊.上帝告诉我这个啥坑呗? 捕获异常不抛出来也不处理是啥意思?
			// 这里处理掉.同时帮着改下JUNIT @see SettleServiceImplTest
		}
		
/*
			Map map = new HashMap();
			
			map.put(CFunctionConstant.TXN_TYPE, "9001");
			
			map.put(CFunctionConstant.DATA_HEAD, settleQueryDTO.getSettleObject1());
			logger.debug(map.get(CFunctionConstant.DATA_HEAD));
			map.put(CFunctionConstant.INNER_MERCHANT_NO, settleQueryDTO.getSettleObject2());
			map.put(CFunctionConstant.FILE_PATH, settleQueryDTO.getSettleObject2Name());
			map.put(CFunctionConstant.CHANNEL, settleQueryDTO.getSettleState());
			map.put(CFunctionConstant.SWT_TXN_DATE, settleQueryDTO.getSettleStartDate());
			map.put(CFunctionConstant.SWT_SETTLE_DATE, settleQueryDTO.getSettleEndDate());
			map.put(CFunctionConstant.TXN_AMOUNT, settleQueryDTO.getMinAmt());
			map.put(CFunctionConstant.BALANCE, settleQueryDTO.getMaxAmt());
			map.put(CFunctionConstant.REC_TXN_DATE, String.valueOf(settleQueryDTO.getFirstCursorPosition()));
			map.put(CFunctionConstant.REC_TXN_TIME, String.valueOf(settleQueryDTO.getLastCursorPosition()));
			OperationResult or;
			try {
				or = java2C.sendTpService("vBillProc", map,
						Const.JAVA2C_BIG_AMT, false);
			} catch (Exception e) {
				
				this.logger.error(e.getMessage());
				throw new BizServiceException("后台未启动！！");
			}
			HashMap map1 = (HashMap) or.getDetailvo();
			
			String rspCode = (String) map1.get(CFunctionConstant.RESP_CODE);
			logger.debug("rspCode:" + rspCode);
			
		
			if ("00".equals(rspCode)) {
				List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
				String result = (String) map1.get(CFunctionConstant.OTHERDATA);
				String[] records = result.split("\\|");
				if (result != "" && records != null && records.length > 0) {
					for (int i = 0; i < records.length; i++) {
						String record = records[i];
						String[] fields = record.split("\\^");
						Map map2 = new HashMap();
						map2.put("settleObject1", fields[0]);

						map2.put("merchantId", fields[1]);
						
//						 * MerchantExample example = new MerchantExample();
//						 * example
//						 * .createCriteria().andEntityIdEqualTo(fields[1]);
//						 * List<Merchant> merchantList = merchantServiceDAO
//						 * .selectByExample(example); map2.put("merchantId",
//						 * merchantList.get(0).getMerchantCode());
						 
						MerchantExample example = new MerchantExample();
						example.createCriteria().andEntityIdEqualTo(fields[1]);
						List<Merchant> merchantList = merchantServiceDAO
								.selectByExample(example);
						map2.put("merchantCode", merchantList.get(0)
								.getMerchantCode());
						map2.put("merchantName", fields[2]);
						map2.put("settStartDate", fields[3]);
						map2.put("settleEndDate", fields[4]);
						map2.put("amtDesc", Amount.getReallyAmount(fields[5]));
						map2.put("contractId", fields[6]);

						logger.debug(map2.get("settStartDate"));
						list.add(map2);
					}
				}
				if ((String) map1.get(CFunctionConstant.REC_TXN_TIME) != "") {
					pageDataDTO.setTotalRecord(Integer.parseInt(map1.get(
							CFunctionConstant.REC_TXN_TIME).toString()));
				}
				pageDataDTO.setData(list);
			} else if ("03".equals(rspCode)) {
				throw new BizServiceException("无效商户！！");
			} else if ("96".equals(rspCode)) {
				throw new BizServiceException("未找到消费合同");
			} else if ("07".equals(rspCode)) {
				throw new BizServiceException("交易代码有误");
			} else {
				throw new BizServiceException("系统错误!错误码:" + rspCode);
			}
			

		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
=======
//		try {
//			stlPackageDTO.setEntityId(settleQueryDTO.getSettleObject1());
//			stlPackageDTO.setMerchantCode(settleQueryDTO.getSettleObject2());
//			stlPackageDTO.setMerchantName(settleQueryDTO.getSettleObject2Name());
//			stlPackageDTO.setSettleState(settleQueryDTO.getSettleState());
//			stlPackageDTO.setSettleStartDate(settleQueryDTO.getSettleStartDate());
//			stlPackageDTO.setSettleEndDate(settleQueryDTO.getSettleEndDate());
//			stlPackageDTO.setMinAmount(settleQueryDTO.getMinAmt());
//			stlPackageDTO.setMaxAmount(settleQueryDTO.getMaxAmt());
//			stlPackageDTO.setStartRow(String.valueOf(settleQueryDTO.getFirstCursorPosition()));
//			stlPackageDTO.setEndRow(String.valueOf(settleQueryDTO.getLastCursorPosition()));			
//			try {
//				or = java2C.sendTpService("vBillProc", map,
//						Const.JAVA2C_BIG_AMT, false);
//			} catch (Exception e) {
//				
//				this.logger.error(e.getMessage());
//				throw new BizServiceException("后台未启动！！");
//			}
//			HashMap map1 = (HashMap) or.getDetailvo();
//			String rspCode = (String) map1.get(CFunctionConstant.RESP_CODE);
//			logger.debug("rspCode:" + rspCode);
//			if ("00".equals(rspCode)) {
//				List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
//				String result = (String) map1.get(CFunctionConstant.OTHERDATA);
//				String[] records = result.split("\\|");
//				if (result != "" && records != null && records.length > 0) {
//					for (int i = 0; i < records.length; i++) {
//						String record = records[i];
//						String[] fields = record.split("\\^");
//						Map map2 = new HashMap();
//						map2.put("settleObject1", fields[0]);
//
//						map2.put("merchantId", fields[1]);
//						/*
//						 * MerchantExample example = new MerchantExample();
//						 * example
//						 * .createCriteria().andEntityIdEqualTo(fields[1]);
//						 * List<Merchant> merchantList = merchantServiceDAO
//						 * .selectByExample(example); map2.put("merchantId",
//						 * merchantList.get(0).getMerchantCode());
//						 */
//						MerchantExample example = new MerchantExample();
//						example.createCriteria().andEntityIdEqualTo(fields[1]);
//						List<Merchant> merchantList = merchantServiceDAO
//								.selectByExample(example);
//						map2.put("merchantCode", merchantList.get(0)
//								.getMerchantCode());
//						map2.put("merchantName", fields[2]);
//						map2.put("settStartDate", fields[3]);
//						map2.put("settleEndDate", fields[4]);
//						map2.put("amtDesc", Amount.getReallyAmount(fields[5]));
//						map2.put("contractId", fields[6]);
//
//						logger.debug(map2.get("settStartDate"));
//						list.add(map2);
//					}
//				}
//				if ((String) map1.get(CFunctionConstant.REC_TXN_TIME) != "") {
//					pageDataDTO.setTotalRecord(Integer.parseInt(map1.get(
//							CFunctionConstant.REC_TXN_TIME).toString()));
//				}
//				pageDataDTO.setData(list);
//			} else if ("03".equals(rspCode)) {
//				throw new BizServiceException("无效商户！！");
//			} else if ("96".equals(rspCode)) {
//				throw new BizServiceException("未找到消费合同");
//			} else if ("07".equals(rspCode)) {
//				throw new BizServiceException("交易代码有误");
//			} else {
//				throw new BizServiceException("系统错误!错误码:" + rspCode);
//			}
//
//		} catch (BizServiceException b) {
//			throw b;
//		} catch (Exception e) {
//			this.logger.error(e.getMessage());
//		}


		return pageDataDTO;
	}

	// 生成结算单
	public void generateSettle(SettleDTO settleDTO) throws BizServiceException {

		try {
			StlPackageDTO stlPackageDTO=new StlPackageDTO();
			
			stlPackageDTO.setTxnCode("9003");
			//结算单号
			stlPackageDTO.setSettleId(settleDTO.getSettleObject1());
			//商户编号
			stlPackageDTO.setMerchantCode(settleDTO.getSettleObject2());
			//上一结算日期
			stlPackageDTO.setSettle_dt_pre(settleDTO.getSettleStartDate());
			//下一结算日期
			stlPackageDTO.setSettle_dt_next(settleDTO.getSettleDate());
			
			stlPackageDTO.setUserid(settleDTO.getLoginUserId());
			//商户合同号
			stlPackageDTO.setContractId(settleDTO.getContractId());
			
			
			try{
				stlPackageDTO=java2STLBusinessService.generateSettle(stlPackageDTO);
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				throw new BizServiceException("后台未启动！！");
			}
			
			String respCode=stlPackageDTO.getRspCode();
			
			if ("00".equals(respCode)) {
				logger.debug("结算单生成成功");
			} else if ("03".equals(respCode)) {
				throw new BizServiceException("无效商户");
			} else if ("12".equals(respCode)) {
				throw new BizServiceException("无效日期");
			} else if ("07".equals(respCode)) {
				throw new BizServiceException("交易代码有误");
			} else {
				throw new BizServiceException("系统错误!错误码:" + respCode);
			}
			
/*			Map map = new HashMap();
			map.put(CFunctionConstant.TXN_TYPE, "9003");
			// map.put(CFunctionConstant.DATA_HEAD, settleDTO
			// .getDefaultEntityId());
			map.put(CFunctionConstant.DATA_HEAD, settleDTO.getSettleObject1());
			map.put(CFunctionConstant.INNER_MERCHANT_NO, settleDTO.getSettleObject2());
			map.put(CFunctionConstant.SWT_TXN_DATE, settleDTO.getSettleStartDate());
			map.put(CFunctionConstant.SWT_SETTLE_DATE, settleDTO.getSettleDate());
			map.put(CFunctionConstant.INNER_POS_NO, settleDTO.getLoginUserId());
			OperationResult or;
			try {
				or = java2C.sendTpService("vBillProc", map,
						Const.JAVA2C_BIG_AMT, false);
			} catch (Exception e) {
				
				this.logger.error(e.getMessage());
				throw new BizServiceException("后台未启动！！");
			}
			HashMap map1 = (HashMap) or.getDetailvo();
			String respCode = (String) map1.get(CFunctionConstant.RESP_CODE);
			logger.debug(respCode);
			if ("00".equals(respCode)) {
				logger.debug("结算单生成成功");
			} else if ("03".equals(respCode)) {
				throw new BizServiceException("无效商户");
			} else if ("12".equals(respCode)) {
				throw new BizServiceException("无效日期");
			} else if ("07".equals(respCode)) {
				throw new BizServiceException("交易代码有误");
			} else {
				throw new BizServiceException("系统错误!错误码:" + respCode);
			}*/
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}

	
	//结算单明细信息
	public PageDataDTO settleDetail(SettleQueryDTO settleQueryDTO)
			throws BizServiceException {
		PageDataDTO pageDataDTO = new PageDataDTO();
		
		try {
			StlPackageDTO stlPackageDTO=new StlPackageDTO();
			
			stlPackageDTO.setTxnCode("9008");
			//结算单号
			stlPackageDTO.setSettleId(settleQueryDTO.getSettleId());
	
			try{
				stlPackageDTO=java2STLBusinessService.settleDetail(stlPackageDTO);
				
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				throw new BizServiceException("后台未启动！！");
			}
			
		
			String respCode = stlPackageDTO.getRspCode();
			logger.debug("respCode:" + respCode);
			if ("00".equals(respCode)) {
				List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
				String result =stlPackageDTO.getOtherData();
				logger.debug("result" + result);
				String[] records = result.split("\\|");
				logger.debug(records.length);
				if (null != result && !"".equals(result) && records != null && records.length > 0) {
					for (int i = 0; i < records.length; i++) {
						String record = records[i];
						logger.debug(record);
						String[] fields = record.split("\\^");
						logger.debug(fields.length);
						Map<String,String> map2 = new HashMap<String,String>();
						map2.put("settleId", fields[0]);
						map2.put("merchantId", fields[1]); 
						MerchantExample example = new MerchantExample();
						example.createCriteria().andEntityIdEqualTo(fields[1])
						        .andFatherEntityIdEqualTo(settleQueryDTO.getDefaultEntityId());
						List<Merchant> merchantList = merchantServiceDAO.selectByExample(example);
						map2.put("merchantCode", merchantList.get(0).getMerchantCode());
						map2.put("merchantName", fields[2]);
						map2.put("settleStartDate", DateUtil.formatStringDate(fields[3]));
						map2.put("settleEndDate", DateUtil.formatStringDate(fields[4]));
						map2.put("txnAmt", Amount.getReallyAmount(fields[5]));
						map2.put("serviceFee", Amount.getReallyAmount(fields[6]));
						map2.put("settleAmount", Amount.getReallyAmount(fields[7]));
						map2.put("contractId", fields[8]);
						list.add(map2);
					}
				}
				pageDataDTO.setData(list);
				logger.debug("查询明细成功");
			} else if ("01".equals(respCode)) {
				throw new BizServiceException("无效结算单号");
			} else if ("07".equals(respCode)) {
				throw new BizServiceException("交易代码有误");
			} else {
				throw new BizServiceException("系统错误!错误码:" + respCode);
			}
			
			
/*			Map map = new HashMap();
			map.put(CFunctionConstant.TXN_TYPE, "9008");
			map.put(CFunctionConstant.PIN_TXN, settleQueryDTO.getSettleId());
			OperationResult or;
			try {
				or = java2C.sendTpService("vBillProc", map,Const.JAVA2C_BIG_AMT, false);
			} catch (Exception e) {
				
				this.logger.error(e.getMessage());
				throw new BizServiceException("后台未启动！！");
			}
			HashMap map1 = (HashMap) or.getDetailvo();
			String respCode = (String) map1.get(CFunctionConstant.RESP_CODE);
			logger.debug("respCode:" + respCode);
			if ("00".equals(respCode)) {
				List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
				String result = (String) map1.get(CFunctionConstant.OTHERDATA);
				logger.debug("result" + result);
				String[] records = result.split("\\|");
				logger.debug(records.length);
				if (result != "" && records != null && records.length > 0) {
					for (int i = 0; i < records.length; i++) {
						String record = records[i];
						logger.debug(record);
						String[] fields = record.split("\\^");
						logger.debug(fields.length);
						Map map2 = new HashMap();
						map2.put("settleId", fields[0]);
						map2.put("merchantId", fields[1]);
						
						 * MerchantExample example = new MerchantExample();
						 * example
						 * .createCriteria().andEntityIdEqualTo(fields[1]);
						 * List<Merchant> merchantList = merchantServiceDAO
						 * .selectByExample(example); map2.put("merchantId",
						 * merchantList.get(0).getMerchantCode());
						 
						MerchantExample example = new MerchantExample();
						example.createCriteria().andEntityIdEqualTo(fields[1]);
						List<Merchant> merchantList = merchantServiceDAO.selectByExample(example);
						map2.put("merchantCode", merchantList.get(0).getMerchantCode());
						map2.put("merchantName", fields[2]);
						map2.put("settleStartDate", DateUtil.formatStringDate(fields[3]));
						map2.put("settleEndDate", DateUtil.formatStringDate(fields[4]));
						map2.put("txnAmt", Amount.getReallyAmount(fields[5]));
						map2.put("serviceFee", Amount.getReallyAmount(fields[6]));
						map2.put("settleAmount", Amount.getReallyAmount(fields[7]));
						map2.put("contractId", fields[8]);
						list.add(map2);
					}
				}
				pageDataDTO.setData(list);
				logger.debug("查询明细成功");
			} else if ("01".equals(respCode)) {
				throw new BizServiceException("无效结算单号");
			} else if ("07".equals(respCode)) {
				throw new BizServiceException("交易代码有误");
			} else {
				throw new BizServiceException("系统错误!错误码:" + respCode);
			}*/
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return pageDataDTO;
	}

	// 查询结算单明细

	public SettleQueryDTO querySettleDetail(SettleQueryDTO settleQueryDTO)
			throws BizServiceException {
		// PageDataDTO pageDataDTO = new PageDataDTO();
		// try {
		// Map map = new HashMap();
		// map.put(CFunctionConstant.TXN_TYPE, "9008");
		// map.put(CFunctionConstant.PIN_TXN, settleQueryDTO.getSettleId());
		// OperationResult or;
		// try {
		// or = java2C.sendTpService("vBillProc", map,
		// Const.JAVA2C_BIG_AMT, false);
		// } catch (Exception e) {
		// 
		// this.logger.error(e.getMessage());
		// throw new BizServiceException("后台未启动！！");
		// }
		// HashMap map1 = (HashMap) or.getDetailvo();
		// String respCode = (String) map1.get(CFunctionConstant.RESP_CODE);
		// logger.debug("respCode:" + respCode);
		// if ("00".equals(respCode)) {
		// List<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
		// String result = (String) map1.get(CFunctionConstant.OTHERDATA);
		// logger.debug("result" + result);
		// String[] records = result.split("\\|");
		// logger.debug(records.length);
		// if (result != "" && records != null && records.length > 0) {
		// for (int i = 0; i < records.length; i++) {
		// String record = records[i];
		// logger.debug(record);
		// String[] fields = record.split("\\^");
		// logger.debug(fields.length);
		// Map map2 = new HashMap();
		// map2.put("settleId", fields[0]);
		// map2.put("merchantId", fields[1]);
		// map2.put("merchantName", fields[2]);
		// map2.put("settleStartDate", fields[3]);
		// map2.put("settleEndDate", fields[4]);
		// map2.put("txnAmt", Amount.getReallyAmount(fields[5]));
		// map2.put("serviceFee", Amount.getReallyAmount(fields[6]));
		// map2.put("settleAmount", Amount
		// .getReallyAmount(fields[7]));
		// map2.put("contractId", fields[8]);
		// list.add(map2);
		// }
		// }
		// pageDataDTO.setData(list);
		// logger.debug("查询明细成功");
		// }else if("01".equals(respCode)){
		// throw new BizServiceException("无效结算单号");
		// }else if("07".equals(respCode)){
		// throw new BizServiceException("交易代码有误");
		// }else{
		// throw new BizServiceException("C后台未启动！！");
		// }
		// } catch (BizServiceException b) {
		// throw b;
		// } catch (Exception e) {
		// 
		// this.logger.error(e.getMessage());
		// }
		// return pageDataDTO;

		try {
			String downFileName = settleQueryDTO.getSettleId() + "detail";
			Map<String, List<byte[]>> fileMap = new HashMap<String, List<byte[]>>();
			List<byte[]> byteList = this.ftpGetSettleDetail(downFileName);
			fileMap.put(downFileName, byteList);
			settleQueryDTO.setFileData(fileMap);
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			logger.debug("查询结算单明细失败");
		}
		return settleQueryDTO;

	}

	public List<byte[]> ftpGetSettleDetail(String downFileName)
			throws Exception {
		if (StringUtil.isNotEmpty(downFileName)) {
			downFileName = downFileName.trim();
		}
		List<byte[]> byteList = null;
		String downloadFilePath = ConfigMakeCard.getFilePath();
		logger.debug("file[" + downloadFilePath + downFileName + "]");
		BufferedInputStream bs = null;
		File tempFile = new File(downloadFilePath + downFileName + ".txt");
		try {
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
			this.logger.error(e.getMessage());
		}
		return byteList;

	}

	// 结算单确认
	public void confirmSettle(SettleQueryDTO settleQueryDTO)
			throws BizServiceException {
		try {
			StlPackageDTO stlPackageDTO=new StlPackageDTO();
			//交易编号
			stlPackageDTO.setTxnCode("9009");
			//结算单号
			stlPackageDTO.setSettleId(settleQueryDTO.getSettleId());
			//结算单状态
			stlPackageDTO.setSettleState(settleQueryDTO.getSettleState());
			//用户编号
			stlPackageDTO.setUserid(settleQueryDTO.getLoginUserId());
	
			try{
				stlPackageDTO=java2STLBusinessService.confirmSettle(stlPackageDTO);
				
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				throw new BizServiceException("后台未启动！！");
			}
			
			String respCode=stlPackageDTO.getRspCode();
			logger.debug(respCode);
			if ("00".equals(respCode)) {
				logger.debug("结算单确认成功");
			} else if ("01".equals(respCode)) {
				throw new BizServiceException("无效结算单号");
			} else if ("02".equals(respCode)) {
				throw new BizServiceException("无效结算单状态");
			} else if ("07".equals(respCode)) {
				throw new BizServiceException("交易代码有误");
			} else {
				throw new BizServiceException("系统错误!错误码:" + respCode);
			}
			
			
			
		/*	Map map = new HashMap();
			map.put(CFunctionConstant.TXN_TYPE, "9009");
			map.put(CFunctionConstant.PIN_TXN, settleQueryDTO.getSettleId());
			map.put(CFunctionConstant.PIN_TXN_NEW, settleQueryDTO.getSettleState());
			map.put(CFunctionConstant.INNER_POS_NO, settleQueryDTO.getLoginUserId());
			OperationResult or;
			try {
				or = java2C.sendTpService("vBillProc", map,Const.JAVA2C_BIG_AMT, false);
			} catch (Exception e) {
				
				this.logger.error(e.getMessage());
				throw new BizServiceException("后台未启动！！");
			}
			HashMap map1 = (HashMap) or.getDetailvo();
			String respCode = (String) map1.get(CFunctionConstant.RESP_CODE);
			logger.debug(respCode);
			if ("00".equals(respCode)) {
				logger.debug("结算单确认成功");
			} else if ("01".equals(respCode)) {
				throw new BizServiceException("无效结算单号");
			} else if ("02".equals(respCode)) {
				throw new BizServiceException("无效结算单状态");
			} else if ("07".equals(respCode)) {
				throw new BizServiceException("交易代码有误");
			} else {
				throw new BizServiceException("系统错误!错误码:" + respCode);
			}*/
			
			
			
			
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}

	// 结算单付款确认
	public void settlePaymentConfirm(SettleDTO settleDTO)
			throws BizServiceException {

		try {
			StlPackageDTO stlPackageDTO=new StlPackageDTO();
			//交易编号
			stlPackageDTO.setTxnCode("9004");
			//结算单号
			stlPackageDTO.setSettleId(settleDTO.getSettleId());
			//
			stlPackageDTO.setUserid(settleDTO.getLoginUserId());
			
			
			try{
				stlPackageDTO=java2STLBusinessService.settlePaymentConfirm(stlPackageDTO);
				
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				throw new BizServiceException("后台未启动！！");
			}
			
			
/*			Map map = new HashMap();
			map.put(CFunctionConstant.TXN_TYPE, "9004");
			map.put(CFunctionConstant.PIN_TXN, settleDTO.getSettleId());
			map.put(CFunctionConstant.INNER_POS_NO, settleDTO.getLoginUserId());
			OperationResult or;
			try {
				or = java2C.sendTpService("vBillProc", map,Const.JAVA2C_BIG_AMT, false);
			} catch (Exception e) {
				
				this.logger.error(e.getMessage());
				throw new BizServiceException("后台未启动！！");
			}
			HashMap map1 = (HashMap) or.getDetailvo();
			String respCode = (String) map1.get(CFunctionConstant.RESP_CODE);
			logger.debug(respCode);*/
			
			String respCode=stlPackageDTO.getRspCode();
			if ("00".equals(respCode)) {
				logger.debug("结算单付款确认成功");
			} else if ("01".equals(respCode)) {
				throw new BizServiceException("无效结算单号");
			} else if ("07".equals(respCode)) {
				throw new BizServiceException("交易代码有误");
			} else {
				throw new BizServiceException("系统错误!错误码:" + respCode);
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}

	// 修改结算日期
	public void changeSettleDate(SettleDTO settleDTO)
			throws BizServiceException {
		try {
			StlPackageDTO stlPackageDTO=new StlPackageDTO();
			//交易编号
			stlPackageDTO.setTxnCode("9002");
			//实体ID DATA_HEAD
			stlPackageDTO.setSettleId(settleDTO.getSettleObject1());
			//商户编号 INNER_MERCHANT_NO
			stlPackageDTO.setMerchantCode(settleDTO.getSettleObject2());
			//下一结算日期 SWT_SETTLE_DATE
			stlPackageDTO.setSettle_dt_next( settleDTO.getSettleDate());
			//操作人编号 INNER_POS_NO
			stlPackageDTO.setUserid(settleDTO.getLoginUserId());
			//商户合同号
			stlPackageDTO.setContractId(settleDTO.getContractId());

			try{
				stlPackageDTO=java2STLBusinessService.changeSettleDate(stlPackageDTO);
				
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				throw new BizServiceException("后台未启动！！");
			}
			String respCode=stlPackageDTO.getRspCode();
			
			if ("00".equals(respCode)) {
				logger.debug("结算单日期修改成功");
			} else if ("03".equals(respCode)) {
				throw new BizServiceException("无效商户");
			} else if ("12".equals(respCode)) {
				 //日期无效的情况 
				String liquidationDateString = stlPackageDTO.getStartDate()== null ? "": stlPackageDTO.getStartDate();
				String liquidationDate = liquidationDateString.equals("") ? "": DateUtil.formatStringDate(liquidationDateString);
				throw new BizServiceException("结算截止日期必须小于清算日期:"+ liquidationDate+"和小于合同结束日期");
			} else if ("07".equals(respCode)) {
				throw new BizServiceException("交易代码有误");
			} else {
				throw new BizServiceException("系统错误!错误码:" + respCode);
			}
			
			
			
			
/*			Map map = new HashMap();
			map.put(CFunctionConstant.TXN_TYPE, "9002");
			map.put(CFunctionConstant.DATA_HEAD, settleDTO.getSettleObject1());
			map.put(CFunctionConstant.INNER_MERCHANT_NO, settleDTO.getSettleObject2());
			map.put(CFunctionConstant.SWT_SETTLE_DATE, settleDTO.getSettleDate());
			map.put(CFunctionConstant.INNER_POS_NO, settleDTO.getLoginUserId());
			OperationResult or;
			try {
				or = java2C.sendTpService("vBillProc", map,Const.JAVA2C_BIG_AMT, false);
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				throw new BizServiceException("后台未启动！！");
			}
			HashMap map1 = (HashMap) or.getDetailvo();
			String respCode = (String) map1.get(CFunctionConstant.RESP_CODE);
			logger.debug(respCode);
			if ("00".equals(respCode)) {
				logger.debug("结算单日期修改成功");
			} else if ("03".equals(respCode)) {
				throw new BizServiceException("无效商户");
			} else if ("12".equals(respCode)) {
				 日期无效的情况 
				String liquidationDateString = map1.get(CFunctionConstant.SWT_TXN_DATE) == null ? "": (String) map1.get(CFunctionConstant.SWT_TXN_DATE);
				String liquidationDate = liquidationDateString.equals("") ? "": DateUtil.formatStringDate(liquidationDateString);
				throw new BizServiceException("结算截止日期必须小于清算日期:"+ liquidationDate);
			} else if ("07".equals(respCode)) {
				throw new BizServiceException("交易代码有误");
			} else {
				throw new BizServiceException("系统错误!错误码:" + respCode);
			}*/
			
			
			
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}

	// 取消结算单
	public void cancelSettle(SettleDTO settleDTO) throws BizServiceException {
		try {
			
			StlPackageDTO stlPackageDTO=new StlPackageDTO();
			//交易编号
			stlPackageDTO.setTxnCode("9005");
			//结算单号PIN_TXN 
			stlPackageDTO.setSettleId(settleDTO.getSettleId());
			//操作人编号 INNER_POS_NO
			stlPackageDTO.setUserid(settleDTO.getLoginUserId());
			

			try{
				stlPackageDTO=java2STLBusinessService.cancelSettle(stlPackageDTO);
				
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				throw new BizServiceException("后台未启动！！");
			}
			
			String respCode = stlPackageDTO.getRspCode();
			logger.debug(respCode);
			
			if ("00".equals(respCode)) {
				logger.debug("取消结算单成功");
			} else if ("01".equals(respCode)) {
				throw new BizServiceException("无效结算单号");
			} else if ("04".equals(respCode)) {
				throw new BizServiceException("无法取消结算单，需从最近的结算单开始撤销");
			} else if ("07".equals(respCode)) {
				throw new BizServiceException("交易代码有误");
			} else {
				throw new BizServiceException("C后台未启动！！");
			}
			
			
/*			
			Map map = new HashMap();
			map.put(CFunctionConstant.TXN_TYPE, "9005");
			map.put(CFunctionConstant.PIN_TXN, settleDTO.getSettleId());
			map.put(CFunctionConstant.INNER_POS_NO, settleDTO.getLoginUserId());
			OperationResult or;
			try {
				or = java2C.sendTpService("vBillProc", map,Const.JAVA2C_BIG_AMT, false);
			} catch (Exception e) {
				
				this.logger.error(e.getMessage());
				throw new BizServiceException("后台未启动！！");
			}
			HashMap map1 = (HashMap) or.getDetailvo();
			String respCode = (String) map1.get(CFunctionConstant.RESP_CODE);
			logger.debug(respCode);
			if ("00".equals(respCode)) {
				logger.debug("取消结算单成功");
			} else if ("01".equals(respCode)) {
				throw new BizServiceException("无效结算单号");
			} else if ("04".equals(respCode)) {
				throw new BizServiceException("无法取消结算单，需从最近的结算单开始撤销");
			} else if ("07".equals(respCode)) {
				throw new BizServiceException("交易代码有误");
			} else {
				throw new BizServiceException("C后台未启动！！");
			}*/
		}
		catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}

	// 修改手续费
	public void settleChangeFee(SettleDTO settleDTO) throws BizServiceException {
		try {
			StlPackageDTO stlPackageDTO=new StlPackageDTO();
			//交易编号
			stlPackageDTO.setTxnCode("9006");
			//结算单号PIN_TXN 
			stlPackageDTO.setSettleId(settleDTO.getSettleId());
			//操作人编号 INNER_POS_NO
			stlPackageDTO.setUserid(settleDTO.getLoginUserId());
			//手续费偏移量 REFERENCE_NO
			stlPackageDTO.setFee_offset(Amount.getDataBaseAmount(settleDTO.getSettleFeeOffset()));
			

			try{
				stlPackageDTO=java2STLBusinessService.settleChangeFee(stlPackageDTO);
				
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				throw new BizServiceException("后台未启动！！");
			}
			
			
			
/*			Map map = new HashMap();
			map.put(CFunctionConstant.TXN_TYPE, "9006");
			map.put(CFunctionConstant.PIN_TXN, settleDTO.getSettleId());
			map.put(CFunctionConstant.REFERENCE_NO, Amount.getDataBaseAmount(settleDTO.getSettleFeeOffset()));
			map.put(CFunctionConstant.INNER_POS_NO, settleDTO.getLoginUserId());
			OperationResult or;
			try {
				or = java2C.sendTpService("vBillProc", map,
						Const.JAVA2C_BIG_AMT, false);
			} catch (Exception e) {
				this.logger.error(e.getMessage());
				throw new BizServiceException("后台未启动！！");
			}
			HashMap map1 = (HashMap) or.getDetailvo();
			String respCode = (String) map1.get(CFunctionConstant.RESP_CODE);
			logger.debug(respCode);*/
			String repCode=stlPackageDTO.getRspCode();
			
			if ("00".equals(repCode)) {
				logger.debug("手续费修改成功");
			} else if ("01".equals(repCode)) {
				throw new BizServiceException("无效结算单号");
			} else if ("07".equals(repCode)) {
				throw new BizServiceException("交易代码有误");
			} else {
				throw new BizServiceException("操作失败,错误码:" + repCode);
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}

}
