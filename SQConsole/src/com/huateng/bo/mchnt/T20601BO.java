package com.huateng.bo.mchnt;
import java.util.List;

import com.huateng.po.mchnt.TblMchtBranInf;
import com.huateng.po.mchnt.TblMchtBranInfPK;



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
public interface T20601BO {
	
	public String addMchtBranchInfo(TblMchtBranInf tblMchtBranInf);
	public void delete(TblMchtBranInfPK pk);	
	public String updateMchtBranchInfo(TblMchtBranInf tblMchtBranInf);	
	public TblMchtBranInf getMchtBranchInfo(TblMchtBranInfPK key);
	public String update(List<TblMchtBranInf> tblMchtBranInfList);	
}
