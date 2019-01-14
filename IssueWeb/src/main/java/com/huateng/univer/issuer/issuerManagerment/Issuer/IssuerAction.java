package com.huateng.univer.issuer.issuerManagerment.Issuer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.allinfinance.univer.entity.dto.DeliveryRecipientDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;
import com.allinfinance.univer.issuer.dto.cardserialnumber.CardSerialNumberDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerDTO;
import com.allinfinance.univer.issuer.dto.issuer.IssuerQueryDTO;
import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.util.MD5EncryptAlgorithm;
import com.huateng.framework.webservice.service.SystemInfoService;

/**
 * 
 * @author fengfeng.shi
 * 
 */
public class IssuerAction extends BaseAction {
	private Logger logger = Logger.getLogger(IssuerAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IssuerQueryDTO issuerQueryDTO = new IssuerQueryDTO();
	private IssuerDTO issuerDTO = new IssuerDTO();
	private IssuerDTO issuerDTO_tmp=new IssuerDTO();
	// 发票公司DTO
	private InvoiceCompanyDTO invoiceCompanyDTO = new InvoiceCompanyDTO();
	// 发票地址信息DTO
	private InvoiceAddressDTO invoiceAddressDTO = new InvoiceAddressDTO();
	// 联系人DTO
	private ContactDTO contactDTO = new ContactDTO();
	// 部门DTO
	private DepartmentDTO departmentDTO = new DepartmentDTO();
    //投递点DTO
	private DeliveryPointDTO deliveryPointDTO=new DeliveryPointDTO();
	//发货人DTO
	private  DeliveryRecipientDTO deliveryRecipientDTO=new DeliveryRecipientDTO();
	//银行DTO
	private BankDTO bankDTO=new BankDTO();
	private String deliveryId;
	
	private String entityId;
	private EntitySystemParameterDTO entitySystemParameterDTO= new EntitySystemParameterDTO();
    
	/**
	 * 发票公司ID
	 */
	private List<String> chooseId = new ArrayList<String>();

	/**
	 * 发票地址id
	 */
	private List<String> invoiceAddressIdList = new ArrayList<String>();
	/**
	 * 联系人id
	 */
	private List<String> contactIdList = new ArrayList<String>();
	/**
	 * 部门id
	 */
	private List<String> departmentIdList = new ArrayList<String>();
	/**
	 * 投递点id
	 */
	private List<String> deliveryPointIdList = new ArrayList<String>();
	/**
	 * 银行id
	 */
	private List<String> bankIdList=new ArrayList<String>();
	/**
	 * 查询ID
	 */
	private List<String> choose = new ArrayList<String>();
	/**
	 * 查询信息DTO
	 */
	private PageDataDTO pageDataDTO = new PageDataDTO();

	private CardSerialNumberDTO cardSerialNumberDTO = new CardSerialNumberDTO();
	private List<CardSerialNumberDTO> cardSerialNumberDTOs = new ArrayList<CardSerialNumberDTO>();
	
	private String sucessMessage;
	private String password;
	private String repassword;
	private UserDTO userDTO;
	private SystemInfoService systemInfoService;
	private String menuList;
	private String nmenuList;

	private String checkedId;
	
	
	public String getCheckedId() {
		return checkedId;
	}


	public void setCheckedId(String checkedId) {
		this.checkedId = checkedId;
	}


	public String getNmenuList() {
		return nmenuList;
	}


	public BankDTO getBankDTO() {
		return bankDTO;
	}


	public void setBankDTO(BankDTO bankDTO) {
		this.bankDTO = bankDTO;
	}


	public List<String> getBankIdList() {
		return bankIdList;
	}


	public void setBankIdList(List<String> bankIdList) {
		this.bankIdList = bankIdList;
	}


	public void setNmenuList(String nmenuList) {
		this.nmenuList = nmenuList;
	}


	public String getMenuList() {
		return menuList;
	}


	public void setMenuList(String menuList) {
		this.menuList = menuList;
	}


	public SystemInfoService getSystemInfoService() {
		return systemInfoService;
	}


	public void setSystemInfoService(SystemInfoService systemInfoService) {
		this.systemInfoService = systemInfoService;
	}


	public UserDTO getUserDTO() {
		return userDTO;
	}


	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public IssuerDTO getIssuerDTO_tmp() {
		return issuerDTO_tmp;
	}


	public void setIssuerDTO_tmp(IssuerDTO issuerDTOTmp) {
		issuerDTO_tmp = issuerDTOTmp;
	}


	public String getRepassword() {
		return repassword;
	}


	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}


	public EntitySystemParameterDTO getEntitySystemParameterDTO() {
		return entitySystemParameterDTO;
	}


	public void setEntitySystemParameterDTO(
			EntitySystemParameterDTO entitySystemParameterDTO) {
		this.entitySystemParameterDTO = entitySystemParameterDTO;
	}


