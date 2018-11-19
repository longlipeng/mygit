package com.huateng.univer.seller.customer.biz.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.shangqi.gateway.dto.CustomerUpdateForGatewayDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;
import com.allinfinance.univer.seller.cardholder.dto.AttachInfoDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.customer.CustomerQueryDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.AttachDAO;
import com.huateng.framework.ibatis.dao.CardholderDAO;
import com.huateng.framework.ibatis.dao.CheckPeopleInfoDAO;
import com.huateng.framework.ibatis.dao.CustomerDAO;
import com.huateng.framework.ibatis.dao.DeliveryContactDAO;
import com.huateng.framework.ibatis.dao.EntityDeliveryDAO;
import com.huateng.framework.ibatis.dao.SellContractDAO;
import com.huateng.framework.ibatis.model.Cardholder;
import com.huateng.framework.ibatis.model.CardholderExample;
import com.huateng.framework.ibatis.model.CheckPeopleInfo;
import com.huateng.framework.ibatis.model.CheckPeopleInfoExample;
import com.huateng.framework.ibatis.model.CheckPeopleInfoKey;
import com.huateng.framework.ibatis.model.Customer;
import com.huateng.framework.ibatis.model.CustomerExample;
import com.huateng.framework.ibatis.model.DeliveryContact;
import com.huateng.framework.ibatis.model.DeliveryContactExample;
import com.huateng.framework.ibatis.model.EntityDelivery;
import com.huateng.framework.ibatis.model.EntityDeliveryExample;
import com.huateng.framework.ibatis.model.InvoiceCompany;
import com.huateng.framework.ibatis.model.SellContract;
import com.huateng.framework.ibatis.model.SellContractExample;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.ibatis.model.UserExample;
import com.huateng.framework.ibatis.model.UserExample.Criteria;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.hstserver.gatewayService.Java2ACCBusinessServiceImpl;
import com.huateng.hstserver.model.AccPackageDTO;
//import com.huateng.univer.cardmanage.biz.service.CardManageService;
import com.huateng.univer.entitybaseinfo.bank.biz.service.BankService;
import com.huateng.univer.entitybaseinfo.contact.biz.service.ContactService;
import com.huateng.univer.entitybaseinfo.delivarypoint.biz.service.DeliveryPointService;
import com.huateng.univer.entitybaseinfo.deliverycontact.biz.service.DeliveryContactService;
import com.huateng.univer.entitybaseinfo.department.biz.service.DepartmentService;
import com.huateng.univer.entitybaseinfo.invoiceaddress.biz.service.InvoiceAddressService;
import com.huateng.univer.entitybaseinfo.invoicecompany.biz.service.InvoiceCompanyService;
import com.huateng.univer.seller.customer.biz.service.CustomerService;
import com.huateng.univer.seller.sellercontract.biz.service.SellerContractService;
import com.huateng.univer.system.user.integration.dao.UserServiceDAO;

/**
 * 客户管理服务实现
 */
public class CustomerServiceImpl implements CustomerService {

	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 共通操作DAO
	 */
	private CommonsDAO commonsDAO;
	private PageQueryDAO pageQueryDAO;
	private CustomerDAO customerDAO;
	private UserServiceDAO userServiceDAO;

	// 实体service
	private ContactService contactService;
	private DeliveryPointService deliveryPointService;
	private DeliveryContactService deliveryContactService;
	private DepartmentService departmentService;
	private InvoiceAddressService invoiceAddressService;
	private InvoiceCompanyService invoiceCompanyService;
	private SellContractDAO sellerContractDAO;
	private BankService bankService;
	private SellerContractService sellerContractService;
	private AttachDAO attachDAO;
	private CardholderDAO cardholderDAO;
	private DeliveryContactDAO deliveryContractDAO;
	private EntityDeliveryDAO deliveryDAO;
	private CheckPeopleInfoDAO checkPeopleInfoDAO;
	
	
	public CheckPeopleInfoDAO getCheckPeopleInfoDAO() {
		return checkPeopleInfoDAO;
	}

	public void setCheckPeopleInfoDAO(CheckPeopleInfoDAO checkPeopleInfoDAO) {
		this.checkPeopleInfoDAO = checkPeopleInfoDAO;
	}

	private Java2ACCBusinessServiceImpl java2ACCBusinessService;
	public Java2ACCBusinessServiceImpl getJava2ACCBusinessService() {
		return java2ACCBusinessService;
	}

