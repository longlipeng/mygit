package com.huateng.hstserver.communicate.mina.common.controller;

import com.huateng.hstserver.communicate.mina.comm.common.entity.CommMessage;

/**
 * 
 */



/**
 * @author charely
 *
 */
public interface Icontroller {
	public CommMessage serviceProcess(CommMessage inmessage) throws Exception;
}
