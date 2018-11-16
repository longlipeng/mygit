package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblMchtBeneficiaryInfTmp;
import com.huateng.po.mchnt.TblHisDiscAlgo1Tmp;
import com.huateng.po.mchnt.TblHisDiscAlgo2Tmp;

public interface T2070302BO {
	    public String add(TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp);
	    
	    public String addAlgo2(TblHisDiscAlgo2Tmp tblHisDiscAlgo2Tmp);
		
		public String update(TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp);
		
		public TblHisDiscAlgo1Tmp get(String id);
		public void delete(String id);
		/* (non-Javadoc)
		 * @see com.huateng.bo.T10401BO#update(java.util.List)
		 */
		public  String update(List<TblHisDiscAlgo1Tmp> tblHisDiscAlgo1TmpList) ;

		public TblHisDiscAlgo2Tmp getAlgo2(String discId);

		public void deleteAlgo2(String discId);
		
		public String updateAlgo2(TblHisDiscAlgo2Tmp tblHisDiscAlgo2Tmp);
		
		/**
		 * 根据id查找记录
		 * @param antiId
		 * @return
		 */
		public TblMchtBeneficiaryInfTmp getBeneficiary(String beneficiaryId);
		
		/**
		 * 受益人信息删除
		 * @param antiId
		 */
		public void deleteBeneficiary(String beneficiaryId);
		
		/**
		 * 添加受益人信息
		 * @return
		 */
		public String addBeneficiary(TblMchtBeneficiaryInfTmp amlt);
		
		/**
		 * 修改受益人信息
		 * @param mlt
		 * @return
		 */
		public String updateBeneficiary(TblMchtBeneficiaryInfTmp amlt);
}
