package com.allinfinance.prepay.order.business.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.mapper.svc_mng.ProductMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellContractMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderCardListMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderListMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderOrigCardListMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellProdContractMapper;
import com.allinfinance.prepay.mapper.svc_mng.UserMapper;
import com.allinfinance.prepay.model.Product;
import com.allinfinance.prepay.model.ProductExample;
import com.allinfinance.prepay.model.SellContract;
import com.allinfinance.prepay.model.SellContractExample;
import com.allinfinance.prepay.model.SellOrder;
import com.allinfinance.prepay.model.SellOrderCardList;
import com.allinfinance.prepay.model.SellOrderCardListExample;
import com.allinfinance.prepay.model.SellOrderExample;
import com.allinfinance.prepay.model.SellOrderList;
import com.allinfinance.prepay.model.SellOrderListExample;
import com.allinfinance.prepay.model.SellOrderOrigCardList;
import com.allinfinance.prepay.model.SellOrderOrigCardListExample;
import com.allinfinance.prepay.model.SellProdContract;
import com.allinfinance.prepay.model.SellProdContractExample;
import com.allinfinance.prepay.model.User;
import com.allinfinance.prepay.model.UserExample;
import com.allinfinance.prepay.service.BankService;
import com.allinfinance.prepay.service.ProductService;
import com.allinfinance.prepay.utils.Amount;
import com.allinfinance.prepay.utils.DataBaseConstant;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.OrderConst;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
@Service
public class OrderBaseQueryBO {
	@Autowired
	private SellOrderMapper sellOrderMapper;
	@Autowired
	private SellOrderCardListMapper sellOrderCardListMapper;
	@Autowired
	private BankService bankService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private SellContractMapper  sellContractMapper;
	@Autowired 
	private SellProdContractMapper sellProdContractMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductService productService;
	@Autowired
	private SellOrderListMapper sellOrderListMapper;
	@Autowired
	private SellOrderOrigCardListMapper sellOrderOrigCardListMapper;
	
	public SellOrder getSellOrder(String orderId) throws Exception {
		SellOrderExample ex=new SellOrderExample();
		ex.createCriteria().andOrderIdEqualTo(orderId);
		SellOrder order=sellOrderMapper.selectByExample(ex).get(0);
		return order;
	}
	
	public String getSellOrderCardListByOrderId(String orderId)
			throws Exception {
		SellOrderCardListExample sellOrderExample = new SellOrderCardListExample();
		sellOrderExample.createCriteria().andDataStateEqualTo(
				"1").andOrderIdEqualTo(orderId);
		return String.valueOf(sellOrderCardListMapper
				.countByExample(sellOrderExample));
	}
	


	public SellOrderDTO viewSellOrderDTO(SellOrderDTO sellOrderDTO)
			throws Exception {
		
		SellOrderDTO resultOrderDTO  =sellOrderMapper.getCustomerOrderById(sellOrderDTO.getOrderId());
		/*SellOrderDTO resultOrderDTO = (SellOrderDTO) baseOrderDAO
				.queryForObject("getCustomerOrderById", sellOrderDTO
						.getOrderId());*/
		resultOrderDTO.setLstBankDTO(bankService.inquery(resultOrderDTO
				.getProcessEntityId()));
		return resultOrderDTO;
	}
	
	public List<UserDTO> getSaleUserList(SellOrderInputDTO sellOrderInputDTO)
			throws Exception {
		UserExample example = new UserExample();
		example.createCriteria().andDataStateEqualTo("1").andIsSaleFlageEqualTo("1").andEntityIdEqualTo(
				sellOrderInputDTO.getDefaultEntityId());
		List<User> userList = userMapper.selectByExample(example);
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		for (User user : userList) {
			UserDTO userDTO = new UserDTO();
			userDTO.setUserId(user.getUserId());
			userDTO.setUserName(user.getUserName());
			userDTOList.add(userDTO);
		}
		return userDTOList;
	}
	
