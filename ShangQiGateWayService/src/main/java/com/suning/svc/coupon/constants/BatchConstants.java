/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: BatchConstants.java
 * Author:   13040443
 * Date:     2013-10-30 上午09:21:09
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.constants;

/**
 * 批量处理常量
 * 
 * @author yanbin
 */
public class BatchConstants {

    /** 每批处理的次数 */
    public static final Long DEAL_BATCH_SIZE = 1000l;

    /** 处理的计数最大 */
    public static final Long MAX_DEAL_COUNTER = 3L;

    /** 字符串1 */
    public static final String ONE = "1";

    /** 字符串空 */
    public static final String BLOCK = "";

    /** 再次批处理时间:一小时 */
    public static final int REDEAL_TIME = -2;

}
