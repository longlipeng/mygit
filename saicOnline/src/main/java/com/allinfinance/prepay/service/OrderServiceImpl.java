package com.allinfinance.prepay.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.CardHolderMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderCardListMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderListMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderOrigCardListMapper;
import com.allinfinance.prepay.model.AccCardInfoExample;
import com.allinfinance.prepay.model.CardHolder;
import com.allinfinance.prepay.model.CardHolderExample;
import com.allinfinance.prepay.model.SellOrder;
import com.allinfinance.prepay.model.SellOrderCardList;
import com.allinfinance.prepay.model.SellOrderCardListExample;
import com.allinfinance.prepay.model.SellOrderExample;
import com.allinfinance.prepay.model.SellOrderList;
import com.allinfinance.prepay.model.SellOrderListExample;
import com.allinfinance.prepay.model.SellOrderOrigCardList;
import com.allinfinance.prepay.model.SellOrderOrigCardListExample;
import com.allinfinance.prepay.order.business.bo.OrderBaseQueryBO;
import com.allinfinance.prepay.utils.Amount;
import com.allinfinance.prepay.utils.DataBaseConstant;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.MathUtil;
import com.allinfinance.prepay.utils.OrderConst;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.prepay.utils.StringUtil;
import com.allinfinance.univer.issuer.dto.product.ProdFaceValueDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderQueryDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;

@Service
public class OrderServiceImpl implements OrderService {

	private Logger logger = Logger.getLogger(OrderServiceImpl.class);
	@Autowired
	private SellOrderMapper sellOrderMapper;
	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private StockOrderCommonService stockOrderCommonService;
	@Autowired
	private OrderBaseQueryBO orderBaseQueryBO;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SellOrderCardListMapper sellOrderCardListMapper;
	@Autowired
	private BankService bankService;
	@Autowired
	private SellOrderListMapper sellOrderListMapper;
	@Autowired
	private SellOrderOrigCardListMapper sellOrderOrigCardListMapper;
	@Autowired
	private CardHolderMapper cardholderMapper;

