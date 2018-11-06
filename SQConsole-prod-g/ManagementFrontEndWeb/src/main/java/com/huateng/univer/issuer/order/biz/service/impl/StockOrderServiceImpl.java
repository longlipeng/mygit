package com.huateng.univer.issuer.order.biz.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.allinfinance.cardnotmp.dao.CardNotmpDao;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.service.uploadFile.dto.UploadCardFileDTO;
import com.allinfinance.univer.businessbatch.dto.BatchFileDTO;
import com.allinfinance.univer.entity.stock.dto.EntityStockDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCompositeDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderFlowDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.DictInfoConstants;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.AccBatchFileDAO;
import com.huateng.framework.ibatis.dao.BatchSumDAO;
import com.huateng.framework.ibatis.dao.CustomerDAO;
import com.huateng.framework.ibatis.dao.LoyaltyContractDAO;
import com.huateng.framework.ibatis.dao.ProductDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderListDAO;
import com.huateng.framework.ibatis.model.AccBatchFile;
import com.huateng.framework.ibatis.model.AccBatchFileExample;
import com.huateng.framework.ibatis.model.BatchSum;
import com.huateng.framework.ibatis.model.Customer;
import com.huateng.framework.ibatis.model.CustomerExample;
import com.huateng.framework.ibatis.model.DeliveryContact;
import com.huateng.framework.ibatis.model.LoyaltyContract;
import com.huateng.framework.ibatis.model.LoyaltyContractExample;
import com.huateng.framework.ibatis.model.LoyaltyProdContract;
import com.huateng.framework.ibatis.model.Product;
import com.huateng.framework.ibatis.model.ProductCardBin;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderFlow;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.ConfigMakeCard;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.StringUtil;
import com.huateng.univer.issuer.order.biz.bo.StockOrderCommonService;
import com.huateng.univer.issuer.order.biz.service.StockOrderService;
import com.huateng.univer.issuer.product.service.ProductService;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.order.business.bo.OrderCountTotalPrice;
import com.huateng.univer.order.business.service.OrderCardListService;
import com.huateng.univer.seller.seller.biz.service.SellerService;

/**
 * 库存订单服务
 * 
 * @author xxl
 * 
 */
public class StockOrderServiceImpl implements StockOrderService {

	private Logger logger = Logger.getLogger(this.getClass());
	private PageQueryDAO pageQueryDAO;
	private CommonsDAO commonsDAO;
	private ProductService productService;
	private ProductDAO productDAO;
	private CustomerDAO customerDAO;

	private SellOrderDAO sellOrderDAO;
	private SellOrderListDAO sellOrderListDAO;
	private StockOrderCommonService stockOrderCommonService;
	private OrderCardListService orderCardListService;
	private OrderBaseQueryBO orderBaseQueryBO;
	private SellerService sellerService;
	private OrderCountTotalPrice orderCountTotalPrice;
	private LoyaltyContractDAO loyaltyContractDAO;
	private CardNotmpDao cardNoTmp;
	private BaseDAO baseDAO;
	private BatchSumDAO batchSumDAO;
	private AccBatchFileDAO  accBatchFileDAO;
	
	
	public AccBatchFileDAO getAccBatchFileDAO() {
		return accBatchFileDAO;
	}

	public void setAccBatchFileDAO(AccBatchFileDAO accBatchFileDAO) {
		this.accBatchFileDAO = accBatchFileDAO;
	}

	public BatchSumDAO getBatchSumDAO() {
		return batchSumDAO;
	}

	public void setBatchSumDAO(BatchSumDAO batchSumDAO) {
		this.batchSumDAO = batchSumDAO;
	}

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public CardNotmpDao getCardNoTmp() {
		return cardNoTmp;
	}

	public void setCardNoTmp(CardNotmpDao cardNoTmp) {
		this.cardNoTmp = cardNoTmp;
	}

	public LoyaltyContractDAO getLoyaltyContractDAO() {
		return loyaltyContractDAO;
	}

	public void setLoyaltyContractDAO(LoyaltyContractDAO loyaltyContractDAO) {
		this.loyaltyContractDAO = loyaltyContractDAO;
	}

	public OrderCountTotalPrice getOrderCountTotalPrice() {
		return orderCountTotalPrice;
	}

	public void setOrderCountTotalPrice(
			OrderCountTotalPrice orderCountTotalPrice) {
		this.orderCountTotalPrice = orderCountTotalPrice;
	}

