package com.huateng.bo.base;

import java.util.List;
import com.huateng.po.base.TblIssueBranchInf;

public interface T60101BO {
	public String add(TblIssueBranchInf tblIssueBranchInf);
	
	public String update(TblIssueBranchInf tblIssueBranchInf);
		
	public TblIssueBranchInf get(String branchId);
	
	public void delete(String branchId);
	public void delete(TblIssueBranchInf tblIssueBranchInf);
	
	public  String update(List<TblIssueBranchInf> tblIssueBranchInf) ;
}
