package com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.security;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;



public interface IPackageSecurityProcessor {
	
	public boolean isValidMac(CommMessage commMessage) throws Exception;
	
	public void generateMac(CommMessage commMessage) throws Exception;

}
