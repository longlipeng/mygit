/**
 * Classname SettleServiceImplTest.java
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
 *		huateng		2012-12-4
 * =============================================================================
 */

package com.huateng.univer.settle.biz.service.impl;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.allinfinance.univer.settle.dto.SettleQueryDTO;
import com.huateng.framework.ibatis.dao.MerchantDAO;
import com.huateng.framework.ibatis.dao.UserDAO;
import com.huateng.framework.ibatis.model.Merchant;
import com.huateng.framework.ibatis.model.MerchantExample;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.ibatis.model.UserExample;
import com.huateng.framework.util.MakeTestType;
import com.huateng.framework.util.ReDTOs;
import com.huateng.test.BaseTest;
import com.huateng.univer.settle.biz.service.SettleService;

/**
 * @author huateng
 *
 */
@MakeTestType(testType = { "ServiceTest" })
public class SettleServiceImplTest extends BaseTest{
	private Logger logger = Logger.getLogger(SettleServiceImplTest.class);
	@Resource
	private SettleService service;
	@Resource
	private MerchantDAO merchantDAO;
	@Resource
	private UserDAO userDAO;
	@Test
	/**
	 * 结算单预览测试
	 */
	public void previewSettleTest(){
		SettleQueryDTO dto = (SettleQueryDTO)ReDTOs.getDTO(SettleQueryDTO.class);
		Merchant merchant = getOneMerchant();
		dto.setDefaultEntityId(merchant.getFatherEntityId());
		dto.setLoginUserId(getLoginUserId(merchant.getFatherEntityId()));
		logger.info("========================================="+getLoginUserId(merchant.getFatherEntityId()));
		dto.setSettleObject1(merchant.getFatherEntityId());
		dto.setSettleObject2(merchant.getEntityId());
		try{
			service.previewSettle(dto);
		}catch(Exception e){
			this.logger.error(e.getMessage());
			fail(e.getMessage());
		}
	}
	
	/**
	 * 由entityId获得loginUserId
	 * @param fatherEntityId
	 * @return
	 */
	private String getLoginUserId(String fatherEntityId) {
		UserExample ue = new UserExample();
		ue.createCriteria().andEntityIdEqualTo(fatherEntityId);
		User user = userDAO.selectByExample(ue).get(0);
		return user.getUserId();
	}

	/**
	 * 获得一个商户
	 */
	private Merchant getOneMerchant(){
		MerchantExample me = new MerchantExample();
		return merchantDAO.selectByExample(me).get(0);
	}
}