	public void setJava2ACCBusinessService(
			Java2ACCBusinessServiceImpl java2accBusinessService) {
		java2ACCBusinessService = java2accBusinessService;
	}


	public AttachDAO getAttachDAO() {
		return attachDAO;
	}

	public void setAttachDAO(AttachDAO attachDAO) {
		this.attachDAO = attachDAO;
	}

	public CardholderDAO getCardholderDAO() {
		return cardholderDAO;
	}

	public void setCardholderDAO(CardholderDAO cardholderDAO) {
		this.cardholderDAO = cardholderDAO;
	}

	public DeliveryContactDAO getDeliveryContractDAO() {
		return deliveryContractDAO;
	}

	public void setDeliveryContractDAO(DeliveryContactDAO deliveryContractDAO) {
		this.deliveryContractDAO = deliveryContractDAO;
	}

	public EntityDeliveryDAO getDeliveryDAO() {
		return deliveryDAO;
	}

	public void setDeliveryDAO(EntityDeliveryDAO deliveryDAO) {
		this.deliveryDAO = deliveryDAO;
	}

	public SellerContractService getSellerContractService() {
		return sellerContractService;
	}

	public void setSellerContractService(
			SellerContractService sellerContractService) {
		this.sellerContractService = sellerContractService;
	}

	public BankService getBankService() {
		return bankService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public SellContractDAO getSellerContractDAO() {
		return sellerContractDAO;
	}

	public void setSellerContractDAO(SellContractDAO sellerContractDAO) {
		this.sellerContractDAO = sellerContractDAO;
	}

	/**
	 * 初始化客户
	 */
	public CustomerDTO initCustomer(CustomerDTO customerDTO)
			throws BizServiceException {
		try {
			UserExample example = new UserExample();
			Criteria criteria = example.createCriteria().andIsSaleFlageEqualTo(
					DataBaseConstant.DEFAULT_FLAG_YES).andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL);
			if (null != customerDTO.getDefaultEntityId()
					&& !"".equals(customerDTO.getDefaultEntityId())) {
				criteria.andEntityIdEqualTo(customerDTO.getDefaultEntityId());
			}
			List<User> userList = userServiceDAO.selectByExample(example);
			List<UserDTO> userDTOList = new ArrayList<UserDTO>();
			for (User user : userList) {
				UserDTO userDTO = new UserDTO();
				ReflectionUtil.copyProperties(user, userDTO);
				userDTOList.add(userDTO);
			}
			// 销售人员信息
			customerDTO.setSalesmanList(userDTOList);
		} catch (Exception e) {
			throw new BizServiceException("初始化客户信息失败！");
		}
		return customerDTO;
	}

	/**
	 * 查看客户
	 */
	public CustomerDTO viewCustomer(CustomerDTO customerDTO)
			throws BizServiceException {
		try {

			CustomerExample example = new CustomerExample();
			com.huateng.framework.ibatis.model.CustomerExample.Criteria criteria = example
					.createCriteria();
			criteria.andEntityIdEqualTo(customerDTO.getEntityId());
			if (null != customerDTO.getFatherEntityId()) {
				criteria.andFatherEntityIdEqualTo(customerDTO
						.getFatherEntityId());
			}
			Customer customer = new Customer();
			List<Customer> customerList = customerDAO.selectByExample(example);
			if (customerList.size() > 0) {
				customer = customerList.get(0);
			}

			/*
			 * CustomerKey key = new CustomerKey();
			 * key.setEntityId(customerDTO.getEntityId()); if (null !=
			 * customerDTO.getFatherEntityId()) {
			 * key.setFatherEntityId(customerDTO.getFatherEntityId()); }
			 * Customer customer = customerDAO.selectByPrimaryKey(key);
			 */

			ReflectionUtil.copyProperties(customer, customerDTO);
			customerDTO = initCustomer(customerDTO);

			String entityId = customerDTO.getEntityId();

			if (customerDTO.getCustContract() != null
					&& !"".equals(customerDTO.getCustContract())) {
				SellContractExample sce = new SellContractExample();
				sce
						.createCriteria()
						.andContractBuyerEqualTo(entityId)
						.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
				List<SellContract> scds = (List<SellContract>) sellerContractDAO
						.selectByExample(sce);
				if (scds.size() > 0) {
					String custContract = scds.get(0).getSellContractId();
					customerDTO.setCustContract(custContract);
				}
			}
			// 关联实体信息
			customerDTO
					.setContractList(contactService.inqueryContact(entityId));
			customerDTO.setDeliveryPointList(deliveryPointService
					.inquery(entityId));
			customerDTO.setDepartmentList(departmentService.inquery(entityId));
			customerDTO.setInvoiceAddressList(invoiceAddressService
					.inquery(entityId));
			customerDTO.setInvoiceCompanyList(invoiceCompanyService
					.inquery(entityId));
			customerDTO.setBankList(bankService.inquery(entityId, "1"));
			return customerDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看客户失败！");
		}
	}

