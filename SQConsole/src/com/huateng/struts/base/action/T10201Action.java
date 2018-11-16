/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-17       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.struts.base.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.base.T1020101BO;
import com.huateng.common.Constants;
import com.huateng.po.base.TblCityCodeCode;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.ContextUtil;

/**
 * Title:地区码维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-17
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T10201Action extends BaseAction {
	
	private String CITY_CODE;
	private String CITY_DES;
	public String getCITY_CODE() {
		return CITY_CODE;
	}

	public void setCITY_CODE(String cITYCODE) {
		CITY_CODE = cITYCODE;
	}

	public String getCITY_DES() {
		return CITY_DES;
	}

	public void setCITY_DES(String cITYDES) {
		CITY_DES = cITYDES;
	}
	T1020101BO t10201BO = (T1020101BO) ContextUtil.getBean("T1020101BO");
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		if("add".equals(method)) {
			log("新增地区码信息。操作员编号：" + operator.getOprId());
			rspCode = add();
		} else if("delete".equals(method)) {
			log("删除地区码信息。操作员编号：" + operator.getOprId());
			rspCode = delete();
		} else if("update".equals(method)) {
			log("同步地区码信息。操作员编号：" + operator.getOprId());
			rspCode = update();
		}
		
		return rspCode;
	}
	
	/**
	 * add city code
	 * @return
	 */
	private String add() {
		
		if(t10201BO.get(getCITY_CODE()) != null) {
			return "您所添加的地区码已经被使用";
		}
		

		TblCityCodeCode TblCityCodecode = new TblCityCodeCode();
		TblCityCodecode.setCITY_CODE(CITY_CODE);
		TblCityCodecode.setCITY_DES(CITY_DES);
		t10201BO.add(TblCityCodecode);
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * delete city code
	 * @return
	 */
	private String delete() {
		
		if(t10201BO.get(CITY_CODE) == null) {
			return "没有找到要删除的地区码信息";
		}
		
		t10201BO.delete(CITY_CODE);
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * update city code
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		jsonBean.parseJSONArrayData(getCityCodeList());
		
		int len = jsonBean.getArray().size();
		
		TblCityCodeCode tblCityCodeCode = null;
		
		List<TblCityCodeCode> cityCodeInfoList = new ArrayList<TblCityCodeCode>(len);
		
		for(int i = 0; i < len; i++) {			
			jsonBean.setObject(jsonBean.getJSONDataAt(i));	
			CITY_CODE= jsonBean.getJSONDataAt(i).getString("CITY_CODE");
			CITY_DES= jsonBean.getJSONDataAt(i).getString("CITY_DES");
			tblCityCodeCode	=t10201BO.get(CITY_CODE);
			tblCityCodeCode.setCITY_DES(CITY_DES);
			cityCodeInfoList.add(tblCityCodeCode);
		}
		
		t10201BO.update(cityCodeInfoList);
		
		return Constants.SUCCESS_CODE;
	}
	
	
	private String cityCodeList;	

	/**
	 * @return the cityCodeList
	 */
	public String getCityCodeList() {
		return cityCodeList;
	}

	/**
	 * @param cityCodeList the cityCodeList to set
	 */
	public void setCityCodeList(String cityCodeList) {
		this.cityCodeList = cityCodeList;
	}
	
	
}
