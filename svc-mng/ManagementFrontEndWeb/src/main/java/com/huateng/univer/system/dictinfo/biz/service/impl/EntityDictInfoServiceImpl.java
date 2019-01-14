package com.huateng.univer.system.dictinfo.biz.service.impl;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoDTO;
import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.DictTypeDAO;
import com.huateng.framework.ibatis.dao.EntityDictInfoDAO;
import com.huateng.framework.ibatis.model.DictType;
import com.huateng.framework.ibatis.model.EntityDictInfo;
import com.huateng.framework.ibatis.model.EntityDictInfoExample;
import com.huateng.framework.servlet.SystemInfoBO;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.webservice.service.SystemInfoService;
import com.huateng.univer.system.dictinfo.biz.service.EntityDictInfoService;

/**
 * 实体数据字典service
 * 
 * @author xxl
 * 
 */
public class EntityDictInfoServiceImpl implements EntityDictInfoService {

	Logger logger = Logger.getLogger(this.getClass());
	private EntityDictInfoDAO entityDictInfoDAO;
	private PageQueryDAO pageQueryDAO;
	private BaseDAO baseDAO;
	private CommonsDAO commonsDAO;
	private SystemInfoBO systemInfoBO;
	private DictTypeDAO dictTypeDAO;
	private SystemInfoService systemInfoService;

	public SystemInfoService getSystemInfoService() {
		return systemInfoService;
	}

	public void setSystemInfoService(SystemInfoService systemInfoService) {
		this.systemInfoService = systemInfoService;
	}

	public void edit(EntityDictInfoDTO dictInfoDTO) throws BizServiceException {
		try {
			EntityDictInfo entityDictInfo = new EntityDictInfo();
			ReflectionUtil.copyProperties(dictInfoDTO, entityDictInfo);

			entityDictInfoDAO.updateByPrimaryKeySelective(entityDictInfo);
			overload();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新数据字典失败！");
		}
	}

	//public Map<String, List<EntityDictInfoDTO>> getEntityDictInfo(
//			String entityId) throws BizServiceException {
//		try {
//			EntityDictInfoExample example = new EntityDictInfoExample();
//			example.createCriteria().andEntityIdEqualTo(entityId);
//			example.setOrderByClause("DICT_TYPE_CODE,DICT_CODE");
//
//			List<EntityDictInfo> entityDictInfos = entityDictInfoDAO
//					.selectByExample(example);
//			Map<String, List<EntityDictInfoDTO>> dictMap = new HashMap<String, List<EntityDictInfoDTO>>();
//			List<EntityDictInfoDTO> dictInfoDTOs = new ArrayList<EntityDictInfoDTO>();
//
//			String dictTypeCode = null;
//			for (EntityDictInfo dictInfo : entityDictInfos) {
//				EntityDictInfoDTO entityDictInfoDTO = new EntityDictInfoDTO();
//				ReflectionUtil.copyProperties(dictInfo, entityDictInfoDTO);
//
//				String dictType = dictInfo.getDictTypeCode();
//				if (dictTypeCode.equals(dictType)) {
//					dictInfoDTOs.add(entityDictInfoDTO);
//				} else {
//					if (null != dictTypeCode) {
//						dictMap.put(dictTypeCode, dictInfoDTOs);
//					}
//					dictInfoDTOs = new ArrayList<EntityDictInfoDTO>();
//					dictTypeCode = dictType;
//					dictInfoDTOs.add(entityDictInfoDTO);
//				}
//			}
//			dictMap.put(dictTypeCode, dictInfoDTOs);
//			return dictMap;
//		} catch (Exception e) {
//			this.logger.error(e.getMessage());
//			throw new BizServiceException("加载实体数据字典失败！");
//		}
//	}

	public PageDataDTO inquery(EntityDictInfoQueryDTO entityDictInfoQueryDTO)
			throws BizServiceException {
		try {
			return pageQueryDAO.query("DICTINFO.selectEntityDictInfoByDTO",
					entityDictInfoQueryDTO);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询数据字典失败！");
		}
	}

