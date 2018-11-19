package com.huateng.univer.system.sysparam.biz.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.pos.dto.PosInfoDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.EntitySystemParameterDAO;
import com.huateng.framework.ibatis.model.EntitySystemParameter;
import com.huateng.framework.ibatis.model.EntitySystemParameterExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.system.sysparam.biz.service.EntitySystemParameterService;
import com.suning.svc.coupon.constants.OrgnizationConstants;

/**
 * 实体系统参数
 * 
 * @author xxl
 * 
 */
public class EntitySystemParameterServiceImpl implements EntitySystemParameterService {

    Logger logger = Logger.getLogger(this.getClass());
    private EntitySystemParameterDAO entitySystemParameterDAO;
    private PageQueryDAO pageQueryDAO;
    private BaseDAO baseDAO;

    public PageDataDTO inqueryEntitySystemParamter(EntitySystemParameterQueryDTO entitySystemParameterQueryDTO)
            throws BizServiceException {
        try {
            entitySystemParameterQueryDTO.setEntityId(entitySystemParameterQueryDTO.getDefaultEntityId());
            entitySystemParameterQueryDTO.setParameterRole("0");
            PageDataDTO pdd = pageQueryDAO.query("SYSTEMPARAMETER.selectEntitySystemParameter",
                    entitySystemParameterQueryDTO);
            return pdd;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询系统参数失败!");
        }
    }

    /**
     * 添加00000000发行机构时 从systemParameter表里拷贝密钥信息
     */
    public void insertEntitySysParam(String entityId, String loginUserId) throws BizServiceException {
        EntitySystemParameter parameter = new EntitySystemParameter();
        parameter.setEntityId(entityId);
        parameter.setCreateUser(loginUserId);
        parameter.setCreateTime(DateUtil.getCurrentTime());
        parameter.setModifyTime(DateUtil.getCurrentTime());
        parameter.setModifyUser(loginUserId);
        parameter.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

        baseDAO.insert("SYSTEMPARAMETER.insertEntitySysParamFromSysParam", parameter);
    }

    /**
     * 添加除00000000发行机构时 拷贝上级发行机构的密钥信息
     */
    public void insertEntitySystemParameter(String entityId, String fatherEntityId, String userId)
            throws BizServiceException {
        EntitySystemParameter parameter = new EntitySystemParameter();
        EntitySystemParameterExample entitySystemParameterExample = new EntitySystemParameterExample();
        entitySystemParameterExample.createCriteria().andEntityIdEqualTo(entityId);
        if (entitySystemParameterDAO.countByExample(entitySystemParameterExample) <= 0) {
            parameter.setEntityId(entityId);
            parameter.setFatherEntityId(fatherEntityId);
            parameter.setCreateUser(userId);
            parameter.setCreateTime(DateUtil.getCurrentTime());
            parameter.setModifyTime(DateUtil.getCurrentTime());
            parameter.setModifyUser(userId);
            parameter.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

            baseDAO.insert("SYSTEMPARAMETER.insertEntitySystemParameter", parameter);

        }

    }

    public void updateEntitySystemParamter(EntitySystemParameterDTO entitySystemParameterDTO)
            throws BizServiceException {
        try {
            EntitySystemParameter entitySystemParameter = new EntitySystemParameter();
            ReflectionUtil.copyProperties(entitySystemParameterDTO, entitySystemParameter);
            if (entitySystemParameter.getParameterCode().equals(DataBaseConstant.ORDER_CARD_MAXIMUM_NAME)) {
                if (Integer.parseInt(entitySystemParameter.getParameterValue()) > Integer
                        .parseInt(entitySystemParameterDTO.getModifyUser())) {
                    throw new BizServiceException("订单最大卡数量不能大于系统既定的值：" + entitySystemParameterDTO.getModifyUser());
                }
            }
            entitySystemParameter.setModifyUser(entitySystemParameterDTO.getLoginUserId());
            entitySystemParameter.setModifyTime(DateUtil.getCurrentTime());

            EntitySystemParameterExample example = new EntitySystemParameterExample();
            example.createCriteria().andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
                    .andEntityIdEqualTo(entitySystemParameterDTO.getEntityId())
                    .andParameterCodeEqualTo(entitySystemParameterDTO.getParameterCode());

            entitySystemParameterDAO.updateByExampleSelective(entitySystemParameter, example);

        } catch (BizServiceException b) {
            logger.error(b);
            throw b;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("更新系统参数失败");
        }

    }

