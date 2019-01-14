package com.huateng.univer.seller.seller.biz.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerQueryDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;
import com.allinfinance.univer.seller.seller.dto.SellerQueryDTO;
import com.allinfinance.univer.seller.seller.dto.TreeNodeDTO;
import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.ConsumerDAO;
import com.huateng.framework.ibatis.dao.CustomerDAO;
import com.huateng.framework.ibatis.dao.EntityContactDAO;
import com.huateng.framework.ibatis.dao.EntityDeliveryDAO;
import com.huateng.framework.ibatis.dao.EntityDepartmentDAO;
import com.huateng.framework.ibatis.dao.EntityInvoiceAddressDAO;
import com.huateng.framework.ibatis.dao.InvoiceCompanyDAO;
import com.huateng.framework.ibatis.dao.IssueResourceDAO;
import com.huateng.framework.ibatis.dao.IssuerDAO;
import com.huateng.framework.ibatis.dao.SellContractDAO;
import com.huateng.framework.ibatis.dao.SellerDAO;
import com.huateng.framework.ibatis.model.Consumer;
import com.huateng.framework.ibatis.model.ConsumerKey;
import com.huateng.framework.ibatis.model.Customer;
import com.huateng.framework.ibatis.model.EntityContact;
import com.huateng.framework.ibatis.model.EntityContactExample;
import com.huateng.framework.ibatis.model.EntityDelivery;
import com.huateng.framework.ibatis.model.EntityDeliveryExample;
import com.huateng.framework.ibatis.model.EntityDepartment;
import com.huateng.framework.ibatis.model.EntityDepartmentExample;
import com.huateng.framework.ibatis.model.EntityInvoiceAddress;
import com.huateng.framework.ibatis.model.EntityInvoiceAddressExample;
import com.huateng.framework.ibatis.model.InvoiceCompany;
import com.huateng.framework.ibatis.model.InvoiceCompanyExample;
import com.huateng.framework.ibatis.model.IssueResource;
import com.huateng.framework.ibatis.model.IssueResourceExample;
import com.huateng.framework.ibatis.model.Issuer;
import com.huateng.framework.ibatis.model.IssuerKey;
import com.huateng.framework.ibatis.model.SellContract;
import com.huateng.framework.ibatis.model.Seller;
import com.huateng.framework.ibatis.model.SellerExample;
import com.huateng.framework.ibatis.model.SellerKey;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.ibatis.model.UserExample;
import com.huateng.framework.ibatis.model.UserExample.Criteria;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.entitybaseinfo.bank.biz.service.BankService;
import com.huateng.univer.entitybaseinfo.contact.biz.service.ContactService;
import com.huateng.univer.entitybaseinfo.delivarypoint.biz.service.DeliveryPointService;
import com.huateng.univer.entitybaseinfo.deliverycontact.biz.service.DeliveryContactService;
import com.huateng.univer.entitybaseinfo.department.biz.service.DepartmentService;
import com.huateng.univer.entitybaseinfo.invoiceaddress.biz.service.InvoiceAddressService;
import com.huateng.univer.entitybaseinfo.invoicecompany.biz.service.InvoiceCompanyService;
import com.huateng.univer.seller.seller.biz.service.SellerService;
import com.huateng.univer.servicefeerule.biz.service.CaclInfService;
import com.huateng.univer.settleperiodrule.biz.service.SettlePeriodRuleService;
import com.huateng.univer.system.dictinfo.biz.service.EntityDictInfoService;
import com.huateng.univer.system.role.biz.service.RoleService;
import com.huateng.univer.system.role.integration.dao.RoleServiceDAO;
import com.huateng.univer.system.sysparam.biz.service.EntitySystemParameterService;
import com.huateng.univer.system.user.biz.service.UserService;
import com.huateng.univer.system.user.integration.dao.UserServiceDAO;

/**
 * 营销机构管理service
 * 
 * @author xxl
 * 
 */
public class SellerServiceImpl implements SellerService {

	Logger logger = Logger.getLogger(this.getClass());
	private SellerDAO sellerDAO;
	private PageQueryDAO pageQueryDAO;
	private CommonsDAO commonsDAO;
	private UserServiceDAO userServiceDAO;
	private UserService userService;
	// 实体service
	private ContactService contactService;
	private SellContractDAO sellerContractDAO;
	private DeliveryPointService deliveryPointService;
	private DeliveryContactService deliveryContactService;
	private DepartmentService departmentService;
	private InvoiceAddressService invoiceAddressService;
	private InvoiceCompanyService invoiceCompanyService;
	private EntityDictInfoService entityDictInfoService;
	private EntitySystemParameterService entitySystemParameterService;
	private IssuerDAO issuerDAO;
	private ConsumerDAO consumerDAO;
	private EntityContactDAO contractDAO;
	private EntityDeliveryDAO deliveryDAO;
	private InvoiceCompanyDAO invoiceCompanyDAO;
	private EntityInvoiceAddressDAO invoiceAddressDAO;
	private EntityDepartmentDAO departmentDAO;
	private IssueResourceDAO issueResourceDAO;
	private IssuerQueryDTO issuerQueryDTO = new IssuerQueryDTO();
	private RoleService roleService;
	private RoleServiceDAO roleDAO;
	private BankService bankService;
	private CustomerDAO customerDAO;
	private CaclInfService caclInfService;
	private SettlePeriodRuleService settlePeriodRuleService;

