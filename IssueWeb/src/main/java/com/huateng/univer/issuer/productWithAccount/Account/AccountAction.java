package com.huateng.univer.issuer.productWithAccount.Account;

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
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceDTO;
import com.allinfinance.univer.issuer.dto.service.ServiceQueryDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.SystemInfo;
/**
 * 
 * @author fengfeng.shi
 * create date:2010-11-05
 */
public class AccountAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1086366126921787549L;
	private Logger logger = Logger.getLogger(AccountAction.class);
	private boolean flag;
	
	/**
	 * 账户信息DTO
	 */
	private ServiceDTO serviceDTO = new ServiceDTO();
	/**
	 * 账户查询条件DTO
	 */
	private ServiceQueryDTO acctypeQueryDTO = new ServiceQueryDTO();
	
	/**	
	 * 服务信息集DTO
	 */
	private PageDataDTO pageDataDTO = new PageDataDTO();
	/**
	 * 服务信息ID
	 */
	private List<String> choose = new ArrayList<String>();
	
	private int totalRows=0;
	
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	//所属发行机构
	List<Map<String,String>> entityList = new ArrayList<Map<String,String>>();
	public List<Map<String,String>> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<Map<String,String>> entityList) {
		this.entityList = entityList;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public ServiceDTO getServiceDTO() {
		return serviceDTO;
	}
	public void setServiceDTO(ServiceDTO serviceDTO) {
		this.serviceDTO = serviceDTO;
	}
	public ServiceQueryDTO getAcctypeQueryDTO() {
		return acctypeQueryDTO;
	}
	public void setAcctypeQueryDTO(ServiceQueryDTO acctypeQueryDTO) {
		this.acctypeQueryDTO = acctypeQueryDTO;
	}
	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}
	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}
	public List<String> getChoose() {
		return choose;
	}
	public void setChoose(List<String> choose) {
		this.choose = choose;
	}
	
	public String init() throws Exception {
		serviceDTO = (ServiceDTO) sendService(
				ConstCode.ACCTYPE_SERVICE_ACCTYPEINIT, null).getDetailvo();
		if(acctypeQueryDTO==null){
			acctypeQueryDTO = new ServiceQueryDTO();
		}
		if (this.hasErrors()) {
			return "input";
		}
		return inquery();
	}
	
	public String inquery() throws Exception {
		ListPageInit(null, acctypeQueryDTO);
		if(acctypeQueryDTO.isQueryAll()){
			acctypeQueryDTO.setQueryAll(false);
			acctypeQueryDTO.setRowsDisplayed(Integer.parseInt(SystemInfo.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
		}
		pageDataDTO = (PageDataDTO) sendService(
				ConstCode.ACCTYPE_SERVICE_ACCTYPEINQUERY, acctypeQueryDTO)
				.getDetailvo();
		acctypeQueryDTO.setEntityIdStr(this.getUser().getEntityId());
		//entityList = getIssuerLst(acctypeQueryDTO);
		acctypeQueryDTO.setEntityIdStr("");
		if(pageDataDTO!=null){
			totalRows = pageDataDTO.getTotalRecord();
		}
		if (this.hasErrors()) {
			return "input";
		}
		return "list";
	}
   
	/**
	 * 获得所属发行机构和子发行机构
	 * @return
	 */
	 @SuppressWarnings("unchecked")
	public List<Map<String, String>> getIssuerLst(ServiceQueryDTO acctypeQueryDTO) {
		 List<ServiceQueryDTO> sDTO=(List<ServiceQueryDTO>) sendService(ConstCode.ISSUER_ACCOUNT_SERVICE_INQUERY, acctypeQueryDTO).getDetailvo();
		 List<Map<String, String>> issuerList= new ArrayList<Map<String, String>>();
		 for(ServiceQueryDTO serviceDto:sDTO){
			   Map<String, String> issuer = new HashMap<String, String>();
			   issuer.put("entityId",serviceDto.getEntityIdStr());
			   issuer.put("entityName",serviceDto.getEntityName());
			   issuerList.add(issuer);
		 }
		 return issuerList;
	    }
	
	
	 /**
	  * 转向账户添加页面
	  * @return
	  * @throws Exception
	  */
	 public String reload() throws Exception {
		    acctypeQueryDTO.setEntityIdStr(this.getUser().getEntityId());
			//entityList = getIssuerLst(acctypeQueryDTO);
			serviceDTO.setEntityId(this.getUser().getEntityId());
			/*serviceDTO = new ServiceDTO();
			serviceDTO.setEntityId(this.getUser().getEntityId());*/
			if (this.hasErrors()) {
				return "input";
			}
			return "reAdd";
		}
	 
	 public void validateInsertAccount()throws BizServiceException{
		 acctypeQueryDTO.setEntityIdStr(getUser().getEntityId());
		 entityList = getIssuerLst(acctypeQueryDTO);
		 DateFormat format=new SimpleDateFormat("yyyyMMdd");
		 try {
			if(null!=serviceDTO.getExpiryDate() || !"".equals(serviceDTO.getExpiryDate())){
				   String dateString= serviceDTO.getExpiryDate().replace("-","");
				   Date date=format.parse(dateString);
				   if( date.compareTo(DateUtil.getCurrentDate()) <= 0){
					   this.addActionError("过期日期不能早于当前日期");				   
				   }
				 }
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
	 }
	 
	 /**
	  * 添加账户信息
	  * @return
	  * @throws Exception
	  */
	 public String insertAccount() throws Exception {
           // logger.info(serviceDTO.getEntityId().replace(",",""));
		 if(null!=serviceDTO.getExpiryDate() || !"".equals(serviceDTO.getExpiryDate())){
		    serviceDTO.setExpiryDate(serviceDTO.getExpiryDate().replace("-",""));
		 }
		    serviceDTO.setCreateUser(getUser().getUserId());
			serviceDTO.setDefaultRate(new BigDecimal(serviceDTO.getDefaultRate()).toString());
			//serviceDTO.setEntityId(serviceDTO.getEntityId().replace(",","").trim());
			serviceDTO.setEntityId(getUser().getEntityId());
			sendService(ConstCode.ACCTYPE_SERVICE_ACCTYPEINSERT, serviceDTO);
			if (this.hasErrors()) {
				return INPUT;
			}else{
				addActionMessage("账户信息添加成功！");
			}
			return inquery();
		}
	 
	
	 /**
	  * 转向账户修改页面
	  * @return
	  * @throws Exception
	  */
	 public String accountLoad()throws Exception{
		    String id = choose.get(0);
			serviceDTO = new ServiceDTO();
			serviceDTO.setServiceId(id);
			serviceDTO=(ServiceDTO)sendService(ConstCode.ACCTYPE_EDIT_SERVICE_ACCTYPEVIEW, serviceDTO).getDetailvo();
//			entityList = getIssuerLst(acctypeQueryDTO);
			if (this.hasErrors()) {
				
				return inquery();
			
			//return "list";
		}
		
				if(serviceDTO.getExpiryDate().equals(DataBaseConstant.DEFAULT_EXPIRY_DATE)){
				serviceDTO.setExpiryDate("");
			}else{
				serviceDTO.setExpiryDate(DateUtil.formatStringDate(serviceDTO.getExpiryDate()));
			}
		acctypeQueryDTO.setEntityIdStr(getUser().getEntityId());
		return "edit";
			
			
	 }
	 
	    /**
		 * 账户信息调用
		 * 
		 * @return
		 * @throws Exception
		 */
		public String accountView() throws Exception {
			String id = getRequest().getParameter("id");
			serviceDTO = new ServiceDTO();
			serviceDTO.setServiceId(id);
			serviceDTO = (ServiceDTO) sendService(ConstCode.ACCTYPE_SERVICE_ACCTYPEVIEW, serviceDTO).getDetailvo();
			serviceDTO.setExpiryDate(DateUtil.formatStringDate(serviceDTO.getExpiryDate()));
			entityList = getIssuerLst(acctypeQueryDTO);
			if (this.hasErrors()) {
				return "list";
			}
			return "view";
		}

	 
	 
	 /**
	  * 更新账户 
	  * @return
	  * @throws Exception
	  */
	 public String updateAccount() throws Exception{
		    serviceDTO.setModifyUser(getUser().getUserId());
		    if(null != serviceDTO.getExpiryDate() && !serviceDTO.getExpiryDate().equals("")){
		    	serviceDTO.setExpiryDate(serviceDTO.getExpiryDate().replace("-",""));
		    }
		    serviceDTO.setDefaultRate(new BigDecimal(serviceDTO.getDefaultRate()).toString());
		    serviceDTO.setEntityId(getUser().getEntityId());
			sendService(ConstCode.ACCTYPE_SERVICE_ACCTYPEUPDATE, serviceDTO);
			if (this.hasErrors()) {
				return INPUT;
			}else{
				addActionMessage("账户信息修改成功！");
			}
			acctypeQueryDTO.setEntityIdStr("");
			return inquery();
	 }
	 
	 public void validateUpdateAccount(){
		 entityList = getIssuerLst(acctypeQueryDTO);
		 DateFormat format=new SimpleDateFormat("yyyyMMdd");
		 try {
			if(null!=serviceDTO.getExpiryDate() || !"".equals(serviceDTO.getExpiryDate())){
				   String dateString= serviceDTO.getExpiryDate().replace("-","");
				   Date date=format.parse(dateString);
				   if( date.compareTo(DateUtil.getCurrentDate()) <= 0){
					   this.addActionError("过期日期不能早于当前日期");				   
				   }
				 }
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
	 }
	 
	 public String delete() throws Exception {
			List<ServiceDTO> acctypeDTOs = new ArrayList<ServiceDTO>();
			for (String id : choose) {
				serviceDTO = new ServiceDTO();
				serviceDTO.setServiceId(id);
				acctypeDTOs.add(serviceDTO);
			}
			serviceDTO=new ServiceDTO();
			serviceDTO.setServiceDTOs(acctypeDTOs);
			sendService(ConstCode.ACCTYPE_SERVICE_ACCTYPEDELETE, serviceDTO);
			if (this.hasErrors()) {
				return inquery();
			}else{
				addActionMessage("账户信息删除成功！");
			}
			return inquery();
		}
}
