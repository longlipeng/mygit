package com.huateng.hstserver.communicate.mina.comm.common.connection;

import java.net.InetSocketAddress;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.log4j.Logger;
import com.huateng.hstserver.config.HSTProperties;
import com.huateng.hstserver.constants.HSTConstants;


public class ConnectionProcessor {
	private static final Logger logger = Logger.getLogger(ConnectionProcessor.class);
	
	private static ConnectionProcessor instance;
	
	private ConnectionProcessor(){
		
	}
	
	/**
	 * singleton mode
	 * @return
	 */
	public  static ConnectionProcessor getInstance(){
		if (instance == null)
			instance = new ConnectionProcessor();
		return instance;
	}
	
	private IoConnector connector = null;


	public boolean maintainConnection(String connectionNm){		
		/**发起测试交易，判定链路（JAVA->C;C->JAVA）的有效性*/
		if(testConnection(connectionNm)){
			//如果链路正常，则修改链路状态为"已链接"，链路状态："已关闭"，"建立中"，"已链接"
			ConnectionPool.getInstance().setConnectionStatus(connectionNm,HSTConstants.CONNECTION_STATUS_ESTABLISHED);
			return true;
		}		
		
		/**关闭当前链路*/
		ConnectionPool.getInstance().setConnectionStatus(connectionNm,HSTConstants.CONNECTION_STATUS_CLOSED);
		ConnectionPool.getInstance().closeConnection(connectionNm);
		/**修改链路状态为"建立中"*/
		ConnectionPool.getInstance().setConnectionStatus(connectionNm,HSTConstants.CONNECTION_STATUS_CREATING);
		
		/**建立链路，更新链路的时间戳*/		
		return createConnection(connectionNm);
	}

	/**
	 * create connection by connection name
	 * @param connectionNm
	 * @return
	 */
	private boolean createConnection(String connectionNm){			
		//通过配置文件获取链路中C端的的IP和PORT
		String ip ;
		int port ;
		if(connectionNm.equals(HSTConstants.CONNECTION_TXN_1)){
			ip = HSTProperties.getString("TXN_IP_1");
			port = Integer.parseInt(HSTProperties.getString("TXN_PORT_1"));
		}else if(connectionNm.equals(HSTConstants.CONNECTION_ACC_1)){
			ip = HSTProperties.getString("ACC_IP_1");
			port = Integer.parseInt(HSTProperties.getString("ACC_PORT_1"));
		}else if(connectionNm.equals(HSTConstants.CONNECTION_STL_1)){
			ip = HSTProperties.getString("STL_IP_1");
			port = Integer.parseInt(HSTProperties.getString("STL_PORT_1"));
		}else if(connectionNm.equals(HSTConstants.CONNECTION_TXN_2)){
			ip = HSTProperties.getString("TXN_IP_2");
			port = Integer.parseInt(HSTProperties.getString("TXN_PORT_2"));
		}else if(connectionNm.equals(HSTConstants.CONNECTION_ACC_2)){
			ip = HSTProperties.getString("ACC_IP_2");
			port = Integer.parseInt(HSTProperties.getString("ACC_PORT_2"));
		}else if(connectionNm.equals(HSTConstants.CONNECTION_STL_2)){
			ip = HSTProperties.getString("STL_IP_2");
			port = Integer.parseInt(HSTProperties.getString("STL_PORT_2"));
		}else{
			return false;
		}
		InetSocketAddress remoteAddress = new InetSocketAddress(ip, port);	
		
		//通过注入的IoConnector建立到C端的连接
		ConnectFuture connectfuture = connector.connect(remoteAddress);
		connectfuture.awaitUninterruptibly();//2014-12-24 同步请求
		IoSession session = null;
		try{
			session = connectfuture.getSession();
		}catch(Exception e){
			logger.error(e.getMessage());
			//释放链路维护标识
			ConnectionSemaphore.setBusy(false);
			ConnectionSemaphore.setConnectionNm(null);	
			//修改链路状态为“已关闭”
			ConnectionPool.getInstance().setConnectionStatus(connectionNm,HSTConstants.CONNECTION_STATUS_CLOSED);
			return false;
		}
		//如果未建立到C端的连接，则返回失败
		if(session == null)
			return false;		
		logger.info("Connected to " + connectionNm +", ip:{"+ip+"}, port:{"+port+"}");
		
		//如果建立了到C端的连接，则在链路中设置到C端的连接	
		Connection connect = ConnectionPool.getInstance().getConnection(connectionNm);
		//将链路名称放入session，用于在seesion closed事件时区分链路
		session.setAttribute(HSTConstants.REMOTE_IP, ip);
		session.setAttribute(HSTConstants.REMOTE_PORT, String.valueOf(port));
		session.setAttribute(HSTConstants.CONNECTION_NM,connectionNm);
		session.setAttribute(HSTConstants.LINK_UUID,ConnectionSemaphore.getLinkUUID());
		connect.setClientSession(session);		
		return true;	
	}

	/**
	 * test connection
	 * @param connectionNm
	 * @return
	 */
	public boolean testConnection(String connectionNm){
		Connection connect = ConnectionPool.getInstance().getConnection(connectionNm);
		if(connect == null)
			return false;
		//TODO:发起链路测试
		return false;
	}	
	

	
	
	public IoConnector getConnector() {
		return connector;
	}

	public void setConnector(IoConnector connector) throws Exception {
		if (this.connector == null)
		{
			if (connector == null)
			{
				throw new Exception("Can not set a null connector");
			}
			connector.getSessionConfig().setUseReadOperation(true);
			this.connector = connector;
		}
		else
			throw new Exception("Can not reset the connector");
	}
}