    public EntitySystemParameterDTO viewEntitySystemParamter(EntitySystemParameterDTO entitySystemParameterDTO)
            throws BizServiceException {
        try {
            EntitySystemParameterExample example = new EntitySystemParameterExample();
            example.createCriteria().andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
                    .andEntityIdEqualTo(entitySystemParameterDTO.getEntityId())
                    .andParameterCodeEqualTo(entitySystemParameterDTO.getParameterCode());

            EntitySystemParameter entitySystemParameter = entitySystemParameterDAO.selectByExample(example).get(0);
            ReflectionUtil.copyProperties(entitySystemParameter, entitySystemParameterDTO);
            return entitySystemParameterDTO;

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询系统参数信息失败!");
        }
    }

    public EntitySystemParameterDAO getEntitySystemParameterDAO() {
        return entitySystemParameterDAO;
    }

    public void setEntitySystemParameterDAO(EntitySystemParameterDAO entitySystemParameterDAO) {
        this.entitySystemParameterDAO = entitySystemParameterDAO;
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

    public Map<String, List<EntitySystemParameterDTO>> getEntitySystemParameter() {
        /**
         * 设置查询条件,只查询状态为有效的（等于1）,且根据字典类型进行排序
         */
        EntitySystemParameterExample example = new EntitySystemParameterExample();
        example.createCriteria().andEntityIdEqualTo(OrgnizationConstants.SQ_ENTITY_ID);
        example.or(example.createCriteria().andEntityIdEqualTo(OrgnizationConstants.WXGCK_ENTITY_ID));
        example.setOrderByClause("ENTITY_ID");
        List<EntitySystemParameter> list = entitySystemParameterDAO.selectByExample(example);
        Map<String, List<EntitySystemParameterDTO>> map = new HashMap<String, List<EntitySystemParameterDTO>>();

        String entityId = null;

        List<EntitySystemParameterDTO> lstEntIdSysParameterList = new ArrayList<EntitySystemParameterDTO>();
        /**
		 * 
		 */
        for (int i = 0; i < list.size(); i++) {
            EntitySystemParameter entitySystemParameter = (EntitySystemParameter) list.get(i);
            EntitySystemParameterDTO entitySystemParameterDTO = new EntitySystemParameterDTO();
            String entityId_tmp = entitySystemParameter.getEntityId();
            ReflectionUtil.copyProperties(entitySystemParameter, entitySystemParameterDTO);
            /**
             * 如果是第一个
             */
            if (i == 0) {
                entityId = entityId_tmp;
                lstEntIdSysParameterList.add(entitySystemParameterDTO);
            } else {
                if (entityId_tmp.equals(entityId)) {
                    lstEntIdSysParameterList.add(entitySystemParameterDTO);
                } else {
                    map.put(entityId, lstEntIdSysParameterList);
                    entityId = entityId_tmp;
                    lstEntIdSysParameterList = new ArrayList<EntitySystemParameterDTO>();
                    lstEntIdSysParameterList.add(entitySystemParameterDTO);
                }
            }
        }
        /**
         * 将最后一次字典参数进去
         */
        map.put(entityId, lstEntIdSysParameterList);
        logger.debug(map.size());
        return map;
    }

    /**
     * 通过posid 找到上级收单机构id
     * 
     */
    public String getFatherEntityId(PosInfoDTO posInfoDTO) throws BizServiceException {
        String id = entitySystemParameterDAO.selectByposInfoid(posInfoDTO);
        return id;
    }

}
