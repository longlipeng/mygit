package com.huateng.dao.iface.rout;

import java.util.List;
import com.huateng.po.rout.TblTermChannelInf;
import com.huateng.po.rout.TblTermChannelInfTmp;

public interface TblTermChannelInfDAO {

	public TblTermChannelInf get(String id);
	
	public TblTermChannelInfTmp getTmp(String id);

	public TblTermChannelInf load(String id);

	public List<TblTermChannelInf> findAll ();

	public String save(TblTermChannelInf tblTermChannelInf);

	public void saveOrUpdate(TblTermChannelInf tblTermChannelInf);

	public void update(TblTermChannelInf tblTermChannelInf);
	
	public void updateTmp(TblTermChannelInfTmp tblTermChannelInfTmp);

	public void delete(String id);

	public void delete(TblTermChannelInf tblTermChannelInf);

	public void saveOrUpdate(TblTermChannelInfTmp tblTermChannelInfTmp);
}
