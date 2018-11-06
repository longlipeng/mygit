package com.huateng.univer.system.dictinfo.biz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.dictinfo.dto.DictInfoDTO;
import com.allinfinance.univer.system.dictinfo.dto.DictInfoQueryDTO;
import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.DictTypeDAO;
import com.huateng.framework.ibatis.dao.EntityDictInfoDAO;
import com.huateng.framework.ibatis.model.DictInfo;
import com.huateng.framework.ibatis.model.DictInfoExample;
import com.huateng.framework.ibatis.model.DictType;
import com.huateng.framework.ibatis.model.EntityDictInfo;
import com.huateng.framework.ibatis.model.EntityDictInfoExample;
import com.huateng.framework.servlet.SystemInfoBO;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.system.dictinfo.biz.service.DictInfoService;
import com.huateng.univer.system.dictinfo.integration.dao.DictInfoServiceDAO;

public class DictInfoServiceImpl implements DictInfoService {

	Logger logger = Logger.getLogger(DictInfoServiceImpl.class);

	private DictInfoServiceDAO dictInfoDAO;
	private PageQueryDAO pageQueryDAO;
	private BaseDAO baseDictInfoDAO;
	private SystemInfoBO systemInfoBO;
	private DictTypeDAO dictTypeDAO;
	private CommonsDAO commonsDAO;

	private EntityDictInfoDAO entityDictInfoDAO;

	public Map<String, List<DictInfoDTO>> getDictInfo() {

		/**
		 * 设置查询条件,只查询状态为有效的（等于1）,且根据字典类型进行排序
		 */
		DictInfoExample example = new DictInfoExample();
		example.createCriteria().andDictStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL);
		example.setOrderByClause("DICT_TYPE_CODE,DICT_ID");
		List<DictInfo> list = dictInfoDAO.selectByExample(example);
		Map<String, List<DictInfoDTO>> map = new HashMap<String, List<DictInfoDTO>>();

		String dictTypeCode = null;

