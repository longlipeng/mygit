package com.huateng.univer.order.business.bo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.stock.dto.EntityStockDTO;
import com.allinfinance.univer.issuer.dto.issuerContract.LoyaltyContractDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderQueryDTO;
import com.allinfinance.univer.seller.order.dto.AcceptOrderDTO;
import com.allinfinance.univer.seller.order.dto.CardNoSectionDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.OrderReceiveCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellBuyOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderFlowQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderOrigCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerAcctypeContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.BankDAO;
import com.huateng.framework.ibatis.dao.EntityStockDAO;
import com.huateng.framework.ibatis.dao.IssuerDAO;
import com.huateng.framework.ibatis.dao.LoyaltyContractDAO;
import com.huateng.framework.ibatis.dao.LoyaltyProdContractDAO;
import com.huateng.framework.ibatis.dao.ProductDAO;
import com.huateng.framework.ibatis.dao.ProductDAOImpl;
import com.huateng.framework.ibatis.dao.SellContractDAO;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderListDAO;
import com.huateng.framework.ibatis.dao.SellOrderOrigCardListDAO;
import com.huateng.framework.ibatis.dao.SellProdContractDAO;
import com.huateng.framework.ibatis.dao.SellerDAO;
import com.huateng.framework.ibatis.model.DeliveryContact;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.EntityStockExample;
import com.huateng.framework.ibatis.model.Issuer;
import com.huateng.framework.ibatis.model.IssuerExample;
import com.huateng.framework.ibatis.model.LoyaltyContract;
import com.huateng.framework.ibatis.model.LoyaltyContractExample;
import com.huateng.framework.ibatis.model.LoyaltyProdContract;
import com.huateng.framework.ibatis.model.LoyaltyProdContractExample;
import com.huateng.framework.ibatis.model.Product;
import com.huateng.framework.ibatis.model.ProductCardBin;
import com.huateng.framework.ibatis.model.ProductExample;
import com.huateng.framework.ibatis.model.SellContract;
import com.huateng.framework.ibatis.model.SellContractExample;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderCardListExample;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.ibatis.model.SellOrderListExample;
import com.huateng.framework.ibatis.model.SellOrderOrigCardList;
import com.huateng.framework.ibatis.model.SellOrderOrigCardListExample;
import com.huateng.framework.ibatis.model.SellProdContract;
import com.huateng.framework.ibatis.model.SellProdContractExample;
import com.huateng.framework.ibatis.model.Seller;
import com.huateng.framework.ibatis.model.SellerExample;
import com.huateng.framework.ibatis.model.UnionOrder;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.ibatis.model.UserExample;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.ConfigMakeCard;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.entitybaseinfo.bank.biz.service.BankService;
import com.huateng.univer.issuer.product.service.ProductService;
import com.huateng.univer.system.user.integration.dao.UserServiceDAO;

/**
 * 
 * @author dawn 后台订单处理类
 */
public class OrderBaseQueryBO {
	private Logger logger = Logger.getLogger(OrderBaseQueryBO.class);
	/**
	 * 分页查询dao
	 */
	protected PageQueryDAO pageQueryDAO;

	protected UserServiceDAO userServiceDAO;

	protected SellContractDAO sellerContractDAO;

	protected SellProdContractDAO sellProdContractDAO;

	protected ProductService productService;

	protected BaseDAO baseOrderDAO;

	private BaseDAO baseDAO;

	private SellOrderDAO sellOrderDAO;

	private SellOrderListDAO sellOrderListDAO;

	private SellOrderCardListDAO sellOrderCardListDAO;
	private SellOrderOrigCardListDAO sellOrderOrigCardListDAO;
	private SellerDAO sellerDAO;

	private LoyaltyContractDAO loyaltyContractDAO;

	private LoyaltyProdContractDAO loyaltyProdContractDAO;

	private IssuerDAO issuerDAO;

	private BankDAO bankDAO;

	private BankService bankService;

	private ProductDAO productDAO;
	private OrderBO orderBO;
	private EntityStockDAO entityStockDAO;
	public SellOrderOrigCardListDAO getSellOrderOrigCardListDAO() {
		return sellOrderOrigCardListDAO;
	}

	public void setSellOrderOrigCardListDAO(
			SellOrderOrigCardListDAO sellOrderOrigCardListDAO) {
		this.sellOrderOrigCardListDAO = sellOrderOrigCardListDAO;
	}

	public BankService getBankService() {
		return bankService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public BankDAO getBankDAO() {
		return bankDAO;
	}

	public void setBankDAO(BankDAO bankDAO) {
		this.bankDAO = bankDAO;
	}

	public IssuerDAO getIssuerDAO() {
		return issuerDAO;
	}

	public void setIssuerDAO(IssuerDAO issuerDAO) {
		this.issuerDAO = issuerDAO;
	}

	public LoyaltyContractDAO getLoyaltyContractDAO() {
		return loyaltyContractDAO;
	}

	public void setLoyaltyContractDAO(LoyaltyContractDAO loyaltyContractDAO) {
		this.loyaltyContractDAO = loyaltyContractDAO;
	}

	public LoyaltyProdContractDAO getLoyaltyProdContractDAO() {
		return loyaltyProdContractDAO;
	}

	public void setLoyaltyProdContractDAO(
			LoyaltyProdContractDAO loyaltyProdContractDAO) {
		this.loyaltyProdContractDAO = loyaltyProdContractDAO;
	}

	public PageDataDTO querySellOrder(SellOrderQueryDTO sellOrderQueryDTO)
			throws Exception {
		return pageQueryDAO.query("ORDER.selectOrderList", sellOrderQueryDTO);
	}
	//页面初始化
    @SuppressWarnings("unchecked")
    public PageDataDTO queryOrderBySeller(SellOrderQueryDTO sellOrderQueryDTO)
			throws Exception {
//	    if(sellOrderQueryDTO.getCardNo() == null){
//	        return pageQueryDAO.query("ORDER.queryOrder", sellOrderQueryDTO); 
//	    }
	    List<String> newOrderList = (List<String>)baseOrderDAO.queryForList("queryNewOrderList", sellOrderQueryDTO);
	    if(newOrderList.size() == 0){
	        sellOrderQueryDTO.setNewOrderList(null);
	    }
	    else{
	        sellOrderQueryDTO.setNewOrderList((String[])newOrderList.toArray(new String[newOrderList.size()]));
	    }
		return pageQueryDAO.query("ORDER.queryOrder", sellOrderQueryDTO);
	}

	public PageDataDTO queryOrderActivate(SellOrderQueryDTO sellOrderQueryDTO)
			throws Exception {
		return pageQueryDAO
				.query("ORDER.queryOrderActivate", sellOrderQueryDTO);
	}
	
	public PageDataDTO queryOtherOrder(SellOrderQueryDTO sellOrderQueryDTO)
			throws Exception {
		return pageQueryDAO
				.query("ORDER.queryOtherOrder", sellOrderQueryDTO);
	}
	/**
	 * 
	 * 功能描述: <br>
	 * 查找非现金、银行卡满足条件的待激活的订单
	 * @param sellOrderQueryDTO
	 * @return
	 * @throws Exception
	 */
    public PageDataDTO queryOrderActivateEx(SellOrderQueryDTO sellOrderQueryDTO)
            throws Exception {
        return pageQueryDAO
                .query("ORDER.queryOrderActivateEx", sellOrderQueryDTO);
    }	
	/**
	 * 
	 * 功能描述: <br>
	 * 查询待激活的订单
	 *
	 * @return
	 * @throws BizServiceException
	 */
    @SuppressWarnings("unchecked")
    public List<SellOrderDTO> queryAutoActOrder()throws BizServiceException {
        List<SellOrderDTO> list = (List<SellOrderDTO>) baseDAO.queryForList("ORDER.queryAutoActOrder", "");
        return list;
    }   
    /**
     * 
     * 功能描述: <br>
     * 查询待修改激活状态的订单
     *
     * @return
     * @throws BizServiceException
     */    
    @SuppressWarnings("unchecked")
    public List<SellOrderDTO> getOrderActUpdate()throws BizServiceException {
        List<SellOrderDTO> list = (List<SellOrderDTO>) baseDAO.queryForList("ORDER.queryOrderActUpdate", "");
        return list;
    } 
    
	public PageDataDTO queryOrderAtSellerHandOutReady(
			SellOrderQueryDTO sellOrderQueryDTO) throws Exception {
		return pageQueryDAO.query("ORDER.queryOrderAtSellerHandOutReady",
				sellOrderQueryDTO);
	}

	public PageDataDTO queryOrderForOrderAccept(
			SellOrderQueryDTO sellOrderQueryDTO) throws Exception {
		return pageQueryDAO.query("ORDER.queryOrderForOrderAccept",
				sellOrderQueryDTO);
	}

	public PageDataDTO queryOrderAtSellerConfirm(
			SellOrderQueryDTO sellOrderQueryDTO) throws Exception {
		return pageQueryDAO.query("ORDER.queryOrderAtSellerConfirm",
				sellOrderQueryDTO);
	}

	/**
	 * 销售订单录入
	 */
	public PageDataDTO queryOrderAtSellInput(SellOrderQueryDTO sellOrderQueryDTO)
			throws Exception {
		return pageQueryDAO.query("ORDER.queryOrderForSellInput",
				sellOrderQueryDTO);
	}

	/**
	 * 采购订单录入
	 */
	public PageDataDTO queryOrderAtBuyInput(SellOrderQueryDTO sellOrderQueryDTO)
			throws Exception {
		return pageQueryDAO.query("ORDER.queryOrderForBuyInput",
				sellOrderQueryDTO);
	}

	/**
	 * 销售订单确定
	 */
	public PageDataDTO queryOrderAtSellConfirm(
			SellOrderQueryDTO sellOrderQueryDTO) throws Exception {
		return pageQueryDAO.query("ORDER.queryOrderAtSellConfirm",
				sellOrderQueryDTO);
	}

	/**
	 * 采购订单确定
	 */
	public PageDataDTO queryOrderAtBuyConfirm(
			SellOrderQueryDTO sellOrderQueryDTO) throws Exception {
		return pageQueryDAO.query("ORDER.queryOrderAtBuyConfirm",
				sellOrderQueryDTO);
	}

	/**
	 * 订单付款确认
	 * 
	 * @param sellOrderQueryDTO
	 * @return
	 * @throws Exception
	 */
	public PageDataDTO queryOrderAtOrderPayment(
			SellOrderQueryDTO sellOrderQueryDTO) throws Exception {
		return pageQueryDAO.query("ORDER.queryOrderAtOrderPayment",
				sellOrderQueryDTO);
	}

	/**
	 * 充值订单立即充值
	 * 
	 * @param sellOrderQueryDTO
	 * @return
	 * @throws Exception
	 */
	public PageDataDTO queryOrderAtImmediatelyCredit(
			SellOrderQueryDTO sellOrderQueryDTO) throws Exception {
		return pageQueryDAO.query("ORDER.queryOrderForOrderImmediatelyCredit",
				sellOrderQueryDTO);
	}

	public PageDataDTO queryOrderAtSendConfirm(
			SellOrderQueryDTO sellOrderQueryDTO) throws Exception {
		return pageQueryDAO.query("ORDER.queryOrderAtSendConfirm",
				sellOrderQueryDTO);
	}

	public List<UserDTO> getSaleUserList(SellOrderInputDTO sellOrderInputDTO)
			throws Exception {
		UserExample example = new UserExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andIsSaleFlageEqualTo(
				DataBaseConstant.DATA_TYPE_YES).andEntityIdEqualTo(
				sellOrderInputDTO.getDefaultEntityId());
		List<User> userList = userServiceDAO.selectByExample(example);
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		for (User user : userList) {
			UserDTO userDTO = new UserDTO();
			userDTO.setUserId(user.getUserId());
			userDTO.setUserName(user.getUserName());
			userDTOList.add(userDTO);
		}
		return userDTOList;
	}
	public List<ProductDTO> getProductByContract(SellOrderDTO sellOrderDTO,
			String contractType) throws Exception {
		SellContractExample example = new SellContractExample();
		example.createCriteria().andContractBuyerEqualTo(
				sellOrderDTO.getFirstEntityId()).andContractStateEqualTo(
				DataBaseConstant.STATE_ACTIVE).andContractTypeEqualTo(
				contractType).andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andContractSellerEqualTo(
				sellOrderDTO.getProcessEntityId()).andExpiryDateGreaterThanOrEqualTo(
				DateUtil.date2String(new Date()));
		List<SellContract> sellContractList = sellerContractDAO
				.selectByExample(example);

		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		if (null == sellContractList || sellContractList.size() == 0) {
			productDTOs = null;
		} else if (sellContractList.size() > 0) {
			SellContract sellContract = sellContractList.get(0);
			/***
			 * 设置快递费
			 */
			sellOrderDTO.setDeliveryFee(Amount.getReallyAmount(sellContract
					.getDeliveryFee()));
			/***
			 * 营销合同产品明细表
			 */
			SellProdContractExample sellProdContexample = new SellProdContractExample();

			sellProdContexample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL)
					.andSellContractIdEqualTo(sellContract.getSellContractId());
			List<SellProdContract> sellProdContractList = sellProdContractDAO
					.selectByExample(sellProdContexample);
			for (SellProdContract sellProdContract : sellProdContractList) {
				ProductDTO productDTO = new ProductDTO();
				productDTO.setProductId(sellProdContract.getProductId());
				productDTO = productService.viewProduct(productDTO);
				//充值订单不显示不记名卡产品
				

				/***
				 * 在这里将合同中的卡费
				 */
				productDTO.setCardFee(Amount.getReallyAmount(sellProdContract
						.getCardFee()));
				productDTO.setAnnualFee(Amount.getReallyAmount(sellProdContract
						.getAnnualFee()));
				productDTOs.add(productDTO);
			}
		}
		return productDTOs;
	}

