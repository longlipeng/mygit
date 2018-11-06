package com.huateng.univer.seller.sellorder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.extremecomponents.table.core.TableConstants;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageDTO;
import com.allinfinance.univer.issuer.dto.product.ProductCardBinDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.invoice.dto.OrderInvoiceInfoDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDownLoadDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderPaymentDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.util.DateUtil;


public class OrderQueryAction extends OrderBaseAction{
	private Logger logger = Logger.getLogger(OrderQueryAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<BankDTO> lstBankDTOs = new ArrayList<BankDTO>();
	private String initFlag="0";
    private OrderInvoiceInfoDTO orderInvoiceInfoDTO;
    private String errorInfo;
    private String fileName;
    private ProductCardBinDTO productCardBinDTO;
    
    public ProductCardBinDTO getProductCardBinDTO() {
		return productCardBinDTO;
	}
	public void setProductCardBinDTO(ProductCardBinDTO productCardBinDTO) {
		this.productCardBinDTO = productCardBinDTO;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private SellOrderDownLoadDTO sellOrderDownLoadDTO = new SellOrderDownLoadDTO();
	public String getErrorInfo() {
		return errorInfo;
	}
	public SellOrderDownLoadDTO getSellOrderDownLoadDTO() {
		return sellOrderDownLoadDTO;
	}
	public void setSellOrderDownLoadDTO(SellOrderDownLoadDTO sellOrderDownLoadDTO) {
		this.sellOrderDownLoadDTO = sellOrderDownLoadDTO;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public OrderInvoiceInfoDTO getOrderInvoiceInfoDTO() {
		return orderInvoiceInfoDTO;
	}
	public void setOrderInvoiceInfoDTO(OrderInvoiceInfoDTO orderInvoiceInfoDTO) {
		this.orderInvoiceInfoDTO = orderInvoiceInfoDTO;
	}
	public String getInitFlag() {
		return initFlag;
	}
		@Override
	protected void addCondition() {
		
		
	}

	public void setInitFlag(String initFlag) {
		this.initFlag = initFlag;
	}

	@Override
	protected void dealWithSellOrderInputDTO() { 
	}

	@Override
	public String edit() {
		
		return null;
	}
	
	protected void initInput() {
		productDTOs = sellOrderInputDTO.getProductDTOs();
		ProductDTO productDTO = sellOrderInputDTO.getProductDTO();
		sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
		// sellOrderDTO.setProcessEntityName(getUser().getSellerName());
		customerDTO = sellOrderInputDTO.getCustomerDTO();
		if (productDTO != null && productDTO.getProductId() != null
				&& !"".equals(productDTO.getProductId())) {
			sellOrderDTO.setServiceName(productDTO.getServices().get(0).getServiceName());
			sellOrderDTO.setServiceId(productDTO.getServices().get(0).getServiceId());
			sellOrderDTO.setPackageId(productDTO.getPackages().get(0).getPackageId());
		}
	}
	
	public String unsignInit(){
		try {
			sellOrderDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN);
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
			sellOrderInputDTO =  (SellOrderInputDTO) sendService("2016082901", sellOrderInputDTO).getDetailvo();
			this.initInput();
			if(null!=sellOrderInputDTO.getProductStocks()){
				this.getRequest().setAttribute("list",
						sellOrderInputDTO.getProductStocks().getData());
				this.getRequest().setAttribute(TableConstants.TOTAL_ROWS,
						sellOrderInputDTO.getProductStocks().getTotalRecord());
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		
		return "unsignInit";
	}
	
	public String initUnsignOrder(){
		return "initUnsignOrder";
	}

	/***
	 * 查询条件init
	 */
	@Override
	protected void init() {
		actionName= "orderQueryAction";
		actionMethodName="view";
	}

	@Override
	protected void initOrderType() {
		
		
	}
	
	
	public String query(){
		try{
			 ListPageInit("sellOrder", sellOrderQueryDTO);
			 /***
			  * 按订单ID的倒序排序
			  */
			 if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
				 sellOrderQueryDTO.setSort("desc");
				 sellOrderQueryDTO.setSortFieldName("orderId");
			 }
			 
			 boolean isDateStartEmptyFlag = false;
			 boolean isDateEndEmptyFlag = false;
			 if (sellOrderQueryDTO.getOrderDateStart() == null || "".equals(sellOrderQueryDTO.getOrderDateStart())){
				 isDateStartEmptyFlag = true;
				 sellOrderQueryDTO.setOrderDateStart(DateUtil.dateToSting_(DateUtils.addMonths(new Date(), -3)));
			 }
			 if (sellOrderQueryDTO.getOrderDateEnd() == null || "".equals(sellOrderQueryDTO.getOrderDateEnd())){
				 isDateEndEmptyFlag = true;
				 sellOrderQueryDTO.setOrderDateEnd(DateUtil.dateToSting_(new Date()));
			 }
			//当订单类型为充值订单 并且 支付方式为1时  支付方式为充值前
				if(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER.equals(sellOrderQueryDTO.getOrderTypeArray()) && "1".equals(sellOrderQueryDTO.getPaymentTerm().trim())){
					sellOrderQueryDTO.setPaymentTerm("3");
				}else if(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER.equals(sellOrderQueryDTO.getOrderTypeArray()) && "3".equals(sellOrderQueryDTO.getPaymentTerm().trim())){
					//当订单类型为充值订单 并且 支付方式为3时  配送前  没有此支付方式 查不出来记录
					sellOrderQueryDTO.setPaymentTerm("1");
				}
			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.ORDER_INQUERY_BY_SELLER,
					  sellOrderQueryDTO).getDetailvo();
			 sellOrders = result.getData();
			 sellOrder_totalRows = result.getTotalRecord();
			 if(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER.equals(sellOrderQueryDTO.getOrderTypeArray()) && "3".equals(sellOrderQueryDTO.getPaymentTerm().trim())){
					sellOrderQueryDTO.setPaymentTerm("1");
				}else if(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER.equals(sellOrderQueryDTO.getOrderTypeArray()) && "1".equals(sellOrderQueryDTO.getPaymentTerm().trim())){
					//当订单类型为充值订单 并且 支付方式为3时  配送前  没有此支付方式 查不出来记录
					sellOrderQueryDTO.setPaymentTerm("3");
				}
			 if (isDateStartEmptyFlag && isDateEndEmptyFlag) {
				 sellOrderQueryDTO.setOrderDateStart(null);
				 sellOrderQueryDTO.setOrderDateEnd(null);
			 }
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "list";
	}
	//查询外地发卡
	public String queryOther(){
		try{
			 ListPageInit("sellOrder", sellOrderQueryDTO);
			 /***
			  * 按订单ID的倒序排序
			  */
			 if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
				 sellOrderQueryDTO.setSort("desc");
				 sellOrderQueryDTO.setSortFieldName("orderId");
			 }
			 boolean isDateStartEmptyFlag = false;
			 boolean isDateEndEmptyFlag = false;
			 if (sellOrderQueryDTO.getOrderDateStart() == null || "".equals(sellOrderQueryDTO.getOrderDateStart())){
				 isDateStartEmptyFlag = true;
				 sellOrderQueryDTO.setOrderDateStart(DateUtil.dateToSting_(DateUtils.addMonths(new Date(), -3)));
			 }
			 if (sellOrderQueryDTO.getOrderDateEnd() == null|| "".equals(sellOrderQueryDTO.getOrderDateEnd())){
				 isDateEndEmptyFlag = true;
				 sellOrderQueryDTO.setOrderDateEnd(DateUtil.dateToSting_(new Date()));
			 }
			
			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.ORDER_INQUERY_BY_SELLER_OTHER,
					  sellOrderQueryDTO).getDetailvo();
			 sellOrders = result.getData();
			 sellOrder_totalRows = result.getTotalRecord();
			 if (isDateStartEmptyFlag && isDateEndEmptyFlag) {
				 sellOrderQueryDTO.setOrderDateStart(null);
				 sellOrderQueryDTO.setOrderDateEnd(null);
			 }
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "otherList";
	}
	
	public String downloadInfo(){
		String startDate = sellOrderQueryDTO.getOrderDateStart();
		String endDate = sellOrderQueryDTO.getOrderDateEnd();
		String startDateTemp = "";
		String endDateTemp = "";
		if (!StringUtils.isEmpty(getRequest().getParameter("downloadFlg"))){
			//全部下载
			if (!StringUtils.isEmpty(startDate)) {
				startDateTemp = startDate.replaceAll("-", "");
				sellOrderDownLoadDTO.setStartDate(startDateTemp);
			}
			if (!StringUtils.isEmpty(endDate)) {
				endDateTemp = endDate.replaceAll("-", "");
				sellOrderDownLoadDTO.setEndDate(endDateTemp);
			}
			fileName = startDateTemp + "-" + endDateTemp;
		} else {
			sellOrderDownLoadDTO.setOrderId(sellOrderDTO.getOrderId());
			fileName = sellOrderDTO.getOrderId() + "-" + DateUtil.getCurrentDateFormatStr("yyyyMMddhhmmss");
		}
		// 只有配送成功且未激活的才可以下载
		sellOrderDownLoadDTO.setOrderState(OrderConst.ORDER_STATE_ORDER_SEND_CONFIRM);
		sellOrderDownLoadDTO.setInitActStat("0");
		sellOrderDownLoadDTO.setOrderType(sellOrderDTO.getOrderType());
		sellOrderDownLoadDTO.setDeliveryMeans("1");
		sellOrderDownLoadDTO = (SellOrderDownLoadDTO) this.sendService(ConstCode.DOWNLOAD_MAILING_INFO, sellOrderDownLoadDTO).getDetailvo();
		
		if (hasErrors()) {
			return this.list();
		}
		return "downloadInfo";
	}
	/**
	 * 下载邮寄信息文件
	 * @throws Exception
	 */
	public void saveFileZip() throws Exception {
		HttpServletResponse response = getResponse();
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		InputStream is = null;
		try {
			for (Entry<String, List<byte[]>> entry : sellOrderDownLoadDTO
					.getFileData().entrySet()) {
				List<byte[]> byteList = entry.getValue();
				for (byte[] byteArr : byteList) {
					is = new ByteArrayInputStream(byteArr);
				}
			}
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
	public String queryActivate(){
		try{
			
			 ListPageInit("sellOrder", sellOrderQueryDTO);
			 /***
			  * 按订单ID的倒序排序
			  */
			 if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
				 sellOrderQueryDTO.setSort("desc");
				 sellOrderQueryDTO.setSortFieldName("orderId");
				 sellOrderQueryDTO.setValidFlag("valid");
			 }
			 
			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.ORDER_INQUERY_ACTIVATE,
					  sellOrderQueryDTO).getDetailvo();
			 sellOrders = result.getData();
			 sellOrder_totalRows = result.getTotalRecord();
			 
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "list1";
	}
	
    /**
     * 
     * 功能描述: <br>
     * 查询非现金、银行的待激活订单
     */
    public String queryActivateEx() {
        try {

            ListPageInit("sellOrder", sellOrderQueryDTO);
            /***
             * 按订单ID的倒序排序
             */
            if (isEmpty(sellOrderQueryDTO.getSortFieldName())) {
                sellOrderQueryDTO.setSort("desc");
                sellOrderQueryDTO.setSortFieldName("orderId");
                sellOrderQueryDTO.setValidFlag("valid");
            }

            PageDataDTO result = (PageDataDTO) sendService(ConstCode.ORDER_INQUERY_ACTIVATEEX, sellOrderQueryDTO)
                    .getDetailvo();
            sellOrders = result.getData();
            sellOrder_totalRows = result.getTotalRecord();

        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "list2";
    }	
    
	/**
	 * 打印购卡、换卡订单凭证
	 * @author Yifeng.Shi
	 * @since 2012-2-6
	 * 
	 * */
	public String queryForCertPrint(){
		sellOrderDTO=(SellOrderDTO)sendService(ConstCode.ORDER_INQUERY_PRINT_CERTIFICATE,
				  sellOrderDTO).getDetailvo();
		if (hasErrors()) {
			return query();
		}
		if(sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD)){
			return "changeCardOrderPrint";
		}else if(sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_RANSOM)){
			return "ransomOrderPrint";
		}else if(sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER)){
			return "creditOrderPrint";
		}else if(sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN)
				|| sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN)
				|| sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN)
		){
			return "orderPrintUnsign";
		}else if(sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN)
				|| sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN)
				|| sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN)
		){
			return "sellOrderPrint";
		}
    return "";
	}
	
	/**
	 * 查询订单付款明细
	 * */
	
	public String queryForPaymentPrint(){
		String entityId=sellOrderDTO.getDefaultEntityId();
		sellOrderDTO=(SellOrderDTO)sendService(ConstCode.SELL_ORDER_INQUERY_PRINT_PAYMENT,
				  sellOrderDTO).getDetailvo();
		if(hasErrors()){
			sellOrderQueryDTO.setDefaultEntityId(entityId);
			return query();
		}
		return "orderPaymentPrint";
	}
	
	/**
	 * 打印订单出入库凭证查询
	 * */
	public String queryForOrderStockPrint(){
		logger.debug("enter queryForOrderStockPrint");
		sellOrderDTO=(SellOrderDTO)sendService(ConstCode.SELL_ORDER_INQUERY_PRINT_STOCK,
				  sellOrderDTO).getDetailvo();
		if (hasErrors()) {
			return query();
		}
		init();
		actionMethodName="query";
		logger.debug("out queryForOrderStockPrint");
		if(sellOrderDTO.getStockCertFlag().equals("entStock")){
			return "stockPrintEnt";
		}else if(sellOrderDTO.getStockCertFlag().equals("outStock")){
			return "stockPrintOut";
		}
		return "";
	}
	
	 /**
     * 基础岗位跳转到订单发票录入
     */
	@SuppressWarnings("unchecked")
    public String addOrderInvoiceBasicPositionInit() {
        String orderType = sellOrderDTO.getOrderType();
        String paymentState= sellOrderDTO.getPaymentState();
        if(OrderConst.ORDER_PAY_STATE_PAID.equals(paymentState)) {
            if((OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN.equals(orderType)
                || OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN.equals(orderType) 
                || OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN.equals(orderType) 
                || OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN.equals(orderType) 
                || OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN.equals(orderType)
                || OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN.equals(orderType))
                || OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER.equals(orderType)
                || OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD.equals(orderType)) {
                List<SellOrderPaymentDTO> list = (List<SellOrderPaymentDTO>) sendService(ConstCode.QUERY_ORDER_PAYMENTS,
                        sellOrderDTO.getOrderId()).getDetailvo();
                for(SellOrderPaymentDTO temp : list){
                    if(temp.getOrderState() != null 
                            && temp.getPaymentState() != null
                                    && temp.getOrderState().equals(OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL) 
                                            && temp.getPaymentState().equals(OrderConst.ORDER_PAY_STATE_PAID)){
                        addActionError("已支付但处理失败的订单不能开票");
                        return query();
                    }
                }
                if(list.size() == 0) {
                    addActionError("此订单未付款确认审核");
                    return query();
                }
                //订单付款审核时间
                String modifyTime = list.get(0).getModifyTime();
                if(StringUtils.isBlank(modifyTime)) {
                    addActionError("此订单未付款确认审核");
                    return query();
                }
                Date mDate = DateUtil.string2Dateyyyymmdd(modifyTime.substring(0, 8));
                Date threeDate = DateUtil.addDay(mDate, 3);
                Date todayDate = DateUtil.getCurrentDate();
                if(todayDate.compareTo(threeDate) < 0) {     
                }
                else {
                    addActionError("已超出可开票期限");
                    return query();
                }
            }
            else {
                addActionError("已付款的销售订单、充值订单、换卡订单才允许进行发票信息录入");
                return query();
            }
        }
        else {
            addActionError("已付款的销售订单、充值订单、换卡订单才允许进行发票信息录入");
            return query();
        }
        return addOrderInvoiceInit();
    }
	
	/**
     * 管理岗位跳转到订单发票录入
     */
    @SuppressWarnings("unchecked")
    public String addOrderInvoiceManagemetPositionInit(){
        String orderType = sellOrderDTO.getOrderType();
        String paymentState= sellOrderDTO.getPaymentState();
        if(OrderConst.ORDER_PAY_STATE_PAID.equals(paymentState)) {
            if((OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN.equals(orderType)
                || OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN.equals(orderType) 
                || OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN.equals(orderType) 
                || OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN.equals(orderType) 
                || OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN.equals(orderType)
                || OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN.equals(orderType))
                || OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER.equals(orderType)
                || OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD.equals(orderType)) {
                List<SellOrderPaymentDTO> list = (List<SellOrderPaymentDTO>) sendService(ConstCode.QUERY_ORDER_PAYMENTS,
                        sellOrderDTO.getOrderId()).getDetailvo();
                for(SellOrderPaymentDTO temp : list){
                    if(temp.getOrderState() != null 
                            && temp.getPaymentState() != null
                                    && temp.getOrderState().equals(OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL) 
                                            && temp.getPaymentState().equals(OrderConst.ORDER_PAY_STATE_PAID)){
                        addActionError("已支付但处理失败的订单不能开票");
                        return query();
                    }
                }
                if(list.size() == 0) {
                    addActionError("此订单未付款确认审核");
                    return query();
                }
                //订单付款审核时间
                String modifyTime = list.get(0).getModifyTime();
                if(StringUtils.isBlank(modifyTime)) {
                    addActionError("此订单未付款确认审核");
                    return query();
                }  
            }
            else {
                addActionError("已付款的销售订单、充值订单、换卡订单才允许进行发票信息录入");
                return query();
            }
        }
        else {
            addActionError("已付款的销售订单、充值订单、换卡订单才允许进行发票信息录入");
            return query();
        }        
        return addOrderInvoiceInit();
    }
	
	/**
	 * 跳转到订单发票录入
	 */
	public String addOrderInvoiceInit(){
		logger.debug("addOrderInvoiceInit");
		orderInvoiceInfoDTO=(OrderInvoiceInfoDTO)sendService(ConstCode.SELL_ORDER_INVOICE_ADD_INIT,
				  sellOrderDTO).getDetailvo();
		if (this.hasErrors()) {
			return query();
		}
		if(errorInfo != null){
			addActionError(errorInfo);
		}
		orderInvoiceInfoDTO.setUserName(getUser().getUsername());
		init();
		actionMethodName="addOrderInvoice";
		logger.debug("out addOrderInvoice");
		return "addOrderInvoice";
	}
	
	
	/**
	 * 录入订单发票
	 */
	public String addOrderInvoice(){
		logger.debug("addOrderInvoice");
		orderInvoiceInfoDTO.setUserId(getUser().getUserId());
		orderInvoiceInfoDTO.setLoginUserId(getUser().getUserId());
		String orderId = orderInvoiceInfoDTO.getOrderId();
		sellOrderDTO.setOrderId(orderId);
		OrderInvoiceInfoDTO dto=(OrderInvoiceInfoDTO)sendService(ConstCode.SELL_ORDER_INVOICE_ADD_INIT,
                sellOrderDTO).getDetailvo();
		if(!orderInvoiceInfoDTO.getOrderId().equals(dto.getOrderId())) {
            addActionError("订单号不允许被修改");
            return query();
        }
		if(!orderInvoiceInfoDTO.getTotalPrice().equals(dto.getTotalPrice())) {
		    addActionError("总金额不允许被修改");
            return query();
		}
		orderInvoiceInfoDTO=(OrderInvoiceInfoDTO)sendService(ConstCode.SELL_ORDER_INVOICE_ADD,
				orderInvoiceInfoDTO).getDetailvo();
		if (this.hasErrors()) {
			sellOrderDTO.setOrderId(orderId);
			errorInfo = getActionErrors().toString();
			errorInfo = errorInfo.substring(1, errorInfo.length() - 1);
			clearActionErrors();
			return addOrderInvoiceInit();
		}
		return query();
	}
	
	public void validateAddOrderInvoice(){
		if(logger.isDebugEnabled()){
			logger.debug(this.getFieldErrors());
		}
		if(this.hasFieldErrors()){
			query();
		}
	}
	
	public void setLstBankDTOs(List<BankDTO> lstBankDTOs) {
		this.lstBankDTOs = lstBankDTOs;
	}

	public List<BankDTO> getLstBankDTOs() {
		return lstBankDTOs;
	}



}
