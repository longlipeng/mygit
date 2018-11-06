package com.allinfinance.batch.importOrder.service;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.allinfinance.framework.dto.ImportServiceDTO;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.ibatis.attach.dao.AttachDAO;
import com.allinfinance.shangqi.gateway.dao.ApplyAndBindDao;
import com.allinfinance.shangqi.gateway.dto.ApplyAndBindCardDTO;
import com.allinfinance.shangqi.gateway.dto.BatchCardHolderMessageDTO;
import com.allinfinance.univer.entity.dto.ContactDTO;
import com.allinfinance.univer.entity.dto.InvoiceAddressDTO;
import com.allinfinance.univer.entity.stock.dto.EntityStockDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.allinfinance.univer.seller.cardholder.dto.AttachInfoDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderQueryDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.OrderReadyDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderPaymentDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerAcctypeContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerProductContractDTO;
import com.allinfinance.univer.stockcard.dto.StockCardQueryDTO;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CardholderDAO;
import com.huateng.framework.ibatis.dao.CustomerDAO;
import com.huateng.framework.ibatis.dao.DeliveryContactDAO;
import com.huateng.framework.ibatis.dao.EntityDeliveryDAO;
import com.huateng.framework.ibatis.dao.SellContractDAO;
import com.huateng.framework.ibatis.dao.SellOrderCardListDAO;
import com.huateng.framework.ibatis.dao.SellOrderDAO;
import com.huateng.framework.ibatis.dao.SellOrderListDAO;
import com.huateng.framework.ibatis.dao.SellProdContractDAO;
import com.huateng.framework.ibatis.model.Cardholder;
import com.huateng.framework.ibatis.model.CardholderExample;
import com.huateng.framework.ibatis.model.Customer;
import com.huateng.framework.ibatis.model.CustomerKey;
import com.huateng.framework.ibatis.model.DeliveryContact;
import com.huateng.framework.ibatis.model.DeliveryContactExample;
import com.huateng.framework.ibatis.model.EntityDelivery;
import com.huateng.framework.ibatis.model.EntityDeliveryExample;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.InvoiceCompany;
import com.huateng.framework.ibatis.model.SellContract;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.ibatis.model.SellOrderCardListExample;
import com.huateng.framework.ibatis.model.SellOrderList;
import com.huateng.framework.ibatis.model.SellOrderListExample;
import com.huateng.framework.ibatis.model.SellProdContractExample;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ReflectionUtil;
import com.huateng.univer.batch.BatchService;
import com.huateng.univer.entitybaseinfo.contact.biz.service.ContactService;
import com.huateng.univer.entitybaseinfo.invoiceaddress.biz.service.InvoiceAddressService;
import com.huateng.univer.entitybaseinfo.invoicecompany.biz.service.InvoiceCompanyService;
import com.huateng.univer.issuer.product.service.ProductService;
import com.huateng.univer.order.business.action.OrderPaymentActionImpl;
import com.huateng.univer.order.business.action.OrderSendConfimActionInterface;
import com.huateng.univer.order.business.bo.OrderBaseQueryBO;
import com.huateng.univer.order.business.service.OrderReadyService;
import com.huateng.univer.order.business.service.OrderService;
import com.huateng.univer.order.business.service.SellOrderPaymentService;
import com.huateng.univer.order.business.service.SubmitOrderService;
import com.huateng.univer.seller.cardholder.biz.service.CardholderService;
import com.huateng.univer.seller.customer.biz.service.CustomerService;
import com.huateng.univer.seller.sellercontract.biz.service.SellerContractService;
import com.huateng.univer.seller.sellercontract.biz.service.SellerProductContractService;
import com.huateng.univer.stockcard.biz.service.StockCardService;
/**
 * 
 * @author wanglei
 *该
 */
public class ImportServiceImpl implements ImportService{
	
	private CustomerService customerService;
	private CardholderService cardholderService;
	private CustomerDAO customerDAO;
	private CardholderDAO cardholderDAO;
	private EntityDeliveryDAO deliveryDAO;
	private DeliveryContactDAO deliveryContractDAO;
	private ApplyAndBindDao applyAndBindCardDao;
	private InvoiceCompanyService invoiceCompanyService;
	private InvoiceAddressService invoiceAddressService;
	private ContactService contactService;
	private SellerContractService sellerContractService;
	private SellerProductContractService sellerProductContractService;
	private ProductService productService;
	private SellContractDAO sellerContractDAO;
	private OrderService orderService;
	private SubmitOrderService submitOrderService;
	private OrderBaseQueryBO orderBaseQueryBO;
	private OrderReadyService orderReadyService;
	private StockCardService stockCardService;
	private OrderSendConfimActionInterface orderSendConfirmActionImpl;
	private BatchService batchService;
	private SellOrderPaymentService sellOrderPaymentService;
	private SellOrderDAO sellOrderDAO;
	private OrderPaymentActionImpl orderPaymentActionImpl;
	private SellProdContractDAO sellProdContractDAO;
	private SellOrderCardListDAO sellOrderCardListDAO;
	private PageQueryDAO pageQueryDAO;
	private SellOrderListDAO sellOrderListDAO;
	private AttachDAO attachDAO;
	
	
	
	public AttachDAO getAttachDAO() {
		return attachDAO;
	}



