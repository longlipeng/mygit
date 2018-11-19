package com.huateng.hstserver.communicate.mina.comm.common.filter.logging;

//import org.apache.log4j.Level;   
//import org.apache.log4j.Logger; 
import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.IoFilterAdapter;   
import org.apache.mina.core.session.IdleStatus;   
import org.apache.mina.core.session.IoSession;   
import org.apache.mina.core.write.WriteRequest;   
import org.apache.mina.filter.logging.LogLevel;   
import org.slf4j.helpers.MessageFormatter;   




public class PackLogFilter extends IoFilterAdapter {   
    /** The logger name */   
    private final String name;   
    
    /** Message getter which get message from message object and it's getter setter */
    ILogMessGetter messGetter = new ILogMessGetter(){
    	public Object getMessFromObj(Object message)
    	{
    		return message;
    	}
    };
    
    public ILogMessGetter getMessGetter() {
		return messGetter;
	}

	public void setMessGetter(ILogMessGetter messGetter) {
		this.messGetter = messGetter;
	}
	
	
	/** The logger */   
    private final Logger logger;   
       
    /** The log level for the exceptionCaught event. Default to WARN. */   
    private LogLevel exceptionCaughtLevel = LogLevel.WARN;   
       
    /** The log level for the messageSent event. Default to INFO. */   
    private LogLevel messageSentLevel = LogLevel.INFO;   
       
    /** The log level for the messageReceived event. Default to INFO. */   
    private LogLevel messageReceivedLevel = LogLevel.INFO;   
       
    /** The log level for the sessionCreated event. Default to INFO. */   
    private LogLevel sessionCreatedLevel = LogLevel.INFO;   
       
    /** The log level for the sessionOpened event. Default to INFO. */   
    private LogLevel sessionOpenedLevel = LogLevel.INFO;   
       
    /** The log level for the sessionIdle event. Default to INFO. */   
    private LogLevel sessionIdleLevel = LogLevel.INFO;   
       
    /** The log level for the sessionClosed event. Default to INFO. */   
    private LogLevel sessionClosedLevel = LogLevel.INFO;   
       
    /**  
     * Default Constructor.  
     */   
    public PackLogFilter(Logger logger) {   
        this.logger=logger;   
        this.name=logger.getName();   
    }   
    
    public PackLogFilter()
    {
    	this(Logger.getLogger("root"));
//    	Logger logger = 
    	
    }
        
        
    /**  
     * @return The logger's name  
     */   
    public String getName() {   
        return name;   
    }   
       
    
//    protected Object getMessFromObj(Object message)
//	{
//		
//		String messageStr = null;
//		if (message instanceof CommMessage)
//		{
//			CommMessage outmessage=(CommMessage) message;
//			return outmessage.getMessagbody();
//		}
//		else if (message instanceof IoBuffer)
//		{
//			IoBuffer messBuffer = (IoBuffer) message;
//			int messLen = messBuffer.remaining();
//			byte[] messBytes = new byte[messLen];
//			messBuffer.get(messBytes, 0, messLen);
//			messageStr = DataTransTools.bytesToHexString(messBytes);
//			messBuffer.position(0);
//		}
//		else
//		{
//			messageStr = "Unknown message type";
//		}
//		
//		return messageStr;
//	}
    
    /**  
     * Log if the logger and the current event log level are compatible. We log  
     * a message and an exception.  
     *  
     * @param eventLevel the event log level as requested by the user  
     * @param message the message to log  
     * @param cause the exception cause to log  
     */   
    protected void log(LogLevel eventLevel, String message, Throwable cause) {   
        if (eventLevel == LogLevel.TRACE) {   
            logger.trace(message, cause);   
        } else if (eventLevel == LogLevel.INFO) {   
            logger.info(message, cause);   
        } else if (eventLevel == LogLevel.WARN) {   
            logger.warn(message, cause);   
        } else if (eventLevel == LogLevel.ERROR) {   
            logger.error(message, cause);   
        }   
    }   
    /**  
     * Log if the logger and the current event log level are compatible. We log  
     * a formated message and its parameters.  
     *  
     * @param eventLevel the event log level as requested by the user  
     * @param message the formated message to log  
     * @param param the parameter injected into the message  
     */   
    protected void log(LogLevel eventLevel, String message, Object param) {   
        String msgStr = MessageFormatter.format(message, param).toString();   
        if (eventLevel == LogLevel.TRACE) {   
//            logger.log(name, Level.TRACE, msgStr, null);  
            logger.trace(msgStr);
            
        } else if (eventLevel == LogLevel.INFO) {   
//            logger.log(name, Level.INFO, msgStr, null);   
        	logger.info(msgStr);
        } else if (eventLevel == LogLevel.WARN) {   
//            logger.log(name, Level.WARN, msgStr, null);   
        	logger.warn(msgStr);
        } else if (eventLevel == LogLevel.ERROR) {   
//            logger.log(name, Level.ERROR, msgStr, null);   
            logger.error(msgStr);
        }   
    }   
    /**  
     * Log if the logger and the current event log level are compatible. We log  
     * a simple message.  
     *  
     * @param eventLevel the event log level as requested by the user  
     * @param message the message to log  
     */   
    protected void log(LogLevel eventLevel, String message) {   
        if (eventLevel == LogLevel.TRACE) {   
            logger.trace(message);   
        } else if (eventLevel == LogLevel.INFO) {   
            logger.info(message);   
        } else if (eventLevel == LogLevel.WARN) {   
            logger.warn(message);   
        } else if (eventLevel == LogLevel.ERROR) {   
            logger.error(message);   
        }   
    }   
    @Override   
    public void exceptionCaught(NextFilter nextFilter, IoSession session,   
            Throwable cause) throws Exception {   
        log(exceptionCaughtLevel, "EXCEPTION :", cause);   
        nextFilter.exceptionCaught(session, cause);   
    }   
    @Override   
    public void messageReceived(NextFilter nextFilter, IoSession session,   
            Object message) throws Exception {
//    	CommMessage outmessage=(CommMessage) message;
        log(messageReceivedLevel, "RECEIVED: {}", messGetter.getMessFromObj(message));
        nextFilter.messageReceived(session, message);   
    }   
    @Override   
    public void messageSent(NextFilter nextFilter, IoSession session,   
            WriteRequest writeRequest) throws Exception {   
        log(messageSentLevel, "SENT: {}", messGetter.getMessFromObj(writeRequest.getMessage()));   
        nextFilter.messageSent(session, writeRequest);   
    }   
    @Override   
    public void sessionCreated(NextFilter nextFilter, IoSession session)   
            throws Exception {   
        log(sessionCreatedLevel, "CREATED");   
        nextFilter.sessionCreated(session);   
    }   
    @Override   
    public void sessionOpened(NextFilter nextFilter, IoSession session)   
    throws Exception {   
        log(sessionOpenedLevel, "OPENED");   
        nextFilter.sessionOpened(session);   
    }   
    @Override   
    public void sessionIdle(NextFilter nextFilter, IoSession session,   
            IdleStatus status) throws Exception {   
        log(sessionIdleLevel, "IDLE");   
        nextFilter.sessionIdle(session, status);   
    }   
    @Override   
    public void sessionClosed(NextFilter nextFilter, IoSession session) throws Exception {   
        log(sessionClosedLevel, "CLOSED");   
        nextFilter.sessionClosed(session);   
    }   
       
