package com.huateng.dao.iface.risk;

import java.util.List;
import com.huateng.po.risk.TblRiskTermTranLimit;

public interface TblRiskTermTranLimitDAO {

	public TblRiskTermTranLimit get(String key);

	public TblRiskTermTranLimit load(String key);

	public List<TblRiskTermTranLimit> findAll ();

	public String save(TblRiskTermTranLimit tblRiskTermTranLimit);

	public void saveOrUpdate(TblRiskTermTranLimit tblRiskTermTranLimit);

	public void update(TblRiskTermTranLimit tblRiskTermTranLimit);

	public void delete(String id);

	public void delete(TblRiskTermTranLimit tblRiskTermTranLimit);
	
}
