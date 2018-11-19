package com.huateng.univer.issuer.consumerManagerment.consumer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.consumer.ConsumerDTO;
import com.allinfinance.univer.issuer.dto.consumer.ConsumerQueryDTO;
import com.allinfinance.univer.seller.seller.dto.SellerDTO;
import com.allinfinance.univer.system.role.dto.ResourceDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.MD5EncryptAlgorithm;
import com.huateng.framework.util.SystemInfo;
import com.huateng.univer.entity.EntityBaseAction;

public class ConsumerAction extends EntityBaseAction {
	
	private Logger logger = Logger.getLogger(ConsumerAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ConsumerQueryDTO consumerQueryDTO=new ConsumerQueryDTO();
	
	private ConsumerDTO consumerDTO=new ConsumerDTO();
	
	private PageDataDTO pageDataDTO = new PageDataDTO();
	
	private SellerDTO sellerDTO = new SellerDTO();
	
	private List<UserDTO> salesmanList=new ArrayList<UserDTO>();
	
	private List<String> consumerIdList=new ArrayList<String>();
	
	private List<String> userIdList = new ArrayList<String>();
	
	private int totalRows = 0;
	private String password;
	private String repassword;
	private String nameSpace;
    private UserDTO userDTO;
    private String menuList;
	private String nmenuList;
	
	
	public List<String> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<String> userIdList) {
		this.userIdList = userIdList;
	}

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

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
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

	public List<String> getConsumerIdList() {
		return consumerIdList;
	}

	public void setConsumerIdList(List<String> consumerIdList) {
		this.consumerIdList = consumerIdList;
	}

	public List<UserDTO> getSalesmanList() {
		return salesmanList;
	}

	public void setSalesmanList(List<UserDTO> salesmanList) {
		this.salesmanList = salesmanList;
	}

	public SellerDTO getSellerDTO() {
		return sellerDTO;
	}

	public void setSellerDTO(SellerDTO sellerDTO) {
		this.sellerDTO = sellerDTO;
	}

	public ConsumerQueryDTO getConsumerQueryDTO() {
		return consumerQueryDTO;
	}

	public void setConsumerQueryDTO(ConsumerQueryDTO consumerQueryDTO) {
		this.consumerQueryDTO = consumerQueryDTO;
	}

	public ConsumerDTO getConsumerDTO() {
		return consumerDTO;
	}

