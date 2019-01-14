package com.huateng.hstserver.gatewayService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.biz.BizMessageObj;
import com.huateng.hstserver.communicate.mina.comm.server.client.ManagedAsyn2SynClient;
import com.huateng.hstserver.communicate.mina.comm.server.client.ManagedAsynClient;
import com.huateng.hstserver.config.HSTProperties;
import com.huateng.hstserver.constants.HSTConstants;
import com.huateng.hstserver.exception.BizServiceException;
import com.huateng.hstserver.frameworkUtil.Amount;
import com.huateng.hstserver.frameworkUtil.DateUtil;
import com.huateng.hstserver.frameworkUtil.PararmeterInfo;
import com.huateng.hstserver.model.AccPackageDTO;
import com.huateng.hstserver.model.CardInfoDTO;


public class Java2ACCBusinessServiceImpl {
	private Logger logger = Logger.getLogger(Java2ACCBusinessServiceImpl.class);
	private CommMessage sendMsg = new CommMessage();
	private ManagedAsyn2SynClient managedAsyn2SynClient;
	private ManagedAsynClient managedAsynClient;
	

	private Java2CCommonServiceImpl java2CCommonService;

	// 卡片批量查询
	public AccPackageDTO cardBatchQuery(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.debug("############卡 片 批 量 查 询############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType("G000");
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.CARD_BATCH_QUERY);
			obj.setResv1(accPackageDTO.getCardNo());

			logger.debug("############entry password############");

			if (StringUtils.isNotBlank(accPackageDTO.getCardNo()) && StringUtils.isNotBlank(accPackageDTO.getPassword())) {	
				logger.info("cardno:"+accPackageDTO.getCardNo()+",password:"+accPackageDTO.getPassword()+",SYS_ACC:"+HSTConstants.SYS_ACC);
				obj.setPinTxn(java2CCommonService.encPwdJNKY(accPackageDTO.getCardNo(), accPackageDTO.getPassword(), HSTConstants.SYS_ACC));
			}

			logger.debug("############entry password end############");
			if (StringUtils.isNotBlank(accPackageDTO.getCvv2())) {
				obj.setCvv2(accPackageDTO.getCvv2());
			}
			logger.debug("cardNo:  " + accPackageDTO.getCardNo());
			logger.debug("password:" + accPackageDTO.getPassword());
			logger.debug("CVV2:    " + accPackageDTO.getCvv2());

			obj =send(managedAsyn2SynClient,obj);
			String rspCode = obj.getRespCode();
			accPackageDTO.setRespCode(rspCode);
			logger.debug("respCode：" + rspCode);
			if ("00".equals(obj.getRespCode())) {
				String allRecord = obj.getOtherdata();
				logger.info("allRecord:" + allRecord);

				if (!"".equals(allRecord)) {
					// 解析所有的记录 ，以“|”截取
					String[] records = allRecord.split("\\|");
					logger.info("recordsLength: " + records.length
							+ " records：  " + records);
					List<AccPackageDTO> cardsInfo = new ArrayList<AccPackageDTO>();
					if (records != null && records.length > 0) {
						// 循环取出每一条记录中的字段，以“^”截取
						for (int i = 0; i < records.length; i++) {
							String record = records[i];
							logger.info(record);
							String[] field = record.split("\\^", -1);

							AccPackageDTO dto = new AccPackageDTO();
							/** 卡号 **/
							dto.setCardNo(field[0]);
							dto.setProductId(field[1]);
							dto.setProductName(field[2]);
							dto.setCardType(PararmeterInfo
									.getCardType(field[3]));
							if (StringUtils.isNotBlank(field[4])) {
								dto.setActiveTime(DateUtil
										.formatStringDate(field[4]));
							}
							if (StringUtils.isNotBlank(field[5])) {
								dto.setValideTime(DateUtil
										.formatStringDate(field[5]));
							}
							dto.setActiveState(field[6]);
							dto.setLossState(field[7]);
							dto.setFreezesState(field[8]);
							dto.setLockState(field[9]);
							dto.setNote(field[10]);
							dto.setEmailInfo(field[11]);
							dto.setCardHolder(field[12]);

							// 循环取出每张卡账户信息，可能一张卡有多个账户信息
							String cardServer = field[13];
							logger.info(cardServer);

							List<CardInfoDTO> list = new ArrayList<CardInfoDTO>();
							String[] cardStateField = cardServer.split("\\@");
							if (cardStateField != null
									&& cardStateField.length > 0) {
								for (int t = 0; t < cardStateField.length; t++) {
									String[] cardInfoField = cardStateField[t]
											.split("\\#");
									CardInfoDTO infoDto = new CardInfoDTO();
									infoDto.setServiceId(cardInfoField[0]);
									infoDto.setServiceName(cardInfoField[1]);
									infoDto.setBalance(Amount
											.getReallyAmount(cardInfoField[2]));
									infoDto.setEpayIn(cardInfoField[3]);
									infoDto.setLimitPayMoney(Amount
											.getReallyAmount(cardInfoField[4]));
									list.add(infoDto);
								}
							}
							CardInfoDTO infoDto = new CardInfoDTO();
							infoDto.setCardInfoDTO(list);
							dto.setCardInfoDTO(infoDto);
							cardsInfo.add(dto);
						}						
						accPackageDTO.setAccPackageDTOs(cardsInfo);
					}
				}
			}
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return accPackageDTO;
	}
	
