package com.huateng.struts.base.action;

import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.base.T10209BO;
import com.huateng.bo.risk.T40206BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.log.Log;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.po.base.TblHolidays;
import com.huateng.po.risk.TblRiskRefuse;

public class T10210Action extends BaseAction{

	private static final long serialVersionUID = 1676783867603010378L;

	private String id;
	private String refuseInfo;
	/**
	 * @return the refuseInfo
	 */
	public String getRefuseInfo() {
		return refuseInfo;
	}

	/**
	 * @param refuseInfo the refuseInfo to set
	 */
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
		
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	T10209BO t10209BO = (T10209BO) ContextUtil.getBean("T10209BO");
	private T40206BO t40206bo = (T40206BO) ContextUtil.getBean("T40206BO");
	
	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		//在新增、修改、删除时，操作人和审核人不能使同一人
		String sql = "SELECT CREATE_OPR_ID FROM TBL_HOLIDAYS WHERE ID= '" + this.id + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				Log.log("修改人："+list.get(0)+" 审核人"+operator.getOprId());
				if(list.get(0).equals(operator.getOprId())){
					return "同一操作员不能审核！";
				}
			}
		}
		try {
			if("accept".equals(getMethod())) {			
				rspCode = accept();			
			} else if("refuse".equals(getMethod())) {
				rspCode = refuse();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对节假日的审核操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}

	private String accept() {
		TblHolidays tblholidays = t10209BO.get(id);//获得表中的这条数据

		String state = tblholidays.getSaState();
		//审核通过:删除待审核的直接删除，其余将状态更新为正常
		if(CommonFunction.DELETE_TO_CHECK.equals(state)){
//			t10209BO.delete(id);
			tblholidays.setSaState(CommonFunction.DELETE);
		}else{
			tblholidays.setSaState(CommonFunction.NORMAL);
		}
		tblholidays.setHolidayStartOld(tblholidays.getHolidayStart());//审核通过之后就把修改后的数据给旧的数据
		tblholidays.setHolidayEndOld(tblholidays.getHolidayEnd());
		tblholidays.setNameOld(tblholidays.getName());
		tblholidays.setUpdOprId(operator.getOprId());
		tblholidays.setUpdTime(CommonFunction.getCurrentDateTime());
		
		List<TblHolidays> tblholidaysList = new ArrayList<TblHolidays>();
		tblholidaysList.add(tblholidays);
		t10209BO.update(tblholidaysList);
		return Constants.SUCCESS_CODE;
	}
	
	private String refuse() throws Exception {
		TblRiskRefuse tblRiskRefuse = new TblRiskRefuse();
		TblHolidays tblholidays = t10209BO.get(id);
		String state = tblholidays.getSaState();
		//新增待审核拒绝，直接删除
		if(CommonFunction.ADD_TO_CHECK.equals(state)){
			t10209BO.delete(id);	 
		}
		//修改待审核拒绝，变成正常状态
		if((CommonFunction.MODIFY_TO_CHECK).equals(state)){
			//恢复原来的数据:修改前的数据old给修改后holiday是修改后
		    tblholidays.setHolidayStart(tblholidays.getHolidayStartOld());
		    tblholidays.setHolidayEnd(tblholidays.getHolidayEndOld());
		    tblholidays.setName(tblholidays.getNameOld());
		    tblholidays.setSaState(CommonFunction.NORMAL);
		    tblholidays.setUpdOprId(operator.getOprId());
			tblholidays.setUpdTime(CommonFunction.getCurrentDateTime());
			
		    t10209BO.update(tblholidays);
		}
		//注销待审核拒绝，变成正常状态
		if(("4").equals(state)){
			tblholidays.setHolidayStart(tblholidays.getHolidayStartOld());
			tblholidays.setHolidayEnd(tblholidays.getHolidayEndOld());
		    tblholidays.setName(tblholidays.getNameOld());
		    tblholidays.setSaState(CommonFunction.NORMAL);
		    tblholidays.setUpdOprId(operator.getOprId());
			tblholidays.setUpdTime(CommonFunction.getCurrentDateTime());
			
		    t10209BO.update(tblholidays);
		}			
		tblRiskRefuse.setRefuseType(state);
		tblRiskRefuse.setRefuseInfo(getRefuseInfo());
		tblRiskRefuse.setOprId(operator.getOprId());
		tblRiskRefuse.setBrhId(operator.getOprBrhId());
		tblRiskRefuse.setParam1(tblholidays.getHolidayStart());
		tblRiskRefuse.setParam2(tblholidays.getHolidayEnd());
		tblRiskRefuse.setParam3(tblholidays.getName());
		tblRiskRefuse.setTxnTime(CommonFunction.getCurrentDateTime());
		tblRiskRefuse.setFlag("90");
		
		t40206bo.saveRefuseInfo(tblRiskRefuse);
		return Constants.SUCCESS_CODE;
	}

}
