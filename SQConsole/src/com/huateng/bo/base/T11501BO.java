package com.huateng.bo.base;

import com.huateng.po.base.TblAgentInfo;

public interface T11501BO {
    public TblAgentInfo get(String agentNo);
    public String delete(String agentNo)throws Exception;
    public String add(TblAgentInfo tblAgentInfo) throws Exception;
    public String update(TblAgentInfo tblAgentInfo) throws Exception;
    
}