	public SellerService getSellerService() {
		return sellerService;
	}

	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	public PageDataDTO inquery(SellOrderQueryDTO sellOrderQueryDTO)
			throws BizServiceException {
		try {
			// return pageQueryDAO.query("STOCK_ORDER.selectStockOrderList",
			// sellOrderQueryDTO);
			dealFailMakeCardOrders();
			return pageQueryDAO.query("STOCK_ORDER.selectStockOrders",
					sellOrderQueryDTO);

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询制卡订单失败！");
		}
	}
	/*处理制卡订单中的处理失败之后，后台制卡手工重制之后的状态*/
	public void dealFailMakeCardOrders(){
		SellOrder order = new SellOrder();
		order.setOrderType("04");
		order.setOrderState(OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL);
		List<BatchSum> batchNos = sellOrderDAO.selectOrderWithBatchSum(order);
		if(batchNos!=null && batchNos.size()>=0){
			for(int i=0;i<batchNos.size();i++){
				BatchSum sum = batchSumDAO.selectByPrimaryKey(batchNos.get(i).getBatchNo());
				if(BatchFileDTO.BATCH_STATE_END.equals(sum.getBatchState())){
					SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(sum
							.getOrderId());
					if (!OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL.equals(sellOrder
							.getOrderState())) {
						return ;
					}
						AccBatchFileExample accBatchFileExample = new AccBatchFileExample();
						accBatchFileExample.createCriteria().andOrderIdEqualTo(
								sellOrder.getOrderId());
						List<AccBatchFile> accBatchFiles = accBatchFileDAO
								.selectByExample(accBatchFileExample);
						if (accBatchFiles == null || accBatchFiles.size() <= 0)
							return;
						
						// 文件状态 00:初始 01:待处理 02:处理中 03:结束 
						// 文件处理结束，插入订单流程
						if("03".equals(accBatchFiles.get(0).getFileStat())){
							if(Long.parseLong(accBatchFiles.get(0).getTotCnt().trim())== Long.parseLong(accBatchFiles.get(0).getSucCnt().trim())){
								sellOrder.setOrderState(OrderConst.ORDER_STATE_CARDGFILE_MAKING);
							}else{
								sellOrder.setOrderState(OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL);
							}
							sellOrder.setModifyTime(DateUtil.getCurrentTime());
							sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
					}
				}
			}
			
		}
	
		
	}
	public SellOrderDTO load(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			SellOrder sellOrder = sellOrderDAO.selectByPrimaryKey(sellOrderDTO
					.getOrderId());
			ReflectionUtil.copyProperties(sellOrder, sellOrderDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看订单信息失败！");
		}
		return sellOrderDTO;
	}

