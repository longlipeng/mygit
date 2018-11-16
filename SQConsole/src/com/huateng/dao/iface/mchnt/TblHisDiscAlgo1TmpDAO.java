package com.huateng.dao.iface.mchnt;

import com.huateng.po.mchnt.TblMchtBeneficiaryInfTmp;
import com.huateng.po.mchnt.TblHisDiscAlgo1Tmp;
import com.huateng.po.mchnt.TblHisDiscAlgo2Tmp;


public interface TblHisDiscAlgo1TmpDAO {
	public Class<TblHisDiscAlgo1Tmp> getReferenceClass () ;
	public TblHisDiscAlgo1Tmp cast (Object object);
	public TblHisDiscAlgo1Tmp load(String key);
	public TblHisDiscAlgo1Tmp get(String key);
	public String save(TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp);
	
	public String saveAlgo2(TblHisDiscAlgo2Tmp tblHisDiscAlgo2Tmp);
	
	public void saveOrUpdate(TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp);
	public void update(TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp);
	public void delete(TblHisDiscAlgo1Tmp tblHisDiscAlgo1Tmp);
	public void delete(String id);
	public TblHisDiscAlgo2Tmp getAlgo2(String id);
	public void updateAlgo2(TblHisDiscAlgo2Tmp tblHisDiscAlgo2Tmp);
	public void deleteAlgo2(String id);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public TblMchtBeneficiaryInfTmp getBeneficiary(String id);
	
	/**
	 * 受益人信息删除
	 * @param antiId
	 */
	public void deleteBeneficiary(String beneficiaryId);
	
	/**
	 * 添加受益人信息
	 * @return
	 */
	public void addBeneficiary(TblMchtBeneficiaryInfTmp amlt);
	
	/**
	 * 修改受益人信息
	 * @param mlt
	 * @return
	 */
	public void updateBeneficiary(TblMchtBeneficiaryInfTmp amlt);
}