	/**
	 * 卡片信息查询
	 * 
	 * @param accPackageDTO
	 * @return
	 * @author yaoxin
	 * @throws BizServiceException
	 */
	public AccPackageDTO queryCard(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############卡片信息查询############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			//交易编号
			obj.setTxnType(accPackageDTO.getTxnCode());
			//渠道号
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			//卡号
			obj.setCardNo(accPackageDTO.getCardNo());	
			//包号
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			//服务名
			obj.setServiceName(HSTConstants.SWITCH);


			obj =send(managedAsyn2SynClient,obj);
			String rspCode = obj.getRespCode();
			accPackageDTO.setRespCode(rspCode);
			logger.debug("respCode：" + rspCode);
			//成功返回后对卡片信息进行解析
			if ("00".equals(rspCode)) {
				String resv2=obj.getResv2().trim();
				//CVV2
				accPackageDTO.setCvv2(obj.getCvv2());
				//卡密码
				accPackageDTO.setPassword(obj.getPinQuiry());
				//有效期
				accPackageDTO.setValideTime(resv2.substring(0, 8));
				//激活
				accPackageDTO.setActiveState(resv2.substring(8, 9));
				//锁定
				accPackageDTO.setLockState(resv2.substring(9, 10));
				//注销
				accPackageDTO.setOff(resv2.substring(10, 11));
				//卡挂失
				accPackageDTO.setLossState(resv2.substring(11, 12));
				//换卡状态
				accPackageDTO.setTrade(resv2.substring(12, 13));
				//卡激活日期
				accPackageDTO.setActiveTime(resv2.substring(13,21));
				//卡交易密码错误次数
				accPackageDTO.setOtherData(resv2.substring(21,22));
				//卡冻结状态
				accPackageDTO.setFreezesState(resv2.substring(22,23));
				//卡回收
				accPackageDTO.setCallBack(resv2.substring(23,24));
				//持卡人编号
				accPackageDTO.setCardHolder(obj.getResv3());
				
				String balance=obj.getBalance();
				
				//账号数量，2位
				accPackageDTO.setAccountNum(balance.substring(0, 2));
				int accnum=Integer.valueOf(balance.substring(0, 2));
				//账号名称
				String[] serviceName=obj.getResv4().split("\\|");
				accPackageDTO.setServiceNames(serviceName);
				//账号ID
				String[] serviceId=new String[accnum];
				//账号总金额
				String[] balances=new String[accnum];
				//冻结金额
				String[] congeal=new String[accnum];
			
				
				
				//准备循环解析账号报文
				balance=balance.substring(2, balance.length());
				int j = 0;
				for (int i = 0; i < balance.length();i+=32) {
					logger.debug("帐户：" + serviceName[j]);
					//账户ID，8位
					serviceId[j]=balance.substring(i,i + 8);
					//账号总余额，12位余额，
					balances[j]=String.valueOf(Long.parseLong(balance.substring(i + 8, i + 20)));
					//冻结金额12位冻结金额
					congeal[j]=String.valueOf(Long.parseLong(balance.substring(i + 20, i + 32)));		
					j++;
				}
				accPackageDTO.setServiceIDs(serviceId);
				accPackageDTO.setBalances(balances);
				accPackageDTO.setCongeal(congeal);
				
				logger.debug("########完成解析:" );
			}		

		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}
	
