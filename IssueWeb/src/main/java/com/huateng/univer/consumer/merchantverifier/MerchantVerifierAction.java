package com.huateng.univer.consumer.merchantverifier;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantQueryDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantTxnQueryDTO;
import com.allinfinance.univer.consumercontract.dto.AccTypeContractDTO;
import com.allinfinance.univer.consumercontract.dto.ConsumerContractDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.MD5EncryptAlgorithm;
import com.huateng.univer.system.dictinfo.DictInfoAction;

/**
 * 商户审核管理Action
 * 
 * @author 
 * 
 */
public class MerchantVerifierAction extends BaseAction {

	private static final long serialVersionUID = 237035722016610707L;

	private MerchantQueryDTO merchantQueryDTO = new MerchantQueryDTO();
	private MerchantDTO merchantDTO = new MerchantDTO();
	private PageDataDTO pageDataDTO = new PageDataDTO();
	private ContactDTO contactDTO;
	private MerchantTxnQueryDTO merchantTxnQueryDTO = new MerchantTxnQueryDTO();
	// 银行
	private DictInfoAction dictInfoAction;

	private int totalRows;
	private String stateFlag;
	private String actionName;
	private String entityId;
	private String fatherEntityId;
	private String disabled;
	private String webPassword;
	private String verifierFlag;
	private boolean sensitiveData;

	private List<String> entityIdList;
	private List<Long> bankIdList;
	private List<String> entityIdAll;
	private List<String> stateList;
	private List<String> contactIdList;
	
	protected String[] chooseBankId;
	protected List<BankDTO> bankList = new ArrayList<BankDTO>();
	protected List<ConsumerContractDTO> contractList = new ArrayList<ConsumerContractDTO>();
	protected List<AccTypeContractDTO> accTypeContractDTOList = new ArrayList<AccTypeContractDTO>();


	// 银行账户DTO
	protected BankDTO bankDTO = new BankDTO(); 
	protected ConsumerContractDTO contractDTO = new ConsumerContractDTO();


	/**
	 * 进入编辑界面操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		merchantDTO = new MerchantDTO();
		if (entityIdList != null && entityIdList.size() != 0)
			merchantDTO.setEntityId((String) entityIdList.get(0));
		if (entityId != null)
			merchantDTO.setEntityId(entityId);
		merchantDTO.setFatherEntityId(getUser().getEntityId());
		this.merchantDTO = (MerchantDTO) sendService("8008021804", merchantDTO)
				.getDetailvo();
		//银行信息
		bankList = merchantDTO.getBankList();
		//合同信息
		contractList = merchantDTO.getContractList();
		//合同明细信息
		accTypeContractDTOList = merchantDTO.getAccTypeContractDTOList();
		
		if (this.getUser().getRoleDatePurviewDTO() != null
				&& this.getUser().getRoleDatePurviewDTO().getSensitiveData() == 1) {
			sensitiveData = true;
			disabled = "disabled";
		}
		merchantDTO.setCreateTime(DateUtil.formatStringDate(merchantDTO
				.getCreateTime().substring(0, 8)));

		/**
		 * mod by yy, 判断商户是否开通网上交易
		 */
		if (merchantDTO.getWebsiteUserName() == null || merchantDTO.getWebsiteUserName().trim().length() == 0){
			merchantDTO = (MerchantDTO) sendService("8008021800", merchantDTO)
			.getDetailvo();
		}
		
