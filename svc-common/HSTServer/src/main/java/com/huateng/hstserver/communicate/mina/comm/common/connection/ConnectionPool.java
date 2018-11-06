package com.huateng.hstserver.communicate.mina.comm.common.connection;


import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import com.huateng.hstserver.communicate.mina.comm.common.quartz.ConnectionMaintainJob;
import com.huateng.hstserver.config.HSTProperties;
import com.huateng.hstserver.constants.HSTConstants;


public class ConnectionPool {
	private  Logger logger = Logger.getLogger(ConnectionPool.class);
	private ConnectionPool(){		
	}
	
	private static ConnectionPool instance;
	
	public static ConnectionPool getInstance(){
		if(instance == null)
			instance = new ConnectionPool();
		return instance;
	}
	
	//链路池
	private ConcurrentHashMap<String, Connection> pool = new ConcurrentHashMap<String, Connection>();
	
	//TXN主备标识
	private boolean hasTxnBackupConnection;
	//TXN调用次数计数
	private int txnConnectionUsedSum;	
	
	//ACC主备标识
	private boolean hasAccBackupConnection;
	//ACC调用次数计数
	private int accConnectionUsedSum;	
	
	
	//STL主备标识
	private boolean hasStlBackupConnection;
	//STL调用次数计数
	private int stlConnectionUsedSum;	
	
	
	public Connection putConnection(String connectionNm, Connection connect){
		return pool.put(connectionNm,connect);
	}
	
	public Connection getConnection(String connectionNm){
		return pool.get(connectionNm);
	}

	public ConcurrentHashMap<String, Connection> getPool(){
		return pool;
	}

	/**
	 * initial connection to TXN/ACC/STL when application startup
	 * @return
	 */
	public void initialConnection(boolean txn,boolean acc,boolean stl){		
		if(txn){
			logger.info("initial TXN Connection");
			//链路池放入初始化链路
			Connection txnConnect = new Connection();		
			txnConnect.setConnectionNm(HSTConstants.CONNECTION_TXN_1);
			txnConnect.setStatus(HSTConstants.CONNECTION_STATUS_CLOSED);
			putConnection(txnConnect.getConnectionNm(), txnConnect);
			//队列插入链路创建任务
			ConnectionMaintainJob.addTask(HSTConstants.CONNECTION_TXN_1);	
			//设置TXN初始调用次数为0
			txnConnectionUsedSum = 0;
			
			//初始化备用链路并往队列插入链路创建任务
			if (HSTProperties.getString("TXN_IP_2") != null
					&& !"".equals(HSTProperties.getString("TXN_IP_2"))) {
				Connection txnConnect2 = new Connection();		
				txnConnect2.setConnectionNm(HSTConstants.CONNECTION_TXN_2);
				txnConnect2.setStatus(HSTConstants.CONNECTION_STATUS_CLOSED);
				putConnection(txnConnect2.getConnectionNm(), txnConnect2);
				ConnectionMaintainJob.addTask(HSTConstants.CONNECTION_TXN_2);
				hasTxnBackupConnection = true;
			}

		}
		if(acc){
			logger.info("initial ACC Connection");
			Connection accConnect = new Connection();	
			accConnect.setConnectionNm(HSTConstants.CONNECTION_ACC_1);
			accConnect.setStatus(HSTConstants.CONNECTION_STATUS_CLOSED);
			putConnection(accConnect.getConnectionNm(), accConnect);
			ConnectionMaintainJob.addTask(HSTConstants.CONNECTION_ACC_1);
			//设置ACC初始调用次数为0
			accConnectionUsedSum = 0;
			
			if (HSTProperties.getString("ACC_IP_2") != null
					&& !"".equals(HSTProperties.getString("ACC_IP_2"))) {
				Connection accConnect2 = new Connection();		
				accConnect2.setConnectionNm(HSTConstants.CONNECTION_ACC_2);
				accConnect2.setStatus(HSTConstants.CONNECTION_STATUS_CLOSED);
				putConnection(accConnect2.getConnectionNm(), accConnect2);
				ConnectionMaintainJob.addTask(HSTConstants.CONNECTION_ACC_2);
				hasAccBackupConnection = true;
			}
		}
		if(stl){
			logger.info("initial STL Connection");
			Connection stlConnect = new Connection();	
			stlConnect.setConnectionNm(HSTConstants.CONNECTION_STL_1);
			stlConnect.setStatus(HSTConstants.CONNECTION_STATUS_CLOSED);
			putConnection(stlConnect.getConnectionNm(), stlConnect);
			ConnectionMaintainJob.addTask(HSTConstants.CONNECTION_STL_1);
			//设置STL初始调用次数为0
			stlConnectionUsedSum = 0;
			
			if (HSTProperties.getString("STL_IP_2") != null
					&& !"".equals(HSTProperties.getString("STL_IP_2"))) {
				Connection stlConnect2 = new Connection();		
				stlConnect2.setConnectionNm(HSTConstants.CONNECTION_STL_2);
				stlConnect2.setStatus(HSTConstants.CONNECTION_STATUS_CLOSED);
				putConnection(stlConnect2.getConnectionNm(), stlConnect2);
				ConnectionMaintainJob.addTask(HSTConstants.CONNECTION_STL_2);
				hasStlBackupConnection = true;
			}
		}
	}
	
	
	/**
	 * 获取备用链路
	 * @param connectionNm
	 * @return
	 */
	public String getBackupConnectionNm(String connectionNm){
		if(connectionNm.equals(HSTConstants.CONNECTION_TXN_1))
			if(hasTxnBackupConnection)
				return HSTConstants.CONNECTION_TXN_2;
			else
				return null;
		else if(connectionNm.equals(HSTConstants.CONNECTION_TXN_2))
			return HSTConstants.CONNECTION_TXN_1;
		else if(connectionNm.equals(HSTConstants.CONNECTION_ACC_1))
			if(hasAccBackupConnection)
				return HSTConstants.CONNECTION_ACC_2;
			else
				return null;
		else if(connectionNm.equals(HSTConstants.CONNECTION_ACC_2))
				return HSTConstants.CONNECTION_ACC_1;
		else if(connectionNm.equals(HSTConstants.CONNECTION_STL_1))
			if(hasStlBackupConnection)
				return HSTConstants.CONNECTION_STL_2;
			else
				return null;
		else if(connectionNm.equals(HSTConstants.CONNECTION_STL_2))
			return HSTConstants.CONNECTION_STL_1;	
		else
			return connectionNm;
	}
	
