package com.huateng.hstserver.communicate.mina.comm.common.filter.protocolcodec.security;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;





public class DefaultNullPackageSecurityProcessor implements
		IPackageSecurityProcessor {

	@Override
	public void generateMac(CommMessage commMessage) throws Exception {
	
//		return null;
	}

	@Override
	public boolean isValidMac(CommMessage commMessage) throws Exception {
		
		return true;
	}

}
