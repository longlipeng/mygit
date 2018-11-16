package com.huateng.dao.iface.risk;

import com.huateng.po.risk.TblRiskMchtTranLimit;

public interface TblRiskMchtTranLimitDAO {


	public TblRiskMchtTranLimit get(java.lang.String key);

	public TblRiskMchtTranLimit load(java.lang.String key);

	public java.util.List<TblRiskMchtTranLimit> findAll ();

	public java.lang.String save(TblRiskMchtTranLimit tblRiskMchtTranLimit);

	public void saveOrUpdate(TblRiskMchtTranLimit tblRiskMchtTranLimit);

	public void update(TblRiskMchtTranLimit tblRiskMchtTranLimit);

	public void delete(java.lang.String id);

	public void delete(TblRiskMchtTranLimit tblRiskMchtTranLimit);

}
