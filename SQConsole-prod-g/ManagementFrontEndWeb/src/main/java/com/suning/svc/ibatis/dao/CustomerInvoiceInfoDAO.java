package com.suning.svc.ibatis.dao;

import java.util.List;

import com.suning.svc.ibatis.model.CustomerInvoiceInfo;
import com.suning.svc.ibatis.model.CustomerInvoiceInfoExample;

public interface CustomerInvoiceInfoDAO {

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table
     * TB_CUSTOMER_INVOICE_INFO
     * 
     * @abatorgenerated Sun Nov 03 14:56:22 CST 2013
     */
    void insert(CustomerInvoiceInfo record);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table
     * TB_CUSTOMER_INVOICE_INFO
     * 
     * @abatorgenerated Sun Nov 03 14:56:22 CST 2013
     */
    int updateByPrimaryKey(CustomerInvoiceInfo record);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table
     * TB_CUSTOMER_INVOICE_INFO
     * 
     * @abatorgenerated Sun Nov 03 14:56:22 CST 2013
     */
    int updateByPrimaryKeySelective(CustomerInvoiceInfo record);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table
     * TB_CUSTOMER_INVOICE_INFO
     * 
     * @abatorgenerated Sun Nov 03 14:56:22 CST 2013
     */
    List selectByExample(CustomerInvoiceInfoExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table
     * TB_CUSTOMER_INVOICE_INFO
     * 
     * @abatorgenerated Sun Nov 03 14:56:22 CST 2013
     */
    CustomerInvoiceInfo selectByPrimaryKey(Long id);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table
     * TB_CUSTOMER_INVOICE_INFO
     * 
     * @abatorgenerated Sun Nov 03 14:56:22 CST 2013
     */
    int deleteByExample(CustomerInvoiceInfoExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table
     * TB_CUSTOMER_INVOICE_INFO
     * 
     * @abatorgenerated Sun Nov 03 14:56:22 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table
     * TB_CUSTOMER_INVOICE_INFO
     * 
     * @abatorgenerated Sun Nov 03 14:56:22 CST 2013
     */
    int countByExample(CustomerInvoiceInfoExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table
     * TB_CUSTOMER_INVOICE_INFO
     * 
     * @abatorgenerated Sun Nov 03 14:56:22 CST 2013
     */
    int updateByExampleSelective(CustomerInvoiceInfo record, CustomerInvoiceInfoExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table
     * TB_CUSTOMER_INVOICE_INFO
     * 
     * @abatorgenerated Sun Nov 03 14:56:22 CST 2013
     */
    int updateByExample(CustomerInvoiceInfo record, CustomerInvoiceInfoExample example);
}