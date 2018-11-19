package com.allinfinance.prepay.processor.ipos;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.AccCardInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.AttachInfoMapper;
import com.allinfinance.prepay.mapper.svc_mng.CardHolderMapper;
import com.allinfinance.prepay.mapper.svc_mng.CustomerMapper;
import com.allinfinance.prepay.mapper.svc_mng.DeliveryContactMapper;
import com.allinfinance.prepay.mapper.svc_mng.EntityDeliveryMapper;
import com.allinfinance.prepay.mapper.svc_mng.EntityStockMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellContractMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellProdContractMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellerMapper;
import com.allinfinance.prepay.mapper.svc_mng.TermSellOrderMapper;
import com.allinfinance.prepay.message.BasicMessage;
import com.allinfinance.prepay.message.MessageSyncP001Req;
import com.allinfinance.prepay.message.MessageSyncP001Resp;
import com.allinfinance.prepay.message.MessageSyncP011Req;
import com.allinfinance.prepay.message.MessageSyncP011Resp;
import com.allinfinance.prepay.model.AccCardInfo;
import com.allinfinance.prepay.model.AccCardInfoExample;
import com.allinfinance.prepay.model.AttachInfo;
import com.allinfinance.prepay.model.AttachInfoExample;
import com.allinfinance.prepay.model.CardHolder;
import com.allinfinance.prepay.model.CardHolderExample;
import com.allinfinance.prepay.model.CustomerExample;
import com.allinfinance.prepay.model.Customer;
import com.allinfinance.prepay.model.DeliveryContact;
import com.allinfinance.prepay.model.DeliveryContactExample;
import com.allinfinance.prepay.model.EntityDelivery;
import com.allinfinance.prepay.model.EntityDeliveryExample;
import com.allinfinance.prepay.model.EntityStock;
import com.allinfinance.prepay.model.EntityStockExample;
import com.allinfinance.prepay.model.InvoiceCompany;
import com.allinfinance.prepay.model.SellContract;
import com.allinfinance.prepay.model.SellContractExample;
import com.allinfinance.prepay.model.SellProdContractExample;
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
import com.allinfinance.prepay.socket.SendShortMessageThread;
import com.allinfinance.prepay.utils.CharUtil;
import com.allinfinance.prepay.utils.Config;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.IDCardCheck;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.prepay.utils.ResponseCode;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.allinfinance.univer.seller.cardholder.dto.AttachInfoDTO;
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
 * 售卡
 * 
 * @author joesun
 *
 */
@Service
public class SyncP001Processor implements IProcessor {
	private static Logger logger = Logger.getLogger(SyncP001Processor.class);
	// @Autowired
	// private CommonsDAO commonsDAO;
	// @Autowired
	// private CardHolderMapper cardHolderMapper;
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
	private AttachInfoMapper attachInfoMapper;
	
public void insertAttachInfoDTO(CustomerDTO customerDTO,String type,String id) throws BizServiceException{
		
		//增加附加信息
		AttachInfo attachInfo=new AttachInfo();
		attachInfo.setPeopleNo(id);
		attachInfo.setPeopleType(type);//00代表客户，01代表持卡人
		attachInfo.setIndustry(customerDTO.getActivitySector());//行业
		attachInfo.setProfession(customerDTO.getAwareness());//职业
		attachInfo.setValidity(DateUtil.StringDate(customerDTO.getCorpCredEndValidity()));//失效期
		attachInfo.setCountyr(customerDTO.getNationality());//国家
		attachInfo.setCity(customerDTO.getCity());//城市
		attachInfo.setUpdateDate(DateUtil.getCurrentTime());
		attachInfo.setEntityId(customerDTO.getFatherEntityId());//机构号
		attachInfo.setDataStat("1");
		attachInfoMapper.insert(attachInfo);
		
	}
	
