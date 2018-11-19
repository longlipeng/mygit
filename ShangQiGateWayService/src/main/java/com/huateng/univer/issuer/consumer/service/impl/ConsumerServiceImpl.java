package com.huateng.univer.issuer.consumer.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.consumer.ConsumerDTO;
import com.allinfinance.univer.issuer.dto.consumer.ConsumerQueryDTO;
import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.dao.BaseDAO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.ConsumerContractDAO;
import com.huateng.framework.ibatis.dao.ConsumerDAO;
import com.huateng.framework.ibatis.dao.EntityContactDAO;
import com.huateng.framework.ibatis.dao.EntityDeliveryDAO;
import com.huateng.framework.ibatis.dao.EntityDepartmentDAO;
import com.huateng.framework.ibatis.dao.EntityInvoiceAddressDAO;
import com.huateng.framework.ibatis.dao.InvoiceCompanyDAO;
import com.huateng.framework.ibatis.dao.IssueResourceDAO;
import com.huateng.framework.ibatis.dao.IssuerDAO;
import com.huateng.framework.ibatis.dao.PosParamenterDAO;
import com.huateng.framework.ibatis.dao.SellerDAO;
import com.huateng.framework.ibatis.model.Consumer;
import com.huateng.framework.ibatis.model.ConsumerExample;
import com.huateng.framework.ibatis.model.ConsumerKey;
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
import com.huateng.framework.ibatis.model.PosParamenter;
import com.huateng.framework.ibatis.model.Seller;
import com.huateng.framework.ibatis.model.SellerKey;
import com.huateng.framework.ibatis.model.User;
import com.huateng.framework.ibatis.model.UserExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.entitybaseinfo.bank.biz.service.BankService;
import com.huateng.univer.entitybaseinfo.contact.biz.service.ContactService;
import com.huateng.univer.entitybaseinfo.delivarypoint.biz.service.DeliveryPointService;
import com.huateng.univer.entitybaseinfo.deliverycontact.biz.service.DeliveryContactService;
import com.huateng.univer.entitybaseinfo.department.biz.service.DepartmentService;
import com.huateng.univer.entitybaseinfo.invoiceaddress.biz.service.InvoiceAddressService;
import com.huateng.univer.entitybaseinfo.invoicecompany.biz.service.InvoiceCompanyService;
import com.huateng.univer.issuer.consumer.service.ConsumerService;
import com.huateng.univer.issuer.entityBaseInfo.service.EntityBaseInfoService;
import com.huateng.univer.servicefeerule.biz.service.CaclInfService;
import com.huateng.univer.settleperiodrule.biz.service.SettlePeriodRuleService;
import com.huateng.univer.system.dictinfo.biz.service.EntityDictInfoService;
import com.huateng.univer.system.role.biz.service.RoleService;
import com.huateng.univer.system.role.integration.dao.RoleServiceDAO;
import com.huateng.univer.system.sysparam.biz.service.EntitySystemParameterService;
import com.huateng.univer.system.user.biz.service.UserService;
import com.huateng.univer.system.user.integration.dao.UserServiceDAO;

public class ConsumerServiceImpl implements ConsumerService {
	Logger logger = Logger.getLogger(ConsumerServiceImpl.class);

	private ConsumerDAO consumerDAO;

	private PageQueryDAO pageQueryDAO;

	private BaseDAO baseDAO;

	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	private CommonsDAO commonsDAO;
	// 实体service
	private ContactService contactService;
	private DeliveryPointService deliveryPointService;
	private DeliveryContactService deliveryContactService;
	private DepartmentService departmentService;
	private InvoiceAddressService invoiceAddressService;
	private InvoiceCompanyService invoiceCompanyService;
	private ConsumerContractDAO consumerContractDAO;
	private EntityDictInfoService entityDictInfoService;
	private EntitySystemParameterService entitySystemParameterService;
	private UserService userService;
	private EntityBaseInfoService entityBaseInfoService;
	private IssuerDAO issuerDAO;
	private SellerDAO sellerDAO;
	private UserServiceDAO userServiceDAO;
	private EntityContactDAO contractDAO;
	private EntityDeliveryDAO deliveryDAO;
	private InvoiceCompanyDAO invoiceCompanyDAO;
	private EntityInvoiceAddressDAO invoiceAddressDAO;
	private EntityDepartmentDAO departmentDAO;
	private IssueResourceDAO issueResourceDAO;
	private RoleService roleService;
	private RoleServiceDAO roleDAO;
	private PosParamenterDAO terParameterDAO;
	private BankService bankService;
	private SettlePeriodRuleService settlePeriodRuleService;
	private CaclInfService caclInfService;

