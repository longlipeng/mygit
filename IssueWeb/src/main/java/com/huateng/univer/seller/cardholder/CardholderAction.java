package com.huateng.univer.seller.cardholder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.JudgeInforDTO;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.univer.cardmanagement.dto.CardManagementDTO;
import com.allinfinance.univer.entity.dto.DepartmentDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderQueryDTO;
import com.allinfinance.univer.seller.cardholder.dto.CheckPeopleInfoDTO;
import com.allinfinance.univer.seller.cardholder.dto.CompanyInfoDTO;
import com.allinfinance.univer.seller.cardholder.dto.IdCardInfoDTO;
import com.allinfinance.univer.seller.cardholder.dto.PictureInfoQueryDTO;
import com.allinfinance.univer.seller.customer.CustomerDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderCardListQueryDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderDTO;
import com.allinfinance.univer.seller.order.dto.SellOrderInputDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.DataBaseConstant;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.FileOperate;
import com.huateng.framework.util.SendShortMessageThread;
import com.huateng.framework.util.SystemInfo;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONArray;

/**
 * 持卡人信息管理action
 * 
 * @author xxl
 * 
 */
public class CardholderAction extends BaseAction {
    private Logger logger = Logger.getLogger(CardholderAction.class);
    private static final long serialVersionUID = 1051841917692436382L;

    private PageDataDTO pageDataDTO = new PageDataDTO();
    private CardholderQueryDTO cardholderQueryDTO = new CardholderQueryDTO();
    private CardholderDTO cardholderDTO = new CardholderDTO();
    protected SellOrderInputDTO sellOrderInputDTO = new SellOrderInputDTO();
    private PictureInfoQueryDTO pictureInfoQueryDTO=new PictureInfoQueryDTO();
    private CheckPeopleInfoDTO checkPeopleInfoDTO =new CheckPeopleInfoDTO();
    protected SellOrderDTO sellOrderDTO = new SellOrderDTO();
    private CardManagementDTO cardManagementDTO;
    private CustomerDTO customerDTO = new CustomerDTO();
    private String[] cardholderIds;
    private String[] orderListStr;
    private Integer totalRows = 0;
    private Integer cardInfo_totalRows = 0;
    private JudgeInforDTO JudgeInforDTO =new JudgeInforDTO();
    private String id_No;
    private String cusType;
    
    
    

    public CheckPeopleInfoDTO getCheckPeopleInfoDTO() {
		return checkPeopleInfoDTO;
	}

	public void setCheckPeopleInfoDTO(CheckPeopleInfoDTO checkPeopleInfoDTO) {
		this.checkPeopleInfoDTO = checkPeopleInfoDTO;
	}

	public PictureInfoQueryDTO getPictureInfoQueryDTO() {
		return pictureInfoQueryDTO;
	}

	public void setPictureInfoQueryDTO(PictureInfoQueryDTO pictureInfoQueryDTO) {
		this.pictureInfoQueryDTO = pictureInfoQueryDTO;
	}

	public String getCusType() {
        return cusType;
    }

    public void setCusType(String cusType) {
        this.cusType = cusType;
    }

    public String getId_No() {
		return id_No;
	}

	public void setId_No(String id_No) {
		this.id_No = id_No;
	}


	//从呼叫中心过来的为：1，其他为0；
    private Integer callcenter = 0;
    
	public Integer getCallcenter() {
		return callcenter;
	}

	public void setCallcenter(Integer callcenter) {
		this.callcenter = callcenter;
	}

	// 返回到卡管理页面的标记 1、返回到卡管理
    private String cardManagementFlag;
    private String personFlag;
    private List<Map<String, String>> departmentLists = new ArrayList<Map<String, String>>();

    public CardManagementDTO getCardManagementDTO() {
        return cardManagementDTO;
    }

    public void setCardManagementDTO(CardManagementDTO cardManagementDTO) {
        this.cardManagementDTO = cardManagementDTO;
    }

    public String getCardManagementFlag() {
        return cardManagementFlag;
    }

    public void setCardManagementFlag(String cardManagementFlag) {
        this.cardManagementFlag = cardManagementFlag;
    }

    // 导入的文件
    private File cardholderFile;
    // 导入的文件名
    private String cardholderFileFileName;

    private SellOrderCardListQueryDTO sellOrderCardListQueryDTO = new SellOrderCardListQueryDTO();

