/**
 * 
 */
package com.huateng.dao.iface.error;

import java.util.List;

import com.huateng.po.error.ManualReturn;

/**
 * @author zoujunqing
 *
 */
public interface TblManualReturnDao {

	void saveOrUpdate(ManualReturn manu);

	ManualReturn get(String id);

	void saveupdate(ManualReturn manualReturn);

	void delete(String id);

}
