package com.huateng.struts.rout.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import com.huateng.bo.rout.T11014BO;
import com.huateng.common.Constants;
import com.huateng.po.rout.TblRouteChgInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T11014Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private T11014BO t11014BO = (T11014BO)ContextUtil.getBean("T11014BO");
	private String destInstId;
	private String dftDestId;
	private String chgFlag;
	private String chgDestId;
	private String parameterList;
	
	@Override
	protected String subExecute() throws Exception {
		try {
			if("add".equals(method)) {
				rspCode = add();			
			} else if("delete".equals(method)) {
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			} else if("switchFlag".equals(method)){
				rspCode = switchFlag();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId()+ "，操作员维护路由切换操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	
	}

	public String add() throws Exception{//添加新的路由切换信息
		String sql = "select count(*) from TBL_ROUTE_CHG_INF where DEST_INST_ID='" + this.getDestInstId() + "'";
		String count = commQueryDAO.findCountBySQLQuery(sql);
		if(!"0".equals(count)) {
			return "您所选机构已有对应的路由切换信息存在，请选择其他未配置机构";
		}
		
		String sql2 = "select count(*) from TBL_ROUTE_CHG_INF where DFT_DEST_ID='" + this.getDftDestId() + "'";
		String count2 = commQueryDAO.findCountBySQLQuery(sql2);
		if(!"0".equals(count2)) {
			return "您所选的原目的ID已存在，请选择其他原目的ID";
		}
		
 		TblRouteChgInf tblRouteChgInf = new TblRouteChgInf();
 		tblRouteChgInf.setDestInstId(this.getDestInstId());
 		tblRouteChgInf.setDftDestId(this.getDftDestId());
 		tblRouteChgInf.setChgFlag(this.getChgFlag());
 		tblRouteChgInf.setChgDestId(this.getChgDestId());
 		tblRouteChgInf.setCreateOprId(this.operator.getOprId());
 		tblRouteChgInf.setCreateTime(CommonFunction.getCurrentDateTime());
		
 		t11014BO.add(tblRouteChgInf);
		return Constants.SUCCESS_CODE;
	}
	
	/**删除
	 * delete response code
	 * @return
	 */
	public String delete() {
		TblRouteChgInf tblRouteChgInf = t11014BO.get(this.getDestInstId());
		if(tblRouteChgInf == null) {
			return "没有找到要删除的路由切换信息";
		}
		t11014BO.delete(this.getDestInstId());
		return Constants.SUCCESS_CODE;
	}
	
	/** 修改
	 * 
	 * @return
	 */
	public String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {		
		jsonBean.parseJSONArrayData(getParameterList());
		   int len = jsonBean.getArray().size();
		   List<TblRouteChgInf> tblRouteChgInfList = new ArrayList<TblRouteChgInf>(len);	
			for(int i = 0; i < len; i++) {					   
			    jsonBean.setObject(jsonBean.getJSONDataAt(i));
			    String destInstId = jsonBean.getJSONDataAt(i).getString("destInstId");
			    
			    TblRouteChgInf tblRouteChgInf = t11014BO.get(destInstId);
				BeanUtils.setObjectWithPropertiesValue(tblRouteChgInf, jsonBean, false);
		 		tblRouteChgInf.setModiOprId(this.operator.getOprId());
		 		tblRouteChgInf.setModiTime(CommonFunction.getCurrentDateTime());
				tblRouteChgInfList.add(tblRouteChgInf);
			}
			t11014BO.update(tblRouteChgInfList);
			return Constants.SUCCESS_CODE;
	}

	/** 是否切换到备用通道
	 * 
	 * @return
	 */
	public String switchFlag() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {		
		TblRouteChgInf tblRouteChgInf = t11014BO.get(this.getDestInstId());
		if(tblRouteChgInf == null) {
			return "没有找到要切换的路由规则信息";
		}
		if(tblRouteChgInf.getChgFlag().trim().equals("0")){
			tblRouteChgInf.setChgFlag("1");
			t11014BO.update(tblRouteChgInf);	
			return Constants.SUCCESS_CODE;
		}
		if(tblRouteChgInf.getChgFlag().trim().equals("1")){
			tblRouteChgInf.setChgFlag("0");
			t11014BO.update(tblRouteChgInf);	
			return Constants.SUCCESS_CODE;
		}
		return null;
	}
	
	public T11014BO getT11014BO() {
		return t11014BO;
	}

	public void setT11014BO(T11014BO t11014bo) {
		t11014BO = t11014bo;
	}

	public String getChgDestId() {
		return chgDestId;
	}

	public void setChgDestId(String chgDestId) {
		this.chgDestId = chgDestId;
	}

	public String getParameterList() {
		return parameterList;
	}

	public void setParameterList(String parameterList) {
		this.parameterList = parameterList;
	}

	public String getDestInstId() {
		return destInstId.split("-")[0];
	}

	public void setDestInstId(String destInstId) {
		this.destInstId = destInstId;
	}

	public String getDftDestId() {
		return dftDestId;
	}

	public void setDftDestId(String dftDestId) {
		this.dftDestId = dftDestId;
	}

	public String getChgFlag() {
		return chgFlag;
	}

	public void setChgFlag(String chgFlag) {
		this.chgFlag = chgFlag;
	}
	
}
