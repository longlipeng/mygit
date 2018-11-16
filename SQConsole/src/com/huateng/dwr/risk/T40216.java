package com.huateng.dwr.risk;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import com.huateng.bo.mchnt.T20101BO;
import com.huateng.bo.risk.T40202BO;
import com.huateng.bo.term.T3010BO;
import com.huateng.po.TblCtlMchtInf;
import com.huateng.po.TblTermInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.system.util.ContextUtil;

public class T40216 {
	
	private TblMchtBaseInfTmp tblMchtBaseInfTmp;
	private static Logger log = Logger.getLogger(T40216.class);
	private TblCtlMchtInf tblCtlMchtInf;
	
	/**
	 * 查询终端信息
	 * @param termID
	 * @return
	 * 
	 */
	public String getMchtInfo(String termID,HttpServletRequest request,HttpServletResponse response) {
		String jsonData = null;
		try {
			T3010BO t3010BO = (T3010BO) ContextUtil.getBean("t3010BO");
			T20101BO t20101BO = (T20101BO) ContextUtil.getBean("T20101BO");
			
			TblTermInf termInfo = t3010BO.getTermInfo(termID);
			tblMchtBaseInfTmp = t20101BO.get(termInfo.getMchtCd());
			if(tblMchtBaseInfTmp != null){
				jsonData = JSONArray.fromObject(tblMchtBaseInfTmp).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}

	/**
	 * 查看该商户是否属于受控商户
	 * 
	 * @param id
	 * @return
	 */
	public String checkHasMchnt(String id,HttpServletRequest request,HttpServletResponse response){
		String jsonData = null;
		try{
			T40202BO t40202BO = (T40202BO) ContextUtil.getBean("T40202BO");
			
			tblCtlMchtInf = t40202BO.get(id);
			if(tblCtlMchtInf != null && !"1".equals(tblCtlMchtInf.getSaState())){
				jsonData = JSONArray.fromObject(tblCtlMchtInf).toString();
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	public void setMchntInfo(TblMchtBaseInfTmp tblMchtBaseInfTmp) {
		this.tblMchtBaseInfTmp = tblMchtBaseInfTmp;
	}

	public TblCtlMchtInf getTblCtlMchtInf() {
		return tblCtlMchtInf;
	}

	public void setTblCtlMchtInf(TblCtlMchtInf tblCtlMchtInf) {
		this.tblCtlMchtInf = tblCtlMchtInf;
	}

}
