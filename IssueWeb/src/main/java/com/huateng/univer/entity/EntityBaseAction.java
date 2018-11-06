package com.huateng.univer.entity;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;
import com.huateng.framework.action.BaseAction;

/**
 * 实体信息通用action
 * 
 * @author xxl
 * 
 */
public abstract class EntityBaseAction extends BaseAction {

	protected static final long serialVersionUID = -8468440175387822070L;

	// 查询信息DTO
	protected PageDataDTO pageDataDTO = new PageDataDTO();

	// 发票公司DTO
	protected InvoiceCompanyDTO invoiceCompanyDTO = new InvoiceCompanyDTO();
	// 发票地址信息DTO
	protected InvoiceAddressDTO invoiceAddressDTO = new InvoiceAddressDTO();
	// 联系人DTO
	protected ContactDTO contactDTO = new ContactDTO();
	// 部门DTO
	protected DepartmentDTO departmentDTO = new DepartmentDTO();
	// 投递点DTO
	protected DeliveryPointDTO deliveryPointDTO = new DeliveryPointDTO();
	// 发货人DTO
	protected DeliveryRecipientDTO deliveryRecipientDTO = new DeliveryRecipientDTO();
	//银行账户DTO
	protected BankDTO bankDTO=new BankDTO();
	// 实体id
	protected String entityId;
	// 不同子action的命名空间
	protected String nameSpace;
	// ID
	protected String[] chooseDepartmentId;
	protected String[] chooseContactId;
	protected String[] choosePointId;
	protected String[] chooseAddressId;
	protected String[] chooseCompanyId;
	protected String[] chooseDeliveryContactId;
	protected String[] chooseBankId;
	// 发票地址
	protected List<InvoiceAddressDTO> invoiceAddressList = new ArrayList<InvoiceAddressDTO>();
	// 发票公司
	protected List<InvoiceCompanyDTO> invoiceCompanyList = new ArrayList<InvoiceCompanyDTO>();
	// 快递点
	protected List<DeliveryPointDTO> deliveryPointList = new ArrayList<DeliveryPointDTO>();
	// 快递点联系人
	protected List<DeliveryRecipientDTO> deliveryRecipientList = new ArrayList<DeliveryRecipientDTO>();
	// 联系人
	protected List<ContactDTO> contractList = new ArrayList<ContactDTO>();
	// 部门
	protected List<DepartmentDTO> departmentList = new ArrayList<DepartmentDTO>();
	//银行
	protected List<BankDTO> bankList = new ArrayList<BankDTO>();
	
	protected String actionName;

	private String checkedId;
	public String getCheckedId() {
		return checkedId;
	}

	public void setCheckedId(String checkedId) {
		this.checkedId = checkedId;
	}

	/**
	 * 初始化每个实体的父实体id(entityId)
	 */
	public abstract void initEntityId() throws Exception;

	public abstract void initNameSpace() throws Exception;

	public abstract String reloadForEntity() throws Exception;

	/**
	 * 转向发票公司添加
	 */
	public String addCompany() throws Exception {
		return "addCompany";
	}

	/**
	 * 发票公司添加
	 */
	public String insertCompany() throws Exception {

		invoiceCompanyDTO.setEntityId(entityId);
		invoiceCompanyDTO.setCreateUser(getUser().getUserId());
		invoiceCompanyDTO.setModifyUser(getUser().getUserId());
		sendService(ConstCode.ENTITY_INVOICECOMPANY_SERVICE_INSERT,
				invoiceCompanyDTO);

		if (this.hasActionErrors()) {
			return INPUT;
		}

		addActionMessage("发票公司信息添加成功！");
		return "blank";
	}
	/**
	 * 跳转到发票公司编辑页面
	 */
	public String editCompany() throws Exception {
		invoiceCompanyDTO.setInvoiceCompanyId(checkedId);
		invoiceCompanyDTO = (InvoiceCompanyDTO)sendService(ConstCode.ISSUER_ENTITYCOMPANY_VIEW, invoiceCompanyDTO).getDetailvo();
		if (this.hasErrors()) {
			return INPUT;
		} 
		return "editCompany";
		
	}

	/**
	 * 更新发票公司信息
	 */
	public String updateCompany() throws Exception {
		invoiceCompanyDTO.setCreateUser(this.getUser().getUserId());
		invoiceCompanyDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.ISSUER_ENTITYCOMPANY_UPDATE, invoiceCompanyDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			addActionMessage("发票公司更新成功！");
		}

