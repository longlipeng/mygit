package com.huateng.dao.iface.risk;

public interface TblCnpcMchtInfDAO {
	public com.huateng.po.TblCnpcCardInf get(com.huateng.po.TblCnpcCardInfPK key);

	public com.huateng.po.TblCnpcCardInf load(java.lang.String key);

	public void save(com.huateng.po.TblCnpcCardInf TblCnpcCardInf);

	public void saveOrUpdate(com.huateng.po.TblCnpcCardInf TblCnpcCardInf);
	
	public void update(com.huateng.po.TblCnpcCardInf TblCnpcCardInf);


	public void delete(com.huateng.po.TblCnpcCardInfPK TblCnpcCardInf);


}