	/**
	 * 卡片操作  1 挂失 ， 2 解挂，3 锁定，4 解锁，5 冻结 ，6 解冻 
	 * @author yaoxin
	 * @param AccPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO cardOperate(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############卡片操作 1 挂失 ， 2 解挂，3 锁定，4 解锁，5 冻结 ，6 解冻 ############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(accPackageDTO.getTxnCode());
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			//费用
			obj.setTxnAmount(accPackageDTO.getServiceFee());
//			obj.setCardHolderFee(accPackageDTO.getServiceFee());
			//卡号
			obj.setCardNo(accPackageDTO.getCardNo());	
			//包号
			obj.setPackageNo((String.valueOf(UUID.randomUUID().toString())));
			//服务名
			obj.setServiceName(HSTConstants.SWITCH);
			
			//按之前代码改写，不知何用，以防出错
			if (accPackageDTO.getTxnCode().equals("M100")) {
				obj.setResv2(accPackageDTO.getCardNo());
				if (accPackageDTO.getAdjustType().equals("1")) {// 卡激活
					obj.setPinQuiry("1");
				} else {
					obj.setPinQuiry("3");// 卡销毁
				}
			}


			obj =send(managedAsyn2SynClient,obj);
			
			String respCode = obj.getRespCode();
			logger.debug("##############respCode：" + respCode);
			accPackageDTO.setRespCode(respCode);

		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}
	
	/**
	 *  卡片赎回
	 * @author yaoxin
	 * @param AccPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO redemptionCard(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############卡片赎回 ############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(accPackageDTO.getTxnCode());
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			obj.setPinQuiry(accPackageDTO.getCardType());
			//费用
			obj.setTxnAmount(accPackageDTO.getServiceFee());
			//卡号
			obj.setCardNo(accPackageDTO.getCardNo());
			//之前代码在Resv2中设置卡号
			obj.setResv2(accPackageDTO.getCardNo());
			//包号
			obj.setPackageNo((String.valueOf(UUID.randomUUID().toString())));
			//服务名
			obj.setServiceName(HSTConstants.SWITCH);


			obj =send(managedAsyn2SynClient,obj);
			
			String respCode = obj.getRespCode();
			logger.debug("##############respCode：" + respCode);
			accPackageDTO.setRespCode(respCode);

		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}
	
	/**
	 *  卡片激活
	 * @author yaoxin
	 * @param AccPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO OprateOrigCard(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############卡片激活 ############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(accPackageDTO.getTxnCode());
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			obj.setPinQuiry(accPackageDTO.getActiveState());
			//服务费
			obj.setTxnAmount(accPackageDTO.getServiceFee());
			//卡号
			obj.setCardNo(accPackageDTO.getCardNo());
			//之前代码在Resv2中设置卡号
			obj.setResv2(accPackageDTO.getCardNo());
			//包号
			obj.setPackageNo((String.valueOf(UUID.randomUUID().toString())));
			//服务名
			obj.setServiceName(HSTConstants.SWITCH);


			obj =send(managedAsyn2SynClient,obj);
			
			String respCode = obj.getRespCode();
			logger.debug("##############respCode：" + respCode);
			accPackageDTO.setRespCode(respCode);

		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}
	
	/**
	 *  查询卡的总金额，可用金额，冻结金额 *
	 * @author yaoxin
	 * @param commonDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO queryCardBalance(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############查询卡的总金额，可用金额，冻结金额  ############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			
		/*	map.put(CFunctionConstant.TXN_TYPE, "S000");
			map.put(CFunctionConstant.CHANNEL, Const.JAVA2C_CHANNEL);
			map.put(CFunctionConstant.CARD_NO, dto.getCardNo());
			OperationResult or = java2C.sendTpService("Switch", map,Const.JAVA2C_NORMAL, false);*/
			
