package com.huateng.univer.issuer.productWithAccount.Product;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;



import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.http.client.support.HttpAccessor;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutDTO;
import com.allinfinance.univer.issuer.dto.cardLayOut.CardLayoutQueryDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageDTO;
import com.allinfinance.univer.issuer.dto.procuctPackage.PackageQueryDTO;
import com.allinfinance.univer.issuer.dto.product.ProdFaceValueDTO;
import com.allinfinance.univer.issuer.dto.product.ProdServiceDTO;
import com.allinfinance.univer.issuer.dto.product.ProductCardBinDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.product.ProductQueryDTO;
import com.allinfinance.univer.issuer.dto.product.ValidPeriodRuleDspDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceQueryDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.SystemInfo;
import com.opensymphony.xwork2.ActionContext;

public class ProductAction extends BaseAction{
	private Logger logger = Logger.getLogger(ProductAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1242914610547362961L;

	/**
	 * 关闭标识
	 */
	private boolean flag;
	/**
	 * 产品ID
	 */
	private String pid;

	/**
	 * 查询ID
	 */
	private List<String> choose = new ArrayList<String>();

	/**
	 * 服务ID
	 */
	private String acctypeId;
	private String isDisplay="none";
	private String ifDisplay="none";
	/**
	 * 面额ID
	 */
	private List<String> faceValuebox = new ArrayList<String>();

	/**
	 * 查询信息DTO
	 */
	private PageDataDTO pageDataDTO = new PageDataDTO();
	/**
	 * 查询条件DTO
	 */
	private ProductQueryDTO productQueryDTO = new ProductQueryDTO();
	/**
	 * 产品信息DTO
	 */
	private ProductDTO productDTO = new ProductDTO();
	/**
	 * 非固定面额的添加次数
	 */
	private String faceValueTypeCount;
	
	public String getIfDisplay() {
		return ifDisplay;
	}

	public void setIfDisplay(String ifDisplay) {
		this.ifDisplay = ifDisplay;
	}

	public String getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}

	public String getFaceValueTypeCount() {
		return faceValueTypeCount;
	}

	public void setFaceValueTypeCount(String faceValueTypeCount) {
		this.faceValueTypeCount = faceValueTypeCount;
	}

	/**
	 * 账户信息查询DTO
	 */
	private ServiceQueryDTO serviceQueryDTO = new ServiceQueryDTO();
	/**
	 * 产品与账户DTO
	 */
	private ProdServiceDTO prodAcctypeDTO = new ProdServiceDTO();
	/**
	 * 卡面查询DTO
	 */
	private CardLayoutQueryDTO cardLayouQueryDTO = new CardLayoutQueryDTO();
	/**
	 * 卡面信息DTO
	 */
	private CardLayoutDTO cardLayoutDTO = new CardLayoutDTO();
	/**
	 * 产品面额信息查询条件DTO
	 */
	private ProdFaceValueDTO prodFaceValueDTO = new ProdFaceValueDTO();
	/**
	 * 包装信息查询条件DTO
	 */
	private PackageQueryDTO packageQueryDTO = new PackageQueryDTO();
	/**
	 * 账户信息ID
	 */
	private List<String> serviceAccountId = new ArrayList<String>();

	/**
	 * 服务信息ID
	 */
	private List<String> acctypebox = new ArrayList<String>();

	/**
	 * 卡面信息ID
	 */
	private List<String> cardLayoutbox = new ArrayList<String>();
	/**
	 * 包装信息ID
	 */
	private List<String> packagebox = new ArrayList<String>();

	/**
	 * 所属发行机构
	 */
	List<Map<String, String>> entityList = new ArrayList<Map<String, String>>();
	/**
	 * 有效期规则
	 */
	List<Map<String, String>> validRuleList = new ArrayList<Map<String, String>>();


	private List<Map<String, Object>> cardBins = new ArrayList<Map<String, Object>>();

	private List<ProductCardBinDTO> cardBinDTOs = new ArrayList<ProductCardBinDTO>();
	
	private int cardBin_totalRows = 0;

	private String[] cardBinIds;

	private ProductCardBinDTO productCardBinDTO;

	private String rechargeTimesId;
	
	private String consumerTimesId;
	private String max;
	
	
	public List<Map<String, String>> getValidRuleList() {
		return validRuleList;
	}

	public void setValidRuleList(List<Map<String, String>> validRuleList) {
		this.validRuleList = (List<Map<String, String>>)validRuleList;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getAcctypeId() {
		return acctypeId;
	}

	public void setAcctypeId(String acctypeId) {
		this.acctypeId = acctypeId;
	}

	public List<String> getChoose() {
		return choose;
	}

	public void setChoose(List<String> choose) {
		this.choose = choose;
	}

	public List<String> getPackagebox() {
		return packagebox;
	}

	public void setPackagebox(List<String> packagebox) {
		this.packagebox = packagebox;
	}

	public List<String> getFaceValuebox() {
		return faceValuebox;
	}

	public void setFaceValuebox(List<String> faceValuebox) {
		this.faceValuebox = faceValuebox;
	}

	public ProdFaceValueDTO getProdFaceValueDTO() {
		return prodFaceValueDTO;
	}

	public void setProdFaceValueDTO(ProdFaceValueDTO prodFaceValueDTO) {
		this.prodFaceValueDTO = prodFaceValueDTO;
	}

	public List<String> getCardLayoutbox() {
		return cardLayoutbox;
	}

	public void setCardLayoutbox(List<String> cardLayoutbox) {
		this.cardLayoutbox = cardLayoutbox;
	}

	public List<String> getAcctypebox() {
		return acctypebox;
	}

	public void setAcctypebox(List<String> acctypebox) {
		this.acctypebox = acctypebox;
	}

	public List<String> getServiceAccountId() {
		return serviceAccountId;
	}

	public void setServiceAccountId(List<String> serviceAccountId) {
		this.serviceAccountId = serviceAccountId;
	}

	public ProdServiceDTO getProdAcctypeDTO() {
		return prodAcctypeDTO;
	}

	public void setProdAcctypeDTO(ProdServiceDTO prodAcctypeDTO) {
		this.prodAcctypeDTO = prodAcctypeDTO;
	}

	public ServiceQueryDTO getServiceQueryDTO() {
		return serviceQueryDTO;
	}

	public void setServiceQueryDTO(ServiceQueryDTO serviceQueryDTO) {
		this.serviceQueryDTO = serviceQueryDTO;
	}

	private BigDecimal temp = new BigDecimal("100");

	public List<Map<String, String>> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<Map<String, String>> entityList) {
		this.entityList = entityList;
	}

