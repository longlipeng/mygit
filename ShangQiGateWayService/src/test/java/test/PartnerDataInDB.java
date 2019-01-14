package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import com.suning.svc.coupon.constants.SequenceContansts;
import com.suning.svc.coupon.util.SerialNumberUtil;
import com.suning.svc.ibatis.model.TradeItemTemp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-test.xml"})
public class PartnerDataInDB {
    Logger logger = LoggerFactory.getLogger(PartnerDataInDB.class);

    private static final String splitor = ",";
    
    Timestamp now = new Timestamp(new Date().getTime());
    
    //private static final String SQL = "insert into t_action_access_tmp(sub_system, access_time, invoke_No, ip, action, method, spend, fileName) values(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL = "INSERT INTO SVC_MNG.CP_TRADE_ITEM_TEMP_PARTNER(id, PARTNER, MCHT_CODE, TRADE_TYPE, DIRECTION, COUPON_NO, ITEM_ORDER_NO, TRADE_TIME, AMOUNT, STATUS, COUNTER, CREATED_TIME, UPDATED_TIME) VALUES(nextval for seq_trade_item_sn, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final int BATCH_SIZE = 10000;
    
    @Autowired
    @Qualifier("jdbcTemplate")
    JdbcTemplate template;
    
    @Test
    public void load(){
        String path = "D:\\im\\st\\SametimeFileTransfers\\SAP结算数据";
        File f = new File(path);
        String[] files = f.list(new FilenameFilter() {
            
            @Override
            public boolean accept(File dir, String name) {
                if(name.startsWith("SAP")){
                    return true;
                }
                return false;
            }
        });
        for(String fileName : files){
            loadFile("SAPFM", path, fileName);
        }
        
        files = f.list(new FilenameFilter() {
            
            @Override
            public boolean accept(File dir, String name) {
                if(name.startsWith("SAC")){
                    return true;
                }
                return false;
            }
        });
        for(String fileName : files){
            loadFile("SAC", path, fileName);
        }
    }
    
    private void loadFile(final String partner, String path, String fileName){
        int formate = (fileName.indexOf("1108") >=0 || fileName.indexOf("SAC") >= 0)?1:0;

        File currentFile = new File(path, fileName);
        BufferedReader reader =  null;
        String line = null;
        
        String[] cols;
        String status = "0";
        String merchantCode = null;
        String tradeType = null;
        String direction  = null;
        String couponNO = null;
        String itemOrderNO = null;
        String tradeTime = null;
        long amount = 0;
        int counter = 0;
        List<TradeItemTemp> l = new LinkedList<TradeItemTemp>();
        
        try {
            logger.debug("processing file: {}", currentFile.getAbsolutePath());
            reader = new BufferedReader(new FileReader(currentFile));
            reader.readLine();
            while((line = reader.readLine())!= null){
                if(!StringUtils.hasText(line)){
                    continue;
                }
                try{
                    cols = line.split(splitor);
                    if(formate == 1){
                        merchantCode = cols[0].trim();
                        tradeType = org.apache.commons.lang3.StringUtils.leftPad(cols[1].trim(), 4, "0");
                        direction = cols[2].trim();
                        couponNO = cols[3].trim();
                        itemOrderNO = cols[4].trim();
                        tradeTime = cols[5].trim();
                    }else{
                        merchantCode = cols[0].trim();
                        tradeType = org.apache.commons.lang3.StringUtils.leftPad(cols[2].trim(), 4, "0");
                        direction = cols[3].trim();
                        couponNO = cols[1].trim();
                        itemOrderNO = cols[4].trim();
                        tradeTime = cols[5].trim();
                        
                    }
                        
                    try{
                            //amount = new Double(Double.parseDouble(cols[6].trim()) * 100).intValue();
                        amount = Integer.parseInt(MoneyUtils.fromYuanToFen(cols[6].trim()));
                    }catch(Exception e){
                            logger.error("{}记录有问题:{}", fileName, line);
                            //logger.error(e.getMessage(), e);
                            amount = 0;
                    }
                    TradeItemTemp access = new TradeItemTemp();
                    //access.setId(SerialNumberUtil.getSequence(SequenceContansts.SEQ_TRADE_ITEM_SN));
                    access.setPartner(partner);
                    access.setMchtCode(merchantCode);
                    access.setTradeType(tradeType);
                    access.setDirection(direction);
                    access.setCouponNo(couponNO);
                    access.setItemOrderNo(itemOrderNO);
                    access.setTradeTime(tradeTime);
                    access.setAmount(amount);
                    access.setStatus(status);
                    access.setCounter(counter);
                    access.setCreatedTime(now);
                    access.setUpdatedTime(now);
                    l.add(access);
                    if (l.size() == BATCH_SIZE) {
                        logger.debug("batch insert {} 记录", l.size());
                        batchInsertActionAccess(l);
                        l.clear();
                    }
                    
                }catch(Exception e){
                    logger.error("入库文件" + currentFile.getAbsolutePath() + "记录" + line + "出错", e);
                    l.clear();
                }
            }
            if(l.size() > 0){
                logger.debug("batch insert {} 记录", l.size());
                batchInsertActionAccess(l);
                l.clear();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (Exception e) {
            logger.error("入库文件{}记录{}出错",currentFile.getAbsolutePath(),line);
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
    void batchInsertActionAccess(final List<TradeItemTemp> l){
        template.batchUpdate(SQL, new BatchPreparedStatementSetter() {
            
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TradeItemTemp item = l.get(i);
                //ps.setLong(1, item.getId());
                ps.setString(1, item.getPartner());
                ps.setString(2, item.getMchtCode());
                ps.setString(3, item.getTradeType());
                ps.setString(4, item.getDirection());
                ps.setString(5, item.getCouponNo());
                ps.setString(6, item.getItemOrderNo());
                ps.setString(7, item.getTradeTime());
                ps.setLong(8, item.getAmount());
                ps.setString(9, item.getStatus());
                ps.setInt(10, item.getCounter());
                ps.setTimestamp(11, new Timestamp(item.getCreatedTime().getTime()));
                ps.setTimestamp(12, new Timestamp(item.getCreatedTime().getTime()));
            }
            
            public int getBatchSize() {
                return l.size();
            }
        });
    }
    
}


