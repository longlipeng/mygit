package com.huateng.hstserver.gatewayService;

import hsmj.HsmApp;
import hsmj.HsmConst;
import hsmj.HsmSession;
import hsmj.HsmStruct;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.huateng.hstserver.cached.BizDataCached;
import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.biz.BizMessageObj;
import com.huateng.hstserver.communicate.mina.comm.server.client.ManagedAsyn2SynClient;
import com.huateng.hstserver.config.HSTProperties;
import com.huateng.hstserver.constants.HSTConstants;
import com.huateng.hstserver.exception.BizServiceException;
import com.huateng.hstserver.frameworkUtil.Amount;
import com.huateng.hstserver.frameworkUtil.Const;
import com.huateng.hstserver.frameworkUtil.DateUtil;
import com.huateng.hstserver.frameworkUtil.PararmeterInfo;
import com.huateng.hstserver.model.CardInfoDTO;
import com.huateng.hstserver.model.CommonDTO;
import com.huateng.secure.util.OutputSecResult;
import com.huateng.secure.util.exception.SecException;

/**
 * 收单网关/持卡人网站 /商户网站,共用类
 * 
 * @author fengfeng.shi
 * 
 */
public class Java2CCommonServiceImpl {

	private static Logger logger = Logger.getLogger(Java2CCommonServiceImpl.class);
	
	String respCode = "";
	String allRecord = "";
	ManagedAsyn2SynClient managedAsyn2SynClient;

	public ManagedAsyn2SynClient getManagedAsyn2SynClient() {
		return managedAsyn2SynClient;
	}

	public void setManagedAsyn2SynClient(ManagedAsyn2SynClient managedAsyn2SynClient) {
		this.managedAsyn2SynClient = managedAsyn2SynClient;
	}	


	
	/**
	 * 取回一个索引的区域主密钥
	 * @param pinKeyIndex
	 * @return
	 */
	private byte[] getPinKeyByLMK(String pinKeyIndex){
		//请求结果
		int nRet = 0;

		//初始化配置文件
		String cfgname = HSTProperties.baseDir + "hsm.ini";	
		
		//获取到加密机的链接,如果获取链接失败,则释放分配的链路句柄
		HsmSession hSession = null;
		hSession = new HsmSession(cfgname);
		HsmApp hApp = new HsmApp();
		nRet = hSession.GetLastError();
		if (nRet != 0) {
			hSession.HsmSessionEnd();
			return null;
		}
		
		//初始化到加密机的请求报文
		HsmStruct hsmStruct = null;		
    	HsmStruct.EncDec31_Data EncDec31_Data = null;
    	HsmStruct.EncDec31_Data.EncDec31_DataIN   EncDec31_DataIN = null;
    	HsmStruct.EncDec31_Data.EncDec31_DataOUT  EncDec31_DataOUT = null;    	
    	hsmStruct = new HsmStruct();
		EncDec31_Data = hsmStruct.new EncDec31_Data();
		EncDec31_DataIN = EncDec31_Data.new EncDec31_DataIN(pinKeyIndex.getBytes());
		EncDec31_DataOUT = EncDec31_Data.new EncDec31_DataOUT();
		
		//请求加密机，并获取响应报文
		nRet = hApp.EncDec31_Data(hSession, EncDec31_DataIN, EncDec31_DataOUT);
		//释放到加密机的链路
		hSession.HsmSessionEnd(); 
		
		//如果响应成功报文,则返回LMK加密后的PINKEY
        if (nRet == HsmConst.T_SUCCESS) {
            logger.info("EncData replyCode:" + HsmApp.byte2hex(EncDec31_DataOUT.reply_code));
            logger.info("EncData length:" + HsmApp.byte2hex(EncDec31_DataOUT.pk_encrypted_bylmk));
            logger.info("EncData data:" + HsmApp.byte2hex(EncDec31_DataOUT.check_value));
        	return EncDec31_DataOUT.pk_encrypted_bylmk;        				          
        }
        else{
        	return null;
        }
	}

