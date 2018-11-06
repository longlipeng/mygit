package com.huateng.hstserver.communicate.mina.comm.common.filter.connectmaintain;



import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.filterchain.IoFilter.NextFilter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

public class JustCloseWhileIdle extends IoFilterAdapter {

	private int sessionTimeOutTimeSec = 0;
	
	private IdleStatus interestedIdleStatus = IdleStatus.BOTH_IDLE;

	public int getSessionTimeOutTimeSec() {
		return sessionTimeOutTimeSec;
	}

	public void setSessionTimeOutTimeSec(int sessionTimeOutTimeSec) {
		this.sessionTimeOutTimeSec = sessionTimeOutTimeSec;
	}

	public IdleStatus getInterestedIdleStatus() {
		return interestedIdleStatus;
	}

	public void setInterestedIdleStatus(IdleStatus interestedIdleStatus) {
		this.interestedIdleStatus = interestedIdleStatus;
	}

	
	
	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session,
			Object message) throws Exception {
		
		super.messageReceived(nextFilter, session, message);
	}

	@Override
	public void messageSent(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) throws Exception {
		
		super.messageSent(nextFilter, session, writeRequest);
	}

	@Override
	public void sessionCreated(NextFilter nextFilter, IoSession session)
			throws Exception {
		if (sessionTimeOutTimeSec > 0)
		{
			session.getConfig().setIdleTime(interestedIdleStatus, sessionTimeOutTimeSec);
		}
		super.sessionCreated(nextFilter, session);
	}

	@Override
	public void sessionIdle(NextFilter nextFilter, IoSession session,
			IdleStatus status) throws Exception {
		
		nextFilter.sessionIdle(session, status);
		session.close(false);
	}

}
