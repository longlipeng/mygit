package com.huateng.framework.listener.batch;

import static org.junit.Assert.fail;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.test.BaseTest;
import com.huateng.univer.businessbatch.BusinessBatchService;
import com.huateng.univer.businessbatch.impl.RechargeActBatchServiceImpl;
import com.huateng.univer.businessbatch.impl.RechargeBatchServiceImpl;

public class BatchCheckQuartzTest extends BaseTest {
	private Logger logger = Logger.getLogger(BatchCheckQuartzTest.class);
	@Resource(name="batchCheckQuartz")
	private BatchCheckQuartz batchService;
	@Resource(name="businessRechargeAct")
	private BusinessBatchService service;
	@Resource(name="businessRecharge")
	private BusinessBatchService service1;

	@Test
	public void testAddEvent() {
		BatchListener event = new RechargeActBatchServiceImpl();
		BatchListener event1 = new RechargeBatchServiceImpl();
		batchService.addEvent("1234", event);
		batchService.addEvent("5678", event1);
		//不抛错，就说明成功了
	}

	@Test
	public void testProcess() {
		batchService.addEvent("1234", service);
		batchService.addEvent("5678", service1);
		batchService.process();
	}

	@Test
	public void testInit() {
//		fail("Not yet implemented");
		try {
			batchService.init();
		} catch (BizServiceException e) {
			this.logger.error(e.getMessage());
			fail("failed");
		}
	}

}
