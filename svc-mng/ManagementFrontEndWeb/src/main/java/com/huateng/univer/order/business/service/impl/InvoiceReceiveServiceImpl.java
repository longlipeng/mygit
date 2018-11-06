/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: sss.java
 * Author:   Administrator
 * Date:     2013-11-1 下午04:12:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author> gouhao     <time> 2013-11-1 下午04:12:00     <version> 1.0    <desc> 发票管理页面的service实现类
 */
package com.huateng.univer.order.business.service.impl;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.invoice.dto.InvoiceReceiveQueryDTO;
import com.allinfinance.univer.seller.invoice.dto.InvoiceReceiveUpdateDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderDaySumBatchConstant;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.DateUtil;
import com.huateng.univer.order.business.service.InvoiceReceiveService;
import com.suning.svc.coupon.constants.SapInfoConstants;
import com.suning.svc.coupon.constants.SequenceContansts;
import com.suning.svc.coupon.constants.SettlementConstants;
import com.suning.svc.coupon.dao.SettlementInvoiceDao;
import com.suning.svc.coupon.util.SerialNumberUtil;
import com.suning.svc.ibatis.dao.InvoiceDAO;
import com.suning.svc.ibatis.dao.SapInfoDAO;
import com.suning.svc.ibatis.dao.SettlementDAO;
import com.suning.svc.ibatis.model.Invoice;
import com.suning.svc.ibatis.model.SapInfo;
import com.suning.svc.ibatis.model.Settlement;
import com.suning.svc.ibatis.model.SumOrderResult;
import com.suning.svc.ibatis.model.SumOrderResultExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 发票管理页面的service实现类<br> 
 * 
 *
 * @author gouhao
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class InvoiceReceiveServiceImpl implements InvoiceReceiveService {
	
    /**
     * 日志类
     */
	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 分页查询DAO
	 */
	private PageQueryDAO pageQueryDAO;
	/**
	 * 发票DAO
	 */
	private InvoiceDAO invoiceDAO;
	/**
	 * 发票结算关系DAO
	 */
	private SettlementInvoiceDao settlementInvoiceDao;
	/**
	 * 结算单DAO
	 */
	private SettlementDAO settlementDAO;
	 /**
     * 记账信息Dao
     */
    private SapInfoDAO sapInfoDAO;
	
   

    /**
	 * 查询发票信息列表
	 * 
	 * @param orderInvoiceInfoQueryDTO
	 * @return PageDataDTO
	 */
	@Override
	public PageDataDTO query(InvoiceReceiveQueryDTO invoiceReceiveQueryDTO)
			throws BizServiceException {
	    logger.info("InvoiceReceiveServiceImpl!query");
		try {
			return pageQueryDAO.query("INVOICE_REQUIREMENT_SERVICE.queryInvoice",
					invoiceReceiveQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询订单失败!");
		}
	}
	
	/**
     * 收票操作
     * 
     * @param invoiceReceiveUpdateDTO
     * @return null
     */
	@Override
	public void modifyReceive(InvoiceReceiveUpdateDTO invoiceReceiveUpdateDTO)
			throws BizServiceException {
	    logger.info("InvoiceReceiveServiceImpl!receiveOrHand");
		try {
			Long[] idss = invoiceReceiveUpdateDTO.getIdss();
			String userId = invoiceReceiveUpdateDTO.getUserId();
			String invoiceNO = invoiceReceiveUpdateDTO.getInvoiceNO();
			logger.info("执行发票开票操作");
			//获取登陆操作员信息
			long invoiceId = idss[0];
				Invoice invoice = new Invoice();
				invoice.setId(invoiceId);
				invoice.setStatus(DataBaseConstant.INVOICE_ALREADY_RECEIVE);
				invoice.setUpdatedTime(DateUtil.getCurrentDate());
				invoice.setOpenInvoicer(userId);
				invoice.setInvoiceNo(invoiceNO);
				invoiceDAO.updateByPrimaryKeySelective(invoice);
			 //得到匹配结算单号
			logger.info("开始查询所开发票关联的已匹配结算单号");
            List<Long> settlementS =  this.querySettlement(invoiceReceiveUpdateDTO);
           // 如果结算单号不为空，检查其下发票是否都开票,修改结算单状态
            if(settlementS!=null&&settlementS.size()>0){
                logger.info("执行结算单已开票操作");
               for(long id:settlementS){
                   this.settlementReceive(id);
                   this.insertSapInfo(id);
                   //记账成功后，修改结算单状态为3-已记账
                   logger.info("记账成功后，修改结算单状态为3-已记账");
                   Settlement settlement = new Settlement();
                   settlement.setId(id);
                   settlement.setStatus(SettlementConstants.SETTLEMENT_STATUS_THREE);
                   settlement.setUpdatedTime(DateUtil.getCurrentDate());
                   settlementDAO.updateByPrimaryKeySelective(settlement);
               }
                
            }
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("收票操作失败!");
		}
	}
	/**
     * 交票操作
     * 
     * @param invoiceReceiveUpdateDTO
     * @return null
     */
    @Override
    public void modifyHand(InvoiceReceiveUpdateDTO invoiceReceiveUpdateDTO)
            throws BizServiceException {
        logger.info("InvoiceReceiveServiceImpl!receiveOrHand");
        try {
            Long[] idss = invoiceReceiveUpdateDTO.getIdss();
            String userId = invoiceReceiveUpdateDTO.getUserId();
            long invoiceId = idss[0];
                Invoice invoice = new Invoice();
                invoice.setId(invoiceId);
                invoice.setStatus(DataBaseConstant.INVOICE_ALREADY_BAND);
                invoice.setUpdatedTime(DateUtil.getCurrentDate());
                invoice.setHandInvoicer(userId);
                invoiceDAO.updateByPrimaryKeySelective(invoice);
        } catch (Exception e) { 
            this.logger.error(e.getMessage());
            logger.error(e.getStackTrace());
            throw new BizServiceException("交票操作失败!!");
        }
    }
	
	/**
	 * 
	 * 功能描述: 查询结算单<br>
	 * 查询开票发票的结算单信息
	 * 返回已匹配的结算单号
	 *
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public List<Long> querySettlement(InvoiceReceiveUpdateDTO invoiceReceiveUpdateDTO ) 
	        throws BizServiceException{
	    try {   
	        logger.info("查询结算单信息");
	        return settlementInvoiceDao.selectSettlement("SETTLEMENT_INVOICE.querySettlement", invoiceReceiveUpdateDTO);
        } catch (Exception e) { 
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询结算单失败!");
        }
	   
	}
	/**
	 * 
	 * 功能描述: 查询结算单下发票<br>
	 * 〈查询结算单下所有发票是否都已完成开票,返回true表示发票都完成开票，false表示为全部完成〉
	 *
	 * @param settlementS
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public boolean checkInvoiceAll(long id) throws BizServiceException{
	    try {
	      logger.info("检查结算单关联发票开票状态");
	      int i = settlementInvoiceDao.checkInvoiceAll("SETTLEMENT_INVOICE.checkInvoiceAll", id);
	      if(i>0){
	         return false; 
	      }else{
	          return true;
	      }
	    } catch (Exception e) { 
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询结算单失败!");
        }
	}
	/**
	 * 
	 * 功能描述:结算单开票<br>
	 * 〈 修改结算单状态为已开票〉
	 *
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public void settlementReceive(long id) throws BizServiceException{
	    try {
	        logger.info("进入结算单开票方法");
    	    boolean flag = this.checkInvoiceAll(id);
    	    if(flag){
    	        logger.info("修改结算单状态为已开票");
    	        Settlement settlement = new Settlement();
    	        settlement.setId(id);
    	        settlement.setStatus(SettlementConstants.SETTLEMENT_STATUS_TWO);
    	        settlement.setUpdatedTime(DateUtil.getCurrentDate());
    	        settlementDAO.updateByPrimaryKeySelective(settlement);
    	        logger.info("修改结算单状态为已开票完成");
    	    }
	    } catch (Exception e) { 
            this.logger.error(e.getMessage());
            throw new BizServiceException("修改结算单状态失败!");
        }
	}
	/**
	 * 
	 * 功能描述: 插入记账信息<br>
	 * 〈结算单开票完成，插入信息到记账信息表〉
	 * @throws BizServiceException 
	 *
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public void insertSapInfo(long id) throws BizServiceException{
	    try {
            logger.info("进入插入记账信息方法");
            //查询出结算单信息
            Settlement settlement = settlementDAO.selectByPrimaryKey(id);
            if(settlement==null){
                this.logger.error("获取结算单信息失败");
                throw new BizServiceException("获取结算单信息失败!");  
            }
            //组装记账信息
            SapInfo sapInfo = this.getSapInfo(settlement);
            //插入记账信息
            if(sapInfo!=null){
                    logger.info("开始插入SAPInfo数据！");
                    sapInfoDAO.insert(sapInfo);
                }
            logger.info("结束插入SAPInfo数据！"); 
        } catch (Exception e) { 
            this.logger.error(e.getMessage());
            throw new BizServiceException("记账信息插入失败!");
        }
	}
	
	/**
	 * 
	 * 封装记账信息 <br>
	 * 〈功能详细描述〉
	 * @throws BizServiceException 
	 *
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public SapInfo getSapInfo(Settlement settlement) throws BizServiceException{
	    try {
	    SapInfo sapInfo = new SapInfo();
	    
        sapInfo.setTransSeq(SerialNumberUtil.getSequence(SequenceContansts.SEQ_TRANS_SSN).toString().trim());
        sapInfo.setTransType(SapInfoConstants.TRANS_TYPE);
        sapInfo.setPayment(SapInfoConstants.PAYMENT);
        sapInfo.setServiceType(SapInfoConstants.SERVICE_TYPE);
        // 05+settlement.getCreatedTime().toString().trim()+settlement.id
        sapInfo.setDocNo(SapInfoConstants.SERVICE_TYPE+DateUtil.date2String(settlement.getCreatedTime())
                +settlement.getId().toString().trim()); 
        sapInfo.setCurrencyCode(SapInfoConstants.CURRENCY_CODE);
        sapInfo.setPaymtType(SapInfoConstants.PAYMT_TYPE);
        sapInfo.setPaymtFlag(SapInfoConstants.PAYMT_FLAG);
        sapInfo.setPubPvtIntFlag(SapInfoConstants.PUB_PVT_INT_FLAG);
        sapInfo.setReAmount(SapInfoConstants.RE_AMOUNT);
        String flag = null;
        String mchtEntityId = settlement.getMchtEntityId();
        if(mchtEntityId.length()>2){
            flag = mchtEntityId.substring(0, 1);
        }
        if(flag!=null&&"RE".equals(flag)){
            sapInfo.setServiceSubtype("05001");
            sapInfo.setPubPvtIntFlag("03");
        }else{
            sapInfo.setServiceSubtype("05002");
            sapInfo.setPubPvtIntFlag("01");
        }
        sapInfo.setVendor(mchtEntityId);
        sapInfo.setSaleCompany(settlement.getFatherEntityId());
        if(settlement.getTotalAmount()!=0&&settlement.getTotalAmount()!=null){
            Long amount = settlement.getTotalAmount();
            String amountMoneyS=String.valueOf(amount);
            Double amountMoneyD=Double.valueOf(amountMoneyS)/100;
            sapInfo.setAmount(String.valueOf(amountMoneyD));
        }
        if(settlement.getCommissionAmount()!=0&&settlement.getCommissionAmount()!=null){
            Long rateAmount = settlement.getCommissionAmount();
            String amountMoneyS=String.valueOf(rateAmount);
            Double amountMoneyD=Double.valueOf(amountMoneyS)/100;
            sapInfo.setAmount(String.valueOf(amountMoneyD));
        }
        sapInfo.setTransDt(DateUtil.date2String(settlement.getCreatedTime()));
        sapInfo.setTransCompany(settlement.getFatherEntityId());
        Date date=DateUtil.getCurrentDateAndTime();
        sapInfo.setCreatedTime(date);
        sapInfo.setUpdatedTime(date);
	    return sapInfo;
	    } catch (Exception e) { 
            this.logger.error(e.getMessage());
            throw new BizServiceException("封装记账信息失败!");
        }
	}

	public InvoiceDAO getInvoiceDAO() {
		return invoiceDAO;
	}

	public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
		this.invoiceDAO = invoiceDAO;
	}
	public PageQueryDAO getPageQueryDAO() {
        return pageQueryDAO;
    }

    public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
        this.pageQueryDAO = pageQueryDAO;
    }
    public SettlementInvoiceDao getSettlementInvoiceDao() {
        return settlementInvoiceDao;
    }

    public void setSettlementInvoiceDao(SettlementInvoiceDao settlementInvoiceDao) {
        this.settlementInvoiceDao = settlementInvoiceDao;
    }
    public SettlementDAO getSettlementDAO() {
        return settlementDAO;
    }

    public void setSettlementDAO(SettlementDAO settlementDAO) {
        this.settlementDAO = settlementDAO;
    }
    public SapInfoDAO getSapInfoDAO() {
        return sapInfoDAO;
    }

    public void setSapInfoDAO(SapInfoDAO sapInfoDAO) {
        this.sapInfoDAO = sapInfoDAO;
    }
    
}
