package com.huateng.univer.issuer.consumerManagerment.acqPay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.consumer.AcqPayDTO;
import com.allinfinance.univer.issuer.dto.consumer.ConsumerQueryDTO;
import com.allinfinance.univer.issuer.dto.product.ProductDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.Config;
import com.huateng.framework.util.SystemInfo;

public class AcqPayAction extends BaseAction {
	
	private Logger logger = Logger.getLogger(AcqPayAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -5714343501847095030L;
	
	private ConsumerQueryDTO consumerQueryDTO=new ConsumerQueryDTO();
	private PageDataDTO pageDataDTO = new PageDataDTO();
	private AcqPayDTO acqPayDTO=new AcqPayDTO();
	private int totalRows = 0;
	private File cert;
	private String certContentType;
	private String certFileName;
	private String certPath;
	private File logo;
	private String logoContentType;
	private String logoFileName;
	private String logoPath;
	private String [] idList;

	
	public String inquery(){
		try {
			ListPageInit(null, consumerQueryDTO);
			consumerQueryDTO.setFatherEntityId(this.getUser().getEntityId());
			pageDataDTO=(PageDataDTO)sendService(ConstCode.CONSUMER_ACQ_PAY_INQUERY, consumerQueryDTO).getDetailvo();
			if(pageDataDTO!=null){
				totalRows=pageDataDTO.getTotalRecord();
			}
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		return "list";
	}
	
	public String initAdd(){
//		List<BankDTO> bankDTOs=(List<BankDTO>)sendService(ConstCode.CONSUMER_ACQ_PAY_SELECT_BANK, acqPayDTO).getDetailvo();
//		if(hasActionErrors()){
//			this.addActionError("查询银行接口失败");
//			return "input";
//		}
//		acqPayDTO.setBanks(bankDTOs);
		return "add";
	}
	
	public String chooseBank(){
		try {
			ListPageInit(null, consumerQueryDTO);
			consumerQueryDTO.setEntityId(acqPayDTO.getEntityId());
			pageDataDTO=(PageDataDTO)sendService(ConstCode.CONSUMER_ACQ_PAY_CHOOSE_BANK, consumerQueryDTO).getDetailvo();
			if(hasActionErrors()){
				this.addActionError("查询银行接口失败");
				return "input";
			}
			if(pageDataDTO!=null){
				totalRows=pageDataDTO.getTotalRecord();
			}
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		return "chooseBank";
	}
	public String addBank(){
		try {
			sendService(ConstCode.CONSUMER_ACQ_PAY_ADD_BANK, acqPayDTO).getDetailvo();
			if(hasActionErrors()){
				this.addActionError("添加银行接口失败");
				return "input";
			}else{
				getRequest().setAttribute("sucessMessage", "添加银行接口信息成功!");
			}
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		return "addSucess";
	}
	public void load(File in,File out){
		FileInputStream input=null;
		FileOutputStream output=null;
		try {
			byte[] bytes=new byte[(int)in.length()];
			input=new FileInputStream(in);
			output=new FileOutputStream(out);
			if(input.read(bytes)>0){
				output.write(bytes);
			}
		} catch (FileNotFoundException e) {
			
			this.logger.error(e.getMessage());
		} catch (IOException e) {
			
			this.logger.error(e.getMessage());
		}finally {
            if (input != null) {
                try {
                	input.close();
                } catch (IOException e1) {
                    this.logger.error(e1.getMessage());  
                }
            }
            if (output != null) {
                try {
                	output.close();
                } catch (IOException e1) {
                    this.logger.error(e1.getMessage()); 
                }
            }
        }

	}
	public String rename(String filename){
		int i=filename.indexOf(".");
		String name=filename.substring(0, i);	
		String type=filename.substring(i);
		String rename=name+"_"+System.currentTimeMillis()+type;
		return rename;
	}
	
	public String add(){
		logger.info("-------------------------------------");
		try {
			logger.info(getRequest().getSession().getAttribute("productDTOs"));
			//收单证书
			logger.info(certFileName);
			
			String certUrl=Config.getCertURL()+rename(certFileName);
			File certFile=new File(certUrl);
			load(getCert(),certFile);
			acqPayDTO.setCerts(certUrl);
			//logo
			String logoName=rename(logoFileName);
			String logoUrl=Config.getLogoURL()+logoName;
			File logoFile=new File(logoUrl);
			load(getLogo(),logoFile);
			acqPayDTO.setLogo(logoName);
			
			acqPayDTO.setProductDTOs((List<ProductDTO>)getRequest().getSession().getAttribute("productDTOs"));
			sendService(ConstCode.CONSUMER_ACQ_PAY_INSERT, acqPayDTO);
			if(hasActionErrors()){
				inquery();
				return "input";
			}
			
			
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		
		reEdit();
		return "edit";
	}
	public void validateAdd(){
		logger.info(logoPath);
		logger.info(logoFileName);
		if(hasErrors()){
			cert=new File(certPath);
			logo=new File(logoPath);
			if(null!=acqPayDTO.getEntityId() && !"".equals(acqPayDTO.getEntityId())){
			selectProd();
			}
		}
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
				pageDataDTO = (PageDataDTO) sendService(ConstCode.CONSUMER_ACQ_PAY_CHOOSE, consumerQueryDTO).getDetailvo();
				if (pageDataDTO != null) {
					totalRows = pageDataDTO.getTotalRecord();
				}
				if (hasErrors()) {
					return "input";
				}
			} catch (Exception e) {
				this.logger.error(e.getMessage());
			}
			return "choose";  
	   }
	
	public String selectProd(){
		List<ProductDTO> productDTOs=(List<ProductDTO>)sendService(ConstCode.CONSUMER_ACQ_PAY_SELECT_PROD,acqPayDTO).getDetailvo();
		if(productDTOs!=null && productDTOs.size()>0){
			acqPayDTO.setProductDTOs(productDTOs);
			if(getRequest().getSession().getAttribute("productDTOs")!=null){
				getRequest().getSession().setAttribute("productDTOs", null);
			}
			getRequest().getSession().setAttribute("productDTOs", productDTOs);
			logger.info(getRequest().getSession().getAttribute("productDTOs"));
		}
		return "add";
	}
	public String reEdit(){
		if(null!=idList && idList.length>0){
			acqPayDTO.setEntityId(idList[0]);
		}
		acqPayDTO=(AcqPayDTO)sendService(ConstCode.CONSUMER_ACQ_PAY_REEDIT, acqPayDTO).getDetailvo();
		return "edit";
	}
	
	public String edit(){
		if(cert!=null){
		String certUrl=Config.getCertURL()+rename(certFileName);
		File certFile=new File(certUrl);
		load(getCert(),certFile);
		acqPayDTO.setCerts(certUrl);
		}
		//logo
		if(logo!=null){
		String logoName=rename(logoFileName);
		String logoUrl=Config.getLogoURL()+logoName;
		File logoFile=new File(logoUrl);
		load(getLogo(),logoFile);
		acqPayDTO.setLogo(logoName);
		}
		
		sendService(ConstCode.CONSUMER_ACQ_PAY_EDIT, acqPayDTO);
		if(hasActionErrors()){
			inquery();
			return "input";
		}
		inquery();
		return "list";
	}
	
	public void validateEdit(){
		if(hasErrors()){
			reEdit();
			
		}
	}
	public String view(){
		acqPayDTO=(AcqPayDTO)sendService(ConstCode.CONSUMER_ACQ_PAY_REEDIT, acqPayDTO).getDetailvo();
		return "view";
	}
	
	public String delete(){
		if(null!=idList && idList.length>0){
			acqPayDTO.setEntityId(idList[0]);
		}
		sendService(ConstCode.CONSUMER_ACQ_PAY_DELETE, acqPayDTO);
		if(hasActionErrors()){
			inquery();
			return "input";
		}
		inquery();
		return "list";
	}
	
	public String delBank(){
		try {
			sendService(ConstCode.CONSUMER_ACQ_PAY_DELETE_BANK, acqPayDTO);
			if(hasActionErrors()){
				this.addActionError("删除银行接口失败");
				return "input";
			}
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		reEdit();
		return "edit";
	}
	
	public ConsumerQueryDTO getConsumerQueryDTO() {
		return consumerQueryDTO;
	}
	public void setConsumerQueryDTO(ConsumerQueryDTO consumerQueryDTO) {
		this.consumerQueryDTO = consumerQueryDTO;
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

	public File getCert() {
		return cert;
	}

	public void setCert(File cert) {
		this.cert = cert;
	}

	public String getCertContentType() {
		return certContentType;
	}

	public void setCertContentType(String certContentType) {
		this.certContentType = certContentType;
	}

	public String getCertFileName() {
		return certFileName;
	}

	public void setCertFileName(String certFileName) {
		this.certFileName = certFileName;
	}

	public File getLogo() {
		return logo;
	}

	public void setLogo(File logo) {
		this.logo = logo;
	}

	public String getLogoContentType() {
		return logoContentType;
	}

	public void setLogoContentType(String logoContentType) {
		this.logoContentType = logoContentType;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}

	public AcqPayDTO getAcqPayDTO() {
		return acqPayDTO;
	}

	public void setAcqPayDTO(AcqPayDTO acqPayDTO) {
		this.acqPayDTO = acqPayDTO;
	}

	public String[] getIdList() {
		return idList;
	}

	public void setIdList(String[] idList) {
		this.idList = idList;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	
	
	
	
	
}
