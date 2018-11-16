package com.huateng.dao.iface.pos;

import com.huateng.po.pos.CstTermFeeIn;
import com.huateng.po.pos.CstTermFeePK;

public interface CstTermFeeInDAO {
	public Class<CstTermFeeIn> getReferenceClass () ;
	public CstTermFeeIn cast (Object object);
	public CstTermFeeIn load(CstTermFeePK key);
	public CstTermFeeIn get(CstTermFeePK key);
	public CstTermFeePK save(CstTermFeeIn cstTermFeeIn);
	public void saveOrUpdate(CstTermFeeIn cstTermFeeIn);
	public void update(CstTermFeeIn cstTermFeeIn);
	public void delete(CstTermFeeIn cstTermFeeIn);
	public void delete(CstTermFeePK id);
}
