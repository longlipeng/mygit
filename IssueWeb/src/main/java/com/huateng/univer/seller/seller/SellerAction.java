package com.huateng.univer.seller.seller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;
import com.allinfinance.univer.seller.seller.dto.SellerQueryDTO;
import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoDTO;
import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.MD5EncryptAlgorithm;
import com.huateng.framework.util.SystemInfo;
import com.huateng.univer.entity.EntityBaseAction;

/**
 * 营销机构action
 * 
 * @author xxl
 * 
 */
public class SellerAction extends EntityBaseAction {

	private static final long serialVersionUID = -2571077108260572188L;
	private Logger logger = Logger.getLogger(SellerAction.class);
	private PageDataDTO pageDataDTO = new PageDataDTO();
	private SellerDTO sellerDTO = new SellerDTO();
	private UserDTO userDTO;
	private SellerQueryDTO sellerQueryDTO = new SellerQueryDTO();
	private String[] sellerIds;
	private List<UserDTO> salesmanList = new ArrayList<UserDTO>();
	private Integer totalRows;
	/** 密码参数 */
	private String password;
	/** 确认密码参数 */
	private String repassword;
	private String menuList;
	private String nmenuList;
	public String getMenuList() {
		return menuList;
	}

	public void setMenuList(String menuList) {
		this.menuList = menuList;
	}

	public String getNmenuList() {
		return nmenuList;
	}

	public void setNmenuList(String nmenuList) {
		this.nmenuList = nmenuList;
	}

	/**
	 * 初始化每个实体的父实体id(entityId)
	 */
	public void initEntityId() throws Exception {
		entityId = sellerDTO.getEntityId();
	}
	
	public void initNameSpace() throws Exception{
		nameSpace = "seller";
	}
	
