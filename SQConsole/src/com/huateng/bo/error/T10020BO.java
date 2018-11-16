package com.huateng.bo.error;



import com.huateng.po.error.TblMchtHang;
import com.huateng.po.error.TBLTermHang;
import com.huateng.po.error.TblTransHang;
import com.huateng.po.error.TblTransHangPK;
import com.huateng.po.error.TblMchtHangPK;
import com.huateng.po.error.TBLTermHangPK;
public interface T10020BO {
    public String add(TblMchtHang tblMchtHang);
    public String add(TBLTermHang tBLTermHang);
    public String add(TblTransHang tblTransHang);
    
    public String update(TblMchtHang tblMchtHang);
	public String update(TBLTermHang tBLTermHang);
	public String update(TblTransHang tblTransHang);
	
	public String saveOrUpdate(TblTransHang tblTransHang);
	public String saveOrUpdate(TBLTermHang tBLTermHang);
	public String saveOrUpdate(TblMchtHang tblMchtHang);
	
	public TblMchtHang getMcht(TblMchtHangPK id);
	public TBLTermHang getTerm(TBLTermHangPK id);
	public TblTransHang getTran(TblTransHangPK id);
	
}