	public void setConsumerDTO(ConsumerDTO consumerDTO) {
		this.consumerDTO = consumerDTO;
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
	
	public String record() throws Exception{
		try{
			consumerQueryDTO.setEntityId(getUser().getEntityId());
			pageDataDTO=(PageDataDTO) sendService(ConstCode.QUERY_FOR_Consumer, consumerQueryDTO).getDetailvo();
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
			consumerQueryDTO.setFatherEntityId(getUser().getEntityId());
			consumerDTO=(ConsumerDTO) sendService(ConstCode.selectEntity_FOR_Consumer,consumerQueryDTO).getDetailvo();
			if(hasActionErrors()){
				return "input";
			}
		}catch(Exception e){
			this.logger.error(e.getMessage());
		}
		return "configEntiyId";
	}
	/**
	 * 查询收单机构信息
	 * @return
	 * @throws Exception
	 */
	public String query(){
		try {
			ListPageInit(null, consumerQueryDTO);
			if (consumerQueryDTO.isQueryAll()) {
				consumerQueryDTO.setQueryAll(false);
				consumerQueryDTO.setRowsDisplayed(Integer.parseInt(SystemInfo.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
			}
			pageDataDTO = (PageDataDTO) sendService(ConstCode.CONSUMER_SERVICE_INQUERY, consumerQueryDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (hasErrors()) {
				return "input";
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";
	}
	
	/**
	 * 选择收单机构中查询收单机构信息
	 * @return
	 * @throws Exception
	 */
	public String consumerChooseQuery(){
		try {
			ListPageInit(null, consumerQueryDTO);
			if (consumerQueryDTO.isQueryAll()) {
				consumerQueryDTO.setQueryAll(false);
				consumerQueryDTO.setRowsDisplayed(Integer.parseInt(SystemInfo.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
			}
			pageDataDTO = (PageDataDTO) sendService(ConstCode.CONSUMER_SERVICE_INQUERY, consumerQueryDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (hasErrors()) {
				return "input";
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";
	}
	/**
	 * 转向收单机构增加
	 * @return
	 * @throws Exception
	 */
   public String reAdd()throws Exception{
//	   this.sendService(ConstCode.CONSUMER_CHECK,consumerDTO);
	   if(this.hasErrors()){
			query();
			return "input";
		}
	   sellerDTO = (SellerDTO) this.sendService(ConstCode.SELLER_SERVICE_INIT,
				sellerDTO).getDetailvo();
	   
		if (sellerDTO != null) {
			salesmanList = sellerDTO.getSalesmanList();
		}
		List<String> flist=new ArrayList<String>();
		flist.add("0");
		flist.add("4");
		userDTO=new UserDTO();
		userDTO.setFunctionRoleId(flist);
		userDTO.setEntityId(getUser().getEntityId());
		userDTO.setIsSaleFlage("2");
		List<ResourceDTO> resourceDTOs = (List<ResourceDTO>) sendService(
				ConstCode.ROLE_SERVICE_SELECTISSERRESOURCE,userDTO).getDetailvo();
		
		JSONArray jsonObject = JSONArray.fromObject(resourceDTOs);
		menuList = jsonObject.toString();
    return "reAdd"; 
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
   /**
    * 添加收单机构信息
    * @return
    * @throws Exception
    */
   public String insertConsumer(){
	  try{
	   checkPassword();
	   if (this.hasActionErrors() || this.hasFieldErrors()) {
		   return INPUT;
	   }
	   consumerDTO.setUserPassword(MD5EncryptAlgorithm.md5(password));
	   consumerDTO.setCreateUser(getUser().getUserId());
	   if(!"".equals(consumerDTO.getWebsitePassword())){
		   consumerDTO.setWebsitePassword(MD5EncryptAlgorithm.md5(consumerDTO.getWebsitePassword()));
		}
	   if(consumerDTO.getSettleDatePre()!=null&&!consumerDTO.getSettleDatePre().equals("")){
		   consumerDTO.setSettleDatePre(DateUtil.getFormatTime(consumerDTO.getSettleDatePre()));
	   }
	   if(consumerDTO.getSettleDateNext()!=null&&!consumerDTO.getSettleDateNext().equals("")){
		   consumerDTO.setSettleDateNext(DateUtil.getFormatTime(consumerDTO.getSettleDateNext()));
	   }
	  consumerDTO=(ConsumerDTO) sendService(ConstCode.CONSUMER_SERVICE_INSERT,consumerDTO).getDetailvo();
	   if (this.hasActionErrors()) {
		    sellerDTO = (SellerDTO) this.sendService(ConstCode.SELLER_SERVICE_INIT,sellerDTO).getDetailvo();
			if (sellerDTO != null) {
				salesmanList = sellerDTO.getSalesmanList();
			}
			return INPUT;
		} else {
			addActionMessage("收单机构信息添加成功！");
			 return reEdit();
		}
	  }catch(Exception e){
		  this.logger.error(e.getMessage());
	  }
	 return "edit";
   }
   
   
	 public void validateInsertConsumer(){
		  sellerDTO = (SellerDTO) this.sendService(ConstCode.SELLER_SERVICE_INIT,sellerDTO).getDetailvo();
			if (sellerDTO != null) {
				salesmanList = sellerDTO.getSalesmanList();
			}
			List<String> flist=new ArrayList<String>();
			flist.add("0");
			flist.add("4");
			userDTO=new UserDTO();
			userDTO.setFunctionRoleId(flist);
			userDTO.setEntityId(getUser().getEntityId());
			userDTO.setIsSaleFlage("2");
			List<ResourceDTO> resourceDTOs = (List<ResourceDTO>) sendService(
					ConstCode.ROLE_SERVICE_SELECTISSERRESOURCE,userDTO).getDetailvo();
			JSONArray jsonObject = JSONArray.fromObject(resourceDTOs);
			menuList = jsonObject.toString();
			if(consumerDTO.getEnableWebsite()!=null){
		    	if("".equals(consumerDTO.getWebsiteUserName().trim())){
					this.addFieldError("consumerDTO.websiteUserName", "网站登录名不能为空");
					return ;
				}
				if("".equals(consumerDTO.getWebsitePassword().trim())){
					this.addFieldError("consumerDTO.websitePassword", "密码不能为空");
					return ;
				}
				
				if("".equals(consumerDTO.getWebsiteUserName()) && !"".equals(consumerDTO.getWebsitePassword())){
					this.addFieldError("consumerDTO.websiteUserName", "密码已填写网站登录名必须填写");
					return ;
				}
				if(!"".equals(consumerDTO.getWebsiteUserName()) && "".equals(consumerDTO.getWebsitePassword())){
					this.addFieldError("consumerDTO.websitePassword", "网站登录名已填写密码必须填写");
					return;
				}
		    	if(!consumerDTO.getWebsitePassword().matches("[^\\s]{6,16}")){
		    		this.addFieldError("consumerDTO.websitePassword", "必须由字母和数字组成开头为字母不包含空格(6-16)");
					return;
		    	}
		    	if(!consumerDTO.getWebsitePassword().matches("^[a-zA-Z](?=.*?\\d)[a-zA-Z0-9]{5,15}")){
		    		this.addFieldError("consumerDTO.websitePassword", "必须由字母和数字组成开头为字母(6-16)");
		    		return ;
		    	}
		    	}
	 }
   
	 /**
	  * 转向收单机构编辑
	  * @return
	  * @throws Exception
	  */
    public String reEdit()throws Exception{
        sellerDTO = (SellerDTO) this.sendService(ConstCode.SELLER_SERVICE_INIT,sellerDTO).getDetailvo();
			if (sellerDTO != null) {
				salesmanList = sellerDTO.getSalesmanList();
			}
	    if(consumerIdList!=null&&consumerIdList.size()>0){
	    	consumerDTO.setEntityId(consumerIdList.get(0));
	    }
    	consumerDTO.setDefaultEntityId(getUser().getEntityId());
    	consumerDTO=(ConsumerDTO) sendService(ConstCode.CONSUMER_SERVICE_VIEW,consumerDTO).getDetailvo();
    	
    	if (consumerDTO.getJoinDate()!=null) {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			consumerDTO.setJoinDateDate(df.parse(consumerDTO.getJoinDate()));
		}
    	if (consumerDTO.getSettleDatePre()!=null) {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			consumerDTO.setSettleDatePreDate(df.parse(consumerDTO.getSettleDatePre()));
		}
    	if (consumerDTO.getSettleDateNext()!=null) {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			consumerDTO.setSettleDateNextDate(df.parse(consumerDTO.getSettleDateNext()));
		}
    	if (consumerDTO != null) {
			invoiceAddressList = consumerDTO.getInvoiceAddressList();
			invoiceCompanyList = consumerDTO.getInvoiceCompanyList();
			deliveryPointList = consumerDTO.getDeliveryPointList();
			contractList = consumerDTO.getContractList();
			departmentList = consumerDTO.getDepartmentList();
			bankList=consumerDTO.getBankList();
		}
    	this.initEntityId();
    	this.initNameSpace();
    	
		
		JSONArray jsonObject = JSONArray.fromObject(consumerDTO.getResourceDTOs());
		menuList = jsonObject.toString();
		JSONArray jsonObject1 = JSONArray.fromObject(consumerDTO.getNresourceDTOs());
		nmenuList=jsonObject1.toString();
    	
    	if (hasErrors()) {
			return "input";
		}
		return "edit";
    }
   
    /**
     * 编辑收单机构
     * @return
     * @throws Exception
     */
    public String edit()throws Exception{
    	 if(consumerDTO.getJoinDateDate()!=null){
    		 consumerDTO.setJoinDate(DateUtil.date2String(consumerDTO.getJoinDateDate())) ;
    	 } 
    	 if(consumerDTO.getSettleDatePreDate()!=null){
    		 consumerDTO.setSettleDatePre(DateUtil.date2String(consumerDTO.getSettleDatePreDate())) ;
    	 } 
    	 if(consumerDTO.getSettleDateNextDate()!=null){
    		 consumerDTO.setSettleDateNext(DateUtil.date2String(consumerDTO.getSettleDateNextDate())) ;
    	 } 
    	 if(null==consumerDTO.getEnableWebsite()){
    		 
    		 consumerDTO.setEnableWebsite("0");//未开通网站管理
    	 }
    	consumerDTO.setModifyUser(getUser().getUserId());
    	 sendService(ConstCode.CONSUMER_SERVICE_EDIT,consumerDTO);
    	
    	if (hasErrors()) {
    		sellerDTO = (SellerDTO) this.sendService(ConstCode.SELLER_SERVICE_INIT,sellerDTO).getDetailvo();
			if (sellerDTO != null) {
				salesmanList = sellerDTO.getSalesmanList();
			}
			return "input";
		}else{
			addActionMessage("收单机构信息编辑成功！");
		}
		return query();
    }
    
    public void validateEdit(){
		  sellerDTO = (SellerDTO) this.sendService(ConstCode.SELLER_SERVICE_INIT,sellerDTO).getDetailvo();
			if (sellerDTO != null) {
				salesmanList = sellerDTO.getSalesmanList();
			}
		    if(consumerIdList!=null&&consumerIdList.size()>0){
		    	consumerDTO.setEntityId(consumerIdList.get(0));
		    }
	    	consumerDTO.setDefaultEntityId(getUser().getEntityId());
	    	ConsumerDTO consumerDTO_tmp=(ConsumerDTO) sendService(ConstCode.CONSUMER_SERVICE_VIEW,consumerDTO).getDetailvo();
	    	JSONArray jsonObject = JSONArray.fromObject(consumerDTO_tmp.getResourceDTOs());
			menuList = jsonObject.toString();
			JSONArray jsonObject1 = JSONArray.fromObject(consumerDTO_tmp.getNresourceDTOs());
			nmenuList=jsonObject1.toString();
	 }
    /**
     * 查看收单机构信息
     * @return
     * @throws Exception
     */
   public String view()throws Exception{
	   if(null!=consumerQueryDTO.getEntityId()){
   	consumerDTO.setEntityId(consumerQueryDTO.getEntityId());
	   }
   	consumerDTO=(ConsumerDTO) sendService(ConstCode.CONSUMER_SERVICE_VIEW,consumerDTO).getDetailvo();
    sellerDTO = (SellerDTO) this.sendService(ConstCode.SELLER_SERVICE_INIT,sellerDTO).getDetailvo();
	if (sellerDTO != null) {
		salesmanList = sellerDTO.getSalesmanList();
		
	}
   	if (consumerDTO.getJoinDate()!=null) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		consumerDTO.setJoinDateDate(df.parse(consumerDTO.getJoinDate()));
	}
	if (consumerDTO.getSettleDatePre()!=null) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		consumerDTO.setSettleDatePreDate(df.parse(consumerDTO.getSettleDatePre()));
	}
	if (consumerDTO.getSettleDateNext()!=null) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		consumerDTO.setSettleDateNextDate(df.parse(consumerDTO.getSettleDateNext()));
	}
	bankList=consumerDTO.getBankList();
	JSONArray jsonObject = JSONArray.fromObject(consumerDTO.getResourceDTOs());
	menuList = jsonObject.toString();
	JSONArray jsonObject1 = JSONArray.fromObject(consumerDTO.getNresourceDTOs());
	nmenuList=jsonObject1.toString();
   	if (hasErrors()) { 
   		return "input";
   	}
   	return "view";
   }
   
   public String delete()throws Exception{
	   List<ConsumerDTO> consumerDTOs = new ArrayList<ConsumerDTO>();
		for (String id : consumerIdList) {
			consumerDTO = new ConsumerDTO();
			consumerDTO.setEntityId(id);
			consumerDTOs.add(consumerDTO);
		}
		consumerDTO.setConsumerDTOs(consumerDTOs);
		consumerDTO.setModifyUser(this.getUser().getUserId());
		consumerDTO.setFatherEntityId(this.getUser().getEntityId());
		sendService(ConstCode.CONSUMER_SERVICE_DELETE, consumerDTO);
		if (!this.hasErrors()) {
			addActionMessage("收单机构信息删除成功！");
		}
		return query();
   }
    
   /**
    * 收单机构选择
    */
   public String chooseConsumer()throws Exception{
	   try {
			ListPageInit(null, consumerQueryDTO);
			if (consumerQueryDTO.isQueryAll()) {
				consumerQueryDTO.setQueryAll(false);
				consumerQueryDTO.setRowsDisplayed(Integer.parseInt(SystemInfo.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
			}
			pageDataDTO = (PageDataDTO) sendService(ConstCode.CONSUMER_SERVICE_INQUERY, consumerQueryDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (hasErrors()) {
				return "input";
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";  
   }
   public UserDTO getUserDTO() {
	return userDTO;
}

public void setUserDTO(UserDTO userDTO) {
	this.userDTO = userDTO;
}

public String consumerModifyPassword(){
   	try{
   		getUserId();
   		userDTO = (UserDTO) sendService(ConstCode.USER_SERVICE_VIEW, userDTO)
			.getDetailvo();
   		if(hasActionErrors()){
   			return "input";
   		}
   	}catch(Exception e){
   		this.logger.error(e.getMessage());
   	}
   	return "consumerModifyPassword";
   }
   public String consumerUpdatePassword(){
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
   	return query();
   }
   public void validateConsumerUpdatePassword(){
	   this.checkPassword();
   }
   /**
	 * 初始化每个实体的父实体id(entityId)
	 */
	public void initEntityId() throws Exception {
		entityId = consumerDTO.getEntityId();
	}
	
	public void initNameSpace() throws Exception{
		nameSpace = "consumer";
	}
	
	public String reloadForEntity() throws Exception{
		return this.reEdit();
	}
   
	 /**
	 * modifyUserState
	 * @return
	 */
	public String modifyUserState() throws Exception{
			try{
				getUserId();
	    		userDTO = (UserDTO) sendService(ConstCode.USER_SERVICE_VIEW, userDTO)
				.getDetailvo();
	    		if(hasActionErrors()){
	    			return "input";
	    		}
	    	}catch(Exception e){
	    		this.logger.error(e.getMessage());
	    	}
	    	return "modifyUserState";
	}
	
	/**
	 * updateUserState
	 * @return
	 */
	public String updateUserState() throws Exception{
			userDTO.setModifyUser(getUser().getUserId());
			sendService(ConstCode.USER_SERVICE_UPDATEUSER, userDTO);
    		if(hasActionErrors()){
    			return "input";
    		}
    		addActionMessage("用户信息修改成功!");
    		return query();
	}
	/**
	 * 收单机构网站修改密码
	 * 
	 */
	public String loadForModifyPassowrd() throws Exception{
		consumerDTO.setFatherEntityId(getUser().getEntityId());
		
		consumerDTO = (ConsumerDTO)sendService(ConstCode.ADMIN_CONSUMER_MODIFY_PASSWORD,consumerDTO).getDetailvo();
		if(this.hasErrors()){
			query();
			return "modifyfailed";
		}
		return "modify";
	}
	public String modifWebPassword() throws Exception{
		consumerDTO.setFatherEntityId(getUser().getEntityId());
		consumerDTO.setWebsitePassword(MD5EncryptAlgorithm.md5(password));
		sendService(ConstCode.ADMIN_CONSUMER_UPDATE,consumerDTO);
		if(this.hasErrors()){
			return "failed";
		}else{
			this.addActionMessage("修改密码改成功!");
		}
		return query();
	}
	/**
	 * 
	 * 核对web登录名是否可用
	 * @throws Exception
	 */
	public void checkWebName() throws Exception{
		String message=(String)sendService(ConstCode.ADMIN_CONSUMER_CHECK_NAME,consumerDTO).getDetailvo();
		if(this.hasErrors()){
			message=(String) getActionErrors().toArray()[0];
		}
		getResponse().setContentType("application/json; charset=utf-8");
		getResponse().setCharacterEncoding("utf-8");
		getResponse().getWriter().println(message);
		getResponse().getWriter().close();
	}
	
	/*
	 * 取用户编号
	 */
	public void getUserId() throws Exception{
		if(userIdList!=null && userIdList.size()>0){
		    userDTO = new UserDTO();
			userDTO.setUserId(userIdList.get(0));
	    }
	}
}
