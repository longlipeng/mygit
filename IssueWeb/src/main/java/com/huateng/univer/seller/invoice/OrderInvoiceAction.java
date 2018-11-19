/**
 * Classname OrderInvoiceAction.java
 *
 * Version information
 *
 * Licensed Property to HuaTeng Data Co., Ltd.
 * 
 * (C) Copyright of HuaTeng Data Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * Project Info: HuaTeng Data Internet Acquiring  Project
 * 
 * Modification History:
 * =============================================================================
 *		Author		Date		Description
 *   ------------ ---------- ---------------------------------------------------
 *		Administrator		2013-3-25
 * =============================================================================
 */

package com.huateng.univer.seller.invoice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.invoice.dto.OrderInvoiceInfoDTO;
import com.allinfinance.univer.seller.invoice.dto.OrderInvoiceInfoQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.Const;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;

/**
 * 销售类订单发票管理
 * 
 * @author lfr
 * 
 */
@SuppressWarnings("unchecked")
public class OrderInvoiceAction extends BaseAction {
	private Logger logger = Logger.getLogger(OrderInvoiceAction.class);
	private static final long serialVersionUID = 1L;
	private OrderInvoiceInfoQueryDTO orderInvoiceInfoQueryDTO = new OrderInvoiceInfoQueryDTO();
	private	OrderInvoiceInfoDTO orderInvoiceInfoDTO = new OrderInvoiceInfoDTO();
	protected List<?> invoiceInfos = new ArrayList();
	protected int invoiceInfo_totalRows = 0;
	private SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();
	private List<UserDTO> saleManList = new ArrayList();
	
	private List<?> invoiceIdList = new ArrayList();
	
