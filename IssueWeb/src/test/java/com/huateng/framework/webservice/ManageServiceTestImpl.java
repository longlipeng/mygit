package com.huateng.framework.webservice;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationResult;
import com.huateng.framework.util.XstreamDateConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

/**
 * 测试webservice的取代类. 设计思路.屏蔽真正的后台请求,实现返回一个假值.利用假值来实现代码覆盖
 * 
 * @author wpf
 * 
 */
public class ManageServiceTestImpl implements ManageService {
	Logger logger = Logger.getLogger(ManageServiceTestImpl.class);
	/**
	 * key交易号 value返回对象
	 */
	private static Map<String, Object> map = new HashMap<String, Object>();

	public static Map<String, Object> getMap() {
		return map;
	}

	public static void setMap(Map<String, Object> map) {
		ManageServiceTestImpl.map = map;
	}

	@Override
	public String sendMisService(String ctx, String req) {
		
		XStream smReq = new XStream();
		smReq.registerConverter(new XstreamDateConverter());
		smReq.aliasPackage("REQ", "com.allinfinance.");
		OperationCtx opCtx = null;
		XStream smRsp = new XStream();
		smRsp.registerConverter(new XstreamDateConverter());
		smRsp.alias("OperationResult", OperationResult.class);
		smRsp.aliasPackage("RSP", "com.allinfinance.");

		try {
			opCtx = (OperationCtx) smReq.fromXML(ctx);
		} catch (XStreamException xe) {
			logger.error(xe.getMessage());
			logger.error("xml字符转换字符出错");
		}
		String txnCode = opCtx.getTxncode();
		Object obj = map.get(txnCode);
		String rspStr = smRsp.toXML(obj);
		return rspStr;
	}

	@Override
	public String sendServece(String ctx, String req) {
		
		XStream smReq = new XStream();
		smReq.registerConverter(new XstreamDateConverter());
		smReq.aliasPackage("REQ", "com.allinfinance.");
		OperationCtx opCtx = null;
		XStream smRsp = new XStream();
		smRsp.registerConverter(new XstreamDateConverter());
		smRsp.alias("OperationResult", OperationResult.class);
		smRsp.aliasPackage("RSP", "com.allinfinance.");

		try {
			opCtx = (OperationCtx) smReq.fromXML(ctx);
		} catch (XStreamException xe) {
			logger.error(xe.getMessage());
			logger.error("xml字符转换字符出错");
		}
		String txnCode = opCtx.getTxncode();
		Object obj = map.get(txnCode);
		String rspStr = smRsp.toXML(obj);
		return rspStr;
	}

}
