package com.huateng.univer.system.sysparam;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterQueryDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.SystemInfo;

/**
 * 系统参数action
 */
public class SystemParameterAction extends BaseAction {
    private Logger logger = Logger.getLogger(SystemParameterAction.class);
    private static final long serialVersionUID = 1727479262903027661L;
    private PageDataDTO pageDataDTO = new PageDataDTO();
    private EntitySystemParameterDTO entitySystemParameterDTO = new EntitySystemParameterDTO();
    private EntitySystemParameterQueryDTO entitySystemParameterQueryDTO = new EntitySystemParameterQueryDTO();
    private Integer totalRows = 0;
    private IssuerDTO issuerDTO = new IssuerDTO();

    /**
     * 查询系统参数信息
     */
    public String inquery() throws Exception {

        ListPageInit(null, entitySystemParameterQueryDTO);
        if (entitySystemParameterQueryDTO.isQueryAll()) {
            entitySystemParameterQueryDTO.setQueryAll(false);
            entitySystemParameterQueryDTO.setRowsDisplayed(Integer.parseInt(SystemInfo
                    .getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
        }
        pageDataDTO = (PageDataDTO) sendService(ConstCode.ENTITY_SYSTEMPARAMETER_SERVICE_INQUERY,
                entitySystemParameterQueryDTO).getDetailvo();
        if (pageDataDTO != null) {
            totalRows = pageDataDTO.getTotalRecord();
        }
        return "list";
    }

    public String load() throws Exception {
        logger.debug(entitySystemParameterDTO.getEntityId());
        String parameterCode = this.getRequest().getParameter("parameterCode");
        entitySystemParameterDTO.setEntityId(getUser().getEntityId());
        if (null != parameterCode && !"".equals(parameterCode)) {
            entitySystemParameterDTO.setParameterCode(parameterCode);
        }
        entitySystemParameterDTO = (EntitySystemParameterDTO) sendService(
                ConstCode.ENTITY_SYSTEMPARAMETER_SERVICE_VIEW, entitySystemParameterDTO).getDetailvo();

        if (hasActionErrors()) {
            return this.inquery();
        }

        return "view";
    }

    public String edit() throws Exception {
        this.load();
        return "edit";
    }

    public String update() throws Exception {
        entitySystemParameterDTO.setModifyUser(SystemInfo.getParameterValue(DataBaseConstant.ORDER_CARD_MAXIMUM_NAME));
        sendService(ConstCode.ENTITY_SYSTEMPARAMETER_SERVICE_UPDATE, entitySystemParameterDTO).getDetailvo();
        // systemInfoService.initSystemParameter();

        if (hasActionErrors()) {
            return INPUT;
        }
        this.addActionMessage("更新系统参数成功！");
        return this.inquery();
    }

    public PageDataDTO getPageDataDTO() {
        return pageDataDTO;
    }

    public void setPageDataDTO(PageDataDTO pageDataDTO) {
        this.pageDataDTO = pageDataDTO;
    }

    public EntitySystemParameterDTO getEntitySystemParameterDTO() {
        return entitySystemParameterDTO;
    }

    public void setEntitySystemParameterDTO(EntitySystemParameterDTO entitySystemParameterDTO) {
        this.entitySystemParameterDTO = entitySystemParameterDTO;
    }

    public EntitySystemParameterQueryDTO getEntitySystemParameterQueryDTO() {
        return entitySystemParameterQueryDTO;
    }

    public void setEntitySystemParameterQueryDTO(EntitySystemParameterQueryDTO entitySystemParameterQueryDTO) {
        this.entitySystemParameterQueryDTO = entitySystemParameterQueryDTO;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public IssuerDTO getIssuerDTO() {
        return issuerDTO;
    }

    public void setIssuerDTO(IssuerDTO issuerDTO) {
        this.issuerDTO = issuerDTO;
    }

    public void validateUpdate() {
        try {
            String rex1 = "^[0-1]{1}$";
            String rex2 = "^[0-9]{1,12}$";
            if (entitySystemParameterDTO.getParameterCode().equals("RANSOME_ORDER_MAX_MONEY")) {
                if (!entitySystemParameterDTO.getParameterValue().matches(rex2)) {
                    addFieldError("entitySystemParameterDTO.parameterValue", "参数值必须为1到12位数字!");
                }
            } else if (entitySystemParameterDTO.getParameterCode().equals("CHECK_CARD")) {
                if (!entitySystemParameterDTO.getParameterValue().matches(rex1)) {
                    addFieldError("entitySystemParameterDTO.parameterValue", "验卡参数值必须是0或1!");
                }
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
    }

}
