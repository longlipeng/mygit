package com.huateng.univer.batchfile;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.ReDTOs;
import com.huateng.test.BaseTest;
import com.huateng.univer.batchfile.action.BatchFileActionInterface;

public class BatchFileServiceImplTest extends BaseTest{	
	@Resource
	private BatchFileActionInterface fileBatchService;
	
	
	@Test
	@Rollback(false)
	public void batchFileTest() {
			SellOrderListDTO sellOrderListDTO=(SellOrderListDTO)ReDTOs
			.getDTO(SellOrderListDTO.class);
			List<SellOrderListDTO> list = new ArrayList();			
			sellOrderListDTO.setActFlag("0");
			sellOrderListDTO.setCardNo("1222244222222222222");
			sellOrderListDTO.setFaceValue("1001");
			sellOrderListDTO.setValidityPeriod("20161010");
			sellOrderListDTO.setServiceId("10000202");
			list.add(sellOrderListDTO);
			
			try {
				fileBatchService.fileBatch("businessRechargeAct", "1889", list);
			} catch (BizServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}


	public BatchFileActionInterface getRechargeActBatchFileService() {
		return fileBatchService;
	}


	public void setRechargeActBatchFileService(
			BatchFileActionInterface rechargeActBatchFileService) {
		this.fileBatchService = rechargeActBatchFileService;
	}


	
}