			obj.setTxnType(accPackageDTO.getTxnCode());
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			//费用
			obj.setTxnAmount(accPackageDTO.getServiceFee());
			//卡号
			obj.setCardNo(accPackageDTO.getCardNo());	
			//包号
			obj.setPackageNo((String.valueOf(UUID.randomUUID().toString())));
			//服务名
			obj.setServiceName(HSTConstants.SWITCH);
			
			//按之前代码改写，不知何用，以防出错
			if (accPackageDTO.getTxnCode().equals("M100")) {
				obj.setResv2(accPackageDTO.getCardNo());
				if (accPackageDTO.getAdjustType().equals("1")) {// 卡激活
					obj.setPinQuiry("1");
				} else {
					obj.setPinQuiry("3");// 卡销毁
				}
			}


			obj =send(managedAsyn2SynClient,obj);
			
			String respCode = obj.getRespCode();
			logger.debug("##############respCode：" + respCode);
			accPackageDTO.setRespCode(respCode);

		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}
	
	

	/**
	 * 卡片信息设置
	 * 
	 * @return
	 */
	public AccPackageDTO cardInfoSetUp(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############卡 片 信 息 设 置############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType("G010");
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.SWITCH);
			obj.setCardNo(accPackageDTO.getCardNo());
			//logger.info("Java2C对象中卡号："+obj.getCardNo());
			obj.setSwtBatchNo(accPackageDTO.getLossState());
			//logger.info("Java2C对象中挂失状态："+obj.getSwtBatchNo());
			obj.setRecFlowNo(accPackageDTO.getNote());
			//logger.info("Java2C是否开通短信："+obj.getRecFlowNo());
			obj.setOriSwtbatchNo(accPackageDTO.getEmailInfo());
			//logger.info("Java2C是否开通邮件："+obj.getOriSwtbatchNo());
			/** 网上支付限额 **/
			if (StringUtils.isNotBlank(accPackageDTO.getLimitPayAmount())) {
				obj.setOriRecBatchNo(accPackageDTO.getLimitPayAmount());
				logger.info("Java2C对象中账号支付限额："+obj.getOriRecBatchNo());
			}
			/** 账户id **/
			if(accPackageDTO.getServiceIDs()!=null){
				if (StringUtils.isNotBlank(accPackageDTO.getServiceIDs()[0])) {
					obj.setIssChannel(accPackageDTO.getServiceIDs()[0]);
					logger.info("Java2C对象账号："+obj.getIssChannel());
				}
			}
			/** 网上支付限额状态 **/
			if (StringUtils.isNotBlank(accPackageDTO.getEpayIn())) {
				obj.setOriCardHolderFee(accPackageDTO.getEpayIn());
				logger.info("Java2C网上支付状态："+obj.getOriCardHolderFee());
			}


			obj =send(managedAsyn2SynClient,obj);

			String respCode = obj.getRespCode();
			logger.info("respCode：" + respCode);
			if ("00".equals(respCode)) {
				logger.debug("卡片信息设置修改成功！");
			}

			accPackageDTO.setRespCode(respCode);

		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}

	/**
	 * 密码重置
	 * @param AccPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO reSetCardPayPassWord(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############支 付 密 码 重 置############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType("S590");
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.SWITCH);
			obj.setCardNo(accPackageDTO.getCardNo());
			//obj.setPinQuiryNew(accPackageDTO.getNewPin());
			obj.setResv1(accPackageDTO.getNewPin());
			//修改密码用
			if(accPackageDTO.getPassword()!=null&&!accPackageDTO.getPassword().equals("")){
				//obj.setPinQuiry(accPackageDTO.getPassword());
				obj.setResv2(accPackageDTO.getPassword());
			}
			obj.setTxnAmount(accPackageDTO.getServiceFee());


			obj =send(managedAsyn2SynClient,obj);

			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			if ("00".equals(respCode)) {
				logger.debug("密码重置成功！");
			}

			accPackageDTO.setRespCode(respCode);

		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}

	/**
	 * 密码修改
	 * 
	 * @param accPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO modifyCardPassWord(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############密 码 修 改############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType("G020");
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.SWITCH);
			obj.setCardNo(accPackageDTO.getCardNo());
			String password = java2CCommonService.encPwdJNKY(accPackageDTO.getCardNo(), accPackageDTO.getPassword(), HSTConstants.SYS_ACC);
			obj.setPinTxn(password);
			String newPassword = java2CCommonService.encPwdJNKY(accPackageDTO.getCardNo(), accPackageDTO.getNewPin(), HSTConstants.SYS_ACC);
			obj.setPinTxnNew(newPassword);


			obj =send(managedAsyn2SynClient,obj);

			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			if ("00".equals(respCode)) {
				logger.debug("密码修改成功！");
			}

			accPackageDTO.setRespCode(respCode);

		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}
	
	/**
	 * 卡片延期
	 * 
	 * @param accPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO cardDelay(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############卡片延期############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(accPackageDTO.getTxnCode());
			//渠道
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			//卡号
			obj.setCardNo(accPackageDTO.getCardNo());
			//延期月份
			obj.setPinQuiryNew(accPackageDTO.getMonth());
			//费用
			obj.setTxnAmount(accPackageDTO.getServiceFee());
			
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.SWITCH);


			obj =send(managedAsyn2SynClient,obj);

			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			accPackageDTO.setRespCode(respCode);

		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}
	
	/**
	 * 单张卡充值
	 * 
	 * @param accPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO singleCardRecharge(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############单张卡充值############");
		try {
					
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(accPackageDTO.getTxnCode());
			//渠道
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			obj.setIssChannel(accPackageDTO.getChannel());
			//卡号
			obj.setCardNo(accPackageDTO.getCardNo());
			//金额
			obj.setTxnAmount(accPackageDTO.getCreditAmonts());
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.SWITCH);
			obj.setAccType(accPackageDTO.getAccType());
			obj.setCardHolderFee(accPackageDTO.getServiceFee());
			/*上汽门户用*/
			if(accPackageDTO.getTxnNo()!=null&&accPackageDTO.getOrderNo()!=null){
				obj.setResv3(accPackageDTO.getTxnNo());
				obj.setResv4(accPackageDTO.getOrderNo());
			}
			obj =send(managedAsyn2SynClient,obj);
			
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			accPackageDTO.setRespCode(respCode);

		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}
	
	/**
	 * 批量查询持卡人信息  返回卡片信息
	 * @param TxnPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO insertChangeCardOrderOrigCard(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############批量查询持卡人信息  M040############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			
			// 组装报文
			obj.setTxnType(accPackageDTO.getTxnCode());
			
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.CARDHOLDER_BAT_INQ);
			obj.setResv2(accPackageDTO.getCardNo());
			obj.setSwtTxnDate(accPackageDTO.getBegin_row());
			obj.setSwtTxnTime(accPackageDTO.getEnd_row());
			
			
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
			accPackageDTO.setRespCode(obj.getRespCode());
			accPackageDTO.setOtherData(obj.getOtherdata());
			
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return accPackageDTO;
	}
	
	/**
	 * 批量查询持卡人信息  返回卡片信息
	 * @param TxnPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO queryCardByCardHolder(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############批量查询持卡人信息 S040############");
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			
			// 组装报文
			obj.setTxnType(accPackageDTO.getTxnCode());
			
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.CARDHOLDER_BAT_INQ);
			obj.setResv2(accPackageDTO.getCardNo());
			obj.setSwtTxnDate(accPackageDTO.getBegin_row());
			obj.setSwtTxnTime(accPackageDTO.getEnd_row());
			
			
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
			accPackageDTO.setRespCode(obj.getRespCode());
			if("00".equals(respCode)){
			accPackageDTO.setOtherData(obj.getOtherdata());
			accPackageDTO.setTotalRow(obj.getRecTxnDate());
			accPackageDTO.setRealRow(obj.getRecTxnTime());
			}
			
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return accPackageDTO;
	}
	
	
	/**
	 * 卡安全信息查询
	 * 
	 * @param accPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO cardSeuriyQuery(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############卡安全信息查询############");
		try {
					
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(accPackageDTO.getTxnCode());
			//渠道
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			//卡号
			obj.setCardNo(accPackageDTO.getCardNo());
			obj.setCvv2(accPackageDTO.getCvv2());
			obj.setPinTxn(accPackageDTO.getPassword());
			obj.setAccType(accPackageDTO.getAccType());

			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.SWITCH);


			obj =send(managedAsyn2SynClient,obj);
			
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			accPackageDTO.setRespCode(respCode);
			accPackageDTO.setWithoutPinAmount(obj.getPinTxn());
			accPackageDTO.setCardSeuriyStr(obj.getResv2());
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}
	
	/**
	 * 卡安全信息设置
	 * 
	 * @param accPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO setCardSecurityInfo(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############卡安全信息设置############");
		try {
					
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			obj.setTxnType(accPackageDTO.getTxnCode());
			//渠道
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			//卡号
			obj.setCardNo(accPackageDTO.getCardNo());

			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.SWITCH);
			
			obj.setPinTxn(java2CCommonService.encPwdJNKY(accPackageDTO.getCardNo(),accPackageDTO.getPassword(),HSTConstants.SYS_ACC));
			
			obj.setCvv2(accPackageDTO.getCvv2());
			//帐户号
			obj.setAccType(accPackageDTO.getAccType());
			//服务费
			obj.setTxnAmount(accPackageDTO.getServiceFee());
			obj.setResv2(accPackageDTO.getCardSeuriyStr());


			obj =send(managedAsyn2SynClient,obj);
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			accPackageDTO.setRespCode(respCode);
			
			if ("00".equals(respCode)) {
				accPackageDTO.setCardSeuriyStr(obj.getResv2());
				accPackageDTO.setWithoutPinAmount(obj.getPinTxn());
			}

		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}

	/**
	 * 卡安全信息设置
	 * 
	 * @param accPackageDTO
	 * @return
	 * 
	 *         返回信息:
	 * 
	 *         RespCode : 响应码
	 * 
	 *         OtherData :
	 *         卡号^卡状态^产品id^产品名称^持卡人id^持卡人名^有效期^卡内账户总余额（包括冻结金额）|
	 * 
	 * @throws BizServiceException
	 */
	public AccPackageDTO setCardInfoInquery(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############卡信息查询 SWITCH M400############");
		
		
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			
			// 组装报文
			obj.setServiceName(HSTConstants.SWITCH);
			obj.setTxnType("M400");
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			obj.setCardNo(accPackageDTO.getCardNo());
			obj.setTrack2(accPackageDTO.getCvv());
			
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));

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
			accPackageDTO.setRespCode(obj.getRespCode());
			accPackageDTO.setOtherData(obj.getResv2());
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return accPackageDTO;
	}

	/**
	 * 单卡销毁
	 * 
	 * @param accPackageDTO
	 *            使用cardState作为销毁状态标识
	 * @return
	 * 
	 *         返回信息:
	 * 
	 *         RespCode 响应码
	 * 
	 *         ServiceFee 卡内账户总余额
	 * 
	 * @throws BizServiceException
	 */
	public AccPackageDTO oneCardDestroy(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############单卡销毁 SWITCH M410############");

		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();

			// 组装报文
			obj.setServiceName(HSTConstants.SWITCH);
			obj.setTxnType("M410");
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			obj.setCardNo(accPackageDTO.getCardNo());
			obj.setPinQuiry(accPackageDTO.getCardState());
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setCardHolderFee(accPackageDTO.getServiceFee());
			obj.setResv2(accPackageDTO.getCallBack());
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
			accPackageDTO.setRespCode(obj.getRespCode());
			accPackageDTO.setServiceFee(obj.getTxnAmount());
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return accPackageDTO;
	}
	
	/**
	 * 发送制卡文件通知报文
	 * 
	 * @param accPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO sendMakeCard(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############制卡文件通知报文############");
		try {
					
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			//
			obj.setMsgHead("|"+HSTProperties.getString("SERVICE_NM"));
			// 交易类型
			obj.setTxnType(accPackageDTO.getTxnCode());
			obj.setServiceName(HSTConstants.MAKE_CARD);
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			// 中心交易日期
			obj.setSwtTxnDate(accPackageDTO.getTxnDate());
			// 中心交易时间
			obj.setSwtTxnTime(accPackageDTO.getTxnTime());
			// 中心清算日期
			obj.setSwtSettleDate(accPackageDTO.getSettleDate());
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			obj.setFilePath(accPackageDTO.getFilePath().trim());
			obj.setResv1(accPackageDTO.getCardSeuriyStr());

			obj=send(managedAsyn2SynClient,obj);
			
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			accPackageDTO.setRespCode(respCode);
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}
	
	/**
	 * 发送制卡文件下载报文
	 * 
	 * @param accPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO sendMakeCardMessage(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############发送制卡文件下载报文############");
		try {
					
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			// 交易类型
			obj.setTxnType(accPackageDTO.getTxnCode());
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.CARD_QUERY);
			
			// 中心交易日期
			obj.setSwtTxnDate(accPackageDTO.getTxnDate());
			// 中心交易时间
			obj.setSwtTxnTime(accPackageDTO.getTxnTime());
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			obj.setReferenceNo(accPackageDTO.getBatchNo());

			obj =send(managedAsyn2SynClient,obj);
			
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			accPackageDTO.setRespCode(obj.getRespCode());
			accPackageDTO.setMakeCardState(obj.getAuthCode());
			accPackageDTO.setFilePath(obj.getCardNo());
			
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}
	
	
	/**
	 * 充值通知
	 * 
	 * @param accPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO sendRechargeMessage(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############充值通知############");
		try {
					
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			// 交易类型
			obj.setTxnType(accPackageDTO.getTxnCode());
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.RECHARGE);
			
			// 中心交易日期
			obj.setSwtTxnDate(accPackageDTO.getTxnDate());
			// 中心交易时间
			obj.setSwtTxnTime(accPackageDTO.getTxnTime());
			
			obj.setPinQuiry(accPackageDTO.getBatchNo());
			//批次信息    批次类型|总比数+'|'+总金额+'|'+剩余笔数+'|'+剩余金额
			obj.setResv1(accPackageDTO.getBatchFileInfo());
			obj.setCvv2(accPackageDTO.getCvv2());
			obj =send(managedAsyn2SynClient,obj);
			
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			accPackageDTO.setRespCode(respCode);
			
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}
	
	/**
	 * 卡激活（文件）
	 * 
	 * @param accPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO sendActiveMessage(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############卡片激活############");
		try {
					
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			
			// 交易类型
			obj.setTxnType(accPackageDTO.getTxnCode());
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.ACTIVE);

			obj.setSwtTxnTime(accPackageDTO.getTxnTime());
			obj.setSwtTxnDate(accPackageDTO.getTxnDate());
			obj.setFilePath(accPackageDTO.getFilePath());
		

			obj =send(managedAsyn2SynClient,obj);
			
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			accPackageDTO.setRespCode(respCode);
			
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}
	
	
	/**
	 * 卡充值（文件）
	 * 
	 * @param accPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO vNoActRecharge(AccPackageDTO accPackageDTO)
			throws BizServiceException {
		logger.info("############卡充值（文件）############");
		try {
					
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			
			// 交易类型
			obj.setTxnType(accPackageDTO.getTxnCode());
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			obj.setServiceName(HSTConstants.RECHARGE_NOACT);

			obj.setSwtTxnTime(accPackageDTO.getTxnTime());
			obj.setSwtTxnDate(accPackageDTO.getTxnDate());
			obj.setFilePath(accPackageDTO.getFilePath());
			obj.setPinQuiry(accPackageDTO.getBatchNo());
			obj.setResv1(accPackageDTO.getBatchFileInfo());


			obj= send(managedAsyn2SynClient,obj);
			
			String respCode = obj.getRespCode();
			logger.debug("respCode：" + respCode);
			accPackageDTO.setRespCode(obj.getRespCode());
			
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}
	
	private BizMessageObj send(ManagedAsyn2SynClient managedAsyn2SynClient, Object obj) throws BizServiceException {
		CommMessage sendMsg = new CommMessage();
		sendMsg.setMessageObject(obj);
		// 发送报文 JAVA-->C
		logger.info("begin java------->c");
		try {
			
			sendMsg = managedAsyn2SynClient.sendMessage(sendMsg);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("C后台未启动！！");
			throw new BizServiceException("C后台未启动！！");
		}
		// 解析返回值C-->JAVA
		logger.info("begin c------->java");
		if (sendMsg != null) {
			return (BizMessageObj) sendMsg.getMessageObject();
		} else {
			return null;
		}
		
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

	public ManagedAsynClient getManagedAsynClient() {
		return managedAsynClient;
	}

	public void setManagedAsynClient(ManagedAsynClient managedAsynClient) {
		this.managedAsynClient = managedAsynClient;
	}
	
	/**
	 * 批量充值 
	 * @author yaoxin
	 * @param AccPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public AccPackageDTO batchRecharge(String data,String createUser)
			throws BizServiceException {
		logger.info("############批量充值 ############");
		AccPackageDTO accPackageDTO=new AccPackageDTO();
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			//服务名
			obj.setServiceName("RechargeBat");
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			//TxnCode==M320
			obj.setTxnType("M350");
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
			obj.setOtherdata(data);
			obj.setResv2(createUser);
			

			obj =send(managedAsyn2SynClient,obj);
			
			String respCode = obj.getRespCode();
			logger.debug("##############respCode：" + respCode);
			
			accPackageDTO.setRespCode(respCode);
			if(!respCode.equals("00")){
				accPackageDTO.setCallBack(obj.getResv1());
			} 

		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}

	
	/**
	 * 批量激活 
	 * @author hejian
	 * @param AccPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
//	public AccPackageDTO batchActivate(String data)
//			throws BizServiceException {
//		logger.info("############批量激活 ############");
//		AccPackageDTO accPackageDTO=new AccPackageDTO();
//		try {
//			// 组装报文
//			BizMessageObj obj = new BizMessageObj();
//			//服务名
//			obj.setServiceName("RechargeBat");
//			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
//			//TxnCode==M320
//			obj.setTxnType("M360");
//			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
//			obj.setOtherdata(data);
//			obj =send(managedAsyn2SynClient,obj);
//			
//			String respCode = obj.getRespCode();
//			logger.debug("##############respCode：" + respCode);
//			
//			accPackageDTO.setRespCode(respCode);
//			if(!respCode.equals("00")){
//				accPackageDTO.setCallBack(obj.getResv1());
//			} 
////			accPackageDTO.setBatchNo(obj.getSwtBatchNo());
//		} catch (BizServiceException e) {
//			logger.error(e.getMessage());
//			throw e;
//		}
//
//		return accPackageDTO;
//	}
	
	public AccPackageDTO batchActivate()
			throws BizServiceException {
		logger.info("############批量激活 ############");
		AccPackageDTO accPackageDTO=new AccPackageDTO();
		try {
			// 组装报文
			BizMessageObj obj = new BizMessageObj();
			//服务名
			obj.setServiceName("RechargeBat");
			obj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
			//TxnCode==M320
			obj.setTxnType("M360");
			obj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
//			obj.setOtherdata(data);
			obj =send(managedAsyn2SynClient,obj);
			
			String respCode = obj.getRespCode();
			logger.debug("##############respCode：" + respCode);
			
			accPackageDTO.setRespCode(respCode);
//			if(!respCode.equals("00")){
//				accPackageDTO.setCallBack(obj.getResv1());
//			} 
//			accPackageDTO.setBatchNo(obj.getSwtBatchNo());
		} catch (BizServiceException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return accPackageDTO;
	}
}
