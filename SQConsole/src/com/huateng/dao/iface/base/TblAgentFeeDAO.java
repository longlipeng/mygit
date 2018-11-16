package com.huateng.dao.iface.base;

import com.huateng.po.base.TblAgentFee;
import com.huateng.po.base.TblAgentFeeRefuseInfo;
import com.huateng.po.base.TblAgentFeeTmp;
import com.huateng.po.base.TblAgentInfo;

public interface TblAgentFeeDAO {
	
	public TblAgentFee load(String key);
	
	public TblAgentFeeTmp get(String uuid);
	
	public TblAgentFee getTrue(String uuid);
	
	public void delete(String uuId);
	
	public String save(TblAgentFee tblAgentFee);
	
	public void update(TblAgentFee tblAgentFee);

	public String save(TblAgentFeeTmp tblAgentFeeTmp);

	public void update(TblAgentFeeTmp tblAgentFeeTmp);

	public void deleteTmp(String idkey);

	public String save(TblAgentFeeRefuseInfo tblAgentFeeRefuseInfo);
	
	public void saveOrUpdate(TblAgentFee tblAgentFee);
	
	public void saveOrUpdate(TblAgentFeeTmp tblAgentFeeTmp);
	
	

}
