package com.huateng.dao.iface.rout;

import java.util.List;
import com.huateng.po.rout.TblRouteChgInf;

public interface TblRouteChgInfDAO {

	public TblRouteChgInf getTblRouteChgInf(String key);

	public TblRouteChgInf loadTblRouteChgInf(String key);

	public List<TblRouteChgInf> findAllTblRouteChgInf();

	public String save(TblRouteChgInf tblRouteChgInf);

	public void saveOrUpdate(TblRouteChgInf tblRouteChgInf);

	public void update(TblRouteChgInf tblRouteChgInf);

	public void deleteTblRouteChgInf(String destInstId);

	public void delete(TblRouteChgInf tblRouteChgInf);
}
