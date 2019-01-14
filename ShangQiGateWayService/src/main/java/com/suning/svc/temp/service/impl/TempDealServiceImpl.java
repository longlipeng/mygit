/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TempDealServiceImpl.java
 * Author:   13040443
 * Date:     2013-11-20 下午03:36:31
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.temp.service.impl;

import com.huateng.framework.ibatis.dao.EntitySystemParameterDAO;
import com.huateng.framework.ibatis.model.EntitySystemParameterExample;
import com.suning.svc.ibatis.dao.InvoiceRequirementDAO;
import com.suning.svc.ibatis.model.InvoiceRequirementExample;
import com.suning.svc.temp.service.TempDealService;

/**
 * 临时处理数据
 * 
 * @author yanbin
 */
public class TempDealServiceImpl implements TempDealService {

    private EntitySystemParameterDAO entitySystemParameterDAO;

    private InvoiceRequirementDAO invoiceRequirementDAO;

    /**
     * 删除参数实体数据
     */
    @Override
    public void deleteEntitySystemParameter() {
        EntitySystemParameterExample example = new EntitySystemParameterExample();
        example.createCriteria().andFatherEntityIdNotEqualTo("00000000");
        entitySystemParameterDAO.deleteByExample(example);
    }

    @Override
    public void deleteInvoicRequirement() {
        try {
            InvoiceRequirementExample example = new InvoiceRequirementExample();
            example.createCriteria().andYetAmountEqualTo(0l).andWaitAmountEqualTo(0l);
            invoiceRequirementDAO.deleteByExample(example);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @return the entitySystemParameterDAO
     */
    public EntitySystemParameterDAO getEntitySystemParameterDAO() {
        return entitySystemParameterDAO;
    }

    /**
     * @param entitySystemParameterDAO the entitySystemParameterDAO to set
     */
    public void setEntitySystemParameterDAO(EntitySystemParameterDAO entitySystemParameterDAO) {
        this.entitySystemParameterDAO = entitySystemParameterDAO;
    }

    /**
     * @return the invoiceRequirementDAO
     */
    public InvoiceRequirementDAO getInvoiceRequirementDAO() {
        return invoiceRequirementDAO;
    }

    /**
     * @param invoiceRequirementDAO the invoiceRequirementDAO to set
     */
    public void setInvoiceRequirementDAO(InvoiceRequirementDAO invoiceRequirementDAO) {
        this.invoiceRequirementDAO = invoiceRequirementDAO;
    }

}
