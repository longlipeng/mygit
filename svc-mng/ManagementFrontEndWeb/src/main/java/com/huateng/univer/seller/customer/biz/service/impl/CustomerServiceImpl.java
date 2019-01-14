package com.huateng.univer.seller.customer.biz.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.ibatis.attach.dao.AttachDAO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;
import com.allinfinance.univer.seller.cardholder.dto.AttachInfoDTO;
import com.allinfinance.univer.seller.customer.CertifincateValidityQueryDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.customer.CustomerQueryDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CardholderDAO;
import com.huateng.framework.ibatis.dao.CompanyInfoDAO;
import com.huateng.framework.ibatis.dao.CustomerDAO;
import com.huateng.framework.ibatis.dao.SellContractDAO;
import com.huateng.framework.ibatis.model.CompanyInfo;
import com.huateng.framework.ibatis.model.CompanyInfoExample;
import com.huateng.framework.ibatis.model.CompanyInfoKey;
import com.huateng.framework.ibatis.model.Customer;
import com.huateng.framework.ibatis.model.CustomerExample;
import com.huateng.framework.ibatis.model.CustomerKey;
import com.huateng.framework.ibatis.model.SellContract;
import com.huateng.framework.ibatis.model.SellContractExample;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.ibatis.model.UserExample;
import com.huateng.framework.ibatis.model.UserExample.Criteria;
import com.huateng.framework.util.ConfigMessage;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.framework.util.SendShortMessageThread;
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
	private CompanyInfoDAO companyInfoDAO;

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
	private CardholderDAO cardholderDAO;
	private AttachDAO attachDAO;

	
	public AttachDAO getAttachDAO() {
		return attachDAO;
	}

	public void setAttachDAO(AttachDAO attachDAO) {
		this.attachDAO = attachDAO;
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
	@Override
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
	@Override
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
				List<SellContract> scds = sellerContractDAO
						.selectByExample(sce);
				if (scds.size() > 0) {
					String custContract = scds.get(0).getSellContractId();
					customerDTO.setCustContract(custContract);
				}
			}
			// 公司客户关联公司信息
			if (customerDTO.getCustomerType().equals("0")) {
				viewCompanyInfo(customerDTO);
			}else {
				//修改个人学历和婚姻状况
				if ("".equals(customerDTO.getMarriage())) {
					customerDTO.setMarriage("5");
				}
				if ("".equals(customerDTO.getEducation())) {
					customerDTO.setEducation("7");
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
			//读取附加信息
			if (customerDTO.getCustomerType().equals("1")) {
				AttachInfoDTO attachInfoDTO = new AttachInfoDTO();
				attachInfoDTO.setPeopleNo(entityId);
				attachInfoDTO.setPeopleType("00");// 00代表客户，01代表持卡人
				attachInfoDTO.setDataStat("1");
				attachInfoDTO.setEntityId(customerDTO.getDefaultEntityId());
				List<AttachInfoDTO> attachInfoDTOs = attachDAO.getAttachInfos(attachInfoDTO);
				if (attachInfoDTOs != null && attachInfoDTOs.size() > 0) {
					attachInfoDTO = attachInfoDTOs.get(0);
					customerDTO.setValidity(attachInfoDTO.getValidity());// 证件有效期
					customerDTO.setNation(attachInfoDTO.getNation());// 名族
					customerDTO.setMarriage(attachInfoDTO.getMarriage());// 婚姻
					customerDTO.setEducation(attachInfoDTO.getEducation());// 教育
					customerDTO.setEmail(attachInfoDTO.getEmail());// 邮箱
					customerDTO.setCity(attachInfoDTO.getCity());
					customerDTO.setGender(attachInfoDTO.getRs1());// 设置性别信息
					customerDTO.setIsblacklist(
							attachInfoDTO.getIsblacklist() == null ? "0" : attachInfoDTO.getIsblacklist());// 黑名单
					customerDTO.setRiskGrade(attachInfoDTO.getRiskGrade() == null ? "O" : attachInfoDTO.getRiskGrade());// 风险等级
				}
			}
			return customerDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查看客户失败！");
		}
	}

	/**
	 * 获取企业客户的信息
	 * 
	 * @param dto
	 */
	private void viewCompanyInfo(CustomerDTO dto) {
		if (dto == null || dto.getEntityId() == null) {
			return;
		}
		CompanyInfoKey key = new CompanyInfoKey();
		key.setRelationNo(dto.getEntityId());
		key.setRelationType("00");
		CompanyInfo info = companyInfoDAO.selectByPrimaryKey(key);
		if (info != null) {
			ReflectionUtil.copyProperties(info, dto);
			dto.setBankPermit(info.getBankName());// 开户银行
			dto.setCustomerEnglishName(info.getCompanyEnglishname());// 企业英文名称
			dto.setCustomerName(info.getCompanyName());// 公司姓名
			dto.setLicenseId(info.getCompanyId());// 企业证件号码（营业执照）
			dto.setCustomerSize(info.getCompanySize());// 企业规模
			// dto.setCustomerFax(info.getCompanyFax());// 公司传真
			dto.setCustomerPostcode(info.getPostcode());// 公司邮编
			dto.setCompanyCountyr(info.getCorpCountyr());// 法人国籍
			dto.setCorpCredEndValidity(info.getCorpCredValidity());// 法人证件有效期
			dto.setCorpPhone(info.getCorpTelephoneNumber());// 法人号码
			dto.setCorpProfession(info.getCorpProfession());// 法人职业
			dto.setRegiCapital(info.getRegisteredCapital());// 注册资本
			dto.setLinkphone(info.getLinkphone());// 联系人固定电话
			dto.setCorpGender(info.getCorpGender());
			dto.setCorpCountyr(info.getCorpCountyr());// 法人国籍
			dto.setCompanyDescription(info.getCompanyDescription());// 公司描述
			dto.setCtidEdt(info.getCtidEdt());// 主体证件有效期
			dto.setCompanyRegisteredAddress(info.getCompanyRegisteredAddress());// 企业注册地
			dto.setCompanyAccountant(info.getCompanyAccountant());// 公司会计
			dto.setOperatorTelephoneNumber(info.getOperatorTelephoneNumber());// 经办人联系电话
			dto.setOperatorAddress(info.getOperatorAddress());// 代办人地址
			dto.setOperatorValidity(info.getOperatorValidity());// 代办人证件有效期
			dto.setOperatorId(info.getOperatorId());// 代办人证件号
			dto.setCorpAliasName(info.getCorpAliasName());// 法人别名
			dto.setCorpBirthday(info.getCorpBirthday());/// 法人出生日期
			dto.setCorpAddress(info.getCorpAddress());// 法人地址
			dto.setModifyUser(info.getModifyUser());
			dto.setModifyTime(info.getModifyTime());
		}
	}

	/**
	 * 查询客户
	 */
	@Override
	public PageDataDTO inqueryCustomer(CustomerQueryDTO customerQueryDTO)
			throws BizServiceException {
		try {
			customerQueryDTO.setSortFieldName("entityId");// 按创建时间排序(无效)
			customerQueryDTO.setSort("desc");
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
	@Override
	public CustomerDTO insertCustomer(CustomerDTO customerDTO)
			throws BizServiceException {
		try {
			// 检查用户名是否存在

			Customer customer = new Customer();

			ReflectionUtil.copyProperties(customerDTO, customer);
			String entityId = commonsDAO.getNextValueOfSequence("TB_ENTITY");
			customerDTO.setEntityId(entityId);

			String operateUser = customerDTO.getLoginUserId();
			if ("1".equals(customerDTO.getCustomerType().trim())) {
				customer.setCorpCredEndValidity(customerDTO.getValidity());
			}
			customer.setEntityId(entityId);
			customer.setFatherEntityId(customerDTO.getDefaultEntityId());
			customer.setCreateTime(DateUtil.getCurrentTime());
			customer.setCreateUser(operateUser);
			customer.setModifyTime(DateUtil.getCurrentTime());
			customer.setModifyUser(operateUser);
			customer.setDataState(DataBaseConstant.DATA_STATE_NORMAL);

			customerDAO.insert(customer);
			ReflectionUtil.copyProperties(customer, customerDTO);
			if ("0".equals(customerDTO.getCustomerType().trim())) {
				insertCompanyCostomerInfo(customerDTO);
			}else {
				insertAttachInfoForCustomer(customerDTO);
			}
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
//			entityContact.setContactTelephone(customer.getCustomerTelephone());
			entityContact.setContactMobilePhone(customer.getCustomerTelephone());
			entityContact.setCreateUser(operateUser);
			entityContact.setModifyUser(operateUser);
			entityContact.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_YES);
			entityContact.setValidityFlag("1");
			entityContact.setStarValidity(customerDTO.getCorpCredStaValidity());
			entityContact.setEndValidity(customerDTO.getCorpCredEndValidity());
			entityContact.setPapersNo(customerDTO.getCorpCredId());
			entityContact.setPapersType(customerDTO.getCorpCredType());
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

	/*
	 * 给用户添加附加信息
	 */
	private void insertAttachInfoForCustomer(CustomerDTO customerDTO) throws BizServiceException {
		// 增加附加信息
		try {
			AttachInfoDTO attachInfoDTO = new AttachInfoDTO();
			ReflectionUtil.copyProperties(customerDTO, attachInfoDTO);
			attachInfoDTO.setPeopleNo(customerDTO.getEntityId());
			attachInfoDTO.setPeopleType("00");// 00代表客户，01代表持卡人
			attachInfoDTO.setIndustry(customerDTO.getActivitySector());// 行业
			attachInfoDTO.setProfession(customerDTO.getAwareness());// 职业
			// attachInfoDTO.setValidity(DateUtil.getFormatTime(customerDTO.getCorpCredEndValidity()));
			// 失效期
			attachInfoDTO.setCountry(customerDTO.getNationality());// 国家
			attachInfoDTO.setCity(customerDTO.getCity());// 城市
			attachInfoDTO.setUpdateDate(DateUtil.getCurrentTime());
			attachInfoDTO.setEntityId(customerDTO.getDefaultEntityId());// 机构号
			attachInfoDTO.setNation(customerDTO.getNation());// 名族
			attachInfoDTO.setEducation(customerDTO.getEducation());// 教育
			attachInfoDTO.setMarriage(customerDTO.getMarriage());// 婚姻状况
			attachInfoDTO.setEmail(customerDTO.getEmail());// email
			attachInfoDTO.setDataStat("1");
			attachInfoDTO.setIsblacklist("0");// 黑名单标识
			attachInfoDTO.setRiskGrade("O");// 风险等级
			attachInfoDTO.setRs1(customerDTO.getGender());// 性别
			attachDAO.insertAttachInfo(attachInfoDTO);
		} catch (SQLException e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("添加客户附加信息失败！");
		}
	}

	/**
	 * 插入企业客户的信息
	 * 
	 * @param dto
	 */
	private void insertCompanyCostomerInfo(CustomerDTO dto) {
		CompanyInfo info = new CompanyInfo();

		String operateUser = dto.getLoginUserId();
		ReflectionUtil.copyProperties(dto, info);
		info.setRelationNo(dto.getEntityId());// 客户id
		info.setRelationType("00");
		info.setBankName(dto.getBankPermit());// 开户银行
		info.setCreateUser(operateUser);// 创建人
		info.setCreateTime(DateUtil.getCurrentTime());// 创建日期
		info.setCompanyEnglishname(dto.getCustomerEnglishName());// 企业英文名称
		info.setCompanyName(dto.getCustomerName());// 公司姓名
		info.setCompanyId(dto.getLicenseId());// 企业证件号码（营业执照）
		info.setCompanySize(dto.getCustomerSize());// 企业规模
		info.setCompanyFax(dto.getActivitySector());// 公司
		info.setLinkphone(dto.getLinkphone());// 联系人固话
		info.setCtidEdt(dto.getLicenseEndValidity());// 主体证件有效期
		info.setEmail(dto.getEmail());// 电子邮箱
		info.setPostcode(dto.getCustomerPostcode());// 公司邮编
		info.setCorpCountyr(dto.getCompanyCountyr());// 法人国籍
		info.setCorpCredValidity(dto.getCorpCredEndValidity());// 法人证件有效期
		info.setCorpTelephoneNumber(dto.getCorpPhone());// 法人号码
		info.setRegisteredCapital(dto.getRegiCapital());// 注册资本
		info.setModifyUser(operateUser);
		info.setCorpGender(dto.getCorpGender());
		info.setIsblacklist("0");// 黑名单标识
		info.setRiskGrade("O");// 风险等级
		info.setModifyTime(DateUtil.getCurrentTime());
		info.setVerifyStat("0");
		info.setDataState("1");
		// 页面部分信息去掉,设置默认值
		info.setCorpAliasName("@n");//
		info.setCorpProfession("@n");
		companyInfoDAO.insert(info);
	}

	/**
	 * 修改客户状态
	 */
	@Override
	public void modifyState(CustomerDTO customerDTO) throws BizServiceException {

	}

	/**
	 * 修改更新客户
	 */
	@Override
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
			if (customerDTO.getCustomerType() != null && "1".equals(customerDTO.getCustomerType().trim())) {
				customer.setCorpCredEndValidity(customerDTO.getValidity());
			}
			customerDAO.updateByPrimaryKeySelective(customer);
			// 修改企业客户信息
			if (customerDTO.getCustomerType() != null && customerDTO.getCustomerType().equals("0")) {
				updateCompanyCustomerInfo(customerDTO);
			} else if (customerDTO.getCustomerType() != null && customerDTO.getCustomerType().equals("1")) {
				// 修改附加信息
				updateAttachInfo(customerDTO);
			}
			//取客户信息
			CustomerKey customerKey = new CustomerKey();
			customerKey.setEntityId(customer.getEntityId());
			customerKey.setFatherEntityId(customer.getFatherEntityId());
			Customer newCustomer = customerDAO.selectByPrimaryKey(customerKey);
			//TODO
			//渠道为除1（网络）外的发送短(前台已经限制录入网络，后台不需要判断)
			if(!"1".equals(newCustomer.getChannel())){
				//审核通过
				if("1".endsWith(newCustomer.getCusState())){
					//发送短信
					SendShortMessageThread sendShortMessageThread = new SendShortMessageThread(newCustomer.getEntityId(),newCustomer.getCustomerTelephone(),ConfigMessage.getTemplet3_No(),newCustomer.getCustomerName());
					sendShortMessageThread.start();
				}
//				//拒绝审核(拒绝不需要发短信)
//				else if("3".endsWith(customer.getCusState())){
//					SendShortMessageThread sendShortMessageThread = new SendShortMessageThread(customer.getEntityId(),customer.getCustomerTelephone(),ConfigMessage.getTemplet4_No());
//					sendShortMessageThread.start();
//				}
			}
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("更新客户失败！");
		}
	}
	// 将审核通过的客户信息同步至cim
	@Override
	public void syncToCRM(CustomerDTO dto) throws Exception {

	}

	/**
	 * 更新个人信息表
	 * 
	 * @throws SQLException
	 * 
	 */
	private void updateAttachInfo(CustomerDTO customerDTO) throws SQLException {
		AttachInfoDTO attachInfoDTO = new AttachInfoDTO();
		ReflectionUtil.copyProperties(customerDTO, attachInfoDTO);
		attachInfoDTO.setPeopleNo(customerDTO.getEntityId());
		attachInfoDTO.setPeopleType("00");// 00代表客户，01代表持卡人
		attachInfoDTO.setIndustry(customerDTO.getActivitySector());// 行业
		attachInfoDTO.setProfession(customerDTO.getAwareness());// 职业
		// attachInfoDTO.setValidity(DateUtil.getFormatTime(customerDTO.getCorpCredEndValidity()));//失效期
		attachInfoDTO.setCountry(customerDTO.getNationality());// 国家
		attachInfoDTO.setCity(customerDTO.getCity());// 城市
		attachInfoDTO.setUpdateDate(DateUtil.getCurrentTime());
		attachInfoDTO.setEntityId(customerDTO.getDefaultEntityId());// 机构号
		attachInfoDTO.setEducation(customerDTO.getEducation());
		attachInfoDTO.setEmail(customerDTO.getEmail());
		attachInfoDTO.setMarriage(customerDTO.getMarriage());
		attachInfoDTO.setNation(customerDTO.getNation());
		attachInfoDTO.setDataStat("1");
		attachInfoDTO.setRs1(customerDTO.getGender());
		List<AttachInfoDTO> attachInfoDTOList = attachDAO.getAttachInfos(attachInfoDTO);
		if (attachInfoDTOList == null || attachInfoDTOList.size() == 0) {
			attachDAO.insertAttachInfo(attachInfoDTO);
		} else {
			attachDAO.updateAttachInfo(attachInfoDTO);
		}
	}
	
	
	/**
	 * 更新企业客户的信息
	 * 
	 * @param dto
	 */
	private void updateCompanyCustomerInfo(CustomerDTO dto) {
		CompanyInfo info = new CompanyInfo();
		ReflectionUtil.copyProperties(dto, info);
		info.setRelationNo(dto.getEntityId());
		info.setRelationType("00");
		String operateUser = dto.getLoginUserId();
		// 不知怎么有些信息无法copy
		info.setBankName(dto.getBankPermit());// 开户银行
		info.setCompanyEnglishname(dto.getCustomerEnglishName());// 企业英文名称
		info.setCompanyName(dto.getCustomerName());// 公司姓名
		info.setCompanyId(dto.getLicenseId());// 企业证件号码（营业执照）
		info.setCompanySize(dto.getCustomerSize());// 企业规模
		info.setCompanyFax(dto.getActivitySector());// 公司行业
		info.setPostcode(dto.getCustomerPostcode());// 公司邮编
		info.setCorpCountyr(dto.getCompanyCountyr());// 法人国籍
		info.setCorpCredValidity(dto.getCorpCredEndValidity());// 法人证件有效期
		info.setCorpTelephoneNumber(dto.getCorpPhone());// 法人号码
		info.setCorpProfession(dto.getCorpProfession());// 法人职业
		info.setRegisteredCapital(dto.getRegiCapital());// 注册资本
		info.setLinkphone(dto.getLinkphone());// 联系人固定电话
		info.setCorpCountyr(dto.getCorpCountyr());// 法人国籍
		info.setCompanyDescription(dto.getCompanyDescription());// 公司描述
		info.setCtidEdt(dto.getLicenseEndValidity());// 主体证件有效期
		info.setCompanyRegisteredAddress(dto.getCompanyRegisteredAddress());// 企业注册地
		info.setCompanyAccountant(dto.getCompanyAccountant());// 公司会计
		info.setOperatorTelephoneNumber(dto.getOperatorTelephoneNumber());// 经办人联系电话
		info.setOperatorAddress(dto.getOperatorAddress());// 代办人地址
		info.setOperatorValidity(dto.getOperatorValidity());// 代办人证件有效期
		info.setOperatorId(dto.getOperatorId());// 代办人证件号
		info.setCorpAliasName(dto.getCorpAliasName());// 法人别名
		info.setCorpBirthday(dto.getCorpBirthday());/// 法人出生日期
		info.setCorpAddress(dto.getCorpAddress());// 法人地址
		info.setModifyUser(operateUser);
		info.setModifyTime(DateUtil.getCurrentTime());
		CompanyInfoKey key = new CompanyInfoKey();
		key.setRelationNo(dto.getEntityId());
		key.setRelationType("00");
		// 如果存在则update,否则insert
		if (companyInfoDAO.selectByPrimaryKey(key) != null) {
			companyInfoDAO.updateByPrimaryKeySelective(info);
		} else {
			/*
			 * info.setDataState("1"); info.setIsvalidity("0"); info.setVerifyStat("0");
			 * info.setIsSynchronize(""); info.setCreateTime(DateUtil.getCurrentTime());
			 * info.setCreateUser(dto.getCreateUser()); companyInfoDAO.insert(info);
			 */
			insertCompanyCostomerInfo(dto);
		}
	}

	@Override
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
				CompanyInfoKey key = new CompanyInfoKey();
				key.setRelationNo(keyId[0]);
				key.setRelationType("00");
				CompanyInfo info = companyInfoDAO.selectByPrimaryKey(key);
				if (info != null) {
					info.setDataState("0");
					companyInfoDAO.updateByPrimaryKey(info);
				}
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
	@Override
	public CustomerDTO getCustomerById(String entityId)
			throws BizServiceException {
		CustomerExample example = new CustomerExample();
		example.createCriteria().andEntityIdEqualTo(entityId);
		Customer customer = customerDAO.selectByExample(example).get(0);
		CustomerDTO dto = new CustomerDTO();
		ReflectionUtil.copyProperties(customer, dto);

		return dto;
	}

	@Override
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
	@Override
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
	 * 根据条件获取客户信息
	 */
	@Override
	public List<CustomerDTO> getCustomerByIdNo(CustomerDTO customerDTO)
			throws BizServiceException {
		return customerDAO.selectCustomeInfoByIdNo(customerDTO);
	}
	
	/**
	 * 导入客户信息
	 */
	@Override
	public void importFile(CustomerDTO customerDTO) throws BizServiceException {
	}
	
	
	/**
	 * 更具企业证件类型好证件号判断企业是否重复
	 * @return
	 * @throws BizServiceException 
	 */
	@Override
	public void  checkLicense(CustomerDTO dto) throws BizServiceException{
		CompanyInfoExample example = new CompanyInfoExample();
		example.createCriteria().andCompanyIdTypeEqualTo(dto.getCompanyIdType()).andCompanyIdEqualTo(dto.getCompanyId())
				.andRelationTypeEqualTo("00").andDataStateEqualTo("1");
		List<CompanyInfo> list=companyInfoDAO.selectByExample(example);
		if(list!=null&&list.size()>0) {
			throw new BizServiceException("已存在相同证件类型的公司!");
		}
	}
	
	//查询证件号是否过期
	@Override
	public PageDataDTO queryOutdateCertifincate(CertifincateValidityQueryDTO dto) throws BizServiceException {
		try {
		return pageQueryDAO.query("CUSTOMER.queryOutdateCertifincate",
				dto);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询失败");
		}
	}

	public CardholderDAO getCardholderDAO() {
		return cardholderDAO;
	}

	public void setCardholderDAO(CardholderDAO cardholderDAO) {
		this.cardholderDAO = cardholderDAO;
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

	@Override
	public CustomerDTO insertOrderCustomer(CustomerDTO customerDTO)
			throws BizServiceException {
		CustomerDTO dto = this.insertCustomer(customerDTO);
		sellerContractService.insertByMasterplate(dto);
		return dto;
	}
	
	
	/***********************************新增区域********************************/
	    /**
	     * 查询企业客户信息
	     */
	    @Override
		public PageDataDTO inqueryCus(CustomerQueryDTO customerQueryDTO) throws BizServiceException {
	        try {
	            return pageQueryDAO.query("CUSTOMER.selectCusByDTO",
	                    customerQueryDTO);
	        } catch (Exception e) {
	            this.logger.error(e.getMessage());
	            throw new BizServiceException("查询企业客户信息失败");
	        }
	    }
	    
	    
	    /**
	     * 查询个人客户信息
	     */
	    @Override
		public PageDataDTO inqueryPer(CustomerQueryDTO customerQueryDTO) throws BizServiceException {
	        try {
	            return pageQueryDAO.query("CUSTOMER.selectPerByDTO",
	                    customerQueryDTO);
	        } catch (Exception e) {
	            this.logger.error(e.getMessage());
	            throw new BizServiceException("查询个人客户信息失败");
	        }
	    }
	  /***********************************新增区域********************************/

	public CompanyInfoDAO getCompanyInfoDAO() {
		return companyInfoDAO;
	}

	public void setCompanyInfoDAO(CompanyInfoDAO companyInfoDAO) {
		this.companyInfoDAO = companyInfoDAO;
	}

}
