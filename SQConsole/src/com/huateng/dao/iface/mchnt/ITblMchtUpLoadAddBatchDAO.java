package com.huateng.dao.iface.mchnt;

import java.util.List;

import com.huateng.po.TblTermInfTmp;

import com.huateng.po.base.TblSignInf;
import com.huateng.po.mchnt.TblHisDiscAlgo2Tmp;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.po.mchnt.TblMchtSupp1Tmp;


public interface ITblMchtUpLoadAddBatchDAO {
	public abstract Object upLoadAddBatch(final List<TblMchtBaseInfTmp> MchtBaseInfListAdd,final List<TblMchtSupp1Tmp> MchtSupp1ListAdd,final List<TblHisDiscAlgo2Tmp> HisDiscAlgo2ListAdd
			,final List<TblMchtSettleInfTmp> MchtSettleInfTmpListAdd) throws Exception;   //佰付通商户批量上传
	
	public Object upLoadBFZBatch(final List<TblTermInfTmp> TblTermInfTmpList) throws Exception; //佰付通商户终端商户批量上传

}
