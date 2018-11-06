package com.allinfinance.prepay.service;

import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CompanyInfoDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;

/**
 * CRMͬ��
 * @author yly
 *
 */
public interface SynchCRMService {
    /**
     * ���˳ֿ���ͬ��CRM
     * @param dto
     * @throws Exception
     */
    public void syncToPerCRM(CardholderDTO dto) throws Exception;
    
    /**
     * ��ҵ�ֿ���ͬ��CRM
     * @param dto
     * @throws Exception
     */
    public void syncToCusCRM(CompanyInfoDTO dto) throws Exception;
    
    /**
     * ���˿ͻ�ͬ��CRM
     * @param dto
     * @throws Exception
     */
    public void synchPerToCRM(CustomerDTO dto) throws Exception;
    
    /**
     * ��ҵ�ͻ�ͬ��CRM
     * @param dto
     * @throws Exception
     */
    public void synchCusToCRM(CompanyInfoDTO dto) throws Exception;

}
