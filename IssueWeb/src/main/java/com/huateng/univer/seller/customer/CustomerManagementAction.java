package com.huateng.univer.seller.customer;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.JudgeInforDTO;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.entity.dto.BankDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.DeliveryPointDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.entity.dto.InvoiceCompanyDTO;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageDTO;
import com.allinfinance.univer.issuer.dto.product.ProdFaceValueDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.customer.CustomerQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoDTO;
import com.allinfinance.univer.system.user.dto.UserDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.DictInfoConstants;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.util.SystemInfo;
import com.huateng.univer.entity.EntityBaseAction;
import com.huateng.univer.system.dictinfo.DictInfoAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CustomerManagementAction extends EntityBaseAction {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(CustomerManagementAction.class);
	private CustomerDTO customerDTO = new CustomerDTO();
	private CustomerQueryDTO customerQueryDTO = new CustomerQueryDTO();
	private PageDataDTO pageDataDTO = new PageDataDTO();
	private List<UserDTO> salesmanList = new ArrayList<UserDTO>();
	
	private List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
	private List<CardLayoutDTO> cardLayouts = new ArrayList<CardLayoutDTO>();
	private List<ServiceDTO> services = new ArrayList<ServiceDTO>();
	private List<ProdFaceValueDTO> prodFaceValues = new ArrayList<ProdFaceValueDTO>();
	private JSONArray saleUserList;
	private List<?> customerOrderLists = new ArrayList();
	private int customerOrderLists_totalRows =0;
	private String productName;
	private List<PackageDTO> packages = new ArrayList<PackageDTO>();
	private BigDecimal temp = new BigDecimal("100");
	private SellOrderDTO sellOrderDTO = new SellOrderDTO();
	private SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();

	private Integer totalRows;
	private String[] customerIds;
	private DictInfoAction dictInfoAction;
	private String personFlag; 
	private String returnEntityId;
	private String banklistsize; 
	private String invoiceAddressListSize; 
	private String invoiceCompanyListSize; 
	private String deliveryPointListSize; 
	private String contractListSize; 
	private String departmentListSize;
	private String orderType;
	private String verifierViewFlag="";
	private String userId;
	private String customerChoiceForContract="0";
	/**
	 * 初始化每个实体的父实体id(entityId)
	 */
	@Override
	public void initEntityId() throws Exception {
		entityId = customerDTO.getEntityId();
	}
	
	public String getCustomerChoiceForContract() {
		return customerChoiceForContract;
	}

	public void setCustomerChoiceForContract(String customerChoiceForContract) {
		this.customerChoiceForContract = customerChoiceForContract;
	}

	public String getVerifierViewFlag() {
		return verifierViewFlag;
	}

	public void setVerifierViewFlag(String verifierViewFlag) {
		this.verifierViewFlag = verifierViewFlag;
	}

	@Override
	public void initNameSpace() throws Exception{
		nameSpace = "customer";
	}
	
	@Override
	public String reloadForEntity() throws Exception{
		return this.edit();
	}
	/**
	 * 返回销售区域列表
	 * 
	 * @return
	 */
//	public JSONObject getSalesRegions() {
//		Map<String, List<DictInfoDTO>> salesRegions = new HashMap<String, List<DictInfoDTO>>();
//		List<DictInfoDTO> dictInfoDTOList = SystemInfo.getDictList("405");
//		if (dictInfoDTOList != null) {
//			for (DictInfoDTO dictInfoDTO : dictInfoDTOList) {
//				salesRegions.put(dictInfoDTO.getDictId(), SystemInfo
//						.getSubDictList("408", new Integer(dictInfoDTO
//								.getDictId())));
//			}
//		}
//		return JSONObject.fromObject(salesRegions);
//	}
	
	/**
	 * 返回销售区域列表
	 * 
	 * @return
	 */
	public JSONObject getSalesRegions() throws Exception {
		//dictInfoAction.init();
		String entityId = getUser().getEntityId();
		Map<String, List<EntityDictInfoDTO>> salesRegions = new HashMap<String, List<EntityDictInfoDTO>>();
		List<EntityDictInfoDTO> dictInfoDTOList = SystemInfo.getDictList(entityId, "405");
		if (dictInfoDTOList != null) {
			for (EntityDictInfoDTO dictInfoDTO : dictInfoDTOList) {
				List<EntityDictInfoDTO> list = SystemInfo.getSubDictList(entityId, "408", new Integer(dictInfoDTO.getDictCode()));
				salesRegions.put(dictInfoDTO.getDictCode(), list);
			}
		}
		return JSONObject.fromObject(salesRegions);
	}

	public String list() {
		try {
			ListPageInit(null, customerQueryDTO);
			if(null != customerQueryDTO.getStartTime() && null!=customerQueryDTO.getStopTime()){
				if(customerQueryDTO.getStartTime().getTime()> customerQueryDTO.getStopTime().getTime()){
		    		  this.addActionError("开始日期不能大于结束日期");
		    	 }
			}
			if(null==verifierViewFlag|| "".equals(verifierViewFlag) ||verifierViewFlag.equals("0")){//客户信息管理时，客户信息查看
				
				return inquery();		
			}
			 else{
				      if(verifierViewFlag.equals("2")){//添加持卡人时，客户信息查看
				    	  customerQueryDTO.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
							if(personFlag==null||"".equals(personFlag)){
								customerQueryDTO.setCusState("1");
							}else{
								customerQueryDTO.setCusState("2");
								if("per".equals(personFlag)){
									customerQueryDTO.setCustomerType("1");
									customerQueryDTO.setCusState("1");
								}
								if("con".equals(personFlag)){
									customerQueryDTO.setCusState("1");
									customerQueryDTO.setCustomerType("0");
								}
							}
							inquery();	
				    	  return "choice";
				      }
				      else if(verifierViewFlag.equals("1")){//客户审核信息管理时，客户信息查看
				    	  customerQueryDTO.setFlag("1");
				    	  
						pageDataDTO = (PageDataDTO) sendService(
								ConstCode.CUSTOMER_SERVICE_INQUERY, customerQueryDTO)
								.getDetailvo();
						if (this.hasErrors()) {
							this.addActionError("查询客户信息失败!");
						}
						if (pageDataDTO != null) {
							totalRows = pageDataDTO.getTotalRecord();
						  }
					return "verifierList";
				      }
				      
				      else{//添加客户合同时，查看客户信息
				    	  //customerQueryDTO.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
						
								
								if("per".equals(personFlag)){
									customerQueryDTO.setCustomerType("1");
									customerQueryDTO.setCusState("1");
								}
								if("con".equals(personFlag)){
									customerQueryDTO.setCusState("1");
									customerQueryDTO.setCustomerType("0");
								}
							
							//	customerQueryDTO.setDataState("1");
							inquery();	
				    	  return "contractChoice";  
				    	  
				      }	
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return null;
	}
	
	public String inquery() {
		try {
			
			ListPageInit(null, customerQueryDTO);
			
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.CUSTOMER_SERVICE_INQUERY, customerQueryDTO)
					.getDetailvo();
			//去掉企业的职业(),办法不好暂时没有找到更好的办法
			// deleteEnterpriseJob(pageDataDTO.getData());
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "list";
	}
	
	public String view() throws Exception {
		if (null == customerDTO.getEntityId()) {
			if (null != customerIds && customerIds.length == 1) {
				String[] keyId = customerIds[0].split(",");
				customerDTO.setEntityId(keyId[0]);
				customerDTO.setFatherEntityId(keyId[1]);
			}
		}
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
			bankList=customerDTO.getBankList();

			banklistsize = String.valueOf(customerDTO.getBankList().size());
			invoiceAddressListSize = String.valueOf(customerDTO.getInvoiceAddressList().size()); 
			invoiceCompanyListSize = String.valueOf(customerDTO.getInvoiceCompanyList().size()); 
			deliveryPointListSize = String.valueOf(customerDTO.getDeliveryPointList().size()); 
			contractListSize = String.valueOf(customerDTO.getContractList().size()); 
			departmentListSize = String.valueOf(customerDTO.getDepartmentList().size()); 
			
			this.initEntityId();
		}
		this.initNameSpace();
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
		userId = getUser().getUserId();
		customerDTO = (CustomerDTO) this.sendService(
				ConstCode.CUSTOMER_SERVICE_INIT, customerDTO).getDetailvo();
		if (customerDTO != null) {
			salesmanList = customerDTO.getSalesmanList();
		}
		customerDTO.setIsblacklist("0");
		customerDTO.setRiskGrade("O");
		if("1".equals(this.getPersonFlag())){
			customerDTO.setCreditStatus("0");
			return "addPerson";
		}else{
			customerDTO.setCreditStatus("0");
//			customerDTO.setAwareness("0");
			return "add";
		}
	}
	/**
	 * 根据企业证件类型判断是否重复
	 */
	
	public void checkLicense() {
		try {
			boolean existFlag = false;
			this.sendService(ConstCode.QUERY_COMPANY_BY_IDNO, customerDTO);
			// 根据身份证号查询未注销客户信息，判断是否已经存在该客户信息
			if(hasActionErrors()) {//后台如果有同类型的公司则抛出异常
				existFlag=true;
			}	
			getResponse().setContentType("application/json; charset=utf-8");

			getResponse().setCharacterEncoding("utf-8");

			getResponse().getWriter().println(existFlag);

			getResponse().getWriter().close();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}
	
	/**
	 * 根据身份证号 取得客户信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void checkIdNo() {
		try {
			boolean existFlag = false;
			String corpCredId = customerDTO.getCorpCredId();// 客户证件号
			String type = customerDTO.getCorpCredType();// 证件类型
			if (type.equals("1")) {
				customerDTO.setCorpCredId(corpCredId.toUpperCase());
				customerDTO.setCorpCredType(type.toUpperCase());
			}
			List<CustomerDTO> datas = (ArrayList<CustomerDTO>) this
					.sendService(ConstCode.QUERY_CUSTOMER_BY_IDNO, customerDTO).getDetailvo();
			// 根据身份证号查询未注销客户信息，判断是否已经存在该客户信息
			if (datas.size() > 0) {
				existFlag = true;
			}
			getResponse().setContentType("application/json; charset=utf-8");

			getResponse().setCharacterEncoding("utf-8");

			getResponse().getWriter().println(existFlag);

			getResponse().getWriter().close();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}
	

	public void validateInsert() throws Exception {
		this.add();
	}

	@SuppressWarnings("unchecked")
	public String insert() throws Exception {
		if (null != customerDTO.getCloseDateDate()) {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			customerDTO.setCloseDate(df.format(customerDTO.getCloseDateDate()));
		}
//		String corpCredType = customerDTO.getCorpCredType();//客户证件类型
		String corpCredId = customerDTO.getCorpCredId();//客户证件号
		if(customerDTO.getCorpCredType().equals("1")){
			customerDTO.setCorpCredId(corpCredId.toUpperCase());
		}
		
		/*if (corpCredType.equals("1")) {
			String substring = corpCredId.substring(17, 18);
			if(!Character.isDigit(substring.charAt(0))){//不是数字
				if(!Character.isUpperCase(substring.charAt(0))){ //小写
					customerDTO.setCorpCredId(corpCredId.substring(0, 17)+"X");
				}
			}
		}*/
		
		if(customerDTO.getCtid()==null) {
			List<CustomerDTO> datas = (ArrayList<CustomerDTO>) this.sendService(ConstCode.QUERY_CUSTOMER_BY_IDNO, customerDTO).getDetailvo();
			// 根据身份证号查询未注销客户信息，判断是否已经存在该客户信息
			if (datas.size() > 0) {
				this.addActionError("该证件号已存在！");
			}
		}else {	//根据证件类型和证件号判断是否重复
		 	customerDTO.setCompanyId(customerDTO.getLicenseId());
			this.sendService(ConstCode.QUERY_COMPANY_BY_IDNO, customerDTO);
			customerDTO.setCompanyId(null);
		}	
		if (hasActionErrors()) {
			return INPUT;
		}
		
		customerDTO=(CustomerDTO)this.sendService(ConstCode.CUSTOMER_SERVICE_INSERT, customerDTO).getDetailvo();
		if (hasActionErrors()) {
			return INPUT;
		}
		view();
		this.addActionMessage("添加客户成功！");
		
		JudgeInforDTO dto =new JudgeInforDTO();
		dto.setJudgeType("5");
		dto.setUserType("1");
		dto.setEntityID(customerDTO.getEntityId());
		this.sendService(ConstCode.CUSTOMER_BLACK_RISK_JUDGE, dto);
		blockListOrRiskCheck();
		
		if("1".equals(customerDTO.getCustomerType())){
			return "editPer";
		}else{
			return "edit";
		}
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
	
	public String edit() throws Exception {
		view();
//		if("1".equals(customerDTO.getCusState())){
//			addActionError("客户信息审核过不能编辑");
//			return inquery();
//		}
		if("1".equals(customerDTO.getCustomerType())){
			return "editPer";
		}else{
		    return "edit";
		}
	}
	


	public void validateUpdate() throws Exception {
		this.add();
	}

	public String updateInfo() throws Exception {
//		if (null != customerDTO.getCloseDateDate()) {
//			DateFormat df = new SimpleDateFormat("yyyyMMdd");
//			customerDTO.setCloseDate(df.format(customerDTO.getCloseDateDate()));
//		}
		customerDTO.setCusState("4");
		this.sendService(ConstCode.CUSTOMER_SERVICE_UPDATE, customerDTO);
		if (!hasActionErrors()) {
			this.addActionMessage("编辑客户成功！");
		}
		
		return this.inquery();
	}
	
	public String update() throws Exception {
            customerDTO.setCusState("4");
            this.sendService(ConstCode.CUSTOMER_SERVICE_UPDATE, customerDTO);
            if (!hasActionErrors()) {
                    this.addActionMessage("编辑客户成功！");
            }
            
            return this.inquery();
    }

	public String modifyState() throws Exception {
		this.view();
		return "modifyState";
	}

	public String updateState() throws Exception {
		this.sendService(ConstCode.CUSTOMER_SERVICE_UPDATE, customerDTO);
		if (!hasActionErrors()) {
			this.addActionMessage("修改客户状态成功！");
		}		
		return this.inquery();
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

	/***
	 * 订单查询
	 * 
	 * @return
	 */
	public String chooseCustomer() {
		try {
			//customerQueryDTO.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			if(personFlag==null||"".equals(personFlag)){
				//customerQueryDTO.setCusState("1");
			}else{
				customerQueryDTO.setCusState("2");
				if("per".equals(personFlag)){
					customerQueryDTO.setCustomerType("1");
					customerQueryDTO.setCusState("1");
				}
				if("con".equals(personFlag)){
					customerQueryDTO.setCusState("1");
					customerQueryDTO.setCustomerType("0");
				}
			}
			//customerQueryDTO.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			list();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "choose";
	}
	
	public String chooseCustomerByCusType() {
		try {
			customerQueryDTO.setDataState(DataBaseConstant.DATA_STATE_NORMAL);
			list();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "chooseByCusType";
	}
	
	public String choice() {
		try {
			this.chooseCustomer();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		if(null!=customerChoiceForContract&&customerChoiceForContract.equals("1")){
			return "contractChoice";
		}
		else return "choice";
	}
	
	public String choiceInOrder() {
		try {
			customerQueryDTO.setEntityId(customerDTO.getEntityId());
			this.chooseCustomer();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "choice";
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

	/**
	 * 订单录入中快速添加客户信息
	 * @return
	 */
	public String quickAddCustomer(){
		String returnValue = "";
		userId = getUser().getUserId();
		customerDTO = (CustomerDTO) this.sendService(
				ConstCode.CUSTOMER_SERVICE_INIT, customerDTO).getDetailvo();
		if (customerDTO != null) {
			salesmanList = customerDTO.getSalesmanList();
		}
		if(personFlag.equals("con")){//跳转到企业客户添加页面
			returnValue = "quickAddCon";
		}else{//跳转到个人客户添加页面
			returnValue = "quickAddPer";
		}
		return returnValue;
	}

	/**
	 * 快速添加客户操作
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String quickInsertCustomer() throws Exception {
		// 添加客户操作，客户状态已审核
		if (null != customerDTO.getCloseDateDate()) {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			customerDTO.setCloseDate(df.format(customerDTO.getCloseDateDate()));
		}
		customerDTO.setCusState("1");
		if(customerDTO.getCorpCredType().equals("1")){
			customerDTO.setCorpCredId(customerDTO.getCorpCredId().toUpperCase());
		}
		
		customerDTO = (CustomerDTO) this.sendService(
				"2001020302", customerDTO).getDetailvo();
		if (hasActionErrors()) {
			return INPUT;
		}
		
		// 根据合同模板，生成新的合同（将合同模板具有的产品明细同样添加到新合同上）,并将新合同绑定到新客户上
		//this.sendService(ConstCode.CUSTOMER_SERVICE_QIUCK_INSERT, customerDTO);
		
		this.view(); 
		String returnValue = "";
		returnValue = personFlag.equals("per")?"quickEditPer":"quickEditCon";
		return returnValue; 
	} 
	
	/**
	 * 快速添加客户后的 编辑更新操作
	 * 
	 * @return
	 */
	public void quickUpdate(){
		orderType = this.getOrderType();
		this.sendService(ConstCode.CUSTOMER_SERVICE_UPDATE, customerDTO); 
	}
	
	public String quickEdit() throws Exception {
		view(); 
		if ("1".equals(customerDTO.getCustomerType())) {
			if (personFlag.equals("per")) {
				return "quickEditPer";
			} else {
				return "editPer";
			}
		} else if ("0".equals(customerDTO.getCustomerType())) {
			return "quickEditCon";
		} else {
			return "edit";
		}
	}
	
	public String quickEditCustomer(){
		orderType = this.getOrderType();
		customerDTO.setEntityId(returnEntityId);
		customerDTO = (CustomerDTO) this.sendService(
				ConstCode.CUSTOMER_SERVICE_VIEW, customerDTO).getDetailvo();
		 
		sellOrderDTO.setFirstEntityId(customerDTO.getEntityId());
		sellOrderInputDTO.setCustomerDTO(customerDTO);
		
		sellOrderDTO.setOrderType(orderType);
		sellOrderDTO.setOrderSource(OrderConst.ORDER_SOURCE_SYSTEM_INPUT);//订单来源于系统录入
		sellOrderDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);//订单状态为草稿状态  
		sellOrderDTO.setPaymentState(OrderConst.ORDER_PAY_STATE_UNPAID);//订单状态为未支付
		
		sellOrderDTO.setCardQuantity("0");//订单张数
		sellOrderDTO.setProcessEntityId(getUser().getEntityId());
		
		if(isEmpty(sellOrderDTO.getOrderDate())){//默认订单日期为当天
			sellOrderDTO.setOrderDate(getDateFormat(new Date()));
		}
		if(isEmpty(sellOrderDTO.getInvoiceDate())){//设置发票日期
			sellOrderDTO.setInvoiceDate(getDateFormat(new Date()));
		}
		if(isEmpty(sellOrderDTO.getDeliveryFee())){//默认送货费用为0
			sellOrderDTO.setDeliveryFee("0");
		}
		if(isEmpty(sellOrderDTO.getDiscountFee())){//默认折扣费为0
			sellOrderDTO.setDiscountFee("0");
		} 
		if(isEmpty(sellOrderDTO.getAdditionalFee())){//默认附加费为0
			sellOrderDTO.setAdditionalFee("0");
		} 
		sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);//将订单相关信息带入inputDTO中
		sellOrderInputDTO=(SellOrderInputDTO)sendService(ConstCode.SELL_ORDER_INIT,
				sellOrderInputDTO).getDetailvo();
		 
		saleUserList = JSONArray.fromObject(sellOrderInputDTO.getSaleUserList());
		
		productDTOs = sellOrderInputDTO.getProductDTOs();
		
		ProductDTO productDTO = sellOrderInputDTO.getProductDTO();
		String tempMaxBalanceString="";
		if(productDTO!= null && productDTO.getMaxBalance()!= null && !productDTO.getMaxBalance().equals("")){
			//tempMaxBalanceString=Integer.parseInt(productDTO.getMaxBalance())/100;
			tempMaxBalanceString=new BigDecimal(productDTO.getMaxBalance()).divide(temp).toString();
		}
		getRequest().setAttribute("currentProdMaxBalance", tempMaxBalanceString);
		sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
		sellOrderDTO.setProcessEntityName(getUser().getSellerName());
		
		if(isEmpty(sellOrderDTO.getSaleMan())&&sellOrderInputDTO.getSaleUserList().size()!=0){
			String userId=sellOrderInputDTO.getSaleUserList().get(0).getUserId();
			sellOrderDTO.setSaleMan(userId);
		}
		
		if(productDTO!=null&&productDTO.getProductId()!=null &&!"".equals(productDTO.getProductId())){
			cardLayouts = productDTO.getCardLayoutDTOs();//卡面列表
			packages = productDTO.getPackages();//包装列表
			services = productDTO.getServices();//服务列表
			prodFaceValues = productDTO.getProdFaceValueDTO();//面额列表
			productName=productDTO.getProductName();
			if(isEmpty(sellOrderDTO.getCardIssueFee())){
				sellOrderDTO.setCardIssueFee(productDTO.getCardFee());
			}
			if(isEmpty(sellOrderDTO.getAnnualFee())){
				sellOrderDTO.setAnnualFee(productDTO.getAnnualFee());
			}
		}
		//customerDTO = sellOrderInputDTO.getCustomerDTO();
		if("1".equals(customerDTO.getCustomerType())){
			sellOrderDTO.setPerFlag("per");
		}
		customerOrderLists = sellOrderDTO.getOrderCardList().getData();
		customerOrderLists_totalRows =  sellOrderDTO.getOrderCardList().getTotalRecord();
		
		if(sellOrderInputDTO.getSellOrderDTO()!=null){
	    	ProductDTO productDTO1 = sellOrderInputDTO.getProductDTO();
			if(productDTO1!=null&&productDTO1.getProductId()!=null &&!"".equals(productDTO1.getProductId())){
				if(isNotEmpty(sellOrderDTO.getCardIssueFee())){
					sellOrderDTO.setCardIssueFee(productDTO1.getCardFee());
				}
				if(isNotEmpty(sellOrderDTO.getAnnualFee())){
					sellOrderDTO.setAnnualFee(productDTO1.getAnnualFee());
				}
			}
		} 
		//将订单的支付方式修改为客户的默认支付方式 
		sellOrderDTO.setPaymentTerm(customerDTO.getPaymentTerm());
		//如果支付方式为非实时支付 那么设置延期天数
		if(DictInfoConstants.PAY_MENT_IS_NOT_NEED_PAY_IMMEDIATELY.equals(sellOrderDTO.getPaymentTerm())
				&&("0".equals(sellOrderDTO.getPaymentDelay())||"".equals(sellOrderDTO.getPaymentDelay()))){
			sellOrderDTO.setPaymentDelay(customerDTO.getPaymentDelay());
		} 
		
		String returnValue = "";
		returnValue = orderType.equals("10000001")||orderType.equals("10000011")?"quickCustomerSign":"quickCustomerUnsign";
		return returnValue;  
	}
	
	/**
	 * 快速添加客户功能中的删除银行账户信息
	 * （返回值与EntityBaseAction中的方法不同）
	 */
	public String delBankQuick() throws Exception{
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
			addActionMessage("银行账户信息删除失败！");
		}
		addActionMessage("银行账户信息删除成功！");
		return this.quickEdit();
	}
	
	/**
	 * 快速添加客户功能中的删除部门信息
	 * （返回值与EntityBaseAction中的方法不同）
	 * 
	 */
	public String delDepartmentQuick() throws Exception {
		List<DepartmentDTO> departDTOLists = new ArrayList<DepartmentDTO>();
		for (String id : chooseDepartmentId) {
			departmentDTO = new DepartmentDTO();
			departmentDTO.setDepartmentId(id);
			departDTOLists.add(departmentDTO);
		}
		departmentDTO.setDepartmentDTO(departDTOLists);
		sendService(ConstCode.ENTITY_DEPARTMENT_SERVICE_DEL, departmentDTO);
		if (this.hasErrors()) {
			addActionMessage("部门信息删除失败！");
		}else{
			addActionMessage("部门信息删除成功！");
		}
		return this.quickEdit();
	}
	
	/**
	 * 快速添加客户功能中的删除联系人
	 * （返回值与EntityBaseAction中的方法不同）
	 * 
	 */
	public String delContactQuick() throws Exception {
		List<ContactDTO> contactDTOLists = new ArrayList<ContactDTO>();
		for (String id : chooseContactId) {
			contactDTO = new ContactDTO();
			contactDTO.setContactId(id);
			contactDTOLists.add(contactDTO);
		}
		contactDTO.setContactDTOList(contactDTOLists);
		sendService(ConstCode.ENTITY_CONTACT_SERVICE_DEL, contactDTO);
		if (this.hasErrors()) {
			addActionMessage("联系人信息删除失败！");
		}

		addActionMessage("联系人信息删除成功！");
		return this.quickEdit();
	}
	
	/**
	 * 快速添加客户功能中的删除快递信息
	 * （返回值与EntityBaseAction中的方法不同）
	 * 
	 */
	public String delDeliveryPointQuick() throws Exception {

		List<DeliveryPointDTO> deliveryPointDTOs = new ArrayList<DeliveryPointDTO>();
		for (String id : choosePointId) {
			deliveryPointDTO = new DeliveryPointDTO();
			deliveryPointDTO.setDeliveryId(id);
			deliveryPointDTOs.add(deliveryPointDTO);
		}
		deliveryPointDTO.setDeliveryPointDTOs(deliveryPointDTOs);
		this.sendService(ConstCode.ENTITY_DELIVERYPOINT_SERVICE_DEL,
				deliveryPointDTO);
		if (this.hasErrors()) {
			addActionMessage("快递信息删除失败！");
		}

		addActionMessage("快递信息删除成功！");
		return this.quickEdit();
	}
	
	/**
	 * 快速添加客户功能中的删除发票地址
	 * （返回值与EntityBaseAction中的方法不同）
	 */
	public String delAddressQuick() throws Exception {
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
			addActionMessage("发票地址信息删除失败！");
		}

		addActionMessage("发票地址信息删除成功！");
		return this.quickEdit();
	}
	
	/**
	 * 快速添加客户功能中的删除发票公司信息
	 * （返回值与EntityBaseAction中的方法不同）
	 */
	public String delCompanyQuick() throws Exception {
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
			addActionMessage("发票公司信息删除失败！");
		}
		addActionMessage("发票公司信息删除成功！");
		return this.quickEdit();
	}
	/////////////////////////////////////////////////////////////////////
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
	public void setProductDTOs(List<ProductDTO> productDTOs) {
		this.productDTOs = productDTOs;
	}
	public List<ProductDTO> getProductDTOs() {
		return productDTOs;
	}
	public List<CardLayoutDTO> getCardLayouts() {
		return cardLayouts;
	}
	public void setCardLayouts(List<CardLayoutDTO> cardLayouts) {
		this.cardLayouts = cardLayouts;
	}
	public List<ServiceDTO> getServices() {
		return services;
	}
	public void setServices(List<ServiceDTO> services) {
		this.services = services;
	}
	public List<ProdFaceValueDTO> getProdFaceValues() {
		return prodFaceValues;
	}
	public void setProdFaceValues(List<ProdFaceValueDTO> prodFaceValues) {
		this.prodFaceValues = prodFaceValues;
	}
	public JSONArray getSaleUserList() {
		return saleUserList;
	}
	public void setSaleUserList(JSONArray saleUserList) {
		this.saleUserList = saleUserList;
	}
	public List<?> getCustomerOrderLists() {
		return customerOrderLists;
	}
	public void setCustomerOrderLists(List<?> customerOrderLists) {
		this.customerOrderLists = customerOrderLists;
	}
	public int getCustomerOrderLists_totalRows() {
		return customerOrderLists_totalRows;
	}
	public void setCustomerOrderLists_totalRows(int customerOrderListsTotalRows) {
		customerOrderLists_totalRows = customerOrderListsTotalRows;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<PackageDTO> getPackages() {
		return packages;
	}
	public void setPackages(List<PackageDTO> packages) {
		this.packages = packages;
	}
	public BigDecimal getTemp() {
		return temp;
	}
	public void setTemp(BigDecimal temp) {
		this.temp = temp;
	}
	public void setReturnEntityId(String returnEntityId) {
		this.returnEntityId = returnEntityId;
	}
	public String getReturnEntityId() {
		return returnEntityId;
	}
	public SellOrderDTO getSellOrderDTO() {
		return sellOrderDTO;
	}
	public void setSellOrderDTO(SellOrderDTO sellOrderDTO) {
		this.sellOrderDTO = sellOrderDTO;
	}
	public SellOrderInputDTO getSellOrderInputDTO() {
		return sellOrderInputDTO;
	}
	public void setSellOrderInputDTO(SellOrderInputDTO sellOrderInputDTO) {
		this.sellOrderInputDTO = sellOrderInputDTO;
	}
	public void setBanklistsize(String banklistsize) {
		this.banklistsize = banklistsize;
	}
	public String getBanklistsize() {
		return banklistsize;
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

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getInvoiceAddressListSize() {
		return invoiceAddressListSize;
	}

	public void setInvoiceAddressListSize(String invoiceAddressListSize) {
		this.invoiceAddressListSize = invoiceAddressListSize;
	}

	public String getInvoiceCompanyListSize() {
		return invoiceCompanyListSize;
	}

	public void setInvoiceCompanyListSize(String invoiceCompanyListSize) {
		this.invoiceCompanyListSize = invoiceCompanyListSize;
	}

	public String getDeliveryPointListSize() {
		return deliveryPointListSize;
	}

	public void setDeliveryPointListSize(String deliveryPointListSize) {
		this.deliveryPointListSize = deliveryPointListSize;
	}

	public String getContractListSize() {
		return contractListSize;
	}

	public void setContractListSize(String contractListSize) {
		this.contractListSize = contractListSize;
	}

	public String getDepartmentListSize() {
		return departmentListSize;
	}

	public void setDepartmentListSize(String departmentListSize) {
		this.departmentListSize = departmentListSize;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/***************************************新增区域*****************************************/        
        /**
         * 查看企业客户
         * @return
         */
        public String choiceCus() {
            try {
                ListPageInit(null, customerQueryDTO);               
                pageDataDTO = (PageDataDTO) sendService(
                                ConstCode.CUSTOMER_SERVICE_INQUERYCUS, customerQueryDTO)
                                .getDetailvo();
                if (pageDataDTO != null) {
                        totalRows = pageDataDTO.getTotalRecord();
                }
            } catch (Exception e) {
                    this.logger.error(e.getMessage());
            }
            return "choiceCus";
    }
        
        /**
         * 查看个人客户
         * @return
         */
        public String choicePer() {
            try {
                ListPageInit(null, customerQueryDTO);               
                pageDataDTO = (PageDataDTO) sendService(
                                ConstCode.CUSTOMER_SERVICE_INQUERYPER, customerQueryDTO)
                                .getDetailvo();
                if (pageDataDTO != null) {
                        totalRows = pageDataDTO.getTotalRecord();
                }
            } catch (Exception e) {
                    this.logger.error(e.getMessage());
            }
            return "choicePer";
    }
        
        
   /***************************************新增区域*****************************************/
 
}
