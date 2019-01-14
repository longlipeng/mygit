package com.allinfinance.prepay.batch.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.allinfinance.prepay.mapper.svc_mng.EntityStockMapper;
import com.allinfinance.prepay.mapper.svc_mng.ProductMapper;
import com.allinfinance.prepay.mapper.svc_mng.SaicOnlineBatchInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellContractMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellProdContractMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellerMapper;
import com.allinfinance.prepay.mapper.svc_mng.TbOrderResourceMapper;
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
import com.allinfinance.prepay.model.InvoiceCompany;
import com.allinfinance.prepay.model.LeaguerInfo;
import com.allinfinance.prepay.model.Product;
import com.allinfinance.prepay.model.ProductExample;
import com.allinfinance.prepay.model.SaicOnlineBatchInfo;
import com.allinfinance.prepay.model.SaicOnlineBatchInfoExample;
import com.allinfinance.prepay.model.SellContract;
import com.allinfinance.prepay.model.SellContractExample;
import com.allinfinance.prepay.model.SellProdContractExample;
import com.allinfinance.prepay.model.TbOrderResource;
import com.allinfinance.prepay.service.CardholderService;
import com.allinfinance.prepay.service.CustomerService;
import com.allinfinance.prepay.service.DeliveryContactService;
import com.allinfinance.prepay.service.EntityContactService;
import com.allinfinance.prepay.service.EntityDeliveryService;
import com.allinfinance.prepay.service.EntityStockService;
import com.allinfinance.prepay.service.InvoiceAddressService;
import com.allinfinance.prepay.service.InvoiceCompanyService;
import com.allinfinance.prepay.service.MngAccountInfoService;
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

