package com.huateng.univer.cardmanage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.junit.Test;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.cardmanagement.dto.CardBalanceDTO;
import com.allinfinance.univer.cardmanagement.dto.CardManagementDTO;
import com.huateng.framework.util.ReDTOs;
import com.huateng.framework.webservice.ManageServiceTestImpl;
import com.huateng.test.BaseTest;
import com.opensymphony.xwork2.ActionProxy;

public class CardManageActionTest extends BaseTest {
	Logger logger = Logger.getLogger(CardManageActionTest.class);
	/**
	 * 卡查询初始
	 */
	@Test
	public void cardQueryInitTest() {
		ActionProxy proxy = null;
		proxy = getActionProxy("/cardManage/cardQueryInit");
		// CardManageAction cma = (CardManageAction) proxy.getAction();
		CardManageAction cma = (CardManageAction) proxy.getAction();
		String result = null;
		try {
			result = proxy.execute();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		Collection<String> actionmessage = cma.getActionMessages();
		actionmessage.size();
		assertEquals("init", result);
		// assertEquals("test...", cma.getParam());
	}

	/**
	 * 查询卡片操作
	 */
	@Test
	public void selectCardOperationTest() {

		ActionProxy proxy = null;
		// request.setParameter("param", "test...");
		proxy = getActionProxy("/cardManage/selectCardOperation");
		Map<String, Object> map = ManageServiceTestImpl.getMap();
		Object pageDto = ReDTOs.getDTO(PageDataDTO.class);
		Object obj = ReDTOs.getObj(pageDto);
		map.put(ConstCode.CARDMANAGEMENT_SELECTOPERATION, obj);
		proxy.getAction();

		String result = null;
		try {
			result = proxy.execute();
		} catch (Exception e) {
			fail(e.getMessage());
			this.logger.error(e.getMessage());
		}

		assertEquals("select", result);
		// assertEquals("test...", cma.getParam());

	}

	@Test
	public void cardQueryTest() {
		request.setParameter("cardManagementDTO.transferOutCard",
				"1234567890123456789");

		ActionProxy proxy = getActionProxy("/cardManage/cardQuery");

		Map<String, Object> session = new HashMap<String, Object>();
		session.put("balanceDtoList", new ArrayList<CardBalanceDTO>());
		ServletActionContext.getContext().setSession(session);

		CardManagementDTO cardManagementDTO = (CardManagementDTO) ReDTOs
				.getDTO(CardManagementDTO.class);
		Object obj = ReDTOs.getObj(cardManagementDTO);
		cardManagementDTO.setTotalBalance("100");
		cardManagementDTO.setBalance("100");
		Map<String, Object> map = ManageServiceTestImpl.getMap();
		map.put(ConstCode.CARDMANAGEMENT_QUERY, obj);

		CardManageAction cma = (CardManageAction) proxy.getAction();
		
		String result = null;
		try {
			result = proxy.execute();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			fail(e.getMessage());
		}

		assertEquals("succ", result);
	}
}