    /**  
     * Set the LogLevel for the ExceptionCaught event.  
     *  
     * @param level The LogLevel to set  
     */   
    public void setExceptionCaughtLoglevel(LogLevel level) {   
        exceptionCaughtLevel = level;   
    }   
       
    /**  
     * Get the LogLevel for the ExceptionCaught event.  
     *  
     * @return The LogLevel for the ExceptionCaught eventType  
     */   
    public LogLevel getExceptionCaughtLoglevel() {   
        return exceptionCaughtLevel;   
    }   
       
    /**  
     * Set the LogLevel for the MessageReceived event.  
     *  
     * @param level The LogLevel to set  
     */   
    public void setMessageReceivedLoglevel(LogLevel level) {   
        messageReceivedLevel = level;   
    }   
       
    /**  
     * Get the LogLevel for the MessageReceived event.  
     *  
     * @return The LogLevel for the MessageReceived eventType  
     */   
    public LogLevel getMessageReceivedLoglevel() {   
        return messageReceivedLevel;   
    }   
       
    /**  
     * Set the LogLevel for the MessageSent event.  
     *  
     * @param level The LogLevel to set  
     */   
    public void setMessageSentLoglevel(LogLevel level) {   
        messageSentLevel = level;   
    }   
       
    /**  
     * Get the LogLevel for the MessageSent event.  
     *  
     * @return The LogLevel for the MessageSent eventType  
     */   
    public LogLevel getMessageSentLoglevel() {   
        return messageSentLevel;   
    }   
       
    /**  
     * Set the LogLevel for the SessionCreated event.  
     *  
     * @param level The LogLevel to set  
     */   
    public void setSessionCreatedLoglevel(LogLevel level) {   
        sessionCreatedLevel = level;   
    }   
       
    /**  
     * Get the LogLevel for the SessionCreated event.  
     *  
     * @return The LogLevel for the SessionCreated eventType  
     */   
    public LogLevel getSessionCreatedLoglevel() {   
        return sessionCreatedLevel;   
    }   
       
    /**  
     * Set the LogLevel for the SessionOpened event.  
     *  
     * @param level The LogLevel to set  
     */   
    public void setSessionOpenedLoglevel(LogLevel level) {   
        sessionOpenedLevel = level;   
    }   
       
    /**  
     * Get the LogLevel for the SessionOpened event.  
     *  
     * @return The LogLevel for the SessionOpened eventType  
     */   
    public LogLevel getSessionOpenedLoglevel() {   
        return sessionOpenedLevel;   
    }   
       
    /**  
     * Set the LogLevel for the SessionIdle event.  
     *  
     * @param level The LogLevel to set  
     */   
    public void setSessionIdleLoglevel(LogLevel level) {   
        sessionIdleLevel = level;   
    }   
       
    /**  
     * Get the LogLevel for the SessionIdle event.  
     *  
     * @return The LogLevel for the SessionIdle eventType  
     */   
    public LogLevel getSessionIdleLoglevel() {   
        return sessionIdleLevel;   
    }   
       
    /**  
     * Set the LogLevel for the SessionClosed event.  
     *  
     * @param level The LogLevel to set  
     */   
    public void setSessionClosedLoglevel(LogLevel level) {   
        sessionClosedLevel = level;   
    }   
    /**  
     * Get the LogLevel for the SessionClosed event.  
     *  
     * @return The LogLevel for the SessionClosed eventType  
     */   
    public LogLevel getSessionClosedLoglevel() {   
        return sessionClosedLevel;   
    }   
} 
