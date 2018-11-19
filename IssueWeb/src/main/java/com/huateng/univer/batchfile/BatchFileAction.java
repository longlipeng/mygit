package com.huateng.univer.batchfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.allinfinance.batchImport.BatchImportCustomerAndCardHolder;
import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.BatchActivateDTO;
import com.allinfinance.framework.dto.BatchRechargeDTO;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.businessbatch.dto.BatchFileDetailDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderQueryDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.CsvUtil;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ExcelReader;

public class BatchFileAction  extends BaseAction {
	private Logger logger = Logger.getLogger(BatchFileAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String order_id;
	private String batch_id;
	private String batch_type;
	private String flag ="0";
	private int totalRows;
	private String sucessMessage;
	private String batchNo;
	private PageDataDTO pageDataDTO= new PageDataDTO();
	private SellOrderCardListDTO sellOrderCardListDTO = new SellOrderCardListDTO();
	private SellOrderQueryDTO sellOrderQueryDTO = new SellOrderQueryDTO();
	private BatchRechargeDTO batchRechargeDTO=new BatchRechargeDTO();
	private BatchActivateDTO batchActivateDTO=new BatchActivateDTO();
	private File batchFile;
	private String batchFileFileName;
	private static final String FIRST_RECHARGE = "01";
	private static final String ACTIVATE = "02";
	private static final String RECHARGE_AGAIN = "03";
	public String rechargeActView(){
		return "view";
	}
	public String rechargeView(){
		return "view";
	}
	public String makeCardView(){
		return "view";
	}
	public String ransomView(){
		return "view";
	}
	public String actView(){
		return "view";
	}
	public void getOrderState(){
		try {
			SellOrderDTO sellOrderDTO=new SellOrderDTO();		
			sellOrderDTO.setOrderId(order_id);
			sellOrderDTO.setBatchType(batch_type);
			String result=(String)sendService(ConstCode.GET_ORDER_STATE, sellOrderDTO).getDetailvo();
			if(null!=result){
				getResponse().getWriter().print("success");
			}
		} catch (Exception b) {
			logger.error(b.getMessage());
		}
		
	}
	
	public String getRechargeActBatchDetail() throws BizServiceException{
		try {
			BatchFileDetailDTO batchFileDetailDTO = new BatchFileDetailDTO();
			ListPageInit(null, batchFileDetailDTO);
			batchFileDetailDTO.setOrderId(order_id);
			batchFileDetailDTO.setBatchNO(batch_id);
			pageDataDTO=(PageDataDTO)sendService(ConstCode.GET_RECHARGE_ACT_BATCH_DETAIL, batchFileDetailDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (this.hasErrors()) {
				return INPUT;
			}
			return "rechargeActList";
		} catch (Exception b) {
			logger.error(b.getMessage());
			throw new BizServiceException("查询明细失败");
		}
	}

	public String getRechargeBatchDetail() throws BizServiceException{
		try {
			BatchFileDetailDTO batchFileDetailDTO = new BatchFileDetailDTO();
			ListPageInit(null, batchFileDetailDTO);
			batchFileDetailDTO.setOrderId(order_id);
			batchFileDetailDTO.setBatchNO(batch_id);
			pageDataDTO=(PageDataDTO)sendService(ConstCode.GET_RECHARGE_BATCH_DETAIL, batchFileDetailDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (this.hasErrors()) {
				return INPUT;
			}
			ArrayList<HashMap> list= (ArrayList<HashMap>) pageDataDTO.getData();
			if(list.size()>0){
				HashMap map =list.get(0);
				this.getRequest().getSession().setAttribute("card_no", map.get("cardNo"));
			}else{
				this.getRequest().getSession().setAttribute("card_no", "");
			}
			
			return "rechargeList";
		} catch (Exception b) {
			
			logger.error(b.getMessage());
			throw new BizServiceException("查询明细失败");
		}
	}
	public String changeCardNo() {
		sellOrderQueryDTO.setOldCardNo((String) this.getSession().get("card_no"));
		sellOrderQueryDTO.setOrderId(order_id);
		Integer state = (Integer) this.sendService(ConstCode.CHANGE_CARD, sellOrderQueryDTO).getDetailvo();;
		if(state == 0) {
			sucessMessage = "卡号信息只允许更换一次!";
			return "changeCardNo";
		}
		if( hasErrors()){
			sucessMessage = "修改卡号失败!";
			return "changeCardNo";
		}
		sucessMessage = "修改卡号成功!";
//		this.addActionMessage("添加卡号成功!");
		return "changeCardNo";
	}
	
	public String getMakeCardBatchDetail() throws BizServiceException{
		try {
			BatchFileDetailDTO batchFileDetailDTO = new BatchFileDetailDTO();
			ListPageInit(null, batchFileDetailDTO);
			batchFileDetailDTO.setOrderId(order_id);
			batchFileDetailDTO.setBatchNO(batch_id);
			pageDataDTO=(PageDataDTO)sendService(ConstCode.GET_MAKE_CARD_BATCH_DETAIL, batchFileDetailDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (this.hasErrors()) {
				return INPUT;
			}
			return "makeCardList";
		} catch (Exception b) {
			
			logger.error(b.getMessage());
			throw new BizServiceException("查询明细失败");
		}
	}
	
	public String getRansomBatchDetail() throws BizServiceException{
		try {
			BatchFileDetailDTO batchFileDetailDTO = new BatchFileDetailDTO();
			ListPageInit(null, batchFileDetailDTO);
			batchFileDetailDTO.setOrderId(order_id);
			batchFileDetailDTO.setBatchNO(batch_id);
			pageDataDTO=(PageDataDTO)sendService(ConstCode.GET_RANSOM_BATCH_DETAIL, batchFileDetailDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (this.hasErrors()) {
				return INPUT;
			}
			return "ransomList";
		} catch (Exception b) {
			
			logger.error(b.getMessage());
			throw new BizServiceException("查询明细失败");
		}
	}
	public String getActBatchDetail() throws BizServiceException{
		try {
			BatchFileDetailDTO batchFileDetailDTO = new BatchFileDetailDTO();
			ListPageInit(null, batchFileDetailDTO);
			batchFileDetailDTO.setOrderId(order_id);
			batchFileDetailDTO.setBatchNO(batch_id);
			pageDataDTO=(PageDataDTO)sendService(ConstCode.GET_ACT_BATCH_DETAIL, batchFileDetailDTO).getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (this.hasErrors()) {
				return INPUT;
			}
			return "actList";
		} catch (Exception b) {
			
			logger.error(b.getMessage());
			throw new BizServiceException("查询明细失败");
		}
	}
	public String batchRechargeAction() throws BizServiceException{
		InputStream  is=null;
		
		try {
			
			if(batchFile==null||batchFile.length()<=0){
				this.addActionError("文件不能为空文件！");
				return INPUT;
			}
			
			is = new FileInputStream(batchFile);
			
			ExcelReader reader = new ExcelReader();
			Map<Integer, String> readExcelContent2 = null;
			try {
				readExcelContent2 = reader.readExcelContent2(is);
			} catch (Exception e) {
				// TODO: handle exception
				this.addActionError("文件格式有误！");
				return INPUT;
			}
			//Map<Integer, String> readExcelContent2 = reader.readExcelContent2(is);
			if(readExcelContent2==null||readExcelContent2.size()<=0){
				this.addActionError("文件内容不能为空！");
				return INPUT;
			}
			if(!batchRechargeDTO.getCountNum().equals(readExcelContent2.size()+"")){
				this.addActionError("上传记录数与输入记录数不一致！");
				return INPUT;
			}
			if(readExcelContent2.size()>200){
				this.addActionError("上传记录数不能超过200条！");
				return INPUT;
			}
			List<String> errmgs=new ArrayList<String>();
			List<String> list=new ArrayList<String>();
			List<String> cardList=new ArrayList<String>();
			DecimalFormat df=new DecimalFormat("0");
			for(Integer key : readExcelContent2.keySet()){
				String messageString = readExcelContent2.get(key);
				
				String[] cardMessage= messageString.split("-");
				//当行有空内容时，即一行不足三列时。
				if(cardMessage.length<3){
					this.addActionError("第"+key+"行，列内容不能为空！");
					return INPUT;
				}
				
				//校验文本是否有特殊字符
				if(cardMessage.length>3){
					errmgs.add("第"+key+"行内容可能带有“-”字符,或者列数大于3列！");
				}
				/*校验必填项*/
				if(cardMessage[0].trim().equals("")){
					this.addActionError("第"+key+"行卡号不能为空！");
					return INPUT;
				}else{
					if(cardMessage[0].trim().matches("^\\d+$")==false){
						 errmgs.add("第"+key+"行，文件中卡号有误，请检查文件！");
					}
				}
				if(cardMessage[1].trim().equals("")){
					this.addActionError("第"+key+"行金额不能为空！");
					return INPUT;
				}else{
					boolean isNum = cardMessage[1].trim().matches("([1-9][0-9]*)+(.[0-9]{1,2})?");
					if(isNum==false){
		    			 errmgs.add("第"+key+"行，文件中充值金额有误，请检查文件！");
		    		 }else{
		    			 Double ant = null;
		    			 try {
		    				 ant=Double.parseDouble( cardMessage[1].trim());
						} catch (Exception e) {
							// TODO: handle exception
							this.addActionError("第"+key+"行，文件中充值金额有误，请检查文件！");
							return INPUT;
						}
		    			
			 	    	 if(ant>1000000){
			 	    			errmgs.add("第"+key+"行，文件中充值金额过大，请检查文件！");
			 	    	 }
		    		 }
				}
				if(cardMessage[2].trim().equals("")){
					this.addActionError("第"+key+"行服务费不能为空！");
					return INPUT;
				}else{
					 boolean isNum = cardMessage[2].matches("([0-9][0-9]*)+(.[0-9]{1,2})?");
		    		 if(isNum==false){
		    			 errmgs.add("第"+key+"行，文件中服务费有误，请检查文件！");
		    		 }
				}
				if(errmgs.size()==0){
					Double fee=null;
					try {
						fee=Double.parseDouble(cardMessage[2].trim());
					} catch (Exception e) {
						// TODO: handle exception
						this.addActionError("第"+key+"行，文件中服务费有误，请检查文件！");
						return INPUT;
					}
					if(Float.parseFloat(cardMessage[1].trim())<fee){
						this.addActionError("第"+key+"行服务费不能大于充值金额！");
						return INPUT;
		    		 }
					Double reSum=Double.parseDouble(cardMessage[1])*100;
					int reSumInt=Integer.parseInt(df.format(reSum));
					Double serFee=Double.parseDouble(cardMessage[2])*100;
					int serFeeInt=Integer.parseInt(df.format(serFee));
					String rechargeSum=String.format("%09d", reSumInt);
		    		String serviceFee=String.format("%09d", serFeeInt);
		    		list.add(cardMessage[0].trim()+","+rechargeSum+","+serviceFee+"|");
		    		cardList.add(cardMessage[0].trim());
				}
				
			}
			is.close();
			HashSet set=new HashSet();
			   for(String i:cardList)
			     set.add(i);
			if(!(set.size()==cardList.size())){
				this.addActionError("文件出现重复数据，相同卡号。请检查文件！");
				return INPUT;
			 }
			if(errmgs.size()>0){
		   	 	BatchImportCustomerAndCardHolder importInfo=new BatchImportCustomerAndCardHolder();
		   	 	importInfo.downLoad(errmgs);
		   	 	return INPUT;
	   	 	}
			batchRechargeDTO.setList(list);
			batchRechargeDTO.setCreateUser(getUser().getUserId());
			OperationResult sendService = this.sendService(ConstCode.TO_BATCH_RECHARGE, batchRechargeDTO);
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
			this.addActionError("文件不存在！");
			return INPUT;
		} catch (IOException e) {
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
	//导入TXT
//	public String batchRechargeAction() throws BizServiceException{
//		 try {
//			if(new FileInputStream(batchFile).available()==0){
//				 this.addActionError("导入的文件内容为空！");
//				 return INPUT;
//			    }
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			this.addActionError("文件不存在！");
//			 return INPUT;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			this.addActionError("读取文件错！");
//			 return INPUT;
//		}
//		 
//		try {
//			LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(new FileInputStream(batchFile), FileOperate
//			            .getCharset(batchFile)));
//	
//		 
//		String strLine;
//		List<String> list=new ArrayList<String>();
//		 List<String> errmgs=new ArrayList<String>();
//		 Map<String, String> cardMap=new HashMap<String, String>();
//	    while ((strLine = lineNumberReader.readLine()) != null) {
//	    	 if (lineNumberReader.getLineNumber() != 1) {
//	    		 String[] strLines =null;
//	    		 try {
//	    			 if(strLine==null||strLine.equals("")){
//	    				 this.addActionError("文件中不能有空行！请检查文件");
//	 	 	 			return INPUT;
//	    			 }
//	    			strLines = strLine.split(";");
//				} catch (Exception e) {
//					// TODO: handle exception
//					this.addActionError("文件内容有误！请检查文件");
// 	 	 			return INPUT;
//				}
//	    		 
//	    		 if(strLines.length>3){
//	    			 this.addActionError("文件中第"+lineNumberReader.getLineNumber()+"行，文件内容格式有误！请检查文件！");
//	 	 			return INPUT;
//	    		 }else if(strLines.length<3){
//	    			 this.addActionError("文件中第"+lineNumberReader.getLineNumber()+"行，文件内容格式有误！请检查文件！");
//		 	 		 return INPUT;
//	    		 }else{
//	    		 if(null == strLines[0]||strLines[0].equals("")){
//	    			 errmgs.add("第"+lineNumberReader.getLineNumber()+"行，文件中卡号不能为空，请检查文件！");
//	    		 }
//	    		 
//	    		 if(null == strLines[1]||strLines[1].equals("")){
//	    			 errmgs.add("第"+lineNumberReader.getLineNumber()+"行，文件中充值金额不能为空，请检查文件！");
//	    			
//	    		 }else{    		
//	    		 boolean isNum = strLines[1].matches("([1-9][0-9]*)+(.[0-9]{1,2})?");
//	    		 if(isNum==false){
//	    			 errmgs.add("第"+lineNumberReader.getLineNumber()+"行，文件中充值金额有误，请检查文件！");
//	    		 }
//	    		 try {
//	    			 Double ant=Double.parseDouble(strLines[1]);
//	 	    		if(ant>5000){
//	 	    			errmgs.add("第"+lineNumberReader.getLineNumber()+"行，文件中充值金额不能大于5000，请检查文件！");
//	 	    		}
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//	    		
//	    		 }
//	    		 if(strLines.length<3){
//	    			 errmgs.add("第"+lineNumberReader.getLineNumber()+"行，文件中服务费不能为空，请检查文件！");
//	    		 }else{
//		    		 if(null == strLines[2]||strLines[2].equals("")){
//		    			 errmgs.add("第"+lineNumberReader.getLineNumber()+"行，文件中服务费不能为空，请检查文件！");
//		    		 }else{
//			    		 boolean isNum = strLines[2].matches("([0-9][0-9]*)+(.[0-9]{1,2})?");
//			    		 if(isNum==false){
//			    			 errmgs.add("第"+lineNumberReader.getLineNumber()+"行，文件中服务费有误，请检查文件！");
//			    		 }
//		    		 }
//	    		 }
//	    		 if(errmgs.size()==0){
//	    			 //金额转换
//	    			 cardMap.put(strLines[0].trim(), strLines[0].trim());
//		    		 String rechargeSum=String.format("%09d", (int)(Double.parseDouble(strLines[1].trim())*100));
//		    		 String serviceFee=String.format("%09d", (int)(Double.parseDouble(strLines[2].trim())*100));
//		    		 if(Double.parseDouble(strLines[1].trim())<Double.parseDouble(strLines[2].trim())){
//		    			 
//		    		 }
//		    		 list.add(strLines[0].trim()+","+rechargeSum+","+serviceFee+"|");
//	    		 }
//	    		 }
//	    		
//	    	 } 
//	    	 }
//	    int inputCount=Integer.parseInt(batchRechargeDTO.getCountNum());
//	    if(errmgs.size()==0){
//	    	 	if(list.size()!=inputCount){
//	 	   	 	this.addActionError("输入记录数与导入条数不一致！");
//	 			return INPUT;
//	 	    }
//	    }
//	   
//   	 	if(inputCount>100){
//	   	 	this.addActionError("一次最多充值100条！");
//			return INPUT;
//   	 	}
//   	 	
//   	 	if(errmgs.size()>0){
//	   	 	BatchImportCustomerAndCardHolder importInfo=new BatchImportCustomerAndCardHolder();
//	   	 	importInfo.downLoad(errmgs);
//   	 	}
//   	 	if(cardMap.size()<list.size()){
//	   	 	this.addActionError("文件中存在重复卡号！请检查文件！");
//			return INPUT;
//   	 	}
//   	 	batchRechargeDTO.setList(list);
//   	 	batchRechargeDTO.setCreateUser(getUser().getUserId());
//   	 	OperationResult sendService = this.sendService(
//   	 			ConstCode.TO_BATCH_RECHARGE, batchRechargeDTO);
//   	 	if(sendService!=null){
//			if(sendService.getTxnstate()==null||!sendService.getTxnstate().equals("1")){
//				this.clearActionErrors();
//				this.addActionError(sendService.getErrMessage());
//				return INPUT;
//			}else{
//				this.addActionMessage("导入成功");
//				return SUCCESS;
//			}
//		}else{
//			this.addActionError("系统异常！");
//			return INPUT;
//		}
//		
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			this.addActionError("文件不存在！");
//			return INPUT;
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			this.addActionError("读取文件错！");
//			return INPUT;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			this.addActionError("读取文件错！");
//			return INPUT;
//		}
//	}
	
	public String getBatchRechargeInfoAction() throws BizServiceException{
		try {
			ListPageInit(null, batchRechargeDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  BizServiceException("查询失败！");
		}
		String date = batchRechargeDTO.getCreateTime();
		String dateTemp="";
		if(!StringUtils.isEmpty(date)){
			dateTemp = date.replaceAll("-", "");
			batchRechargeDTO.setCreateTime(dateTemp);
		}
		pageDataDTO=(PageDataDTO)sendService(ConstCode.BATCH_RECHARGE, batchRechargeDTO).getDetailvo();
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		if(!StringUtils.isEmpty(date)){
			batchRechargeDTO.setCreateTime(date);
		}
		
		if (this.hasErrors()) {
			
			return INPUT;
		}
		return "rechargeInfoList";
	}
	
	public String getBatchActivateInfoAction() throws BizServiceException{
		try {
			ListPageInit(null, batchActivateDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  BizServiceException("查询失败！");
		}
		String beginTime = batchActivateDTO.getBeginTime();
		String endTime = batchActivateDTO.getEndTime();
		String bdateTemp="";
		String edateTemp="";
		if(!StringUtils.isEmpty(beginTime)){
			bdateTemp = beginTime.replaceAll("-", "");
			batchActivateDTO.setBeginTime(bdateTemp);
		}
		if(!StringUtils.isEmpty(endTime)){
			edateTemp = endTime.replaceAll("-", "");
			batchActivateDTO.setEndTime(edateTemp);
		}
		pageDataDTO=(PageDataDTO)sendService(ConstCode.BATCH_ACTIVATE, batchActivateDTO).getDetailvo();
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		if(!StringUtils.isEmpty(beginTime)){
			batchActivateDTO.setBeginTime(beginTime);
		}
		if(!StringUtils.isEmpty(endTime)){
			batchActivateDTO.setEndTime(endTime);
		}
		if (this.hasErrors()) {
			return INPUT;
		}
		return "activateInfoList";
	}
	
	
	//批量激活
	public String batchActivateAction() throws BizServiceException{
		String fileName = "";
		String cardNo = "";
		String cash = "";
		String param = "";
		String n = "";
		String flag = "";
		List<String> errmgs=new ArrayList<String>();
//		List<String> list=new ArrayList<String>();
		List<BatchActivateDTO> lists = new ArrayList<BatchActivateDTO>();
		List<BatchRechargeDTO> batchRechargeList = new ArrayList<BatchRechargeDTO>();
		DecimalFormat df=new DecimalFormat("0");
		List<String> cardList = new ArrayList<String>();
		List<String> cardlist = new ArrayList<String>();
//		String regex = "[\u4E00-\u9FA5]+";
		cardList = CsvUtil.importCsv(batchFile);
		if (cardList.size()<=0){
			this.addActionError("文件不能为空文件！");
			return INPUT;
		}
		fileName = batchFileFileName.substring(0, batchFileFileName.indexOf("."))+"("+DateUtil.getCurrentTime24()+")";
		if (fileName.length()>50){
			this.addActionError("文件名过长，请修改文件名后重新上传");
			return INPUT;
		}
//		String p = cardList.get(0).trim().replaceAll(",", "");
		if(StringUtils.isEmpty(cardList.get(0).trim())){
			this.addActionError("第1行表头不能为空，请检查文件！");
			return INPUT;
		}
		flag = batchActivateDTO.getFlag();
		for (int i=1 ; i<cardList.size(); i++){
			BatchActivateDTO bDto = new BatchActivateDTO();
			param = cardList.get(i).trim();
			if (StringUtils.isEmpty(param)){
				continue;
			}
			n = param.substring(0, 1);
			int j = i+1;
			if (n.equals("N")==false){
				this.addActionError("第"+j+"行卡号前面必须加N，请检查文件！");
				return INPUT;
			}
			if (flag.equals(ACTIVATE) ){
				if (param.contains(",")){
					this.addActionError("文件格式有误，请检查文件！");
					return INPUT;
				}
				cardNo = param.substring(1, param.length());
			}else{
				if (!param.contains(",")){
					this.addActionError("文件格式有误，请检查文件！");
					return INPUT;
				}
				cardNo = param.substring(1, param.indexOf(","));
				cash = param.substring(param.indexOf(",")+1, param.length()).trim();
				if (cash.contains(",")){
					cash = param.substring(param.indexOf(",")+1, param.length()-1).trim();
				}else{
					cash = param.substring(param.indexOf(",")+1, param.length()).trim();
				}
				bDto.setActivateAmt(cash);
				if (cash.equals("")){
					errmgs.add("第"+j+"行，文件中金额为空，请检查文件！");
				}else if(cash.matches("^(?!0)(?:[0-9]{1,3}|1000)$")==false){
					errmgs.add("第"+j+"行金额有误，只能为1到1000的正整数！");
				}else {
					boolean isNum = cash.trim().matches("([1-9][0-9]*)+(.[0-9]{1,2})?");
					if(isNum==false){
		    			 errmgs.add("第"+j+"行，文件中充值金额有误，请检查文件！");
		    		 }else if (isMatchCardNo(cash.trim(),cardNo)==false && flag.equals(FIRST_RECHARGE)){
		    			 errmgs.add("第"+j+"行，文件中充值金额跟卡号不对应，且金额必须小于1000，请检查文件！");
		    		 }else{
		    			 Double ant = null;
		    			 try {
		    				 ant=Double.parseDouble( cash.trim());
						} catch (Exception e) {
							// TODO: handle exception
							this.addActionError("第"+j+"行，文件中充值金额有误，请检查文件！");
							return INPUT;
						}
		    			
			 	    	 if(ant>1000000){
			 	    			errmgs.add("第"+j+"行，文件中充值金额过大，请检查文件！");
			 	    	 }
		    		 }
				}
			}
			bDto.setCardNo(cardNo);
			if (cardNo.matches("^\\d{19}|\\d{16}$")==false){
				errmgs.add("第"+j+"行，文件中卡号有误，请检查文件！");
			}
			if (cardList.size()-1>200){
				this.addActionError("导入记录数不能超过200条");
				return INPUT;
			}
			String rechargeSum = "";
			if(errmgs.size()==0){
				if (!StringUtils.isEmpty(cash)){
					Double reSum=Double.parseDouble(cash)*100;
					int reSumInt=Integer.parseInt(df.format(reSum));
					rechargeSum=String.format("%09d", reSumInt);
//					list.add(cardNo+","+rechargeSum+"|");
				}
	    		cardlist.add(cardNo.trim());
	    		lists.add(bDto);
			}
			if(errmgs.size()>0){
		   	 	BatchImportCustomerAndCardHolder importInfo=new BatchImportCustomerAndCardHolder();
		   	 	importInfo.downLoad(errmgs);
		   	 	return INPUT;
	   	 	}
			BatchRechargeDTO batchRechargeDTO = new BatchRechargeDTO();
			batchRechargeDTO.setSmltDate(DateUtil.getCurrentDateStr());
			batchRechargeDTO.setSmltTime(DateUtil.getCurrenTime24());
			batchRechargeDTO.setRechargeType(flag);
			batchRechargeDTO.setCardNo(cardNo);
			batchRechargeDTO.setRechargeSum(StringUtils.isEmpty(rechargeSum)?"000000000":rechargeSum);
			batchRechargeDTO.setRechargeFee("000000000");
			batchRechargeDTO.setRechargeState("01");	//处理中
			batchRechargeDTO.setCreateUser(getUser().getUserId());
			batchRechargeDTO.setCreateTime(DateUtil.getCurrentTime24());
			batchRechargeList.add(batchRechargeDTO);
		}
		HashSet set=new HashSet();
		   for(String k:cardlist){
			   set.add(k);
		   }
		if(!(set.size()==cardlist.size())){
			this.addActionError("文件中存在重复卡号，请检查文件！");
			return INPUT;
		 }
		if (cardlist.size() != cardList.size()-1){
			this.addActionError("文件格式错误，请用excel编辑文件并另存为csv文件！");
			return INPUT;
		}
//		batchActivateDTO.setList(list);
		batchActivateDTO.setCreateUser(getUser().getUserId());
		batchActivateDTO.setBatList(lists);
		batchActivateDTO.setFileName(fileName);
		batchActivateDTO.setRecList(batchRechargeList);
		batchActivateDTO.setCreateTime(DateUtil.getCurrentTime24());
		batchActivateDTO.setCardNum(String.valueOf(cardlist.size()));
		this.sendService(ConstCode.TO_BATCH_ACTIVATE, batchActivateDTO);
//		if(sendService!=null){
//			if(sendService.getTxnstate()==null||!sendService.getTxnstate().equals("1")){
//				this.clearActionErrors();
//				this.addActionError(sendService.getErrMessage());
//				return INPUT;
//			}else{
//				//导入成功，插表
				this.addActionMessage("导入成功");
				return SUCCESS;
//			}
//		}else{
//			this.addActionError("导入失败！");
//			return INPUT;
//		}
	}
	
	public String toBatchRechargeInfoAction() throws BizServiceException{
		
		return "tochargeInfo";
	}
	
	public String toBatchActivateInfoAction() throws BizServiceException{
		
		return "toActivateInfo";
	}
	
	public String batchActivateDetail()throws BizServiceException{
		try {
			ListPageInit(null, batchActivateDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  BizServiceException("查询失败！");
		}
		pageDataDTO=(PageDataDTO)sendService(ConstCode.ACTIVATE_CARD_LIST, batchActivateDTO).getDetailvo();
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		if (this.hasErrors()) {
			this.addActionError("系统异常！");
			return INPUT;
		}
		return SUCCESS;
	}
	
	public String getBatchActivateCardDetail()throws BizServiceException{
		try {
			ListPageInit(null, batchActivateDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  BizServiceException("查询失败！");
		}
		pageDataDTO=(PageDataDTO)sendService(ConstCode.ACITVATE_DETAIL, batchActivateDTO).getDetailvo();
		if (pageDataDTO != null) {
			totalRows = pageDataDTO.getTotalRecord();
		}
		if (this.hasErrors()) {
			this.addActionError("系统异常！");
			return INPUT;
		}
		return SUCCESS;
	}
	
	/**
	 * 用于判断卡号和金额是否匹配
	 * @param cash
	 * @param cardNo
	 */
	private static boolean isMatchCardNo(String cash,String cardNo){
		String newCash = String.format("%0"+4+"d",Integer.parseInt(cash));
		if ("00".equals(cardNo.substring(6, 8))){
			if (Integer.parseInt(cash)>1000){
				return false;
			}
			return true;
		}
		if ("00".equals(newCash.substring(2, newCash.length()))){
			return newCash.substring(0, 2).matches(cardNo.substring(6, 8));
		}else{
			return false;
		}
	}
	
	public BatchRechargeDTO getBatchRechargeDTO() {
		return batchRechargeDTO;
	}
	public void setBatchRechargeDTO(BatchRechargeDTO batchRechargeDTO) {
		this.batchRechargeDTO = batchRechargeDTO;
	}
	public BatchActivateDTO getBatchActivateDTO() {
		return batchActivateDTO;
	}
	public void setBatchActivateDTO(BatchActivateDTO batchActivateDTO) {
		this.batchActivateDTO = batchActivateDTO;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String orderId) {
		order_id = orderId;
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batchId) {
		batch_id = batchId;
	}
	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}
	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}
	public String getBatch_type() {
		return batch_type;
	}
	public void setBatch_type(String batchType) {
		batch_type = batchType;
	}
	public SellOrderCardListDTO getSellOrderCardListDTO() {
		return sellOrderCardListDTO;
	}
	public void setSellOrderCardListDTO(SellOrderCardListDTO sellOrderCardListDTO) {
		this.sellOrderCardListDTO = sellOrderCardListDTO;
	}
	public String getSucessMessage() {
		return sucessMessage;
	}
	public void setSucessMessage(String sucessMessage) {
		this.sucessMessage = sucessMessage;
	}
	public SellOrderQueryDTO getSellOrderQueryDTO() {
		return sellOrderQueryDTO;
	}
	public void setSellOrderQueryDTO(SellOrderQueryDTO sellOrderQueryDTO) {
		this.sellOrderQueryDTO = sellOrderQueryDTO;
	}
	public File getBatchFile() {
		return batchFile;
	}
	public void setBatchFile(File batchFile) {
		this.batchFile = batchFile;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getBatchFileFileName() {
		return batchFileFileName;
	}
	public void setBatchFileFileName(String batchFileFileName) {
		this.batchFileFileName = batchFileFileName;
	}
	
	
}