	/**
	 * 取回一个索引的区域主密钥
	 * @param pinKeyEncryptedByLMK
	 * @return
	 */
	private byte[] getPinKeyByZMK(String pinKeyIndex,byte[] pinKeyEncryptedByLMK){
		//请求结果
		int nRet = 0;

		//初始化配置文件
		String cfgname = HSTProperties.baseDir + "hsm.ini";	
		
		//获取到加密机的链接,如果获取链接失败,则释放分配的链路句柄
		HsmSession hSession = null;
		hSession = new HsmSession(cfgname);
		HsmApp hApp = new HsmApp();
		nRet = hSession.GetLastError();
		if (nRet != 0) {
			hSession.HsmSessionEnd();
			return null;
		}
		
		//初始化到加密机的请求报文
		HsmStruct hsmStruct = null;		
    	HsmStruct.EncDec0552_Data EncDec0552_Data=null;
    	HsmStruct.EncDec0552_Data.EncDec0552_DataIN   EncDec0552_DataIN=null;
    	HsmStruct.EncDec0552_Data.EncDec0552_DataOUT  EncDec0552_DataOUT=null;  	
    	hsmStruct = new HsmStruct();
    	EncDec0552_Data = hsmStruct.new EncDec0552_Data();
    	EncDec0552_DataIN = EncDec0552_Data.new EncDec0552_DataIN(pinKeyIndex.getBytes(),pinKeyEncryptedByLMK);
    	EncDec0552_DataOUT = EncDec0552_Data.new EncDec0552_DataOUT();    	
		
		//请求加密机，并获取响应报文
    	nRet = hApp.EncDec0552_Data(hSession, EncDec0552_DataIN, EncDec0552_DataOUT);
		//释放到加密机的链路
		hSession.HsmSessionEnd(); 
		
		//如果响应成功报文,则返回ZMK加密后的PINKEY
        if (nRet == HsmConst.T_SUCCESS) {
            logger.info("EncData replyCode:" + HsmApp.byte2hex(EncDec0552_DataOUT.reply_code));
            logger.info("EncData length:" + HsmApp.byte2hex(EncDec0552_DataOUT.pk_encrypted_byzmk));
        	return EncDec0552_DataOUT.pk_encrypted_byzmk;        				          
        }
        else{
        	return null;
        }
	}
	
