/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: ScriptCreater.java
 * Author:   秦伟
 * Date:     2013-11-9 下午5:15:22
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.suning.svc.coupon.db;

import java.io.FileOutputStream;
import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScriptCreater {
    public static ClassPathXmlApplicationContext getBeanFactory() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "classpath:spring/applicationContext-test.xml"});
        return context;
    }

    public static Connection getConnection() throws Exception {
        DataSource ds = (DataSource) getBeanFactory().getBean("dataSource");
        Connection conn = ds.getConnection();

        return conn;
    }

    public static void exportXMLData() throws Exception {
        DataSource ds = (DataSource) getBeanFactory().getBean("dataSource");
        Connection conn = ds.getConnection();

        IDatabaseConnection connection = new DatabaseConnection(conn);
        DatabaseConfig config = connection.getConfig();
        config.setFeature("http://www.dbunit.org/features/qualifiedTableNames", false);
        //connection.getConfig().setFeature(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, false);
 
        // partial database export
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        //partialDataSet.addTable("CP_TRADE_ITEM_TEMP", "select * from CP_TRADE_ITEM_TEMP where trade_type='0008'");
        //partialDataSet.addTable("CP_VIRTUAL_CARD");
        partialDataSet.addTable("CP_CONSUME_ORDER");
        
        //FlatXmlDataSet.write(partialDataSet, new FileOutputStream("src/test/resources/dbunit/data.xml"));
        
        //XmlDataSet .write(partialDataSet, new FileOutputStream("src/test/resources/dbunit/cp_virtual_card.xml"));
        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("src/test/resources/dbunit/consumeOrder.xml"));
        // full database export
        // IDataSet fullDataSet = connection.createDataSet();
        // FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));
    }

    @SuppressWarnings("deprecation")
    public static void exportDTD() throws Exception {
        DataSource ds = (DataSource) getBeanFactory().getBean("dataSource");
        Connection conn = ds.getConnection();

        IDatabaseConnection connection = new DatabaseConnection(conn);
        DatabaseConfig config = connection.getConfig();
        config.setFeature("http://www.dbunit.org/features/qualifiedTableNames",
                false);

        // partial database export
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("CP_CONSUME_ORDER");

        FlatDtdDataSet.write(partialDataSet, new FileOutputStream(
                "src/test/resources/dbunit/data.dtd"));

        // write DTD file
        // FlatDtdDataSet.write(connection.createDataSet(),
        // new FileOutputStream("security.dtd"));
    }

    public static void main(String[] args) throws Exception {
        exportXMLData();
        //exportDTD();
    }
}
