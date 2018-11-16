package com.huateng.dao.iface.error;

import com.huateng.po.error.TBLTermHangPK;
import com.huateng.po.error.TblMchtHang;
import com.huateng.po.error.TBLTermHang;
import com.huateng.po.error.TblMchtHangPK;
import com.huateng.po.error.TblTransHang;
import com.huateng.po.error.TblTransHangPK;

public interface TblMchtHangDAO {
	public Class<TblMchtHang> getReferenceClass () ;
	public Class<TBLTermHang> getReferenceClass2() ;
	public Class<TblTransHang> getReferenceClass3() ;
	
	public TblMchtHang cast (Object object);
	public TBLTermHang cast2 (Object object);
	public TblTransHang cast3 (Object object);
	
	public TblMchtHang load(String key);
	public TBLTermHang load2(String key);
	public TblTransHang load3(String key);
	
	public TblMchtHang get(TblMchtHangPK id);
	public TBLTermHang get2(TBLTermHangPK id);
	public TblTransHang get3(TblTransHangPK id);
	
	public void save(TblMchtHang tblMchtHang);
	public void save(TBLTermHang tBLTermHang);
	public void save(TblTransHang tblTransHang);
	
	public void saveOrUpdate(TblMchtHang tblMchtHang);
	public void saveOrUpdate(TBLTermHang tBLTermHang);
	public void saveOrUpdate(TblTransHang tblTransHang);
	
	public void update(TblMchtHang tblMchtHang);
	public void update(TBLTermHang tBLTermHang);
	public void update(TblTransHang tblTransHang);
	
	public void delete(TblMchtHang tblMchtHang);
	public void delete(TBLTermHang tBLTermHang);
	public void delete(TblTransHang tblTransHang);
	
	public void delete(String id);
}