	public void updateAttachInfoDTO(CustomerDTO customerDTO,String type,String id) throws BizServiceException{
		//附加信息
		AttachInfo attachInfo=new AttachInfo();
		attachInfo.setPeopleNo(id);
		attachInfo.setPeopleType(type);//00代表客户，01代表持卡人
		attachInfo.setIndustry(customerDTO.getActivitySector());//行业
		attachInfo.setProfession(customerDTO.getAwareness());//职业
		attachInfo.setValidity(DateUtil.StringDate(customerDTO.getCorpCredEndValidity()));//失效期
		attachInfo.setCountyr(customerDTO.getNationality());//国家
		attachInfo.setCity(customerDTO.getCity());//城市
		attachInfo.setUpdateDate(DateUtil.getCurrentTime());
		attachInfo.setEntityId(customerDTO.getFatherEntityId());//机构号
		attachInfo.setDataStat("1");
		AttachInfoExample example=new AttachInfoExample();
		example.createCriteria().andPeopleNoEqualTo(id).andPeopleTypeEqualTo(type)
			.andDataStatEqualTo("1").andEntityIdEqualTo(attachInfo.getEntityId());
		attachInfoMapper.updateByExampleSelective(attachInfo, example);
		
	}
	
	public int selectAttachInfoDTO(CustomerDTO customerDTO,String type,String id) throws BizServiceException{
		
		//附加信息
		AttachInfoExample example=new AttachInfoExample();
		example.createCriteria().andPeopleNoEqualTo(id).andPeopleTypeEqualTo(type)
			.andDataStatEqualTo("1").andEntityIdEqualTo(customerDTO.getFatherEntityId());
		List<AttachInfo> list=attachInfoMapper.selectByExample(example);
		return list.size();
		
	}
	@Override
	public BasicMessage process(BasicMessage req) throws Exception {
		MessageSyncP001Req reqData = (MessageSyncP001Req)req;
		MessageSyncP001Resp resp =(MessageSyncP001Resp) reqData.createResp() ;
		CustomerDTO customerDTO = new CustomerDTO();
		CardholderDTO cardholderDTO = new CardholderDTO();
		if(reqData.getCUSTOMER_TPYE()!=null&&!reqData.getCUSTOMER_TPYE().equals("")){
			if(!reqData.getCUSTOMER_TPYE().equals("0")){
				resp.setRESP_CODE(ResponseCode.ORDER_TYPE_ERROR);
				logger.error("暂不支持企业订单["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
		}
		String channel=null;//渠道号
		String branch_Id=null;//网点
		
		
		if(reqData.getCHANNEL()!=null&&!reqData.getCHANNEL().trim().equals("")){
			channel=reqData.getCHANNEL().trim();
		}else{
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("渠道不能为空！["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		if(reqData.getBRANCH_ID()!=null&&!reqData.getBRANCH_ID().trim().equals("")){
			branch_Id=reqData.getBRANCH_ID().trim();
		}else{
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("网点不能为空！["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		
		
		if(reqData.getISSUER_ID()!=null&&!reqData.getISSUER_ID().trim().equals("")){
			if(checkIssueId(reqData.getISSUER_ID().trim())){
			cardholderDTO.setDefaultEntityId(reqData.getISSUER_ID().trim());
			customerDTO.setFatherEntityId(reqData.getISSUER_ID().trim());
			}else{
				//没有找到机构号
				//resp.setRESP_TEXT("机构号有误！");
				resp.setRESP_CODE(ResponseCode.ISSUEID_ERROR);
				logger.error("机构号有误！["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
		}else{
			//resp.setRESP_TEXT("机构号不能为空！");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("机构号不能为空！["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		if(reqData.getCARD_NO()==null&&reqData.getCARD_NO().trim().equals("")){
			//resp.setRESP_TEXT("卡号不能为空！");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("卡号不能为空！["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		
		
	
		String defaultEntityId=reqData.getISSUER_ID().trim();
		customerDTO.setSalesmanId(Config.getUserId());
		customerDTO.setFatherEntityId(defaultEntityId);
		customerDTO.setDefaultEntityId(defaultEntityId);
		customerDTO.setCreateUser(Config.getUserId());
		customerDTO.setLoginUserId(Config.getUserId());
		customerDTO.setCreateTime(DateUtil.getCurrentTime());
		customerDTO.setModifyTime(DateUtil.getCurrentTime());
		customerDTO.setModifyUser(Config.getUserId());
		cardholderDTO.setLoginUserId(Config.getUserId());
		/*姓名*/
		if(reqData.getFIRST_NAME()!=null&&!reqData.getFIRST_NAME().trim().equals("")){
			if(CharUtil.isChinese(reqData.getFIRST_NAME().trim())){
				customerDTO.setCustomerName(reqData.getFIRST_NAME().trim());
				cardholderDTO.setFirstName(reqData.getFIRST_NAME().trim());
			}else{
				//包含非法字符
				resp.setRESP_CODE(ResponseCode.ILLEGAL_CHARACTER);
				logger.error("姓名包含非法字符！["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			
		}else{
			//resp.setRESP_TEXT("持卡人姓名不能为空！");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("持卡人姓名不能为空！["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		
		
		
		/*电话*/
		if(reqData.getCARDHOLDER_MOBILE()!=null&&!reqData.getCARDHOLDER_MOBILE().trim().equals("")){
			boolean isTrue=reqData.getCARDHOLDER_MOBILE().trim().matches("([1][3,4,5,8,7][0-9]{9})");
			if(isTrue){
				customerDTO.setCustomerTelephone(reqData.getCARDHOLDER_MOBILE().trim());
				cardholderDTO.setCardholderMobile(reqData.getCARDHOLDER_MOBILE().trim());
			}else{
				//手机号码格式不正确
				resp.setRESP_CODE(ResponseCode.MOBILE_ERROR);
				logger.error("手机号码格式不正确！["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			
		}else{
			//resp.setRESP_TEXT("手机号码不能为空！");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("手机号码不能为空！["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		/*证件类型*/
		if(reqData.getID_TYPE()!=null&&!reqData.getID_TYPE().trim().equals("")){
			if(reqData.getID_TYPE().trim().equals("1")){
				customerDTO.setCorpCredType("1");
				cardholderDTO.setIdType("1");
			}else if(reqData.getID_TYPE().trim().equals("2")){
				customerDTO.setCorpCredType("3");
				cardholderDTO.setIdType("2");
			}else if(reqData.getID_TYPE().trim().equals("3")){
				customerDTO.setCorpCredType("6");
				cardholderDTO.setIdType("3");
			}else{
				//没有这个证件类型
				resp.setRESP_CODE(ResponseCode.ID_TYPE_ERROR);
				//resp.setRESP_TEXT("证件类型有误！");
				logger.error("证件类型有误！["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
		}else{
			//resp.setRESP_TEXT("证件类型不能为空！");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("证件类型不能为空！["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		
		/*证件号*/
		if(reqData.getID_NO()!=null&&!reqData.getID_NO().trim().equals("")){
			if(reqData.getID_TYPE().trim().equals("1")){
				//身份证
				String errMsg=IDCardCheck.IDCardValidate(reqData.getID_NO().toUpperCase());
				if(errMsg==null||errMsg.equals("")){
					customerDTO.setCorpCredId(reqData.getID_NO().toUpperCase());
					cardholderDTO.setIdNo(reqData.getID_NO().toUpperCase());
				}else{
					//resp.setRESP_TEXT("证件号有误！");
					resp.setRESP_CODE(ResponseCode.ID_NO_ERROR);
					logger.error("证件号有误（身份证）！["+reqData.getRESPSEQNO()+"]");
					return resp;
				}
				
			}else if(reqData.getID_TYPE().equals("2")){
				//护照
				boolean isTrue = reqData.getID_NO().trim().matches("([0-9a-zA-Z]*)");
	    		 if(isTrue==true){
	    			 customerDTO.setCorpCredId(reqData.getID_NO().trim());
	    			 cardholderDTO.setIdNo(reqData.getID_NO().trim());
	    		 }else{
	    			 //resp.setRESP_TEXT("证件号有误！");
	    			 resp.setRESP_CODE(ResponseCode.ID_NO_ERROR);
	    			 logger.error("证件号有误（护照）！["+reqData.getRESPSEQNO()+"]");
	    			 return resp;
	    		 }
			}else if(reqData.getID_TYPE().trim().equals("3")){
				//其他
				customerDTO.setCorpCredId(reqData.getID_NO().trim());
   			 	cardholderDTO.setIdNo(reqData.getID_NO().trim());
			}
		}else{
			//resp.setRESP_TEXT("证件号不能为空！");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("证件号不能为空！["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		/*性别*/
		if(reqData.getCARDHOLDER_GENDER()!=null&&!reqData.getCARDHOLDER_GENDER().trim().equals("")){
			if(reqData.getCARDHOLDER_GENDER().trim().endsWith("1")||reqData.getCARDHOLDER_GENDER().trim().endsWith("2")){
				cardholderDTO.setCardholderGender(reqData.getCARDHOLDER_GENDER().trim());
			}else{
				resp.setRESP_CODE(ResponseCode.GENDER_ERROR);
				logger.error("性别有误！["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
		}else{
			//如果性别为空，判断证件类型
			if(reqData.getID_TYPE().trim().equals("1")){
				//18位身份证
				if(reqData.getID_NO().trim().length()==18){
					//截取身份证的第17位代表性别，奇数为男，偶数为女
					int num=Integer.parseInt(reqData.getID_NO().trim().substring(16,17));
					if(num%2==0){
						//偶数
						cardholderDTO.setCardholderGender("2");
					}else{
						cardholderDTO.setCardholderGender("1");
					}
				}
				//15位身份证
				if(reqData.getID_NO().length()==15){
					//截取身份证的第15位代表性别，奇数为男，偶数为女
					int num=Integer.parseInt(reqData.getID_NO().trim().substring(14,15));
					if(num%2==0){
						//偶数
						cardholderDTO.setCardholderGender("2");
					}else{
						cardholderDTO.setCardholderGender("1");
					}
				}
				
			}else{
				//返回错误，填写性别
				//resp.setRESP_TEXT("请填写性别！");
				resp.setRESP_CODE(ResponseCode.ISNULL);
				logger.error("请填写性别！["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
		}
		/*客户证件失效日期*/
		if(isValidDate(reqData.getVALIDITY())==false){
			resp.setRESP_CODE(ResponseCode.VALIDITY_ERROR);
			logger.error("日期格式错！["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		customerDTO.setCorpCredEndValidity(DateUtil.formatStringDate(reqData.getVALIDITY()));
		
		EntityStockExample stockEX=new EntityStockExample();
		stockEX.createCriteria().andCardNoEqualTo(reqData.getCARD_NO())
		.andStockStateEqualTo("1").andDataStateEqualTo("1")
		.andFunctionRoleIdEqualTo("3").andEntityIdEqualTo(reqData.getISSUER_ID());
		logger.debug("查询卡号：["+reqData.getRESPSEQNO()+"]");
		List<EntityStock> stocks = entityStockMapper.selectByExample(stockEX);
		if (stocks.size() == 0) {
			//resp.setRESP_TEXT("营销机构库存中没有这张卡！");
			resp.setRESP_CODE(ResponseCode.CARDNO_ISNULL);
			return resp;
		}
		EntityStock enStock=new EntityStock();
		enStock.setStockState("2");
		logger.debug("修改卡号库存状态：["+reqData.getRESPSEQNO()+"]");
		entityStockMapper.updateByExampleSelective(enStock, stockEX);
		
		/*称谓*/
		if(cardholderDTO.getCardholderGender().trim().equals("1")){
			cardholderDTO.setCardholderSalutation("1");
		}
		else if(cardholderDTO.getCardholderGender().trim().equals("2")){
			cardholderDTO.setCardholderSalutation("3");//女士
		}
		/*地址*/
		customerDTO.setCustomerAddress(reqData.getDELIVERY_ADDRESS().trim());
		cardholderDTO.setMailingAddress(reqData.getDELIVERY_ADDRESS().trim());
//		/*生日*/
//		cardholderDTO.setCardholderBirthday("");
		/*车牌号*/
		cardholderDTO.setPlateNumber(reqData.getPLATE_NUMBER().trim());
		/*车架号*/
		//cardholderDTO.setVId();
		/*驾驶证号*/
		cardholderDTO.setDriverLicence(reqData.getDRIVER_LICENCE().trim());
		/*持卡人分类*/
		cardholderDTO.setCardholderSegment("0");
		
		/*客户职业类别*/
		if(StringUtils.isEmpty(reqData.getAWARENESS())){
			customerDTO.setAwareness("1");
		}else{
			customerDTO.setAwareness(reqData.getAWARENESS());
		}
		
		/*持卡人分类*/
		cardholderDTO.setCardholderSegment("0");
		/*渠道*/
		customerDTO.setChannel("3");//3门店
		/*客户行业*/
		if(StringUtils.isEmpty(reqData.getACTIVITY_SECTOR())){
			customerDTO.setActivitySector("2");
		}else{
			customerDTO.setActivitySector(reqData.getACTIVITY_SECTOR());
		}
		//国籍
		customerDTO.setNationality(reqData.getCOUNTRY());
		//城市
		customerDTO.setCity(reqData.getCITY());
		
		//持卡人备注
		cardholderDTO.setCardholderComment(reqData.getREMARK().trim());
		/*客户的其他信息都填写为默认*/
		//customerDTO.setActivitySector("2");
		//customerDTO.setAwareness("1");
		customerDTO.setCorpActionAtLaw("2");
		customerDTO.setCreditStatus("0");
		customerDTO.setCusState("1");//1.已审核  4未审核
		customerDTO.setCustomerType("1");
		customerDTO.setHasDm("1");
		customerDTO.setPaymentTerm("2");
		customerDTO.setPictureSave("2");
		customerDTO.setPunishRecordFlag("2");
		customerDTO.setSalesRegionId("1");
		/*持卡人信息默认字段*/
		cardholderDTO.setCardholderFunction("1");
		cardholderDTO.setCardholderState("1");
		String cardNo=reqData.getCARD_NO();// 报文里的卡号
		logger.debug("查询客户信息：["+reqData.getRESPSEQNO()+"]");
		CustomerDTO customerByIdNo = customerService
				.getCustomerByIdNo(customerDTO);
		customerDTO.setFatherEntityId(reqData.getISSUER_ID());
		CardHolder cardHolder = new CardHolder();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		try {
			if (customerByIdNo != null) {

				String modifyTime = format.format(new Date());
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
				logger.debug("修改客户信息：["+reqData.getRESPSEQNO()+"]");
				CustomerMapper.updateByExampleSelective(customer, CustomerEx);

				// 更新快递点信息
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
				logger.debug("更新快递点信息：["+reqData.getRESPSEQNO()+"]");
				entityDeliveryMapper.updateByExampleSelective(entityDelivery,
						entityDeliveryExample);
				// 更新默认快递点联系人信息
				List<EntityDelivery> example = entityDeliveryMapper
						.selectByExample(entityDeliveryExample);
				if (example != null && example.size() > 0) {
					DeliveryContactExample deliveryContactExample = new DeliveryContactExample();
					deliveryContactExample
							.createCriteria()
							.andDefaultFlagEqualTo("1")
							.andDataStateEqualTo("1")
							.andDeliveryPointIdEqualTo(
									example.get(0).getDeliveryId());
					DeliveryContact deliveryContact = new DeliveryContact();
					deliveryContact.setContactPhone(customerDTO
							.getCustomerTelephone());
					deliveryContact.setModifyTime(modifyTime);
					deliveryContact.setModifyUser(customerDTO.getModifyUser());
					deliveryContact.setDeliveryContact(customerDTO
							.getCustomerName());
					logger.debug("更新默认快递点联系人信息：["+reqData.getRESPSEQNO()+"]");
					deliveryContactMapper.updateByExampleSelective(deliveryContact,
							deliveryContactExample);
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
						logger.debug("更新默认联系人信息：["+reqData.getRESPSEQNO()+"]");
						contactService.updateContact(contactDTO);
					}
				}

				// 更新默认发票地址信息
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
							logger.debug(" 更新默认发票地址信息["+reqData.getRESPSEQNO()+"]");
							invoiceAddressService.updateByPrimaryKey(invoiceAddress);
						}
					}
				}

				// 更新默认发票公司信息
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
							logger.debug("更新默认发票公司信息：["+reqData.getRESPSEQNO()+"]");
							invoiceCompanyService.updateByPrimaryKey(invoiceCompany);
						}
					}
				}

			} else {
				logger.debug("新增客户：["+reqData.getRESPSEQNO()+"]");
				CustomerDTO insertCustomer = customerService
						.insertCustomer(customerDTO);
				cardholderDTO.setEntityId(insertCustomer.getEntityId());
				customerDTO.setEntityId(insertCustomer.getEntityId());
			}
		} catch (Exception e) {
			// TODO: handle exception
			updateStockStat(cardNo,defaultEntityId);
			logger.error("["+reqData.getRESPSEQNO()+"]"+e);
			throw new Exception(e);
		}
		
		int attachCount=selectAttachInfoDTO(customerDTO,"00",customerDTO.getEntityId());
		if(attachCount>0){
			updateAttachInfoDTO(customerDTO, "00", customerDTO.getEntityId());
		}else{
			insertAttachInfoDTO(customerDTO, "00", customerDTO.getEntityId());
		}
 	

		/* 查询看持卡人是否存在，如果存在则更新为最新的信息，不存在则插入新信息 */
		String modifyTime = format.format(new Date());
		CardHolder cardholder = new CardHolder();
		ReflectionUtil.copyProperties(cardholderDTO, cardholder);
		cardholder.setModifyTime(modifyTime);
		cardholder.setModifyUser(customerDTO.getModifyUser());
		CardHolderExample cardholderExample = new CardHolderExample();
		cardholderExample.createCriteria()
				.andIdTypeEqualTo(cardholderDTO.getIdType())
				.andIdNoEqualTo(cardholderDTO.getIdNo());
		List<CardHolder> cardholderLists = cardHolderMapper
				.selectByExample(cardholderExample);
		try {
			if (cardholderLists.size() > 0) {
				cardholder.setEntityId(customerByIdNo.getEntityId());
				logger.debug("修改持卡人信息：["+reqData.getRESPSEQNO()+"]");
				cardHolderMapper.updateByExampleSelective(cardholder,
						cardholderExample);
				ReflectionUtil.copyProperties(cardholderLists.get(0), cardHolder);

			} else {
				logger.debug("增加持卡人信息：["+reqData.getRESPSEQNO()+"]");
				cardHolder = cardHolderService.insert(cardholderDTO);
			}
		} catch (Exception e) {
			// TODO: handle exception
			updateStockStat(cardNo,defaultEntityId);
			logger.error("["+reqData.getRESPSEQNO()+"]"+e);
			throw new Exception(e);
		}
		int attachCounts=selectAttachInfoDTO(customerDTO,"01",cardHolder.getCardholderId());
		if(attachCounts>0){
			updateAttachInfoDTO(customerDTO, "01", cardHolder.getCardholderId());
		}else{
			insertAttachInfoDTO(customerDTO, "01", cardHolder.getCardholderId());
		}
		
		CustomerDTO customer = customerService
				.getCustomerByIdNo(customerDTO);
		SellerContractDTO sellerContractDTO = new SellerContractDTO();
		sellerContractDTO.setContractBuyer(customer.getEntityId());
		sellerContractDTO.setContractSellerName(customer
				.getCustomerName());
		sellerContractDTO.setLoginUserId(Config.getUserId());
		sellerContractDTO.setContractSeller(defaultEntityId);//报文里的机构号
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
				updateStockStat(cardNo,defaultEntityId);
//				throw new BizServiceException("客户："
//						+ customerDTO.getCustomerName() + ",添加客户合同失败！导入中断！");
				logger.error("["+reqData.getRESPSEQNO()+"]"+"客户："+customerDTO.getCustomerName()+"添加客户合同失败！");
				throw new Exception(e);
			}

		}
		if (contractDTO.getSellContractId() == null) {
			contractDTO.setSellContractId(sellContracts.get(0).getSellContractId());
		}
		
		EntityStockExample entityStockExample = new EntityStockExample();
		
		entityStockExample.createCriteria().andCardNoEqualTo(cardNo);
		List<EntityStock> entityStocks = entityStockMapper
				.selectByExample(entityStockExample);
		// 添加产品
		SellerProductContractDTO sellerProductContractDTO = new SellerProductContractDTO();
		sellerProductContractDTO.setProductId(entityStocks.get(0)
				.getProductId());
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
				updateStockStat(cardNo,defaultEntityId);
				logger.error("["+reqData.getRESPSEQNO()+"]"+"客户："+customerDTO.getCustomerName()+"添加产品失败！");
				throw new Exception(e);
			}
		}
		String packageFee = productDTO.getPackages().get(0).getPackageFee();
		// 销售记名卡订单
		SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();
		SellOrderDTO sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
		sellOrderDTO.setFaceValue("0");//第一次充值金额
		sellOrderDTO.setCardLayoutId(productDTO.getCardLayoutDTOs().get(0).getCardLayoutId());//卡面
		sellOrderDTO.setPackageId(productDTO.getPackages().get(0).getPackageId());//包装
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
		sellOrderDTO.setOrderDate(getDateFormat(new Date()));
		sellOrderDTO.setInvoiceDate(getDateFormat(new Date()));
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
			sellOrderInputDTO = orderService.orderInsert(sellOrderDTO);
		} catch (Exception e) {
			// TODO: handle exception
			updateStockStat(cardNo,defaultEntityId);
			logger.error("["+reqData.getRESPSEQNO()+"]"+"新增订单失败");
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
			updateStockStat(cardNo,defaultEntityId);
			orderService.updateOrderStat(sellOrderInputDTO.getSellOrderDTO().getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"订单："+sellOrderInputDTO.getSellOrderDTO().getOrderId()+"订单添加持卡人失败！");
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
			updateStockStat(cardNo,defaultEntityId);
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"订单："+sellOrderInputDTO.getOrderId()+"订单提交失败！");
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
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			updateStockStat(cardNo,defaultEntityId);
			logger.error("["+reqData.getRESPSEQNO()+"]"+"订单："+sellOrderInputDTO.getOrderId()+"订单审核失败！");
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
		EntityStockExample entityStockEx = new EntityStockExample();
		// 报文里的卡号，机构号
		entityStockEx.createCriteria().andCardNoEqualTo(cardNo)
				.andStockStateEqualTo("2").andDataStateEqualTo("1")
				.andFunctionRoleIdEqualTo("3").andEntityIdEqualTo(defaultEntityId);
		List<EntityStock> entityStock = entityStockMapper
				.selectByExample(entityStockEx);
		if (entityStock.size() == 0) {
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			//updateStockStat(cardNo,defaultEntityId);
			resp.setRESP_CODE(ResponseCode.CARDNO_ISNULL);
			return resp;
//			throw new BizServiceException("客户：" + customerDTO.getCustomerName()
//					+ "订单准备时，产品：" + productDTO.getProductName() + ",库存不足！导入中断！");
		}
		// 报文传过来的卡号
		String[] cardNoArray = new String[] {cardNo};
		orderReadyDTO.setCardNoArray(cardNoArray);
		// 订单准备
		try{
		orderReadyService.cardReady(orderReadyDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//报文传过来的卡号
			updateStockStat(cardNo,defaultEntityId);
//			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"订单："+sellOrderInputDTO.getOrderId()+"订单准备失败！");
			throw new Exception(e);
//			throw new BizServiceException("售卡订单：" + sellOrderDTO.getOrderId()
//					+ ",订单准备失败！导入中断！");
		}
		// 订单准备，提交订单
		try {
			submitOrderService.submitOrderAtReady(sellOrderDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//报文传过来的卡号
//			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			updateStockStat(cardNo,defaultEntityId);
			logger.error("["+reqData.getRESPSEQNO()+"]"+"订单："+sellOrderInputDTO.getOrderId()+"订单准备提交失败！");
			throw new Exception(e);
//			throw new BizServiceException("售卡订单：" + sellOrderDTO.getOrderId()
//					+ ",订单准备失败！导入中断！");
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
		sellOrderDto.setCardNo(cardNo);// 报文的卡号
		try{
			submitOrderService.submitOrderAtHandOut(sellOrderDto);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//报文传过来的卡号
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),sellOrderDto.getDefaultEntityId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"订单："+sellOrderInputDTO.getOrderId()+"订单配送确定失败！");
			throw new Exception(e);
		}
		try {
			sellOrderDto.setBatchNo("");
			sellOrderDto.setInitActStat("0");
			sellOrderDto.setOrderState("32");
			sellOrderDto.setTotalPrice(String.valueOf(totalPrice));
			sellOrderDto.setCvn2(channel);//报文渠道号暂存字段
			submitOrderService.submitOrderAtSendConfirm(sellOrderDto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//报文传过来的卡号
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),sellOrderDto.getDefaultEntityId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"订单："+sellOrderInputDTO.getOrderId()+"订单配送确定失败！");
			throw new Exception(e);
//			throw new BizServiceException("售卡订单：" + sellOrderDto.getOrderId()
//					+ ",订单配送确定失败！导入中断！");
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
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//报文传过来的卡号
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),sellOrderDto.getDefaultEntityId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"订单："+sellOrderInputDTO.getOrderId()+"添加订单付款方式失败！");
			throw new Exception(e);
//			throw new BizServiceException("售卡订单：" + sellOrderDto.getOrderId()
//					+ ",添加订单付款方式失败！导入中断！");
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
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//报文传过来的卡号
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),sellOrderDto.getDefaultEntityId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"订单："+sellOrderInputDTO.getOrderId()+"订单付款确认提交失败！");
			throw new Exception(e);
//			throw new BizServiceException("售卡订单：" + dto.getOrderId()
//					+ ",订单付款确认提交失败！导入中断！");
		}

		dto.setInitActStat("0");
		// 付款审核
		try {
			submitOrderService.submitOrderForPayment(dto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//报文传过来的卡号
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),sellOrderDto.getDefaultEntityId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"订单："+sellOrderInputDTO.getOrderId()+"订单付款审核失败！");
			throw new Exception(e);
//			throw new BizServiceException("售卡订单：" + dto.getOrderId()
//					+ ",订单付款审核失败！导入中断！");
		}
		
		//订单激活
		try {
			submitOrderService.insertActive(sellOrderDto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//报文传过来的卡号
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),sellOrderDto.getDefaultEntityId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"订单："+sellOrderInputDTO.getOrderId()+"激活失败！");
			throw new Exception(e);
		}
		TermSellOrder termSellOrder=new TermSellOrder();
		termSellOrder.setBranchId(reqData.getBRANCH_ID());//服务网点
		termSellOrder.setChannel("70000023");//系统渠道号
		termSellOrder.setTermChannel(reqData.getCHANNEL());//报文渠道号
		termSellOrder.setOrderId(sellOrderInputDTO.getOrderId());
		termSellOrder.setOrderType("10000011");//销售个人记名卡订单
		termSellOrder.setDataState("1");//数据状态1.正常
		termSellOrder.setReqseqno(reqData.getREQSEQNO());//请求流水
		termSellOrder.setIssuerId(reqData.getISSUER_ID());//营销机构
		termSellOrder.setTermId(reqData.getTERM_ID());//终端号
		termSellOrder.setMchntCd(reqData.getMCHNT_CD());//商户号
		termSellOrder.setCreateTime(DateUtil.getCurrentTime());//创建时间
		termSellOrder.setOrderDate(DateUtil.getCurrentDateStr());
		termSellOrder.setOrderState("32");
		try {
			termSellOrderMapper.insertSelective(termSellOrder);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//报文传过来的卡号
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),sellOrderDto.getDefaultEntityId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"订单："+sellOrderInputDTO.getOrderId()+"存储渠道失败！");
			throw new Exception(e);
		}
		//发送短信
		SendShortMessageThread msg=new SendShortMessageThread(reqData.getCARDHOLDER_MOBILE().trim(), "10039");
		msg.start();
		//成功返回
		resp.setRESP_CODE(ResponseCode.SUCCESS_IPOS);
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
	
	 public boolean isValidDate(String str) {
	        boolean convertSuccess=true;
	        if(str.length()!=8){
	        	return false;
	        }
	         SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	         try {
	        	 // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
	            format.setLenient(false);
	            format.parse(str);
	         } catch (ParseException e) {
	           // e.printStackTrace();
	 // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
	            convertSuccess=false;
	        } 
	        return convertSuccess;
	 }

	public boolean checkIssueId(String id){
		SellerExample ex=new SellerExample();
		ex.createCriteria().andEntityIdEqualTo(id).andDataStateEqualTo("1").andSellerStateEqualTo("1");
		List<Seller> list=sellerMapper.selectByExample(ex);
		
		if(list.size()>0){
			return true;
		}else{
			return false;
		}
		
	}

	
	public String checkData(String cardNo,String defaultEntityId) {
		String msg=null;
		// 报文里的卡号，机构号
		EntityStockExample entityStockEx = new EntityStockExample();
		entityStockEx.createCriteria().andCardNoEqualTo(cardNo)
				.andStockStateEqualTo("1").andDataStateEqualTo("1")
				.andFunctionRoleIdEqualTo("3").andEntityIdEqualTo(defaultEntityId);
		List<EntityStock> entityStock = entityStockMapper
				.selectByExample(entityStockEx);
		if (entityStock.size() == 0) {
			msg="营销库存中没有这张卡！";
			return msg;
		}

		return null;

	}
	
	public void updateStockStat(String cardNo,String entityId){
		EntityStockExample ex = new EntityStockExample();
		ex.createCriteria()
				.andEntityIdEqualTo(entityId)
				.andCardNoEqualTo(cardNo);
		EntityStock enStock = new EntityStock();
		enStock.setStockState("1");
		entityStockMapper.updateByExampleSelective(enStock, ex);
	}
	public void updateAccCardInfo(String cardNo){
		AccCardInfo acc=new AccCardInfo();
		AccCardInfoExample ex =new AccCardInfoExample();
		ex.createCriteria().andCardNoEqualTo(cardNo);
		accCardInfoMapper.updateCardHolderByExample(acc, ex);
	}
	
	
	

}
