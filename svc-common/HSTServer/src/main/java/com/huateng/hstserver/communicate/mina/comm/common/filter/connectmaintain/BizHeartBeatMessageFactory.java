package com.huateng.hstserver.communicate.mina.comm.common.filter.connectmaintain;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;
import com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.app.biz.BizMessageObj;
import com.huateng.hstserver.config.HSTProperties;
import com.huateng.hstserver.constants.HSTConstants;
import com.huateng.hstserver.frameworkUtil.StringUtils;



public class BizHeartBeatMessageFactory implements KeepAliveMessageFactory {

	public Object getRequest(IoSession iosession) {
		//TODO:根据KEY值判定该连接是与哪个系统(TXN/ACC/STL)的连接
		String remoteServiceNm = null;
		String localServiceNm = null;
		String sysNm = (String)iosession.getAttribute("SYS_NM");
		if(sysNm.equals(HSTConstants.SYS_TXN)){
			remoteServiceNm = HSTConstants.TXN_HEART_BEAT;
		}else if(sysNm.equals(HSTConstants.SYS_ACC)){
			remoteServiceNm = HSTConstants.ACC_HEART_BEAT;
		}else if(sysNm.equals(HSTConstants.SYS_STL)){
			remoteServiceNm = HSTConstants.STL_HEART_BEAT;
		}
		
		CommMessage heartBeatmes = new CommMessage();		
		BizMessageObj messageObj = new BizMessageObj();
		messageObj.setServiceName(remoteServiceNm);
		localServiceNm = HSTProperties.getString("SERVICE_NM_HEARTBEAT");
		String localServiceNmLenStr = StringUtils.addZero(String.valueOf(localServiceNm.length()), 2);
		messageObj.setOtherdata(localServiceNmLenStr + localServiceNm + "0000000000");
		heartBeatmes.setMessageObject(messageObj);
		
		return heartBeatmes;
	}

	public Object getResponse(IoSession iosession, Object obj) {
		CommMessage heartBeatmes = new CommMessage();
		
		BizMessageObj messageObj = new BizMessageObj();
		
		heartBeatmes.setMessageObject(messageObj);
		
		return heartBeatmes;
	}

	public boolean isRequest(IoSession iosession, Object obj) {
		CommMessage heartBeatmes;
		try
		{
			heartBeatmes = (CommMessage) obj;
		}
		catch (Exception e)
		{
			return false;
		}
		
		if (heartBeatmes.getLength() == 1)
		{
			return true;
		}
		
		return false;
	}

	public boolean isResponse(IoSession iosession, Object obj) {
		CommMessage heartBeatmes;
		try
		{
			heartBeatmes = (CommMessage) obj;
		}
		catch (Exception e)
		{
			return false;
		}
		
		if (((BizMessageObj)heartBeatmes.getMessageObject()).getPackageNo() == null)
		{
			return true;
		}
		
		return false;

	}

}
