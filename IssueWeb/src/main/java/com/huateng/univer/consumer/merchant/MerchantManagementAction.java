package com.huateng.univer.consumer.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import antlr.StringUtils;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantQueryDTO;
import com.allinfinance.univer.consumer.merchant.dto.MerchantTxnQueryDTO;
import com.allinfinance.univer.consumer.shop.dto.ShopDTO;
import com.allinfinance.univer.consumer.txn.dto.TxnQueryDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.ctc.wstx.util.StringUtil;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.MD5EncryptAlgorithm;
import com.huateng.framework.util.ObjectConver;
import com.huateng.univer.system.dictinfo.DictInfoAction;

/**
 * 商户管理Action
 * 
 * @author zengfenghua
 * 
 */
public class MerchantManagementAction extends BaseAction {
	private Logger logger = Logger.getLogger(MerchantManagementAction.class);
	private static final long serialVersionUID = 237035722016610707L;

	private MerchantQueryDTO merchantQueryDTO = new MerchantQueryDTO();

	private MerchantDTO merchantDTO;

	private PageDataDTO pageDataDTO = new PageDataDTO();

	private ContactDTO contactDTO;

	private ShopDTO shopDTO;
	private int totalRows;

	private List<String> entityIdList;
	private List<String> contactIdList;
	private List<Long> bankIdList;
	private List<Long> taxAccountIdList;

	private String actionName;
	private String entityId;

	private List<String> entityIdAll;

	private List<String> stateList;

	private TxnQueryDTO txnQueryDTO;
	
	private List<Map<String, String>> shopList = new ArrayList<Map<String,String>>();
	
	//银行
	protected List<BankDTO> bankList = new ArrayList<BankDTO>();
	
	private String userId;

	/**
	 * 密码参数
	 */
	private String password;
	/**
	 * 确认密码参数
	 */

	private String repassword;

	private boolean sensitiveData;

	private String disabled;
	private MerchantTxnQueryDTO merchantTxnQueryDTO = new MerchantTxnQueryDTO();

	private DictInfoAction dictInfoAction;

	private Map<String, String> txnTypeMap = new HashMap<String, String>();

	private String webPassword;
	
	  //银行账户DTO
	protected BankDTO bankDTO=new BankDTO();
	protected String[] chooseBankId;
    
	

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

	public Map<String, String> getTxnTypeMap() {
		return txnTypeMap;
	}

