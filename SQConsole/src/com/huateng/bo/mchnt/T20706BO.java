package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblHisDiscAlgo1;
import com.huateng.po.mchnt.TblInfDiscAlgo;
import com.huateng.po.mchnt.TblInfDiscCd;
import com.huateng.po.mchnt.TblHisDiscAlgoPK;
import com.huateng.po.mchnt.TblHisDiscAlgo;
public interface T20706BO {
	    public String add(TblInfDiscCd tblInfDiscCd);
	    public String add(TblHisDiscAlgo tblHisDiscAlgo);
	    
		public String update(TblInfDiscCd tblInfDiscCd);
		public String update(TblHisDiscAlgo tblHisDiscAlgo);
		
		public  String update(List<TblHisDiscAlgo> tblHisDiscAlgoList) ;
		public  String update2(List<TblInfDiscCd> tblInfDiscCdList);
		
		public TblInfDiscCd get(String id);
		public TblHisDiscAlgo get(TblHisDiscAlgoPK id);
		
		
		public void delete(String id);
		public void delete(TblHisDiscAlgoPK id);
		/* (non-Javadoc)
		 * @see com.huateng.bo.T10401BO#update(java.util.List)
		 */
		public String saveOrUpdate(TblInfDiscCd tblInfDiscCd);
		public String saveOrUpdate(TblHisDiscAlgo tblHisDiscAlgo);

		public String saveOrUpdate(TblHisDiscAlgo1 algo1);
		public String createArith(List<TblInfDiscAlgo> list,
				TblInfDiscCd tblInfDiscCd, List<TblHisDiscAlgo> descList);
}
