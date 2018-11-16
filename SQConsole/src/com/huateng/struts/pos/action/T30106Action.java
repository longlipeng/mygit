package com.huateng.struts.pos.action;

import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.pos.T30106BO;
import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.po.base.TblAgentDivide;
import com.huateng.po.pos.CstTermFeeIn;
import com.huateng.po.pos.CstTermFeePK;

public class T30106Action extends BaseAction{

	private static final long serialVersionUID = 1L;
	private String MCHT_CD;
	private String TERM_ID;
	private String DAY_NUM;
	private String DAY_AMT;
	private String DAY_SINGLE;
	private String saaction;
	private String mchtcd;
	private String termid;
	private String monAmt;
	private String cardType;
	
	public String getMchtcd() {
		return mchtcd;
	}
	public void setMchtcd(String mchtcd) {
		this.mchtcd = mchtcd;
	}
	public String getTermid() {
		return termid;
	}
	public void setTermid(String termid) {
		this.termid = termid;
	}

	public String getCstTermFeeList() {
		return CstTermFeeList;
	}
	public void setCstTermFeeList(String cstTermFeeList) {
		CstTermFeeList = cstTermFeeList;
	}

	private String CstTermFeeList;
	public String getSaaction() {
		return saaction;
	}
	public void setSaaction(String saaction) {
		this.saaction = saaction;
	}
	public String getMCHT_CD() {
		return MCHT_CD;
	}
	public void setMCHT_CD(String mCHTCD) {
		MCHT_CD = mCHTCD;
	}
	public String getTERM_ID() {
		return TERM_ID;
	}
	public void setTERM_ID(String tERMID) {
		TERM_ID = tERMID;
	}
	public String getDAY_NUM() {
		return DAY_NUM;
	}
	public void setDAY_NUM(String dAYNUM) {
		DAY_NUM = dAYNUM;
	}
	public String getDAY_AMT() {
		return DAY_AMT;
	}
	public void setDAY_AMT(String dAYAMT) {
		DAY_AMT = dAYAMT;
	}
	public String getDAY_SINGLE() {
		return DAY_SINGLE;
	}
	public void setDAY_SINGLE(String dAYSINGLE) {
		DAY_SINGLE = dAYSINGLE;
	}
    
	
	
	
	
