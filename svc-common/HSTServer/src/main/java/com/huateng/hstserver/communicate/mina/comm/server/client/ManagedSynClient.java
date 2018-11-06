package com.huateng.hstserver.communicate.mina.comm.server.client;

import java.net.InetSocketAddress;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.biz.BizMessageObj;
import com.huateng.hstserver.config.HSTProperties;
import com.huateng.hstserver.constants.HSTConstants;
import com.huateng.hstserver.exception.BizServiceException;

public class ManagedSynClient extends ClientProcessor {

	public ManagedSynClient(boolean isShortConnection, boolean synProcessDataTrans) {
		super(isShortConnection, synProcessDataTrans);
	}

	public ManagedSynClient(boolean isShortConnection) {
		super(isShortConnection);
	}

	public ManagedSynClient() {
		super(true, true);
	}
	
	@Override
	public void setConnector(IoConnector connector) throws Exception {
//		IoHandler ioHandler = connector.getHandler();
//		if (!(ioHandler instanceof ManagedSynClientCoordinateHandler))
//		{
//			
//			throw new Exception("Connector do not have the ManagedSynClientCoordinateHandler as IoHandler");
//		}
		super.setConnector(connector);
	}

	public CommMessage sendMessage(CommMessage sendmesg) throws BizServiceException	{
		CommMessage recvMessage = null;	
		IoSession session = null;
		
		String ip = sendmesg.getRemoteip();
		int port = sendmesg.getRemoteport();
		if(ip==null ){
			BizMessageObj bizMessageObj = (BizMessageObj)sendmesg.getMessageObject();
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
		
		recvMessage = processMessage(sendmesg, session);
		
		if (isShortConnection())
		{
			session.close(true);
		}
		
		return recvMessage;
	}

}
