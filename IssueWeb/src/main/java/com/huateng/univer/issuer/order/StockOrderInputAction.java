package com.huateng.univer.issuer.order;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.util.ServletContextAware;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.service.uploadFile.dto.UploadCardFileDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.Amount;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.ExcelReader;
import com.huateng.framework.util.SystemInfo;

import java.io.*;

public class StockOrderInputAction extends StockOrderBaseAction {
	
	private Logger logger = Logger.getLogger(StockOrderInputAction.class);
	private static final long serialVersionUID = -1493680757440953878L;

	private String svalidityPriod;
	private File cardNoFile;
	private boolean flag=false;
	 FileOutputStream fileoutputstream=null;
	private String path;
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List getRepCard() {
		return repCard;
	}

	public void setRepCard(List repCard) {
		this.repCard = repCard;
	}
	private List repCard;
    public String getFileName() throws UnsupportedEncodingException {
		return this.fileName=new String(this.fileName.getBytes(),"ISO-8859-1");
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	//private String fileName=new String("导出重复卡号.xls");
	private String fileName;
    private InputStream inputStream;
    
	
	public File getCardNoFile() {
		return cardNoFile;
	}

	public void setCardNoFile(File cardNoFile) {
		this.cardNoFile = cardNoFile;
	}

	public String getSvalidityPriod() {
		return svalidityPriod;
	}

	public void setSvalidityPriod(String svalidityPriod) {
		this.svalidityPriod = svalidityPriod;
	}

	protected void initActionName() {
		actionName = "stockOrderInputAction";
	}

	protected void inqueryInit() {
		// 订单类型： 制卡订单
		//sellOrderQueryDTO.setOrderType(OrderConst.ORDER_TYPE_ORDER_MAKE_CARD);
		// 订单状态：1草稿
		sellOrderQueryDTO.setOrderState(OrderConst.ORDER_STATE_DRAFT);
	}

	protected void initInput() {
		productDTOs = sellOrderInputDTO.getProductDTOs();
		ProductDTO productDTO = sellOrderInputDTO.getProductDTO();
		sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();
		// sellOrderDTO.setProcessEntityName(getUser().getSellerName());
		HttpServletRequest request = ServletActionContext.getRequest();
		if (productDTO != null && productDTO.getProductId() != null
				&& !"".equals(productDTO.getProductId())) {
			// 卡面列表
			cardLayouts = productDTO.getCardLayoutDTOs();
			// 服务列表
			services = productDTO.getServices();
			// 面额列表
			prodFaceValues = productDTO.getProdFaceValueDTO();
			// productName = productDTO.getProductName();
			productCardBinDTO=productDTO.getProductCardBinDTOs().get(0);
			request.setAttribute("binType",productCardBinDTO.getBinType() );
			// 卡有效期
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String validityPeriod = df.format(DateUtil.countCardValidate(new Date(),Integer.parseInt(productDTO.getValidityPeriod())));
			sellOrderDTO.setValidityPeriod(validityPeriod);
			
		}
	}
	
	protected void initEdit() {
		productDTOs = sellOrderInputDTO.getProductDTOs();
		ProductDTO productDTO = sellOrderInputDTO.getProductDTO();
		sellOrderDTO = sellOrderInputDTO.getSellOrderDTO();

		if (productDTO != null && productDTO.getProductId() != null
				&& !"".equals(productDTO.getProductId())) {
			// 卡面列表
			cardLayouts = productDTO.getCardLayoutDTOs();
			// 服务列表
			services = productDTO.getServices();
			// 面额列表
			prodFaceValues = productDTO.getProdFaceValueDTO();
			// productName = productDTO.getProductName();
		}
	}
	
	@SkipValidation
	public String add() throws Exception {
		try {
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
			sellOrderInputDTO = (SellOrderInputDTO) this.sendService(
					ConstCode.STOCK_ORDER_INIT, sellOrderInputDTO)
					.getDetailvo();
			this.initInput();
			if(repCard!=null&&repCard.size()>0)
			{
				upload();
			}
			this.getRequest().setAttribute("productType", sellOrderInputDTO.getProductDTO().getOnymousStat());
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "add";
	}

	public void validateInsert(){
		try {
			this.add();
			errorJsp = "WEB-INF/pages/univer/issuer/order/orderinput/stockOrderAdd.jsp";
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		
	}
	
	public String insert() throws Exception {
		try {
			//BigDecimal faceValue = new BigDecimal(sellOrderDTO.getFaceValue()).multiply(new BigDecimal(100));
			//sellOrderDTO.setFaceValue(faceValue.toString());
			logger.debug("卡BIN类型："+uploadCardFileDTO.getBinType());
			logger.debug("制卡文件："+uploadCardFileDTO.getCardNoFile());
			if(uploadCardFileDTO.getBinType()!=null&&uploadCardFileDTO.getBinType().equals("1")){
				if(uploadCardFileDTO.getCardNoFile()==null){
					this.addActionError("必须输入文件！");
					return this.add();
				}
				List<String> cardNos = new ArrayList<String>();
				InputStream  is=null;
				Map<Integer, String> readExcelContent =null;
				try {
					//File  file = new File(uploadCardFileDTO.getFilepath());
					File file = uploadCardFileDTO.getCardNoFile();
					is = new FileInputStream(file);
					ExcelReader reader = new ExcelReader();
					readExcelContent= reader.readExcelContent(is);
					is.close();
					
				} catch (FileNotFoundException e) {
					this.addActionError("文件不存在！");
					return this.add();
				} catch (IOException e) {
					this.addActionError("读取文件错！");
					return this.add();
				}finally{
					if(is!=null){
						is.close();
					}
				}
//				long size = uploadCardFileDTO.getCardNoFile().length();
//				if(size>1024*1024){
//					addActionError("只能上传小于1M的文件");
//					return this.add();
//				}
				if(readExcelContent==null||readExcelContent.size()==0){
					this.addActionError("文件中没有卡号！");
					return this.add();
				}else{
					//readExcelContent的key从1开始
					for(int i=1;i<readExcelContent.size()+1;i++){
						if(readExcelContent.get(i).trim().equals("")){
							continue;
						}
						cardNos.add(readExcelContent.get(i).trim());
					}
				}
				
				if(Integer.parseInt(sellOrderDTO.getCardQuantity())!=cardNos.size()){
					this.addActionError("文件中卡的数量与订单输入数量不符！");
					return this.add();
				}
				uploadCardFileDTO.setCardNos(cardNos);
				uploadCardFileDTO.setEntityId(this.getUser().getEntityId());
				/*OperationResult result = this.sendService(ConstCode.UPLOAD_CARDNOS_FILE, uploadCardFileDTO);
				if(result.getTxnstate().equals("0")){
					this.addActionError("上传文件错！");
					return this.add();
				}*/
			}
			Integer cardTotalInteger=Integer
			.parseInt((SystemInfo
					.getParameterValue(SystemInfoConstants.ORDER_CARD_MAXIMUM)));
			if(Integer.parseInt(sellOrderDTO.getCardQuantity())>cardTotalInteger){
				this.addActionError("订单卡数量不能超过系统既定最大值:"+cardTotalInteger);
				return this.add();
			}
			sellOrderDTO.setUploadCardFileDTO(uploadCardFileDTO);
			sellOrderDTO.setIsCheckCard("0");//可选验卡
			sellOrderDTO.setValidityPeriod(getSvalidityPriod());
			sellOrderInputDTO = (SellOrderInputDTO)this.sendService(ConstCode.STOCK_ORDER_INSERT, sellOrderDTO).getDetailvo();
			if(sellOrderInputDTO!=null){
				sellOrderDTO=sellOrderInputDTO.getSellOrderDTO();
				repCard=sellOrderDTO.getUploadCardFileDTO().getRepeatedCardNo();
			}
			
			if (hasErrors()) {
				return this.add();
			}
			if(repCard!=null&&repCard.size()>0)
			{
				this.addActionError("卡号重复或卡号已存在！");
				return this.add();
			}
			//this.addActionMessage("添加制卡订单成功！");
			/*if(repCard!=null)
			{
				flag=true;
			}*/
			
			
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			return this.add();
		}
		this.addActionMessage("添加制卡订单成功！");
		return this.list();
	}
	@SkipValidation
	public String editInput() throws Exception {
		this.add();
		return "edit";
	}
	@SkipValidation
	public String edit() throws Exception {
		try {
			view();
			if(sellOrderDTO.getOrderType().equals(OrderConst.ORDER_TYPE_ORDER_BUY_ISSUER_ISSUER_SIGN)){
				this.addActionError("不能编辑记名采购订单");
				return this.list();
			}
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
			sellOrderInputDTO = (SellOrderInputDTO) this.sendService(
					ConstCode.STOCK_ORDER_INIT, sellOrderInputDTO)
					.getDetailvo();
			sellOrderDTO.setFaceValueId(Amount.getDataBaseAmount(sellOrderDTO.getFaceValue()));
			sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
			
			productCardBinDTO = sellOrderInputDTO.getProductDTO().getProductCardBinDTOs().get(0);
			
			this.initEdit();
			if (hasErrors()) {
				return this.list();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "edit";
	}

	public void validateUpdate(){
		try {
			if(hasErrors()){
				errorJsp = "WEB-INF/pages/univer/issuer/order/orderinput/stockOrderEdit.jsp";
				this.add();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
	}
	
	public String update() throws Exception {
		try {
			Integer cardTotalInteger=Integer
			.parseInt((SystemInfo
					.getParameterValue(SystemInfoConstants.ORDER_CARD_MAXIMUM)));
			if(Integer.parseInt(sellOrderDTO.getCardQuantity())>cardTotalInteger){
				this.edit();
				this.addActionError("订单卡数量不能超过系统既定最大值:"+cardTotalInteger);
				return "edit";
			}
			sellOrderDTO.setIsCheckCard("0");//可选验卡
			this.sendService(ConstCode.STOCK_ORDER_UPDATE, sellOrderDTO);
			if (hasErrors()) {
				return this.edit();
			}
			this.addActionMessage("更新制卡订单成功！");
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return this.list();
	}
	
	@SkipValidation
	public String batchSubmit() throws Exception {
		try {
			if (null != choose && choose.length > 0) {
				sellOrderDTO.setOrderIds(choose);
				this.sendService(ConstCode.STOCK_ORDER_BATCHSUBMIT,
						sellOrderDTO);
				if (!hasErrors()) {
					addActionMessage("提交制卡订单成功！");
				}
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return this.list();
	}
	
	@SkipValidation
	public String batchCancel() throws Exception {
		try {
			if (null != choose && choose.length > 0) {
				sellOrderDTO.setOrderIds(choose);
				this.sendService(ConstCode.STOCK_ORDER_BATCHCANCEL,
						sellOrderDTO);
				if (!hasErrors()) {
					addActionMessage("取消制卡订单成功！");
				}
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return this.list();
	}
	public void upload() 
	{
		//String getSavePath=ServletActionContext.getServletContext().getRealPath("/");
		String getSavePath=ServletActionContext.getServletContext().getRealPath("/");
		  
	     String path1=getSavePath+"upload"+"/";
	     this.setPath(path1);
			 createExcel(repCard,path);
		
	}
	public String download() throws FileNotFoundException
	{
		  String getSavePath=ServletActionContext.getServletContext().getRealPath("/");
		  this.fileName="导出重复卡号.xls";
	     String path1=getSavePath+"upload"+"/"+fileName;
	     logger.debug("文件路径为："+path1);
		//String test="/home/"
          this.inputStream=new FileInputStream(path1);
            this.fileName="导出重复卡号.xls";
        return "SUCESS";
	}
	
	 public  void createExcel(List xls,String path) {
	        // 获取总列数
	        int CountColumnNum = xls.size();
	        // 创建Excel文档
	        HSSFWorkbook hssfworkbook  = new HSSFWorkbook();
	       
	        // sheet 对应一个工作页
	        HSSFSheet sheet = hssfworkbook.createSheet("pldrxkxxmb");
	       //取得第一行
	        HSSFRow hssfrow = sheet.createRow(0);
	        //创建第一个单元格    并处理乱码
	        HSSFCell hssfcell_0 = hssfrow.createCell((short)0);
	        hssfcell_0.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
	        //设置单元格格式为字符串类型
	        hssfcell_0.setCellType(HSSFCell.ENCODING_UTF_16);
	      //对第一个单元格赋值
	        hssfcell_0.setCellValue("重复卡号");
	        for (int  i = 0; i < xls.size(); i++) {
	        	 //取得第一行
		         hssfrow = sheet.createRow(i+1);
		        //创建第一个单元格    并处理乱码
		         String s= (String) xls.get(i);
		         HSSFCell cell = hssfrow.createCell( (short) 0);
		        //对第一个单元格赋值
		         cell.setCellValue(s);
		         System.out.println("hello");
	        }
	        //输出
			
	         File file=new File(path);
	      // 如果文件夹不存在则创建    
	 		//如果文件夹不存在则创建    
	 		if  (!file .exists())      
	 		{       
	 		    //System.out.println("//不存在");  
	 		    file .mkdirs();    
	 		}
				try {
					file = new File(path+"导出重复卡号.xls");
					fileoutputstream = new FileOutputStream(file);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
					try {
						hssfworkbook.write(fileoutputstream);
						fileoutputstream.flush();
						fileoutputstream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	
	       
	       
	    }
	 


}
