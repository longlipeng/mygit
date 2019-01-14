package com.huateng.univer.commmsg.impl;

import org.apache.log4j.Logger;
import com.huateng.univer.commmsg.CommMsgService;

public class RechargeCommMsgServiceImpl implements CommMsgService {
	Logger logger = Logger.getLogger(RechargeCommMsgServiceImpl.class);
	@Override
	public void commMsg() {
		
		logger.info("commMsg");
	}

}
