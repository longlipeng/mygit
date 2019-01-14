/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SapInfoService.java
 * Author:   Administrator
 * Date:     2013-10-31 下午02:53:41
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.service;
import com.suning.svc.ibatis.model.SumOrderResult;

import java.util.List;

/**
 * 结算信息
 *
 * @author xuwei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface SapInfoService {
/**
 *  结算信息数据插入
 *
 * @param record
 * @see [相关类/方法](可选)
 * @since [产品/模块版本](可选)
 */
public void insert(SumOrderResult record) ;
/**
 * 得到SumOrderResult List值
 *
 * @return List
 * @see [相关类/方法](可选)
 * @since [产品/模块版本](可选)
 */
public List<SumOrderResult> list();
/**
 * 插入方法接口
 * @see [相关类/方法](可选)
 * @since [产品/模块版本](可选)
 */
public void operator();
}
