/**
 * 
 */
package com.huateng.dao.iface.error;

import java.util.List;

import com.huateng.po.error.TblChangeAccInf;
import com.huateng.po.error.TblChangeAccInfRefuse;
import com.huateng.po.error.TblChangeAccInfTmp;
import com.huateng.po.error.TblChangeAccInfTmpId;

/**
 * @author zoujunqing
 *
 */
public interface TblChangeAccInfTmpDao {

	void saveOrUpdate(TblChangeAccInfTmp tblChangeAccInfTmp);

	TblChangeAccInfTmp get(String id);

	void saveupdate(TblChangeAccInfTmp tblChangeAccInfTmp);

	void delete(String id);

	void deletelist(TblChangeAccInfTmpId tblChangeAccInfTmpId);

	TblChangeAccInfTmp get(TblChangeAccInfTmpId tblChangeAccInfTmpId);

	void saveOrUpdate(TblChangeAccInf tblChangeAccInf);
	
	void saveOrUpdate(TblChangeAccInfRefuse tblChangeAccInfRefuse);
	

}
