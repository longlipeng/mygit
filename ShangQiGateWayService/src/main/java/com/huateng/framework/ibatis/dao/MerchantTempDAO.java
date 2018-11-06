package com.huateng.framework.ibatis.dao;

import java.util.List;

import com.huateng.framework.ibatis.model.MerchantTemp;
import com.huateng.framework.ibatis.model.MerchantTempExample;

/**
 * 
 * MerchantTempDAO<BR>
 * generated by Abator
 * 
 * @author 12073942
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface MerchantTempDAO {
    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_MERCHANT_TEMP
     * 
     * @abatorgenerated Tue Aug 06 13:10:33 CST 2013
     */
    void insert(MerchantTemp record);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_MERCHANT_TEMP
     * 
     * @abatorgenerated Tue Aug 06 13:10:33 CST 2013
     */
    int updateByPrimaryKey(MerchantTemp record);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_MERCHANT_TEMP
     * 
     * @abatorgenerated Tue Aug 06 13:10:33 CST 2013
     */
    int updateByPrimaryKeySelective(MerchantTemp record);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_MERCHANT_TEMP
     * 
     * @abatorgenerated Tue Aug 06 13:10:33 CST 2013
     */
    List selectByExample(MerchantTempExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_MERCHANT_TEMP
     * 
     * @abatorgenerated Tue Aug 06 13:10:33 CST 2013
     */
    MerchantTemp selectByPrimaryKey(String entityId);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_MERCHANT_TEMP
     * 
     * @abatorgenerated Tue Aug 06 13:10:33 CST 2013
     */
    int deleteByExample(MerchantTempExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_MERCHANT_TEMP
     * 
     * @abatorgenerated Tue Aug 06 13:10:33 CST 2013
     */
    int deleteByPrimaryKey(String entityId);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_MERCHANT_TEMP
     * 
     * @abatorgenerated Tue Aug 06 13:10:33 CST 2013
     */
    int countByExample(MerchantTempExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_MERCHANT_TEMP
     * 
     * @abatorgenerated Tue Aug 06 13:10:33 CST 2013
     */
    int updateByExampleSelective(MerchantTemp record, MerchantTempExample example);

    /**
     * This method was generated by Abator for iBATIS. This method corresponds to the database table TB_MERCHANT_TEMP
     * 
     * @abatorgenerated Tue Aug 06 13:10:33 CST 2013
     */
    int updateByExample(MerchantTemp record, MerchantTempExample example);
}