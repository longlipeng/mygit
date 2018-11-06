package com.huateng.univer.seller.creditorder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.cardmanagement.dto.CardManagementDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderListDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.StringUtils;
import com.huateng.univer.seller.sellorder.OrderBaseAction;

public class CreditOrderAction extends OrderBaseAction {

	private Logger logger = Logger.getLogger(CreditOrderAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String maxbalance;

	private String[] cardNoArray;

	private String[] orderCardIdStr;

	private SellOrderListDTO sellOrderListDTO = new SellOrderListDTO();
	private SellOrderCardListDTO sellOrderCardListDTO=new SellOrderCardListDTO();

	// 导入的文件
	private File cardholderFile;
	// 导入的文件名
	private String cardholderFileFileName;
	
	private HSSFWorkbook workbook;
	
	private CardholderDTO cardholderDTO=new CardholderDTO();
	
	private CardManagementDTO cardManagementDTO;
    private Integer totalRows = 0;
    private Integer cardInfo_totalRows = 0;
	@Override
	protected void addCondition() {

	}

	@Override
	protected void dealWithSellOrderInputDTO() {
		saleUserList = JSONArray
				.fromObject(sellOrderInputDTO.getSaleUserList());

		productDTOs = sellOrderInputDTO.getProductDTOs();

		ProductDTO productDTO = sellOrderInputDTO.getProductDTO();

		sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
		sellOrderDTO.setProcessEntityName(getUser().getSellerName());

		if (isEmpty(sellOrderDTO.getSaleMan())
				&& sellOrderInputDTO.getSaleUserList().size() != 0) {
			String userId = sellOrderInputDTO.getSaleUserList().get(0)
					.getUserId();
			sellOrderDTO.setSaleMan(userId);
		}

		if (productDTO != null && productDTO.getProductId() != null
				&& !"".equals(productDTO.getProductId())) {
			/**
			 * 服务列表
			 */
			services = productDTO.getServices();
			productName = productDTO.getProductName();
			maxbalance = productDTO.getMaxBalance();

		}
		customerDTO = sellOrderInputDTO.getCustomerDTO();

		if (isEmpty(sellOrderDTO.getForecastCreditDate())) {
			sellOrderDTO.setForecastCreditDate(getDateFormat(new Date()));
		}
		if (isEmpty(sellOrderDTO.getValidityPeriod())) {
			sellOrderDTO.setValidityPeriod(getDateFormat(new Date()));
		}

		customerOrderLists = sellOrderDTO.getOrderCardList().getData();
		customerOrderLists_totalRows = sellOrderDTO.getOrderCardList()
				.getTotalRecord();
	}

	@Override
	protected void init() {
		sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
	}

	@Override
	protected void initOrderType() {
		sellOrderDTO
				.setOrderType(OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER);
	}

	@SkipValidation
	public String addCreditOrder() {
		sellerContractQueryDTO.setContractBuyer(getUser().getEntityId());
		List<SellerContractDTO>  sellerContractDTOList= (List<SellerContractDTO>)sendService(ConstCode.CONTRACT_SERVICE_INQUERY_MASTERPALTE,
				 sellerContractQueryDTO).getDetailvo();
		if (null != sellerContractDTOList && sellerContractDTOList.size() > 0) {
			String expiryDate = sellerContractDTOList.get(0).getExpiryDate();
			String sysDate = DateUtil.getCurrentDateStr();
			System.out.println("合同失效日期:" + expiryDate);
			System.out.println("当前系统日期:" + DateUtil.getCurrentDateStr());
			if(Integer.parseInt(sysDate) > Integer.parseInt(expiryDate)) {
				this.addActionError("当前机构与上级发行机构合同已过期!");
				return list();
			}
		}
		add();
		return "add";
	}

	@SkipValidation
	public String addCreditOrderByCustomer() {
		
		add();
		if (null == productDTOs || productDTOs.size() == 0) {
			this.addActionError("合同未关联产品或合同已失效。");
		}
		return "add";
	}

	public String insert() {
		try {
			sellOrderInputDTO = (SellOrderInputDTO) sendService(
					ConstCode.SELL_ORDER_INSERT, sellOrderDTO).getDetailvo();
			if (!this.hasErrors()) {
				dealWithSellOrderInputDTO();
				this.addActionMessage("新增订单成功!");
			} else {
				sellOrderInputDTO = new SellOrderInputDTO();
				addCreditOrder();
				return "add";
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			logger.error(e);
		}
		return "creditOrderEdit";
	}

	/**
	 * 查询出所有卖给该客户的卡 不管是记名还是匿名
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@SkipValidation
	public String addOrderList() {
		try {

			ListPageInit(null, sellOrderCardListQueryDTO); 
			
			sellOrderDTO = (SellOrderDTO)sendService(
					ConstCode.SELL_ORDER_BY_ORDERID,sellOrderDTO.getOrderId())
					.getDetailvo();
			customerDTO = (CustomerDTO)sendService(
					ConstCode.CUSTOMER_SERVICE_BY_ID,sellOrderDTO.getFirstEntityId())
					.getDetailvo(); 
			sellOrderCardListQueryDTO.setOrderId(sellOrderDTO.getOrderId());

			sellOrderCardListQueryDTO.setFirstEntityId(sellOrderDTO
					.getFirstEntityId());

			sellOrderCardListQueryDTO.setProcessEntityId(sellOrderDTO
					.getProcessEntityId());

			sellOrderCardListQueryDTO.setProductId(sellOrderDTO.getProductId());

			sellOrderInputDTO
					.setSellOrderCardListQueryDTO(sellOrderCardListQueryDTO);
			/** 初始默认用户没有勾选非本客户 */
			if (null == sellOrderCardListQueryDTO.getIsCurCustomer()
					|| "".equals(sellOrderCardListQueryDTO.getIsCurCustomer())) {
				sellOrderCardListQueryDTO.setIsCurCustomer("false");
			}
			PageDataDTO pageDataDTO = (PageDataDTO) sendService(
					ConstCode.SELL_ORDER_GET_CUSTOMER_CARD, sellOrderInputDTO)
					.getDetailvo();
			if (pageDataDTO != null && pageDataDTO.getData() != null
					&& pageDataDTO.getData().size() > 0) {
				List<HashMap<String, String>> lstStockList = (List<HashMap<String, String>>) pageDataDTO
						.getData();
				sellOrderCardListQueryDTO.setStartCardNo(lstStockList.get(0).get("min"));
				sellOrderCardListQueryDTO.setEndCardNo(lstStockList.get(0).get("max"));
				getRequest().setAttribute("totalRows",
						pageDataDTO.getTotalRecord());
				getRequest().setAttribute("list", pageDataDTO.getData());
				getRequest().setAttribute("sellOrderDTO", sellOrderDTO);
			} else {
				getRequest().setAttribute("totalRows", 0);
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			logger.error(e);
		}

		return "addOrderList";
	}
	 /**
     * 点击持卡人姓名，查看持卡人信息
     */
	@SuppressWarnings("unchecked")
	@SkipValidation
	  public String checkView() throws Exception {

		 String str=sellOrderDTO.getOrderId();
		 
	      try {
	        	if (null == cardManagementDTO) {
					cardManagementDTO = new CardManagementDTO();
	        	}
	            ListPageInit("cardListTable", sellOrderCardListQueryDTO);
	            if (null != sellOrderCardListQueryDTO.getCardholderId()
	                    && !sellOrderCardListQueryDTO.getCardholderId().equals("")) {
	                cardholderDTO = new CardholderDTO();
	                cardholderDTO.setCardholderId(sellOrderCardListQueryDTO.getCardholderId());
	            }
	            cardholderDTO.setSellOrderCardListQueryDTO(this.sellOrderCardListQueryDTO);
	            cardholderDTO = (CardholderDTO) sendService(ConstCode.CARDHOLDER_SERVICE_VIEW, cardholderDTO).getDetailvo();
	            if (hasActionErrors()) {
	                return addOrderList();
	            }
	            if (null != cardholderDTO.getCardholderBirthday() && !"".equals(cardholderDTO.getCardholderBirthday())) {
	                cardholderDTO.setCardholderBirthday(DateUtil
	                        .dbFormatToDateFormat(cardholderDTO.getCardholderBirthday()));
	            }
	            if (null != cardholderDTO.getCloseDate() && !"".equals(cardholderDTO.getCloseDate())) {
	                cardholderDTO.setCloseDate(DateUtil.dbFormatToDateFormat(cardholderDTO.getCloseDate()));
	            }
	            if (cardholderDTO.getCardholderCardList() != null
	                    && cardholderDTO.getCardholderCardList().getData() != null) {
	                cardInfo_totalRows = cardholderDTO.getCardholderCardList().getTotalRecord();
	            } else {
	                cardInfo_totalRows = 0;
	            }
	        } catch (Exception e) {
	            this.logger.error(e.getMessage());
	        }
	        return "cardholderMessage";
	    }

	@Override
	@SkipValidation
	public String edit() {
		try {
			ListPageInit("customerOrderLists", sellOrderInputDTO
					.getSellOrderCardListQueryDTO());

			ListPageInit(null, sellOrderInputDTO.getSellOrderListQueryDTO());

			sellOrderInputDTO.getSellOrderCardListQueryDTO().setOrderId(
					sellOrderDTO.getOrderId());

			sellOrderInputDTO.getSellOrderCardListQueryDTO().setOrderType(
					sellOrderDTO.getOrderType());

			sellOrderInputDTO.getSellOrderListQueryDTO().setOrderId(
					sellOrderDTO.getOrderId());

			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);

			sellOrderInputDTO = (SellOrderInputDTO) sendService(
					ConstCode.SELL_ORDER_EDIT, sellOrderInputDTO).getDetailvo();

			dealWithSellOrderInputDTO();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "creditOrderEdit";
	}

	@SkipValidation
	public void validateInsertOrderListByCardSegment() {
		error_jsp = "WEB-INF/pages/univer/seller/order/orderinput/sellorder/creditorder/creditOrderListAdd.jsp";
		if (isEmpty(sellOrderCardListQueryDTO.getStartCardNo())) {
			this.addFieldError("sellOrderCardListQueryDTO.startCardNo",
					"起始卡号必须是19位数字");
		}
		if (isEmpty(sellOrderCardListQueryDTO.getEndCardNo())) {
			this.addFieldError("sellOrderCardListQueryDTO.endCardNo",
					"截止卡号必须是19位数字");
		}
		if (this.hasFieldErrors()) {
			addOrderList();
			return;
		}

		if (19 != sellOrderCardListQueryDTO.getStartCardNo().trim().length()) {
			this.addFieldError("sellOrderCardListQueryDTO.startCardNo",
					"起始卡号必须是19位数字");
		}
		if (19 != sellOrderCardListQueryDTO.getEndCardNo().trim().length()) {
			this.addFieldError("sellOrderCardListQueryDTO.endCardNo",
					"截止卡号必须是19位数字");
		}
		if (this.hasFieldErrors()) {
			addOrderList();
			return;
		}
		try {
			Long.parseLong(sellOrderCardListQueryDTO
					.getStartCardNo());
		} catch (NumberFormatException e) {
			this.addFieldError("sellOrderCardListQueryDTO.startCardNo",
					"起始卡号必须是19位数字 ");
		}

		try {
			Long.parseLong(sellOrderCardListQueryDTO.getEndCardNo());
		} catch (NumberFormatException e) {
			this.addFieldError("sellOrderCardListQueryDTO.endCardNo",
					"截止卡号必须是19位数字 ");
		}

		if (this.hasFieldErrors()) {
			addOrderList();
			return;
		}
		if (this.hasFieldErrors()) {
			addOrderList();
			return;
		}
	}

	@SkipValidation
	public String insertOrderListByCardSegment() {
		try {
			sellOrderInputDTO.setCardNoArray(null);
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
			sellOrderInputDTO.setStartCardNo(sellOrderCardListQueryDTO
					.getStartCardNo());
			sellOrderInputDTO.setEndCardNo(sellOrderCardListQueryDTO
					.getEndCardNo());
			sellOrderInputDTO.setIsCurCustomer(sellOrderCardListQueryDTO.getIsCurCustomer());
			sendService(ConstCode.CREDIT_ORDER_INSER_ORDER_CARD_LIST,
					sellOrderInputDTO).getDetailvo();

		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "sucess";
	}

	@SkipValidation
	public String insertOrderList() {
		try {
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
			sellOrderInputDTO.setCardNoArray(cardNoArray);
			sendService(ConstCode.CREDIT_ORDER_INSER_ORDER_CARD_LIST,
					sellOrderInputDTO).getDetailvo();
			if (this.hasErrors()) {
				return addOrderList();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "sucess";
	}

	@SkipValidation
	public String deleteOrderList() {
		try {
			sellOrderListDTO.setOrderListIdStr(orderCardIdStr);
			sellOrderListDTO.setOrderId(sellOrderDTO.getOrderId());
			sendService(ConstCode.CREDIT_ORDER_DELETE_ORDER_CARD_LIST,
					sellOrderListDTO).getDetailvo();
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return edit();
	}

	public void validateInsert() {
		if (this.hasFieldErrors()) {
			addCreditOrder();
			error_jsp = "WEB-INF/pages/univer/seller/order/orderinput/sellorder/creditorder/creditOrderAdd.jsp";
		}
	}

	public void validateUpdate() {
		if (this.hasFieldErrors()) {
			edit();
			error_jsp = "WEB-INF/pages/univer/seller/order/orderinput/sellorder/creditorder/creditOrderEdit.jsp";
		}

	}
	
	@SkipValidation
	public String importFile(){
		return "cardholderImport";
	}
	@SkipValidation
	public String checkImport() throws BizServiceException{
		String result=this.ExcelSheetParser();
		if(hasActionErrors()){
			return "cardholderImport";
		}
		if("".equals(result)){
			sendService(ConstCode.CREDIT_ORDER_INSER_CARDHOLDER_LIST, sellOrderInputDTO);
		}else{
			addActionError(result);
			return "cardholderImport";
		}
		if(hasActionErrors()){
			this.addActionError("添加充值订单明细失败");
			return "cardholderImport";
		}
		return "sucess";
	}
	
	
	@SuppressWarnings("unchecked")
	public String ExcelSheetParser()throws BizServiceException{
		String result="";
		if (null == cardholderDTO.getEntityId() || "".equals(cardholderDTO.getEntityId())) {
			addFieldError("cardholderDTO.entityId", "客户必须选择！");
			result = "客户必须选择！";
			return result;
		}
		if (null == cardholderFile) {
			result = "导入文件不存在！";
			return result;
		}
		if (cardholderFileFileName.isEmpty()) {
			result = "导入文件不能为空！";
			return result;
		}
		if (!".xls".equals(getExtension())) {
			result = "导入文件必须为.xls格式！";
			return result;
		}
		
		sellOrderInputDTO.getSellOrderCardListQueryDTO().setOrderId(
				sellOrderDTO.getOrderId());

		sellOrderInputDTO.getSellOrderCardListQueryDTO().setOrderType(
				OrderConst.ORDER_TYPE_SELL_CUSTOMER_CREDIT_ORDER);

		sellOrderInputDTO.getSellOrderListQueryDTO().setOrderId(
				sellOrderDTO.getOrderId());

		sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);

		sellOrderInputDTO = (SellOrderInputDTO) sendService(
				ConstCode.SELL_ORDER_EDIT, sellOrderInputDTO).getDetailvo();
		if(hasActionErrors()){
			result="查询订单信息失败";
			return result;
		}
		
		try {
			if(!cardholderDTO.getEntityId().equals(sellOrderInputDTO.getCustomerDTO().getEntityId())){
				result="所选客户："+cardholderDTO.getEntityId()+"与订单不符，请检查";
				return result;
			}
			workbook=new HSSFWorkbook(new FileInputStream(cardholderFile));
			//获取指定表
			HSSFSheet sheet=workbook.getSheetAt(0);
			//获取总行数
			int rowCount=sheet.getLastRowNum();
			logger.info("rowCount:"+rowCount);
			if(rowCount<1){
				result="文件数据为空";
				return result;
			}
			//逐行读取数据
			String m="0";
			String money="0";
			int num=0;
			Map<String,SellOrderCardListQueryDTO> map=new HashMap<String,SellOrderCardListQueryDTO>();
			List<SellOrderCardListQueryDTO> sellOrderCardLists=new ArrayList<SellOrderCardListQueryDTO>();
			for(int index=0;index<=rowCount;index++){
				//获取行对象
				HSSFRow row=sheet.getRow(index);				
				if(row!=null){
					//获取本行单元格个数
					int cellCount=row.getLastCellNum();
					if(cellCount<0){						
						//表头
						if(index==8){
							result="第"+(index+1)+"行格式不正确";
							return result;
						}
						if(index==1||index==7){
							continue;
						}else{
							if(num==0){
								result="第"+(index+1)+"行格式不正确";
								return result;
							}else{
								continue;
							}
						}
					}
					//第二、八行 为空行
					if((index==1||index==7) && cellCount>0){
						result="第"+(index+1)+"行格式不正确!!";
						return result;
					}
					String cell;
					logger.info("cell "+index);
					cell=getCellByCellType(row.getCell((short)0));
					if(index==0 && "".equals(cell)){
						result="第"+(index+1)+"行格式不正确!!";
						return result;
					}
					
					//客户名称
					if(index==2){
						cell=getCellByCellType(row.getCell((short)1));
						if("".equals(cell) || null==cell){
							result="客户名称不能为空";
							return result;
						}
						if(!cell.equals(sellOrderInputDTO.getCustomerDTO().getCustomerName())){
							result="客户名称:"+cell+" 与所选客户不匹配";
							return result;
						}
					}
					//客户号
					if(index==3){
						cell=getCellByCellType(row.getCell((short)1));
						if("".equals(cell)||null==cell){
							result="客户号不能为空";
							return result;
						}
						if(!cell.equals(sellOrderInputDTO.getCustomerDTO().getEntityId())){
							result="客户号："+cell+" 与所选客户不匹配";
							return result;
						}
					}
					//订单日期
					if(index==4){
						cell=getCellByCellType(row.getCell((short)1));
						if("".equals(cell)||null==cell){
							result="订单日期不能为空";
							return result;
						}
						Date date=DateUtil.string2date(cell);
						if(null==date){
							result="订单日期格式不正确  （YYYY-MM-DD）";
							return result;
						}
						if(!date.equals(DateUtil.string2date(sellOrderInputDTO.getSellOrderDTO().getOrderDate()))){
							result="订单日期："+cell+" 与当前订单的日期不匹配";
							return result;
						}
					}
					
					if(index>8){						
						if(!"总计".equals(getCellByCellType(row.getCell((short)0)))){		
							SellOrderCardListQueryDTO sellOrderCardListQueryDTO=new SellOrderCardListQueryDTO();
							if("".equals(getCellByCellType(row.getCell((short)3)))){
								result="卡号不能为空";
								return result;
							}	
							logger.info("cell "+row.getCell((short)4).getCellType());
								m = getCellByCellType(row.getCell((short)4));
								if(false==StringUtils.Isdouble(m)){
									result="充值金额必须为数字";
									return result;
								}
							
							sellOrderCardListQueryDTO.setCardholderId(getCellByCellType(row.getCell((short)0)));
							sellOrderCardListQueryDTO.setFirstName(getCellByCellType(row.getCell((short)1)));
							sellOrderCardListQueryDTO.setExternalId(getCellByCellType(row.getCell((short)2)));
							sellOrderCardListQueryDTO.setCardNo(getCellByCellType(row.getCell((short)3)));
							sellOrderCardListQueryDTO.setCreditAmount(m);
							money=new BigDecimal(money).add(new BigDecimal(m)).toString();
							SellOrderCardListQueryDTO replaced=map.put(sellOrderCardListQueryDTO.getCardNo(), sellOrderCardListQueryDTO);
							if(null!=replaced){
								result="导入文件卡号："+sellOrderCardListQueryDTO.getCardNo()+"有重复，请检查";
								return result;
							}
							List list=sellOrderInputDTO.getSellOrderDTO().getOrderCardList().getData();
							if(null!=list && list.size()>0){
								for(int i=0;i<list.size();i++){
									logger.info(((Map)list.get(i)).get("cardNo"));
									if(sellOrderCardListQueryDTO.getCardNo().equals(((Map)list.get(i)).get("cardNo"))){
										result="卡号："+sellOrderCardListQueryDTO.getCardNo()+"已存在，请检查";
										return result;
									}
								}
							}
							sellOrderInputDTO.setSellOrderCardListQueryDTO(sellOrderCardListQueryDTO);
							result=(String)sendService(ConstCode.CREDIT_ORDER_INSER_CARDHOLDER_CARD_LIST, sellOrderInputDTO).getDetailvo();
							if(!"".equals(result.trim())){
								return result;
							}							
							sellOrderCardLists.add(sellOrderCardListQueryDTO);
						}else{
							logger.info("num "+index);
							num=index;
							if(!getCellByCellType(row.getCell((short)4)).equals(money)){
								result="充值金额总计不正确";
								return result;
							}else{
								sellOrderInputDTO.setSellOrderCardListQueryDTOs(sellOrderCardLists);
							}
						}
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			this.addActionError("添加充值订单明细失败");
			this.logger.error(e.getMessage());
		} catch (IOException e) {
			this.addActionError("添加充值订单明细失败");
			this.logger.error(e.getMessage());
		}
		
		
		return result;
	}
	
	private String getCellByCellType(HSSFCell cell){
		String result="";
		logger.info("getCell "+cell.getCellType());
		switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_NUMERIC :
            result = String.valueOf(cell.getNumericCellValue());
            break;
        case HSSFCell.CELL_TYPE_STRING :
            result = cell.getStringCellValue().trim();
            break;
        case HSSFCell.CELL_TYPE_FORMULA :
            result = cell.getCellFormula();
            break;
		}
		return result;
	}
	
	/**
	 * 查看扩展名(.txt)
	 */
	private String getExtension() {
		String extName = "";
		int pos = cardholderFileFileName.lastIndexOf(".");
		if (pos != -1) {
			extName = cardholderFileFileName.substring(pos);
		}
		return extName;
	}
	public String getMaxbalance() {
		return maxbalance;
	}

	public void setMaxbalance(String maxbalance) {
		this.maxbalance = maxbalance;
	}

	public String[] getCardNoArray() {
		return cardNoArray;
	}

	public void setCardNoArray(String[] cardNoArray) {
		this.cardNoArray = cardNoArray;
	}

	public String[] getOrderCardIdStr() {
		return orderCardIdStr;
	}

	public void setOrderCardIdStr(String[] orderCardIdStr) {
		this.orderCardIdStr = orderCardIdStr;
	}

	public SellOrderListDTO getSellOrderListDTO() {
		return sellOrderListDTO;
	}

	public void setSellOrderListDTO(SellOrderListDTO sellOrderListDTO) {
		this.sellOrderListDTO = sellOrderListDTO;
	}

	public File getCardholderFile() {
		return cardholderFile;
	}

	public void setCardholderFile(File cardholderFile) {
		this.cardholderFile = cardholderFile;
	}

	public String getCardholderFileFileName() {
		return cardholderFileFileName;
	}

	public void setCardholderFileFileName(String cardholderFileFileName) {
		this.cardholderFileFileName = cardholderFileFileName;
	}

	public SellOrderCardListDTO getSellOrderCardListDTO() {
		return sellOrderCardListDTO;
	}

	public void setSellOrderCardListDTO(SellOrderCardListDTO sellOrderCardListDTO) {
		this.sellOrderCardListDTO = sellOrderCardListDTO;
	}

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public CardholderDTO getCardholderDTO() {
		return cardholderDTO;
	}

	public void setCardholderDTO(CardholderDTO cardholderDTO) {
		this.cardholderDTO = cardholderDTO;
	}

	
}
