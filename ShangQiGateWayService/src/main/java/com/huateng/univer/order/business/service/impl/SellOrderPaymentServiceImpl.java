package com.huateng.univer.order.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderPaymentDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderPaymentQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderPaymentDAO;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderExample;
import com.huateng.framework.ibatis.model.SellOrderPayment;
import com.huateng.framework.ibatis.model.SellOrderPaymentExample;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.entitybaseinfo.bank.biz.service.BankService;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.order.business.bo.OrderCountTotalPrice;
import com.huateng.univer.order.business.service.SellOrderPaymentService;
import com.huateng.univer.order.business.service.SubmitOrderService;

public class SellOrderPaymentServiceImpl implements SellOrderPaymentService {
    Logger logger = Logger.getLogger(SellOrderPaymentServiceImpl.class);
    private PageQueryDAO pageQueryDAO;
    private SellOrderPaymentDAO sellOrderPaymentDAO;
    private CommonsDAO commonsDAO;
    private OrderBaseQueryBO orderBaseQueryBO;
    private OrderCountTotalPrice orderCountTotalPrice;
    private SellOrderDAO sellOrderDAO;
    private BaseDAO baseDAO;
    private SubmitOrderService submitOrderService;
    private BankService bankService;

    public PageDataDTO queryPage(SellOrderPaymentQueryDTO dto) throws BizServiceException {
        try {
            return pageQueryDAO.query("TB_SELL_ORDER_PAYMENT.orderPaymentPageData", dto);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("订单付款方式查询失败");
        }
    }

    public void insertOrderPayment(SellOrderPaymentDTO dto) throws BizServiceException {
        try {
            SellOrderPayment tempOrderPayment = new SellOrderPayment();
            ReflectionUtil.copyProperties(dto, tempOrderPayment);
            tempOrderPayment.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
            tempOrderPayment.setCreateTime(DateUtil.getCurrentTime());
            tempOrderPayment.setCreateUser(dto.getLoginUserId());
            BigDecimal amount = new BigDecimal(dto.getPaymentAmount());
            BigDecimal tempValue = new BigDecimal("100");
            SellOrder order = sellOrderDAO.selectByPrimaryKey(dto.getOrderId());
            String orderType = order.getOrderType();
            String tempValueStr = amount.multiply(tempValue).toBigInteger().toString();
            if (OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(orderType)) {
                tempValueStr = "-" + tempValueStr;
            }
            tempOrderPayment.setPaymentAmount(tempValueStr);
            tempOrderPayment.setModifyTime(tempOrderPayment.getCreateTime());
            tempOrderPayment.setModifyUser(dto.getLoginUserId());
            tempOrderPayment.setPaymentId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER_PAYMENT"));
            sellOrderPaymentDAO.insert(tempOrderPayment);
            orderCountTotalPrice.countAmountForPayment(dto.getOrderId());
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("添加付款方式失败");
        }
    }

    public void delete(SellOrderPaymentDTO dto) throws Exception {
        try {
            String[] paymentIds = dto.getPaymentId().split(",");
            for (String paymentId : paymentIds) {
                SellOrderPayment tempPayment = new SellOrderPayment();
                tempPayment.setModifyTime(DateUtil.getCurrentTime());
                tempPayment.setModifyUser(dto.getLoginUserId());
                tempPayment.setDataState("0");
                SellOrderPaymentExample example = new SellOrderPaymentExample();
                example.createCriteria().andOrderIdEqualTo(paymentId).andDataStateEqualTo("1");
                sellOrderPaymentDAO.updateByExampleSelective(tempPayment, example);
            }
        } catch (Exception e) {

            this.logger.error(e.getMessage());
        }

    }

    public void deleteOrderPayment(SellOrderPaymentDTO dto) throws BizServiceException {
        try {

            List<SellOrderPayment> orderPayments = new ArrayList<SellOrderPayment>();
            String[] paymentIds = dto.getPaymentId().split(",");
            SellOrderPayment tempPayment;
            for (String paymentId : paymentIds) {
                tempPayment = new SellOrderPayment();
                tempPayment.setOrderId(dto.getOrderId());
                tempPayment.setPaymentId(paymentId.replace(" ", ""));
                tempPayment.setModifyTime(DateUtil.getCurrentTime());
                tempPayment.setModifyUser(dto.getLoginUserId());
                tempPayment.setDataState(DataBaseConstant.DATA_STATE_DELETE);
                orderPayments.add(tempPayment);
            }
            commonsDAO.batchUpdate("TB_SELL_ORDER_PAYMENT.abatorgenerated_updateByPrimaryKeySelective", orderPayments);
            orderCountTotalPrice.countAmountForPayment(dto.getOrderId());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BizServiceException("删除付款方式失败");
        }
    }

