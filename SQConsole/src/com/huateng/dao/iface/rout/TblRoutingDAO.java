package com.huateng.dao.iface.rout;

import java.util.List;
import com.huateng.po.rout.TblRouteInfoTemp;

public interface TblRoutingDAO {
	
	public TblRouteInfoTemp get(String key);

	public TblRouteInfoTemp load(String key);

	public List<TblRouteInfoTemp> findAll ();

	public String save(TblRouteInfoTemp tblRouteInfoTemp);

	public void saveOrUpdate(TblRouteInfoTemp tblRouteInfoTemp);

	public void update(TblRouteInfoTemp tblRouteInfoTemp);

	public void delete(String reserved);

	public void delete(TblRouteInfoTemp tblRouteInfoTemp);
	
}
