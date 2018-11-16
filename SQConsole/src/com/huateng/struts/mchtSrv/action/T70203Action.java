package com.huateng.struts.mchtSrv.action;

import com.huateng.bo.impl.mchtSrv.MarketActSrv;
import com.huateng.po.mchtSrv.TblMarketActReview;
import com.huateng.struts.mchtSrv.MarketActConstants;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.ContextUtil;

public class T70203Action extends BaseSupport {

	private static final long serialVersionUID = 3599807122031714973L;
	private String[] selectedOptions;
	private String actNo;
	private MarketActSrv marketActSrv = (MarketActSrv) ContextUtil.getBean("marketActSrv");

	
	public String[] getSelectedOptions() {
		return selectedOptions;
	}

	public void setSelectedOptions(String[] selectedOptions) {
		this.selectedOptions = selectedOptions;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String delete()
	{
		try {
			if(selectedOptions == null||selectedOptions.length == 0)
				return returnService(MarketActConstants.T70203_02);
			if(actNo == null||actNo.equals(""))
				return returnService(MarketActConstants.T70203_01);
			
			TblMarketActReview tblMarketActReview = marketActSrv.getMarketAct(actNo);
			if(tblMarketActReview!=null
						&&!tblMarketActReview.getState().equals(MarketActConstants.STATE_OK))
					return returnService(MarketActConstants.T70205_08);
			
			rspCode = marketActSrv.delMchnts(actNo,selectedOptions,getOperator().getOprId());
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

}