	public SellOrderCompositeDTO view(
			SellOrderCompositeDTO sellOrderCompositeDTO)
			throws BizServiceException {
		try {
			SellOrderCompositeDTO resultDTO = new SellOrderCompositeDTO();
			SellOrderDTO sellOrderDTO = sellOrderCompositeDTO.getSellOrderDTO();
			String orderId = sellOrderDTO.getOrderId();
			// 基本信息
			SellOrder sellOrder = stockOrderCommonService
					.getSellOrderById(orderId);
			String orderType = sellOrder.getOrderType();
			//perFlag=1 表示订单提交或退回
			if("1".equals(sellOrderDTO.getPerFlag())
			        && (OrderConst.ORDER_STATE_OPEN_ACCOUNT.equals(sellOrder.getOrderState())
					|| OrderConst.ORDER_STATE_ORDER_PROCESSING.equals(sellOrder.getOrderState())
					|| OrderConst.ORDER_STATE_ORDER_PROCESS_FAIL.equals(sellOrder.getOrderState()))){
				throw new BizServiceException("订单已提交，不能做提交或退回操作");
			}
			// 动态表查询
			if (OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN
					.equals(orderType)
					|| OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
							.equals(orderType)
					|| OrderConst.ORDER_TYPE_ORDER_MAKE_CARD.equals(orderType)) {
				sellOrderDTO.setDynamicFirstEntityTable("TB_ISSUER");
				sellOrderDTO.setDynamicProcessEntityTable("TB_ISSUER");

				sellOrderDTO.setDynamicFirstEntitycolumn("T6.ISSUER_NAME");
				sellOrderDTO.setDynamicprocessEntitycolumn("T7.ISSUER_NAME");
			} else if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
					.equals(orderType)
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(orderType)) {

				sellOrderDTO.setDynamicFirstEntityTable("TB_SELLER");
				sellOrderDTO.setDynamicProcessEntityTable("TB_ISSUER");

				sellOrderDTO.setDynamicFirstEntitycolumn("T6.SELLER_NAME");
				sellOrderDTO.setDynamicprocessEntitycolumn("T7.ISSUER_NAME");
			}

			resultDTO.setSellOrderDTO(stockOrderCommonService
					.getSellOrderDTOByDto(sellOrderDTO));
			String firstEntityId = sellOrder.getFirstEntityId();
			if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
					.equals(orderType)
					|| OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
							.equals(orderType)) {
				// 初始化
				SellerDTO sellerDTO = sellOrderCompositeDTO.getSellerDTO();

				sellerDTO.setEntityId(firstEntityId);

				sellerDTO = sellerService.getSellerByEntityId(sellerDTO);

				resultDTO.setSellerDTO(sellerDTO);
				sellOrderDTO.setFirstEntityId(resultDTO.getSellOrderDTO()
						.getFirstEntityId());
				sellOrderDTO.setProcessEntityId(resultDTO.getSellOrderDTO()
						.getProcessEntityId());
				List<ProductDTO> productDTOs_need = orderBaseQueryBO
						.getProductByContract(sellOrderDTO,
								DataBaseConstant.SELL_CONTRACT_ISSUER);
				if (null == productDTOs_need) {
					return resultDTO;
				}

				/***
				 *
				 */
				List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();

				for (ProductDTO productDTO_temp : productDTOs_need) {
					if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_SIGN
							.equals(orderType)) {
						if (DictInfoConstants.SIGN_ORDER_MUST
								.equals(productDTO_temp.getOnymousStat())) {
							productDTOs.add(productDTO_temp);
						}
					} else {
						if (!DictInfoConstants.SIGN_ORDER_MUST
								.equals(productDTO_temp.getOnymousStat())) {
							productDTOs.add(productDTO_temp);
						}
					}
				}
				/***
				 * 由于产品的面额并没有除以100在这里将面额值进行转换
				 */
				// if(productDTOs.size()>0){
				// for (ProductDTO temp:productDTOs){
				// List<ProdFaceValueDTO> prodFaceValueList =
				// temp.getProdFaceValueDTO();
				// for (ProdFaceValueDTO faceValueDTO:prodFaceValueList){
				// faceValueDTO.setFaceValue(Amount.getReallyAmount(faceValueDTO.getFaceValue()));
				// }

