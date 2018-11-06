package com.suning.svc.ibatis.dao;

import java.util.List;

import com.suning.svc.ibatis.model.Partner;
import com.suning.svc.ibatis.model.PartnerExample;

public interface PartnerDAO {

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_PARTNER
     * 
     * @abatorgenerated Tue Oct 29 19:15:07 CST 2013
     */
    void insert(Partner record);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_PARTNER
     * 
     * @abatorgenerated Tue Oct 29 19:15:07 CST 2013
     */
    int updateByPrimaryKey(Partner record);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_PARTNER
     * 
     * @abatorgenerated Tue Oct 29 19:15:07 CST 2013
     */
    int updateByPrimaryKeySelective(Partner record);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_PARTNER
     * 
     * @abatorgenerated Tue Oct 29 19:15:07 CST 2013
     */
    List selectByExample(PartnerExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_PARTNER
     * 
     * @abatorgenerated Tue Oct 29 19:15:07 CST 2013
     */
    Partner selectByPrimaryKey(String code);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_PARTNER
     * 
     * @abatorgenerated Tue Oct 29 19:15:07 CST 2013
     */
    int deleteByExample(PartnerExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_PARTNER
     * 
     * @abatorgenerated Tue Oct 29 19:15:07 CST 2013
     */
    int deleteByPrimaryKey(String code);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_PARTNER
     * 
     * @abatorgenerated Tue Oct 29 19:15:07 CST 2013
     */
    int countByExample(PartnerExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_PARTNER
     * 
     * @abatorgenerated Tue Oct 29 19:15:07 CST 2013
     */
    int updateByExampleSelective(Partner record, PartnerExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_PARTNER
     * 
     * @abatorgenerated Tue Oct 29 19:15:07 CST 2013
     */
    int updateByExample(Partner record, PartnerExample example);
}