	@Override
	public SellOrderInputDTO orderInsert(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			SellOrder sellOrder = new SellOrder();
			ReflectionUtil.copyProperties(sellOrderDTO, sellOrder);
			/**
			 * ��ȡ����ID
			 */
			sellOrder.setOrderId(commonsDAO
					.getNextValueOfSequence("TB_SELL_ORDER"));
			/***
			 * ���������Ϣ
			 */
			/***
			 * �����еĽ���ֶ�������*100
			 */
			// ��װ��
			if (StringUtil.isNotEmpty(sellOrder.getPackageFee())) {
				sellOrder.setPackageFee(Amount.getDataBaseAmount(sellOrder
						.getPackageFee()));
			}
			// ���
			if (StringUtil.isNotEmpty(sellOrder.getFaceValue())) {
				sellOrder.setFaceValue(Amount.getDataBaseAmount(sellOrder
						.getFaceValue()));
			}
			// ���
			if (StringUtil.isNotEmpty(sellOrder.getAnnualFee())) {
				sellOrder.setAnnualFee(Amount.getDataBaseAmount(sellOrder
						.getAnnualFee()));
			}
			// ����
			if (StringUtil.isNotEmpty(sellOrder.getCardIssueFee())) {
				sellOrder.setCardIssueFee(Amount.getDataBaseAmount(sellOrder
						.getCardIssueFee()));
			}
			// ��ݷ�
			if (StringUtil.isNotEmpty(sellOrder.getDeliveryFee())) {
				sellOrder.setDeliveryFee(Amount.getDataBaseAmount(sellOrder
						.getDeliveryFee()));
			}
			// ���ӷ�
			if (StringUtil.isNotEmpty(sellOrder.getAdditionalFee())) {
				sellOrder.setAdditionalFee(Amount.getDataBaseAmount(sellOrder
						.getAdditionalFee()));
			}
			// �ۿ۷�
			if (StringUtil.isNotEmpty(sellOrder.getDiscountFee())) {
				sellOrder.setDiscountFee(Amount.getDataBaseAmount(sellOrder
						.getDiscountFee()));
			}
			sellOrder.setOrderDate(DateUtil.getFormatTime(sellOrder
					.getOrderDate()));
			// ��Ʊ����
			if (StringUtil.isNotEmpty(sellOrder.getInvoiceDate())) {
				sellOrder.setInvoiceDate(DateUtil.getFormatTime(sellOrder
						.getInvoiceDate()));
			}
			if (StringUtil.isNotEmpty(sellOrder.getValidityPeriod())) {
				sellOrder.setValidityPeriod(DateUtil.getFormatTime(sellOrder
						.getValidityPeriod()));
			}

			// Ԥ�Ƴ�ֵ����
			if (StringUtil.isNotEmpty(sellOrder.getForecastCreditDate())) {
				sellOrder.setForecastCreditDate(DateUtil
						.getFormatTime(sellOrder.getForecastCreditDate()));
			}

			sellOrder.setDataState("1");
			sellOrder.setCreateTime(DateUtil.getCurrentTime());
			sellOrder.setCreateUser(sellOrderDTO.getLoginUserId());
			sellOrder.setModifyTime(DateUtil.getCurrentTime());
			sellOrder.setModifyUser(sellOrderDTO.getLoginUserId());

			sellOrderMapper.insert(sellOrder);
			// ��Ӷ���������Ϣ
			sellOrderDTO.setOrderId(sellOrder.getOrderId());
			stockOrderCommonService.addNewOrderFlow(sellOrderDTO,
					OrderConst.ORDER_FLOW_INPUT,
					OrderConst.ORDER_FLOW_OPRATION_ADD);
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
					.equals(sellOrderDTO.getOrderType())) {
				this.countTotalPriceForSellOrdersign(sellOrder.getOrderId());
			} else if (OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD.equals(sellOrder
					.getOrderType())) {
				this.countTotalPriceForChangeCardOrder(sellOrder.getOrderId());
			}
			SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();
			sellOrderDTO.setOrderId(sellOrder.getOrderId());

