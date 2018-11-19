package com.allinfinance.prepay.processor.ipos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.mapper.svc_mng.AccAccountInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.AccCardInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.CardHolderMapper;
import com.allinfinance.prepay.mapper.svc_mng.CustomerMapper;
import com.allinfinance.prepay.mapper.svc_mng.DeliveryContactMapper;
import com.allinfinance.prepay.mapper.svc_mng.EntityDeliveryMapper;
import com.allinfinance.prepay.mapper.svc_mng.EntityStockMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellContractMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellOrderOrigCardListMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellProdContractMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellerMapper;
import com.allinfinance.prepay.mapper.svc_mng.TermSellOrderMapper;
import com.allinfinance.prepay.message.BasicMessage;
import com.allinfinance.prepay.message.MessageSyncP001Req;
import com.allinfinance.prepay.message.MessageSyncP001Resp;
import com.allinfinance.prepay.message.MessageSyncP004Req;
import com.allinfinance.prepay.message.MessageSyncP004Resp;
import com.allinfinance.prepay.message.MessageSyncP011Req;
import com.allinfinance.prepay.model.AccAccountInfo;
import com.allinfinance.prepay.model.AccCardInfo;
import com.allinfinance.prepay.model.AccCardInfoExample;
import com.allinfinance.prepay.model.CardHolder;
import com.allinfinance.prepay.model.CardHolderExample;
import com.allinfinance.prepay.model.Customer;
import com.allinfinance.prepay.model.CustomerExample;
import com.allinfinance.prepay.model.EntityStock;
import com.allinfinance.prepay.model.EntityStockExample;
import com.allinfinance.prepay.model.SellOrder;
import com.allinfinance.prepay.model.SellOrderExample;
import com.allinfinance.prepay.model.SellOrderList;
import com.allinfinance.prepay.model.SellOrderOrigCardList;
import com.allinfinance.prepay.model.SellOrderOrigCardListExample;
import com.allinfinance.prepay.model.Seller;
import com.allinfinance.prepay.model.SellerExample;
import com.allinfinance.prepay.model.TermSellOrder;
import com.allinfinance.prepay.processor.IProcessor;
import com.allinfinance.prepay.service.CardholderService;
import com.allinfinance.prepay.service.CustomerService;
import com.allinfinance.prepay.service.EntityContactService;
import com.allinfinance.prepay.service.EntityStockService;
import com.allinfinance.prepay.service.InvoiceAddressService;
import com.allinfinance.prepay.service.InvoiceCompanyService;
import com.allinfinance.prepay.service.OrderReadyService;
import com.allinfinance.prepay.service.OrderService;
import com.allinfinance.prepay.service.ProductService;
import com.allinfinance.prepay.service.SellOrderPaymentService;
import com.allinfinance.prepay.service.SellerContractService;
import com.allinfinance.prepay.service.SellerProductContractService;
import com.allinfinance.prepay.service.SubmitOrderService;
import com.allinfinance.prepay.socket.SocketSend;
import com.allinfinance.prepay.utils.Config;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.OrderConst;
import com.allinfinance.prepay.utils.ParseToXML;
import com.allinfinance.prepay.utils.ResponseCode;
import com.allinfinance.prepay.utils.XmlDom;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderPaymentDTO;

/**
 * ����
 * 
 * 
 *
 */