	public void setAttachDAO(AttachDAO attachDAO) {
		this.attachDAO = attachDAO;
	}



	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}



	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}



	public SellOrderCardListDAO getSellOrderCardListDAO() {
		return sellOrderCardListDAO;
	}



	public void setSellOrderCardListDAO(SellOrderCardListDAO sellOrderCardListDAO) {
		this.sellOrderCardListDAO = sellOrderCardListDAO;
	}



	public ApplyAndBindDao getApplyAndBindCardDao() {
		return applyAndBindCardDao;
	}



	public void setApplyAndBindCardDao(ApplyAndBindDao applyAndBindCardDao) {
		this.applyAndBindCardDao = applyAndBindCardDao;
	}



	public DeliveryContactDAO getDeliveryContractDAO() {
		return deliveryContractDAO;
	}



	public void setDeliveryContractDAO(DeliveryContactDAO deliveryContractDAO) {
		this.deliveryContractDAO = deliveryContractDAO;
	}



	public EntityDeliveryDAO getDeliveryDAO() {
		return deliveryDAO;
	}



	public void setDeliveryDAO(EntityDeliveryDAO deliveryDAO) {
		this.deliveryDAO = deliveryDAO;
	}



	public CardholderDAO getCardholderDAO() {
		return cardholderDAO;
	}



	public void setCardholderDAO(CardholderDAO cardholderDAO) {
		this.cardholderDAO = cardholderDAO;
	}



	public CardholderService getCardholderService() {
		return cardholderService;
	}



	public void setCardholderService(CardholderService cardholderService) {
		this.cardholderService = cardholderService;
	}



	public CustomerService getCustomerService() {
		return customerService;
	}
	


	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	

	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}



	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}
	
	
	
	

	public InvoiceCompanyService getInvoiceCompanyService() {
		return invoiceCompanyService;
	}



	public void setInvoiceCompanyService(InvoiceCompanyService invoiceCompanyService) {
		this.invoiceCompanyService = invoiceCompanyService;
	}



	public InvoiceAddressService getInvoiceAddressService() {
		return invoiceAddressService;
	}



	public void setInvoiceAddressService(InvoiceAddressService invoiceAddressService) {
		this.invoiceAddressService = invoiceAddressService;
	}



	public ContactService getContactService() {
		return contactService;
	}



	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}



	@Override
	public void batchProduceOrderForReadyOrder() throws BizServiceException {
		
	
		
		
	}


	/* 从文件批量导入持卡人信息和客户信息 */
	@Override
	public void batchInsertCustomerAndCardHolderFromOffLine(BatchCardHolderMessageDTO batchCardHolderMessageDTO) throws BizServiceException {
		Map<Integer, String> fileMap = batchCardHolderMessageDTO.getFileMap();
		for(int i=0;i<fileMap.size();i++){
			String data = fileMap.get(i+1);
			if(data==null){
				continue;
			}
			String[] cardholderMessage = data.split("-");
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
			String modifyTime = format.format(new Date());
			CustomerDTO customerDTO = new CustomerDTO();
			CardholderDTO  cardholderDTO = new CardholderDTO();
			customerDTO.setSalesmanId(batchCardHolderMessageDTO.getLoginUserId());
			customerDTO.setFatherEntityId(batchCardHolderMessageDTO.getDefaultEntityId());
			customerDTO.setDefaultEntityId(batchCardHolderMessageDTO.getDefaultEntityId());
			customerDTO.setCreateUser(batchCardHolderMessageDTO.getLoginUserId());
			customerDTO.setLoginUserId(batchCardHolderMessageDTO.getLoginUserId());
			// customerDTO.setCreateTime(modifyTime);
			customerDTO.setModifyTime(modifyTime);
			customerDTO.setModifyUser(batchCardHolderMessageDTO.getLoginUserId());
			cardholderDTO.setLoginUserId(batchCardHolderMessageDTO.getLoginUserId());
			/*姓名*/
			customerDTO.setCustomerName(cardholderMessage[0].trim());
			cardholderDTO.setFirstName(cardholderMessage[0].trim());
			/*性别*/
			cardholderDTO.setCardholderGender(cardholderMessage[1].trim());
			customerDTO.setGender(cardholderMessage[1].trim());// 性别
			if(StringUtils.isNotEmpty(cardholderMessage[1].trim())){
				/*称谓*/
				if(cardholderDTO.getCardholderGender().trim().equals("1")){
					cardholderDTO.setCardholderSalutation("1");
				}
				else if(cardholderDTO.getCardholderGender().trim().equals("2")){
					cardholderDTO.setCardholderSalutation("3");//女士
				}
			}
			/*电话*/
			customerDTO.setCustomerTelephone(cardholderMessage[2].trim());
			cardholderDTO.setCardholderMobile(cardholderMessage[2].trim());
			/*地址*/
			customerDTO.setCustomerAddress(cardholderMessage[3].trim());
			cardholderDTO.setMailingAddress(cardholderMessage[3].trim());
			/*证件类型*/
			customerDTO.setCorpCredType(cardholderMessage[4].trim());
			cardholderDTO.setIdType(cardholderMessage[4].trim());
			/*证件号*/
			customerDTO.setCorpCredId(cardholderMessage[5].trim());
			cardholderDTO.setIdNo(cardholderMessage[5].trim());
			/*生日*/
			cardholderDTO.setCardholderBirthday(cardholderMessage[6].trim());
			/*车牌号*/
			cardholderDTO.setPlateNumber(cardholderMessage[7].trim());
			/*车架号*/
			cardholderDTO.setV_Id(cardholderMessage[8].trim());
			/*驾驶证号*/
			cardholderDTO.setDriverLicence(cardholderMessage[9].trim());
			/*邮箱地址*/
			cardholderDTO.setCardholderEmail(cardholderMessage[10].trim());
			customerDTO.setEmail(cardholderMessage[10].trim());
			/*持卡人分类*/
			cardholderDTO.setCardholderSegment(cardholderMessage[11].trim());
			/* 客户职业类别 */
			customerDTO.setAwareness(cardholderMessage[12].trim());
			/*渠道*/
			customerDTO.setChannel(cardholderMessage[13].trim());
			//持卡人备注
			cardholderDTO.setCardholderComment(cardholderMessage[14].trim());
			
			/*证件失效日期*/
			if (StringUtils.isNotEmpty(cardholderMessage[15])) {
				customerDTO.setCorpCredEndValidity(DateUtil.formatStringDate(cardholderMessage[15].trim()));
				customerDTO.setValidity(DateUtil.formatStringDate(cardholderMessage[15].trim()));
			}
			
			/*国籍*/
			customerDTO.setNationality(cardholderMessage[16].trim());
			cardholderDTO.setCountry(cardholderMessage[16].trim());
			/*城市*/
			customerDTO.setCity(cardholderMessage[17].trim());
			cardholderDTO.setCity(cardholderMessage[17].trim());
			/*邮编*/
			customerDTO.setCustomerPostcode(cardholderMessage[18].trim());
			/* 民族 */
			customerDTO.setNation(cardholderMessage[19].trim());
			cardholderDTO.setCardholderNation(cardholderMessage[19].trim());
			/*客户的其他信息都填写为默认*/
			//customerDTO.setActivitySector("2");
			//customerDTO.setAwareness("1");
			customerDTO.setCorpActionAtLaw("2");
			customerDTO.setCreditStatus("0");
			customerDTO.setCusState("4");//1.已审核  4未审核
			customerDTO.setCustomerType("1");
			customerDTO.setHasDm("1");
			customerDTO.setPaymentTerm("2");
			customerDTO.setPictureSave("2");
			customerDTO.setPunishRecordFlag("2");
			customerDTO.setPunishRecordInfo("");
			customerDTO.setSalesRegionId("1");
			customerDTO.setIsblacklist("0");// 黑名单
			customerDTO.setRiskGrade("O");// 风险等级
			customerDTO.setEducation("7");// 教育
			customerDTO.setMarriage("5");// 婚姻
			/*持卡人信息默认字段*/
			cardholderDTO.setIsblacklist("0");
			cardholderDTO.setRiskGrade("O");
			cardholderDTO.setCardholderEducation("7");// 教育
			cardholderDTO.setCardholderMarriage("5");// 婚姻
			cardholderDTO.setCardholderFunction("1");
//			cardholderDTO.setCardholderSegment("0");
			cardholderDTO.setCardholderState("1");
			batchInsertCustomerAndCardHolder(customerDTO,cardholderDTO);
		}
	}
	
	@Override
	public void batchInsertIssue(// 一键售卡
			BatchCardHolderMessageDTO batchCardHolderMessageDTO)
			throws BizServiceException {
		int typeKH = 0;//lk 
		typeKH = batchCardHolderMessageDTO.getTypeKH();//前台传来的类型码，判断是否是选择卡号开卡
		
		// TODO Auto-generated method stub
		Map<Integer, String> fileMap = batchCardHolderMessageDTO.getFileMap();
		DecimalFormat df=new DecimalFormat("0");
		for(int i=0;i<fileMap.size();i++){
			String data = fileMap.get(i+1);
			if(data==null){
				continue;
			}
			String[] cardholderMessage= data.split("-");
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
			String modifyTime = format.format(new Date());
			CustomerDTO customerDTO = new CustomerDTO();
			CardholderDTO  cardholderDTO = new CardholderDTO();
			customerDTO.setSalesmanId(batchCardHolderMessageDTO.getLoginUserId());
			customerDTO.setFatherEntityId(batchCardHolderMessageDTO.getDefaultEntityId());
			customerDTO.setDefaultEntityId(batchCardHolderMessageDTO.getDefaultEntityId());
			customerDTO.setCreateUser(batchCardHolderMessageDTO.getLoginUserId());
			customerDTO.setLoginUserId(batchCardHolderMessageDTO.getLoginUserId());
			// customerDTO.setCreateTime(modifyTime);
			customerDTO.setModifyTime(modifyTime);
			customerDTO.setModifyUser(batchCardHolderMessageDTO.getLoginUserId());
			cardholderDTO.setLoginUserId(batchCardHolderMessageDTO.getLoginUserId());
			/*姓名*/
			customerDTO.setCustomerName(cardholderMessage[0].trim());
			cardholderDTO.setFirstName(cardholderMessage[0].trim());
			/*性别*/
			cardholderDTO.setCardholderGender(cardholderMessage[1].trim());
			customerDTO.setGender(cardholderMessage[1].trim());
			if(StringUtils.isNotEmpty(cardholderMessage[1].trim())){
				/*称谓*/
				if(cardholderDTO.getCardholderGender().trim().equals("1")){
					cardholderDTO.setCardholderSalutation("1");
				}
				else if(cardholderDTO.getCardholderGender().trim().equals("2")){
					cardholderDTO.setCardholderSalutation("3");//女士
				}
			}
			/*电话*/
			customerDTO.setCustomerTelephone(cardholderMessage[2].trim());
			cardholderDTO.setCardholderMobile(cardholderMessage[2].trim());
			/*地址*/
			customerDTO.setCustomerAddress(cardholderMessage[3].trim());
			cardholderDTO.setMailingAddress(cardholderMessage[3].trim());
			/*证件类型*/
			customerDTO.setCorpCredType(cardholderMessage[4].trim());
			cardholderDTO.setIdType(cardholderMessage[4].trim());
			/*证件号*/
			customerDTO.setCorpCredId(cardholderMessage[5].trim());
			cardholderDTO.setIdNo(cardholderMessage[5].trim());
			/*生日*/
			cardholderDTO.setCardholderBirthday(cardholderMessage[6].trim());
			/*车牌号*/
			cardholderDTO.setPlateNumber(cardholderMessage[7].trim());
			/*车架号*/
			cardholderDTO.setV_Id(cardholderMessage[8].trim());
			/*驾驶证号*/
			cardholderDTO.setDriverLicence(cardholderMessage[9].trim());
			/*邮箱地址*/
			cardholderDTO.setCardholderEmail(cardholderMessage[10].trim());
			customerDTO.setEmail(cardholderMessage[10].trim());
			/*持卡人分类*/
			cardholderDTO.setCardholderSegment(cardholderMessage[11].trim());
			/* 客户职业类别 */
			customerDTO.setAwareness(cardholderMessage[12].trim());
			/*渠道*/
			customerDTO.setChannel(cardholderMessage[13].trim());
			//持卡人备注
			cardholderDTO.setCardholderComment(cardholderMessage[14].trim());
			
			/*证件失效日期*/
			if (StringUtils.isNotEmpty(cardholderMessage[15])) {
				customerDTO.setCorpCredEndValidity(DateUtil.formatStringDate(cardholderMessage[15].trim()));
				customerDTO.setValidity(DateUtil.formatStringDate(cardholderMessage[15].trim()));
			}
			/*国籍*/
			customerDTO.setNationality(cardholderMessage[16].trim());
			cardholderDTO.setCountry(cardholderMessage[16].trim());
			/*城市*/
			customerDTO.setCity(cardholderMessage[17].trim());
			cardholderDTO.setCity(cardholderMessage[17].trim());
			/*邮编*/
			customerDTO.setCustomerPostcode(cardholderMessage[18].trim());
			/* 民族 */
			customerDTO.setNation(cardholderMessage[19].trim());
			cardholderDTO.setCardholderNation(cardholderMessage[19].trim());
			//客户合同
			SellerContractDTO sellerContractDTO =new SellerContractDTO();
			sellerContractDTO.setContractSeller(batchCardHolderMessageDTO.getDefaultEntityId());
			sellerContractDTO.setExpiryDate(cardholderMessage[20].trim());
			sellerContractDTO.setLoginUserId(batchCardHolderMessageDTO.getLoginUserId());
			sellerContractDTO.setDefaultEntityId(batchCardHolderMessageDTO.getDefaultEntityId());
			Double serFee=Double.parseDouble(cardholderMessage[21].trim())*100;
			String fee=df.format(serFee);
			//String fee=String.valueOf(Float.parseFloat(cardholderMessage[18])*100);
			sellerContractDTO.setDeliveryFee(fee);
			sellerContractDTO.setContractState("1");
			sellerContractDTO.setContractType("34");
			ImportServiceDTO importServiceDTO=new ImportServiceDTO();
			importServiceDTO.setSellerContractDTO(sellerContractDTO);
			SellerProductContractDTO sellerProductContractDTO=new SellerProductContractDTO();
			sellerProductContractDTO.setProductId(cardholderMessage[22].trim());//产品号
			importServiceDTO.setSellerProductContractDTO(sellerProductContractDTO);
			SellOrderInputDTO sellOrderInputDTO=new SellOrderInputDTO();
			SellOrderDTO sellOrderDTO=new SellOrderDTO();
			sellOrderDTO.setFaceValue(cardholderMessage[23].trim());// 第一次充值金额
			if (typeKH == 20170614) {
				sellOrderDTO.setCardLayoutId(cardholderMessage[24].trim());// 卡号
			} else {
				sellOrderDTO.setCardLayoutId(cardholderMessage[24].trim());// 卡面
				sellOrderDTO.setPackageId(cardholderMessage[25].trim());// 包装
			}

			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
			importServiceDTO.setSellOrderInputDTO(sellOrderInputDTO);
			customerDTO.setCorpActionAtLaw("2");
			customerDTO.setCreditStatus("0");
			customerDTO.setCusState("1");//1.已审核  4未审核
			customerDTO.setCustomerType("1");
			customerDTO.setHasDm("1");
			customerDTO.setPaymentTerm("2");
			customerDTO.setPictureSave("2");
			customerDTO.setPunishRecordFlag("2");
			customerDTO.setPunishRecordInfo("");
			customerDTO.setSalesRegionId("1");
			customerDTO.setIsblacklist("0");// 黑名单
			customerDTO.setRiskGrade("O");// 风险等级
			customerDTO.setEducation("7");// 教育
			customerDTO.setMarriage("5");// 婚姻
			/*持卡人信息默认字段*/
			cardholderDTO.setCardholderEducation("7");// 教育
			cardholderDTO.setCardholderMarriage("5");// 婚姻
			cardholderDTO.setIsblacklist("0");
			cardholderDTO.setRiskGrade("O");
			cardholderDTO.setCardholderFunction("1");
			cardholderDTO.setCardholderState("1");
			batchInsertCustomerAndCardHolder(customerDTO,cardholderDTO,importServiceDTO,typeKH);
		}
	}
	
	public String checkProductsTwo(ImportServiceDTO importServiceDTO) throws BizServiceException{
		String msg=null;
		SellContract sellContractObj=sellerContractDAO.selectByContractSeller(importServiceDTO.getDefaultEntityId());
		SellerContractDTO contractdto =new SellerContractDTO();
		contractdto.setSellContractId(sellContractObj.getSellContractId());
		contractdto=sellerContractService.view(contractdto);
		ProductDTO proDTO=new ProductDTO();
		//字段初始化
		String state=null;
		String maxBalance=null;
		for (int i = 0; i < contractdto.getProDTOs().size(); i++) {
			if(contractdto.getProDTOs().get(i).getProductId().equals(importServiceDTO.getSellerProductContractDTO().getProductId())){
				state="1";
				proDTO.setProductId(contractdto.getProDTOs().get(i).getProductId());
				proDTO= productService.viewProduct(proDTO);
				maxBalance=proDTO.getMaxBalance();
			}
		}
		if(state==null){
			 msg="当前登录的营销机构下没有ID为："+importServiceDTO.getSellerProductContractDTO().getProductId()+"的产品!";
			 return msg;
		}
		Float mBalance=Float.parseFloat(maxBalance)/100;
		if(Float.parseFloat(importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getFaceValue())>mBalance){
			msg = "第一次充值金额大于产品最大余额.";
		}
		ProductDTO productDTOs = new ProductDTO();
		productDTOs.setProductId(importServiceDTO.getSellerProductContractDTO().getProductId());
		productDTOs.setDefaultEntityId(importServiceDTO.getDefaultEntityId());
		productDTOs = productService.viewProduct(productDTOs);
		// 卡面
		// 字段初始化
		state = null;
		if (productDTOs.getOnymousStat().equals("2")) {
			return "不支持不记名卡产品售卡！";
		}
		return msg;
		
	}
	
	public String checkProducts(ImportServiceDTO importServiceDTO) throws BizServiceException{
		String msg=null;
		SellContract sellContractObj=sellerContractDAO.selectByContractSeller(importServiceDTO.getDefaultEntityId());
		SellerContractDTO contractdto =new SellerContractDTO();
		contractdto.setSellContractId(sellContractObj.getSellContractId());
		contractdto=sellerContractService.view(contractdto);
		ProductDTO proDTO=new ProductDTO();
		
		//字段初始化
		String state=null;
		String maxBalance=null;
		for (int i = 0; i < contractdto.getProDTOs().size(); i++) {
			if(contractdto.getProDTOs().get(i).getProductId().equals(importServiceDTO.getSellerProductContractDTO().getProductId())){
				state="1";
				proDTO.setProductId(contractdto.getProDTOs().get(i).getProductId());
				proDTO= productService.viewProduct(proDTO);
				maxBalance=proDTO.getMaxBalance();
			}
		}
		if(state==null){
			 msg="当前登录的营销机构下没有ID为："+importServiceDTO.getSellerProductContractDTO().getProductId()+"的产品!";
			 return msg;
		}
		Float mBalance=Float.parseFloat(maxBalance)/100;
		if(Float.parseFloat(importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getFaceValue())>mBalance){
			msg = "第一次充值金额大于产品最大余额.";
		}

		ProductDTO productDTOs = new ProductDTO();
		productDTOs.setProductId(importServiceDTO.getSellerProductContractDTO().getProductId());
		productDTOs.setDefaultEntityId(importServiceDTO.getDefaultEntityId());
		productDTOs = productService.viewProduct(productDTOs);
		// 卡面
		// 字段初始化
		state = null;
		if (productDTOs.getOnymousStat().equals("2")) {
			return "不支持不记名卡产品售卡！";
		}
		for (int i = 0; i < productDTOs.getCardLayoutDTOs().size(); i++) {
			if (productDTOs.getCardLayoutDTOs().get(i).getCardLayoutId()
					.equals(importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getCardLayoutId())) {
				state = "1";
			}
		}
		if (state == null) {
			if (msg != null) {
				msg = msg + "该产品下没有ID为：" + importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getCardLayoutId()
						+ "的卡面!";
			} else {
				msg = "该产品下没有ID为：" + importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getCardLayoutId()
						+ "的卡面!";
			}
		}
		String packageFee=null;
		 for (int i = 0; i < productDTOs.getPackages().size(); i++) {
				if(productDTOs.getPackages().get(i).getPackageId().equals(importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getPackageId())){
					packageFee=productDTOs.getPackages().get(i).getPackageFee();
				}
			}
		 if(packageFee==null){
			 if(msg!=null){
				 msg=msg+"该产品下没有ID为："+importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getPackageId()+"的包装!";
			 }else{
				 msg="该产品下没有ID为："+importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getPackageId()+"的包装!";
			 }
			
		 }
	 	
		return msg;
		
	}
	
	//lk
	@Override
	@SuppressWarnings("unused")
	public ImportServiceDTO checkProductTwo(ImportServiceDTO importServiceDTO) throws BizServiceException {
			int cunt=0;
			List<String> errMsg=new ArrayList<String>();
			for (int i = 0; i <importServiceDTO.getImportServiceDtoList().size(); i++) {
				importServiceDTO.getImportServiceDtoList().get(i).setDefaultEntityId(importServiceDTO.getDefaultEntityId());
				String msg=this.checkProductsTwo(importServiceDTO.getImportServiceDtoList().get(i));
				if(msg!=null&&!msg.equals("")){
					cunt=i+1;
					msg="第"+cunt+"行，"+msg;
					errMsg.add(msg);
				}
			}
			if(errMsg==null||errMsg.size()==0){
				Set entries = importServiceDTO.getMap().entrySet();
				if(entries != null) {
					Iterator iterator = entries.iterator();
					while(iterator.hasNext()) {
					    Map.Entry entry =(Entry) iterator.next();
					    String key = (String) entry.getKey();
					    String value = (String) entry.getValue();
					    StockCardQueryDTO stockCardQueryDTO=new StockCardQueryDTO();
					    stockCardQueryDTO.setEntityId(importServiceDTO.getDefaultEntityId());
						stockCardQueryDTO.setFunctionRoleId("3");//营销机构下的库存
						stockCardQueryDTO.setProductId(key);
						PageDataDTO pd = pageQueryDAO.query(
								"STOCKCARD.selectStockCardInfo", stockCardQueryDTO);
						int number=0;
						if(pd.getData()!=null&&pd.getData().size()!=0){
							Map<String, Integer> count= (Map<String, Integer>) pd.getData().get(0);
							number=count.get("stockNumber");
						}
						
						if(number<Integer.parseInt(value)){
							String msg="卡产品："+key+",库存不足!";
							errMsg.add(msg);
						}
					}
				}
			}
			importServiceDTO.setList(errMsg);
			return importServiceDTO;
			
		}
		
	@Override
	@SuppressWarnings("unused")
	public ImportServiceDTO checkProduct(ImportServiceDTO importServiceDTO) throws BizServiceException{
		int cunt=0;
		List<String> errMsg=new ArrayList<String>();
		for (int i = 0; i <importServiceDTO.getImportServiceDtoList().size(); i++) {
			importServiceDTO.getImportServiceDtoList().get(i).setDefaultEntityId(importServiceDTO.getDefaultEntityId());
			String msg=this.checkProducts(importServiceDTO.getImportServiceDtoList().get(i));
			if(msg!=null&&!msg.equals("")){
				cunt=i+1;
				msg="第"+cunt+"行，"+msg;
				errMsg.add(msg);
			}
		}
		if(errMsg==null||errMsg.size()==0){
			Set entries = importServiceDTO.getMap().entrySet();
			if(entries != null) {
				Iterator iterator = entries.iterator();
				while(iterator.hasNext()) {
				    Map.Entry entry =(Entry) iterator.next();
				    String key = (String) entry.getKey();
				    String value = (String) entry.getValue();
				    StockCardQueryDTO stockCardQueryDTO=new StockCardQueryDTO();
				    stockCardQueryDTO.setEntityId(importServiceDTO.getDefaultEntityId());
					stockCardQueryDTO.setFunctionRoleId("3");//营销机构下的库存
					stockCardQueryDTO.setProductId(key);
					PageDataDTO pd = pageQueryDAO.query(
							"STOCKCARD.selectStockCardInfo", stockCardQueryDTO);
					int number=0;
					if(pd.getData()!=null&&pd.getData().size()!=0){
						Map<String, Integer> count= (Map<String, Integer>) pd.getData().get(0);
						number=count.get("stockNumber");
					}
					
					if(number<Integer.parseInt(value)){
						String msg="卡产品："+key+",库存不足!";
						errMsg.add(msg);
					}
				}
			}
		}
		importServiceDTO.setList(errMsg);
		return importServiceDTO;
		
	}
	//查询持卡人下持有的卡
	public List<ProductDTO> checkProductId(CardholderDTO cardholderDTO) throws BizServiceException{
		CardholderExample  cardholderExample = new CardholderExample();
		cardholderExample.createCriteria().andIdTypeEqualTo(cardholderDTO.getIdType()).andIdNoEqualTo(cardholderDTO.getIdNo()).andDataStateEqualTo("1");
		List<Cardholder> cardholderList=cardholderDAO.selectByExample(cardholderExample);
		List<ProductDTO> lstCardNo =new ArrayList<ProductDTO>();
		if(cardholderList.size()>0){
			/** 关联持卡人持有卡信息 */
			try {
				for (int i = 0; i < cardholderList.size(); i++) {
					lstCardNo = cardholderDAO
							.selectCardNoListByCardholderId(cardholderList.get(i).getCardholderId());
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
//		List<String> list=new ArrayList<String>();
//		if(lstCardNo.size()>0){
//			
//			for (int i = 0; i < lstCardNo.size(); i++) {
//				list.add(lstCardNo.get(i).getProductId());
//			}
//			
//			//去除重复的卡产品
//			 List<String> listTemp= new ArrayList<String>();  
//			 Iterator<String> it=list.iterator();  
//			 while(it.hasNext()){  
//			  String a=it.next();  
//			  if(listTemp.contains(a)){  
//			   it.remove();  
//			  }  
//			  else{  
//			   listTemp.add(a);  
//			  }  
//			 }  
//		}

		return lstCardNo;
	}
	
	@Override
	public ImportServiceDTO checkProductIdByCardholder(CardholderDTO cardholderDTO) throws BizServiceException{
		ImportServiceDTO importServiceDTO=new ImportServiceDTO();
		int cunt=0;
		List<String> errMsg=new ArrayList<String>();
		for (int i = 0; i < cardholderDTO.getCardholderDTOList().size(); i++) {
			List<ProductDTO> list=this.checkProductId(cardholderDTO.getCardholderDTOList().get(i));
			cunt=i+1;
			for (int j = 0; j <list.size(); j++) {
				//把卡产品从暂存字段中取出 对比
				if(list.get(j).getProductId().equals(cardholderDTO.getCardholderDTOList().get(i).getCardholderEmail())){
					String msg="第"+cunt+"行,该持卡人已存在相同的卡产品，无法自动导入。请确定后，手动售卡！";
					errMsg.add(msg);
				}
			}
		}
		importServiceDTO.setList(errMsg);
		return importServiceDTO;
		
	}
	//判断卡片状态 and该卡产品下是否存在卡片
	@Override
	public ImportServiceDTO checkProductIdByCardholderTwo(CardholderDTO cardholderDTO) throws BizServiceException{
		ImportServiceDTO importServiceDTO=new ImportServiceDTO();
		int cunt=0;
		List<String> errMsg=new ArrayList<String>();
		for (int i = 0; i < cardholderDTO.getCardholderDTOList().size(); i++) {
			cunt=i+1;
			
			OrderReadyDTO orderReadyDTO=new OrderReadyDTO();
			String kcp = cardholderDTO.getCardholderDTOList().get(i).getCardholderEmail();//卡产品
			orderReadyDTO.setProductId(kcp);//卡产品
			orderReadyDTO.setOrderType("10000011");
			
			orderReadyDTO.setStartCardNo(cardholderDTO.getCardholderDTOList().get(i).getCardholderMobile());//卡号
			orderReadyDTO.setEndCardNo(cardholderDTO.getCardholderDTOList().get(i).getCardholderMobile());//卡号
			 
			List<EntityStock> list = null;
			try {
				list = orderBaseQueryBO.getSelectIsCARDP001(orderReadyDTO);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BizServiceException("第"+cunt+"行,查询卡片状态时，系统异常！");
			}
		 	if(list.size()==0){
//		 		String state = list.get(0).getStockState();
		 		String msg = "第"+cunt+"行,卡号不可用。";
//		 		if(state.equals("5")){
//		 			msg="第"+cunt+"行,卡状态是，不正确。";
//		 		}
//		 		else if(state.equals("3")){
//		 			msg="第"+cunt+"行,卡状态是，不正确。";
//		 		}
//		 		else if(state.equals("2")){
//		 			msg="第"+cunt+"行,卡状态是，不正确。";
//		 		}
//		 		else if(state.equals("1")){
//		 			msg="第"+cunt+"行,卡状态是，不正确。";
//		 		}
//		 		else{
//		 			msg="第"+cunt+"行,"+kcp+"卡产品下不存在该卡。";
//		 		}
				errMsg.add(msg);
			}
		}
		importServiceDTO.setList(errMsg);
		return importServiceDTO;
	}


	public void insertAttachInfoDTO(CustomerDTO customerDTO,String type,String id) throws BizServiceException{
		
		//增加附加信息
		AttachInfoDTO attachInfoDTO=new AttachInfoDTO();
		attachInfoDTO.setPeopleNo(id);
		attachInfoDTO.setPeopleType(type);//00代表客户，01代表持卡人
		attachInfoDTO.setIndustry(customerDTO.getActivitySector());//行业
		attachInfoDTO.setProfession(customerDTO.getAwareness());//职业
		attachInfoDTO.setValidity(DateUtil.StringDate(customerDTO.getCorpCredEndValidity()));//失效期
		attachInfoDTO.setCountry(customerDTO.getNationality());//国家
		attachInfoDTO.setCity(customerDTO.getCity());//城市
		attachInfoDTO.setUpdateDate(DateUtil.getCurrentTime());
		attachInfoDTO.setEntityId(customerDTO.getDefaultEntityId());//机构号
		attachInfoDTO.setDataStat("1");
		attachInfoDTO.setNation(customerDTO.getNation());// 名族
		attachInfoDTO.setEducation(customerDTO.getEducation());// 教育
		attachInfoDTO.setMarriage(customerDTO.getMarriage());// 婚姻状况
		attachInfoDTO.setRs1(customerDTO.getGender());// 性别
		attachInfoDTO.setEmail(customerDTO.getEmail());// 邮箱
		attachInfoDTO.setIsblacklist(customerDTO.getIsblacklist());// 黑名单
		attachInfoDTO.setRiskGrade(customerDTO.getRiskGrade());// 风险等级
		try {
			attachDAO.insertAttachInfo(attachInfoDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizServiceException("查询持卡人客户信息失败！");
		}
		
	}
	
	public void updateAttachInfoDTO(CustomerDTO customerDTO,String type,String id) throws BizServiceException{
		//附加信息
		AttachInfoDTO attachInfoDTO=new AttachInfoDTO();
		attachInfoDTO.setPeopleNo(id);
		attachInfoDTO.setPeopleType(type);//00代表客户，01代表持卡人
		attachInfoDTO.setIndustry(customerDTO.getActivitySector());//行业
		attachInfoDTO.setProfession(customerDTO.getAwareness());//职业
		attachInfoDTO.setValidity(DateUtil.StringDate(customerDTO.getCorpCredEndValidity()));//失效期
		attachInfoDTO.setCountry(customerDTO.getNationality());//国家
		attachInfoDTO.setCity(customerDTO.getCity());//城市
		attachInfoDTO.setUpdateDate(DateUtil.getCurrentTime());
		attachInfoDTO.setEntityId(customerDTO.getDefaultEntityId());//机构号
		attachInfoDTO.setDataStat("1");
		attachInfoDTO.setRs1(customerDTO.getGender());// 性别
		attachInfoDTO.setEmail(customerDTO.getEmail());// 邮箱
		attachInfoDTO.setEducation(customerDTO.getEducation());// 教育
		attachInfoDTO.setMarriage(customerDTO.getMarriage());// 婚姻状况
		attachInfoDTO.setNation(customerDTO.getNation());// 名族
		attachInfoDTO.setIsblacklist(customerDTO.getIsblacklist());// 黑名单
		attachInfoDTO.setRiskGrade(customerDTO.getRiskGrade());// 风险等级
		try {
			attachDAO.updateAttachInfo(attachInfoDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizServiceException("查询持卡人客户信息失败！");
		}
		
	}
	
	public int selectAttachInfoDTO(CustomerDTO customerDTO,String type,String id) throws BizServiceException{
		
		//增加附加信息
		AttachInfoDTO attachInfoDTO=new AttachInfoDTO();
		attachInfoDTO.setPeopleNo(id);
		attachInfoDTO.setPeopleType(type);//00代表客户，01代表持卡人
		attachInfoDTO.setEntityId(customerDTO.getDefaultEntityId());//机构号
		attachInfoDTO.setDataStat("1");
		try {
			List<AttachInfoDTO> attachInfos=attachDAO.getAttachInfos(attachInfoDTO);
			return attachInfos.size();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizServiceException("查询持卡人客户信息失败！");
		}
		
		
	}
	/*客户信息及持卡人信息和自动售卡批量导入*/
	public void batchInsertCustomerAndCardHolder(CustomerDTO customerDTO,CardholderDTO cardholderDTO,ImportServiceDTO importServiceDTO,int typeKH) throws BizServiceException{
		SellContract sellContractObj = sellerContractDAO.selectByContractSeller(customerDTO.getDefaultEntityId());
		SellerContractDTO contractdto =new SellerContractDTO();
		contractdto.setSellContractId(sellContractObj.getSellContractId());
		contractdto=sellerContractService.view(contractdto);
		ProductDTO proDTO = new ProductDTO();

		// 字段初始化
		String state = null;
		ProductDTO productDTOs = new ProductDTO();
		productDTOs.setProductId(importServiceDTO.getSellerProductContractDTO().getProductId());
		productDTOs.setDefaultEntityId(importServiceDTO.getSellerProductContractDTO().getDefaultEntityId());
		productDTOs = productService.viewProduct(productDTOs);
		String packageFee = null;
		if (typeKH == 20170614) {
			for (int i = 0; i < productDTOs.getPackages().size(); i++) {// 没有包装列
				// if(productDTOs.getPackages().get(i).getPackageId().equals("1001")){
				// state="2";
				packageFee = productDTOs.getPackages().get(i).getPackageFee();
				// }
			}
		} else {
			for (int i = 0; i < productDTOs.getPackages().size(); i++) {
				if (productDTOs.getPackages().get(i).getPackageId()
						.equals(importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getPackageId())) {
					// state="2";
					packageFee = productDTOs.getPackages().get(i).getPackageFee();
				}
			}
		}
		 /*查询看客户是否存在，如果存在则更新为最新的信息，不存在则插入新信息*/
		List<CustomerDTO> customerByIdNo = customerService.getCustomerByIdNo(customerDTO);
		Cardholder cardholder = new Cardholder();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		if(customerByIdNo!=null&&customerByIdNo.size()>0){
		
			String modifyTime = format.format(new Date());
			//更新客户信息
			
			Customer  customer = new Customer();
			ReflectionUtil.copyProperties(customerDTO, customer);
			customer.setEntityId(customerByIdNo.get(0).getEntityId());
			customer.setFatherEntityId(customerByIdNo.get(0).getFatherEntityId());
			customer.setModifyUser(customerDTO.getModifyUser());
			customer.setModifyTime(modifyTime);
			cardholderDTO.setEntityId(customer.getEntityId());
			customerDTO.setEntityId(customer.getEntityId());
			customerDAO.updateByPrimaryKeySelective(customer);
			//更新快递点信息
			EntityDelivery entityDelivery = new EntityDelivery();
			entityDelivery.setDeliveryAddress(customerDTO.getCustomerAddress());
			entityDelivery.setDeliveryName(customerDTO.getCustomerName());
			entityDelivery.setDeliveryPostcode(customerDTO.getCustomerPostcode());
			entityDelivery.setModifyTime(modifyTime);
			entityDelivery.setModifyUser(customerDTO.getModifyUser());
			EntityDeliveryExample entityDeliveryExample = new EntityDeliveryExample();
			entityDeliveryExample.createCriteria().andEntityIdEqualTo(customerByIdNo.get(0).getEntityId())
											.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
											.andDefaultFlagEqualTo(DataBaseConstant.DEFAULT_FLAG_YES);
			deliveryDAO.updateByExampleSelective(entityDelivery, entityDeliveryExample);
			//更新默认快递点联系人信息
			List<EntityDelivery> example = deliveryDAO.selectByExample(entityDeliveryExample);
			if(example!=null&&example.size()>0){
				DeliveryContactExample deliveryContactExample = new DeliveryContactExample();
				deliveryContactExample.createCriteria().andDefaultFlagEqualTo(DataBaseConstant.DEFAULT_FLAG_YES)
																			.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
																			.andDeliveryPointIdEqualTo(example.get(0).getDeliveryId());
				DeliveryContact deliveryContact = new DeliveryContact();
				deliveryContact.setContactPhone(customerDTO.getCustomerTelephone());
				deliveryContact.setModifyTime(modifyTime);
				deliveryContact.setModifyUser(customerDTO.getModifyUser());
				deliveryContact.setDeliveryContact(customerDTO.getCustomerName());
				deliveryContractDAO.updateByExampleSelective(deliveryContact, deliveryContactExample);
			}
			
			// 更新默认联系人信息
			List<ContactDTO> ContactDTOs = contactService.inqueryContact(customerByIdNo.get(0).getEntityId());
			for(ContactDTO contactDTO : ContactDTOs){
				if(contactDTO.getDataState().trim().equals("1")&&contactDTO.getDefaultFlag().trim().equals("1")){
					contactDTO.setContactName(customer.getCustomerName());
					contactDTO.setContactType(customer.getCustomerType());
					contactDTO.setContactGender(cardholderDTO.getCardholderGender());
					contactDTO.setContactMobilePhone(customer.getCustomerTelephone());
					contactDTO.setModifyUser(customerDTO.getModifyUser());
					contactDTO.setModifyTime(modifyTime);
					contactDTO.setPapersNo(customerDTO.getCorpCredId());
					contactDTO.setPapersType(customerDTO.getCorpCredType());
					contactService.updateContact(contactDTO);
				}
			}
			
			// 更新默认发票地址信息
			List<InvoiceAddressDTO> invoiceAddressDTOs= invoiceAddressService.inquery(customerByIdNo.get(0).getEntityId());
			for(InvoiceAddressDTO invoiceAddressDTO : invoiceAddressDTOs ){
				if(invoiceAddressDTO.getDataState().trim().equals("1")&&invoiceAddressDTO.getDefaultFlag().trim().equals("1")){
					InvoiceAddressDTO invoiceAddress = new InvoiceAddressDTO();
					ReflectionUtil.copyProperties(invoiceAddressDTO, invoiceAddress);
					
					invoiceAddress.setInvoiceAddress(customer.getCustomerAddress());
					invoiceAddress.setAddressPostcode(customer.getCustomerPostcode());
					invoiceAddress.setModifyUser(customerDTO.getModifyUser());
					invoiceAddress.setModifyTime(modifyTime);
					invoiceAddress.setInvoiceRecipient(customer.getCustomerName());
					invoiceAddressService.updateByPrimaryKey(invoiceAddress);
				}
			}
			

			// 更新默认发票公司信息
			List<InvoiceCompany> invoiceCompanys= invoiceCompanyService.selectByEntityId(customerByIdNo.get(0).getEntityId());
			for(InvoiceCompany invoiceCompany : invoiceCompanys){
				if(invoiceCompany.getDataState().trim().equals("1")&&invoiceCompany.getDefaultFlag().trim().equals("1")){
					invoiceCompany.setInvoiceCompanyName(customerDTO.getCustomerName());
					invoiceCompany.setModifyTime(modifyTime);
					invoiceCompany.setModifyUser(customerDTO.getModifyUser());
					invoiceCompanyService.updateByPrimaryKey(invoiceCompany);
				}
			}
			
		}else{
			
			CustomerDTO insertCustomer = customerService.insertCustomer(customerDTO);
			cardholderDTO.setEntityId(insertCustomer.getEntityId());
			customerDTO.setDefaultEntityId(insertCustomer.getFatherEntityId());
			customerDTO.setEntityId(insertCustomer.getEntityId());
			//cardholderService.insert(cardholderDTO);
		}
		//人行附加信息
		int count=selectAttachInfoDTO(customerDTO, "00",customerDTO.getEntityId());
		if(count>0){
			updateAttachInfoDTO(customerDTO, "00",customerDTO.getEntityId());
		}else{
			insertAttachInfoDTO(customerDTO, "00",customerDTO.getEntityId());
		}
		/*查询看持卡人是否存在，如果存在则更新为最新的信息，不存在则插入新信息*/
		String modifyTime = format.format(new Date());
		ReflectionUtil.copyProperties(cardholderDTO, cardholder);
		cardholder.setModifyTime(modifyTime);
		cardholderDTO.setDefaultEntityId(customerDTO.getDefaultEntityId());
		cardholder.setModifyUser(customerDTO.getModifyUser());
		CardholderExample  cardholderExample = new CardholderExample();
		cardholderExample.createCriteria().andIdTypeEqualTo(cardholderDTO.getIdType()).andIdNoEqualTo(cardholderDTO.getIdNo()).andDataStateEqualTo("1");
		List<Cardholder> cardholderLists=cardholderDAO.selectByExample(cardholderExample);
		if(cardholderLists.size()>0){
			cardholder.setEntityId(customerByIdNo.get(0).getEntityId());
			cardholder.setCardholderId(cardholderLists.get(0).getCardholderId());
			cardholderDAO.updateByExampleSelective(cardholder, cardholderExample);
			SellOrderCardListExample sellOrderCardListExample=new SellOrderCardListExample();
			sellOrderCardListExample.createCriteria().andCardholderIdEqualTo(cardholderLists.get(0).getCardholderId());
			//查询订单中有没有存在当前持卡人，如果存在，修改FirstName
			List<SellOrderCardList> sellOrderCardList=sellOrderCardListDAO.selectByExample(sellOrderCardListExample);
			for (int i = 0; i < sellOrderCardList.size(); i++) {
				SellOrderCardList sellOrderCardListDTO=new SellOrderCardList();
				sellOrderCardListDTO.setCardholderId(sellOrderCardList.get(i).getCardholderId());
				sellOrderCardListDTO.setOrderCardListId(sellOrderCardList.get(i).getOrderCardListId());
				sellOrderCardListDTO.setOrderId(sellOrderCardList.get(i).getOrderId());
				sellOrderCardListDTO.setFirstName(cardholder.getFirstName());
				sellOrderCardListDAO.updateByPrimaryKeySelective(sellOrderCardListDTO);
				
			}
			
		}else{
			cardholderService.insert(cardholderDTO);
		}
		List<Cardholder> cardholders=cardholderDAO.selectByExample(cardholderExample);
		//人行附加信息
		//持卡人id
		int counts=selectAttachInfoDTO(customerDTO, "01",cardholders.get(0).getCardholderId());
		if(counts>0){
			updateAttachInfoDTO(customerDTO, "01",cardholders.get(0).getCardholderId());
		}else{
			insertAttachInfoDTO(customerDTO, "01",cardholders.get(0).getCardholderId());
		}
		//添加客户合同
		 List<CustomerDTO> customers= customerService.getCustomerByIdNo(customerDTO);
		 SellerContractDTO sellerContractDTO =importServiceDTO.getSellerContractDTO();
		 sellerContractDTO.setContractBuyer(customers.get(0).getEntityId());
		 sellerContractDTO.setContractSellerName(customers.get(0).getCustomerName());
		 SellContract sellContract=new SellContract();
		 ReflectionUtil.copyProperties(sellerContractDTO, sellContract);
		 sellContract=sellerContractDAO.selectByPrimary(sellContract);
		 SellerContractDTO contractDTO=new SellerContractDTO();
		 if(sellContract==null){
			 try {
				 contractDTO=sellerContractService.insert(sellerContractDTO);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new BizServiceException("客户："+customerDTO.getCustomerName()+",添加客户合同失败！导入中断！");
			}
			 
		 }else{
			 sellerContractDTO.setSellContractId(sellContract.getSellContractId());
			 sellerContractService.update(sellerContractDTO);
		 }
		 if(contractDTO.getSellContractId()==null){
			 contractDTO.setSellContractId(sellContract.getSellContractId());
		 }
		
		//添加产品
		 SellerProductContractDTO sellerProductContractDTO= importServiceDTO.getSellerProductContractDTO();
		 sellerProductContractDTO.setAnnualFee("0");
		 sellerProductContractDTO.setCardFee("0");
		 sellerProductContractDTO.setDefaultEntityId(sellerContractDTO.getDefaultEntityId());
		 sellerProductContractDTO.setLoginUserId(sellerContractDTO.getLoginUserId());
		 sellerProductContractDTO.setSellContractId(contractDTO.getSellContractId());
		 ProductDTO productDTO=new ProductDTO();
		 productDTO.setProductId(sellerProductContractDTO.getProductId());
		 productDTO.setDefaultEntityId(sellerProductContractDTO.getDefaultEntityId());
		 //添加合同时查看产品
		 productDTO= productService.viewProductForContract(productDTO);
		 List<ServiceDTO> productServices =  productDTO.getServices();
		// 添加合同服务明细
		if (null != productServices && productServices.size() > 0) {
			List<SellerAcctypeContractDTO> accDTOs = new ArrayList<SellerAcctypeContractDTO>();
			for (int i = 0; i < productServices.size() ; i++) {
				SellerAcctypeContractDTO accDTO = new SellerAcctypeContractDTO();
				accDTO.setSellContractId(sellerContractDTO
									.getSellContractId());
				accDTO.setProductId(sellerProductContractDTO
											.getProductId());
				accDTO.setAcctypeId(productServices.get(i).getServiceId());
				// 交易类型暂定为0000
				accDTO.setTxnNum("0000");
				accDTO.setFee(productServices.get(i).getDefaultRate());
				accDTOs.add(accDTO);
				}
				sellerProductContractDTO.setAccDTOs(accDTOs);
			}
		// 判断产品在合同中是否已存在
		SellProdContractExample example = new SellProdContractExample();
		example.createCriteria().andProductIdEqualTo(
					sellerProductContractDTO.getProductId()) 
						.andSellContractIdEqualTo(
							sellerProductContractDTO.getSellContractId())
							.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL);
		if (sellProdContractDAO.countByExample(example)== 0) {
			try {
				 sellerProductContractService.insert(sellerProductContractDTO);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new BizServiceException("客户："+customerDTO.getCustomerName()+",添加产品失败！导入中断！");
			}
		}
		
		
		 
		 //销售记名卡订单
		 SellOrderInputDTO sellOrderInputDTO=importServiceDTO.getSellOrderInputDTO();
		 SellOrderDTO sellOrderDTO=sellOrderInputDTO.getSellOrderDTO();
		 sellOrderDTO.setAdditionalFee("0");//默认附加费为0
		 sellOrderDTO.setCardQuantity("0");//订单张数
		 String deliveryFee=String.valueOf(Float.parseFloat(sellerContractDTO.getDeliveryFee())/100);	
		 sellOrderDTO.setDeliveryFee(deliveryFee);//送货费用
		 sellOrderDTO.setDeliveryMeans("1");//送货方式
		 sellOrderDTO.setDiscountFee("0");//折扣费
		 sellOrderDTO.setFirstEntityId(customers.get(0).getEntityId());//客户号
		 sellOrderDTO.setProcessEntityId(sellerContractDTO.getDefaultEntityId());//营销机构
		 sellOrderDTO.setOrderType("10000011");//订单类型
		 sellOrderDTO.setSaleMan(sellerContractDTO.getLoginUserId());//销售人员
		 sellOrderDTO.setPayChannel("1");//支付渠道
		 sellOrderDTO.setPackageFee(packageFee.trim());//包装费
		 sellOrderDTO.setProductId(sellerProductContractDTO.getProductId());//产品ID
		 sellOrderDTO.setOrderSource("1");//订单来源
		 sellOrderDTO.setOrderState("1");//订单状态
		 sellOrderDTO.setPaymentDelay("0");//付款延期天数
		 sellOrderDTO.setPaymentState("0");//订单状态为未支付
		 sellOrderDTO.setPaymentTerm("2");//支付节点
		 sellOrderDTO.setPerFlag("per");
		 sellOrderDTO.setServiceId(productDTO.getServices().get(0).getServiceId());//充值账户
		 sellOrderDTO.setServiceName(productDTO.getServices().get(0).getServiceName());//充值账户名称
		 sellOrderDTO.setCardIssueFee("0");//卡费
		 sellOrderDTO.setAnnualFee("0");//年费
		 sellOrderDTO.setModifyUser(sellerContractDTO.getLoginUserId());//修改订单用户
		 sellOrderDTO.setCreateUser(sellerContractDTO.getLoginUserId());//创建订单用户
		 sellOrderDTO.setLoginUserId(sellerContractDTO.getLoginUserId());
		 sellOrderDTO.setOrderDate(getDateFormat(new Date()));
		 sellOrderDTO.setInvoiceDate(getDateFormat(new Date()));
		 Float totalPrice=Float.parseFloat(sellOrderDTO.getDeliveryFee())+Float.parseFloat(packageFee)+
				 Float.parseFloat(importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getFaceValue());
		 sellOrderDTO.setTotalPrice(String.valueOf(totalPrice));
		 sellOrderInputDTO.setDefaultEntityId(sellerContractDTO.getDefaultEntityId());
		 sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
		 sellOrderInputDTO=orderService.initAdd(sellOrderInputDTO);
		 sellOrderDTO=sellOrderInputDTO.getSellOrderDTO();
		 for (int i = 0; i < sellOrderInputDTO.getCustomerDTO().getDeliveryPointList().size(); i++) {
			if( sellOrderInputDTO.getCustomerDTO().getDeliveryPointList().get(i).getEntityId().equals(customers.get(0).getEntityId())&&
					sellOrderInputDTO.getCustomerDTO().getDeliveryPointList().get(i).getDeliveryAddress().equals(customerDTO.getCustomerAddress())){
				//快递地址
				sellOrderDTO.setDeliveryPoint(sellOrderInputDTO.getCustomerDTO().getDeliveryPointList().get(i).getDeliveryId());
				for (int j = 0; j < sellOrderInputDTO.getCustomerDTO().getDeliveryPointList().get(i).getRecipientList().size(); j++) {
					if(sellOrderInputDTO.getCustomerDTO().getDeliveryPointList().get(j).getDeliveryId().equals(sellOrderDTO.getDeliveryPoint())){
						//收件人
						sellOrderDTO.setOrderContact(sellOrderInputDTO.getCustomerDTO().getDeliveryPointList().get(i).getRecipientList().get(j).getDeliveryContactId());
					}
				}
			}
		}
		 sellOrderDTO.setContactId(sellOrderInputDTO.getCustomerDTO().getContractList().get(0).getContactId());//经办人ID
		 
		 try {
			 sellOrderInputDTO=orderService.orderInsert(sellOrderDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BizServiceException("客户："+customerDTO.getCustomerName()+",添加售卡订单失败！导入中断！");
		}
		
		 
		 //添加持卡人
		 CardholderQueryDTO cardholderQueryDTO=new CardholderQueryDTO();
		 ReflectionUtil.copyProperties(cardholderDTO, cardholderQueryDTO);
		 PageDataDTO pageDataDTO=cardholderService.inquery(cardholderQueryDTO);
		 Map<String, String> map=(Map<String, String>) pageDataDTO.getData().get(0);
		 String cardholderId=map.get("cardholderId");
		 String [] cardholderList=new String[]{cardholderId};
		 sellOrderInputDTO.setOrderListStr(cardholderList);
		 sellOrderInputDTO.setLoginUserId(sellerContractDTO.getLoginUserId());
		 try {
			 sellOrderInputDTO=orderService.insertCardholderList(sellOrderInputDTO);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BizServiceException("售卡订单："+sellOrderInputDTO.getOrderId()+",添加持卡人失败！导入中断！");
		}
		
		 //提交订单
		 String [] ecChoose=new String[]{sellOrderInputDTO.getSellOrderDTO().getOrderId()};
		 sellOrderInputDTO.setEc_choose(ecChoose);
		 try {
			 submitOrderService.submitOrderAtInput(sellOrderInputDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BizServiceException("售卡订单："+sellOrderInputDTO.getOrderId()+",提交订单失败！导入中断！");
		}
		 //订单审核
		 SellOrderDTO sellOrder= sellOrderInputDTO.getSellOrderDTO();
		 sellOrder.setLoginUserId(sellOrderInputDTO.getLoginUserId());
		 try {
			 submitOrderService.submitOrderAtConfirm(sellOrder);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BizServiceException("售卡订单："+sellOrder.getOrderId()+",订单审核失败！导入中断！");
		}
		 OrderReadyDTO orderReadyDTO=new OrderReadyDTO();
		 try {
		 //订单准备
		 SellOrderDTO sellOrderdto= orderBaseQueryBO.viewSellOrderDTO(sellOrderDTO);
		 orderReadyDTO.setProcessEntityId(sellOrderdto.getProcessEntityId());
		 orderReadyDTO.setDefaultEntityId(sellOrderInputDTO.getDefaultEntityId());
		 orderReadyDTO.setLoginUserId(sellOrder.getLoginUserId());
		 orderReadyDTO.setProductId(sellerProductContractDTO.getProductId());
		 orderReadyDTO.setOrderId(sellOrderInputDTO.getSellOrderDTO().getOrderId());
		 orderReadyDTO.setOrderType("10000011");
		 
		 if(typeKH == 20170614){
			 orderReadyDTO.setStartCardNo(importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getCardLayoutId());//sellOrderInputDTO-sellOrderDTO
			 orderReadyDTO.setEndCardNo(importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getCardLayoutId());
		 } 
		 
		 	PageDataDTO pageDataDto = orderBaseQueryBO.getCardForSellOrderReady(orderReadyDTO);
		 	List<HashMap<String, String>> lstStockList = (List<HashMap<String, String>>) pageDataDto.getData();
			if(lstStockList.size()==0){
				throw new BizServiceException("客户："+customerDTO.getCustomerName()+"订单准备时，产品："+productDTO.getProductName()+",库存不足！导入中断！");
			}
			String[] cardNoArray=new String[]{lstStockList.get(0).get("cardNo")};
			orderReadyDTO.setCardNoArray(cardNoArray);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BizServiceException("订单："+orderReadyDTO.getOrderId()+",订单准备时系统异常！导入中断！");
			}
			try {
				orderReadyService.cardReady(orderReadyDTO);
				 //订单准备，提交订单
				submitOrderService.submitOrderAtReady(sellOrderDTO);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new BizServiceException("售卡订单："+sellOrderDTO.getOrderId()+",订单准备失败！导入中断！");
			}
		
		 
		 //订单配送确定
		 SellOrderDTO sellOrderDto=new SellOrderDTO();
		 sellOrderDto.setOperationMemo("");
		 sellOrderDto.setDefaultEntityId(sellOrderDTO.getDefaultEntityId());
		 sellOrderDto.setDeliveryPoint(sellOrderDTO.getDeliveryPoint());
		 sellOrderDto.setFaceValueType(sellOrderDTO.getFaceValueType());
		 sellOrderDto.setLoginUserId(sellOrderDTO.getLoginUserId());
		 sellOrderDto.setOrderContact(sellOrderDTO.getOrderContact());
		 sellOrderDto.setOrderId(sellOrderDTO.getOrderId());
		 sellOrderDto.setOrderType(sellOrderDTO.getOrderType());
		 sellOrderDto.setPaymentState(sellOrderDTO.getPaymentState());
		 sellOrderDto.setPaymentTerm(sellOrderDTO.getPaymentTerm());
		 sellOrderDto.setPerFlag(sellOrderDTO.getPerFlag());
		 try {
			 submitOrderService.submitOrderAtHandOut(sellOrderDto);
		 sellOrderDto.setBatchNo("");
		 sellOrderDto.setInitActStat("0");
		 sellOrderDto.setOrderState("31");
		 sellOrderDto.setTotalPrice(String.valueOf(totalPrice));
		 
			 orderSendConfirmActionImpl.submitOrderAtSendConfirm(sellOrderDto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BizServiceException("售卡订单："+sellOrderDto.getOrderId()+",订单配送确定失败！导入中断！");
		}
		
		 /*SellOrder sellOrderByOrderState=sellOrderDAO.selectByPrimaryKey(sellOrderDto.getOrderId());
		 if(!sellOrderByOrderState.getOrderState().equals("33")){
			 throw new BizServiceException("订单："+sellOrderDto.getOrderId()+",订单配送确定未成功！导入中断！");
		 }*/
		 //如果总金额不是0，执行付款确认，付款审核
		 if(Float.parseFloat(sellOrderDto.getTotalPrice())!=0){
			 //付款确认
			 SellOrderPaymentDTO sellOrderPaymentDTO=new SellOrderPaymentDTO();
			 sellOrderPaymentDTO.setDefaultEntityId(sellerContractDTO.getDefaultEntityId());
			 sellOrderPaymentDTO.setLoginUserId(sellOrderDto.getLoginUserId());
			 sellOrderPaymentDTO.setOrderId(sellOrderDto.getOrderId());
			 sellOrderPaymentDTO.setPaymentAmount(sellOrderDto.getTotalPrice());
			 sellOrderPaymentDTO.setPaymentType("1");
			 //添加订单付款方式
			 try {
				 sellOrderPaymentService.insertOrderPayment(sellOrderPaymentDTO);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new BizServiceException("售卡订单："+sellOrderDto.getOrderId()+",添加订单付款方式失败！导入中断！");
			}
			 
			 //付款确认提交订单
			 SellOrderDTO dto=new SellOrderDTO();
			 dto.setDefaultEntityId(sellOrderDto.getDefaultEntityId());
			 dto.setLoginUserId(sellOrderDto.getLoginUserId());
			 dto.setOrderId(sellOrderDto.getOrderId());
			 dto.setOrderState("32");
			 dto.setOrderType(sellOrderDto.getOrderType());
			 dto.setPaymentState("2");
			 dto.setPaymentTerm("2");
			 try {
				 submitOrderService.confirmAtOrderPayment(dto);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new BizServiceException("售卡订单："+dto.getOrderId()+",订单付款确认提交失败！导入中断！");
			}
			 
			 dto.setInitActStat("0");
			 //付款审核
			 try {
				 orderPaymentActionImpl.submitOrderForPayment(dto);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new BizServiceException("售卡订单："+dto.getOrderId()+",订单付款审核失败！导入中断！");
			}
		 }
		
	}


	
	
	/*客户信息及不记名卡批量导入*/
	@Override
	synchronized public void batchInsertUnsignOrder(ImportServiceDTO importServiceDTO) throws BizServiceException{
		
		String defaultEntityId=importServiceDTO.getDefaultEntityId();
		String customerId=importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getFirstEntityId();
		String loginUserId=importServiceDTO.getLoginUserId();
		String productId=importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getProductId();
		String packageId=importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getPackageId();
		CustomerKey key = new CustomerKey();
		key.setEntityId(customerId);
		key.setFatherEntityId(defaultEntityId);
		Customer customer = customerDAO.selectByPrimaryKey(key);
		String customerType = "";
		if (customer != null) {
			customerType = customer.getCustomerType();
		} else {
			throw new BizServiceException("查询客户失败!");
		}
		//校验库存
		EntityStockDTO entityStockDTO = new EntityStockDTO();
		entityStockDTO.setQueryAll(true);
		entityStockDTO.setEntityId(defaultEntityId);
		entityStockDTO.setProductId(productId);
		entityStockDTO.setFunctionRoleId("3");
		
		try {
			PageDataDTO pageDataDto=orderBaseQueryBO
					.getProductStockByDTOForUnsignOrder(entityStockDTO);
			List<Map<String, String>> entityStocList =(List<Map<String, String>>) pageDataDto.getData();
			for (int i = 0; i < entityStocList.size(); i++) {
				
				for (int j = 0; j < importServiceDTO.getOrderListInfo().length; j++){
					String info=importServiceDTO.getOrderListInfo()[j];
					String [] infoDetail=info.split(",");
					//页面传过来的面额
					String faceValue=infoDetail[0];
					//库存查询的面额
					Object faceV=entityStocList.get(i).get("faceValue");
					
					if(String.valueOf(faceV).equals(faceValue)){
						//库存数
						Object count=entityStocList.get(i).get("countRecord");
						int countRecord=Integer.parseInt(String.valueOf(count));
						//售卡数
						int cardNum=Integer.parseInt(infoDetail[2]);
						if(cardNum>countRecord){
							throw new BizServiceException("面额:"+faceValue+",库存不足！");
						}
					}

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizServiceException("查询库存失败！");
		}
		
		
		/*int sum=0;
		for (int i = 0; i < importServiceDTO.getOrderListInfo().length; i++) {
			String info=importServiceDTO.getOrderListInfo()[i];
			String [] infoDetail=info.split(",");
			String faceValue=infoDetail[0];
			String cardSum=infoDetail[2];
			sum+=Integer.parseInt(faceValue)*Integer.parseInt(cardSum);
		}*/
		
		 //销售不记名卡订单
		 SellOrderInputDTO sellOrderInputDTO=importServiceDTO.getSellOrderInputDTO();
		 SellOrderDTO sellOrderDTO=sellOrderInputDTO.getSellOrderDTO();
		 sellOrderDTO.setAdditionalFee("0");//默认附加费为0
		 sellOrderDTO.setCardQuantity("0");//订单张数
		 sellOrderDTO.setDeliveryFee("0");//送货费用
		 sellOrderDTO.setDeliveryMeans("1");//送货方式
		 sellOrderDTO.setDiscountFee("0");//折扣费
		 sellOrderDTO.setFirstEntityId(customerId);//客户号
		 sellOrderDTO.setProcessEntityId(defaultEntityId);//营销机构
		if (customerType.equals("1")) {
			sellOrderDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN);// 订单类型
		}
		if (customerType.equals("0")) {
			sellOrderDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN);// 订单类型
		}
		 sellOrderDTO.setSaleMan(loginUserId);//销售人员
		 sellOrderDTO.setPayChannel("1");//支付渠道
		 sellOrderDTO.setPackageFee("0");//包装费
		 sellOrderDTO.setPackageId(packageId);//包装ID
		 sellOrderDTO.setProductId(productId);//产品ID
		 sellOrderDTO.setOrderSource("1");//订单来源
		 sellOrderDTO.setOrderState("1");//订单状态
		 sellOrderDTO.setPaymentDelay("0");//付款延期天数
		 sellOrderDTO.setPaymentState("0");//订单状态为未支付
		 sellOrderDTO.setPaymentTerm("2");//支付节点
		 sellOrderDTO.setPerFlag("per");
		 sellOrderDTO.setServiceId(importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getServiceId());//充值账户
		 sellOrderDTO.setServiceName(importServiceDTO.getSellOrderInputDTO().getSellOrderDTO().getServiceName());//充值账户名称
		 sellOrderDTO.setCardIssueFee("0");//卡费
		 sellOrderDTO.setAnnualFee("0");//年费	
		 sellOrderDTO.setModifyUser(loginUserId);//修改订单用户
		 sellOrderDTO.setCreateUser(loginUserId);//创建订单用户
		 sellOrderDTO.setLoginUserId(loginUserId);
		 sellOrderDTO.setOrderDate(getDateFormat(new Date()));
		 sellOrderDTO.setInvoiceDate(getDateFormat(new Date()));
		 sellOrderInputDTO.setDefaultEntityId(defaultEntityId);
		 sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
		 sellOrderInputDTO=orderService.initAdd(sellOrderInputDTO);
		 sellOrderDTO=sellOrderInputDTO.getSellOrderDTO();
		 sellOrderDTO.setTotalPrice("0");
		 sellOrderDTO.setInitActStat("1");
		 for (int i = 0; i < sellOrderInputDTO.getCustomerDTO().getDeliveryPointList().size(); i++) {
			if( sellOrderInputDTO.getCustomerDTO().getDeliveryPointList().get(i).getEntityId().equals(customerId)){
				//快递地址
				sellOrderDTO.setDeliveryPoint(sellOrderInputDTO.getCustomerDTO().getDeliveryPointList().get(i).getDeliveryId());
				for (int j = 0; j < sellOrderInputDTO.getCustomerDTO().getDeliveryPointList().get(i).getRecipientList().size(); j++) {
					if(sellOrderInputDTO.getCustomerDTO().getDeliveryPointList().get(j).getDeliveryId().equals(sellOrderDTO.getDeliveryPoint())){
						//收件人
						sellOrderDTO.setOrderContact(sellOrderInputDTO.getCustomerDTO().getDeliveryPointList().get(i).getRecipientList().get(j).getDeliveryContactId());
					}
				}
			}
		}
		 sellOrderDTO.setContactId(sellOrderInputDTO.getCustomerDTO().getContractList().get(0).getContactId());//经办人ID
		 
		 try {
			 sellOrderInputDTO=orderService.orderInsert(sellOrderDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BizServiceException("添加售卡订单失败！导入中断！");
		}
		
		 
		 	
		 //添加订单明细
		 
		 SellOrderListDTO sellOrderListDTO=new SellOrderListDTO();
		 for (int i = 0; i < importServiceDTO.getOrderListInfo().length; i++) {
				String info=importServiceDTO.getOrderListInfo()[i];
				String [] infoDetail=info.split(",");
				String faceValue=infoDetail[0];
				String layoutId=infoDetail[1];
				String cardSum=infoDetail[2];
				sellOrderListDTO.setCardAmount(cardSum);
			    //卡面
			    sellOrderListDTO.setCardLayoutId(layoutId);
			    //机构号
			    sellOrderListDTO.setDefaultEntityId(defaultEntityId);
			    //面额
			    sellOrderListDTO.setFaceValue(faceValue);
			    //固定面额
			    sellOrderListDTO.setFaceValueType("0");
			    
			    sellOrderListDTO.setLoginUserId(loginUserId);
			    //订单号
			    sellOrderListDTO.setOrderId(sellOrderInputDTO.getSellOrderDTO().getOrderId());
			    //总金额
			    sellOrderListDTO.setTotalPrice("0");
			    orderService.insertOrderListForSellOrderUnsign(sellOrderListDTO);
			}
		
		
		 
		
		
		 //提交订单
		 String [] ecChoose=new String[]{sellOrderInputDTO.getSellOrderDTO().getOrderId()};
		 sellOrderInputDTO.setEc_choose(ecChoose);
		 sellOrderInputDTO.setLoginUserId(loginUserId);
		 try {
			 submitOrderService.submitOrderAtInput(sellOrderInputDTO);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BizServiceException("售卡订单："+sellOrderInputDTO.getSellOrderDTO().getOrderId()+",提交订单失败！导入中断！");
		}
		 //订单审核
		 SellOrderDTO sellOrder= sellOrderInputDTO.getSellOrderDTO();
		 sellOrder.setLoginUserId(sellOrderInputDTO.getLoginUserId());
		 try {
			 submitOrderService.submitOrderAtConfirm(sellOrder);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BizServiceException("售卡订单："+sellOrder.getOrderId()+",订单审核失败！导入中断！");
		}
		 
		 try {
			 //查询订单明细
			 SellOrderListExample ex  =new SellOrderListExample();
			 ex.createCriteria().andOrderIdEqualTo(sellOrder.getOrderId());
			 List<SellOrderList>  orderList=sellOrderListDAO.selectByExample(ex);
			 SellOrderDTO sellOrderdto= orderBaseQueryBO.viewSellOrderDTO(sellOrderDTO);
		 for (int i = 0; i < orderList.size(); i++) {
			
	
		 //订单准备
				OrderReadyDTO orderReadyDTO = new OrderReadyDTO();
				orderReadyDTO.setProcessEntityId(sellOrderdto.getProcessEntityId());
				orderReadyDTO.setDefaultEntityId(sellOrderInputDTO.getDefaultEntityId());
				orderReadyDTO.setLoginUserId(sellOrder.getLoginUserId());
				orderReadyDTO.setProductId(productId);
				orderReadyDTO.setOrderId(sellOrderInputDTO.getSellOrderDTO().getOrderId());
				if (customerType.equals("1")) {
					sellOrderDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_PERSON_UNSIGN);// 订单类型
				}
				if (customerType.equals("0")) {
					sellOrderDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_SELL_SELLER_CUSTOMER_UNSIGN);// 订单类型
				}
				orderReadyDTO.setOrderListId(orderList.get(i).getOrderListId());
				PageDataDTO pageDataDto = null;
				try {
					pageDataDto = orderBaseQueryBO.getCardForSellOrderReady(orderReadyDTO);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BizServiceException("订单：" + orderReadyDTO.getOrderId() + ",订单准备时系统异常！导入中断！");
				}
				List<HashMap<String, String>> lstStockList = (List<HashMap<String, String>>) pageDataDto.getData();
				/*
				 * if(lstStockList.size()==0){ throw new
				 * BizServiceException("订单准备时,库存不足！导入中断！"); }
				 */
				orderReadyDTO.setStartCardNo(lstStockList.get(0).get("min"));
				orderReadyDTO.setEndCardNo(lstStockList.get(0).get("max"));

				orderReadyService.cardSegmentReady(orderReadyDTO);
				// orderReadyService.cardReady(orderReadyDTO);
			}
			
		 
		} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new BizServiceException("订单："+sellOrder.getOrderId()+",订单准备时系统异常！导入中断！");
			}
			try {
				
				//订单准备，提交订单
				submitOrderService.submitOrderAtReady(sellOrderDTO);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new BizServiceException("售卡订单："+sellOrderDTO.getOrderId()+",订单准备提交订单失败！导入中断！");
			}
		
		 
		// 订单配送确定
		SellOrderDTO sellOrderDto = new SellOrderDTO();
		sellOrderDto.setOperationMemo("");
		sellOrderDto.setDefaultEntityId(sellOrderDTO.getDefaultEntityId());
		sellOrderDto.setDeliveryPoint(sellOrderDTO.getDeliveryPoint());
		sellOrderDto.setFaceValueType(sellOrderDTO.getFaceValueType());
		sellOrderDto.setLoginUserId(sellOrderDTO.getLoginUserId());
		sellOrderDto.setOrderContact(sellOrderDTO.getOrderContact());
		sellOrderDto.setOrderId(sellOrderDTO.getOrderId());
		sellOrderDto.setOrderType(sellOrderDTO.getOrderType());
		sellOrderDto.setPaymentState(sellOrderDTO.getPaymentState());
		sellOrderDto.setPaymentTerm(sellOrderDTO.getPaymentTerm());
		sellOrderDto.setPerFlag(sellOrderDTO.getPerFlag());
		try {
			submitOrderService.submitOrderAtHandOut(sellOrderDto);
			sellOrderDto.setInitActStat("1");
			sellOrderDto.setOrderState("31");
			sellOrderDto.setTotalPrice("0");
		 
			 orderSendConfirmActionImpl.submitOrderAtSendConfirm(sellOrderDto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BizServiceException("售卡订单："+sellOrderDto.getOrderId()+",订单配送确定失败！导入中断！");
		}
		
		 /*SellOrder sellOrderByOrderState=sellOrderDAO.selectByPrimaryKey(sellOrderDto.getOrderId());
		 if(!sellOrderByOrderState.getOrderState().equals("33")){
			 throw new BizServiceException("订单："+sellOrderDto.getOrderId()+",订单配送确定未成功！导入中断！");
		 }*/
			 //付款确认
			 SellOrderPaymentDTO sellOrderPaymentDTO=new SellOrderPaymentDTO();
			 sellOrderPaymentDTO.setDefaultEntityId(defaultEntityId);
			 sellOrderPaymentDTO.setLoginUserId(sellOrderDto.getLoginUserId());
			 sellOrderPaymentDTO.setOrderId(sellOrderDto.getOrderId());
			 sellOrderPaymentDTO.setPaymentAmount("0");
			 sellOrderPaymentDTO.setPaymentType("1");
			 //添加订单付款方式
			 try {
				 sellOrderPaymentService.insertOrderPayment(sellOrderPaymentDTO);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new BizServiceException("售卡订单："+sellOrderDto.getOrderId()+",添加订单付款方式失败！导入中断！");
			}
			 
			 //付款确认提交订单
			 SellOrderDTO dto=new SellOrderDTO();
			 dto.setDefaultEntityId(sellOrderDto.getDefaultEntityId());
			 dto.setLoginUserId(sellOrderDto.getLoginUserId());
			 dto.setOrderId(sellOrderDto.getOrderId());
			 dto.setOrderState("32");
			 dto.setOrderType(sellOrderDto.getOrderType());
			 dto.setPaymentState("2");
			 dto.setPaymentTerm("2");
			 try {
				 submitOrderService.confirmAtOrderPayment(dto);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new BizServiceException("售卡订单："+dto.getOrderId()+",订单付款确认提交失败！导入中断！");
			}
			 
			 dto.setInitActStat("0");
			 //付款审核
			 try {
				 orderPaymentActionImpl.submitOrderForPayment(dto);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new BizServiceException("售卡订单："+dto.getOrderId()+",订单付款审核失败！导入中断！");
			}
			
			 
			 
		
	}
	
	
	public String getDateFormat(Date date) {
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String returnValue = null;
        try {
         returnValue = simpleDateFormat.format(date);
        } catch (Exception e) {

        }
        return returnValue;
    }
	
	/*客户信息及持卡人信息批量导入*/
	public void batchInsertCustomerAndCardHolder(CustomerDTO customerDTO,CardholderDTO cardholderDTO) throws BizServiceException{
		/*查询看客户是否存在，如果存在则更新为最新的信息，不存在则插入新信息*/
		List<CustomerDTO> customerByIdNo = customerService.getCustomerByIdNo(customerDTO);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String modifyTime = format.format(new Date());
		if(customerByIdNo!=null&&customerByIdNo.size()>0){
			
			/*更新客户信息*/
			
			Customer  customer = new Customer();
			ReflectionUtil.copyProperties(customerDTO, customer);
			customer.setEntityId(customerByIdNo.get(0).getEntityId());
			customer.setFatherEntityId(customerByIdNo.get(0).getFatherEntityId());
			customer.setModifyUser(customerDTO.getModifyUser());
			customer.setModifyTime(modifyTime);
			cardholderDTO.setEntityId(customer.getEntityId());
			customerDTO.setEntityId(customer.getEntityId());
			customerDAO.updateByPrimaryKeySelective(customer);
//			/*更新持卡人信息*/
//			Cardholder cardholder = new Cardholder();
//			ReflectionUtil.copyProperties(cardholderDTO, cardholder);
//			cardholder.setModifyTime(modifyTime);
//			cardholder.setModifyUser(customerDTO.getModifyUser());
//			CardholderExample  cardholderExample = new CardholderExample();
//			cardholderExample.createCriteria().andIdTypeEqualTo(cardholderDTO.getIdType()).andIdNoEqualTo(cardholderDTO.getIdNo());
//			//int count = cardholderDAO.countByExample(cardholderExample);
//			List<Cardholder> cardholderList=cardholderDAO.selectByExample(cardholderExample);
//			if(cardholderList.size()>0){
//				cardholder.setEntityId(customerByIdNo.get(0).getEntityId());
//				cardholderDAO.updateByExampleSelective(cardholder, cardholderExample);
//				SellOrderCardListExample sellOrderCardListExample=new SellOrderCardListExample();
//				sellOrderCardListExample.createCriteria().andCardholderIdEqualTo(cardholderList.get(0).getCardholderId());
//				//查询订单中有没有存在当前持卡人，如果存在，修改FirstName
//				List<SellOrderCardList> sellOrderCardList=sellOrderCardListDAO.selectByExample(sellOrderCardListExample);
//				for (int i = 0; i < sellOrderCardList.size(); i++) {
//					SellOrderCardList sellOrderCardListDTO=new SellOrderCardList();
//					sellOrderCardListDTO.setCardholderId(sellOrderCardList.get(i).getCardholderId());
//					sellOrderCardListDTO.setOrderCardListId(sellOrderCardList.get(i).getOrderCardListId());
//					sellOrderCardListDTO.setOrderId(sellOrderCardList.get(i).getOrderId());
//					sellOrderCardListDTO.setFirstName(cardholder.getFirstName());
//					sellOrderCardListDAO.updateByPrimaryKeySelective(sellOrderCardListDTO);
//				}
//				
//			}else{
//				cardholderDTO.setEntityId(customerByIdNo.get(0).getEntityId());
//				cardholderService.insert(cardholderDTO);
//			}
			
			/*更新快递点信息*/
			EntityDelivery entityDelivery = new EntityDelivery();
			entityDelivery.setDeliveryAddress(customerDTO.getCustomerAddress());
			entityDelivery.setDeliveryName(customerDTO.getCustomerName());
			entityDelivery.setDeliveryPostcode(customerDTO.getCustomerPostcode());
			entityDelivery.setModifyTime(modifyTime);
			entityDelivery.setModifyUser(customerDTO.getModifyUser());
			EntityDeliveryExample entityDeliveryExample = new EntityDeliveryExample();
			entityDeliveryExample.createCriteria().andEntityIdEqualTo(customerByIdNo.get(0).getEntityId())
											.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
											.andDefaultFlagEqualTo(DataBaseConstant.DEFAULT_FLAG_YES);
			deliveryDAO.updateByExampleSelective(entityDelivery, entityDeliveryExample);
			/*更新默认快递点联系人信息*/
			List<EntityDelivery> example = deliveryDAO.selectByExample(entityDeliveryExample);
			if(example!=null&&example.size()>0){
				DeliveryContactExample deliveryContactExample = new DeliveryContactExample();
				deliveryContactExample.createCriteria().andDefaultFlagEqualTo(DataBaseConstant.DEFAULT_FLAG_YES)
																			.andDataStateEqualTo(DataBaseConstant.DATA_STATE_NORMAL)
																			.andDeliveryPointIdEqualTo(example.get(0).getDeliveryId());
				DeliveryContact deliveryContact = new DeliveryContact();
				deliveryContact.setContactPhone(customerDTO.getCustomerTelephone());
				deliveryContact.setModifyTime(modifyTime);
				deliveryContact.setModifyUser(customerDTO.getModifyUser());
				deliveryContact.setDeliveryContact(customerDTO.getCustomerName());
				deliveryContractDAO.updateByExampleSelective(deliveryContact, deliveryContactExample);
			}
			
			// 更新默认联系人信息
			List<ContactDTO> ContactDTOs = contactService.inqueryContact(customerByIdNo.get(0).getEntityId());
			for(ContactDTO contactDTO : ContactDTOs){
				if(contactDTO.getDataState().trim().equals("1")&&contactDTO.getDefaultFlag().trim().equals("1")){
					contactDTO.setContactName(customer.getCustomerName());
					contactDTO.setContactType(customer.getCustomerType());
					contactDTO.setContactGender(cardholderDTO.getCardholderGender());
					contactDTO.setContactMobilePhone(customer.getCustomerTelephone());
					contactDTO.setModifyUser(customerDTO.getModifyUser());
					contactDTO.setModifyTime(modifyTime);
					contactDTO.setPapersNo(customerDTO.getCorpCredId());
					contactDTO.setPapersType(customerDTO.getCorpCredType());
					contactService.updateContact(contactDTO);
				}
			}
			
			// 更新默认发票地址信息
			List<InvoiceAddressDTO> invoiceAddressDTOs= invoiceAddressService.inquery(customerByIdNo.get(0).getEntityId());
			for(InvoiceAddressDTO invoiceAddressDTO : invoiceAddressDTOs ){
				if(invoiceAddressDTO.getDataState().trim().equals("1")&&invoiceAddressDTO.getDefaultFlag().trim().equals("1")){
					InvoiceAddressDTO invoiceAddress = new InvoiceAddressDTO();
					ReflectionUtil.copyProperties(invoiceAddressDTO, invoiceAddress);
					
					invoiceAddress.setInvoiceAddress(customer.getCustomerAddress());
					invoiceAddress.setAddressPostcode(customer.getCustomerPostcode());
					invoiceAddress.setModifyUser(customerDTO.getModifyUser());
					invoiceAddress.setModifyTime(modifyTime);
					invoiceAddress.setInvoiceRecipient(customer.getCustomerName());
					invoiceAddressService.updateByPrimaryKey(invoiceAddress);
				}
			}
			

			// 更新默认发票公司信息
			List<InvoiceCompany> invoiceCompanys= invoiceCompanyService.selectByEntityId(customerByIdNo.get(0).getEntityId());
			for(InvoiceCompany invoiceCompany : invoiceCompanys){
				if(invoiceCompany.getDataState().trim().equals("1")&&invoiceCompany.getDefaultFlag().trim().equals("1")){
					invoiceCompany.setInvoiceCompanyName(customerDTO.getCustomerName());
					invoiceCompany.setModifyTime(modifyTime);
					invoiceCompany.setModifyUser(customerDTO.getModifyUser());
					invoiceCompanyService.updateByPrimaryKey(invoiceCompany);
				}
			}
		}else{
			// format.format(new Date());
			CustomerDTO insertCustomer = customerService.insertCustomer(customerDTO);
			cardholderDTO.setEntityId(insertCustomer.getEntityId());
			customerDTO.setDefaultEntityId(insertCustomer.getFatherEntityId());
			customerDTO.setEntityId(insertCustomer.getEntityId());
//			cardholderService.insert(cardholderDTO);
		}
		//人行附加信息
		int count=selectAttachInfoDTO(customerDTO, "00",customerDTO.getEntityId());
		if(count>0){
			updateAttachInfoDTO(customerDTO, "00",customerDTO.getEntityId());
		}else{
			insertAttachInfoDTO(customerDTO, "00",customerDTO.getEntityId());
		}
		
		
		/*更新持卡人信息*/
		Cardholder cardholder = new Cardholder();
		ReflectionUtil.copyProperties(cardholderDTO, cardholder);
		cardholder.setModifyTime(modifyTime);
		cardholder.setModifyUser(customerDTO.getModifyUser());
		cardholderDTO.setDefaultEntityId(customerDTO.getDefaultEntityId());
		CardholderExample  cardholderExample = new CardholderExample();
		cardholderExample.createCriteria().andIdTypeEqualTo(cardholderDTO.getIdType()).andIdNoEqualTo(cardholderDTO.getIdNo()).andDataStateEqualTo("1");
		//int count = cardholderDAO.countByExample(cardholderExample);
		List<Cardholder> cardholderList=cardholderDAO.selectByExample(cardholderExample);
		if(cardholderList.size()>0){
			cardholder.setEntityId(customerByIdNo.get(0).getEntityId());
			cardholderDAO.updateByExampleSelective(cardholder, cardholderExample);
			SellOrderCardListExample sellOrderCardListExample=new SellOrderCardListExample();
			sellOrderCardListExample.createCriteria().andCardholderIdEqualTo(cardholderList.get(0).getCardholderId());
			//查询订单中有没有存在当前持卡人，如果存在，修改FirstName
			List<SellOrderCardList> sellOrderCardList=sellOrderCardListDAO.selectByExample(sellOrderCardListExample);
			for (int i = 0; i < sellOrderCardList.size(); i++) {
				SellOrderCardList sellOrderCardListDTO=new SellOrderCardList();
				sellOrderCardListDTO.setCardholderId(sellOrderCardList.get(i).getCardholderId());
				sellOrderCardListDTO.setOrderCardListId(sellOrderCardList.get(i).getOrderCardListId());
				sellOrderCardListDTO.setOrderId(sellOrderCardList.get(i).getOrderId());
				sellOrderCardListDTO.setFirstName(cardholder.getFirstName());
				sellOrderCardListDAO.updateByPrimaryKeySelective(sellOrderCardListDTO);
			}
			
			
			
		}else{
			//cardholderDTO.setEntityId(cardholderDTO.getEntityId());
			cardholderService.insert(cardholderDTO);
		}
		//人行附加信息
		List<Cardholder> cardholders=cardholderDAO.selectByExample(cardholderExample);
		//人行附加信息
		//持卡人id
		int counts=selectAttachInfoDTO(customerDTO, "01",cardholders.get(0).getCardholderId());
		if(counts>0){
			updateAttachInfoDTO(customerDTO, "01",cardholders.get(0).getCardholderId());
		}else{
			insertAttachInfoDTO(customerDTO, "01",cardholders.get(0).getCardholderId());
		}
	}



	@Override
	public void batchInsertCustomerAndCardHolderFromOnline(ApplyAndBindCardDTO applyAndBindCardDTO)
			throws BizServiceException {
		ApplyAndBindCardDTO applyAndBindCard = null;
		try {
			applyAndBindCard = applyAndBindCardDao.getApplyAndBindCard("ApplyAndBindCard.SinglePersonMessage",applyAndBindCardDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw  new BizServiceException("查询卡申请信息错！");
		}
		if(applyAndBindCardDTO==null){
			throw  new BizServiceException("查询卡申请信息错！");
		}
		CustomerDTO customerDTO = new CustomerDTO();
		CardholderDTO  cardholderDTO = new CardholderDTO();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String modifyTime= format.format(new Date());
		/*机构号*/
		customerDTO.setSalesmanId(applyAndBindCardDTO.getLoginUserId());
		customerDTO.setFatherEntityId(applyAndBindCardDTO.getEntityId());
		customerDTO.setDefaultEntityId(applyAndBindCardDTO.getEntityId());
		customerDTO.setCreateUser(applyAndBindCardDTO.getLoginUserId());
		customerDTO.setLoginUserId(applyAndBindCardDTO.getLoginUserId());
		// customerDTO.setCreateTime(modifyTime);
		customerDTO.setModifyTime(modifyTime);
		customerDTO.setModifyUser(applyAndBindCardDTO.getLoginUserId());
		cardholderDTO.setLoginUserId(applyAndBindCardDTO.getLoginUserId());
		/*姓名*/
		customerDTO.setCustomerName(applyAndBindCard.getRecipient_name());
		cardholderDTO.setFirstName(applyAndBindCard.getFirstName());
		/*性别*/
		if(applyAndBindCard.getCardholderGender()!=null&&applyAndBindCard.getCardholderGender().equals("0")){
			cardholderDTO.setCardholderGender("2");
			cardholderDTO.setCardholderSalutation("3");
		}else{
			cardholderDTO.setCardholderGender(applyAndBindCard.getCardholderGender());
			cardholderDTO.setCardholderSalutation("1");
		}
		
		/*电话*/
		customerDTO.setCustomerTelephone(applyAndBindCard.getRecipient_phone());
		cardholderDTO.setCardholderMobile(applyAndBindCard.getCardholderMobile());
		/*地址*/
		customerDTO.setCustomerAddress(applyAndBindCard.getRecipient_addr());
		/*证件类型*/
		customerDTO.setCorpCredType(applyAndBindCard.getIdType());
		cardholderDTO.setIdType(applyAndBindCard.getIdType());
		/*证件号*/
		customerDTO.setCorpCredId(applyAndBindCard.getIdNo());
		cardholderDTO.setIdNo(applyAndBindCard.getIdNo());
		/*生日*/
		cardholderDTO.setCardholderBirthday(applyAndBindCard.getCardholderBirthday());
		/*邮寄地址*/
		cardholderDTO.setMailingAddress(applyAndBindCard.getRecipient_addr());
		/*车牌号*/
		cardholderDTO.setPlateNumber(applyAndBindCard.getPlateNumber());
		/*车架号*/
		cardholderDTO.setV_Id(applyAndBindCard.getVId());
		/*驾驶证号*/
		cardholderDTO.setDriverLicence(applyAndBindCard.getDriverLicence());
		/*邮箱地址*/
		cardholderDTO.setCardholderEmail(applyAndBindCard.getCardholderEmail());
		/*持卡人分类*/
		//cardholderDTO.setCardholderSegment(cardholderMessage[11]);
		/*渠道*/
		customerDTO.setChannel("1");
		/*邮编*/
		customerDTO.setCustomerPostcode(applyAndBindCard.getPost_code());
		/*客户的其他信息都填写为默认*/
		customerDTO.setActivitySector("2");
		customerDTO.setAwareness("1");
		customerDTO.setCorpActionAtLaw("2");
		customerDTO.setCreditStatus("0");
		customerDTO.setCusState("4");
		customerDTO.setCustomerType("1");
		customerDTO.setHasDm("1");
		customerDTO.setPaymentTerm("2");
		customerDTO.setPictureSave("2");
		customerDTO.setPunishRecordFlag("2");
		customerDTO.setPunishRecordInfo("");
		customerDTO.setSalesRegionId("1");
		/*持卡人信息默认字段*/
		cardholderDTO.setCardholderFunction("1");
		cardholderDTO.setCardholderSegment("0");
		cardholderDTO.setCardholderState("1");
		this.batchInsertCustomerAndCardHolder(customerDTO, cardholderDTO);
	}



	public SellerContractService getSellerContractService() {
		return sellerContractService;
	}



	public void setSellerContractService(SellerContractService sellerContractService) {
		this.sellerContractService = sellerContractService;
	}



	public SellerProductContractService getSellerProductContractService() {
		return sellerProductContractService;
	}



	public void setSellerProductContractService(
			SellerProductContractService sellerProductContractService) {
		this.sellerProductContractService = sellerProductContractService;
	}



	public ProductService getProductService() {
		return productService;
	}



	public void setProductService(ProductService productService) {
		this.productService = productService;
	}



	public SellContractDAO getSellerContractDAO() {
		return sellerContractDAO;
	}



	public void setSellerContractDAO(SellContractDAO sellerContractDAO) {
		this.sellerContractDAO = sellerContractDAO;
	}



	public OrderService getOrderService() {
		return orderService;
	}



	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}



	public SubmitOrderService getSubmitOrderService() {
		return submitOrderService;
	}



	public void setSubmitOrderService(SubmitOrderService submitOrderService) {
		this.submitOrderService = submitOrderService;
	}



	public OrderBaseQueryBO getOrderBaseQueryBO() {
		return orderBaseQueryBO;
	}



	public void setOrderBaseQueryBO(OrderBaseQueryBO orderBaseQueryBO) {
		this.orderBaseQueryBO = orderBaseQueryBO;
	}



	public OrderReadyService getOrderReadyService() {
		return orderReadyService;
	}



	public void setOrderReadyService(OrderReadyService orderReadyService) {
		this.orderReadyService = orderReadyService;
	}



	public StockCardService getStockCardService() {
		return stockCardService;
	}



	public void setStockCardService(StockCardService stockCardService) {
		this.stockCardService = stockCardService;
	}
	
	
	public OrderSendConfimActionInterface getOrderSendConfirmActionImpl() {
		return orderSendConfirmActionImpl;
	}



	public void setOrderSendConfirmActionImpl(
			OrderSendConfimActionInterface orderSendConfirmActionImpl) {
		this.orderSendConfirmActionImpl = orderSendConfirmActionImpl;
	}



	public BatchService getBatchService() {
		return batchService;
	}



	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}



	public SellOrderPaymentService getSellOrderPaymentService() {
		return sellOrderPaymentService;
	}



	public void setSellOrderPaymentService(
			SellOrderPaymentService sellOrderPaymentService) {
		this.sellOrderPaymentService = sellOrderPaymentService;
	}



	public SellOrderDAO getSellOrderDAO() {
		return sellOrderDAO;
	}



	public void setSellOrderDAO(SellOrderDAO sellOrderDAO) {
		this.sellOrderDAO = sellOrderDAO;
	}



	public OrderPaymentActionImpl getOrderPaymentActionImpl() {
		return orderPaymentActionImpl;
	}


	

	public SellOrderListDAO getSellOrderListDAO() {
		return sellOrderListDAO;
	}



	public void setSellOrderListDAO(SellOrderListDAO sellOrderListDAO) {
		this.sellOrderListDAO = sellOrderListDAO;
	}



	public void setOrderPaymentActionImpl(
			OrderPaymentActionImpl orderPaymentActionImpl) {
		this.orderPaymentActionImpl = orderPaymentActionImpl;
	}



	public SellProdContractDAO getSellProdContractDAO() {
		return sellProdContractDAO;
	}



	public void setSellProdContractDAO(SellProdContractDAO sellProdContractDAO) {
		this.sellProdContractDAO = sellProdContractDAO;
	}



	
	
	
	
	
	
	
	
}
