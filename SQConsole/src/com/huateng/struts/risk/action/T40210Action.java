package com.huateng.struts.risk.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.bo.risk.T40210BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.TblCtlCardInf;
import com.huateng.po.risk.TblRiskChlTranCtl;
import com.huateng.po.risk.TblRiskMchtTranCtl;

public class T40210Action extends BaseAction{

	private static final long serialVersionUID = 1L;
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

	private String id;
	private String CHANNEL;
    private String CARD_BIN;
    private String TXN_NUM;
    private String RiskTranList;
    
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
			log("操作员编号：" + operator.getOprId()+ "，对渠道交易黑名单的维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	private T40210BO t40210BO = (T40210BO) ContextUtil.getBean("T40210BO");
	
	@SuppressWarnings("unchecked")
	private String add() throws Exception {
		//20120910,限制添加同一渠道交易黑名单记录
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from TBL_RISK_CHL_TRAN_CTL where (sa_state in('0','2','4') and (txn_num='").append(TXN_NUM)
			.append("' Or txn_num='*') And (channel='").append(CHANNEL).append("' Or channel='*') And (card_bin='*' Or  card_bin='")
			.append(CARD_BIN).append("')) or(sa_state='3' and (txn_num_old='").append(TXN_NUM)
			.append("' Or txn_num_old='*') and (trim(channel)='").append(CHANNEL).append("' Or channel='*') And ( card_bin_old='")
			.append(CARD_BIN).append("' or card_bin_old='*'))");
		String count = commQueryDAO.findCountBySQLQuery(sql.toString());
		if(!"0".equals(count)){
			return "您所添加的渠道交易黑名单信息中已经存在，请勿重复添加";
		}
		
		StringBuffer sql2 = new StringBuffer();
		sql2.append("select count(*) from TBL_RISK_CHL_TRAN_CTL where sa_state='1' and (txn_num='").append(TXN_NUM)
			.append("' Or txn_num='*') And (channel='").append(CHANNEL).append("' Or channel='*') And (card_bin='*' Or  card_bin='")
			.append(CARD_BIN).append("')");
		String count2 = commQueryDAO.findCountBySQLQuery(sql2.toString());
		
		StringBuffer sql3 = new StringBuffer();
		sql3.append("select ID from TBL_RISK_CHL_TRAN_CTL where sa_state='1' and (txn_num='").append(TXN_NUM)
			.append("' Or txn_num='*') And (channel='").append(CHANNEL).append("' Or channel='*') And (card_bin='*' Or  card_bin='")
			.append(CARD_BIN).append("')");
		List list = commQueryDAO.findBySQLQuery(sql3.toString());
		
		if(!"0".equals(count2)){
			TblRiskChlTranCtl exsit = t40210BO.get((String)list.get(0));
			exsit.setSastatue("0");
			exsit.setCretime(CommonFunction.getCurrentDateTime());
			exsit.setCreid(operator.getOprId());
			t40210BO.update(exsit);
			return Constants.SUCCESS_CODE;
		}
		
//		String sql = "select count(*) from TBL_RISK_CHL_TRAN_CTL where (txn_num='"+TXN_NUM+"' Or txn_num='*') " +
//				"And (channel='"+CHANNEL+"' Or channel='*') And (card_bin='*' Or  card_bin='"+CARD_BIN+"') ";
//						"and sa_state<>'1'";20120809修改，和注销状态的渠道交易黑名单相同也不能重复添加
		
//		String count=commQueryDAO.findCountBySQLQuery(sql);
//		if(!"0".equals(count)){
//			return "已存在相同或包含了该记录的数据";
//		}
		TblRiskChlTranCtl tblRiskChlTranCtl=new TblRiskChlTranCtl();
		tblRiskChlTranCtl.setChannel(CHANNEL);
		tblRiskChlTranCtl.setCardbin(CARD_BIN);
		tblRiskChlTranCtl.setTxnnum(TXN_NUM);
		tblRiskChlTranCtl.setSastatue("0");
		tblRiskChlTranCtl.setCreid(operator.getOprId());
		tblRiskChlTranCtl.setCretime(CommonFunction.getCurrentDateTime());
		tblRiskChlTranCtl.setId(StringUtil.getUUID());
		tblRiskChlTranCtl.setCardbinold(CARD_BIN);//存修改后
		tblRiskChlTranCtl.setTxnnumold(TXN_NUM);//存修改后
		t40210BO.add(tblRiskChlTranCtl);
		
		log("添加渠道交易黑名单信息成功。操作员编号：" + operator.getOprId() + "，分公司编号：" +tblRiskChlTranCtl.getId());
		return Constants.SUCCESS_CODE;
	}
	
	private String update() throws Exception, InvocationTargetException, Exception {
		jsonBean.parseJSONArrayData(getRiskTranList());
		int len = jsonBean.getArray().size();
		 
		List<TblRiskChlTranCtl> tblRiskChlTranCtlList=new ArrayList<TblRiskChlTranCtl>(len);
		TblRiskChlTranCtl tblRiskChlTranCtl=null;
		for(int i = 0; i < len; i++) {
			id= jsonBean.getJSONDataAt(i).getString("id");
			tblRiskChlTranCtl=t40210BO.get(id);
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			BeanUtils.setObjectWithPropertiesValue(tblRiskChlTranCtl,jsonBean,true);
			tblRiskChlTranCtl.setCreid(operator.getOprId());
			tblRiskChlTranCtl.setCretime(CommonFunction.getCurrentDateTime());
			String state=tblRiskChlTranCtl.getSastatue();
			//如果状态时正常的话，我们就需要往表里面插修改人和修改时间，状态也要修改
			if(("2").equals(state)){
				tblRiskChlTranCtl.setSastatue(CommonFunction.MODIFY_TO_CHECK);
			}
			tblRiskChlTranCtl.setCreid(operator.getOprId());
			tblRiskChlTranCtl.setCretime(CommonFunction.getCurrentDateTime());
			tblRiskChlTranCtlList.add(tblRiskChlTranCtl);
		}	
		t40210BO.update(tblRiskChlTranCtlList);
		
		log("更新渠道交易黑名单交易信息成功。操作员编号：" + operator.getOprId());
	    return Constants.SUCCESS_CODE;
	}

	private String delete() throws Exception {
		TblRiskChlTranCtl tblRiskChlTranCtl=t40210BO.get(this.getId());
		String state=tblRiskChlTranCtl.getSastatue();
		if("0".equals(state)){
			t40210BO.delete(tblRiskChlTranCtl);
		}
		
		tblRiskChlTranCtl.setCreid(operator.getOprId());
		tblRiskChlTranCtl.setCretime(CommonFunction.getCurrentDateTime());
		tblRiskChlTranCtl.setSastatue(CommonFunction.DELETE_TO_CHECK);
		t40210BO.update(tblRiskChlTranCtl);
		
		return Constants.SUCCESS_CODE;
	}

}