	private int totalRows = 0;
   
    
	public CardLayoutQueryDTO getCardLayouQueryDTO() {
		return cardLayouQueryDTO;
	}

	public void setCardLayouQueryDTO(CardLayoutQueryDTO cardLayouQueryDTO) {
		this.cardLayouQueryDTO = cardLayouQueryDTO;
	}

	public CardLayoutDTO getCardLayoutDTO() {
		return cardLayoutDTO;
	}

	public void setCardLayoutDTO(CardLayoutDTO cardLayoutDTO) {
		this.cardLayoutDTO = cardLayoutDTO;
	}

	public PackageQueryDTO getPackageQueryDTO() {
		return packageQueryDTO;
	}

	public void setPackageQueryDTO(PackageQueryDTO packageQueryDTO) {
		this.packageQueryDTO = packageQueryDTO;
	}

	public BigDecimal getTemp() {
		return temp;
	}

	public void setTemp(BigDecimal temp) {
		this.temp = temp;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public ProductQueryDTO getProductQueryDTO() {
		return productQueryDTO;
	}

	public void setProductQueryDTO(ProductQueryDTO productQueryDTO) {
		this.productQueryDTO = productQueryDTO;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public String[] getCardBinIds() {
		return cardBinIds;
	}

	public void setCardBinIds(String[] cardBinIds) {
		this.cardBinIds = cardBinIds;
	}

	public ProductCardBinDTO getProductCardBinDTO() {
		return productCardBinDTO;
	}

	public void setProductCardBinDTO(ProductCardBinDTO productCardBinDTO) {
		this.productCardBinDTO = productCardBinDTO;
	}

	public String init() {
		if (this.hasErrors()) {
			return "list";
		}
		return inquery();
	}

	/**
	 * 产品信息查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String inquery() {
		productQueryDTO.setDefaultEntityId(getUser().getEntityId());
		try {
			ListPageInit(null, productQueryDTO);
		} catch (Exception e) {
			addActionError("初始化错误");
			return INPUT;
		}
		
		if (productQueryDTO.isQueryAll()) {
			productQueryDTO.setQueryAll(false);
			productQueryDTO
					.setRowsDisplayed(Integer
							.parseInt((SystemInfo
									.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM))));
		}
		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.PRODUCT_SERVICE_INQUERY, productQueryDTO)
				.getDetailvo();
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		productQueryDTO.setEntityId(this.getUser().getEntityId());
		entityList = getIssuerLst(productQueryDTO);
		productQueryDTO.setEntityId("");
		if (this.hasErrors()) {
			return INPUT;
		}
		return "list";
	}

	/**
	 * 跳转到产品添加页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String reAdd(){
		productQueryDTO.setEntityId(this.getUser().getEntityId());
		entityList = getIssuerLst(productQueryDTO);
		validRuleList=getValidPeriodRuleList(productQueryDTO);
		HashMap<String, String> defaultvalidRuleMap=new HashMap<String, String>();
		defaultvalidRuleMap.put("ruleName", "---请选择---");
		defaultvalidRuleMap.put("ruleId", "");
		validRuleList.add(0, defaultvalidRuleMap);
		productQueryDTO.setEntityId("");
		productDTO.setEntityId(getUser().getEntityId());
		productDTO.setNoPinLimit("0");
	    isDisplay="none";
	    ifDisplay="none";
		return "readd";
	}

	/**
	 * 产品添加
	 */
	public String insertProduct() throws Exception {
		productDTO.setCreateUser(getUser().getUserId());
		productDTO.setCardFee((new BigDecimal(productDTO.getCardFee())
				.multiply(temp)).toString());
		productDTO.setAnnualFee((new BigDecimal(productDTO.getAnnualFee())
				.multiply(temp)).toString());
		productDTO.setReplaceFee((new BigDecimal(productDTO.getReplaceFee())
				.multiply(temp)).toString());
		productDTO.setMaxBalance((new BigDecimal(productDTO.getMaxBalance())
				.multiply(temp)).toString());
		productDTO.setNoPinLimit((new BigDecimal(productDTO.getNoPinLimit())
				.multiply(temp)).toString());
		productDTO.setDefaultEntityId(getUser().getEntityId());
		if(productDTO.getOnymousStat().equals("1")||"3".equals(productDTO.getOnymousStat())){
			
			productDTO.setStartDate(modifyDateString(new Date()));
			productDTO.setEndTime("29991231");
			productDTO.setValidityPeriod("0");
		}
		else{
			
			productDTO.setStartDate(modifyDateString(new Date()));
			productDTO.setEndTime(plusEndTime(productDTO.getValidityPeriod()));
		}
		
		productDTO=(ProductDTO)sendService(ConstCode.PRODUCT_SERVICE_INSERT,
				productDTO).getDetailvo();
		if (this.hasErrors()) {
			  if (productDTO.getOnymousStat().equals("2")) {
	        	   if("".equals(productDTO.getValidityPeriod())){
	        		
	        		   isDisplay="block";
	        	   }
					if("1".equals(productDTO.getChangeValidPeriod())){
					  if (productDTO.getValidPeriodRule().equals("")) {
						  ifDisplay="block";
					  }
					}
				}
			productQueryDTO.setEntityId(this.getUser().getEntityId());
			entityList = getIssuerLst(productQueryDTO);
			return INPUT;
		} else {
			addActionMessage("产品信息添加成功！");
		}
		load();
		return "edit";
	}

	public void validateInsertProduct() {
		try {
			
			
			// 判断截止日期大于起始日期
			if (!productDTO.getConsumerTimes().equals("")) {
				String rex = "^[0-9]*[1-9][0-9]*$";
				if (!productDTO.getConsumerTimes().matches(rex)) {
					addFieldError("productDTO.consumerTimes", "消费次数必须是正整数!");
				}
			}

			if (!productDTO.getRechargeTimes().equals("")) {
				String rex = "^[0-9]*[1-9][0-9]*$";
				if (!productDTO.getRechargeTimes().matches(rex)) {
					addFieldError("productDTO.rechargeTimes", "充值次数必须是正整数!");
				}
			}
           if (productDTO.getOnymousStat().equals("2")) {
        	   if("".equals(productDTO.getValidityPeriod())){
        		   addFieldError("productDTO.validityPeriod", "有效期月数不能为空!");
        		   isDisplay="block";
        	   }
				if("1".equals(productDTO.getChangeValidPeriod())){
				  if (productDTO.getValidPeriodRule().equals("")) {
					  ifDisplay="block";
					addFieldError("productDTO.validPeriodRule", "有效期规则必须选择!");
				  }
				}
			}
			if(this.hasFieldErrors()){
				productQueryDTO.setEntityId(getUser().getEntityId());
				entityList = getIssuerLst(productQueryDTO);
				validRuleList=getValidPeriodRuleList(productQueryDTO);
				HashMap<String, String> defaultvalidRuleMap=new HashMap<String, String>();
				defaultvalidRuleMap.put("ruleName", "---请选择---");
				defaultvalidRuleMap.put("ruleId", "");
				validRuleList.add(0, defaultvalidRuleMap);
			}	
			
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}
	
	private String modifyDateString(Date date){
		String resultString="";
		SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
		String resultArrayStrings=format.format(date).split(" ")[0];
		resultString=resultArrayStrings.substring(0,4)+resultArrayStrings.substring(5, 7)+resultArrayStrings.substring(8,10);
		return resultString;
	}

	private String plusEndTime(String validMonth){
		
		if(null==validMonth || "".equals(validMonth)){
			return "";
		}
		  Calendar now = Calendar.getInstance();
		  now.setTime(new Date());
		  now.add(Calendar.MONTH, Integer.valueOf(validMonth));
		  return modifyDateString(now.getTime());
	}
	private String plusEndTime(String startDate,String validMonth){
		if(null==validMonth || "".equals(validMonth)){
			return "";
		}
		String dateString=startDate.substring(0,4)+"-"+startDate.substring(4, 6)+"-"+startDate.substring(6, 8);
		Date startTime=DateUtil.string2date(dateString);
		Calendar nowCalendar=Calendar.getInstance();
		nowCalendar.setTime(startTime);
		nowCalendar.add(Calendar.MONTH, Integer.valueOf(validMonth));
		return modifyDateString(nowCalendar.getTime());
		
	}
	/**
	 * 产品修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		productDTO.setCardFee((new BigDecimal(productDTO.getCardFee())
				.multiply(temp)).toString());
		productDTO.setAnnualFee((new BigDecimal(productDTO.getAnnualFee())
				.multiply(temp)).toString());
		productDTO.setReplaceFee((new BigDecimal(productDTO.getReplaceFee())
				.multiply(temp)).toString());
		productDTO.setMaxBalance((new BigDecimal(productDTO.getMaxBalance())
				.multiply(temp)).toString());
		if(null!=productDTO.getNoPinLimit()&&!"".equals(productDTO.getNoPinLimit())){
		productDTO.setNoPinLimit((new BigDecimal(productDTO.getNoPinLimit())
		.multiply(temp)).toString());
		}
		productDTO.setModifyUser(getUser().getUserId());
		productDTO.setEndTime(plusEndTime(productDTO.getStartDate(),productDTO.getValidityPeriod()));
		sendService(ConstCode.PRODUCT_SERVICE_UPDATE, productDTO);
		if (this.hasErrors()) {
			entityList = getIssuerLst(productQueryDTO);
			return INPUT;
		} else {
			addActionMessage("产品信息修改成功！");
		}
		return inquery();
	}

	public void validateUpdate() throws Exception {
		
			entityList = getIssuerLst(productQueryDTO);
			if (!productDTO.getConsumerTimes().equals("")) {
				String rex = "^[0-9]*[1-9][0-9]*$";
				if (!productDTO.getConsumerTimes().matches(rex)) {
					addFieldError("productDTO.consumerTimes", "消费次数必须是正整数!");
				}
			}

			if (!productDTO.getRechargeTimes().equals("")) {
				String rex = "^[0-9]*[1-9][0-9]*$";
				if (!productDTO.getRechargeTimes().matches(rex)) {
					addFieldError("productDTO.rechargeTimes", "充值次数必须是正整数!");
				}
			}
			
			  if (null!=productDTO.getChangeValidPeriod()&&"1".equals(productDTO.getChangeValidPeriod())) {
				
				if (("").equals(productDTO.getValidPeriodRule())) {
					addFieldError("productDTO.validPeriodRule", "有效期规则必须选择!");
				}
			
			}
			if(hasErrors()){
				productQueryDTO.setEntityId(getUser().getEntityId());
				//entityList = getIssuerLst(productQueryDTO);
				validRuleList=getValidPeriodRuleList(productQueryDTO);
				HashMap<String, String> defaultvalidRuleMap=new HashMap<String, String>();
				defaultvalidRuleMap.put("ruleName", "---请选择---");
				defaultvalidRuleMap.put("ruleId", "");
				validRuleList.add(0, defaultvalidRuleMap);
				load();
			}
		
	}

	/**
	 * 获得所属发行机构和子发行机构
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getIssuerLst(
			ProductQueryDTO productQueryDTO) {

		List<ProductQueryDTO> sDTO = (List<ProductQueryDTO>) sendService(
				ConstCode.ISSUER_PRODUCT_SERVICE_INQUERY, productQueryDTO)
				.getDetailvo();
		List<Map<String, String>> issuerList = new ArrayList<Map<String, String>>();
		for (ProductQueryDTO serviceDto : sDTO) {
			Map<String, String> issuer = new HashMap<String, String>();
			issuer.put("entityId", serviceDto.getEntityId());
			issuer.put("entityName", serviceDto.getEntityName());
			issuerList.add(issuer);
		}
		return issuerList;
	}
	
	/**
	 * 获得有效期规则
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getValidPeriodRuleList(ProductQueryDTO productQueryDTO){
		List<ValidPeriodRuleDspDTO> validPeriodRuleDspDTOs=(List<ValidPeriodRuleDspDTO>)sendService(ConstCode.VALID_PERIOD_RULE_LIST, productQueryDTO).getDetailvo();
		List<Map<String, String>> validPeriodRuleList = new ArrayList<Map<String, String>>();
		Map<String, String> a = new HashMap<String, String>();
		for (ValidPeriodRuleDspDTO dspDTO : validPeriodRuleDspDTOs) {
			Map<String, String> dsp = new HashMap<String, String>();
			String result=a.put(dspDTO.getRuleName(), "1");
			if(null!=result){
				continue;
			}
			dsp.put("ruleId", dspDTO.getRuleDspId());
			dsp.put("ruleName", dspDTO.getRuleName());
			validPeriodRuleList.add(dsp);
		}
		return validPeriodRuleList;
	}
	/**
	 * 产品账户明细添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String accountInquery() throws Exception {
		if (serviceQueryDTO == null) {
			serviceQueryDTO = new ServiceQueryDTO();
		}
		ListPageInit(null, serviceQueryDTO);
		String id = getRequest().getParameter("id");
		if (!"".equals(id) && id != null) {
			serviceQueryDTO.setProductId(id);
		}
		serviceQueryDTO.setExpiryDate(DateUtil.date2String(DateUtil.getCurrentDate()));
		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.ACCTYPE_SERVICE_ACCTYPEINQUERY, serviceQueryDTO)
				.getDetailvo();

		// 查询添加的是否为第一个账户，如果是那么defaultFlag=1；
		String count = (String) sendService(
				ConstCode.PRODUCT_ACCTYPE_SERVICE_ACCTYPEINQUERY,
				serviceQueryDTO).getDetailvo();
		if (count.equals("0")) {
			prodAcctypeDTO.setDefaultFlag("1");
		} else {
			prodAcctypeDTO.setDefaultFlag("0");
		}
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		productQueryDTO.setEntityId(this.getUser().getEntityId());
		entityList = getIssuerLst(productQueryDTO);
		productQueryDTO.setEntityId("");

		if (this.hasErrors()) {
			return INPUT;
		}
		return "accountAdd";
	}

	public String serviceQuery() throws Exception {
		if (!serviceQueryDTO.getServiceId().equals("")
				&& serviceQueryDTO.getServiceId() != null) {
			logger.info(serviceQueryDTO.getServiceId().replace(",", "")
					.trim());
			serviceQueryDTO.setServiceId(serviceQueryDTO.getServiceId()
					.replace(",", "").trim());
		}
		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.ACCTYPE_SERVICE_ACCTYPEINQUERY, serviceQueryDTO)
				.getDetailvo();
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		productQueryDTO.setEntityId(this.getUser().getEntityId());
		entityList = getIssuerLst(productQueryDTO);
		productQueryDTO.setEntityId("");
		serviceQueryDTO.setServiceId("");

		if (this.hasErrors()) {
			return INPUT;
		}
		return "accountAdd";

	}

	/**
	 * 产品中服务信息添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String accountAdd() throws Exception {
		try {
			String id = serviceAccountId.get(0);
			logger.info(id);
			productDTO.setProductId(serviceQueryDTO.getProductId());
			productDTO.setModifyUser(getUser().getUserId());
			productDTO.setMaxBalance((new BigDecimal(productDTO.getMaxBalance())
			.multiply(temp)).toString());
			sendService(ConstCode.PRODUCT_SERVICE_UPDATE, productDTO);// 更新产品信息，主要是修改时间和修改人
			prodAcctypeDTO.setServiceId(id);
			prodAcctypeDTO.setProductId(serviceQueryDTO.getProductId());
			/**
			 * 类型转换从页面从过来的为String
			 */
			prodAcctypeDTO.setMaxDayTxnAmt(new BigDecimal(prodAcctypeDTO
					.getMaxDayTxnAmt()).multiply(temp).toString());
			prodAcctypeDTO.setMaxTxnAmt(new BigDecimal(prodAcctypeDTO
					.getMaxTxnAmt()).multiply(temp).toString());
			prodAcctypeDTO.setServiceFee(new BigDecimal(prodAcctypeDTO
					.getServiceFee()).toString());
			prodAcctypeDTO.setWebMaxDayTxnAmt(new BigDecimal(prodAcctypeDTO
					.getWebMaxDayTxnAmt()).multiply(temp).toString());
			prodAcctypeDTO.setWebMaxTxnAmt(new BigDecimal(prodAcctypeDTO
					.getWebMaxTxnAmt()).multiply(temp).toString());
			sendService(ConstCode.PRODACCTYPE_SERVICE_ADD, prodAcctypeDTO);// 更新产品账户关联表
			if (this.hasErrors()) {
				flag = false;
				return accountInquery();
			} else {
				addActionMessage("服务信息添加成功！");
			}
			return "edit";
		} catch (BizServiceException e) {

			flag = false;
			throw e;

		}
	}

	public void validateAccountAdd() throws Exception {
		accountInquery();
		entityList = getIssuerLst(productQueryDTO);
		if (this.hasFieldErrors()) {
			flag = false;
		} else {
			flag = true;
		}
	}

	public String load() throws Exception {
		if(null != choose && choose.size()>0){
			String productDefineIssuer=choose.get(0).split(",")[1];
			
			if(!productDefineIssuer.equals(getUser().getEntityId())){
				init();
				addActionError("下机机构不能操作上级发卡机构定义的代发产品!");
				return INPUT;
			}
		}
		if (productDTO.getProductId() == null) {
			String id = choose.get(0).split(",")[0];
			productDTO = new ProductDTO();
			productDTO.setProductId(id);
		}
		productDTO = (ProductDTO) sendService(ConstCode.PRODUCT_SERVICE_VIEW,
				productDTO).getDetailvo();

		if (productDTO.getConsumerTimes() != null) {
			productDTO.setConsumerTimeFlag("0");
			productDTO.setConsumerTimes(productDTO.getConsumerTimes());
		} else {
			productDTO.setConsumerTimeFlag("1");
			productDTO.setConsumerTimes(productDTO.getConsumerTimes());
		}
		if (productDTO.getValidPeriodRule() != null) {
			productDTO.setChangeValidPeriod("1");
			productDTO.setValidPeriodRule(productDTO.getValidPeriodRule());
		} else {
			productDTO.setChangeValidPeriod("0");
			productDTO.setValidPeriodRule(productDTO.getValidPeriodRule());
		}
		if (productDTO.getRechargeTimes() != null) {
			productDTO.setRechargeTimeFlag("0");
			productDTO.setRechargeTimes(productDTO.getRechargeTimes());
		} else {
			productDTO.setRechargeTimeFlag("1");
			productDTO.setRechargeTimes(productDTO.getRechargeTimes());
		}

		productDTO.setCardFee((new BigDecimal(productDTO.getCardFee())).divide(
				temp).toString());
		productDTO.setAnnualFee((new BigDecimal(productDTO.getAnnualFee()))
				.divide(temp).toString());
		productDTO.setReplaceFee((new BigDecimal(productDTO.getReplaceFee()))
				.divide(temp).toString());
		productDTO.setMaxBalance((new BigDecimal(productDTO.getMaxBalance()))
				.divide(temp).toString());
		if(null!=productDTO.getNoPinLimit()&&!"".equals(productDTO.getNoPinLimit())){
		productDTO.setNoPinLimit((new BigDecimal(productDTO.getNoPinLimit()))
				.divide(temp)
				+ "");
		}
//		productDTO.setRedemptiveServiceRate((new BigDecimal(productDTO.getRedemptiveServiceRate()))
//				.divide(temp).toString());
		productQueryDTO.setEntityId(this.getUser().getEntityId());
		entityList = getIssuerLst(productQueryDTO);
		validRuleList=getValidPeriodRuleList(productQueryDTO);
		HashMap<String, String> defaultvalidRuleMap=new HashMap<String, String>();
		defaultvalidRuleMap.put("ruleName", "---请选择---");
		defaultvalidRuleMap.put("ruleId", "");
		validRuleList.add(0, defaultvalidRuleMap);
		datachangeType();
//		if (this.hasErrors()) {
//			return "list";
//		}
		return "edit";
	}

	public String accountEdit() throws Exception {

		prodAcctypeDTO = new ProdServiceDTO();
		prodAcctypeDTO.setServiceId(acctypeId);
		prodAcctypeDTO.setProductId(pid);

		prodAcctypeDTO = (ProdServiceDTO) sendService(
				ConstCode.PRODACCTYPE_SERVICE_VIEW, prodAcctypeDTO)
				.getDetailvo();
		prodAcctypeDTO.setWebMaxDayTxnAmt(new BigDecimal(prodAcctypeDTO
				.getWebMaxDayTxnAmt()).divide(temp).toString());
		prodAcctypeDTO.setWebMaxTxnAmt(new BigDecimal(prodAcctypeDTO
				.getWebMaxTxnAmt()).divide(temp).toString());
		prodAcctypeDTO.setMaxDayTxnAmt(new BigDecimal(prodAcctypeDTO
				.getMaxDayTxnAmt()).divide(temp).toString());
		prodAcctypeDTO.setMaxTxnAmt(new BigDecimal(prodAcctypeDTO
				.getMaxTxnAmt()).divide(temp).toString());
		prodAcctypeDTO.setServiceFee(prodAcctypeDTO.getServiceFee().toString());
		prodAcctypeDTO.setRelId(prodAcctypeDTO.getRelId());
		prodAcctypeDTO.setDefaultFlag(prodAcctypeDTO.getDefaultFlag());

		// if(this.getUser().getRoleDatePurviewDTO()!=null &&
		// this.getUser().getRoleDatePurviewDTO().getModifyRate()==1){
		// modifyRate=true;
		// }

		if (this.hasErrors()) {
			return "acctypeEdit";
		}
		// if(getRequest().getParameter("look")!=null){
		// return "viewAcctype";
		// }
		return "acctypeEdit";
	}

	/**
	 * 服务信息修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String prodAccounteEdit() throws Exception {
		// 更新产品信息
//		productDTO.setProductId(prodAcctypeDTO.getProductId());
//		productDTO.setModifyUser(getUser().getUserId());
//
//		sendService(ConstCode.PRODUCT_SERVICE_UPDATE, productDTO);

		// 更新产品账户关联信息
		prodAcctypeDTO.setLoginUserId(getUser().getUserId());
		prodAcctypeDTO.setDefaultFlag(prodAcctypeDTO.getDefaultFlag());
		prodAcctypeDTO.setServiceId(prodAcctypeDTO.getServiceId());
		prodAcctypeDTO.setServiceName(prodAcctypeDTO.getServiceName());
		prodAcctypeDTO.setProductId(prodAcctypeDTO.getProductId());
		prodAcctypeDTO.setRelId(prodAcctypeDTO.getRelId());
		prodAcctypeDTO.setMaxDayTxnAmt(new BigDecimal(prodAcctypeDTO
				.getMaxDayTxnAmt()).multiply(temp).toString());
		prodAcctypeDTO.setMaxTxnAmt(new BigDecimal(prodAcctypeDTO
				.getMaxTxnAmt()).multiply(temp).toString());
		prodAcctypeDTO.setServiceFee(new BigDecimal(prodAcctypeDTO
				.getServiceFee()).toString());
		prodAcctypeDTO.setWebMaxDayTxnAmt(new BigDecimal(prodAcctypeDTO
				.getWebMaxDayTxnAmt()).multiply(temp).toString());
		prodAcctypeDTO.setWebMaxTxnAmt(new BigDecimal(prodAcctypeDTO
				.getWebMaxTxnAmt()).multiply(temp).toString());
		sendService(ConstCode.PRODACCTYPE_SERVICE_EDIT, prodAcctypeDTO);
		if (this.hasActionErrors()) {
			flag = false;
			prodAcctypeDTO.setServiceName(prodAcctypeDTO.getServiceName());
			prodAcctypeDTO.setDefaultFlag(prodAcctypeDTO.getDefaultFlag());
			prodAcctypeDTO.setWebMaxDayTxnAmt(new BigDecimal(prodAcctypeDTO
					.getWebMaxDayTxnAmt()).divide(temp).toString());
			prodAcctypeDTO.setWebMaxTxnAmt(new BigDecimal(prodAcctypeDTO
					.getWebMaxTxnAmt()).divide(temp).toString());
			prodAcctypeDTO.setMaxDayTxnAmt(new BigDecimal(prodAcctypeDTO
					.getMaxDayTxnAmt()).divide(temp).toString());
			prodAcctypeDTO.setMaxTxnAmt(new BigDecimal(prodAcctypeDTO
					.getMaxTxnAmt()).divide(temp).toString());
			return "input";
		} else {
			addActionMessage("服务信息修改成功！");
		}
		return "edit";
	}

	public void validateProdAccounteEdit() {
		if (this.hasFieldErrors()) {
			flag = false;
		} else {
			flag = true;
		}
	}

	/**
	 * 服务信息删除
	 * 
	 * @return
	 * @throws Exception
	 */
	public String accountDel() throws Exception {
		List<ServiceDTO> acctypeDTOs = new ArrayList<ServiceDTO>();

		for (String id : acctypebox) {
			ServiceDTO acctypeDTO = new ServiceDTO();
			acctypeDTO.setServiceId(id);
			acctypeDTOs.add(acctypeDTO);
		}
		productDTO.setServices(acctypeDTOs);
		sendService(ConstCode.PRODACCTYPE_SERVICE_DEL, productDTO);
		load();
		datachangeType();
		if (this.hasErrors()) {
			return load();
		} else {
			addActionMessage("服务信息删除成功！");
		}
		return "edit";
	}

	/**
	 * 卡面信息查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String cardLayoutList() throws Exception {
		if (cardLayouQueryDTO == null) {
			cardLayouQueryDTO = new CardLayoutQueryDTO();
		}
		ListPageInit(null, cardLayouQueryDTO);
		String id = getRequest().getParameter("id");
		if (id != null && !"".equals(id)) {
			cardLayouQueryDTO.setProductId(id);
		}
		cardLayouQueryDTO.setValidFlag("1");
		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.CARDLAYOUT_SERVICE_SELETE, cardLayouQueryDTO)
				.getDetailvo();
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}

		if (this.hasErrors()) {
			return "cardLayoutList";
		}
		return "cardLayoutList";
	}

	/**
	 * 图片显示
	 * 
	 * @throws Exception
	 */
	public void getCardLayoutImg() throws Exception {
		String id = getRequest().getParameter("id");
		cardLayoutDTO = new CardLayoutDTO();
		cardLayoutDTO.setCardLayoutId(id);
		cardLayoutDTO = (CardLayoutDTO) sendService(
				ConstCode.CARDLAYOUT_SERVICE_VIEW, cardLayoutDTO).getDetailvo();

		getResponse().setContentType("image/jpeg");
		byte[] data = cardLayoutDTO.getPicture();
		if (data == null || data.length == 0) {
			getRequest().getRequestDispatcher("/images/icon/cardLayout.jpg")
					.forward(getRequest(), getResponse());
		} else {
			ServletOutputStream op = getResponse().getOutputStream();
			op.write(data, 0, data.length);
			op.close();
			op = null;
			getResponse().flushBuffer();
		}
	}

	/**
	 * 卡面信息添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String cardLayoutAdd() throws Exception {
		inintProductParamerter();
		// productDTO.setCardLayoutId(productDTO.getCardLayoutId());
		productDTO.setCreateUser(getUser().getUserId());
		sendService(ConstCode.PRODUCT_SERVICE_CARDADD, productDTO);
		load();
		if (this.hasErrors()) {
			return load();
		} else {
			addActionMessage("卡面信息添加成功！");
		}
		return "edit";
	}

	/**
	 * 卡面信息删除
	 * 
	 * @return
	 * @throws Exception
	 */
	public String productCardLayoutDel() throws Exception {
		inintProductParamerter();
		List<CardLayoutDTO> cardLayoutDTOs = new ArrayList<CardLayoutDTO>();
		for (String id : cardLayoutbox) {
			cardLayoutDTO = new CardLayoutDTO();
			cardLayoutDTO.setRelId(id);
			cardLayoutDTOs.add(cardLayoutDTO);
		}
		productDTO.setCardLayoutDTOs(cardLayoutDTOs);
		// 删除卡面
		sendService(ConstCode.PRODUCT_SERVICE_CARDDEL, productDTO);
		load();

		if (this.hasErrors()) {
			return load();
		} else {
			addActionMessage("卡面信息删除成功！");
		}
		return "edit";
	}

	/**
	 * 跳转面额信息页面
	 * 
	 * @return
	 */
	public String faceValueList() {
		return "faceValueList";
	}

	/**
	 * 面额信息添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String faceValueAdd() throws Exception {
		inintProductParamerter();
		// 添加面额
		String[] faceValue = getRequest().getParameter("faceValue").split(",");
		prodFaceValueDTO = new ProdFaceValueDTO();
		prodFaceValueDTO.setFaceValueType(faceValue[0]);
		prodFaceValueDTO.setFaceValue(new BigDecimal(faceValue[1]).multiply(
				temp).toString());
		prodFaceValueDTO.setProductId(productDTO.getProductId());
		prodFaceValueDTO.setCreateUser(getUser().getUserId());
		prodFaceValueDTO.setModifyUser(getUser().getUserId());
		sendService(ConstCode.PRODFACEVALUE_SERVICE_INSERT, prodFaceValueDTO);
		load();
		if (this.hasErrors()) {
			return load();
		} else {
			addActionMessage("面额信息添加成功！");
		}
		return "edit";
	}

	/**
	 * 转换下显示的格式
	 */
	public void datachangeType() {
		if (productDTO.getProdFaceValueDTO() != null) {
			for (ProdFaceValueDTO faceValue : productDTO.getProdFaceValueDTO()) {
				faceValue.setFaceValue(new BigDecimal(faceValue.getFaceValue())
						.divide(temp).toString());
			}
		}
	}

	/**
	 * 面额信息删除
	 * 
	 * @return
	 * @throws Exception
	 */
	public String prodFaceValueDel() throws Exception {
		inintProductParamerter();
		List<ProdFaceValueDTO> prodFaceValueDTOs = new ArrayList<ProdFaceValueDTO>();
		for (String id : faceValuebox) {
			prodFaceValueDTO = new ProdFaceValueDTO();
			prodFaceValueDTO.setFaceValueId(id.split(",")[0]);
			prodFaceValueDTOs.add(prodFaceValueDTO);
		}
		prodFaceValueDTO.setProdFaceValueDTO(prodFaceValueDTOs);
		sendService(ConstCode.PRODFACEVLAUE_SERVICE_DELETE, prodFaceValueDTO);
		load();
		if (this.hasErrors()) {
			return load();
		} else {
			addActionMessage("面额信息删除成功！");
		}
		return "edit";
	}

	/**
	 * 包装信息查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String productPackageList() throws Exception {
		ListPageInit(null, packageQueryDTO);
		String id = getRequest().getParameter("id");
		if (id != null && !"".equals(id)) {
			packageQueryDTO.setProductId(id);
		}
		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.PACKAGE_SERVICE_INQUERY, packageQueryDTO)
				.getDetailvo();
		packageQueryDTO.setProductId(packageQueryDTO.getProductId());
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		if (this.hasErrors()) {
			return "packageList";
		}
		return "packageList";
	}

	/**
	 * 包装信息添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String packageAdd() throws Exception {
		inintProductParamerter();
		String packageId = getRequest().getParameter("packageId");
		productDTO.setPackageId(packageId);
		productDTO.setCreateUser(getUser().getUserId());
		sendService(ConstCode.PRODUCT_SERVICE_PACKAGEADD, productDTO);
		load();
		if (this.hasErrors()) {
			return load();
		} else {
			addActionMessage("包装信息添加成功！");
		}
		return "edit";
	}

	/**
	 * 包装信息删除
	 * 
	 * @return
	 * @throws Exception
	 */
	public String packageDel() throws Exception {
		inintProductParamerter();
		List<PackageDTO> packageDTOs = new ArrayList<PackageDTO>();
		for (String id : packagebox) {
			PackageDTO packageDTO = new PackageDTO();
			packageDTO.setRelId(id);
			packageDTOs.add(packageDTO);
		}
		productDTO.setPackages(packageDTOs);
		sendService(ConstCode.PRODUCT_SERVICE_PACKAGEDEL, productDTO);
		load();
		if (this.hasErrors()) {
			return "edit";
		} else {
			addActionMessage("包装信息删除成功！");
		}
		return "edit";
	}

	public String productPackageList1() throws Exception {
		ListPageInit(null, packageQueryDTO);
		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.PACKAGE_SERVICE_INQUERY, packageQueryDTO)
				.getDetailvo();
		if (this.hasErrors()) {
			return "packageList";
		}
		return "packageList";
	}

