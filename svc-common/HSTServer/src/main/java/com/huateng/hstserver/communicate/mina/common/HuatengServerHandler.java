package com.huateng.hstserver.communicate.mina.common;


import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.log4j.Logger;
import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.biz.BizMessageObj;


/**
 * The Class HuatengServerHandler.
 */
public class HuatengServerHandler extends IoHandlerAdapter
{
	
	
	/** The logger. */
	final Logger logger = Logger.getLogger(HuatengServerHandler.class);
	
	
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
		
		String remoteIp = (String)iosession.getAttribute("remoteIp");
		String remotePort = (String)iosession.getAttribute("remotePort");
		
		logger.info("session closed, remote ip is: [" + remoteIp + "], remote port is: [" + remotePort +"]");
	}

	@Override
	public void sessionCreated(IoSession iosession) throws Exception {
		InetSocketAddress address  = (InetSocketAddress)iosession.getRemoteAddress();
		iosession.setAttribute("remoteIp", address.getAddress().getHostAddress());
		iosession.setAttribute("remotePort", String.valueOf(address.getPort()));
		logger.info("session created, remote ip is: [" + address.getAddress().getHostAddress() + "], remote port is: [" + address.getPort() +"]");
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
        logger.error(cause.getMessage());
 
    }


    /* (non-Javadoc)
     * @see org.apache.mina.core.service.IoHandlerAdapter#messageReceived(org.apache.mina.core.session.IoSession, java.lang.Object)
     */
    @Override 
    public void messageReceived( IoSession session, Object message ) throws Exception
    { 
    	logger.info("messageReceived : " );
    	CommMessage mes = (CommMessage)message;
    	String packageNo = ((BizMessageObj)mes.getMessageObject()).getPackageNo();
    	logger.info("recieved message seqno is : " + packageNo);
    	((BizMessageObj)mes.getMessageObject()).setPackageNo(packageNo+"_no");
    	
    	session.write(message);
    }
    
    
    

    @Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.info("sending message");
		logger.info("Message len is" + ((CommMessage)message).getLength());
		super.messageSent(session, message);
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
    }

	/**
	 * Gets the bussinesscontroller.
	 * 
	 * @return the bussinesscontroller
	 */

}
