package com.allinfinance.prepay.service;

import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CompanyInfoDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;

/**
 * CRM同步
 * @author yly
 *
 */
public interface SynchCRMService {
    /**
     * 个人持卡人同步CRM
     * @param dto
     * @throws Exception
     */
    public void syncToPerCRM(CardholderDTO dto) throws Exception;
    
    /**
     * 企业持卡人同步CRM
     * @param dto
     * @throws Exception
     */
    public void syncToCusCRM(CompanyInfoDTO dto) throws Exception;
    
    /**
     * 个人客户同步CRM
     * @param dto
     * @throws Exception
     */
    public void synchPerToCRM(CustomerDTO dto) throws Exception;
    
    /**
     * 企业客户同步CRM
     * @param dto
     * @throws Exception
     */
    public void synchCusToCRM(CompanyInfoDTO dto) throws Exception;

}
