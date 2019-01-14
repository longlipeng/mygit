package com.huateng.univer.seller.sellorder;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.huateng.framework.constant.OrderConst;

import java.math.BigInteger;

/**
 * 订单接收action
 *
 */
public class OrderAcceptAction extends OrderBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3608876647554658084L;
	private Logger logger = Logger.getLogger(OrderAcceptAction.class);
	private String message;
	//用来需要删除的卡号段
    private String[] orderListCardPool;
    //要接收的卡号段
    private String[] orderListStr;
    
    private String cardNo;
    
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
    
	@Override
	protected void addCondition() {

	}

	@Override
	protected void dealWithSellOrderInputDTO() {

	}

	@Override
	public String edit() {
		return null;
	}

	@Override
	protected void init() {
		actionName= "orderAcceptAction";
		actionMethodName="button";
	}

	@Override
	protected void initOrderType() {

	}
	
	public String list(){
		try{
			ListPageInit("sellOrder", sellOrderQueryDTO);
			sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_ORDER_BRANCH_ACCEPT);
			 /***
			  * 按订单ID的倒序排序
			  */
			 if(isEmpty(sellOrderQueryDTO.getSortFieldName())){
				 sellOrderQueryDTO.setSort("desc");
				 sellOrderQueryDTO.setSortFieldName("orderId");
			 }
			 PageDataDTO result = (PageDataDTO) sendService(ConstCode.SELL_ORDER_QUERY_FOR_ORDER_ACCEPT,
					  sellOrderQueryDTO).getDetailvo();
			 sellOrders = result.getData();
			 sellOrder_totalRows = result.getTotalRecord();
			}catch(Exception e){
				this.logger.error(e.getMessage());
			}
		return "list";
	}
	
	public String button(){
		try{
			view();
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "button";
	}
	
	
	public String submitConfirm(){
		message="提交";
		operation="submit";
		return button();
	}
	
	
	public String sendBackConfirm(){
		message="退回";
		operation="sendback";
		return button();
	}

	public String submit(){
		try{
			sendService(ConstCode.SELL_ORDER_SUBMIT_AT_ACCEPT,
					  sellOrderDTO).getDetailvo();
			if(!this.hasErrors()){
				this.addActionMessage("提交订单成功!");
			}
			
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return list();
	
	}
	
	public String sendback(){
		try{
			sendService(ConstCode.SELL_ORDER_SENDBACK_AT_ACCEPT,
					  sellOrderDTO).getDetailvo();
			if(!this.hasErrors()){
				this.addActionMessage("退回订单成功!");
			}
			
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return list();
	
	}
	/**
	 * 卡片接收
	 * */
    public String accept(){
        init();
        
        sellOrderDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN);
        try{
            logger.info("开始接收卡号段！");
            acceptOrderDTO.setEntity(getUser().getEntityId());
            //按选择接收
            boolean isNull = acceptOrderDTO.getCardNoList()!=null;
            boolean isNull_b = acceptOrderDTO.getCardNoList()!="";
            boolean isNull_c = acceptOrderDTO.getCardNoList()!=" ";
            boolean isNull_d = acceptOrderDTO.getCardNoList().length()!=0;
            if(isNull&&isNull_b&&isNull_c&&isNull_d){
            	String[] cardNoList = acceptOrderDTO.getCardNoList().split(",");
            	for(String cardNo : cardNoList){
            		acceptOrderDTO.setStartCardNo(cardNo);
                	acceptOrderDTO.setEndCardNo(cardNo);
            		sendService(ConstCode.ACCEPT_CARD, acceptOrderDTO);
            	}
            	acceptOrderDTO.setCardNoList(null);
            	acceptOrderDTO.setStartCardNo(null);
            	acceptOrderDTO.setEndCardNo(null);
            }
            //按卡号段接收
            else{
            	sendService(ConstCode.ACCEPT_CARD, acceptOrderDTO);
            }
        }catch(Exception e){
            logger.info(e.getMessage());
            logger.info("接收卡号段失败！");
        }
        return button();
    }
    /**
	 * 卡片接收
	 * */
    public String acceptAll(){
    	
    	init();
    	sellOrderDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN);
        try{
            logger.info("开始接收全部卡号！");
            acceptOrderDTO.setEntity(getUser().getEntityId());
            sendService(ConstCode.ACCEPT_CARD_ALL, acceptOrderDTO);
        }catch(Exception e){
            logger.info(e.getMessage());
            logger.info("接收全部卡号失败！");
        }
    	return button();
    }
    /**
     * 卡片删除
     * */
    public String delete(){
        try{
            logger.info("删除该接收卡号段");
	        //部分删除时只能选择一条数据进行删除，将接收到的卡段信息解析
            String startNo=orderListCardPool[0].split(",")[0];
            String endno=orderListCardPool[0].split(",")[1];
            String num=orderListCardPool[0].split(",")[2];
            acceptOrderDTO.setStartCardNo(startNo);
            acceptOrderDTO.setEndCardNo(endno);
            acceptOrderDTO.setCardNum(num);
            acceptOrderDTO.setEntity(getUser().getEntityId());
            sendService(ConstCode.DELETE_CARD_POOL,acceptOrderDTO);
            if(hasActionErrors()){
                return button(); 
            }
        }catch(Exception e){
            logger.info(e.getMessage());
            logger.info("删除该接收卡号段失败");
        }
        return button();
    }
	/**
     * 卡片全部删除
     * */
    public String deleteAll(){
        try{
            logger.info("全部删除该接收卡号段");
            acceptOrderDTO.setEntity(getUser().getEntityId());
            sendService(ConstCode.DELETE_ALL_CARD_POOL,acceptOrderDTO);
        }catch(Exception e){
            logger.info(e.getMessage());
            logger.info("全部删除该接收卡号段失败");
        }
        return button();
    }
    public void checkCardNo(){
        String cardNoRule="^[0-9]{13,19}$";
        if(acceptOrderDTO.getStartCardNo()==null||
                !acceptOrderDTO.getStartCardNo().trim().matches(cardNoRule)||
                "".equals(acceptOrderDTO.getStartCardNo().trim())){
            addFieldError("batchCardDTO.startCardNo", "请输入格式正确的卡号(13-19位数字)");
        }
        if(acceptOrderDTO.getEndCardNo()==null||
                !acceptOrderDTO.getEndCardNo().trim().matches(cardNoRule)||
                "".equals(acceptOrderDTO.getEndCardNo().trim())){
            addFieldError("batchCardDTO.endCardNo", "请输入格式正确的卡号(13-19位数字)");
        }
    }
    public void check(){
        BigInteger startCardNo=new BigInteger(acceptOrderDTO.getStartCardNo().trim());
        BigInteger endCardNo=new BigInteger(acceptOrderDTO.getEndCardNo().trim());
        if(startCardNo.compareTo(endCardNo)==1){
            addActionError("起始卡号不能大于结束卡号！");
        }
    }
    
    /**
	 * 接收订单
	 * @return
	 */
	public String acceptByChoose(){
		init();
		String[] aa = orderListStr;
//        sellOrderDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN);
        try{
            logger.info("开始接收所选卡号！");
//            acceptOrderDTO.setEntity(getUser().getEntityId());
//            sendService(ConstCode.ACCEPT_CARD, acceptOrderDTO);
        }catch(Exception e){
            logger.info(e.getMessage());
            logger.info("接收接收所选卡号失败！");
        }
        return button();
	}
    
	
	
	
    
	

	public String[] getOrderListStr() {
		return orderListStr;
	}

	public void setOrderListStr(String[] orderListStr) {
		this.orderListStr = orderListStr;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    /**
     * @return the orderListCardPool
     */
    public String[] getOrderListCardPool() {
        return orderListCardPool;
    }

    /**
     * @param orderListCardPool the orderListCardPool to set
     */
    public void setOrderListCardPool(String[] orderListCardPool) {
        this.orderListCardPool = orderListCardPool;
    }

}
