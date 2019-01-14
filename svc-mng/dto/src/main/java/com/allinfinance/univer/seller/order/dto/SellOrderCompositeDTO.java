package com.allinfinance.univer.seller.order.dto;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.framework.dto.PageQueryDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;

/**
 * 订单组合dto 该dto保存订单端需要的所有数据
 * 
 * @author dawn
 * 
 */
public class SellOrderCompositeDTO extends PageQueryDTO {

	private static final long serialVersionUID = -4320073479445698853L;

	/**
	 * 订单信息
	 */
	private SellOrderDTO sellOrderDTO;

	/**
	 * 订单明细信息列表
	 */
	private PageDataDTO sellOrderList;
	
	/**
	 * 订单原有信息列表
	 */
	private PageDataDTO sellOrderOrigCardList;
	/**
	 * 订单卡接收信息列表
	 */
	private PageDataDTO sellOrderReceCardList;
	/**
	 * 订单卡未接收接收信息列表
	 */
	private PageDataDTO sellOrderNotReceCardList;
	

    /**
	 * 订单流信息列表
	 */
	private PageDataDTO sellOrderFlowList;

	/**
	 * 订单有效卡明细信息列表
	 */
	private PageDataDTO sellOrderCardList;

	/**
	 * 订单卡退回订单明细信息列表
	 */
	private PageDataDTO sellOrderBackCardList;

	/**
	 * 订单包信息
	 */
	private PageDataDTO orderPackageList;
	/**
	 * 	录入卡
	 */
	private PageDataDTO orderInputCardPackageList;
	/***
	 * 取产品
	 * 
	 */
	private  List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
	
	/**
	 * 订单支付方式明细列表
	 */
	private PageDataDTO sellOrderPaymentList;
	
	
	public PageDataDTO getOrderInputCardPackageList() {
		return orderInputCardPackageList;
	}

	public void setOrderInputCardPackageList(PageDataDTO orderInputCardPackageList) {
		this.orderInputCardPackageList = orderInputCardPackageList;
	}

	public List<ProductDTO> getProductDTOs() {
		return productDTOs;
	}

	public void setProductDTOs(List<ProductDTO> productDTOs) {
		this.productDTOs = productDTOs;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}
	
	

	public PageDataDTO getSellOrderNotReceCardList() {
		return sellOrderNotReceCardList;
	}

	public void setSellOrderNotReceCardList(PageDataDTO sellOrderNotReceCardList) {
		this.sellOrderNotReceCardList = sellOrderNotReceCardList;
	}



	/***
	 * 选中产品信息
	 */
	private ProductDTO productDTO = new ProductDTO();
	
	private SellOrderListQueryDTO sellOrderListQueryDTO  = new SellOrderListQueryDTO();
	
	private SellOrderCardListQueryDTO sellOrderCardListQueryDTO = new SellOrderCardListQueryDTO();
	
	private SellOrderInputCardListDTO sellOrderInputCardListDTO = new SellOrderInputCardListDTO();
	
	private OrderReceiveCardListQueryDTO orderReceiveCardListQueryDTO = new OrderReceiveCardListQueryDTO();
	
	private OrderReceiveCardListQueryDTO orderNotReceiveCardListQueryDTO = new OrderReceiveCardListQueryDTO();

    private SellOrderFlowQueryDTO sellOrderFlowQueryDTO = new SellOrderFlowQueryDTO();
	
	private SellOrderOrigCardListQueryDTO sellOrderOrigCardListQueryDTO = new SellOrderOrigCardListQueryDTO();
	
	private SellOrderPaymentQueryDTO sellOrderPaymentQueryDTO = new SellOrderPaymentQueryDTO();
	
	public PageDataDTO getSellOrderOrigCardList() {
		return sellOrderOrigCardList;
	}

	public void setSellOrderOrigCardList(PageDataDTO sellOrderOrigCardList) {
		this.sellOrderOrigCardList = sellOrderOrigCardList;
	}

	/**
	 * 客户信息
	 */
	private CustomerDTO customerDTO = new CustomerDTO();
	
	/**
	 * 营销机构信息
	 */
	private SellerDTO sellerDTO = new SellerDTO();
	/**
	 * 发行机构信息
	 */
	private IssuerDTO issuerDTO = new IssuerDTO();
	
	/**
     * 接受卡号段信息
     * */
    private AcceptOrderDTO acceptOrderDTO =new AcceptOrderDTO();

    /**
     * 接收卡号段信息列表
     * */
    private PageDataDTO acceptOrderList;
    
    
	
	public SellOrderInputCardListDTO getSellOrderInputCardListDTO() {
		return sellOrderInputCardListDTO;
	}

	public void setSellOrderInputCardListDTO(
			SellOrderInputCardListDTO sellOrderInputCardListDTO) {
		this.sellOrderInputCardListDTO = sellOrderInputCardListDTO;
	}

	public IssuerDTO getIssuerDTO() {
		return issuerDTO;
	}

	public void setIssuerDTO(IssuerDTO issuerDTO) {
		this.issuerDTO = issuerDTO;
	}

	public SellerDTO getSellerDTO() {
		return sellerDTO;
	}

	public void setSellerDTO(SellerDTO sellerDTO) {
		this.sellerDTO = sellerDTO;
	}
	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public SellOrderDTO getSellOrderDTO() {
		return sellOrderDTO;
	}

