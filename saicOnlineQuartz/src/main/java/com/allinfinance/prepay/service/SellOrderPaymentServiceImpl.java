package com.allinfinance.prepay.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderPaymentMapper;
import com.allinfinance.prepay.model.SellOrder;
import com.allinfinance.prepay.model.SellOrderExample;
import com.allinfinance.prepay.model.SellOrderPayment;
import com.allinfinance.prepay.model.SellOrderPaymentExample;
import com.allinfinance.prepay.utils.DataBaseConstant;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.OrderConst;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.univer.seller.order.dto.SellOrderPaymentDTO;

@Service
public class SellOrderPaymentServiceImpl implements SellOrderPaymentService {

	Logger logger = Logger.getLogger(SellOrderPaymentServiceImpl.class);
	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private SellOrderPaymentMapper sellOrderPaymentMapper;
	@Autowired
	private SellOrderMapper sellOrderMapper;
	@Override
	public void insertOrderPayment(SellOrderPaymentDTO dto)
			throws BizServiceException {
		  try {
	            SellOrderPayment tempOrderPayment = new SellOrderPayment();
	            ReflectionUtil.copyProperties(dto, tempOrderPayment);
	            tempOrderPayment.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
	            tempOrderPayment.setCreateTime(DateUtil.getCurrentTime());
	            tempOrderPayment.setCreateUser(dto.getLoginUserId());
	            BigDecimal amount = new BigDecimal(dto.getPaymentAmount());
	            BigDecimal tempValue = new BigDecimal("100");
	            String tempValueStr = amount.multiply(tempValue).toBigInteger().toString();
	            tempOrderPayment.setPaymentAmount(tempValueStr);
	            tempOrderPayment.setModifyTime(tempOrderPayment.getCreateTime());
	            tempOrderPayment.setModifyUser(dto.getLoginUserId());
	            tempOrderPayment.setPaymentId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER_PAYMENT"));
	            sellOrderPaymentMapper.insert(tempOrderPayment);
	            this.countAmountForPayment(dto.getOrderId());
	        } catch (Exception e) {
	            this.logger.error(e.getMessage());
	            throw new BizServiceException("添加付款方式失败");
	        }
	}
	
	public void countAmountForPayment(String orderId)
			throws BizServiceException {
		SellOrderPaymentExample example = new SellOrderPaymentExample();
		example.createCriteria().andOrderIdEqualTo(orderId)
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
		List<SellOrderPayment> orderPaymentList = sellOrderPaymentMapper
				.selectByExample(example);
		BigDecimal totalAmount = new BigDecimal("0");
		BigDecimal tempDecimal = null;
		for (SellOrderPayment tempPayment : orderPaymentList) {
			tempDecimal = new BigDecimal(tempPayment.getPaymentAmount());
			totalAmount = totalAmount.add(tempDecimal);
		}
		SellOrder order = new SellOrder();
		/*order.setOrderId(orderId);*/
		SellOrderExample orderEx=new SellOrderExample();
		orderEx.createCriteria().andOrderIdEqualTo(orderId);
		order.setPaymentAmount(totalAmount.toString());
		sellOrderMapper.updateByExampleSelective(order, orderEx);
	}

}
