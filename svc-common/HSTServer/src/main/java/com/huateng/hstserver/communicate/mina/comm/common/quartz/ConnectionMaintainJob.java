/**
 * Classname ConnectionMaintainJob.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2012
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		hu_wanli		2012-12-12
 * =============================================================================
 */

package com.huateng.hstserver.communicate.mina.comm.common.quartz;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.log4j.Logger;
import com.huateng.hstserver.communicate.mina.comm.common.connection.Connection;
import com.huateng.hstserver.communicate.mina.comm.common.connection.ConnectionPool;
import com.huateng.hstserver.communicate.mina.comm.common.connection.ConnectionProcessor;
import com.huateng.hstserver.communicate.mina.comm.common.connection.ConnectionSemaphore;
import com.huateng.hstserver.constants.HSTConstants;


/**
 * @author hu_wanli
 *
 */
public class ConnectionMaintainJob {
	private static final Logger logger = Logger.getLogger(ConnectionMaintainJob.class);
	
	private static ConnectionMaintainJob instance = new ConnectionMaintainJob();
	
	public static ConnectionMaintainJob getInstance() {
		return instance;
	}
	
	private ConnectionProcessor connectionProcessor;	
	
	public ConnectionProcessor getConnectionProcessor() {
		return connectionProcessor;
	}

	public void setConnectionProcessor(ConnectionProcessor connectionProcessor) {
		this.connectionProcessor = connectionProcessor;
	}

	private static ConcurrentLinkedQueue<ConnectionTask> queue = new ConcurrentLinkedQueue<ConnectionTask>();
	
	public static void addTask(String connectionNm){
		Connection connect = ConnectionPool.getInstance().getConnection(connectionNm);
		//如果链路不是正在创建，则新增任务
		if(!HSTConstants.CONNECTION_STATUS_CREATING.equals(connect.getStatus())){
			ConnectionTask task = new ConnectionTask();
			task.setConnectionNm(connectionNm);
			task.setRequestTms(System.currentTimeMillis());
			queue.offer(task);		
		}
	}
	
	public void procConnectionTask() {		
		//logger.info("procConnectionTask doing...");
		while(queue.size() > 0){
			logger.info("procConnectionTask doing...");
			if(!ConnectionSemaphore.isBusy()){
				ConnectionTask task =  queue.poll();
				String connectionNm = task.getConnectionNm();
				Connection connect = ConnectionPool.getInstance().getConnection(connectionNm);
				logger.info("Connection [" + connectionNm + "] begin to create");
				//已建立连接
				if(HSTConstants.CONNECTION_STATUS_ESTABLISHED.equals(connect.getStatus())){
					//链路最近建立时间
					long currentConnectionTms = connect.getCreateTms();
					//链路重建申请时间
					long requestConnectionTms = task.getRequestTms();
					//链路重建申请时间晚于链路最近建立时间，则进行链路维护
					if(requestConnectionTms > currentConnectionTms){
						ConnectionSemaphore.setBusy(true);
						ConnectionSemaphore.setLastCreateTms(System.currentTimeMillis());
						ConnectionSemaphore.setConnectionNm(connectionNm);		
						ConnectionSemaphore.setLinkUUID(String.valueOf(UUID.randomUUID().toString()));
						connectionProcessor.maintainConnection(connectionNm);
					}
					//连接关闭（初始化链路状态也是这个）
				}else if(HSTConstants.CONNECTION_STATUS_CLOSED.equals(connect.getStatus())){
					ConnectionSemaphore.setBusy(true);
					ConnectionSemaphore.setLastCreateTms(System.currentTimeMillis());
					ConnectionSemaphore.setConnectionNm(connectionNm);				
					ConnectionSemaphore.setLinkUUID(String.valueOf(UUID.randomUUID().toString()));
					connectionProcessor.maintainConnection(connectionNm);					
				}else
					logger.info("Connection is creating");
					continue;
			}else{
				logger.info("Connection processor is busy,[" + ConnectionSemaphore.getConnectionNm() + "]" + " is creating");
				if((System.currentTimeMillis() - ConnectionSemaphore.getLastCreateTms()) > HSTConstants.CONNECTION_CREATING_MAX_MILLIS){					
					ConnectionPool.getInstance().getConnection(ConnectionSemaphore.getConnectionNm()).setStatus(HSTConstants.CONNECTION_STATUS_CLOSED);
					ConnectionSemaphore.setBusy(false);
					logger.info("Semaphore is opened");
				}
//				continue;
				break;
			}
		}
	}
}
