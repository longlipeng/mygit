package com.huateng.dao.iface.pos;

import com.huateng.po.pos.CstTermFeeInTrue;
import com.huateng.po.pos.CstTermFeePK;

public interface CstTermFeeInTrueDAO {
	public Class<CstTermFeeInTrue> getReferenceClass () ;
	public CstTermFeeInTrue cast (Object object);
	public CstTermFeeInTrue load(CstTermFeePK key);
	public CstTermFeeInTrue get(CstTermFeePK key);
	public CstTermFeePK save(CstTermFeeInTrue cstTermFeeInTrue);
	public void saveOrUpdate(CstTermFeeInTrue cstTermFeeInTrue);
	public void update(CstTermFeeInTrue cstTermFeeInTrue);
	public void delete(CstTermFeeInTrue cstTermFeeInTrue);
	public void delete(CstTermFeePK id);
}
