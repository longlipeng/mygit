package com.allinfinance.prepay.model;

public class ProdLayout {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVC_MNG.TB_REL_PROD_LAYOUT.REL_ID
     *
     * @mbggenerated Mon Apr 25 10:05:50 CST 2016
     */
    private String relId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVC_MNG.TB_REL_PROD_LAYOUT.CARD_LAYOUT_ID
     *
     * @mbggenerated Mon Apr 25 10:05:50 CST 2016
     */
    private String cardLayoutId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SVC_MNG.TB_REL_PROD_LAYOUT.PRODUCT_ID
     *
     * @mbggenerated Mon Apr 25 10:05:50 CST 2016
     */
    private String productId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVC_MNG.TB_REL_PROD_LAYOUT.REL_ID
     *
     * @return the value of SVC_MNG.TB_REL_PROD_LAYOUT.REL_ID
     *
     * @mbggenerated Mon Apr 25 10:05:50 CST 2016
     */
    public String getRelId() {
        return relId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVC_MNG.TB_REL_PROD_LAYOUT.REL_ID
     *
     * @param relId the value for SVC_MNG.TB_REL_PROD_LAYOUT.REL_ID
     *
     * @mbggenerated Mon Apr 25 10:05:50 CST 2016
     */
    public void setRelId(String relId) {
        this.relId = relId == null ? null : relId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVC_MNG.TB_REL_PROD_LAYOUT.CARD_LAYOUT_ID
     *
     * @return the value of SVC_MNG.TB_REL_PROD_LAYOUT.CARD_LAYOUT_ID
     *
     * @mbggenerated Mon Apr 25 10:05:50 CST 2016
     */
    public String getCardLayoutId() {
        return cardLayoutId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVC_MNG.TB_REL_PROD_LAYOUT.CARD_LAYOUT_ID
     *
     * @param cardLayoutId the value for SVC_MNG.TB_REL_PROD_LAYOUT.CARD_LAYOUT_ID
     *
     * @mbggenerated Mon Apr 25 10:05:50 CST 2016
     */
    public void setCardLayoutId(String cardLayoutId) {
        this.cardLayoutId = cardLayoutId == null ? null : cardLayoutId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SVC_MNG.TB_REL_PROD_LAYOUT.PRODUCT_ID
     *
     * @return the value of SVC_MNG.TB_REL_PROD_LAYOUT.PRODUCT_ID
     *
     * @mbggenerated Mon Apr 25 10:05:50 CST 2016
     */
    public String getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SVC_MNG.TB_REL_PROD_LAYOUT.PRODUCT_ID
     *
     * @param productId the value for SVC_MNG.TB_REL_PROD_LAYOUT.PRODUCT_ID
     *
     * @mbggenerated Mon Apr 25 10:05:50 CST 2016
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }
}