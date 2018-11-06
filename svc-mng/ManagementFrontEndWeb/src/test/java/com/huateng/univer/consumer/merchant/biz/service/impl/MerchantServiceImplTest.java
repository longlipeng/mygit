/**
 * Classname MerchantServiceImplTest.java
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

package com.huateng.univer.consumer.merchant.biz.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantQueryDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantTxnQueryDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.MerchantDAO;
import com.huateng.framework.ibatis.dao.ShopDAO;
import com.huateng.framework.ibatis.dao.UserDAO;
import com.huateng.framework.ibatis.model.Merchant;
import com.huateng.framework.ibatis.model.MerchantExample;
import com.huateng.framework.ibatis.model.Shop;
import com.huateng.framework.ibatis.model.ShopExample;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.ibatis.model.UserExample;
import com.huateng.framework.util.MakeTestType;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.test.BaseTest;
import com.huateng.univer.consumer.merchant.biz.service.MerchantService;

/**
 * @author huateng
 *
 */
@MakeTestType(testType = { "ServiceTest" })
public class MerchantServiceImplTest extends BaseTest{
	Logger logger = Logger.getLogger(MerchantServiceImplTest.class);
	@Resource
	private MerchantService service;
	@Resource
	private MerchantDAO merchantDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private ShopDAO shopDAO;
	@Test
	/**
	 * 查询商户测试
	 */
	public void inquiryMerchantTest(){
		MerchantQueryDTO dto = new MerchantQueryDTO();
		
		try{
			Merchant merchant = getOneMerchant();
			dto.setDefaultEntityId(merchant.getFatherEntityId());
			dto.setFatherEntityId(merchant.getFatherEntityId());
			dto.setLoginUserId(getLoginUserId(merchant.getFatherEntityId()));
			List l = service.inquiryMerchant(dto).getData();
			logger.info(dto.getDefaultEntityId()+","+dto.getLoginUserId()+"========================================================"+l.size());
		}catch(NullPointerException e){
			logger.info("no data");
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	/**
	 * 查询商户交易测试！！！跟c连接。配置未完成
	 */
	//@Test
	public void merchantTxnQueryTest(){
		MerchantTxnQueryDTO dto = new MerchantTxnQueryDTO();
		Merchant merchant = getOneMerchant();
		dto.setCardNo("");
		dto.setDefaultEntityId(merchant.getFatherEntityId());
		dto.setEntityId(merchant.getFatherEntityId());
		dto.setLoginUserId(getLoginUserId(merchant.getFatherEntityId()));
		dto.setMerchantCode(merchant.getMerchantCode());
		dto.setPosId("");
		dto.setSelectFlag("0");
		dto.setStartDate("");
		dto.setTransType("S22");
		dto.setShopId(getOneShop(merchant.getEntityId()).getShopId());
		try{
			List l = service.merchantTxnQuery(dto).getData();
			logger.info(l.size());
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	/**
	 * 添加商户测试
	 */
	@Test
	public void insertMerchantTest(){
		MerchantDTO dto = new MerchantDTO();
		Merchant merchant = getOneMerchant();
		ReflectionUtil.copyProperties(merchant, dto);
		dto.setLoginUserId(getLoginUserId(merchant.getFatherEntityId()));
		dto.setModifyUser(dto.getLoginUserId());
		String str = "";
		//正常添加成功
		try{
			String code,name;
			z:
			while(true){
				code = getOneRandomMerchantCode();
				name = code;
				for(Merchant m:getMerchants(merchant)){
					if(code.equals(m.getMerchantCode())||
							name.equals(m.getMerchantName()))
						continue z;
				}
				str = code;
				break;
			}
			dto.setMerchantCode(code);
			dto.setMerchantName(name);
			service.insertMerchant(dto);
			
		}catch(Exception e){
			fail(e.getMessage());
		}
		
		
		//商户号已存在，不允许添加
		try{
			dto.setMerchantCode(merchant.getMerchantCode());
			dto.setMerchantName(str);
			service.insertMerchant(dto);
			fail("错误");
		}catch(BizServiceException e){
			logger.info(e.getErrorMessage());
			Assert.assertEquals(e.getErrorMessage(), "此商户号已存在");
		}
		//商户name已存在，不允许添加
		try{
			dto.setMerchantCode(str);
			dto.setMerchantName(merchant.getMerchantName());
			service.insertMerchant(dto);
			fail("错误");
		}catch(BizServiceException e){
			Assert.assertEquals(e.getErrorMessage(), "该机构下商户名称已存在");
		}
	}
	/**
	 * 获得一个商户
	 */
	private Merchant getOneMerchant(){
		MerchantExample me = new MerchantExample();
		me.createCriteria().andMerchantStateEqualTo("1");
		return merchantDAO.selectByExample(me).get(0);
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
	 * 由商户id获得户下一家门店
	 */
	private Shop getOneShop(String code){
		Shop shop = new Shop();
		ShopExample se = new ShopExample();
		se.createCriteria().andEntityIdEqualTo(code);
		List<Shop> l = shopDAO.selectByExample(se);
		if(l.size()>0)
			shop = l.get(0);
		return shop;
	}
	
	/**
	 * 获得一个随机商户号15位
	 */
	private String getOneRandomMerchantCode(){
		long l = (long)(Math.random() * 900000000000000l) + 100000000000000l;
		return String.valueOf(l);
	}
	/**
	 * 获得所有商户
	 */
	private List<Merchant> getMerchants(Merchant m){
		MerchantExample me = new MerchantExample();
		me.createCriteria().andFatherEntityIdEqualTo(m.getFatherEntityId());
		return merchantDAO.selectByExample(me);
	}
}
































