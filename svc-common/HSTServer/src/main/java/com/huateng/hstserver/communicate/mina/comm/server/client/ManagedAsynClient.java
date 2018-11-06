package com.huateng.hstserver.communicate.mina.comm.server.client;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.biz.BizMessageObj;
import com.huateng.hstserver.config.HSTProperties;
import com.huateng.hstserver.constants.HSTConstants;
import com.huateng.hstserver.exception.BizServiceException;


public class ManagedAsynClient extends ClientProcessor {
	private Logger logger = Logger.getLogger(ManagedAsynClient.class);
	private BlockingQueue<CommMessage> replyList = new LinkedBlockingQueue<CommMessage>();
	
//	//test only
//	public BlockingQueue<CommMessage> getReplyList() {
//		return replyList;
//	}
//	//end	

	//	public ManagedAsynClient(IoConnector connector, boolean isShortConnection,
//			boolean synProcessDataTrans) {
//		super(connector, isShortConnection, synProcessDataTrans);
//		
//	}
//
	public ManagedAsynClient(boolean isShortConnection) {
		super(isShortConnection, false);
		
	}

	public ManagedAsynClient() {
		super(false, false);
	}
	
	
	@Override
	public void setConnector(IoConnector connector) throws Exception {
//		
//		IoHandler ioHandler = connector.getHandler();
//		if (!(ioHandler instanceof ManagedAsynClientCoordinateHandler))
//		{
//			
//			throw new Exception("Connector do not have the ManagedAsynClientCoordinateHandler as IoHandler");
//		}
		super.setConnector(connector);
	}





	protected void putReplyMessage(CommMessage sendmesg)
	{
		replyList.offer(sendmesg);
	}
	
	public CommMessage sendMessage(CommMessage sendmesg) throws BizServiceException 
	{
		
//		IoHandler ioHandler = getConnector().getHandler();
//		if (!(ioHandler instanceof ManagedAsynClientCoordinateHandler))
//		{
//			logger.info("");
//			
////			throw new Exception("Connector do not have the ManagedAsynClientCoordinateHandler as IoHandler");
//		}
		logger.info("sendMessage");
//		CommMessage recvMessage = null;	
//		InetSocketAddress synAddress = null;
		IoSession session = null;
		String ip = sendmesg.getRemoteip();
		int port = sendmesg.getRemoteport();
		
		if(ip==null ){
			BizMessageObj bizMessageObj = (BizMessageObj)sendmesg.getMessageObject();
			//根据服务名称获取目标系统名称,并读取配置文件,设置目标系统的IP和PORT
			String svrNm = bizMessageObj.getServiceName();
			String sysNm = HSTConstants.getSysBySvr(svrNm);
			if(sysNm.equals(HSTConstants.SYS_TXN)){
				ip = HSTProperties.getString("TXN_IP");
				port = Integer.parseInt(HSTProperties.getString("TXN_PORT"));
			}else if(sysNm.equals(HSTConstants.SYS_ACC)){
				ip = HSTProperties.getString("ACC_IP");
				port = Integer.parseInt(HSTProperties.getString("ACC_PORT"));
			}else if(sysNm.equals(HSTConstants.SYS_STL)){
				ip = HSTProperties.getString("STL_IP");
				port = Integer.parseInt(HSTProperties.getString("STL_PORT"));
			}else{
				throw new BizServiceException("service not config,can't route to remote system !");
			}
		}
		
		
		
		
		InetSocketAddress remoteAddress = new InetSocketAddress(ip, port);
		
		session = getWorkingSession(remoteAddress);
		
		session.write(sendmesg);
		
//		recvMessage = processMessage(sendmesg, session);
			
		return null;
	}
	
	public CommMessage recieveMessage()
	{
		CommMessage replyMsg = null;
		try {
			replyMsg = replyList.take();
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
		
		return replyMsg;
	}
	
}
