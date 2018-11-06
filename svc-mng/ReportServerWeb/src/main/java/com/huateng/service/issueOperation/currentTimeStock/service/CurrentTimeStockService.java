/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: dqdqwd.java
 * Author:   13071598
 * Date:     2013-8-1 下午04:37:55
 * Description: //卡实时库存查询报表接口      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 李斌斌            2013-08-01
 */
package com.huateng.service.issueOperation.currentTimeStock.service;

import com.allinfinance.univer.report.dto.CurrentTimeStockDTO;
import com.huateng.service.BizBaseService;
import com.huateng.service.BizService;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * 〈卡实时库存查询报表Service类  〉<br> 
 * 〈卡实时库存查询报表  Service〉
 *
 * @author 13071598
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CurrentTimeStockService extends BizBaseService implements BizService{

    /**
     * 调用后台配置文件，完成对数据库的操作
     * */
    @SuppressWarnings("unchecked")
    public List<Object> getList(JSONObject jsonDto) {
        CurrentTimeStockDTO currentTimeStockDTO = (CurrentTimeStockDTO) JSONObject.toBean(
                jsonDto, CurrentTimeStockDTO.class);
        return baseDao.queryForList("current_time_stock", "current_time_stock", currentTimeStockDTO);
    }
}
