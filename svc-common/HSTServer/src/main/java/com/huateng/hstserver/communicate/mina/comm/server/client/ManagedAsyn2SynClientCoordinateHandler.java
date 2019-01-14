package com.huateng.hstserver.communicate.mina.comm.server.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.log4j.Logger;
import com.huateng.hstserver.communicate.mina.comm.common.connection.Connection;
import com.huateng.hstserver.communicate.mina.comm.common.connection.ConnectionPool;
import com.huateng.hstserver.communicate.mina.comm.common.connection.ConnectionSemaphore;
import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.comm.common.quartz.ConnectionMaintainJob;
import com.huateng.hstserver.constants.HSTConstants;


public class ManagedAsyn2SynClientCoordinateHandler extends IoHandlerAdapter {
	/** The logger. */
	final Logger logger = Logger.getLogger(ManagedAsyn2SynClientCoordinateHandler.class);
	
	private ManagedAsyn2SynClient managedAsyn2SynClient = null;
	
	public ManagedAsyn2SynClient getManagedAsyn2SynClient() {
		return managedAsyn2SynClient;
	}

	public void setManagedAsyn2SynClient(ManagedAsyn2SynClient managedAsyn2SynClient) {
		this.managedAsyn2SynClient = managedAsyn2SynClient;
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		managedAsyn2SynClient.putReplyMessage((CommMessage)message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {	
		String linkUUID = (String)session.getAttribute(HSTConstants.LINK_UUID);
		String connectionNm = (String)session.getAttribute(HSTConstants.CONNECTION_NM);
		String remoteIp = (String)session.getAttribute(HSTConstants.REMOTE_IP);
		String remotePort = (String)session.getAttribute(HSTConstants.REMOTE_PORT);
		
		logger.info("session " + connectionNm +" closed, remote ip is: [" + remoteIp + "], remote port is: [" + remotePort +"]");
		session.close(true);				
		
		//如果当前链路被重置,需要判定该链路是否正在进行链路维护,如果正在维护链路则释放链路维护标识（同名链路多次建链,容易导致同名链路被反复重置）
		//在链路中增加UUID,如果UUID相同者释放维护标识，否则不释放				
		Connection connect = ConnectionPool.getInstance().getConnection(connectionNm);
		if(linkUUID.equals(ConnectionSemaphore.getLinkUUID()) 
				&& ConnectionSemaphore.isBusy() 
				&& HSTConstants.CONNECTION_STATUS_CREATING.equals(connect.getStatus())){
			//释放链路维护标识
			ConnectionSemaphore.setBusy(false);
			ConnectionSemaphore.setConnectionNm(null);		
			ConnectionPool.getInstance().setConnectionStatus(connectionNm,HSTConstants.CONNECTION_STATUS_CLOSED);
		}
		
		ConnectionMaintainJob.addTask(connectionNm);
	}

//	@Override
//	public void exceptionCaught(IoSession session, Throwable cause)
//			throws Exception {
//		super.exceptionCaught(session, cause);
//	}
	
	

}