		if (hasErrors()) {
			inquiry();
			return "list";
		}
		return "edit";
	}

	/**
	 * 查看商户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String view() throws Exception {
		String returnValue="";
		if(verifierFlag.equals("0")){
			returnValue = "view";//查看商户信息
		}else{//审核商户信息
			merchantDTO.setEntityId(entityIdList.get(0));
			returnValue = "viewVerifier";
		}
		
		merchantDTO.setFatherEntityId(getUser().getEntityId());
		merchantDTO = (MerchantDTO) sendService("8008021810", merchantDTO)
				.getDetailvo();
		if (this.hasErrors()) {
			inquiry();
			return "input";
		}
		merchantDTO.setCreateTime(DateUtil.dbFormatToDateFormat(merchantDTO
				.getCreateTime().substring(0, 8)));
		//银行信息
		bankList = merchantDTO.getBankList();
		//合同信息
		contractList = merchantDTO.getContractList();
		//合同明细信息
		accTypeContractDTOList = merchantDTO.getAccTypeContractDTOList();
 
		return returnValue;
	}
 
	/**
	 * 商户信息查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String inquiry() throws Exception {
		try {
			if (merchantQueryDTO == null) {
				merchantQueryDTO = new MerchantQueryDTO();
			}
			merchantQueryDTO.setFatherEntityId(getUser().getEntityId());
			ListPageInit(null, merchantQueryDTO);
			this.pageDataDTO = (PageDataDTO) sendService("8008021815",
					merchantQueryDTO).getDetailvo();
			if (hasErrors()) {
				return "input";
			}
			return "list";
		} catch (Exception e) {
			throw new Exception();
		}
	}

	/**
	 * 更新商户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		if (null == merchantDTO.getWebsitePassword()
				|| merchantDTO.getWebsitePassword().trim().equals("")) {
			if (null != merchantDTO.getEnableWebsite()
					&& merchantDTO.getEnableWebsite().equals("1")) {
				merchantDTO.setWebsitePassword(MD5EncryptAlgorithm
						.md5(this.webPassword));
			}
		}
		merchantDTO.setMerchantState("2");//未审核
		sendService("8008021809", merchantDTO);
		if (hasErrors()) {
			this.merchantDTO = (MerchantDTO) sendService("8008021802",
					merchantDTO).getDetailvo();
			return "input";
		}
		addActionMessage("修改商户成功！");
		return inquiry();

	}

	/**
	 * 商户信息审核操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public String pass() throws Exception {
		merchantDTO.setEntityId(entityId);
		merchantDTO.setFatherEntityId(fatherEntityId);
		merchantDTO = (MerchantDTO) sendService("800888880001",merchantDTO).getDetailvo();
		merchantDTO.setMerchantState(stateFlag);
		this.sendService("8008021809", merchantDTO);
		if (!hasActionErrors()) {
			this.addActionMessage("客户审核完成！");
		}
		return this.inquiry();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	//转向添加银行账户页面
	public String addBank() throws Exception{
		return "addBank";
	}
	
	//添加银行账户信息
	public String insertBank() throws Exception{
		bankDTO.setEntityId(entityId);
		bankDTO.setBankType("2");//商户
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
		bankDTO.setBankId(chooseBankId[0]);
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
	public String deleteBank() throws Exception{
		List<BankDTO> bankDTOs = new ArrayList<BankDTO>();
		for (String id : chooseBankId) {
			bankDTO = new BankDTO();
			bankDTO.setBankId(id);
			bankDTOs.add(bankDTO);
		}
		bankDTO.setBankDTOs(bankDTOs);
		this.sendService(ConstCode.ENTITY_BANK_SERVICE_DEL,
				bankDTO);
		if (!hasActionErrors()) {
			addActionMessage("银行账户信息删除成功！");
		}
		return edit();
	}
	
	///////////////////////////////////////////////////////////////////////
	
	public String addContact() throws Exception {
		contactDTO = new ContactDTO();
		contactDTO.setEntityId(merchantDTO.getEntityId());
		if (hasErrors()) {
			return "input";
		}
		return "addContact";
	}

	public String insertContact() throws Exception {
		if (contactDTO == null)
			contactDTO = new ContactDTO();
		contactDTO.setEntityId(merchantDTO.getEntityId());
		//contactDTO.setContactType(DataBaseConstant.CONTACT_REF_TYPE_MERCHANT);
		sendService("8008021805", contactDTO);
		if (hasErrors()) {
			return "input";
		}
		return "blank";

	}

	public String editContact() throws Exception {
		contactDTO = new ContactDTO();
		contactDTO.setContactId(contactIdList.get(0));
		this.contactDTO = (ContactDTO) sendService("8008021806", contactDTO)
				.getDetailvo();
		if (hasErrors()) {
			return "input";
		}
		return "editContact";
	}

	public String updateContact() throws Exception {
		sendService("8008021807", contactDTO);
		if (hasErrors()) {
			return "input";
		}
		return "blank";

	}

	public String viewContact() throws Exception {
		this.contactDTO = (ContactDTO) sendService("2002020900", contactDTO)
				.getDetailvo();
		if (hasErrors()) {
			return "input";
		}
		return "viewContact";
	}

	public String deleteContact() throws Exception {
		List<ContactDTO> contactDTOList = new ArrayList<ContactDTO>();
		for (String contactId : contactIdList) {
			ContactDTO dto = new ContactDTO();
			dto.setContactId(contactId);
			contactDTOList.add(dto);
		}
		contactDTO = new ContactDTO();
		contactDTO.setContactDTOList(contactDTOList);
		sendService("8008021808", contactDTO);
		if (hasErrors()) {
			return "input";
		}
		return "blank";
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	
	public List<BankDTO> getBankList() {
		return bankList;
	}

	public void setBankList(List<BankDTO> bankList) {
		this.bankList = bankList;
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

	public String getWebPassword() {
		return webPassword;
	}

	public void setWebPassword(String webPassword) {
		this.webPassword = webPassword;
	}

	public DictInfoAction getDictInfoAction() {
		return dictInfoAction;
	}

	public void setDictInfoAction(DictInfoAction dictInfoAction) {
		this.dictInfoAction = dictInfoAction;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public boolean isSensitiveData() {
		return sensitiveData;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public void setSensitiveData(boolean sensitiveData) {
		this.sensitiveData = sensitiveData;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public MerchantQueryDTO getMerchantQueryDTO() {
		return merchantQueryDTO;
	}

	public void setMerchantQueryDTO(MerchantQueryDTO merchantQueryDTO) {
		this.merchantQueryDTO = merchantQueryDTO;
	}

	public int getTotalRows() {
		if (null == pageDataDTO) {
			return 0;
		}
		return pageDataDTO.getTotalRecord();

	}

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	public List<Long> getBankIdList() {
		return bankIdList;
	}

	public void setBankIdList(List<Long> bankIdList) {
		this.bankIdList = bankIdList;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public List<String> getEntityIdList() {
		return entityIdList;
	}

	public void setEntityIdList(List<String> entityIdList) {
		this.entityIdList = entityIdList;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public List<String> getEntityIdAll() {
		return entityIdAll;
	}

	public void setEntityIdAll(List<String> entityIdAll) {
		this.entityIdAll = entityIdAll;
	}

	public JSONArray getSalesmanList() {
		return JSONArray.fromObject(merchantDTO.getSalesmanList());
	}

	public JSONArray getMerchantDTOList() {
		return JSONArray.fromObject(merchantTxnQueryDTO.getMerchantDTOList());
	}

	public List<String> getStateList() {
		return stateList;
	}

	public void setStateList(List<String> stateList) {
		this.stateList = stateList;
	}

	public void setVerifierFlag(String verifierFlag) {
		this.verifierFlag = verifierFlag;
	}

	public String getVerifierFlag() {
		return verifierFlag;
	}

	public void setFatherEntityId(String fatherEntityId) {
		this.fatherEntityId = fatherEntityId;
	}

	public String getFatherEntityId() {
		return fatherEntityId;
	}

	public void setStateFlag(String stateFlag) {
		this.stateFlag = stateFlag;
	}

	public String getStateFlag() {
		return stateFlag;
	}

	public List<ConsumerContractDTO> getContractList() {
		return contractList;
	}

	public void setContractList(List<ConsumerContractDTO> contractList) {
		this.contractList = contractList;
	}

	public ConsumerContractDTO getContractDTO() {
		return contractDTO;
	}

	public void setContractDTO(ConsumerContractDTO contractDTO) {
		this.contractDTO = contractDTO;
	}

	public List<AccTypeContractDTO> getAccTypeContractDTOList() {
		return accTypeContractDTOList;
	}

	public void setAccTypeContractDTOList(
			List<AccTypeContractDTO> accTypeContractDTOList) {
		this.accTypeContractDTOList = accTypeContractDTOList;
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
 
}
