package com.huateng.hstserver.communicate.mina.common.handler;



import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.log4j.Logger;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.common.controller.Icontroller;



/**
 * The Class HuatengServerHandler.
 */
public class ServerCommConnector extends IoHandlerAdapter
{
	
	
	/** The logger. */
	final Logger logger = Logger.getLogger(ServerCommConnector.class);
	
	/** 业务接口*. */
	private Icontroller bussinesscontroller=null;
	
	/** The shortconnect. */
	private boolean shortconnect;
    
    /**
     * Checks if is shortconnect.
     * 
     * @return true, if is shortconnect
     */
    public boolean isShortconnect() {
		return shortconnect;
	}

	/**
	 * Sets the shortconnect.
	 * 
	 * @param shortconnect the new shortconnect
	 */
	public void setShortconnect(boolean shortconnect) {
		this.shortconnect = shortconnect;
	}
	
	@Override
	public void sessionClosed(IoSession iosession) throws Exception {
		
		
		//logger.info("session closed, remote ip is: [" + remoteIp + "], remote port is: [" + remotePort +"]");
	}

	@Override
	public void sessionCreated(IoSession iosession) throws Exception {
		//logger.info("session created, remote ip is: [" + address.getAddress().getHostAddress() + "], remote port is: [" + address.getPort() +"]");
	}


	/**
	 * Trap exceptions.
	 * 
	 * @param session the session
	 * @param cause the cause
	 * 
	 * @throws Exception the exception
	 */
    @Override
    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
    {
    	//client 关断时也会触发该异常
      //  causlogger.error(e.getMessage());
 
    }


    /* (non-Javadoc)
     * @see org.apache.mina.core.service.IoHandlerAdapter#messageReceived(org.apache.mina.core.session.IoSession, java.lang.Object)
     */
    @Override 
    public void messageReceived( IoSession session, Object message ) throws Exception
    { 
      //首先先接收一干完整帧/包的数据
    	CommMessage Receivedmessage=(CommMessage)message;
    	
    	//将请求报文写入日志文件
//    	LogInfo.getLogInstance(Receivedmessage);
    	//然后直接调用统一的业务接口
    	CommMessage Sendmessage=bussinesscontroller.serviceProcess(Receivedmessage);
    	//将应答报文写入日志文件
//    	LogInfo.getLogInstance(Sendmessage);
    	//最后发回处理后的报文
        session.write(Sendmessage);
        
    }

    /**
     * On idle, we just write a message on the console.
     * 
     * @param session the session
     * @param status the status
     * 
     * @throws Exception the exception
     */
    @Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
        logger.info( "IDLE " + session.getIdleCount( status ));
    }

	/**
	 * Gets the bussinesscontroller.
	 * 
	 * @return the bussinesscontroller
	 */
	public Icontroller getBussinesscontroller() {
		return bussinesscontroller;
	}

	/**
	 * Sets the bussinesscontroller.
	 * 
	 * @param bussinesscontroller the new bussinesscontroller
	 */
	public void setBussinesscontroller(Icontroller bussinesscontroller) {
		this.bussinesscontroller = bussinesscontroller;
	}
}