	/**
	 * 返回记名或者是不记名的产品
	 * 
	 * @param sellOrderDTO
	 * @param contractType
	 * @return
	 * @throws Exception
	 */
	public List<ProductDTO> getProductByContractAndOrderType(
			SellOrderDTO sellOrderDTO, String contractType) throws Exception {
		SellContractExample example = new SellContractExample();
		example.createCriteria().andContractBuyerEqualTo(
				sellOrderDTO.getFirstEntityId()).andContractStateEqualTo(
				DataBaseConstant.STATE_ACTIVE).andContractTypeEqualTo(
				contractType).andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andContractSellerEqualTo(
				sellOrderDTO.getProcessEntityId()).andExpiryDateGreaterThanOrEqualTo(DateUtil.date2String(new Date()));
		List<SellContract> sellContractList = sellerContractDAO
				.selectByExample(example);

		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		if (null == sellContractList || sellContractList.size() == 0) {
			productDTOs = null;
		} else if (sellContractList.size() > 0) {
			SellContract sellContract = sellContractList.get(0);
			/***
			 * 设置快递费
			 */
			sellOrderDTO.setDeliveryFee(Amount.getReallyAmount(sellContract
					.getDeliveryFee()));
			/***
			 * 营销合同产品明细表
			 */
			SellProdContractExample sellProdContexample = new SellProdContractExample();

			sellProdContexample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL)
					.andSellContractIdEqualTo(sellContract.getSellContractId());
			List<SellProdContract> sellProdContractList = sellProdContractDAO
					.selectByExample(sellProdContexample);
			List<String> spcds = (List<String>) baseDAO.queryForList(
					"SELLERCONTRACT.selectSellProduct", sellOrderDTO
							.getDefaultEntityId());
			for (SellProdContract sellProdContract : sellProdContractList) {
				if (spcds.contains(sellProdContract.getProductId())) {
					ProductDTO productDTO = new ProductDTO();
					ProductDTO productUnsignDTO = new ProductDTO();

					if (sellOrderDTO
							.getOrderType()
							.equals(
									OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN)
							|| sellOrderDTO
									.getOrderType()
									.equals(
											OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN)
							|| sellOrderDTO
									.getOrderType()
									.equals(
											OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN)) {
						productDTO
								.setProductId(sellProdContract.getProductId());
						ProductExample pe = new ProductExample();
						pe.createCriteria().andProductIdEqualTo(
								sellProdContract.getProductId())
								.andOnymousStatNotEqualTo("2");// 记名产品
						List<Product> product = productDAO.selectByExample(pe);
						if (product.size() > 0) {
							ReflectionUtil.copyProperties(product.get(0),
									productDTO);
							productDTO = productService.viewProduct(productDTO);
							productDTO.setCardFee(Amount
									.getReallyAmount(sellProdContract
											.getCardFee()));// 在这里将合同中的卡费
							productDTO.setAnnualFee(Amount
									.getReallyAmount(sellProdContract
											.getAnnualFee()));
							productDTOs.add(productDTO);
						}
					} else {
						productUnsignDTO.setProductId(sellProdContract
								.getProductId());
						ProductExample pe = new ProductExample();

						pe.createCriteria().andProductIdEqualTo(
								sellProdContract.getProductId())
								.andOnymousStatNotEqualTo("1");// 不记名产品和记名库存
						List<Product> product = productDAO.selectByExample(pe);
						if (product.size() > 0) {
							ReflectionUtil.copyProperties(product.get(0),
									productUnsignDTO);
							productUnsignDTO = productService
									.viewProduct(productUnsignDTO);
							productUnsignDTO.setCardFee(Amount
									.getReallyAmount(sellProdContract
											.getCardFee()));// 在这里将合同中的卡费
							productUnsignDTO.setAnnualFee(Amount
									.getReallyAmount(sellProdContract
											.getAnnualFee()));
							productDTOs.add(productUnsignDTO);
						}
					}
				}
			}
		}
		return productDTOs;
	}

	public List<ProductDTO> getProductByLoyaltyContract(
			SellOrderDTO sellOrderDTO) throws Exception {
		LoyaltyContractExample example = new LoyaltyContractExample();
		example.createCriteria().andContractBuyerEqualTo(
				sellOrderDTO.getFirstEntityId()).andContractStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andExpiryDateGreaterThan(
				DateUtil.getCurrentDateStr());
		List<LoyaltyContract> LoyaltyContractList = loyaltyContractDAO
				.selectByExample(example);
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		if (null == LoyaltyContractList || LoyaltyContractList.size() == 0) {
			productDTOs = null;
		} else if (LoyaltyContractList.size() > 0) {
			LoyaltyContract loyaltyContract = LoyaltyContractList.get(0);
			/***
			 * 设置快递费
			 */
			sellOrderDTO.setDeliveryFee(Amount.getReallyAmount(loyaltyContract
					.getDeliveryFee()));
			/***
			 * 发行合同产品明细表
			 */
			LoyaltyProdContractExample loyaltyProdContexample = new LoyaltyProdContractExample();

			loyaltyProdContexample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL)
					.andLoyaltyContractIdEqualTo(
							loyaltyContract.getLoyaltyContractId());
			List<LoyaltyProdContract> loyaltyProdContractList = loyaltyProdContractDAO
					.selectByExample(loyaltyProdContexample);
			for (LoyaltyProdContract loyaltyProdContract : loyaltyProdContractList) {
				ProductDTO productDTO = new ProductDTO();
				productDTO.setProductId(loyaltyProdContract.getProductId());
				productDTO = productService.viewProduct(productDTO);

				/***
				 * 在这里将合同中的卡费
				 */
				productDTO.setCardFee(Amount
						.getReallyAmount(loyaltyProdContract.getCardFee()));
				productDTO.setAnnualFee(Amount
						.getReallyAmount(loyaltyProdContract.getAnnualFee()));
				productDTOs.add(productDTO);
			}
		}
		return productDTOs;
	}

	/**
	 * 通过订单ID获取订单卡明细
	 * 
	 * @return
	 */
	//TODO
	@SuppressWarnings("unchecked")
    public PageDataDTO queryCardListByOrderbyId(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws Exception {
		if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
				.equals(sellOrderCardListQueryDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
						.equals(sellOrderCardListQueryDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
						.equals(sellOrderCardListQueryDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
						.equals(sellOrderCardListQueryDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
						.equals(sellOrderCardListQueryDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
						.equals(sellOrderCardListQueryDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER
						.equals(sellOrderCardListQueryDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
						.equals(sellOrderCardListQueryDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
						.equals(sellOrderCardListQueryDTO.getOrderType())) {
			return pageQueryDAO.query(
					"TB_SELL_ORDER_CARD_LIST.selectOrderCardList",
					sellOrderCardListQueryDTO);
		} else if (OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD
				.equals(sellOrderCardListQueryDTO.getOrderType())) {
			return pageQueryDAO.query(
					"TB_SELL_ORDER_CARD_LIST.selectOrderCardList",
					sellOrderCardListQueryDTO);
		} else {
		    List<String> oldOrderList = (List<String>)baseDAO.queryForList("TB_SELL_ORDER_CARD_LIST.queryOldOrderList", sellOrderCardListQueryDTO);
		    if(oldOrderList.size() == 0){
		        sellOrderCardListQueryDTO.setOldOrderList(null);
	        }
	        else{
	            sellOrderCardListQueryDTO.setOldOrderList((String[])oldOrderList.toArray(new String[oldOrderList.size()]));
	        }
			return pageQueryDAO.query(
					"TB_SELL_ORDER_CARD_LIST.selectBuyOrderCardList",
					sellOrderCardListQueryDTO);
		}
	}
	
	/**
	 * 通过订单ID获取不记名订单结算卡明细
	 * 
	 * @return
	 */
	//TODO
    public PageDataDTO querySettlementCardListByOrderbyId(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws Exception {
			return pageQueryDAO.query(
					"TB_SELL_ORDER_CARD_LIST.selectOrderSettlementCardList",
					sellOrderCardListQueryDTO);
		
	}
	/**
	 * 通过订单ID、卡号获取订单卡明细
	 * 
	 * @return
	 */
	//TODO
    public PageDataDTO queryCardListByOrderbyCardNo(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws Exception {
		
			return pageQueryDAO.query(
					"TB_SELL_ORDER_CARD_LIST.selectOrderCardListByCardNo",
					sellOrderCardListQueryDTO);
		
	}
	/**
	 * 通过订单ID获取销售订单明细
	 * 
	 * @return
	 */
	public PageDataDTO queryOrderListByOrderbyId(
			SellOrderListQueryDTO sellOrderListQueryDTO) throws Exception {
		return pageQueryDAO.query("TB_SELL_ORDER_LIST.selectOrderList",
				sellOrderListQueryDTO);
	}
	
	/**
	 * 通过订单ID获取销售不记名订单结算明细
	 * 
	 * @return
	 */
	public PageDataDTO querySettlementOrderListByOrderbyId(
			SellOrderListQueryDTO sellOrderListQueryDTO) throws Exception {
		return pageQueryDAO.query("TB_SELL_ORDER_LIST.selectSettlementOrderList",
				sellOrderListQueryDTO);
	}
	
	/**
	 * 通过orderListID获取销售不记名订单已结算卡张数
	 * 
	 * @return
	 */
	public int querySettlementCardAmountByOrderLsitId(
			String orderListId ) throws Exception {
		 return (Integer) baseOrderDAO.queryForObject("TB_SELL_ORDER_LIST",
	 				"selectSettlementCardAmount", orderListId);
		/*return pageQueryDAO.query("TB_SELL_ORDER_LIST.selectSettlementCardAmount",
				sellOrderListQueryDTO);*/ 
	}

	/**
	 * 通过订单ID获取换卡订单订单明细
	 * 
	 * @return
	 */
	public PageDataDTO queryChangeCardOrderListByOrderbyId(
			SellOrderListQueryDTO sellOrderListQueryDTO) throws Exception {
		return pageQueryDAO.query(
				"TB_SELL_ORDER_LIST.selectChangeCardOrderList",
				sellOrderListQueryDTO);
	}

	/**
	 * 通过订单ID获取换卡订单原有卡明细
	 * 
	 * @return
	 */
	public PageDataDTO queryOrigCardListByOrderbyId(
			SellOrderOrigCardListQueryDTO sellOrderOrigCardListQueryDTO)
			throws Exception {
		return pageQueryDAO.query(
				"TB_SELL_ORDER_ORIGCARD_LIST.selectOrigCardList",
				sellOrderOrigCardListQueryDTO);
	}

	/**
	 * 通过订单ID获取匿名卡明细信息
	 */
	@SuppressWarnings("unchecked")
	public List<SellOrderListDTO> getSellUnSignCardListByOrderId(String orderId)
			throws Exception {
		return baseOrderDAO.queryForList("TB_SELL_ORDER_CARD_LIST",
				"selectOrderCardListByorderId", orderId);
	}

	/**
	 * 通过订单ID获取换卡订单卡明细信息
	 */
	@SuppressWarnings("unchecked")
	public List<SellOrderListDTO> getChangeCardOrderCardListByOrderId(
			String orderId) throws Exception {
		return baseOrderDAO.queryForList("TB_SELL_ORDER_CARD_LIST",
				"selectChangeCardOrderCardListByorderId", orderId);
	}

	/***
	 * 通过订单ID获取记名卡明细信息
	 */
	@SuppressWarnings("unchecked")
	public List<SellOrderListDTO> getSellSignCardListByOrderId(String orderId)
			throws Exception {
		return baseOrderDAO.queryForList("TB_SELL_ORDER_CARD_LIST",
				"getSellSignCardListByOrderId", orderId);
	}

	/***
	 * 通过订单ID获取充值订单明细
	 */

	@SuppressWarnings("unchecked")
	public List<SellOrderListDTO> getCreditCardListByOrderId(String orderId)
			throws Exception {
		return baseOrderDAO.queryForList("TB_SELL_ORDER_CARD_LIST",
				"getCreditCardListByOrderId", orderId);
	}

	public List<SellOrderOrigCardListDTO> getOrigCardListByOrderId(
			String orderId) throws Exception {
		return baseOrderDAO.queryForList("TB_SELL_ORDER_CARD_LIST",
				"getOrigCardListByOrderId", orderId);
	}

	/**
	 * 通过订单ID获取流程记录
	 * 
	 * @param sellOrderFlowQueryDTO
	 * @return
	 * @throws Exception
	 */
	public PageDataDTO queryOrderFlowByOrderId(
			SellOrderFlowQueryDTO sellOrderFlowQueryDTO) throws Exception {
		return pageQueryDAO.query("TB_SELL_ORDER_FLOW.selectOrderFlow",
				sellOrderFlowQueryDTO);
	}

	/**
	 * 获取客户下的持卡人
	 * 
	 * @return
	 */
	public PageDataDTO queryCardholderlist(CardholderQueryDTO cardholderQueryDTO)
			throws Exception {
		return pageQueryDAO.query("ORDER.selectCardholder", cardholderQueryDTO);
	}

	/***
	 * 查看订单用于编辑
	 * 
	 * @return
	 */

	public SellOrderDTO viewChangeCardOrderDTO(SellOrderDTO sellOrderDTO)
			throws Exception {
		SellOrderDTO resultOrderDTO = (SellOrderDTO) baseOrderDAO
				.queryForObject("getChangeCardOrderById", sellOrderDTO
						.getOrderId());
		resultOrderDTO.setLstBankDTO(bankService.inquery(resultOrderDTO
				.getProcessEntityId()));
		return resultOrderDTO;
	}

	/***
	 * 查看订单用于编辑
	 * 
	 * @return
	 */

	public SellOrderDTO viewSellOrderDTO(SellOrderDTO sellOrderDTO)
			throws Exception {
		SellOrderDTO resultOrderDTO = (SellOrderDTO) baseOrderDAO
				.queryForObject("getCustomerOrderById", sellOrderDTO
						.getOrderId());
		resultOrderDTO.setLstBankDTO(bankService.inquery(resultOrderDTO
				.getProcessEntityId()));
		return resultOrderDTO;
	}

	/**
	 * 查看订单用于订单查看
	 */

	public SellOrderDTO getSellOrderDTO(SellOrderDTO sellOrderDTO)
			throws Exception {
		SellOrderDTO resultOrderDTO = null;
		if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
				.equals(sellOrderDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN
						.equals(sellOrderDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
						.equals(sellOrderDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_UNSIGN
						.equals(sellOrderDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
						.equals(sellOrderDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN
						.equals(sellOrderDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER
						.equals(sellOrderDTO.getOrderType())) {
			resultOrderDTO = (SellOrderDTO) baseOrderDAO.queryForObject(
					"getsellOrderView", sellOrderDTO.getOrderId());
			resultOrderDTO.setLstBankDTO(bankService.inquery(resultOrderDTO
					.getProcessEntityId()));
		} else if (OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD.equals(sellOrderDTO
				.getOrderType())) {
			resultOrderDTO = (SellOrderDTO) baseOrderDAO.queryForObject(
					"getChangeCardOrderById", sellOrderDTO.getOrderId());
			resultOrderDTO.setLstBankDTO(bankService.inquery(resultOrderDTO
					.getProcessEntityId()));
		} else if (OrderConst.ORDER_TYPE_ORDER_RANSOM.equals(sellOrderDTO
				.getOrderType())) {
			resultOrderDTO = (SellOrderDTO) baseOrderDAO.queryForObject(
					"getChangeCardOrderById", sellOrderDTO.getOrderId());
			resultOrderDTO.setLstBankDTO(bankService.inquery(resultOrderDTO
					.getProcessEntityId()));
		} else {
			resultOrderDTO = getBuyOrderDTO(sellOrderDTO);
		}
		// if(null!=resultOrderDTO.getPaymentAmount()&&!"".equals(resultOrderDTO.getPaymentAmount())){
		// resultOrderDTO.setPaymentAmount(String.valueOf(Double
		// .valueOf(resultOrderDTO.getPaymentAmount()) / 100));
		// }
		return resultOrderDTO;
	}

	/**
	 * 
	 * 查询持卡人通过ID
	 */

	public CardholderDTO getCardholderById(String cardholderId)
			throws Exception {
		return (CardholderDTO) baseOrderDAO.queryForObject(
				"selectCardholdergetCardholderId", cardholderId);
	}

	/**
	 * 能过合同找到相关的费率
	 */
	@SuppressWarnings("unchecked")
	public List<SellerAcctypeContractDTO> getFeeForCreditOrder(
			SellerContractDTO sellerContractDTO) {
		return baseOrderDAO.queryForList("getCreditFee", sellerContractDTO);
	}

	/**
	 * 营销机构订单准备
	 * 
	 * @param orderReadyDTO
	 * @return
	 * @throws Exception
	 */
	public PageDataDTO getCardForSellOrderReady(OrderReadyDTO orderReadyDTO)
			throws Exception {
		PageDataDTO pageDataDTO = null;
		String resourceNameString = "";
		SellOrder tempOrder = this.getSellOrder(orderReadyDTO.getOrderId());
		
		if (tempOrder.getOrderType() != null
				&& (tempOrder.getOrderType().equals(
						OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN)
						|| tempOrder
								.getOrderType()
								.equals(
										OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN) || tempOrder
						.getOrderType()
						.equals(
								OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN))) {
			resourceNameString = "ORDER.selectCardNoforOrderReadySign";
		} else if (tempOrder.getOrderType().equals(
				OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD)) {
			Product product = productDAO.selectByPrimaryKey(tempOrder
					.getProductId());
			if(product.getOnymousStat().equals("2")){
				resourceNameString = "ORDER.selectCardForReadyChangeCardByUnsignCard";
			}else{
				resourceNameString = "ORDER.selectCardForReadyChangeCard";
			}
			
		} else {
			resourceNameString = "ORDER.selectCardNoforOrderReady";
		}
		orderReadyDTO.setLastRowNum("all");
		pageDataDTO = pageQueryDAO.query(resourceNameString, orderReadyDTO);
		
		
		/** 结果不为空时，取符合当前条件(去除分页)的首张末张卡 */
		// if (pageDataDTO != null && pageDataDTO.getTotalRecord() >
		// 0) {
		// orderReadyDTO.setLastRowNum("min");
		// orderReadyDTO.setQueryAll(true);
		// PageDataDTO minCardDataDTO =
		// pageQueryDAO.query(resourceNameString,
		// orderReadyDTO);
		// List<HashMap<String, String>> lstDataHashMaps =
		// (List<HashMap<String, String>>) pageDataDTO
		// .getData();
		// lstDataHashMaps.add((HashMap<String, String>)
		// minCardDataDTO
		// .getData().get(0));
		// orderReadyDTO.setLastRowNum("max");
		// orderReadyDTO.setQueryAll(true);
		// PageDataDTO maxCardDataDTO =
		// pageQueryDAO.query(resourceNameString,
		// orderReadyDTO);
		// lstDataHashMaps.add((HashMap<String, String>)
		// maxCardDataDTO
		// .getData().get(0));
		// pageDataDTO.setData(lstDataHashMaps);
		// pageDataDTO.setTotalRecord(pageDataDTO.getTotalRecord() +
		// 2);
		// }

		return pageDataDTO;
	}

	public PageDataDTO getCardNoforAllocate(OrderReadyDTO orderReadyDTO) {
		PageDataDTO pageDataDTO = null;
		String resourceNameString = "ORDER.selectCardNoforAllocate";
		orderReadyDTO.setLastRowNum("all");
		try {
			pageDataDTO = pageQueryDAO.query(resourceNameString, orderReadyDTO);
			/** 结果不为空时，取符合当前条件(去除分页)的首张末张卡 */
			if (pageDataDTO != null && pageDataDTO.getTotalRecord() > 0) {
				orderReadyDTO.setLastRowNum("min");
				orderReadyDTO.setQueryAll(true);
				PageDataDTO minCardDataDTO = pageQueryDAO.query(
						resourceNameString, orderReadyDTO);
				List<HashMap<String, String>> lstDataHashMaps = (List<HashMap<String, String>>) pageDataDTO
						.getData();
				lstDataHashMaps.add((HashMap<String, String>) minCardDataDTO
						.getData().get(0));
				orderReadyDTO.setLastRowNum("max");
				orderReadyDTO.setQueryAll(true);
				PageDataDTO maxCardDataDTO = pageQueryDAO.query(
						resourceNameString, orderReadyDTO);
				lstDataHashMaps.add((HashMap<String, String>) maxCardDataDTO
						.getData().get(0));
				pageDataDTO.setData(lstDataHashMaps);
				pageDataDTO.setTotalRecord(pageDataDTO.getTotalRecord() + 2);
			}
		} catch (Exception e) {

			this.logger.error(e.getMessage());
		}
		return pageDataDTO;
	}

	public PageDataDTO getProductStockByDTO(EntityStockDTO entityStockDTO)
			throws Exception {
		return pageQueryDAO.query("ORDER.getEntityStockByDTO", entityStockDTO);
	}
	
	public PageDataDTO getProductStockByDTOForUnsignOrder(EntityStockDTO entityStockDTO)
			throws Exception {
		return pageQueryDAO.query("ORDER.getEntityStockByDTOForUnsignOrder", entityStockDTO);
	}

	public PageDataDTO getProductStockForChangeCard(
			EntityStockDTO entityStockDTO) throws Exception {
		return pageQueryDAO.query("ORDER.getEntityStockForChangeCard",
				entityStockDTO);
	}

	public SellOrder getSellOrder(String orderId) throws Exception {
		return sellOrderDAO.selectByPrimaryKey(orderId);
	}

	public List<SellOrderOrigCardList> getSellOrderOrigCardList(String orderId)
			throws Exception {
		SellOrderOrigCardListExample example = new SellOrderOrigCardListExample();
		example.createCriteria().andOrderIdEqualTo(orderId)
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
		return sellOrderOrigCardListDAO.selectByExample(example);
	}

	/* 充值订单 购卡总金额 */
	public String getCreditOrderTotalCreditAmount(String orderId)
			throws Exception {
		return (String) baseDAO.queryForObject("TB_ENT_CUSTOMER_ORDER",
				"getTotalCreditAmount", orderId);
	}

	/* 充值订单 金额分段 */
	public List<CardNoSectionDTO> getCreditAmountSection(String orderId)
			throws Exception {
		return (List<CardNoSectionDTO>) baseDAO.queryForList(
				"TB_ENT_CUSTOMER_ORDER", "getCreditAmountSection", orderId);
	}

	/**
	 * 合并付款List
	 * 
	 * @param sellOrderDTO
	 * @return
	 * @throws Exception
	 */
	public SellOrderDTO getCombineList(SellOrderDTO sellOrderDTO)
			throws Exception {
		SellOrderQueryDTO dto = new SellOrderQueryDTO();
		try {
			String[] orderIds = sellOrderDTO.getPaymentId().split(",");
			sellOrderDTO.setOrderIds(orderIds);
			dto.setOrderIdList(new ArrayList<String>(Arrays.asList(orderIds)));
			sellOrderDTO = (SellOrderDTO) baseDAO.queryForObject(
					"ORDER.getOrderTotalPrice", sellOrderDTO);
			dto.setOrderIdList(new ArrayList<String>(Arrays.asList(orderIds)));
			sellOrderDTO.setPaymentList(pageQueryDAO.query(
					"ORDER.getCombineList", dto));
			sellOrderDTO.setOrderIds(orderIds);
			// sellOrderDTO.setPaymentAmount((String)baseDAO.queryForObject("ORDER.getOrderPayment",
			// sellOrderDTO));

		} catch (Exception e) {

			this.logger.error(e.getMessage());
		}
		return sellOrderDTO;
	}
	
	
	/**
     * 查看采购订单卡接收明细
     */
    public PageDataDTO getOrderReceiveListPageData(OrderReceiveCardListQueryDTO orderReceiveCardListQueryDTO) throws BizServiceException {
        PageDataDTO pageDataDTO = null;
        try{
            
            if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
                            .equals(orderReceiveCardListQueryDTO.getOrderType())
                    || OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
                            .equals(orderReceiveCardListQueryDTO.getOrderType())) {
                pageDataDTO = pageQueryDAO.query(
                        "TB_SELL_ORDER_CARD_LIST.selectOrderCardReceiveListPageData",
                        orderReceiveCardListQueryDTO);
            }
        }catch(Exception e){
            this.logger.error(e.getMessage());
            throw new BizServiceException("查看卡接收明细失败!");
        }
        return pageDataDTO;
   
    }
    
    /**
     * 查看采购订单卡未接收卡接收明细
     */
    public PageDataDTO getOrderNotReceiveListPageData(OrderReceiveCardListQueryDTO orderReceiveCardListQueryDTO) throws BizServiceException {
        //TODO
    	PageDataDTO pageDataDTO = null;
        try{
            if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
                            .equals(orderReceiveCardListQueryDTO.getOrderType())
                    || OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
                            .equals(orderReceiveCardListQueryDTO.getOrderType())) {
                pageDataDTO = pageQueryDAO.query(
                		"TB_SELL_ORDER_CARD_LIST.selectOrderCardNotReceiveListPageData",
                        orderReceiveCardListQueryDTO);
            }
        }catch(Exception e){
            this.logger.error(e.getMessage());
            throw new BizServiceException("查看未接收卡明细失败!");
        }
        return pageDataDTO;
   
    }
    
    /**
     * 〈功能详细描述〉
     *
     * @param acceptOrderDTO
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public PageDataDTO queryAcceptOrderByOrderId(AcceptOrderDTO acceptOrderDTO){
        //订单类型为30000002
        if(OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
                .equals(acceptOrderDTO.getOrderType())){
            return pageQueryDAO.query(
                    "TB_SELL_ORDER_CARD_LIST.selectAcceptOrderList",
                    acceptOrderDTO);
        }
        return pageQueryDAO.query(
                "TB_SELL_ORDER_CARD_LIST.selectAcceptOrderList",
                acceptOrderDTO);
    }
	public SellOrderDTO getSellOrderCardList(String orderId) throws Exception {
		return (SellOrderDTO) baseOrderDAO.queryForObject("getCardFaceValue",
				orderId);
	}

	public String getCardQuantityByOrderId(String orderId) throws Exception {
		return (String) baseOrderDAO.queryForObject("getCardQuantityById",
				orderId);
	}

	public String selectSignCardQuantity(String orderId) throws Exception {
		return (String) baseOrderDAO.queryForObject("selectSignCardQuantity",
				orderId);
	}

	public int selectSignCardCount(String orderId) throws Exception {
		List<SellOrderCardList>  list = new ArrayList<SellOrderCardList>();
		
		SellOrderCardListExample example1 = new SellOrderCardListExample();
        example1.createCriteria().andOrderIdEqualTo(orderId)
                .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
                .andCardNoEqualTo("");
        
        SellOrderCardListExample example2 = new SellOrderCardListExample();
        example2.createCriteria().andOrderIdEqualTo(orderId)
                .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
                .andCardNoIsNull();
        
        //卡号等于""
        List<SellOrderCardList> list1 = sellOrderCardListDAO.selectByExample(example1);
        //卡号等于null
        List<SellOrderCardList> list2 = sellOrderCardListDAO.selectByExample(example2);
        if(null != list1 && list1.size() > 0) {
            list.addAll(list1);
        }
        if(null != list2 && list2.size() > 0) {
            list.addAll(list2);
        }
		return list.size();
	}

	public String selectSellAndSellSignCardQuantity(UnionOrder order)
			throws Exception {
		return (String) baseOrderDAO.queryForObject(
				"selectSellAndSellSignCardQuantity", order);
	}

	public String selectUnsignCardQuantity(String orderId) throws Exception {
		return (String) baseOrderDAO.queryForObject("selectUnsignCardQuantity",
				orderId);
	}

	public List<SellOrderList> getSellOrderListByOrderId(String orderId)
			throws Exception {
		SellOrderListExample example = new SellOrderListExample();
		example.createCriteria().andOrderIdEqualTo(orderId)
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
		return sellOrderListDAO.selectByExample(example);
	}

	public List<SellOrderCardList> getSellOrderCardListsByOrderId(String orderId) {
		List<SellOrderCardList>  list = new ArrayList<SellOrderCardList>();
		
		SellOrderCardListExample example1 = new SellOrderCardListExample();
        example1.createCriteria().andOrderIdEqualTo(orderId)
                .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
                .andCardNoEqualTo("");
        
        SellOrderCardListExample example2 = new SellOrderCardListExample();
        example2.createCriteria().andOrderIdEqualTo(orderId)
                .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
                .andCardNoIsNull();
        //卡号等于""
        List<SellOrderCardList> list1 = sellOrderCardListDAO.selectByExample(example1);
        //卡号等于null
        List<SellOrderCardList> list2 = sellOrderCardListDAO.selectByExample(example2);
        if(null != list1 && list1.size() > 0) {
            list.addAll(list1);
        }
        if(null != list2 && list2.size() > 0) {
            list.addAll(list2);
        }
		return list;
	}

	public List<SellOrderCardList> getSellOrderCardListByorderId(String orderId) {
		List<SellOrderCardList>  list = new ArrayList<SellOrderCardList>();
		
		SellOrderCardListExample example1 = new SellOrderCardListExample();
        example1.createCriteria().andOrderIdEqualTo(orderId)
                .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
                .andCardNoNotEqualTo("");
        
        SellOrderCardListExample example2 = new SellOrderCardListExample();
        example2.createCriteria().andOrderIdEqualTo(orderId)
                .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
                .andCardNoIsNotNull();
        //卡号不等于""
        List<SellOrderCardList>  list1 = sellOrderCardListDAO.selectByExample(example1);
        //卡号不等于null
        List<SellOrderCardList>  list2 = sellOrderCardListDAO.selectByExample(example2);
        if(null != list1 && list1.size() > 0) {
            list.addAll(list1);
        }
        if(null != list2 && list2.size() > 0) {
            list.addAll(list2);
        }
		return list;
	}

	public List<String> getCardNos(OrderReadyDTO orderReadyDTO) {
		baseOrderDAO.queryForList("selectCardNo", orderReadyDTO);
		return null;
	}

	public String getSellOrderCardListByOrderId(String orderId)
			throws Exception {
		SellOrderCardListExample sellOrderExample = new SellOrderCardListExample();
		sellOrderExample.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andOrderIdEqualTo(orderId);
		return String.valueOf(sellOrderCardListDAO
				.countByExample(sellOrderExample));
	}

	public String checkSellOrderSignGenerateBuyerOrder(String orderId) {
		return (String) baseOrderDAO.queryForObject(
				"checkSellOrderSignGenerateBuyerOrder", orderId);
	}

	/**
	 * 查询出所有 固定面额的卡 符合订单条件
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SellOrderCardList> selectCardDetailFororderReadyByFixDetail(
			OrderReadyDTO orderReadyDTO) throws Exception {
		return baseOrderDAO.queryForList(
				"selectCardDetailFororderReadyByFixDetail", orderReadyDTO);
	}

	/**
	 * 查询出所有非固定面额的卡 符合订单条件
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SellOrderCardList> selectCardDetailFororderReadyByNotFixDetail(
			OrderReadyDTO orderReadyDTO) throws Exception {
		return baseOrderDAO.queryForList(
				"selectCardDetailFororderReadyByNotFixDetail", orderReadyDTO);
	}

	public List<SellOrderCardList> getSellOrderCardListDetailByOrderId(
			String orderId) throws Exception {
		SellOrderCardListExample example = new SellOrderCardListExample();
		example.createCriteria().andOrderIdEqualTo(orderId)
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
		example.setOrderByClause("CARD_NO asc");
		return sellOrderCardListDAO.selectByExample(example);
	}

	public SellOrderList getSellOrderListByPrimaryKey(String sellOrderlistId)
			throws Exception {
		return sellOrderListDAO.selectByPrimaryKey(sellOrderlistId);
	}

	/***
	 *获取营销机构名称
	 */
	public String getIssuerNameByEntityId(String entityId) throws Exception {
		return (String) baseOrderDAO.queryForObject(
				"selectIssuerNameByEntityId", entityId);
	}

	/**
	 * 获取发卡机构名称
	 */
	public String getSellNameByEntityId(String entityId) throws Exception {
		return (String) baseOrderDAO.queryForObject("selectSellNameByEntityId",
				entityId);
	}

	@SuppressWarnings("unchecked")
    public PageDataDTO queryBuyOrderCardListByOrderbyId(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws Exception {
	    List<String> oldOrderList = (List<String>)baseDAO.queryForList("TB_SELL_ORDER_CARD_LIST.queryOldOrderList", sellOrderCardListQueryDTO);
        if(oldOrderList.size() == 0){
            sellOrderCardListQueryDTO.setOldOrderList(null);
        }
        else{
            sellOrderCardListQueryDTO.setOldOrderList((String[])oldOrderList.toArray(new String[oldOrderList.size()]));
        }
		return pageQueryDAO.query(
				"TB_SELL_ORDER_CARD_LIST.selectBuyOrderCardList",
				sellOrderCardListQueryDTO);
	}

	/**
	 * -------------------------------------采购订单start----------------
	 * ---------- ------------------------------
	 */
	/**
	 * 查询合同买方合同明细
	 */
	@SuppressWarnings("unchecked")
	public List<SellerContractDTO> getContractDTOForBuyOrder(
			SellBuyOrderDTO sellBuyOrderDTO) throws Exception {
		return baseDAO
				.queryForList("SELL_BUY_ORDER.selectSellContractForBuyOrder",
						sellBuyOrderDTO);
	}

	/**
	 * 查询合同代卡发合同明细
	 */
	@SuppressWarnings("unchecked")
	public List<LoyaltyContractDTO> getContractDTOForSellBuyOrder(
			SellBuyOrderDTO sellBuyOrderDTO) throws Exception {
		return baseDAO.queryForList(
				"SELL_BUY_ORDER.selectloyaltyContractForBuyOrder",
				sellBuyOrderDTO);
	}

	@SuppressWarnings("unchecked")
	public List<SellOrderList> getOrderListForBuyOrderInput(String orderIdArray)
			throws Exception {
		return baseDAO.queryForList(
				"SELL_BUY_ORDER.selectOrderListForBuyOrderInput", orderIdArray);
	}

	public SellProdContract getSellProductContractByExample(
			String sellContractId, String productId) {
		SellProdContractExample example = new SellProdContractExample();
		example.createCriteria().andProductIdEqualTo(productId)
				.andSellContractIdEqualTo(sellContractId).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL);
		return sellProdContractDAO.selectByExample(example).get(0);

	}

	public LoyaltyProdContract getLoyaltyProductContractByExample(
			String loyaltyContractId, String productId) {
		LoyaltyProdContractExample example = new LoyaltyProdContractExample();
		example.createCriteria().andProductIdEqualTo(productId)
				.andLoyaltyContractIdEqualTo(loyaltyContractId)
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
		return loyaltyProdContractDAO.selectByExample(example).get(0);

	}

	public SellContract getSellContractById(String contractId) throws Exception {
		return sellerContractDAO.selectByPrimaryKey(contractId);
	}

	public LoyaltyContract getLoyaltyContractById(String contractId)
			throws Exception {
		return loyaltyContractDAO.selectByPrimaryKey(contractId);
	}

	public DeliveryContact getDeliveryContactForBuyOrder(String sellerId)
			throws Exception {
		return (DeliveryContact) baseDAO.queryForObject(
				"SELL_BUY_ORDER.selectDefaultContactForBuyOrder", sellerId);
	}

	@SuppressWarnings("unchecked")
	public List<SellOrderListDTO> getOrderListForBuyOrderInsert(
			SellBuyOrderDTO sellBuyOrderDTO) {
		if ((OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
				.equals(sellBuyOrderDTO.getOrderType()) || OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
				.equals(sellBuyOrderDTO.getOrderType()) || OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
				.equals(sellBuyOrderDTO.getOrderType()))
				&& sellBuyOrderDTO.getOnymousStat() != null
				&& "2".equals(sellBuyOrderDTO.getOnymousStat())) {
			return baseDAO.queryForList(
					"SELL_BUY_ORDER.selectRequiredAmountCardList",
					sellBuyOrderDTO);
		} else {
			return baseDAO.queryForList(
					"SELL_BUY_ORDER.selectRequiredAmountCardListForBuyOrder",
					sellBuyOrderDTO);
		}
	}

	/**
	 * 订单总金额
	 * 
	 * @param sellOrderDTO
	 * @return
	 * @throws Exception
	 */
	public SellOrderDTO getOrderTotalPrice(SellOrderDTO sellOrderDTO)
			throws Exception {
		sellOrderDTO = (SellOrderDTO) baseDAO.queryForObject(
				"ORDER.getOrderTotalPrice", sellOrderDTO);
		return sellOrderDTO;
	}

	public List<SellOrderDTO> getFirstEntityId(SellOrderDTO sellOrderDTO)
			throws Exception {
		List<SellOrderDTO> list = (List<SellOrderDTO>) baseDAO.queryForList(
				"ORDER.getFirstEntityId", sellOrderDTO);
		return list;
	}

	public Seller getSellerByExample(String sellerId) throws Exception {
		SellerExample example = new SellerExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL)
				.andEntityIdEqualTo(sellerId);
		return sellerDAO.selectByExample(example).get(0);
	}

	/***
	 * ${dynamicFirstEntityTable} T3, ${dynamicProcessEntityTable}
	 * T10,
	 * 
	 * @param sellOrderDTO
	 * @return
	 * @throws Exception
	 */
	public SellOrderDTO getBuyOrderDTO(SellOrderDTO sellOrderDTO)
			throws Exception {
		if (OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN
				.equals(sellOrderDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
						.equals(sellOrderDTO.getOrderType())) {
			sellOrderDTO.setDynamicFirstEntityTable("TB_ISSUER");
			sellOrderDTO.setDynamicProcessEntityTable("TB_ISSUER");

			sellOrderDTO.setDynamicFirstEntitycolumn("T3.ISSUER_NAME");
			sellOrderDTO.setDynamicprocessEntitycolumn("T10.ISSUER_NAME");
		} else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
				.equals(sellOrderDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
						.equals(sellOrderDTO.getOrderType())) {

			sellOrderDTO.setDynamicFirstEntityTable("TB_SELLER");
			sellOrderDTO.setDynamicProcessEntityTable("TB_ISSUER");

			sellOrderDTO.setDynamicFirstEntitycolumn("T3.SELLER_NAME");
			sellOrderDTO.setDynamicprocessEntitycolumn("T10.ISSUER_NAME");

		} else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
				.equals(sellOrderDTO.getOrderType())
				|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
						.equals(sellOrderDTO.getOrderType())) {
			sellOrderDTO.setDynamicFirstEntityTable("TB_SELLER");
			sellOrderDTO.setDynamicProcessEntityTable("TB_SELLER");

			sellOrderDTO.setDynamicFirstEntitycolumn("T3.SELLER_NAME");

			sellOrderDTO.setDynamicprocessEntitycolumn("T10.SELLER_NAME");
		}
		return (SellOrderDTO) baseOrderDAO.queryForObject("getbuyOrderView",
				sellOrderDTO);
	}

	/**
	 * ---------------------------------------采购订单over----------------
	 * ---------- ----------------------------
	 */
	
	@SuppressWarnings("unchecked")
    public List<String> getSuccessCardNoList(String orderId)
            throws BizServiceException {
        List<String> cardNoList = null;
        try {
            SellOrder sellOrder = this.getSellOrder(orderId);
            if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
                    .equals(sellOrder.getOrderType())
                    || OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
                            .equals(sellOrder.getOrderType())) {
                SellOrderDTO sellOrderDTO = new SellOrderDTO();
                sellOrderDTO.setOrderId(orderId);
                List<String> oldOrderIdOrderList = (List<String>)baseDAO.queryForList("STOCK_ORDER_CARD_LIST.selectOrigOrderByBuyOrderId", orderId);
                if(oldOrderIdOrderList.size() == 0){
                    sellOrderDTO.setOrderIds(null);
                }
                else{
                    sellOrderDTO.setOrderIds((String[])oldOrderIdOrderList.toArray(new String[oldOrderIdOrderList.size()])); 
                }
                cardNoList = baseDAO.queryForList(
                        "STOCK_ORDER_CARD_LIST.selectBuySuccessCardNoList",
                        sellOrderDTO);
            }
            if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
                    .equals(sellOrder.getOrderType())
                    || OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
                            .equals(sellOrder.getOrderType())) {
              cardNoList = baseDAO.queryForList(
                      "STOCK_ORDER_CARD_LIST.selectStockSuccessCardNoList",
                      orderId);
            }

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询卡号失败！");
        }
        return cardNoList;

    }

	@SuppressWarnings("unchecked")
	public List<String> getSuccessCardNoList(String orderId,AcceptOrderDTO acceptOrderDTO)
			throws BizServiceException {
		List<String> cardNoList = null;
		try {
			SellOrder sellOrder = this.getSellOrder(orderId);
			if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
					.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_SIGN
							.equals(sellOrder.getOrderType())) {
			    SellOrderDTO sellOrderDTO = new SellOrderDTO();
                sellOrderDTO.setOrderId(orderId);
                List<String> oldOrderIdOrderList = (List<String>)baseDAO.queryForList("STOCK_ORDER_CARD_LIST.selectOrigOrderByBuyOrderId", orderId);
                if(oldOrderIdOrderList.size() == 0){
                    sellOrderDTO.setOrderIds(null);
                }
                else{
                    sellOrderDTO.setOrderIds((String[])oldOrderIdOrderList.toArray(new String[oldOrderIdOrderList.size()])); 
                }
				cardNoList = baseDAO.queryForList(
						"STOCK_ORDER_CARD_LIST.selectBuySuccessCardNoList",
						sellOrderDTO);
			}
			if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
					.equals(sellOrder.getOrderType())
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_SELLER_UNSIGN
							.equals(sellOrder.getOrderType())) {
			    //有卡号段接收
//				cardNoList = baseDAO.queryForList(
//						"STOCK_ORDER_CARD_LIST.selectStockSuccessCardNoList",
//						orderId);
			    cardNoList = baseDAO.queryForList(
                      "STOCK_ORDER_CARD_LIST.selectStockSuccessCardNoListBy",
                      acceptOrderDTO);
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询卡号失败！");
		}
		return cardNoList;

	}

	public List<String> getOrigCardListByChangeOrder(
			SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		List<String> cardNoList = null;
		try {
			// FIXME by wpf 这个sql语句里面.大坑一枚, 当一张卡片被销售后.之后赎回.
			// 后续没有被销售时.依然可以再次查询出来. 如果卡片状态正常.则可以再次被赎回 .
			cardNoList = baseDAO.queryForList(
					"TB_SELL_ORDER_ORIGCARD_LIST.selectChangeOrderForOrig",
					sellOrderDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询卡号失败！");
		}
		return cardNoList;
	}
	
	
	public List<String> getCheckProductIdByChangeOrder(
			SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		List<String> cardNoList = null;
		try {
			cardNoList = baseDAO.queryForList(
					"TB_SELL_ORDER_ORIGCARD_LIST.checkProductIdByChangeOrder",
					sellOrderDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询卡号失败！");
		}
		return cardNoList;
	}
	
	
	public List<String> getOrigCardList(
			SellOrderOrigCardListQueryDTO sellOrderOrigCardListQueryDTO)
			throws BizServiceException {
		List<String> cardNoList = null;
		try {
			// FIXME by wpf 这个sql语句里面.大坑一枚, 当一张卡片被销售后.之后赎回.
			// 后续没有被销售时.依然可以再次查询出来. 如果卡片状态正常.则可以再次被赎回 .
			cardNoList = baseDAO.queryForList(
					"TB_SELL_ORDER_ORIGCARD_LIST.selectValiCardForOrig",
					sellOrderOrigCardListQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询卡号失败！");
		}
		return cardNoList;
	}
	//查询该卡号是否是换卡是销售的卡
	public List<String> getOrigCardChennlList(
			String cardNo)
			throws BizServiceException {
		List<String> cardNoList = null;
		try {
			cardNoList = baseDAO.queryForList(
					"TB_SELL_ORDER_ORIGCARD_LIST.selectChangeOrder",
					cardNo);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询卡号失败！");
		}
		return cardNoList;
	}

	/***
	 * 查看当前营销机构下的库存情况
	 * 
	 * @param entityId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<EntityStockDTO> getCurrentStock(String entityId)
			throws Exception {
		return baseOrderDAO.queryForList("getCurrentStock", entityId);
	}

	public String getCreditFaceValueByOrderCardList(String orderid)
			throws Exception {
		return (String) baseOrderDAO.queryForObject(
				"getCreditOrderTotalCreditFaceValue", orderid);
	}

	public PageDataDTO getCardNoByOrderType(
			SellOrderCardListQueryDTO sellOrderCardListQueryDTO)
			throws Exception {
		return pageQueryDAO.query("ORDER.getCardNoByOrderType",
				sellOrderCardListQueryDTO);
	}

	public Issuer getIssuerByExample(String IssuerId) throws Exception {
		IssuerExample example = new IssuerExample();
		example.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL)
				.andEntityIdEqualTo(IssuerId);
		return issuerDAO.selectByExample(example).get(0);
	}

	public DeliveryContact getDeliveryContactForLoyaltyBuyOrder(String sellerId)
			throws Exception {
		return (DeliveryContact) baseDAO.queryForObject(
				"SELL_BUY_ORDER.selectDefaultContactForLoyaltyBuyOrder",
				sellerId);
	}

	/**
	 * 通过机构id找到合同所关联的产品
	 * */
	@SuppressWarnings("unchecked")
	public List<ProductDTO> getProdByContractForBuyOrderUnsign(String entityId) {
		return baseDAO.queryForList("ORDER",
				"getProdByContractForBuyOrderUnsign", entityId);
	}

	public List<SellOrderOrigCardList> getOrigCardByOrderId(String orderId) {
		return baseDAO.queryForList("TB_SELL_ORDER_ORIGCARD_LIST",
				"selectOrigCardListByOrderId", orderId);
	}

	/* 查询售卡订单，用于凭证打印功能 */
	public SellOrderDTO getSellOrderForCertPrint(String orderId) {
		return (SellOrderDTO) baseDAO.queryForObject("TB_ENT_CUSTOMER_ORDER",
				"querySellOrderForCertPrint", orderId);
	}

	public String getCreditTotalAmount(String orderId) {
		return (String) baseDAO.queryForObject("TB_ENT_CUSTOMER_ORDER",
				"getTotalCreditAmount", orderId);
	}

	/* 查询换卡订单，用于凭证打印功能 */
	public SellOrderDTO getChangeOrderForCertPrint(String orderId) {
		return (SellOrderDTO) baseDAO.queryForObject("TB_ENT_CUSTOMER_ORDER",
				"queryCCOrderForCertPrint", orderId);
	}

	/* 查询充值订单，用于凭证打印功能 */
	public SellOrderDTO getCreditOrderForCertPrint(String orderId) {
		return (SellOrderDTO) baseDAO.queryForObject("TB_ENT_CUSTOMER_ORDER",
				"queryCreditOrderForCertPrint", orderId);
	}

	public String getOrigCardProdNames(String orderId) {
		String resultString = "";
		List<Map<String, String>> tempList = (List<Map<String, String>>) baseDAO
				.queryForList("TB_SELL_ORDER_ORIGCARD_LIST",
						"selectOrigCardProdNames", orderId);
		if (null == tempList || tempList.size() == 0) {
			return resultString;
		}
		for (Map<String, String> tempMap : tempList) {
			resultString += tempMap.get("productName") + "、";
		}
		return resultString.substring(0, resultString.length() - 1);
	}
	
	public Map<String, String> getRansomSellOrderPrintDetail(String orderId) {
		Map<String, String> map = (Map<String, String>) baseDAO.queryForObject(
				"TB_SELL_ORDER_ORIGCARD_LIST.getRansomSellOrderPrintDetail", orderId);
		return map;
	}

	public List<Map<String, String>> getOrderListForCertPrint(String orderId) {
		return (List<Map<String, String>>) baseDAO.queryForList(
				"TB_SELL_ORDER_LIST", "selectNewCardProdNames", orderId);
	}

	public SellOrderCardListDTO getCardImage(String cardNo) {
		return (SellOrderCardListDTO) baseDAO.queryForObject(
				"TB_SELL_ORDER_CARD_LIST.getCardImage", cardNo);
	}
    
	/**
	 * 接收卡片号段的逻辑代码
	 * */
    public void accept(AcceptOrderDTO acceptOrderDTO) throws Exception{
        Integer count1=(Integer)baseDAO.queryForObject(
	            "TB_SELL_ORDER_CARD_LIST.isAcceptOrderList", acceptOrderDTO);
        
        String cardBin = acceptOrderDTO.getStartCardNo().trim().substring(0, 5);
        BigInteger startCardNo = null;
        BigInteger endCardNo = null;
	    if(cardBin.equals(ConfigMakeCard.getCardBin())){
	    	startCardNo=new BigInteger(acceptOrderDTO.getStartCardNo().trim().substring(0,16));
	    	endCardNo=new BigInteger(acceptOrderDTO.getEndCardNo().trim().substring(0,16));
	    }else{
	    	startCardNo=new BigInteger(acceptOrderDTO.getStartCardNo().trim().substring(0,acceptOrderDTO.getStartCardNo().length()-1));
	    	endCardNo=new BigInteger(acceptOrderDTO.getEndCardNo().trim().substring(0,acceptOrderDTO.getStartCardNo().length()-1));
	    }  
         
        BigInteger cardNum=endCardNo.subtract(startCardNo).add(new BigInteger("1"));
        if(count1!=null){
            if(!cardNum.toString().equals(count1.toString())){
                throw new BizServiceException("该卡号段中的卡不存在订单中，请调整卡号段！");
            }
        }
        if(count1==null){
            throw new BizServiceException("该卡号段中的卡不存在订单中");
        }
        String count2=(String)baseDAO.queryForObject(
	            "TB_SELL_ORDER_CARD_LIST.isStockOrderList", acceptOrderDTO);
        int num=Integer.parseInt(count2);
        if(num>0){
            throw new BizServiceException("该卡号段中的卡已经部分接收，请调整卡号段！");
        }
        try{
        	orderBO.orderAcceptInStock(acceptOrderDTO);
        }catch(Exception e){
        	logger.info(e.getMessage());
		}
    }
    
    
    /**
	 * 接收全部卡片
	 * */
    public void acceptAll(AcceptOrderDTO acceptOrderDTO) throws Exception{
    	//取未接收卡明细
    	OrderReceiveCardListQueryDTO orderNotReceiveCardListQueryDTO = new OrderReceiveCardListQueryDTO();
    	orderNotReceiveCardListQueryDTO.setCurrentPage(1);
    	orderNotReceiveCardListQueryDTO.setDoCount(true);
    	orderNotReceiveCardListQueryDTO.setOrderId(acceptOrderDTO.getOrderId());
    	orderNotReceiveCardListQueryDTO.setOrderType(acceptOrderDTO.getOrderType());
    	orderNotReceiveCardListQueryDTO.setPageSize(100000);
    	orderNotReceiveCardListQueryDTO.setQueryAll(true);
    	orderNotReceiveCardListQueryDTO.setRowsDisplayed(50);
    	PageDataDTO orderNotReceiveCardList = getOrderNotReceiveListPageData(orderNotReceiveCardListQueryDTO);
    	
    	
    	for(int i=0;i<orderNotReceiveCardList.getData().size();i++){
    		Map<String, String> map = (Map<String, String>)orderNotReceiveCardList.getData().get(i);
    		String cardNo = map.get("cardNo");
    		AcceptOrderDTO newAcceptOrderDTO = acceptOrderDTO;
    		newAcceptOrderDTO.setStartCardNo(cardNo);
    		newAcceptOrderDTO.setEndCardNo(cardNo);
    		try{
    			orderBO.orderAcceptInStock(newAcceptOrderDTO);
            }catch(Exception e){
            	logger.info(e.getMessage());
    		}
    	}
    }
    
    
    /**
     * 检查已入库数量
     * */
    public Integer checkNum(SellOrderDTO sellOrderDTO){
        return (Integer)baseDAO.queryForObject(
                    "STOCK_ORDER_CARD_LIST.selectStockSuccessCardNoListAlready", sellOrderDTO);
    }
	/**
	 * 根据卡号段删除
	 * */
    public void delete(AcceptOrderDTO acceptOrderDTO){
        try{
	    EntityStockExample example=new EntityStockExample();
	    example.createCriteria().
                andCardNoGreaterThanOrEqualTo(acceptOrderDTO.getStartCardNo()).
                andCardNoLessThanOrEqualTo(acceptOrderDTO.getEndCardNo()).
                andEntityIdEqualTo(acceptOrderDTO.getEntity()).
                andDataStateEqualTo(DataBaseConstant.DATA_TYPE_YES).
                andFunctionRoleIdEqualTo(Const.FUNCTION_ROLE_SELLER);
        //更新接收数量
            SellOrder sellOrder=sellOrderDAO.selectByPrimaryKey(acceptOrderDTO.getOrderId());
            if(sellOrder.getOrigcardQuantity()!=null&&!"".equals(sellOrder.getOrigcardQuantity())){
                sellOrder.setOrigcardQuantity(String.valueOf(Integer.valueOf(sellOrder.getOrigcardQuantity())-
                        Integer.valueOf(acceptOrderDTO.getCardNum())));
                try{
                    sellOrderDAO.updateByPrimaryKey(sellOrder);
                }catch(Exception e){
                    logger.info(e.getMessage()); 
                }
            }
            List<EntityStock> list=(List<EntityStock>)entityStockDAO.selectByExample(example);
            EntityStock record=new EntityStock();
            for(int i=0;i<list.size();i++){
                record=(EntityStock)list.get(i);
                SellerExample ex=new SellerExample();
                ex.createCriteria().andEntityIdEqualTo(record.getEntityId());
                Seller seller=((List<Seller>)sellerDAO.selectByExample(ex)).get(0);
                record.setEntityId(seller.getFatherEntityId());
                record.setFunctionRoleId(Const.FUNCTION_ROLE_ISSUER);
                record.setStockState(OrderConst.CARD_STOCK_OUT);
                record.setFldResData2(null);
                try{
                    entityStockDAO.updateByPrimaryKey(record);
                }catch(Exception e){
                    logger.info(e.getMessage());
                }
            }
        }catch(Exception e){
        	logger.info(e.getMessage());
        }
    }
	/**
	 * 全部删除
	 * */
    @SuppressWarnings("unchecked")
    public void deleteAll(AcceptOrderDTO acceptOrderDTO){
        try{
            SellOrderList sellOrderList=(SellOrderList)baseDAO.
                        queryForList("STOCK_ORDER_LIST.selectOrderListByOrderIdByAcceptOrderDTO", 
                                acceptOrderDTO).get(0);
            SellOrderList bean=(SellOrderList)sellOrderListDAO.selectByPrimaryKey(sellOrderList.getOrderListId());
            List<String> listNo=(List<String>)baseDAO.queryForList("TB_SELL_ORDER_LIST.selectCardNosById", bean);
            EntityStockExample example=new EntityStockExample();
            example.createCriteria().andEntityIdEqualTo(acceptOrderDTO.getEntity()).
            andDataStateEqualTo(DataBaseConstant.DATA_TYPE_YES).
            andFunctionRoleIdEqualTo(Const.FUNCTION_ROLE_SELLER).andCardNoIn(listNo);
            SellOrder sellOrder=sellOrderDAO.selectByPrimaryKey(acceptOrderDTO.getOrderId());
            sellOrder.setOrigcardQuantity(OrderConst.INIT_NUM);
            try{
                sellOrderDAO.updateByPrimaryKey(sellOrder);
            }catch(Exception e){
                logger.info(e.getMessage());
            }
            List<EntityStock> list=(List<EntityStock>)entityStockDAO.selectByExample(example);
            EntityStock record=new EntityStock();
            for(int i=0;i<list.size();i++){
                record=(EntityStock)list.get(i);
                SellerExample ex=new SellerExample();
                ex.createCriteria().andEntityIdEqualTo(record.getEntityId());
                Seller seller=((List<Seller>)sellerDAO.selectByExample(ex)).get(0);
                record.setEntityId(seller.getFatherEntityId());
                record.setFunctionRoleId(Const.FUNCTION_ROLE_ISSUER);
                record.setStockState(OrderConst.CARD_STOCK_OUT);
                record.setFldResData2(null);
                try{
                    entityStockDAO.updateByPrimaryKey(record);
                }catch(Exception e){
                    logger.info(e.getMessage());
                }
            }
	}catch(Exception e ){
	    logger.info(e.getMessage());
	}
    }
	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public UserServiceDAO getUserServiceDAO() {
		return userServiceDAO;
	}

	public void setUserServiceDAO(UserServiceDAO userServiceDAO) {
		this.userServiceDAO = userServiceDAO;
	}

	public SellContractDAO getSellerContractDAO() {
		return sellerContractDAO;
	}

	public void setSellerContractDAO(SellContractDAO sellerContractDAO) {
		this.sellerContractDAO = sellerContractDAO;
	}

	public SellProdContractDAO getSellProdContractDAO() {
		return sellProdContractDAO;
	}

	public void setSellProdContractDAO(SellProdContractDAO sellProdContractDAO) {
		this.sellProdContractDAO = sellProdContractDAO;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public BaseDAO getBaseOrderDAO() {
		return baseOrderDAO;
	}

	public void setBaseOrderDAO(BaseDAO baseOrderDAO) {
		this.baseOrderDAO = baseOrderDAO;
	}

	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}

	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}

	public SellOrderListDAO getSellOrderListDAO() {
		return sellOrderListDAO;
	}

	public void setSellOrderListDAO(SellOrderListDAO sellOrderListDAO) {
		this.sellOrderListDAO = sellOrderListDAO;
	}

	public SellOrderCardListDAO getSellOrderCardListDAO() {
		return sellOrderCardListDAO;
	}

	public void setSellOrderCardListDAO(
			SellOrderCardListDAO sellOrderCardListDAO) {
		this.sellOrderCardListDAO = sellOrderCardListDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public SellerDAO getSellerDAO() {
		return sellerDAO;
	}

	public void setSellerDAO(SellerDAO sellerDAO) {
		this.sellerDAO = sellerDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

    /**
     * @return the orderBO
     */
    public OrderBO getOrderBO() {
        return orderBO;
    }

    /**
     * @param orderBO the orderBO to set
     */
    public void setOrderBO(OrderBO orderBO) {
        this.orderBO = orderBO;
    }

    /**
     * @return the entityStockDAO
     */
    public EntityStockDAO getEntityStockDAO() {
        return entityStockDAO;
    }

    /**
     * @param entityStockDAO the entityStockDAO to set
     */
    public void setEntityStockDAO(EntityStockDAO entityStockDAO) {
        this.entityStockDAO = entityStockDAO;
    }

	public List<EntityStock> getSelectIsCARDP001(OrderReadyDTO orderReadyDTO){
		return (List<EntityStock>) entityStockDAO.abatorgenerated_selectIsCARDP001(orderReadyDTO);
	}

}
