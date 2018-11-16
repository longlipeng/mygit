package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.po.TblTermInfTmp;

import com.huateng.po.mchnt.TblHisDiscAlgo2Tmp;

import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.po.mchnt.TblMchtSupp1Tmp;


public interface T20105BO {
	public int saveUploadForAddBatch(List<TblMchtBaseInfTmp> MchtBaseInfListAdd, List<TblMchtSupp1Tmp> MchtSupp1ListAdd,List<TblHisDiscAlgo2Tmp> HisDiscAlgo2ListAdd
			,List<TblMchtSettleInfTmp> MchtSettleInfTmpListAdd) throws Exception;
	
	public int saveForBFZBatch(List<TblTermInfTmp> TblTermInfTmpList)throws Exception;
	
}
