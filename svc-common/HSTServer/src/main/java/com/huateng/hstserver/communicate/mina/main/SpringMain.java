package com.huateng.hstserver.communicate.mina.main;


import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {

	/**
	 * @param args
	 */
/*	public static void main(String[] args) {
	
		ConfigurableApplicationContext ctx = getApplicationContext();
	        logger.info("Listening ...");
//		String start=String.format("%%0%dd",10);
//		logger.info(start);
	        
	        
	        CommMessage sendmesg = new CommMessage();
	        sendmesg.setLength(2);
	        sendmesg.setRemoteip("127.0.0.1");
	        sendmesg.setRemoteport(60000);
	        byte[] messagbody = new byte[2];
	        messagbody[0] = 0x34;
	        messagbody[1] = 0x38;
	        sendmesg.setMessagbody(messagbody);

	        
	        ManagedSynClient managedSynClient = (ManagedSynClient)ctx.getBean("managedSynClient");
	        CommMessage recvmesg = managedSynClient.sendMessage(sendmesg);
	        logger.info("recieve message is: " + recvmesg);
	        messagbody[0] = 0x33;
	        messagbody[1] = 0x39;
	        logger.info("recieve message is: " + recvmesg);
	        
//	        
//	        ManagedAsynClient managedAsynClient = (ManagedAsynClient)ctx.getBean("managedAsynClient");
//	        
//	        RecieveThread rt = new RecieveThread();
//	        rt.setManagedAsynClient(managedAsynClient);
//	        
//	        SendThread st = new SendThread();
//	        st.setManagedAsynClient(managedAsynClient);
//	        
//	        rt.start();
//	        st.start();
	        
    }*/

    public static ConfigurableApplicationContext getApplicationContext() {
        return new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}