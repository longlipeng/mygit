package com.allinfinance.univer.seller.order.dto;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderQueryDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
/***
 * 订单录入时初始化一些相关的信息
 * @author dawn
 *
 */
public class SellOrderInputDTO  extends BaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SellOrderDTO sellOrderDTO=new SellOrderDTO();
	/**
	 * 销售订单取销售人员
	 */
	private List<UserDTO> saleUserList;

	/***
	 * 取产品
	 * 
	 */
	private  List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
	
	/**
	 * 客户信息
	 */
	private CustomerDTO customerDTO = new CustomerDTO();
	
	/***
	 * 选中产品信息
	 */
	private ProductDTO productDTO = new ProductDTO();
	
    private CardholderQueryDTO cardholderQueryDTO = new CardholderQueryDTO();
    private CardholderDTO cardholderDTO = new CardholderDTO();
    
    private PageDataDTO cardholderList = new PageDataDTO();
    
    private String[]  orderListStr;
    
    
    private SellOrderCardListQueryDTO sellOrderCardListQueryDTO = new SellOrderCardListQueryDTO();
    private List<SellOrderCardListQueryDTO> sellOrderCardListQueryDTOs=new ArrayList<SellOrderCardListQueryDTO>();
    
    private SellOrderListQueryDTO  sellOrderListQueryDTO = new SellOrderListQueryDTO();
    private SellOrderOrigCardListQueryDTO sellOrderOrigCardListQueryDTO= new SellOrderOrigCardListQueryDTO();
    /**
     * 产品库存信息
     */
    private PageDataDTO productStocks;
    private PageDataDTO orderList;
    
    private SellerContractDTO sellerContractDTO = new SellerContractDTO();
    
    private String[] ec_choose;


    private SellerDTO sellerDTO = new SellerDTO();
    
    
    private List<EntityDTO> entityDTOList = new ArrayList<EntityDTO>();
    
    private String creditAmount;
    
    private String orderId;
  
    private String cardNoArray[];
    
    private String startCardNo;
    
    private String endCardNo;
    private String isCurCustomer;
    private List<IssuerDTO> issuerDTOs=new ArrayList<IssuerDTO>();
    
	public SellOrderOrigCardListQueryDTO getSellOrderOrigCardListQueryDTO() {
		return sellOrderOrigCardListQueryDTO;
	}

	public void setSellOrderOrigCardListQueryDTO(
			SellOrderOrigCardListQueryDTO sellOrderOrigCardListQueryDTO) {
		this.sellOrderOrigCardListQueryDTO = sellOrderOrigCardListQueryDTO;
	}

	public SellOrderDTO getSellOrderDTO() {
		return sellOrderDTO;
	}

	public void setSellOrderDTO(SellOrderDTO sellOrderDTO) {
		this.sellOrderDTO = sellOrderDTO;
	}

	public List<UserDTO> getSaleUserList() {
		return saleUserList;
	}

	public void setSaleUserList(List<UserDTO> saleUserList) {
		this.saleUserList = saleUserList;
	}

	public List<ProductDTO> getProductDTOs() {
		return productDTOs;
	}

	public void setProductDTOs(List<ProductDTO> productDTOs) {
		this.productDTOs = productDTOs;
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public CardholderQueryDTO getCardholderQueryDTO() {
		return cardholderQueryDTO;
	}

	public void setCardholderQueryDTO(CardholderQueryDTO cardholderQueryDTO) {
		this.cardholderQueryDTO = cardholderQueryDTO;
	}

	public PageDataDTO getCardholderList() {
		return cardholderList;
	}

	public void setCardholderList(PageDataDTO cardholderList) {
		this.cardholderList = cardholderList;
	}

	public String[] getOrderListStr() {
		return orderListStr;
	}

	public void setOrderListStr(String[] orderListStr) {
		this.orderListStr = orderListStr;
	}

	public SellOrderCardListQueryDTO getSellOrderCardListQueryDTO() {
		return sellOrderCardListQueryDTO;
	}

	public void setSellOrderCardListQueryDTO(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO) {
		this.sellOrderCardListQueryDTO = sellOrderCardListQueryDTO;
	}

	public SellOrderListQueryDTO getSellOrderListQueryDTO() {
		return sellOrderListQueryDTO;
	}

	public void setSellOrderListQueryDTO(SellOrderListQueryDTO sellOrderListQueryDTO) {
		this.sellOrderListQueryDTO = sellOrderListQueryDTO;
	}

	public PageDataDTO getProductStocks() {
		return productStocks;
	}

	public void setProductStocks(PageDataDTO productStocks) {
		this.productStocks = productStocks;
	}

	public SellerContractDTO getSellerContractDTO() {
		return sellerContractDTO;
	}

	public void setSellerContractDTO(SellerContractDTO sellerContractDTO) {
		this.sellerContractDTO = sellerContractDTO;
	}

	public String[] getEc_choose() {
		return ec_choose;
	}

	public void setEc_choose(String[] ecChoose) {
		ec_choose = ecChoose;
	}

	public SellerDTO getSellerDTO() {
		return sellerDTO;
	}

	public void setSellerDTO(SellerDTO sellerDTO) {
		this.sellerDTO = sellerDTO;
	}

	public List<EntityDTO> getEntityDTOList() {
		return entityDTOList;
	}

	public void setEntityDTOList(List<EntityDTO> entityDTOList) {
		this.entityDTOList = entityDTOList;
	}

	public String getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}

	

	public String[] getCardNoArray() {
		return cardNoArray;
	}

	public void setCardNoArray(String[] cardNoArray) {
		this.cardNoArray = cardNoArray;
	}

	public String getStartCardNo() {
		return startCardNo;
	}

	public void setStartCardNo(String startCardNo) {
		this.startCardNo = startCardNo;
	}

	public String getEndCardNo() {
		return endCardNo;
	}

	public void setEndCardNo(String endCardNo) {
		this.endCardNo = endCardNo;
	}

	public CardholderDTO getCardholderDTO() {
		return cardholderDTO;
	}

	public void setCardholderDTO(CardholderDTO cardholderDTO) {
		this.cardholderDTO = cardholderDTO;
	}

	public List<SellOrderCardListQueryDTO> getSellOrderCardListQueryDTOs() {
		return sellOrderCardListQueryDTOs;
	}

	public void setSellOrderCardListQueryDTOs(
			List<SellOrderCardListQueryDTO> sellOrderCardListQueryDTOs) {
		this.sellOrderCardListQueryDTOs = sellOrderCardListQueryDTOs;
	}

	public List<IssuerDTO> getIssuerDTOs() {
		return issuerDTOs;
	}

	public void setIssuerDTOs(List<IssuerDTO> issuerDTOs) {
		this.issuerDTOs = issuerDTOs;
	}

	public PageDataDTO getOrderList() {
		return orderList;
	}

	public void setOrderList(PageDataDTO orderList) {
		this.orderList = orderList;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getIsCurCustomer() {
		return isCurCustomer;
	}

	public void setIsCurCustomer(String isCurCustomer) {
		this.isCurCustomer = isCurCustomer;
	}
	
			
}