	/**
	 * 产品信息删除
	 * 
	 * @return
	 * @throws Exception
	 */

	public String productDelete() throws Exception {

		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		for (String id : choose) {
			String defineIssuerIdString=id.split(",")[1];
			if(!defineIssuerIdString.equals(getUser().getEntityId())){
				init();
				addActionError("下机机构不能操作上级发卡机构定义的代发产品!");
				return INPUT;
			}
			productDTO = new ProductDTO();
			productDTO.setProductId(id.split(",")[0]);
			productDTOs.add(productDTO);
		}
		productDTO.setProductDTOs(productDTOs);
		productDTO.setModifyUser(this.getUser().getUserId());
		sendService(ConstCode.PRODUCT_SERVICE_DELETE, productDTO);
		if (this.hasErrors()) {
			return inquery();
		} else {
			addActionMessage("产品信息删除成功！");
		}
		return inquery();
	}

	/**
	 * 产品信息调用
	 * 
	 * @return
	 * @throws Exception
	 */
	public String view() throws Exception {
		productDTO = (ProductDTO) sendService(ConstCode.PRODUCT_SERVICE_VIEW,
				productDTO).getDetailvo();
		productDTO.setCardFee((new BigDecimal(productDTO.getCardFee()))
				.divide(temp)
				+ "");
		productDTO.setAnnualFee((new BigDecimal(productDTO.getAnnualFee()))
				.divide(temp)
				+ "");
		productDTO.setReplaceFee((new BigDecimal(productDTO.getReplaceFee()))
				.divide(temp)
				+ "");
		productDTO.setMaxBalance((new BigDecimal(productDTO.getMaxBalance()))
				.divide(temp)
				+ "");
		if(null!=productDTO.getNoPinLimit()&&!"".equals(productDTO.getNoPinLimit())){
		  productDTO.setNoPinLimit((new BigDecimal(productDTO.getNoPinLimit()))
				.divide(temp)
				+ "");
		}
		if (productDTO.getConsumerTimes() != null) {
			productDTO.setConsumerTimeFlag("0");
			productDTO.setConsumerTimes(productDTO.getConsumerTimes());
		} else {
			productDTO.setConsumerTimeFlag("1");
			productDTO.setConsumerTimes(productDTO.getConsumerTimes());
		}
		if (productDTO.getRechargeTimes() != null) {
			productDTO.setRechargeTimeFlag("0");
			productDTO.setRechargeTimes(productDTO.getRechargeTimes());
		} else {
			productDTO.setRechargeTimeFlag("1");
			productDTO.setRechargeTimes(productDTO.getRechargeTimes());
		}
		if (productDTO.getValidPeriodRule() != null) {
			productDTO.setChangeValidPeriod("1");
			productDTO.setValidPeriodRule(productDTO.getValidPeriodRule());
		} else {
			productDTO.setChangeValidPeriod("0");
			productDTO.setValidPeriodRule(productDTO.getValidPeriodRule());
		}
		productQueryDTO.setEntityId(getUser().getEntityId());
		List<ValidPeriodRuleDspDTO> validPeriodRuleDspDTOs=(List<ValidPeriodRuleDspDTO>)sendService(ConstCode.VALID_PERIOD_RULE_LIST, productQueryDTO).getDetailvo();
		if(productDTO.getChangeValidPeriod()!=null){
		if(productDTO.getChangeValidPeriod().equals("1")){
		for (ValidPeriodRuleDspDTO dspDTO : validPeriodRuleDspDTOs) {
			
			if((dspDTO.getRuleDspId()).equals(productDTO.getValidPeriodRule())){
				productDTO.setValidPeriodRule(dspDTO.getRuleName());
				
				
			}
			
		}
		}
		}
		entityList = getIssuerLst(productQueryDTO);
		
		datachangeType();
		if (this.hasErrors()) {
			return "list";
		}
		return "view";
	}

