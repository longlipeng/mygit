package com.allinfinance.prepay.processor.ipos;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.allinfinance.prepay.dao.AttachInfoDAO;
import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.dao.LeaguerInfoDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.AccCardInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.CardHolderMapper;
import com.allinfinance.prepay.mapper.svc_mng.CustomerMapper;
import com.allinfinance.prepay.mapper.svc_mng.DeliveryContactMapper;
import com.allinfinance.prepay.mapper.svc_mng.EntityContactMapper;
import com.allinfinance.prepay.mapper.svc_mng.EntityDeliveryMapper;
import com.allinfinance.prepay.mapper.svc_mng.EntityDictInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.EntityStockMapper;
import com.allinfinance.prepay.mapper.svc_mng.LeaguerInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.ProductMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellContractMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellProdContractMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellerMapper;
import com.allinfinance.prepay.mapper.svc_mng.TbOrderResourceMapper;
import com.allinfinance.prepay.message.BasicMessage;
import com.allinfinance.prepay.message.MessageSyncP024Req;
import com.allinfinance.prepay.message.MessageSyncP024Resp;
import com.allinfinance.prepay.model.AccCardInfo;
import com.allinfinance.prepay.model.AccCardInfoExample;
import com.allinfinance.prepay.model.CardHolder;
import com.allinfinance.prepay.model.CardHolderExample;
import com.allinfinance.prepay.model.Customer;
import com.allinfinance.prepay.model.CustomerExample;
import com.allinfinance.prepay.model.DeliveryContact;
import com.allinfinance.prepay.model.DeliveryContactExample;
import com.allinfinance.prepay.model.EntityContact;
import com.allinfinance.prepay.model.EntityContactExample;
import com.allinfinance.prepay.model.EntityDelivery;
import com.allinfinance.prepay.model.EntityDeliveryExample;
import com.allinfinance.prepay.model.EntityDictInfoExample;
import com.allinfinance.prepay.model.EntityStock;
import com.allinfinance.prepay.model.EntityStockExample;
import com.allinfinance.prepay.model.InvoiceCompany;
import com.allinfinance.prepay.model.LeaguerInfo;
import com.allinfinance.prepay.model.Product;
import com.allinfinance.prepay.model.ProductExample;
import com.allinfinance.prepay.model.SellContract;
import com.allinfinance.prepay.model.SellContractExample;
import com.allinfinance.prepay.model.SellProdContractExample;
import com.allinfinance.prepay.model.TbOrderResource;
import com.allinfinance.prepay.processor.IProcessor;
import com.allinfinance.prepay.service.AccAccountInfoService;
import com.allinfinance.prepay.service.CardholderService;
import com.allinfinance.prepay.service.CustomerService;
import com.allinfinance.prepay.service.DeliveryContactService;
import com.allinfinance.prepay.service.EntityContactService;
import com.allinfinance.prepay.service.EntityDeliveryService;
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
import com.allinfinance.prepay.utils.Config;
import com.allinfinance.prepay.utils.DataBaseConstant;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.IDCardCheck;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.prepay.utils.ResponseCode;
import com.allinfinance.prepay.utils.StringUtil;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderPaymentDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerAcctypeContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerProductContractDTO;
/**
 * ��̨����
 * @author 
 *
 */
