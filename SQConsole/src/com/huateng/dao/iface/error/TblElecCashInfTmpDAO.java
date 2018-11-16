package com.huateng.dao.iface.error;

import java.util.List;
import com.huateng.po.error.TblElecCashInfTmp;

public interface TblElecCashInfTmpDAO {

	public TblElecCashInfTmp get(String key);

	public TblElecCashInfTmp load(String key);

	public List<TblElecCashInfTmp> findAll ();

	public String save(TblElecCashInfTmp tblElecCashInfTmp);

	public void saveOrUpdate(TblElecCashInfTmp tblElecCashInfTmp);

	public void update(TblElecCashInfTmp tblElecCashInfTmp);

	public void delete(String id);

	public void delete(TblElecCashInfTmp tblElecCashInfTmp);
	
}
