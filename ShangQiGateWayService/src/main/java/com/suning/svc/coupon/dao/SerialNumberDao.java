/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: SerialNumberDao.java
 * Author:   秦伟
 * Date:     2013-10-29 下午8:52:15
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.dao;

import java.sql.SQLException;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author 11051612
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface SerialNumberDao {
    /**
     * 根据序列名称生成16位序列单号 (8位日期+8位序列)如：2011091500000052
     * 
     * @param seqName 序列名称
     * @return
     * @throws SQLException
     */
    public String getSerialNumberOf16BySeqName(String seqName);
    
    /**
     * 从序列中取值
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param seqName 序列名称 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public Long getSequence(String seqName);
}