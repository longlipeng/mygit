/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-10       first release
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
package com.huateng.bo.mchnt;


import com.huateng.common.Operator;
import com.huateng.po.TblMchntRefusePK;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;

/**
 * Title:商户信息审核BO
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-10
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public interface T20201BO {
	
	/**
	 * 商户审核通过
	 * @param mchntId    商户编号
	 * @return
	 * @throws Exception 
	 */
	public String accept(String mchntId) throws Exception;
	
	/**
	 * 商户审核退回
	 * @param mchntId    商户编号
	 * @param refuseInfo    退回原因
	 * @param operator    操作员信息
	 * @return
	 */
	public String back(String mchntId,String refuseInfo,Operator operator);
	
	/**
	 * 商户审核拒绝
	 * @param mchntId    商户编号
	 * @param refuseInfo    拒绝原因
	 * @param operator    操作员信息
	 * @return
	 * @throws Exception 
	 */
	public String refuse(String mchntId,String refuseInfo,Operator operator) throws Exception;
	
	/**
	 * 查询商户
	 * @param key
	 * @return
	 * 2011-6-16下午01:42:16
	 */
	public TblMchtBaseInf get(String key);
	/**
	 * 商户拒绝删除
	 */
	public void delete(TblMchntRefusePK  tblMchntRefusePK);
}
