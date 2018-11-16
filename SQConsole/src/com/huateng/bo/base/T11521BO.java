package com.huateng.bo.base;

import com.huateng.po.base.TblAgentFee;
import com.huateng.po.base.TblAgentFeeRefuseInfo;
import com.huateng.po.base.TblAgentFeeTmp;

public interface T11521BO {
    public TblAgentFeeTmp get(String uuid);
    public TblAgentFee getTrue(String uuid);
    public String delete(String uuId)throws Exception;
    public String add(TblAgentFee tblAgentFee) throws Exception;
    public String update(TblAgentFee tblAgentFee) throws Exception;
	public String add(TblAgentFeeTmp tblAgentFeeTmp);
	public String update(TblAgentFeeTmp tblAgentFeeTmp) throws Exception;
	public String deleteTmp(String idkey)throws Exception;
	public String add(TblAgentFeeRefuseInfo tblAgentFeeRefuseInfo)throws Exception;
	public String saveOrUpdate(TblAgentFeeTmp tblAgentFeeTmp) throws Exception;
	public String saveOrUpdate(TblAgentFee tblAgentFee) throws Exception;
    
}