	public void setSellOrderDTO(SellOrderDTO sellOrderDTO) {
		this.sellOrderDTO = sellOrderDTO;
	}

	public PageDataDTO getSellOrderList() {
		return sellOrderList;
	}

	public void setSellOrderList(PageDataDTO sellOrderList) {
		this.sellOrderList = sellOrderList;
	}

	public PageDataDTO getSellOrderFlowList() {
		return sellOrderFlowList;
	}

	public void setSellOrderFlowList(PageDataDTO sellOrderFlowList) {
		this.sellOrderFlowList = sellOrderFlowList;
	}

	public PageDataDTO getSellOrderCardList() {
		return sellOrderCardList;
	}

	public void setSellOrderCardList(PageDataDTO sellOrderCardList) {
		this.sellOrderCardList = sellOrderCardList;
	}

	public PageDataDTO getSellOrderBackCardList() {
		return sellOrderBackCardList;
	}

	public void setSellOrderBackCardList(PageDataDTO sellOrderBackCardList) {
		this.sellOrderBackCardList = sellOrderBackCardList;
	}

	public PageDataDTO getOrderPackageList() {
		return orderPackageList;
	}

	public void setOrderPackageList(PageDataDTO orderPackageList) {
		this.orderPackageList = orderPackageList;
	}

	public SellOrderListQueryDTO getSellOrderListQueryDTO() {
		return sellOrderListQueryDTO;
	}

	public void setSellOrderListQueryDTO(SellOrderListQueryDTO sellOrderListQueryDTO) {
		this.sellOrderListQueryDTO = sellOrderListQueryDTO;
	}

	public SellOrderCardListQueryDTO getSellOrderCardListQueryDTO() {
		return sellOrderCardListQueryDTO;
	}

	public void setSellOrderCardListQueryDTO(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO) {
		this.sellOrderCardListQueryDTO = sellOrderCardListQueryDTO;
	}

	public SellOrderFlowQueryDTO getSellOrderFlowQueryDTO() {
		return sellOrderFlowQueryDTO;
	}

	public void setSellOrderFlowQueryDTO(SellOrderFlowQueryDTO sellOrderFlowQueryDTO) {
		this.sellOrderFlowQueryDTO = sellOrderFlowQueryDTO;
	}

	public SellOrderOrigCardListQueryDTO getSellOrderOrigCardListQueryDTO() {
		return sellOrderOrigCardListQueryDTO;
	}

	public void setSellOrderOrigCardListQueryDTO(
			SellOrderOrigCardListQueryDTO sellOrderOrigCardListQueryDTO) {
		this.sellOrderOrigCardListQueryDTO = sellOrderOrigCardListQueryDTO;
	}

	public SellOrderPaymentQueryDTO getSellOrderPaymentQueryDTO() {
		return sellOrderPaymentQueryDTO;
	}

	public void setSellOrderPaymentQueryDTO(
			SellOrderPaymentQueryDTO sellOrderPaymentQueryDTO) {
		this.sellOrderPaymentQueryDTO = sellOrderPaymentQueryDTO;
	}

	public PageDataDTO getSellOrderPaymentList() {
		return sellOrderPaymentList;
	}

	public void setSellOrderPaymentList(PageDataDTO sellOrderPaymentList) {
		this.sellOrderPaymentList = sellOrderPaymentList;
	}
	/**
     * @return the sellOrderReceCardList
     */
    public PageDataDTO getSellOrderReceCardList() {
        return sellOrderReceCardList;
    }

    /**
     * @param sellOrderReceCardList the sellOrderReceCardList to set
     */
    public void setSellOrderReceCardList(PageDataDTO sellOrderReceCardList) {
        this.sellOrderReceCardList = sellOrderReceCardList;
    }
    /**
     * @return the orderReceiveCardListQueryDTO
     */
    public OrderReceiveCardListQueryDTO getOrderReceiveCardListQueryDTO() {
        return orderReceiveCardListQueryDTO;
    }

    /**
     * @param orderReceiveCardListQueryDTO the orderReceiveCardListQueryDTO to set
     */
    public void setOrderReceiveCardListQueryDTO(OrderReceiveCardListQueryDTO orderReceiveCardListQueryDTO) {
        this.orderReceiveCardListQueryDTO = orderReceiveCardListQueryDTO;
    }

    /**
     * @return the acceptOrderDTO
     */
    public AcceptOrderDTO getAcceptOrderDTO() {
        return acceptOrderDTO;
    }

    /**
     * @param acceptOrderDTO the acceptOrderDTO to set
     */
    public void setAcceptOrderDTO(AcceptOrderDTO acceptOrderDTO) {
        this.acceptOrderDTO = acceptOrderDTO;
    }

    /**
     * @return the acceptOrderList
     */
    public PageDataDTO getAcceptOrderList() {
        return acceptOrderList;
    }

    /**
     * @param acceptOrderList the acceptOrderList to set
     */
    public void setAcceptOrderList(PageDataDTO acceptOrderList) {
        this.acceptOrderList = acceptOrderList;
    }

	public OrderReceiveCardListQueryDTO getOrderNotReceiveCardListQueryDTO() {
		return orderNotReceiveCardListQueryDTO;
	}

	public void setOrderNotReceiveCardListQueryDTO(
			OrderReceiveCardListQueryDTO orderNotReceiveCardListQueryDTO) {
		this.orderNotReceiveCardListQueryDTO = orderNotReceiveCardListQueryDTO;
	}
    
}