	public BankService getBankService() {
		return bankService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public RoleServiceDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleServiceDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public IssueResourceDAO getIssueResourceDAO() {
		return issueResourceDAO;
	}

	public void setIssueResourceDAO(IssueResourceDAO issueResourceDAO) {
		this.issueResourceDAO = issueResourceDAO;
	}

	public EntityContactDAO getContractDAO() {
		return contractDAO;
	}

	public void setContractDAO(EntityContactDAO contractDAO) {
		this.contractDAO = contractDAO;
	}

	public EntityDeliveryDAO getDeliveryDAO() {
		return deliveryDAO;
	}

	public void setDeliveryDAO(EntityDeliveryDAO deliveryDAO) {
		this.deliveryDAO = deliveryDAO;
	}

	public InvoiceCompanyDAO getInvoiceCompanyDAO() {
		return invoiceCompanyDAO;
	}

	public void setInvoiceCompanyDAO(InvoiceCompanyDAO invoiceCompanyDAO) {
		this.invoiceCompanyDAO = invoiceCompanyDAO;
	}

	public EntityInvoiceAddressDAO getInvoiceAddressDAO() {
		return invoiceAddressDAO;
	}

	public void setInvoiceAddressDAO(EntityInvoiceAddressDAO invoiceAddressDAO) {
		this.invoiceAddressDAO = invoiceAddressDAO;
	}

	public EntityDepartmentDAO getDepartmentDAO() {
		return departmentDAO;
	}

	public void setDepartmentDAO(EntityDepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	public PageDataDTO inquery(SellerQueryDTO sellerQueryDTO)
			throws BizServiceException {
		try {
			PageDataDTO pageDataDTO = pageQueryDAO.query("SELLER.selectSeller",
					sellerQueryDTO);
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询营销机构失败！");
		}
	}

	public PageDataDTO inqueryForSelf(SellerQueryDTO sellerQueryDTO)
			throws BizServiceException {
		try {
			PageDataDTO pageDataDTO = pageQueryDAO.query(
					"SELLER.selectSellerForSelf", sellerQueryDTO);
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询营销机构失败！");
		}
	}

	// 查询包含自身的营销机构
	public PageDataDTO inqueryorSelf(SellerQueryDTO sellerQueryDTO)
			throws BizServiceException {
		try {
			PageDataDTO pageDataDTO = pageQueryDAO.query("SELLER.sellerSelect",
					sellerQueryDTO);
			return pageDataDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询营销机构失败！");
		}
	}

	public SellerDTO viewSeller(SellerDTO sellerDTO) throws BizServiceException {
		try {
			SellerKey key = new SellerKey();
			key.setEntityId(sellerDTO.getEntityId());
			key.setFatherEntityId(sellerDTO.getFatherEntityId());

			Seller seller = sellerDAO.selectByPrimaryKey(key);
			ReflectionUtil.copyProperties(seller, sellerDTO);
			sellerDTO = initSeller(sellerDTO);

			String entityId = sellerDTO.getEntityId();

			// 关联实体信息
			sellerDTO.setContractList(contactService.inqueryContact(entityId));
			sellerDTO.setDeliveryPointList(deliveryPointService
					.inquery(entityId));
			sellerDTO.setDepartmentList(departmentService.inquery(entityId));
			sellerDTO.setInvoiceAddressList(invoiceAddressService
					.inquery(entityId));
			sellerDTO.setInvoiceCompanyList(invoiceCompanyService
					.inquery(entityId));
			sellerDTO.setBankList(bankService.inquery(entityId, "1"));
			UserDTO userDTO = userService.getUserById(seller.getUserId());
			if (null != userDTO) {
				sellerDTO.setUserDTO(userDTO);
				sellerDTO.setUserId(userDTO.getUserId());
				sellerDTO.setUserName(userDTO.getUserName());
				sellerDTO.setUserEmail(userDTO.getEmail());

				// 得到此机构权限管理
				List<String> flist = new ArrayList<String>();
				flist.add("0");
				flist.add("3");
				userDTO.setFunctionRoleId(flist);
				userDTO.setIsSaleFlage("2");
				userDTO.setEntityId(sellerDTO.getDefaultEntityId());
				List<ResourceDTO> resourceDTOlist = roleService
						.selectIssueResource(userDTO);
				// 获取已设置的权限
				userDTO.setEntityId(sellerDTO.getEntityId());
				userDTO.setIsSaleFlage("2");
				List<ResourceDTO> nresourceDTOlist = roleDAO
						.getIssueResourceDTOs(userDTO);
				sellerDTO.setResourceDTOs(resourceDTOlist);
				sellerDTO.setNresourceDTOs(nresourceDTOlist);
			}
			return sellerDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看营销机构失败！");
		}
	}

	public SellerDTO initSeller(SellerDTO sellerDTO) throws BizServiceException {
		try {
			UserExample example = new UserExample();
			Criteria criteria = example.createCriteria().andIsSaleFlageEqualTo(
					"1")
					.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
			if (null != sellerDTO.getDefaultEntityId()
					&& !"".equals(sellerDTO.getDefaultEntityId())) {
				criteria.andEntityIdEqualTo(sellerDTO.getDefaultEntityId());
			}			
			List<User> userList = userServiceDAO.selectByExample(example);
			List<UserDTO> userDTOList = new ArrayList<UserDTO>();
			for (User user : userList) {
				UserDTO userDTO = new UserDTO();
				ReflectionUtil.copyProperties(user, userDTO);
				userDTOList.add(userDTO);
			}

			sellerDTO.setSalesmanList(userDTOList);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("初始化营销机构信息失败！");
		}
		return sellerDTO;
	}

	public SellerDTO insertSeller(SellerDTO sellerDTO)
			throws BizServiceException {
		try {/*
			 * SellerExample sellerExample = new SellerExample();
			 * sellerExample.createCriteria().andSellerNameEqualTo(
			 * sellerDTO.getSellerName
			 * ()).andFatherEntityIdEqualTo(sellerDTO.getDefaultEntityId());
			 * List<Seller> sellers = sellerDAO.selectByExample(sellerExample);
			 * if (sellers != null && sellers.size() > 0) { throw new
			 * BizServiceException(" 营销机构名称:" + sellerDTO.getSellerName() +
			 * "已存在!"); }
			 */
			Seller seller = new Seller();
			ReflectionUtil.copyProperties(sellerDTO, seller);
			User loginUser = userServiceDAO.selectByPrimaryKey(sellerDTO
					.getLoginUserId());
			String operateUser = sellerDTO.getLoginUserId();
			String entityId = sellerDTO.getEntityId();
//			if (entityId == null || "".equals(entityId.trim())) {
//				throw new BizServiceException("营销机构号为空");
//			}else{
//				entityId = entityId.trim();
//			}
			if (entityId == null || "".equals(entityId.trim())) {
				entityId = commonsDAO.getNextValueOfSequence("TB_ENTITY");
			
			SellerKey key = new SellerKey();
			key.setEntityId(entityId);
			//String fatherEntityId = sellerDTO.getFatherEntityId();
			//key.setFatherEntityId(fatherEntityId);
			Seller sellerOld = sellerDAO.selectByPrimaryKey(key);
			if(null!= sellerOld){
				if(DataBaseConstant.DATA_STATE_NORMAL.equals(sellerOld.getDataState())){
					throw new BizServiceException("此营销机构号" + entityId + "已经存在");
				}
			}
			
			
			// 默认插入营销机构部门信息
			DepartmentDTO entityDepartment = new DepartmentDTO();
			entityDepartment.setEntityId(entityId);
			entityDepartment.setDepartmentName(seller.getSellerName());
			entityDepartment
					.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			entityDepartment.setCreateUser(operateUser);
			entityDepartment.setModifyUser(operateUser);
			departmentService.insert(entityDepartment);

			// 默认插入营销机构联系人信息
			ContactDTO entityContact = new ContactDTO();
			entityContact.setEntityId(entityId);
			entityContact.setContactName(seller.getSellerName());
			entityContact.setContactType("1");
			entityContact.setContactGender("1");
//			entityContact.setContactTelephone(seller.getSellerTelephone());
			entityContact.setContactMobilePhone(seller.getSellerTelephone());
			entityContact.setCreateUser(operateUser);
			entityContact.setModifyUser(operateUser);
			entityContact.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			entityContact.setValidityFlag("1");
			entityContact.setPapersNo("");
			entityContact.setPapersType("1");
			contactService.insert(entityContact);

			String customerEntityId = commonsDAO.getNextValueOfSequence("TB_ENTITY");
			// 添加散户客户
			Customer customer = new Customer();
			customer.setEntityId(customerEntityId);
			customer.setFatherEntityId(entityId);
			customer.setCustomerName("散户");
			customer.setCusState("2");
			customer.setCustomerType("3");
			customer.setCustomerAddress("00");
			customer.setCustomerFax("00");
			customer.setCustomerPostcode("000000");
			customer.setCustomerTelephone("0000");
			customer.setSalesmanId(operateUser);
			customer.setCreateTime(DateUtil.getCurrentTime());
			customer.setCreateUser(operateUser);
			customer.setModifyTime(DateUtil.getCurrentTime());
			customer.setModifyUser(operateUser);
			customer.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			customerDAO.insert(customer);
			
			//添加客户合同模板
			SellContract sellContract=new SellContract();
			sellContract.setSellContractId(commonsDAO
					.getNextValueOfSequence("TB_SELL_CONTRACT"));
			sellContract.setContractSeller(entityId);
			sellContract.setContractBuyer("0");
			sellContract.setDeliveryFee("0");
			sellContract.setContractState(DataBaseConstant.DATA_STATE_NORMAL);
			sellContract.setExpiryDate("29991231");
			sellContract.setCreateTime(DateUtil.getCurrentTime());
			sellContract.setCreateUser(operateUser);
			sellContract.setModifyTime(DateUtil.getCurrentTime());
			sellContract.setModifyUser(operateUser);
			sellContract.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			sellContract.setContractType(DataBaseConstant.CONTRACT_TEMPLATE);
			sellerContractDAO.insert(sellContract);
			
			// 添加初始计算规则
			caclInfService.insertInit(entityId);

			// 默认插入营销机构快递点信息
			DeliveryPointDTO entityDelivery = new DeliveryPointDTO();
			entityDelivery.setEntityId(entityId);
			entityDelivery.setDeliveryName(seller.getSellerName());
			entityDelivery.setDeliveryPostcode(seller.getSellerPostcode());
			entityDelivery.setDeliveryAddress(seller.getSellerAddress());
			entityDelivery.setCreateUser(operateUser);
			entityDelivery.setModifyUser(operateUser);
			entityDelivery
					.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			entityDelivery = deliveryPointService.insert(entityDelivery);

			// 默认营销机构快递点联系人（依赖于快递点）
			DeliveryRecipientDTO deliveryContact = new DeliveryRecipientDTO();
			deliveryContact.setDeliveryPointId(entityDelivery
					.getDeliveryId());
			deliveryContact.setDeliveryContact(entityDelivery
					.getDeliveryName());
			deliveryContact.setContactPhone(seller.getSellerTelephone());
			deliveryContact.setCreateUser(operateUser);
			deliveryContact.setModifyUser(operateUser);
			deliveryContact
					.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			deliveryContactService.insert(deliveryContact);

			// 默认插入营销机构发票地址信息
			InvoiceAddressDTO invoiceAddress = new InvoiceAddressDTO();
			invoiceAddress.setEntityId(entityId);
			invoiceAddress.setInvoiceAddress(seller.getSellerAddress());
			invoiceAddress.setAddressPostcode(seller.getSellerPostcode());
			invoiceAddress.setCreateUser(operateUser);
			invoiceAddress.setModifyUser(operateUser);
			invoiceAddress.setInvoiceRecipient(seller.getSellerName());
			invoiceAddress
					.setDeliveryOption(DataBaseConstant.DELIVERY_OPERATION_SEND);
			invoiceAddress
					.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			invoiceAddressService.insert(invoiceAddress);

			// 默认插入营销机构发票公司信息
			InvoiceCompanyDTO invoiceCompanyDTO = new InvoiceCompanyDTO();
			invoiceCompanyDTO.setEntityId(entityId);
			invoiceCompanyDTO.setInvoiceCompanyName(seller.getSellerName());
			invoiceCompanyDTO.setCreateUser(operateUser);
			invoiceCompanyDTO.setModifyUser(operateUser);
			invoiceCompanyDTO
					.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			invoiceCompanyService.insert(invoiceCompanyDTO);
			
			//插入散户一些基础信息
			/**此版本散户客户屏蔽*/
			// 默认插入散户部门信息
			DepartmentDTO entityDepart = new DepartmentDTO();
			entityDepart.setEntityId(customerEntityId);
			entityDepart.setDepartmentName(customer.getCustomerName());
			entityDepart.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			entityDepart.setCreateUser(operateUser);
			entityDepart.setModifyUser(operateUser);
			departmentService.insert(entityDepart);

			// 默认插入散户联系人信息
			ContactDTO entityCont = new ContactDTO();
			entityCont.setEntityId(customerEntityId);
			entityCont.setContactName(customer.getCustomerName());
			entityCont.setContactType("1");
			entityCont.setContactGender("1");
//			entityCont.setContactTelephone(customer.getCustomerTelephone());
			entityCont.setContactMobilePhone(seller.getSellerTelephone());
			entityCont.setCreateUser(operateUser);
			entityCont.setModifyUser(operateUser);
			entityCont.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			entityCont.setValidityFlag("1");
			entityCont.setPapersNo("1");
			entityCont.setPapersType("1");
			contactService.insert(entityCont);

			// 默认插入散户快递点信息
			DeliveryPointDTO entityDel = new DeliveryPointDTO();
			entityDel.setEntityId(customerEntityId);
			entityDel.setDeliveryName(customer.getCustomerName());
			entityDel.setDeliveryPostcode(customer.getCustomerPostcode());
			entityDel.setDeliveryAddress(customer.getCustomerAddress());
			entityDel.setCreateUser(operateUser);
			entityDel.setModifyUser(operateUser);
			entityDel.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			entityDel = deliveryPointService.insert(entityDel);

			// 默认散户快递点联系人（依赖于快递点）
			DeliveryRecipientDTO deliveryCont = new DeliveryRecipientDTO();
			deliveryCont.setDeliveryPointId(entityDel.getDeliveryId());
			deliveryCont
					.setDeliveryContact(entityDel.getDeliveryName());
			deliveryCont.setContactPhone(customer.getCustomerTelephone());
			deliveryCont.setCreateUser(operateUser);
			deliveryCont.setModifyUser(operateUser);
			deliveryCont.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			deliveryContactService.insert(deliveryCont);

			// 默认散户插入发票地址信息
			InvoiceAddressDTO invoiceAddr = new InvoiceAddressDTO();
			invoiceAddr.setEntityId(customerEntityId);
			invoiceAddr.setInvoiceAddress(customer.getCustomerAddress());
			invoiceAddr.setAddressPostcode(customer.getCustomerPostcode());
			invoiceAddr.setCreateUser(operateUser);
			invoiceAddr.setModifyUser(operateUser);
			invoiceAddr.setInvoiceRecipient(customer.getCustomerName());
			invoiceAddr
					.setDeliveryOption(DataBaseConstant.DELIVERY_OPERATION_SEND);
			invoiceAddr.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			invoiceAddressService.insert(invoiceAddr);

			// 默认插入散户发票公司信息
			InvoiceCompanyDTO invoiceCompany = new InvoiceCompanyDTO();
			invoiceCompany.setEntityId(customerEntityId);
			invoiceCompany.setInvoiceCompanyName(customer.getCustomerName());
			invoiceCompany.setCreateUser(operateUser);
			invoiceCompany.setModifyUser(operateUser);
			invoiceCompany.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			invoiceCompanyService.insert(invoiceCompany);
			
			
			
			
			// 添加实体数据字典
			entityDictInfoService.insertEntityDictInfo(entityId);
			// 添加 周期规则初始数据
			settlePeriodRuleService.initInsert(entityId, operateUser);

			// 添加实体系统参数
//			entitySystemParameterService.insertEntitySystemParameter(
//					entityId, loginUser.getEntityId(), sellerDTO
//							.getLoginUserId());
//			}

			String userId = commonsDAO.getNextValueOfSequence("TB_USER");
			seller.setEntityId(entityId);
			seller.setUserId(userId);
			seller.setFatherEntityId(sellerDTO.getFatherEntityId());
			seller.setCreateTime(DateUtil.getCurrentTime());
			seller.setCreateUser(operateUser);
			seller.setModifyTime(DateUtil.getCurrentTime());
			seller.setModifyUser(operateUser);
			seller.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			seller.setFunctionRoleId(sellerDTO.getFunctionRoleId());

			sellerDAO.insert(seller);
			userService.insertUserForEntity(entityId, userId, sellerDTO
					.getUserName(), sellerDTO.getUserPassword(), sellerDTO
					.getUserEmail(), operateUser);
			ReflectionUtil.copyProperties(seller, sellerDTO);

			// 添加权限控制
			sellerDTO.setCreateUser(operateUser);
			this.insertResources(sellerDTO);
		}
		} catch (BizServiceException ex) {
			throw ex;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加营销机构失败！");
		}
		return sellerDTO;
	}

	/**
	 * dawn
	 * 
	 * @param sellerDTO
	 * @return
	 * @throws BizServiceException
	 */
	public SellerDTO getSellerByEntityId(SellerDTO sellerDTO)
			throws BizServiceException {
		try {
			SellerExample sellerExample = new SellerExample();
			Seller seller = new Seller();
			sellerExample.createCriteria().andDataStateEqualTo(
					DataBaseConstant.DATA_STATE_NORMAL).andEntityIdEqualTo(
					sellerDTO.getEntityId());

			List<Seller> sellerList = sellerDAO.selectByExample(sellerExample);

			if (sellerList.size() > 0) {
				seller = sellerList.get(0);
			}
			ReflectionUtil.copyProperties(seller, sellerDTO);
			String entityId = sellerDTO.getEntityId();
			// 关联实体信息
			sellerDTO.setContractList(contactService.inqueryContact(entityId));
			sellerDTO.setDeliveryPointList(deliveryPointService
					.inquery(entityId));
			sellerDTO.setDepartmentList(departmentService.inquery(entityId));
			sellerDTO.setInvoiceAddressList(invoiceAddressService
					.inquery(entityId));
			sellerDTO.setInvoiceCompanyList(invoiceCompanyService
					.inquery(entityId));
			return sellerDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("获取实体信息失败!");
		}
	}

	public void updateSeller(SellerDTO sellerDTO) throws BizServiceException {
		try {/*
			 * SellerExample sellerExample = new SellerExample();
			 * sellerExample.createCriteria().andSellerNameEqualTo(
			 * sellerDTO.getSellerName
			 * ()).andFatherEntityIdEqualTo(sellerDTO.getDefaultEntityId())
			 * .andEntityIdNotEqualTo(sellerDTO.getEntityId()); List<Seller>
			 * sellers = sellerDAO.selectByExample(sellerExample); if (sellers
			 * != null && sellers.size() > 0) { throw new
			 * BizServiceException(" 营销机构名称:" + sellerDTO.getSellerName() +
			 * "已存在!"); }
			 */
			Seller seller = new Seller();
			ReflectionUtil.copyProperties(sellerDTO, seller);
			seller.setModifyTime(DateUtil.getCurrentTime());
			seller.setModifyUser(sellerDTO.getLoginUserId());
			seller.setFatherEntityId(sellerDTO.getNewFatherEntityId());
			if ((null != seller.getSellerState() || !"".equals(seller
					.getSellerState()))
					&& "0".equals(seller.getSellerState())) {
				List<String> ids = new ArrayList<String>();
				ids.add(seller.getEntityId());
				int i = commonsDAO.isDeleteSeller(sellerDTO);
				if (i == 0) {
					throw new BizServiceException("不能修改为无效状态");
				}
				/**
				 * 删除用户
				 */
				UserExample userExample = new UserExample();
				User user = new User();
				user.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				userExample.createCriteria().andEntityIdEqualTo(
						sellerDTO.getEntityId()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL);
				userServiceDAO.updateByExampleSelective(user, userExample);
			} else if ((DataBaseConstant.DATA_STATE_NORMAL).equals(seller
					.getSellerState())) {
				/**
				 * DATA_STATE_NORMAL 恢复用户
				 */
				UserExample userExample = new UserExample();
				User user = new User();
				user.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
				userExample.createCriteria().andEntityIdEqualTo(
						sellerDTO.getEntityId());
				userServiceDAO.updateByExampleSelective(user, userExample);
			}
			SellerExample sellerExample = new SellerExample();
			sellerExample.createCriteria().andEntityIdEqualTo(sellerDTO.getEntityId());
			sellerDAO.updateByExampleSelective(seller, sellerExample);
			if (null != sellerDTO.getUserId()
					&& !"".equals(sellerDTO.getUserId())) {
				User user = new User();
				user.setUserId(sellerDTO.getUserId());
				user.setUserName(sellerDTO.getUserName());
				user.setEmail(sellerDTO.getUserEmail());
				userServiceDAO.updateByPrimaryKeySelective(user);
				// 权限控制
				sellerDTO.setCreateUser(sellerDTO.getLoginUserId());

				this.deleteResource(sellerDTO);
				this.insertResources(sellerDTO);
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新营销机构失败！");
		}
	}

	public void delSeller(SellerDTO sellerDTO) throws BizServiceException {
		try {
			String[] sellerIds = sellerDTO.getSellerIds();
			for (String sellerId : sellerIds) {
				String[] keyId = sellerId.split(",");
				sellerDTO.setEntityId(keyId[0]);
				sellerDTO.setFatherEntityId(keyId[1]);
				// for(int i=0;i<keyId.length;i++){
				// ids.add(keyId[i]);
				// }
				int i = commonsDAO.isDeleteSeller(sellerDTO);
				if (i == 0) {
					throw new BizServiceException("删除失败");
				}
				// if(isContract(ids)>0){
				// throw new BizServiceException("所选营销机构还有合同是可用的，不能删除！");
				// }
				/**
				 * 
				 * 删除用户
				 */
				UserExample userExample = new UserExample();
				User user = new User();
				user.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				userExample.createCriteria().andEntityIdEqualTo(
						sellerDTO.getEntityId()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL);
				userServiceDAO.updateByExampleSelective(user, userExample);

				/**
				 * 删除关联的联系人信息
				 */
				EntityContactExample ex = new EntityContactExample();
				EntityContact contact = new EntityContact();
				contact.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				ex
						.createCriteria()
						.andEntityIdEqualTo(sellerDTO.getEntityId())
						.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
				contractDAO.updateByExampleSelective(contact, ex);
				/**
				 * 删除关联的快递点信息
				 */
				EntityDeliveryExample dex = new EntityDeliveryExample();
				EntityDelivery delivery = new EntityDelivery();
				delivery.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				dex
						.createCriteria()
						.andEntityIdEqualTo(sellerDTO.getEntityId())
						.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
				deliveryDAO.updateByExampleSelective(delivery, dex);
				/**
				 * 删除关联的发票公司信息
				 */
				InvoiceCompanyExample cex = new InvoiceCompanyExample();
				InvoiceCompany invc = new InvoiceCompany();
				invc.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				cex
						.createCriteria()
						.andEntityIdEqualTo(sellerDTO.getEntityId())
						.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
				invoiceCompanyDAO.updateByExampleSelective(invc, cex);
				/**
				 * 删除关联的发票公司地址信息
				 */
				EntityInvoiceAddressExample aex = new EntityInvoiceAddressExample();
				EntityInvoiceAddress Address = new EntityInvoiceAddress();
				Address.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				aex
						.createCriteria()
						.andEntityIdEqualTo(sellerDTO.getEntityId())
						.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
				invoiceAddressDAO.updateByExampleSelective(Address, aex);
				/**
				 * 删除关联的部门信息
				 */
				EntityDepartmentExample depex = new EntityDepartmentExample();
				EntityDepartment department = new EntityDepartment();
				department.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				depex.createCriteria().andEntityIdEqualTo(
						sellerDTO.getEntityId()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL);
				departmentDAO.updateByExampleSelective(department, depex);

				Seller seller = new Seller();
				seller.setEntityId(keyId[0]);
				seller.setFatherEntityId(keyId[1]);
				seller.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				seller.setModifyTime(DateUtil.getCurrentTime());
				seller.setModifyUser(seller.getModifyUser());
				sellerDAO.updateByPrimaryKeySelective(seller);
				// sellerDAO.deleteByPrimaryKey(seller);

				// this.deleteResource(sellerDTO);
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("删除营销机构失败！");
		}
	}

	//判断是否有可用合同
//	private int isContract(List<String> sellerIds) throws BizServiceException{
//		int a=0;
//		SellContractExample example=new SellContractExample();
//		try {
//			example.createCriteria().andContractBuyerIn(sellerIds).andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL).andExpiryDateGreaterThanOrEqualTo(DateUtil.date2String(DateUtil.getCurrentDate()));
//			example.setOrderByClause("SELL_CONTRACT_ID");
//			List<SellContract> contracts=sellerContractDAO.selectByExample(example);
//			if(contracts!=null && contracts.size()!=0){
//				a=contracts.size();
//			}
//		} catch (Exception e) {
//			this.logger.error(e.getMessage());
//			throw new BizServiceException("查询是否有可用合同!");
//		}
//		return a;
//	}
	
	public SellerDAO getSellerDAO() {
		return sellerDAO;
	}

	public void setSellerDAO(SellerDAO sellerDAO) {
		this.sellerDAO = sellerDAO;
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

	public EntityDictInfoService getEntityDictInfoService() {
		return entityDictInfoService;
	}

	public void setEntityDictInfoService(
			EntityDictInfoService entityDictInfoService) {
		this.entityDictInfoService = entityDictInfoService;
	}

	public EntitySystemParameterService getEntitySystemParameterService() {
		return entitySystemParameterService;
	}

	public void setEntitySystemParameterService(
			EntitySystemParameterService entitySystemParameterService) {
		this.entitySystemParameterService = entitySystemParameterService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public SellerDTO configEntityId(SellerQueryDTO sellerQueryDTO)
			throws BizServiceException {
		SellerDTO sellerDTO = new SellerDTO();
		try {
			IssuerKey key1 = new IssuerKey();
			key1.setEntityId(sellerQueryDTO.getEntityId());
			key1.setFatherEntityId(sellerQueryDTO.getFatherEntityId());
			Issuer issuer = issuerDAO.selectByPrimaryKey(key1);
			if (issuer != null) {
				sellerDTO.setEntityId(issuer.getEntityId());
				sellerDTO.setFatherEntityId(issuer.getFatherEntityId());
				sellerDTO.setSellerName(issuer.getIssuerName());
				sellerDTO.setSellerEnglishName(issuer.getIssuerEnglishName());
				sellerDTO.setSellerAddress(issuer.getIssuerAddress());
				sellerDTO.setSellerEnglishAddress(issuer
						.getIssuerEnglishAddress());
				sellerDTO.setSellerFax(issuer.getIssuerFax());
				sellerDTO.setSellerPostcode(issuer.getIssuerPostcode());
				sellerDTO.setSellerTelephone(issuer.getIssuerTelephone());
				/**
				 * Annote by Yifeng.Shi @2011-6-8 String userId =
				 * issuer.getUserId(); User user =
				 * userServiceDAO.selectByPrimaryKey(userId);
				 * sellerDTO.setUserId(user.getUserId());
				 * sellerDTO.setUserName(user.getUserName());
				 * sellerDTO.setUserEmail(user.getEmail());
				 */
				return sellerDTO;

			}
			ConsumerKey key = new ConsumerKey();
			key.setEntityId(sellerQueryDTO.getEntityId());
			key.setFatherEntityId(sellerQueryDTO.getFatherEntityId());
			Consumer consumer = consumerDAO.selectByPrimaryKey(key);
			if (consumer != null) {
				sellerDTO.setEntityId(consumer.getEntityId());
				sellerDTO.setFatherEntityId(consumer.getFatherEntityId());
				sellerDTO.setSellerName(consumer.getConsumerName());
				sellerDTO.setSellerEnglishName(consumer
						.getConsumerEnglishName());
				sellerDTO.setSellerAddress(consumer.getConsumerAddress());
				sellerDTO.setSellerEnglishAddress(consumer
						.getConsumerEnglishAddress());
				sellerDTO.setSellerFax(consumer.getConsumerFax());
				sellerDTO.setSellerPostcode(consumer.getConsumerPostcode());
				sellerDTO.setSellerTelephone(consumer.getConsumerTelephone());
				String userId = consumer.getUserId();
				User user = userServiceDAO.selectByPrimaryKey(userId);
				sellerDTO.setUserId(userId);
				sellerDTO.setUserName(user.getUserName());
				sellerDTO.setUserEmail(user.getEmail());
				return sellerDTO;
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("选择实体失败！");
		}
		return sellerDTO;
	}

	public PageDataDTO queryEntityId(SellerQueryDTO sellerQueryDTO)
			throws BizServiceException {
		PageDataDTO pageDataDTO = new PageDataDTO();
		try {
			if (sellerQueryDTO.getEntityFlag() == null
					|| sellerQueryDTO.getEntityFlag().trim().equals("")) {
				pageDataDTO = pageQueryDAO.query("SELLER.selectEntityId",
						sellerQueryDTO);
			} else if ("1".equals(sellerQueryDTO.getEntityFlag())) {
				pageDataDTO = pageQueryDAO.query(
						"SELLER.selectEntityIdFromIssuer", sellerQueryDTO);
			} else {
				pageDataDTO = pageQueryDAO.query(
						"SELLER.selectEntityIdFromConsumer", sellerQueryDTO);
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询实体失败！");
		}
		return pageDataDTO;
	}

	public PageDataDTO selectOrder(SellerQueryDTO sellerQueryDTO) throws BizServiceException {
		try {
			return pageQueryDAO.query("SELLER.selectOrder", sellerQueryDTO);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询发行机构失败！");
		}
	}
	
	/**
     * 根据当前的机构号查询下级营销机构，建树型菜单
     */
    @SuppressWarnings("unchecked")
    public List<TreeNodeDTO> buildTree(String entityId) throws BizServiceException {
        try {
            return (List<TreeNodeDTO>)commonsDAO.queryForList("SELLER.queryNextEntity", entityId);
        
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询下级营销机构失败!");
        }  
    }
    
    /**
     * 
     *判断选择的功能列表是否是有采购或限制的条件权限ID
     * @param resourceId
     * @return
     * @throws BizServiceException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("unchecked")
    private boolean isAuthorityResourceId(String resourceId, String authorityId ) throws BizServiceException {
        try {
            List<ResourceDTO> pResourceList = (List<ResourceDTO>)commonsDAO.queryForList(
                    "SELLER.nextResourceIds", authorityId);
            if(null != pResourceList && pResourceList.size() > 0) {
                for(ResourceDTO dto : pResourceList) {
                    if(resourceId.equals(dto.getResourceId())) {
                        return true;
                    }
                }
            }
           return false;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new BizServiceException("查询功能列表失败!");
        }
        
    }

	public IssuerDAO getIssuerDAO() {
		return issuerDAO;
	}

	public void setIssuerDAO(IssuerDAO issuerDAO) {
		this.issuerDAO = issuerDAO;
	}

	public ConsumerDAO getConsumerDAO() {
		return consumerDAO;
	}

	public void setConsumerDAO(ConsumerDAO consumerDAO) {
		this.consumerDAO = consumerDAO;
	}

	public SellContractDAO getSellerContractDAO() {
		return sellerContractDAO;
	}

	public void setSellerContractDAO(SellContractDAO sellerContractDAO) {
		this.sellerContractDAO = sellerContractDAO;
	}

	public void insertResources(SellerDTO sellerDTO) throws BizServiceException {
		List<String> issueResources = Arrays.asList(sellerDTO.getResourceIds()
				.split(","));
		List<IssueResource> issueResourceList = new ArrayList<IssueResource>();
		for (String resource : issueResources) {
		    if(!DataBaseConstant.MANAGEMENT_RESOURCE_ID.equals(sellerDTO.getDefaultEntityId())) {
		        if(!sellerDTO.getEntityId().equals(sellerDTO.getFatherEntityId())) {
        		    //判断是否有采购权限 410100
        		    if(isAuthorityResourceId(resource, DataBaseConstant.PURCHASE_RESOURCE_ID)) {
        		        throw new BizServiceException("二级、三级营销机构没有采购权限！");
        		    } 
        		    //判断是否有系统参数管理权限 60300
                    if(isAuthorityResourceId(resource, DataBaseConstant.SYSTEM_PARAMETER_RESOURCE_ID)) {
                        throw new BizServiceException("二级、三级营销机构没有系统参数管理权限！");
                    } 
                    //判断是否有数据字典管理权限  60400
                    if(isAuthorityResourceId(resource, DataBaseConstant.DICTINFO_RESOURCE_ID)) {
                        throw new BizServiceException("二级、三级营销机构没有数据字典管理权限！");
                    } 
                    //判断是否有系统日志查询权限  60500
                    if(isAuthorityResourceId(resource, DataBaseConstant.SYSTEM_LOG_RESOURCE_ID)) {
                        throw new BizServiceException("二级、三级营销机构系统日志查询权限！");
                    } 
                    //判断是否有结算周期管理权限  60600
                    if(isAuthorityResourceId(resource, DataBaseConstant.SETTLE_RESOURCE_ID)) {
                        throw new BizServiceException("二级、三级营销机构没有结算周期管理权限！");
                    } 
                    //判断是否有计算规则管理权限 60700
                    if(isAuthorityResourceId(resource, DataBaseConstant.CAALLINF_RESOURCE_ID)) {
                        throw new BizServiceException("二级、三级营销机构没有计算规则管理权限！");
                    } 
		        }
		    }
			IssueResource issueResource = new IssueResource();
			issueResource.setIssueId(sellerDTO.getEntityId());
			issueResource.setRank(DataBaseConstant.SELLER_RESOURCE_RANK);
			issueResource.setResourceId(resource);
			issueResource.setCreateUser(sellerDTO.getCreateUser());
			issueResource.setCreateTime(DateUtil.getCurrentTime());
			issueResource.setModifyUser(sellerDTO.getCreateUser());
			issueResource.setModifyTime(issueResource.getCreateTime());
			issueResource.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			issueResourceList.add(issueResource);
		}
		commonsDAO.batchInsert("TB_REL_ISSUE_RESOURCE.abatorgenerated_insert",
				issueResourceList);
	}

	public void deleteResource(SellerDTO sellerDTO) throws BizServiceException {
		IssueResourceExample example = new IssueResourceExample();
		example.createCriteria().andIssueIdEqualTo(sellerDTO.getEntityId())
				.andRankEqualTo("2");
		issueResourceDAO.deleteByExample(example);
	}

	public IssuerQueryDTO getIssuerQueryDTO() {
		return issuerQueryDTO;
	}

	public void setIssuerQueryDTO(IssuerQueryDTO issuerQueryDTO) {
		this.issuerQueryDTO = issuerQueryDTO;
	}

	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public CaclInfService getCaclInfService() {
		return caclInfService;
	}

	public void setCaclInfService(CaclInfService caclInfService) {
		this.caclInfService = caclInfService;
	}

	public SettlePeriodRuleService getSettlePeriodRuleService() {
		return settlePeriodRuleService;
	}

	public void setSettlePeriodRuleService(
			SettlePeriodRuleService settlePeriodRuleService) {
		this.settlePeriodRuleService = settlePeriodRuleService;
	}
/**
 * 查询营销机构及下属机构
 * */
    @SuppressWarnings("unchecked")
    public List<SellerQueryDTO> getSellerList(
            SellerQueryDTO sellerQueryDTO) {
        List<SellerQueryDTO> sellerList = (List<SellerQueryDTO>) commonsDAO
                .queryForList("SELLER.selectSellerList", sellerQueryDTO);
        return sellerList;
    }
}
