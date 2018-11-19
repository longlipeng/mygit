package com.allinfinance.prepay.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allinfinance.prepay.dao.CommonsDAO;
import com.allinfinance.prepay.exception.BizServiceException;
import com.allinfinance.prepay.mapper.svc_mng.CustomerMapper;
import com.allinfinance.prepay.mapper.svc_mng.SellContractMapper;
import com.allinfinance.prepay.mapper.svc_mng.UserMapper;
import com.allinfinance.prepay.model.Customer;
import com.allinfinance.prepay.model.CustomerExample;
import com.allinfinance.prepay.model.SellContract;
import com.allinfinance.prepay.model.SellContractExample;
import com.allinfinance.prepay.model.User;
import com.allinfinance.prepay.model.UserExample;
import com.allinfinance.prepay.model.UserExample.Criteria;
import com.allinfinance.prepay.utils.DateUtil;
import com.allinfinance.prepay.utils.ReflectionUtil;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;


@Service
public class CustomerServiceImpl implements CustomerService {

	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private CommonsDAO commonsDAO;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private EntityContactService entityContactService;
	@Autowired
	private EntityDeliveryService deliveryPointService;
	@Autowired
	private DeliveryContactService deliveryContactService;
	@Autowired
	private InvoiceAddressService invoiceAddressService;
	@Autowired
	private InvoiceCompanyService invoiceCompanyService;
	@Autowired
	private SellContractMapper sellContractMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private BankService bankService;

	@Override
	public CustomerDTO getCustomerByIdNo(CustomerDTO customerDTO)
			throws BizServiceException {
		// TODO Auto-generated method stub
		CustomerDTO customer = customerMapper
				.selectCustomeInfoByIdNo(customerDTO);
		return customer;
	}

	@Override
	public CustomerDTO insertCustomer(CustomerDTO customerDTO)
			throws BizServiceException {

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
		customer.setDataState("1");
		customerMapper.insert(customer);
		ReflectionUtil.copyProperties(customer, customerDTO);

		// 默认插入部门信息
		DepartmentDTO entityDepartment = new DepartmentDTO();
		entityDepartment.setEntityId(entityId);
		entityDepartment.setDepartmentName(customer.getCustomerName());
		entityDepartment.setDefaultFlag("1");
		entityDepartment.setCreateUser(operateUser);
		entityDepartment.setModifyUser(operateUser);
		departmentService.insert(entityDepartment);

		// 默认插入联系人信息
		ContactDTO entityContact = new ContactDTO();
		entityContact.setEntityId(entityId);
		entityContact.setContactName(customer.getCustomerName());
		entityContact.setContactType("1");
		entityContact.setContactGender("1");
		// entityContact.setContactTelephone(customer.getCustomerTelephone());
		entityContact.setContactMobilePhone(customer.getCustomerTelephone());
		entityContact.setCreateUser(operateUser);
		entityContact.setModifyUser(operateUser);
		entityContact.setDefaultFlag("1");
		entityContact.setValidityFlag("1");
		entityContact.setStarValidity(customerDTO.getCorpCredStaValidity());
		entityContact.setEndValidity(customerDTO.getCorpCredEndValidity());
		entityContact.setPapersNo(customerDTO.getCorpCredId());
		entityContact.setPapersType(customerDTO.getCorpCredType());
		entityContactService.insert(entityContact);

		// 默认插入快递点信息
		DeliveryPointDTO entityDelivery = new DeliveryPointDTO();
		entityDelivery.setEntityId(entityId);
		entityDelivery.setDeliveryName(customer.getCustomerName());
		entityDelivery.setDeliveryPostcode(customer.getCustomerPostcode());
		entityDelivery.setDeliveryAddress(customer.getCustomerAddress());
		entityDelivery.setCreateUser(operateUser);
		entityDelivery.setModifyUser(operateUser);
		entityDelivery.setDefaultFlag("1");
		entityDelivery = deliveryPointService.insert(entityDelivery);

		// 默认快递点联系人（依赖于快递点）
		DeliveryRecipientDTO deliveryContact = new DeliveryRecipientDTO();
		deliveryContact.setDeliveryPointId(entityDelivery.getDeliveryId());
		deliveryContact.setDeliveryContact(entityDelivery.getDeliveryName());
		deliveryContact.setContactPhone(customer.getCustomerTelephone());
		deliveryContact.setCreateUser(operateUser);
		deliveryContact.setModifyUser(operateUser);
		deliveryContact.setDefaultFlag("1");
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
				.setDeliveryOption("1");
		invoiceAddress.setDefaultFlag("1");
		invoiceAddressService.insert(invoiceAddress);

		// 默认插入发票公司信息
		InvoiceCompanyDTO invoiceCompanyDTO = new InvoiceCompanyDTO();
		invoiceCompanyDTO.setEntityId(entityId);
		invoiceCompanyDTO.setInvoiceCompanyName(customer.getCustomerName());
		invoiceCompanyDTO.setCreateUser(operateUser);
		invoiceCompanyDTO.setModifyUser(operateUser);
		invoiceCompanyDTO.setDefaultFlag("1");
		invoiceCompanyService.insert(invoiceCompanyDTO);
		return customerDTO;
	}

	@Override
	public CustomerDTO viewCustomer(CustomerDTO customerDTO)
			throws BizServiceException {
		try {

			CustomerExample example = new CustomerExample();
			CustomerExample.Criteria criteria = example
					.createCriteria();
			criteria.andEntityIdEqualTo(customerDTO.getEntityId());
			if (null != customerDTO.getFatherEntityId()) {
				criteria.andFatherEntityIdEqualTo(customerDTO
						.getFatherEntityId());
			}
			Customer customer = new Customer();
			List<Customer> customerList = customerMapper.selectByExample(example);
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
						.andDataStateEqualTo("1");
				List<SellContract> scds = (List<SellContract>) sellContractMapper
						.selectByExample(sce);
				if (scds.size() > 0) {
					String custContract = scds.get(0).getSellContractId();
					customerDTO.setCustContract(custContract);
				}
			}
			// 关联实体信息
			/*customerDTO
					.setContractList(contactService.inqueryContact(entityId));
			customerDTO.setDeliveryPointList(deliveryPointService
					.inquery(entityId));
			customerDTO.setDepartmentList(departmentService.inquery(entityId));*/
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
	 * 初始化客户
	 */
	public CustomerDTO initCustomer(CustomerDTO customerDTO)
			throws BizServiceException {
		try {
			UserExample example = new UserExample();
			Criteria criteria = example.createCriteria().andIsSaleFlageEqualTo("q")
					.andDataStateEqualTo("1");
			if (null != customerDTO.getDefaultEntityId()
					&& !"".equals(customerDTO.getDefaultEntityId())) {
				criteria.andEntityIdEqualTo(customerDTO.getDefaultEntityId());
			}
			List<User> userList = userMapper.selectByExample(example);
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

	@Override
	public List<Customer> selectByExample(CustomerExample example) {
		return customerMapper.selectByExample(example);
	}

}
