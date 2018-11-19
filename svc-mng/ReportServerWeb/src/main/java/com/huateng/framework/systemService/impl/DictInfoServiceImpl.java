package com.huateng.framework.systemService.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.db.ibatis.dao.DictInfoDAO;
import com.huateng.framework.db.ibatis.dao.EntityDictInfoDAO;
import com.huateng.framework.db.ibatis.model.DictInfo;
import com.huateng.framework.db.ibatis.model.DictInfoExample;
import com.huateng.framework.db.ibatis.model.EntityDictInfo;
import com.huateng.framework.db.ibatis.model.EntityDictInfoExample;
import com.huateng.framework.systemService.DictInfoService;

public class DictInfoServiceImpl implements DictInfoService {

	Logger logger = Logger.getLogger(DictInfoServiceImpl.class);

	private DictInfoDAO dictInfoDAO;

	private EntityDictInfoDAO entityDictInfoDAO;

	public Map<String, Map<String, Map<String, String>>> getDictInfo() {

		/**
		 * 设置查询条件,只查询状态为有效的（等于1）,且根据字典类型进行排序
		 */
		DictInfoExample example = new DictInfoExample();
		example.createCriteria().andDictStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL);
		example.setOrderByClause("DICT_TYPE_CODE,DICT_ID");
		List<DictInfo> list = dictInfoDAO.selectByExample(example);

		String dictTypeCode = null;

		// 字典表
		Map<String, Map<String, Map<String, String>>> map = new HashMap<String, Map<String, Map<String, String>>>();
		// 字典类型
		Map<String, Map<String, String>> dictTypeMap = new HashMap<String, Map<String, String>>();
		// key & value
		Map<String, String> dictMap = new HashMap<String, String>();

		for (DictInfo dictInfo : list) {
			// 得到字典类型
			String dictTypeCode_tmp = dictInfo.getDictTypeCode();

			if (dictTypeCode != null && !dictTypeCode.equals(dictTypeCode_tmp)) {
				dictTypeMap.put(dictTypeCode, dictMap);
				dictMap = new HashMap<String, String>();
			}
			dictMap.put(dictInfo.getDictCode(), dictInfo.getDictName());
			dictTypeCode = dictTypeCode_tmp;
		}
		dictTypeMap.put(dictTypeCode, dictMap);
		map.put("", dictTypeMap);

		// FIXME 总数测试.过后删除
		// int i = 0;
		// @SuppressWarnings("unchecked")
		// Set set = map.keySet();
		// logger.info("entity 个数 " + map.size());
		// for (Object object : set) {
		// Map<String, Map<String, String>> typemap = map.get(object);
		// logger.info("entity" + object + "type 个数 " +
		// typemap.size());
		// @SuppressWarnings("unchecked")
		// Set typeset = typemap.keySet();
		// for (Object object2 : typeset) {
		// Map<String, String> codeMap = typemap.get(object2);
		// i += codeMap.size();
		// }
		//
		// }
		// logger.info("sum " + i);
		// logger.info(list.size() == i);
		return map;
	}

	public Map<String, Map<String, Map<String, String>>> getAllDictInfo() {
		Map<String, Map<String, Map<String, String>>> map = getEntityDictInfo();
		map.putAll(getDictInfo());
		return map;
	}

	public Map<String, Map<String, Map<String, String>>> getEntityDictInfo() {
		EntityDictInfoExample example = new EntityDictInfoExample();
		example.createCriteria().andDictStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL);
		example.setOrderByClause("ENTITY_ID,DICT_TYPE_CODE,DICT_ID");
		List<EntityDictInfo> list = entityDictInfoDAO.selectByExample(example);

		String dictTypeCode = null;
		String entityId = null;

		// 字典表
		Map<String, Map<String, Map<String, String>>> map = new HashMap<String, Map<String, Map<String, String>>>();
		// 字典类型
		Map<String, Map<String, String>> dictTypeMap = new HashMap<String, Map<String, String>>();
		// key & value
		Map<String, String> dictMap = new HashMap<String, String>();
		// int m = 0;
		for (EntityDictInfo entityDictInfo : list) {
			// 得到字典类型
			String dictTypeCode_tmp = entityDictInfo.getDictTypeCode();
			// 得到实体ID
			String dictEntity_tmp = entityDictInfo.getEntityId();

			if (entityId != null && !dictEntity_tmp.equals(entityId)) {
				// logger.info(entityId+"机构TYPE:"+m);
				// m=0;
				map.put(entityId, dictTypeMap);
				dictTypeMap.put(dictTypeCode, dictMap);
				dictTypeMap = new HashMap<String, Map<String, String>>();
				dictMap = new HashMap<String, String>();
			} else {
				if (dictTypeCode != null
						&& !dictTypeCode_tmp.equals(dictTypeCode)) {
					// m += 1;
					dictTypeMap.put(dictTypeCode, dictMap);
					dictMap = new HashMap<String, String>();
				}
			}
			dictMap.put(entityDictInfo.getDictCode(), entityDictInfo
					.getDictName());

			// 下一次循环判断是否为同一类字典
			dictTypeCode = dictTypeCode_tmp;
			// 下一次循环判断是否为同一实体
			entityId = dictEntity_tmp;

		}
		dictTypeMap.put(dictTypeCode, dictMap);
		// 加载最后一个:
		map.put(entityId, dictTypeMap);

		// FIXME 总数测试.过后删除
		// int i = 0;
		// @SuppressWarnings("unchecked")
		// Set set = map.keySet();
		// logger.info("entity 个数 "+map.size());
		// for (Object object : set) {
		// Map<String, Map<String, String>> typemap = map.get(object);
		// logger.info("entity"+object+"type 个数 "+typemap.size());
		// @SuppressWarnings("unchecked")
		// Set typeset = typemap.keySet();
		// for (Object object2 : typeset) {
		// Map<String, String> codeMap = typemap.get(object2);
		// i += codeMap.size();
		// }
		//
		// }
		// logger.info("sum " + i);
		return map;
	}

	public DictInfoDAO getDictInfoDAO() {
		return dictInfoDAO;
	}

	public void setDictInfoDAO(DictInfoDAO dictInfoDAO) {
		this.dictInfoDAO = dictInfoDAO;
	}

	public EntityDictInfoDAO getEntityDictInfoDAO() {
		return entityDictInfoDAO;
	}

	public void setEntityDictInfoDAO(EntityDictInfoDAO entityDictInfoDAO) {
		this.entityDictInfoDAO = entityDictInfoDAO;
	}

}