	public void insert(EntityDictInfoDTO dictInfoDTO)
			throws BizServiceException {
		try {
			EntityDictInfoExample example = new EntityDictInfoExample();
			example.createCriteria().andEntityIdEqualTo(
					dictInfoDTO.getDefaultEntityId()).andDictCodeEqualTo(
					dictInfoDTO.getDictCode()).andDictTypeCodeEqualTo(
					dictInfoDTO.getDictTypeCode()).andDictStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL);
			if (entityDictInfoDAO.countByExample(example) > 0) {
				throw new BizServiceException("添加失败!"
						+ dictInfoDTO.getDictCode() + "数据字典表编号已存在");
			}
			EntityDictInfo dictInfo = new EntityDictInfo();
			ReflectionUtil.copyProperties(dictInfoDTO, dictInfo);
			// dictInfo.setDictId(commonsDAO.getNextValueOfSequence("TB_ENTITY_DICT_INFO"));
			dictInfo.setDictId(commonsDAO
					.getNextValueOfSequenceBySequence("SEQ_ENTITY_DICT_ID"));
			dictInfo.setDictState(DataBaseConstant.DATA_STATE_NORMAL);
			dictInfo.setEntityId(dictInfoDTO.getDefaultEntityId());
			dictInfo.setFatherEntityId("0");
			entityDictInfoDAO.insert(dictInfo);
			overload();
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("增加字典数据失败!");
		}
	}

	/**
	 * 为每个实体添加一套初始的系统参数
	 */
	public void insertEntityDictInfo(String entityId)
			throws BizServiceException {
		baseDAO.insert("DICTINFO.insertEntityDictInfoFromDictInfo", entityId);
		overload();
	}

	public void overload() throws BizServiceException {
		try {
			// systemInfoBO.initEntityDict();
			// 添加 By Yifeng.Shi 2011-05-05
			// systemInfoService.initEntityDictInfo();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("重载数据字典失败");
		}
	}

	public EntityDictInfoDTO view(EntityDictInfoDTO dictInfoDTO)
			throws BizServiceException {
		try {
			EntityDictInfo entityDictInfo = entityDictInfoDAO
					.selectByPrimaryKey(dictInfoDTO.getDictId());
			ReflectionUtil.copyProperties(entityDictInfo, dictInfoDTO);
			DictType dictType = dictTypeDAO.selectByPrimaryKey(entityDictInfo
					.getDictTypeCode());
			if (dictType != null) {
				dictInfoDTO.setDictTypeName(dictType.getDictTypeName());
			}
			return dictInfoDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看数据字典失败！");
		}
	}

	public void del(EntityDictInfoDTO dictInfoDTO) throws BizServiceException {
		try {
			String[] delStr = dictInfoDTO.getDelStr();
			for (String del : delStr) {
				// 查看是否有子字典类型
				EntityDictInfo fatherDictInfo = entityDictInfoDAO
						.selectByPrimaryKey(del);
				if (fatherDictInfo != null) {
					EntityDictInfoExample example = new EntityDictInfoExample();

					example.createCriteria().andFatherDictCodeEqualTo(
							fatherDictInfo.getDictCode())
							.andDictTypeCodeEqualTo(
									fatherDictInfo.getDictTypeCode())
							.andDictStateEqualTo(
									DataBaseConstant.DATA_STATE_NORMAL)
							.andEntityIdEqualTo(
									dictInfoDTO.getDefaultEntityId());

					if (entityDictInfoDAO.countByExample(example) > 0) {
						throw new BizServiceException("数据字典编号："
								+ fatherDictInfo.getDictCode()
								+ " 下有子数据字典，不能删除！");
					}
				}

				EntityDictInfo dictInfo = new EntityDictInfo();
				dictInfo.setDictId(del);
				dictInfo.setDictState(DataBaseConstant.DATA_STATE_DELETE);
				entityDictInfoDAO.updateByPrimaryKeySelective(dictInfo);
			}
			// overload();
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除字典表失败!");
		}
	}

	public EntityDictInfoDAO getEntityDictInfoDAO() {
		return entityDictInfoDAO;
	}

	public void setEntityDictInfoDAO(EntityDictInfoDAO entityDictInfoDAO) {
		this.entityDictInfoDAO = entityDictInfoDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
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

}