	public boolean initialPinKey() throws BizServiceException{
		//加载加密机配置文件
		String cfgname = HSTProperties.baseDir + "hsm.ini";		
		Properties pr = new Properties();		
		try {
			FileInputStream raf;
			raf = new FileInputStream(cfgname);			
			pr.load(raf);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new BizServiceException(SecException.ERR_SEC_CODE_7005 + SecException.ERR_SEC_MSG_7005);
		}		
		//TXN主密钥索引
		String txnZMKIndex = pr.getProperty("TXN_BMK_INDEX");
		//ACC主密钥索引
		String accZMKIndex = pr.getProperty("ACC_BMK_INDEX");
		
		//获取TXN PINKEY
		byte[] txnPinKeyByLMK = getPinKeyByLMK(txnZMKIndex);
		if(txnPinKeyByLMK == null){
			return false;
		}
		byte[] txnPinKeyByZMK = getPinKeyByZMK(txnZMKIndex,txnPinKeyByLMK);
		if(txnPinKeyByZMK == null){
			return false;
		}
		BizDataCached.REQ_PIN_KEY_TXN =  txnPinKeyByZMK;
      
		//获取ACC PINKEY
		byte[] accPinKeyByLMK = getPinKeyByLMK(accZMKIndex);
		if(accPinKeyByLMK == null){
			return false;
		}
		byte[] accPinKeyByZMK = getPinKeyByZMK(txnZMKIndex,accPinKeyByLMK); 
		if(accPinKeyByZMK == null){
			return false;
		}		
		BizDataCached.REQ_PIN_KEY_ACC = accPinKeyByZMK;
	
		return true;
	}
	

	
	
	
	/**
	 * 签到交易
	 * @param commonDTO
	 * @param remoteSys 远端系统(交易系统、账户系统)
	 * @return
	 * @throws BizServiceException
	 */
	public boolean signIn(String remoteSys) throws BizServiceException {
		logger.debug("############ 签  到  交  易############");
		
		CommMessage sendMsg = new CommMessage();		
		BizMessageObj sendBizMessageObj = new BizMessageObj();
		
		//设置报文包号
		sendBizMessageObj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
		
		//根据远端系统，给服务名称赋值
		String serviceNm = "";
		if(remoteSys.equals(HSTConstants.SYS_TXN))//交易系统
			serviceNm = "vChgKeyServiceTxn";
		else if(remoteSys.equals(HSTConstants.SYS_ACC))//账户系统
			serviceNm = "vChgKeyServiceAcc";		
		//设置服务名称	
		sendBizMessageObj.setServiceName(serviceNm);
		
		sendBizMessageObj.setTxnType("8000");
		//设置服务名称
		sendBizMessageObj.setTrack2(HSTProperties.getString("SERVICE_NM"));//网关:vChgKeyServiceAcq  管理平台:vChgKeyServiceJava

		sendMsg.setMessageObject(sendBizMessageObj);
		
		//签到并获取返回结果
		CommMessage rcvMsg = managedAsyn2SynClient.sendMessage(sendMsg);
		BizMessageObj rcvBizMessageObj = (BizMessageObj)rcvMsg.getMessageObject();				
		
		respCode = rcvBizMessageObj.getRespCode();;
		logger.debug("respCode：" + respCode);
		logger.debug("############ 签  到  交  易  结  束############");
		// 解析返回值
		if ("00".equals(respCode)) {
			String reqPinKey = rcvBizMessageObj.getPinQuiry();
			if(remoteSys.equals(HSTConstants.SYS_TXN))//交易系统
				BizDataCached.REQ_PIN_KEY_TXN = reqPinKey.getBytes();
			else if(remoteSys.equals(HSTConstants.SYS_ACC))//账户系统
				BizDataCached.REQ_PIN_KEY_ACC = reqPinKey.getBytes();;	
			return true;
		}else if ("07".equals(respCode)) {
			throw new BizServiceException("交易代码有误");
		} else {
			throw new BizServiceException("错误码:" + respCode);
		}
	}
	
