package com.huateng.struts.settle.action;

import com.huateng.common.Constants;
import com.huateng.struts.system.action.BaseAction;

public class T80216Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String dateSettlmt;
	
	public String getDateSettlmt() {
		return dateSettlmt;
	}

	public void setDateSettlmt(String dateSettlmt) {
		this.dateSettlmt = dateSettlmt;
	}

	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("rollback".equals(method)) {
			log("操作员："+this.operator.getOprBrhId()+"执行日终批处理回滚操作");
			rspCode = rollback();
		}
		if(rspCode.equals(Constants.SUCCESS_CODE))
			log("日终批处理回滚成功");
		return rspCode;
	}

	private String rollback() throws Exception{//日终批处理回滚
		//判断回滚日期是否已清分
		StringBuffer sql = new StringBuffer("select count(*) from tbl_bat_main_ctl_dtl where date_settlmt='");
		sql.append(this.dateSettlmt).append("' and bat_id='B0005' and bat_state='2' ");
		String rs = commQueryDAO.findCountBySQLQuery(sql.toString());
		if( !rs.equals("0") ){
			return "所选回滚日期："+this.dateSettlmt+"已做清分，不允许回滚！";
		}
		
		String result1 = update();//step1
		
		String result2 = null;
		if(Constants.SUCCESS_CODE.equals(result1))
			result2 = delete1();//step2
		else 
			return Constants.FAILURE_CODE;
		String result3 = null;
		if(Constants.SUCCESS_CODE.equals(result2))
			result3 = delete2();//step3
		else 
			return Constants.FAILURE_CODE;
		if(!Constants.SUCCESS_CODE.equals(result3))
			return Constants.FAILURE_CODE;
		return Constants.SUCCESS_CODE;
	}
	
	private String update(){
		String sql="update tbl_bat_main_ctl set bat_state ='0',task_assign_state ='0',task_start_flg ='0'";
		commQueryDAO.excute(sql);
		return Constants.SUCCESS_CODE;
	}
	
	private String delete1(){
		String sql="delete from tbl_bat_main_ctl_dtl where date_settlmt ='"+this.getDateSettlmt()+"'";
		commQueryDAO.excute(sql);
		return Constants.SUCCESS_CODE;
	}
	
	private String delete2(){
		String sql = "delete from tbl_bat_task where date_settlmt ='"+this.getDateSettlmt()+"'";
		commQueryDAO.excute(sql);
		return Constants.SUCCESS_CODE;
	}
}
