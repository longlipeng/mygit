/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: sss.java
 * Author:   Administrator
 * Date:     2013-11-1 下午04:12:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author> gouhao     <time> 2013-11-1 下午04:12:00     <version> 1.0    <desc> 发票管理页面的service类
 */

package com.huateng.univer.order.business.service;

import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.invoice.dto.InvoiceReceiveDTO;
import com.allinfinance.univer.seller.invoice.dto.InvoiceReceiveQueryDTO;
import com.allinfinance.univer.seller.invoice.dto.InvoiceReceiveUpdateDTO;
import com.allinfinance.univer.seller.invoice.dto.OrderInvoiceInfoDTO;
import com.allinfinance.univer.seller.invoice.dto.OrderInvoiceInfoQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.exception.BizServiceException;
/**
 * 发票管理接口<br> 
 * 提供发票管理的方法接口
 *
 * @author gouhao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface InvoiceReceiveService {
	
    /**
     * 
     * 功能描述: 发票管理查询接口方法<br>
     * 〈功能详细描述〉
     *
     * @param invoiceReceiveQueryDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
	public PageDataDTO query(InvoiceReceiveQueryDTO invoiceReceiveQueryDTO) throws BizServiceException;
	
	 /**
     * 
     * 功能描述: 收票操作接口方法<br>
     * 〈功能详细描述〉
     *
     * @param invoiceReceiveQueryDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
	public void modifyReceive(InvoiceReceiveUpdateDTO invoiceReceiveUpdateDTO) throws BizServiceException;
	
	 /**
     * 
     * 功能描述: 交票操作接口方法<br>
     * 〈功能详细描述〉
     *
     * @param invoiceReceiveQueryDTO
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void modifyHand(InvoiceReceiveUpdateDTO invoiceReceiveUpdateDTO) throws BizServiceException;
}
