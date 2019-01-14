package com.huateng.hstserver.communicate.mina.comm.common.filter.connectmaintain;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class SilentListenerHeartBeatMessageFactory implements KeepAliveMessageFactory {

	public Object getRequest(IoSession iosession) {
		
		return null;
	}

	public Object getResponse(IoSession iosession, Object obj) {
		
		return null;
	}

	public boolean isRequest(IoSession iosession, Object obj) {
		
		return false;
	}

	public boolean isResponse(IoSession iosession, Object obj) {
		
		return false;
	}

}
