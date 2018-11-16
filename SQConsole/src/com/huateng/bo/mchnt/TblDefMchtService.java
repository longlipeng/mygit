/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-6-17       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.huateng.po.mchnt.CstMchtFeeInfTmp;
import com.huateng.po.mchnt.TblDefMchtInf;




/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-17
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public interface TblDefMchtService {
	
	
	/**
	 * 读取商户进件表基本信息
	 * @param recId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TblDefMchtInf getDefMchtInf(String recId)  throws IllegalAccessException, InvocationTargetException;

	public String acceptAdd(String mchntId, String oprInfo,
			CstMchtFeeInfTmp cstMchtFeeInfTmpDebit,
			CstMchtFeeInfTmp cstMchtFeeInfTmpCredit, String checked,
			String checked2) throws IllegalAccessException, InvocationTargetException, IOException;

	public String back(String mchntId, String oprInfo) throws IllegalAccessException, InvocationTargetException;

	public String refuse(String mchntId, String oprInfo) throws IllegalAccessException, InvocationTargetException;
}
