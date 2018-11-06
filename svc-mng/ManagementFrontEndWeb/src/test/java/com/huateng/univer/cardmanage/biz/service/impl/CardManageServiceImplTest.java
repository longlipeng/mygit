package com.huateng.univer.cardmanage.biz.service.impl;

import javax.annotation.Resource;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.allinfinance.univer.cardmanagement.dto.CardManagementDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.MakeTestType;
import com.huateng.framework.util.ReDTOs;
import com.huateng.test.BaseTest;
import com.huateng.univer.cardmanage.biz.service.CardManageService;

/**
 * 
 * @author wpf
 * 
 */
@MakeTestType(testType = { "DemoTest" })
public class CardManageServiceImplTest extends BaseTest {
	private static Logger logger = Logger.getLogger(CardManageServiceImplTest.class);
	/**
	 * 注入需要测试的service
	 */
	@Resource
	private CardManageService cardManageService;

	@Test
	@Rollback(true)
	public void insertCardManagementTest() {

		CardManagementDTO dto = (CardManagementDTO) ReDTOs
				.getDTO(CardManagementDTO.class);

		dto.setCardValidityPeriod("2014-10-10");
		dto.setStartDate("");
		dto.setEndDate("");
		dto.setLoginUserId("10000");
		dto.setFlag("1");

		try {
			cardManageService.insertCardManagement(dto);
		} catch (BizServiceException e) {
			fail(e.getErrorMessage());
			this.logger.error(e.getMessage());
		}
	}

}