		return "blank";
	}
	/**
	 * 删除发票公司信息
	 */
	public String delCompany() throws Exception {
		List<InvoiceCompanyDTO> companyValueDTOs = new ArrayList<InvoiceCompanyDTO>();
		for (String id : chooseCompanyId) {
			invoiceCompanyDTO = new InvoiceCompanyDTO();
			invoiceCompanyDTO.setInvoiceCompanyId(id);
			companyValueDTOs.add(invoiceCompanyDTO);
		}
		invoiceCompanyDTO.setInvoiceCompanyDTO(companyValueDTOs);
		sendService(ConstCode.ENTITY_INVOICECOMPANY_SERVICE_DEL,
				invoiceCompanyDTO);
		if (hasErrors()) {
			return INPUT;
		}

		addActionMessage("发票公司信息删除成功！");
		return reloadForEntity();
	}

	/**
	 * 转向发票地址信息
	 */
	public String addAddress() throws Exception {
		return "addAddress";
	}

	/**
	 * 添加发票地址信息
	 */
	public String insertAddress() throws Exception {
		invoiceAddressDTO.setEntityId(entityId);
		invoiceAddressDTO.setCreateUser(this.getUser().getUserId());
		invoiceAddressDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.ENTITY_INVOICEADDRESS_SERVICE_INSERT,
				invoiceAddressDTO);
		if (this.hasErrors()) {
			return INPUT;
		}

		addActionMessage("地址信息添加成功！");
		return "blank";
	}
	/**
	 * 跳转到发票地址编辑页面
	 */
	public String editAddress() throws Exception {
		invoiceAddressDTO.setInvoiceAddressId(checkedId);
		invoiceAddressDTO = (InvoiceAddressDTO)sendService(ConstCode.INVOICEADDRESS_SERVICE_VIEW, invoiceAddressDTO).getDetailvo();
		if (this.hasErrors()) {
			return INPUT;
		} 
		return "editAddress";
		
	}

	/**
	 * 更新发票地址信息
	 */
	public String updateAddress() throws Exception {
		invoiceAddressDTO.setCreateUser(this.getUser().getUserId());
		invoiceAddressDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.INVOICEADDRESS_SERVICE_UPDATE,
				invoiceAddressDTO);
		if (this.hasErrors()) {
			return INPUT;
		}

		return "blank";
	}
	/**
	 * 删除发票地址
	 */
	public String delAddress() throws Exception {
		List<InvoiceAddressDTO> invoiceAddressDTOs = new ArrayList<InvoiceAddressDTO>();
		for (String id : chooseAddressId) {
			invoiceAddressDTO = new InvoiceAddressDTO();
			invoiceAddressDTO.setInvoiceAddressId(id);
			invoiceAddressDTOs.add(invoiceAddressDTO);
		}
		invoiceAddressDTO.setInvoiceAddressDTO(invoiceAddressDTOs);
		sendService(ConstCode.ENTITY_INVOICEADDRESS_SERVICE_DEL,
				invoiceAddressDTO);
		if (this.hasErrors()) {
			return INPUT;
		}

		addActionMessage("发票地址信息删除成功！");
		return reloadForEntity();
	}

	/**
	 * 转向联系人
	 */
	public String addContact() throws Exception {
		return "addContact";
	}

	/**
	 * 添加联系人
	 */
	public String insertContact() throws Exception {
		contactDTO.setEntityId(entityId);
		contactDTO.setCreateUser(this.getUser().getUserId());
		contactDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.ENTITY_CONTACT_SERVICE_INSERT, contactDTO);
		if (this.hasErrors()) {
			return INPUT;
		}

		addActionMessage("联系人信息添加成功！");
		return "blank";
	}
	
	/** 查看联系人信息 */
	public String viewContact() throws Exception {
		contactDTO.setContactId(checkedId);
		contactDTO = (ContactDTO) this.sendService(
				ConstCode.ENTITY_CONTACT_SERVICE_VIEW, contactDTO)
				.getDetailvo();
		return "viewContact";
	}
	
	/**
	 * 跳转到联系人编辑页面
	 */
	public String editContact() throws Exception {
		actionName="customer/updateContact.action";
		contactDTO.setContactId(checkedId);
		contactDTO = (ContactDTO) this.sendService(
				ConstCode.ENTITY_CONTACT_SERVICE_VIEW, contactDTO)
				.getDetailvo();
		
		return "editContact";
	}

	/**
	 * 更新联系人信息
	 */
	public String updateContact() throws Exception {
		sendService(ConstCode.ENTITY_CONTACT_SERVICE_UPDATE,
				contactDTO);
		if (this.hasErrors()) {
			return INPUT;
		}

		return "blank";
	}

	/**
	 * 删除联系人
	 */
	public String delContact() throws Exception {
		List<ContactDTO> contactDTOLists = new ArrayList<ContactDTO>();
		for (String id : chooseContactId) {
			contactDTO = new ContactDTO();
			contactDTO.setContactId(id);
			contactDTOLists.add(contactDTO);
		}
		contactDTO.setContactDTOList(contactDTOLists);
		sendService(ConstCode.ENTITY_CONTACT_SERVICE_DEL, contactDTO);
		if (this.hasErrors()) {
			return INPUT;
		}

		addActionMessage("联系人信息删除成功！");
		return reloadForEntity();
	}
	
	/**
	 * 转向部门信息
	 */
	public String addDepartment() throws Exception {
		return "addDepartment";
	}

	/**
	 * 部门信息添加
	 */
	public String insertDepartment() throws Exception {
		departmentDTO.setEntityId(entityId);
		departmentDTO.setCreateUser(this.getUser().getUserId());
		departmentDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.ENTITY_DEPARTMENT_SERVICE_INSERT, departmentDTO);
		if (this.hasErrors()) {
			return INPUT;
		}

		addActionMessage("部门信息添加成功！");
		return "blank";
	}

	/**
	 * 跳转到部门编辑页面
	 */
	public String editDepartment() throws Exception {
		departmentDTO.setDepartmentId(checkedId);
		departmentDTO = (DepartmentDTO)sendService(ConstCode.ISSUER_ENTITYDEPARTMENT_VIEW, departmentDTO).getDetailvo();
		if (this.hasErrors()) {
			return INPUT;
		} 
		return "editDepartment";
	}

	/**
	 * 更新部门信息
	 */
	public String updateDepartment() throws Exception {
		departmentDTO.setCreateUser(this.getUser().getUserId());
		departmentDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.ISSUER_ENTITYDEPARTMENT_UPDATE, departmentDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			addActionMessage("部门信息编辑成功！");
		}

		return "blank";
	}
	/**
	 * 删除部门信息
	 */
	public String delDepartment() throws Exception {
		List<DepartmentDTO> departDTOLists = new ArrayList<DepartmentDTO>();
		for (String id : chooseDepartmentId) {
			departmentDTO = new DepartmentDTO();
			departmentDTO.setDepartmentId(id);
			departDTOLists.add(departmentDTO);
		}
		departmentDTO.setDepartmentDTO(departDTOLists);
		sendService(ConstCode.ENTITY_DEPARTMENT_SERVICE_DEL, departmentDTO);
		if (this.hasErrors()) {
			return INPUT;
		}
		addActionMessage("部门信息删除成功！");
		return reloadForEntity();
	}

	/** 查看快递点信息 */
	public String viewDeliveryPoint() throws Exception {
		/*if(null!=choosePointId && choosePointId.length==1){
			deliveryPointDTO.setDeliveryId(choosePointId[0]);
		}*/
		deliveryPointDTO = (DeliveryPointDTO) this.sendService(
				ConstCode.ENTITY_DELIVERYPOINT_SERVICE_VIEW, deliveryPointDTO)
				.getDetailvo();

		if (null != deliveryPointDTO) {
			deliveryRecipientList = deliveryPointDTO.getRecipientList();
		}
		return "viewDeliveryPoint";
	}

	/**
	 * 转向投递点信息
	 */
	public String addDeliveryPoint() throws Exception {

		return "addDeliveryPoint";
	}

	/**
	 * 投递点信息添加
	 */
	public String insertDeliveryPoint() throws Exception {
		deliveryPointDTO.setEntityId(entityId);
		deliveryPointDTO.setCreateUser(this.getUser().getUserId());
		deliveryPointDTO.setModifyUser(this.getUser().getUserId());
		deliveryPointDTO = (DeliveryPointDTO)sendService(ConstCode.ENTITY_DELIVERYPOINT_SERVICE_INSERT,
				deliveryPointDTO).getDetailvo();
		if (this.hasErrors()) {
			return INPUT;
		}

		addActionMessage("投递点信息添加成功！");
//		return "blank";
		return editDeliveryPoint();
	}

	public String editDeliveryPoint() throws Exception {
		this.viewDeliveryPoint();
		return "editDeliveryPoint";
	}

	public String updateDeliveryPoint() throws Exception {
		deliveryPointDTO.setEntityId(entityId);
		sendService(ConstCode.ENTITY_DELIVERYPOINT_SERVICE_UPDATE,
				deliveryPointDTO);
		return "blank";
	}

	public String delDeliveryPoint() throws Exception {

		List<DeliveryPointDTO> deliveryPointDTOs = new ArrayList<DeliveryPointDTO>();
		for (String id : choosePointId) {
			deliveryPointDTO = new DeliveryPointDTO();
			deliveryPointDTO.setDeliveryId(id);
			deliveryPointDTOs.add(deliveryPointDTO);
		}
		deliveryPointDTO.setDeliveryPointDTOs(deliveryPointDTOs);
		this.sendService(ConstCode.ENTITY_DELIVERYPOINT_SERVICE_DEL,
				deliveryPointDTO);
		if (!hasActionErrors()) {
			addActionMessage("投递点信息删除成功！");
		}
		return this.reloadForEntity();
	}

	/**
	 * 快递点联系人信息添加
	 */

	/*
	 * public String addDeliveryContanct() throws Exception { return
	 * "addDeliveryContanct"; }
	 */

	public void validateInsertDeliveryContanct() throws Exception{
		this.editDeliveryPoint();
	}
	
	public String insertDeliveryContanct() throws Exception {
		deliveryRecipientDTO.setDeliveryPointId(deliveryPointDTO
				.getDeliveryId());
		deliveryRecipientDTO.setCreateUser(this.getUser().getUserId());
		deliveryRecipientDTO.setModifyUser(this.getUser().getUserId());
		 sendService(
				ConstCode.ENTITY_DELIVERYCONTACT_SERVICE_INSERT,
				deliveryRecipientDTO);
		if (this.hasErrors()) {
			return INPUT;
		}
		addActionMessage("快递点联系人添加成功！");
		return this.editDeliveryPoint();
	}

	public String delDeliveryContanct() throws Exception {

		List<DeliveryRecipientDTO> deliveryRecipientDTOs = new ArrayList<DeliveryRecipientDTO>();
		for (String id : chooseDeliveryContactId) {
			deliveryRecipientDTO = new DeliveryRecipientDTO();
			deliveryRecipientDTO.setDeliveryContactId(id);
			deliveryRecipientDTOs.add(deliveryRecipientDTO);
		}
		deliveryRecipientDTO.setDeliveryRecipientDTO(deliveryRecipientDTOs);
		this.sendService(ConstCode.ENTITY_DELIVERYCONTACT_SERVICE_DEL,
				deliveryRecipientDTO);
		return this.editDeliveryPoint();
	}
	//转向添加银行账户页面
	public String addBank() throws Exception{
		return "addBank";
	}
	//添加银行账户信息
	public String insertBank() throws Exception{
		bankDTO.setEntityId(entityId);
		bankDTO.setBankType("1");
		sendService(
				ConstCode.ENTITY_BANK_SERVICE_INSERT,
				bankDTO);
		if (this.hasErrors()) {
			return INPUT;
		}
		addActionMessage("银行账户添加成功！");
		return "blank";
	}
	//转向编辑银行账户页面
	public String editBank() throws Exception{
		bankDTO.setBankId(checkedId);
		bankDTO = (BankDTO)sendService(ConstCode.ENTITY_BANK_SERVICE_QUERY, bankDTO).getDetailvo();
		if (this.hasErrors()) {
			return INPUT;
		} 
		return "editBank";
	}
	//修改银行账户信息
	public String updateBank() throws Exception{
		sendService(ConstCode.ENTITY_BANK_SERVICE_UPDATE, bankDTO);
		if (this.hasErrors()) {
			return INPUT;
		} 
		return "blank";
	}
	//删除银行账户信息
	public String delBank() throws Exception{
		List<BankDTO> bankDTOs = new ArrayList<BankDTO>();
		for (String id : chooseBankId) {
			bankDTO = new BankDTO();
			bankDTO.setBankId(id);
			bankDTOs.add(bankDTO);
		}
		bankDTO.setBankDTOs(bankDTOs);
		this.sendService(ConstCode.ENTITY_BANK_SERVICE_DEL,
				bankDTO);
		if (this.hasErrors()) {
			return INPUT;
		}
		if (!hasActionErrors()) {
			addActionMessage("银行账户信息删除成功！");
		}
		return reloadForEntity();
	}
	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public InvoiceCompanyDTO getInvoiceCompanyDTO() {
		return invoiceCompanyDTO;
	}

	public void setInvoiceCompanyDTO(InvoiceCompanyDTO invoiceCompanyDTO) {
		this.invoiceCompanyDTO = invoiceCompanyDTO;
	}

	public InvoiceAddressDTO getInvoiceAddressDTO() {
		return invoiceAddressDTO;
	}

	public void setInvoiceAddressDTO(InvoiceAddressDTO invoiceAddressDTO) {
		this.invoiceAddressDTO = invoiceAddressDTO;
	}

	public ContactDTO getContactDTO() {
		return contactDTO;
	}

	public void setContactDTO(ContactDTO contactDTO) {
		this.contactDTO = contactDTO;
	}

	public DepartmentDTO getDepartmentDTO() {
		return departmentDTO;
	}

	public void setDepartmentDTO(DepartmentDTO departmentDTO) {
		this.departmentDTO = departmentDTO;
	}

	public DeliveryPointDTO getDeliveryPointDTO() {
		return deliveryPointDTO;
	}

	public void setDeliveryPointDTO(DeliveryPointDTO deliveryPointDTO) {
		this.deliveryPointDTO = deliveryPointDTO;
	}

	public DeliveryRecipientDTO getDeliveryRecipientDTO() {
		return deliveryRecipientDTO;
	}

	public void setDeliveryRecipientDTO(
			DeliveryRecipientDTO deliveryRecipientDTO) {
		this.deliveryRecipientDTO = deliveryRecipientDTO;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public List<InvoiceAddressDTO> getInvoiceAddressList() {
		return invoiceAddressList;
	}

	public void setInvoiceAddressList(List<InvoiceAddressDTO> invoiceAddressList) {
		this.invoiceAddressList = invoiceAddressList;
	}

	public List<InvoiceCompanyDTO> getInvoiceCompanyList() {
		return invoiceCompanyList;
	}

	public void setInvoiceCompanyList(List<InvoiceCompanyDTO> invoiceCompanyList) {
		this.invoiceCompanyList = invoiceCompanyList;
	}

	public List<DeliveryPointDTO> getDeliveryPointList() {
		return deliveryPointList;
	}

	public void setDeliveryPointList(List<DeliveryPointDTO> deliveryPointList) {
		this.deliveryPointList = deliveryPointList;
	}

	public List<ContactDTO> getContractList() {
		return contractList;
	}

	public void setContractList(List<ContactDTO> contractList) {
		this.contractList = contractList;
	}

	public List<DepartmentDTO> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<DepartmentDTO> departmentList) {
		this.departmentList = departmentList;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String[] getChooseDepartmentId() {
		return chooseDepartmentId;
	}

	public void setChooseDepartmentId(String[] chooseDepartmentId) {
		this.chooseDepartmentId = chooseDepartmentId;
	}

	public String[] getChooseContactId() {
		return chooseContactId;
	}

	public void setChooseContactId(String[] chooseContactId) {
		this.chooseContactId = chooseContactId;
	}

	public String[] getChoosePointId() {
		return choosePointId;
	}

	public void setChoosePointId(String[] choosePointId) {
		this.choosePointId = choosePointId;
	}

	public String[] getChooseAddressId() {
		return chooseAddressId;
	}

	public void setChooseAddressId(String[] chooseAddressId) {
		this.chooseAddressId = chooseAddressId;
	}

	public String[] getChooseCompanyId() {
		return chooseCompanyId;
	}

	public void setChooseCompanyId(String[] chooseCompanyId) {
		this.chooseCompanyId = chooseCompanyId;
	}

	public String[] getChooseDeliveryContactId() {
		return chooseDeliveryContactId;
	}

	public void setChooseDeliveryContactId(String[] chooseDeliveryContactId) {
		this.chooseDeliveryContactId = chooseDeliveryContactId;
	}

	public String[] getChooseBankId() {
		return chooseBankId;
	}

	public void setChooseBankId(String[] chooseBankId) {
		this.chooseBankId = chooseBankId;
	}

	public BankDTO getBankDTO() {
		return bankDTO;
	}

	public void setBankDTO(BankDTO bankDTO) {
		this.bankDTO = bankDTO;
	}

	public List<BankDTO> getBankList() {
		return bankList;
	}

	public void setBankList(List<BankDTO> bankList) {
		this.bankList = bankList;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

}
