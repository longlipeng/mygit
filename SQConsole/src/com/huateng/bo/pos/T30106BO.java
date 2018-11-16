package com.huateng.bo.pos;

import java.util.List;

import com.huateng.po.pos.CstTermFeeIn;
import com.huateng.po.pos.CstTermFeePK;

public interface T30106BO {
    public String add(CstTermFeeIn cstTermFeeIn);
	
	public String update(CstTermFeeIn cstTermFeeIn);
	
	public CstTermFeeIn get(CstTermFeePK cstTermFeePK);
	public void delete(CstTermFeePK id);
	/* (non-Javadoc)
	 * @see com.huateng.bo.T10401BO#update(java.util.List)
	 */
	public  String update(List<CstTermFeeIn> cstTermFeeInList) ;
}