@Service
public class SyncP004Processor implements IProcessor {
	private Logger logger = Logger.getLogger(SyncP001Processor.class);
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerMapper CustomerMapper;
	@Autowired
	private EntityDeliveryMapper entityDeliveryMapper;
	@Autowired
	private DeliveryContactMapper deliveryContactMapper;
	@Autowired
	private EntityContactService contactService;
	@Autowired
	private InvoiceAddressService invoiceAddressService;
	@Autowired
	private InvoiceCompanyService invoiceCompanyService;
	@Autowired
	private CardHolderMapper cardHolderMapper;
	@Autowired
	private CardholderService cardHolderService;
	@Autowired
	private SellContractMapper sellContractMapper;
	@Autowired
	private SellerContractService sellerContractService;
	@Autowired
	private EntityStockMapper entityStockMapper;
	@Autowired
	private ProductService productService;
	@Autowired
	private SellProdContractMapper sellProdContractMapper;
	@Autowired
	private SellerProductContractService sellerProductContractService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private SubmitOrderService submitOrderService;
	@Autowired
	private OrderReadyService orderReadyService;
	@Autowired
	private SellOrderPaymentService sellOrderPaymentService;
	@Autowired
	private EntityStockService entityStockService;
	@Autowired
	private AccCardInfoMapper accCardInfoMapper;
	@Autowired
	private SellerMapper sellerMapper;
	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private TermSellOrderMapper termSellOrderMapper;
	@Autowired
	private AccAccountInfoMapper accAccountInfoMapper;
	@Autowired
	private SellOrderOrigCardListMapper sellOrderOrigCardListMapper;
	@Autowired
	private SellOrderMapper sellOrderMapper;

