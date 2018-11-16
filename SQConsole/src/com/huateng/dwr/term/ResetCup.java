/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-7-6       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.dwr.term;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.huateng.system.util.SocketConnect;

/**
 * Title: 银联密钥重置
 * 
 * File: T10209.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-6
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author zxc
 * 
 * @version 1.0
 */
public class ResetCup {
	
	private static Logger log = Logger.getLogger(ResetCup.class);
	
	public static String resetCup(String flag) throws IOException {
		StringBuffer result = new StringBuffer();
		//交易码
		StringBuffer req = new StringBuffer();
		if(flag.equals("01") || flag.equals("02")) {
//			req = new StringBuffer("00066071");
			log.info("银联密钥重置("+(flag.equals("01")?"PINK":"MACK")+")");
			req.append("00066071");
			req.append(flag);
		}else if (flag.equals("03")) { //银联线路测试
			log.info("银联线路测试");
			req.append("00046131");
		}else if (flag.equals("04")) { //机构签到
			log.info("机构签到");
			req.append("00046111");
		}else if (flag.equals("05")) { //机构签退
			log.info("机构签退");
			req.append("00046121");
		}
		
		try {
			
			log.info("发送报文:["+ req.toString() + "]");
			String resp = SocketConnect.sendMessageCup(req.toString(),"UTF-8");
			log.info("响应报文:[" + resp + "]");
			if(resp.substring(0, 2).equals("00"))
				result.append("S");
			else
				result.append("F");
			return result.toString();
		} catch (Exception e) {
			log.error(e);
		}
		return "E银联密钥重置失败";
	}
			
	public static void main(String[] args) {
		try {
			resetCup("02");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
