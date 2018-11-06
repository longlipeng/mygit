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
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
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
public class SVCDataInDB {
    Logger logger = LoggerFactory.getLogger(SVCDataInDB.class);

    private static final String splitor = ",";
    
    Timestamp now = new Timestamp(new Date().getTime());
    
    //private static final String SQL = "insert into t_action_access_tmp(sub_system, access_time, invoke_No, ip, action, method, spend, fileName) values(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL = "INSERT INTO SVC_MNG.CP_TRADE_ITEM_TEMP(ID, PARTNER, MCHT_CODE, TRADE_TYPE, DIRECTION, COUPON_NO, ITEM_ORDER_NO, TRADE_TIME, AMOUNT, STATUS, COUNTER, CREATED_TIME, UPDATED_TIME) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final int BATCH_SIZE = 10000;
    
    @Autowired
    @Qualifier("jdbcTemplate")
    JdbcTemplate template;
    
    @Test
    public void load(){
        String path = "D:\\im\\st\\SametimeFileTransfers\\预付卡";
        File f = new File(path);
        String[] files = f.list();
        for(String fileName : files){
            loadFile(null, path, fileName);
        }
        
    }
    
    private void loadFile(final String partner, String path, String fileName){

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
            while((line = reader.readLine())!= null){
                if(!StringUtils.hasText(line)){
                    continue;
                }
                try{
                    cols = line.split(splitor);
                    merchantCode = cols[2];
                    tradeType = cols[3];
                    direction = cols[4];
                    couponNO = cols[5];
                    itemOrderNO = cols[6];
                    tradeTime = cols[7];
                    amount = Long.parseLong(cols[8]);
                    
                    TradeItemTemp access = new TradeItemTemp();
                    access.setId(Long.parseLong(cols[0]));
                    access.setPartner(cols[1]);
                    access.setMchtCode(merchantCode);
                    access.setTradeType(tradeType);
                    access.setDirection(direction);
                    access.setCouponNo(couponNO);
                    access.setItemOrderNo(itemOrderNO);
                    access.setTradeTime(tradeTime);
                    access.setAmount(amount);
                    access.setStatus(cols[12]);
                    access.setCounter(Integer.parseInt(cols[13]));
                    access.setCreatedTime(DateUtils.parseDate(cols[15], "yyyy-MM-dd-HH.mm.ss.SSSSSS"));
                    access.setUpdatedTime(DateUtils.parseDate(cols[16], "yyyy-MM-dd-HH.mm.ss.SSSSSS"));
                    //access.setUpdatedTime(now);
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
                ps.setLong(1, item.getId());
                ps.setString(2, item.getPartner());
                ps.setString(3, item.getMchtCode());
                ps.setString(4, item.getTradeType());
                ps.setString(5, item.getDirection());
                ps.setString(6, item.getCouponNo());
                ps.setString(7, item.getItemOrderNo());
                ps.setString(8, item.getTradeTime());
                ps.setLong(9, item.getAmount());
                ps.setString(10, item.getStatus());
                ps.setInt(11, item.getCounter());
                ps.setTimestamp(12, new Timestamp(item.getCreatedTime().getTime()));
                ps.setTimestamp(13, new Timestamp(item.getCreatedTime().getTime()));
            }
            
            public int getBatchSize() {
                return l.size();
            }
        });
    }
    
}