    public SellOrderDTO queryOrderForPaymentPrint(SellOrderDTO dto) throws BizServiceException {
        SellOrderDTO resultDTO = new SellOrderDTO();
        try {
            checkPaymentPrint(dto.getOrderId());
            List<SellOrderPaymentDTO> orderPaymentDTOList = new ArrayList<SellOrderPaymentDTO>();
            SellOrderPaymentDTO tempDTO = null;
            resultDTO = orderBaseQueryBO.getSellOrderForCertPrint(dto.getOrderId());
            /* 充值订单，统计充值总额和充值卡张数 */
            if (dto.getOrderType().equals(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER)) {
                resultDTO.setCreditTotalAmount(orderBaseQueryBO.getCreditTotalAmount(dto.getOrderId()));
            }
            resultDTO.setOrderPaymentDTOList(queryOrderPayments(dto.getOrderId()));
        } catch (BizServiceException bse) {
            throw bse;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BizServiceException("查询订单付款信息失败");
        }
        return resultDTO;
    }

    private void checkPaymentPrint(String orderId) throws BizServiceException {
        try {
            SellOrder operateOrder = orderBaseQueryBO.getSellOrder(orderId);
            String orderType = operateOrder.getOrderType();
            String paymentState = operateOrder.getPaymentState();
            /* 非售卡或充值订单不能做打印 */
            if (!(orderType.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN)
                    || orderType.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN)
                    || orderType.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN)
                    || orderType.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN)
                    || orderType.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN)
                    || orderType.equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN) || orderType
                    .equals(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER))) {
                throw new BizServiceException("只能打印售卡订单或充值订单");
            }
            if (!paymentState.equals(OrderConst.ORDER_PAY_STATE_PAID)) {
                throw new BizServiceException("只能打印付款后的订单");
            }
        } catch (BizServiceException bse) {
            throw bse;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BizServiceException("查询付款打印异常");
        }

    }

    public void confirm(SellOrderPaymentDTO sellOrderPaymentDTO) throws BizServiceException {
        String[] orderIds = sellOrderPaymentDTO.getPaymentId().split(",");
        SellOrderExample example = new SellOrderExample();
        example.createCriteria().andOrderIdIn(new ArrayList<String>(Arrays.asList(orderIds))).andDataStateEqualTo("1");
        example.setOrderByClause("order_Id");
        List<SellOrder> sellOrders = sellOrderDAO.selectByExample(example);
        SellOrderDTO sellOrderDTO = new SellOrderDTO();
        if (null != sellOrders && sellOrders.size() > 0) {
            for (SellOrder sellOrder : sellOrders) {
                sellOrderDTO.setOrderId(sellOrder.getOrderId());
                sellOrderDTO.setLoginUserId(sellOrderPaymentDTO.getLoginUserId());
                sellOrderDTO.setDefaultEntityId(sellOrderPaymentDTO.getDefaultEntityId());
                submitOrderService.confirmAtOrderPayment(sellOrderDTO);
            }
        }
    }

    public void combinePayment(SellOrderPaymentDTO sellOrderPaymentDTO) throws BizServiceException {
        try {
            String[] orderIds = sellOrderPaymentDTO.getPaymentId().split(",");
            SellOrderPaymentExample payexample = new SellOrderPaymentExample();
            payexample.createCriteria().andOrderIdIn(new ArrayList<String>(Arrays.asList(orderIds)))
                    .andDataStateEqualTo("1");
            int count = sellOrderPaymentDAO.countByExample(payexample);
            if (count > 0) {
                throw new BizServiceException("合并付款只能添加单一付款方式！！");
            }
            BigDecimal totalPrice = new BigDecimal(Amount.getDataBaseAmount(sellOrderPaymentDTO.getPaymentAmount()));
            if (totalPrice.subtract(new BigDecimal(Amount.getDataBaseAmount(sellOrderPaymentDTO.getTotalPrice())))
                    .doubleValue() < 0) {
                throw new BizServiceException("付款金额不能小于订单金额！！");
            }
            SellOrderExample example = new SellOrderExample();
            example.createCriteria().andOrderIdIn(new ArrayList<String>(Arrays.asList(orderIds)))
                    .andDataStateEqualTo("1");
            example.setOrderByClause("order_Id");
            List<SellOrder> sellOrders = sellOrderDAO.selectByExample(example);
            List<SellOrderPayment> sellOrderPaymentDTOs = new ArrayList<SellOrderPayment>();
            if (null != sellOrders && sellOrders.size() > 0) {
                for (int i = 0; i < sellOrders.size(); i++) {
                    SellOrder sellOrder = sellOrders.get(i);
                    SellOrderPayment dto = new SellOrderPayment();
                    if (i == sellOrders.size() - 1) {
                        dto.setPaymentAmount(totalPrice.toString());
                        sellOrder.setPaymentAmount(totalPrice.toString());
                    } else {
                        dto.setPaymentAmount(sellOrder.getTotalPrice());
                        sellOrder.setPaymentAmount(sellOrder.getTotalPrice());
                    }
                    sellOrder.setModifyTime(DateUtil.getCurrentTime());
                    sellOrder.setModifyUser(sellOrderPaymentDTO.getLoginUserId());
                    sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
                    // submitOrderService.confirmAtOrderPayment(sellOrderDTO);
                    dto.setPaymentId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER_PAYMENT"));
                    dto.setOrderId(sellOrder.getOrderId());
                    dto.setPaymentType(sellOrderPaymentDTO.getPaymentType());
                    dto.setRemark(sellOrderPaymentDTO.getRemark());
                    dto.setCreateUser(sellOrderPaymentDTO.getLoginUserId());
                    dto.setCreateTime(DateUtil.getCurrentTime());
                    dto.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
                    totalPrice = totalPrice.subtract(new BigDecimal(sellOrder.getTotalPrice()));
                    sellOrderPaymentDTOs.add(dto);
                }
                baseDAO.batchInsert("TB_SELL_ORDER_PAYMENT", "abatorgenerated_insert", sellOrderPaymentDTOs);
            }
        } catch (BizServiceException b) {
            throw b;
        } catch (Exception e) {

            this.logger.error(e.getMessage());
            throw new BizServiceException("添加合并付款信息失败！！");
        }
    }

    /**
     * 合并付款LIST
     */
    public SellOrderDTO combineList(SellOrderDTO sellOrderDTO) throws Exception {
        sellOrderDTO = orderBaseQueryBO.getCombineList(sellOrderDTO);
        return sellOrderDTO;

    }

    public List<SellOrderPaymentDTO> queryOrderPayments(String orderId) {
        SellOrderPaymentExample example = new SellOrderPaymentExample();
        example.createCriteria().andOrderIdEqualTo(orderId).andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
        List<SellOrderPayment> tempList = sellOrderPaymentDAO.selectByExample(example);
        //获得订单状态
        SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(orderId);
        List<SellOrderPaymentDTO> orderPaymentDTOList = new ArrayList<SellOrderPaymentDTO>();
        
        SellOrderPaymentDTO tempDTO = new SellOrderPaymentDTO();
        
        
        for (SellOrderPayment tempPayment : tempList) {
            tempDTO = new SellOrderPaymentDTO();
            ReflectionUtil.copyProperties(tempPayment, tempDTO);
            if (null != tempDTO.getPaymentAmount() && !"".equals(tempDTO.getPaymentAmount().trim())) {
                tempDTO.setPaymentAmount(tempDTO.getPaymentAmount());
            }
            tempDTO.setOrderState(sellOrder.getOrderState());
            tempDTO.setPaymentState(sellOrder.getPaymentState());
            orderPaymentDTOList.add(tempDTO);
        }
        return orderPaymentDTOList;
    }

    public List<BankDTO> queryBankInfoByOrderId(SellOrderDTO sellOrderDTO) throws Exception {
        try {
            List<BankDTO> lstBankDTO = bankService.inquery(sellOrderDTO.getFirstEntityId(), "1");
            return lstBankDTO;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询银行信息失败");
        }
    }

    public CommonsDAO getCommonsDAO() {
        return commonsDAO;
    }

    public void setCommonsDAO(CommonsDAO commonsDAO) {
        this.commonsDAO = commonsDAO;
    }

    public PageQueryDAO getPageQueryDAO() {
        return pageQueryDAO;
    }

    public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
        this.pageQueryDAO = pageQueryDAO;
    }

    public SellOrderPaymentDAO getSellOrderPaymentDAO() {
        return sellOrderPaymentDAO;
    }

    public void setSellOrderPaymentDAO(SellOrderPaymentDAO sellOrderPaymentDAO) {
        this.sellOrderPaymentDAO = sellOrderPaymentDAO;
    }

    public OrderBaseQueryBO getOrderBaseQueryBO() {
        return orderBaseQueryBO;
    }

    public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
        this.orderBaseQueryBO = orderBaseQueryBO;
    }

    public OrderCountTotalPrice getOrderCountTotalPrice() {
        return orderCountTotalPrice;
    }

    public void setOrderCountTotalPrice(OrderCountTotalPrice orderCountTotalPrice) {
        this.orderCountTotalPrice = orderCountTotalPrice;
    }

    public SellOrderDAO getSellOrderDAO() {
        return sellOrderDAO;
    }

    public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
        this.sellOrderDAO = sellOrderDAO;
    }

    public BaseDAO getBaseDAO() {
        return baseDAO;
    }

    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    public SubmitOrderService getSubmitOrderService() {
        return submitOrderService;
    }

    public void setSubmitOrderService(SubmitOrderService submitOrderService) {
        this.submitOrderService = submitOrderService;
    }

    public BankService getBankService() {
        return bankService;
    }

    public void setBankService(BankService bankService) {
        this.bankService = bankService;
    }

}
