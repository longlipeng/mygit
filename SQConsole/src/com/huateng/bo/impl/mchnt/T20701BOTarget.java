package com.huateng.bo.impl.mchnt;

import java.math.BigDecimal;
import java.util.List;

import com.huateng.bo.mchnt.T20701BO;
import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.mchnt.TblHisDiscAlgoTmpDAO;
import com.huateng.dao.iface.mchnt.TblInfDiscAlgoDAO;
import com.huateng.dao.iface.mchnt.TblInfDiscCdTmpDAO;
import com.huateng.po.mchnt.TblHisDiscAlgoTmp;
import com.huateng.po.mchnt.TblInfDiscAlgo;
import com.huateng.po.mchnt.TblInfDiscCdTmp;
import com.huateng.system.util.CommonFunction;

/**
 * Title:商户组别维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-10
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author
 * 
 * @version 1.0
 */
public class T20701BOTarget implements T20701BO {

	private TblInfDiscAlgoDAO tblInfDiscAlgoDAO;//存在
	private TblInfDiscCdTmpDAO tblInfDiscCdTmpDAO;//存在 建临时表
	private TblHisDiscAlgoTmpDAO tblHisDiscAlgoTmpDAO;//存在 建临时表


	public TblHisDiscAlgoTmpDAO getTblHisDiscAlgoTmpDAO() {
		return tblHisDiscAlgoTmpDAO;
	}

	public void setTblHisDiscAlgoTmpDAO(TblHisDiscAlgoTmpDAO tblHisDiscAlgoTmpDAO) {
		this.tblHisDiscAlgoTmpDAO = tblHisDiscAlgoTmpDAO;
	}

	private ICommQueryDAO commQueryDAO;
	public TblInfDiscCdTmpDAO getTblInfDiscCdTmpDAO() {
		return tblInfDiscCdTmpDAO;
	}

	public void setTblInfDiscCdTmpDAO(TblInfDiscCdTmpDAO tblInfDiscCdTmpDAO) {
		this.tblInfDiscCdTmpDAO = tblInfDiscCdTmpDAO;
	}
	public TblInfDiscAlgoDAO getTblInfDiscAlgoDAO() {
		return tblInfDiscAlgoDAO;
	}

	public void setTblInfDiscAlgoDAO(TblInfDiscAlgoDAO tblInfDiscAlgoDAO) {
		this.tblInfDiscAlgoDAO = tblInfDiscAlgoDAO;
	}


	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	public String createArith(List<TblInfDiscAlgo> list,
			TblInfDiscCdTmp tblInfDiscCdTmp, List<TblHisDiscAlgoTmp> descList) {
		
		if(tblInfDiscCdTmpDAO.get(tblInfDiscCdTmp.getDiscCd()) != null) {
			return "该算法信息已经存在";
		}
				
		tblInfDiscCdTmpDAO.save(tblInfDiscCdTmp);// 商户手续费名称定义表
		//如存在不正确数据就先删除
		String sql = "delete from tbl_his_disc_algo_tmp where trim(DISC_ID) = '"
			+ tblInfDiscCdTmp.getDiscCd().trim() + "'";
		commQueryDAO.excute(sql);
		
		for (TblHisDiscAlgoTmp his : descList) {
			tblHisDiscAlgoTmpDAO.save(his);// 商户手续费配置表
		}
		return Constants.SUCCESS_CODE;
	}

	/* 
	 * 更新计费信息
	 * 
	 * @see com.huateng.bo.mchnt.T20701BO#updateArith(java.util.List, com.huateng.po.mchnt.TblInfDiscCd, java.util.List)
	 */
	public String updateArith(List<TblInfDiscAlgo> list,
			TblInfDiscCdTmp tblInfDiscCdTmp, List<TblHisDiscAlgoTmp> descList) {

		String sql = "delete from tbl_his_disc_algo_tmp where trim(DISC_ID) = '"
				+ tblInfDiscCdTmp.getDiscCd().trim() + "'";
		commQueryDAO.excute(sql);

		tblInfDiscCdTmpDAO.update(tblInfDiscCdTmp);// 商户手续费名称定义表
		 
		for (TblHisDiscAlgoTmp his : descList) {
			his.setRecUpdUsrId(tblInfDiscCdTmp.getRecUpdUserId());//审核通过都有时间直接从tblInfDiscCdTmp表里面取数据
			his.setRecUpdTs(tblInfDiscCdTmp.getRecUpdTs());
			his.setRecCrtTs(tblInfDiscCdTmp.getRecCrtTs());
			tblHisDiscAlgoTmpDAO.save(his);// 商户手续费配置表
		}

		return Constants.SUCCESS_CODE;
	}

	/*
	 * 删除计费信息
	 * 
	 * @see com.huateng.bo.mchnt.T20701BO#deleteArith(java.lang.String)
	 */
	public String deleteArith(String discCd) {
	
		//判断当前商户是否有使用该计费算法
		String s = "select count(1) from TBL_MCHT_SETTLE_INF where TRIM(FEE_RATE) = '" + discCd + "'";
		BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(s).get(0);
		if (count.intValue() > 0) {
			return "该计费算法已被商户使用，不能被删除";
		}
		s = "select count(1) from TBL_MCHT_SETTLE_INF_TMP where TRIM(FEE_RATE) = '" + discCd + "'";
		count = (BigDecimal) commQueryDAO.findBySQLQuery(s).get(0);
		if (count.intValue() > 0) {
			return "该计费算法已被商户使用，不能被删除";
		}
		
		String sql = "delete from tbl_his_disc_algo_tmp where trim(DISC_ID) = '"
				+ discCd.trim() + "'";
		commQueryDAO.excute(sql);

		tblInfDiscCdTmpDAO.delete(CommonFunction.fillString(discCd, ' ', 5, true));

		return Constants.SUCCESS_CODE;
	}

	/*
	 * 取得计费信息
	 * 
	 * @see com.huateng.bo.mchnt.T20701BO#getTblInfDiscCd(java.lang.String)
	 */
	public TblInfDiscCdTmp getTblInfDiscCd(String discCd) throws Exception {
		return tblInfDiscCdTmpDAO.get(CommonFunction.fillString(discCd, ' ', 5,
				true));
	}

}
