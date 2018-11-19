package com.huateng.univer.seller.customerverifier;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.JudgeInforDTO;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.customer.CustomerQueryDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerProductContractDTO;
import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.util.SystemInfo;
import com.huateng.univer.entity.EntityBaseAction;
import com.huateng.univer.system.dictinfo.DictInfoAction;

import net.sf.json.JSONObject;

public class CustomerVerifierAction extends EntityBaseAction {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(CustomerVerifierAction.class);
	private CustomerDTO customerDTO = new CustomerDTO();
	
	private CustomerQueryDTO customerQueryDTO = new CustomerQueryDTO();
	private PageDataDTO pageDataDTO = new PageDataDTO();
	private List<UserDTO> salesmanList = new ArrayList<UserDTO>();
	private Integer totalRows;
	private String[] customerIds;
	private DictInfoAction dictInfoAction;
	private String personFlag;
	private String operateType;
	private String customEntityId;
	protected SellerContractDTO sellerContractDTO = new SellerContractDTO();

	protected List<SellerProductContractDTO> productContractDTOs = new ArrayList<SellerProductContractDTO>();
	
	public List<SellerProductContractDTO> getProductContractDTOs() {
		return productContractDTOs;
	}

	public void setProductContractDTOs(
			List<SellerProductContractDTO> productContractDTOs) {
		this.productContractDTOs = productContractDTOs;
	}

	public SellerContractDTO getSellerContractDTO() {
		return sellerContractDTO;
	}

	public void setSellerContractDTO(SellerContractDTO sellerContractDTO) {
		this.sellerContractDTO = sellerContractDTO;
	}

	public String getPersonFlag() {
		return personFlag;
	}

	public void setPersonFlag(String personFlag) {
		this.personFlag = personFlag;
	}

	public DictInfoAction getDictInfoAction() {
		return dictInfoAction;
	}

	public void setDictInfoAction(DictInfoAction dictInfoAction) {
		this.dictInfoAction = dictInfoAction;
	}

	

	


	/**
	 * 初始化每个实体的父实体id(entityId)
	 */
	@Override
	public void initEntityId() throws Exception {
		entityId = customerDTO.getEntityId();
	}

	@Override
	public void initNameSpace() throws Exception {
		nameSpace = "customer";
	}

	@Override
	public String reloadForEntity() throws Exception {
		return this.edit();
	}

	/**
	 * 返回销售区域列表
	 * 
	 * @return
	 */
	// public JSONObject getSalesRegions() {
	// Map<String, List<DictInfoDTO>> salesRegions = new HashMap<String,
	// List<DictInfoDTO>>();
	// List<DictInfoDTO> dictInfoDTOList = SystemInfo.getDictList("405");
	// if (dictInfoDTOList != null) {
	// for (DictInfoDTO dictInfoDTO : dictInfoDTOList) {
	// salesRegions.put(dictInfoDTO.getDictId(), SystemInfo
	// .getSubDictList("408", new Integer(dictInfoDTO
	// .getDictId())));
	// }
	// }
	// return JSONObject.fromObject(salesRegions);
	// }

	/**
	 * 返回销售区域列表
	 * 
	 * @return
	 */
	public JSONObject getSalesRegions() throws Exception {
		//dictInfoAction.init();
		Map<String, List<EntityDictInfoDTO>> salesRegions = new HashMap<String, List<EntityDictInfoDTO>>();
		List<EntityDictInfoDTO> dictInfoDTOList = SystemInfo.getDictList(
				getUser().getEntityId(), "405");
		if (dictInfoDTOList != null) {
			for (EntityDictInfoDTO dictInfoDTO : dictInfoDTOList) {
				salesRegions.put(dictInfoDTO.getDictCode(), SystemInfo
						.getSubDictList(getUser().getEntityId(), "408",
								new Integer(dictInfoDTO.getDictCode())));
			}
		}
		return JSONObject.fromObject(salesRegions);
	}

