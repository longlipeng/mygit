package com.huateng.framework.ibatis.dao;

import java.util.List;

import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.invoice.dto.OrderInvoiceInfoDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.model.OrderInvoiceInfoExample;

public interface OrderInvoiceDAO {
    /**
     * 返回所有销售人员的姓名
     * @return 
     * @throws BizServiceException 
     */
    List<CustomerDTO> querySaleManNameByEntityId(CustomerDTO customerDTO) throws BizServiceException;
    /**
     * 根据id查找订单和发票信息
     * @param orderInvoiceInfoDTO
     * @return
     * @throws BizServiceException
     */
    OrderInvoiceInfoDTO queryInvoiceInfoById(OrderInvoiceInfoDTO orderInvoiceInfoDTO) throws BizServiceException;
    
}
