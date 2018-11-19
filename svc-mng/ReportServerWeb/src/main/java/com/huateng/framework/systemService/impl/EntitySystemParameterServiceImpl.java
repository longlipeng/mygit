package com.huateng.framework.systemService.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.db.ibatis.dao.EntitySystemParameterDAO;
import com.huateng.framework.db.ibatis.dao.SystemParameterDAO;
import com.huateng.framework.db.ibatis.model.EntitySystemParameter;
import com.huateng.framework.db.ibatis.model.EntitySystemParameterExample;
import com.huateng.framework.db.ibatis.model.SystemParameter;
import com.huateng.framework.db.ibatis.model.SystemParameterExample;
import com.huateng.framework.systemService.EntitySystemParameterService;

/**
 * 实体系统参数
 * 
 * @author xxl
 * 
 */
public class EntitySystemParameterServiceImpl implements
		EntitySystemParameterService {

	//Logger logger = Logger.getLogger(this.getClass());
	private EntitySystemParameterDAO entitySystemParameterDAO;

	private SystemParameterDAO systemParameterDAO;

	public Map<String, Map<String, String>> getSystemParameter() {

		SystemParameterExample systemParameterExample = new SystemParameterExample();

		List<SystemParameter> systemParameters = systemParameterDAO
				.selectByExample(systemParameterExample);

		// 实体系统参数
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		Map<String, String> sysParameterMap = new HashMap<String, String>();
		for (SystemParameter systemParameter : systemParameters) {
			sysParameterMap.put(systemParameter.getParameterCode(),
					systemParameter.getParameterValue());
		}

		map.put("", sysParameterMap);
		return map;
	}

	public Map<String, Map<String, String>> getAllEntitySystemParameter() {
		Map<String, Map<String, String>> map = getEntitySystemParameter();
		map.putAll(getSystemParameter());

		return map;
	}

	public Map<String, Map<String, String>> getEntitySystemParameter() {
		/**
		 * 设置查询条件,只查询状态为有效的（等于1）,且根据字典类型进行排序
		 */
		EntitySystemParameterExample example = new EntitySystemParameterExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL);
		example.setOrderByClause("ENTITY_ID");
		List<EntitySystemParameter> list = entitySystemParameterDAO
				.selectByExample(example);
		// 实体系统参数
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

		String entityId = null;

		Map<String, String> sysParameterMap = new HashMap<String, String>();

		for (EntitySystemParameter entitySystemParameter : list) {
			String entityId_tmp = entitySystemParameter.getEntityId();
			if (entityId != null && !entityId.equals(entityId_tmp)) {
				map.put(entityId, sysParameterMap);
				sysParameterMap = new HashMap<String, String>();
			}
			sysParameterMap.put(entitySystemParameter.getParameterCode(),
					entitySystemParameter.getParameterValue());

			entityId = entityId_tmp;
		}

		map.put(entityId, sysParameterMap);

		return map;
	}

	public SystemParameterDAO getSystemParameterDAO() {
		return systemParameterDAO;
	}

	public void setSystemParameterDAO(SystemParameterDAO systemParameterDAO) {
		this.systemParameterDAO = systemParameterDAO;
	}

	public EntitySystemParameterDAO getEntitySystemParameterDAO() {
		return entitySystemParameterDAO;
	}

	public void setEntitySystemParameterDAO(
			EntitySystemParameterDAO entitySystemParameterDAO) {
		this.entitySystemParameterDAO = entitySystemParameterDAO;
	}

}
