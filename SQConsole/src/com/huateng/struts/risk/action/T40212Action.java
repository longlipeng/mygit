package com.huateng.struts.risk.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.bo.risk.T40212BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.risk.TblRiskChlTranLimit;

public class T40212Action extends BaseAction{

	private static final long serialVersionUID = 1L;
	private String id;
	private String CHANNEL;
    private String CARD_BIN;
    private String TXN_NUM;
    private String RiskTranList;
	private String LIMIT;
    public String getLIMIT() {
		return LIMIT;
	}

	public void setLIMIT(String lIMIT) {
		LIMIT = lIMIT;
	}

    public String getCHANNEL() {
		return CHANNEL;
	}

	public void setCHANNEL(String cHANNEL) {
		CHANNEL = cHANNEL;
	}

	public String getCARD_BIN() {
		return CARD_BIN;
	}

	public void setCARD_BIN(String cARDBIN) {
		CARD_BIN = cARDBIN;
	}

	public String getTXN_NUM() {
		return TXN_NUM;
	}

	public void setTXN_NUM(String tXNNUM) {
		TXN_NUM = tXNNUM;
	}


    public String getRiskTranList() {
		return RiskTranList;
	}

	public void setRiskTranList(String riskTranList) {
		RiskTranList = riskTranList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	protected String subExecute() throws Exception {
		try {
			if("add".equals(getMethod())) {			
					rspCode = add();			
			} else if("delete".equals(getMethod())) {
				rspCode = delete();
			} else if("update".equals(getMethod())) {
				rspCode = update();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对渠道交易权限的维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private T40212BO t40212BO = (T40212BO) ContextUtil.getBean("T40212BO");
	
	private String add() {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from TBL_RISK_CHL_TRAN_LIMIT where channel='").append(this.CHANNEL.trim()).append("' and card_bin='")
			.append(this.CARD_BIN.trim()).append("' and txn_num='").append(this.TXN_NUM.trim()).append("' ");
		String count = commQueryDAO.findCountBySQLQuery(sql.toString());
		if(!"0".equals(count)){
			return "该渠道交易权限记录已经存在，请勿重复添加";
		}
		
		TblRiskChlTranLimit tblRiskChlTranLimit=new TblRiskChlTranLimit();
		tblRiskChlTranLimit.setChannel(CHANNEL);
		tblRiskChlTranLimit.setCardbin(CARD_BIN.trim());
		tblRiskChlTranLimit.setTxnnum(TXN_NUM);
		tblRiskChlTranLimit.setLimit(LIMIT);
		tblRiskChlTranLimit.setStatue(CommonFunction.ADD_TO_CHECK);
		tblRiskChlTranLimit.setCreid(operator.getOprId());
		tblRiskChlTranLimit.setCretime(CommonFunction.getCurrentDateTime());
		//tblRiskChlTranLimit.setUpid(operator.getOprId());
		//tblRiskChlTranLimit.setUptime(CommonFunction.getCurrentDateTime());
		tblRiskChlTranLimit.setId(StringUtil.getUUID());
		tblRiskChlTranLimit.setCardbinold(CARD_BIN);
		tblRiskChlTranLimit.setTxnnumold(TXN_NUM);
		tblRiskChlTranLimit.setLimit(LIMIT!=null?LIMIT:"00");
		
		t40212BO.add(tblRiskChlTranLimit);
		log("添加渠道交易权限成功。操作员编号：" + operator.getOprId() + "，分公司编号：" +tblRiskChlTranLimit.getId());
		return Constants.SUCCESS_CODE;
	}
	private String update() throws Exception, InvocationTargetException, Exception {
		jsonBean.parseJSONArrayData(getRiskTranList());
		int len = jsonBean.getArray().size();
		List<TblRiskChlTranLimit> tblRiskChlTranLimitList=new ArrayList<TblRiskChlTranLimit>(len);
		TblRiskChlTranLimit tblRiskChlTranLimit=null;
		for(int i = 0; i < len; i++) {
			 id= jsonBean.getJSONDataAt(i).getString("id");
			 tblRiskChlTranLimit=t40212BO.get(id);
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			BeanUtils.setObjectWithPropertiesValue(tblRiskChlTranLimit,jsonBean,true);
			tblRiskChlTranLimit.setCreid(operator.getOprId());
			tblRiskChlTranLimit.setCretime(CommonFunction.getCurrentDateTime());
			String state=tblRiskChlTranLimit.getStatue();
			//如果状态时正常的话，我们就需要往表里面插修改人和修改时间，状态也要修改
			if(CommonFunction.NORMAL.equals(state)){
				tblRiskChlTranLimit.setStatue(CommonFunction.MODIFY_TO_CHECK);
			}
			
			//tblRiskChlTranLimit.setUpid(operator.getOprId());
			//tblRiskChlTranLimit.setUptime(CommonFunction.getCurrentDateTime());
			tblRiskChlTranLimitList.add(tblRiskChlTranLimit);
		}	
		t40212BO.update(tblRiskChlTranLimitList);
		log("更新渠道交易权限信息成功。操作员编号：" + operator.getOprId());
	    return Constants.SUCCESS_CODE;
	}

	private String delete() {
		TblRiskChlTranLimit tblRiskChlTranLimit=t40212BO.get(this.getId());
		String state=tblRiskChlTranLimit.getStatue();
		//正常状态注销
		if(CommonFunction.ADD_TO_CHECK.equals(state)){
			t40212BO.delete(tblRiskChlTranLimit.getId());
		}else{
			tblRiskChlTranLimit.setStatue(CommonFunction.DELETE_TO_CHECK);
			tblRiskChlTranLimit.setCreid(operator.getOprId());
			tblRiskChlTranLimit.setCretime(CommonFunction.getCurrentDateTime());
			List<TblRiskChlTranLimit> tblRiskChlTranLimitList=new ArrayList<TblRiskChlTranLimit>();
			tblRiskChlTranLimitList.add(tblRiskChlTranLimit);
			
			t40212BO.update(tblRiskChlTranLimitList);
		}
		//修改状态到临时表
		return Constants.SUCCESS_CODE;
	}

}