	public String encPwd(String cardNo, String pin, String remoteSys) throws BizServiceException{
		//计算PINBlock
		String pinBlockStr = makePinBlock(cardNo, pin);
		byte[] pinBlockByte = new byte[8];
		HsmApp.Str2Hex(pinBlockStr.getBytes(), pinBlockByte, 16);
		String cfgname = HSTProperties.baseDir + "hsm.ini";		
		Properties pr = new Properties();		
		try {
			FileInputStream raf;
			raf = new FileInputStream(cfgname);			
			pr.load(raf);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new BizServiceException(SecException.ERR_SEC_CODE_7005 + SecException.ERR_SEC_MSG_7005);
		}		
		
		int nRet = 0;
        OutputSecResult outRslt = null;
    	HsmSession hSession = null;
    	HsmStruct hsmStruct = null;
    	HsmStruct.EncDec71_Data EncDec71_Data = null;
    	HsmStruct.EncDec71_Data.EncDec71_DataIN   EncDec71_DataIN = null;
    	HsmStruct.EncDec71_Data.EncDec71_DataOUT  EncDec71_DataOUT = null;
    	try{
        	hSession = new HsmSession(cfgname);
        	HsmApp hApp = new HsmApp();
        	nRet = hSession.GetLastError();
        	if (nRet != 0) {
        		throw new BizServiceException(SecException.ERR_SEC_CODE_7005 + SecException.ERR_SEC_MSG_7005);
        	}
        	hsmStruct = new HsmStruct();
        	EncDec71_Data = hsmStruct.new EncDec71_Data();
			
        	//根据远端系统从Cache获取工作密钥
        	byte[] reqPinKey  = new byte[16];
        	String bmkIndex = ""; 
			if(remoteSys.equals(HSTConstants.SYS_TXN)){//交易系统
				reqPinKey = BizDataCached.REQ_PIN_KEY_TXN;
				bmkIndex = pr.getProperty("TXN_BMK_INDEX");
			}else if(remoteSys.equals(HSTConstants.SYS_ACC)){//账户系统
				reqPinKey = BizDataCached.REQ_PIN_KEY_ACC;
				bmkIndex = pr.getProperty("ACC_BMK_INDEX");
			}
//			byte[] reqPinKeyByte = new byte[16];
			//reqPinKey = "78D3DAF55516BA763B08E8C3B4FF305C";
//			HsmApp.Str2Hex(reqPinKey.getBytes("gbk"), reqPinKeyByte, 32);

        	EncDec71_DataIN = EncDec71_Data.new EncDec71_DataIN(
        							  bmkIndex.getBytes(),//主密钥索引 
        	                          "16".getBytes(),	//工作密钥长度        	                          
        	                          reqPinKey, //工作密钥
        	                          "00000000".getBytes(),//CBC加密的初始向量
        	                          "1".getBytes(),//加/解密标识
        	                          "0".getBytes(),//算法标识
        	                          String.valueOf(pinBlockByte.length).getBytes(),//待处理数据长度
        	                          pinBlockByte);//待处理数据

        	EncDec71_DataOUT = EncDec71_Data.new EncDec71_DataOUT();
        	
        	nRet = hApp.EncDec71_Data(hSession, EncDec71_DataIN, EncDec71_DataOUT);
        	           
    	}catch (Exception e){
    		logger.error(e.getMessage());
    		throw new BizServiceException(SecException.ERR_SEC_CODE_7005 + SecException.ERR_SEC_MSG_7005 + ":" + new String(EncDec71_DataOUT.reply_code));
    		}
    	finally {
        	if(	hSession != null) 
        		hSession.HsmSessionEnd();
        }
    	    	
    	
        if (nRet != HsmConst.T_SUCCESS) {
        	throw new BizServiceException(SecException.ERR_SEC_CODE_7005 + SecException.ERR_SEC_MSG_7005 + ":" +new String(EncDec71_DataOUT.reply_code));			       
        }
        else{
        	 outRslt = new OutputSecResult();
        	 outRslt.setData(EncDec71_DataOUT.data);
             outRslt.setDataLen(EncDec71_DataOUT.data_len); 
             String  pinEncryed = HsmApp.byte2hex(EncDec71_DataOUT.data);
             
             logger.info("EncData data:" + pinEncryed);
             return pinEncryed.replaceAll(" ", "").substring(0,16);
        }
	}
	
	
	/*
	 * 测试江南科友加密机连接
	 */
	public boolean testHSM() throws BizServiceException{
		
		logger.info("测试加密机连接开始。。。。。。。。。。。。。。。。");
		
		//加载加密机配置文件
		String cfgname = "/" + HSTProperties.baseDir + "hsm.ini";		
		Properties pr = new Properties();
		try {
			InputStream input = Java2CCommonServiceImpl.class.getResourceAsStream(cfgname);
			pr.load(input);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new BizServiceException(SecException.ERR_SEC_CODE_7005 + SecException.ERR_SEC_MSG_7005);
		}	
		
		int nRet = 0;
    	HsmSession hSession = null;
    	byte[] SndDataNC = new byte[8]; 
    	byte[] RcvDataNC = new byte[256];
    	try{
    		hSession = new HsmSession(cfgname);
        	nRet = hSession.GetLastError();
    
        	if (nRet != 0) {
        		throw new BizServiceException(SecException.ERR_SEC_CODE_7005 + SecException.ERR_SEC_MSG_7005);
        	}
    		
    		
    		SndDataNC[0] = 0x00;
    		SndDataNC[1] = 0x06;
    		byte[] nc = "0000NC".getBytes();
    		System.arraycopy(nc, 0, SndDataNC, 2, 6);
    		
    		logger.info("SndDataNC:：" + new String(SndDataNC));
    		
    		hSession.SndAndRcvDataJNKY(SndDataNC,8,RcvDataNC);
    		
    		hSession.HsmSessionEnd(); 
    		
    		if(RcvDataNC == null){
    			throw new BizServiceException("加密机请求异常");
    		}
    		String RcvDataNCStr = new String(RcvDataNC);
    		logger.info("加密机返回信息：" + RcvDataNCStr);
    		return true;
    	}catch (Exception e){
    		logger.error(e.getMessage());
    		throw new BizServiceException("加密机测试异常");
    	}
    	finally {
        	if(	hSession != null) 
        		hSession.HsmSessionEnd();
        }	
	}
	
	
    /*
     * 使用江南科友加密机E0指令加密password
     * add by lfr
     */
	public String encPwdJNKY(String cardNo, String pin, String remoteSys) throws BizServiceException{
		//计算PINBlock
		String pinBlockStr = makePinBlock(cardNo, pin);
		pinBlockStr = pinBlockStr.toUpperCase();
		byte[] pinBlockByte = new byte[16];
//		HsmApp.Str2Hex(pinBlockStr.getBytes(), pinBlockByte, 16);
		System.arraycopy(pinBlockStr.getBytes(), 0, pinBlockByte, 0, 16);
		
		String cfgname = HSTProperties.baseDir + "hsm.ini";		
		Properties pr = new Properties();		
		try {
			FileInputStream raf;
			raf = new FileInputStream(cfgname);			
			pr.load(raf);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new BizServiceException(SecException.ERR_SEC_CODE_7005 + SecException.ERR_SEC_MSG_7005);
		}		
		
		int nRet = 0;
        OutputSecResult outRslt = null;
    	HsmSession hSession = null;
    	HsmStruct hsmStruct = null;
    	HsmStruct.EncDec_JNKY_E0_Data EncDec_JNKY_E0_Data = null;
    	HsmStruct.EncDec_JNKY_E0_Data.EncDec_JNKY_E0_DataIN   EncDec_JNKY_E0_DataIN = null;
    	HsmStruct.EncDec_JNKY_E0_Data.EncDec_JNKY_E0_DataOUT  EncDec_JNKY_E0_DataOUT = null;
    	try{
        	hSession = new HsmSession(cfgname);
        	HsmApp hApp = new HsmApp();
        	nRet = hSession.GetLastError();
        	if (nRet != 0) {
        		throw new BizServiceException(SecException.ERR_SEC_CODE_7005 + SecException.ERR_SEC_MSG_7005);
        	}
        	hsmStruct = new HsmStruct();
        	EncDec_JNKY_E0_Data = hsmStruct.new EncDec_JNKY_E0_Data();
			
        	//根据远端系统从Cache获取工作密钥
        	@SuppressWarnings("unused")
			byte[] reqPinKey  = new byte[16];
        	String workKey = ""; 
			if(remoteSys.equals(HSTConstants.SYS_TXN)){//交易系统
				reqPinKey = BizDataCached.REQ_PIN_KEY_TXN;
				workKey = pr.getProperty("TXN_BMK");
			}else if(remoteSys.equals(HSTConstants.SYS_ACC)){//账户系统
				reqPinKey = BizDataCached.REQ_PIN_KEY_ACC;
				workKey = pr.getProperty("ACC_BMK");
			}
			
			//消息头
			String messageHeader = pr.getProperty("MESSAGE_HEADER");
			String pinBlockByteLen = String.valueOf(pinBlockByte.length/2);
			pinBlockByteLen = com.huateng.hstserver.frameworkUtil.StringUtils.fillString(pinBlockByteLen,3);
			EncDec_JNKY_E0_DataIN = EncDec_JNKY_E0_Data.new EncDec_JNKY_E0_DataIN(
					                   messageHeader.getBytes(),//消息头
					                   "E0".getBytes(),//命令代码
					                   "0".getBytes(),//消息块号(0=仅一块)
					                   "0".getBytes(),//密钥模式(0=DES加密)
        	                           "1".getBytes(),//密钥加密模式 (1=ECB)	 
        	                           "0".getBytes(),//密钥类型(0=ZEK)
        	                           ("X"+workKey).getBytes(),//密钥(1A+32H)
        	                           "1".getBytes(),//输入消息类型(1=消息数据为扩展十六进制)
        	                           "1".getBytes(),//输出消息类型(1=消息数据为扩展十六进制)
        	                           "0".getBytes(),//填充模式(0=如果数据块长度为8的整数倍，不需要填充；如果长度不为8的整数倍，填充至长度满8的整数倍
        	                           "0000".getBytes(),//填充字符(0000：第一个字节填充X’00 其他需要填充的字节都填充为X’00)
        	                           "0".getBytes(),//填充类型(0=填充数据的最后一个字节不填充计数位。解密后的数据包含填充数据，由应用摘取实际数据)
        	                           pinBlockByteLen.getBytes(),//消息数据长度(消息类型为扩展十六进制，则为随后域长度的一半)
           	                           pinBlockByte);//待处理数据

			EncDec_JNKY_E0_DataOUT = EncDec_JNKY_E0_Data.new EncDec_JNKY_E0_DataOUT();
        	
        	nRet = hApp.EncDec_JNKY_E0_Data(hSession, EncDec_JNKY_E0_DataIN, EncDec_JNKY_E0_DataOUT);
        	           
    	}catch (Exception e){
    		logger.error(e.getMessage());
    		throw new BizServiceException(SecException.ERR_SEC_CODE_7005 + SecException.ERR_SEC_MSG_7005 + ":" + new String(EncDec_JNKY_E0_DataOUT.reply_code));
    		}
    	finally {
        	if(	hSession != null) 
        		hSession.HsmSessionEnd();
        }
    	    	
        if (nRet != HsmConst.T_SUCCESS) {
        	logger.info("#####################reply_code:"+new String(EncDec_JNKY_E0_DataOUT.reply_code));
        	throw new BizServiceException(SecException.ERR_SEC_CODE_7005 + SecException.ERR_SEC_MSG_7005 + ":" +new String(EncDec_JNKY_E0_DataOUT.reply_code));			       
        }
        else{
        	 outRslt = new OutputSecResult();
        	 outRslt.setData(EncDec_JNKY_E0_DataOUT.data);
             outRslt.setDataLen(EncDec_JNKY_E0_DataOUT.data_length); 
             String  pinEncryed = new String(EncDec_JNKY_E0_DataOUT.data);
             
             logger.info("EncData data:" + pinEncryed);
             return pinEncryed.replaceAll(" ", "").substring(0,16);
        }
	}
	