	/**
	 * get connection by system name 
	 * @param sysNm 
	 * @return connection
	 */
	public synchronized Connection getConnectionBySys(String sysNm){	
		String connectionNm ;
		if(sysNm.equals(HSTConstants.SYS_TXN)){
			if(hasTxnBackupConnection){
				logger.info("对否有备用链路：" + hasTxnBackupConnection);
				if(txnConnectionUsedSum % 2 ==0)
					connectionNm = HSTConstants.CONNECTION_TXN_1;
				else
					connectionNm = HSTConstants.CONNECTION_TXN_2;
				logger.info("有备用链路，链路号："   +  connectionNm);
				if(txnConnectionUsedSum > 999999999)
					txnConnectionUsedSum = 0;
				else
					txnConnectionUsedSum++;
			}else{
				connectionNm = HSTConstants.CONNECTION_TXN_1;
				logger.info("无备用链路，链路号："   +  connectionNm);
			}
			
		}else if(sysNm.equals(HSTConstants.SYS_ACC)){
			if(hasAccBackupConnection){
				if(accConnectionUsedSum % 2 ==0)
					connectionNm = HSTConstants.CONNECTION_ACC_1;
				else
					connectionNm = HSTConstants.CONNECTION_ACC_2;
				if(accConnectionUsedSum > 999999999)
					accConnectionUsedSum = 0;
				else
					accConnectionUsedSum++;
			}else{
				connectionNm = HSTConstants.CONNECTION_ACC_1;
			}	
		}else if(sysNm.equals(HSTConstants.SYS_STL)){
			if(hasStlBackupConnection){
				if(stlConnectionUsedSum % 2 ==0)
					connectionNm = HSTConstants.CONNECTION_STL_1;
				else
					connectionNm = HSTConstants.CONNECTION_STL_2;
				if(stlConnectionUsedSum > 999999999)
					stlConnectionUsedSum = 0;
				else
					stlConnectionUsedSum++;
			}else{
				connectionNm = HSTConstants.CONNECTION_STL_1;
			}		
		}else{
			return null;
		}
		
		//则按照负载均衡规则，取出链路		
		Connection connect = getConnection(connectionNm);
		logger.info("链路状态" + connect.getStatus() );
		//如果链路可用
		if(connect != null && HSTConstants.CONNECTION_STATUS_ESTABLISHED.equals(connect.getStatus()))
			return connect;
		else{
			//将不可用链路添加到链路维护任务
			ConnectionMaintainJob.addTask(connectionNm);
			//如果链路不可用，则切换至另外一条链路
			connectionNm = getBackupConnectionNm(connectionNm);
			connect = getConnection(connectionNm);
			if(connect != null && HSTConstants.CONNECTION_STATUS_ESTABLISHED.equals(connect.getStatus()))
				return connect;
			else{//主备链路都不可用
				//将不可用链路添加到链路维护任务
				ConnectionMaintainJob.addTask(connectionNm);
				return null;
			}
		}		
	}	
	
	/**
	 * close connection by connection name
	 * @param connectionNm
	 * @return
	 */
	public boolean closeConnection(String connectionNm){
		Connection connect = getConnection(connectionNm);
		if(connect != null){
			IoSession clientSession = connect.getClientSession();
			IoSession serverSession = connect.getServerSession();
			if(clientSession != null && clientSession.isConnected())
				connect.getClientSession().close(true);
			if(serverSession != null && serverSession.isConnected())
				connect.getServerSession().close(true);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * set connection status
	 * @param connectionNm
	 * @param status
	 */
	public void setConnectionStatus(String connectionNm,String status){
		Connection connect = getConnection(connectionNm);
		if(connect != null)
			connect.setStatus(status);		
	}
	
	/**
	 * 关闭所有链路，一般用于停止应用的时候
	 */
	public void destroy(){
		Enumeration<Connection> connections = pool.elements();
		while(connections.hasMoreElements()){
			closeConnection(connections.nextElement().getConnectionNm());
		}
	}
}
