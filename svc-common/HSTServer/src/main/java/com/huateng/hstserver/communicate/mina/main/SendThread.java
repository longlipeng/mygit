package com.huateng.hstserver.communicate.mina.main;



import java.util.Date;

import org.apache.log4j.Logger;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.comm.server.client.ManagedAsynClient;
import com.huateng.hstserver.exception.BizServiceException;



public class SendThread extends Thread {
	private Logger logger = Logger.getLogger(SendThread.class);
	private int idx = 0;
	
	private ManagedAsynClient managedAsynClient;
	
	public ManagedAsynClient getManagedAsynClient() {
		return managedAsynClient;
	}

	public void setManagedAsynClient(ManagedAsynClient managedAsynClient) {
		this.managedAsynClient = managedAsynClient;
	}
	
	public void run() {
		CommMessage myMsg = new CommMessage();
		myMsg.setLength(10);
		myMsg.setRemoteip("127.0.0.1");
		myMsg.setRemoteport(6000);
		
		for (int i = 0; i < 500000; i ++)
		{
			if (i % 10000 == 0)
			{
				logger.info("the " + i + " times, date:" + new Date());
			}
			if (i % 100 == 0)
			{
				try {
					sleep(30);
				} catch (InterruptedException e) {
					
					logger.error(e.getMessage());
				}
			}
			
			String msg = String.format("%010d", i);
			myMsg.setMessagbody(msg.getBytes());
			try {
				managedAsynClient.sendMessage(myMsg);
			} catch (BizServiceException e) {
				logger.error(e.getMessage());
			}
//			try {
//				sleep(10);
//			} catch (InterruptedException e) {
//				
//				logger.error(e.getMessage());
//			}
		}
	}

}
