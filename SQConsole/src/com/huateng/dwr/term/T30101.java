package com.huateng.dwr.term;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtSettleInf;
import com.huateng.po.mchnt.TblMchtSupp1;
import com.huateng.po.mchnt.TblMchtSupp1Tmp;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-21
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class T30101 {
	private static Logger log = Logger.getLogger(T30101.class);
	public String getMchnt(String mchntId,HttpServletRequest request,HttpServletResponse response) {
		String jsonData = null;
		try {
			TblMchntService service = (TblMchntService) ContextUtil.getBean("TblMchntService");
			TblMchtBaseInf info = service.getMccByMchntId(mchntId);
//			TblMchtSettleInf settleInfo = service.getSettleInf(info.getMchtNo());
//            把清算信息存在临时域
//			if(settleInfo.getSettleAcct()!=null && settleInfo.getSettleAcct().length()>0)
//				info.setUpdOprId(settleInfo.getSettleAcct().substring(1));
			if(info != null)
				jsonData = JSONArray.fromObject(info).toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	
	public String getTermParam(String termParam,HttpServletRequest request,HttpServletResponse response){
		StringBuffer result = new StringBuffer();
		for (char c : termParam.toCharArray()){
			result.append(CommonFunction.fillString(Integer.toBinaryString(Integer.parseInt(String.valueOf(c),16)), '0', 4, false));
		}
		return result.toString();
	}
	
	//添加 TblMchtSupp1中的预授权 和退货    By shiyiwen 20140914
	public String getMchtSupp1(String mchntId,HttpServletRequest request,HttpServletResponse response) {
		String jsonData = null;
		try {
			TblMchntService service = (TblMchntService) ContextUtil.getBean("TblMchntService");
			//TblMchtSupp1Tmp tblMchtSupp1Tmp = service.getMchtSupp1Tmp(mchntId);
			TblMchtSupp1 tblMchtSupp1 = service.getMchtSupp1(mchntId);
			if(tblMchtSupp1 != null)
				jsonData = JSONArray.fromObject(tblMchtSupp1).toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}
}