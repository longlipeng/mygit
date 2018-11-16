package com.huateng.dao.iface.error;

import java.util.List;
import com.huateng.po.error.TblElecCashInf;

public interface TblElecCashInfDAO {

	public TblElecCashInf get(String key);

	public TblElecCashInf load(String key);

	public List<TblElecCashInf> findAll ();

	public String save(TblElecCashInf tblElecCashInf);

	public void saveOrUpdate(TblElecCashInf tblElecCashInf);

	public void update(TblElecCashInf tblElecCashInf);

	public void delete(String id);

	public void delete(TblElecCashInf tblElecCashInf);
	
}