	public BankService getBankService() {
		return bankService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public PosParamenterDAO getTerParameterDAO() {
		return terParameterDAO;
	}

	public void setTerParameterDAO(PosParamenterDAO terParameterDAO) {
		this.terParameterDAO = terParameterDAO;
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

	public ConsumerContractDAO getConsumerContractDAO() {
		return consumerContractDAO;
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

	public void setConsumerContractDAO(ConsumerContractDAO consumerContractDAO) {
		this.consumerContractDAO = consumerContractDAO;
	}

	public ConsumerDAO getConsumerDAO() {
		return consumerDAO;
	}

	public void setConsumerDAO(ConsumerDAO consumerDAO) {
		this.consumerDAO = consumerDAO;
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

	public SettlePeriodRuleService getSettlePeriodRuleService() {
		return settlePeriodRuleService;
	}

	public void setSettlePeriodRuleService(
			SettlePeriodRuleService settlePeriodRuleService) {
		this.settlePeriodRuleService = settlePeriodRuleService;
	}

	public PageDataDTO inqueryConsumer(ConsumerQueryDTO consumerQueryDTO)
			throws BizServiceException {
		try {
			consumerQueryDTO.setSort("desc");
			consumerQueryDTO.setSortFieldName("entityId");
			PageDataDTO pdd = pageQueryDAO.query("CONSUMER.selectConsumer",
					consumerQueryDTO);
			return pdd;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			logger.error(e);
			throw new BizServiceException("查询收单机构信息失败~！");
		}
	}

	public ConsumerDTO insertConsumer(ConsumerDTO consumerDTO)
			throws BizServiceException {
		try {

			Consumer consumer = new Consumer();
			ReflectionUtil.copyProperties(consumerDTO, consumer);
			String id = consumerDTO.getEntityId();
			if (id == null || "".equals(id.trim())) {
				id = commonsDAO.getNextValueOfSequence("TB_ENTITY");
				// 默认插入部门信息
				EntityDepartment entityDepartment = new EntityDepartment();
				entityDepartment.setEntityId(id);
				entityDepartment.setDepartmentName(consumerDTO
						.getConsumerName());
				entityDepartment.setCreateUser(consumerDTO.getCreateUser());
				entityDepartment
						.setModifyUser(entityDepartment.getCreateUser());
				entityDepartment.setDefaultFlag("1");
				entityBaseInfoService.insertDepartMentInfo(entityDepartment);

				// 默认插入联系人信息
				EntityContact entityContact = new EntityContact();
				entityContact.setEntityId(id);
				entityContact.setContactName(consumerDTO.getConsumerName());
				entityContact.setCreateUser(consumerDTO.getCreateUser());
				entityContact.setModifyUser(entityContact.getCreateUser());
				entityContact.setDefaultFlag("1");
				entityContact.setValidityFlag("1");
				entityContact.setPapersNo("1");
				entityContact.setPapersType("1");
				entityBaseInfoService.insertContactInfo(entityContact);

				// 默认插入快递点信息
				EntityDelivery entityDelivery = new EntityDelivery();
				entityDelivery.setEntityId(id);
				entityDelivery.setDeliveryName(consumerDTO.getConsumerName());
				entityDelivery.setDeliveryAddress(consumerDTO
						.getConsumerAddress());
				entityDelivery.setCreateUser(consumerDTO.getCreateUser());
				entityDelivery.setModifyUser(entityDelivery.getCreateUser());
				entityDelivery.setDefaultFlag("1");
				entityBaseInfoService.insertDeliveryInfo(entityDelivery);

				// 添加初始计算规则
				caclInfService.insertInit(id);

				// 默认插入发票地址信息
				EntityInvoiceAddress invoiceAddress = new EntityInvoiceAddress();
				invoiceAddress.setEntityId(id);
				invoiceAddress.setInvoiceAddress(consumerDTO
						.getConsumerAddress());
				invoiceAddress.setAddressPostcode(consumerDTO
						.getConsumerPostcode());
				invoiceAddress.setCreateUser(consumerDTO.getCreateUser());
				invoiceAddress.setModifyUser(invoiceAddress.getCreateUser());
				invoiceAddress.setInvoiceRecipient(consumerDTO
						.getConsumerName());
				invoiceAddress.setDeliveryOption("1");
				invoiceAddress.setDefaultFlag("1");
				entityBaseInfoService.insertInvoiceAddressInfo(invoiceAddress);

				// 默认插入发票公司信息
				InvoiceCompany invoiceCompany = new InvoiceCompany();
				invoiceCompany.setEntityId(id);
				invoiceCompany.setInvoiceCompanyName(consumerDTO
						.getConsumerName());
				invoiceCompany.setCreateUser(consumerDTO.getCreateUser());
				invoiceCompany.setModifyUser(invoiceCompany.getCreateUser());
				invoiceCompany.setDefaultFlag("1");
				entityBaseInfoService.insertInvoiceCompanyInfo(invoiceCompany);
				// 添加实体数据字典
				entityDictInfoService.insertEntityDictInfo(id);
				// 添加实体系统参数
//				entitySystemParameterService.insertEntitySystemParameter(id,
//						consumerDTO.getDefaultEntityId(), consumerDTO
//								.getLoginUserId());
			}
			consumer.setEntityId(id);
			String userId = commonsDAO.getNextValueOfSequence("TB_USER");
			consumer.setUserId(userId);
			consumer.setFatherEntityId(consumerDTO.getDefaultEntityId());
			consumer.setCreateTime(DateUtil.getCurrentTime());
			consumer.setCreateUser(consumerDTO.getCreateUser());
			consumer.setModifyTime(DateUtil.getCurrentTime());
			consumer.setModifyUser(consumer.getCreateUser());
			consumer.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			consumerDAO.insert(consumer);

			userService.insertUserForEntity(id, userId, consumerDTO
					.getUserName(), consumerDTO.getUserPassword(), consumerDTO
					.getUserEmail(), consumerDTO.getLoginUserId());
			ReflectionUtil.copyProperties(consumer, consumerDTO);

			// 收单机构权限添加
			consumerDTO.setCreateUser(consumerDTO.getLoginUserId());
			this.insertResources(consumerDTO);
			PosParamenter posParamenter = new PosParamenter();
			posParamenter.setConsumerId(id);
			terParameterDAO.insert_default(posParamenter);

			// 添加 周期规则初始数据
			settlePeriodRuleService.initInsert(id, userId);

		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			logger.error(e);
			throw new BizServiceException("添加收单机构信息失败~！");
		}
		return consumerDTO;
	}

	public void checkConsumer(ConsumerDTO consumerDTO)
			throws BizServiceException {
		ConsumerExample consumerExample = new ConsumerExample();
		consumerExample.createCriteria().andDataStateEqualTo(
				DataBaseConstant.DATA_STATE_NORMAL).andFatherEntityIdEqualTo(
				consumerDTO.getDefaultEntityId());
		List<Consumer> consumers = consumerDAO.selectByExample(consumerExample);
		if (consumers.size() > 0) {
			throw new BizServiceException("发行机构只能添加一个收单机构！");
		}

	}

	public ConsumerDTO viewConsumer(ConsumerDTO consumerDTO)
			throws BizServiceException {
		try {
			ConsumerKey key = new ConsumerKey();
			key.setEntityId(consumerDTO.getEntityId());
			key.setFatherEntityId(consumerDTO.getDefaultEntityId());
			Consumer consumer = consumerDAO.selectByPrimaryKey(key);
			ConsumerDTO consDTO = new ConsumerDTO();
			ReflectionUtil.copyProperties(consumer, consDTO);
			String entityId = consumerDTO.getEntityId();
			// 关联实体信息
			consDTO.setContractList(contactService.inqueryContact(entityId));
			consDTO
					.setDeliveryPointList(deliveryPointService
							.inquery(entityId));
			consDTO.setDepartmentList(departmentService.inquery(entityId));
			consDTO.setInvoiceAddressList(invoiceAddressService
					.inquery(entityId));
			consDTO.setInvoiceCompanyList(invoiceCompanyService
					.inquery(entityId));
			consDTO.setBankList(bankService.inquery(entityId, "1"));
			User user = userServiceDAO.selectByPrimaryKey(consumer.getUserId());
			if (user != null) {
				consDTO.setUserName(user.getUserName());
				consDTO.setUserEmail(user.getEmail());
			}
			// 加载权限信息
			// 得到此机构权限管理
			UserDTO userDTO = new UserDTO();
			List<String> flist = new ArrayList<String>();
			flist.add("0");
			flist.add("4");
			userDTO.setFunctionRoleId(flist);
			userDTO.setIsSaleFlage("3");
			userDTO.setEntityId(consumerDTO.getDefaultEntityId());
			List<ResourceDTO> resourceDTOlist = roleService
					.selectIssueResource(userDTO);
			// 获取已设置的权限
			userDTO.setEntityId(consumerDTO.getEntityId());
			userDTO.setIsSaleFlage("3");
			List<ResourceDTO> nresourceDTOlist = roleDAO
					.getIssueResourceDTOs(userDTO);
			consDTO.setResourceDTOs(resourceDTOlist);
			consDTO.setNresourceDTOs(nresourceDTOlist);
			return consDTO;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			logger.error(e);
			throw new BizServiceException("编辑收单机构信息失败~！");
		}
	}

	public void editConsumer(ConsumerDTO consumerDTO)
			throws BizServiceException {
		try {

			if (null != consumerDTO.getConsumerState()
					&& "0".equals(consumerDTO.getConsumerState())) {
				int i = commonsDAO.isDeleteConsumer(consumerDTO);
				if (i == 0) {
					throw new BizServiceException("修改失败");
				}
				/**
				 * 删除用户
				 */
				UserExample userExample = new UserExample();
				User user = new User();
				user.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				userExample.createCriteria().andEntityIdEqualTo(
						consumerDTO.getEntityId()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL);
				userServiceDAO.updateByExampleSelective(user, userExample);
			} else if (consumerDTO.getConsumerState().equals(
					DataBaseConstant.DATA_STATE_NORMAL)) {
				/**
				 * 恢复用户
				 */
				UserExample userExample = new UserExample();
				User user = new User();
				user.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
				userExample.createCriteria().andEntityIdEqualTo(
						consumerDTO.getEntityId());
				userServiceDAO.updateByExampleSelective(user, userExample);
			}

			ConsumerKey key = new ConsumerKey();
			key.setEntityId(consumerDTO.getEntityId());
			key.setFatherEntityId(consumerDTO.getDefaultEntityId());
			Consumer consumer = consumerDAO.selectByPrimaryKey(key);
			ReflectionUtil.copyProperties(consumerDTO, consumer);
			consumer.setFatherEntityId(consumerDTO.getDefaultEntityId());
			consumer.setModifyTime(DateUtil.getCurrentTime());
			consumerDAO.updateByPrimaryKeySelective(consumer);
			User user = new User();
			user.setUserId(consumerDTO.getUserId());
			user.setUserName(consumerDTO.getUserName());
			user.setEmail(consumerDTO.getUserEmail());
			userServiceDAO.updateByPrimaryKeySelective(user);

			// 修改权限
			this.deleteResource(consumerDTO);
			consumerDTO.setCreateUser(consumerDTO.getLoginUserId());
			this.insertResources(consumerDTO);
		} catch (BizServiceException b) {
			throw b;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			logger.error(e);
			throw new BizServiceException("编辑收单机构信息失败~！");
		}
	}

	public void deleteConsumer(ConsumerDTO consumerDTO)
			throws BizServiceException {
		try {
			List<ConsumerDTO> list = consumerDTO.getConsumerDTOs();
			List<Consumer> updateList = new ArrayList<Consumer>();
			List<String> entityIdList = new ArrayList<String>();
			for (ConsumerDTO dto : list) {
				Consumer consumer = new Consumer();
				ConsumerExample example = new ConsumerExample();
				example.createCriteria().andEntityIdEqualTo(dto.getEntityId());
				consumer = consumerDAO.selectByExample(example).get(0);
				int i = commonsDAO.isDeleteConsumer(consumerDTO);
				if (i == 0) {
					throw new BizServiceException("删除失败");
				}
				/**
				 * 
				 * 删除用户
				 */
				UserExample userExample = new UserExample();
				User user = new User();
				user.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				userExample.createCriteria().andEntityIdEqualTo(
						consumerDTO.getEntityId()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL);
				userServiceDAO.updateByExampleSelective(user, userExample);

				/**
				 * 删除关联的联系人信息
				 */
				EntityContactExample ex = new EntityContactExample();
				EntityContact contact = new EntityContact();
				contact.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				ex.createCriteria().andEntityIdEqualTo(
						consumerDTO.getEntityId()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL);
				contractDAO.updateByExampleSelective(contact, ex);
				/**
				 * 删除关联的快递点信息
				 */
				EntityDeliveryExample dex = new EntityDeliveryExample();
				EntityDelivery delivery = new EntityDelivery();
				delivery.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				dex.createCriteria().andEntityIdEqualTo(
						consumerDTO.getEntityId()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL);
				deliveryDAO.updateByExampleSelective(delivery, dex);
				/**
				 * 删除关联的发票公司信息
				 */
				InvoiceCompanyExample cex = new InvoiceCompanyExample();
				InvoiceCompany invc = new InvoiceCompany();
				invc.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				cex.createCriteria().andEntityIdEqualTo(
						consumerDTO.getEntityId()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL);
				invoiceCompanyDAO.updateByExampleSelective(invc, cex);
				/**
				 * 删除关联的发票公司地址信息
				 */
				EntityInvoiceAddressExample aex = new EntityInvoiceAddressExample();
				EntityInvoiceAddress Address = new EntityInvoiceAddress();
				Address.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				aex.createCriteria().andEntityIdEqualTo(
						consumerDTO.getEntityId()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL);
				invoiceAddressDAO.updateByExampleSelective(Address, aex);
				/**
				 * 删除关联的部门信息
				 */
				EntityDepartmentExample depex = new EntityDepartmentExample();
				EntityDepartment department = new EntityDepartment();
				department.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				depex.createCriteria().andEntityIdEqualTo(
						consumerDTO.getEntityId()).andDataStateEqualTo(
						DataBaseConstant.DATA_STATE_NORMAL);
				departmentDAO.updateByExampleSelective(department, depex);
				consumer.setModifyUser(dto.getLoginUserId());
				consumer.setModifyTime(DateUtil.getCurrentTime());
				consumer.setDataState(DataBaseConstant.DATA_STATE_DELETE);
				updateList.add(consumer);
				entityIdList.add(dto.getEntityId());
			}

			// 判断收单机构下是否还有可用合同
			// if (chkConsumerContract(entityIdList) > 0) {
			// throw new BizServiceException("所选收单机构还有合同是可用的，不能删除！");
			// }
			// 删除权限
			this.deleteResource(consumerDTO);
			commonsDAO.batchDelete(
					"TB_CONSUMER.abatorgenerated_updateByPrimaryKey",
					updateList);
		} catch (BizServiceException e) {
			throw e;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			logger.error(e);
			throw new BizServiceException("删除服务信息失败~！");
		}

	}

	/*
	 * 
	 * 修改收单机构网站密码
	 */
	public ConsumerDTO loadForModifyWebPassowrd(ConsumerDTO consumerDTO)
			throws BizServiceException {
		try {
			ConsumerKey key = new ConsumerKey();
			key.setEntityId(consumerDTO.getEntityId());
			key.setFatherEntityId(consumerDTO.getFatherEntityId());
			Consumer consumer = consumerDAO.selectByPrimaryKey(key);
			if (("0").equals(consumer.getEnableWebsite())
					|| consumer.getEnableWebsite() == null) {
				throw new BizServiceException("收单机构并没有开通网上管理请先开通");
			} else {
				consumerDTO = new ConsumerDTO();
				ReflectionUtil.copyProperties(consumer, consumerDTO);
				return consumerDTO;
			}
		} catch (BizServiceException e1) {
			throw e1;
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			logger.error(e);
			throw new BizServiceException("加载收单机构失败!");
		}
	}

	/**
	 * 部分更新用户
	 */

	public void updatePart(ConsumerDTO consumerDTO) throws BizServiceException {
		try {
			Consumer consumer = new Consumer();
			ReflectionUtil.copyProperties(consumerDTO, consumer);
			consumerDAO.updateByPrimaryKeySelective(consumer);
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			logger.error(e);
			throw new BizServiceException("修改密码失败!");
		}

	}

	// 判断收单机构下是否还有可用合同
	// public int chkConsumerContract(List<String> merchantIdList) {
	// int ret = 0;
	// ConsumerContractExample mchntContractExample = new
	// ConsumerContractExample();
	// mchntContractExample.createCriteria()
	// .andContractBuyerIn(merchantIdList)
	// .andContractEndDateIsNotNull()
	// .andContractEndDateGreaterThanOrEqualTo(DateUtil.date2String(DateUtil.getCurrentDate()))
	// .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
	// List<ConsumerContract> mchntContractList =
	// consumerContractDAO.selectByExample(mchntContractExample);
	// if (mchntContractList != null && mchntContractList.size() != 0) {
	// ret = mchntContractList.size();
	// return ret;
	// }
	// mchntContractExample = new ConsumerContractExample();
	// mchntContractExample.createCriteria()
	// .andContractBuyerIn(merchantIdList).andContractEndDateIsNull()
	// .andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
	// mchntContractList =
	// consumerContractDAO.selectByExample(mchntContractExample);
	// if (mchntContractList != null && mchntContractList.size() != 0) {
	// ret = mchntContractList.size();
	// }
	// return ret;
	// }

	public EntityDictInfoService getEntityDictInfoService() {
		return entityDictInfoService;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
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

	public EntityBaseInfoService getEntityBaseInfoService() {
		return entityBaseInfoService;
	}

	public void setEntityBaseInfoService(
			EntityBaseInfoService entityBaseInfoService) {
		this.entityBaseInfoService = entityBaseInfoService;
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

	public ConsumerDTO configEntityId(ConsumerQueryDTO consumerQueryDTO)
			throws BizServiceException {
		ConsumerDTO consumerDTO = new ConsumerDTO();
		try {
			IssuerKey key1 = new IssuerKey();
			key1.setEntityId(consumerQueryDTO.getEntityId());
			key1.setFatherEntityId(consumerQueryDTO.getFatherEntityId());
			Issuer issuer = issuerDAO.selectByPrimaryKey(key1);
			if (issuer != null) {
				consumerDTO.setEntityId(issuer.getEntityId());
				consumerDTO.setFatherEntityId(issuer.getFatherEntityId());
				consumerDTO.setConsumerName(issuer.getIssuerName());
				consumerDTO.setConsumerEnglishName(issuer
						.getIssuerEnglishName());
				consumerDTO.setConsumerAddress(issuer.getIssuerAddress());
				consumerDTO.setConsumerEnglishAddress(issuer
						.getIssuerEnglishAddress());
				consumerDTO.setConsumerFax(issuer.getIssuerFax());
				consumerDTO.setConsumerPostcode(issuer.getIssuerPostcode());
				consumerDTO.setConsumerTelephone(issuer.getIssuerTelephone());
				return consumerDTO;
			}
			SellerKey key2 = new SellerKey();
			key2.setEntityId(consumerQueryDTO.getEntityId());
			key2.setFatherEntityId(consumerQueryDTO.getFatherEntityId());
			Seller seller = sellerDAO.selectByPrimaryKey(key2);
			if (seller != null) {
				consumerDTO.setEntityId(seller.getEntityId());
				consumerDTO.setFatherEntityId(seller.getFatherEntityId());
				consumerDTO.setConsumerName(seller.getSellerName());
				consumerDTO.setConsumerEnglishName(seller
						.getSellerEnglishName());
				consumerDTO.setConsumerAddress(seller.getSellerAddress());
				consumerDTO.setConsumerEnglishAddress(seller
						.getSellerEnglishAddress());
				consumerDTO.setConsumerFax(seller.getSellerFax());
				consumerDTO.setConsumerPostcode(seller.getSellerPostcode());
				consumerDTO.setConsumerTelephone(seller.getSellerTelephone());
				return consumerDTO;
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			logger.error(e);
			throw new BizServiceException("添加实体失败~！");
		}
		return consumerDTO;
	}

	public PageDataDTO queryEntityId(ConsumerQueryDTO consumerQueryDTO)
			throws BizServiceException {
		PageDataDTO pageDataDTO = new PageDataDTO();
		try {
			if (consumerQueryDTO.getEntityFlag() == null
					|| "".equals(consumerQueryDTO.getEntityFlag().trim())) {
				pageDataDTO = pageQueryDAO.query("CONSUMER.selectEntityId",
						consumerQueryDTO);
			} else if ("1".equals(consumerQueryDTO.getEntityFlag())) {
				pageDataDTO = pageQueryDAO.query(
						"CONSUMER.selectEntityIdFromIssuer", consumerQueryDTO);
			} else {
				pageDataDTO = pageQueryDAO.query(
						"CONSUMER.selectEntityIdFromSeller", consumerQueryDTO);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			logger.error(e);
			throw new BizServiceException("查询实体失败~！");
		}
		return pageDataDTO;
	}

	public PageDataDTO selectIssuer(ConsumerQueryDTO consumerQueryDTO)
			throws BizServiceException {
		PageDataDTO pageDataDTO = new PageDataDTO();
		try {
			pageDataDTO = pageQueryDAO.query("CONSUMER.selectIssuer",
					consumerQueryDTO);
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		return pageDataDTO;
	}

	public void insertResources(ConsumerDTO consumerDTO)
			throws BizServiceException {
		List<String> issueResources = Arrays.asList(consumerDTO
				.getResourceIds().split(","));
		List<IssueResource> issueResourceList = new ArrayList<IssueResource>();
		for (String resource : issueResources) {
			IssueResource issueResource = new IssueResource();
			issueResource.setIssueId(consumerDTO.getEntityId());
			issueResource.setRank("3");
			issueResource.setResourceId(resource);
			issueResource.setCreateUser(consumerDTO.getCreateUser());
			issueResource.setCreateTime(DateUtil.getCurrentTime());
			issueResource.setModifyUser(consumerDTO.getCreateUser());
			issueResource.setModifyTime(issueResource.getCreateTime());
			issueResource.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			issueResourceList.add(issueResource);
		}
		commonsDAO.batchInsert("TB_REL_ISSUE_RESOURCE.abatorgenerated_insert",
				issueResourceList);
	}

	public void deleteResource(ConsumerDTO consumerDTO)
			throws BizServiceException {
		IssueResourceExample example = new IssueResourceExample();
		example.createCriteria().andIssueIdEqualTo(consumerDTO.getEntityId())
				.andRankEqualTo("3");
		issueResourceDAO.deleteByExample(example);
	}

	@SuppressWarnings("unchecked")
	public String checkWebName(ConsumerDTO consumerDTO)
			throws BizServiceException {
		try {
			List<ConsumerDTO> consumerDTOList = (List<ConsumerDTO>) baseDAO
					.queryForList("CONSUMER", "selectWebSizeName", consumerDTO);

			if (consumerDTOList.size() > 0) {
				return consumerDTO.getWebsiteUserName() + "网站登录名已存在!";
			} else {
				return "网站登录名可用";
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			logger.error(e);
			throw new BizServiceException("查询失败!");
		}
	}

	public IssuerDAO getIssuerDAO() {
		return issuerDAO;
	}

	public void setIssuerDAO(IssuerDAO issuerDAO) {
		this.issuerDAO = issuerDAO;
	}

	public SellerDAO getSellerDAO() {
		return sellerDAO;
	}

	public void setSellerDAO(SellerDAO sellerDAO) {
		this.sellerDAO = sellerDAO;
	}

	public UserServiceDAO getUserServiceDAO() {
		return userServiceDAO;
	}

	public void setUserServiceDAO(UserServiceDAO userServiceDAO) {
		this.userServiceDAO = userServiceDAO;
	}

	public CaclInfService getCaclInfService() {
		return caclInfService;
	}

	public void setCaclInfService(CaclInfService caclInfService) {
		this.caclInfService = caclInfService;
	}

}