				// }
				// }
				ProductDTO productDTO_choose = new ProductDTO();
				resultDTO.setProductDTOs(productDTOs);
				if (productDTOs.size() > 0) {
					if (StringUtil.isEmpty(sellOrderDTO.getProductId())) {
						productDTO_choose = productDTOs.get(0);

					} else {
						for (ProductDTO productDTO : productDTOs) {
							if (productDTO.getProductId().equals(
									sellOrderDTO.getProductId())) {
								productDTO_choose = productDTO;
								break;
							}
						}
					}
				}
				resultDTO.setProductDTO(productDTO_choose);
			}
			if (OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN
					.equals(orderType)
					|| OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN
							.equals(orderType)) {
				sellOrderDTO.setFirstEntityId(resultDTO.getSellOrderDTO()
						.getFirstEntityId());
				sellOrderDTO.setProcessEntityId(resultDTO.getSellOrderDTO()
						.getProcessEntityId());
				List<ProductDTO> productDTOs_need = orderBaseQueryBO
						.getProductByLoyaltyContract(sellOrderDTO);
				if (null == productDTOs_need) {
					return resultDTO;
				}

				/***
				 *
				 */
				List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();

				for (ProductDTO productDTO_temp : productDTOs_need) {
					if (OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN
							.equals(orderType)) {
						if (DictInfoConstants.SIGN_ORDER_MUST
								.equals(productDTO_temp.getOnymousStat())) {
							productDTOs.add(productDTO_temp);
						}
					} else {
						if (!DictInfoConstants.SIGN_ORDER_MUST
								.equals(productDTO_temp.getOnymousStat())) {
							productDTOs.add(productDTO_temp);
						}
					}
				}
				/***
				 * 由于产品的面额并没有除以100在这里将面额值进行转换
				 */
				// if(productDTOs.size()>0){
				// for (ProductDTO temp:productDTOs){
				// List<ProdFaceValueDTO> prodFaceValueList =
				// temp.getProdFaceValueDTO();
				// for (ProdFaceValueDTO faceValueDTO:prodFaceValueList){
				// faceValueDTO.setFaceValue(Amount.getReallyAmount(faceValueDTO.getFaceValue()));
				// }

				// }
				// }
				ProductDTO productDTO_choose = new ProductDTO();
				resultDTO.setProductDTOs(productDTOs);
				if (productDTOs.size() > 0) {
					if (StringUtil.isEmpty(sellOrderDTO.getProductId())) {
						productDTO_choose = productDTOs.get(0);

					} else {
						for (ProductDTO productDTO : productDTOs) {
							if (productDTO.getProductId().equals(
									sellOrderDTO.getProductId())) {
								productDTO_choose = productDTO;
								break;
							}
						}
					}
				}
				resultDTO.setProductDTO(productDTO_choose);
			}
			// 流程信息
			resultDTO.setSellOrderFlowList(stockOrderCommonService
					.getSellOrderFlowList(orderId));

			// 订单明细信息
			if (OrderConst.ORDER_TYPE_ORDER_BUY_SELLER_ISSUER_UNSIGN
					.equals(orderType)) {
				resultDTO.setSellOrderList(orderBaseQueryBO
						.queryOrderListByOrderbyId(sellOrderCompositeDTO
								.getSellOrderListQueryDTO()));
			}

			// 订单卡明细信息
			resultDTO.setSellOrderCardList(orderCardListService
					.getOrderCardListPageData(sellOrderCompositeDTO
							.getSellOrderCardListQueryDTO()));
			//录入卡
			resultDTO.setOrderInputCardPackageList(orderCardListService
					.getOrderInputCardListPageData(sellOrderCompositeDTO
							.getSellOrderInputCardListDTO()));
			
			
			//订单卡接收明细信息
			PageDataDTO pageDataDTO = orderCardListService
            .getOrderReceiveListPageData(sellOrderCompositeDTO
                    .getOrderReceiveCardListQueryDTO());
			resultDTO.setSellOrderReceCardList(pageDataDTO);
			return resultDTO;
		} catch (BizServiceException b){
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看订单信息失败！");
		}

	}
	
	public List<SellOrderInputCardListDTO> orderInputCardList(SellOrderInputCardListDTO sellOrderInputCardListDTO) throws BizServiceException{
		
		List<SellOrderInputCardListDTO> list=baseDAO.queryForList(
				"STOCK_ORDER_CARD_LIST.selectOrderInputCardListData",
				sellOrderInputCardListDTO);
			return list;
	}
	

	public SellOrderFlowDTO viewOrderFlow(SellOrderFlowDTO sellOrderFlowDTO)
			throws BizServiceException {
		try {
			sellOrderFlowDTO = stockOrderCommonService
					.viewOrderFlow(sellOrderFlowDTO.getOrderFlowId());
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return sellOrderFlowDTO;
	}

	/**
	 * 添加编辑前的初始化
	 */
	public SellOrderInputDTO initAdd(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException {

		SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
		// 该发卡机构下面所有的产品以及代发卡产品
		List<ProductDTO> productDTOs =null;
		productDTOs = productService
					.getProductsForStockOrder(sellOrderInputDTO
							.getDefaultEntityId());
		
		sellOrderInputDTO.setProductDTOs(productDTOs);

		if (null != productDTOs && productDTOs.size() > 0) {
			// 被选中的产品
			ProductDTO choosedProductDTO = new ProductDTO();
			String choosedProductId = sellOrderDTO.getProductId();
			if (StringUtil.isEmpty(choosedProductId)) {
				choosedProductDTO = productDTOs.get(0);
			} else {
				for (ProductDTO dto : productDTOs) {
					if (choosedProductId.equals(dto.getProductId())) {
						choosedProductDTO = dto;
						break;
					}
				}
			}
			sellOrderInputDTO.setProductDTO(choosedProductDTO);
		}
		return sellOrderInputDTO;
	}
	
	
	/**
	 * 添加编辑前的初始化
	 */
	public SellOrderInputDTO unsignOrderinitAdd(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException {

		SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
		// 该发卡机构下面所有的产品以及代发卡产品
		List<ProductDTO> productDTOs =null;
		
			 productDTOs = productService
						.getProductsForUnsignOrder(sellOrderInputDTO);
		
		
		sellOrderInputDTO.setProductDTOs(productDTOs);

		if (null != productDTOs && productDTOs.size() > 0) {
			// 被选中的产品
			ProductDTO choosedProductDTO = new ProductDTO();
			String choosedProductId = sellOrderDTO.getProductId();
			if (StringUtil.isEmpty(choosedProductId)) {
				choosedProductDTO = productDTOs.get(0);
			} else {
				for (ProductDTO dto : productDTOs) {
					if (choosedProductId.equals(dto.getProductId())) {
						choosedProductDTO = dto;
						break;
					}
				}
			}
			sellOrderInputDTO.setProductDTO(choosedProductDTO);
			// 取库存信息
			EntityStockDTO entityStockDTO = new EntityStockDTO();
			String productId = null;
			if(null==sellOrderDTO.getProductId()||"".equals(sellOrderDTO.getProductId())){
				productId = productDTOs.get(0).getProductId();
			}else{
				productId=sellOrderDTO.getProductId();
			}
			 
			entityStockDTO.setQueryAll(true);
			entityStockDTO.setEntityId(sellOrderInputDTO.getDefaultEntityId());
			entityStockDTO.setProductId(productId);
			entityStockDTO.setFunctionRoleId("3");
			PageDataDTO pageDataDTO;
			try {
				pageDataDTO = orderBaseQueryBO
						.getProductStockByDTOForUnsignOrder(entityStockDTO);
				sellOrderInputDTO.setProductStocks(pageDataDTO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BizServiceException("查询库存失败！");
			}
			
			

			
			
		}
		//查询客户信息
		if(null!=sellOrderInputDTO.getSellOrderDTO().getFirstEntityId()&&!"".equals(sellOrderInputDTO.getSellOrderDTO().getFirstEntityId())){
			CustomerDTO customerDTO= new CustomerDTO();
			customerDTO.setEntityId(sellOrderInputDTO.getSellOrderDTO().getFirstEntityId());		
			CustomerExample example = new CustomerExample();
			com.huateng.framework.ibatis.model.CustomerExample.Criteria criteria = example
					.createCriteria();
			criteria.andEntityIdEqualTo(customerDTO.getEntityId());
			
			List<Customer> customerList = customerDAO.selectByExample(example);
			if (customerList.size() > 0) {
				customerList.get(0);
				ReflectionUtil.copyProperties(customerList.get(0), customerDTO);
				sellOrderInputDTO.setCustomerDTO(customerDTO);
			}
		}
		return sellOrderInputDTO;
	}

	/**
	 * 制卡订单录入
	 * @throws BizServiceException 
	 */
	public SellOrderInputDTO insert(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		
		SellOrderInputDTO sid=new SellOrderInputDTO();
		UploadCardFileDTO uploadCardFileDTO=sellOrderDTO.getUploadCardFileDTO();
		UploadCardFileDTO ucfd;
		String filename=commonsDAO
				.getNextValueOfSequence("TB_SELL_ORDER");
		if(uploadCardFileDTO!=null){
			if(uploadCardFileDTO.getBinType()!=null&&uploadCardFileDTO.getBinType().equals("1")){
				uploadCardFileDTO.setOrderId(filename);
				try{
				ucfd=this.uploadCardFile(uploadCardFileDTO,sellOrderDTO.getProductId());
				if(ucfd!=null&&ucfd.getRepeatedCardNo()!=null&&ucfd.getRepeatedCardNo().size()>0)
				{
					sellOrderDTO.setUploadCardFileDTO((ucfd));
					sid.setSellOrderDTO(sellOrderDTO);
					//new BizServiceException("卡号重复或卡号已存在！");
					return sid;
				}
				}catch(BizServiceException b)
				{
					throw b;
				}/*finally{
					return sid;
				}*/
			}
		}
		try {
			SellOrder sellOrder = new SellOrder();
			ReflectionUtil.copyProperties(sellOrderDTO, sellOrder);

			sellOrder.setOrderId(filename);

			// 面额
			if (StringUtil.isNotEmpty(sellOrder.getFaceValue())) {
				sellOrder.setFaceValue(Amount.getDataBaseAmount(sellOrder
						.getFaceValue()));
			}
			sellOrder.setOrderDate(DateUtil.getFormatTime(sellOrder
					.getOrderDate()));
			sellOrder.setValidityPeriod(DateUtil.getFormatTime(sellOrder
					.getValidityPeriod()));

			String productId = sellOrderDTO.getProductId();
			Product product = productDAO.selectByPrimaryKey(productId);

			sellOrder.setFirstEntityId(sellOrderDTO.getDefaultEntityId());
			if (product.getEntityId().equals(product.getProductDefineIssuer())
					|| product.getProductDefineIssuer().equals(
							sellOrderDTO.getDefaultEntityId())) {
				// 订单处理方为发卡机构自身
				sellOrder.setProcessEntityId(sellOrderDTO.getDefaultEntityId());
				sellOrder.setOrderType(OrderConst.ORDER_TYPE_ORDER_MAKE_CARD);
			} else {
				// 代发卡，采购订单
				// 订单处理方为上级发卡机构
				LoyaltyContractExample example = new LoyaltyContractExample();
				example.createCriteria().andContractBuyerEqualTo(
						sellOrderDTO.getDefaultEntityId()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL)
						.andExpiryDateGreaterThan(DateUtil.getCurrentDateStr());
				List<LoyaltyContract> loyaltyContracts = loyaltyContractDAO
						.selectByExample(example);
				if (null != loyaltyContracts && loyaltyContracts.size() > 0) {
					sellOrder.setProcessEntityId(loyaltyContracts.get(0)
							.getContractSeller());
					sellOrderDTO.setLoyaltyContractId(loyaltyContracts.get(0)
							.getLoyaltyContractId());
					sellOrderDTO.setDeliveryFee(loyaltyContracts.get(0)
							.getDeliveryFee());
				}
				// sellOrder.setProcessEntityId(product.getProductDefineIssuer());
				sellOrder
						.setOrderType(OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_UNSIGN);

				String sellerId = sellOrderDTO.getDefaultEntityId();
				String loyaltyContractId = sellOrderDTO.getLoyaltyContractId();
				logger.debug(loyaltyContractId);
				String orderId = commonsDAO
						.getNextValueOfSequence("TB_SELL_ORDER");
				Integer cardQuantity = 0;
				String deliveryFee = sellOrderDTO.getDeliveryFee();
				// 合同
				// LoyaltyContract loyaltyContract = orderBaseQueryBO
				// .getLoyaltyContractById(loyaltyContractId);
				LoyaltyProdContract prodContract = orderBaseQueryBO
						.getLoyaltyProductContractByExample(loyaltyContractId,
								productId);
				// 送货信息
				DeliveryContact contact = orderBaseQueryBO
						.getDeliveryContactForLoyaltyBuyOrder(sellerId);
				sellOrder.setDeliveryMeans("4");
				sellOrder.setDeliveryFee(deliveryFee);
				if (null != contact) {
					sellOrder.setDeliveryPoint(contact.getDeliveryPointId());
					sellOrder.setOrderContact(contact.getDeliveryContactId());
				}
				sellOrder.setCardIssueFee(prodContract.getCardFee());
				sellOrder.setAnnualFee(prodContract.getAnnualFee());
				// 生成订单明细
				SellOrderList sellOrderList = new SellOrderList();
				sellOrderList.setCardAmount(sellOrder.getCardQuantity());
				sellOrderList.setOrderListId(commonsDAO
						.getNextValueOfSequence("TB_SELL_ORDER_LIST"));
				sellOrderList.setOrderId(sellOrder.getOrderId());
				sellOrderList.setRealAmount("0");
				sellOrderList.setCardLayoutId(sellOrder.getCardLayoutId());
				sellOrderList.setFaceValueType(sellOrder.getFaceValueType());
				sellOrderList.setFaceValue(sellOrder.getFaceValue());
				sellOrderList.setPackageFee("0");
				sellOrderList.setTotalPrice(sellOrder.getTotalPrice());
				sellOrderList.setValidityPeriod(sellOrder.getValidityPeriod());

				sellOrderList.setCreateUser(sellOrderDTO.getLoginUserId());
				sellOrderList.setCreateTime(DateUtil.getCurrentTime());
				sellOrderList.setModifyUser(sellOrderDTO.getLoginUserId());
				sellOrderList.setModifyTime(DateUtil.getCurrentTime());
				sellOrderList.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

				sellOrderListDAO.insert(sellOrderList);
			}

			sellOrder.setOrderState(OrderConst.ORDER_STATE_DRAFT);

			sellOrder.setCreateUser(sellOrderDTO.getLoginUserId());
			sellOrder.setCreateTime(DateUtil.getCurrentTime());
			sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());
			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			sellOrder.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			sellOrderDAO.insert(sellOrder);
			if (product.getEntityId().equals(product.getProductDefineIssuer())
					|| product.getProductDefineIssuer().equals(
							sellOrderDTO.getDefaultEntityId())) {
				orderCountTotalPrice
						.countTotalPriceForSellOrderMakeCard(sellOrder
								.getOrderId());
			} else {
				orderCountTotalPrice
						.countTotalPriceForSellOrderUnsign(sellOrder
								.getOrderId());
			}
			// 添加订单流程信息
			SellOrderFlow orderFlow = new SellOrderFlow();
			orderFlow.setOrderFlowId(commonsDAO
					.getNextValueOfSequence("TB_SELL_ORDER_FLOW"));
			orderFlow.setOrderId(sellOrder.getOrderId());
			orderFlow.setEntityId(sellOrderDTO.getDefaultEntityId());
			orderFlow.setOrderFlowNode(OrderConst.ORDER_FLOW_INPUT);
			orderFlow.setOperateType(OrderConst.ORDER_FLOW_OPRATION_ADD);
			orderFlow.setCreateTime(DateUtil.getCurrentTime());
			orderFlow.setModifyTime(DateUtil.getCurrentTime());
			orderFlow.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			orderFlow.setCreateUser(sellOrderDTO.getLoginUserId());
			orderFlow.setModifyUser(sellOrderDTO.getLoginUserId());
			stockOrderCommonService.addOrderFlow(orderFlow);
			return null;
//		} catch (BizServiceException be) {
//			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加制卡订单失败！");
		}
	}

	public SellOrderInputDTO update(SellOrderDTO sellOrderDTO)
			throws BizServiceException {

		try {
			SellOrder sellOrder = new SellOrder();
			ReflectionUtil.copyProperties(sellOrderDTO, sellOrder);
			// 面额
			if (StringUtil.isNotEmpty(sellOrder.getFaceValue())) {
				sellOrder.setFaceValue(Amount.getDataBaseAmount(sellOrder
						.getFaceValue()));
			}
			// 年费
			if (StringUtil.isNotEmpty(sellOrder.getAnnualFee())) {
				sellOrder.setAnnualFee(Amount.getDataBaseAmount(sellOrder
						.getAnnualFee()));
			}
			// 卡费
			if (StringUtil.isNotEmpty(sellOrder.getCardIssueFee())) {
				sellOrder.setCardIssueFee(Amount.getDataBaseAmount(sellOrder
						.getCardIssueFee()));
			}
			// 送货费
			if (StringUtil.isNotEmpty(sellOrder.getDeliveryFee())) {
				sellOrder.setDeliveryFee(Amount.getDataBaseAmount(sellOrder
						.getDeliveryFee()));
			}
			if (StringUtil.isNotEmpty(sellOrder.getOrderDate())) {
				sellOrder.setOrderDate(DateUtil.getFormatTime(sellOrder
						.getOrderDate()));
			}
			if (StringUtil.isNotEmpty(sellOrder.getValidityPeriod())) {
				sellOrder.setValidityPeriod(DateUtil.getFormatTime(sellOrder
						.getValidityPeriod()));
			}

			sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());
			sellOrder.setModifyTime(DateUtil.getCurrentTime());

			sellOrderDAO.updateByPrimaryKeySelective(sellOrder);
			String productId = sellOrderDTO.getProductId();
			Product product = productDAO.selectByPrimaryKey(productId);

			sellOrder.setFirstEntityId(sellOrderDTO.getDefaultEntityId());
			// 代发订单情况下
			if (!product.getEntityId().equals(product.getProductDefineIssuer())
					&& product.getEntityId().equals(
							sellOrderDTO.getDefaultEntityId())) {
				// 生成订单明细
				SellOrderList curSellOrderList = orderBaseQueryBO
						.getSellOrderListByOrderId(sellOrderDTO.getOrderId())
						.get(0);
				SellOrderList sellOrderList = new SellOrderList();
				sellOrderList.setCardAmount(sellOrder.getCardQuantity());
				sellOrderList.setCardLayoutId(sellOrder.getCardLayoutId());
				sellOrderList.setFaceValueType(sellOrder.getFaceValueType());
				sellOrderList.setFaceValue(sellOrder.getFaceValue());
				sellOrderList.setTotalPrice(sellOrder.getTotalPrice());
				sellOrderList.setValidityPeriod(sellOrder.getValidityPeriod());

				sellOrderList.setModifyUser(sellOrderDTO.getLoginUserId());
				sellOrderList.setModifyTime(DateUtil.getCurrentTime());
				sellOrderList.setOrderListId(curSellOrderList.getOrderListId());
				sellOrderListDAO.updateByPrimaryKeySelective(sellOrderList);
			}
			if (!product.getEntityId().equals(product.getProductDefineIssuer())
					&& product.getEntityId().equals(
							sellOrderDTO.getDefaultEntityId())) {
				orderCountTotalPrice
						.countTotalPriceForSellOrderUnsign(sellOrder
								.getOrderId());
			} else {
				orderCountTotalPrice
						.countTotalPriceForSellOrderMakeCard(sellOrder
								.getOrderId());
			}
			return null;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新制卡订单失败！");
		}
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
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

	public StockOrderCommonService getStockOrderCommonService() {
		return stockOrderCommonService;
	}

	public void setStockOrderCommonService(
			StockOrderCommonService stockOrderCommonService) {
		this.stockOrderCommonService = stockOrderCommonService;
	}

	public OrderCardListService getOrderCardListService() {
		return orderCardListService;
	}
	
	

	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public void setOrderCardListService(
			OrderCardListService orderCardListService) {
		this.orderCardListService = orderCardListService;
	}

	public OrderBaseQueryBO getOrderBaseQueryBO() {
		return orderBaseQueryBO;
	}

	public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
		this.orderBaseQueryBO = orderBaseQueryBO;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	public UploadCardFileDTO uploadCardFile(UploadCardFileDTO uploadCardFileDTO,String productId)
			throws BizServiceException {

		/*byte[] content = uploadCardFileDTO.getContent();
		if(content!=null&&content.length!=0){
			try {
				String orderId=uploadCardFileDTO.getOrderId();
				File file = new File(ConfigMakeCard.getCardNosFilePath()+uploadCardFileDTO.getEntityId()+"/");
				if(!file.exists()){
					file.mkdirs();
				}
				FileOutputStream os = new FileOutputStream(ConfigMakeCard.getCardNosFilePath()+uploadCardFileDTO.getEntityId()+"/"+orderId+".xls");
				os.write(content);
				os.flush();
				os.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			throw new BizServiceException("无数据可写入文件！");
		}*/
		        
		       UploadCardFileDTO ucfd=new UploadCardFileDTO();
		       List<String> repeatedCardNos=new ArrayList<String>();
				List<String> cardNos= uploadCardFileDTO.getCardNos();
			    List <String>sucessCard=new ArrayList<String>();
				if(cardNos==null||cardNos.size()==0){
					throw new BizServiceException("输入卡号为空");
				}else{
					
					ProductCardBin productCardBin = (ProductCardBin) baseDAO
							.queryForObject("PRODUCT.getProductCardBinSerialNumberForLock",
									productId);
					String cardBin = productCardBin.getRealCardBin();
					for(int i=0;i<cardNos.size();i++){
						if(!cardNos.get(i).substring(0, cardBin.length()).equals(cardBin)){
							try {
								cardNoTmp.delete(uploadCardFileDTO);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							throw new BizServiceException("该产品的卡bin与上传文件中卡号卡bin不符！ ！");
						}
						if(cardNos.get(i).length()!=16){
							try {
								cardNoTmp.delete(uploadCardFileDTO);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							throw new BizServiceException("上传文件中卡号必须是16位！ ！");
						}
						UploadCardFileDTO dto = new UploadCardFileDTO();
						dto.setCardNo(cardNos.get(i));
						dto.setOrderId(uploadCardFileDTO.getOrderId());
						try {
							cardNoTmp.insert(dto);
						    sucessCard.add(cardNos.get(i));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							repeatedCardNos.add(cardNos.get(i));	
						}
					}
					if(repeatedCardNos!=null&&repeatedCardNos.size()>0&&sucessCard!=null)
					{
						for(int i=0;i<sucessCard.size();i++){
							
							UploadCardFileDTO dto = new UploadCardFileDTO();
							dto.setCardNo(sucessCard.get(i));
							try {
								cardNoTmp.deleteAll(dto);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
						}
							
						try {
							cardNoTmp.delete(uploadCardFileDTO);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					ucfd.setRepeatedCardNo(repeatedCardNos);
					return ucfd;
					
				}
			
		
	}

}
