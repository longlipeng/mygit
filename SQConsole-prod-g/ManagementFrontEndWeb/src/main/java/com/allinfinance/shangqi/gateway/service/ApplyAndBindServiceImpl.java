package com.allinfinance.shangqi.gateway.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.allinfinance.batch.importOrder.service.ImportService;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.framework.dto.PageQueryDTO;
import com.allinfinance.shangqi.gateway.dao.ApplyAndBindDao;
import com.allinfinance.shangqi.gateway.dto.ApplyAndBindCardDTO;
import com.allinfinance.univer.cardmanagement.dto.CardManagementDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.huateng.framework.dao.CommonsDAO;
import com.huateng.framework.dao.PageQueryDAO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.ibatis.dao.CustomerDAO;
import com.huateng.framework.ibatis.dao.EntityStockDAO;
import com.huateng.framework.ibatis.model.Cardholder;
import com.huateng.framework.ibatis.model.Customer;
import com.huateng.framework.ibatis.model.EntityStock;
import com.huateng.framework.ibatis.model.EntityStockExample;
import com.huateng.framework.ibatis.model.SellOrder;
import com.huateng.framework.ibatis.model.SellOrderCardList;
import com.huateng.framework.util.ConfigMakeCard;
import com.huateng.framework.util.ConfigMessage;
import com.huateng.framework.util.DownNoteXML;
import com.huateng.framework.util.ExportExcelUtil;
import com.huateng.framework.util.JaxbUtil;
import com.huateng.framework.util.SFtpUtil;
import com.huateng.framework.util.SendShortMessageThread;
import com.huateng.framework.util.StringUtil;
import com.huateng.univer.cardmanage.biz.service.CardManageService;
import com.ibm.disthub2.impl.matching.selector.ParseException;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.sun.xml.messaging.saaj.util.ByteOutputStream;


public class ApplyAndBindServiceImpl implements ApplyAndBindService {
	Logger logger = Logger.getLogger(TxnqueryServiceImpl.class);
	private ApplyAndBindDao applyAndBindCardDao;
	private CommonsDAO commonsDAO;
	private CardManageService cardManageService;
	private List<ApplyAndBindCardDTO> cardHolderMsg;
	private PageQueryDAO pageQueryDAO;
	private CustomerDAO  customerDAO;
	private ImportService   importService;

	
	
	public ImportService getImportService() {
		return importService;
	}