	public String reloadForEntity() throws Exception{
		return this.edit();
	}
	public String record() throws Exception{
		try{
			sellerQueryDTO.setEntityId(getUser().getEntityId());
			pageDataDTO=(PageDataDTO) sendService(ConstCode.QUERY_FOR_Seller, sellerQueryDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (hasErrors()) {
				return INPUT;
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "selectEntityId";
	}
	public String configEntiyId(){
		try{
			sellerDTO.setEntityId(getUser().getEntityId());
			sellerDTO = (SellerDTO) this.sendService(ConstCode.SELLER_SERVICE_INIT,
					sellerDTO).getDetailvo();
			if (sellerDTO != null) {
				salesmanList = sellerDTO.getSalesmanList();
			}
			sellerQueryDTO.setFatherEntityId(getUser().getEntityId());
			sellerDTO=(SellerDTO) sendService(ConstCode.selectEntity_FOR_Seller, sellerQueryDTO).getDetailvo();
			if (hasErrors()) {
				return INPUT;
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "configEntiyId";
	}
	/**
	 * 返回销售区域列表
	 * 
	 * @return
	 */
	public JSONObject getSalesRegions() {
		Map<String, List<EntityDictInfoDTO>> salesRegions = new HashMap<String, List<EntityDictInfoDTO>>();
		List<EntityDictInfoDTO> dictInfoDTOList = SystemInfo.getDictList(getUser().getEntityId(),"405");
		if (dictInfoDTOList != null) {
			for (EntityDictInfoDTO dictInfoDTO : dictInfoDTOList) {
				salesRegions.put(dictInfoDTO.getDictCode(), SystemInfo
						.getSubDictList(getUser().getEntityId(),"408", new Integer(dictInfoDTO.getDictCode())));
			}
		}
		return JSONObject.fromObject(salesRegions);
	}

	public String list() throws Exception {
		ListPageInit(null, sellerQueryDTO);

		if (sellerQueryDTO.isQueryAll()) {
			sellerQueryDTO.setQueryAll(false);
			sellerQueryDTO
					.setRowsDisplayed(Integer
							.parseInt(SystemInfo
									.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
			
		}
		sellerQueryDTO.setFunctionRoleId("3");
		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.SELLER_SERVICE_INQUERY, sellerQueryDTO).getDetailvo();

		if (pageDataDTO.getData().size() > 0) {
			totalRows = pageDataDTO.getTotalRecord();
		} else {
			totalRows = 0;
		}
		return "list";
	}
	/**
	 * 显示有效的营销机构
	 * 
	 * @return
	 * @throws Exception
	 */
	public String choice() throws Exception {
		sellerQueryDTO.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
		sellerQueryDTO.setSellerState("1");
		this.list();
		return "choice";
	}
	
	/**
	 * 显示有效的营销机构
	 * 
	 * @return
	 * @throws Exception
	 */
	public String choiceSellerOrSelf() throws Exception {
		ListPageInit(null, sellerQueryDTO);
 		
		if (sellerQueryDTO.isQueryAll()) {
			sellerQueryDTO.setQueryAll(false);
			sellerQueryDTO
					.setRowsDisplayed(Integer
							.parseInt(SystemInfo
									.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
		}

		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.SELLER_SERVICE_INQUERYORSELF, sellerQueryDTO).getDetailvo();

		if (pageDataDTO.getData().size() > 0) {
			totalRows = pageDataDTO.getTotalRecord();
		} else {
			totalRows = 0;
		}
		return "choice";
	}
	

	/**
	 * 查找营销机构返回JSON对象
	 * 
	 * @return
	 * @throws Exception
	 */

	public void selectAjax() throws Exception {
		view();
		getResponse().setContentType("application/json; charset=utf-8");
		getResponse().setCharacterEncoding("utf-8");
		getResponse().getWriter().println(
				JSONObject.fromObject(sellerDTO).toString());
		getResponse().getWriter().close();
	}

	public String view() throws Exception {

		if (null == sellerDTO.getEntityId()) {
			if (null != sellerIds && sellerIds.length == 1) {
				String[] keyId = sellerIds[0].split(",");
				sellerDTO.setEntityId(keyId[0]);
				sellerDTO.setFatherEntityId(keyId[1]);
			}
		}
		sellerDTO.setDefaultEntityId(getUser().getEntityId());
		sellerDTO = (SellerDTO) this.sendService(ConstCode.SELLER_SERVICE_VIEW,
				sellerDTO).getDetailvo();
		
		JSONArray jsonObject = JSONArray.fromObject(sellerDTO.getResourceDTOs());
		menuList = jsonObject.toString();
		JSONArray jsonObject1 = JSONArray.fromObject(sellerDTO.getNresourceDTOs());
		nmenuList=jsonObject1.toString();
		
		if (null != sellerDTO.getCloseDate()
				&& !"".equals(sellerDTO.getCloseDate())) {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			sellerDTO.setCloseDateDate(df.parse(sellerDTO.getCloseDate()));
		}
		if (sellerDTO != null) {
			salesmanList = sellerDTO.getSalesmanList();
			salesmanList = sellerDTO.getSalesmanList();
			invoiceAddressList = sellerDTO.getInvoiceAddressList();
			invoiceCompanyList = sellerDTO.getInvoiceCompanyList();
			deliveryPointList = sellerDTO.getDeliveryPointList();
			contractList = sellerDTO.getContractList();
			departmentList = sellerDTO.getDepartmentList();
			bankList=sellerDTO.getBankList();
			this.initEntityId();
		}
		this.initNameSpace();
		if (hasActionErrors()) {
			return INPUT;
		}
		return "view";
	}

	public String add() throws Exception {
		sellerDTO = (SellerDTO) this.sendService(ConstCode.SELLER_SERVICE_INIT,
				sellerDTO).getDetailvo();
		if (sellerDTO != null) {
			salesmanList = sellerDTO.getSalesmanList();
		}
		
		List<String> flist=new ArrayList<String>();
		flist.add("0");
		flist.add("3");
		userDTO=new UserDTO();
		userDTO.setFunctionRoleId(flist);
		userDTO.setEntityId(getUser().getEntityId());
		userDTO.setIsSaleFlage("2");
		List<ResourceDTO> resourceDTOs = (List<ResourceDTO>) sendService(
				ConstCode.ROLE_SERVICE_SELECTISSERRESOURCE,userDTO).getDetailvo();
		JSONArray jsonObject = JSONArray.fromObject(resourceDTOs);
		menuList = jsonObject.toString();
		return "add";
	}
	public void validateInsert() throws Exception {
		logger.info(this.getFieldErrors());
		this.add();
		this.checkPassword();
	}

	public String insert() throws Exception {
		if (null != sellerDTO.getCloseDateDate()) {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			sellerDTO.setCloseDate(df.format(sellerDTO.getCloseDateDate()));
		}
		sellerDTO.setUserPassword(MD5EncryptAlgorithm.md5(password));
		sellerDTO=(SellerDTO)this.sendService(ConstCode.SELLER_SERVICE_INSERT, sellerDTO).getDetailvo();
		if (hasActionErrors()) {
			return INPUT;
		}
		this.view();
		this.addActionMessage("添加营销机构成功！");
		return "edit";
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
	
	public String edit() throws Exception {
		view();
		return "edit";
	}

	public void validateUpdate() throws Exception {
		this.add();
		if (null == sellerDTO.getEntityId()) {
			if (null != sellerIds && sellerIds.length == 1) {
				String[] keyId = sellerIds[0].split(",");
				sellerDTO.setEntityId(keyId[0]);
				sellerDTO.setFatherEntityId(keyId[1]);
			}
		}
		sellerDTO.setDefaultEntityId(getUser().getEntityId());
		SellerDTO sellerDTO_tmp = (SellerDTO) this.sendService(ConstCode.SELLER_SERVICE_VIEW,
				sellerDTO).getDetailvo();
		
		JSONArray jsonObject = JSONArray.fromObject(sellerDTO_tmp.getResourceDTOs());
		menuList = jsonObject.toString();
		JSONArray jsonObject1 = JSONArray.fromObject(sellerDTO_tmp.getNresourceDTOs());
		nmenuList=jsonObject1.toString();
	}

	public String update() throws Exception {
		if (null != sellerDTO.getCloseDateDate()) {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			sellerDTO.setCloseDate(df.format(sellerDTO.getCloseDateDate()));
		}
		this.sendService(ConstCode.SELLER_SERVICE_UPDATE, sellerDTO);
		if (hasActionErrors()) {
			return INPUT;
		}
		this.addActionMessage("编辑营销机构成功！");
		return this.list();
	}

	public String modifyState() throws Exception {
		this.view();
		return "modifyState";
	}

	public String updateState() throws Exception {
		this.sendService(ConstCode.SELLER_SERVICE_UPDATE, sellerDTO);
		if (hasActionErrors()) {
			return this.list();
		}
		this.addActionMessage("修改营销机构状态成功！");
		return this.list();
	}

	public String delete() throws Exception {
		if (sellerIds != null && sellerIds.length > 0) {
			sellerDTO.setSellerIds(sellerIds);
			this.sendService(ConstCode.SELLER_SERVICE_DEL, sellerDTO);
			if (!hasActionErrors()) {
				this.addActionMessage("删除营销机构成功！");
			}
		}
		return this.list();
	}
	/**
	 * 
	 * @author Yifeng.Shi
	 * @serialData 2011-05-16
	 * 
	 */ 
	 public String modifyPassword(){
	    	try{
	    		userDTO = (UserDTO) sendService(ConstCode.USER_SERVICE_VIEW, userDTO)
				.getDetailvo();
	    	}catch(Exception e){
	    		this.logger.error(e.getMessage());
	    	}
	    	return "modifyPassword";
	    }
	 
	 public String updatePassword() throws Exception{
			try {
				
				checkPassword();
				if(hasFieldErrors()){
					return "input";
				}
				userDTO.setModifyUser(getUser().getUserId());
				userDTO.setUserPassword(MD5EncryptAlgorithm.md5(password));
				sendService(ConstCode.USER_SERVICE_UPDATEUSER, userDTO);
				if (hasActionErrors()) {
					return "input";
				} else {
					this.addActionMessage("修改密码成功");
				}
			} catch (Exception e) {
				this.logger.error(e.getMessage());
			}
			return list();
		}
	 
	 /**
		 * sellerModifyUserState
		 * @return
		 */
		public String sellerModifyUserState() throws Exception{
				try{
		    		userDTO = (UserDTO) sendService(ConstCode.USER_SERVICE_VIEW, userDTO)
					.getDetailvo();
		    		if(hasActionErrors()){
		    			return "input";
		    		}
		    	}catch(Exception e){
		    		this.logger.error(e.getMessage());
		    	}
		    	return "sellerModifyUserState";
		}
		
		/**
		 * sellerUpdateUserState
		 * @return
		 */
		public String sellerUpdateUserState() throws Exception{
				userDTO.setModifyUser(getUser().getUserId());
				sendService(ConstCode.USER_SERVICE_UPDATEUSER, userDTO);
	    		if(hasActionErrors()){
	    			return "input";
	    		}
	    		addActionMessage("用户信息修改成功!");
	    		return list();
		}
	 
	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public SellerDTO getSellerDTO() {
		return sellerDTO;
	}

	public void setSellerDTO(SellerDTO sellerDTO) {
		this.sellerDTO = sellerDTO;
	}

	public SellerQueryDTO getSellerQueryDTO() {
		return sellerQueryDTO;
	}

	public void setSellerQueryDTO(SellerQueryDTO sellerQueryDTO) {
		this.sellerQueryDTO = sellerQueryDTO;
	}

	public String[] getSellerIds() {
		return sellerIds;
	}

	public void setSellerIds(String[] sellerIds) {
		this.sellerIds = sellerIds;
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

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
}