	/**
	 * 查询发行机构信息
	 * 
	 * @return
	 * @throws Exception
	 */
	
	
	
	
	public String inQuery(){
		try {
			ListPageInit(null, issuerQueryDTO);
			pageDataDTO = (PageDataDTO) sendService(ConstCode.ISSUER_SERVICE_INQUERY, issuerQueryDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (hasErrors()) {
				return INPUT;
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";
	}
	
	public String listIssuer(){
		try {
			ListPageInit(null, issuerQueryDTO);
			pageDataDTO = (PageDataDTO) sendService(ConstCode.ISSUER_SERVICE_LISTISSUER, issuerQueryDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (hasErrors()) {
				return INPUT;
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";
	}
	
	/**
	 * 发行机构选择
	 * @return
	 * @throws Exception
	 */
	public String issuerChoose() throws Exception {
		try {
			ListPageInit(null, issuerQueryDTO);
			pageDataDTO = (PageDataDTO) sendService(ConstCode.ISSUER_SERVICE_INQUERY, issuerQueryDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (hasErrors()) {
				return INPUT;
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "choose";
	}
	public String record() throws Exception{
		try{
			issuerQueryDTO.setEntityId(getUser().getEntityId());
			pageDataDTO=(PageDataDTO) sendService(ConstCode.QUERY_FOR_ISSUER, issuerQueryDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (hasErrors()) {
				return INPUT;
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
			return "input";
		}
		return "selectEntityId";
	}
	public String configEntiyId() throws Exception{
		try{
			issuerQueryDTO.setFatherEntityId(getUser().getEntityId());
			issuerDTO=(IssuerDTO) sendService(ConstCode.selectEntity_FOR_ISSUER, issuerQueryDTO).getDetailvo();
			if(hasActionErrors()){
				return "input";
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
	    return "configEntiyId";
	}
	/**
	 * 转向发行机构添加
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String reAdd() throws Exception {
		//判断用户是否为华腾数据的用户，add  zengfenghua  2011-3-7
		String eid=getUser().getEntityId();
		if("00000000".equals(eid)){
			getRequest().getSession().setAttribute("isHuaShu", "1");
		}else{
			getRequest().getSession().setAttribute("isHuaShu", "0");
		}
		List<String> flist=new ArrayList<String>();
		flist.add("0");
		flist.add("2");
		if("00000000".equals(eid)){
			flist.add("3");
			flist.add("4");
		}
		userDTO=new UserDTO();
		userDTO.setFunctionRoleId(flist);
		userDTO.setEntityId(getUser().getEntityId());
		userDTO.setIsSaleFlage("1");
		userDTO.setUserId(getUser().getUserId());
		List<ResourceDTO> resourceDTOs = (List<ResourceDTO>) sendService(
				ConstCode.ROLE_SERVICE_SELECTISSERRESOURCE,userDTO).getDetailvo();
		JSONArray jsonObject = JSONArray.fromObject(resourceDTOs);
		menuList = jsonObject.toString();
		if (hasErrors()) {
			return INPUT;
		}
		return "readd";
	}

	/**
	 * 发行机构添加
	 */
	public String insert() throws Exception {
		if(getRequest().getSession().getAttribute("isHuaShu").equals("2")){
		   checkIssuerIdBin();
		}   
        checkPassword();
		issuerDTO.setCreateUser(this.getUser().getUserId());
		issuerDTO.setUserPassword(MD5EncryptAlgorithm.md5(password));
		IssuerDTO issuerDTO1=issuerDTO;
		if (this.hasErrors()) {
			return INPUT;
		}
		issuerDTO = (IssuerDTO) sendService(ConstCode.ISSUER_SERVICE_INSERT,issuerDTO).getDetailvo();
		systemInfoService.initEntityDictInfo();
		if (this.hasErrors()) {
			issuerDTO=issuerDTO1;
			return INPUT;
		} else {
			editIssuer();
			addActionMessage("发行机构信息添加成功！");
		}
		return "edit";
	}
	public void validateInsert() throws Exception {
		if (issuerDTO.getEntityId() == null) {
			String id = choose.get(0);
			issuerDTO = new IssuerDTO();
			issuerDTO.setEntityId(id);
		}
		issuerDTO_tmp = (IssuerDTO) sendService(ConstCode.ISSUER_SERVICE_VIEW,
				issuerDTO).getDetailvo();
		JSONArray jsonObject = JSONArray.fromObject(issuerDTO_tmp.getResourceDTOs());
		menuList = jsonObject.toString();
		JSONArray jsonObject1 = JSONArray.fromObject(issuerDTO_tmp.getNresourceDTOs());
		nmenuList=jsonObject1.toString();
	}
	
	
	public void checkIssuerIdBin(){
		if((issuerDTO.getEntityId()!=null && !issuerDTO.getEntityId().matches("^[0-9]*$")) || 
				"请输入四位数字前缀".equals(issuerDTO.getEntityId().trim())){
			this.addFieldError("issuerDTO.entityId","请输入四位数字发卡机构前缀");
			return;
		}
	}
	public void checkPassword() {
		if ("".equals(password)) {
			this.addFieldError("password", "必须由字母和数字组成开头为字母(7-20)");
			return;
		}
		if ("".equals(repassword)) {
			this.addFieldError("repassword", "确认密码不能为空");
			return;
		}
		if (password != null) {
			if (!password.matches("[^\\s]{7,20}")) {
				this.addFieldError("password", "必须由字母和数字组成开头为字母不包含空格(7-20)");
				return;
			}
		}
		if (password != null && repassword != null) {
			if (!password.matches("^[a-zA-Z](?=.*?\\d)[a-zA-Z0-9]{6,19}")) {
				this.addFieldError("password", "必须由字母和数字组成开头为字母(7-20)");
				return;
			}
			if (!password.equals(repassword)) {
				this.addFieldError("repassword", "两次密码不相同");
				return;
			}
		}
	}
	public String issuerModifyPassword(){
    	try{
    		userDTO = (UserDTO) sendService(ConstCode.USER_SERVICE_VIEW, userDTO)
			.getDetailvo();
    		if(hasActionErrors()){
    			return "input";
    		}
    	}catch(Exception e){
    		this.logger.error(e.getMessage());
    	}
    	return "issuerModifyPassword";
    }
    public String issuerUpdatePassword(){
    	try{
    		userDTO.setModifyUser(getUser().getUserId());
    		userDTO.setUserPassword(MD5EncryptAlgorithm.md5(password));
    		sendService(ConstCode.USER_SERVICE_UPDATEUSER, userDTO);
    		if(hasActionErrors()){
    			return "input";
    		}else{
    			this.addActionMessage("修改密码成功");
    		}
    	}catch(Exception e){
    		this.logger.error(e.getMessage());
    	}
    	return inQuery();
    }
    public void validateIssuerUpdatePassword(){
    	this.checkPassword();
    }
	public String load() throws Exception {
		if (issuerDTO.getEntityId() == null) {
			String id = choose.get(0);
			issuerDTO = new IssuerDTO();
			issuerDTO.setEntityId(id);
		}
		issuerDTO.setDefaultEntityId(getUser().getEntityId());
		issuerDTO.setUserId(getUser().getUserId());
		issuerDTO = (IssuerDTO) sendService(ConstCode.ISSUER_SERVICE_VIEW,
				issuerDTO).getDetailvo();
		JSONArray jsonObject = JSONArray.fromObject(issuerDTO.getResourceDTOs());
		menuList = jsonObject.toString();
		JSONArray jsonObject1 = JSONArray.fromObject(issuerDTO.getNresourceDTOs());
		nmenuList=jsonObject1.toString();
		return "";
	}

	/**
	 * 转向发票公司添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String companyList() throws Exception {
		return "companyList";
	}

	/**
	 * 发票公司添加
	 * 
	 * @return
	 * @throws Exception
	 */

	public String companyAdd() throws Exception {
		String[] invoiceCompany = getRequest().getParameter("invoiceCompanyId").split(",");
		invoiceCompanyDTO = new InvoiceCompanyDTO();
		invoiceCompanyDTO.setInvoiceCompanyName(invoiceCompany[0]);
		invoiceCompanyDTO.setDefaultFlag(invoiceCompany[1]);
		invoiceCompanyDTO.setCreateUser(this.getUser().getUserId());
		invoiceCompanyDTO.setModifyUser(this.getUser().getUserId());
		invoiceCompanyDTO.setEntityId(issuerDTO.getEntityId());
		sendService(ConstCode.ISSUER_ENTITYCOMPANY_INSERT, invoiceCompanyDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			load();
			addActionMessage("发票公司信息添加成功！");
		}
		return "edit";
	}
	
	/**
	 * 转向发票公司编辑页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toInvoiceCompanyEdit() throws Exception {
		invoiceCompanyDTO.setInvoiceCompanyId(checkedId);
		invoiceCompanyDTO = (InvoiceCompanyDTO)sendService(ConstCode.ISSUER_ENTITYCOMPANY_VIEW, invoiceCompanyDTO).getDetailvo();
		if (this.hasErrors()) {
			return INPUT;
		} 
		return "editCompany";
	}
	

	/**
	 * 更新发票公司
	 * 
	 * @return
	 * @throws Exception
	 */
	public String invoiceCompanyUpdate() throws Exception {
		invoiceCompanyDTO.setCreateUser(this.getUser().getUserId());
		invoiceCompanyDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.ISSUER_ENTITYCOMPANY_UPDATE, invoiceCompanyDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			addActionMessage("发票公司更新成功！");
		}
		return "success";
	}
	

	/**
	 * 删除发票公司信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String companyDel() throws Exception {
		List<InvoiceCompanyDTO> companyValueDTOs = new ArrayList<InvoiceCompanyDTO>();
		for (String id : chooseId) {
			invoiceCompanyDTO = new InvoiceCompanyDTO();
			invoiceCompanyDTO.setInvoiceCompanyId(id);
			companyValueDTOs.add(invoiceCompanyDTO);
		}
		invoiceCompanyDTO.setInvoiceCompanyDTO(companyValueDTOs);
		sendService(ConstCode.ISSUER_ENTITYCOMPANY_DELETE, invoiceCompanyDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			load();
			addActionMessage("发票公司信息删除成功！");
		}
		return "edit";
	}

	/**
	 * 转向发票地址信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addressList() throws Exception {
		invoiceAddressDTO = new InvoiceAddressDTO();
		String id = getRequest().getParameter("customerId");
		invoiceAddressDTO.setEntityId(id);
		return "addresslist";
	}

	/**
	 * 添加发票地址信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addAddress() throws Exception {
		invoiceAddressDTO.setCreateUser(this.getUser().getUserId());
		invoiceAddressDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.INVOICEADDRESS_SERVICE_INSERT, invoiceAddressDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			addActionMessage("地址信息添加成功！");
		}
		return "success";
	}
	
	

	/**
	 * 转向发票地址信息编辑页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toInvoiceAddressEdit() throws Exception {
		invoiceAddressDTO.setInvoiceAddressId(checkedId);
		invoiceAddressDTO = (InvoiceAddressDTO)sendService(ConstCode.INVOICEADDRESS_SERVICE_VIEW, invoiceAddressDTO).getDetailvo();
		if (this.hasErrors()) {
			return INPUT;
		} 
		return "editInvoiceAddress";
	}
	

	/**
	 * 更新发票地址
	 * 
	 * @return
	 * @throws Exception
	 */
	public String invoiceAddressUpdate() throws Exception {
		invoiceAddressDTO.setCreateUser(this.getUser().getUserId());
		invoiceAddressDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.INVOICEADDRESS_SERVICE_UPDATE, invoiceAddressDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			addActionMessage("发票地址信息更新成功！");
		}
		return "success";
	}

	public String reLoad() throws Exception {
		if (issuerDTO.getEntityId() == null) {
			String id = choose.get(0);
			issuerDTO = new IssuerDTO();
			issuerDTO.setEntityId(id);
		}
		issuerDTO.setDefaultEntityId(getUser().getEntityId());
		issuerDTO.setUserId(getUser().getUserId());
		issuerDTO = (IssuerDTO) sendService(ConstCode.ISSUER_SERVICE_VIEW,issuerDTO).getDetailvo();
		JSONArray jsonObject = JSONArray.fromObject(issuerDTO.getResourceDTOs());
		menuList = jsonObject.toString();
		JSONArray jsonObject1 = JSONArray.fromObject(issuerDTO.getNresourceDTOs());
		nmenuList=jsonObject1.toString();
		if (this.hasErrors()) {
			return INPUT;
		} else {
			addActionMessage("信息编辑成功！");
		}
		return "edit";
	}
	
	public String editIssuer() throws Exception{
		if (issuerDTO.getEntityId() == null) {
			if (null != choose && choose.size() > 0) {
				String id = choose.get(0);
				issuerDTO = new IssuerDTO();
				issuerDTO.setEntityId(id);
			}
		}
		issuerDTO.setDefaultEntityId(getUser().getEntityId());
		issuerDTO.setUserId(getUser().getUserId());
		issuerDTO = (IssuerDTO) sendService(ConstCode.ISSUER_SERVICE_VIEW,issuerDTO).getDetailvo();
		JSONArray jsonObject = JSONArray.fromObject(issuerDTO.getResourceDTOs());
		menuList = jsonObject.toString();
		JSONArray jsonObject1 = JSONArray.fromObject(issuerDTO.getNresourceDTOs());
		nmenuList=jsonObject1.toString();
		return "edit";
	}
	
	/**
	 * 删除发票地址
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addressDel() throws Exception {
		List<InvoiceAddressDTO> invoiceAddressDTOs = new ArrayList<InvoiceAddressDTO>();
		for (String id : invoiceAddressIdList) {
			invoiceAddressDTO = new InvoiceAddressDTO();
			invoiceAddressDTO.setInvoiceAddressId(id);
			invoiceAddressDTOs.add(invoiceAddressDTO);
		}
		invoiceAddressDTO.setInvoiceAddressDTO(invoiceAddressDTOs);
		sendService(ConstCode.ISSUER_ENTITYADDRESS_DELETE, invoiceAddressDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			load();
			addActionMessage("发票地址信息删除成功！");
		}
		return "edit";
	}

	/**
	 * 转向联系人
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toContract() throws Exception {
		contactDTO = new ContactDTO();
		String id = getRequest().getParameter("customerId");
		contactDTO.setEntityId(id);
		return "contractlist";
	}
	

	/**
	 * 添加联系人
	 * 
	 * @return
	 * @throws Exception
	 */
	public String contactInsert() throws Exception {
		contactDTO.setCreateUser(this.getUser().getUserId());
		contactDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.ISSUER_ENTITYCONTACT_INSERT, contactDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			addActionMessage("联系人信息添加成功！");
		}
		return "success";
	}
	
	

	/**
	 * 转向联系人编辑页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toContactEdit() throws Exception {
		contactDTO.setContactId(checkedId);
		contactDTO = (ContactDTO)sendService(ConstCode.ISSUER_ENTITYCONTACT_VIEW, contactDTO).getDetailvo();
		if (this.hasErrors()) {
			return INPUT;
		} 
		return "editContract";
	}
	

	/**
	 * 更新联系人
	 * 
	 * @return
	 * @throws Exception
	 */
	public String contactUpdate() throws Exception {
		contactDTO.setCreateUser(this.getUser().getUserId());
		contactDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.ISSUER_ENTITYCONTACT_UPDATE, contactDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			addActionMessage("联系人信息更新成功！");
		}
		return "success";
	}
	
	/**
	 * 删除联系人
	 * 
	 * @return
	 * @throws Exception
	 */
	public String contractDel() throws Exception {
		List<ContactDTO> contactDTOLists = new ArrayList<ContactDTO>();
		for (String id : contactIdList) {
			contactDTO = new ContactDTO();
			contactDTO.setContactId(id);
			contactDTOLists.add(contactDTO);
		}
		contactDTO.setContactDTOList(contactDTOLists);
		sendService(ConstCode.ISSUER_ENTITYCONTACT_DELETE, contactDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			load();
			addActionMessage("联系人信息删除成功！");
		}
		return "edit";
	}
	/**
	 * 转向银行
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toBank() throws Exception {
		bankDTO = new BankDTO();
		String id = getRequest().getParameter("customerId");
		bankDTO.setEntityId(id);
		return "bankAdd";
	}
	/**
	 * 添加银行
	 * 
	 * @return
	 * @throws Exception
	 */
	public String bankInsert() throws Exception {
		sendService(ConstCode.ENTITY_BANK_SERVICE_INSERT, bankDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			addActionMessage("银行信息添加成功！");
		}
		return "success";
	}
	
	public void validateBankInsert(){
		String accountNo = bankDTO.getBankAccount();
		try {
			Long.valueOf(accountNo);
		} catch (Exception e) {
			addFieldError("bankDTO.bankAccount", "账户信息只能为数字");
		}
		
	}
	
	/**
	 * 编辑银行
	 * 
	 * @return
	 * @throws Exception
	 */
	public String bankEdit() throws Exception {
		bankDTO.setBankId(checkedId);
		bankDTO = (BankDTO)sendService(ConstCode.ENTITY_BANK_SERVICE_QUERY, bankDTO).getDetailvo();
		if (this.hasErrors()) {
			return INPUT;
		} 
		return "editBank";
	}

	public void validateBankUpdate(){
		validateBankInsert();
	}

	/**
	 * 更新银行
	 * 
	 * @return
	 * @throws Exception
	 */
	public String bankUpdate() throws Exception {
		sendService(ConstCode.ENTITY_BANK_SERVICE_UPDATE, bankDTO);
		if (this.hasErrors()) {
			return INPUT;
		} 
		return "success";
	}	
		
	/**
	 * 删除银行
	 * 
	 * @return
	 * @throws Exception
	 */
	public String bankDel() throws Exception{
		List<BankDTO> bankDTOLists = new ArrayList<BankDTO>();
		for (String id : bankIdList) {
			bankDTO = new BankDTO();
			bankDTO.setBankId(id);
			bankDTOLists.add(bankDTO);
		}
		bankDTO.setBankDTOs(bankDTOLists);
		sendService(ConstCode.ENTITY_BANK_SERVICE_DEL, bankDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			load();
			addActionMessage("银行信息删除成功！");
		}
		return "edit";
	}
	/**
	 * 转向部门信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toDepartMent() throws Exception {
		departmentDTO = new DepartmentDTO();
		String id = getRequest().getParameter("customerId");
		departmentDTO.setEntityId(id);
		return "departlist";
	}

	/**
	 * 部门信息添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String departmentInsert() throws Exception {
		departmentDTO.setCreateUser(this.getUser().getUserId());
		departmentDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.ISSUER_ENTITYDEPARTMENT_INSERT, departmentDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			addActionMessage("部门信息添加成功！");
		}
		return "success";
	}
	/**
	 * 部门信息编辑
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toDepartmentEdit() throws Exception {
		departmentDTO.setDepartmentId(checkedId);
		departmentDTO = (DepartmentDTO)sendService(ConstCode.ISSUER_ENTITYDEPARTMENT_VIEW, departmentDTO).getDetailvo();
		if (this.hasErrors()) {
			return INPUT;
		}
		return "editDepart";
	}
	/**
	 * 部门信息编辑
	 * 
	 * @return
	 * @throws Exception
	 */
	public String departmentUpdate() throws Exception {
		departmentDTO.setCreateUser(this.getUser().getUserId());
		departmentDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.ISSUER_ENTITYDEPARTMENT_UPDATE, departmentDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			addActionMessage("部门信息编辑成功！");
		}
		return "success";
	}

	/**
	 * 删除部门信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String departMentDel() throws Exception {
		List<DepartmentDTO> departDTOLists = new ArrayList<DepartmentDTO>();
		for (String id : departmentIdList) {
			departmentDTO = new DepartmentDTO();
			departmentDTO.setDepartmentId(id);
			departDTOLists.add(departmentDTO);
		}
		departmentDTO.setDepartmentDTO(departDTOLists);
		sendService(ConstCode.ISSUER_ENTITYDEPARTMENT_DELETE, departmentDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			load();
			addActionMessage("部门信息删除成功！");
		}
		return "edit";
	}

	/**
	 * 转向投递点信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toDeliveryPoint() throws Exception {
		deliveryPointDTO=new DeliveryPointDTO();
		String id = getRequest().getParameter("customerId");
		deliveryPointDTO.setEntityId(id);
		return "deliveryPoint";
	}
	
	/**
	 * 投递点信息添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String insertDeliveryPoint() throws Exception {
		deliveryPointDTO.setCreateUser(this.getUser().getUserId());
		deliveryPointDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.ISSUER_ENTITYDELIVERYPOINT_INSERT, deliveryPointDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			addActionMessage("投递点信息添加成功！");
		}
		return "success";
	}
	
	/**
	 * 快递点联系人信息添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String insertAndEdit() throws Exception {
		deliveryPointDTO.setCreateUser(this.getUser().getUserId());
		deliveryPointDTO.setModifyUser(this.getUser().getUserId());
		deliveryPointDTO=(DeliveryPointDTO)sendService(ConstCode.ISSUER_ENTITYDELIVERYCONTRACT_INSERT, deliveryPointDTO).getDetailvo();
		if (this.hasErrors()) {
			return INPUT;
		} else {
			addActionMessage("快递点联系人添加成功！");
		}
		return "edit";
	}
	
	/**
	 * 添加收货人信息
	 * @return
	 * @throws Exception
	 */
	public String insertRecipient() throws Exception {
		deliveryRecipientDTO.setCreateUser(this.getUser().getUserId());
		deliveryRecipientDTO.setModifyUser(this.getUser().getUserId());
		deliveryRecipientDTO.setDeliveryPointId(deliveryRecipientDTO.getDeliveryPointId().replace(",", "").trim());
		sendService(ConstCode.ISSUER_ENTITYDELIVERYRECEOT_INSERT, deliveryRecipientDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			addActionMessage("收货人信息添加成功！");
		}
		return  inqueryDeliveryInfo();
		
	}
	
	public String inqueryDeliveryInfo()throws Exception{
		deliveryRecipientDTO.setDeliveryPointId(deliveryRecipientDTO.getDeliveryPointId());
		deliveryPointDTO=(DeliveryPointDTO)sendService(ConstCode.ISSUER_ENTITYDELIVERYRECEOT_QUERY, deliveryRecipientDTO).getDetailvo();
		if (this.hasErrors()) {
			return INPUT;
		} 
		return "edit";
	}
	
	/**
	 * 更新投递点信息
	 * @return
	 * @throws Exception
	 */
	public String deliveryPointUpdate()throws Exception{
		deliveryPointDTO.setCreateUser(this.getUser().getUserId());
		deliveryPointDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.ISSUER_ENTITYDELIVERYRECEOT_UPDATE, deliveryPointDTO);
		if (this.hasErrors()) {
			return INPUT;
		} 
		return "success";
	}
	
	
	public String deliveryInfoEdit()throws Exception{
		deliveryRecipientDTO.setDeliveryPointId(deliveryId);
		deliveryPointDTO=(DeliveryPointDTO)sendService(ConstCode.ISSUER_ENTITYDELIVERYRECEOT_QUERY, deliveryRecipientDTO).getDetailvo();
		if (this.hasErrors()) {
			return INPUT;
		} 
		return "edit";
	}
	
	
	
	public String deleteRecipient()throws Exception{
    deliveryRecipientDTO.setCreateUser(this.getUser().getUserId());
    deliveryRecipientDTO.setModifyUser(this.getUser().getUserId());
	sendService(ConstCode.ISSUER_ENTITYDELIVERYRECEOT_DELETE, deliveryRecipientDTO);
	if (this.hasErrors()) {
	  return INPUT;
	 }
	 addActionMessage("收货人信息删除成功！");
	 return inqueryDeliveryInfo();	
    }
	
	
	
	/**
	 * 删除部门投递点信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deliveryPointDel() throws Exception {
		List<DeliveryPointDTO> deliveryDTOLists = new ArrayList<DeliveryPointDTO>();
		for (String id : deliveryPointIdList) {
			deliveryPointDTO = new DeliveryPointDTO();
			deliveryPointDTO.setDeliveryId(id);
			deliveryDTOLists.add(deliveryPointDTO);
		}
		deliveryPointDTO.setDeliveryPointDTOs(deliveryDTOLists);
		sendService(ConstCode.ISSUER_ENTITYDELIVERYPOINT_DELETE, deliveryPointDTO);
		if (this.hasErrors()) {
			return INPUT;
		} else {
			load();
			addActionMessage("快递点信息删除成功！");
		}
		return "edit";
	}
	
	
	/**
	 * 删除发行机构信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteIssuer() throws Exception {
		List<IssuerDTO> issuerDTOs = new ArrayList<IssuerDTO>();
		for (String id : choose) {
			issuerDTO = new IssuerDTO();
			issuerDTO.setEntityId(id);
			issuerDTOs.add(issuerDTO);
		}
		issuerDTO.setIssuerDTO(issuerDTOs);
		issuerDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.ISSUER_ENTITY_DELETE, issuerDTO);
		if (!this.hasErrors()) {
			addActionMessage("发行机构信息删除成功！");
		}
		return listIssuer();
	}

	
	public String updateIssuer()throws Exception{
		issuerDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.ISSUER_ENTITY_UPDATE,issuerDTO);
		if (this.hasErrors()) {
			issuerDTO = (IssuerDTO) sendService(ConstCode.ISSUER_SERVICE_VIEW,issuerDTO).getDetailvo(); 
			JSONArray jsonObject = JSONArray.fromObject(issuerDTO.getResourceDTOs());
			menuList = jsonObject.toString();
			JSONArray jsonObject1 = JSONArray.fromObject(issuerDTO.getNresourceDTOs());
			nmenuList=jsonObject1.toString();
			return INPUT;
		} else {
			addActionMessage("发行机构信息修改成功！");
		}
		return listIssuer();
	}
	public void validateUpdateIssuer() throws Exception {
		if (issuerDTO.getEntityId() == null) {
			String id = choose.get(0);
			issuerDTO = new IssuerDTO();
			issuerDTO.setEntityId(id);
		}
		issuerDTO_tmp = (IssuerDTO) sendService(ConstCode.ISSUER_SERVICE_VIEW,
				issuerDTO).getDetailvo();
		JSONArray jsonObject = JSONArray.fromObject(issuerDTO_tmp.getResourceDTOs());
		menuList = jsonObject.toString();
		JSONArray jsonObject1 = JSONArray.fromObject(issuerDTO_tmp.getNresourceDTOs());
		nmenuList=jsonObject1.toString();
	}
	
	public String addCardBin() throws Exception{
		try{
			issuerDTO = (IssuerDTO) this.sendService(ConstCode.ISSUER_SERVICE_INQUERY_CARDBIN, issuerDTO).getDetailvo();
			if(null!=issuerDTO){
				cardSerialNumberDTOs = issuerDTO.getCardSerialNumberDTOList();
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "addCardBin";
	}
	
	/**
	 * insertCardPin
	 * @return
	 */
	public String insertCardBin() throws Exception{
		try{
			
			 cardSerialNumberDTO.setIssuerId(issuerDTO.getEntityId());
			 //cardSerialNumberDTO.setIssuerCode(issuerDTO.getIssuerCode());
			 cardSerialNumberDTO.setSerialNumber("0");
			 sendService(ConstCode.ISSUER_SERVICE_INIT_CARDBIN,cardSerialNumberDTO).getDetailvo(); 
			
			 if(!this.hasErrors()){
				  sucessMessage ="添加卡BIN信息成功";
				  return "addCardBinSucess";
			 }
			 
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		 return addCardBin();
	}
	
	public String loadKey()throws Exception{
		logger.debug("entitySystemParameterDTO.ParameterCode:"+entitySystemParameterDTO.getParameterCode());
//		logger.debug(issuerDTO.getEntityId());
		logger.debug(entitySystemParameterDTO.getEntityId());
		if(!"00000000".equals(getUser().getEntityId())){
			entitySystemParameterDTO.setEntityId(null);
		}
//		if (parameterCode != null && !"".equals(parameterCode)) {
//			entitySystemParameterDTO.setParameterCode(parameterCode);
//		}
		entitySystemParameterDTO = (EntitySystemParameterDTO) sendService(
				ConstCode.ENTITY_SYSTEMPARAMETER_SERVICE_VIEW,
				entitySystemParameterDTO).getDetailvo();

		if (hasActionErrors()) {
			return "view";
		}

		return "view";
	}
	
	public String updateKey()throws Exception{
		sendService(ConstCode.ENTITY_SYSTEMPARAMETER_SERVICE_UPDATE,
				entitySystemParameterDTO).getDetailvo();
		// systemInfoService.initSystemParameter();

		if (hasActionErrors()) {
			return INPUT;
		}
		this.addActionMessage("更新系统参数成功！");
		return "blank";
	}
	
	
	/**
	 * issuerModifyUserState
	 * @return
	 */
	public String issuerModifyUserState() throws Exception{
			
			try{
	    		userDTO = (UserDTO) sendService(ConstCode.USER_SERVICE_VIEW, userDTO)
				.getDetailvo();
	    		if(hasActionErrors()){
	    			return "input";
	    		}
	    	}catch(Exception e){
	    		this.logger.error(e.getMessage());
	    	}
	    	return "issuerModifyUserState";
	}
	
	/**
	 * issuerModifyUserState
	 * @return
	 */
	public String issuerUpdateUserState() throws Exception{
			userDTO.setModifyUser(getUser().getUserId());
			sendService(ConstCode.USER_SERVICE_UPDATEUSER, userDTO);
    		if(hasActionErrors()){
    			return "input";
    		}
    		addActionMessage("用户信息修改成功!");
    		return inQuery();
	}
	
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public DeliveryRecipientDTO getDeliveryRecipientDTO() {
		return deliveryRecipientDTO;
	}

	public void setDeliveryRecipientDTO(DeliveryRecipientDTO deliveryRecipientDTO) {
		this.deliveryRecipientDTO = deliveryRecipientDTO;
	}

	public List<String> getDeliveryPointIdList() {
		return deliveryPointIdList;
	}

	public void setDeliveryPointIdList(List<String> deliveryPointIdList) {
		this.deliveryPointIdList = deliveryPointIdList;
	}

	public DeliveryPointDTO getDeliveryPointDTO() {
		return deliveryPointDTO;
	}

	public void setDeliveryPointDTO(DeliveryPointDTO deliveryPointDTO) {
		this.deliveryPointDTO = deliveryPointDTO;
	}

	public List<String> getDepartmentIdList() {
		return departmentIdList;
	}

	public void setDepartmentIdList(List<String> departmentIdList) {
		this.departmentIdList = departmentIdList;
	}

	public DepartmentDTO getDepartmentDTO() {
		return departmentDTO;
	}

	public void setDepartmentDTO(DepartmentDTO departmentDTO) {
		this.departmentDTO = departmentDTO;
	}

	public InvoiceCompanyDTO getInvoiceCompanyDTO() {
		return invoiceCompanyDTO;
	}

	public void setInvoiceCompanyDTO(InvoiceCompanyDTO invoiceCompanyDTO) {
		this.invoiceCompanyDTO = invoiceCompanyDTO;
	}

	private int totalRows = 0;

	public IssuerQueryDTO getIssuerQueryDTO() {
		return issuerQueryDTO;
	}

	public void setIssuerQueryDTO(IssuerQueryDTO issuerQueryDTO) {
		this.issuerQueryDTO = issuerQueryDTO;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public IssuerDTO getIssuerDTO() {
		return issuerDTO;
	}

	public void setIssuerDTO(IssuerDTO issuerDTO) {
		this.issuerDTO = issuerDTO;
	}

	public List<String> getChoose() {
		return choose;
	}

	public void setChoose(List<String> choose) {
		this.choose = choose;
	}

	public List<String> getChooseId() {
		return chooseId;
	}

	public void setChooseId(List<String> chooseId) {
		this.chooseId = chooseId;
	}

	public InvoiceAddressDTO getInvoiceAddressDTO() {
		return invoiceAddressDTO;
	}

	public void setInvoiceAddressDTO(InvoiceAddressDTO invoiceAddressDTO) {
		this.invoiceAddressDTO = invoiceAddressDTO;
	}

	public List<String> getInvoiceAddressIdList() {
		return invoiceAddressIdList;
	}

	public void setInvoiceAddressIdList(List<String> invoiceAddressIdList) {
		this.invoiceAddressIdList = invoiceAddressIdList;
	}

	public ContactDTO getContactDTO() {
		return contactDTO;
	}

	public void setContactDTO(ContactDTO contactDTO) {
		this.contactDTO = contactDTO;
	}

	public List<String> getContactIdList() {
		return contactIdList;
	}

	public void setContactIdList(List<String> contactIdList) {
		this.contactIdList = contactIdList;
	}


	public CardSerialNumberDTO getCardSerialNumberDTO() {
		return cardSerialNumberDTO;
	}


	public void setCardSerialNumberDTO(CardSerialNumberDTO cardSerialNumberDTO) {
		this.cardSerialNumberDTO = cardSerialNumberDTO;
	}


	public String getSucessMessage() {
		return sucessMessage;
	}


	public void setSucessMessage(String sucessMessage) {
		this.sucessMessage = sucessMessage;
	}


	public List<CardSerialNumberDTO> getCardSerialNumberDTOs() {
		return cardSerialNumberDTOs;
	}


	public void setCardSerialNumberDTOs(
			List<CardSerialNumberDTO> cardSerialNumberDTOs) {
		this.cardSerialNumberDTOs = cardSerialNumberDTOs;
	}

	
	/**
	 * ajax查看发卡机构前缀是否存在
	 */
	public void checkIssuerIdPin() {

		try {
			issuerDTO = (IssuerDTO) this.sendService(
					ConstCode.CHECK_ISSUER_ID_PIN, issuerDTO).getDetailvo();
			getResponse().setContentType("application/json; charset=utf-8");
			getResponse().setCharacterEncoding("utf-8");
			getResponse().getWriter().println(
					JSONObject.fromObject(issuerDTO).toString());
			getResponse().getWriter().close();
		} catch (IOException e) {
			this.logger.error(e.getMessage());
		}
	}
	
	
}
