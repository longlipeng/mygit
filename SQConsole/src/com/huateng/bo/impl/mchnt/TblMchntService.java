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
package com.huateng.bo.impl.mchnt;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.huateng.po.mchnt.TblMchtBeneficiaryInf;
import com.huateng.po.mchnt.TblMchtBeneficiaryInfTmp;
import com.huateng.po.mchnt.CstMchtFeeInfTmp;
import com.huateng.po.mchnt.TblGroupMchtInf;
import com.huateng.po.mchnt.TblMchtAcctInf;
import com.huateng.po.mchnt.TblMchtBaseBusiRange;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInf;
import com.huateng.po.mchnt.TblMchtSettleInfPK;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.po.mchnt.TblMchtSupp1;
import com.huateng.po.mchnt.TblMchtSupp1Tmp;



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
public interface TblMchntService {
	
	/**
	 * 保存商户临时信息
	 * @param tblMchtBaseInfTmp
	 * @param tblMchtSettleInfTmp
	 * @param supp1Tmp 
	 * @return
	 */
	public String saveTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp, TblMchtSettleInfTmp tblMchtSettleInfTmp, TblMchtSupp1Tmp supp1Tmp, TblMchtBaseBusiRange tblMchtBaseBusiRange);
	public String getMchtNo(String mchtNo);
	
	/**
	 * 更新商户临时信息
	 * @param tblMchtBaseInfTmp
	 * @param tblMchtSettleInfTmp
	 * @param supp1Tmp 
	 * @return
	 */
	public String updateTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp,TblMchtSupp1Tmp supp1Tmp);
	
	/**
	 * 商户信息审核（通过）
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String accept(String mchntId,String oprInfo) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 商户信息审核（退回）
	 * @param mchntId
	 * @param refuseInfo
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String back(String mchntId, String refuseInfo) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 商户信息审核（拒绝）
	 * @param mchntId
	 * @param refuseInfo
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String refuse(String mchntId, String refuseInfo) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 保存集团商户信息
	 * @param inf
	 * @param acctinf
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String saveGroup(TblGroupMchtInf inf, TblMchtAcctInf acctinf) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 更新集团商户信息
	 * @param inf
	 * @param acctinf
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String updateGroup(TblGroupMchtInf inf, TblMchtAcctInf acctinf) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 获取商户信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * 2011-6-24下午02:34:05
	 */
	public TblMchtBaseInf getMccByMchntId(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TblGroupMchtInf getGroupInf(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 读取商户临时表基本信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TblMchtBaseInfTmp getBaseInfTmp(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	public TblMchtBaseInf getBaseInf(String mchntId) throws IllegalAccessException, InvocationTargetException;

	/**
	 * 更新商户临时表基本信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String updateBaseInfTmp(TblMchtBaseInfTmp inf) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 读取商户临时表清算信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TblMchtSettleInfTmp getSettleInfTmp(TblMchtSettleInfPK id) throws IllegalAccessException, InvocationTargetException;
	public TblMchtSettleInf getSettleInf(TblMchtSettleInfPK id) throws IllegalAccessException, InvocationTargetException;

	public TblMchtSupp1Tmp getMchtSupp1Tmp(String parameter);
	public TblMchtSupp1 getMchtSupp1(String parameter);
	
	/**
	 * 临时表
	 * 根据商户编号查询受益人信息
	 * @param mchntId
	 * @return
	 */
	public List<TblMchtBeneficiaryInfTmp> getMchtBeneficiaryTmp(String mchtNo);
	
	/**
	 * 正式表
	 * 根据商户编号查询受益人信息
	 * @param mchntId
	 * @return
	 */
	public List<TblMchtBeneficiaryInf> getMchtBeneficiary(String mchtNo);
	
	/**
	 * 正式表
	 * 新增或修改
	 */
	public String saveOrUpdate(TblMchtBeneficiaryInf tblMchtBeneficiaryInf);
	
	/**
	 * 临时表
	 * 新增或修改
	 */
	public String saveOrUpdate(TblMchtBeneficiaryInfTmp tblMchtBeneficiaryInfTmp);
	
	/**
	 * 修改正式表id
	 */
	public String beneficiaryUpdate(TblMchtBeneficiaryInf tblMchtBeneficiaryInf);
	
	/**
	 * 修改临时表id
	 */
	public String beneficiaryUpdateTmp(TblMchtBeneficiaryInfTmp tblMchtBeneficiaryInfTmp);
	
	/**
	 * 删除
	 * @return
	 */
	public String beneficiaryDel(String beneficiaryId);
	
	/**
	 * 新增
	 * 正式表
	 * @param tblMchtBeneficiaryInfTmp
	 * @return
	 */
	public String beneficiaryAdd(TblMchtBeneficiaryInfTmp tblMchtBeneficiaryInfTmp);
	
	/**
	 * 新增
	 * 临时表
	 * @param tblMchtBeneficiaryInf
	 * @return
	 */
	public String beneficiaryAdd1(TblMchtBeneficiaryInf tblMchtBeneficiaryInf);
	
	/**
	 * 根据id查询数据
	 * @return
	 */
	public TblMchtBeneficiaryInf getBeneficiaryInf(String beneficiaryId);
	
	/**
	 * 根据mchtId查询数据
	 * @param mchtId
	 * @return
	 */
	public TblMchtBaseBusiRange getBaseBusiRange(String mchtId);
	
	/**
	 * 新增
	 * @param tblMchtBaseBusiRange
	 * @return
	 */
	public String addBaseBusiRange(TblMchtBaseBusiRange tblMchtBaseBusiRange);
	
	/**
	 * 修改
	 * @param tblMchtBaseBusiRange
	 * @return
	 */
	public String upBaseBusiRange(TblMchtBaseBusiRange tblMchtBaseBusiRange);
	
	  //新增商户审核通过
	public String acceptAdd(String mchntId, String mchntInd,String oprInfo, CstMchtFeeInfTmp cstMchtFeeInfTmpDebit,
			CstMchtFeeInfTmp cstMchtFeeInfTmpCredit, String isRoute, String isWhite) throws IllegalAccessException, InvocationTargetException;



	public String back2(String mchntId, String oprInfo) throws IllegalAccessException, InvocationTargetException;
	public String saveSettleInfTmp(TblMchtSettleInfTmp settleInfTmp) throws IllegalAccessException, InvocationTargetException;
	public String updateSettleInfTmp(TblMchtSettleInfTmp settleInf) throws IllegalAccessException, InvocationTargetException;
	public String deleteSettleInfTmp(TblMchtSettleInfTmp settleInf) throws IllegalAccessException, InvocationTargetException;
	public String updateTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp,
			TblMchtSettleInfTmp tblMchtSettleInfTmp, TblMchtSupp1Tmp supp1Tmp);
	public String updateTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp,
			TblMchtSettleInfTmp tblMchtSettleInfTmp, TblMchtSupp1Tmp supp1Tmp,
			String mchtNoInput);
	
	public void updateMchntLogs(TblMchtBaseInfTmp tblMchtBaseInfTmp, String oprType, String oprStatus, String oprInfo);
	
}
