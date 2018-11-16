package com.huateng.dao.iface.rout;

import java.util.List;
import com.huateng.po.rout.TblTermChannelInfTmp;

public interface TblTermChannelInfTmpDAO {

	public TblTermChannelInfTmp get(String id);

	public TblTermChannelInfTmp load(String id);

	public List<TblTermChannelInfTmp> findAll ();

	public String save(TblTermChannelInfTmp TblTermChannelInfTmp);

	public void saveOrUpdate(TblTermChannelInfTmp TblTermChannelInfTmp);

	public void update(TblTermChannelInfTmp TblTermChannelInfTmp);

	public void delete(String id);

	public void delete(TblTermChannelInfTmp TblTermChannelInfTmp);

	public TblTermChannelInfTmp getTmp(String id);
}
