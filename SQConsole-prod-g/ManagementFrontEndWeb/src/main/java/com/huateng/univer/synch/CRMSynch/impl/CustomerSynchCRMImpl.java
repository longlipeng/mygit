package com.huateng.univer.synch.CRMSynch.impl;

import java.util.List;

import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.ibatis.dao.CustomerEnterPriseDAO;
import com.huateng.framework.ibatis.dao.CustomerPersonalDAO;
import com.huateng.framework.ibatis.dao.UniqueUserIdDAO;
import com.huateng.framework.ibatis.model.CustomerEnterPrise;
import com.huateng.framework.ibatis.model.CustomerEnterPriseExample;
import com.huateng.framework.ibatis.model.CustomerEnterPriseKey;
import com.huateng.framework.ibatis.model.CustomerPersonal;
import com.huateng.framework.ibatis.model.CustomerPersonalExample;
import com.huateng.framework.ibatis.model.CustomerPersonalKey;
import com.huateng.framework.ibatis.model.UniqueUserId;
import com.huateng.framework.ibatis.model.UniqueUserIdExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.seller.customer.biz.service.CustomerService;
import com.huateng.univer.synch.CRMSynch.CustomerSynchCRM;

/**
 * 将通过审查的客户信息同步至CRM
 * 
 * @author jason
 *
 */
public class CustomerSynchCRMImpl implements CustomerSynchCRM {
	private UniqueUserIdDAO uniqueUserIdDAO;
	private CustomerEnterPriseDAO customerEnterPriseDAO;
	private CustomerPersonalDAO customerPersonalDAO;
	private CustomerService customerService;
	private CommonsDAO commonsDAO;

	@Override
	public void syncToCRM(CustomerDTO dto) throws Exception {
		dto = customerService.viewCustomer(dto);
		UniqueUserIdExample example = new UniqueUserIdExample();

		if ("1".equals(dto.getCustomerType())) {
			example.createCriteria().andTypeEqualTo("1").andIdNoEqualTo(dto.getCorpCredId()).andIdTypeEqualTo(judgeIdType(dto.getCorpCredType())).andCustomerIdEqualTo(dto.getEntityId()).andSystemIdEqualTo("1");
/*			record.setType("1");//个人类型
			record.setIdNo(dto.getCorpCredId());//个人证件号
			record.setIdType(dto.getCorpCredType());//证件类型
*/		} else if ("0".equals(dto.getCustomerType())) {
			example.createCriteria().andTypeEqualTo("2").andIdNoEqualTo(dto.getLicenseId()).andIdTypeEqualTo("4").andCustomerIdEqualTo(dto.getEntityId()).andSystemIdEqualTo("1");
/*			record.setType("2");//企业类型
			record.setIdNo(dto.getLicenseId());//企业三证合一
			record.setIdType(dto.getCompanyIdType());*/
		}

		List ids = uniqueUserIdDAO.selectByExample(example);
		if (ids == null || ids.size() == 0) {
			UniqueUserId record = new UniqueUserId();
			String id =commonsDAO.getNextValueOfSequence("CUSTOMER_UNIQUE_ID");
		//	record.setUniqueId(Long.valueOf(id));//id
			UniqueUserIdExample example1 = new UniqueUserIdExample();
			//int result = uniqueUserIdDAO.countByExample(example1);
			record.setUniqueId(new Long(id));
			record.setCustomerId(dto.getEntityId());
			record.setSystemId("1");
			if ("1".equals(dto.getCustomerType())) {
				record.setType("1");//个人类型
				record.setIdNo(dto.getCorpCredId());//个人证件号
				record.setIdType(judgeIdType(dto.getCorpCredType()));//证件类型
			} else if ("0".equals(dto.getCustomerType())) {
				record.setType("2");//企业类型
				record.setIdNo(dto.getLicenseId());//企业三证合一
				record.setIdType("4");
			}
			uniqueUserIdDAO.insertSelective(record);
		}
		UniqueUserId id = (UniqueUserId) uniqueUserIdDAO.selectByExample(example).get(0);
		if ("1".equals(id.getType())) {
			syncPersonToCRM(dto, id);// 个人同步
		} else if ("2".equals(id.getType())) {
			syncEnterpriseToCRM(dto, id);//// 企业同步
		}

	}

