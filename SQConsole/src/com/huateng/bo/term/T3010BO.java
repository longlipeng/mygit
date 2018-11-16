package com.huateng.bo.term;

import java.util.List;

import com.huateng.common.Operator;
import com.huateng.po.TblTermInf;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.huateng.po.TblTermTmkLog;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;

/**
 * Title:终端信息BO
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-16
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public interface T3010BO {
	public void clear(TblTermInf tblTermInf);
	
	/**
	 * 增加终端信息
	 * @param tblTermInf
	 * @return
	 */
	public String add(TblTermInfTmp tblTermInfTmp);
	/**
	 * 更新终端信息
	 * @param tblTermInfTmp
	 * @return
	 */
	public boolean update(TblTermInfTmp tblTermInfTmp,String termSta);
	/**
	 * 更新终端信息
	 * @param tblTermInfTmp
	 * @return
	 */
	public void update(TblTermInfTmp tblTermInfTmp);
	/**
	 * 更新真实表终端信息
	 * @param tblTermInfTmp
	 * @return
	 */
	public boolean update(TblTermInf tblTermInf,String termSta);
	/**
	 * 查询终端信息
	 * @param key
	 * @return
	 * 2010-8-17下午05:07:50
	 */
	public TblTermInfTmp get(String termId,String recCrtTs);
	/**
	 * 获取终端号
	 * @param brhId
	 * @return
	 * 2011-6-21下午12:43:39
	 */
	public TblTermInf get1(String termId);
	/**
	 * 获取终端号
	 * @param brhId
	 * @return
	 * 2011-6-21下午12:43:39
	 */
	public String getTermId(String brhId);
	
	/**生成终端号，8位递增数字
	 * @return
	 */
	public String getTermNo();
	/**
	 * 保存终端审核信息入正式表
	 * @param tblTermInfTmp
	 * @param oprId
	 * @param termBranchNm
	 * @return
	 * 2011-9-1下午05:23:02
	 */
	public boolean save(TblTermInfTmp tblTermInfTmp,String oprId,String termBatchNm,String termSignSta);
	/**
	 * 查询符合条件的终端信息
	 * @param termNo
	 * @param state
	 * @param operator
	 * @param mchtCd
	 * @param termBranch
	 * @param startDate
	 * @param endDate
	 * @return
	 * 2011-7-5下午01:36:26
	 */
	public List qryTermInfo(String termNo,String state,Operator operator,String mchtCd,String termBranch,String startDate,String endDate);
	//密钥申请（带验证码） 批量申请时的查询条件
	public List qryTermInfo2(String termNo,String state,Operator operator,String mchtCd,String termBranch,String startDate,String endDate);
	//密钥申请（不带验证码） 批量申请时的查询条件
	public List qryTermInfo3(String termNo,String state,Operator operator,String mchtCd,String termBranch,String startDate,String endDate);

	/**
	 * 初始化申请密钥
	 * @param tblTermTmkLog
	 * @return
	 * 2011-7-5下午03:23:38
	 */
	public boolean initTmkLog(TblTermTmkLog tblTermTmkLog);
	/**
	 * 获取密钥下发日志表的批次号
	 * @return
	 * 2011-7-5下午03:29:42
	 */
	public String getBatchNo();
	/**
	 * 密钥下发审核（单笔）
	 * @param termIdId
	 * @param batchNo
	 * @return
	 * 2011-7-6下午01:37:20
	 */
	public String chkTmkLog(String termIdId,String batchNo,String oprId);
	/**
	 * 密钥下发审核（批量）
	 * @param batchNo
	 * @param oprId
	 * @return
	 * 2011-7-6下午02:30:19
	 */
	public String chkTmkLog(String batchNo,String oprId);
	/**
	 * 密钥下发拒绝
	 * @param batchNo
	 * @param oprId
	 * @return
	 * 2011-7-6下午02:30:19
	 */
	public String delTmkLog(String termIdId, String batchNo,String oprId);
	/**
	 * 密钥下发拒绝
	 * @param batchNo
	 * @param oprId
	 * @return
	 * 2011-7-6下午02:30:19
	 */
	public String delTmkLog(String batchNo,String oprId);
	/**
	 * 批量初始化申请密钥
	 * @param list
	 * @return
	 * 2011-7-7上午11:01:27
	 */
	public boolean initTmkLog(List<TblTermTmkLog> list);
	/**
	 * 获取终端正式表中的信息
	 * @param termId
	 * @return
	 * 2011-8-1下午01:50:07
	 */
	public TblTermInf getTermInfo(String termId);
	/**
	 * 终端拒绝原因
	 * @param termId
	 * @param refuseInfo
	 * @param refuseType
	 * @return
	 * 2011-8-22下午06:22:57
	 */
	public String refuseLog( String termId,String refuseInfo,String refuseType);
	
	/**
	 * 判断终端密钥下发状态
	 * @param termId
	 * @return
	 * 2011-8-23下午03:41:51
	 */
	public boolean chkTmkLog(String termId);
	
	/**
	 * 终端拒绝回退
	 * @param termId
	 * @param refuseInfo
	 * @param refuseType
	 * @return
	 * 2011-8-22下午06:22:57
	 */
	public String refuse(TblTermInfTmpPK pk,String state,String oprId);
	
	/**
	 * 查询商户信息
	 * @param mchntId    商户编号
	 * @return
	 */
	public TblMchtBaseInfTmp getMchnt(String mchntId);
	/**
	 * 查询商户
	 * @param key
	 * @return
	 * 2011-6-16下午01:42:16
	 */
	public TblMchtBaseInf get(String key);
	
	/**
	 * 删除临时表信息
	 * 
	 */
	public void delete(TblTermInfTmpPK tblTermInfTmpPK);
	/**
	 * 查询终端信息
	 * @param key
	 * @return
	 * 2010-8-17下午05:07:50
	 */
	public TblTermInfTmp get2(TblTermInfTmpPK tblTermInfTmpPK);
}
