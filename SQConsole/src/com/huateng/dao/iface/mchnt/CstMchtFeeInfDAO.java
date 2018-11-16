package com.huateng.dao.iface.mchnt;

import com.huateng.po.mchnt.CstMchtFeeInf;
import com.huateng.po.mchnt.CstMchtFeeInfPK;
import com.huateng.po.mchnt.CstMchtFeeInfTmp;

public interface CstMchtFeeInfDAO {
	public Class<CstMchtFeeInf> getReferenceClass () ;
	public CstMchtFeeInf cast (Object object);
	public CstMchtFeeInf load(CstMchtFeeInfPK key);
	public CstMchtFeeInf get(CstMchtFeeInfPK key);
	public CstMchtFeeInfTmp getTmp(CstMchtFeeInfPK key);
	public CstMchtFeeInfPK save(CstMchtFeeInf cstMchtFeeInf);
	
	public CstMchtFeeInfPK saveTmp(CstMchtFeeInfTmp cstMchtFeeInf);
	
	public void saveOrUpdate(CstMchtFeeInf cstMchtFeeInf);
	public void saveOrUpdate(CstMchtFeeInfTmp cstMchtFeeInf);
	
	public void update(CstMchtFeeInf cstMchtFeeInf);
	public void updateTmp(CstMchtFeeInfTmp cstMchtFeeInf);
	
	public void delete(CstMchtFeeInf cstMchtFeeInf);
	public void delete(CstMchtFeeInfPK id);
}