	// 个人信息同步
	private void syncPersonToCRM(CustomerDTO dto, UniqueUserId id) {
		CustomerPersonalExample example = new CustomerPersonalExample();
		example.createCriteria().andUniqueIdEqualTo(id.getUniqueId());
		List<CustomerPersonal> customerList = customerPersonalDAO.selectByExample(example);
		if (customerList == null||customerList.size()==0) {
			CustomerPersonal customer = new CustomerPersonal();
			customer.setUniqueId(id.getUniqueId());
			createPerson(dto, customer);
			customerPersonalDAO.insertSelective(customer);
		} else {
			// update
			CustomerPersonal customer = customerList.get(0);
			customer.setUniqueId(id.getUniqueId());
			createPerson(dto, customer);
			customerPersonalDAO.updateByPrimaryKeySelective(customer);
		}
	}

	// 企业信息同步
	private void syncEnterpriseToCRM(CustomerDTO dto, UniqueUserId id) {
		CustomerEnterPriseExample example = new CustomerEnterPriseExample();
		example.createCriteria().andCustomerIdEqualTo(dto.getEntityId()).andSystemIdEqualTo("1");
		List<CustomerEnterPrise> customers = (List<CustomerEnterPrise>)customerEnterPriseDAO.selectByExample(example);
		if (customers == null||customers.size()==0) {
			CustomerEnterPrise customer = new CustomerEnterPrise();
			customer.setUniqueId(id.getUniqueId());
			createEnterprise(dto,customer);
			customerEnterPriseDAO.insertSelective(customer);
		} else {
			CustomerEnterPrise customer =  customers.get(0);
			createEnterprise(dto, customer);
			customerEnterPriseDAO.updateByPrimaryKeySelective(customer);
		}
	}


	// 创建企业对象
	private void createEnterprise(CustomerDTO dto, CustomerEnterPrise customer) {
		ReflectionUtil.copyProperties(dto, customer);
		customer.setCustomerId(dto.getEntityId());// 客户id(*)
		customer.setSystemId("1");// 多用途(*)
		customer.setEnterpriseCname(dto.getCustomerName());// 公司名称(*)
		customer.setEnterpriseEname(dto.getCompanyEnglishname());
		customer.setLegalIdType(judgeIdType(dto.getCorpCredType()));// 法人证件类型(*)
		customer.setLegalIdNo(dto.getCorpCredId());// 法人证件号(*)
		customer.setLegalIdValidity(DateUtil.string2Dateyyyymmdd(dto.getCorpCredEndValidity()));// 法人证件有效期(*)
		customer.setLegalName(dto.getCorpName());// 法人姓名(*)
		customer.setLegalAlias(dto.getCorpAliasName());//法人别名
		customer.setLegalMobile(dto.getCorpPhone());// 法人电话(*)
		customer.setLegalGender(dto.getGender());// 法人性别(*)
		customer.setLegalBirthday(DateUtil.string2date(dto.getCorpBirthday()));// 法人出生日期(*)
		customer.setLegalNationality(dto.getCorpCountyr());// 法人国籍(*)
		customer.setLegalAddress(dto.getCorpAddress());// 法人住址(*)
		customer.setLegalOccupation(dto.getCorpProfession());// 法人职业(*)
		customer.setEnterpriseCountry(dto.getCompanyCountyr());// 企业国别(*)
		customer.setEnterpriseRegistry(dto.getCompanyRegisteredAddress());// 企业注册地(*)
		customer.setEnterpriseIdType("4");// 企业证件类型(*)
		customer.setEnterpriseIdNo(dto.getCompanyId());// 企业证件号(*)
		customer.setEnterpriseAccountant(dto.getCompanyAccountant());// 公司会计
		customer.setEnterpriseDesc(dto.getCompanyDescription());// 公司描述，经营范围(*)
		customer.setAccountNo(dto.getExternalId());// 关联账号
		customer.setAreaCode(dto.getAcceptArea() == null ? "@n" : dto.getAcceptArea());// 受理区域(*)
		customer.setCreateTimestamp(DateUtil.longfromyyyyMMddhhmmss(dto.getCreateTime()));// 创建的时间戳
		customer.setCreateTime(dto.getCreateTime());
		customer.setCreator(dto.getCreateUser());// 创建人
		customer.setUpdateTimestamp((DateUtil.longfromyyyyMMddhhmmss(dto.getModifyTime())));// 修改日期戳
		customer.setUpdateTime(dto.getModifyTime());
		customer.setUpdater(dto.getModifyUser());
		customer.setStatus(dto.getCusState());
		customer.setIndustry(dto.getActivitySector());// 客户行业
		customer.setAgencyName(dto.getOperatorName());// 代办人
		customer.setAgencyIdtype(judgeIdType(dto.getOperatorType()));// 代办人证件类型
		customer.setAgencyIdno(dto.getOperatorId());
		customer.setAgencyIdvalidity(DateUtil.string2Dateyyyymmdd(dto.getOperatorValidity()));
		customer.setBankAccountNo(dto.getBankAccount());
		customer.setBankName(dto.getBankName());
		customer.setCmail(dto.getEmail());
		customer.setLinkphone(dto.getLinkphone());
		customer.setScal(dto.getCustomerSize());// 企业规模
		customer.setRegCptl(dto.getRegiCapital());
		customer.setCtidEdt(dto.getCtidEdt());// 主体证件有效期，
	}