	public void inintProductParamerter() {
		productDTO.setCardFee((new BigDecimal(productDTO.getCardFee())
				.multiply(temp)).toString());
		productDTO.setAnnualFee((new BigDecimal(productDTO.getAnnualFee())
				.multiply(temp)).toString());
		productDTO.setReplaceFee((new BigDecimal(productDTO.getReplaceFee())
				.multiply(temp)).toString());
		productDTO.setMaxBalance((new BigDecimal(productDTO.getMaxBalance())
				.multiply(temp)).toString());
		productDTO.setNoPinLimit((new BigDecimal(productDTO.getNoPinLimit())
				.multiply(temp)).toString());
		productDTO.setModifyUser(getUser().getUserId());
		//productDTO.setStartDate(DateUtil.date2String(productDTO
		//		.getStartDateDate()));
		//if (productDTO.getEndTimeTime() != null) {
		//	productDTO.setEndTime(DateUtil.date2String(productDTO
		//			.getEndTimeTime()));
		//}
		productDTO.setEndTime(plusEndTime(productDTO.getStartDate(), productDTO.getValidityPeriod()));
		sendService(ConstCode.PRODUCT_SERVICE_UPDATE, productDTO);

	}

	public String choiceCardBin() {
		try {
			// ListPageInit("cardBins", productQueryDTO);
			// pageDataDTO = (PageDataDTO) this.sendService(
			// ConstCode.PRODUCT_SERIVCE_CHOICE_CARD_BIN, productQueryDTO)
			// .getDetailvo();
			// if (null != pageDataDTO) {
			// cardBins = (List<Map<String, Object>>) pageDataDTO.getData();
			// if (null != cardBins && cardBins.size() > 0) {
			// cardBin_totalRows = cardBins.size();
			// }
			// }
			ActionContext cxt = ActionContext.getContext();
			HttpServletRequest request =(HttpServletRequest) cxt.get(ServletActionContext.HTTP_REQUEST);
			request.setAttribute("productType", productDTO.getOnymousStat());
			productDTO = (ProductDTO) this.sendService(
					ConstCode.PRODUCT_SERIVCE_CHOICE_CARD_BIN, productDTO)
					.getDetailvo();
			if (null != productDTO) {
				cardBinDTOs=productDTO.getProductCardBinDTOs();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "choiceCardBin";
	}

	public String insertCardBin() throws Exception {
		try {
			productCardBinDTO.setProductId(productDTO.getProductId());

			this.sendService(ConstCode.PRODUCT_SERIVCE_CHOICE_CARD_INSERT,
					productCardBinDTO);
			if (!hasErrors()) {
				this.addActionMessage("添加卡BIN成功！");
			}
			
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return load();
	}

	public String modifyCardBinDefault() throws Exception {
		try {
			String[] cardBinKey = cardBinIds[0].split(",");
			productCardBinDTO.setProductId(cardBinKey[0]);
			productCardBinDTO.setCardBin(cardBinKey[1]);
			this.sendService(
					ConstCode.PRODUCT_SERIVCE_CHOICE_CARD_MODIFY_DEFAULT,
					productCardBinDTO);
			if (!hasErrors()) {
				this.addActionMessage("修改卡BIN状态成功！");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return load();
	}

	public String deleteCardBin() throws Exception {
		try {
			String[] cardBinKey = cardBinIds[0].split(",");
			productCardBinDTO.setProductId(cardBinKey[0]);
			productCardBinDTO.setCardBin(cardBinKey[1]);
			this.sendService(ConstCode.PRODUCT_SERIVCE_CHOICE_CARD_DELETE,
					productCardBinDTO);

			if (!hasErrors()) {
				this.addActionMessage("删除卡BIN成功！");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return load();
	}

	public List<Map<String, Object>> getCardBins() {
		return cardBins;
	}

	public void setCardBins(List<Map<String, Object>> cardBins) {
		this.cardBins = cardBins;
	}

	public int getCardBin_totalRows() {
		return cardBin_totalRows;
	}

	public void setCardBin_totalRows(int cardBinTotalRows) {
		cardBin_totalRows = cardBinTotalRows;
	}

	public List<ProductCardBinDTO> getCardBinDTOs() {
		return cardBinDTOs;
	}

	public void setCardBinDTOs(List<ProductCardBinDTO> cardBinDTOs) {
		this.cardBinDTOs = cardBinDTOs;
	}

	public String getRechargeTimesId() {
		return rechargeTimesId;
	}

	public void setRechargeTimesId(String rechargeTimesId) {
		this.rechargeTimesId = rechargeTimesId;
	}

	public String getConsumerTimesId() {
		return consumerTimesId;
	}

	public void setConsumerTimesId(String consumerTimesId) {
		this.consumerTimesId = consumerTimesId;
	}

	
}
