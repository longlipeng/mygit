package com.huateng.dao.iface.error;

import java.util.List;
import com.huateng.po.error.TblAlgoDtl;
import com.huateng.po.error.TblAlgoDtlPK;

public interface TblAlgoDtlDAO {

	public TblAlgoDtl get(TblAlgoDtlPK key);

	public TblAlgoDtl load(TblAlgoDtlPK key);

	public List<TblAlgoDtl> findAll ();

	public String save(TblAlgoDtl tblAlgoDtl);

	public void saveOrUpdate(TblAlgoDtl tblAlgoDtl);

	public void update(TblAlgoDtl tblAlgoDtl);

	public void delete(TblAlgoDtlPK id);

	public void delete(TblAlgoDtl tblAlgoDtl);
}