		List<DictInfoDTO> lstDictInfo = new ArrayList<DictInfoDTO>();
		/**
		 * 
		 */
		for (int i = 0; i < list.size(); i++) {
			DictInfo dictInfo = (DictInfo) list.get(i);
			DictInfoDTO dictInfoDTO = new DictInfoDTO();
			String dictTypeCode_tmp = dictInfo.getDictTypeCode();
			ReflectionUtil.copyProperties(dictInfo, dictInfoDTO);
			/**
			 * 如果是第一个
			 */
			if (i == 0) {
				dictTypeCode = list.get(0).getDictTypeCode();
				lstDictInfo.add(dictInfoDTO);
			} else {
				if (dictTypeCode_tmp.equals(dictTypeCode)) {
					lstDictInfo.add(dictInfoDTO);
				} else {
					map.put(dictTypeCode, lstDictInfo);
					dictTypeCode = dictTypeCode_tmp;
					lstDictInfo = new ArrayList<DictInfoDTO>();
					lstDictInfo.add(dictInfoDTO);
				}
			}
		}
		/**
		 * 将最后一次字典参数进去
		 */
		map.put(dictTypeCode, lstDictInfo);
		return map;
	}

	public Map<String, Map<String, List<EntityDictInfoDTO>>> getEntityDictInfo() {
		EntityDictInfoExample example = new EntityDictInfoExample();
		example.createCriteria().andDictStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL);
		example.setOrderByClause("ENTITY_ID,DICT_TYPE_CODE,DICT_ID");
		List<EntityDictInfo> list = entityDictInfoDAO.selectByExample(example);

		Map<String, Map<String, List<EntityDictInfoDTO>>> map = new HashMap<String, Map<String, List<EntityDictInfoDTO>>>();

		String dictTypeCode = null;

		String entityId = null;

		List<EntityDictInfoDTO> lstEntityDictInfo = new ArrayList<EntityDictInfoDTO>();

		Map<String, List<EntityDictInfoDTO>> mapDictType = new HashMap<String, List<EntityDictInfoDTO>>();

		for (int i = 0; i < list.size(); i++) {
			EntityDictInfo entityDictInfo = (EntityDictInfo) list.get(i);
			EntityDictInfoDTO entityDictInfoDTO = new EntityDictInfoDTO();

			String dictTypeCode_tmp = entityDictInfo.getDictTypeCode();

			String dictEntity_tmp = entityDictInfo.getEntityId();

			ReflectionUtil.copyProperties(entityDictInfo, entityDictInfoDTO);
			/**
			 * 如果是第一个
			 */
			if (i == 0) {
				dictTypeCode = list.get(0).getDictTypeCode();

				entityId = list.get(0).getEntityId();

				lstEntityDictInfo.add(entityDictInfoDTO);

				mapDictType.put(dictTypeCode, lstEntityDictInfo);

				map.put(entityId, mapDictType);

			} else {
				if (dictEntity_tmp.equals(entityId)) {
					if (dictTypeCode_tmp.equals(dictTypeCode)) {
						lstEntityDictInfo.add(entityDictInfoDTO);
					} else {
						mapDictType.put(dictTypeCode, lstEntityDictInfo);
						dictTypeCode = dictTypeCode_tmp;
						lstEntityDictInfo = new ArrayList<EntityDictInfoDTO>();
						lstEntityDictInfo.add(entityDictInfoDTO);
					}

				} else {
					mapDictType.put(dictTypeCode, lstEntityDictInfo);

					map.put(entityId, mapDictType);

					dictTypeCode = dictTypeCode_tmp;

					entityId = dictEntity_tmp;

					lstEntityDictInfo = new ArrayList<EntityDictInfoDTO>();

					lstEntityDictInfo.add(entityDictInfoDTO);

					mapDictType = new HashMap<String, List<EntityDictInfoDTO>>();
				}
			}
		}
		mapDictType.put(dictTypeCode, lstEntityDictInfo);

		map.put(entityId, mapDictType);

		int i = 0;
		for (String key : map.keySet()) {
			Map<String, List<EntityDictInfoDTO>> map_list = map.get(key);
			for (String key_list : map_list.keySet()) {
				List<EntityDictInfoDTO> entityDictInfoList = map_list
						.get(key_list);
				i += entityDictInfoList.size();
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("totalRows：" + i);
		}
		return map;

	}

	public PageDataDTO inquery(DictInfoQueryDTO dictInfoQueryDTO)
			throws BizServiceException {
		try {
			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"DICTINFO.selectEntityDictInfoByDTO", dictInfoQueryDTO);
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询字典表失败!");
		}
	}

	public List<DictInfo> getAllDictInfo() throws BizServiceException {
		try {
			DictInfoExample example = new DictInfoExample();

			return dictInfoDAO.selectByExample(example);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询字典表失败!");
		}
	}

	public void add(DictInfoDTO dictInfoDTO) throws BizServiceException {
		try {
			DictInfoExample example = new DictInfoExample();
			example.createCriteria().andDictCodeEqualTo(
					dictInfoDTO.getDictCode()).andDictTypeCodeEqualTo(
					dictInfoDTO.getDictTypeCode()).andDictStateEqualTo("1");
			List<DictInfo> dictInfos = dictInfoDAO.selectByExample(example);
			if (dictInfos != null && dictInfos.size() > 0) {
				throw new BizServiceException("添加失败!"
						+ dictInfoDTO.getDictCode() + "数据字典表编号已存在");
			}
			DictInfo dictInfo = new DictInfo();
			ReflectionUtil.copyProperties(dictInfoDTO, dictInfo);
			dictInfo.setDictId(commonsDAO
					.getNextValueOfSequence("TB_DICT_INFO"));
			dictInfo.setDictState("1");
			dictInfoDAO.insert(dictInfo);
			overload();
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("增加字典表数据失败!");
		}
	}

	public DictInfoDTO view(DictInfoDTO dictInfoDTO) throws BizServiceException {
		try {
			DictInfo dictInfo = dictInfoDAO.selectByPrimaryKey(dictInfoDTO
					.getDictId());
			DictType dictType = dictTypeDAO.selectByPrimaryKey(dictInfo
					.getDictTypeCode());
			dictInfoDTO = new DictInfoDTO();
			if (dictType != null) {
				dictInfoDTO.setDictTypeName(dictType.getDictTypeName());
			}
			ReflectionUtil.copyProperties(dictInfo, dictInfoDTO);
			return dictInfoDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("加载字典表失败!");
		}
	}

	public void edit(DictInfoDTO dictInfoDTO) throws BizServiceException {
		try {

			DictInfo dictInfo = new DictInfo();
			ReflectionUtil.copyProperties(dictInfoDTO, dictInfo);
			dictInfoDAO.updateByPrimaryKeySelective(dictInfo);
			overload();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新字典表数据失败!");
		}
	}

	public void del(DictInfoDTO dictInfoDTO) throws BizServiceException {
		try {
			String[] delStr = dictInfoDTO.getDelStr();
			for (String del : delStr) {
				// 查看是否有子字典类型
				DictInfo fatherDictInfo = dictInfoDAO.selectByPrimaryKey(del);
				if (fatherDictInfo != null) {
					DictInfoExample example = new DictInfoExample();
					example.createCriteria().andFatherDictCodeEqualTo(
							fatherDictInfo.getDictCode()).andDictStateEqualTo(
							"1");
					List<DictInfo> childDictInfos = dictInfoDAO
							.selectByExample(example);
					if (childDictInfos != null && childDictInfos.size() > 0) {
						throw new BizServiceException("数据字典编号："
								+ fatherDictInfo.getDictCode()
								+ " 下有子数据字典，不能删除！");
					}
				}

				DictInfo dictInfo = new DictInfo();
				dictInfo.setDictId(del);
				dictInfo.setDictState("0");
				dictInfoDAO.updateByPrimaryKeySelective(dictInfo);
			}
			overload();
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除字典表失败!");
		}
	}

	public void overload() throws BizServiceException {
		try {
			systemInfoBO.initDict();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("重载数据字典失败");
		}
	}

	public DictInfoServiceDAO getDictInfoDAO() {
		return dictInfoDAO;
	}

	public void setDictInfoDAO(DictInfoServiceDAO dictInfoDAO) {
		this.dictInfoDAO = dictInfoDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public BaseDAO getBaseDictInfoDAO() {
		return baseDictInfoDAO;
	}

	public void setBaseDictInfoDAO(BaseDAO baseDictInfoDAO) {
		this.baseDictInfoDAO = baseDictInfoDAO;
	}

	public SystemInfoBO getSystemInfoBO() {
		return systemInfoBO;
	}

	public void setSystemInfoBO(SystemInfoBO systemInfoBO) {
		this.systemInfoBO = systemInfoBO;
	}

	public DictTypeDAO getDictTypeDAO() {
		return dictTypeDAO;
	}

	public void setDictTypeDAO(DictTypeDAO dictTypeDAO) {
		this.dictTypeDAO = dictTypeDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public EntityDictInfoDAO getEntityDictInfoDAO() {
		return entityDictInfoDAO;
	}

	public void setEntityDictInfoDAO(EntityDictInfoDAO entityDictInfoDAO) {
		this.entityDictInfoDAO = entityDictInfoDAO;
	}

}