	public List<SellOrderList> getSellOrderListByOrderId(String orderId)
			throws Exception {
		SellOrderListExample example = new SellOrderListExample();
		example.createCriteria().andOrderIdEqualTo(orderId)
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
		return sellOrderListMapper.selectByExample(example);
	}
	
	public List<SellOrderOrigCardList> getSellOrderOrigCardList(String orderId)
			throws Exception {
		SellOrderOrigCardListExample example = new SellOrderOrigCardListExample();
		example.createCriteria().andOrderIdEqualTo(orderId)
				.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
		return sellOrderOrigCardListMapper.selectByExample(example);
	}
	
	public List<SellOrderCardList> getSellOrderCardListsByOrderId(String orderId) {
		List<SellOrderCardList>  list = new ArrayList<SellOrderCardList>();
		
		SellOrderCardListExample example1 = new SellOrderCardListExample();
        example1.createCriteria().andOrderIdEqualTo(orderId)
                .andDataStateEqualTo("1")
                .andCardNoEqualTo("");
        
        SellOrderCardListExample example2 = new SellOrderCardListExample();
        example2.createCriteria().andOrderIdEqualTo(orderId)
                .andDataStateEqualTo("1")
                .andCardNoIsNull();
        //卡号等于""
        List<SellOrderCardList> list1 = sellOrderCardListMapper.selectByExample(example1);
        //卡号等于null
        List<SellOrderCardList> list2 = sellOrderCardListMapper.selectByExample(example2);
        if(null != list1 && list1.size() > 0) {
            list.addAll(list1);
        }
        if(null != list2 && list2.size() > 0) {
            list.addAll(list2);
        }
		return list;
	}
	
	public SellOrderList getSellOrderListByPrimaryKey(String orderId)
			throws Exception {
		SellOrderListExample ex=new SellOrderListExample();
		ex.createCriteria().andOrderIdEqualTo(orderId).andDataStateEqualTo("1");
		List<SellOrderList> sellOrderList=sellOrderListMapper.selectByExample(ex);
		return sellOrderList.get(0);
	}
	
	public List<ProductDTO> getProductByContractAndOrderType(
			SellOrderDTO sellOrderDTO, String contractType) throws Exception {
		SellContractExample example = new SellContractExample();
		example.createCriteria().andContractBuyerEqualTo(
				sellOrderDTO.getFirstEntityId()).andContractStateEqualTo(
				DataBaseConstant.STATE_ACTIVE).andContractTypeEqualTo(
				contractType).andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andContractSellerEqualTo(
				sellOrderDTO.getProcessEntityId()).andExpiryDateGreaterThanOrEqualTo(DateUtil.date2String(new Date()));
		List<SellContract> sellContractList = sellContractMapper.selectByExample(example);

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
			List<SellProdContract> sellProdContractList = sellProdContractMapper
					.selectByExample(sellProdContexample);
			List<String> spcds = (List<String>) productMapper.selectSellProduct( sellOrderDTO
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
						List<Product> product = productMapper.selectByExample(pe);
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
						List<Product> product = productMapper.selectByExample(pe);
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
	
	public List<SellOrderListDTO> getSellSignCardListByOrderId(String orderId)
			throws Exception{
		List<SellOrderListDTO> list=sellOrderCardListMapper.selectByOrderId(orderId);
		return list;
		
	}
	
	/***
	 * 查看订单用于编辑
	 * 
	 * @return
	 */

	public SellOrderDTO viewChangeCardOrderDTO(SellOrderDTO sellOrderDTO)
			throws Exception {
		SellOrderDTO resultOrderDTO =  sellOrderMapper
				.getChangeCardOrderById( sellOrderDTO.getOrderId());
		resultOrderDTO.setLstBankDTO(bankService.inquery(resultOrderDTO
				.getProcessEntityId()));
		return resultOrderDTO;
	}
	
	
	

}