			sellOrderInputDTO.getSellOrderCardListQueryDTO().setOrderId(
					sellOrderDTO.getOrderId());
			sellOrderInputDTO.getSellOrderListQueryDTO().setOrderId(
					sellOrderDTO.getOrderId());
			sellOrderInputDTO.getSellOrderCardListQueryDTO().setOrderType(
					sellOrderDTO.getOrderType());
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);

			sellOrderInputDTO.setDefaultEntityId(sellOrderDTO
					.getDefaultEntityId());
			if (OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD.equals(sellOrderDTO
					.getOrderType())) {
				sellOrderInputDTO.getSellOrderOrigCardListQueryDTO()
						.setOrderId(sellOrderDTO.getOrderId());
				return editChangeCardOrderDTO(sellOrderInputDTO);
			}
			return editSellOrderDTO(sellOrderInputDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("�������۶���ʧ��");
		}
	}

	public SellOrderInputDTO editChangeCardOrderDTO(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException {
		SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();

		try {
			sellOrderInputDTO.setSaleUserList(orderBaseQueryBO
					.getSaleUserList(sellOrderInputDTO));

			sellOrderDTO = orderBaseQueryBO
					.viewChangeCardOrderDTO(sellOrderDTO);
			sellOrderDTO.setOldPaymentTerm(sellOrderDTO.getPaymentTerm());
			CustomerDTO customerDTO = sellOrderInputDTO.getCustomerDTO();

			customerDTO.setEntityId(sellOrderDTO.getFirstEntityId());

			customerDTO.setFatherEntityId(sellOrderDTO.getProcessEntityId());

			customerDTO = customerService.viewCustomer(customerDTO);

			sellOrderInputDTO.setCustomerDTO(customerDTO);

			// ProductDTO productDTO = sellOrderInputDTO.getProductDTO();
			//
			// productDTO.setProductId(sellOrderDTO.getProductId());
			//
			// productDTO = productService.viewProduct(productDTO);
			//
			// sellOrderInputDTO.setProductDTO(productDTO);

			/* �������� ȡ������ԭ�п��б��¿���ϸ�б� */

			// /* ԭ�����б� */
			// sellOrderDTO.setOrigCardList(orderBaseQueryBO
			// .queryOrigCardListByOrderbyId(sellOrderInputDTO
			// .getSellOrderOrigCardListQueryDTO()));
			//
			// /* �¿���ϸ */
			// sellOrderDTO.setOrderList(orderBaseQueryBO
			// .queryChangeCardOrderListByOrderbyId(sellOrderInputDTO
			// .getSellOrderListQueryDTO()));
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("��ȡ������Ϣʧ��!");
		}
		return sellOrderInputDTO;
	}

	public SellOrderInputDTO editSellOrderDTO(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException {
		SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();

		try {
			sellOrderInputDTO.setSaleUserList(orderBaseQueryBO
					.getSaleUserList(sellOrderInputDTO));

			sellOrderDTO = orderBaseQueryBO.viewSellOrderDTO(sellOrderDTO);

			sellOrderDTO.setOldPaymentTerm(sellOrderDTO.getPaymentTerm());

			CustomerDTO customerDTO = sellOrderInputDTO.getCustomerDTO();

			customerDTO.setEntityId(sellOrderDTO.getFirstEntityId());

			customerDTO.setFatherEntityId(sellOrderDTO.getProcessEntityId());

			customerDTO = customerService.viewCustomer(customerDTO);

			sellOrderInputDTO.setCustomerDTO(customerDTO);

			ProductDTO productDTO = sellOrderInputDTO.getProductDTO();

			productDTO.setProductId(sellOrderDTO.getProductId());

			productDTO = productService.viewProduct(productDTO);

			sellOrderInputDTO.setProductDTO(productDTO);

			/*
			 * if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_SIGN
			 * .equals(sellOrderDTO.getOrderType()) ||
			 * OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER
			 * .equals(sellOrderDTO.getOrderType()) ||
			 * OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_RETAIL_SIGN
			 * .equals(sellOrderDTO.getOrderType()) ||
			 * OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
			 * .equals(sellOrderDTO.getOrderType())) {
			 *//**
			 * ��ȡ��������ϸ
			 */
			/*
			 * sellOrderDTO.setOrderCardList(orderBaseQueryBO
			 * .queryCardListByOrderbyId(sellOrderInputDTO
			 * .getSellOrderCardListQueryDTO())); }
			 */
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("��ȡ������Ϣʧ��!");
		}
		return sellOrderInputDTO;
	}

	public void countTotalPriceForChangeCardOrder(String orderId)
			throws Exception {
		SellOrder sellOrder = orderBaseQueryBO.getSellOrder(orderId);
		String totalPrice = "0";
		/* �ͻ��� */
		if (StringUtil.isNotEmpty(sellOrder.getDeliveryFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getDeliveryFee())
					.toString();
		}
		/* ����� */
		if (StringUtil.isNotEmpty(sellOrder.getAdditionalFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getAdditionalFee())
					.toString();
		}
		/* ��װ�� */
		if (StringUtil.isNotEmpty(sellOrder.getPackageFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getPackageFee())
					.toString();
		}

		/* ԭ���ܷ��ü��� ���������� */
		List<SellOrderOrigCardList> origCardList = orderBaseQueryBO
				.getSellOrderOrigCardList(orderId);
		String origCountAmount = "0";
		int origCardCount = 0;
		for (SellOrderOrigCardList origCard : origCardList) {
			origCountAmount = MathUtil.add(origCountAmount,
					origCard.getAmount()).toString();
			origCardCount++;
		}

		/* �¿��ܷ��ü��� */
		List<SellOrderList> newCardList = orderBaseQueryBO
				.getSellOrderListByOrderId(orderId);
		String newCardAmount = "0";
		String newCardCount = "0";
		for (SellOrderList orderList : newCardList) {
			newCardAmount = MathUtil.add(
					newCardAmount,
					MathUtil.multiply(orderList.getFaceValue(),
							orderList.getCardAmount()).toString()).toString();
			newCardCount = MathUtil
					.add(newCardCount, orderList.getCardAmount()).toString();
		}
		/* �ܷ���=��װ��+�ͻ���+�����+(�¿��ܷ���-ԭ���ܷ���) */
		totalPrice = MathUtil.add(totalPrice,
				MathUtil.subtract(newCardAmount, origCountAmount).toString())
				.toString();
		sellOrder.setOrigcardTotalamt(origCountAmount);
		sellOrder.setNewcardTotalamt(newCardAmount);
		sellOrder.setTotalPrice(totalPrice);
		sellOrder.setOrigcardQuantity(Integer.toString(origCardCount));
		sellOrder.setCardQuantity(newCardCount);
		SellOrderExample ex = new SellOrderExample();
		ex.createCriteria().andOrderIdEqualTo(sellOrder.getOrderId());

		sellOrderMapper.updateByExampleSelective(sellOrder, ex);
	}

	public void countTotalPriceForSellOrdersign(String orderId)
			throws Exception {

		SellOrder sellOrder = orderBaseQueryBO.getSellOrder(orderId);

		String totalPrice = "0";

		String cardQuantity = "0";

		/**
		 * ��װ��
		 */
		if (StringUtil.isNotEmpty(sellOrder.getPackageFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getPackageFee())
					.toString();
		}
		//
		// /**
		// * ��һ�γ�ֵ���
		// */
		// if(StringUtil.isNotEmpty(sellOrder.getFaceValue())){
		// totalPrice=MathUtil.add(totalPrice,sellOrder.getFaceValue()).toString();
		// }

		/**
		 * �ۿ۷�
		 */
		if (StringUtil.isNotEmpty(sellOrder.getDiscountFee())) {
			totalPrice = MathUtil.subtract(totalPrice,
					sellOrder.getDiscountFee()).toString();
		}
		/***
		 * �ͻ���
		 */
		if (StringUtil.isNotEmpty(sellOrder.getDeliveryFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getDeliveryFee())
					.toString();
		}
		/**
		 * ���ӷ�
		 */
		if (StringUtil.isNotEmpty(sellOrder.getAdditionalFee())) {
			totalPrice = MathUtil.add(totalPrice, sellOrder.getAdditionalFee())
					.toString();
		}

		/**
		 * ��ϸ����,ͨ����ϸ��ȡ����
		 */
		cardQuantity = orderBaseQueryBO.getSellOrderCardListByOrderId(orderId);

		String totalList = MathUtil.multiply(
				cardQuantity,
				MathUtil.add(sellOrder.getCardIssueFee(),
						sellOrder.getFaceValue(), sellOrder.getAnnualFee())
						.toString()).toString();

		totalPrice = MathUtil.add(totalPrice, totalList).toString();

		sellOrder.setTotalPrice(totalPrice);

		sellOrder.setCardQuantity(cardQuantity);
		SellOrderExample ex = new SellOrderExample();
		ex.createCriteria().andOrderIdEqualTo(sellOrder.getOrderId());

		sellOrderMapper.updateByExampleSelective(sellOrder, ex);
	}

	@Override
	public SellOrderInputDTO insertCardholderList(
			SellOrderInputDTO sellOrderInputDTO) throws BizServiceException {
		try {
			CardholderDTO cardholderDTO = sellOrderInputDTO.getCardholderDTO();
			SellOrderCardList sellOrderCardList = new SellOrderCardList();
			sellOrderCardList
					.setOrderCardListId(commonsDAO
							.getNextValueOfSequenceBySequence("SEQ_SELL_ORDER_CARD_LIST"));
			sellOrderCardList.setCardholderId(cardholderDTO.getCardholderId());
			sellOrderCardList.setOrderId(sellOrderInputDTO.getSellOrderDTO()
					.getOrderId());
			sellOrderCardList.setLastName(cardholderDTO.getLastName());
			sellOrderCardList.setFirstName(cardholderDTO.getFirstName());
			sellOrderCardList.setExternalId(cardholderDTO.getExternalId());
			// sellOrderCardList.setExternalId(cardholderDTO.getEntityId());
			sellOrderCardList.setCreateTime(DateUtil.getCurrentTime());
			sellOrderCardList.setCreateUser(sellOrderInputDTO.getLoginUserId());
			sellOrderCardList.setModifyTime(DateUtil.getCurrentTime());
			sellOrderCardList.setModifyUser(sellOrderInputDTO.getLoginUserId());
			sellOrderCardList.setDataState("1");
			sellOrderCardListMapper.insert(sellOrderCardList);

			this.countTotalPriceForSellOrdersign(sellOrderInputDTO
					.getSellOrderDTO().getOrderId());
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("����������ϸʧ��");
		}
		return sellOrderInputDTO;
	}

	@Override
	public SellOrderInputDTO initAdd(SellOrderInputDTO sellOrderInputDTO)
			throws BizServiceException {
		try {
			SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
			if (OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_SIGN
					.equals(sellOrderDTO.getOrderType())) {
				sellOrderInputDTO.setSaleUserList(orderBaseQueryBO
						.getSaleUserList(sellOrderInputDTO));
				/* ��ȡ��������������Ϣ 3:Ӫ������ */
				sellOrderDTO.setLstBankDTO(bankService.inquery(sellOrderDTO
						.getProcessEntityId()));
				if (StringUtil.isNotEmpty(sellOrderDTO.getFirstEntityId())) {
					CustomerDTO customerDTO = new CustomerDTO();
					customerDTO.setEntityId(sellOrderDTO.getFirstEntityId());
					customerDTO.setFatherEntityId(sellOrderDTO
							.getProcessEntityId());
					customerDTO = customerService.viewCustomer(customerDTO);
					sellOrderInputDTO.setCustomerDTO(customerDTO);
					sellOrderDTO.setDefaultEntityId(sellOrderInputDTO
							.getDefaultEntityId());
					List<ProductDTO> productDTOs_need = orderBaseQueryBO
							.getProductByContractAndOrderType(sellOrderDTO,
									DataBaseConstant.SELL_CONTRACT_CUSTOMER);
					if (null == productDTOs_need) {
						return sellOrderInputDTO;
					}

					/***
		 *
		 */
					List<ProductDTO> productDTOs = productDTOs_need;
					/*
					 * ע������Ϊ������������
					 */
					// for (ProductDTO productDTO_temp:productDTOs_need){
					//
					// if(DictInfoConstants.SIGN_ORDER_MUST.equals(productDTO_temp.getOnymousStat())){
					// productDTOs.add(productDTO_temp);
					// }
					// }

					/***
					 * ���ڲ�Ʒ����û�г���100�����ｫ���ֵ����ת��
					 */
					// if (productDTOs.size() > 0) {
					// for (ProductDTO temp : productDTOs) {
					// List<ProdFaceValueDTO> prodFaceValueList = temp
					// .getProdFaceValueDTO();
					// for (ProdFaceValueDTO faceValueDTO : prodFaceValueList) {
					// faceValueDTO.setFaceValue(Amount
					// .getReallyAmount(faceValueDTO
					// .getFaceValue()));
					// }
					//
					// }
					// }
					ProductDTO productDTO_choose = new ProductDTO();
					sellOrderInputDTO.setProductDTOs(productDTOs);
					if (productDTOs.size() > 0) {
						// if (StringUtil.isEmpty(sellOrderDTO.getProductId()))
						// {
						productDTO_choose = productDTOs.get(0);

						// } else {
						for (ProductDTO productDTO : productDTOs) {
							if (productDTO.getProductId().equals(
									sellOrderDTO.getProductId())) {
								productDTO_choose = productDTO;
								break;
							}
						}
						// }
					}
					sellOrderInputDTO.setProductDTO(productDTO_choose);

					/***
					 * ���ÿ���Ч���ڣ���ǰʱ����Ͽ���Ч����
					 */
					if (productDTO_choose.getValidityPeriod() != null) {
						Date cardValidityPeriod = DateUtil.countCardValidate(
								new Date(), Integer.parseInt(productDTO_choose
										.getValidityPeriod()));
						sellOrderDTO.setValidityPeriod(DateUtil
								.dateToSting_(cardValidityPeriod));
					}
					sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
				}
			}

			if (OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD.equals(sellOrderDTO
					.getOrderType())) {
				sellOrderInputDTO.setSaleUserList(orderBaseQueryBO
						.getSaleUserList(sellOrderInputDTO));
				/* ��ȡ��������������Ϣ 3:Ӫ������ */
				sellOrderDTO.setLstBankDTO(bankService.inquery(sellOrderDTO
						.getProcessEntityId()));
				if (StringUtil.isNotEmpty(sellOrderDTO.getFirstEntityId())) {
					CustomerDTO customerDTO = new CustomerDTO();
					customerDTO.setEntityId(sellOrderDTO.getFirstEntityId());
					customerDTO.setFatherEntityId(sellOrderDTO
							.getProcessEntityId());
					customerDTO = customerService.viewCustomer(customerDTO);
					sellOrderInputDTO.setCustomerDTO(customerDTO);
					sellOrderDTO.setDefaultEntityId(sellOrderInputDTO
							.getDefaultEntityId());
					List<ProductDTO> productDTOs_need = orderBaseQueryBO
							.getProductByContractAndOrderType(sellOrderDTO,
									DataBaseConstant.SELL_CONTRACT_CUSTOMER);
					if (null == productDTOs_need) {
						return sellOrderInputDTO;
					}

					List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
					/***
					 * �����Ʒ�Ǽ�����ɾ��
					 */
					for (ProductDTO productDTO_temp : productDTOs_need) {
						if (!"1".equals(productDTO_temp.getOnymousStat())) {
							productDTOs.add(productDTO_temp);
						}
					}

					ProductDTO productDTO_choose = new ProductDTO();
					sellOrderInputDTO.setProductDTOs(productDTOs);
					if (productDTOs.size() > 0) {
						if (StringUtil.isEmpty(sellOrderDTO.getProductId())) {
							// ȥ��������濨
							// List<ProductDTO> productDTOsByNoName = new
							// ArrayList<ProductDTO>();
							// for(ProductDTO p :productDTOs){
							// if(!"3".equals(p.getOnymousStat())){
							// productDTOsByNoName.add(p);
							// }
							// }
							// for(ProductDTO productDTOByNoName :
							// productDTOsByNoName){
							// for (ProductDTO productDTO : productDTOs) {
							// if (productDTO.getProductId().equals(
							// productDTOByNoName.getProductId())) {
							// productDTO_choose = productDTO;
							// break;
							// }
							// }
							// }
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
					sellOrderInputDTO.setProductDTO(productDTO_choose);
					sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
				}
			}
			return sellOrderInputDTO;

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("��ʼ��������Ϣʧ��");
		}
	}

	public void insertOrderListForChangeCardOrder(
			SellOrderListDTO sellOrderListDTO) throws BizServiceException {
		try {

			SellOrderListExample example = new SellOrderListExample();
			example.createCriteria()
					.andOrderIdEqualTo(sellOrderListDTO.getOrderId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			int num = sellOrderListMapper.countByExample(example);
			if (num >= 1) {
				throw new BizServiceException("ֻ�������һ���¿���ϸ��Ϣ");
			}
			SellOrderList sellOrderList = new SellOrderList();

			ReflectionUtil.copyProperties(sellOrderListDTO, sellOrderList);

			sellOrderList.setOrderListId(commonsDAO
					.getNextValueOfSequence("TB_SELL_ORDER_LIST"));

			if (StringUtil.isNotEmpty(sellOrderListDTO.getFaceValue())) {
				DecimalFormat df = new DecimalFormat("#");//��ʽ��С��  
				String faceValue=df.format(Double.valueOf(Amount
						.getDataBaseAmount(sellOrderListDTO.getFaceValue())));
				sellOrderList.setFaceValue(faceValue);
			}
			sellOrderList.setCreateTime(DateUtil.getCurrentTime());
			if (StringUtil.isNotEmpty(sellOrderListDTO.getValidityPeriod())) {
				sellOrderList.setValidityPeriod(DateUtil
						.getFormatTime(sellOrderListDTO.getValidityPeriod()));
			}

			sellOrderList.setCreateUser(sellOrderListDTO.getLoginUserId());

			sellOrderList.setModifyTime(DateUtil.getCurrentTime());

			sellOrderList.setModifyUser(sellOrderListDTO.getLoginUserId());

			sellOrderList.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			sellOrderListMapper.insert(sellOrderList);
			this.countTotalPriceForChangeCardOrder(sellOrderListDTO
					.getOrderId());

		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("������ϸʧ��!");
		}

	}

	// ��ѯԭ�п���Ϣ
	public void insertChangeCardOrderOrigCard(SellOrderDTO sellOrderDTO)
			throws BizServiceException {
		try {
			SellOrderOrigCardListExample example = new SellOrderOrigCardListExample();
			example.createCriteria()
					.andOrderIdEqualTo(sellOrderDTO.getOrderId())
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			int num = sellOrderOrigCardListMapper.countByExample(example);
			if (num >= 1) {
				throw new BizServiceException("ֻ�������һ��ԭ����ϸ��Ϣ");
			}
			SellOrderOrigCardList origCard = new SellOrderOrigCardList();
			List<CardHolder> cardholders = cardholderMapper
					.selectCardHolderByCardNo(sellOrderDTO.getCardNo());
			if (null != cardholders && cardholders.size() > 0) {
				origCard.setCardholderId(cardholders.get(0).getCardholderId());
				logger.debug("CardHolderName:"
						+ cardholders.get(0).getFirstName());
				origCard.setFirstName(cardholders.get(0).getFirstName());
			}

			origCard.setCardNo(sellOrderDTO.getCardNo());
			origCard.setProductId(sellOrderDTO.getProductId());
			origCard.setProductName(sellOrderDTO.getProductName());
			origCard.setValidityPeriod(sellOrderDTO.getValidityPeriod());
			origCard.setAmount(sellOrderDTO.getFaceValue());
			origCard.setOrigcardListId(commonsDAO
					.getNextValueOfSequence("TB_SELL_ORDER_ORIGCARD_LIST"));
			origCard.setCreateUser(sellOrderDTO.getLoginUserId());
			origCard.setCreateTime(DateUtil.getCurrentTime24());
			origCard.setModifyUser(sellOrderDTO.getLoginUserId());
			origCard.setModifyTime(DateUtil.getCurrentTime24());
			origCard.setOrderId(sellOrderDTO.getOrderId());
			origCard.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			origCard.setCardSate(OrderConst.ORIG_CARD_STAT_UNCHECK);
			sellOrderOrigCardListMapper.insert(origCard);
			// baseOrderDAO.batchInsert("TB_SELL_ORDER_ORIGCARD_LIST","abatorgenerated_insert",
			// list);
			 this.countTotalPriceForChangeCardOrder(sellOrderDTO.getOrderId());

		} catch (BizServiceException e1) {
			e1.printStackTrace();
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("��ӿͻ���Ƭ��Ϣʧ��!");
		}

	}

	@Override
	public void updateOrderStat(String orderId) throws BizServiceException {
		// TODO Auto-generated method stub
		SellOrderExample ex = new SellOrderExample();
		ex.createCriteria().andOrderIdEqualTo(orderId);
		SellOrder order = new SellOrder();
		order.setOrderState("0");// ����ȡ��
		sellOrderMapper.updateByExampleSelective(order, ex);

	}

	public void delSellOrderCardList(String orderId) throws Exception {
		SellOrderCardListExample ex = new SellOrderCardListExample();
		ex.createCriteria().andOrderIdEqualTo(orderId);
		sellOrderCardListMapper.deleteByExample(ex);
	}

}
