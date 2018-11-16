/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-5-21       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2008 Huateng Software, Inc. All rights reserved.
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
package com.huateng.struts.system.action;

import org.apache.commons.lang.StringUtils;

import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.dao.iface.base.TblOprInfoDAO;
import com.huateng.po.TblOprInfo;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.Encryption;
import com.huateng.system.util.SysParamUtil;

/**
 * Title:系统登录
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-5-21
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = -915297506148396706L;

	/**操作员编号*/
	private String oprid;
	/**操作员授权码*/
	private String password;
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		TblOprInfoDAO tblOprInfoDAO = (TblOprInfoDAO) ContextUtil.getBean("OprInfoDAO");
		
		//判断ID是否为有效ID
		boolean authUser = false;
		try {
			String ids = SysParamUtil.getParam("AUTH_USER");
			if (StringUtil.isNull(ids)) {
				writeErrorMsg("登录失败，没有找到可登录的柜员ID。");
				return SUCCESS;
			} else {
				String[] id = ids.split(",");
				for (String tmp : id) {
					if (!StringUtil.isNull(tmp) && tmp.equals(oprid)) {
						authUser = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			log("登录失败，解析可登录柜员失败。");
			e.printStackTrace();
		}
//		if (!authUser) {
//			log("登录失败，柜员[" + oprid + "]不是可登录的柜员ID。");
//			writeErrorMsg("登录失败，柜员[" + oprid + "]不是可登录的柜员ID，请至统一认证平台登录。");
//			return SUCCESS;
//		}
		
		TblOprInfo tblOprInfo = tblOprInfoDAO.get(oprid);
		
		//判断操作员是否存在
		if(tblOprInfo == null) {
			log("登录失败，操作员不存在。编号[ " + oprid + " ]");
			writeErrorMsg("登录失败，操作员不存在");
			return SUCCESS;
		}
		
		// 判断操作员合法性
		if("1".equals(tblOprInfo.getOprSta().trim())) {
			log("登录失败，操作员被冻结。编号[ " + oprid + " ]");
			writeErrorMsg("该操作员已经被冻结，请与管理员联系。");
			return SUCCESS;
		}
		
		// 判断授权码是否过期
		if(Integer.parseInt(tblOprInfo.getPwdOutDate().trim()) <= Integer.parseInt(CommonFunction.getCurrentDate())) {
			log("登录失败，操作员授权码过期。编号[ " + oprid + " ]");
			writeAlertMsg("您的授权码已经过期，请进行修改。", SysParamUtil.getParam(SysParamConstants.RESET_PWD));
			return SUCCESS;
		}
		
		//判断授权码输入是否正确
		String oprPassword = StringUtils.trim(tblOprInfo.getOprPwd());
		password = StringUtils.trim(Encryption.encrypt(password));
		if(!oprPassword.equals(password)) {
			
//			if(!CommonFunction.getCurrentDate().equals(tblOprInfo.getPwdWrLastDt())) {
//				tblOprInfo.setPwdWrLastDt(CommonFunction.getCurrentDate());
//				tblOprInfo.setPwdWrTm("1");
//			} else {
//				tblOprInfo.setPwdWrTm(String.valueOf(Integer.parseInt(tblOprInfo.getPwdWrTm().trim()) + 1));
//			}
//			tblOprInfo.setPwdWrTmTotal(String.valueOf(Integer.parseInt(tblOprInfo.getPwdWrTmTotal().trim()) + 1));
//			
//			// 如果输入错误总次数大于5次或当天输入错误次数大于3次则冻结操作员
//			if(Integer.parseInt(tblOprInfo.getPwdWrTmTotal().trim()) >= 5 || 
//					(Integer.parseInt(tblOprInfo.getPwdWrTm().trim()) >= 3 && 
//							CommonFunction.getCurrentDate().equals(tblOprInfo.getPwdWrLastDt().trim()))) {
//				tblOprInfo.setOprSta("1");
//			}
//			tblOprInfoDAO.update(tblOprInfo);
			
			log("登录失败，授权码错误。编号[ " + oprid + " ]");
			writeErrorMsg("登录失败，您输入的授权码错误");
			return SUCCESS;
		}
		
		setSessionAttribute("oprId", oprid);
		setSessionAttribute("operator", tblOprInfo);
		writeSuccessMsg("登录成功");
		return SUCCESS;
	}

	public String getOprid() {
		return oprid;
	}

	public void setOprid(String oprid) {
		this.oprid = oprid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
