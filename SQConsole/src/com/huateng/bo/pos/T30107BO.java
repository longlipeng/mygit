package com.huateng.bo.pos;

import java.util.List;

import com.huateng.po.pos.CstTermFeeInTrue;
import com.huateng.po.pos.CstTermFeePK;

public interface T30107BO {
	  public String add(CstTermFeeInTrue cstTermFeeInTrue);
		
		public String update(CstTermFeeInTrue cstTermFeeInTrue);
		
		public CstTermFeeInTrue get(CstTermFeePK cstTermFeePK);
		public void delete(CstTermFeePK id);
		/* (non-Javadoc)
		 * @see com.huateng.bo.T10401BO#update(java.util.List)
		 */
		public String update(List<CstTermFeeInTrue> cstTermFeeInTrueList) ;
}
