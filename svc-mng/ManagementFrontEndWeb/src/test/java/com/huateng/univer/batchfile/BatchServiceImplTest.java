package com.huateng.univer.batchfile;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import junit.framework.Assert;

import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.test.BaseTest;
import com.huateng.univer.batch.BatchService;

public class BatchServiceImplTest extends BaseTest {
	@Resource
	private BatchService batchService;
	private static Logger logger = Logger.getLogger(BatchServiceImplTest.class);
	
	public void batchStateGetTest(){
		try {
			SellOrderDTO sellOrderDTO = new SellOrderDTO();
			sellOrderDTO.setOrderId("1018");
			sellOrderDTO.setBatchNo("100000000017");
			String result = batchService.getOrderState(sellOrderDTO);
			Assert.assertEquals("end", result);
		} catch (BizServiceException e) {
			
			this.logger.error(e.getMessage());
		}
	}
	
	
}
