package com.huateng.struts.settle.action;


import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.settle.T80402BO;
import com.huateng.common.Constants;
import com.huateng.po.settle.TblFrozenAccInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;


public class T80402Action extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	T80402BO t80402BO = (T80402BO) ContextUtil.getBean("T80402BO");
	private String stats;
	private String ids;
	
	public String getStats() {
		return stats;
	}


	public void setStats(String stats) {
		this.stats = stats;
	}


	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}


	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("add".equals(method)) {
			log("添加冻结商户信息");
			rspCode = add();
		} else if("update".equals(method)) {
			log("更新冻结商户信息");
			rspCode = update();
		} else if("delete".equals(method)) {
			log("删除冻结商户信息");
			rspCode = delete();
		}  else if("accept".equals(method)) {
			rspCode = accept();
		} else if("refuse".equals(method)) {
			rspCode = refuse();
		}
		return rspCode;
	}
	

	private String accept() {
		System.out.println("ids = "+ ids);
		System.out.println("stats = "+ stats);
		try {
			String sql1 = "select REC_OPR from TBL_FROZEN_ACC_INF where ID = '"+ids+"'";
			String REC_OPR = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
			System.out.println("更新人："+REC_OPR);
			if (operator.getOprId().trim().equals(REC_OPR.trim())) {
				return "提交人与审核人不能是同一个人";
			}
			String updatesql = "UPDATE TBL_FROZEN_ACC_INF SET STATS = '2' WHERE ID = '"+ids+"'";
			CommonFunction.getCommQueryDAO().excute(updatesql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.SUCCESS_CODE;
	}


	private String refuse() {
		System.out.println("ids = "+ ids);
		System.out.println("stats = "+ stats);
		try {
			String sql1 = "select REC_OPR from TBL_FROZEN_ACC_INF where ID = '"+ids+"'";
			String REC_OPR = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql1);
			System.out.println("更新人："+REC_OPR);
			if (operator.getOprId().trim().equals(REC_OPR.trim())) {
				return "提交人与审核人不能是同一个人";
			}
			String updatesql = "UPDATE TBL_FROZEN_ACC_INF SET STATS = '5' WHERE ID = '"+ids+"'";
			CommonFunction.getCommQueryDAO().excute(updatesql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.SUCCESS_CODE;
	}


	private String delete() throws Exception {

		t80402BO.delete(id);
		return Constants.SUCCESS_CODE;
	}
	
	
	/**
	 * 添加商户白名单信息
	 * @return
	 * 2014-08-04
	 * @throws Exception 
	 */
	private String add() throws Exception {
		
	//    Random r = new Random();
	//    String id = CommonFunction.getCurrentDateTime();
	    /*for(int i=0;i<7;i++){
	       String s= String.valueOf(r.nextInt(10));
	       id += s;
	    }
	    if(termId == null || termId.isEmpty()){
	    	termId = "*";
	    }*/
		String idSql = "  select SEQ_FROZEN_ACC_INF.nextval from dual";
		String id = CommonFunction.getCommQueryDAO().findCountBySQLQuery(idSql);
	    String currentTime = CommonFunction.getCurrentDateTime();
	    
		TblFrozenAccInf tblFrozenAccInf = new TblFrozenAccInf();
		tblFrozenAccInf.setId(id);
		tblFrozenAccInf.setFrozenDate(currentTime.substring(0,8));
		tblFrozenAccInf.setMchtNo(mchtNo);
		tblFrozenAccInf.setTermId(termId);
		tblFrozenAccInf.setFrozenAccount(frozenAccount);  //冻结金额
		tblFrozenAccInf.setFrozenAccountFinish("0");  	 //已冻结金额  ---
		tblFrozenAccInf.setFrozenAccountNoFinish(frozenAccount);  //未完成冻结金额 ---
		tblFrozenAccInf.setDesId(desId);
		/*if (frozenRoute != null) {
			tblFrozenAccInf.setFrozenRoute(frozenRoute);   //1-风控冻结  2-手动冻结  3-负金额冻结 ---frozenRoute
		}else{
			tblFrozenAccInf.setFrozenRoute("2");
		}*/
		System.out.println("frozenRoute:"+frozenRoute);
		System.out.println("valueField:"+valueField);
		if (valueField != null) {
			tblFrozenAccInf.setFrozenRoute(valueField);   //1-风控冻结  2-手动冻结  3-负金额冻结 ---frozenRoute
		}else{
			tblFrozenAccInf.setFrozenRoute("2");
		}
		
		tblFrozenAccInf.setFrozenReason(frozenReason); 
		tblFrozenAccInf.setFrozenFinishFlag("0");  //0-未完成   1-已完成   ----
		tblFrozenAccInf.setOprFlag("0");
		tblFrozenAccInf.setStats("0");
		tblFrozenAccInf.setRecOpr(operator.getOprId().trim());
		rspCode = t80402BO.add(tblFrozenAccInf);
		return rspCode;
	    
	    /* try{
	     String sql = "INSERT INTO TBL_FROZEN_ACC_INF_TMP "
	    		+ "(FROZEN_DATE, MCHT_NO, TERM_ID, FROZEN_ACCOUNT_FINISH, FROZEN_ACCOUNT_NO_FINISH, FROZEN_ROUTE, FROZEN_REASON, FROZEN_FINISH_FLAG, ID, OPR_FLAG, FROZEN_ACCOUNT, OPR_DATE, STATS) VALUES "
	    		+ "('"+currentTime.substring(0,8)+"', '"+mchtNo+"', '"+termId+"', '"+0+"', '"+frozenAccount+"', '2', '"+frozenReason+"', '0', '"+id+"', '0', '"+frozenAccount+"', '"+currentTime.substring(0,8)+"','0')";
	    System.out.println("添加到临时表的SQL "+sql);
		log("添加延时结算成功。操作员编号：" + operator.getOprId());
		CommonFunction.getCommQueryDAO().excute(sql);	
		return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.DATA_OPR_FAIL;
		}*/
	    

	}
	
	
	private String update() throws Exception {
		jsonBean.parseJSONArrayData(getFrozenList());		
		int len = jsonBean.getArray().size();
		List<TblFrozenAccInf> list = new ArrayList<TblFrozenAccInf>();
		
		for(int i = 0; i < len; i++) {				
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			
			TblFrozenAccInf tblFrozenAccInf = new TblFrozenAccInf();
			
			BeanUtils.setObjectWithPropertiesValue(tblFrozenAccInf,jsonBean,false);
            
			list.add(tblFrozenAccInf);
		  
		}
		rspCode = t80402BO.updateAll(list);
		return Constants.SUCCESS_CODE;
	}
	
	private String id;
//	private String frozenDate;
	private String mchtNo;
	private String termId;
	private String frozenAccount;
	private String frozenReason;
	private String frozenRoute;//冻结方式
	private String valueField;//冻结方式
    private String frozenList;   //修改列表
    private String desId;   //
    
	public String getDesId() {
		return desId;
	}
	public void setDesId(String desId) {
		this.desId = desId;
	}
	public String getValueField() {
		return valueField;
	}
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	public String getFrozenRoute() {
		return frozenRoute;
	}
	public void setFrozenRoute(String frozenRoute) {
		this.frozenRoute = frozenRoute;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

/*	public String getFrozenDate() {
		return frozenDate;
	}


	public void setFrozenDate(String frozenDate) {
		this.frozenDate = frozenDate;
	}
*/

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getFrozenAccount() {
		return frozenAccount;
	}

	public void setFrozenAccount(String frozenAccount) {
		this.frozenAccount = frozenAccount;
	}

	public String getFrozenReason() {
		return frozenReason;
	}

	public void setFrozenReason(String frozenReason) {
		this.frozenReason = frozenReason;
	}

	public String getFrozenList() {
		return frozenList;
	}

	public void setFrozenList(String frozenList) {
		this.frozenList = frozenList;
	}
	
}
