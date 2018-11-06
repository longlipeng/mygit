package com.huateng.framework.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

import com.allinfinance.univer.system.dictinfo.dto.DictInfoDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.SystemInfo;

public class PaymentTermTag extends BodyTagSupport{
	
	private Logger logger = Logger.getLogger(PaymentTermTag.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//支付方式
	private String paymentTerm;
	
	//订单类型
	private String orderType;

	@Override
	public int doStartTag() throws JspException {
		String strHtml="";
		try {
			if(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER.equals(orderType)){
				 if("3".equals(paymentTerm.trim())){
					 strHtml = "充值前";
				 }else{
					 strHtml = getDictName("207",paymentTerm);
				 }
			}else{
				strHtml = getDictName("207",paymentTerm);
			}
			JspWriter writer = pageContext.getOut();

			try {
				writer.print(strHtml);
			} catch (IOException e) {
				this.logger.error(e.getMessage());
				throw new JspException(e.toString());
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new JspException(e.toString());
		}
		return BodyTagSupport.EVAL_BODY_TAG;
	}
	
	/**
	 * 根据字典类型和字典值，取字典用来显示的名称
	 * 
	 * @return
	 * @throws BizServiceException
	 */
	private String getDictName(String dictType , String dictValue) throws BizServiceException {
		String dictName = "";

		Map<?, ?> map = SystemInfo.getDictInfo();
		List<?> lstDictInfo = (List<?>) map.get(dictType);
		for (int i = 0; i < lstDictInfo.size(); i++) {
			DictInfoDTO dictInfoDTO = (DictInfoDTO) lstDictInfo.get(i);
			String dictCode = dictInfoDTO.getDictCode();
			if (dictValue.equals(dictCode)) {
				dictName = dictInfoDTO.getDictShortName();
				break;
			}
		}
		return dictName;

	}

	@Override
	public int doEndTag() throws JspException {
		return BodyTagSupport.EVAL_BODY_TAG;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
}
