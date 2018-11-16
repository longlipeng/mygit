
package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.exception.AppException;
import com.huateng.po.mchnt.TblMerRoleFuncMap;
import com.huateng.po.mchnt.TblMerRoleFuncMapId;




/**
 * Title:商户权限维护BO
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-10
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public interface T20301BO {
	
	/**添加（更新）角色功能信息*/
	public void addRoleFuncMap(List<TblMerRoleFuncMap> tblRoleFuncMapList) ;
	/**更新角色功能信息*/
	public void updateRoleFuncMap(List<TblMerRoleFuncMap> tblMerRoleFuncMapList,List<TblMerRoleFuncMapId> tblMerRoleFuncMapIdList) ;
	/**获得指定角色的所有功能*/
	public List<TblMerRoleFuncMap> getAllRoleMapId(Long mchntTp);
	
	public List<Object> getRoleMap(Long mchntTp);
}