	@Override
	public BasicMessage process(BasicMessage req) throws Exception {
		MessageSyncP004Req reqData = (MessageSyncP004Req) req;
		MessageSyncP004Resp resp = (MessageSyncP004Resp) reqData.createResp();
		CustomerDTO customerDTO = new CustomerDTO();
		CardholderDTO cardholderDTO = new CardholderDTO();
		String cardNo = reqData.getCARD_NO();
		String oldCardNo = reqData.getOLD_CARD_NO();
		String defaultEntityId = reqData.getISSUER_ID();

		

		if (checkIssueId(reqData.getISSUER_ID().trim()) == false) {
			// û���ҵ�������
			// resp.setRESP_TEXT("����������");
			resp.setRESP_CODE(ResponseCode.ISSUEID_ERROR);
			return resp;
		}

		// ֤������
		if (reqData.getID_TYPE().trim().equals("1")) {
			customerDTO.setCorpCredType("1");
			cardholderDTO.setIdType("1");
		} else if (reqData.getID_TYPE().trim().equals("2")) {
			customerDTO.setCorpCredType("3");
			cardholderDTO.setIdType("2");
		} else if (reqData.getID_TYPE().trim().equals("3")) {
			customerDTO.setCorpCredType("6");
			cardholderDTO.setIdType("3");
		} else {
			// û�����֤������
			resp.setRESP_CODE(ResponseCode.ID_TYPE_ERROR);
			// resp.setRESP_TEXT("֤����������");
			return resp;
		}

		CustomerExample ex = new CustomerExample();
		ex.createCriteria().andCorpCredIdEqualTo(reqData.getID_NO())
				.andCorpCredTypeEqualTo(customerDTO.getCorpCredType());
		List<Customer> customer = CustomerMapper.selectByExample(ex);
		if (customer.size() == 0) {
			// δ�ҵ��ͻ�
			resp.setRESP_CODE(ResponseCode.CARDHOLDER_INFO_ISNULL);
			return resp;
		}

		CardHolderExample cardHolderEX = new CardHolderExample();
		cardHolderEX.createCriteria().andIdNoEqualTo(reqData.getID_NO())
				.andIdTypeEqualTo(reqData.getID_TYPE())
				.andDataStateEqualTo("1");
		List<CardHolder> cardHolders = cardHolderMapper
				.selectByExample(cardHolderEX);
		if (cardHolders.size() == 0) {
			// δ�ҵ��ֿ���
			resp.setRESP_CODE(ResponseCode.CARDHOLDER_INFO_ISNULL);
			return resp;
		}
		AccCardInfoExample accEX = new AccCardInfoExample();
		accEX.createCriteria().andCardNoEqualTo(oldCardNo);
		List<AccCardInfo> accList = accCardInfoMapper.selectByExample(accEX);
		if (!cardHolders.get(0).getCardholderId()
				.equals(accList.get(0).getCardholderId())) {
			// ����ԭ�п��ֿ��˸�֤�����͡�֤���Ų�ƥ��
			resp.setRESP_CODE(ResponseCode.ID_NO_INCONFORMITY);
			return resp;
		}
		//�ж�ԭ�п�����״̬�Ƿ���ע��״̬
		if("3".equals(accList.get(0).getCardStat())){
			resp.setRESP_CODE(ResponseCode.CARD_STATE_ERROR);
			return resp;
		}

		List<Customer> customers = accCardInfoMapper
				.selectCustomerByCardNo(oldCardNo);
		EntityStockExample entityStockExample = new EntityStockExample();
		entityStockExample.createCriteria().andCardNoEqualTo(oldCardNo);
		List<EntityStock> oldCardNoEntityStocks = entityStockMapper
				.selectByExample(entityStockExample);
		if (oldCardNoEntityStocks.size() == 0) {
			resp.setRESP_CODE(ResponseCode.CARDNO_ISNULL);
			return resp;
		}
		EntityStockExample stockEX = new EntityStockExample();
		stockEX.createCriteria().andCardNoEqualTo(cardNo)
				.andStockStateEqualTo("1").andDataStateEqualTo("1")
				.andFunctionRoleIdEqualTo("3")
				.andEntityIdEqualTo(reqData.getISSUER_ID());
		;
		List<EntityStock> cardNoEntityStocks = entityStockMapper
				.selectByExample(stockEX);
		if (cardNoEntityStocks.size() == 0) {
			resp.setRESP_CODE(ResponseCode.CARDNO_ISNULL);
			return resp;
		}
		if (!cardNoEntityStocks.get(0).getProductId()
				.equals(oldCardNoEntityStocks.get(0).getProductId())) {
			resp.setRESP_CODE(ResponseCode.PRODUCT_ID_ERROR);
			return resp;
		}
		
		if (checkCardPwd(reqData) == false) {
			resp.setRESP_CODE(ResponseCode.PWD_ERROR);
			return resp;
		}

		EntityStock enStock = new EntityStock();
		enStock.setStockState("2");
		logger.info("�޸Ŀ��ſ��״̬��[" + reqData.getRESPSEQNO() + "]");
		entityStockMapper.updateByExampleSelective(enStock, stockEX);

		SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();
		SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
		sellOrderDTO.setDefaultEntityId(defaultEntityId);
		sellOrderDTO.setAdditionalFee("0");// Ĭ�ϸ��ӷ�Ϊ0
		sellOrderDTO.setCardQuantity("0");// ��������
		sellOrderDTO.setDeliveryFee("0");// �ͻ�����
		sellOrderDTO.setDeliveryMeans("2");// �ͻ���ʽ
		sellOrderDTO.setDiscountFee("0");// �ۿ۷�
		sellOrderDTO.setFirstEntityId(customers.get(0).getEntityId());// �ͻ���
		sellOrderDTO.setProcessEntityId(defaultEntityId);// Ӫ������
		sellOrderDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD);// ��������
		sellOrderDTO.setSaleMan(Config.getUserId());// ������Ա
		sellOrderDTO.setPayChannel("1");// ֧������
		sellOrderDTO.setPackageFee("0");// ��װ��
		sellOrderDTO.setProductId(cardNoEntityStocks.get(0).getProductId());// ��ƷID
		sellOrderDTO.setOrderSource("1");// ������Դ
		sellOrderDTO.setOrderState("1");// ����״̬
		sellOrderDTO.setPaymentDelay("0");// ������������
		sellOrderDTO.setPaymentState("0");// ����״̬Ϊδ֧��
		sellOrderDTO.setPaymentTerm("2");// ֧���ڵ�
		sellOrderDTO.setCardIssueFee("0");// ����
		sellOrderDTO.setAnnualFee("0");// ���
		sellOrderDTO.setTotalPrice("0");// �����ܽ��
		sellOrderDTO.setModifyUser(Config.getUserId());// �޸Ķ����û�
		sellOrderDTO.setCreateUser(Config.getUserId());// ���������û�
		sellOrderDTO.setLoginUserId(Config.getUserId());
		sellOrderDTO.setOrderDate(getDateFormat(new Date()));
		sellOrderInputDTO.setDefaultEntityId(defaultEntityId);
		sellOrderInputDTO.setLoginUserId(Config.getUserId());
		sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
		try {
			sellOrderInputDTO = orderService.initAdd(sellOrderInputDTO);
			sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
			List<ContactDTO> contactDTOs = contactService
					.inqueryContact(customers.get(0).getEntityId());
			sellOrderDTO.setContactId(contactDTOs.get(0).getContactId());
			sellOrderDTO.setIntoBankId(sellOrderDTO.getLstBankDTO().get(0)
					.getBankId());
			sellOrderInputDTO = orderService.orderInsert(sellOrderDTO);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("[" + reqData.getRESPSEQNO() + "]" + "��������ʧ��");
			updateStockStat(cardNo, defaultEntityId, "1");
			throw new Exception(e);
		}
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductId(cardNoEntityStocks.get(0).getProductId());
		productDTO.setDefaultEntityId(defaultEntityId);
		productDTO = productService.viewProduct(productDTO);
		List<AccAccountInfo> acc = accAccountInfoMapper
				.selectByCardNo(oldCardNo);
		// ���ԭ�п���ϸ
		String bal = String
				.valueOf(Integer.parseInt(acc.get(0).getAccBal()) / 100);
		sellOrderDTO.setCardNo(oldCardNo);
		sellOrderDTO.setOrderId(sellOrderInputDTO.getSellOrderDTO()
				.getOrderId());
		sellOrderDTO.setProductName(productDTO.getProductName());
		sellOrderDTO.setValidityPeriod(acc.get(0).getValidDate());
		sellOrderDTO.setFaceValue(acc.get(0).getAccBal());// ���
		orderService.insertChangeCardOrderOrigCard(sellOrderDTO);

