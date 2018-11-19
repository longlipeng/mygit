/**
 * Classname CardlayoutServiceImplTest.java
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

package com.huateng.univer.issuer.cardLayout.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutQueryDTO;
import com.huateng.framework.ibatis.dao.CardLayoutDAO;
import com.huateng.framework.ibatis.dao.UserDAO;
import com.huateng.framework.ibatis.model.CardLayout;
import com.huateng.framework.ibatis.model.CardLayoutExample;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.ibatis.model.UserExample;
import com.huateng.framework.util.MakeTestType;
import com.huateng.test.BaseTest;
import com.huateng.univer.issuer.cardLayout.service.CardLayoutService;

/**
 * @author huateng
 *
 */
@MakeTestType(testType = { "ServiceTest" })
public class CardlayoutServiceImplTest extends BaseTest{
	Logger logger = Logger.getLogger(CardlayoutServiceImplTest.class);
	@Resource
	private CardLayoutService service;
	@Resource
	private UserDAO userDAO;
	@Resource
	private CardLayoutDAO cardLayoutDAO;
	@Test
	/**
	 * 查询卡面信息测试
	 */
	public void inqueryCardLayoutTest(){
		CardLayoutQueryDTO dto = new CardLayoutQueryDTO();
		CardLayout cardLayout = getOneCardLayout();
		dto.setDefaultEntityId(cardLayout.getEntityId());
		dto.setLoginUserId(getLoginUserId(cardLayout.getEntityId()));
		try{
			service.inqueryCardLayout(dto);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	/**
	 * 获得一个cardLayout
	 */
	private CardLayout getOneCardLayout(){
		CardLayout c = new CardLayout();
		CardLayoutExample ce = new CardLayoutExample();
		List<CardLayout> l = cardLayoutDAO.selectByExampleWithBLOBs(ce);
		logger.info("====================="+l.size());
		if(l.size()>0)
			c = l.get(0);
		return c;
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
}
