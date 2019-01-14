package com.huateng.hstserver.communicate.mina.main;



import java.util.Date;

import org.apache.log4j.Logger;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.comm.server.client.ManagedAsynClient;


	public class RecieveThread extends Thread {
	private Logger logger = Logger.getLogger(RecieveThread.class);
	private ManagedAsynClient managedAsynClient;
	
	public ManagedAsynClient getManagedAsynClient() {
		return managedAsynClient;
	}

	public void setManagedAsynClient(ManagedAsynClient managedAsynClient) {
		this.managedAsynClient = managedAsynClient;
	}

	public void run() {
		int i = 0;
		
		try {
			sleep(50*1000);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
		
		while(true)
		{
			CommMessage myMsg = managedAsynClient.recieveMessage();
			//String msg = new String(myMsg.getMessagbody());
			
			
//			IoConnector connector = new SerialConnector();
			
//			NettyDecoder my  = new NettyDecoder();
			
			
			i ++;
			if (i % 10000 == 0)
			{
				String content = new String(myMsg.getMessagbody());
				logger.info("recieve the " + i + " packages, content is [" + content +"], date:" + new Date());
				
			}
			
//			logger.info(msg);
		}

	}

}