	// 创建个人对象
	private void createPerson(CustomerDTO dto, CustomerPersonal customer) {
		ReflectionUtil.copyProperties(dto, customer);
		customer.setCustomerId(dto.getEntityId());// 客户id(*)
		customer.setSystemId("1");// 此系统为多用途(*)
		customer.setIdType(judgeIdType(dto.getCorpCredType()));// 证件类型
		customer.setIdNo(dto.getCorpCredId());// 证件号
		customer.setIdValidity(DateUtil.string2Dateyyyymmdd(dto.getCorpCredEndValidity()));// 证件有效期
		customer.setCustomerName(dto.getCorpName());// 客户姓名
		customer.setCustomerAlias(dto.getCorpAliasName());// 客户别名
		customer.setMobile(dto.getCustomerTelephone());// 联系电话(*)
		customer.setGender(dto.getGender());
		customer.setBirthday(DateUtil.string2Dateyyyymmdd(dto.getCorpBirthday()));
		// customer.setNationality(dto.getNationality());
		customer.setAddress(dto.getCorpAddress());
		customer.setOccupation(dto.getAwareness());// 职业
		customer.setAccountNo(dto.getExternalId());// 关联账号
		customer.setAreaCode(dto.getAcceptArea() == null ? "@n" : dto.getAcceptArea());// 受理区域(*)
		customer.setCreateTimestamp(DateUtil.longfromyyyyMMddhhmmss(dto.getCreateTime()));//创建的时间戳
		customer.setCreateTime(dto.getCreateTime());
		customer.setCreator(dto.getCreateUser());//创建人
		customer.setUpdateTimestamp((DateUtil.longfromyyyyMMddhhmmss(dto.getModifyTime())));//修改日期戳
		customer.setUpdateTime(dto.getModifyTime());
		customer.setUpdater(dto.getModifyUser());
		customer.setStatus(dto.getCusState());
		//customer.setEducation(dto.getEducation());
		//customer.setMarriaged(dto.getMarriage());
		//customer.setEmail(dto.getEmail());
		customer.setCloseDate(dto.getCloseDateDate());
		// customer.setNation(dto.getNation());
	}

	/**
	 * 根据传入的证件类型,产生crm对应的证件类型 1:身份证 2:护照 3:其他
	 * 
	 * @param id
	 * @return
	 */
	private String judgeIdType(String id) {
		if ("1".equals(id)) {
			return "1";
		} else if ("4".equals(id)) {
			return "2";
		}
		return "3";
	}
	public UniqueUserIdDAO getUniqueUserIdDAO() {
		return uniqueUserIdDAO;
	}

	public void setUniqueUserIdDAO(UniqueUserIdDAO uniqueUserIdDAO) {
		this.uniqueUserIdDAO = uniqueUserIdDAO;
	}

	public CustomerEnterPriseDAO getCustomerEnterPriseDAO() {
		return customerEnterPriseDAO;
	}

	public void setCustomerEnterPriseDAO(CustomerEnterPriseDAO customerEnterPriseDAO) {
		this.customerEnterPriseDAO = customerEnterPriseDAO;
	}

	public CustomerPersonalDAO getCustomerPersonalDAO() {
		return customerPersonalDAO;
	}

	public void setCustomerPersonalDAO(CustomerPersonalDAO customerPersonalDAO) {
		this.customerPersonalDAO = customerPersonalDAO;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}

}