@Service
public class BacthIssueCardServiceImpl implements BacthIssueCardService {
	private static Logger logger = Logger
			.getLogger(BacthIssueCardServiceImpl.class);
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerMapper CustomerMapper;
	@Autowired
	private EntityDeliveryMapper entityDeliveryMapper;
	@Autowired
	private EntityContactMapper entityContactMapper;
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
	private MngAccountInfoService mngAccountInfoService;
	@Autowired
	private LeaguerInfoDAO leaguerInfoDAO;
	@Autowired
	private AttachInfoDAO attachInfoDAO;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private SaicOnlineBatchInfoMapper saicOnlineBatchInfoMapper;
	@Autowired
	private EntityDeliveryService deliveryPointService;
	@Autowired
	private DeliveryContactService deliveryContactService;
	@Autowired
	TbOrderResourceMapper tbOrderResourceMapper;
	@Override
	public void issueCard(SaicOnlineBatchInfo info) throws Exception {
		// TODO Auto-generated method stub
		ProductExample productExample=new ProductExample();
		productExample.createCriteria().andProductIdEqualTo(info.getProductId())
		.andDataStateEqualTo("1").andEntityIdEqualTo(info.getEntityId());
		List<Product> products = productMapper.selectByExample(productExample);
		if(products==null||products.size()==0){
			logger.error("卡产品ID错！");
			throw new BizServiceException("卡产品ID错！");
		}
		
		if(products.get(0).getOnymousStat().endsWith("2")){
			logger.error("该接口不能销售不记名卡产品！");
			throw new BizServiceException("卡产品ID错！");
		}
		if(products.get(0).getOnymousStat().endsWith("1")){
			logger.error("该接口不能销售个性化卡产品！");
			throw new BizServiceException("卡产品ID错！");
		}
		if(products.get(0).getOnymousStat().endsWith("3")&&!products.get(0).getCardType().endsWith("3")){
			//库存查询  记名卡库存面额非固定  面额值0
			OrderReadyDTO readyDTO=new OrderReadyDTO();
			readyDTO.setProductId(info.getProductId());
			readyDTO.setFaceValue("0");
			readyDTO.setFaceValueType("1");
			readyDTO.setDefaultEntityId(info.getEntityId());
			List<HashMap<String, Object>> cardNum=entityStockMapper.selectStockByProductId(readyDTO);
			if(cardNum==null||cardNum.size()==0){
				logger.error("卡产品库存不足！");
				throw new BizServiceException("卡产品库存不足！");
			}
			int num=(Integer) cardNum.get(0).get("stockNumber");
			if(num==0){
				logger.error("卡产品库存不足!");
				throw new BizServiceException("卡产品库存不足！");
			}
		}

		CustomerDTO customerDTO = new CustomerDTO();
		CardholderDTO cardholderDTO = new CardholderDTO();
		if (info.getIdType().trim().equals("1")) {
			customerDTO.setCorpCredType("1");
			cardholderDTO.setIdType("1");
		} else if (info.getIdType().trim().equals("2")) {
			customerDTO.setCorpCredType("3");
			cardholderDTO.setIdType("2");
		} else if (info.getIdType().trim().equals("3")) {
			customerDTO.setCorpCredType("6");
			cardholderDTO.setIdType("3");
		} else {
			// 没有这个证件类型
			// resp.setRESP_TEXT("证件类型有误！");
			logger.error("证件类型有误！");
			throw new BizServiceException("证件类型有误！");
		}

		/* 证件号 */
		if (info.getIdType().trim().trim().equals("1")) {
			// 身份证
			customerDTO.setCorpCredId(info.getIdNo().toUpperCase());
			cardholderDTO.setIdNo(info.getIdNo().toUpperCase());
		} else if (info.getIdType().trim().equals("2")) {
			// 护照

			customerDTO.setCorpCredId(info.getIdNo().trim());
			cardholderDTO.setIdNo(info.getIdNo().trim());
		} else if (info.getIdType().trim().equals("3")) {
			// 其他
			customerDTO.setCorpCredId(info.getIdNo().trim());
			cardholderDTO.setIdNo(info.getIdNo().trim());
		}
		// 性别
		cardholderDTO.setCardholderGender(info.getGender());
		/* 称谓 */
		if (cardholderDTO.getCardholderGender().trim().equals("1")) {
			cardholderDTO.setCardholderSalutation("1");
		} else if (cardholderDTO.getCardholderGender().trim().equals("2")) {
			cardholderDTO.setCardholderSalutation("3");// 女士
		}

		cardholderDTO.setCardholderBirthday(info.getCardholderBirthday());
		cardholderDTO.setCardholderEmail(info.getCardholderEmail());
		/* 客户证件失效日期 */
		customerDTO.setCorpCredEndValidity(info.getValidity());
		customerDTO.setCustomerAddress(info.getDeliveryAddress());
		cardholderDTO.setMailingAddress(info.getDeliveryAddress());
		// 国籍
		customerDTO.setNationality(info.getCountry());
		String defaultEntityId = info.getEntityId();
		cardholderDTO.setCardholderBirthday(info.getCardholderBirthday());
		cardholderDTO.setDefaultEntityId(defaultEntityId);
		customerDTO.setDefaultEntityId(defaultEntityId);
		customerDTO.setFatherEntityId(defaultEntityId);
		customerDTO.setSalesmanId(Config.getUserId());
		customerDTO.setFatherEntityId(defaultEntityId);
		customerDTO.setCreateUser(Config.getUserId());
		customerDTO.setLoginUserId(Config.getUserId());
		customerDTO.setCreateTime(DateUtil.getCurrentTime());
		customerDTO.setModifyTime(DateUtil.getCurrentTime());
		customerDTO.setModifyUser(Config.getUserId());
		cardholderDTO.setLoginUserId(Config.getUserId());
		customerDTO.setCustomerName(info.getLeaguerName());
		cardholderDTO.setFirstName(info.getLeaguerName());
		customerDTO.setCustomerTelephone(info.getPhoneNumber());
		cardholderDTO.setCardholderMobile(info.getPhoneNumber());
		cardholderDTO.setCardholderBirthday(info.getCardholderBirthday());
		/* 持卡人分类 */
		cardholderDTO.setCardholderSegment("0");
		/* 渠道 */
		customerDTO.setChannel("3");// 3门店
		/* 客户的其他信息都填写为默认 */
		/* 客户行业类别 */
		customerDTO.setActivitySector("2");
		/* 客户职业类别 */
		customerDTO.setAwareness(info.getAwareness());
		customerDTO.setCorpActionAtLaw("2");
		customerDTO.setCreditStatus("0");
		customerDTO.setCusState("1");// 1.已审核 4未审核
		customerDTO.setCustomerType("1");
		customerDTO.setHasDm("1");
		customerDTO.setPaymentTerm("2");
		customerDTO.setPictureSave("2");
		customerDTO.setPunishRecordFlag("2");
		customerDTO.setSalesRegionId("1");
		/* 持卡人信息默认字段 */
		cardholderDTO.setCardholderFunction("1");
		cardholderDTO.setCardholderState("1");

		// 客户受理区域
		customerDTO.setAcceptArea(info.getCustArea());
		cardholderDTO.setCardholderArea(info.getCustArea());
		CustomerDTO customerByIdNo = customerService
				.getCustomerByIdNo(customerDTO);
		customerDTO.setFatherEntityId(defaultEntityId);
		CardHolder cardHolder = new CardHolder();
		try {
			if (customerByIdNo != null) {

				String modifyTime = DateUtil.getCurrentTime();
				// 更新客户信息

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
				logger.debug("修改客户信息：");
				CustomerMapper.updateByExampleSelective(customer, CustomerEx);

				// 更新快递点信息
				EntityDelivery entityDelivery = new EntityDelivery();
				entityDelivery.setDeliveryAddress(customerDTO
						.getCustomerAddress());
				entityDelivery.setDeliveryName(customerDTO.getCustomerName());
				entityDelivery.setDeliveryPostcode(customerDTO
						.getCustomerPostcode());
				entityDelivery.setModifyTime(modifyTime);
				entityDelivery.setModifyUser(customerDTO.getModifyUser());
				EntityDeliveryExample entityDeliveryExample = new EntityDeliveryExample();
				entityDeliveryExample.createCriteria()
						.andEntityIdEqualTo(customerByIdNo.getEntityId())
						.andDataStateEqualTo("1").andDefaultFlagEqualTo("1");
				logger.debug("更新快递点信息：");
				List<EntityDelivery> entityDeliveryList = entityDeliveryMapper
						.selectByExample(entityDeliveryExample);
				if (entityDeliveryList.size() > 0) {
					entityDeliveryMapper.updateByExampleSelective(
							entityDelivery, entityDeliveryExample);
					entityDelivery.setDeliveryId(entityDeliveryList.get(0)
							.getDeliveryId());
					entityDelivery.setDeliveryName(entityDeliveryList.get(0)
							.getDeliveryName());
				} else {
					entityDelivery.setEntityId(customerDTO.getEntityId());
					entityDelivery.setDeliveryName(customer.getCustomerName());
					entityDelivery.setDeliveryPostcode(customer
							.getCustomerPostcode());
					entityDelivery.setDeliveryAddress(customer
							.getCustomerAddress());
					entityDelivery.setDefaultFlag("1");
					entityDelivery.setCreateUser(Config.getUserId());
					DeliveryPointDTO dto = new DeliveryPointDTO();
					ReflectionUtil.copyProperties(entityDelivery, dto);
					dto = deliveryPointService.insert(dto);
					ReflectionUtil.copyProperties(dto, entityDelivery);
				}

				// 更新默认快递点联系人信息
				DeliveryContactExample deliveryContactExample = new DeliveryContactExample();
				deliveryContactExample
						.createCriteria()
						.andDefaultFlagEqualTo("1")
						.andDataStateEqualTo("1")
						.andDeliveryPointIdEqualTo(
								entityDelivery.getDeliveryId());
				List<DeliveryContact> deliveryContactList = deliveryContactMapper
						.selectByExample(deliveryContactExample);

				if (deliveryContactList.size() > 0) {
					DeliveryContact deliveryContact = new DeliveryContact();
					deliveryContact.setContactPhone(customerDTO
							.getCustomerTelephone());
					deliveryContact.setModifyTime(modifyTime);
					deliveryContact.setModifyUser(customerDTO.getModifyUser());
					deliveryContact.setDeliveryContact(customerDTO
							.getCustomerName());
					logger.debug("更新默认快递点联系人信息：");
					deliveryContactMapper.updateByExampleSelective(
							deliveryContact, deliveryContactExample);
				} else {
					DeliveryRecipientDTO deliveryContact = new DeliveryRecipientDTO();
					deliveryContact.setDeliveryPointId(entityDelivery
							.getDeliveryId());
					deliveryContact.setDeliveryContact(entityDelivery
							.getDeliveryName());
					deliveryContact.setContactPhone(customer
							.getCustomerTelephone());
					deliveryContact.setCreateUser(Config.getUserId());
					deliveryContact.setModifyUser(Config.getUserId());
					deliveryContact.setDefaultFlag("1");
					deliveryContactService.insert(deliveryContact);
				}

				// 更新默认联系人信息
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
						logger.debug("更新默认联系人信息：");
						contactService.updateContact(contactDTO);
					}
				}

				// 更新默认发票地址信息
				List<InvoiceAddressDTO> invoiceAddressDTOs = invoiceAddressService
						.inquery(customerByIdNo.getEntityId());
				if (invoiceAddressDTOs != null) {

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
							invoiceAddress.setModifyUser(customerDTO
									.getModifyUser());
							invoiceAddress.setModifyTime(modifyTime);
							invoiceAddress.setInvoiceRecipient(customer
									.getCustomerName());
							logger.debug(" 更新默认发票地址信息");
							invoiceAddressService
									.updateByPrimaryKey(invoiceAddress);
						}
					}
				}

				// 更新默认发票公司信息
				List<InvoiceCompany> invoiceCompanys = invoiceCompanyService
						.selectByEntityId(customerByIdNo.getEntityId());
				if (invoiceCompanys != null) {
					for (InvoiceCompany invoiceCompany : invoiceCompanys) {
						if (invoiceCompany.getDataState().trim().equals("1")
								&& invoiceCompany.getDefaultFlag().trim()
										.equals("1")) {
							invoiceCompany.setInvoiceCompanyName(customerDTO
									.getCustomerName());
							invoiceCompany.setModifyTime(modifyTime);
							invoiceCompany.setModifyUser(customerDTO
									.getModifyUser());
							logger.debug("更新默认发票公司信息");
							invoiceCompanyService
									.updateByPrimaryKey(invoiceCompany);
						}
					}
				}

			} else {
				logger.debug("新增客户：");
				CustomerDTO insertCustomer = customerService
						.insertCustomer(customerDTO);
				cardholderDTO.setEntityId(insertCustomer.getEntityId());
				customerDTO.setEntityId(insertCustomer.getEntityId());
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new Exception(e);
		}
		try {
			// 客户受理区域
			LeaguerInfo custLeaguerInfo = new LeaguerInfo();
			custLeaguerInfo.setCusNo(customerDTO.getEntityId());
			// 00 代表客户 01代表持卡人
			custLeaguerInfo.setCusType("00");
			custLeaguerInfo.setCusArea(customerDTO.getAcceptArea());
			custLeaguerInfo.setLeaguerId(info.getLeaguerId());

			LeaguerInfo leinfo = leaguerInfoDAO
					.selectLeaguerInfo(custLeaguerInfo);
			if (leinfo != null) {
				leaguerInfoDAO.updateLeaguerInfo(custLeaguerInfo);
			} else {
				leaguerInfoDAO.insertLeaguerInfo(custLeaguerInfo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new Exception(e);
		}

		try {
			int attachCount = attachInfoDAO.selectAttachInfoDTO(customerDTO,
					"00", customerDTO.getEntityId());
			if (attachCount > 0) {

				attachInfoDAO.updateAttachInfoDTO(customerDTO, "00",
						cardholderDTO.getCardholderGender(),
						customerDTO.getEntityId());
			} else {
				attachInfoDAO.insertAttachInfoDTO(customerDTO, "00",
						cardholderDTO.getCardholderGender(),
						customerDTO.getEntityId());
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new Exception(e);
		}
		/* 查询看持卡人是否存在，如果存在则更新为最新的信息，不存在则插入新信息 */
		CardHolder cardholder = new CardHolder();
		ReflectionUtil.copyProperties(cardholderDTO, cardholder);
		cardholder.setModifyTime(DateUtil.getCurrentTime());
		cardholder.setModifyUser(customerDTO.getModifyUser());
		CardHolderExample cardholderExample = new CardHolderExample();
		cardholderExample.createCriteria()
				.andIdTypeEqualTo(cardholderDTO.getIdType())
				.andIdNoEqualTo(cardholderDTO.getIdNo())
				.andDataStateEqualTo("1");
		List<CardHolder> cardholderLists = cardHolderMapper
				.selectByExample(cardholderExample);
		try {
			if (cardholderLists.size() > 0) {
				cardholder.setEntityId(customerByIdNo.getEntityId());
				logger.debug("修改持卡人信息");
				cardHolderMapper.updateByExampleSelective(cardholder,
						cardholderExample);
				ReflectionUtil.copyProperties(cardholderLists.get(0),
						cardHolder);
				// 开户，绑定关系   --此版本废弃
//				mngAccountInfoService.insertAccInfoAndBinding(
//						cardHolder.getCardholderId(), "1");
			} else {
				logger.debug("增加持卡人信息");
				cardHolder = cardHolderService.insert(cardholderDTO);
				// 开户，绑定关系 --此版本废弃
//				mngAccountInfoService.insertAccInfoAndBinding(
//						cardHolder.getCardholderId(), "1");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new Exception(e);
		}
		try {
			// 持卡人受理区域
			LeaguerInfo leaguerInfo = new LeaguerInfo();
			leaguerInfo.setCusNo(cardHolder.getCardholderId());
			// 00 代表客户 01代表持卡人
			leaguerInfo.setCusType("01");
			leaguerInfo.setCusArea(cardholderDTO.getCardholderArea());
			leaguerInfo.setLeaguerId(info.getLeaguerId());
			LeaguerInfo cardholderinfo = leaguerInfoDAO
					.selectLeaguerInfo(leaguerInfo);
			if (cardholderinfo != null) {
				leaguerInfoDAO.updateLeaguerInfo(leaguerInfo);
			} else {
				leaguerInfoDAO.insertLeaguerInfo(leaguerInfo);
			}

			int attachCounts = attachInfoDAO.selectAttachInfoDTO(customerDTO,
					"01", cardHolder.getCardholderId());
			if (attachCounts > 0) {
				attachInfoDAO.updateAttachInfoDTO(customerDTO, "01",
						cardholderDTO.getCardholderGender(),
						cardHolder.getCardholderId());
			} else {
				attachInfoDAO.insertAttachInfoDTO(customerDTO, "01",
						cardholderDTO.getCardholderGender(),
						cardHolder.getCardholderId());
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new Exception(e);
		}
		
		 //如果是电子卡产品，则完成售卡 
		if(products.get(0).getCardType().equals("3")){
			logger.info("记名电子卡销售完成");
			return; 
		}
		

		CustomerDTO customer = customerService.getCustomerByIdNo(customerDTO);
		SellerContractDTO sellerContractDTO = new SellerContractDTO();
		sellerContractDTO.setContractBuyer(customer.getEntityId());
		sellerContractDTO.setContractSellerName(customer.getCustomerName());
		sellerContractDTO.setLoginUserId(Config.getUserId());
		sellerContractDTO.setContractSeller(defaultEntityId);// 报文里的机构号
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
				logger.error("客户：" + customerDTO.getCustomerName()
						+ "添加客户合同失败！");
				throw new Exception(e);
			}

		}
		if (contractDTO.getSellContractId() == null) {
			contractDTO.setSellContractId(sellContracts.get(0)
					.getSellContractId());
		}

		// 添加产品
		SellerProductContractDTO sellerProductContractDTO = new SellerProductContractDTO();
		sellerProductContractDTO.setProductId(info.getProductId());
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
		// 添加合同时查看产品
		productDTO = productService.viewProductForContract(productDTO);
		List<ServiceDTO> productServices = productDTO.getServices();
		// 添加合同服务明细
		if (null != productServices && productServices.size() > 0) {
			List<SellerAcctypeContractDTO> accDTOs = new ArrayList<SellerAcctypeContractDTO>();
			for (int i = 0; i < productServices.size(); i++) {
				SellerAcctypeContractDTO accDTO = new SellerAcctypeContractDTO();
				accDTO.setSellContractId(sellerContractDTO.getSellContractId());
				accDTO.setProductId(sellerProductContractDTO.getProductId());
				accDTO.setAcctypeId(productServices.get(i).getServiceId());
				// 交易类型暂定为0000
				accDTO.setTxnNum("0000");
				accDTO.setFee(productServices.get(i).getDefaultRate());
				accDTOs.add(accDTO);
			}
			sellerProductContractDTO.setAccDTOs(accDTOs);
		}
		sellerContractDTO.setDeliveryFee("0");
		sellerContractDTO.setContractState("1");
		sellerContractDTO.setContractType("34");
		// 判断产品在合同中是否已存在
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
				logger.error("客户：" + customerDTO.getCustomerName() + "添加产品失败！");
				throw new Exception(e);
			}
		}
		String packageFee = productDTO.getPackages().get(0).getPackageFee();
		// 销售记名卡订单
		SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();
		SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
		sellOrderDTO.setFaceValue("0");// 第一次充值金额
		sellOrderDTO.setCardLayoutId(productDTO.getCardLayoutDTOs().get(0)
				.getCardLayoutId());// 卡面
		sellOrderDTO.setPackageId(productDTO.getPackages().get(0)
				.getPackageId());// 包装
		sellOrderDTO.setAdditionalFee("0");// 默认附加费为0
		sellOrderDTO.setCardQuantity("0");// 订单张数
		sellOrderDTO.setDeliveryFee("0");// 送货费用
		sellOrderDTO.setDeliveryMeans("2");// 送货方式
		sellOrderDTO.setDiscountFee("0");// 折扣费
		sellOrderDTO.setFirstEntityId(customer.getEntityId());// 客户号
		sellOrderDTO.setProcessEntityId(sellerContractDTO.getDefaultEntityId());// 营销机构
		sellOrderDTO.setOrderType("10000011");// 订单类型
		sellOrderDTO.setSaleMan(sellerContractDTO.getLoginUserId());// 销售人员
		sellOrderDTO.setPayChannel("1");// 支付渠道
		sellOrderDTO.setPackageFee(packageFee.trim());// 包装费
		sellOrderDTO.setProductId(sellerProductContractDTO.getProductId());// 产品ID
		sellOrderDTO.setOrderSource("1");// 订单来源
		sellOrderDTO.setOrderState("1");// 订单状态
		sellOrderDTO.setPaymentDelay("0");// 付款延期天数
		sellOrderDTO.setPaymentState("0");// 订单状态为未支付
		sellOrderDTO.setPaymentTerm("2");// 支付节点
		sellOrderDTO.setPerFlag("per");
		sellOrderDTO.setServiceId(productDTO.getServices().get(0)
				.getServiceId());// 充值账户
		sellOrderDTO.setServiceName(productDTO.getServices().get(0)
				.getServiceName());// 充值账户名称
		sellOrderDTO.setCardIssueFee("0");// 卡费
		sellOrderDTO.setAnnualFee("0");// 年费
		sellOrderDTO.setModifyUser(sellerContractDTO.getLoginUserId());// 修改订单用户
		sellOrderDTO.setCreateUser(sellerContractDTO.getLoginUserId());// 创建订单用户
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
			// 快递地址
			List<EntityDelivery> entityDeliveryList = entityDeliveryMapper
					.selectByExample(entityDeliveryExample);

			DeliveryContactExample deliveryContactExample = new DeliveryContactExample();
			deliveryContactExample
					.createCriteria()
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
					.andDefaultFlagEqualTo("1")
					.andDeliveryPointIdEqualTo(
							entityDeliveryList.get(0).getDeliveryId());
			deliveryContactExample.setOrderByClause("default_flag desc");
			List<DeliveryContact> deliveryContactList = deliveryContactMapper
					.selectByExample(deliveryContactExample);
			sellOrderDTO.setDeliveryPoint(entityDeliveryList.get(0)
					.getDeliveryId());

			// 收件人
			sellOrderDTO.setOrderContact(deliveryContactList.get(0)
					.getDeliveryContactId());
			sellOrderInputDTO = orderService.orderInsert(sellOrderDTO);
			TbOrderResource tbOrderResource=new TbOrderResource();
			tbOrderResource.setOrderId(sellOrderDTO.getOrderId());
			tbOrderResource.setResource("ZT");//订单来源是中台
			tbOrderResourceMapper.insertSelective(tbOrderResource);//订单来源表
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("新增订单失败");
			throw new Exception(e);
		}

		// 添加持卡人
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
			logger.error("订单："
					+ sellOrderInputDTO.getSellOrderDTO().getOrderId()
					+ "订单添加持卡人失败！");
			throw new Exception(e);
		}

		// 提交订单
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
			logger.error("订单：" + sellOrderInputDTO.getOrderId() + "订单提交失败！");
			throw new Exception(e);
		}
		// 订单审核
		SellOrderDTO sellOrder = sellOrderInputDTO.getSellOrderDTO();
		sellOrder.setLoginUserId(sellOrderInputDTO.getLoginUserId());
		try {
			submitOrderService.submitOrderAtConfirm(sellOrder);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("订单：" + sellOrderInputDTO.getOrderId() + "订单审核失败！");
			throw new Exception(e);
		}

		// 订单准备
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

		List<HashMap<String, String>> entityStockList = entityStockMapper
				.selectCardNoforOrderReadySign(orderReadyDTO);

		if (entityStockList == null || entityStockList.size() == 0) {
			// updateStockStat(cardNo,defaultEntityId);
			logger.error("客户：" + customerDTO.getCustomerName() + "订单准备时，产品："
					+ productDTO.getProductName() + ",库存不足！导入中断！");
			throw new BizServiceException("客户：" + customerDTO.getCustomerName()
					+ "订单准备时，产品：" + productDTO.getProductName() + ",库存不足！导入中断！");
		}
		// 准备的卡号
		String cardNo = entityStockList.get(0).get("cardNo");
		String[] cardNoArray = new String[] { cardNo };
		orderReadyDTO.setCardNoArray(cardNoArray);
		// 订单准备
		try {
			orderReadyService.cardReady(orderReadyDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("订单：" + sellOrderInputDTO.getOrderId() + "订单准备失败！");
			throw new Exception(e);
		}
		// 订单准备，提交订单
		try {
			submitOrderService.submitOrderAtReady(sellOrderDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("订单：" + sellOrderInputDTO.getOrderId() + "订单准备提交失败！");
			throw new Exception(e);
			// throw new BizServiceException("售卡订单：" + sellOrderDTO.getOrderId()
			// + ",订单准备失败！导入中断！");
		}

		// 订单配送确定
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
		try {
			submitOrderService.submitOrderAtHandOut(sellOrderDto);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("订单：" + sellOrderInputDTO.getOrderId() + "订单配送确定失败！");
			throw new Exception(e);
		}
		try {
			sellOrderDto.setBatchNo("");
			sellOrderDto.setInitActStat("0");
			sellOrderDto.setOrderState("32");
			sellOrderDto.setTotalPrice(String.valueOf(totalPrice));
			// sellOrderDto.setCvn2(reqData.getCHANNEL());//报文渠道号暂存字段
			submitOrderService.submitOrderAtSendConfirm(sellOrderDto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("订单：" + sellOrderInputDTO.getOrderId() + "订单配送确定失败！");
			throw new Exception(e);
			// throw new BizServiceException("售卡订单：" + sellOrderDto.getOrderId()
			// + ",订单配送确定失败！导入中断！");
		}

		// 付款确认
		SellOrderPaymentDTO sellOrderPaymentDTO = new SellOrderPaymentDTO();
		sellOrderPaymentDTO.setDefaultEntityId(defaultEntityId);
		sellOrderPaymentDTO.setLoginUserId(sellOrderDto.getLoginUserId());
		sellOrderPaymentDTO.setOrderId(sellOrderDto.getOrderId());
		sellOrderPaymentDTO.setPaymentAmount(sellOrderDto.getTotalPrice());
		sellOrderPaymentDTO.setPaymentType("1");
		// 添加订单付款方式
		try {
			sellOrderPaymentService.insertOrderPayment(sellOrderPaymentDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("订单：" + sellOrderInputDTO.getOrderId() + "添加订单付款方式失败！");
			throw new Exception(e);
			// throw new BizServiceException("售卡订单：" + sellOrderDto.getOrderId()
			// + ",添加订单付款方式失败！导入中断！");
		}

		// 付款确认提交订单
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
			logger.error("订单：" + sellOrderInputDTO.getOrderId() + "订单付款确认提交失败！");
			throw new Exception(e);
			// throw new BizServiceException("售卡订单：" + dto.getOrderId()
			// + ",订单付款确认提交失败！导入中断！");
		}

		dto.setInitActStat("0");
		// 付款审核
		try {
			submitOrderService.submitOrderForPayment(dto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("订单：" + sellOrderInputDTO.getOrderId() + "订单付款审核失败！");
			throw new Exception(e);
			// throw new BizServiceException("售卡订单：" + dto.getOrderId()
			// + ",订单付款审核失败！导入中断！");
		}
		
		//订单配送确定后绑定卡和人的关系   --新增
		AccCardInfo acc=new AccCardInfo();
		acc.setCardholderId(cardholderId);
		AccCardInfoExample ex2 =new AccCardInfoExample();
		ex2.createCriteria().andCardNoEqualTo(cardNo);
		
		try {
			accCardInfoMapper.updateCardHolderByExample(acc, ex2);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(""+"持卡人："+cardholderId+"绑定卡信息表失败！");
			throw new Exception(e);
//			throw new BizServiceException("售卡订单：" + sellOrderDto.getOrderId()
//					+ ",添加订单付款方式失败！导入中断！");
		}
		
		try {
			SaicOnlineBatchInfoExample ex = new SaicOnlineBatchInfoExample();
			ex.createCriteria().andBatchIdEqualTo(info.getBatchId());
			SaicOnlineBatchInfo saicOnlineBatchInfo = new SaicOnlineBatchInfo();
			// 00待处理 01处理中 02处理成功 03 处理失败
			saicOnlineBatchInfo.setBatchStat("02");
			saicOnlineBatchInfo.setUpdateTime(DateUtil.getCurrentTime());
			saicOnlineBatchInfoMapper.updateByExampleSelective(
					saicOnlineBatchInfo, ex);
			logger.info("batchId:"+info.getBatchId()+",处理成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("订单：" + sellOrderInputDTO.getOrderId() + ",batchID="
					+ info.getBatchId() + ",更新批次状态失败！");
			throw new Exception(e);
		}

	}

}
