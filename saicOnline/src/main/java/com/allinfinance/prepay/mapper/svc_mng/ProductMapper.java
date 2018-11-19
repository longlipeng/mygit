package com.allinfinance.prepay.mapper.svc_mng;

import com.allinfinance.prepay.model.Product;
import com.allinfinance.prepay.model.ProductExample;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageDTO;
import com.allinfinance.univer.issuer.dto.product.ProductCardBinDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ProductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_PRODUCT
     *
     * @mbggenerated Tue Apr 26 11:05:47 CST 2016
     */
    int countByExample(ProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_PRODUCT
     *
     * @mbggenerated Tue Apr 26 11:05:47 CST 2016
     */
    int deleteByExample(ProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_PRODUCT
     *
     * @mbggenerated Tue Apr 26 11:05:47 CST 2016
     */
    int insert(Product record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_PRODUCT
     *
     * @mbggenerated Tue Apr 26 11:05:47 CST 2016
     */
    int insertSelective(Product record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_PRODUCT
     *
     * @mbggenerated Tue Apr 26 11:05:47 CST 2016
     */
    List<Product> selectByExample(ProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_PRODUCT
     *
     * @mbggenerated Tue Apr 26 11:05:47 CST 2016
     */
    int updateByExampleSelective(@Param("record") Product record, @Param("example") ProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SVC_MNG.TB_PRODUCT
     *
     * @mbggenerated Tue Apr 26 11:05:47 CST 2016
     */
    int updateByExample(@Param("record") Product record, @Param("example") ProductExample example);
    
    List<ServiceDTO> getProdAcctypeDTOs(ProductDTO productDto);
    List<ProductCardBinDTO> getCardBinByProductId(String productId);
    List<PackageDTO> getProdPackageDTOs(ProductDTO productDto);
    List<CardLayoutDTO> getCardLayoutDTOs(ProductDTO productDto);
    List<String> selectSellProduct(String defaultEntityId);
    //查询所有的卡产品
    List<com.allinfinance.prepay.dto.Product> selectProductAll(String issueId);
}