	public void setImportService(ImportService importService) {
		this.importService = importService;
	}


	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}


	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}


	public PageQueryDAO getPageQueryDAO() {
		return pageQueryDAO;
	}


	public void setPageQueryDAO(PageQueryDAO pageQueryDAO) {
		this.pageQueryDAO = pageQueryDAO;
	}


	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}


	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}


	public CardManageService getCardManageService() {
		return cardManageService;
	}


	public void setCardManageService(CardManageService cardManageService) {
		this.cardManageService = cardManageService;
	}


	public ApplyAndBindDao getApplyAndBindCardDao() {
		return applyAndBindCardDao;
	}


	public void setApplyAndBindCardDao(ApplyAndBindDao applyAndBindCardDao) {
		this.applyAndBindCardDao = applyAndBindCardDao;
	}


	/*查询库存*/
	public String inqueryStockCount(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException{
		int count=-1;
		try {
			stockCountDTO.setStockStatus("1");
			count = applyAndBindCardDao.StockCardCountQuery( "ApplyAndBindCard.selectCountByDTO",stockCountDTO);
			//cardActive(stockCountDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BizServiceException("查询库存卡数量失败！");
			
		}
		logger.info("卡类型"+stockCountDTO.getProductId()+"的库存数量为"+count);
		if(count==0){
			return "01";  //没有库存
		}else if(count>0){
			return "00"; //有库存
		}else{
			return "02";//其他错误
		}
	}


	/*从库存中获得一张卡*/
	public String getCard(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException {
		String cardNo=null;
		try {
			
			List<EntityStock> list=applyAndBindCardDao.getStockCards("ApplyAndBindCard.selectCardsByDTO", stockCountDTO);
			if(list.size()>0){
				logger.info("返回获得卡信息："+ list.get(0).getCardNo());
				cardNo=list.get(0).getCardNo();
			}else{
				throw new BizServiceException("库存不足！");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cardNo;
	}


	/*检查持卡人信息*/
	public String checkCardHolderMsg(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		String checkStatus="";
		try {
			this.cardHolderMsg = applyAndBindCardDao.getCardHolderMsg("ApplyAndBindCard.selectCardsHoderByDTO", stockCountDTO);
			if(cardHolderMsg!=null&&cardHolderMsg.size()>0){
				ApplyAndBindCardDTO cardHolder = cardHolderMsg.get(0);
				if(cardHolder.getFirstName().equals(stockCountDTO.getFirstName())&&
						cardHolder.getIdType().equals(stockCountDTO.getIdType()) &&
								cardHolder.getIdNo().equals(stockCountDTO.getIdNo())){
					checkStatus="00";//持卡人信息检查通过
				}else{
					checkStatus= "01";//持卡人信息有误
				}
			}else{
				checkStatus= "02"; //持卡人不存在
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checkStatus;
	}


	/*卡片激活*/
	public String cardActive(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		CardManagementDTO dto = new CardManagementDTO();
		dto.setTransferOutCard(stockCountDTO.getCardNo());
		dto.setServiceFee(stockCountDTO.getServiceFee());
		dto.setCardNo(stockCountDTO.getCardNo());
		
		
		return cardManageService.activateCard(dto);
	}


	@Override
	public ApplyAndBindCardDTO bindCard(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		String checkCardHolderMsg = this.checkCardHolderMsg(stockCountDTO);
		if(checkCardHolderMsg.equals("01")){
			throw new BizServiceException("持卡人信息错！");  
		}else if(checkCardHolderMsg.equals("02")){
			throw new BizServiceException("卡信息不存在！");
		}else{
			String activeSta=this.cardActive(stockCountDTO);
			logger.debug("卡激活状态为："+activeSta);
			if( activeSta.equals("00")){
				return this.cardHolderMsg.get(0);
			}else{
				throw new BizServiceException("卡激活错！");
			}
			
		}
		
	}


	/*插入持卡人信息*/
	public void insertApplyCardMsg(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		if(stockCountDTO.getProductId()==null||stockCountDTO.getEntityId()==null||stockCountDTO.getFirstName()==null
				||stockCountDTO.getIdType()==null||stockCountDTO.getIdNo()==null||stockCountDTO.getRecipient_addr()==null
				||stockCountDTO.getRecipient_name()==null||stockCountDTO.getRecipient_phone()==null){
			throw new BizServiceException("输入信息有误！");
		}
		try {
			applyAndBindCardDao.insertApplyCard("ApplyAndBindCard.insert_ApplyAndBindInfo", stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BizServiceException("数据库插入错");
		}
		
	}
	/*更新持卡人信息*/
	public void updateApplyCardMsg(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		if(stockCountDTO.getProductId()==null||stockCountDTO.getEntityId()==null||stockCountDTO.getFirstName()==null
				||stockCountDTO.getIdType()==null||stockCountDTO.getIdNo()==null||stockCountDTO.getRecipient_addr()==null
				||stockCountDTO.getRecipient_name()==null||stockCountDTO.getRecipient_phone()==null){
			throw new BizServiceException("输入信息有误！");
		}
		try {
			applyAndBindCardDao.update("ApplyAndBindCard.update_ApplyMsgInfo", stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BizServiceException("数据库更新错");
		}
		
	}
	/*更新库存状态*/
	public void updateStockState(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException {
		try {
			applyAndBindCardDao.update("ApplyAndBindCard.updateStockStatus", stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void applyCardMessage(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		ApplyAndBindCardDTO applyAndBindCard=null;
//		try {
//			/*查看是否已经申请过*/
//			 applyAndBindCard = applyAndBindCardDao.getApplyAndBindCard("ApplyAndBindCard.lookApplyStatus", stockCountDTO);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	/*	String stock = this.inqueryStockCount(stockCountDTO);
		if(stock.equals("01")){
			throw new BizServiceException("库存不足");
		}else if(stock.equals("02")){
			throw new BizServiceException("其他错误");
		}else{*/
			stockCountDTO.setApplyStatus("0");//审核中
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String applyDate = format.format(new Date());
			stockCountDTO.setStartDate(applyDate);
			SimpleDateFormat formatSeconds = new SimpleDateFormat("yyyyMMddHHmmss");
			String applyDateSeconds = formatSeconds.format(new Date());
			stockCountDTO.setApplyDateSeconds(applyDateSeconds);
			
//			if(applyAndBindCard==null){
				insertApplyCardMsg(stockCountDTO);/*如果没有申请过插入数据库*/
//			}else{
//				updateApplyCardMsg(stockCountDTO);/*申请过，则修改申请状态*/
//			}
		//}
		
	}


	@Override
	public PageDataDTO checkApplyMsg(
			ApplyAndBindCardDTO stockCountDTO) throws BizServiceException {
		/*PageDataDTO dto = new PageDataDTO();
		try {
			List<ApplyAndBindCardDTO> list=applyAndBindCardDao.getApplyAndBindCardDTOList("ApplyAndBindCard.listForCheckApplyMsg", stockCountDTO);
			dto.setData(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BizServiceException("查询审核卡信息错！");
		}*/
		try {
			return pageQueryDAO.query("ApplyAndBindCard.listForCheckApplyMsg", stockCountDTO);
		} catch (Exception e) {
		    e.printStackTrace();
			this.logger.error(e.getMessage());
			throw new BizServiceException("查询审核信息失败");
		}
		
	}


	@Override
	public String lookApplyStatus(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		ApplyAndBindCardDTO applyAndBindCard=null;
		try {
			applyAndBindCard = applyAndBindCardDao.getApplyAndBindCard("ApplyAndBindCard.lookApplyStatus", stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BizServiceException("查询审核状态错！");
		}
		if(applyAndBindCard==null){
			return "";
		}else{
			return applyAndBindCard.getApplyStatus();
		}
		
	}
	
	public String getServiceId(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException{
		String id=null;
		try {
			id=applyAndBindCardDao.getID("ApplyAndBindCard.queryServiceId", stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	public String getCardLayoutId(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException{
		String id=null;
		try {
			id=applyAndBindCardDao.getID("ApplyAndBindCard.queryLayoutId", stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	public   void insertCardHolderAndOrderMsg(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException{
		/*原来自动生成订单代码 已注释，现在是直接审核通过*/
		/*Cardholder cardholder = new Cardholder();
		cardholder.setEntityId(stockCountDTO.getEntityId());
		根据机构号，证件类型，证件号查找申请的信息
		ApplyAndBindCardDTO singlePersonMsg = this.singlePersonMsg(stockCountDTO);
		cardholder.setCardholderBirthday(singlePersonMsg.getCardholderBirthday());
		cardholder.setCardholderComment(singlePersonMsg.getCardholderComment());
		cardholder.setCardholderGender(singlePersonMsg.getCardholderGender());
		cardholder.setCardholderFunction(singlePersonMsg.getCardholderFunction());
		cardholder.setCardholderMobile(singlePersonMsg.getCardholderMobile());
		cardholder.setCardholderEmail(singlePersonMsg.getCardholderEmail());
		cardholder.setCardholderSalutation(singlePersonMsg.getCardholderSalutation());
		cardholder.setCardholderSegment(singlePersonMsg.getCardholderSegment());
		cardholder.setFirstName(singlePersonMsg.getFirstName());
		cardholder.setLastName(singlePersonMsg.getLastName());
		cardholder.setIdType(stockCountDTO.getIdType());
		cardholder.setIdNo(stockCountDTO.getIdNo());
		//cardholder.setCheckState("");
		cardholder.setCardholderState("1");
		cardholder.setDataState("1");
		cardholder.setCardholderId(commonsDAO.getNextValueOfSequence("TB_CARDHOLDER"));
		cardholder.setCreateUser("0000");
		cardholder.setModifyUser("0000");
		Date date  = new  Date();
		String time= (date.getYear()+1900)+""+(date.getMonth()+1)+""+date.getDate()+""+date.getHours()+""+date.getMinutes()+""+date.getSeconds();
		cardholder.setModifyTime(time);
		cardholder.setCreateTime(time);
		生成持卡人信息
		try {
			applyAndBindCardDao.insertCardHolderMsg("TB_CARDHOLDER.abatorgenerated_insert", cardholder);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			throw new BizServiceException("插入持卡人信息错");
		}
		生成订单信息
		SellOrder sellOrder = new SellOrder();
		sellOrder.setOrderId(commonsDAO.getNextValueOfSequence("TB_SELL_ORDER"));
		sellOrder.setOrderType("10000011");
		//Date date = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append(date.getYear()+1900);
		if(date.getMonth()<10){
			sb.append("0"+(date.getMonth()+1));
		}else{
			sb.append(date.getMonth()+1);
		}
		if(date.getDate()<0){
			sb.append("0"+date.getDate());
		}else{
			sb.append(date.getDate());
		}
		
		sellOrder.setOrderDate(sb.toString());
		sellOrder.setFirstEntityId(stockCountDTO.getEntityId());
		sellOrder.setProcessEntityId(stockCountDTO.getEntityId());
		sellOrder.setProductId(stockCountDTO.getProductId());
		//sellOrder.setValidityPeriod("");//卡片的失效日期
		sellOrder.setCardLayoutId(this.getCardLayoutId(stockCountDTO));//卡面ID
		sellOrder.setSaleMan("0000");
		sellOrder.setDeliveryMeans("1");
		sellOrder.setAnnualFee("0");//年费
		sellOrder.setCardIssueFee("0");//卡费
		sellOrder.setDeliveryFee("0");//送货费
		sellOrder.setPaymentTerm("2");//激活前
		sellOrder.setPaymentDelay("0");//付款延期天数
		sellOrder.setPaymentState("1");
		sellOrder.setDiscountFee("0");//折扣费
		sellOrder.setAdditionalFee("0");//附加费
		sellOrder.setTotalPrice("0");//总费用
		sellOrder.setFaceValueType("1");
		sellOrder.setFaceValue("0");
		sellOrder.setServiceId(this.getServiceId(stockCountDTO));//账户类型
		sellOrder.setOrderSource("3");//门户录入生成
		sellOrder.setOrderState("32");
		sellOrder.setCardQuantity("1");//门户申请卡数量为1
		sellOrder.setRealCardQuantity("1");//实际卡数量1
		sellOrder.setDataState("1");
		sellOrder.setCreateTime(time);
		sellOrder.setCreateUser("0000");
		sellOrder.setModifyTime(time);
		sellOrder.setModifyUser("0000");
		
		try {
			applyAndBindCardDao.insertSellOrderMsg("TB_SELL_ORDER.abatorgenerated_insert", sellOrder);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			throw new BizServiceException("插入持卡人信息错");
		}
		
		生成订单卡关联信息
		SellOrderCardList  soc = new SellOrderCardList();
		soc.setOrderCardListId(commonsDAO.getNextValueOfSequenceBySequence("SEQ_SELL_ORDER_CARD_LIST"));
		soc.setCardholderId(cardholder.getCardholderId());
		soc.setOrderId(sellOrder.getOrderId());
		soc.setFirstName(cardholder.getFirstName());
		soc.setLastName(cardholder.getLastName());
		soc.setCardState("3");//制卡成功
		String card = this.getCard(stockCountDTO);
		soc.setCardNo(card);
		soc.setCreateTime(time);
		soc.setCreateUser("0000");
		soc.setModifyTime(time);
		soc.setModifyUser("0000");
		soc.setDataState("1");*/
		
		//applyAndBindCardDao.insertSellOrderCardList("TB_SELL_ORDER_CARD_LIST.abatorgenerated_insert", soc);
		//stockCountDTO.setCardNo(card);
		//stockCountDTO.setStockStatus("3");
		/*自动生成持卡人和客户信息*/
		importService.batchInsertCustomerAndCardHolderFromOnline(stockCountDTO);
		stockCountDTO.setApplyStatus("1");//审核通过
		//this.updateStockState(stockCountDTO);//更新在库状态
		this.applyRefuse(stockCountDTO);//更新审核信息
		
	}
	
	public  void applyCard(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException{
		/*String count = this.inqueryStockCount(stockCountDTO);
		if(count.equals("00")){
			this.insertCardHolderAndOrderMsg(stockCountDTO);
		}else{
			logger.error("库存检查返回码："+count);
			throw new BizServiceException("库存不足！");
		}*/
		this.insertCardHolderAndOrderMsg(stockCountDTO);
		
	}


	@Override
	public void checkMsg(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		this.applyCard(stockCountDTO);
		
	}


	@Override
	public PageDataDTO mailMessages(
			ApplyAndBindCardDTO stockCountDTO) throws BizServiceException {
		
		
			//return pageQueryDAO.query("ApplyAndBindCard.listForCheckApplyMsg", stockCountDTO);
			try {
				return pageQueryDAO.query("ApplyAndBindCard.listForCheckApplyMsg", stockCountDTO);
			} catch (Exception e) {
			    e.printStackTrace();
				this.logger.error(e.getMessage());
				throw new BizServiceException("查询邮寄信息失败");
			}
	
		
	
	}


	@Override
	public void MakeSureMail(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		try {
			applyAndBindCardDao.update("ApplyAndBindCard.update_ApplyAndBindInfo", stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void applyRefuse(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		try {
			applyAndBindCardDao.update("ApplyAndBindCard.update_ApplyAndBindInfo", stockCountDTO);
			
			
			/**
			 * 注释卡申请审核通过发送短信模块
			 */
			/*SendShortMessageThread sendShortMessageThread;
			if(stockCountDTO.getApplyStatus().equals("1")){
				ApplyAndBindCardDTO newApplyAndBindCardDTO = applyAndBindCardDao.getApplyAndBindCard("ApplyAndBindCard.SinglePersonMessage", stockCountDTO);
        		sendShortMessageThread = new SendShortMessageThread(stockCountDTO.getEntityId(),newApplyAndBindCardDTO.getCardholderMobile()
        				                                             ,ConfigMessage.getTemplet1_No(),newApplyAndBindCardDTO.getFirstName());
        	}else{
        		sendShortMessageThread = new SendShortMessageThread(stockCountDTO.getEntityId(),stockCountDTO.getCardholderMobile()
        				                                            ,ConfigMessage.getTemplet2_No(),stockCountDTO.getCardholderComment());
        	}
			sendShortMessageThread.start();*/
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public ApplyAndBindCardDTO getCardHolder(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		ApplyAndBindCardDTO dto=null;
		try {
			List<ApplyAndBindCardDTO> cardHolderMs= applyAndBindCardDao.getCardHolderMsg("ApplyAndBindCard.selectCardsHoderByDTO", stockCountDTO);
			if(cardHolderMs.size()==0||cardHolderMs.size()>1){
				throw new BizServiceException("卡不存在或卡号错！");
			}else{
				dto=cardHolderMs.get(0);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dto;
	}


	@Override
	public ApplyAndBindCardDTO onlineOrInline(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		//ApplyAndBindCardDTO dto=null;
		
		try {
 			String id = applyAndBindCardDao.getID("ApplyAndBindCard.queryOnlineOrInline", stockCountDTO);
 			if(id==null){
 				throw new BizServiceException("卡号不存在！");
 			}
			stockCountDTO.setChannel(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stockCountDTO;
	}


	@Override
	public List<String> cardNos(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		List<String>  cardNos = new ArrayList<String>();
		List<ApplyAndBindCardDTO> list = null;
		try {
			list=applyAndBindCardDao.getApplyAndBindCardDTOList("ApplyAndBindCard.queryCardNosByCardHolder", stockCountDTO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list==null||list.size()==0){
			throw new BizServiceException("持卡人下无卡！");
		}else{
			for(int i=0;i<list.size();i++){
				cardNos.add(list.get(i).getCardNo());
			}
		}
		return cardNos;
	}


	
	@Override
	public ApplyAndBindCardDTO singlePersonMsg(ApplyAndBindCardDTO stockCountDTO)
			throws BizServiceException {
		ApplyAndBindCardDTO dto=null;
		try {
			CardholderDTO cardHolder=new CardholderDTO();
			List<ApplyAndBindCardDTO> applyAndBindCardDTOList = applyAndBindCardDao.getApplyAndBindCardDTOList("ApplyAndBindCard.SinglePersonMessage", stockCountDTO);
			cardHolder.setIdNo(stockCountDTO.getIdNo());
			cardHolder= applyAndBindCardDao.getCardholderDTO("TB_CARDHOLDER.getCardHolderInfo", cardHolder);
			if(applyAndBindCardDTOList!=null&&applyAndBindCardDTOList.size()!=0){
				dto=applyAndBindCardDTOList.get(0);
				if(cardHolder!=null&&!cardHolder.equals("")){
					//是否有客户信息  1有   0无
					dto.setIsCustomerInfo("1");
					int count=applyAndBindCardDao.getAccCardholderInfoByCardholderId("TB_CARDHOLDER.selectCardInfoByCardholderId", cardHolder);
					if(count>0){
						//是否有发卡记录  1有   0无
						dto.setIsIssue("1");
					}else{
						dto.setIsIssue("0");
					}
				}else{
					dto.setIsCustomerInfo("0");
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	} 
	
	
/*public   List<Map<String, String>>  createExcelRecord( List<ApplyAndBindCardDTO> list ) {
	
	List<Map<String, String>> records = new ArrayList<Map<String, String>>();
	for(int i=0;i<list.size();i++){
		Map<String, String> record = new HashMap<String, String>();
		ApplyAndBindCardDTO dto=list.get(i);
		record.put("name", dto.getFirstName());
		record.put("idNo", dto.getIdNo());
		record.put("gender", dto.getCardholderGender());
		record.put("birthday", dto.getCardholderBirthday());
		record.put("channel", dto.getChannel());
		record.put("phone", dto.getCardholderMobile());
		record.put("addr", dto.getRecipient_addr());
		record.put("postCode", dto.getPost_code());
		records.add(record);
	}
		return records;
	}
	生成excel文件
	
		public void  exportExcel(ApplyAndBindCardDTO stockCountDTO)throws  BizServiceException{
			
			List<ApplyAndBindCardDTO> filemessage = null;
			try {
				
				filemessage = applyAndBindCardDao.getCardHolderMsg("ApplyAndBindCard.queryApplyMsgByApplyDate", stockCountDTO);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(filemessage==null){
				throw new BizServiceException("无数据可写入");
			}
			List<Map<String, String>> list = createExcelRecord(filemessage);
			
			String columNames[] = {"客户名称", "身份证号", "性别", "出生日期", "渠道信息", "联系电话", "邮寄地址", "邮编"};
			String keys[] = {"name","idNo","gender","birthday","channel","phone","addr","postCode"};
				this.write(ConfigMakeCard.getExcelPathRemote()+stockCountDTO.getStartDate(), keys, list, columNames);
				
		}*/
		/*下载文件
		public void down(ApplyAndBindCardDTO stockCountDTO) throws BizServiceException, NumberFormatException, JSchException, SftpException, IOException{
			if(stockCountDTO.getStartDate()==null){
				throw  new BizServiceException("文件名有误！");
			}
			String fileName = stockCountDTO.getStartDate().trim();
			Map<String,List<byte[]>> fileMap= new HashMap<String,List<byte[]>>();
			List<byte[]> byteList = this.ftpGetExcelFile(fileName);
			fileMap.put(fileName, byteList);
			stockCountDTO.setFileData(fileMap);
		}*/
		
		/*public List<byte[]> ftpGetExcelFile(String filename) throws BizServiceException{
			if(StringUtil.isNotEmpty(filename)){
				filename= filename.trim();
			}
			List<byte[]> byteList=null;
			String filePath = ConfigMakeCard.getExcelPathRemote();
			logger.debug("取得文件路径"+filePath+filename);
			BufferedInputStream bs = null;
			File tempFile = new File(filePath+filename);
			if(tempFile==null||"".equals(tempFile.toString())){
				throw new BizServiceException("无效的文件路径");
			}
			byteList = new ArrayList<byte[]>();
			long len = tempFile.length();
			byte[] bytes = new byte[(int)len];
			try {
				bs = new BufferedInputStream(new FileInputStream(tempFile));
				int r = bs.read(bytes);
				if(r!=len){
					throw new BizServiceException("文件读取错误");
				}
				byteList.add(bytes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new BizServiceException("读取文件失败");
			}finally{
				if(null!=bs){
					try {
						bs.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			return byteList;
		}*/
		
		
		/*生成excel文件
		public  void write(String outPath,String []columns,  List<Map<String,String>> list,String[] columnNames) throws BizServiceException  {
			
		    // 创建工作文档对象
		    Workbook wb = new HSSFWorkbook();

		    // 创建sheet对象
		    Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
		    //写入表头
		    Row bt = (Row) sheet1.createRow(0);
		    for(int i=0;i<columnNames.length ;i++){
		    	bt.createCell(i).setCellValue(columnNames[i]);
		    }
		    // 循环写入行数据
		   
		    for (int i = 1; i <=list.size(); i++) {
		      Row r = (Row) sheet1.createRow(i);
		      Map map = list.get(i-1);
		      // 循环写入列数据
		      for (int j = 0; j <columns.length; j++) {
		    	
		        Cell cell = r.createCell(j);
		        cell.setCellValue((String)map.get(columns[j]));
		      }
		    }
		    // 创建文件流
		    OutputStream stream =null;
			try {
				stream = new FileOutputStream(outPath);
				wb.write(stream);
				stream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BizServiceException("未找到文件！");
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BizServiceException("写文件或者关闭文件流错！");
			}finally{
				if(stream!=null){
					try {
						stream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		    
		   
		  }*/
	private List<Map<String, Object>> createExcelRecord(List<ApplyAndBindCardDTO> dtos) {
        List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        records.add(map);
        ApplyAndBindCardDTO dto=null;
        for (int j = 0; j < dtos.size(); j++) {
        	dto = dtos.get(j);
            Map<String, Object> record = new HashMap<String, Object>();
            record.put("name", dto.getFirstName());
    		record.put("idNo", dto.getIdNo());
    		record.put("gender", dto.getCardholderGender());
    		record.put("birthday", dto.getCardholderBirthday());
    		record.put("channel", dto.getSource());
    		record.put("phone", dto.getRecipient_phone());
    		record.put("addr", dto.getRecipient_addr());
    		record.put("postCode", dto.getPost_code());
    		record.put("cardholderPhone", dto.getCardholderMobile());
    		record.put("vId", dto.getVId());
    		record.put("plateNumber", dto.getPlateNumber());
    		record.put("driverLicence", dto.getDriverLicence());
    		record.put("applyStatus", dto.getApplyStatus());
    		record.put("applyDateSeconds", dto.getApplyDateSeconds());
    		records.add(record);
        }
        return records;
    }
	
	public ApplyAndBindCardDTO  exportExcel(ApplyAndBindCardDTO stockCountDTO)throws  BizServiceException{
		
		List<ApplyAndBindCardDTO> filemessage = null;
		try {
			
			filemessage = applyAndBindCardDao.getCardHolderMsg("ApplyAndBindCard.queryApplyMsgByApplyDate", stockCountDTO);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(filemessage==null){
			throw new BizServiceException("无数据可写入");
		}
		List<Map<String, Object>> list = createExcelRecord(filemessage);
		
		String columNames[] = {"客户名称", "身份证号", "性别", "出生日期", "渠道信息(0-微信，1-PC，2-APP)", "联系电话", "邮寄地址", "邮编","客户电话","车架号","车牌号","驾驶证号","申请状态","申请时间"};
		String keys[] = {"name","idNo","gender","birthday","channel","phone","addr","postCode","cardholderPhone","vId","plateNumber","driverLicence","applyStatus","applyDateSeconds"};
		ByteOutputStream ops = new ByteOutputStream();
		try {
			ExportExcelUtil.createWorkBook(list, keys, columNames).write(ops);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] content = ops.getBytes();
		List<byte[]> byteList = new ArrayList<byte[]>();
		byteList.add(content);
		Map<String,List<byte[]>> fileMap= new HashMap<String,List<byte[]>>();
		fileMap.put(stockCountDTO.getStartDate(), byteList);
		stockCountDTO.setFileData(fileMap);
		return stockCountDTO;
	}
	public ApplyAndBindCardDTO readClearFile(ApplyAndBindCardDTO dto)throws BizServiceException{
		String fileName ="";
		try {
			File filePath = new File(ConfigMakeCard.getExcelPathLocal()+dto.getEntityId());
			if(!filePath.exists()){
				filePath.mkdirs();
			}
			
		
			if(dto.getIsCheXiang().equals("1")){
				fileName=  dto.getStartDate()+".cxj.tar.gz";
			}else if (dto.getIsCheXiang().equals("0")){
				fileName=  dto.getStartDate()+".tar.gz";
			}
			
			SFtpUtil.download(ConfigMakeCard.getIp(),Integer.parseInt(ConfigMakeCard.getPort()),ConfigMakeCard.getUserName(), 
					ConfigMakeCard.getPassword(), ConfigMakeCard.getExcelPathRemote()+dto.getEntityId()+"/"+dto.getStartDate()+"/", fileName, ConfigMakeCard.getExcelPathLocal()+dto.getEntityId()+"/"+ fileName);
		} catch (JSchException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new BizServiceException("文件不存在！");
		} catch (SftpException e1) {
			// TODO Auto-generated catch block
			
			throw new BizServiceException("文件不存在！");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File file = new File(ConfigMakeCard.getExcelPathLocal()+dto.getEntityId()+"/"+fileName);
		InputStream is =null;
		try {
			is = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			ByteOutputStream ops = new ByteOutputStream();
			
				while(-1!=is.read(bytes)){
					ops.write(bytes);
				}
				is.close();
				List<byte[]> byteList = new ArrayList<byte[]>();
				byteList.add(ops.getBytes());
				Map<String,List<byte[]>> fileMap= new HashMap<String,List<byte[]>>();
				fileMap.put(dto.getStartDate(), byteList);
				dto.setFileData(fileMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizServiceException("读文件失败！");
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return dto;
	}
//	public String toShortMessageService(ApplyAndBindCardDTO dto){
//		String responseMessage="";
//		Socket socket =null;
//		 InputStream is =null;
//		 DataInputStream dataIs = null;
//		logger.debug("短息IP"+ConfigMessage.getIp());
//		logger.debug("短信端口"+ConfigMessage.getPort());
//		try {  
//			socket = new Socket(ConfigMessage.getIp(),Integer.parseInt(ConfigMessage.getPort()));  
//            // 向服务器端发送数据  
//            OutputStream os =  socket.getOutputStream();  
//            DataOutputStream bos = new DataOutputStream(os);  
//            DownNoteXML downNoteXML = new DownNoteXML();
//        		downNoteXML.setOrgId(dto.getEntityId());//机构号
//        		//downNoteXML.setSmsNo("1234567");//短信流水号
//        		downNoteXML.setSmsType("1");//短信即刻发
//        		downNoteXML.setMobile(dto.getCardholderMobile());//持卡人手机号码
//	        	if(dto.getApplyStatus().equals("1")){
//	        		downNoteXML.setSms(ConfigMessage.getTemplet1_No());//短信模版号
//	        	}else{
//	        		downNoteXML.setSms(ConfigMessage.getTemplet2_No());//短信模版号
//	        		downNoteXML.setCv(dto.getCardholderComment());
//	        	}	
//        		
//        		
//            String xml = JaxbUtil.convertToXml(downNoteXML).trim();
//            int len = xml.getBytes().length;
//            String str = JaxbUtil.codeAddOne(String.valueOf(len), 4);
//            String con = str + xml.trim();//拼接报文，最前是长度
//            logger.info("短信内容"+con);
//           
//            bos.write(con.getBytes()); 
//            bos.flush();  
////            接收服务器端数据  
//           is = socket.getInputStream();  
//           dataIs = new DataInputStream(is);
//           byte[] header = new byte[4];
//           dataIs.read(header);
//           int msgLen = Integer.parseInt(new String(header));
//           logger.info("报文长度："+msgLen);
//           byte[] body = new byte[msgLen];
//           dataIs.read(body);
//           responseMessage = new String(body);
//           logger.info("收到的回复"+responseMessage);  
//           is.close();
//           socket.close();
//        } catch (UnknownHostException e) {  
//            e.printStackTrace();  
//        } catch (IOException e) {  
//            e.printStackTrace();  
//        } finally{
//        	
//        	
//        		try {
//        			if(dataIs!=null){
//        				dataIs.close();
//        			}
//        			if(is!=null){
//        				is.close();
//        			}
//					if(socket!=null){
//						socket.close();
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//        	}
//        
//		return responseMessage;
//	}
}
