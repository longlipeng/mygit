package com.huateng.dao.iface.rout;

import java.util.List;
import com.huateng.po.rout.TblTermChannelInf2;

public interface TblTermChannelInf2DAO {

	public TblTermChannelInf2 get(String id);

	public TblTermChannelInf2 load(String id);

	public List<TblTermChannelInf2> findAll ();

	public String save(TblTermChannelInf2 tblTermChannelInf2);

	public void saveOrUpdate(TblTermChannelInf2 tblTermChannelInf2);

	public void update(TblTermChannelInf2 tblTermChannelInf2);

	public void delete(String id);

	public void delete(TblTermChannelInf2 tblTermChannelInf2);
}
