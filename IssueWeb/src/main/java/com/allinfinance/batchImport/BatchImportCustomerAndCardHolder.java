package com.allinfinance.batchImport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.ImportServiceDTO;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.shangqi.gateway.dto.BatchCardHolderMessageDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerContractQueryDTO;
import com.allinfinance.univer.seller.sellercontract.dto.SellerProductContractDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.util.CharUtil;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ExcelReader;


public class BatchImportCustomerAndCardHolder extends BaseAction{
	private BatchCardHolderMessageDTO  batchCardHolderMessageDTO = new BatchCardHolderMessageDTO();
	IDCardCheck check=new IDCardCheck();
	private String[] orderListStr;
	
	private CustomerDTO customerDTO;
	
	private SellOrderDTO sellOrderDTO;
	
	private ImportServiceDTO importServiceDTO;

	public ImportServiceDTO getImportServiceDTO() {
		return importServiceDTO;
	}

	public void setImportServiceDTO(ImportServiceDTO importServiceDTO) {
		this.importServiceDTO = importServiceDTO;
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public SellOrderDTO getSellOrderDTO() {
		return sellOrderDTO;
	}

	public void setSellOrderDTO(SellOrderDTO sellOrderDTO) {
		this.sellOrderDTO = sellOrderDTO;
	}

	public String[] getOrderListStr() {
		return orderListStr;
	}

	public void setOrderListStr(String[] orderListStr) {
		this.orderListStr = orderListStr;
	}

	public BatchCardHolderMessageDTO getBatchCardHolderMessageDTO() {
		return batchCardHolderMessageDTO;
	}

	public void setBatchCardHolderMessageDTO(BatchCardHolderMessageDTO batchCardHolderMessageDTO) {
		this.batchCardHolderMessageDTO = batchCardHolderMessageDTO;
	}
	
	public String menuToPage(){
		return SUCCESS;
	}

	// 客户持卡人批量导入
	public String submitFileMessage() {
		InputStream is = null;
		try {
			File file = batchCardHolderMessageDTO.getBatchFile();
			if (file == null || file.length() <= 0) {
				this.addActionError("文件不能为空文件！");
				return INPUT;
			}

			is = new FileInputStream(file);

			ExcelReader reader = new ExcelReader();

			Map<Integer, String> readExcelContent2 = null;
			try {
				readExcelContent2 = reader.readExcelContent2(is);
			} catch (Exception e) {
				// TODO: handle exception
				this.addActionError("文件格式有误！");
				return INPUT;
			}
			List<String> errRows = new ArrayList<String>();
			List<String> IdCard = new ArrayList<String>();
			if (!batchCardHolderMessageDTO.getCountNum().equals(readExcelContent2.size() + "")) {
				this.addActionError("上传记录数与输入记录数不一致！");
				return INPUT;
			}
			if (readExcelContent2.size() > 2000) {
				this.addActionError("上传记录数不能超过2000条！");
				return INPUT;
			}
			List<String> customerPostcode = new ArrayList<String>();
			List<String> birthday = new ArrayList<String>();

			for (Integer key : readExcelContent2.keySet()) {
				String cardholderMessageString = readExcelContent2.get(key);

				String[] cardholderMessage = cardholderMessageString.split("-");
				// 批量导入文件共同部分的校验
				// 当末尾有空内容时，split方法取不到20个参数,要在末尾补上空格
				if (cardholderMessage.length < 20) {
					this.addActionError("第" + key + "行末尾为空，或者文件格式有误（缺少列）！");
					return INPUT;
				}

				// 校验文本是否有特殊字符
				if (cardholderMessage.length > 20) {
					this.addActionError("第" + key + "行内容可能带有“-”字符,或者列数大于20列！");
					return INPUT;
				}
				checkFile(key, cardholderMessage);
				if (hasActionErrors()) {
					return INPUT;
				}
			}
			if (errRows.size() > 0) {
				downLoad(errRows);
				this.addActionError("文件检查错误！");
				return INPUT;
			}

			if (IdCard.size() > 0) {
				downLoad(IdCard);
				this.addActionError("文件检查错误！");
				return INPUT;
			}
			if (birthday.size() > 0) {
				downLoad(birthday);
				this.addActionError("文件检查错误！");
				return INPUT;
			}

			if (customerPostcode.size() > 0) {

				this.addActionError("文件检查错误！");
				downLoad(customerPostcode);
				return INPUT;
			}
			batchCardHolderMessageDTO.setFileMap(readExcelContent2);

			is.close();
			if (readExcelContent2 == null || readExcelContent2.size() <= 0) {
				this.addActionError("文件内容不能为空！");
				return INPUT;
			}
			if (!batchCardHolderMessageDTO.getCountNum().equals(readExcelContent2.size() + "")) {
				this.addActionError("上传记录数与输入记录数不一致！");
				return INPUT;
			}
			batchCardHolderMessageDTO.setBatchFile(null);
			OperationResult sendService = this.sendService("20150817001", batchCardHolderMessageDTO);
			if (sendService != null) {
				if (sendService.getTxnstate() == null || !sendService.getTxnstate().equals("1")) {
					this.clearActionErrors();
					this.addActionError("导入失败！");
					return INPUT;
				} else {
					this.addActionMessage("导入成功");
					return SUCCESS;
				}
			} else {
				this.addActionError("系统异常！");
				return INPUT;
			}
		} catch (FileNotFoundException e) {
			this.addActionError("文件不存在！");
			return INPUT;
		} catch (IOException e) {
			this.addActionError("读取文件错！");
			return INPUT;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * 选择卡号开卡 lk
	 * 
	 * @return
	 */
//	@SuppressWarnings("unchecked")
	public String batchCardImportIssue(){
		
		InputStream  is=null;
		
		try {
			
			File file = batchCardHolderMessageDTO.getBatchFile();
			if(file==null||file.length()<=0){
				this.addActionError("文件不能为空文件！");
				return INPUT;
			}
			
			is = new FileInputStream(file);
			
			ExcelReader reader = new ExcelReader();
			Map<Integer, String> readExcelContent2=null;
			try {
				readExcelContent2 = reader.readExcelContent2(is);
			} catch (Exception e) {
				this.addActionError("文件格式有误!");
				return INPUT;
			}
			
			List<String> errRows = new ArrayList<String>();
			List<String> IdCard=new ArrayList<String>();
			List<String> expiryDateList=new ArrayList<String>();
			List<String> customerPostcode=new ArrayList<String>();
			List<String> birthday=new ArrayList<String>();
			List<String> checkDate=new ArrayList<String>();
			if(!batchCardHolderMessageDTO.getCountNum().equals(readExcelContent2.size()+"")){
				this.addActionError("上传记录数与输入记录数不一致！");
				return INPUT;
			}
			if(readExcelContent2.size()>200){
				this.addActionError("上传记录数不能超过200条！");
				return INPUT;
			}
			SellerContractQueryDTO sellerContractQueryDTO=new SellerContractQueryDTO();
			sellerContractQueryDTO.setContractBuyer(getUser().getEntityId());
			List<SellerContractDTO>  sellerContractDTOList= (List<SellerContractDTO>)sendService(ConstCode.CONTRACT_SERVICE_INQUERY_MASTERPALTE,
					 sellerContractQueryDTO).getDetailvo();
			
			if (null != sellerContractDTOList && sellerContractDTOList.size() > 0) {
				String expiryDate = sellerContractDTOList.get(0).getExpiryDate();
				String sysDate = DateUtil.getCurrentDateStr();
//				System.out.println("合同失效日期:" + expiryDate);
//				System.out.println("当前系统日期:" + DateUtil.getCurrentDateStr());
				if(Integer.parseInt(sysDate) > Integer.parseInt(expiryDate)) {
					this.addActionError("当前机构与上级发行机构合同已过期!");
					return INPUT;
				}
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			List<String> list=new ArrayList<String>();
			CardholderDTO cardholderDTO=new CardholderDTO();
			ImportServiceDTO importDTO=new ImportServiceDTO();
			List<ImportServiceDTO> importDtoList=new ArrayList<ImportServiceDTO>();
			List<CardholderDTO> cardholderDtoList =new ArrayList<CardholderDTO>();
			Map<String, String> productMap=new HashMap<String, String>();
			for(Integer key : readExcelContent2.keySet()){
				String cardholderMessageString = readExcelContent2.get(key);
				
				String[] cardholderMessage = cardholderMessageString.split("-");
				// 当末尾有空内容时，split方法取不到20个参数,要在末尾补上空格
				if (cardholderMessage.length < 25) {
					this.addActionError("第" + key + "行末尾为空，或者文件格式有误（缺少列）！");
					return INPUT;
				}

				// 校验文本是否有特殊字符
				if (cardholderMessage.length > 25) {
					this.addActionError("第" + key + "行内容可能带有“-”字符,或者列数大于25列！");
					return INPUT;
				}
				checkFile(key, cardholderMessage);
				if (hasActionErrors()) {
					return INPUT;
				}
				ImportServiceDTO importServiceDTO=new ImportServiceDTO();
				SellerProductContractDTO sellerProductContractDTO=new SellerProductContractDTO();
				SellOrderInputDTO sellOrderInputDTO=new SellOrderInputDTO();
				SellOrderDTO sellOrderDTO=new SellOrderDTO();
				sellOrderDTO.setFaceValue(cardholderMessage[23]);
				sellOrderDTO.setCardLayoutId(cardholderMessage[24]);
				//sellOrderDTO.setPackageId(cardholderMessage[25]);
				sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
				importServiceDTO.setSellOrderInputDTO(sellOrderInputDTO);
				sellerProductContractDTO.setProductId(cardholderMessage[22]);
				importServiceDTO.setSellerProductContractDTO(sellerProductContractDTO);
				
				
				//卡产品汇总    -卡号 -lk待修改
				if(productMap.containsKey(cardholderMessage[22].trim())){
					int cunt=Integer.parseInt(productMap.get(cardholderMessage[22].trim()))+1;
					productMap.put(cardholderMessage[22].trim(), cunt+"");
				}else{
					productMap.put(cardholderMessage[22].trim(), "1");
				}
				
				importDtoList.add(importServiceDTO);
//				batchCardHolderMessageDTO.setImportServiceDTO(importServiceDTO);
				CardholderDTO cardholderDto=new CardholderDTO();
				cardholderDto.setIdType(cardholderMessage[4].trim());
				cardholderDto.setIdNo(cardholderMessage[5].trim());
				cardholderDto.setCardholderEmail(cardholderMessage[22].trim());//把卡产品暂存到这个字段
				cardholderDto.setCardholderMobile(cardholderMessage[24].trim());//把卡号暂存到这个字段
				cardholderDtoList.add(cardholderDto);
				
				
				//证件类型，证件号，卡产品【拼接字符，放入list，用于判断同一文件有无重复售卡
//				list.add(cardholderMessage[4].trim()+cardholderMessage[5].trim()+cardholderMessage[22].trim()+cardholderMessage[24].trim());
				list.add(cardholderMessage[24].trim());
			}
			
			
			HashSet set = new HashSet();
			for (String i : list)
				set.add(i);
			if (!(set.size() == list.size())) {
				this.addActionError("文件出现重复数据，同一卡号。请检查文件！");// 同一证件号、卡产品并且
				return INPUT;
			}
			
			//检查卡产品    -lk待修改   --//2015123101--2017061401
			importDTO.setImportServiceDtoList(importDtoList);
			importDTO.setMap(productMap);
			ImportServiceDTO importServiceDTOList=(ImportServiceDTO)this.sendService("2017061401", importDTO).getDetailvo();
			
			
			//检查重复售卡 -lk待修改
			cardholderDTO.setCardholderDTOList(cardholderDtoList);
//			ImportServiceDTO importServiceDTOErrList=(ImportServiceDTO)this.sendService("2016021802", cardholderDTO).getDetailvo();
			
			//检查卡号 
			ImportServiceDTO importServiceDTOErrList2=(ImportServiceDTO)this.sendService("2017070402", cardholderDTO).getDetailvo();
			
			
			if(errRows.size()>0){
				this.addActionError("文件检查错误！");
				downLoad(errRows);
				return INPUT;
			}
			if(IdCard.size()>0){
				
				this.addActionError("文件检查错误！");
				downLoad(IdCard);
				return INPUT;
			}
			if(birthday.size()>0){
				downLoad(birthday);
				this.addActionError("文件检查错误！");
				return INPUT;
			}

			if(customerPostcode.size()>0){
				
				this.addActionError("文件检查错误！");
				downLoad(customerPostcode);
				return INPUT;
			}
			if(expiryDateList.size()>0){
				
				this.addActionError("文件检查错误！");
				downLoad(expiryDateList);
				return INPUT;
			}
			if(checkDate.size()>0){
				
				this.addActionError("文件检查错误！");
				downLoad(checkDate);
				return INPUT;
			}
			
			if(importServiceDTOList.getList()!=null&&importServiceDTOList.getList().size()>0){
				this.addActionError("文件检查错误！");
				downLoad(importServiceDTOList.getList());
				return INPUT;
			}
			if(importServiceDTOErrList2.getList()!=null&&importServiceDTOErrList2.getList().size()>0){
				this.addActionError("文件检查错误！");
				downLoad(importServiceDTOErrList2.getList());
				return INPUT;
			}
			
			batchCardHolderMessageDTO.setFileMap(readExcelContent2);
			
			is.close();
			if(readExcelContent2==null||readExcelContent2.size()<=0){
				this.addActionError("文件内容不能为空！");
				return INPUT;
			}
			
			//lk --2015122501     主接口
			batchCardHolderMessageDTO.setBatchFile(null);
			//  选择卡号开卡--定义typeKH类型
			batchCardHolderMessageDTO.setTypeKH(20170614);
			
			OperationResult sendService = this.sendService("2015122501", batchCardHolderMessageDTO);
			if(sendService!=null){
				if(sendService.getTxnstate()==null||!sendService.getTxnstate().equals("1")){
					this.clearActionErrors();
					this.addActionError(sendService.getErrMessage());
					return INPUT;
				}else{
					this.addActionMessage("导入成功");
					return SUCCESS;
				}
			}else{
				this.addActionError("系统异常！");
				return INPUT;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			this.addActionError("文件不存在！");
			return INPUT;
		} catch (IOException e) {
			e.printStackTrace();
			this.addActionError("读取文件错！");
			return INPUT;
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public String batchImportIssue() {// 售卡自动完成
		InputStream  is=null;
		
		try {
			File file = batchCardHolderMessageDTO.getBatchFile();
			if(file==null||file.length()<=0){
				this.addActionError("文件不能为空文件！");
				return INPUT;
			}
			
			is = new FileInputStream(file);
			
			ExcelReader reader = new ExcelReader();
			Map<Integer, String> readExcelContent2=null;
			try {
				readExcelContent2 = reader.readExcelContent2(is);
			} catch (Exception e) {
				// TODO: handle exception
				this.addActionError("文件格式有误!");
				return INPUT;
			}
			
			List<String> errRows = new ArrayList<String>();
			List<String> IdCard=new ArrayList<String>();
			List<String> expiryDateList=new ArrayList<String>();
			List<String> customerPostcode=new ArrayList<String>();
			List<String> birthday=new ArrayList<String>();
			List<String> checkDate=new ArrayList<String>();
			if(!batchCardHolderMessageDTO.getCountNum().equals(readExcelContent2.size()+"")){
				this.addActionError("上传记录数与输入记录数不一致！");
				return INPUT;
			}
			if(readExcelContent2.size()>200){
				this.addActionError("上传记录数不能超过200条！");
				return INPUT;
			}
			SellerContractQueryDTO sellerContractQueryDTO=new SellerContractQueryDTO();
			sellerContractQueryDTO.setContractBuyer(getUser().getEntityId());
			List<SellerContractDTO>  sellerContractDTOList= (List<SellerContractDTO>)sendService(ConstCode.CONTRACT_SERVICE_INQUERY_MASTERPALTE,
					 sellerContractQueryDTO).getDetailvo();
			
			if (null != sellerContractDTOList && sellerContractDTOList.size() > 0) {
				String expiryDate = sellerContractDTOList.get(0).getExpiryDate();
				String sysDate = DateUtil.getCurrentDateStr();
				if(Integer.parseInt(sysDate) > Integer.parseInt(expiryDate)) {
					this.addActionError("当前机构与上级发行机构合同已过期!");
					return INPUT;
				}
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			List<String> list=new ArrayList<String>();
			CardholderDTO cardholderDTO=new CardholderDTO();
			ImportServiceDTO importDTO=new ImportServiceDTO();
			List<ImportServiceDTO> importDtoList=new ArrayList<ImportServiceDTO>();
			List<CardholderDTO> cardholderDtoList =new ArrayList<CardholderDTO>();
			Map<String, String> productMap=new HashMap<String, String>();
			for(Integer key : readExcelContent2.keySet()){
				String cardholderMessageString = readExcelContent2.get(key);
				
				String[] cardholderMessage = cardholderMessageString.split("-");
				// 当末尾有空内容时，split方法取不到20个参数,要在末尾补上空格
				if (cardholderMessage.length < 26) {
					this.addActionError("第" + key + "行末尾为空，或者文件格式有误（缺少列）！");
					return INPUT;
				}

				// 校验文本是否有特殊字符
				if (cardholderMessage.length > 26) {
					this.addActionError("第" + key + "行内容可能带有“-”字符,或者列数大于20列！");
					return INPUT;
				}
				checkFile(key, cardholderMessage);
				if (hasActionErrors()) {
					return INPUT;
				}
				
				if(cardholderMessage[20].trim().equals("")){
					this.addActionError("第"+key+"行客户合同失效时间不能为空！");
					return INPUT;
				}
				if(Integer.parseInt(DateUtil.getCurrentDateStr()) > Integer.parseInt(cardholderMessage[20].trim())) {
					expiryDateList.add("第"+key+"行客户合同失效时间不能小于系统当前时间");
				}
				if(isValidDate(cardholderMessage[20])==false){
					checkDate.add("第"+key+"行客户合同失效时间转换有误，请确认！");
				}
				
				if(cardholderMessage[21].trim().equals("")){
					this.addActionError("第"+key+"行客户合同快递费不能为空！");
					return INPUT;
				}
				if(cardholderMessage[22].trim().equals("")){
					this.addActionError("第"+key+"行产品号不能为空！");
					return INPUT;
				}
				if(cardholderMessage[23].trim().equals("")){
					this.addActionError("第"+key+"行第一次充值金额不能为空！");
					return INPUT;
				}
				if(!IDCardCheck.isNumeric(cardholderMessage[23].trim())){
					this.addActionError("第"+key+"行第一次充值金额应该为正整数或零！");
					return INPUT;
				}
				
				if(cardholderMessage[24].trim().equals("")){
					this.addActionError("第"+key+"行卡面不能为空！");
					return INPUT;
				}
				if(cardholderMessage[25].trim().equals("")){
					this.addActionError("第"+key+"行包装不能为空！");
					return INPUT;
				}
				ImportServiceDTO importServiceDTO=new ImportServiceDTO();
				SellerProductContractDTO sellerProductContractDTO=new SellerProductContractDTO();
				SellOrderInputDTO sellOrderInputDTO=new SellOrderInputDTO();
				SellOrderDTO sellOrderDTO=new SellOrderDTO();
				sellOrderDTO.setFaceValue(cardholderMessage[23]);
				sellOrderDTO.setCardLayoutId(cardholderMessage[24]);
				sellOrderDTO.setPackageId(cardholderMessage[25]);
				sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
				importServiceDTO.setSellOrderInputDTO(sellOrderInputDTO);
				sellerProductContractDTO.setProductId(cardholderMessage[22]);
				importServiceDTO.setSellerProductContractDTO(sellerProductContractDTO);
				//卡产品汇总
				if(productMap.containsKey(cardholderMessage[22].trim())){
					int cunt=Integer.parseInt(productMap.get(cardholderMessage[22].trim()))+1;
					productMap.put(cardholderMessage[22].trim(), cunt+"");
				}else{
					productMap.put(cardholderMessage[22].trim(), "1");
				}
				
				importDtoList.add(importServiceDTO);
				CardholderDTO cardholderDto=new CardholderDTO();
				cardholderDto.setIdType(cardholderMessage[4].trim());
				cardholderDto.setIdNo(cardholderMessage[5].trim());
				cardholderDto.setCardholderEmail(cardholderMessage[22].trim());//把卡产品暂存到这个字段
				cardholderDtoList.add(cardholderDto);
				
				
				//证件类型，证件号，卡产品【拼接字符，放入list，用于判断同一文件有无重复售卡
				list.add(cardholderMessage[4].trim()+cardholderMessage[5].trim()+cardholderMessage[22].trim());
			}

			//检查卡产品
			importDTO.setImportServiceDtoList(importDtoList);
			importDTO.setMap(productMap);
			ImportServiceDTO importServiceDTOList=(ImportServiceDTO)this.sendService("2015123101", importDTO).getDetailvo();
			
			//检查重复售卡
			cardholderDTO.setCardholderDTOList(cardholderDtoList);
//			ImportServiceDTO importServiceDTOErrList=(ImportServiceDTO)this.sendService("2016021802", cardholderDTO).getDetailvo();
			
			if(errRows.size()>0){
				this.addActionError("文件检查错误！");
				downLoad(errRows);
				return INPUT;
			}
			if(IdCard.size()>0){
				
				this.addActionError("文件检查错误！");
				downLoad(IdCard);
				return INPUT;
			}
			if(birthday.size()>0){
				downLoad(birthday);
				this.addActionError("文件检查错误！");
				return INPUT;
			}

			if(customerPostcode.size()>0){
				
				this.addActionError("文件检查错误！");
				downLoad(customerPostcode);
				return INPUT;
			}
			if(expiryDateList.size()>0){
				
				this.addActionError("文件检查错误！");
				downLoad(expiryDateList);
				return INPUT;
			}
			if(checkDate.size()>0){
				
				this.addActionError("文件检查错误！");
				downLoad(checkDate);
				return INPUT;
			}
			
			if(importServiceDTOList.getList()!=null&&importServiceDTOList.getList().size()>0){
				this.addActionError("文件检查错误！");
				downLoad(importServiceDTOList.getList());
				return INPUT;
			}
			batchCardHolderMessageDTO.setFileMap(readExcelContent2);
			
			is.close();
			if(readExcelContent2==null||readExcelContent2.size()<=0){
				this.addActionError("文件内容不能为空！");
				return INPUT;
			}
			
			batchCardHolderMessageDTO.setBatchFile(null);
			OperationResult sendService = this.sendService("2015122501", batchCardHolderMessageDTO);
			if(sendService!=null){
				if(sendService.getTxnstate()==null||!sendService.getTxnstate().equals("1")){
					this.clearActionErrors();
					this.addActionError(sendService.getErrMessage());
					return INPUT;
				}else{
					this.addActionMessage("导入成功");
					return SUCCESS;
				}
			}else{
				this.addActionError("系统异常！");
				return INPUT;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			this.addActionError("文件不存在！");
			return INPUT;
		} catch (IOException e) {
			e.printStackTrace();
			this.addActionError("读取文件错！");
			return INPUT;
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
		
	}
	
	private void checkFile(int key, String[] cardholderMessage) {// 批量导入的文件中公有字段的校验
		/* 校验必填项 */
		if (cardholderMessage[0].trim().equals("") && cardholderMessage[0].trim().length() < 128) {
			this.addActionError("第" + key + "行姓名不能为空并且不能超过128个字符！");
			return;
		}
		if (!CharUtil.isChinese(cardholderMessage[0].trim())) {
			this.addActionError("第" + key + "行姓名包含非法字符！");
			return;
		}
		if (!cardholderMessage[1].trim().matches("^[0-2]$")) {
			this.addActionError("第" + key + "行性别填入有误！");
			return;
		}
		if (!cardholderMessage[2].trim().matches("^[0-9]{1,11}$")) {
			this.addActionError("第" + key + "行电话输入有误！");
			return;
		}
		if (cardholderMessage[3].trim().equals("") || cardholderMessage[3].length() > 75) {
			this.addActionError("第" + key + "行地址不能为空且超过75个字符！");
			return;
		}
		if (cardholderMessage[4].trim().equals("") || !cardholderMessage[4].trim().matches("^[1-5]$")) {
			this.addActionError("第" + key + "行证件类型有误！");
			return;
		}
		if (cardholderMessage[5].trim().equals("")) {
			this.addActionError("第" + key + "行证件号不能为空！");
			return;
		}
		// 如果选择身份证，对身份证进行校验
		if (cardholderMessage[4].trim().equals("1")
				&& !cardholderMessage[5].trim().matches("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)")) {
			this.addActionError("第" + key + "行证件号码(身份证)格式有误！");
			return;
		}
		if (cardholderMessage[6].trim().equals("") || !isValidDate(cardholderMessage[6])) {
			this.addActionError("第" + key + "行生日有误！");
			return;
		}

		if (StringUtils.isEmpty(cardholderMessage[15].trim()) || !isValidDate(cardholderMessage[15])) {
			this.addActionError("第" + key + "行证件失效日期有误！");
			return;
		}
		// 证件失效日期校验
		if (StringUtils.isNotEmpty(cardholderMessage[15])) {
			if (isValidDate(cardholderMessage[15].trim()) == false) {
				this.addActionError("第" + key + "行证件失效日期格式不正确！");
				return;
			}
		}
		if (StringUtils.isNotEmpty(cardholderMessage[11])
				&& !(cardholderMessage[11].trim().matches("^[0-3]$") || cardholderMessage[11].trim().equals("10000"))) {
			this.addActionError("第" + key + "行持卡人分类填入有误！");
			return;
		}
		if (!cardholderMessage[12].trim().matches("^[1-8]$")) {
			this.addActionError("第" + key + "行客户职业类别输入有误！");
			return;
		}
		if (!cardholderMessage[13].trim().matches("^[1-4]$")) {
			this.addActionError("第" + key + "行渠道信息输入有误！");
			return;
		}
		if (cardholderMessage[18].trim().equals("")) {
			this.addActionError("第" + key + "行邮编不能为空！");
			return;
		}

		// 生日校验
		if (isValidDate(cardholderMessage[6].trim()) == false) {
			this.addActionError("第" + key + "行生日日期格式不正确！");
			return;
		}
		// 邮编格式校验
		if (!cardholderMessage[18].matches("^[0-9]\\d{5}$")) {
			this.addActionError("第" + key + "行邮编格式不正确！");
			return;
		}
		if (!cardholderMessage[19].matches("(^[1-9]$)|(^[1-4][0-9]$)|(^5[0-7]$)")) {
			this.addActionError("第" + key + "行民族数据有误！");
			return;
		}
	}
	
	private  List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
	
	
	public List<ProductDTO> getProductDTOs() {
		return productDTOs;
	}

	public void setProductDTOs(List<ProductDTO> productDTOs) {
		this.productDTOs = productDTOs;
	}

	//	public 
	//批量销售不记名卡
	@SuppressWarnings("unchecked")
	public String batchImportUnsignOrder(){
		try {
			ImportServiceDTO importServiceDTO=new ImportServiceDTO();
			SellOrderInputDTO sellOrderInputDTO=new SellOrderInputDTO();
			sellOrderInputDTO.setCustomerDTO(customerDTO);
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
			importServiceDTO.setOrderListInfo(orderListStr);
			importServiceDTO.setSellOrderInputDTO(sellOrderInputDTO);
			this.sendService("2016110101", importServiceDTO);
			if (hasErrors()) {
				return INPUT;
			}
			this.addActionMessage("导入成功");
			return SUCCESS;
		} finally {
			// TODO: handle exception
			//清空页面信息
			sellOrderDTO=null;
			customerDTO=null;
			orderListStr=null;
		}
		
			
		
		
	}
	 public static boolean isValidDate(String str) {
	        boolean convertSuccess=true;
	        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
	         SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	         try {
	        	 // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
	            format.setLenient(false);
	            format.parse(str);
	         } catch (ParseException e) {
	           // e.printStackTrace();
	 // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
	            convertSuccess=false;
	        } 
	        return convertSuccess;
	}

	public void downLoad(List<String> str){
		String txtName = "导入错误列表"+ DateUtil.getCurrentTime();
		StringBuffer result = new StringBuffer();
		result.append("   ").append("错误描述").append("\r\n");
			for (String ca : str) {
				result.append("   ").append(ca+"请重新编辑文件！").append("\r\n");
			}
		HttpServletResponse response = this.getResponse();
		// http响应，生成下载文件
		try {
			response.setContentType("application/msword");
			response.setContentType("text/html;charset=gbk");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ new String(txtName.getBytes("gb2312"), "ISO8859-1")
					+ ".txt");
			response.getWriter().print(result.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.getMessage();
		}
		
	}
	public static void main(String[] args) {
		String sysDate = DateUtil.StringDate("2052-01-10");
		String sysDates=DateUtil.formatStringDate("20520110");
		System.out.println(isValidDate("205002012"));
		System.out.println(sysDate+","+sysDates);
		
	}
}
