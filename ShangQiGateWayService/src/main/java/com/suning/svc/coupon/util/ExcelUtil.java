/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ExcelUtil.java
 * Author:   13040443
 * Date:     2013-12-13 下午04:25:08
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 组装excel工具类
 * 
 * @author yanbin
 */
public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 生成excel
     * 
     * @param excelBean
     * @return
     */
    public static ExcelBean generateExcel(ExcelBean excelBean) {
        HSSFWorkbook workbook;
        if (excelBean.isUseExistbook()) {
            workbook = excelBean.getBook();
        } else {
            workbook = new HSSFWorkbook();
        }

        HSSFSheet sheet;
        if (excelBean.isDefaultSheet()) {
            sheet = workbook.createSheet(excelBean.getSheetName());
        } else {
            sheet = workbook.createSheet();
        }

        HSSFRow titleRow = sheet.createRow(0);

        String[] titles = excelBean.getTitles();
        for (int i = 0; i < titles.length; i++) {
            HSSFCell titleCell = titleRow.createCell(i);
            titleCell.setCellValue(titles[i]);
        }

        List<Map<String, String>> lists = excelBean.getDataList();
        for (int i = 0; i < lists.size(); i++) {
            HSSFRow contentRow = sheet.createRow(i + 1);
            String[] fields = excelBean.getFields();
            for (int j = 0; j < fields.length; j++) {
                HSSFCell fieldCell = contentRow.createCell(j);
                fieldCell.setCellValue(MapUtils.getString(lists.get(i), fields[j]));
            }
        }

        // 定义个文件
        // File file = new File("D:/test/temp.xls");
        // FileOutputStream fos = null;
        // InputStream is = null;
        //
        // try {
        // // 定义文件的输出流
        // fos = new FileOutputStream(file);
        // // 写入
        // workbook.write(fos);
        // is = new FileInputStream(file);
        // excelBean.setInputStream(is);
        // } catch (Exception e) {
        // e.printStackTrace();
        // } finally {
        // try {
        // fos.flush();
        // fos.close();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }

        if (excelBean.isReturnStream()) {
            ByteArrayOutputStream ops = null;
            InputStream is = null;

            try {
                ops = new ByteArrayOutputStream();
                workbook.write(ops);
                is = new ByteArrayInputStream(ops.toByteArray());
                excelBean.setInputStream(is);
            } catch (IOException e) {
                logger.error(e.toString());
            } finally {
                try {
                    if (null != ops) {
                        ops.flush();
                        ops.close();
                    }
                } catch (IOException e) {
                    logger.error(e.toString());
                }
            }
        }
        excelBean.setResultBook(workbook);
        return excelBean;
    }

}
