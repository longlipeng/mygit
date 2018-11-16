/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-20       first release
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
package com.huateng.bo.risk;

import com.huateng.common.Operator;
import com.huateng.po.TblRiskInf;

/**
 * Title:风险模型设置
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-20
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public interface T40101BO {
	/**
	 * 更新风险模型信息
	 * @param tblRiskInf
	 * @return
	 * @throws Exception
	 * 2010-8-20上午11:40:01
	 */
	public String update(TblRiskInf tblRiskInfNew ) throws Exception;
	
	/**
	 * 查找风险模型
	 * @param key
	 * @return
	 * @throws Exception
	 * 2011-6-17上午10:28:30
	 */
	public TblRiskInf get(String key) throws Exception;
	
	/**
	 * 审核风险模型
	 * @param key
	 * @return
	 * @throws Exception
	 * 2011-6-17上午10:28:30
	 */
	public String check(TblRiskInf tblRiskInf,Operator operator) throws Exception;
	
	/**
	 * 保存审核通过后，风险模型修改记录
	 * @throws Exception
	 */
	public boolean recordModify(TblRiskInf tblRiskInf,Operator operator) throws Exception;
	
	/**
	 * 新增风险规则
	 * @return
	 * @throws Exception
	 */
	public String insert(TblRiskInf tblRiskInf) throws Exception;
	
	/**
	 * 删除风险规则
	 * @param tblRiskInf
	 * @return
	 * @throws Exception
	 */
	public String delete(TblRiskInf tblRiskInf) throws Exception;

	public String delete(String saModelKind);
	
}
