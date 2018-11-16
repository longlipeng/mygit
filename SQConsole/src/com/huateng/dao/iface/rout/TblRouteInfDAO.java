package com.huateng.dao.iface.rout;

import java.util.List;
import com.huateng.po.rout.TblRouteInfo;

public interface TblRouteInfDAO {

	public TblRouteInfo getTblRouteInfo(String key);

	public TblRouteInfo loadTblRouteInfo(String key);

	public List<TblRouteInfo> findAllTblRouteInfo();

	public String save(TblRouteInfo tblRouteInfo);

	public void saveOrUpdate(TblRouteInfo tblRouteInfo);

	public void update(TblRouteInfo tblRouteInfo);

	public void deleteTblRouteInfo(String reserved);

	public void delete(TblRouteInfo tblRouteInfo);
}
