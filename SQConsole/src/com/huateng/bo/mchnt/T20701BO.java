package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblHisDiscAlgoTmp;
import com.huateng.po.mchnt.TblInfDiscCdTmp;
import com.huateng.po.mchnt.TblInfDiscAlgo;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-20
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
public interface T20701BO {

	// 3月10号修改，原函数全部删除掉
	public String createArith(List<TblInfDiscAlgo> list,
			TblInfDiscCdTmp tblInfDiscCdTmp, List<TblHisDiscAlgoTmp> descList);

	public String updateArith(List<TblInfDiscAlgo> list,
			TblInfDiscCdTmp tblInfDiscCdTmp, List<TblHisDiscAlgoTmp> descList);

	public String deleteArith(String discCd);

	public TblInfDiscCdTmp getTblInfDiscCd(String discCd) throws Exception;

}
