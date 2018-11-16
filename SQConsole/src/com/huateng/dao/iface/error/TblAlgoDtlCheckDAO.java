package com.huateng.dao.iface.error;

import java.util.List;
import com.huateng.po.error.TblAlgoDtlCheck;

public interface TblAlgoDtlCheckDAO {

	public TblAlgoDtlCheck get(String key);

	public TblAlgoDtlCheck load(String key);

	public List<TblAlgoDtlCheck> findAll ();

	public String save(TblAlgoDtlCheck tblAlgoDtlCheck);

	public void saveOrUpdate(TblAlgoDtlCheck tblAlgoDtlCheck);

	public void update(TblAlgoDtlCheck tblAlgoDtlCheck);

	public void delete(String id);

	public void delete(TblAlgoDtlCheck tblAlgoDtlCheck);
	
}
