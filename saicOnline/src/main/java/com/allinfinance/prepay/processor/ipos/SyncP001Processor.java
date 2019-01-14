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
 * �ۿ�
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
		
		//���Ӹ�����Ϣ
		AttachInfo attachInfo=new AttachInfo();
		attachInfo.setPeopleNo(id);
		attachInfo.setPeopleType(type);//00����ͻ���01����ֿ���
		attachInfo.setIndustry(customerDTO.getActivitySector());//��ҵ
		attachInfo.setProfession(customerDTO.getAwareness());//ְҵ
		attachInfo.setValidity(DateUtil.StringDate(customerDTO.getCorpCredEndValidity()));//ʧЧ��
		attachInfo.setCountyr(customerDTO.getNationality());//����
		attachInfo.setCity(customerDTO.getCity());//����
		attachInfo.setUpdateDate(DateUtil.getCurrentTime());
		attachInfo.setEntityId(customerDTO.getFatherEntityId());//������
		attachInfo.setDataStat("1");
		attachInfoMapper.insert(attachInfo);
		
	}
	
	public void updateAttachInfoDTO(CustomerDTO customerDTO,String type,String id) throws BizServiceException{
		//������Ϣ
		AttachInfo attachInfo=new AttachInfo();
		attachInfo.setPeopleNo(id);
		attachInfo.setPeopleType(type);//00����ͻ���01����ֿ���
		attachInfo.setIndustry(customerDTO.getActivitySector());//��ҵ
		attachInfo.setProfession(customerDTO.getAwareness());//ְҵ
		attachInfo.setValidity(DateUtil.StringDate(customerDTO.getCorpCredEndValidity()));//ʧЧ��
		attachInfo.setCountyr(customerDTO.getNationality());//����
		attachInfo.setCity(customerDTO.getCity());//����
		attachInfo.setUpdateDate(DateUtil.getCurrentTime());
		attachInfo.setEntityId(customerDTO.getFatherEntityId());//������
		attachInfo.setDataStat("1");
		AttachInfoExample example=new AttachInfoExample();
		example.createCriteria().andPeopleNoEqualTo(id).andPeopleTypeEqualTo(type)
			.andDataStatEqualTo("1").andEntityIdEqualTo(attachInfo.getEntityId());
		attachInfoMapper.updateByExampleSelective(attachInfo, example);
		
	}
	
	public int selectAttachInfoDTO(CustomerDTO customerDTO,String type,String id) throws BizServiceException{
		
		//������Ϣ
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
				logger.error("�ݲ�֧����ҵ����["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
		}
		String channel=null;//������
		String branch_Id=null;//����
		
		
		if(reqData.getCHANNEL()!=null&&!reqData.getCHANNEL().trim().equals("")){
			channel=reqData.getCHANNEL().trim();
		}else{
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("��������Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		if(reqData.getBRANCH_ID()!=null&&!reqData.getBRANCH_ID().trim().equals("")){
			branch_Id=reqData.getBRANCH_ID().trim();
		}else{
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("���㲻��Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		
		
		if(reqData.getISSUER_ID()!=null&&!reqData.getISSUER_ID().trim().equals("")){
			if(checkIssueId(reqData.getISSUER_ID().trim())){
			cardholderDTO.setDefaultEntityId(reqData.getISSUER_ID().trim());
			customerDTO.setFatherEntityId(reqData.getISSUER_ID().trim());
			}else{
				//û���ҵ�������
				//resp.setRESP_TEXT("����������");
				resp.setRESP_CODE(ResponseCode.ISSUEID_ERROR);
				logger.error("����������["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
		}else{
			//resp.setRESP_TEXT("�����Ų���Ϊ�գ�");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("�����Ų���Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		if(reqData.getCARD_NO()==null&&reqData.getCARD_NO().trim().equals("")){
			//resp.setRESP_TEXT("���Ų���Ϊ�գ�");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("���Ų���Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
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
		/*����*/
		if(reqData.getFIRST_NAME()!=null&&!reqData.getFIRST_NAME().trim().equals("")){
			if(CharUtil.isChinese(reqData.getFIRST_NAME().trim())){
				customerDTO.setCustomerName(reqData.getFIRST_NAME().trim());
				cardholderDTO.setFirstName(reqData.getFIRST_NAME().trim());
			}else{
				//�����Ƿ��ַ�
				resp.setRESP_CODE(ResponseCode.ILLEGAL_CHARACTER);
				logger.error("���������Ƿ��ַ���["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			
		}else{
			//resp.setRESP_TEXT("�ֿ�����������Ϊ�գ�");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("�ֿ�����������Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		
		
		
		/*�绰*/
		if(reqData.getCARDHOLDER_MOBILE()!=null&&!reqData.getCARDHOLDER_MOBILE().trim().equals("")){
			boolean isTrue=reqData.getCARDHOLDER_MOBILE().trim().matches("([1][3,4,5,8,7][0-9]{9})");
			if(isTrue){
				customerDTO.setCustomerTelephone(reqData.getCARDHOLDER_MOBILE().trim());
				cardholderDTO.setCardholderMobile(reqData.getCARDHOLDER_MOBILE().trim());
			}else{
				//�ֻ������ʽ����ȷ
				resp.setRESP_CODE(ResponseCode.MOBILE_ERROR);
				logger.error("�ֻ������ʽ����ȷ��["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
			
		}else{
			//resp.setRESP_TEXT("�ֻ����벻��Ϊ�գ�");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("�ֻ����벻��Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		/*֤������*/
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
		if(reqData.getID_NO()!=null&&!reqData.getID_NO().trim().equals("")){
			if(reqData.getID_TYPE().trim().equals("1")){
				//���֤
				String errMsg=IDCardCheck.IDCardValidate(reqData.getID_NO().toUpperCase());
				if(errMsg==null||errMsg.equals("")){
					customerDTO.setCorpCredId(reqData.getID_NO().toUpperCase());
					cardholderDTO.setIdNo(reqData.getID_NO().toUpperCase());
				}else{
					//resp.setRESP_TEXT("֤��������");
					resp.setRESP_CODE(ResponseCode.ID_NO_ERROR);
					logger.error("֤�����������֤����["+reqData.getRESPSEQNO()+"]");
					return resp;
				}
				
			}else if(reqData.getID_TYPE().equals("2")){
				//����
				boolean isTrue = reqData.getID_NO().trim().matches("([0-9a-zA-Z]*)");
	    		 if(isTrue==true){
	    			 customerDTO.setCorpCredId(reqData.getID_NO().trim());
	    			 cardholderDTO.setIdNo(reqData.getID_NO().trim());
	    		 }else{
	    			 //resp.setRESP_TEXT("֤��������");
	    			 resp.setRESP_CODE(ResponseCode.ID_NO_ERROR);
	    			 logger.error("֤�������󣨻��գ���["+reqData.getRESPSEQNO()+"]");
	    			 return resp;
	    		 }
			}else if(reqData.getID_TYPE().trim().equals("3")){
				//����
				customerDTO.setCorpCredId(reqData.getID_NO().trim());
   			 	cardholderDTO.setIdNo(reqData.getID_NO().trim());
			}
		}else{
			//resp.setRESP_TEXT("֤���Ų���Ϊ�գ�");
			resp.setRESP_CODE(ResponseCode.ISNULL);
			logger.error("֤���Ų���Ϊ�գ�["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		/*�Ա�*/
		if(reqData.getCARDHOLDER_GENDER()!=null&&!reqData.getCARDHOLDER_GENDER().trim().equals("")){
			if(reqData.getCARDHOLDER_GENDER().trim().endsWith("1")||reqData.getCARDHOLDER_GENDER().trim().endsWith("2")){
				cardholderDTO.setCardholderGender(reqData.getCARDHOLDER_GENDER().trim());
			}else{
				resp.setRESP_CODE(ResponseCode.GENDER_ERROR);
				logger.error("�Ա�����["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
		}else{
			//����Ա�Ϊ�գ��ж�֤������
			if(reqData.getID_TYPE().trim().equals("1")){
				//18λ���֤
				if(reqData.getID_NO().trim().length()==18){
					//��ȡ���֤�ĵ�17λ�����Ա�����Ϊ�У�ż��ΪŮ
					int num=Integer.parseInt(reqData.getID_NO().trim().substring(16,17));
					if(num%2==0){
						//ż��
						cardholderDTO.setCardholderGender("2");
					}else{
						cardholderDTO.setCardholderGender("1");
					}
				}
				//15λ���֤
				if(reqData.getID_NO().length()==15){
					//��ȡ���֤�ĵ�15λ�����Ա�����Ϊ�У�ż��ΪŮ
					int num=Integer.parseInt(reqData.getID_NO().trim().substring(14,15));
					if(num%2==0){
						//ż��
						cardholderDTO.setCardholderGender("2");
					}else{
						cardholderDTO.setCardholderGender("1");
					}
				}
				
			}else{
				//���ش�����д�Ա�
				//resp.setRESP_TEXT("����д�Ա�");
				resp.setRESP_CODE(ResponseCode.ISNULL);
				logger.error("����д�Ա�["+reqData.getRESPSEQNO()+"]");
				return resp;
			}
		}
		/*�ͻ�֤��ʧЧ����*/
		if(isValidDate(reqData.getVALIDITY())==false){
			resp.setRESP_CODE(ResponseCode.VALIDITY_ERROR);
			logger.error("���ڸ�ʽ��["+reqData.getRESPSEQNO()+"]");
			return resp;
		}
		customerDTO.setCorpCredEndValidity(DateUtil.formatStringDate(reqData.getVALIDITY()));
		
		EntityStockExample stockEX=new EntityStockExample();
		stockEX.createCriteria().andCardNoEqualTo(reqData.getCARD_NO())
		.andStockStateEqualTo("1").andDataStateEqualTo("1")
		.andFunctionRoleIdEqualTo("3").andEntityIdEqualTo(reqData.getISSUER_ID());
		logger.debug("��ѯ���ţ�["+reqData.getRESPSEQNO()+"]");
		List<EntityStock> stocks = entityStockMapper.selectByExample(stockEX);
		if (stocks.size() == 0) {
			//resp.setRESP_TEXT("Ӫ�����������û�����ſ���");
			resp.setRESP_CODE(ResponseCode.CARDNO_ISNULL);
			return resp;
		}
		EntityStock enStock=new EntityStock();
		enStock.setStockState("2");
		logger.debug("�޸Ŀ��ſ��״̬��["+reqData.getRESPSEQNO()+"]");
		entityStockMapper.updateByExampleSelective(enStock, stockEX);
		
		/*��ν*/
		if(cardholderDTO.getCardholderGender().trim().equals("1")){
			cardholderDTO.setCardholderSalutation("1");
		}
		else if(cardholderDTO.getCardholderGender().trim().equals("2")){
			cardholderDTO.setCardholderSalutation("3");//Ůʿ
		}
		/*��ַ*/
		customerDTO.setCustomerAddress(reqData.getDELIVERY_ADDRESS().trim());
		cardholderDTO.setMailingAddress(reqData.getDELIVERY_ADDRESS().trim());
//		/*����*/
//		cardholderDTO.setCardholderBirthday("");
		/*���ƺ�*/
		cardholderDTO.setPlateNumber(reqData.getPLATE_NUMBER().trim());
		/*���ܺ�*/
		//cardholderDTO.setVId();
		/*��ʻ֤��*/
		cardholderDTO.setDriverLicence(reqData.getDRIVER_LICENCE().trim());
		/*�ֿ��˷���*/
		cardholderDTO.setCardholderSegment("0");
		
		/*�ͻ�ְҵ���*/
		if(StringUtils.isEmpty(reqData.getAWARENESS())){
			customerDTO.setAwareness("1");
		}else{
			customerDTO.setAwareness(reqData.getAWARENESS());
		}
		
		/*�ֿ��˷���*/
		cardholderDTO.setCardholderSegment("0");
		/*����*/
		customerDTO.setChannel("3");//3�ŵ�
		/*�ͻ���ҵ*/
		if(StringUtils.isEmpty(reqData.getACTIVITY_SECTOR())){
			customerDTO.setActivitySector("2");
		}else{
			customerDTO.setActivitySector(reqData.getACTIVITY_SECTOR());
		}
		//����
		customerDTO.setNationality(reqData.getCOUNTRY());
		//����
		customerDTO.setCity(reqData.getCITY());
		
		//�ֿ��˱�ע
		cardholderDTO.setCardholderComment(reqData.getREMARK().trim());
		/*�ͻ���������Ϣ����дΪĬ��*/
		//customerDTO.setActivitySector("2");
		//customerDTO.setAwareness("1");
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
		String cardNo=reqData.getCARD_NO();// ������Ŀ���
		logger.debug("��ѯ�ͻ���Ϣ��["+reqData.getRESPSEQNO()+"]");
		CustomerDTO customerByIdNo = customerService
				.getCustomerByIdNo(customerDTO);
		customerDTO.setFatherEntityId(reqData.getISSUER_ID());
		CardHolder cardHolder = new CardHolder();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		try {
			if (customerByIdNo != null) {

				String modifyTime = format.format(new Date());
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
				entityDeliveryMapper.updateByExampleSelective(entityDelivery,
						entityDeliveryExample);
				// ����Ĭ�Ͽ�ݵ���ϵ����Ϣ
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
					logger.debug("����Ĭ�Ͽ�ݵ���ϵ����Ϣ��["+reqData.getRESPSEQNO()+"]");
					deliveryContactMapper.updateByExampleSelective(deliveryContact,
							deliveryContactExample);
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
 	

		/* ��ѯ���ֿ����Ƿ���ڣ�������������Ϊ���µ���Ϣ�����������������Ϣ */
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
				logger.debug("�޸ĳֿ�����Ϣ��["+reqData.getRESPSEQNO()+"]");
				cardHolderMapper.updateByExampleSelective(cardholder,
						cardholderExample);
				ReflectionUtil.copyProperties(cardholderLists.get(0), cardHolder);

			} else {
				logger.debug("���ӳֿ�����Ϣ��["+reqData.getRESPSEQNO()+"]");
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
				updateStockStat(cardNo,defaultEntityId);
//				throw new BizServiceException("�ͻ���"
//						+ customerDTO.getCustomerName() + ",��ӿͻ���ͬʧ�ܣ������жϣ�");
				logger.error("["+reqData.getRESPSEQNO()+"]"+"�ͻ���"+customerDTO.getCustomerName()+"��ӿͻ���ͬʧ�ܣ�");
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
		// ��Ӳ�Ʒ
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
				updateStockStat(cardNo,defaultEntityId);
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
		sellOrderDTO.setDeliveryMeans("2");// �ͻ���ʽ
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
			updateStockStat(cardNo,defaultEntityId);
			orderService.updateOrderStat(sellOrderInputDTO.getSellOrderDTO().getOrderId());
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
			updateStockStat(cardNo,defaultEntityId);
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
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
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			updateStockStat(cardNo,defaultEntityId);
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
		EntityStockExample entityStockEx = new EntityStockExample();
		// ������Ŀ��ţ�������
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
//			throw new BizServiceException("�ͻ���" + customerDTO.getCustomerName()
//					+ "����׼��ʱ����Ʒ��" + productDTO.getProductName() + ",��治�㣡�����жϣ�");
		}
		// ���Ĵ������Ŀ���
		String[] cardNoArray = new String[] {cardNo};
		orderReadyDTO.setCardNoArray(cardNoArray);
		// ����׼��
		try{
		orderReadyService.cardReady(orderReadyDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//���Ĵ������Ŀ���
			updateStockStat(cardNo,defaultEntityId);
//			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getOrderId()+"����׼��ʧ�ܣ�");
			throw new Exception(e);
//			throw new BizServiceException("�ۿ�������" + sellOrderDTO.getOrderId()
//					+ ",����׼��ʧ�ܣ������жϣ�");
		}
		// ����׼�����ύ����
		try {
			submitOrderService.submitOrderAtReady(sellOrderDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//���Ĵ������Ŀ���
//			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			updateStockStat(cardNo,defaultEntityId);
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
		sellOrderDto.setCardNo(cardNo);// ���ĵĿ���
		try{
			submitOrderService.submitOrderAtHandOut(sellOrderDto);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//���Ĵ������Ŀ���
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),sellOrderDto.getDefaultEntityId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getOrderId()+"��������ȷ��ʧ�ܣ�");
			throw new Exception(e);
		}
		try {
			sellOrderDto.setBatchNo("");
			sellOrderDto.setInitActStat("0");
			sellOrderDto.setOrderState("32");
			sellOrderDto.setTotalPrice(String.valueOf(totalPrice));
			sellOrderDto.setCvn2(channel);//�����������ݴ��ֶ�
			submitOrderService.submitOrderAtSendConfirm(sellOrderDto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//���Ĵ������Ŀ���
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),sellOrderDto.getDefaultEntityId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getOrderId()+"��������ȷ��ʧ�ܣ�");
			throw new Exception(e);
//			throw new BizServiceException("�ۿ�������" + sellOrderDto.getOrderId()
//					+ ",��������ȷ��ʧ�ܣ������жϣ�");
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
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//���Ĵ������Ŀ���
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),sellOrderDto.getDefaultEntityId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
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
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//���Ĵ������Ŀ���
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),sellOrderDto.getDefaultEntityId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
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
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//���Ĵ������Ŀ���
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),sellOrderDto.getDefaultEntityId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getOrderId()+"�����������ʧ�ܣ�");
			throw new Exception(e);
//			throw new BizServiceException("�ۿ�������" + dto.getOrderId()
//					+ ",�����������ʧ�ܣ������жϣ�");
		}
		
		//��������
		try {
			submitOrderService.insertActive(sellOrderDto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//���Ĵ������Ŀ���
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),sellOrderDto.getDefaultEntityId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getOrderId()+"����ʧ�ܣ�");
			throw new Exception(e);
		}
		TermSellOrder termSellOrder=new TermSellOrder();
		termSellOrder.setBranchId(reqData.getBRANCH_ID());//��������
		termSellOrder.setChannel("70000023");//ϵͳ������
		termSellOrder.setTermChannel(reqData.getCHANNEL());//����������
		termSellOrder.setOrderId(sellOrderInputDTO.getOrderId());
		termSellOrder.setOrderType("10000011");//���۸��˼���������
		termSellOrder.setDataState("1");//����״̬1.����
		termSellOrder.setReqseqno(reqData.getREQSEQNO());//������ˮ
		termSellOrder.setIssuerId(reqData.getISSUER_ID());//Ӫ������
		termSellOrder.setTermId(reqData.getTERM_ID());//�ն˺�
		termSellOrder.setMchntCd(reqData.getMCHNT_CD());//�̻���
		termSellOrder.setCreateTime(DateUtil.getCurrentTime());//����ʱ��
		termSellOrder.setOrderDate(DateUtil.getCurrentDateStr());
		termSellOrder.setOrderState("32");
		try {
			termSellOrderMapper.insertSelective(termSellOrder);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			orderService.delSellOrderCardList(sellOrderInputDTO.getOrderId());
			updateAccCardInfo(cardNo);//���Ĵ������Ŀ���
			entityStockService.delStockOperaterRecord(sellOrderInputDTO.getOrderId());
			updateStockStat(sellOrderDto.getCardNo(),sellOrderDto.getDefaultEntityId());
			orderService.updateOrderStat(sellOrderInputDTO.getOrderId());
			logger.error("["+reqData.getRESPSEQNO()+"]"+"������"+sellOrderInputDTO.getOrderId()+"�洢����ʧ�ܣ�");
			throw new Exception(e);
		}
		//���Ͷ���
		SendShortMessageThread msg=new SendShortMessageThread(reqData.getCARDHOLDER_MOBILE().trim(), "10039");
		msg.start();
		//�ɹ�����
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
	        	 // ����lenientΪfalse. ����SimpleDateFormat��ȽϿ��ɵ���֤���ڣ�����2007/02/29�ᱻ���ܣ���ת����2007/03/01
	            format.setLenient(false);
	            format.parse(str);
	         } catch (ParseException e) {
	           // e.printStackTrace();
	 // ���throw java.text.ParseException����NullPointerException����˵����ʽ����
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
		// ������Ŀ��ţ�������
		EntityStockExample entityStockEx = new EntityStockExample();
		entityStockEx.createCriteria().andCardNoEqualTo(cardNo)
				.andStockStateEqualTo("1").andDataStateEqualTo("1")
				.andFunctionRoleIdEqualTo("3").andEntityIdEqualTo(defaultEntityId);
		List<EntityStock> entityStock = entityStockMapper
				.selectByExample(entityStockEx);
		if (entityStock.size() == 0) {
			msg="Ӫ�������û�����ſ���";
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
