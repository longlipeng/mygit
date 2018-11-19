package com.huateng.hstserver.communicate.mina.comm.server.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.mina.core.session.IoSession;
import org.apache.log4j.Logger;
import com.huateng.hstserver.communicate.mina.comm.common.connection.Connection;
import com.huateng.hstserver.communicate.mina.comm.common.connection.ConnectionPool;
import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.comm.common.entity.RecvMessageBean;
import com.huateng.hstserver.communicate.mina.comm.common.entity.SemaphoreMap;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.biz.BizMessageObj;
import com.huateng.hstserver.communicate.mina.comm.common.quartz.ConnectionMaintainJob;
import com.huateng.hstserver.config.HSTProperties;
import com.huateng.hstserver.constants.HSTConstants;
import com.huateng.hstserver.exception.BizServiceException;
import com.huateng.hstserver.frameworkUtil.StringUtils;

public class ManagedAsyn2SynClient {
	private static final Logger logger = Logger.getLogger(ManagedAsyn2SynClient.class);
	
	private BlockingQueue<CommMessage> replyList = new LinkedBlockingQueue<CommMessage>();

	protected void putReplyMessage(CommMessage sendmesg)
	{
		replyList.offer(sendmesg);
	}
	
	public CommMessage sendMessage(CommMessage sendmesg) throws BizServiceException 
	{		
		
		//获取业务信息
		BizMessageObj bizMessageObj = (BizMessageObj)sendmesg.getMessageObject();
		
		//根据服务名称获取目标系统名称
		String svrNm = bizMessageObj.getServiceName();
		String sysNm = HSTConstants.getSysBySvr(svrNm);		
		
		//根据目标系统名称获取可用链路
		Connection connect = ConnectionPool.getInstance().getConnectionBySys(sysNm);
		if(connect == null){
			logger.error("connect is null");
			throw new BizServiceException("no available connection!");
		}
	
		//通过链路获取domanID
		String connectionNm = connect.getConnectionNm();
		logger.info(" connection is [{"+connectionNm+"}]");	
		if(connectionNm.equals(HSTConstants.CONNECTION_TXN_1) 
				|| connectionNm.equals(HSTConstants.CONNECTION_ACC_1) 
				|| connectionNm.equals(HSTConstants.CONNECTION_STL_1))
			sendmesg.setDomanId(HSTProperties.getString("DOMAN1_ID"));
		else
			sendmesg.setDomanId(HSTProperties.getString("DOMAN2_ID"));		
		
		//通过链路获取session
		IoSession session = connect.getClientSession();			
		
		//将空的待接收消息放入容器   KEY：packageNo  VALUE: RecvMessageBean
		String sendkey = bizMessageObj.getPackageNo();
		RecvMessageBean temprecv = new RecvMessageBean();
		temprecv.setResponsed(false);
		SemaphoreMap.getSemaphore().putIfAbsent(sendkey, temprecv);
		logger.info(" map size is [{"+SemaphoreMap.getSemaphore().size()+"}]");	
		
		//发送信息
		try{
			session.write(sendmesg);
		}catch (Exception e){
			logger.error("sendmesg  error:" + e.getMessage());
			logger.error(e.getMessage());
			SemaphoreMap.getSemaphore().remove(sendkey);
			ConnectionMaintainJob.addTask(connectionNm);	
			throw new BizServiceException("send error!");
		}

		try {
			//挂在该资源上，等待指定秒内有回文
			long waitTime = Long.parseLong(HSTProperties.getString("TIME_OUT_SEC"));
			temprecv.getRecvsemap().tryAcquire(waitTime,TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			logger.error("InterruptedException is {}" + e.getMessage());
		} 
		//从容器里取出收到的消息
		RecvMessageBean tempresult = SemaphoreMap.getSemaphore().remove(sendkey);
	
		//如果服务端有应答
		
		if(tempresult != null && tempresult.isResponsed()){
			logger.info("recv hex msg [{"+StringUtils.Hex2Str(tempresult.getRecvmsg().getMessagbody())+"}]");
			return tempresult.getRecvmsg();
		}else{//超时处理
			logger.info(" map size is [{"+SemaphoreMap.getSemaphore().size()+"}]");
			logger.info("key [{"+sendkey+"}]: is null");			
			//交给链路维护守护线程处理
			ConnectionMaintainJob.addTask(connect.getConnectionNm());				
			throw new BizServiceException("time out!");
		}
		
	}	
	
}
