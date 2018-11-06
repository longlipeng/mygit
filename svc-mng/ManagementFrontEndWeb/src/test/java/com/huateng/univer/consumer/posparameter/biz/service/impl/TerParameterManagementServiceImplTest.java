/**
 * Classname TerParameterManagementServiceImplTest.java
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
 *		huateng		2012-11-28
 * =============================================================================
 */

package com.huateng.univer.consumer.posparameter.biz.service.impl;

import static org.junit.Assert.fail;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.allinfinance.univer.consumer.pos.dto.PosParameterDTO;
import com.allinfinance.univer.consumer.pos.dto.posParameterValueDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.PosParamenterDAO;
import com.huateng.framework.ibatis.dao.UserDAO;
import com.huateng.framework.ibatis.model.PosParamenter;
import com.huateng.framework.ibatis.model.PosParamenterExample;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.ibatis.model.UserExample;
import com.huateng.framework.util.MakeTestType;
import com.huateng.framework.util.ReDTOs;
import com.huateng.test.BaseTest;
import com.huateng.univer.consumer.posparameter.biz.service.TerParameterManagementService;

/**
 * @author huateng
 * 
 */
@MakeTestType(testType = { "ServiceTest" })
public class TerParameterManagementServiceImplTest extends BaseTest {
	private Logger logger = Logger.getLogger(TerParameterManagementServiceImplTest.class);
	@Resource
	private TerParameterManagementService service;// posParameterService
	@Resource
	private UserDAO userDAO;
	@Resource
	private PosParamenterDAO posParamenterDAO;
	@Test
	@Rollback(true)
	/**
	 * 添加卡bin参数测试
	 */
	public void insertCardBinTest() {
		PosParameterDTO dto = (PosParameterDTO) ReDTOs
				.getDTO(PosParameterDTO.class);

		PosParamenter posParamenter = getPosParamenters().get(0);
		dto.setDefaultEntityId(posParamenter.getConsumerId());
		dto.setLoginUserId(getLoginUserId(posParamenter.getConsumerId()));
		dto.setPrmType(posParamenter.getPrmType());
		dto.setPrmName(posParamenter.getPrmName());
		dto.setPrmStat(Short.valueOf("0"));
		dto.setPrmVersion(0);
		dto.setPrmVersionInt(0);
		dto.setPrmDesc("");
		logger.info(dto.getDefaultEntityId()+","+dto.getLoginUserId());
		try {
			// 重复添加，异常
			dto.setPrmVal(posParamenter.getPrmVal());
			service.insertCardBin(dto);
			fail(" error ");
		} catch (BizServiceException e) {
			// this.logger.error(e.getMessage());
			logger.info("测试不允许重复添加成功");
		}
		
		try {
			// 正常添加，成功
			//生成一个表中不存在的参数值prm_val
			String value = "";
			z:
			while(true){
				int i = (int) (Math.random() * 100000000);
				value = String.valueOf(i);
				for(PosParamenter p:getPosParamenters()){
					if(value.equals(p.getPrmVal())){
						continue z;
					}
				}
				break;
			}
			dto.setPrmVal(value);
			service.insertCardBin(dto);
		} catch (Exception e) {
			fail(e.getMessage());
			this.logger.error(e.getMessage());
		}

	}
	
	@Test
	/**
	 * 查询卡bin参数列表测试
	 */
	public void queryCardBinListTest(){
		posParameterValueDTO dto = (posParameterValueDTO)ReDTOs.getDTO(posParameterValueDTO.class);
		try{
			User user = getOneUser();
			dto.setLoginUserId(user.getUserId());
			dto.setUserId(user.getUserId());
			List<posParameterValueDTO> list = service.queryCardBinList(dto);
			logger.info("==================================================="+list.size());
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	/**
	 * 获得一条User
	 * @return
	 */
	private User getOneUser(){
		UserExample ue = new UserExample();
		ue.createCriteria().andUserStateEqualTo("1");
		return userDAO .selectByExample(ue).get(0);
	}
	/**
	 * 获得所有posParameter
	 */
	private List<PosParamenter> getPosParamenters(){
		PosParamenterExample pe = new PosParamenterExample();
		pe.createCriteria().andPrmNameEqualTo("卡BIN");
		return posParamenterDAO.selectByExample(pe);
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
















































