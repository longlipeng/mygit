package com.huateng.univer.synch.CRMSynch;

import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CompanyInfoDTO;

/**
 * 同步持卡人信息到CRM系统接口
 * @author yly
 *
 */
public interface CardholderSynchCRM {
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

}
