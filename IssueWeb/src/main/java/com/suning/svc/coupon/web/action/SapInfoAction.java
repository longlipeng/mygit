/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SapInfoAction.java
 * Author:   12073942
 * Date:     2013-10-30 下午9:47:10
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.web.action;

import org.apache.commons.lang3.StringUtils;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.svc.coupon.dto.SapInfoQueryDTO;
import com.huateng.framework.action.BaseAction;

/**
 * SAP记账表查询
 * 
 * @author LEO
 */
public class SapInfoAction extends BaseAction {

    /**
     */
    private static final long serialVersionUID = 1L;

    /**
     * 构造器
     * 
     * @param hasPrivilege
     * @param actionName
     */
    public SapInfoAction(boolean hasPrivilege, String actionName) {
        // 使用构造器注入，而非setter方法，防止struts参数绑定修改属性值
        this.hasPrivilege = hasPrivilege;
        this.actionName = actionName;
    }

    /**
     * 查询所有机构的特权
     */
    protected boolean hasPrivilege;

    /**
     * 查询Action的名称
     */
    protected String actionName;

    /**
     * 查询条件
     */
    private SapInfoQueryDTO sapInfoQueryDTO = new SapInfoQueryDTO();

    /**
     * 分页查询结果
     */
    private PageDataDTO pageDataDTO = new PageDataDTO();

    /**
     * 总行数
     */
    private Integer totalRows;

    /**
     * 查询条件验证
     */
    public void validateList() throws Exception {
        // 验证交易类型为2位数字（实际只限制不超过2位）
        String transType = sapInfoQueryDTO.getTransType();
        if (!StringUtils.isEmpty(transType) && !transType.matches("\\w{0,2}")) {
            addFieldError("sapInfoQueryDTO.transType", "交易类型为2位数字");
            sapInfoQueryDTO.setTransType(null);
        }
        // 验证支付方式为4位数字（实际只限制不超过4位）
        String payment = sapInfoQueryDTO.getPayment();
        if (!StringUtils.isEmpty(payment) && !payment.matches("\\w{0,4}")) {
            addFieldError("sapInfoQueryDTO.payment", "支付方式为4位数字");
            sapInfoQueryDTO.setPayment(null);
        }
        if (hasFieldErrors()) {
            list();
        }
    }

    /**
     * 列表查询
     */
    public String list() throws Exception {
        // 后台限制非特权用户只能查询本机构
        if (!hasPrivilege) {
            String entityId = super.getUser().getEntityId();
            sapInfoQueryDTO.setTransCompany(entityId);
        }
        ListPageInit(null, sapInfoQueryDTO);
        OperationResult rlt = sendService(ConstCode.SAP_INFO_QUERY, sapInfoQueryDTO);
        pageDataDTO = (PageDataDTO) rlt.getDetailvo();
        if (null != pageDataDTO && pageDataDTO.getData().size() > 0) {
            totalRows = pageDataDTO.getTotalRecord();
        } else {
            totalRows = 0;
        }
        return "list";
    }

    /**
     * @return the sapInfoQueryDTO
     */
    public SapInfoQueryDTO getSapInfoQueryDTO() {
        return sapInfoQueryDTO;
    }

    /**
     * @param sapInfoQueryDTO the sapInfoQueryDTO to set
     */
    public void setSapInfoQueryDTO(SapInfoQueryDTO sapInfoQueryDTO) {
        this.sapInfoQueryDTO = sapInfoQueryDTO;
    }

    /**
     * @return the pageDataDTO
     */
    public PageDataDTO getPageDataDTO() {
        return pageDataDTO;
    }

    /**
     * @param pageDataDTO the pageDataDTO to set
     */
    public void setPageDataDTO(PageDataDTO pageDataDTO) {
        this.pageDataDTO = pageDataDTO;
    }

    /**
     * @return the totalRows
     */
    public Integer getTotalRows() {
        return totalRows;
    }

    /**
     * @param totalRows the totalRows to set
     */
    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    /**
     * @return the hasPrivilege
     */
    public boolean isHasPrivilege() {
        return hasPrivilege;
    }

    /**
     * @return the actionName
     */
    public String getActionName() {
        return actionName;
    }

}
