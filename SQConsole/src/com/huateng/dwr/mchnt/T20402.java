package com.huateng.dwr.mchnt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.huateng.dao.iface.base.TblInfMchntTpDAO;
import com.huateng.po.mchnt.TblInfMchntTp;
import com.huateng.po.mchnt.TblInfMchntTpPK;
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
public class T20402 {
	
	private static Logger log = Logger.getLogger(T20402.class);
	public String getInfo(String mchnt_tp,HttpServletRequest request,
			HttpServletResponse response) {
		
		String jsonData = null;
		try {
			TblInfMchntTpDAO tblInfMchntTpDAO = (TblInfMchntTpDAO) ContextUtil.getBean("TblInfMchntTpDAO");
			TblInfMchntTpPK infoPk =new TblInfMchntTpPK();
			infoPk.setFrnIn("0");
			infoPk.setMchntTp(mchnt_tp);
			TblInfMchntTp info = tblInfMchntTpDAO.get(infoPk);
			
			if(null==info){
				return "0";
			}else {
				jsonData = JSONArray.fromObject(info).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}

	
}