	public void setTxnTypeMap(Map<String, String> txnTypeMap) {
		this.txnTypeMap = txnTypeMap;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
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

	public ContactDTO getContactDTO() {
		return contactDTO;
	}

	public void setContactDTO(ContactDTO contactDTO) {
		this.contactDTO = contactDTO;
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

	public ShopDTO getShopDTO() {
		return shopDTO;
	}

	public void setShopDTO(ShopDTO shopDTO) {
		this.shopDTO = shopDTO;
	}

	public List<String> getContactIdList() {
		return contactIdList;
	}

	public void setContactIdList(List<String> contactIdList) {
		this.contactIdList = contactIdList;
	}

	public List<Long> getBankIdList() {
		return bankIdList;
	}

	public void setBankIdList(List<Long> bankIdList) {
		this.bankIdList = bankIdList;
	}

	public List<Long> getTaxAccountIdList() {
		return taxAccountIdList;
	}

	public void setTaxAccountIdList(List<Long> taxAccountIdList) {
		this.taxAccountIdList = taxAccountIdList;
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

	public String add() throws Exception {
		merchantDTO = new MerchantDTO();
		userId = getUser().getUserId();
		merchantDTO.setFatherEntityId(getUser().getEntityId());
		this.merchantDTO = (MerchantDTO) sendService("8008021800", merchantDTO)
				.getDetailvo();
		if (hasErrors()) {
			return "input";
		}
		return "add";
	}

	public String edit() throws Exception {
		merchantDTO = new MerchantDTO();
		if (entityIdList != null && entityIdList.size() != 0){
			
			String entityIds[]=entityIdList.get(0).split(",");
			merchantDTO.setEntityId(entityIds[0]);
		}
		if (entityId != null)
			merchantDTO.setEntityId(entityId);
		merchantDTO.setFatherEntityId(getUser().getEntityId());
		this.merchantDTO = (MerchantDTO) sendService("800802180501", merchantDTO)
				.getDetailvo();
		bankList = merchantDTO.getBankList();
		if (this.getUser().getRoleDatePurviewDTO() != null
				&& this.getUser().getRoleDatePurviewDTO().getSensitiveData() == 1) {
			sensitiveData = true;
			disabled = "disabled";
		}
		merchantDTO.setCreateTime(DateUtil.formatStringDate(merchantDTO
				.getCreateTime().substring(0, 8)));
		
		if (hasErrors()) {
			inquiry();
			return "list";
		}
		return "edit";
	}

	public String view() throws Exception {
		merchantDTO.setFatherEntityId(getUser().getEntityId());
		merchantDTO = (MerchantDTO) sendService("800802180501", merchantDTO)
				.getDetailvo();
		merchantDTO.setCreateTime(DateUtil.dbFormatToDateFormat(merchantDTO
				.getCreateTime().substring(0, 8)));
		
		// User user = getUser();
		/**
		 * 取组下面发卡机构信息
		 */
		/**
		 * List<IssuerDTO> lstIssuer = user.getIssuerList(); for (int i = 0; i <
		 * lstIssuer.size(); i++) { IssuerDTO issuerDTO = lstIssuer.get(i); if
		 * (issuerDTO.getIssuerId().equals(merchantDTO.getIssuerId())) {
		 * merchantDTO.setIssuerName(issuerDTO.getIssuerName()); break; } }
		 */
		if (hasErrors()) {
			return "input";
		}
		return "view";
	}

	public String viewShopPos() {
		shopDTO = (ShopDTO) sendService("2002031400", shopDTO).getDetailvo();
		if (hasErrors()) {
			return "input";
		}
		return "viewShopPos";
	}

	public String inquiry() throws Exception {
		try {
			if (merchantQueryDTO == null) {
				merchantQueryDTO = new MerchantQueryDTO();
			}
			merchantQueryDTO.setFatherEntityId(getUser().getEntityId());
			ListPageInit(null, merchantQueryDTO);
			this.pageDataDTO = (PageDataDTO) sendService("8008021801",
					merchantQueryDTO).getDetailvo();
			if (hasErrors()) {
				return "input";
			}
			return "list";
		} catch (Exception e) {
			throw new Exception();
		}
	}

	public String insert() throws Exception {
		if (!"".equals(merchantDTO.getWebsitePassword())) {
			merchantDTO.setWebsitePassword(MD5EncryptAlgorithm.md5(merchantDTO
					.getWebsitePassword()));
		}
		if (null == merchantDTO.getTxnQryTimes()) {
			merchantDTO.setTxnQryTimes(0);
		}
		merchantDTO.setFatherEntityId(getUser().getEntityId());
		merchantDTO.setShopMaxCode("0000");//商户下初始门店号
		merchantDTO.setPreviousYearPos("0");//系统自动得出 首次默认为0
		merchantDTO = (MerchantDTO) sendService("8008021803", merchantDTO)
				.getDetailvo();
		if (hasErrors()) {
			add();
			return "input";
		}
		//view();
		merchantDTO.setFatherEntityId(getUser().getEntityId());
		merchantDTO = (MerchantDTO) sendService("800802180501", merchantDTO)
				.getDetailvo();
		merchantDTO.setCreateTime(DateUtil.dbFormatToDateFormat(merchantDTO
				.getCreateTime().substring(0, 8)));
		addActionMessage("新增商户成功！");
		return "edit";
	}

	
	public String update() throws Exception {
		validateEdit();
		// 防止加密后的密码再次被加密.
		if (null != merchantDTO.getWebsitePassword()
				&& !"".equals(merchantDTO.getWebsitePassword().trim())
				&& merchantDTO.getWebsitePassword().indexOf("*") < 0) {
			if (null != merchantDTO.getEnableWebsite()
					&& merchantDTO.getEnableWebsite().equals("1")) {
				merchantDTO.setWebsitePassword(MD5EncryptAlgorithm
						.md5(merchantDTO.getWebsitePassword()));
			}
		} else if (null != merchantDTO.getWebsitePassword()
				&& merchantDTO.getWebsitePassword().indexOf("*") >= 0) {
			merchantDTO.setWebsitePassword(null);
		}
		/*add by wanglei 如果商户信息被审核拒绝，修改后状态为审核中 4*/
		/*if(merchantDTO.getMerchantState()!=null&&merchantDTO.getMerchantState().equals("3")){
			merchantDTO.setMerchantState("4");
		}*/
		merchantDTO.setMerchantState("4");
		sendService("8008021809", merchantDTO);
		if (hasErrors()) {
			this.merchantDTO = (MerchantDTO) sendService("8008021802",
					merchantDTO).getDetailvo();
			return "input";
		}
		addActionMessage("修改商户成功！");
		return inquiry();

	}

	public String delete() throws Exception {
		validateEdit();
		List<MerchantDTO> merchantDTOList = new ArrayList<MerchantDTO>();
		for (String entityId : entityIdList) {
			MerchantDTO dto = new MerchantDTO();
			dto.setEntityId(entityId.split(",")[0]);
			dto.setFatherEntityId(getUser().getEntityId());
			merchantDTOList.add(dto);
		}
		merchantDTO = new MerchantDTO();
		merchantDTO.setMerchantDTOList(merchantDTOList);
		merchantDTO.setFatherEntityId(getUser().getEntityId());
		sendService("8008021811", merchantDTO);

		if (hasErrors()) {
			inquiry();
			return "input";
		}
		addActionMessage("删除商户成功！");
		return inquiry();
	}

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

	/**
	 * public String addTaxAccount() throws Exception { taxAccountDTO = new
	 * TaxAccountDTO(); taxAccountDTO.setentityId(merchantDTO.getEntityId()); if
	 * (hasErrors()) { return "input"; } return "addTaxAccount"; }
	 * 
	 * public String insertTaxAccount() throws Exception {
	 * sendService("2002021400",taxAccountDTO); if (hasErrors()) { return
	 * "input"; } return "blank";
	 * 
	 * }
	 * 
	 * public String editTaxAccount() throws Exception { taxAccountDTO = new
	 * TaxAccountDTO(); taxAccountDTO.setAccountId(taxAccountIdList.get(0));
	 * this.taxAccountDTO =
	 * (TaxAccountDTO)sendService("2002021700",taxAccountDTO).getDetailvo(); if
	 * (hasErrors()) { return "input"; } return "editTaxAccount"; }
	 * 
	 * public String updateTaxAccount() throws Exception {
	 * sendService("2002021500", taxAccountDTO); if (hasErrors()) { return
	 * "input"; } return "blank";
	 * 
	 * }
	 * 
	 * public String viewTaxAccount() throws Exception { this.taxAccountDTO =
	 * (TaxAccountDTO)sendService("2002021700",taxAccountDTO).getDetailvo(); if
	 * (hasErrors()) { return "input"; } return "viewTaxAccount"; }
	 * 
	 * public String deleteTaxAccount() throws Exception { List<TaxAccountDTO>
	 * taxAccountDTOList = new ArrayList<TaxAccountDTO>(); for (Long
	 * taxAccountId : taxAccountIdList) { TaxAccountDTO dto = new
	 * TaxAccountDTO(); dto.setAccountId(taxAccountId);
	 * taxAccountDTOList.add(dto); } taxAccountDTO = new TaxAccountDTO();
	 * taxAccountDTO.setTaxAccountDTOList(taxAccountDTOList);
	 * sendService("2002021600", taxAccountDTO); if (hasErrors()) { return
	 * "input"; } return "blank"; }
	 */
	/**
	 * 选择商户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String chooseMchnt() throws Exception {
		if (merchantQueryDTO == null) {
			merchantQueryDTO = new MerchantQueryDTO();

		}
		ListPageInit(null, merchantQueryDTO);
		if ("".equals(merchantQueryDTO.getSortFieldName())
				|| merchantQueryDTO.getSortFieldName() == null) {
			merchantQueryDTO.setSortFieldName("merchantName");
			merchantQueryDTO.setSort("asc");
		}
		merchantQueryDTO.setFatherEntityId(getUser().getEntityId());
		PageDataDTO pdd=(PageDataDTO)sendService("8008021801", merchantQueryDTO).getDetailvo();
		if ( pdd!= null) {
			this.pageDataDTO = pdd;
		}
		return "choose";
	}
	
	 /**
	 * 添加门店时选择外部商户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String chooseExternalMchnt() throws Exception {
		if (merchantQueryDTO == null) {
			merchantQueryDTO = new MerchantQueryDTO();

		}
		ListPageInit(null, merchantQueryDTO);
		if ("".equals(merchantQueryDTO.getSortFieldName())
				|| merchantQueryDTO.getSortFieldName() == null) {
			merchantQueryDTO.setSortFieldName("merchantName");
			merchantQueryDTO.setSort("asc");
		}
		merchantQueryDTO.setFatherEntityId(getUser().getEntityId());
		PageDataDTO pdd=(PageDataDTO)sendService("2013050701", merchantQueryDTO).getDetailvo();
		if ( pdd!= null) {
			this.pageDataDTO = pdd;
		}
		return "chooseExternalMchnt";
	}
	
	/**
	 * 报表中选择商户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String chooseMchntInReport() throws Exception {
		if (merchantQueryDTO == null) {
			merchantQueryDTO = new MerchantQueryDTO();

		}
		ListPageInit(null, merchantQueryDTO);
		if ("".equals(merchantQueryDTO.getSortFieldName())
				|| merchantQueryDTO.getSortFieldName() == null) {
			merchantQueryDTO.setSortFieldName("merchantName");
			merchantQueryDTO.setSort("asc");
		}
		merchantQueryDTO.setFatherEntityId(getUser().getEntityId());
		PageDataDTO pdd = (PageDataDTO) sendService("8008021801",
				merchantQueryDTO).getDetailvo();
		if (pdd != null) {
			this.pageDataDTO = pdd;
		}
		return "chooseInReport";
	}

	/**
	 * 返回DTO供父画面使用
	 * 
	 * @return
	 * @throws Exception
	 */
	public void selectAjax() throws Exception {
		merchantDTO.setFatherEntityId(getUser().getEntityId());
		this.merchantDTO = (MerchantDTO) sendService("800802180501", merchantDTO)
				.getDetailvo();
		getResponse().setContentType("application/json; charset=utf-8");
		getResponse().setCharacterEncoding("utf-8");
		// merchantDTO.setJoinDate(new
		// SimpleDateFormat("yyyy-MM-dd").format(merchantDTO.getJoinDate()));
		getResponse().getWriter().println(
				JSONObject.fromObject(merchantDTO).toString());
		getResponse().getWriter().close();
	}

	/**
	 * 添加验证
	 * 
	 * @throws Exception
	 */
	public void validateInsert() throws Exception {
		merchantDTO.setFatherEntityId(getUser().getEntityId());
		this.merchantDTO = (MerchantDTO) sendService("8008021802", merchantDTO)
				.getDetailvo();
		checkCertificate();
		if (merchantDTO.getEnableWebsite() != null) {
			if ("".equals(merchantDTO.getWebsiteUserName().trim())) {
				this.addFieldError("merchantDTO.websiteUserName", "网站登录名不能为空");
				return;
			}
			if ("".equals(merchantDTO.getWebsitePassword().trim())) {
				this.addFieldError("merchantDTO.websitePassword", "密码不能为空");
				return;
			}

			if ("".equals(merchantDTO.getWebsiteUserName())
					&& !"".equals(merchantDTO.getWebsitePassword())) {
				this.addFieldError("merchantDTO.websiteUserName",
						"密码已填写网站登录名必须填写");
				return;
			}
			if (!"".equals(merchantDTO.getWebsiteUserName())
					&& "".equals(merchantDTO.getWebsitePassword())) {
				this.addFieldError("merchantDTO.websitePassword",
						"网站登录名已填写密码必须填写");
				return;
			}
			if (!merchantDTO.getWebsitePassword().matches("[^\\s]{6,16}")) {
				this.addFieldError("merchantDTO.websitePassword",
						"必须由字母和数字组成开头为字母不包含空格(6-16)");
				return;
			}
			if (!merchantDTO.getWebsitePassword().matches(
					"^[a-zA-Z](?=.*?\\d)[a-zA-Z0-9]{5,15}")) {
				this.addFieldError("merchantDTO.websitePassword",
						"必须由字母和数字组成开头为字母(6-16)");
				return;
			}
		}
		// 出错初始化数据

	}

	/**
	 * 修改验证
	 * 
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception {
		this.merchantDTO = (MerchantDTO) sendService("8008021802", merchantDTO)
				.getDetailvo();
		// 验证证书是否正确
		checkCertificate();

		/* 选择开通网站管理之后 */
		if (null != merchantDTO.getEnableWebsite()
				&& merchantDTO.getEnableWebsite().equals("1")) {
			if (null == merchantDTO.getWebsiteUserName()
					|| "".equals(merchantDTO.getWebsiteUserName().trim())) {
				this.addFieldError("merchantDTO.websiteUserName", "网站登录名不能为空");
			}
			/* 若websitepassword为空，则表示insert时未开通网站管理，而在编辑时开通，则密码webpassword必须输入 */
			if (null == merchantDTO.getWebsitePassword()
					|| "".equals(merchantDTO.getWebsitePassword().trim())) {
				if (null == this.webPassword
						|| this.webPassword.trim().equals("")) {
					this.addFieldError("merchantDTO.websitePassword", "密码不能为空");
					return;
				}
				if (!this.webPassword.matches("[^\\s]{6,16}")) {
					this.addFieldError("merchantDTO.websitePassword",
							"必须由字母和数字组成开头为字母不包含空格(6-16)");
					return;
				}
				if (!this.webPassword
						.matches("^[a-zA-Z](?=.*?\\d)[a-zA-Z0-9]{5,15}")) {
					this.addFieldError("merchantDTO.websitePassword",
							"必须由字母和数字组成开头为字母(6-16)");
					return;
				}
			}
		}
	}

	/**
	 * 辅助验证：已经被注销的商户，不能编辑
	 * 
	 * @throws Exception
	 */
	public void validateEdit() throws Exception {
		if (entityIdAll != null && entityIdAll.size() != 0) {
			int i = 0;
			String state = "";
			for (String entityId : entityIdAll) {
				String entityIds[]=entityIdList.get(0).split(",");
				if (entityId.equals(String.valueOf(entityIds[0]))){
					state = stateList.get(i);
					break;
				}
				i++;
			}
			if ("无效".equals(state)) {
				addActionError("已经被注销的商户，不能编辑!");
				inquiry();
			}
			if ("已审核".equals(state) ) {
				addActionError("已审核的商户，不能编辑或删除!");
				inquiry();
			}
		}
	}

	public void validateInqueryTxn() throws Exception {
		try {
			if (merchantQueryDTO != null
					&& merchantQueryDTO.getSelecthisFlag() != null) {
				if (merchantQueryDTO.getSelecthisFlag().equals(1)) {

					if (merchantQueryDTO.getTxnStartTime() != null
							&& merchantQueryDTO.getTxnStopTime() != null) {
						if (merchantQueryDTO.getTxnStartTime().compareTo(
								merchantQueryDTO.getTxnStopTime()) > 0) {
							this.addActionError("起始时间必须必须小于截止时间!");
						}
						if (((merchantQueryDTO.getTxnStopTime().getTime() - merchantQueryDTO
								.getTxnStartTime().getTime())) / 1000 > 2592000) {
							merchantQueryDTO.setTxnStartTime(null);
						}

					}
				} else {
					merchantQueryDTO.setTxnStartTime(null);
					merchantQueryDTO.setTxnStopTime(null);
				}
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new Exception();

		}
	}

	@SuppressWarnings("unchecked")
	public String inqueryTxn() throws Exception {
		try {
			// if(this.getUser().getIssuerId()==0){
			// throw new Exception();
			// }

			if (merchantQueryDTO == null) {
				merchantQueryDTO = new MerchantQueryDTO();
			}
			ListPageInit(null, merchantQueryDTO);

			if (merchantQueryDTO.getSortFieldName() == null
					|| "".equals(merchantQueryDTO.getSortFieldName())) {
				merchantQueryDTO.setSortFieldName("tranTime");
				merchantQueryDTO.setSort("desc");
			}

			pageDataDTO = (PageDataDTO) sendService("2002021900",
					merchantQueryDTO).getDetailvo();

			return "txn";
		} catch (Exception e) {
			this.addActionError("当前为一个发卡机构组，请选择一个发卡机构");
			this.logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * 商户网站修改密码
	 */

	public String loadForModifyPassowrd() throws Exception {
		merchantDTO.setFatherEntityId(getUser().getEntityId());
		merchantDTO = (MerchantDTO) sendService(
				ConstCode.ADMIN_MERCHANT_MODIFY_PASSWORD, merchantDTO)
				.getDetailvo();
		if (this.hasErrors()) {
			inquiry();
			return "modifyfailed";
		}
		return "modify";
	}

	public void validateModifWebPassword() {
		if ("".equals(password)) {
			this.addFieldError("password", "必须由字母和数字组成开头为字母(6-16)");
			return;
		}
		if ("".equals(repassword)) {
			this.addFieldError("repassword", "确认密码不能为空");
			return;
		}
		if (password != null) {
			if (!password.matches("[^\\s]{6,16}")) {
				this.addFieldError("password", "必须由字母和数字组成开头为字母不包含空格(6-16)");
				return;
			}
		}
		if (password != null && repassword != null) {
			if (!password.matches("^[a-zA-Z](?=.*?\\d)[a-zA-Z0-9]{5,15}")) {
				this.addFieldError("password", "必须由字母和数字组成开头为字母(6-16)");
				return;
			}
			if (!password.equals(repassword)) {
				this.addFieldError("repassword", "两次密码不相同");
				return;
			}
		}
	}

	public String modifWebPassword() throws Exception {
		merchantDTO.setFatherEntityId(getUser().getEntityId());
		merchantDTO.setWebsitePassword(MD5EncryptAlgorithm.md5(password));
		sendService(ConstCode.ADMIN_MERCHANT_UDPATE, merchantDTO);
		if (this.hasErrors()) {
			return "failed";
		} else {
			this.addActionMessage("修改密码改成功!");
		}
		return inquiry();
	}

	public void checkWebName() throws Exception {
		String message = (String) sendService(
				ConstCode.ADMIN_MERCHANT_CHECK_NAME, merchantDTO).getDetailvo();
		if (this.hasErrors()) {
			message = (String) getActionErrors().toArray()[0];
		}
		getResponse().setContentType("application/json; charset=utf-8");
		getResponse().setCharacterEncoding("utf-8");
		getResponse().getWriter().println(message);
		getResponse().getWriter().close();
	}

	public TxnQueryDTO getTxnQueryDTO() {
		return txnQueryDTO;
	}

	public void setTxnQueryDTO(TxnQueryDTO txnQueryDTO) {
		this.txnQueryDTO = txnQueryDTO;
	}

	private void checkCertificate() throws Exception {
		try {
			if (merchantDTO.getCertificateNo() != null
					&& !"".equals(merchantDTO.getCertificateNo())) {
				if (!merchantDTO.getCertificateNo().matches("[^\\s]{1,256}")) {
					this.addFieldError("merchantDTO.certificateNo",
							"证书号不能有空格(256位内)");
					return;
				}
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}

	}

	public String queryMerchantTxn() {
		try {

			loadMerchantList();
			shopList = getShopList();
			ListPageInit(null, merchantTxnQueryDTO);
			if (merchantTxnQueryDTO.getSelectFlag() == null) {
				merchantTxnQueryDTO.setSelectFlag("0");
			}
			// 默认为消费交易
			if (merchantTxnQueryDTO.getTransType() == null) {
				merchantTxnQueryDTO.setTransType("S22");
			}
			if (null != merchantTxnQueryDTO.getStartDate()
					&& !"".equals(merchantTxnQueryDTO.getStartDate())) {
				merchantTxnQueryDTO.setStartDate(DateUtil
						.getCurrentDateFormatStr(DateUtil
								.getFormatTime(merchantTxnQueryDTO
										.getStartDate())));
			}
			if ("".equals(merchantTxnQueryDTO.getStartDate())) {
				merchantTxnQueryDTO.setStartDate(null);
			}
			if (null != merchantTxnQueryDTO.getEndDate()
					&& !"".equals(merchantTxnQueryDTO.getEndDate())) {
				merchantTxnQueryDTO.setEndDate(DateUtil
						.getCurrentDateFormatStr(DateUtil
								.getFormatTime(merchantTxnQueryDTO
										.getEndDate())));
			}
			if ("".equals(merchantTxnQueryDTO.getEndDate())) {
				merchantTxnQueryDTO.setEndDate(null);
			}
			
			
			merchantTxnQueryDTO.setMerchantCode(ObjectConver
					.conver(merchantTxnQueryDTO.getMerchantCode()));
			merchantTxnQueryDTO.setShopId(ObjectConver
					.conver(merchantTxnQueryDTO.getShopId()));
			merchantTxnQueryDTO.setCardNo(ObjectConver
					.conver(merchantTxnQueryDTO.getCardNo()));
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.MERCHANT_TXN_QUERY, merchantTxnQueryDTO)
					.getDetailvo();
			merchantTxnQueryDTO.setStartDate(DateUtil
					.formatStringDate(merchantTxnQueryDTO.getStartDate()));
			merchantTxnQueryDTO.setEndDate(DateUtil
					.formatStringDate(merchantTxnQueryDTO.getEndDate()));
			if (null != pageDataDTO && pageDataDTO.getData().size() > 0) {
    			List list=pageDataDTO.getData();
    			Map map= new HashMap();
    			for (int i=0;i<list.size();i++) {
    				map =  (Map) list.get(i);
    				map.put("merchantName",String.valueOf(map.get("merchantName")).trim());
    				map.put("shopName",String.valueOf(map.get("shopName")).trim());
    			}
    			this.txnTypeMap =(Map<String,String>)((Map<String,Object>)list.get(list.size()-1)).get("tranTypeMap");
    			this.txnTypeMap.put("", "----------全部----------");
    			list.remove(list.size()-1);
    			if (hasActionErrors()) {
    				loadMerchantList();
    				return "input";
    			}
    			totalRows = pageDataDTO.getTotalRecord();
			}
			else {
			    totalRows = 0;
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";
	}

	public void validateQueryMerchantTxn() {
		if (merchantTxnQueryDTO.getSelectFlag() != null) {
			// if(null == merchantTxnQueryDTO.getMerchantCode() ||
			// merchantTxnQueryDTO.getMerchantCode().equals("")){
			// addFieldError("merchantTxnQueryDTO.merchantCode", "商户必须选择");
			// }
		}
		if (hasActionErrors() || hasErrors()) {
			loadMerchantList();
		}
	}

	@SuppressWarnings("unchecked")
	public void getShopListByMerchantId() {
		try {
				merchantTxnQueryDTO.setShopDTOList((List<ShopDTO>) sendService(
						ConstCode.MERCHANT_GETSHOP_BY_MERCHANTID, merchantTxnQueryDTO)
						.getDetailvo());
	
				getResponse().setContentType("application/json; charset=utf-8");
	
				getResponse().setCharacterEncoding("utf-8");
	
				getResponse().getWriter().println(
						JSONArray.fromObject(merchantTxnQueryDTO.getShopDTOList()).toString());
	
				getResponse().getWriter().close();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}

	}


	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getShopList() throws BizServiceException
	{
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			if(null != merchantTxnQueryDTO.getMerchantCode() && !"".equals(merchantTxnQueryDTO.getMerchantCode().trim())){
				List<ShopDTO> shopList = (List<ShopDTO>) sendService(
						ConstCode.MERCHANT_GETSHOP_BY_MERCHANTID, merchantTxnQueryDTO)
						.getDetailvo();
				
				for (ShopDTO shopDTO : shopList) {
					Map<String, String> shop = new HashMap<String, String>();
					shop.put("shopId", shopDTO.getShopId());
					shop.put("shopName", shopDTO.getShopName());
					list.add(shop);
				}
			}
		return list;
	}

	
	public void loadMerchantList() {
		try {
			merchantTxnQueryDTO.setEntityId(getUser().getEntityId());
			merchantTxnQueryDTO = (MerchantTxnQueryDTO) sendService(
					ConstCode.LOAD_ALL_MERCHANT, merchantTxnQueryDTO)
					.getDetailvo();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}
	
	
	 
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
	//冻结商户
	public String frozen() throws Exception{
		if(merchantDTO==null||merchantDTO.getEntityId()==null||merchantDTO.getEntityId().trim().equals("")){
			this.addActionError("请选择商户！");
		}else{
			//冻结商户的状态
			merchantDTO.setMerchantState("5");
			OperationResult result = sendService("20150703002", merchantDTO);
			if(result==null||result.getTxnstate().equals("0")){
				this.addActionError("商户冻结失败");
			}
		}
		inquiry();
		return "list";
		
	}
	
	//解冻商户
	public String unfrozen() throws Exception{
		if(merchantDTO==null||merchantDTO.getEntityId()==null||merchantDTO.getEntityId().trim().equals("")){
			this.addActionError("请选择商户！");
		}else{
			//冻结商户的状态
			merchantDTO.setMerchantState("1");
			OperationResult result = sendService("20150703001", merchantDTO);
			if(result==null||result.getTxnstate().equals("0")){
				this.addActionError("商户解冻失败");
			}
		}
		inquiry();
		return "list";
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

	public MerchantTxnQueryDTO getMerchantTxnQueryDTO() {
		return merchantTxnQueryDTO;
	}

	public void setMerchantTxnQueryDTO(MerchantTxnQueryDTO merchantTxnQueryDTO) {
		this.merchantTxnQueryDTO = merchantTxnQueryDTO;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setShopList(List<Map<String, String>> shopList) {
		this.shopList = shopList;
	}

	
	
}
