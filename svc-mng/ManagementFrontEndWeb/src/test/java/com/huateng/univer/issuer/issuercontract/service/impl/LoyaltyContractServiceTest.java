/**
 * Classname LoyaltyContractServiceTest.java
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
 *		huateng		2012-12-6
 * =============================================================================
 */

package com.huateng.univer.issuer.issuercontract.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyContractQueryDTO;
import com.huateng.framework.ibatis.dao.LoyaltyContractDAO;
import com.huateng.framework.ibatis.dao.UserDAO;
import com.huateng.framework.ibatis.model.LoyaltyContract;
import com.huateng.framework.ibatis.model.LoyaltyContractExample;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.ibatis.model.UserExample;
import com.huateng.framework.util.MakeTestType;
import com.huateng.test.BaseTest;
import com.huateng.univer.issuer.issuercontract.service.LoyaltyContractService;

/**
 * @author huateng
 *
 */
@MakeTestType(testType = { "ServiceTest" })
public class LoyaltyContractServiceTest extends BaseTest{
	Logger logger = Logger.getLogger(LoyaltyContractServiceTest.class);
	@Resource
	private LoyaltyContractService service;
	@Resource
	private LoyaltyContractDAO loyaltyContractDAO;
	@Resource
	private UserDAO userDAO;
	@SuppressWarnings("unchecked")
	@Test
	/**
	 * 查询发行合同列表测试
	 */
	public void inqueryIssuerContractTest(){
		LoyaltyContractQueryDTO dto = new LoyaltyContractQueryDTO();
		try{
			LoyaltyContract contract = getOneContract();
			dto.setDefaultEntityId(contract.getContractSeller());
			dto.setLoginUserId(getLoginUserId(contract.getContractSeller()));
			List l = service.inqueryIssuerContract(dto).getData();
			logger.info(l.size());
		}catch(NullPointerException e){
			logger.info("no data");
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	private LoyaltyContract getOneContract(){
		LoyaltyContractExample le = new LoyaltyContractExample();
		return loyaltyContractDAO.selectByExample(le).get(0);
	}
	private String getLoginUserId(String fatherEntityId) {
		UserExample ue = new UserExample();
		ue.createCriteria().andEntityIdEqualTo(fatherEntityId);
		User user = userDAO.selectByExample(ue).get(0);
		return user.getUserId();
	}
}