	public String getMonAmt() {
		return monAmt;
	}
	public void setMonAmt(String monAmt) {
		this.monAmt = monAmt;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	private T30106BO t30106BO = (T30106BO) ContextUtil.getBean("T30106BO");
//	private T30107BO t30107BO = (T30107BO) ContextUtil.getBean("T30107BO");
	
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		try {
			if("add".equals(method)) {
				rspCode = add();
			} else if("delete".equals(method)) {
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			} 
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，终端交易限额" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	private String add() throws Exception{
		ICommQueryDAO dao = CommonFunction.getCommQueryDAO();
		String sql = "select count(*) from CST_TERM_FEE_INF where MCHT_CD='" + MCHT_CD 
			+ "' and TERM_ID='" + TERM_ID +"' AND CARD_TYPE ='"+cardType+ "' and SA_STATE<>'1'";//20120809修改，已经对应注销记录的终端，还可以在添加交易限额
		int result = Integer.parseInt(dao.findCountBySQLQuery(sql));
		if(result >= 1){
			return "该终端交易限额已经存在";
		}
		if(Double.parseDouble(DAY_AMT)<Double.parseDouble(DAY_SINGLE)){
			return "终端日单笔交易限额不能超过日累计限额";
		}
		if(Double.parseDouble(monAmt)<Double.parseDouble(DAY_AMT)){
			return "终端日累计限额不能超过月累计上线";
		}
		
		StringBuffer sql2 = new StringBuffer();
		sql2.append("select count(*) from CST_TERM_FEE_INF where sa_state='1' and trim(MCHT_CD)='").append(MCHT_CD)
			.append("' and trim(TERM_ID)='").append(this.TERM_ID).append("' and trim(CARD_TYPE)='").append(this.cardType).append("' ");
		String count2 = commQueryDAO.findCountBySQLQuery(sql2.toString());
		
		if(!"0".equals( count2 )){//所添加的终端交易限额信息的状态从删除成为新增待审核
//			CstTermFeeIn exsit = t30106BO.get((String)list.get(0));
//			exsit.setSastatue("0");
//			exsit.setReccrtts(CommonFunction.getCurrentDateTime());
//			exsit.setCreid(operator.getOprId());
//			t30106BO.update( exsit );
			StringBuffer sql3 = new StringBuffer();
			sql3.append("update CST_TERM_FEE_INF set sa_state='0',rec_crt_ts='").append(CommonFunction.getCurrentDateTime())
				.append("',cre_id='").append(operator.getOprId())
				.append("', CARD_TYPE='").append(this.cardType)
				.append("', DAY_AMT='").append(this.DAY_AMT)
				.append("', DAY_SINGLE='").append(this.DAY_SINGLE)
				.append("', MON_AMT='").append(this.monAmt)
				.append("' where sa_state='1' and trim(MCHT_CD)='").append(this.MCHT_CD)
				.append("' and trim(TERM_ID)='").append(this.TERM_ID)
				.append("' ");
			commQueryDAO.excute(sql3.toString());
			return Constants.SUCCESS_CODE;
		}
		
		CstTermFeeIn cstTermFeeIn=new CstTermFeeIn();
		CstTermFeePK cstTermFeePK=new CstTermFeePK();
		cstTermFeePK.setMchtcd(MCHT_CD);
		cstTermFeePK.setTermid(TERM_ID);
		cstTermFeePK.setCardtype(cardType);
		cstTermFeeIn.setId(cstTermFeePK);
	//	cstTermFeeIn.setCardtype("00");
		cstTermFeeIn.setTxnnum("1101");
		cstTermFeeIn.setChannel("00");
		cstTermFeeIn.setDaynum("0");   //去除
		cstTermFeeIn.setDayamt(Double.parseDouble(DAY_AMT));
		cstTermFeeIn.setDaysingle(Double.parseDouble(DAY_SINGLE));
		cstTermFeeIn.setMonnum("0");
	//	cstTermFeeIn.setMonamt(0);
		cstTermFeeIn.setCreid(operator.getOprId());
		cstTermFeeIn.setReccrtts(CommonFunction.getCurrentDateTime());
//		cstTermFeeIn.setUpid(operator.getOprId());
//		cstTermFeeIn.setRecupdts(CommonFunction.getCurrentDateTime());
		cstTermFeeIn.setSastatue("0");
		cstTermFeeIn.setSaaction("1");  //去除
		cstTermFeeIn.setMonamt(Double.parseDouble(monAmt));
		
		
		t30106BO.add(cstTermFeeIn);
		log("添加终端交易限额成功。操作员编号："+operator.getOprId()) ;
		return Constants.SUCCESS_CODE;
	}

	private String update() throws IllegalAccessException, Exception, Exception {
		jsonBean.parseJSONArrayData(getCstTermFeeList());
		
		int len = jsonBean.getArray().size();
		
		CstTermFeeIn cstTermFeeIn = null;
		
		List<CstTermFeeIn> cstTermFeeInList = new ArrayList<CstTermFeeIn>(len);

		for(int i = 0; i < len; i++) {		
			MCHT_CD = jsonBean.getJSONDataAt(i).getString("mchtcd");
			TERM_ID = jsonBean.getJSONDataAt(i).getString("termid");
			cardType = jsonBean.getJSONDataAt(i).getString("cardtype");
			cstTermFeeIn = t30106BO.get(new CstTermFeePK(MCHT_CD,TERM_ID,cardType));		
			DAY_AMT=jsonBean.getJSONDataAt(i).getString("dayamt");
			DAY_SINGLE=jsonBean.getJSONDataAt(i).getString("daysingle");
			monAmt=jsonBean.getJSONDataAt(i).getString("monamt");
//			monAmt = jsonBean.getJSONDataAt(i).getString("monAmt");
			if(Double.parseDouble(DAY_AMT)<Double.parseDouble(DAY_SINGLE)){
			return "终端日单笔交易限额不能超过日累计限额";}
			if(Double.parseDouble(DAY_AMT)>Double.parseDouble(monAmt)){
				return "终端日累计限额不能超过月交易金额累计上限";
			}
			//如果状态是正常的话，我们就需要往表里面插修改人和修改时间，状态也要修改
			String state=cstTermFeeIn.getSastatue();
			if(("2").equals(state)){
				//cstTermFeeIn.setUpid(operator.getOprId());
				//cstTermFeeIn.setRecupdts(CommonFunction.getCurrentDateTime());
				//cstTermFeeIn.setCreid(operator.getOprId());
				//cstTermFeeIn.setReccrtts(CommonFunction.getCurrentDateTime());
				cstTermFeeIn.setSastatue(CommonFunction.MODIFY_TO_CHECK);
				cstTermFeeIn.setCreid(operator.getOprId());
				cstTermFeeIn.setReccrtts(CommonFunction.getCurrentDateTime());
			}
		
			BeanUtils.setObjectWithPropertiesValue(cstTermFeeIn, jsonBean, false);
			cstTermFeeIn.setCreid(operator.getOprId());
			cstTermFeeIn.setReccrtts(CommonFunction.getCurrentDateTime());
			/*cstTermFeeIn.setCreid(operator.getOprId());
			cstTermFeeIn.setReccrtts(CommonFunction.getCurrentDateTime());*/
			/*cstTermFeeIn.setUpid(operator.getOprId());
			cstTermFeeIn.setRecupdts(CommonFunction.getCurrentDateTime());*/
			
			cstTermFeeInList.add(cstTermFeeIn);
		}
		t30106BO.update(cstTermFeeInList);
		log("更新终端限额维护成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}

	private String delete() throws IllegalAccessException, Exception {
		CstTermFeePK cstTermFeePK=new CstTermFeePK();
		cstTermFeePK.setMchtcd(mchtcd);
		cstTermFeePK.setTermid(termid);
		cstTermFeePK.setCardtype(cardType);
		CstTermFeeIn cstTermFeeIn=t30106BO.get(cstTermFeePK);
		String state=cstTermFeeIn.getSastatue();
		if("0".equals(state)){
			t30106BO.delete(cstTermFeePK);
		}
		cstTermFeeIn.setSastatue(CommonFunction.DELETE_TO_CHECK);
		cstTermFeeIn.setCreid(operator.getOprId());
		cstTermFeeIn.setReccrtts(CommonFunction.getCurrentDateTime());
		t30106BO.update(cstTermFeeIn);
		/*CstTermFeeInTrue cstTermFeeInTrue=t30107BO.get(cstTermFeePK);
		BeanUtils.copyProperties(cstTermFeeIn,cstTermFeeInTrue);
		t30107BO.update(cstTermFeeInTrue);*/
		
		return Constants.SUCCESS_CODE;
	}

}