	public String list() {
		try {
			ListPageInit(null, customerQueryDTO);
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.CUSTOMER_SERVICE_INQUERY, customerQueryDTO)
					.getDetailvo();

			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (this.hasErrors()) {
				this.addActionError("查询客户信息失败!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";
	}

	public String inquery() {
		try {
			ListPageInit(null, customerQueryDTO);
			customerQueryDTO.setFlag("1");
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.CUSTOMER_SERVICE_INQUERY, customerQueryDTO)
					.getDetailvo();
			deleteEnterpriseJob(pageDataDTO.getData());
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";
	}
	private void deleteEnterpriseJob(List list) {
		if(list==null||list.size()==0) {
			return ;
		}
		for(int i=0;i<list.size();i++) {
			Map<String, String> map=(Map<String, String>)list.get(i);
			if(map.get("customerType").equals("0")) {
				map.remove("awareness");
			}else {
				map.remove("activitySector");
			}
		}
	}

	public String view() throws Exception {
		if (null == customerDTO.getEntityId()) {
			if (null != customerIds && customerIds.length == 1) {
				String[] keyId = customerIds[0].split(",");
				customerDTO.setEntityId(keyId[0]);
				customerDTO.setFatherEntityId(keyId[1]);
			}
		}
		customerDTO.setCustContract(personFlag);
		customerDTO = (CustomerDTO) this.sendService(
				ConstCode.CUSTOMER_SERVICE_VIEW, customerDTO).getDetailvo();
		if (null != customerDTO.getCloseDate()
				&& !"".equals(customerDTO.getCloseDate())) {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			customerDTO.setCloseDateDate(df.parse(customerDTO.getCloseDate()));
		}

		if (customerDTO != null) {
			salesmanList = customerDTO.getSalesmanList();
			invoiceAddressList = customerDTO.getInvoiceAddressList();
			invoiceCompanyList = customerDTO.getInvoiceCompanyList();
			deliveryPointList = customerDTO.getDeliveryPointList();
			contractList = customerDTO.getContractList();
			departmentList = customerDTO.getDepartmentList();
			bankList = customerDTO.getBankList();
			this.initEntityId();
		}
		this.initNameSpace();
		if (hasActionErrors()) {
			return INPUT;
		}
		
		// 如果是审核客户，判断是否查到有合同号
		if(personFlag!=null){
			if(customerDTO.getCustContract().equals("1")){
				this.addActionError("未添加合同的客户不能审核！");
				return this.inquery();
			}
		}
		if (customerDTO.getCustContract() != null
				&& !"".equals(customerDTO.getCustContract())) {
			sellerContractDTO.setSellContractId(customerDTO.getCustContract());
			sellerContractDTO = (SellerContractDTO) sendService(
					ConstCode.SELLER_CONTRACT_SERVICE_VIEW, sellerContractDTO)
					.getDetailvo();

			if (null != sellerContractDTO) {
				productContractDTOs = sellerContractDTO.getProDTOs();

			}
		}
		if (hasActionErrors()) {
			return INPUT;
		}
		if ("1".equals(customerDTO.getCustomerType())) {
			return "viewPer";
		} else {
			return "view";
		}
	}

	public String add() throws Exception {
		customerDTO = (CustomerDTO) this.sendService(
				ConstCode.CUSTOMER_SERVICE_INIT, customerDTO).getDetailvo();
		if (customerDTO != null) {
			salesmanList = customerDTO.getSalesmanList();
		}
		if ("1".equals(this.getPersonFlag())) {
			return "addPerson";
		} else {
			return "add";
		}
	}

	public void validateInsert() throws Exception {
		this.add();
	}

	public String insert() throws Exception {
		if (null != customerDTO.getCloseDateDate()) {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			customerDTO.setCloseDate(df.format(customerDTO.getCloseDateDate()));
		}
		customerDTO = (CustomerDTO) this.sendService(
				ConstCode.CUSTOMER_SERVICE_INSERT, customerDTO).getDetailvo();
		if (hasActionErrors()) {
			return INPUT;
		}
		view();
		this.addActionMessage("添加客户成功！");
		if ("1".equals(customerDTO.getCustomerType())) {
			return "editPer";
		} else {
			return "edit";
		}
	}

	public String edit() throws Exception {
		if (customEntityId != null){
			customerDTO.setEntityId(customEntityId);
		}
		 view();
		
		if ("1".equals(customerDTO.getCustomerType())) {
			return "editPer";
		} else {
			return "edit";
		}
	}

	public void validateUpdate() throws Exception {
		if(customerDTO.getCorpCredStaValidity() != null && customerDTO.getCorpCredEndValidity() != null){
			if(customerDTO.getCorpCredStaValidity().compareTo(customerDTO.getCorpCredEndValidity()) > 0)
				this.addFieldError("customerDTO.corpCredEndValidity", "结束时间不能早于开始时间");
		}
		if(customerDTO.getLicenseStaValidity() != null && customerDTO.getLicenseEndValidity() != null){
			if(customerDTO.getLicenseStaValidity().compareTo(customerDTO.getLicenseEndValidity()) > 0)
				this.addFieldError("customerDTO.licenseEndValidity", "结束时间不能早于开始时间");
		}
		
	}

	public String update() throws Exception {
		// if (null != customerDTO.getCloseDateDate()) {
		// DateFormat df = new SimpleDateFormat("yyyyMMdd");
		// customerDTO.setCloseDate(df.format(customerDTO.getCloseDateDate()));
		// }
		this.add();
		customerDTO.setCusState("2");
		this.sendService(ConstCode.CUSTOMER_SERVICE_UPDATE, customerDTO);
		if (!hasActionErrors()) {
			this.addActionMessage("复核客户成功！");
		}

		return this.inquery();
	}

	public String modifyState() throws Exception {
		this.view();
		return "modifyState";
	}
	
	public void validatePass() throws Exception {
		if(customerDTO.getCorpCredStaValidity() != null && customerDTO.getCorpCredEndValidity() != null){
			if(customerDTO.getCorpCredStaValidity().compareTo(customerDTO.getCorpCredEndValidity()) > 0)
				this.addFieldError("customerDTO.corpCredEndValidity", "结束时间不能早于开始时间");
		}
		if(customerDTO.getLicenseStaValidity() != null && customerDTO.getLicenseEndValidity() != null){
			if(customerDTO.getLicenseStaValidity().compareTo(customerDTO.getLicenseEndValidity()) > 0)
				this.addFieldError("customerDTO.licenseEndValidity", "结束时间不能早于开始时间");
		}
	}
	
	public String delete() throws Exception {
		if (customerIds != null && customerIds.length > 0) {
			customerDTO.setCustomerIds(customerIds);
			this.sendService(ConstCode.CUSTOMER_SERVICE_DELETE, customerDTO);
			if (!hasActionErrors()) {
				this.addActionMessage("删除客户成功！");
			}
		}
		return this.inquery();
	}
	
	public String blockListOrRiskCheck() throws Exception {
		String resultPage = view();
		if (!hasActionErrors()) {
			StringBuffer buffer = new StringBuffer("匹配用户黑名单成功!");
			String blackList = customerDTO.getIsblacklist() == null ? "0" : customerDTO.getIsblacklist();
			if (blackList.equals("0")) {
				buffer.append("该用户未上黑名单!");
				this.addActionMessage(buffer.toString());
			} else if (blackList.equals("1")) {
				buffer.append("该用户为黑名单上的用户!");
				this.addActionError(buffer.toString());
			} else {
				buffer.append("但结果未知!");
				this.addActionMessage(buffer.toString());
			}
			
		}else {
			this.addActionError("调用黑名单数据接口失败！");
		}
		return resultPage;
	}

	public String pass() throws Exception {
		// 进行黑名单判断和获取风险等级 AUDIT_BLACK_CHECK：黑名单校验 AUDIT_RISK_GRADE：风控判断
		if (Const.AUDIT_BLACK_CHECK.equals(operateType) || Const.AUDIT_RISK_GRADE.equals(operateType)) {
			JudgeInforDTO dto =new JudgeInforDTO();
			dto.setJudgeType(operateType);
			dto.setUserType("1");
			dto.setEntityID(customerDTO.getEntityId());
			this.sendService(ConstCode.CUSTOMER_BLACK_RISK_JUDGE, dto);
			return blockListOrRiskCheck();
		}
		this.sendService(ConstCode.CUSTOMER_SERVICE_UPDATE, customerDTO);
		/*if (!hasActionErrors()) {
			sendService(ConstCode.CUSTOMER_SYNCTO_CIM, customerDTO);
		}*/
		if (!hasActionErrors()&&Const.AUDIT_PASS.equals(customerDTO.getCusState().trim())) {
			this.addActionMessage("客户审核完成！");
		}
		return this.inquery();
	}

	/**
	 * 查找 客户返回JSON对象
	 * 
	 * @return
	 * @throws Exception
	 */

	public void selectAjax() throws Exception {
		view();
		getResponse().setContentType("application/json; charset=utf-8");
		getResponse().setCharacterEncoding("utf-8");
		getResponse().getWriter().println(
				JSONObject.fromObject(customerDTO).toString());
		getResponse().getWriter().close();
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public CustomerQueryDTO getCustomerQueryDTO() {
		return customerQueryDTO;
	}

	public void setCustomerQueryDTO(CustomerQueryDTO customerQueryDTO) {
		this.customerQueryDTO = customerQueryDTO;
	}

	@Override
	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	@Override
	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public List<UserDTO> getSalesmanList() {
		return salesmanList;
	}

	public void setSalesmanList(List<UserDTO> salesmanList) {
		this.salesmanList = salesmanList;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public String[] getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

	public String getCustomEntityId() {
		return customEntityId;
	}

	public void setCustomEntityId(String customEntityId) {
		this.customEntityId = customEntityId;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
 

}