@Service
public class SyncP024Processor implements IProcessor {
	private static Logger logger = Logger.getLogger(SyncP024Processor.class);
	
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
	private AccAccountInfoService accAccountInfoService;
	@Autowired
	private LeaguerInfoDAO leaguerInfoDAO;
	@Autowired
	private AttachInfoDAO attachInfoDAO;
	@Autowired
	private ProductMapper productMapper;
	@Autowired 
	private EntityContactMapper entityContactMapper;
	@Autowired
	private EntityDictInfoMapper entityDictInfoMapper;
	@Autowired
	private EntityDeliveryService deliveryPointService;
	@Autowired
	private DeliveryContactService deliveryContactService;
	@Autowired
	TbOrderResourceMapper tbOrderResourceMapper;
	@Override
	public BasicMessage process(BasicMessage req) throws Exception {
		
		MessageSyncP024Req reqData = (MessageSyncP024Req)req;
		MessageSyncP024Resp resp =(MessageSyncP024Resp) reqData.createResp() ;
		CustomerDTO customerDTO = new CustomerDTO();
		CardholderDTO cardholderDTO = new CardholderDTO();
		
		
		
		if(StringUtils.isNotEmpty(reqData.getId_type())){
			if(reqData.getId_type().trim().equals("1")){
				customerDTO.setCorpCredType("1");
				cardholderDTO.setIdType("1");
			}else if(reqData.getId_type().trim().equals("2")){
				customerDTO.setCorpCredType("3");
				cardholderDTO.setIdType("2");
			}else if(reqData.getId_type().trim().equals("3")){
				customerDTO.setCorpCredType("6");
				cardholderDTO.setIdType("3");
			}else{
				//û�����֤������
				resp.setRESP_CODE(ResponseCode.ID_TYPE_ERROR);
				//resp.setRESP_TEXT("֤����������");
				logger.error("֤����������["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
		}else{
			//resp.setRESP_TEXT("֤�����Ͳ���Ϊ�գ�");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("֤�����Ͳ���Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		
		/*֤����*/
		if(StringUtils.isNotEmpty(reqData.getId_no())){
			if(reqData.getId_type().trim().equals("1")){
				//���֤
				String errMsg=IDCardCheck.IDCardValidate(reqData.getId_no().toUpperCase());
				if(errMsg==null||errMsg.equals("")){
					customerDTO.setCorpCredId(reqData.getId_no().toUpperCase());
					cardholderDTO.setIdNo(reqData.getId_no().toUpperCase());
				}else{
					//resp.setRESP_TEXT("֤��������");
					resp.setRESP_CODE(ResponseCode.ID_NO_ERROR);
					logger.error("֤�����������֤����["+reqData.getRESPSEQNO()+"]");
					return resp;
				}
				
			}else if(reqData.getId_type().equals("2")){
				//����
				boolean isTrue = reqData.getId_no().trim().matches("([0-9a-zA-Z]*)");
	    		 if(isTrue==true){
	    			 customerDTO.setCorpCredId(reqData.getId_no().trim());
	    			 cardholderDTO.setIdNo(reqData.getId_no().trim());
	    		 }else{
	    			 //resp.setRESP_TEXT("֤��������");
	    			 resp.setRESP_CODE(ResponseCode.ID_NO_ERROR);
	    			 logger.error("֤�������󣨻��գ���["+reqData.getRESPSEQNO()+"]");
	    			 return resp;
	    		 }
			}else if(reqData.getId_type().trim().equals("3")){
				//����
				customerDTO.setCorpCredId(reqData.getId_no().trim());
   			 	cardholderDTO.setIdNo(reqData.getId_no().trim());
			}
		}else{
			//resp.setRESP_TEXT("֤���Ų���Ϊ�գ�");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("֤���Ų���Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		if(StringUtil.isNotEmpty(reqData.getGender())){
			if(reqData.getGender().trim().endsWith("1")||reqData.getGender().trim().endsWith("2")){
				cardholderDTO.setCardholderGender(reqData.getGender().trim());
			}else{
				resp.setRESP_CODE(ResponseCode.GENDER_ERROR);
				logger.error("�Ա�����["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
		}
		/*��ν*/
		if(cardholderDTO.getCardholderGender().trim().equals("1")){
			cardholderDTO.setCardholderSalutation("1");
		}
		else if(cardholderDTO.getCardholderGender().trim().equals("2")){
			cardholderDTO.setCardholderSalutation("3");//Ůʿ
		}
		
		
		/*�ͻ�֤��ʧЧ����*/
		if(DateUtil.isValidDate(reqData.getId_validity())==false){
			resp.setRESP_CODE(ResponseCode.VALIDITY_ERROR);
			logger.error("���ڸ�ʽ��["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		
		/*����*/
		if(StringUtil.isEmpty(reqData.getBirthday())){
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("����Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
			return resp;
		}else if(DateUtil.isValidDate(reqData.getBirthday())==false){
			resp.setRESP_CODE(ResponseCode.VALIDITY_ERROR);
			logger.error("�������ڸ�ʽ��["+reqData.getRESPSEQNO()+"]");
			return resp;
		}else{
			cardholderDTO.setCardholderBirthday(reqData.getBirthday());
		}
		cardholderDTO.setMailingAddress(reqData.getAddress());
		customerDTO.setCustomerAddress(reqData.getAddress());
		customerDTO.setCorpCredEndValidity(DateUtil.formatStringDate(reqData.getId_validity()));
		//����
		cardholderDTO.setCardholderEmail(reqData.getEmail());
		//����
		customerDTO.setNationality(reqData.getNationality());
		String defaultEntityId=reqData.getISSUER_ID().trim();	
		cardholderDTO.setDefaultEntityId(reqData.getISSUER_ID().trim());
		customerDTO.setFatherEntityId(reqData.getISSUER_ID().trim());
		customerDTO.setDefaultEntityId(reqData.getISSUER_ID());
		customerDTO.setSalesmanId(Config.getUserId());
		customerDTO.setFatherEntityId(reqData.getISSUER_ID().trim());
		customerDTO.setCreateUser(Config.getUserId());
		customerDTO.setLoginUserId(Config.getUserId());
		customerDTO.setCreateTime(DateUtil.getCurrentTime());
		customerDTO.setModifyTime(DateUtil.getCurrentTime());
		customerDTO.setModifyUser(Config.getUserId());
		cardholderDTO.setLoginUserId(Config.getUserId());
		customerDTO.setCustomerName(reqData.getUser_name().trim());
		cardholderDTO.setFirstName(reqData.getUser_name().trim());
		customerDTO.setCustomerTelephone(reqData.getMobile().trim());
		cardholderDTO.setCardholderMobile(reqData.getMobile().trim());
		/*�ֿ��˷���*/
		cardholderDTO.setCardholderSegment("0");
		/*����*/
		customerDTO.setChannel("3");//3�ŵ�
		/*�ͻ���������Ϣ����дΪĬ��*/
		/*�ͻ���ҵ���*/
		customerDTO.setActivitySector("2");
		/*�ͻ�ְҵ���*/
		//ְҵ
		if(StringUtil.isNotEmpty(reqData.getOccupation())){
			EntityDictInfoExample ex=new EntityDictInfoExample();
			//�����ֶ�type 145 �ͻ�ְҵ
			ex.createCriteria().andEntityIdEqualTo(reqData.getISSUER_ID())
				.andDictStateEqualTo("1")
				.andDictTypeCodeEqualTo("145")
				.andDictCodeEqualTo(reqData.getOccupation());
			try {
			int count=entityDictInfoMapper.countByExample(ex);
			if(count==0){
				resp.setRESP_CODE(ResponseCode.OCCUPATION_ERROR);
				logger.error("ְҵ����["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("["+reqData.getRESPSEQNO()+"]"+e);
				throw new Exception(e);
			}
			
		}
		customerDTO.setAwareness(reqData.getOccupation());
		customerDTO.setCorpActionAtLaw("2");
		customerDTO.setCreditStatus("0");
		customerDTO.setCusState("1");//1.�����  4δ���
		customerDTO.setCustomerType("1");
		customerDTO.setHasDm("1");
		customerDTO.setPaymentTerm("2");
		customerDTO.setPictureSave("2");
		customerDTO.setPunishRecordFlag("2");
		customerDTO.setSalesRegionId("1");
		/*�ֿ�����ϢĬ���ֶ�*/
		cardholderDTO.setCardholderFunction("1");
		cardholderDTO.setCardholderState("1");
		//�ͻ���������
		customerDTO.setAcceptArea(reqData.getArea_code());
		cardholderDTO.setCardholderArea(reqData.getArea_code());
		ProductExample productExample=new ProductExample();
		productExample.createCriteria().andProductIdEqualTo(reqData.getCard_product())
		.andDataStateEqualTo("1").andEntityIdEqualTo(reqData.getISSUER_ID());
		List<Product> products = productMapper.selectByExample(productExample);
		if(products==null||products.size()==0){
			resp.setRESP_CODE(ResponseCode.PRODUCT_ERROR);
			logger.error("����ƷID��["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		
		if(products.get(0).getOnymousStat().endsWith("2")){
			resp.setRESP_CODE(ResponseCode.PRODUCT_ERROR);
			logger.error("�ýӿڲ������۲���������Ʒ��["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		if(products.get(0).getOnymousStat().endsWith("1")&&!products.get(0).getCardType().endsWith("3")){
			//����ѯ  ������������ǹ̶�  ���ֵ0
			OrderReadyDTO orderReadyDTO=new OrderReadyDTO();
			orderReadyDTO.setProductId(reqData.getCard_product());
			orderReadyDTO.setFaceValue("0");
			orderReadyDTO.setFaceValueType("1");
			orderReadyDTO.setDefaultEntityId(reqData.getISSUER_ID());
			List<HashMap<String, Object>> cardNum=entityStockMapper.selectStockByProductId(orderReadyDTO);
			if(cardNum==null||cardNum.size()==0){
				resp.setRESP_CODE(ResponseCode.CARDNO_ISNULL);
				logger.error("����Ʒ��治�㣡["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			int num=(Integer) cardNum.get(0).get("stockNumber");
			if(num==0){
				resp.setRESP_CODE(ResponseCode.CARDNO_ISNULL);
				logger.error("����Ʒ��治�㣡["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
		}
		
		CustomerDTO customerByIdNo = customerService
				.getCustomerByIdNo(customerDTO);
		customerDTO.setFatherEntityId(reqData.getISSUER_ID());
		CardHolder cardHolder = new CardHolder();
		try {
			if (customerByIdNo != null) {

				String modifyTime = DateUtil.getCurrentTime();
				// ���¿ͻ���Ϣ

				Customer customer = new Customer();
				CustomerExample CustomerEx = new CustomerExample();
				ReflectionUtil.copyProperties(customerDTO, customer);
				customer.setEntityId(customerByIdNo.getEntityId());
				customer.setFatherEntityId(customerByIdNo.getFatherEntityId());
				customer.setModifyUser(customerDTO.getModifyUser());
				customer.setModifyTime(modifyTime);
				cardholderDTO.setEntityId(customer.getEntityId());
				customerDTO.setEntityId(customer.getEntityId());
				CustomerEx
						.createCriteria()
						.andEntityIdEqualTo(customer.getEntityId())
						.andFatherEntityIdEqualTo(
								customerByIdNo.getFatherEntityId());
				logger.debug("�޸Ŀͻ���Ϣ��["+reqData.getRESPSEQNO()+"]");
				CustomerMapper.updateByExampleSelective(customer, CustomerEx);

				// ���¿�ݵ���Ϣ
				EntityDelivery entityDelivery = new EntityDelivery();
				entityDelivery.setDeliveryAddress(customerDTO.getCustomerAddress());
				entityDelivery.setDeliveryName(customerDTO.getCustomerName());
				entityDelivery.setDeliveryPostcode(customerDTO
						.getCustomerPostcode());
				entityDelivery.setModifyTime(modifyTime);
				entityDelivery.setModifyUser(customerDTO.getModifyUser());
				EntityDeliveryExample entityDeliveryExample = new EntityDeliveryExample();
				entityDeliveryExample.createCriteria()
						.andEntityIdEqualTo(customerByIdNo.getEntityId())
						.andDataStateEqualTo("1").andDefaultFlagEqualTo("1");
				logger.debug("���¿�ݵ���Ϣ��["+reqData.getRESPSEQNO()+"]");
				List<EntityDelivery> entityDeliveryList=entityDeliveryMapper.selectByExample(entityDeliveryExample);
				if(entityDeliveryList.size()>0){
					entityDeliveryMapper.updateByExampleSelective(entityDelivery,
							entityDeliveryExample);
					entityDelivery.setDeliveryId(entityDeliveryList.get(0).getDeliveryId());
					entityDelivery.setDeliveryName(entityDeliveryList.get(0).getDeliveryName());
				}else{
					entityDelivery.setEntityId(customerDTO.getEntityId());
					entityDelivery.setDeliveryName(customer.getCustomerName());
					entityDelivery.setDeliveryPostcode(customer.getCustomerPostcode());
					entityDelivery.setDeliveryAddress(customer.getCustomerAddress());
					entityDelivery.setDefaultFlag("1");
					entityDelivery.setCreateUser(Config.getUserId());
					DeliveryPointDTO dto=new DeliveryPointDTO();
					ReflectionUtil.copyProperties(entityDelivery, dto);
					dto=deliveryPointService.insert(dto);
					ReflectionUtil.copyProperties(dto,entityDelivery);
				}
				
				// ����Ĭ�Ͽ�ݵ���ϵ����Ϣ
					DeliveryContactExample deliveryContactExample = new DeliveryContactExample();
					deliveryContactExample
							.createCriteria()
							.andDefaultFlagEqualTo("1")
							.andDataStateEqualTo("1")
							.andDeliveryPointIdEqualTo(
									entityDelivery.getDeliveryId());
					List<DeliveryContact> deliveryContactList=deliveryContactMapper.selectByExample(deliveryContactExample);
					
					if(deliveryContactList.size()>0){
						DeliveryContact deliveryContact = new DeliveryContact();
						deliveryContact.setContactPhone(customerDTO
								.getCustomerTelephone());
						deliveryContact.setModifyTime(modifyTime);
						deliveryContact.setModifyUser(customerDTO.getModifyUser());
						deliveryContact.setDeliveryContact(customerDTO
								.getCustomerName());
						logger.debug("����Ĭ�Ͽ�ݵ���ϵ����Ϣ��["+reqData.getRESPSEQNO()+"]");
						deliveryContactMapper.updateByExampleSelective(deliveryContact,
								deliveryContactExample);
					}else{
						DeliveryRecipientDTO deliveryContact = new DeliveryRecipientDTO();
						deliveryContact.setDeliveryPointId(entityDelivery.getDeliveryId());
						deliveryContact.setDeliveryContact(entityDelivery.getDeliveryName());
						deliveryContact.setContactPhone(customer.getCustomerTelephone());
						deliveryContact.setCreateUser(Config.getUserId());
						deliveryContact.setModifyUser(Config.getUserId());
						deliveryContact.setDefaultFlag("1");
						deliveryContactService.insert(deliveryContact);
					}
					
				
				// ����Ĭ����ϵ����Ϣ
				List<ContactDTO> ContactDTOs = contactService
						.inqueryContact(customerByIdNo.getEntityId());
				for (ContactDTO contactDTO : ContactDTOs) {
					if (contactDTO.getDataState().trim().equals("1")
							&& contactDTO.getDefaultFlag().trim().equals("1")) {
						contactDTO.setContactName(customer.getCustomerName());
						contactDTO.setContactType(customer.getCustomerType());
						contactDTO.setContactGender(cardholderDTO
								.getCardholderGender());
						contactDTO.setContactMobilePhone(customer
								.getCustomerTelephone());
						contactDTO.setModifyUser(customerDTO.getModifyUser());
						contactDTO.setModifyTime(modifyTime);
						contactDTO.setPapersNo(customerDTO.getCorpCredId());
						contactDTO.setPapersType(customerDTO.getCorpCredType());
						logger.debug("����Ĭ����ϵ����Ϣ��["+reqData.getRESPSEQNO()+"]");
						contactService.updateContact(contactDTO);
					}
				}

				// ����Ĭ�Ϸ�Ʊ��ַ��Ϣ
				List<InvoiceAddressDTO> invoiceAddressDTOs = invoiceAddressService
						.inquery(customerByIdNo.getEntityId());
				if(invoiceAddressDTOs!=null){
				
					for (InvoiceAddressDTO invoiceAddressDTO : invoiceAddressDTOs) {
						if (invoiceAddressDTO.getDataState().trim().equals("1")
								&& invoiceAddressDTO.getDefaultFlag().trim()
										.equals("1")) {
							InvoiceAddressDTO invoiceAddress = new InvoiceAddressDTO();
							ReflectionUtil.copyProperties(invoiceAddressDTO,
									invoiceAddress);
		
							invoiceAddress.setInvoiceAddress(customer
									.getCustomerAddress());
							invoiceAddress.setAddressPostcode(customer
									.getCustomerPostcode());
							invoiceAddress.setModifyUser(customerDTO.getModifyUser());
							invoiceAddress.setModifyTime(modifyTime);
							invoiceAddress.setInvoiceRecipient(customer
									.getCustomerName());
							logger.debug(" ����Ĭ�Ϸ�Ʊ��ַ��Ϣ["+reqData.getRESPSEQNO()+"]");
							invoiceAddressService.updateByPrimaryKey(invoiceAddress);
						}
					}
				}

				// ����Ĭ�Ϸ�Ʊ��˾��Ϣ
				List<InvoiceCompany> invoiceCompanys = invoiceCompanyService
						.selectByEntityId(customerByIdNo.getEntityId());
				if(invoiceCompanys!=null){
					for (InvoiceCompany invoiceCompany : invoiceCompanys) {
						if (invoiceCompany.getDataState().trim().equals("1")
								&& invoiceCompany.getDefaultFlag().trim().equals("1")) {
							invoiceCompany.setInvoiceCompanyName(customerDTO
									.getCustomerName());
							invoiceCompany.setModifyTime(modifyTime);
							invoiceCompany.setModifyUser(customerDTO.getModifyUser());
							logger.debug("����Ĭ�Ϸ�Ʊ��˾��Ϣ��["+reqData.getRESPSEQNO()+"]");
							invoiceCompanyService.updateByPrimaryKey(invoiceCompany);
						}
					}
				}

			} else {
				logger.debug("�����ͻ���["+reqData.getRESPSEQNO()+"]");
				CustomerDTO insertCustomer = customerService
						.insertCustomer(customerDTO);
				cardholderDTO.setEntityId(insertCustomer.getEntityId());
				customerDTO.setEntityId(insertCustomer.getEntityId());
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("["+reqData.getRESPSEQNO()+"]"+e);
			throw new Exception(e);
		}
		try {
			//�ͻ���������
			LeaguerInfo custLeaguerInfo= new LeaguerInfo();
			custLeaguerInfo.setCusNo(customerDTO.getEntityId());
			//  00 ����ͻ�  01����ֿ���
			custLeaguerInfo.setCusType("00");
			custLeaguerInfo.setCusArea(customerDTO.getAcceptArea());
			custLeaguerInfo.setLeaguerId(reqData.getUser_id());
			
			LeaguerInfo info=leaguerInfoDAO.selectLeaguerInfo(custLeaguerInfo);
			if(info!=null){
				leaguerInfoDAO.updateLeaguerInfo(custLeaguerInfo);
			}else{
				leaguerInfoDAO.insertLeaguerInfo(custLeaguerInfo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("["+reqData.getRESPSEQNO()+"]"+e);
			throw new Exception(e);
		}
		
		try{
			int attachCount=attachInfoDAO.selectAttachInfoDTO(customerDTO,"00",customerDTO.getEntityId());
			if(attachCount>0){
				
				attachInfoDAO.updateAttachInfoDTO(customerDTO, "00", cardholderDTO.getCardholderGender(),customerDTO.getEntityId());
			}else{
				attachInfoDAO.insertAttachInfoDTO(customerDTO, "00",cardholderDTO.getCardholderGender(), customerDTO.getEntityId());
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("["+reqData.getRESPSEQNO()+"]"+e);
			throw new Exception(e);
		}
		/* ��ѯ���ֿ����Ƿ���ڣ�������������Ϊ���µ���Ϣ�����������������Ϣ */
		CardHolder cardholder = new CardHolder();
		ReflectionUtil.copyProperties(cardholderDTO, cardholder);
		cardholder.setModifyTime(DateUtil.getCurrentTime());
		cardholder.setModifyUser(customerDTO.getModifyUser());
		CardHolderExample cardholderExample = new CardHolderExample();
		cardholderExample.createCriteria()
				.andIdTypeEqualTo(cardholderDTO.getIdType())
				.andIdNoEqualTo(cardholderDTO.getIdNo()).andDataStateEqualTo("1");
		List<CardHolder> cardholderLists = cardHolderMapper
				.selectByExample(cardholderExample);
		try {
			if (cardholderLists.size() > 0) {
				cardholder.setEntityId(customerByIdNo.getEntityId());
				logger.debug("�޸ĳֿ�����Ϣ��["+reqData.getRESPSEQNO()+"]");
				cardHolderMapper.updateByExampleSelective(cardholder,
						cardholderExample);
				ReflectionUtil.copyProperties(cardholderLists.get(0), cardHolder);
				//�������󶨹�ϵ(�˻��ϲ��汾����)
				//accAccountInfoService.insertAccInfoAndBinding(cardHolder.getCardholderId(), "1");
			} else {
				logger.debug("���ӳֿ�����Ϣ��["+reqData.getRESPSEQNO()+"]");
				cardHolder = cardHolderService.insert(cardholderDTO);
				//�������󶨹�ϵ(�˻��ϲ��汾����)
				//accAccountInfoService.insertAccInfoAndBinding(cardHolder.getCardholderId(), "1");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("["+reqData.getRESPSEQNO()+"]"+e);
			throw new Exception(e);
		}
		try{
			//�ֿ�����������
			LeaguerInfo leaguerInfo= new LeaguerInfo();
			leaguerInfo.setCusNo(cardHolder.getCardholderId());
			//  00 ����ͻ�  01����ֿ���
			leaguerInfo.setCusType("01");
			leaguerInfo.setCusArea(cardholderDTO.getCardholderArea());
			leaguerInfo.setLeaguerId(reqData.getUser_id());
			LeaguerInfo cardholderinfo=leaguerInfoDAO.selectLeaguerInfo(leaguerInfo);
			if(cardholderinfo!=null){
				leaguerInfoDAO.updateLeaguerInfo(leaguerInfo);
			}else{
				leaguerInfoDAO.insertLeaguerInfo(leaguerInfo);
			}
			
			int attachCounts=attachInfoDAO.selectAttachInfoDTO(customerDTO,"01",cardHolder.getCardholderId());
			if(attachCounts>0){
				attachInfoDAO.updateAttachInfoDTO(customerDTO, "01", cardholderDTO.getCardholderGender(),cardHolder.getCardholderId());
			}else{
				attachInfoDAO.insertAttachInfoDTO(customerDTO, "01", cardholderDTO.getCardholderGender(),cardHolder.getCardholderId());
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("["+reqData.getRESPSEQNO()+"]"+e);
			throw new Exception(e);
		}
		//����ǵ��ӿ���Ʒ��������ۿ�
		if(products.get(0).getCardType().equals("3")){
			resp.setRESP_CODE("0000");
			return resp;
		}
		
		
		CustomerDTO customer = customerService
				.getCustomerByIdNo(customerDTO);
		SellerContractDTO sellerContractDTO = new SellerContractDTO();
		sellerContractDTO.setContractBuyer(customer.getEntityId());
		sellerContractDTO.setContractSellerName(customer
				.getCustomerName());
		sellerContractDTO.setLoginUserId(Config.getUserId());
		sellerContractDTO.setContractSeller(defaultEntityId);//������Ļ�����
		sellerContractDTO.setDefaultEntityId(defaultEntityId);
		sellerContractDTO.setDeliveryFee("0");
		sellerContractDTO.setContractState("1");
		SellContract sellContract = new SellContract();
		ReflectionUtil.copyProperties(sellerContractDTO, sellContract);
		SellContractExample sellContractExample = new SellContractExample();
		sellContractExample.createCriteria()
				.andContractBuyerEqualTo(customer.getEntityId())
				.andContractTypeEqualTo("3").andContractStateEqualTo("1");
		List<SellContract> sellContracts = sellContractMapper
				.selectByExample(sellContractExample);
		SellerContractDTO contractDTO = new SellerContractDTO();
		if (null == sellContracts || sellContracts.size() == 0) {
			try {
				contractDTO = sellerContractService.insert(sellerContractDTO);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.error("["+reqData.getRESPSEQNO()+"]"+"�ͻ���"+customerDTO.getCustomerName()+"��ӿͻ���ͬʧ�ܣ�");
				throw new Exception(e);
			}

		}
		if (contractDTO.getSellContractId() == null) {
			contractDTO.setSellContractId(sellContracts.get(0).getSellContractId());
		}
		
		// ��Ӳ�Ʒ
		SellerProductContractDTO sellerProductContractDTO = new SellerProductContractDTO();
		sellerProductContractDTO.setProductId(reqData.getCard_product());
		sellerProductContractDTO.setAnnualFee("0");
		sellerProductContractDTO.setCardFee("0");
		sellerProductContractDTO.setDefaultEntityId(defaultEntityId);
		sellerProductContractDTO.setLoginUserId(sellerContractDTO
				.getLoginUserId());
		sellerProductContractDTO.setSellContractId(contractDTO
				.getSellContractId());
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductId(sellerProductContractDTO.getProductId());
		productDTO.setDefaultEntityId(defaultEntityId);
		// ��Ӻ�ͬʱ�鿴��Ʒ
		productDTO = productService.viewProductForContract(productDTO);
		List<ServiceDTO> productServices = productDTO.getServices();
		// ��Ӻ�ͬ������ϸ
		if (null != productServices && productServices.size() > 0) {
			List<SellerAcctypeContractDTO> accDTOs = new ArrayList<SellerAcctypeContractDTO>();
			for (int i = 0; i < productServices.size(); i++) {
				SellerAcctypeContractDTO accDTO = new SellerAcctypeContractDTO();
				accDTO.setSellContractId(sellerContractDTO.getSellContractId());
				accDTO.setProductId(sellerProductContractDTO.getProductId());
				accDTO.setAcctypeId(productServices.get(i).getServiceId());
				// ���������ݶ�Ϊ0000
				accDTO.setTxnNum("0000");
				accDTO.setFee(productServices.get(i).getDefaultRate());
				accDTOs.add(accDTO);
			}
			sellerProductContractDTO.setAccDTOs(accDTOs);
		}
		sellerContractDTO.setDeliveryFee("0");
		sellerContractDTO.setContractState("1");
		sellerContractDTO.setContractType("34");
		// �жϲ�Ʒ�ں�ͬ���Ƿ��Ѵ���
		SellProdContractExample example = new SellProdContractExample();
		example.createCriteria()
				.andProductIdEqualTo(sellerProductContractDTO.getProductId())
				.andSellContractIdEqualTo(
						sellerProductContractDTO.getSellContractId())
				.andDataStateEqualTo("1");
		if (sellProdContractMapper.countByExample(example) == 0) {
			try {
				sellerProductContractService.insert(sellerProductContractDTO);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.error("["+reqData.getRESPSEQNO()+"]"+"�ͻ���"+customerDTO.getCustomerName()+"��Ӳ�Ʒʧ�ܣ�");
				throw new Exception(e);
			}
		}
		String packageFee = productDTO.getPackages().get(0).getPackageFee();
		// ���ۼ���������
		SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();
		SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
		sellOrderDTO.setFaceValue("0");//��һ�γ�ֵ���
		sellOrderDTO.setCardLayoutId(productDTO.getCardLayoutDTOs().get(0).getCardLayoutId());//����
		sellOrderDTO.setPackageId(productDTO.getPackages().get(0).getPackageId());//��װ
		sellOrderDTO.setAdditionalFee("0");// Ĭ�ϸ��ӷ�Ϊ0
		sellOrderDTO.setCardQuantity("0");// ��������
		sellOrderDTO.setDeliveryFee("0");// �ͻ�����
		sellOrderDTO.setDeliveryMeans("1");// �ͻ���ʽ
		sellOrderDTO.setDiscountFee("0");// �ۿ۷�
		sellOrderDTO.setFirstEntityId(customer.getEntityId());// �ͻ���
		sellOrderDTO.setProcessEntityId(sellerContractDTO.getDefaultEntityId());// Ӫ������
		sellOrderDTO.setOrderType("10000011");// ��������
		sellOrderDTO.setSaleMan(sellerContractDTO.getLoginUserId());// ������Ա
		sellOrderDTO.setPayChannel("1");// ֧������
		sellOrderDTO.setPackageFee(packageFee.trim());// ��װ��
		sellOrderDTO.setProductId(sellerProductContractDTO.getProductId());// ��ƷID
		sellOrderDTO.setOrderSource("1");// ������Դ
		sellOrderDTO.setOrderState("1");// ����״̬
		sellOrderDTO.setPaymentDelay("0");// ������������
		sellOrderDTO.setPaymentState("0");// ����״̬Ϊδ֧��
		sellOrderDTO.setPaymentTerm("2");// ֧���ڵ�
		sellOrderDTO.setPerFlag("per");
		sellOrderDTO.setServiceId(productDTO.getServices().get(0)
				.getServiceId());// ��ֵ�˻�
		sellOrderDTO.setServiceName(productDTO.getServices().get(0)
				.getServiceName());// ��ֵ�˻�����
		sellOrderDTO.setCardIssueFee("0");// ����
		sellOrderDTO.setAnnualFee("0");// ���
		sellOrderDTO.setModifyUser(sellerContractDTO.getLoginUserId());// �޸Ķ����û�
		sellOrderDTO.setCreateUser(sellerContractDTO.getLoginUserId());// ���������û�
		sellOrderDTO.setLoginUserId(sellerContractDTO.getLoginUserId());
		sellOrderDTO.setOrderDate(DateUtil.getStringDate());
		sellOrderDTO.setInvoiceDate(DateUtil.getStringDate());
		Float totalPrice = Float.parseFloat("0") + Float.parseFloat(packageFee)
				+ Float.parseFloat("0");
		sellOrderDTO.setTotalPrice(String.valueOf(totalPrice));
		sellOrderInputDTO.setDefaultEntityId(sellerContractDTO
				.getDefaultEntityId());
		sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
		
		try {
			sellOrderInputDTO = orderService.initAdd(sellOrderInputDTO);
			sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
			List<ContactDTO> contactDTOs = contactService
					.inqueryContact(customer.getEntityId());
			sellOrderDTO.setContactId(contactDTOs.get(0).getContactId());
			
			
			
			EntityDeliveryExample entityDeliveryExample = new EntityDeliveryExample();
			entityDeliveryExample.createCriteria()
					.andEntityIdEqualTo(customer.getEntityId())
					.andDataStateEqualTo("1").andDefaultFlagEqualTo("1");
			//��ݵ�ַ
			List<EntityDelivery> entityDeliveryList=entityDeliveryMapper.selectByExample(entityDeliveryExample);
			
			DeliveryContactExample deliveryContactExample = new DeliveryContactExample();
			deliveryContactExample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andDefaultFlagEqualTo("1")
					.andDeliveryPointIdEqualTo(entityDeliveryList.get(0).getDeliveryId());
			deliveryContactExample.setOrderByClause("default_flag desc");
			List<DeliveryContact> deliveryContactList =deliveryContactMapper.selectByExample(deliveryContactExample);
			sellOrderDTO.setDeliveryPoint(entityDeliveryList.get(0).getDeliveryId());
			
			//�ռ���
			sellOrderDTO.setOrderContact(deliveryContactList.get(0).getDeliveryContactId());
			sellOrderInputDTO = orderService.orderInsert(sellOrderDTO);
			TbOrderResource tbOrderResource=new TbOrderResource();
			tbOrderResource.setOrderId(sellOrderDTO.getOrderId());
			tbOrderResource.setResource("ZT");//������Դ����̨
			tbOrderResourceMapper.insertSelective(tbOrderResource);//������Դ��
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("["+reqData.getRESPSEQNO()+"]"+"��������ʧ��");
			throw new Exception(e);
		}
		
		

		// ��ӳֿ���
		/*
		 * CardholderQueryDTO cardholderQueryDTO=new CardholderQueryDTO();
		 * ReflectionUtil.copyProperties(cardholderDTO, cardholderQueryDTO);
		 */
		CardholderDTO cardholderdto = new CardholderDTO();
		ReflectionUtil.copyProperties(cardHolder, cardholderdto);
		String cardholderId = cardHolder.getCardholderId();
		String[] cardholderList = new String[] { cardholderId };
		sellOrderInputDTO.setOrderListStr(cardholderList);
		sellOrderInputDTO.setLoginUserId(sellerContractDTO.getLoginUserId());
		sellOrderInputDTO.setCardholderDTO(cardholderdto);
		try {
			sellOrderInputDTO = orderService
					.insertCardholderList(sellOrderInputDTO);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getSellOrderDTO().getOrderId()+"������ӳֿ���ʧ�ܣ�");
			throw new Exception(e);
		}

		// �ύ����
		/*
		 * String [] ecChoose=new
		 * String[]{sellOrderInputDTO.getSellOrderDTO().getOrderId()};
		 * sellOrderInputDTO.setEc_choose(ecChoose);
		 */
		sellOrderInputDTO.setOrderId(sellOrderInputDTO.getSellOrderDTO()
				.getOrderId());
		try {
			submitOrderService.submitOrderAtInput(sellOrderInputDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getOrderId()+"�����ύʧ�ܣ�");
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
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getOrderId()+"�������ʧ�ܣ�");
			throw new Exception(e);
		}

		// ����׼��
		OrderReadyDTO orderReadyDTO = new OrderReadyDTO();
		orderReadyDTO
				.setProcessEntityId(sellOrderInputDTO.getDefaultEntityId());
		orderReadyDTO
				.setDefaultEntityId(sellOrderInputDTO.getDefaultEntityId());
		orderReadyDTO.setLoginUserId(sellOrder.getLoginUserId());
		orderReadyDTO.setProductId(sellerProductContractDTO.getProductId());
		orderReadyDTO.setOrderId(sellOrderInputDTO.getSellOrderDTO()
				.getOrderId());
		orderReadyDTO.setOrderType("10000011");
		
		List<HashMap<String, String>> entityStockList = 
				entityStockMapper.selectCardNoforOrderReadySign(orderReadyDTO);
		
		if (entityStockList==null||entityStockList.size() == 0) {
			//updateStockStat(cardNo,defaultEntityId);
			resp.setRESP_CODE(ResponseCode.CARDNO_ISNULL);
			logger.error("�ͻ���" + customerDTO.getCustomerName()
					+ "����׼��ʱ����Ʒ��" + productDTO.getProductName() + ",��治�㣡�����жϣ�");
			return resp;
//			throw new BizServiceException("�ͻ���" + customerDTO.getCustomerName()
//					+ "����׼��ʱ����Ʒ��" + productDTO.getProductName() + ",��治�㣡�����жϣ�");
		}
		// ׼���Ŀ���
		String cardNo=entityStockList.get(0).get("cardNo");
		String[] cardNoArray = new String[] {cardNo};
		orderReadyDTO.setCardNoArray(cardNoArray);
		// ����׼��
		try{
		orderReadyService.cardReady(orderReadyDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getOrderId()+"����׼��ʧ�ܣ�");
			throw new Exception(e);
		}
		// ����׼�����ύ����
		try {
			submitOrderService.submitOrderAtReady(sellOrderDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getOrderId()+"����׼���ύʧ�ܣ�");
			throw new Exception(e);
//			throw new BizServiceException("�ۿ�������" + sellOrderDTO.getOrderId()
//					+ ",����׼��ʧ�ܣ������жϣ�");
		}
		

		// ��������ȷ��
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
		sellOrderDto.setCardNo(cardNo);
		try{
			submitOrderService.submitOrderAtHandOut(sellOrderDto);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getOrderId()+"��������ȷ��ʧ�ܣ�");
			throw new Exception(e);
		}
		try {
			sellOrderDto.setBatchNo("");
			sellOrderDto.setInitActStat("0");
			sellOrderDto.setOrderState("32");
			sellOrderDto.setTotalPrice(String.valueOf(totalPrice));
			sellOrderDto.setCvn2(reqData.getCHANNEL());//�����������ݴ��ֶ�
			submitOrderService.submitOrderAtSendConfirm(sellOrderDto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getOrderId()+"��������ȷ��ʧ�ܣ�");
			throw new Exception(e);
//			throw new BizServiceException("�ۿ�������" + sellOrderDto.getOrderId()
//					+ ",��������ȷ��ʧ�ܣ������жϣ�");
		}
		//��������ȷ����󶨿����˵Ĺ�ϵ
		AccCardInfo acc=new AccCardInfo();
		acc.setCardholderId(cardholderId);
		AccCardInfoExample ex =new AccCardInfoExample();
		ex.createCriteria().andCardNoEqualTo(cardNo);
		
		try {
			accCardInfoMapper.updateCardHolderByExample(acc, ex);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("["+reqData.getRESPSEQNO()+"]"+"�ֿ��ˣ�"+cardholderId+"�󶨿���Ϣ��ʧ�ܣ�");
			throw new Exception(e);
		}

		// ����ȷ��
		SellOrderPaymentDTO sellOrderPaymentDTO = new SellOrderPaymentDTO();
		sellOrderPaymentDTO.setDefaultEntityId(defaultEntityId);
		sellOrderPaymentDTO.setLoginUserId(sellOrderDto.getLoginUserId());
		sellOrderPaymentDTO.setOrderId(sellOrderDto.getOrderId());
		sellOrderPaymentDTO.setPaymentAmount(sellOrderDto.getTotalPrice());
		sellOrderPaymentDTO.setPaymentType("1");
		// ��Ӷ������ʽ
		try {
			sellOrderPaymentService.insertOrderPayment(sellOrderPaymentDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getOrderId()+"��Ӷ������ʽʧ�ܣ�");
			throw new Exception(e);
//			throw new BizServiceException("�ۿ�������" + sellOrderDto.getOrderId()
//					+ ",��Ӷ������ʽʧ�ܣ������жϣ�");
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
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getOrderId()+"��������ȷ���ύʧ�ܣ�");
			throw new Exception(e);
//			throw new BizServiceException("�ۿ�������" + dto.getOrderId()
//					+ ",��������ȷ���ύʧ�ܣ������жϣ�");
		}

		dto.setInitActStat("0");
		// �������
		try {
			submitOrderService.submitOrderForPayment(dto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getOrderId()+"�����������ʧ�ܣ�");
			throw new Exception(e);
//			throw new BizServiceException("�ۿ�������" + dto.getOrderId()
//					+ ",�����������ʧ�ܣ������жϣ�");
		}
		
		
		resp.setRESP_CODE("0000");
		return resp;
		
	}
	
	
	
	
		
	

}