		// ����¿���ϸ
		SellOrderListDTO sellOrderListDTO = new SellOrderListDTO();
		sellOrderListDTO.setCardAmount("1");
		sellOrderListDTO.setCardLayoutId(productDTO.getCardLayoutId());
		sellOrderListDTO.setFaceValue(bal);
		sellOrderListDTO.setFaceValueType(productDTO.getProdFaceValueDTO()
				.get(0).getFaceValueType());
		sellOrderListDTO.setDefaultEntityId(productDTO.getDefaultEntityId());
		sellOrderListDTO.setLoginUserId(Config.getUserId());
		sellOrderListDTO.setOrderId(sellOrderInputDTO.getSellOrderDTO()
				.getOrderId());
		sellOrderListDTO.setProductId(productDTO.getProductId());
		sellOrderListDTO.setServiceId(productDTO.getServices().get(0)
				.getServiceId());
		orderService.insertOrderListForChangeCardOrder(sellOrderListDTO);

		// �ύ����
		sellOrderInputDTO.setOrderId(sellOrderInputDTO.getSellOrderDTO()
				.getOrderId());
		sellOrderInputDTO.setLoginUserId(Config.getUserId());
		try {
			submitOrderService.submitOrderAtInput(sellOrderInputDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			delSellOrderOrigCardList(sellOrderInputDTO.getOrderId());
			updateStockStat(cardNo, defaultEntityId, "1");
			logger.error("[" + reqData.getRESPSEQNO() + "]" + "������"
					+ sellOrderInputDTO.getOrderId() + "�����ύʧ�ܣ�");
			throw new Exception(e);
		}
		// �������
		SellOrderDTO sellOrder = sellOrderInputDTO.getSellOrderDTO();
		sellOrder.setLoginUserId(sellOrderInputDTO.getLoginUserId());
		try {
			submitOrderService.submitOrderAtConfirm(sellOrder);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			delSellOrderOrigCardList(sellOrderInputDTO.getOrderId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			updateStockStat(cardNo, defaultEntityId, "1");
			logger.error("[" + reqData.getRESPSEQNO() + "]" + "������"
					+ sellOrderInputDTO.getOrderId() + "�������ʧ�ܣ�");
			throw new Exception(e);
		}

		// ����׼��
		/**
		 * ԭ�п���Ϣ����
		 */
		SellOrderOrigCardListExample OrigEx = new SellOrderOrigCardListExample();
		SellOrderOrigCardList origCardList = new SellOrderOrigCardList();
		origCardList.setCardSate("3");
		OrigEx.createCriteria()
				.andOrderIdEqualTo(sellOrderInputDTO.getOrderId())
				.andDataStateEqualTo("1");
		sellOrderOrigCardListMapper.updateByExampleSelective(origCardList,
				OrigEx);
		// ����׼��
		OrderReadyDTO orderReadyDTO = new OrderReadyDTO();
		orderReadyDTO
				.setProcessEntityId(sellOrderInputDTO.getDefaultEntityId());
		orderReadyDTO
				.setDefaultEntityId(sellOrderInputDTO.getDefaultEntityId());
		orderReadyDTO.setLoginUserId(sellOrder.getLoginUserId());
		orderReadyDTO.setProductId(productDTO.getProductId());
		orderReadyDTO.setOrderId(sellOrderInputDTO.getSellOrderDTO()
				.getOrderId());
		orderReadyDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD);
		EntityStockExample entityStockEx = new EntityStockExample();
		// ������Ŀ��ţ�������
		entityStockEx.createCriteria().andCardNoEqualTo(cardNo)
				.andStockStateEqualTo("2").andDataStateEqualTo("1")
				.andFunctionRoleIdEqualTo("3")
				.andEntityIdEqualTo(defaultEntityId);
		List<EntityStock> entityStock = entityStockMapper
				.selectByExample(entityStockEx);
		if (entityStock.size() == 0) {
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			// updateStockStat(cardNo,defaultEntityId);
			resp.setRESP_CODE(ResponseCode.CARDNO_ISNULL);
			return resp;
			// throw new BizServiceException("�ͻ���" +
			// customerDTO.getCustomerName()
			// + "����׼��ʱ����Ʒ��" + productDTO.getProductName() + ",��治�㣡�����жϣ�");
		}
		// ���Ĵ������Ŀ���
		String[] cardNoArray = new String[] { cardNo };
		orderReadyDTO.setCardNoArray(cardNoArray);
		// ����׼��
		try {
			orderReadyService.cardReady(orderReadyDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			entityStockService.delStockOperaterRecord(sellOrderInputDTO
					.getOrderId());
			delSellOrderOrigCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);// ���Ĵ������Ŀ���
			updateStockStat(cardNo, defaultEntityId, "1");
			// entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("[" + reqData.getRESPSEQNO() + "]" + "������"
					+ sellOrderInputDTO.getOrderId() + "����׼��ʧ�ܣ�");
			throw new Exception(e);
			// throw new BizServiceException("�ۿ�������" + sellOrderDTO.getOrderId()
			// + ",����׼��ʧ�ܣ������жϣ�");
		}
		// ����׼�����ύ����
		try {
			submitOrderService.submitOrderAtReady(sellOrderDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			entityStockService.delStockOperaterRecord(sellOrderInputDTO
					.getOrderId());
			updateAccCardInfo(cardNo);// ���Ĵ������Ŀ���
			delSellOrderOrigCardList(sellOrderInputDTO.getOrderId());
			// entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			updateStockStat(cardNo, defaultEntityId, "1");
			updateStockStat(oldCardNo, defaultEntityId, "3");
			logger.error("[" + reqData.getRESPSEQNO() + "]" + "������"
					+ sellOrderInputDTO.getOrderId() + "����׼���ύʧ�ܣ�");
			throw new Exception(e);
			// throw new BizServiceException("�ۿ�������" + sellOrderDTO.getOrderId()
			// + ",����׼��ʧ�ܣ������жϣ�");
		}
		// ��������
		SellOrderDTO sellOrderDto = new SellOrderDTO();
		sellOrderDto.setOperationMemo("");
		sellOrderDto.setDefaultEntityId(sellOrderDTO.getDefaultEntityId());
		sellOrderDto.setDeliveryPoint(sellOrderDTO.getDeliveryPoint());
		sellOrderDto.setFaceValueType(sellOrderDTO.getFaceValueType());
		sellOrderDto.setLoginUserId(sellOrderDTO.getLoginUserId());
		sellOrderDto.setOrderContact(sellOrderDTO.getOrderContact());
		sellOrderDto.setOrderId(sellOrderDTO.getOrderId());
		sellOrderDto.setOrderType(sellOrderDTO.getOrderType());
		sellOrderDto.setPaymentState(sellOrderDTO.getPaymentState());
		sellOrderDto.setPaymentTerm(sellOrderDTO.getPaymentTerm());
		sellOrderDto.setPerFlag(sellOrderDTO.getPerFlag());
		sellOrderDto.setCardNo(cardNo);// ���ĵĿ���
		try {
			submitOrderService.submitOrderAtHandOut(sellOrderDto);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);// ���Ĵ������Ŀ���
			delSellOrderOrigCardList(sellOrderInputDTO.getOrderId());
			entityStockService.delStockOperaterRecord(sellOrderInputDTO
					.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),
					sellOrderDto.getDefaultEntityId(), "1");
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			updateStockStat(oldCardNo, defaultEntityId, "3");
			logger.error("[" + reqData.getRESPSEQNO() + "]" + "������"
					+ sellOrderInputDTO.getOrderId() + "��������ȷ��ʧ�ܣ�");
			throw new Exception(e);
		}
		// ��������ȷ��
		try {
			sellOrderDto.setInitActStat("0");
			sellOrderDto.setOrderState("32");
			sellOrderDto.setTotalPrice("0");
			submitOrderService.submitOrderAtSendConfirmByRecharge(sellOrderDto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);// ���Ĵ������Ŀ���
			delSellOrderOrigCardList(sellOrderInputDTO.getOrderId());
			entityStockService.delStockOperaterRecord(sellOrderInputDTO
					.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),
					sellOrderDto.getDefaultEntityId(), "1");
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			updateStockStat(oldCardNo, defaultEntityId, "3");
			logger.error("[" + reqData.getRESPSEQNO() + "]" + "������"
					+ sellOrderInputDTO.getOrderId() + "��������ȷ��ʧ�ܣ�");
			throw new Exception(e);
			// throw new BizServiceException("�ۿ�������" + sellOrderDto.getOrderId()
			// + ",��������ȷ��ʧ�ܣ������жϣ�");
		}

		// ����ȷ��
		SellOrderPaymentDTO sellOrderPaymentDTO = new SellOrderPaymentDTO();
		sellOrderPaymentDTO.setDefaultEntityId(defaultEntityId);
		sellOrderPaymentDTO.setLoginUserId(sellOrderDto.getLoginUserId());
		sellOrderPaymentDTO.setOrderId(sellOrderDto.getOrderId());
		sellOrderPaymentDTO.setPaymentAmount("0");
		sellOrderPaymentDTO.setPaymentType("1");
		// ��Ӷ������ʽ
		try {
			sellOrderPaymentService.insertOrderPayment(sellOrderPaymentDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);// ���Ĵ������Ŀ���
			delSellOrderOrigCardList(sellOrderInputDTO.getOrderId());
			entityStockService.delStockOperaterRecord(sellOrderInputDTO
					.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),
					sellOrderDto.getDefaultEntityId(), "1");
			updateStockStat(oldCardNo, defaultEntityId, "3");
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("[" + reqData.getRESPSEQNO() + "]" + "������"
					+ sellOrderInputDTO.getOrderId() + "��Ӷ������ʽʧ�ܣ�");
			throw new Exception(e);
			// throw new BizServiceException("�ۿ�������" + sellOrderDto.getOrderId()
			// + ",��Ӷ������ʽʧ�ܣ������жϣ�");
		}

		// ����ȷ���ύ����
		SellOrderDTO dto = new SellOrderDTO();
		dto.setDefaultEntityId(sellOrderDto.getDefaultEntityId());
		dto.setLoginUserId(sellOrderDto.getLoginUserId());
		dto.setOrderId(sellOrderDto.getOrderId());
		dto.setOrderState("32");
		dto.setOrderType(sellOrderDto.getOrderType());
		dto.setPaymentState("2");
		dto.setPaymentTerm("2");
		try {
			submitOrderService.confirmAtOrderPayment(dto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);// ���Ĵ������Ŀ���
			delSellOrderOrigCardList(sellOrderInputDTO.getOrderId());
			entityStockService.delStockOperaterRecord(sellOrderInputDTO
					.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),
					sellOrderDto.getDefaultEntityId(), "1");
			updateStockStat(oldCardNo, defaultEntityId, "3");
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("[" + reqData.getRESPSEQNO() + "]" + "������"
					+ sellOrderInputDTO.getOrderId() + "��������ȷ���ύʧ�ܣ�");
			throw new Exception(e);
			// throw new BizServiceException("�ۿ�������" + dto.getOrderId()
			// + ",��������ȷ���ύʧ�ܣ������жϣ�");
		}

		dto.setInitActStat("0");
		// �������
		try {
			submitOrderService.submitOrderForPayment(dto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);// ���Ĵ������Ŀ���
			delSellOrderOrigCardList(sellOrderInputDTO.getOrderId());
			entityStockService.delStockOperaterRecord(sellOrderInputDTO
					.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),
					sellOrderDto.getDefaultEntityId(), "1");
			updateStockStat(oldCardNo, defaultEntityId, "3");
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("[" + reqData.getRESPSEQNO() + "]" + "������"
					+ sellOrderInputDTO.getOrderId() + "�����������ʧ�ܣ�");
			throw new Exception(e);
			// throw new BizServiceException("�ۿ�������" + dto.getOrderId()
			// + ",�����������ʧ�ܣ������жϣ�");
		}

		// �޸Ķ���״̬Ϊ�Ѽ���
		try {
			SellOrderExample sellOrderExample = new SellOrderExample();
			sellOrderExample.createCriteria().andOrderIdEqualTo(
					sellOrderInputDTO.getOrderId());
			SellOrder order = new SellOrder();
			order.setInitActStat("1");// �����Ѽ���
			sellOrderMapper.updateByExampleSelective(order, sellOrderExample);
		} catch (Exception e) {
			// TODO: handle exception
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);// ���Ĵ������Ŀ���
			delSellOrderOrigCardList(sellOrderInputDTO.getOrderId());
			entityStockService.delStockOperaterRecord(sellOrderInputDTO
					.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),
					sellOrderDto.getDefaultEntityId(), "1");
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			updateStockStat(oldCardNo, sellOrderDto.getDefaultEntityId(), "3");
			logger.error("[" + reqData.getRESPSEQNO() + "]" + "������"
					+ sellOrderInputDTO.getOrderId() + "����ͨѶʧ�ܣ�");
			throw new Exception(e);
		}
		// �����ķ��ͱ��ģ������¿���ԭ�п������¿��ļ���״̬��ԭ�п�����״̬
		try {
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("TxnCode", "P004");// ������
			map.put("cardNo", cardNo);// �¿�����
			map.put("oldCardNo", oldCardNo);// ԭ�п�����
			map.put("channel", reqData.getCHANNEL());// ���ĵ�������
			String xml = ParseToXML.converterXML(map);
			String msg = SocketSend.SendToCore(xml);
			Map<String, String> Xmlmap = XmlDom.parse2(msg);
			String rspCode = Xmlmap.get("RESP_CODE");
			if (!rspCode.equals("00")) {
				throw new Exception("���Ĵ���ʧ�ܣ�");
			}
		} catch (Exception e) {
			// TODO: handle exception
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);// ���Ĵ������Ŀ���
			delSellOrderOrigCardList(sellOrderInputDTO.getOrderId());
			entityStockService.delStockOperaterRecord(sellOrderInputDTO
					.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),
					sellOrderDto.getDefaultEntityId(), "1");
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			updateStockStat(oldCardNo, sellOrderDto.getDefaultEntityId(), "3");
			logger.error("[" + reqData.getRESPSEQNO() + "]" + "������"
					+ sellOrderInputDTO.getOrderId() + "����ͨѶʧ�ܣ�");
			throw new Exception(e);
		}

		TermSellOrder termSellOrder = new TermSellOrder();
		termSellOrder.setBranchId(reqData.getBRANCH_ID());// ��������
		termSellOrder.setChannel("70000023");// ϵͳ������
		termSellOrder.setTermChannel(reqData.getCHANNEL());// ����������
		termSellOrder.setOrderId(sellOrderInputDTO.getOrderId());
		termSellOrder.setOrderType(OrderConst.ORDER_TYPE_ORDER_CHANGE_CARD);// ���۸��˼���������
		termSellOrder.setDataState("1");// ����״̬1.����
		termSellOrder.setReqseqno(reqData.getREQSEQNO());// ������ˮ
		termSellOrder.setIssuerId(reqData.getISSUER_ID());// Ӫ������
		termSellOrder.setTermId(reqData.getTERM_ID());// �ն˺�
		termSellOrder.setMchntCd(reqData.getMCHNT_CD());// �̻���
		termSellOrder.setCreateTime(DateUtil.getCurrentTime());// ����ʱ��
		termSellOrder.setOrderDate(DateUtil.getCurrentDateStr());
		termSellOrder.setOrderState("32");
		try {
			termSellOrderMapper.insertSelective(termSellOrder);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			delSellOrderOrigCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);// ���Ĵ������Ŀ���
			entityStockService.delStockOperaterRecord(sellOrderInputDTO
					.getOrderId());
			updateStockStat(cardNo, sellOrderDto.getDefaultEntityId(), "1");
			updateStockStat(oldCardNo, sellOrderDto.getDefaultEntityId(), "3");
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("[" + reqData.getRESPSEQNO() + "]" + "������"
					+ sellOrderInputDTO.getOrderId() + "�洢����ʧ�ܣ�");
			throw new Exception(e);
		}

		resp.setRESP_CODE(ResponseCode.SUCCESS);
		return resp;
	}

	public String getDateFormat(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String returnValue = null;
		try {
			returnValue = simpleDateFormat.format(date);
		} catch (Exception e) {

		}
		return returnValue;
	}

	public boolean checkIssueId(String id) {
		SellerExample ex = new SellerExample();
		ex.createCriteria().andEntityIdEqualTo(id).andDataStateEqualTo("1")
				.andSellerStateEqualTo("1");
		List<Seller> list = sellerMapper.selectByExample(ex);

		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	public void updateStockStat(String cardNo, String entityId,
			String enStockState) {
		EntityStockExample ex = new EntityStockExample();
		ex.createCriteria().andEntityIdEqualTo(entityId)
				.andCardNoEqualTo(cardNo);
		EntityStock enStock = new EntityStock();
		enStock.setStockState(enStockState);
		entityStockMapper.updateByExampleSelective(enStock, ex);
	}

	public void updateAccCardInfo(String cardNo) {
		AccCardInfo acc = new AccCardInfo();
		AccCardInfoExample ex = new AccCardInfoExample();
		ex.createCriteria().andCardNoEqualTo(cardNo);
		accCardInfoMapper.updateCardHolderByExample(acc, ex);
	}

	public void delSellOrderOrigCardList(String orderId) {
		SellOrderOrigCardListExample ex = new SellOrderOrigCardListExample();
		ex.createCriteria().andOrderIdEqualTo(orderId);
		sellOrderOrigCardListMapper.deleteByExample(ex);
	}

	/**
	 * У�鿨Ƭ��������
	 * 
	 * @return
	 */
	public boolean checkCardPwd(MessageSyncP004Req reqData) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("TxnCode", "P011");
		map.put("cardNo", reqData.getOLD_CARD_NO());
		map.put("channel", reqData.getCHANNEL());
		map.put("cardPwd", reqData.getCARD_PWD());
		map.put("pwdType", "2");
		String xml = ParseToXML.converterXML(map);
		String msg = SocketSend.SendToCore(xml);
		Map<String, String> Xmlmap = XmlDom.parse2(msg);
		String rspCode = Xmlmap.get("RESP_CODE");
		if (rspCode.trim().equals("00")) {
			return true;
		}

		return false;
	}

}
