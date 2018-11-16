/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-7-28       first release
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
package com.huateng.dwr.settle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.log.Log;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SocketConnect;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: HandleOfBatch.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-28
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class HandleOfBatch {
	
	private static Logger log = Logger.getLogger(HandleOfBatch.class);
	/**
	 * 发送任务启动报文给后台系统
	 * 
	 * @return
	 * 2011-7-28上午11:38:49
	 * 20120920添加参数endDate
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public String sendMsg(String batId, String date, String endDate) throws IOException{
		if (StringUtil.isNull(batId)) {
			return "没有正确获取任务编号";
		}
		if (StringUtil.isNull(date)) {
			return "没有正确获取日终开始日期";
		}
		if (StringUtil.isNull(endDate)) {
			return "没有正确获取日终结束日期";
		}
		//组装报文
		StringBuffer message = new StringBuffer();
//		message.append("AAAA");//中行对账标识符
		message.append(batId);/* 任务编号 */
		message.append(date); /* 日终开始日期 */
		message.append(endDate); /* 日终结束日期 */
		message.append(CommonFunction.fillString("  ", ' ', 2 + 256 + 2 + 256, true));
		

		String ip = SysParamUtil.getParam("SETTLEIP");
		//int port = 30001;
		String port =SysParamUtil.getParam("SETTLEPORT");
		String timeOut=SysParamUtil.getParam("TIMEOUT");
		
		String sendMessage = CommonFunction.fillString(String.valueOf(message.toString().length()), '0', 4, false);
		sendMessage += message.toString();
		System.out.println("send:" + sendMessage);
		String ret = "";
		
		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputstream = null;
		
		try {
			//发送
			socket = new Socket(ip,Integer.valueOf(port));
			socket.setSoTimeout(Integer.valueOf(timeOut)*30);//对账处理时间可能时间比较长所以需要将超时设置为30分钟
			outputStream = socket.getOutputStream();
			outputStream.write(sendMessage.getBytes()); 
			outputStream.flush();
			
			/*BufferedReader br = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			
			String temp;
			while(true){
				temp = br.readLine();
				if(!StringUtil.isNull(temp)){
					ret += temp;
				}else{
					break;
				}
			}*/
			
			InputStream is = socket.getInputStream();
			
			byte[] serverSay = new byte[1024*1024];
			int len = is.read(serverSay);//接受服务器消息
			ret=new String(serverSay, 0, len);
			System.out.println("receive:"+ret);

			if (!StringUtil.isNull(ret) && ret.length() > 273) {
				if ("00".equals(ret.substring(283, 285))) {
					try{
						if("K0005".equals(batId.toUpperCase()) || "H0004".equals(batId.toUpperCase())){
							//对账结束需要返回对账结果
							 String failSql="select count(*),Sum(acq_amt_trans)/100 from bth_dtl_err_gc Where date_settlmt='"+date+"'";
							 List<Object[]> list=CommonFunction.getCommQueryDAO().findBySQLQuery(failSql);
							 if(0==((BigDecimal)list.get(0)[0]).intValue()){//对账平
								 return "ST对账平";
							 }else{
								 String totalSql="select count(*) from bth_gc_txn_succ where DATE_SETTLMT_8='"+date+"'";
								 String total=CommonFunction.getCommQueryDAO().findCountBySQLQuery(totalSql);
								 if(Integer.parseInt(total) == 0){//根据测试环境要求，处理posp没有成功交易的情况，做信息提示。20121011
									 Log.log("对账不平，当日POSP没有成功交易。");
									 return "ST" + "对账不平，当日POSP没有成功交易。";
								 }else{
									 double d= (((BigDecimal)list.get(0)[0]).longValue()*10000/Integer.parseInt(total))/100d;
									 Log.log("对账不平，不平总笔数["+((BigDecimal)list.get(0)[0]).intValue()+"],不平总金额["+((BigDecimal)list.get(0)[1]).doubleValue()+"]元，对账不平率(笔数)["+d+"%]");
									 return "ST"+"对账不平，不平总笔数["+((BigDecimal)list.get(0)[0]).intValue()+"],不平总金额["+((BigDecimal)list.get(0)[1]).doubleValue()+"]元，对账不平率(笔数)["+d+"%] \n\r";
								 }
							 }
						}
						else
							return "SSTest";
					}catch(Exception e){
						e.printStackTrace();
//						return "SSTest";20120827
//						return "SSException";
						return "计算对账结果时出现异常";
					}
				} else {
					return "启动任务失败";
				}
			}
			return "失败";
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if (null != outputStream) {
					outputStream.close();
				}
				if (null != inputstream) {
					inputstream.close();
				}
				if (null != socket) {
					socket.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return "失败";
	}
	
	/**
	 * 发送任务启动报文给后台系统
	 * 
	 * @return
	 * 2011-7-28上午11:38:49
	 * 20120920添加参数endDate
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public String sendXjhsMsg(String value, String date) throws IOException{
		if (StringUtil.isNull(value)) {
			return "没有正确获取任务编号";
		}
		if (StringUtil.isNull(date)) {
			return "没有正确获取日期";
		}
		String sql = "select NEW_STATE from BTH_NEW_BAT_MAIN where NEW_EXECUTE = 'NewDayFile' and NEW_DATE_SETTLMT = '"+date+"'";
		List list=CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(list != null && list.size() > 0){
			if(list.get(0).toString().equals("0") || list.get(0).toString().equals("3")){//0-待运行，3-运行失败
			}else{
				return "该状态不能重新发起交易";
			}
		}
		//组装报文
		StringBuffer message = new StringBuffer();
		message.append("BBBB");//中行对账标识符
		message.append(value);/* 任务编号 */
		message.append(date); /* 日期 */
		message.append(CommonFunction.fillString("  ", ' ', 2 + 256 + 2 + 256, true));
		

		String ip = SysParamUtil.getParam("SETTLEIP");
		//int port = 30001;
		String port =SysParamUtil.getParam("SETTLEPORT");
		String timeOut=SysParamUtil.getParam("TIMEOUT");
		
		String sendMessage = CommonFunction.fillString(String.valueOf(message.toString().length()), '0', 4, false);
		sendMessage += message.toString();
		System.out.println("send:" + sendMessage);
		String ret = "";
		
		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputstream = null;
		
		try {
			//发送
			socket = new Socket(ip,Integer.valueOf(port));
			socket.setSoTimeout(Integer.valueOf(timeOut)*30);//对账处理时间可能时间比较长所以需要将超时设置为30分钟
			outputStream = socket.getOutputStream();
			outputStream.write(sendMessage.getBytes()); 
			outputStream.flush();
			
			InputStream is = socket.getInputStream();
			
			byte[] serverSay = new byte[1024*1024];
			int len = is.read(serverSay);//接受服务器消息
			ret=new String(serverSay, 0, len);
			System.out.println("receive:"+ret);

			if ("00".equals(ret.substring(0, 2))) {
				return "s";
			} else {
				return "先结后算处理失败";
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if (null != outputStream) {
					outputStream.close();
				}
				if (null != inputstream) {
					inputstream.close();
				}
				if (null != socket) {
					socket.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return "失败";
	}
	
	@SuppressWarnings("unchecked")
	public String sendReqMsg(String dateSettlm,String brhId,String money) throws IOException{
		
//		4位交易码(1531)
//		3位长度（062）
		StringBuffer req = new StringBuffer();
//		14位交易时间
		req.append(CommonFunction.getCurrentDateTime());
//		4位流水号
		Random r = new Random();
		int c = r.nextInt(9999);
		req.append(CommonFunction.fillString(String.valueOf(c), '0', 4, false));
//		4位机构号
		req.append(CommonFunction.fillString(brhId, '0', 4, false));
//		交易金额（第一位是符号位,共12位，在符号位和有效数据间补0，到分）
		//比如（12.34转换成+00000001234）（-12.34转换成-00000001234）
		String moneyTmp;
		money = CommonFunction.transYuanToFen(money);
		if(money.indexOf("-") == -1) { //money是正值
			moneyTmp = "+" + CommonFunction.fillString(money, '0', 11, false);
		} else { //money是负值
			moneyTmp = "-" + CommonFunction.fillString(money.substring(1), '0', 11, false);
		}
		req.append(moneyTmp);
		String request = "1531"+CommonFunction.fillString(String.valueOf(req.toString().length()), '0', 3, false)+req.toString();
		log.info(request);
		String len = Integer.toHexString(request.length());
		len = new String(CommonFunction.hexStringToByte(len));
		len = new String(CommonFunction.hexStringToByte("00"))+len;
		SocketConnect socket = null;
		String flag = "0";
		String desp = "";
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		try {
			log.info("报文:["+len+request+"]");
			socket = new SocketConnect(len+request);
			//socket.run("GBK");
			socket.run(Charset.defaultCharset().toString());
			String rsp = socket.getRsp().substring(4, 6);
			
			if ("00".equals(rsp)) {
				flag = "2";
				desp = "SUCCESS:划拨成功";
			} else {
				flag = "1";
				String key = req.substring(0, 22);
				System.out.println(key);
	            String sql = "select SWITCH_DATA from TBL_N_TXN where substr(addtnl_data,1,22) = '" + key + "'";
				List list = commQueryDAO.findBySQLQuery(sql);
				if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
					desp = list.get(0).toString().trim();
					if (desp.length() > 7) {
						desp = desp.substring(0, 7) + ":" + desp.substring(7);
					}
				}
			}
		} catch (UnknownHostException e) {
			flag = "1";
			desp = "E203055:POSP找不到主机";
			log.error(e);
		} catch (IOException e) {
			flag = "1";
			desp = "E203056:POSP请求超时";
			log.error(e);
		} catch (Exception e) {
			flag = "1";
			desp = "E203056:POSP请求超时";
			log.error(e);
		} finally {
			socket.close();
		}
		
		commQueryDAO.excute("update TBL_BRH_INFILE_DTL set SETTLE_FLAG = '" + flag + "',FAIL_RESN = '" + desp + "' " +
				"where DATE_SETTLMT = '" + dateSettlm + "' and SETTLE_FLAG ='0' and BRH_CODE = '" + brhId + "'");
		return flag;
	}
}