	// 卡片批量查询
	public CommonDTO cardBatchQuery(CommonDTO commonDTO) throws BizServiceException {
		logger.debug("############卡 片 批 量 查 询############");		
		CommMessage sendMsg = new CommMessage();
		
		BizMessageObj sendBizMessageObj = new BizMessageObj();
		//TODO:增加包号生成器
		sendBizMessageObj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));

		sendBizMessageObj.setServiceName("vCardBatInq");
		sendBizMessageObj.setTxnType("G000");
		sendBizMessageObj.setChannel(HSTProperties.getString("ACC_CHNL_ID"));
		sendBizMessageObj.setResv1(commonDTO.getCardNo());
		if (StringUtils.isNotBlank(commonDTO.getCardNo()) && StringUtils.isNotBlank(commonDTO.getPassword())) {
			String password = encPwdJNKY(commonDTO.getCardNo(), commonDTO.getPassword(),HSTConstants.SYS_ACC);
			sendBizMessageObj.setPinTxn(password);
		}
		if (StringUtils.isNotBlank(commonDTO.getCvv2())) {
			sendBizMessageObj.setCvv2(commonDTO.getCvv2());
		}		

		sendMsg.setMessageObject(sendBizMessageObj);
		 
		CommMessage rcvMsg = managedAsyn2SynClient.sendMessage(sendMsg);
		BizMessageObj rcvBizMessageObj = (BizMessageObj)rcvMsg.getMessageObject();
		
		CommonDTO comDTO = new CommonDTO();
		respCode = rcvBizMessageObj.getRespCode();;
		logger.debug("respCode：" + respCode);

		// 解析返回值C-->JAVA
		if ("00".equals(respCode)) {
			allRecord = rcvBizMessageObj.getOtherdata();
			logger.info("allRecord:" + allRecord);

			if (!"".equals(allRecord)) {
				// 解析所有的记录 ，以“|”截取
				String[] records = allRecord.split("\\|");
				logger.info("recordsLength: " + records.length + " records：  " + records);
				List<CommonDTO> cardsInfo = new ArrayList<CommonDTO>();

				if (records != null && records.length > 0) {
					// 循环取出每一条记录中的字段，以“^”截取
					for (int i = 0; i < records.length; i++) {
						String record = records[i];
						logger.info(record);
						String[] field = record.split("\\^", -1);
						CommonDTO dto = new CommonDTO();

						/** 卡号 **/
						dto.setCardNo(field[0]);
						dto.setProductId(field[1]);
						dto.setProductName(field[2]);
						dto.setCardType(PararmeterInfo.getCardType(field[3]));
						if (StringUtils.isNotBlank(field[4])) {
							dto.setActiveTime(DateUtil.formatStringDate(field[4]));
						}
						if (StringUtils.isNotBlank(field[5])) {
							dto.setValideTime(DateUtil.formatStringDate(field[5]));
						}
						dto.setActiveState(field[6]);
						dto.setLossState(field[7]);
						dto.setFreezesState(field[8]);
						dto.setLockState(field[9]);
						dto.setSmsState(field[10]);
						dto.setEmailInfo(field[11]);
						dto.setCardHolder(field[12]);

						// 循环取出每张卡账户信息，可能一张卡有多个账户信息
						String cardServer = field[13];
						logger.info(cardServer);

						List<CardInfoDTO> list = new ArrayList<CardInfoDTO>();
						String[] cardStateField = cardServer.split("\\@");
						if (cardStateField != null && cardStateField.length > 0) {
							for (int t = 0; t < cardStateField.length; t++) {
								String[] cardInfoField = cardStateField[t].split("\\#");
								CardInfoDTO infoDto = new CardInfoDTO();
								infoDto.setServiceId(cardInfoField[0]);
								infoDto.setServiceName(cardInfoField[1]);
								infoDto.setBalance(Amount.getReallyAmount(cardInfoField[2]));
								infoDto.setEpayIn(cardInfoField[3]);
								infoDto.setLimitPayMoney(Amount.getReallyAmount(cardInfoField[4]));
								list.add(infoDto);
							}
						}

						CardInfoDTO infoDto = new CardInfoDTO();
						infoDto.setCardInfoDTO(list);
						dto.setInfoState(infoDto);

						cardsInfo.add(dto);
					}
					comDTO.setRespCode(Const.RESPONSE_SUCCESS_CODE);
					comDTO.setCardsInfo(cardsInfo);
				}
			}
		} else if ("05".equals(respCode)) {
			comDTO.setRespCode("05");
			throw new BizServiceException("行数错误");
		} else if ("07".equals(respCode)) {
			comDTO.setRespCode("07");
			throw new BizServiceException("交易代码有误");
		} else {
			comDTO.setRespCode(respCode);
		}		
		return comDTO;
	}
	
	
	/**
	 * 生成卡PIN BLOCK
	 * @param cardNo
	 * @param pin
	 * @return
	 * @throws BizServiceException
	 */
	public static String makePinBlock(String cardNo, String pin) throws BizServiceException{
		try{
			if (pin == null || pin.trim().length() != 6){
				throw new BizServiceException("card pin [" + pin + "] must be the length 6");
			}
			
			String tmpPin = "06" + pin + "FFFFFFFF";
			String tmpCad = "0000" + cardNo.substring(cardNo.length() - 13, cardNo.length() - 1);
			
			String tmpXor = com.huateng.hstserver.frameworkUtil.StringUtils.stringXor(tmpPin, tmpCad);
//			logger.info("xor result :" + tmpXor);
			return tmpXor;
		}catch(BizServiceException e){
			throw e;
		}catch(Exception e){
			logger.error(e.getMessage());
			String err = "调用makePinBlock函数失败!" + e.toString();
			logger.error(err);
			throw new BizServiceException(err);
		}
	}	
	


	// public static void main(String[] args) throws Exception {
	// Java2CCommonServiceImpl java = new Java2CCommonServiceImpl();
	// logger.info(java.makePinBlock("3200029101000000642",
	// "432224"));
	// logger.info(new String("16".getBytes()));
	// }
}
