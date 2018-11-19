/**
 * Classname SellerContractServiceImplTest.java
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

package com.huateng.univer.seller.sellercontract.biz.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.allinfinance.univer.seller.sellercontract.dto.SellerContractQueryDTO;
import com.huateng.framework.ibatis.dao.SellContractDAO;
import com.huateng.framework.ibatis.dao.UserDAO;
import com.huateng.framework.ibatis.model.SellContract;
import com.huateng.framework.ibatis.model.SellContractExample;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.ibatis.model.UserExample;
import com.huateng.framework.util.MakeTestType;
import com.huateng.test.BaseTest;
import com.huateng.univer.seller.sellercontract.biz.service.SellerContractService;

/**
 * @author huateng
 *
 */
@MakeTestType(testType = { "ServiceTest" })
public class SellerContractServiceImplTest extends BaseTest{
	private Logger logger = Logger.getLogger(SellerContractServiceImplTest.class);
	@Resource
	private SellerContractService service;
	@Resource
	private SellContractDAO sellContractDAO;
	@Resource
	private UserDAO userDAO;
	
	@SuppressWarnings("unchecked")
	@Test
	/**
	 * 查询营销合同列表测试
	 */
	public void inqueryTest(){
		SellerContractQueryDTO dto = new SellerContractQueryDTO();
		
		try{
			SellContract sellContract = getOneSellContract();
			dto.setDefaultEntityId(sellContract.getContractSeller());
			dto.setLoginUserId(getLoginUserId(sellContract.getContractSeller()));
			List l = service.inquery(dto).getData();
			logger.info(l.size());
		}catch(NullPointerException e){
			logger.info("no data");
		}catch(Exception e){
			fail(e.getMessage());
		}
		
	}
	
	/**
	 * 得到一条营销合同
	 */
	private SellContract getOneSellContract(){
		SellContractExample se = new SellContractExample();
		return sellContractDAO.selectByExample(se).get(0);
	}
	
	private String getLoginUserId(String fatherEntityId) {
		UserExample ue = new UserExample();
		ue.createCriteria().andEntityIdEqualTo(fatherEntityId);
		User user = userDAO.selectByExample(ue).get(0);
		return user.getUserId();
	}
}
