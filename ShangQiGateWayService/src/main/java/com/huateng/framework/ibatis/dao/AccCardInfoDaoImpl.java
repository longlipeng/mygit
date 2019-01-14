/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: AccCardInfoDaoImpl.java
 * Author:   xuwei
 * Date:     2013-11-8 上午10:18:40
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.framework.ibatis.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.allinfinance.univer.cardmanagement.dto.BatchCardActionDTO;
import com.allinfinance.univer.cardmanagement.dto.BatchCardInfoDTO;

/**
  * 发行机构卡批量作废dao
 *
 * @author xuwei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class AccCardInfoDaoImpl extends SqlMapClientDaoSupport implements AccCardInfoDao {

    /* (non-Javadoc)
     * @see com.huateng.framework.ibatis.dao.AccCardInfoDao#checkCardStatus(com.huateng.univer.cardmanagement.dto.BatchCardActionDTO)
     */
    @Override
    public Long checkCardStatus(BatchCardActionDTO DTO) {
        return (Long) getSqlMapClientTemplate().queryForObject("TB_ACC_CARD_INFO.selectCardNOStatus", DTO) ;
    }

    /* (non-Javadoc)
     * @see com.huateng.framework.ibatis.dao.AccCardInfoDao#updateStatus(com.huateng.univer.cardmanagement.dto.BatchCardActionDTO)
     */
    @Override
    public int updateStatus(BatchCardActionDTO DTO) {
        return getSqlMapClientTemplate().update("TB_ACC_CARD_INFO.updateStatus", DTO);
    }

    /* (non-Javadoc)
     * @see com.huateng.framework.ibatis.dao.AccCardInfoDao#getAccCardInfo()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BatchCardInfoDTO> getAccCardInfo(BatchCardActionDTO DTO) {
        List<BatchCardInfoDTO> list=(List<BatchCardInfoDTO>) getSqlMapClientTemplate().queryForList("TB_ACC_CARD_INFO.selectBatchCardInfo", DTO);
        return list;
    }
    @SuppressWarnings("unchecked")
    public List<BatchCardInfoDTO> getAccCardInfoBySell(BatchCardActionDTO DTO) {
        List<BatchCardInfoDTO> list=(List<BatchCardInfoDTO>) getSqlMapClientTemplate().queryForList("TB_ACC_CARD_INFO.selectBatchCardInfoBySell", DTO);
        return list;
    }
    /* (non-Javadoc)
     * @see com.huateng.framework.ibatis.dao.AccCardInfoDao#checkCardProductId(com.huateng.univer.cardmanagement.dto.BatchCardActionDTO)
     */
    @Override
    public int checkCardProductId(BatchCardActionDTO DTO) {
        return (Integer) getSqlMapClientTemplate().queryForObject("TB_ACC_CARD_INFO.selectCardNOProductId", DTO) ;
    }

    /* (non-Javadoc)
     * @see com.huateng.framework.ibatis.dao.AccCardInfoDao#countByExample(com.huateng.framework.ibatis.dao.AccCardInfoExample)
     */
    @Override
    public int countByExample(AccCardInfoExample example) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
                "TB_ACC_CARD_INFO.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /* (non-Javadoc)
     * @see com.huateng.framework.ibatis.dao.AccCardInfoDao#selectByExampleForPageList(com.huateng.framework.ibatis.dao.AccCardInfoExample, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BatchCardInfoDTO> selectByExampleForPageList(AccCardInfoExample example, int pageIndex, int pageSize) {
        int skipResults = pageIndex * pageSize;
        List list = getSqlMapClientTemplate().queryForList("TB_ACC_CARD_INFO.abatorgenerated_selectByExample", example,
                skipResults, pageSize);
        return list;
    }

    /* (non-Javadoc)
     * @see com.huateng.framework.ibatis.dao.AccCardInfoDao#updateStatusBySeller(com.huateng.univer.cardmanagement.dto.BatchCardActionDTO)
     */
    @Override
    public int updateStatusBySeller(BatchCardActionDTO DTO) {
        return getSqlMapClientTemplate().update("TB_ACC_CARD_INFO.updateStatusBySeller", DTO);
    }

}
