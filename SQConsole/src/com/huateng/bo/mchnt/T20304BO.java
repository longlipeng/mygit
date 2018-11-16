package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.po.mchnt.CstMchtFeeInf;
import com.huateng.po.mchnt.CstMchtFeeInfPK;
import com.huateng.po.mchnt.CstMchtFeeInfTmp;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-15
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
public interface T20304BO {
	public String saveOrUpdate(CstMchtFeeInf cstMchtFeeInf);
	
	public String saveOrUpdate(CstMchtFeeInfTmp cstMchtFeeInf);
	
	public String updateMchtLimit(CstMchtFeeInf cstMchtFeeInf);
	
	public CstMchtFeeInfTmp getMchtLimitTmp(CstMchtFeeInfPK cstMchtFeeInfPK);
	
	public CstMchtFeeInf getMchtLimit(CstMchtFeeInfPK cstMchtFeeInfPK);
	
	public void delete(CstMchtFeeInfPK id);
	/* (non-Javadoc)
	 * @see com.huateng.bo.T10401BO#update(java.util.List)
	 */
	public String update(List<CstMchtFeeInf> cstMchtFeeInfList) ;

	public String updateTmp(List<CstMchtFeeInfTmp> cstMchtFeeInfList);
}