    public String list() throws Exception {
        if (cardManagementFlag == null || "".equals(cardManagementFlag)) {
            ListPageInit(null, cardholderQueryDTO);
            if (cardholderQueryDTO.isQueryAll()) {
                cardholderQueryDTO.setQueryAll(false);
                cardholderQueryDTO.setRowsDisplayed(Integer.parseInt((SystemInfo
                        .getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM))));
            }
            pageDataDTO = (PageDataDTO) this.sendService(ConstCode.CARDHOLDER_SERVICE_INQUERY, cardholderQueryDTO)
                    .getDetailvo();
            if (pageDataDTO.getData().size() > 0) {
                totalRows = pageDataDTO.getTotalRecord();
            } else {
                totalRows = 0;
            }

            return "list";
        } else
            return "cardManageQuery";
    }
    public String getIpAddr() {   
    	ActionContext ac = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest)ac.get(ServletActionContext.HTTP_REQUEST);
        String ip = request.getHeader("x-forwarded-for");      
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
           ip = request.getHeader("Proxy-Client-IP");      
       }      
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
           ip = request.getHeader("WL-Proxy-Client-IP");      
        }      
      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
            ip = request.getRemoteAddr();      
       }      
      return ip;      
 }
    
    public String checkList() throws Exception {
        if (cardManagementFlag == null || "".equals(cardManagementFlag)) {
            ListPageInit(null, cardholderQueryDTO);
            pageDataDTO = (PageDataDTO) this.sendService(ConstCode.CHECK_CARDHOLDER_LIST, cardholderQueryDTO)
                    .getDetailvo();
            if (pageDataDTO.getData().size() > 0) {
                totalRows = pageDataDTO.getTotalRecord();
            } else {
                totalRows = 0;
            }

            return "check";
        } else
            return "cardManageQuery";
    }
    
    public String checkCardholder() throws Exception {
    	sendService(ConstCode.CHECK_CARDHOLDER, cardholderDTO);
    	if (hasActionErrors()) {
            return checkList();
        }
    	addActionMessage("操作成功！");
		return checkList();
    	
    }
    
    public String checkView() throws Exception {
    	 try {
         	
             ListPageInit("cardListTable", sellOrderCardListQueryDTO);
             if (null != sellOrderCardListQueryDTO.getCardholderId()
                     && !sellOrderCardListQueryDTO.getCardholderId().equals("")) {
                 cardholderDTO = new CardholderDTO();
                String[] cardholderIds=sellOrderCardListQueryDTO.getCardholderId().split(",");
                 cardholderDTO.setCardholderId(cardholderIds[0]);
                 if (null == cardManagementDTO) {
      				cardManagementDTO = new CardManagementDTO();
      				if(cardholderIds[1].equals("2")){
      					cardManagementDTO.setDataState("2");
      				}else{
      					cardManagementDTO.setDataState("1");
      				}
      				
              	}
             }
             cardholderDTO.setSellOrderCardListQueryDTO(this.sellOrderCardListQueryDTO);
             cardholderDTO = (CardholderDTO) sendService(ConstCode.CARDHOLDER_SERVICE_VIEW, cardholderDTO).getDetailvo();
             if (hasActionErrors()) {
                 return checkList();
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
    	return "checkView";
    }

    public String view() throws Exception {

        try {
        	if (null == cardManagementDTO) {
				cardManagementDTO = new CardManagementDTO();
        	}
            ListPageInit(null, sellOrderCardListQueryDTO);
            if (null != sellOrderCardListQueryDTO.getCardholderId()
                    && !sellOrderCardListQueryDTO.getCardholderId().equals("")) {
                cardholderDTO = new CardholderDTO();
                cardholderDTO.setCardholderId(sellOrderCardListQueryDTO.getCardholderId());
            }
            if (null != cardholderDTO.getCardholderId()
                    && !cardholderDTO.getCardholderId().equals("")) {
            	sellOrderCardListQueryDTO.setCardholderId(cardholderDTO.getCardholderId());
            }
            cardholderDTO.setSellOrderCardListQueryDTO(this.sellOrderCardListQueryDTO);
            cardholderDTO = (CardholderDTO) sendService(ConstCode.CARDHOLDER_SERVICE_VIEW, cardholderDTO).getDetailvo();
            /*String idNo=cardholderDTO.getIdNo();
            String phone=cardholderDTO.getCardholderMobile();
            if(cardholderDTO.getIdType().equals("1")){
            	cardholderDTO.setIdNo(idNo.substring(0,6) + "********" + idNo.substring(14, idNo.length()));
            }
            if(phone!=null&&!phone.equals("")){
            	cardholderDTO.setCardholderMobile(phone.substring(0,3) + "****" + phone.substring(7, phone.length()));
            }*/
            if (hasActionErrors()) {
                return inqueryShow();
            }
           /* if(StringUtils.isNotEmpty(cardholderDTO.getImgfPath())){
            	if(cardholderDTO.getImgfPath().equals("www")){
            		cardholderDTO.setImgfPath(null);
            	}
            }*/
            if (null != cardholderDTO.getCardholderBirthday() && !"".equals(cardholderDTO.getCardholderBirthday())) {
                cardholderDTO.setCardholderBirthday(DateUtil
                        .dbFormatToDateFormat(cardholderDTO.getCardholderBirthday()));
            }
            if (null != cardholderDTO.getCloseDate() && !"".equals(cardholderDTO.getCloseDate())) {
                cardholderDTO.setCloseDate(DateUtil.dbFormatToDateFormat(cardholderDTO.getCloseDate()));
            }
            if (null != cardholderDTO.getValidity() && !"".equals(cardholderDTO.getValidity())) {
                cardholderDTO.setValidity(DateUtil.dbFormatToDateFormat(cardholderDTO.getValidity()));
            }
            if (cardholderDTO.getCardholderCardList() != null
                    && cardholderDTO.getCardholderCardList().getData() != null) {
                totalRows = cardholderDTO.getCardholderCardList().getTotalRecord();
            } else {
                totalRows = 0;
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "view";
    }
   
    public void getImageInfo()throws IOException{
    	IdCardInfoDTO idCardInfoDTO = (IdCardInfoDTO) sendService(ConstCode.QUERY_ID_IMAGES, id_No).getDetailvo();
		 JSONArray jsonObject = JSONArray.fromObject(idCardInfoDTO);
			getResponse().setContentType("application/json; charset=utf-8");
			getResponse().setCharacterEncoding("utf-8");
			getResponse().getWriter().println(jsonObject);
			getResponse().getWriter().close();
	}
    
    

    public String add() throws Exception {
        cardholderDTO = new CardholderDTO();
        cardholderDTO.setIsblacklist("0");//黑名单标识
        cardholderDTO.setRiskGrade("O");//风险等级标识
        return "add";
    }

    public String insert() throws Exception {
        if (null != cardholderDTO.getCardholderBirthday() && !"".equals(cardholderDTO.getCardholderBirthday())) {
            cardholderDTO.setCardholderBirthday(DateUtil.getFormatTime(cardholderDTO.getCardholderBirthday()));
        }
        if (null != cardholderDTO.getCloseDate() && !"".equals(cardholderDTO.getCloseDate())) {
            cardholderDTO.setCloseDate(DateUtil.getFormatTime(cardholderDTO.getCloseDate()));
        }
        if (null != cardholderDTO.getValidity() && !"".equals(cardholderDTO.getValidity())) {
            cardholderDTO.setValidity(DateUtil.getFormatTime(cardholderDTO.getValidity()));
        }
//        String idType = cardholderDTO.getIdType();
        String idNo = cardholderDTO.getIdNo();
        if(cardholderDTO.getIdType().equals("1")){
        	cardholderDTO.setIdNo(idNo.toUpperCase());
        }
        
        
        /*if (idType.equals("1")) {
			String substring = idNo.substring(17, 18);
			if(!Character.isDigit(substring.charAt(0))){//不是数字
				if(!Character.isUpperCase(substring.charAt(0))){ //小写
					cardholderDTO.setIdNo(idNo.substring(0, 17)+"X");
				}
			}
		}*/
        
        //校验身份证是否存在
        List<CardholderDTO> datas = (ArrayList<CardholderDTO>)this.sendService(ConstCode.CARDHOLDER_SERVICE_SELECT_BY_IDNO, cardholderDTO).getDetailvo();
		if (datas.size() > 0) {
            addFieldError("cardholderDTO.IdNo", "身份证号已存在！");
        }
        if (hasFieldErrors()) {
        	return INPUT;
        }
        
        this.sendService(ConstCode.CARDHOLDER_SERVICE_INSERT, cardholderDTO);

        if (hasActionErrors()) {
            return INPUT;
        }
        addActionMessage("添加持卡人信息成功！");
        return this.list();
    }

    /**
     * 订单编辑中快捷添加持卡人信息
     * 
     * @return
     * @throws Exception
     */
    public String addInOrder() throws Exception {
        // 查询客户信息
        customerDTO = (CustomerDTO) sendService(ConstCode.CUSTOMER_DEPARTMENT_INQUERY, customerDTO).getDetailvo();
        List<DepartmentDTO> departmentDTOs = customerDTO.getDepartmentList();
        for (DepartmentDTO departmentDTO : departmentDTOs) {
            Map<String, String> department = new HashMap<String, String>();
            department.put("entityId", departmentDTO.getDepartmentId());
            department.put("entityName", departmentDTO.getDepartmentName());
            departmentLists.add(department);
        }
        return "addInOrder";
    }

    
    /**
     * 订单编辑中快捷添加功能
     * 
     * @return
     * @throws Exception
     */
    public String insertInOrder() throws Exception {
        if (null == cardholderDTO.getFirstName() || "".equals(cardholderDTO.getFirstName().trim())) {
            addFieldError("cardholderDTO.firstName", "姓名必须输入");
        }
        if (null == cardholderDTO.getIdNo() || "".equals(cardholderDTO.getIdNo().trim())) {
            addFieldError("cardholderDTO.idNo", "证件号必须输入");
        }
        if (null ==cardholderDTO.getCardholderMobile() || "".equals(cardholderDTO.getCardholderMobile() .trim())) {
            addFieldError("cardholderDTO.cardholderMobile", "手机号必须输入");
        }
        if (null ==customerDTO.getCustomerAddress() || "".equals(customerDTO.getCustomerAddress()  .trim())) {
            addFieldError("customerDTO.customerAddress", "邮寄地址必须输入");
        }
        if (hasFieldErrors()) {
        	return INPUT;
        }
        if(cardholderDTO.getIdType().equals("1")){
        	cardholderDTO.setIdNo(cardholderDTO.getIdNo().toUpperCase());
        }
        
        
        if (null == cardholderDTO.getEntityId() || "".equals(cardholderDTO.getEntityId().trim())) {
            cardholderDTO.setEntityId(customerDTO.getEntityId());
        }
        if (null != cardholderDTO.getCardholderBirthday() && !"".equals(cardholderDTO.getCardholderBirthday())) {
            cardholderDTO.setCardholderBirthday(DateUtil.getFormatTime(cardholderDTO.getCardholderBirthday()));
        }
        if (null != cardholderDTO.getCloseDate() && !"".equals(cardholderDTO.getCloseDate())) {
            cardholderDTO.setCloseDate(DateUtil.getFormatTime(cardholderDTO.getCloseDate()));
        }
        if (null != customerDTO.getCustomerAddress() && !"".equals( customerDTO.getCustomerAddress())) {
           cardholderDTO.setMailingAddress( customerDTO.getCustomerAddress());
        }
        
        //校验身份证是否存在
        List<CardholderDTO> datas = (ArrayList<CardholderDTO>)this.sendService(ConstCode.CARDHOLDER_SERVICE_SELECT_BY_IDNO, cardholderDTO).getDetailvo();
		if (datas.size() > 0) {
            addFieldError("cardholderDTO.IdNo", "身份证号已存在！");
        }
        if (hasFieldErrors()) {
        	return INPUT;
        }
        
        cardholderDTO = (CardholderDTO) this.sendService(ConstCode.CARDHOLDER_SERVICE_INSERT_CARDHOLDERDTO,
                cardholderDTO).getDetailvo();

        if (hasActionErrors()) {
            return INPUT;
        }
        try {
            /**
             * 注：暂用defaultEntityId
             * */
            String defaultEntityIdString = sellOrderInputDTO.getDefaultEntityId();
            sellOrderInputDTO.setDefaultEntityId(SystemInfo.getParameterValue(SystemInfoConstants.ORDER_CARD_MAXIMUM));
            sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
            orderListStr = new String[1];
            orderListStr[0] = cardholderDTO.getCardholderId();
            sellOrderInputDTO.setOrderListStr(orderListStr);

            ListPageInit(null, sellOrderInputDTO.getSellOrderCardListQueryDTO());

            ListPageInit(null, sellOrderInputDTO.getSellOrderListQueryDTO());

            sellOrderInputDTO = (SellOrderInputDTO) sendService(ConstCode.SELL_ORDER_INSERT_CARDHOLDER,
                    sellOrderInputDTO).getDetailvo();
            sellOrderInputDTO.setDefaultEntityId(defaultEntityIdString);
            sellOrderInputDTO.setCardholderDTO(cardholderDTO);
            if (!this.hasErrors()) {
                getRequest().setAttribute("sucessMessage", "添加订单明细信息成功!");
            } else {
                return addInOrder();
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        addActionMessage("添加持卡人信息成功！");
        return "addSucess";
    }

    public String edit() throws Exception {
        if (null != cardholderIds && null != cardholderIds[0] && !cardholderIds[0].equals("")) {
            cardholderDTO.setCardholderId(cardholderIds[0]);
        }
        view();
        if (hasActionErrors()) {
            return this.inqueryShow();
        }
        return "edit";
    }

    @SuppressWarnings("unchecked")
    public String update() throws Exception {
        try {
            if (null != cardholderDTO.getCardholderBirthday() && !"".equals(cardholderDTO.getCardholderBirthday().trim())) {
                cardholderDTO.setCardholderBirthday(DateUtil.getFormatTime(cardholderDTO.getCardholderBirthday()));
            }
            if (null != cardholderDTO.getCloseDate() && !"".equals(cardholderDTO.getCloseDate().trim())) {
                cardholderDTO.setCloseDate(DateUtil.getFormatTime(cardholderDTO.getCloseDate()));
            }
            if (null != cardholderDTO.getValidity() && !"".equals(cardholderDTO.getValidity())) {
                cardholderDTO.setValidity(DateUtil.getFormatTime(cardholderDTO.getValidity()));
            }
            if(cardholderDTO.getIdType().equals("1")){
            	cardholderDTO.setIdNo(cardholderDTO.getIdNo().toUpperCase());
            }
            
            //校验身份证是否已被其他用户使用
            List<CardholderDTO> datas = (ArrayList<CardholderDTO>)this.sendService(ConstCode.CHECK_OTHER_CARDHOLDER_BY_ID_NO, cardholderDTO).getDetailvo();
			if (datas.size() > 0) {
	            addFieldError("cardholderDTO.IdNo", "已有其他持卡人使用该身份证号！");
	        }
	        if (hasFieldErrors()) {
	        	return INPUT;
	        }
            
            sendService(ConstCode.CARDHOLDER_SERVICE_UPDATE, cardholderDTO);
            if (hasActionErrors()) {
                return INPUT;
            }
            addActionMessage("更新个人持卡人信息成功！");
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return this.inqueryShow();
    }

    public void validateUpdate() throws Exception {
        if (null == cardholderDTO.getEntityId() || "".equals(cardholderDTO.getEntityId().trim())) {
            addFieldError("cardholderDTO.entityId", "请选择客户号！！");
        }
        if (null == cardholderDTO.getFirstName() || "".equals(cardholderDTO.getFirstName().trim())) {
            addFieldError("cardholderDTO.firstName", "持卡人姓名必须输入！！");
        }
        if (null == cardholderDTO.getIdNo() || "".equals(cardholderDTO.getIdNo().trim())) {
            addFieldError("cardholderDTO.idNo", "证件号码必须输入！！");
        }
        if (null == cardholderDTO.getCardholderMobile() || "".equals( cardholderDTO.getCardholderMobile().trim())) {
            addFieldError("cardholderDTO.cardholderMobile", "手机号码必须输入！！");
        }
        logger.info(this.getFieldErrors());
        if (hasFieldErrors()) {
            this.view();
        }
        logger.info(this.getFieldErrors());
    }

    /**
     * 关联客户
     * 
     * @return
     * @throws Exception
     */
    public String modifyCust() throws Exception {
        cardholderDTO.setCardholderId(cardholderIds[0]);
        view();
        if (hasErrors()) {
            return INPUT;
        }
        return "modifyCust";
    }

    /**
     * 修改关联客户
     * 
     * @return
     * @throws Exception
     */
    public String updateCust() throws Exception {

        try {
            sendService(ConstCode.CARDHOLDER_SERVICE_UPDATECUST, cardholderDTO);
            if (hasErrors()) {
                return "input";
            }
            addActionMessage("修改关联客户成功！");

        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return list();
    }

    /**
     * 删除个人持卡人信息(逻辑删除将数据状态变为0)
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        if (null != cardholderIds && cardholderIds.length > 0) {
            cardholderDTO.setCardholderIds(cardholderIds);
            sendService(ConstCode.CARDHOLDER_SERVICE_DELETE, cardholderDTO);
            if (!hasActionErrors()) {
                addActionMessage("删除个人持卡人成功！");
            }
        }
        return this.inqueryShow();
    }

    /**
     * 检查导入文件
     * 
     * @return
     */
    public void checkFile() throws Exception {
        String resultStr;
        resultStr = parserFile();
        if ("".equals(resultStr)) {
            resultStr = "文件检查通过！";
        }
        getResponse().setContentType("text/html; charset=utf-8");
        getResponse().setCharacterEncoding("utf-8");
        getResponse().getWriter().println(resultStr);
        getResponse().getWriter().close();
    }

    /**
     * 订单编辑中快捷导入持卡人信息页面
     * 
     * @return
     * @throws Exception
     */
    public String importFilePage() throws Exception {
        // 查询客户信息
        customerDTO = (CustomerDTO) sendService(ConstCode.CUSTOMER_DEPARTMENT_INQUERY, customerDTO).getDetailvo();
        List<DepartmentDTO> departmentDTOs = customerDTO.getDepartmentList();
        for (DepartmentDTO departmentDTO : departmentDTOs) {
            Map<String, String> department = new HashMap<String, String>();
            department.put("entityId", departmentDTO.getDepartmentId());
            department.put("entityName", departmentDTO.getDepartmentName());
            departmentLists.add(department);
        }
        return "importFilePage";
    }

    /**
     * 订单编辑中快捷导入持卡人
     */
    public String importFileInOrder() throws Exception {
        if (null == cardholderDTO.getEntityId() || "".equals(cardholderDTO.getEntityId().trim())) {
            cardholderDTO.setEntityId(customerDTO.getEntityId());
        }
        String resultStr = parserFile();
        if ("".equals(resultStr)) {
            cardholderDTO = (CardholderDTO) sendService(ConstCode.CARDHOLDER_SERVICE_IMPORT_RETRUN_IDS, cardholderDTO)
                    .getDetailvo();
        } else {
            addActionError(resultStr);
        }
        if (hasErrors()) {
            return INPUT;
        }
        try {
            /**
             * 注：暂用defaultEntityId
             * */
            String defaultEntityIdString = sellOrderInputDTO.getDefaultEntityId();
            sellOrderInputDTO.setDefaultEntityId(SystemInfo.getParameterValue(SystemInfoConstants.ORDER_CARD_MAXIMUM));
            sellOrderInputDTO.setSellOrderDTO(sellOrderDTO);
            // orderListStr = new String[1];
            // orderListStr[0] = cardholderDTO.getCardholderId();
            sellOrderInputDTO.setOrderListStr(cardholderDTO.getCardholderIds());

            ListPageInit(null, sellOrderInputDTO.getSellOrderCardListQueryDTO());

            ListPageInit(null, sellOrderInputDTO.getSellOrderListQueryDTO());

            sellOrderInputDTO = (SellOrderInputDTO) sendService(ConstCode.SELL_ORDER_INSERT_CARDHOLDER,
                    sellOrderInputDTO).getDetailvo();
            sellOrderInputDTO.setDefaultEntityId(defaultEntityIdString);
            if (!this.hasErrors()) {
                getRequest().setAttribute("sucessMessage", "添加订单明细信息成功!");
            } else {
                return importFilePage();
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return "importSucess";
    }

    /**
     * 导入持卡人
     */
    public String importFile() throws Exception {
        String resultStr = parserFile();
        if ("".equals(resultStr)) {
            sendService(ConstCode.CARDHOLDER_SERVICE_IMPORT, cardholderDTO);
        } else {
            addActionError(resultStr);
        }
        if (hasErrors()) {
            return INPUT;
        }
        addActionMessage("导入持卡人成功！");
        return list();
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

    /**
     * 解析导入持卡人文件
     */
    private String parserFile() throws Exception {
        String resultStr = "";
        if (null == cardholderDTO.getEntityId() || "".equals(cardholderDTO.getEntityId())) {
            addFieldError("cardholderDTO.entityId", "客户必须选择！");
            resultStr = "客户必须选择！";
            return resultStr;
        }
        if (null == cardholderFile) {
            resultStr = "导入文件不存在！";
            return resultStr;
        }
        if (cardholderFileFileName.isEmpty()) {
            resultStr = "导入文件不能为空！";
            return resultStr;
        }
        if (!".txt".equals(getExtension())) {
            resultStr = "导入文件必须为.txt格式！";
            return resultStr;
        }
        if(new FileInputStream(cardholderFile).available()==0){
        	resultStr = "导入的文件内容为空！";
            return resultStr;
        }
        LineNumberReader lineNumberReader = null;
        // try {
        // 取部门列表数
        int departments = 0;
        List<DepartmentDTO> departmentDTOs = new ArrayList<DepartmentDTO>();
        // 用于检查持卡人部门是否在规定部门内
        Map<String, DepartmentDTO> departmentMap = new HashMap<String, DepartmentDTO>();
        // 取持卡人数目
        int holders = 0;
        List<CardholderDTO> cardholderDTOList = new ArrayList<CardholderDTO>();

        lineNumberReader = new LineNumberReader(new InputStreamReader(new FileInputStream(cardholderFile), FileOperate
                .getCharset(cardholderFile)));
        String strLine;
        while ((strLine = lineNumberReader.readLine()) != null) {
            // 判断客户名称是否符合要求
            if (lineNumberReader.getLineNumber() == 2) {
                String[] strLines = strLine.split("\t");
                try {
                    if (null == strLines[1] || "".equals(strLines[1].trim())) {
                        resultStr = "客户名称必须录入！";
                        break;
                    }
                    if (!strLines[1].trim().equals(customerDTO.getCustomerName())) {
                        resultStr = "客户名称:" + strLines[1].trim() + " 与所选客户不匹配！";
                        break;
                    }
                } catch (Exception e) {
                    resultStr = "客户名称为空录入不正确！";
                    break;
                }

            }
            // 空行
            if (lineNumberReader.getLineNumber() == 3) {
                if (!strLine.trim().equals("")) {
                    resultStr = "第3行格式不正确！";
                    break;
                }
            }
            // 取部门数目
            if (lineNumberReader.getLineNumber() == 4) {
                String[] strLines = strLine.split("\t");
                try {
                    departments = new Integer(strLines[1]);
                } catch (Exception e) {
                    resultStr = "部门数目录入不正确！";
                    break;
                }
            }
            // 空行
            if (lineNumberReader.getLineNumber() == 5) {
                if (!strLine.trim().equals("")) {
                    resultStr = "第5行格式不正确！";
                    break;
                }
            }
            
            
           /* String[] strLines1 = strLine.split("\t");
            String idNo1 = strLines1[7].trim();
            CardholderDTO cardholderDTO21 = new CardholderDTO();
            cardholderDTO21.setIdNo(idNo1);
            //校验身份证是否存在
            List<CardholderDTO> datas1 = (ArrayList<CardholderDTO>)this.sendService(ConstCode.CARDHOLDER_SERVICE_SELECT_BY_IDNO, cardholderDTO21).getDetailvo();
    		if (datas1.size() > 0) {
    			resultStr = idNo1 + "身份证号已存在！";
            }*/
            
            
            // 取部信息
            if (lineNumberReader.getLineNumber() >= 6 && lineNumberReader.getLineNumber() < (6 + departments)) {
                // if (strLine.trim().equals("")) {
                // resultStr = "部门信息不允许为空！";
                // break;
                // }
                String[] strLines = strLine.split("\t");
                try {
                    if (null == strLines[1] || "".equals(strLines[1].trim())) {
                        resultStr = "部门信息必须录入！";
                        break;
                    }
                } catch (Exception e) {
                    resultStr = "部门信息为空录入不正确！";
                    break;
                }
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setDepartmentName(strLines[1].trim());
                departmentDTO.setEntityId(cardholderDTO.getEntityId());
                departmentDTO.setDefaultFlag(DataBaseConstant.DEFAULT_FLAG_NO);
                departmentDTOs.add(departmentDTO);
                DepartmentDTO replaced = departmentMap.put(strLines[1].trim(), departmentDTO);
                if (null != replaced) {
                    resultStr = "第" + lineNumberReader.getLineNumber() + "行，部门名称： " + strLines[1].trim() + "  有重复，请检查！";
                    break;
                }

            }
            // 空行
            if (lineNumberReader.getLineNumber() == (6 + departments)) {
                if (!strLine.trim().equals("")) {
                    resultStr = "第" + lineNumberReader.getLineNumber() + "行格式不正确！";
                    break;
                }
            }
            // 取持卡人数目
            if (lineNumberReader.getLineNumber() == (7 + departments)) {
                String[] strLines = strLine.split("\t");
                try {
                    holders = new Integer(strLines[1]);
                } catch (Exception e) {
                    resultStr = "持卡人数目录入不正确！";
                    break;
                }
            }
            // 空行
            if (lineNumberReader.getLineNumber() == (8 + departments)) {
                if (!strLine.trim().equals("")) {
                    resultStr = "第" + lineNumberReader.getLineNumber() + "行格式不正确！";
                    break;
                }
            }

            // 持卡人表头
            if (lineNumberReader.getLineNumber() == (9 + departments)) {
                if (strLine.trim().equals("")) {
                    resultStr = "持卡人表头不允许为空！";
                    break;
                }
                String[] strLines = strLine.split("\t", 15);
                if (strLines.length != 14) {
                    resultStr = "持卡人表头格式不正确！";
                    break;
                }
            }
            // 持卡人信息
            if (lineNumberReader.getLineNumber() >= (10 + departments)
                    && lineNumberReader.getLineNumber() < (10 + departments + holders)) {
                if ("".equals(strLine.trim())) {
                    resultStr = "第" + lineNumberReader.getLineNumber() + "行格式不正确！";
                    break;
                }
                CardholderDTO cardholderDTO = new CardholderDTO();
                String[] strLines = strLine.split("\t", 15);
                // 外部系统代码(工号)
                String externalId = strLines[0].trim();
                cardholderDTO.setExternalId(externalId);
                // 持卡人姓名
                String firstName = strLines[1].trim();
                if ("".equals(firstName)) {
                    resultStr = "第" + lineNumberReader.getLineNumber() + "行持卡人姓名不能为空！";
                    break;
                }
                cardholderDTO.setFirstName(firstName);
                // last name
                String lastName = strLines[2].trim();
                cardholderDTO.setLastName(lastName);
                // 部门名称
                String departmentName = strLines[3].trim();
                // 检查部门名称是否是规定部门
                if (!departmentMap.containsKey(departmentName)) {
                    resultStr = "第" + lineNumberReader.getLineNumber() + "行，部门：" + departmentName + "  不是规定的部门！";
                    break;
                }
                cardholderDTO.setDepartmentName(departmentName);
                // 移动电话号码
                String cardholderMobile = strLines[4].trim();
                cardholderDTO.setCardholderMobile(cardholderMobile);
                // email
                String cardholderEmail = strLines[5].trim();
                cardholderDTO.setCardholderEmail(cardholderEmail);
                // 证件类型
                String idTypeName = strLines[6].trim();
                if (!"".equals(idTypeName)) {
                    String idType = SystemInfo.getDictCode(getUser().getEntityId(), "401", idTypeName);
                    if (idType == null) {
                        resultStr = "证件类型：" + idTypeName + " 不符合规则,请核对！";
                        break;
                    }
                    cardholderDTO.setIdType(idType.toString());
                }
                
                String idNo = strLines[7].trim();
                CardholderDTO cardholderDTO2 = new CardholderDTO();
                if(cardholderDTO.getIdType().equals("1")){
                	cardholderDTO2.setIdNo(idNo.toUpperCase());
                }
                
               /* //校验身份证是否存在
                List<CardholderDTO> datas = (ArrayList<CardholderDTO>)this.sendService(ConstCode.CARDHOLDER_SERVICE_SELECT_BY_IDNO, cardholderDTO2).getDetailvo();
        		if (datas.size() > 0) {
        			resultStr = idNo + "身份证号已存在！";
        			break;
                }*/
                /*if (idTypeName.equals("身份证")) {
                		String substring = idNo.substring(17, 18);
                		if(!Character.isDigit(substring.charAt(0))){//不是数字
                			if(!Character.isUpperCase(substring.charAt(0))){ //小写
                				cardholderDTO.setIdNo(idNo.substring(0, 17)+"X");
                			}else{
        	        			cardholderDTO.setIdNo(idNo);
        	        		}
                		}else{
    	        			cardholderDTO.setIdNo(idNo);
    	        		}
        		}else{
        			cardholderDTO.setIdNo(idNo);
        		}*/
                if(cardholderDTO.getIdType().equals("1")){
                	cardholderDTO.setIdNo(idNo.toUpperCase());
                }
        		
                // 生日
                String strBirthday = strLines[8].trim();
                if (!"".equals(strBirthday)) {
                    try {
                        if (strBirthday.length() != 10) {
                            resultStr = "生日格式必须为yyyy-MM-dd：" + strBirthday + " 格式不正确！";
                            break;
                        }
                        // 转为yyyyMMdd格式
                        cardholderDTO.setCardholderBirthday(DateUtil.getFormatTime(strBirthday));
                    } catch (Exception e) {
                        resultStr = "生日格式必须为yyyy-MM-dd：" + strBirthday + " 格式不正确！";
                        break;
                    }
                }
                // 性别
                String strGender = strLines[9].trim();
                if (!strGender.equals("")) {
                    String gender = SystemInfo.getDictCode(getUser().getEntityId(), "402", strGender);
                    if (gender == null) {
                        resultStr = "性别：" + strGender + " 不符合规则,请核对！";
                        break;
                    }
                    cardholderDTO.setCardholderGender(gender);
                }
                // 称谓
                String strSalutation = strLines[10].trim();
                if (!strSalutation.equals("")) {
                    String salutation = SystemInfo.getDictCode(getUser().getEntityId(), "104", strSalutation);
                    if (salutation == null) {
                        resultStr = "称谓：" + strSalutation + " 不符合规则,请核对！";
                        break;
                    }
                    cardholderDTO.setCardholderSalutation(salutation.toString());
                }
                // 职务
                String strFunction = strLines[11].trim();
                if (!"".equals(strFunction)) {
                    String function = SystemInfo.getDictCode(getUser().getEntityId(), "105", strFunction);
                    if (function == null) {
                        resultStr = "职务：" + strFunction + " 不符合规则,请核对！";
                        break;
                    }
                    cardholderDTO.setCardholderFunction(function.toString());
                }
                // 持卡人类型
                String strSegment = strLines[12].trim();
                if (!"".equals(strSegment)) {
                    String segment = SystemInfo.getDictCode(getUser().getEntityId(), "103", strSegment);
                    if (segment == null) {
                        resultStr = "持卡人类型：" + strSegment + " 不符合规则,请核对！";
                        break;
                    }
                    cardholderDTO.setCardholderSegment(segment.toString());
                }
                // 持卡人说明
                String cardholderComment = strLines[13].trim();
                cardholderDTO.setCardholderComment(cardholderComment);

                cardholderDTOList.add(cardholderDTO);
            }

            if (lineNumberReader.getLineNumber() == 10 + departments + holders) {
                if (!"".equals(strLine.trim())) {
                    resultStr = "第" + lineNumberReader.getLineNumber() + "行格式不正确！";
                    break;
                }
            }
        }

        cardholderDTO.setDepartmentList(departmentDTOs);
        cardholderDTO.setCardholderDTOList(cardholderDTOList);
        /*
         * } catch (Exception e) { this.logger.error(e.getMessage()); } finally { if (null != lineNumberReader) {
         * lineNumberReader.close(); } }
         */

        return resultStr;
    }
    
    /**
	 * 根据证件号、证件类型  取得持卡人信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void checkIdNo() {
		try {
			boolean existFlag = false;
			if(cardholderDTO.getIdType().equals("1")){
				cardholderDTO.setIdNo(cardholderDTO.getIdNo().toUpperCase());
			}
			
			List<CardholderDTO> datas = (ArrayList<CardholderDTO>)this.sendService(ConstCode.CARDHOLDER_SERVICE_SELECT_BY_IDNO, cardholderDTO).getDetailvo();
//			OperationResult o = this.sendService(ConstCode.CARDHOLDER_SERVICE_SELECT, cardholderDTO);
//			List<CardholderDTO> datas = (ArrayList<CardholderDTO>) o.getDetailvo();
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
	
	/**
	 * 查询是否有其他客户有此身份证号
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void checkOtherIdNo() {
		try {
			boolean existFlag = false;
			if(cardholderDTO.getIdType().equals("1")){
				cardholderDTO.setIdNo(cardholderDTO.getIdNo().toUpperCase());
			}
			
			List<CardholderDTO> datas = (ArrayList<CardholderDTO>)this.sendService(ConstCode.CHECK_OTHER_CARDHOLDER_BY_ID_NO, cardholderDTO).getDetailvo();
//			OperationResult o = this.sendService(ConstCode.CARDHOLDER_SERVICE_SELECT, cardholderDTO);
//			List<CardholderDTO> datas = (ArrayList<CardholderDTO>) o.getDetailvo();
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
	
    

    public CardholderQueryDTO getCardholderQueryDTO() {
        return cardholderQueryDTO;
    }

    public void setCardholderQueryDTO(CardholderQueryDTO cardholderQueryDTO) {
        this.cardholderQueryDTO = cardholderQueryDTO;
    }

    public CardholderDTO getCardholderDTO() {
        return cardholderDTO;
    }

    public void setCardholderDTO(CardholderDTO cardholderDTO) {
        this.cardholderDTO = cardholderDTO;
    }

    public String[] getCardholderIds() {
        return cardholderIds;
    }

    public void setCardholderIds(String[] cardholderIds) {
        this.cardholderIds = cardholderIds;
    }

    public PageDataDTO getPageDataDTO() {
        return pageDataDTO;
    }

    public void setPageDataDTO(PageDataDTO pageDataDTO) {
        this.pageDataDTO = pageDataDTO;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
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

    public SellOrderCardListQueryDTO getSellOrderCardListQueryDTO() {
        return sellOrderCardListQueryDTO;
    }

    public void setSellOrderCardListQueryDTO(SellOrderCardListQueryDTO sellOrderCardListQueryDTO) {
        this.sellOrderCardListQueryDTO = sellOrderCardListQueryDTO;
    }

    public SellOrderInputDTO getSellOrderInputDTO() {
        return sellOrderInputDTO;
    }

    public void setSellOrderInputDTO(SellOrderInputDTO sellOrderInputDTO) {
        this.sellOrderInputDTO = sellOrderInputDTO;
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

    public void setPersonFlag(String personFlag) {
        this.personFlag = personFlag;
    }

    public String getPersonFlag() {
        return personFlag;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public Integer getCardInfo_totalRows() {
        return cardInfo_totalRows;
    }

    public void setCardInfo_totalRows(Integer cardInfoTotalRows) {
        cardInfo_totalRows = cardInfoTotalRows;
    }

    public List<Map<String, String>> getDepartmentLists() {
        return departmentLists;
    }

    public void setDepartmentLists(List<Map<String, String>> departmentLists) {
        this.departmentLists = departmentLists;
    }
    
    
    /***************************************新增区域*************************************************/
    /**
     * 查询持卡人列表
     * @return
     * @throws Exception
     */
    public String inqueryShow() throws Exception {
        if (cardManagementFlag == null || "".equals(cardManagementFlag)) {
            ListPageInit(null, cardholderQueryDTO);
            if (cardholderQueryDTO.isQueryAll()) {
                cardholderQueryDTO.setQueryAll(false);
                cardholderQueryDTO.setRowsDisplayed(Integer.parseInt((SystemInfo
                        .getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM))));
            }
            pageDataDTO = (PageDataDTO) this.sendService(ConstCode.CARDHOLDER_SERVICE_INQUERYSHOW, cardholderQueryDTO)
                    .getDetailvo();
            if (pageDataDTO.getData().size() > 0) {
                totalRows = pageDataDTO.getTotalRecord();
            } else {
                totalRows = 0;
            }

            return "list";
        } else
            return "cardManageQuery";
    }
    
    /**
     * 个人持卡人添加
     * @return
     * @throws Exception
     */
    /*@SuppressWarnings("unchecked")
    public String saveCardholder()throws Exception{
        if (null != cardholderDTO.getCardholderBirthday() && !"".equals(cardholderDTO.getCardholderBirthday())) {
            cardholderDTO.setCardholderBirthday(DateUtil.getFormatTime(cardholderDTO.getCardholderBirthday()));
        }
        if (null != cardholderDTO.getCloseDate() && !"".equals(cardholderDTO.getCloseDate())) {
            cardholderDTO.setCloseDate(DateUtil.getFormatTime(cardholderDTO.getCloseDate()));
        }
        if (null != cardholderDTO.getValidity() && !"".equals(cardholderDTO.getValidity())) {
            cardholderDTO.setValidity(DateUtil.getFormatTime(cardholderDTO.getValidity()));
        }
        String idNo = cardholderDTO.getIdNo();
        if(cardholderDTO.getIdType().equals("1")){
                cardholderDTO.setIdNo(idNo.toUpperCase());
        }
        List<CardholderDTO> datas = (ArrayList<CardholderDTO>)this.sendService(ConstCode.CARDHOLDER_SERVICE_SELECT_BY_IDNO, cardholderDTO).getDetailvo();
                if (datas.size() > 0) {
            addFieldError("cardholderDTO.IdNo", "身份证号已存在！");
        }
        if (hasFieldErrors()) {
                return INPUT;
        }
        
        this.sendService(ConstCode.CARDHOLDER_SERVICE_SAVE, cardholderDTO);

        if (hasActionErrors()) {
            return INPUT;
        }
        addActionMessage("添加持卡人信息成功！");
        return this.inqueryShow();
    }*/
    
    //企业持卡人添加
    public String addCustomer() throws Exception {
        companyInfoDTO = new CompanyInfoDTO();
        companyInfoDTO.setIsblacklist("0");//黑名单标识
        companyInfoDTO.setRiskGrade("O");//风险等级标识
        return "addCustomer";
    }
    
    /**
     * 企业持卡人添加
     * @return
     * @throws Exception
     */
    public String insertcustomer() throws Exception {       
		this.sendService(ConstCode.CHECK_COMPANYINFO_BY_IDNO, companyInfoDTO);
        // 根据身份证号查询未注销客户信息，判断是否已经存在该客户信息
        if(hasActionErrors()) {//后台如果有同类型的公司则抛出异常
        	 return INPUT;
        }   
    	
        //this.sendService(ConstCode.COMPANYINFO_SERVICE_INSERT, companyInfoDTO);
    	companyInfoDTO = (CompanyInfoDTO) sendService(ConstCode.COMPANYINFO_SERVICE_INSERT, companyInfoDTO).getDetailvo(); 
        
        if (hasActionErrors()) {
            return INPUT;
        }
        addActionMessage("添加企业持卡人信息成功！");
        
        JudgeInforDTO.setEntityID(companyInfoDTO.getRelationNo());
        JudgeInforDTO.setCName(companyInfoDTO.getCustomerDTO().getCustomerName());
        //JudgeInforDTO.setCertID(companyInfoDTO.getCompanyId());//证件号--企业不需要 
        JudgeInforDTO.setUserType("2");
        JudgeInforDTO.setJudgeType("5");
        JudgeInforDTO.setCustomerType("2");
        checkCusUpdate();
        
        
        
        return this.inqueryShow();
    }
    
    /**
     * 根据证件号、证件类型  取得企业持卡人信息
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void checkCusIdNo() {
            try {
                boolean existFlag = false;
                if(companyInfoDTO.getCorpCredType().equals("1")){
                    companyInfoDTO.setCorpCredId(companyInfoDTO.getCorpCredId().toUpperCase());
                }                
                List<CompanyInfoDTO> datas = (ArrayList<CompanyInfoDTO>)this.sendService(ConstCode.CARDHOLDER_SERVICE_SELECTCUS_BY_IDNO, companyInfoDTO).getDetailvo();
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
       
    /**
     * 查询是否有其他客户有此身份证号
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void checkOtherCusIdNo() {
            try {
                boolean existFlag = false;
                if(companyInfoDTO.getCorpCredType().equals("1")){
                    companyInfoDTO.setCorpCredId(companyInfoDTO.getCorpCredId().toUpperCase());
                }                 
                List<CompanyInfoDTO> datas = (ArrayList<CompanyInfoDTO>)this.sendService(ConstCode.CHECK_OTHER_CUSCARDHOLDER_BY_ID_NO, companyInfoDTO).getDetailvo();
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
    
    
    /**
     * 查看企业持卡人信息
     * @return
     * @throws Exception
     */
    public String cusView()throws Exception{
        try {
            if (null == cardManagementDTO) {
                            cardManagementDTO = new CardManagementDTO();
            }
        ListPageInit(null, sellOrderCardListQueryDTO);
        if (null != sellOrderCardListQueryDTO.getCardholderId()
                && !sellOrderCardListQueryDTO.getCardholderId().equals("")) {
            companyInfoDTO = new CompanyInfoDTO();
            companyInfoDTO.setRelationNo(sellOrderCardListQueryDTO.getCardholderId());
        }
        if (null != companyInfoDTO.getRelationNo()
                && !companyInfoDTO.getRelationNo().equals("")) {
            sellOrderCardListQueryDTO.setCardholderId(companyInfoDTO.getRelationNo());
        }
        companyInfoDTO.setSellOrderCardListQueryDTO(this.sellOrderCardListQueryDTO);
        companyInfoDTO = (CompanyInfoDTO) sendService(ConstCode.CUSCARDHOLDER_SERVICE_VIEW, companyInfoDTO).getDetailvo();
        if (hasActionErrors()) {
            return list();
        }
        if (companyInfoDTO.getCardholderCardList() != null
                && companyInfoDTO.getCardholderCardList().getData() != null) {
            totalRows = companyInfoDTO.getCardholderCardList().getTotalRecord();
        } else {
            totalRows = 0;
        }
    } catch (Exception e) {
        this.logger.error(e.getMessage());
    }
    return "cusView";
    }
    
    /**
     * 编辑企业持卡人信息
     * @return
     * @throws Exception
     */
      public String editCus() throws Exception {
          if (null != cardholderIds && null != cardholderIds[0] && !cardholderIds[0].equals("")) {
              companyInfoDTO.setRelationNo(cardholderIds[0]);
          }
          cusView();
          if (hasActionErrors()) {
              return this.inqueryShow();
          }
          return "editCus";
      }
      
    /**
     * 更新企业持卡人信息
     * @return
     * @throws Exception
     */
      public String updateCus() throws Exception {
          try {            
              sendService(ConstCode.CARDHOLDER_SERVICE_UPDATECUS, companyInfoDTO);
              if (hasActionErrors()) {
                  return INPUT;
              }
              addActionMessage("更新企业持卡人信息成功！");
          } catch (Exception e) {
              this.logger.error(e.getMessage());
          }
          return this.inqueryShow();
      }
      
      /**
       * 查看个人持卡人审核信息
       * @return
       * @throws Exception
       */
      public String checkPerView() throws Exception{
          try {              
              ListPageInit("cardListTable", sellOrderCardListQueryDTO);
              if (null != sellOrderCardListQueryDTO.getCardholderId()
                      && !sellOrderCardListQueryDTO.getCardholderId().equals("null,1")) {
                  cardholderDTO = new CardholderDTO();
                 String[] cardholderIds=sellOrderCardListQueryDTO.getCardholderId().split(",");
                  cardholderDTO.setCardholderId(cardholderIds[0]);
                  if (null == cardManagementDTO) {
                                 cardManagementDTO = new CardManagementDTO();
                                 if(cardholderIds[1].equals("2")){
                                         cardManagementDTO.setDataState("2");
                                 }else{
                                         cardManagementDTO.setDataState("1");
                                 }
                                 
                 }
              }
              cardholderDTO.setSellOrderCardListQueryDTO(this.sellOrderCardListQueryDTO);
              cardholderDTO = (CardholderDTO) sendService(ConstCode.CARDHOLDER_PER_SERVICE_VIEW, cardholderDTO).getDetailvo();
              if (hasActionErrors()) {
                  return checkList();
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
              e.printStackTrace();
              this.logger.error(e.getMessage());
          }
         return "checkPerView";
          
      }
      
      
      /**
       * 查看企业持卡人审核信息
       * @return  
       * @throws Exception
       */
      public String checkCusView() throws Exception{
          try{
              ListPageInit("cardListTable", sellOrderCardListQueryDTO);
              if (null != sellOrderCardListQueryDTO.getCardholderId()
                      && !sellOrderCardListQueryDTO.getCardholderId().equals("null,1")) {
                  companyInfoDTO = new CompanyInfoDTO();
                 String[] cardholderIds=sellOrderCardListQueryDTO.getCardholderId().split(",");
                 companyInfoDTO.setRelationNo(cardholderIds[0]);
                  if (null == cardManagementDTO) {
                                 cardManagementDTO = new CardManagementDTO();
                                 if(cardholderIds[1].equals("2")){
                                         cardManagementDTO.setDataState("2");
                                 }else{
                                         cardManagementDTO.setDataState("1");
                                 }                         
                 }
              }
              companyInfoDTO.setSellOrderCardListQueryDTO(this.sellOrderCardListQueryDTO);
              companyInfoDTO = (CompanyInfoDTO) sendService(ConstCode.CARDHOLDER_CUS_SERVICE_VIEW, companyInfoDTO).getDetailvo();
              if (hasActionErrors()) {
                  return checkList();
              }         
          }catch(Exception e){
              e.printStackTrace();
              this.logger.error(e.getMessage());
          }        
          return "checkCusView";
      }
      
      /**
       * 验证个人持卡人黑名单
       * @return
       * @throws Exception
       */
      public String CheckblackListByPer() throws Exception {
          String cardholderId =  sellOrderCardListQueryDTO.getCardholderId();
          cardholderId+=",1";
          sellOrderCardListQueryDTO.setCardholderId(cardholderId);
          String resultPage = checkPerView();
          if (!hasActionErrors()) {
              StringBuffer buffer = new StringBuffer("匹配用户黑名单成功!");
              String blackList = cardholderDTO.getIsblacklist() == null ? "0" : cardholderDTO.getIsblacklist();
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
      
      /**
       * 验证企业持卡人黑名单
       * @return
       * @throws Exception
       */
      public String CheckblackListByCus() throws Exception {
          String cardholderId =  sellOrderCardListQueryDTO.getCardholderId();
          cardholderId+=",1";
          sellOrderCardListQueryDTO.setCardholderId(cardholderId);
          String resultPage = checkCusView();
          if (!hasActionErrors()) {
              StringBuffer buffer = new StringBuffer("匹配用户黑名单成功!");
              String blackList = companyInfoDTO.getIsblacklist() == null ? "0" : companyInfoDTO.getIsblacklist();
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
              this.addActionMessage(buffer.toString());      
          }else {
              this.addActionError("调用黑名单数据接口失败！");      
          }          
          return resultPage;
  }
      
      /**
       * 更新个人持卡人审核信息
       * @return
       * @throws Exception
       */
      public String checkPerUpdate() throws Exception {
    	  if(null !=JudgeInforDTO.getJudgeType()&& "5".equals(JudgeInforDTO.getJudgeType())) {
    		this.sendService(ConstCode.CUSTOMER_BLACK_RISK_JUDGE, JudgeInforDTO); 
    		return CheckblackListByPer();
    	  }else {
    		  sendService(ConstCode.CHECK_PER_CARDHOLDER, cardholderDTO);
    	  }
          if (hasActionErrors()) {
              return checkList();
          }
          addActionMessage("操作成功！");
          if (cardManagementFlag == null || "".equals(cardManagementFlag)) {
              ListPageInit(null, cardholderQueryDTO);
              pageDataDTO = (PageDataDTO) this.sendService(ConstCode.CHECK_CARDHOLDER_LIST, cardholderQueryDTO)
                      .getDetailvo();
              if (pageDataDTO.getData().size() > 0) {
                  totalRows = pageDataDTO.getTotalRecord();
              } else {
                  totalRows = 0;
              }
              return "checkPerUpdate";
          } else
              return "cardManageQuery";
          
      }
      
      
      
      /**
       * 更新企业持卡人审核信息
       * @return
       * @throws Exception
       */
      public String checkCusUpdate() throws Exception {
    	  if(null !=JudgeInforDTO.getJudgeType()&& JudgeInforDTO.getJudgeType().equals("5")) {
    	      this.sendService(ConstCode.CUSTOMER_BLACK_RISK_JUDGE, JudgeInforDTO); 
    	      return CheckblackListByCus();
    	  }else {
              sendService(ConstCode.CHECK_CUS_CARDHOLDER, companyInfoDTO);                   
    	  }
    	  if (hasActionErrors()) {
    	      return checkList();            
    	  }
          addActionMessage("操作成功！");
          if (cardManagementFlag == null || "".equals(cardManagementFlag)) {
              ListPageInit(null, cardholderQueryDTO);
              pageDataDTO = (PageDataDTO) this.sendService(ConstCode.CHECK_CARDHOLDER_LIST, cardholderQueryDTO)
                      .getDetailvo();
              if (pageDataDTO.getData().size() > 0) {
                  totalRows = pageDataDTO.getTotalRecord();
              } else {
                  totalRows = 0;
              }
              return "checkCusUpdate";
          } else
              return "cardManageQuery";
      }
      
      /**
       * 根据企业证件类型判断是否重复
       */
      
      public void checkLicense() {
          try {
              boolean existFlag = false;
              this.sendService(ConstCode.CHECK_COMPANYINFO_BY_IDNO, companyInfoDTO);
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
       * 删除企业持卡人信息(逻辑删除将数据状态变为0)
       * @return
       * @throws Exception
       */
      public String deleteCus() throws Exception {
          if (null != cardholderIds && cardholderIds.length > 0) {
              companyInfoDTO.setCardholderIds(cardholderIds);
              sendService(ConstCode.CARDHOLDERCUS_SERVICE_DELETE, companyInfoDTO);
              if (!hasActionErrors()) {
                  addActionMessage("删除企业持卡人成功！");
              }
          }
          return this.inqueryShow();
      }
    
      
      /**
       * 个人持卡人添加
       * @return
       * @throws Exception
       */
      @SuppressWarnings("unchecked")
      public String insertcardholder()throws Exception{
          String idNo = cardholderDTO.getIdNo();
          if(cardholderDTO.getIdType().equals("1")){
                  cardholderDTO.setIdNo(idNo.toUpperCase());
          }
          List<CardholderDTO> datas = (ArrayList<CardholderDTO>)this.sendService(ConstCode.CARDHOLDER_SERVICE_SELECT_BY_IDNO, cardholderDTO).getDetailvo();
          if (datas.size() > 0) {
        	  cardholderDTO.setIdNo("");
              this.addActionError("证件号已存在！");
              //addFieldError("cardholderDTO.IdNo", "身份证号已存在！");
          }
          if (hasActionErrors()) {
                  return INPUT;
          }
          
          if (null != cardholderDTO.getCardholderBirthday() && !"".equals(cardholderDTO.getCardholderBirthday())) {
        	  cardholderDTO.setCardholderBirthday(DateUtil.getFormatTime(cardholderDTO.getCardholderBirthday()));
          }
          if (null != cardholderDTO.getCloseDate() && !"".equals(cardholderDTO.getCloseDate())) {
        	  cardholderDTO.setCloseDate(DateUtil.getFormatTime(cardholderDTO.getCloseDate()));
          }
          if (null != cardholderDTO.getValidity() && !"".equals(cardholderDTO.getValidity())) {
        	  cardholderDTO.setValidity(DateUtil.getFormatTime(cardholderDTO.getValidity()));
          }
          //cardholderDTO = this.sendService(ConstCode.CARDHOLDER_SERVICE_SAVE, cardholderDTO);
          cardholderDTO = (CardholderDTO) sendService(ConstCode.CARDHOLDER_SERVICE_SAVE, cardholderDTO).getDetailvo(); 
          if (hasActionErrors()) {
        	  return INPUT;
          }
          addActionMessage("添加持卡人信息成功！");

          JudgeInforDTO.setEntityID(cardholderDTO.getCardholderId());
          JudgeInforDTO.setCName(cardholderDTO.getFirstName());
          JudgeInforDTO.setCertID(cardholderDTO.getIdNo());
          JudgeInforDTO.setUserType("2");
          JudgeInforDTO.setJudgeType("5");
          JudgeInforDTO.setCountry(cardholderDTO.getCountry());
          JudgeInforDTO.setCustomerType("1");
          checkPerUpdate();
          
          return this.inqueryShow();
      }
      
      
      /**riskGRadeCrm
       * 客户风险等级同步
       * @return
     * @throws Exception 
       */
      public String riskGRadeCrmInfo() throws Exception{
          JudgeInforDTO judgeInforDTO = new JudgeInforDTO();
          judgeInforDTO.setJudgeType("4");
          judgeInforDTO.setCName(new String(customerName.getBytes("iso-8859-1"),"UTF-8"));
          judgeInforDTO.setCustomerType(customerType);
          judgeInforDTO.setCNum(customerId);
          judgeInforDTO.setUserType(cusType);
          judgeInforDTO.setEntityID(entityId);
          this.sendService(ConstCode.CUSTOMER_BLACK_RISK_JUDGE, judgeInforDTO).getDetailvo();
          if (!hasActionErrors()) {
              this.addActionMessage("获取用户信息成功！");
          }else {
              this.addActionMessage("调用数据接口失败！");
              return INPUT;
          }
          return queryRiskGRade();
      }
      
      
      /**
       * 查询客户信息列表
       * @return
       * @throws Exception
       */
      public String queryRiskGRade()throws Exception{
          
              ListPageInit(null, cardholderQueryDTO);
              pageDataDTO = (PageDataDTO) this.sendService(ConstCode.CARDHOLDER_SERVICE_QUERY, cardholderQueryDTO)
                      .getDetailvo();
              if (pageDataDTO.getData().size() > 0) {
                  totalRows = pageDataDTO.getTotalRecord();
              } else {
                  totalRows = 0;
              }

              return "queryList";
         
          
      }
      
      
      /**
       * 持卡人补录信息审核列表
       * @return
       * @throws Exception
       */
      public String informationAudit() throws Exception {
    	  
		ListPageInit(null, pictureInfoQueryDTO);
    	  if(!StringUtils.isEmpty(pictureInfoQueryDTO.getStartTime())&&!StringUtils.isEmpty(pictureInfoQueryDTO.getEndTime())) {
	 			if(!pictureInfoQueryDTO.getEndTime().equals("")&&!pictureInfoQueryDTO.getStartTime().equals("")){
	 				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
					Date startDate = sdf.parse(pictureInfoQueryDTO.getStartTime());
		        	Date endDate = sdf.parse(pictureInfoQueryDTO.getEndTime());
		        	if(startDate.getTime()>endDate.getTime()){
		        		addActionError("开始时间不能大于结束时间！");
		        		return "audit";
		        	}
	 			}
				
			}

    	  
      	  pageDataDTO = (PageDataDTO) this.sendService(ConstCode.CARDHOLDER_SERVICE_AUDIT_QUERY, pictureInfoQueryDTO)
                    .getDetailvo();
            if (pageDataDTO.getData().size() > 0) {
                totalRows = pageDataDTO.getTotalRecord();
            } else {
                totalRows = 0;
            }

      	return "audit";
      }
      
      
      /**
       * 补录信息审核页面
       * @return
       * @throws Exception
       */
      public String auditView()throws Exception{
          try {
              if (null == cardManagementDTO) {
                  cardManagementDTO = new CardManagementDTO();
              }
          ListPageInit(null, sellOrderCardListQueryDTO);
          if (null != sellOrderCardListQueryDTO.getCardholderId()
                  && !sellOrderCardListQueryDTO.getCardholderId().equals("")) {
              cardholderDTO = new CardholderDTO();
              cardholderDTO.setCardholderId(sellOrderCardListQueryDTO.getCardholderId());
          }
          if (null != cardholderDTO.getCardholderId()
                  && !cardholderDTO.getCardholderId().equals("")) {
              sellOrderCardListQueryDTO.setCardholderId(cardholderDTO.getCardholderId());
          }
          cardholderDTO.setSellOrderCardListQueryDTO(this.sellOrderCardListQueryDTO);
          cardholderDTO = (CardholderDTO) sendService(ConstCode.CARDHOLDER_SERVICE_VIEW, cardholderDTO).getDetailvo();
          if (hasActionErrors()) {
              return inqueryShow();
          }
          if (null != cardholderDTO.getCardholderBirthday() && !"".equals(cardholderDTO.getCardholderBirthday())) {
              cardholderDTO.setCardholderBirthday(DateUtil.dbFormatToDateFormat(cardholderDTO.getCardholderBirthday()));
          }
          if (null != cardholderDTO.getCloseDate() && !"".equals(cardholderDTO.getCloseDate())) {
              cardholderDTO.setCloseDate(DateUtil.dbFormatToDateFormat(cardholderDTO.getCloseDate()));
          }
          if (null != cardholderDTO.getValidity() && !"".equals(cardholderDTO.getValidity())) {
              cardholderDTO.setValidity(DateUtil.dbFormatToDateFormat(cardholderDTO.getValidity()));
          }
          if (cardholderDTO.getCardholderCardList() != null && cardholderDTO.getCardholderCardList().getData() != null) {
              totalRows = cardholderDTO.getCardholderCardList().getTotalRecord();
          } else {
              totalRows = 0;
          }    
          } catch (Exception e) {
              this.logger.error(e.getMessage());
          }
          return "auditView";    
      }
      
      
      public String auditCheck() throws Exception {
      	String idNo=cardholderDTO.getIdNo();
      	try {
      		if(StringUtils.isNotEmpty(idNo)) {
      			checkPeopleInfoDTO.setPeopleNo(idNo);
      			String telephone=cardholderDTO.getCardholderMobile();
      			String state=checkPeopleInfoDTO.getCheckState();
      			
      			
          		//更新审核状态
          		sendService(ConstCode.CARDHOLDER_SERVICE_AUDIT_EDIT, checkPeopleInfoDTO);
          		if (hasActionErrors()) {
          			informationAudit();
                      return INPUT;
                  }
          		
          		//如果审核未通过发送短信通知客户
      			if(state.equals("2")) {
      				//调用短信接口
    				SendShortMessageThread sendMsg=new SendShortMessageThread(telephone, "10055");
    				sendMsg.start();
      			}
          		addActionMessage("补录信息审核成功！");
          	}
  		} catch (Exception e) {
  			 this.logger.error(e.getMessage());
  			 addActionMessage("补录信息审核失败！");
  		}
      	
      	informationAudit();
      	return "auditCheck";
      }
      
      
    /***************************************新增区域*************************************************/
    private CompanyInfoDTO companyInfoDTO = new CompanyInfoDTO(); //企业持卡人DTO
    private String customerId;
    private String customerType;
    private String customerName;
    private String entityId;
        

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public CompanyInfoDTO getCompanyInfoDTO() {
        return companyInfoDTO;
    }

    public void setCompanyInfoDTO(CompanyInfoDTO companyInfoDTO) {
        this.companyInfoDTO = companyInfoDTO;
    }

	public JudgeInforDTO getJudgeInforDTO() {
		return JudgeInforDTO;
	}

	public void setJudgeInforDTO(JudgeInforDTO judgeInforDTO) {
		JudgeInforDTO = judgeInforDTO;
	}



}
