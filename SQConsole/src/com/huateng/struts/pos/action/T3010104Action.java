package com.huateng.struts.pos.action;


import com.huateng.bo.term.T3010BO;
import com.huateng.common.Constants;
import com.huateng.po.TblTermInf;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.huateng.struts.pos.TblTermInfConstants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T3010104Action extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String recCrtTs;
    private String termId;
    private String termSta;
    public String getTermSta() {
		return termSta;
	}
	public void setTermSta(String termSta) {
		this.termSta = termSta;
	}
	public String getRecCrtTs() {
		return recCrtTs;
	}
	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	private T3010BO t3010BO;
	public T3010BO getT3010BO() {
		return t3010BO;
	}
	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		try {
			 if("delete".equals(method)) {
				rspCode = delete();
			} 
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，终端信息" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	public String delete() {
		// TODO Auto-generated method stub
		TblTermInfTmpPK tblTermInfTmpPK=new TblTermInfTmpPK();
		tblTermInfTmpPK.setTermId(CommonFunction.fillString(termId,' ', 12, true));
		tblTermInfTmpPK.setRecCrtTs(recCrtTs);
		TblTermInfTmp tblTermInfTmp=t3010BO.get2(tblTermInfTmpPK);
		
		System.out.println("termSta   "  +  termSta);
		
		
		//新增状态直接删除
		if(TblTermInfConstants.TERM_STA_INIT.equals(termSta)){
			t3010BO.delete(tblTermInfTmpPK);
		}
		tblTermInfTmp.setRecCrtOpr(operator.getOprId());
		tblTermInfTmp.setRecDelTs(CommonFunction.getCurrentDateTime());
		//启用
		if(TblTermInfConstants.TERM_STA_RUN.equals(termSta)){
			t3010BO.update(tblTermInfTmp,TblTermInfConstants.TERM_STA_CANCEL_UNCHK);
		}
		//停用未审核
		if(TblTermInfConstants.TERM_STA_STOP_UNCHK.equals(termSta)){
			t3010BO.update(tblTermInfTmp,TblTermInfConstants.TERM_STA_CANCEL_UNCHK);
		}
		//停用
		if(TblTermInfConstants.TERM_STA_STOP.equals(termSta)){
			t3010BO.update(tblTermInfTmp,TblTermInfConstants.TERM_STA_CANCEL_UNCHK);
		}
		//恢复未审核
		if(TblTermInfConstants.TERM_STA_STOP.equals(termSta)){
			t3010BO.update(tblTermInfTmp,TblTermInfConstants.TERM_STA_CANCEL_UNCHK);
		}
		return Constants.SUCCESS_CODE;
	}

}
