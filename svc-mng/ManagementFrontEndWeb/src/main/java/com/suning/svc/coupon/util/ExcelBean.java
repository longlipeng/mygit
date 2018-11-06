/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ExcelBean.java
 * Author:   13040443
 * Date:     2013-12-13 下午04:50:32
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.util;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 用于创建excel 的辅助bean
 * 
 * @author yanbin
 */
public class ExcelBean {

    private HSSFWorkbook book;
    private String sheetName;
    private List<Map<String, String>> dataList;
    private String[] titles;
    private String[] fields;
    private InputStream inputStream;
    private HSSFWorkbook resultBook;

    private boolean isDefaultSheet = false;
    private boolean isUseExistbook = false;
    private boolean isReturnStream = false;

    /**
     * @return the book
     */
    public HSSFWorkbook getBook() {
        return book;
    }

    /**
     * @param book the book to set
     */
    public void setBook(HSSFWorkbook book) {
        this.book = book;
    }

    /**
     * @return the sheetName
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * @param sheetName the sheetName to set
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * @return the dataList
     */
    public List<Map<String, String>> getDataList() {
        return dataList;
    }

    /**
     * @param dataList the dataList to set
     */
    public void setDataList(List<Map<String, String>> dataList) {
        this.dataList = dataList;
    }

    /**
     * @return the titles
     */
    public String[] getTitles() {
        return titles;
    }

    /**
     * @param titles the titles to set
     */
    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    /**
     * @return the fields
     */
    public String[] getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(String[] fields) {
        this.fields = fields;
    }

    /**
     * @return the isDefaultSheet
     */
    public boolean isDefaultSheet() {
        return isDefaultSheet;
    }

    /**
     * @param isDefaultSheet the isDefaultSheet to set
     */
    public void setDefaultSheet(boolean isDefaultSheet) {
        this.isDefaultSheet = isDefaultSheet;
    }

    /**
     * @return the isUseExistbook
     */
    public boolean isUseExistbook() {
        return isUseExistbook;
    }

    /**
     * @param isUseExistbook the isUseExistbook to set
     */
    public void setUseExistbook(boolean isUseExistbook) {
        this.isUseExistbook = isUseExistbook;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param inputStream the inputStream to set
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * @return the isReturnStream
     */
    public boolean isReturnStream() {
        return isReturnStream;
    }

    /**
     * @param isReturnStream the isReturnStream to set
     */
    public void setReturnStream(boolean isReturnStream) {
        this.isReturnStream = isReturnStream;
    }

    /**
     * @return the resultBook
     */
    public HSSFWorkbook getResultBook() {
        return resultBook;
    }

    /**
     * @param resultBook the resultBook to set
     */
    public void setResultBook(HSSFWorkbook resultBook) {
        this.resultBook = resultBook;
    }

}