	public String list() {
		logger.info("OrderInvoiceAction!list");
		try {
			ListPageInit("invoiceInfo", orderInvoiceInfoQueryDTO);
			ReflectionUtil.copyProperties(orderInvoiceInfoQueryDTO, orderInvoiceInfoDTO);
			orderInvoiceInfoQueryDTO.setDefaultEntityId(this.getUser().getEntityId());
			sellOrderInputDTO.setDefaultEntityId(this.getUser().getEntityId());
			PageDataDTO result = (PageDataDTO) sendService(
					ConstCode.FRTCODE_SERVICE_INVOICE_MANAGE_LIST,
					orderInvoiceInfoQueryDTO).getDetailvo();
			invoiceInfos = result.getData();
			invoiceInfo_totalRows = result.getTotalRecord();
			/**获取销售人员名单*/
			saleManList = (List<UserDTO>) this.sendService(ConstCode.FRTCODE_SERVICE_GET_SALESMAN, sellOrderInputDTO).getDetailvo();
			if (hasErrors()) {
				return "input";
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";
	}
	/**
	 * 发票编辑
	 * 页面checkbox将id传递过来，根据id查找发票信息回显到页面，
	 * @return
	 */
	public String toEditInvoice(){
	    logger.info("OrderInvoiceAction!toEditInvoice");
        /** 查询*/
        String ids = (String) invoiceIdList.get(0);
        String id = ids.split(",")[0];
        if(orderInvoiceInfoDTO == null) orderInvoiceInfoDTO = new OrderInvoiceInfoDTO();
        orderInvoiceInfoDTO.setInvoiceId(id);
        logger.info("invoice_id"+orderInvoiceInfoDTO.getInvoiceId());
        orderInvoiceInfoDTO = (OrderInvoiceInfoDTO) this.sendService(ConstCode.FRTCODE_SERVICE_INVOICE_QUERY_INVOICE_INFO, orderInvoiceInfoDTO).getDetailvo();
        orderInvoiceInfoDTO.setCustomerExpectedDate(DateUtil.formatStringDate(orderInvoiceInfoDTO.getCustomerExpectedDate()));
        if(hasErrors()){
            return "input";
        }
        if(Const.CANCEL_INVOICE.equals(orderInvoiceInfoDTO.getInvoiceState())||Const.ALREADY_INVOICE.equals(orderInvoiceInfoDTO.getInvoiceState())){
            this.addActionMessage("当前发票信息为不可编辑状态");
            return this.list();
        }
        return  "toEditInvoice";
	}
	/**
	 * 可以对未开票的发票信息进行编辑
	 * @return
	 */
	public String editInvoice(){
	    logger.info("OrderInvoiceAction!editInvoice");
        if(orderInvoiceInfoDTO.getInvoiceTitle() == null ||
                "".equals(orderInvoiceInfoDTO.getInvoiceTitle())||
                orderInvoiceInfoDTO.getInvoiceProj() == null||
                "".equals(orderInvoiceInfoDTO.getInvoiceProj())||
                orderInvoiceInfoDTO.getInvoiceType() == null||
                "".equals(orderInvoiceInfoDTO.getInvoiceType())||
                "".equals(orderInvoiceInfoDTO.getInvoiceType())){
            this.addActionMessage("带\"*\"选项为必填选项!");
            return "input";
        }
        OrderInvoiceInfoDTO dto = (OrderInvoiceInfoDTO) this.sendService(ConstCode.FRTCODE_SERVICE_INVOICE_QUERY_INVOICE_INFO, 
                orderInvoiceInfoDTO).getDetailvo();
        if(!dto.getTotalPrice().equals(orderInvoiceInfoDTO.getTotalPrice())) {
            this.addActionError("总金额不允许被修改!");
            return "input";
        }
        orderInvoiceInfoDTO.setOrderId(null);
        //客户预计取票时间
        String customerExpectedDate = orderInvoiceInfoDTO.getCustomerExpectedDate();
        if(customerExpectedDate != null){
            orderInvoiceInfoDTO.setCustomerExpectedDate(customerExpectedDate.replace("-", ""));
        }
        orderInvoiceInfoDTO.setModifyTime(DateUtil.getCurrentTime24());
        Integer i = (Integer) this.sendService(ConstCode.FRTCODE_SERVICE_INVOICE_MODIFY_INVOICE, orderInvoiceInfoDTO).getDetailvo();
        if(i!=1){
            return "input";
        }
        this.addActionMessage("发票信息编辑成功!");
        return this.list();
	}
		
	/**
	 * 发票明细
	 * @author zhanqiaosong
	 * @since 2013-4-2
	 * 
	 * */
	public String view() {
		try {
			orderInvoiceInfoQueryDTO.setOrderInvoiceInfoDTO(orderInvoiceInfoDTO);
			OrderInvoiceInfoDTO orderInvoiceInfo = (OrderInvoiceInfoDTO)sendService(ConstCode.SELL_ORDER_INVOICE_DETAIL,
					orderInvoiceInfoQueryDTO.getOrderInvoiceInfoDTO()).getDetailvo();
			if(null != orderInvoiceInfo) {
				orderInvoiceInfoQueryDTO.setOrderInvoiceInfoDTO(orderInvoiceInfo);
			}
			
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "view";
	}
	
	/**
	 * 取消开票
	 * @author zhanqiaosong
	 * @since 2013-4-3
	 * 
	 * */
	public String cancelInvoice() {
	    String ids = (String) invoiceIdList.get(0);
        String id = ids.split(",")[0];
        orderInvoiceInfoDTO.setInvoiceId(id);
        sendService(ConstCode.SELL_ORDER_INVOICE_CANCEL, orderInvoiceInfoDTO).getDetailvo();
        if (!hasErrors()) {
            addActionMessage("取消成功！");
        }
        return list();
	} 

	/**
     * 
     * 功能描述: 跳转到开票的对话框<br>
     * 〈功能详细描述〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String toAddOrderInvoiceConfirm() {
        orderInvoiceInfoDTO = (OrderInvoiceInfoDTO) this.sendService(ConstCode.FRTCODE_SERVICE_INVOICE_QUERY_INVOICE_INFO, orderInvoiceInfoDTO).getDetailvo();
        if(hasErrors()){
            getRequest().setAttribute("sucessMessage", "订单查询失败~");
            return "orderInvoiceError";
        }
        if(Const.CANCEL_INVOICE.equals(orderInvoiceInfoDTO.getInvoiceState())){
            getRequest().setAttribute("sucessMessage", "此发票已经取消~");
            return "orderInvoiceError";
        }
        if(Const.ALREADY_INVOICE.equals(orderInvoiceInfoDTO.getInvoiceState())){
            getRequest().setAttribute("sucessMessage", "此发票已开票~");
            return "orderInvoiceError";
        }
        orderInvoiceInfoDTO.setBillingSubject("");
        orderInvoiceInfoDTO.setInvoiceDate("");
        orderInvoiceInfoDTO.setInvoiceCode("");
        orderInvoiceInfoDTO.setInvoiceNumber("");
        return "input";
    }
    
    /**
     * 
     * 功能描述: 开票确定，添加开票的相关信息 <br>
     * 〈功能详细描述〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String addOrderInvoiceConfirm() {
        orderInvoiceInfoDTO.setInvoiceId(orderInvoiceInfoDTO.getInvoiceId());
        if(StringUtils.isBlank(orderInvoiceInfoDTO.getBillingSubject())
                || StringUtils.isBlank(orderInvoiceInfoDTO.getInvoiceDate())
                || StringUtils.isBlank(orderInvoiceInfoDTO.getInvoiceNumber())
                || StringUtils.isBlank(orderInvoiceInfoDTO.getInvoiceCode())) {
            addActionError("带\"*\"选项为必填选项!");
            return "input";
        }
        sendService(ConstCode.SELL_ORDER_INVOICE_DO, orderInvoiceInfoDTO).getDetailvo();
        if (!hasErrors()) {
            getRequest().setAttribute("message", "开票成功!");
            list();
            return "orderInvoiceMessage";
        }
        return "input";
    }
    
	public OrderInvoiceInfoQueryDTO getOrderInvoiceInfoQueryDTO() {
		return orderInvoiceInfoQueryDTO;
	}

	public void setOrderInvoiceInfoQueryDTO(
			OrderInvoiceInfoQueryDTO orderInvoiceInfoQueryDTO) {
		this.orderInvoiceInfoQueryDTO = orderInvoiceInfoQueryDTO;
	}

	public List<?> getInvoiceInfos() {
		return invoiceInfos;
	}

	public void setInvoiceInfos(List<?> invoiceInfos) {
		this.invoiceInfos = invoiceInfos;
	}

	public int getInvoiceInfo_totalRows() {
		return invoiceInfo_totalRows;
	}

	public void setInvoiceInfo_totalRows(int invoiceInfoTotalRows) {
		invoiceInfo_totalRows = invoiceInfoTotalRows;
	}

	public String dateFormat(String date) {
		return date.substring(0, 10);
	}

	public List<?> getInvoiceIdList() {
		return invoiceIdList;
	}
	
	public void setInvoiceIdList(List<?> invoiceIdList) {
		this.invoiceIdList = invoiceIdList;
	}
	public OrderInvoiceInfoDTO getOrderInvoiceInfoDTO() {
		return orderInvoiceInfoDTO;
	}
	public void setOrderInvoiceInfoDTO(OrderInvoiceInfoDTO orderInvoiceInfoDTO) {
		this.orderInvoiceInfoDTO = orderInvoiceInfoDTO;
	}
	public SellOrderInputDTO getSellOrderInputDTO() {
		return sellOrderInputDTO;
	}
	public void setSellOrderInputDTO(SellOrderInputDTO sellOrderInputDTO) {
		this.sellOrderInputDTO = sellOrderInputDTO;
	}
	public List<UserDTO> getSaleManList() {
		return saleManList;
	}
	public void setSaleManList(List<UserDTO> saleManList) {
		this.saleManList = saleManList;
	}
	

	
}