	/**
	 * 查询客户
	 */
	public PageDataDTO inqueryCustomer(CustomerQueryDTO customerQueryDTO)
			throws BizServiceException {
		try {
			return pageQueryDAO.query("CUSTOMER.selectCustomersByDTO",
					customerQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询客户失败");
		}
	}

	/**
	 * 添加插入客户
	 */
	public CustomerDTO insertCustomer(CustomerDTO customerDTO)
			throws BizServiceException {
		try {
			// 检查用户名是否存在

			Customer customer = new Customer();
			ReflectionUtil.copyProperties(customerDTO, customer);

			String entityId = commonsDAO.getNextValueOfSequence("TB_ENTITY");
			String operateUser = customerDTO.getLoginUserId();

			customer.setEntityId(entityId);
			customer.setFatherEntityId(customerDTO.getDefaultEntityId());
			customer.setCreateTime(DateUtil.getCurrentTime());
			customer.setCreateUser(operateUser);
			customer.setModifyTime(DateUtil.getCurrentTime());
			customer.setModifyUser(operateUser);
			customer.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			customerDAO.insert(customer);
			ReflectionUtil.copyProperties(customer, customerDTO);

			// 默认插入部门信息
			DepartmentDTO entityDepartment = new DepartmentDTO();
			entityDepartment.setEntityId(entityId);
			entityDepartment.setDepartmentName(customer.getCustomerName());
			entityDepartment.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			entityDepartment.setCreateUser(operateUser);
			entityDepartment.setModifyUser(operateUser);
			departmentService.insert(entityDepartment);

			// 默认插入联系人信息
			ContactDTO entityContact = new ContactDTO();
			entityContact.setEntityId(entityId);
			entityContact.setContactName(customer.getCustomerName());
			entityContact.setContactType("1");
			entityContact.setContactGender("1");
			entityContact.setContactTelephone(customer.getCustomerTelephone());
			entityContact.setCreateUser(operateUser);
			entityContact.setModifyUser(operateUser);
			entityContact.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			entityContact.setValidityFlag("1");
			entityContact.setPapersNo("1");
			entityContact.setPapersType("1");
			contactService.insert(entityContact);

			// 默认插入快递点信息
			DeliveryPointDTO entityDelivery = new DeliveryPointDTO();
			entityDelivery.setEntityId(entityId);
			entityDelivery.setDeliveryName(customer.getCustomerName());
			entityDelivery.setDeliveryPostcode(customer.getCustomerPostcode());
			entityDelivery.setDeliveryAddress(customer.getCustomerAddress());
			entityDelivery.setCreateUser(operateUser);
			entityDelivery.setModifyUser(operateUser);
			entityDelivery.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			entityDelivery = deliveryPointService.insert(entityDelivery);

			// 默认快递点联系人（依赖于快递点）
			DeliveryRecipientDTO deliveryContact = new DeliveryRecipientDTO();
			deliveryContact.setDeliveryPointId(entityDelivery.getDeliveryId());
			deliveryContact
					.setDeliveryContact(entityDelivery.getDeliveryName());
			deliveryContact.setContactPhone(customer.getCustomerTelephone());
			deliveryContact.setCreateUser(operateUser);
			deliveryContact.setModifyUser(operateUser);
			deliveryContact.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			deliveryContactService.insert(deliveryContact);

			// 默认插入发票地址信息
			InvoiceAddressDTO invoiceAddress = new InvoiceAddressDTO();
			invoiceAddress.setEntityId(entityId);
			invoiceAddress.setInvoiceAddress(customer.getCustomerAddress());
			invoiceAddress.setAddressPostcode(customer.getCustomerPostcode());
			invoiceAddress.setCreateUser(operateUser);
			invoiceAddress.setModifyUser(operateUser);
			invoiceAddress.setInvoiceRecipient(customer.getCustomerName());
			invoiceAddress
					.setDeliveryOption(DataBaseConstant.DELIVERY_OPERATION_SEND);
			invoiceAddress.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			invoiceAddressService.insert(invoiceAddress);

			// 默认插入发票公司信息
			InvoiceCompanyDTO invoiceCompanyDTO = new InvoiceCompanyDTO();
			invoiceCompanyDTO.setEntityId(entityId);
			invoiceCompanyDTO.setInvoiceCompanyName(customer.getCustomerName());
			invoiceCompanyDTO.setCreateUser(operateUser);
			invoiceCompanyDTO.setModifyUser(operateUser);
			invoiceCompanyDTO.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			invoiceCompanyService.insert(invoiceCompanyDTO);

			return customerDTO;
		} catch (BizServiceException be) {
			throw be;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加客户失败！");
		}

	}

	/**
	 * 修改客户状态
	 */
	public void modifyState(CustomerDTO customerDTO) throws BizServiceException {

	}
public void insertAttachInfoDTO(CustomerDTO customerDTO,String type,String id) throws BizServiceException{
		
		//增加附加信息
		AttachInfoDTO attachInfoDTO=new AttachInfoDTO();
		attachInfoDTO.setPeopleNo(id);
		attachInfoDTO.setPeopleType(type);//00代表客户，01代表持卡人
		attachInfoDTO.setIndustry(customerDTO.getActivitySector());//行业
		attachInfoDTO.setProfession(customerDTO.getAwareness());//职业
		attachInfoDTO.setValidity(DateUtil.StringDate(customerDTO.getCorpCredEndValidity()));//失效期
		attachInfoDTO.setCountry(customerDTO.getNationality());//国家
		attachInfoDTO.setCity(customerDTO.getCity());//城市
		attachInfoDTO.setUpdateDate(DateUtil.getCurrentTime());
		attachInfoDTO.setEntityId(customerDTO.getFatherEntityId());//机构号
		attachInfoDTO.setDataStat("1");
		try {
			attachDAO.insertAttachInfo(attachInfoDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizServiceException("查询持卡人客户信息失败！");
		}
		
	}
	
	public void updateAttachInfoDTO(CustomerDTO customerDTO,String type,String id) throws BizServiceException{
		//附加信息
		AttachInfoDTO attachInfoDTO=new AttachInfoDTO();
		attachInfoDTO.setPeopleNo(id);
		attachInfoDTO.setPeopleType(type);//00代表客户，01代表持卡人
		attachInfoDTO.setIndustry(customerDTO.getActivitySector());//行业
		attachInfoDTO.setProfession(customerDTO.getAwareness());//职业
		attachInfoDTO.setValidity(DateUtil.StringDate(customerDTO.getCorpCredEndValidity()));//失效期
		attachInfoDTO.setCountry(customerDTO.getNationality());//国家
		attachInfoDTO.setNation(customerDTO.getNation());//名族
		attachInfoDTO.setCity(customerDTO.getCity());//城市
		attachInfoDTO.setUpdateDate(DateUtil.getCurrentTime());
		attachInfoDTO.setEntityId(customerDTO.getFatherEntityId());//机构号
		attachInfoDTO.setRs1(customerDTO.getGender());//性别
		attachInfoDTO.setDataStat("1");
		try {
			attachDAO.updateAttachInfo(attachInfoDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizServiceException("查询持卡人客户信息失败！");
		}
		
	}
	
	public int selectAttachInfoDTO(CustomerDTO customerDTO,String type,String id) throws BizServiceException{
		
		//增加附加信息
		AttachInfoDTO attachInfoDTO=new AttachInfoDTO();
		attachInfoDTO.setPeopleNo(id);
		attachInfoDTO.setPeopleType(type);//00代表客户，01代表持卡人
		attachInfoDTO.setEntityId(customerDTO.getFatherEntityId());//机构号
		attachInfoDTO.setValidity(customerDTO.getCorpCredEndValidity());
		attachInfoDTO.setProfession(customerDTO.getAwareness());//职业
		attachInfoDTO.setValidity(DateUtil.StringDate(customerDTO.getCorpCredEndValidity()));//失效期
		attachInfoDTO.setCountry(customerDTO.getNationality());//国家
		attachInfoDTO.setNation(customerDTO.getNation());//名族
		attachInfoDTO.setRs1(customerDTO.getGender());//性别
		attachInfoDTO.setDataStat("1");
		try {
			List<AttachInfoDTO> attachInfos=attachDAO.getAttachInfos(attachInfoDTO);
			return attachInfos.size();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizServiceException("查询持卡人客户信息失败！");
		}
		
		
	}
	/**
	 * 门户客户信息补录
	 */
	public void updateCustomerForGateway(CustomerUpdateForGatewayDTO dto)
			throws BizServiceException{
		CustomerDTO customerDTO=new CustomerDTO();
		CardholderDTO cardholderDTO=new CardholderDTO();
		//姓名
		customerDTO.setCustomerName(dto.getCustomerName());
		cardholderDTO.setFirstName(dto.getCustomerName());
		//证件号
		customerDTO.setCorpCredId(dto.getIdNo());
		cardholderDTO.setIdNo(dto.getIdNo());
		//证件类型
		if(dto.getIdType().trim().equals("1")){
			customerDTO.setCorpCredType(dto.getIdType());
			cardholderDTO.setIdType(dto.getIdType());
		}else if(dto.getIdType().trim().equals("2")){
			customerDTO.setCorpCredType("4");
			cardholderDTO.setIdType("4");
		}else if(dto.getIdType().trim().equals("3")){
			customerDTO.setCorpCredType("5");
			cardholderDTO.setIdType("5");
		}else{
			throw new BizServiceException("证件类型有误！");
		}
		//地址
		customerDTO.setCustomerAddress(dto.getCustomerAddress());
		cardholderDTO.setMailingAddress(dto.getCustomerAddress());
		/*电话*/
		customerDTO.setCustomerTelephone(dto.getCustomerTelephone());
		cardholderDTO.setCardholderMobile(dto.getCustomerTelephone());
		//国籍
//		customerDTO.setNationality(dto.getNationality());
		customerDTO.setNationality("CHN");
		//名族
		customerDTO.setNation("1"); 
		/*性别*/
		cardholderDTO.setCardholderGender(dto.getGender());
		customerDTO.setGender(dto.getGender());
		if(StringUtils.isNotEmpty(dto.getGender())){
			/*称谓*/
			if(cardholderDTO.getCardholderGender().trim().equals("1")){
				cardholderDTO.setCardholderSalutation("1");
			}
			else if(cardholderDTO.getCardholderGender().trim().equals("2")){
				cardholderDTO.setCardholderSalutation("3");//女士
			}
		}
		/*证件失效日期*/
		if(StringUtils.isNotEmpty(dto.getEndValidity())){
			customerDTO.setCorpCredEndValidity(dto.getEndValidity());
		}
		
		/*客户职业类别*/
		customerDTO.setAwareness(dto.getAwareness());
		
		/*List<CardholderQueryDTO> cardholderlist=
			cardholderDAO.selectCardholderByCardNo(dto.getCardNo());
		boolean istrue=false;
		for(CardholderQueryDTO queryDTO: cardholderlist){
			if(queryDTO.getIdNo().equals(cardholderDTO.getIdNo())
					&&queryDTO.getIdType().equals(cardholderDTO.getIdType())){
				istrue=true;
			}
		}
		if(istrue==false){
			throw new BizServiceException("卡号与持卡人不匹配！");
		}*/
		
		updateCustomerAndCardholder(customerDTO,cardholderDTO);
		
		//解锁
		/*AccPackageDTO accPackageDTO = new AccPackageDTO();
		try {
			accPackageDTO.setTxnCode("S450");
			accPackageDTO.setCardNo(dto.getCardNo());
			accPackageDTO.setServiceFee(Amount.getDataBaseAmount("0"));
			accPackageDTO = java2ACCBusinessService.cardOperate(accPackageDTO);
			if(!"00".equals(accPackageDTO.getRespCode())){
				this.logger.error(accPackageDTO.getRespCode());
				throw new BizServiceException("卡片操作失败");
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
			throw new BizServiceException("卡片操作失败");
		}*/
		CheckPeopleInfo info=new CheckPeopleInfo();
		info.setCardNo(dto.getCardNo());
		info.setCheckState("0");
		info.setDataState(DataBaseConstant.DEFAULT_FLAG_YES);
		info.setPeopleNo(dto.getIdNo());
		info.setPeopleType(dto.getIdType());
		info.setCreateTime(DateUtil.getCurrentTime());
		info.setUpdateTime(DateUtil.getCurrentTime());
		int returnCount=checkPeopleInfoDAO.updateByPrimaryKey(info);
		if(returnCount==0){
			checkPeopleInfoDAO.insert(info);
		}
	}
	
	
	/**
	 * 门户客户信息补录
	 */
	public void updateCustomerAndCardholder(CustomerDTO customerDTO,CardholderDTO cardholderDTO)
			throws BizServiceException {
		
		Cardholder cardholder = new Cardholder();
		CustomerExample ex=new CustomerExample();
		ex.createCriteria().andCorpCredIdEqualTo(customerDTO.getCorpCredId())
		.andCorpCredTypeEqualTo(customerDTO.getCorpCredType());
		List<Customer> customerResult =customerDAO.selectByExample(ex);
		if (customerResult==null||customerResult.size()==0){
			throw new BizServiceException("该客户不存在！");
		}
		try {
			customerDTO.setFatherEntityId(customerResult.get(0).getFatherEntityId());
			customerDTO.setEntityId(customerResult.get(0).getEntityId());
			//更新客户信息
			Customer  customer = new Customer();
			ReflectionUtil.copyProperties(customerDTO, customer);
			customer.setModifyTime(DateUtil.getCurrentTime());
			cardholderDTO.setEntityId(customer.getEntityId());
			customerDAO.updateByPrimaryKeySelective(customer);
			//更新快递点信息
			EntityDelivery entityDelivery = new EntityDelivery();
			entityDelivery.setDeliveryAddress(customerDTO.getCustomerAddress());
			entityDelivery.setDeliveryName(customerDTO.getCustomerName());
			entityDelivery.setDeliveryPostcode(customerDTO.getCustomerPostcode());
			entityDelivery.setModifyTime(DateUtil.getCurrentTime());
			EntityDeliveryExample entityDeliveryExample = new EntityDeliveryExample();
			entityDeliveryExample.createCriteria().andEntityIdEqualTo(customerResult.get(0).getEntityId())
											.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
											.andDefaultFlagEqualTo(DataBaseConstant.DEFAULT_FLAG_YES);
			deliveryDAO.updateByExampleSelective(entityDelivery, entityDeliveryExample);
			//更新默认快递点联系人信息
			List<EntityDelivery> example = deliveryDAO.selectByExample(entityDeliveryExample);
			if(example!=null&&example.size()>0){
				DeliveryContactExample deliveryContactExample = new DeliveryContactExample();
				deliveryContactExample.createCriteria().andDefaultFlagEqualTo(DataBaseConstant.DEFAULT_FLAG_YES)
																			.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
																			.andDeliveryPointIdEqualTo(example.get(0).getDeliveryId());
				DeliveryContact deliveryContact = new DeliveryContact();
				deliveryContact.setContactPhone(customerDTO.getCustomerTelephone());
				deliveryContact.setModifyTime(DateUtil.getCurrentTime());
				deliveryContact.setDeliveryContact(customerDTO.getCustomerName());
				deliveryContractDAO.updateByExampleSelective(deliveryContact, deliveryContactExample);
			}
			
			// 更新默认联系人信息
			List<ContactDTO> ContactDTOs = contactService.inqueryContact(customerResult.get(0).getEntityId());
			for(ContactDTO contactDTO : ContactDTOs){
				if(contactDTO.getDataState().trim().equals("1")&&contactDTO.getDefaultFlag().trim().equals("1")){
					contactDTO.setContactName(customer.getCustomerName());
					contactDTO.setContactType(customer.getCustomerType());
					contactDTO.setContactGender(cardholderDTO.getCardholderGender());
					contactDTO.setContactMobilePhone(customer.getCustomerTelephone());
					contactDTO.setModifyTime(DateUtil.getCurrentTime());
					contactDTO.setPapersNo(customerDTO.getCorpCredId());
					contactDTO.setPapersType(customerDTO.getCorpCredType());
					contactService.updateContact(contactDTO);
				}
			}
			
			// 更新默认发票地址信息
			List<InvoiceAddressDTO> invoiceAddressDTOs= invoiceAddressService.inquery(customerResult.get(0).getEntityId());
			for(InvoiceAddressDTO invoiceAddressDTO : invoiceAddressDTOs ){
				if(invoiceAddressDTO.getDataState().trim().equals("1")&&invoiceAddressDTO.getDefaultFlag().trim().equals("1")){
					InvoiceAddressDTO invoiceAddress = new InvoiceAddressDTO();
					ReflectionUtil.copyProperties(invoiceAddressDTO, invoiceAddress);
					
					invoiceAddress.setInvoiceAddress(customer.getCustomerAddress());
					invoiceAddress.setAddressPostcode(customer.getCustomerPostcode());
					invoiceAddress.setModifyTime(DateUtil.getCurrentTime());
					invoiceAddress.setInvoiceRecipient(customer.getCustomerName());
					invoiceAddressService.updateByPrimaryKeySelective(invoiceAddress);
				}
			}
			

			// 更新默认发票公司信息
			List<InvoiceCompanyDTO> invoiceCompanys= invoiceCompanyService.inquery(customerResult.get(0).getEntityId());
			for(InvoiceCompanyDTO invoiceCompanyDTO : invoiceCompanys){
				if(invoiceCompanyDTO.getDataState().trim().equals("1")&&invoiceCompanyDTO.getDefaultFlag().trim().equals("1")){
					InvoiceCompany invoiceCompany=new InvoiceCompany();
					invoiceCompanyDTO.setInvoiceCompanyName(customerDTO.getCustomerName());
					invoiceCompanyDTO.setModifyTime(DateUtil.getCurrentTime());
					ReflectionUtil.copyProperties(invoiceCompanyDTO, invoiceCompany);
					invoiceCompanyService.updateByPrimaryKeySelective(invoiceCompany);
				}
				
			}
				
			//人行附加信息
			int count=selectAttachInfoDTO(customerDTO, "00",customerDTO.getEntityId());
			if(count>0){
				updateAttachInfoDTO(customerDTO, "00",customerDTO.getEntityId());
			}else{
				insertAttachInfoDTO(customerDTO, "00",customerDTO.getEntityId());
			}
			/*查询看持卡人是否存在，如果存在则更新为最新的信息，不存在则插入新信息*/
			ReflectionUtil.copyProperties(cardholderDTO, cardholder);
			cardholder.setModifyTime(DateUtil.getCurrentTime());
			cardholderDTO.setDefaultEntityId(customerDTO.getDefaultEntityId());
			CardholderExample  cardholderExample = new CardholderExample();
			cardholderExample.createCriteria().andIdTypeEqualTo(cardholderDTO.getIdType()).andIdNoEqualTo(cardholderDTO.getIdNo()).andDataStateEqualTo("1");
			List<Cardholder> cardholderLists=cardholderDAO.selectByExample(cardholderExample);
			if(cardholderLists.size()>0){
				cardholder.setEntityId(customerResult.get(0).getEntityId());
				cardholder.setCardholderId(cardholderLists.get(0).getCardholderId());
				
				cardholderDAO.updateByExampleSelective(cardholder, cardholderExample);
				//人行附加信息
				//持卡人id
				int counts=selectAttachInfoDTO(customerDTO, "01",cardholderLists.get(0).getCardholderId());
				if(counts>0){
					updateAttachInfoDTO(customerDTO, "01",cardholderLists.get(0).getCardholderId());
				}else{
					insertAttachInfoDTO(customerDTO, "01",cardholderLists.get(0).getCardholderId());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new BizServiceException("更新失败！");
		}
		
		
		
	}
	/**
	 * 修改更新客户
	 */
	public void updateCustomer(CustomerDTO customerDTO)
			throws BizServiceException {
		try {
			Customer customer = new Customer();
			ReflectionUtil.copyProperties(customerDTO, customer);
			customer.setModifyTime(DateUtil.getCurrentTime());
			customer.setModifyUser(customerDTO.getLoginUserId());
			if (null != customer.getCusState()
					&& !"".equals(customer.getCusState())
					&& "0".equals(customer.getCusState())) {
				List<String> ids = new ArrayList<String>();
				ids.add(customer.getEntityId());
				if (isContract(ids) > 0) {
					throw new BizServiceException("当前客户包含有效合同不能修改为无效");
				}
			}
			customerDAO.updateByPrimaryKeySelective(customer);
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新客户失败！");
		}
	}

	public void deleteCustomer(CustomerDTO customerDTO)
			throws BizServiceException {
		try {
			String[] customerIds = customerDTO.getCustomerIds();
			List<String> ids = new ArrayList<String>();
			for (String customerId : customerIds) {
				String[] keyId = customerId.split(",");
				for (int i = 0; i < keyId.length; i++) {
					ids.add(keyId[0]);
				}
				if (isContract(ids) > 0) {
					throw new BizServiceException("所选客户还有合同是可用的，不能删除！");
				}

				Customer customer = new Customer();
				customer.setEntityId(keyId[0]);
				customer.setFatherEntityId(keyId[1]);
				customer.setDataState(DataBaseConstant.DATA_STATE_DELETE);

				customerDAO.updateByPrimaryKeySelective(customer);
			}

		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除客户失败！");
		}
	}

	// 判断是否有可用合同
	public int isContract(List<String> sellerIds) throws BizServiceException {
		int a = 0;
		SellContractExample example = new SellContractExample();
		try {
			example.createCriteria().andContractBuyerIn(sellerIds)
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
					.andContractStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
					.andExpiryDateGreaterThanOrEqualTo(
							DateUtil.date2String(DateUtil.getCurrentDate()));
			example.setOrderByClause("SELL_CONTRACT_ID");
			List<SellContract> contracts = sellerContractDAO
					.selectByExample(example);
			if (contracts != null && contracts.size() != 0) {
				a = contracts.size();
			}
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询是否有可用合同!");
		}
		return a;
	}

	/**
	 * 根据条件获取客户信息
	 */
	public CustomerDTO getCustomerById(String entityId)
			throws BizServiceException {
		CustomerExample example = new CustomerExample();
		example.createCriteria().andEntityIdEqualTo(entityId);
		Customer customer = customerDAO.selectByExample(example).get(0);
		CustomerDTO dto = new CustomerDTO();
		ReflectionUtil.copyProperties(customer, dto);

		return dto;
	}

	public CustomerDTO selectRetailCustomer(String entityId)
			throws BizServiceException {
		CustomerExample example = new CustomerExample();
		example.createCriteria().andFatherEntityIdEqualTo(entityId)
				.andCustomerTypeEqualTo("3");
		Customer customer = customerDAO.selectByExample(example).get(0);
		CustomerDTO dto = new CustomerDTO();
		ReflectionUtil.copyProperties(customer, dto);

		return dto;
	}
	
	//查询客户和部门信息
	public CustomerDTO customerDepartmentInquery(CustomerDTO customerDTO) throws BizServiceException{
		try{
			CustomerExample example = new CustomerExample();
			example.createCriteria().andEntityIdEqualTo(customerDTO.getEntityId());
			Customer customer = new Customer();
			List<Customer> customerList = customerDAO.selectByExample(example);
			if (customerList.size() > 0) {
				customer = customerList.get(0);
			}
			ReflectionUtil.copyProperties(customer, customerDTO);
			String entityId = customerDTO.getEntityId();
			// 关联实体信息	
			customerDTO.setDepartmentList(departmentService.inquery(entityId));
			return customerDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看客户失败！");
		}
	}

	/**
	 * 导入客户信息
	 */
	public void importFile(CustomerDTO customerDTO) throws BizServiceException {
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}

	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}

	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public UserServiceDAO getUserServiceDAO() {
		return userServiceDAO;
	}

	public void setUserServiceDAO(UserServiceDAO userServiceDAO) {
		this.userServiceDAO = userServiceDAO;
	}

	public ContactService getContactService() {
		return contactService;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	public DeliveryPointService getDeliveryPointService() {
		return deliveryPointService;
	}

	public void setDeliveryPointService(
			DeliveryPointService deliveryPointService) {
		this.deliveryPointService = deliveryPointService;
	}

	public DeliveryContactService getDeliveryContactService() {
		return deliveryContactService;
	}

	public void setDeliveryContactService(
			DeliveryContactService deliveryContactService) {
		this.deliveryContactService = deliveryContactService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public InvoiceAddressService getInvoiceAddressService() {
		return invoiceAddressService;
	}

	public void setInvoiceAddressService(
			InvoiceAddressService invoiceAddressService) {
		this.invoiceAddressService = invoiceAddressService;
	}

	public InvoiceCompanyService getInvoiceCompanyService() {
		return invoiceCompanyService;
	}

	public void setInvoiceCompanyService(
			InvoiceCompanyService invoiceCompanyService) {
		this.invoiceCompanyService = invoiceCompanyService;
	}

	public CustomerDTO insertOrderCustomer(CustomerDTO customerDTO)
			throws BizServiceException {
		CustomerDTO dto = this.insertCustomer(customerDTO);
		sellerContractService.insertByMasterplate(dto);
		return dto;
	}

}
