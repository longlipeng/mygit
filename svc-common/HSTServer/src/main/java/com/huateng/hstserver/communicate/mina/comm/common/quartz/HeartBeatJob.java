/**
 * Classname HeartBeatJob.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		dell		2012-9-29
 * =============================================================================
 */

package com.huateng.hstserver.communicate.mina.comm.common.quartz;

import org.apache.log4j.Logger;

import com.huateng.hstserver.config.HSTProperties;
import com.huateng.hstserver.constants.HSTConstants;
import com.huateng.hstserver.exception.BizServiceException;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.gatewayService.Java2STLBusinessServiceImpl;
import com.huateng.hstserver.gatewayService.Java2TXNBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
import com.huateng.hstserver.model.StlPackageDTO;
import com.huateng.hstserver.model.TxnPackageDTO;

/**
 * @author dell
 *
 */
public class HeartBeatJob {
	private Logger logger = Logger.getLogger(HeartBeatJob.class);
	private static HeartBeatJob instance = new HeartBeatJob();
	
	public static HeartBeatJob getInstance() {
		return instance;
	}
	
	/**账户系统服务*/
	private Java2ACCBusinessServiceImpl java2ACCBusinessService;
	/**交易系统服务*/
	private Java2TXNBusinessServiceImpl java2TXNBusinessService;
	/**账务系统服务*/
	private Java2STLBusinessServiceImpl java2STLBusinessService;

	public Java2ACCBusinessServiceImpl getJava2ACCBusinessService() {
		return java2ACCBusinessService;
	}


	public void setJava2ACCBusinessService(
			Java2ACCBusinessServiceImpl java2accBusinessService) {
		java2ACCBusinessService = java2accBusinessService;
	}


	public Java2TXNBusinessServiceImpl getJava2TXNBusinessService() {
		return java2TXNBusinessService;
	}


	public void setJava2TXNBusinessService(
			Java2TXNBusinessServiceImpl java2txnBusinessService) {
		java2TXNBusinessService = java2txnBusinessService;
	}


	public Java2STLBusinessServiceImpl getJava2STLBusinessService() {
		return java2STLBusinessService;
	}


	public void setJava2STLBusinessService(
			Java2STLBusinessServiceImpl java2stlBusinessService) {
		java2STLBusinessService = java2stlBusinessService;
	}


	public void procHeartBeatTask() {		
		//发送心跳到账户系统（用卡片信息查询接口报文作为心跳报文）
		if(HSTProperties.getString("ACC_HEARTBEAT_IN")==null || HSTProperties.getString("ACC_HEARTBEAT_IN").equals(HSTConstants.HEARTBEAT_IN_Y)){
			AccPackageDTO accPackageDTO = new AccPackageDTO();
			accPackageDTO.setTxnCode("M240");		
			try {
				java2ACCBusinessService.queryCard(accPackageDTO);
			} catch (BizServiceException e) {
				logger.error(e.getMessage());
			}
		}
		
		//发送心跳到交易系统（用交易查询接口报文作为心跳报文）		
		if(HSTProperties.getString("TXN_HEARTBEAT_IN")==null || HSTProperties.getString("TXN_HEARTBEAT_IN").equals(HSTConstants.HEARTBEAT_IN_Y)){
			TxnPackageDTO txnPackageDTO = new TxnPackageDTO();
			try {
				java2TXNBusinessService.queryTransation(txnPackageDTO);
			} catch (BizServiceException e) {
				logger.error(e.getMessage());
			}
		}
		
		//发送心跳到账务系统（用结算单查询接口报文作为心跳报文）	
		if(HSTProperties.getString("STL_HEARTBEAT_IN")==null || HSTProperties.getString("STL_HEARTBEAT_IN").equals(HSTConstants.HEARTBEAT_IN_Y)){
			StlPackageDTO stlPackageDTO = new StlPackageDTO();
			try {
				java2STLBusinessService.querySettle(stlPackageDTO);
			} catch (BizServiceException e) {
				logger.error(e.getMessage());
			}
		}
	}
}
