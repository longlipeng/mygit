/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: dsde.java
 * Author:   13071598
 * Date:     2013-8-1 下午04:17:09
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.univer.issuer.report;

import com.allinfinance.univer.report.dto.CurrentTimeStockDTO;
import com.huateng.univer.report.action.NewIreportAction;

import net.sf.json.JSONObject;

/**
 * 〈卡实时库存查询报表action〉<br> 
 * 〈功能详细描述〉
 *
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CurrentTimeStockAction extends NewIreportAction{

    private static final long serialVersionUID = 6911509068002612132L;

    private Integer totalRows = 0;
    
    private String functionRoleId;
    
    
    public String getFunctionRoleId() {
        return functionRoleId;
    }

    public void setFunctionRoleId(String functionRoleId) {
        this.functionRoleId = functionRoleId;
    }

    /**
     * 报表对应的dto
     * */
    private CurrentTimeStockDTO currentTimeStockDTO = new CurrentTimeStockDTO();

    public CurrentTimeStockDTO getCurrentTimeStockDTO() {
        return currentTimeStockDTO;
    }

    public void setCurrentTimeStockDTO(CurrentTimeStockDTO currentTimeStockDTO) {
        this.currentTimeStockDTO = currentTimeStockDTO;
    }

    public Integer getTotalRows() {

        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    //定义报表名称、类型和机构信息
    public String inQuery() throws Exception {
        currentTimeStockDTO.setFunctionRoleId(functionRoleId);
        currentTimeStockDTO.setReportName("current_time_stock");
        currentTimeStockDTO.setReportType("xls");
        currentTimeStockDTO.setIssuerId(getUser().getEntityId());
        currentTimeStockDTO.setIssuerName(getUser().getIssuerName());
        currentTimeStockDTO.setReportFileName("卡实时库存查询报表");
            return "list";
    }

    @Override
    protected JSONObject getJSONOBJect() {
        return JSONObject.fromObject(currentTimeStockDTO);
    }
}
