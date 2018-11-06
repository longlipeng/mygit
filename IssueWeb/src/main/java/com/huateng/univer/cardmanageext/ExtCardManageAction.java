package com.huateng.univer.cardmanageext;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.extremecomponents.table.context.HttpServletRequestContext;

import com.allinfinance.framework.constant.ConstCode;
import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.framework.dto.PageDataDTO;
import com.allinfinance.shangqi.gateway.dto.ApplyAndBindCardDTO;
import com.allinfinance.univer.cardmanagement.dto.BatchCardActionDTO;
import com.allinfinance.univer.cardmanagement.dto.CardBalanceDTO;
import com.allinfinance.univer.cardmanagement.dto.CardManagementDTO;
import com.allinfinance.univer.cardmanagement.dto.CardManagementQueryDTO;
import com.allinfinance.univer.seller.cardholder.dto.CardholderDTO;
import com.allinfinance.univer.system.sysparam.dto.EntitySystemParameterDTO;
import com.huateng.framework.action.BaseAction;
import com.huateng.framework.constant.Const;
import com.huateng.framework.constant.OrderConst;
import com.huateng.framework.constant.SystemInfoConstants;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.security.model.User;
import com.huateng.framework.util.DateUtil;
import com.huateng.framework.util.SystemInfo;

public class ExtCardManageAction extends BaseAction {
	private Logger logger = Logger.getLogger(ExtCardManageAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 卡操作类型operationType 1 挂失 ， 2 解挂，3 锁定，4 解锁，5 冻结 ，6 解冻 ,7 卡密重置，8 卡片延期 9
	 * 卡安全信息设置， 10 卡磁道重写，11 卡账户调整 12 交易查询
	 * 
	 */
	private CardholderDTO cardholderDTO = new CardholderDTO();
	private CardManagementDTO cardManagementDTO;
	private ApplyAndBindCardDTO applyAndBindCardDTO;
	private CardManagementQueryDTO cardManagementQueryDTO = new CardManagementQueryDTO();
	private PageDataDTO pageDataDTO = new PageDataDTO();
	private CardBalanceDTO cardBalanceDTO;
	private int totalRows;
	private String op = null;
	private String adjust = null;
	private EntitySystemParameterDTO entitySystemParameterDTO = new EntitySystemParameterDTO();
	private BigDecimal temp = new BigDecimal("100");
	private String error_jsp = "";
	private String isDisplay="none";
	private String isCall="0";
	private String[] accountId;
	private String[] creditAmont;
	private String[] posSingleAmount;
	private String[] posDailyAmount;
	private String[] webSingleAmount;
	private String[] webDailyAmount;
	private String total;
	
	//add 
	private String idNo;
    private String cardholderName;
    
	/**
	 * 区分发行机构还是营销机构
	 * */
	private String invalidFlag;
	/**
	 * 作废原因
	 * */
	private String invalidReason;
	private String username;
	private String password;
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String[] getCreditAmont() {
		return creditAmont;
	}

	public void setCreditAmont(String[] creditAmont) {
		this.creditAmont = creditAmont;
	}

	public String[] getAccountId() {
		return accountId;
	}

	public void setAccountId(String[] accountId) {
		this.accountId = accountId;
	}

	public String[] getPosSingleAmount() {
		return posSingleAmount;
	}

	public void setPosSingleAmount(String[] posSingleAmount) {
		this.posSingleAmount = posSingleAmount;
	}

	public String[] getPosDailyAmount() {
		return posDailyAmount;
	}

	public void setPosDailyAmount(String[] posDailyAmount) {
		this.posDailyAmount = posDailyAmount;
	}

	public String[] getWebSingleAmount() {
		return webSingleAmount;
	}

	public void setWebSingleAmount(String[] webSingleAmount) {
		this.webSingleAmount = webSingleAmount;
	}

	public String[] getWebDailyAmount() {
		return webDailyAmount;
	}

	public void setWebDailyAmount(String[] webDailyAmount) {
		this.webDailyAmount = webDailyAmount;
	}

	public CardholderDTO getCardholderDTO() {
		return cardholderDTO;
	}

	public void setCardholderDTO(CardholderDTO cardholderDTO) {
		this.cardholderDTO = cardholderDTO;
	}

	public String getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}

	public String getError_jsp() {
		return error_jsp;
	}

	public void setError_jsp(String errorJsp) {
		error_jsp = errorJsp;
	}

	public BigDecimal getTemp() {
		return temp;
	}

	public void setTemp(BigDecimal temp) {
		this.temp = temp;
	}

	public String getAdjust() {
		return adjust;
	}

	public void setAdjust(String adjust) {
		this.adjust = adjust;
	}

	public EntitySystemParameterDTO getEntitySystemParameterDTO() {
		return entitySystemParameterDTO;
	}

	public void setEntitySystemParameterDTO(
			EntitySystemParameterDTO entitySystemParameterDTO) {
		this.entitySystemParameterDTO = entitySystemParameterDTO;
	}

	public String getOp() {
		return op;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getServiceFee(String code) {
		return SystemInfo.getEntityParameterValue(code, getUser().getEntityId());
	}

	public String cardQueryInit() {
//		username = getRequest().getParameter("username");
//		password = getRequest().getParameter("password");
//		SessionFilter filter = new SessionFilter();
//		HttpServletRequest request = null ;
//		HttpServletResponse response = null;
//		FilterChain chain = null;
//		request.setAttribute("j_username", "he");
//		request.setAttribute("j_password", "a333333");
//		try {
//			filter.doFilterHttp(request, response, chain);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ServletException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//http://localhost:8080/IssueWeb/login.jsp?&j_username=he&j_password=a333333
		//http://localhost:8080/IssueWeb/j_spring_security_check;jsessionid=088D460F5FA2DA9859587CD1042D2D43
//		HttpSession httpSession = ServletActionContext.getRequest().getSession();
//		httpSession.setAttribute("username", username);
//		httpSession.setAttribute("password", password);
		isDisplay="none";
		return "init";
	}

	// 查询卡片操作
	public String selectCardOperation() {
		try {
			ListPageInit(null, cardManagementQueryDTO);
			if (cardManagementQueryDTO.isQueryAll()) {
				cardManagementQueryDTO.setQueryAll(false);
				cardManagementQueryDTO.setRowsDisplayed(Integer.parseInt(SystemInfo.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
			}
			if (null != cardManagementQueryDTO.getStarDate()
					&& !"".equals(cardManagementQueryDTO.getStarDate())) {
				cardManagementQueryDTO.setStarDate(DateUtil
						.getCurrentDateFormatStr(DateUtil
								.getFormatTime(cardManagementQueryDTO
										.getStarDate())));
			}
			if ("".equals(cardManagementQueryDTO.getStarDate())) {
				cardManagementQueryDTO.setStarDate(null);
			}
			if (null != cardManagementQueryDTO.getEnDate()
					&& !"".equals(cardManagementQueryDTO.getEnDate())) {
				cardManagementQueryDTO.setEnDate(DateUtil
						.getCurrentDateFormatStr(DateUtil
								.getFormatTime(cardManagementQueryDTO
										.getEnDate())));
			}
			if ("".equals(cardManagementQueryDTO.getEnDate())) {
				cardManagementQueryDTO.setEnDate(null);
			}
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.CARDMANAGEMENT_SELECTOPERATION,
					cardManagementQueryDTO).getDetailvo();
			cardManagementQueryDTO.setStarDate(DateUtil
					.formatStringDate(cardManagementQueryDTO.getStarDate()));
			cardManagementQueryDTO.setEnDate(DateUtil
					.formatStringDate(cardManagementQueryDTO.getEnDate()));
			if (hasActionErrors()) {
				cardManagementQueryDTO
						.setStarDate(DateUtil
								.formatStringDate(cardManagementQueryDTO
										.getStarDate()));
				cardManagementQueryDTO.setEnDate(DateUtil
						.formatStringDate(cardManagementQueryDTO.getEnDate()));
				return "input";
			}
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "select";
	}
	
	//查询单张卡的操作
	public String selectSingleCardOperation() {
		try {
			ListPageInit(null, cardManagementQueryDTO);
			if(null==cardManagementDTO){
				cardManagementDTO = new CardManagementDTO();
				}
			cardManagementQueryDTO.setCardNo(cardManagementDTO.getTransferOutCard());
			if (cardManagementQueryDTO.isQueryAll()) {
				cardManagementQueryDTO.setQueryAll(false);
				cardManagementQueryDTO
						.setRowsDisplayed(Integer
								.parseInt(SystemInfo
										.getParameterValue(SystemInfoConstants.EXPORT_DATA_MAXIMUM)));
			}
			if (null != cardManagementQueryDTO.getStarDate()
					&& !"".equals(cardManagementQueryDTO.getStarDate())) {
				cardManagementQueryDTO.setStarDate(DateUtil
						.getCurrentDateFormatStr(DateUtil
								.getFormatTime(cardManagementQueryDTO
										.getStarDate())));
			}
			if ("".equals(cardManagementQueryDTO.getStarDate())) {
				cardManagementQueryDTO.setStarDate(null);
			}
			if (null != cardManagementQueryDTO.getEnDate()
					&& !"".equals(cardManagementQueryDTO.getEnDate())) {
				cardManagementQueryDTO.setEnDate(DateUtil
						.getCurrentDateFormatStr(DateUtil
								.getFormatTime(cardManagementQueryDTO
										.getEnDate())));
			}
			if ("".equals(cardManagementQueryDTO.getEnDate())) {
				cardManagementQueryDTO.setEnDate(null);
			}
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.CARDMANAGEMENT_SELECT_SINGLE_OPERATION,
					cardManagementQueryDTO).getDetailvo();
			cardManagementQueryDTO.setStarDate(DateUtil
					.formatStringDate(cardManagementQueryDTO.getStarDate()));
			cardManagementQueryDTO.setEnDate(DateUtil
					.formatStringDate(cardManagementQueryDTO.getEnDate()));
			if (hasActionErrors()) {
				cardManagementQueryDTO
						.setStarDate(DateUtil
								.formatStringDate(cardManagementQueryDTO
										.getStarDate()));
				cardManagementQueryDTO.setEnDate(DateUtil
						.formatStringDate(cardManagementQueryDTO.getEnDate()));
				return "input";
			}
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "select";
	}
	
	
	
	
	// 和后台交互的service
//    private WebServiceClientService webServiceClientService;
	
	@Override
	public OperationResult sendService(String code, Object DTO) {
//		return super.sendService(code, DTO);


        OperationResult operationResult = null;
        try {
            OperationCtx operationCtx = new OperationCtx();
            //设置交易代码和交易用户
            operationCtx.setTxncode(code);
//            if (getUser().equals("") && getUser().getUserId().equals("")) {
//            	operationCtx.setOprId("1018");
//			}else {
				operationCtx.setOprId(getUser().getUserId() + "");
//			}
            OperationRequest operationRequest = new OperationRequest();
            /**
             * 如果来的DTO为BaseDTO,则加上用户和发卡机构信息
             */
            if (DTO instanceof BaseDTO) {
                BaseDTO baseDTO = (BaseDTO) DTO;
//                if (this.getUser().equals("") && this.getUser().getUserId().equals("")) {
//                	baseDTO.setLoginUserId("1018");
//				}else {
					baseDTO.setLoginUserId(this.getUser().getUserId());
//				}
//                if (this.getUser().equals("") && this.getUser().getEntityId().equals("")) {
//                	baseDTO.setDefaultEntityId("10000029");
//				}else {
					baseDTO.setDefaultEntityId(this.getUser().getEntityId());
//				}
                operationRequest.setTxnvo(baseDTO);
            } else {
                operationRequest.setTxnvo(DTO);
            }
         
            // 调用后台请求
            operationResult = super.getWebServiceClientService().process(operationCtx, operationRequest);
            if (operationResult.getTxnstate().equals(Const.RETURN_FAILED)) {
                if (operationResult.getErrMessage() != null) {
                    addActionError(operationResult.getErrMessage());
                }
                if (operationResult.getTxncause() != null) {
                    List causeList = (List) operationResult.getTxncause();
                    if (causeList != null) {
                        for (int i = 0; i < causeList.size(); i++) {
                            addActionError((String) causeList.get(i));
                        }
                    }
                }
                if (!hasActionErrors()) {
                	logger.error("response code is error :::: 0");
                    addActionError("系统异常");
                }
            }

        } catch (Exception e) {
        	e.printStackTrace();
        	this.logger.error(e.getMessage());
            addActionError("系统异常");
        }
        return operationResult;
    
	}

//	@Override
//	public User getUser() {
//		// TODO Auto-generated method stub
//		User user = new User();
////		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		user.setEntityId("10000029");
//		user.setDefaultEntityId("10000029");
//		user.setUserId("1018");
//        return user;
//	}

	// 查询卡片
	public String cardQuery() {
		try {
			if(null==applyAndBindCardDTO){
				applyAndBindCardDTO = new ApplyAndBindCardDTO();
			} 
			applyAndBindCardDTO.setIdType("1");
			System.out.println("机构号："+this.getUser().getEntityId());
			applyAndBindCardDTO.setEntityId(this.getUser().getEntityId());
//			if (!applyAndBindCardDTO.getFirstName().equals("")) {
//				applyAndBindCardDTO.setFirstName(applyAndBindCardDTO.getFirstName());
//			}
			ArrayList<String> list = (ArrayList<String>) sendService( ConstCode.CERTIFICATE_QUERY, applyAndBindCardDTO).getDetailvo();
			if(list!=null){
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i)==null) {
						list.remove(i);
					}
				}
			}
			
			HttpServletRequest request = getRequest();
			request.setAttribute("CardNos",list );
			if(pageDataDTO!=null){
				totalRows = pageDataDTO.getTotalRecord();
			}
			if (hasErrors()) {
	            return INPUT;
	        }
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return "succ";
	}
	public String cardQuerys() {
//		try {
//			if(null==cardManagementDTO){
//				cardManagementDTO = new CardManagementDTO();
//			}
////			cardManagementDTO.setCardNo(cardManagementDTO.getTransferOutCard());
//			cardManagementDTO.setIdNo(getRequest().getParameter("id"));
////			cardManagementDTO.setIdType("1");
////			applyAndBindCardDTO.setEntityId("10000030");
////			if (!applyAndBindCardDTO.getFirstName().equals("")) {
////				applyAndBindCardDTO.setFirstName(applyAndBindCardDTO.getFirstName());
////			}
//			sendService( ConstCode.CARDMANAGEMENT_QUERY, cardManagementDTO).getDetailvo();
//			if (hasActionErrors()) {
//				return "input";
//			}
//		} catch (Exception e) {
//			this.logger.error(e.getMessage());
//			e.printStackTrace();
//		}
		try {
			if(null==cardManagementDTO){
				cardManagementDTO = new CardManagementDTO();
			}
			String id = getRequest().getParameter("id");
			System.out.println(id);
//			String transferOutCard = cardManagementDTO.getTransferOutCard();
//			String str = transferOutCard.replace( ",", "").trim();
			cardManagementDTO.setTransferOutCard(id);
//			String transferInCard = cardManagementDTO.getCardNo();
//			cardManagementDTO.setCardNo(getRequest().getParameter("id"));
			cardManagementDTO.setCardNo(id);
//			cardManagementDTO.setCardholderName(cardManagementDTO.getCardholderName());
			cardManagementDTO = (CardManagementDTO) sendService(
					ConstCode.CARDMANAGEMENT_QUERY, cardManagementDTO).getDetailvo();
//			System.out.println("cardNo===="+cardManagementDTO.getCardNo()+"ddddd"+cardManagementDTO.getTransferOutCard());
			if (hasActionErrors()) {
				
				return "input";
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		if(isCall.equals("0")){
		    if(cardManagementDTO.getActive().equals("3")){
			this.addActionError("此卡已销毁");
			return "input";
		   }
		}
		if(isCall.equals("0")){
            if(cardManagementDTO.getActive().equals(OrderConst.INVALID_STATE)){
            this.addActionError("此卡已作废");
            return "input";
           }
        }
		cardManagementDTO.setBalance(cardManagementDTO.getTotalBalance());
		if(!"".equals(cardManagementDTO.getCardholderName())&&null!= cardManagementDTO.getCardholderName()){
			isDisplay="block";//卡关联了持卡人
		}else{
			isDisplay="none";//卡未关联持卡人
		}
		if(null!=cardManagementDTO.getInvalidFlag()){
            this.getSession().remove("invalidFlag");
            this.getSession().put("invalidFlag", cardManagementDTO.getInvalidFlag());
            }
        if(null==cardManagementDTO.getInvalidFlag()){
                cardManagementDTO.setInvalidFlag( this.getSession().get("invalidFlag").toString());
            }
		this.getSession().remove("balanceDtoList");
		this.getSession().put("balanceDtoList", cardManagementDTO.getCardBalanceDTOs());
		return "succ";
	}
	
	public void validateCardQuery() {
		if(null==applyAndBindCardDTO){
			applyAndBindCardDTO = new ApplyAndBindCardDTO();
		}
		if (!this.hasFieldErrors()) {
			
			String idNo = applyAndBindCardDTO.getIdNo().trim();
			String firstName = applyAndBindCardDTO.getFirstName().trim();
//			if (idNo.length() != 18) {
//				this.addFieldError("applyAndBindCardDTO.idNo", "请输入18位证件号");
//			}
//			String idNo = cardManagementDTO.getIdNo().trim();
//			if (idNo.length() != 18) {
//				this.addFieldError("cardManagementDTO.idNo", "请输入18位证件号");
//			}
//			String cardholderName = cardManagementDTO.getCardholderName().trim();
//			if (cardholderName.equals("")) {
//				this.addFieldError("cardManagementDTO.cardholderName", "请输入姓名");
//			}
			
		}
		
//		if(isDisplay.equals("1")){
//				   
//			if ("".equals(cardManagementDTO.getAgentrName())||cardManagementDTO.getAgentrName()==null) {
//				this.addFieldError("cardManagementDTO.agentrName", "代理人姓名必须填");
//			}
//			if ("".equals(cardManagementDTO.getAgentrCertTypeNo())||cardManagementDTO.getAgentrCertTypeNo()==null) {
//				this.addFieldError("cardManagementDTO.agentrCertTypeNo", "证件号码必须填");
//			}
//			if ("".equals(cardManagementDTO.getIdNo())||cardManagementDTO.getIdNo()==null) {
//				this.addFieldError("cardManagementDTO.idNo", "证件号码必须填");
//			}
//			if ("".equals(cardManagementDTO.getMobile())||cardManagementDTO.getMobile()==null) {
//				this.addFieldError("cardManagementDTO.mobile", "手机号必须填");
//			}
//			if ("".equals(cardManagementDTO.getStartDate())||cardManagementDTO.getStartDate()==null) {
//				this.addFieldError("cardManagementDTO.startDate", "请输入12-19位卡号");
//			}
//			if ("".equals(cardManagementDTO.getEndDate())||cardManagementDTO.getEndDate()==null) {
//				this.addFieldError("cardManagementDTO.endDate", "请输入12-19位卡号");
//			}
//		}
	}

	// 返回
	public String comeback() {
		
		return queryByCard();
	}
	
	// 挂失
	public String hangInit() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
//		String parameter = getRequest().getParameter("cardManagementDTO.transferOutCard");
//		System.out.println("parameter---" + parameter);
		String transferOutCard = cardManagementDTO.getCardNo();
		cardManagementDTO.setTransferOutCard(transferOutCard);
//		System.out.println("transferOutCard---"+transferOutCard);
		if(checkAgent().equals("input")){
			queryByCard();
			return "input";
		}

		// try {
		// cardManagementDTO
		// .setServiceFee(getServiceFee(SystemInfoConstants.CARD_BLOCK_FEE));
		// } catch (Exception e) {
		// this.logger.error(e.getMessage());
		// }
		return "hangInit";
	}

	public String insertHang() {
		try {
			if(null==cardManagementDTO){
				cardManagementDTO = new CardManagementDTO();
				}
			checkCardNoAndServiceFee();
			if(hasActionErrors() || hasFieldErrors()){
				return INPUT;
			}
			cardManagementDTO.setCardNo(cardManagementDTO.getTransferOutCard());
			cardManagementDTO.setOperationType("S51");
			sendService(ConstCode.CARDMANAGEMENT_OPERATION, cardManagementDTO);
			if (hasActionErrors()) {
				return "input";
			} else {
				this.addActionMessage("卡片挂失成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return queryByCard();
	}

	// 解挂
	public String nohangInit() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
		}
		String transferOutCard = cardManagementDTO.getCardNo();
		cardManagementDTO.setTransferOutCard(transferOutCard);
		if(checkAgent().equals("input")){
			queryByCard();
			return "input";
		}
	
		// try{
		// cardManagementDTO.setServiceFee(SystemInfoConstants.CARD_UNBLOCK_FEE);
		// }catch(Exception e){
		// this.logger.error(e.getMessage());
		// }
		return "nohangInit";
	}

	public String insertDisengage() {
		try {
			if(null==cardManagementDTO){
				cardManagementDTO = new CardManagementDTO();
				}
			checkCardNoAndServiceFee();
			if(hasActionErrors() || hasFieldErrors()){
				return INPUT;
			}
			cardManagementDTO.setCardNo(cardManagementDTO.getTransferOutCard());
			cardManagementDTO.setOperationType("S53");
			sendService(ConstCode.CARDMANAGEMENT_OPERATION, cardManagementDTO);
			if (hasActionErrors()) {
				return "input";
			} else {
				this.addActionMessage("卡片解挂成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return queryByCard();
	}
	// 查询卡片
		public String queryByCard() {
			try {
			if (null == cardManagementDTO) {
				cardManagementDTO = new CardManagementDTO();
				cardManagementDTO.setCardNo(cardManagementQueryDTO.getCardNo());

			} else {
				cardManagementDTO.setCardNo(cardManagementDTO
						.getTransferOutCard());
			}
				cardManagementDTO.setCardholderName("");
				cardManagementDTO = (CardManagementDTO) sendService(
						ConstCode.CARDMANAGEMENT_QUERY, cardManagementDTO).getDetailvo();
				if (hasActionErrors()) {
					
					return "input";
				}
			} catch (Exception e) {
				this.logger.error(e.getMessage());
			}
			if(isCall.equals("0")){
			    if(cardManagementDTO.getActive().equals("3")){
				this.addActionError("此卡已销毁");
				return "input";
			   }
			}
			if(isCall.equals("0")){
	            if(cardManagementDTO.getActive().equals(OrderConst.INVALID_STATE)){
	            this.addActionError("此卡已作废");
	            return "input";
	           }
	        }
			cardManagementDTO.setBalance(cardManagementDTO.getTotalBalance());
			if(!"".equals(cardManagementDTO.getCardholderName())&&null!= cardManagementDTO.getCardholderName()){
				isDisplay="block";//卡关联了持卡人
			}else{
				isDisplay="none";//卡未关联持卡人
			}
			if(null!=cardManagementDTO.getInvalidFlag()){
	            this.getSession().remove("invalidFlag");
	            this.getSession().put("invalidFlag", cardManagementDTO.getInvalidFlag());
	            }
	        if(null==cardManagementDTO.getInvalidFlag()){
	                cardManagementDTO.setInvalidFlag( this.getSession().get("invalidFlag").toString());
	            }
			this.getSession().remove("balanceDtoList");
			this.getSession().put("balanceDtoList", cardManagementDTO.getCardBalanceDTOs());
			return "succ";
		}
	// 卡锁定
	public String lockInit() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		String transferOutCard = cardManagementDTO.getCardNo();
		cardManagementDTO.setTransferOutCard(transferOutCard);
		if(checkAgent().equals("input")){
			cardQuery();
			return "input";
		}

		// try{
		// cardManagementDTO.setServiceFee(SystemInfoConstants.CARD_LOCK_FEE);
		// }catch(Exception e){
		// this.logger.error(e.getMessage());
		// }
		return "lockInit";
	}

	public String insertLock() {
		try {
			if(null==cardManagementDTO){
				cardManagementDTO = new CardManagementDTO();
				}
			checkCardNoAndServiceFee();
			if(hasActionErrors() || hasFieldErrors()){
				return INPUT;
			}
			checkCardNoAndServiceFee();
			if(hasActionErrors() || hasFieldErrors()){
				return INPUT;
			}
		cardManagementDTO.setCardNo(cardManagementDTO.getTransferOutCard());
			cardManagementDTO.setOperationType("S43");
			cardManagementDTO.setCardBalanceDTOs(null);
			sendService(ConstCode.CARDMANAGEMENT_OPERATION, cardManagementDTO);
			if (hasActionErrors()) {
				return "input";
			} else {
				this.addActionMessage("卡锁定成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}

		return queryByCard();
	}

	// 卡片解锁
	public String nolockInit() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
		}
		String transferOutCard = cardManagementDTO.getCardNo();
		cardManagementDTO.setTransferOutCard(transferOutCard);
		if(checkAgent().equals("input")){
			queryByCard();
			return "input";
		}
	
		// try{
		// cardManagementDTO.setServiceFee(SystemInfoConstants.CARD_UNLOCK_FEE);
		// }catch(Exception e){
		// this.logger.error(e.getMessage());
		// }
		return "nolockInit";
	}

	public String cardNoLock() {
		try {
			if(null==cardManagementDTO){
				cardManagementDTO = new CardManagementDTO();
				}
			checkCardNoAndServiceFee();
			if(hasActionErrors() || hasFieldErrors()){
				return INPUT;
			}
			cardManagementDTO.setCardNo(cardManagementDTO.getTransferOutCard());
			cardManagementDTO.setOperationType("S45");
			sendService(ConstCode.CARDMANAGEMENT_OPERATION, cardManagementDTO);
			if (hasActionErrors()) {
				return "input";
			} else {
				this.addActionMessage("卡解锁成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return queryByCard();
	}
    //卡激活
	public String activeInit() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		cardManagementDTO.setAdjustAmount(total);
		if(checkAgent().equals("input")){
			queryByCard();
			return "input";
		}
//		sendService(ConstCode.CARDMANAGEMENT_CHECK_CARD_STOCK, cardManagementDTO);
//		if(hasErrors()){
//			cardQuery();
//			return "input";
//		}
		// try{
		// cardManagementDTO.setServiceFee(SystemInfoConstants.CARD_UNLOCK_FEE);
		// }catch(Exception e){
		// this.logger.error(e.getMessage());
		// }
		return "activeInit";
	}
	public String activeCard() {
		try {
			if(null==cardManagementDTO){
				cardManagementDTO = new CardManagementDTO();
				}
			cardManagementDTO.setCardNo(cardManagementDTO.getTransferOutCard());
			cardManagementDTO.setOperationType("S41");
			cardManagementDTO.setActive("0");
			cardManagementDTO.setAdjustType("1");
			sendService(ConstCode.CARDMANAGEMENT_ACTIVATION, cardManagementDTO);
			if (hasActionErrors()) {
				return "input";
			} else {
				this.addActionMessage("卡激活成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return queryByCard();
		
	}
	//卡充值初始化
	@SuppressWarnings("unchecked")
	public String rechargeInit() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		if(checkAgent().equals("input")){
			queryByCard();
			return "input";
		}
		List<CardBalanceDTO> balanceDTOList =(ArrayList<CardBalanceDTO>)this.getSession().get("balanceDtoList");
		cardManagementDTO.setCardBalanceDTOs(balanceDTOList);
		
		return "rechargeInit";
	}
	//卡充值
	@SuppressWarnings("unchecked")
	public String recharge() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		try {
			
			List <String> accountIds=new ArrayList<String>();
			List <String> creditAmonts=new ArrayList<String>();
			for(int j=0;j<accountId.length;j++){
				
				accountIds.add(accountId[j]);  
			}
			for(int i=0;i<creditAmont.length;i++){				
				if(!creditAmont[i].equals("")){				
					creditAmonts.add(creditAmont[i]);
				}
				
				
			}
			cardManagementDTO.setAccountList(accountIds);
			cardManagementDTO.setCreditAmonts(creditAmonts);
			cardManagementDTO.setOperationType("S32");
			sendService(ConstCode.CARDMANAGEMENT_OPERATION, cardManagementDTO);
			if (hasActionErrors()) {
				List<CardBalanceDTO> balanceDTOList =(ArrayList<CardBalanceDTO>)this.getSession().get("balanceDtoList");
				cardManagementDTO.setCardBalanceDTOs(balanceDTOList);
				return "input";
			} else {
				this.addActionMessage("卡充值成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			List<CardBalanceDTO> balanceDTOList =(ArrayList<CardBalanceDTO>)this.getSession().get("balanceDtoList");
			cardManagementDTO.setCardBalanceDTOs(balanceDTOList);
			this.addActionMessage("卡充值失败!");
			return "input";
		}
		return queryByCard();
	}
	//记名库存卡，持卡人信息的添加
	public String addCardHolder(){
		
		return "addCardHolder";
	}
    //记名库存卡关联持卡人信息
	
	public void signCardRltCardHolder(){
		if(null==cardBalanceDTO){
			cardBalanceDTO =new CardBalanceDTO();
		}
		if (null != cardholderDTO.getCardholderBirthday() && !"".equals(cardholderDTO.getCardholderBirthday())) {
			cardholderDTO.setCardholderBirthday(DateUtil
					.getFormatTime(cardholderDTO.getCardholderBirthday()));
		}
		if (null != cardholderDTO.getCloseDate() && !"".equals(cardholderDTO.getCloseDate())) {
			cardholderDTO.setCloseDate(DateUtil.getFormatTime(cardholderDTO
					.getCloseDate()));
		}
		cardholderDTO.setCreditAmont(cardManagementDTO.getCreditAmont());
		if(null==cardManagementDTO.getFlag()||"".equals(cardManagementDTO.getFlag())){
			cardholderDTO.setCardNo(cardManagementDTO.getTransferOutCard());//卡充值时
		}
		else{
			cardholderDTO.setCardNo(cardManagementDTO.getTransferInCard());//换卡时
		}
		this.sendService(ConstCode.CARDHOLDER_RLT_CARD_INSERT, cardholderDTO);
       if(this.hasErrors()){
    	   addActionMessage("添加持卡人信息失败！");
    	   
       }
		addActionMessage("添加持卡人信息成功！");
		
		
	}
	
	
	// 卡片延期
	public String cardDelayInit() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		String transferOutCard = cardManagementDTO.getCardNo();
		cardManagementDTO.setTransferOutCard(transferOutCard);
		if(checkAgent().equals("input")){
			queryByCard();
			return "input";
		}
	
		try {
			//cardManagementDTO.setCardNo(cardManagementDTO.getTransferOutCard());
			cardManagementDTO.setCardValidityPeriod(cardManagementDTO
					.getCardValidityPeriod());
			// cardManagementDTO.setServiceFee(getServiceFee(SystemInfoConstants.EXTENSION_FEE));
			
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "cardDelayInit";
	}

	public String cardDelay() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		try {
			cardManagementDTO.setOperationType("S58");
			sendService(ConstCode.CARDMANAGEMENT_OPERATION, cardManagementDTO);
			if (hasActionErrors()) {
				return "input";
			} else {
				this.addActionMessage("卡延期成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return queryByCard();
	}
	
//	public Map<String, Object> getSession() {
//		Map<String, Object> session = ServletActionContext.getContext().getSession();
//		session.esetAttribute("username", username);
//		session.setAttribute("password", password);
//		session.put("username", username);
//		session.put("password", password);
//		return session;
//	}

	public void validateCardDelay() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		if (cardManagementDTO.getExtensionMonth() == null
				|| "".equals(cardManagementDTO.getExtensionMonth()) || !cardManagementDTO.getExtensionMonth().matches("[0-9]{1,8}")) {
			this.addFieldError("cardManagementDTO.extensionMonth", "请填写格式正确的延期月数(8位以内正整数)");
		}
		if (cardManagementDTO.getServiceFee() == null
				|| "".equals(cardManagementDTO.getServiceFee()) || !cardManagementDTO.getServiceFee().matches("[0]|([1-9]{1}[0-9]{0,8})")) {
			this.addFieldError("cardManagementDTO.serviceFee", "请填写格式正确的延期费(10位以内正整数)");
		}
		
	}
	//卡转账初始化
	@SuppressWarnings("unchecked")
	public String transferAccountInit(){
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		if(checkAgent().equals("input")){
			queryByCard();
			return "input";
		}
	
		try {
			cardManagementDTO.setCardNo(cardManagementDTO.getTransferOutCard());
			cardManagementDTO = (CardManagementDTO) sendService(
					ConstCode.CARD_QUERY_FORTRANSFER, cardManagementDTO)
					.getDetailvo();
			this.getSession().remove("transferOutAcc");
			this.getSession().remove("accountRltBalance");
			this.getSession().put("transferOutAcc", cardManagementDTO.getAccountMap());
			this.getSession().put("accountRltBalance", cardManagementDTO.getAccountRltBalance());
			if(cardManagementDTO.getInCardAccounts()==null){
			List inCardAccounts=new ArrayList();
			inCardAccounts.add("----");
			cardManagementDTO.setInCardAccounts(inCardAccounts);
			//cardManagementDTO.setBalance(cardManagementDTO.getBalance());
			cardManagementDTO.setBalance(cardManagementDTO.getTotalBalance());
			}
			if (hasActionErrors()) {
				
				return "input";
			}
			
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		
		return "transferAccountInit";
	}
	//获取卡账户关联的余额
	@SuppressWarnings("unchecked")
	public void accountRltBalance()throws IOException{
		 Map<String, String> accountRltBalances=(Map<String, String>)this.getSession().get("accountRltBalance");
		 JSONArray jsonObject = JSONArray.fromObject(accountRltBalances);
			getResponse().setContentType("application/json; charset=utf-8");
			getResponse().setCharacterEncoding("utf-8");
			getResponse().getWriter().println(jsonObject);
			getResponse().getWriter().close();
	}
	//卡转账时获取转入卡关联的账户
	@SuppressWarnings("unchecked")
	public String inCardRltAccount()throws IOException{
		if (null == cardManagementDTO) {
			cardManagementDTO = new CardManagementDTO();
		}

		String inCard=cardManagementDTO.getTransferInCard();
		String outCard=cardManagementDTO.getTransferOutCard();
		cardManagementDTO.setCardNo(cardManagementDTO.getTransferInCard());
		cardManagementDTO = (CardManagementDTO) sendService(
				ConstCode.IN_CARD_QUERY_FORTRANSFER, cardManagementDTO)
				.getDetailvo();
		if(null!=cardManagementDTO.getAccountMap()){
			this.getSession().remove("transferInAcc");
			this.getSession().put("transferInAcc", cardManagementDTO.getAccountMap());
			}
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		
		cardManagementDTO.setTransferOutCard(outCard);
		cardManagementDTO.setTransferInCard(inCard);
		cardManagementDTO.setBalance(cardManagementDTO.getBalance());
		transferAccountInit();
		if(cardManagementDTO.getPageDataDTO()!=null){
			if(cardManagementDTO.getPageDataDTO().getData().size()>0){
		Map data=(HashMap)cardManagementDTO.getPageDataDTO().getData().get(0);
		cardManagementDTO.setProdName(data.get("productName").toString());
		cardManagementDTO.setProdType(data.get("productType").toString());
		cardManagementDTO.setBalance(cardManagementDTO.getBalance());
			}
		}
		
		if (hasActionErrors()) {
			
			return "input";
		}

		return "selectSucc";
	}
	//卡转账
	@SuppressWarnings("unchecked")
	public String transferAccount() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		try {
			Map transferOutAcc=(HashMap)this.getSession().get("transferOutAcc");
			Map transferInAcc =(HashMap)this.getSession().get("transferInAcc");
			cardManagementDTO.setTransferOutAccount(transferOutAcc.get(cardManagementDTO.getTransferOutAccount()).toString());
			cardManagementDTO.setTransferInAccount(transferInAcc.get(cardManagementDTO.getTransferInAccount()).toString());
			cardManagementDTO.setOperationType("S34");
			sendService(ConstCode.CARDMANAGEMENT_OPERATION, cardManagementDTO);
			if (hasActionErrors()) {
				inCardRltAccount();
				return "input";
			} else {
				this.addActionMessage("卡转账成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return queryByCard();
		
	}
	
	public void validateTransferAccount() throws IOException{
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		if(new BigDecimal(cardManagementDTO.getBalance()).compareTo(new BigDecimal(cardManagementDTO.getTransAmount()))<0){
			inCardRltAccount();
			this.addFieldError("cardManagementDTO.transAmount", "可用余额不足!");	
			
				
			
		}
		if(null==cardManagementDTO.getTransferOutCardPassword()||"".equals(cardManagementDTO.getTransferOutCardPassword())){
			this.clearFieldErrors();
			inCardRltAccount();
			this.addFieldError("cardManagementDTO.transferOutCardPassword", "转出密码必须输入!");	
		}
	}
	// 换卡初始化
	public String changeCardInit() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
		}
		if(checkAgent().equals("input")){
			queryByCard();
			return "input";
		}
			try {
				cardManagementDTO = (CardManagementDTO) sendService(ConstCode.CARD_QUERY_FORTRANSFER, cardManagementDTO).getDetailvo();
				if (hasActionErrors()) {
					return "input";
			}
				/**原卡账户id与账户名关联的session**/
				this.getSession().remove("transferOutAccMap");
				this.getSession().put("transferOutAccMap", cardManagementDTO.getAccountMap());
				/**原卡账户名与余额关联的session**/
				this.getSession().remove("accountRltBalance");
				this.getSession().put("accountRltBalance", cardManagementDTO.getAccountRltBalance());
				/**原卡账户列表的session**/
				this.getSession().remove("transferOutAccList");
				this.getSession().put("transferOutAccList",cardManagementDTO.getAccountList());
				cardManagementDTO.setDisableFlag("disabled");
			} catch (Exception e) {
				this.logger.error(e.getMessage());
			}
		return "changeCardInit";
	}
	
	//换卡时获取新卡关联的账户
	public String newCardRltAccount()throws IOException{
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
		}
		//cardManagementDTO.setAdjustType("1");
		cardManagementDTO.setCardNo(cardManagementDTO.getTransferInCard());
		String newCard=cardManagementDTO.getTransferInCard();
		String outCard=cardManagementDTO.getTransferOutCard();
		cardManagementDTO = (CardManagementDTO) sendService(ConstCode.NEW_CARD_QUERY_FORTRANSFER, cardManagementDTO).getDetailvo();
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
		}
		cardManagementDTO.setTransferOutCard(outCard);
		cardManagementDTO.setTransferInCard(newCard);
		cardManagementDTO.setDisableFlag("disabled");
		changeCardInit();
		/**新卡账户名与余额关联的session**/
		this.getSession().remove("transferInAccBalance");
		if(null!=cardManagementDTO){
		   if(null!=cardManagementDTO.getPageDataDTO()){
		   this.getSession().put("transferInAccBalance", cardManagementDTO.getPageDataDTO());
		   /**新卡账户id与账户名关联的session**/
		   }
		   this.getSession().remove("transferInAccMap");
		   if(null!=cardManagementDTO.getAccountMap()){
		   this.getSession().put("transferInAccMap", cardManagementDTO.getAccountMap());
		   }
		}
       if (hasActionErrors()) {
			return "input";
       }
       cardManagementDTO.setDisableFlag("disabled");
       return "changSucc";
	}
	//换卡
	@SuppressWarnings("unchecked")
	public String changeCard() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
		}
		try {
			Map transferOutAcc=(HashMap)this.getSession().get("transferOutAccMap");
			List transferOutAccList=(ArrayList)this.getSession().get("transferOutAccList");
			Map accountRltBalance=(HashMap)this.getSession().get("accountRltBalance");
			for(int i=0;i<transferOutAccList.size();i++){
			    cardManagementDTO.setTransferOutAccount(transferOutAcc.get(transferOutAccList.get(i)).toString());
			    String balance = accountRltBalance.get(transferOutAccList.get(i)).toString();
			    //换卡时先执行转账，如果填写手续费要从卡内扣，所以交易金额为余额-手续费
			    cardManagementDTO.setTransAmount(String.valueOf(Long.parseLong(balance)-Long.parseLong(cardManagementDTO.getServiceFee())));
			    //cardManagementDTO.setTransferInAccount(transferInAcc.get(cardManagementDTO.getTransferInAccount()).toString());
			    cardManagementDTO.setOperationType("ChangeCard");
			    cardManagementDTO.setFlag("1");
			    sendService(ConstCode.CARDMANAGEMENT_OPERATION, cardManagementDTO);
			    newCardRltAccount();
    			if (hasErrors()) {
    				return "input";
    			}
			}
			if(!hasErrors()){
				cardManagementDTO.setDisableFlag("");
				this.addActionMessage("换卡成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "succ";
	}
	//换卡时原卡处理方式 1、入库   2、销毁
	public String dealWithOldCard(){
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
		}
		try {
			sendService(ConstCode.CARDMANAGEMENT_REDEMPTION, cardManagementDTO);
			if(hasActionErrors()){
				this.addActionError("原卡回收失败!");
				return "input";
			}else{
				this.addActionMessage("原卡回收成功！");
			}
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		isCall="1";//调用queryByCard()的标识
		return queryByCard();
	}
	/**
	 *  赎回
	 * @return
	 */
    public String retrieveCardInit() {
    	if(null==cardManagementDTO){
    		cardManagementDTO = new CardManagementDTO();
    	}
    	if(checkAgent().equals("input")){
    		queryByCard();
    		return "input";
    	}
		cardManagementDTO.setTransferOutCard(cardManagementDTO.getTransferOutCard().trim());
		cardManagementDTO=(CardManagementDTO)sendService(ConstCode.CARDMANAGEMENT_VIEW_ORDER, cardManagementDTO).getDetailvo();
		if(hasActionErrors()){
			return "input";
		}
		if(null!=cardManagementDTO.getCardholderName() && !"".equals(cardManagementDTO.getCardholderName())){			
			cardManagementDTO.setCardholderName(cardManagementDTO.getCardholderName());
		}
		if(null!=cardManagementDTO.getMobile() && !"".equals(cardManagementDTO.getMobile())){
			cardManagementDTO.setMobile(cardManagementDTO.getMobile());
		}
		if(null!=cardManagementDTO.getIdType() && !"".equals(cardManagementDTO.getIdType())){
			cardManagementDTO.setIdType(cardManagementDTO.getIdType());
		}
		if(null!=cardManagementDTO.getIdNo()&& !"".equals(cardManagementDTO.getIdNo())){
			cardManagementDTO.setIdNo(cardManagementDTO.getIdNo());
		}
		if(null!=cardManagementDTO.getBalance()){
			cardManagementDTO.setBalance(cardManagementDTO.getBalance());
		}
		return "retrieveCardInitRansom";
	}
	
	public String redemptionCard() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
		}
		try {
			cardManagementDTO.setOperationType("S60");
			 sendService(ConstCode.CARDMANAGEMENT_OPERATION, cardManagementDTO);
			//sendService(ConstCode.CARDMANAGEMENT_REDEMPTION, cardManagementDTO);
			if(hasActionErrors()){
				this.addActionError("赎回失败!");
				return "input";
			}else{
				this.addActionMessage("赎回成功！");
				isCall="1";//调用queryByCard()的标识
				this.queryByCard();
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "succ";
	}
	// 卡交易信息查询
	public String cardTxnSelectInit() {
		String transferOutCard = cardManagementDTO.getCardNo();
		cardManagementDTO.setTransferOutCard(transferOutCard);
		if (null != op && "1".equals(op)) {
			return "selectTxn";
		} else {
			if(checkAgent().equals("input")){
				queryByCard();
				return "input";
			}
			return "cardTxnSelectInit";
		}
	}
	public String cardTxnSelect() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
		}
		try {
			ListPageInit(null, cardManagementQueryDTO);
			cardManagementQueryDTO.setCardNo(cardManagementDTO.getTransferOutCard());
			if (null == cardManagementQueryDTO.getCardFlag()) {
				cardManagementQueryDTO.setCardFlag(0);
			}
			cardManagementQueryDTO.getFirstCursorPosition();
			cardManagementQueryDTO.getLastCursorPosition();
			if(cardManagementQueryDTO.getCardFlag()==1){
				cardManagementQueryDTO.setStarDate(DateUtil
						.getFormatTime(cardManagementQueryDTO.getStarDate()));
				cardManagementQueryDTO.setEnDate(DateUtil
						.getFormatTime(cardManagementQueryDTO.getEnDate()));
			}else{
				cardManagementQueryDTO.setStarDate("");
				cardManagementQueryDTO.setEnDate("");
			}
			//TODO:暂时用cardManagementQueryDTO的isBo属性传输交易查询标识(卡操作，账务调整)
			if(null != op &&op.equals("1")){
				/**卡账务调整情况为1*/
				cardManagementQueryDTO.setIsBo("1"); 
			}else{
				/**卡账务调整情况为0*/
				cardManagementQueryDTO.setIsBo("0");
			}
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.CARDMANAGEMENT_SELECT, cardManagementQueryDTO)
					.getDetailvo();
			if (pageDataDTO != null) {
				totalRows = pageDataDTO.getTotalRecord();
			}
			logger.debug("cardTxnSelect-->get result");
			if (hasActionErrors()) {
				logger.debug("cardTxnSelect-->error");
				if(cardManagementQueryDTO.getCardFlag()==1){
				cardManagementQueryDTO
						.setStarDate(DateUtil
								.formatStringDate(cardManagementQueryDTO
										.getStarDate()));
				cardManagementQueryDTO.setEnDate(DateUtil
						.formatStringDate(cardManagementQueryDTO.getEnDate()));
				}
				if (null != op && "1".equals(op)) {
					return "selectTxn";
				}
				return "cardTxnSelect";
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		if(cardManagementQueryDTO.getCardFlag()==1){
		cardManagementQueryDTO.setStarDate(DateUtil
				.formatStringDate(cardManagementQueryDTO.getStarDate()));
		cardManagementQueryDTO.setEnDate(DateUtil
				.formatStringDate(cardManagementQueryDTO.getEnDate()));
		}
		if (null != op && "1".equals(op)) {
			return "selectTxn";
		}
		return "cardTxnSelect";
	}

	public void validateCardTxnSelect() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		if (null == cardManagementDTO.getTransferOutCard()
				|| "".equals(cardManagementDTO.getTransferOutCard())) {
			addFieldError("cardManagementDTO.cardNo", "卡号必须输入");
		}
		if(null != cardManagementQueryDTO.getCardFlag() && cardManagementQueryDTO.getCardFlag()==1){
			if (null == cardManagementQueryDTO.getStarDate()
					|| cardManagementQueryDTO.getStarDate().equals("")) {
				addFieldError("cardManagementQueryDTO.starDate", "历史开始时间必须选择");
			}
			if (null == cardManagementQueryDTO.getEnDate()
					|| cardManagementQueryDTO.getEnDate().equals("")) {
				addFieldError("cardManagementQueryDTO.enDate", "历史结束时间必须选择");
			}
	
			if ((null != cardManagementQueryDTO.getStarDate() && !cardManagementQueryDTO
					.getStarDate().equals(""))
					&& (null != cardManagementQueryDTO.getEnDate() && !cardManagementQueryDTO
							.getEnDate().equals(""))) {
				 Calendar cal = Calendar.getInstance();
				   cal.setTime(DateUtil.getCurrentDate());
				   cal.add(Calendar.DATE, -1);
				if (DateUtil.string2date(cardManagementQueryDTO.getEnDate())
						.after(
								DateUtil.string2date(DateUtil.formatStringDate(DateUtil.date2String(cal.getTime()))))) {
					addActionError("历史结束日期必须早于当天日期");
				}
				if (DateUtil.string2date(cardManagementQueryDTO.getEnDate())
						.before(
								DateUtil.string2date(cardManagementQueryDTO
										.getStarDate()))){
					addActionError("结束日期不能早于开始日期");
				}
			}
		}
		if (hasFieldErrors() || hasActionErrors()) {
			if (null != op && "1".equals(op)) {
				error_jsp = "/WEB-INF/pages/univer/cardmanage/cardMoneyList.jsp";
			} else {
				error_jsp = "/WEB-INF/pages/univer/cardmanage/cardTxn.jsp";
			}
		}
	}
	// 卡密重置
	public String passwordInit() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		cardManagementDTO.setTransferOutCard(cardManagementDTO.getCardNo());
		
		if(checkAgent().equals("input")){
			queryByCard();
			return "input";
		}
		
		// try{
		// entitySystemParameterDTO.setParameterCode(SystemInfoConstants.RESET_CARD_PIN_FEE);
		// entitySystemParameterDTO = (EntitySystemParameterDTO) sendService(
		// ConstCode.ENTITY_SYSTEMPARAMETER_SERVICE_VIEW,
		// entitySystemParameterDTO).getDetailvo();
		// cardManagementDTO.setServiceFee(entitySystemParameterDTO.getParameterValue());
		// }catch(Exception e){
		// this.logger.error(e.getMessage());
		// }
		
		return "passwordInit";
	}

	public String insertPassword() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		try {
			cardManagementDTO.setCardNo(cardManagementDTO.getTransferOutCard());
			cardManagementDTO.setOperationType("S59");
			sendService(ConstCode.CARDMANAGEMENT_OPERATION, cardManagementDTO);
			if (hasActionErrors()) {
				return "input";
			} else {
				this.addActionMessage("卡重置密码成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return queryByCard();
	}
    //卡安全信息设置初始化
	@SuppressWarnings("unchecked")
	public String  setCardSecurityInfoInit(){
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		String transferOutCard = cardManagementDTO.getCardNo();
		cardManagementDTO.setTransferOutCard(transferOutCard);
		if(checkAgent().equals("input")){
			queryByCard();
			return "input";
		}
		this.clearErrorsAndMessages();
		cardManagementDTO=(CardManagementDTO)sendService(ConstCode.CARDMANAGEMENT_SECURITY_QUERY, cardManagementDTO).getDetailvo();
			if (this.hasErrors()) {
				queryByCard();
				this.addActionMessage("查询卡安全信息失败!");
				return "input";
			}
			List<Map<?, ?>> cardSeuriyInfos=cardManagementDTO.getCardSeuriyInfos();
			this.getSession().put("cardSeuriyInfos", cardSeuriyInfos);
		List<CardBalanceDTO> balanceDTOList =(ArrayList<CardBalanceDTO>)this.getSession().get("balanceDtoList");
		cardManagementDTO.setCardBalanceDTOs(balanceDTOList);
		if(this.hasErrors()){
			queryByCard();
			return "input";
		}
		return "cardSecurityInfoInit";
	}
	//卡安全信息设置
	@SuppressWarnings("unchecked")
	public String setCardSecurityInfo() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		try {   
			cardManagementDTO.setOperationType("S57");
			List <String> accountIds=new ArrayList<String>();
			List <String> posSingleAmounts=new ArrayList<String>();
			List <String> posDailyAmounts=new ArrayList<String>();
			for(int i=0;i<accountId.length;i++){
				accountIds.add(accountId[i]);  
				posSingleAmounts.add(posSingleAmount[i]);
				posDailyAmounts.add(posDailyAmount[i]);
			}
			cardManagementDTO.setAccountList(accountIds);
			cardManagementDTO.setPosSingleAmounts(posSingleAmounts);
			cardManagementDTO.setPosDailyAmounts(posDailyAmounts);
			sendService(ConstCode.CARDMANAGEMENT_OPERATION, cardManagementDTO);
			if (hasActionErrors()) {
				cardManagementDTO.setCardSeuriyInfos((List<Map<?, ?>>)this.getSession().get("cardSeuriyInfos"));
				List<CardBalanceDTO> balanceDTOList =(ArrayList<CardBalanceDTO>)this.getSession().get("balanceDtoList");
				cardManagementDTO.setCardBalanceDTOs(balanceDTOList);
				return "input";
			} else {
				this.addActionMessage("卡安全信息设置成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			this.addActionError("卡安全信息设置失败!");
		}
		return queryByCard();
	}
	// 卡磁道重写
	public String cardMagnetismInit() {

		return "cardMagnetismInit";
	}

	public String insertMagentism() {
		checkCardNoAndServiceFee();
		if(hasActionErrors() || hasFieldErrors()){
			return INPUT;
		}
		return cardQuery();
	}

	// 卡片冻结
	public String cardFreezInit() {
		if(checkAgent().equals("input")){
			cardQuery();
			return "input";
		}
	
		// try{
		// cardManagementDTO.setServiceFee(getServiceFee(SystemInfoConstants.CARD_FRZZN_FEE));
		// }catch(Exception e){
		// this.logger.error(e.getMessage());
		// }
		return "cardFreezInit";
	}

	public String cardFreez() {
		try {
			checkCardNoAndServiceFee();
			if(hasActionErrors() || hasFieldErrors()){
				return INPUT;
			}
			cardManagementDTO.setOperationType("S46");
			sendService(ConstCode.CARDMANAGEMENT_OPERATION, cardManagementDTO);
			if (hasActionErrors()) {
				return "input";
			} else {
				this.addActionMessage("卡冻结成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return cardQuery();
	}

	// 卡片解冻
	public String noCardFreezInit() {
		if(checkAgent().equals("input")){
			cardQuery();
			return "input";
		}
		
		// try{
		// cardManagementDTO.setServiceFee(getServiceFee(SystemInfoConstants.CARD_UNFRZZN_FEE));
		// }catch(Exception e){
		// this.logger.error(e.getMessage());
		// }
		return "noCardFreezInit";
	}

	public String noCardFreez() {
		try {
			checkCardNoAndServiceFee();
			if(hasActionErrors() || hasFieldErrors()){
				return INPUT;
			}
			cardManagementDTO.setOperationType("S47");
			sendService(ConstCode.CARDMANAGEMENT_OPERATION, cardManagementDTO);
			if (hasActionErrors()) {
				return "input";
			} else {
				this.addActionMessage("卡解冻成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return cardQuery();
	}

	

	// 交易信息详细
	@SuppressWarnings("unchecked")
	public String cardTxnSelectDetail() throws BizServiceException {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		try {
			cardManagementDTO = (CardManagementDTO) sendService(
					ConstCode.CARD_SELECT_DETAIL, cardManagementDTO)
					.getDetailvo();
			cardManagementDTO.setTxnAmt(cardManagementDTO
					.getTxnAmt());
			if ("S32".equals(cardManagementDTO.getTxnType())
					|| "G32".equals(cardManagementDTO.getTxnType())) {
				cardManagementDTO.setTxnNum("2");
			} else {
				cardManagementDTO.setTxnNum("1");
			}
			Map<String, String> transTypeMap=(Map<String, String>)this.getApplication().get("transTypeMap");
			cardManagementDTO.setTxnType(transTypeMap.get(cardManagementDTO.getTxnType()));
			if (hasActionErrors()) {
				this.addActionMessage("交易详细信息查询失败");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
			logger.error("交易详细信息查询失败");
		}
		return "txnDetail";
	}

	
	public String enter(){
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		try {
			this.cardQuery();
			this.retrieveCardInit();
			if(hasActionErrors()){
				return "input";
			}
		} catch (Exception e) {
			
			this.logger.error(e.getMessage());
		}
		return "redemptionCard";
	}
	
	
	// 查询所有卡账户的安全信息
	public String viewCardSecurity() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		try {
			cardManagementQueryDTO.setCardNo(cardManagementDTO.getCardNo());
			ListPageInit(null, cardManagementQueryDTO);
			pageDataDTO = (PageDataDTO) sendService(
					ConstCode.CARDMANAGEMENT_SECURITY, cardManagementQueryDTO)
					.getDetailvo();
			if (hasActionErrors()) {
				return "input";
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return "serviceList";
	}

	public String cardMoneyInquery() {

		return "select";
	}

	public String editCardSecurity() {
		// try{
		// String
		// serviceFee=getServiceFee(SystemInfoConstants.CARD_SECURITY_SETTING_FEE);
		// cardManagementDTO.setServiceFee(serviceFee);
		// }catch(Exception e){
		// this.logger.error(e.getMessage());
		// }
		return "editCardSecurity";
	}

	public String updateCardSecurity() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		try {
			cardManagementDTO.setOperationType("S57");
			sendService(ConstCode.CARDMANAGEMENT_OPERATION, cardManagementDTO);
			if (hasActionErrors()) {
				return "input";
			} else {
				this.addActionMessage("卡片安全信息设置成功!");
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage());
		}
		return viewCardSecurity();
	}

	// 卡片账务调整
	public String adjustmentAccounts() {
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		if (null != adjust && !"".equals(adjust)) {
			String[] a = adjust.split(",");
			cardManagementDTO.setCardNo(a[0]);
			cardManagementDTO.setServiceFee(a[1]);
			cardManagementDTO.setAdjustType(a[2]);
			cardManagementDTO.setAdjustAmount(a[3]);			
			cardManagementDTO.setSysSeqNum(a[4]);	
			cardManagementDTO.setTxnType(a[6]);
			cardManagementDTO.setPayChnl(a[5]);
		}
		  cardManagementDTO.setCardFlag(cardManagementQueryDTO.getCardFlag().toString());
		 // '1':'偿还','2':'扣款'
			  if(cardManagementDTO.getAdjustType().equals("1")){
				  cardManagementDTO.setOperationType("S55");
			  }else if(cardManagementDTO.getAdjustType().equals("2")){
				  cardManagementDTO.setOperationType("S66");
			  }
		
		sendService(ConstCode.CARDMANAGEMENT_OPERATION, cardManagementDTO);
		if (!hasActionErrors()) {
			this.addActionMessage("卡账务调整成功!");
		}

		cardTxnSelect();
		return "adjust";
	}

//	public void validateUpdateCardSecuriry() {
//		String match = "[0-9]+";
//		if (cardManagementDTO.getPosSingleAmount() != null) {
//			if (!cardManagementDTO.getPosSingleAmount().matches(match)) {
//				this.addFieldError("cardManagementDTO.posSingleAmount",
//						"POS单笔最大限额必须是大于零的整数");
//			}
//		}
//		if (cardManagementDTO.getPosDailyAmount() != null) {
//			if (!cardManagementDTO.getPosDailyAmount().matches(match)) {
//				this.addFieldError("cardManagementDTO.posDailyAmount",
//						"POS每天最大限额必须是大于零的整数");
//			}
//		}
//		if (cardManagementDTO.getWebSingleAmount() != null) {
//			if (!cardManagementDTO.getWebSingleAmount().matches(match)) {
//				this.addFieldError("cardManagementDTO.webSingleAmount",
//						"web单笔最大限额必须是大于零的整数");
//			}
//		}
//		if (cardManagementDTO.getWebDailyAmount() != null) {
//			if (!cardManagementDTO.getWebDailyAmount().matches(match)) {
//				this.addFieldError("cardManagementDTO.webDailyAmount",
//						"web每天最大限额必须是大于零的整数");
//			}
//		}
//		if (cardManagementDTO.getWithoutPinAmount() != null) {
//			if (!cardManagementDTO.getWithoutPinAmount().matches(match)) {
//				this.addFieldError("cardManagementDTO.withoutPinAmount",
//						"无PIN金额最大限额必须是大于零的整数");
//			}
//		}
//	}
   //校验代理人信息，是否填写了
	public String checkAgent(){
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		if(isDisplay.equals("block")){
		if(null!=cardManagementDTO.getOwner()||!"".equals(cardManagementDTO.getOwner())){
			if(cardManagementDTO.getOwner().equals("2")){
			  if(cardManagementDTO.getAgentrName()==null||"".equals(cardManagementDTO.getAgentrName())){
				
				this.addFieldError("cardManagementDTO.agentrName",
				"代理人姓名不能为空！");
			}
              if(cardManagementDTO.getAgentrCertTypeNo()==null||"".equals(cardManagementDTO.getAgentrCertTypeNo())){
				
				this.addFieldError("cardManagementDTO.agentrCertTypeNo",
				"证件号码不能为空！");
			}
               if(cardManagementDTO.getStartDate()==null||"".equals(cardManagementDTO.getStartDate())){
	
	           this.addFieldError("cardManagementDTO.startDate",
	          "证件有效开始期不能为空！");
            }
	           if(cardManagementDTO.getEndDate()==null||"".equals(cardManagementDTO.getEndDate())){
		
		       this.addFieldError("cardManagementDTO.endDate",
		      "证件有效结束期不能为空！");
       }

		}
		}
		}
		
		if(this.hasFieldErrors()){
			
			return "input";
			
		}
		return "";
	}
	/**
	 * 单卡作废
	 * */
	public String invaild(){
	    BatchCardActionDTO dto=new BatchCardActionDTO();
	    String[] cardArray=new String[1];
	    if(cardManagementDTO.getTransferOutCard()!=null){
	        cardArray[0]=cardManagementDTO.getTransferOutCard().trim();
	    }else{
	        addActionError("卡作废失败！");
	        return "list";
	    }
	    dto.setCardNoArray(cardArray);
	    dto.setMemo(invalidReason);
	    dto.setUser(getUser().getUserId());
	    //记录机构代码
	    dto.setIssuerId(getUser().getEntityId());
	    sendService(ConstCode.SINGLE_INVALID,dto);
	    if(hasActionErrors()){
	        return "list";
	    }
	    addActionMessage("卡作废成功！");
	    return "list";
	}
	public CardManagementDTO getCardManagementDTO() {
		return cardManagementDTO;
	}

	public void setCardManagementDTO(CardManagementDTO cardManagementDTO) {
		this.cardManagementDTO = cardManagementDTO;
	}

	public ApplyAndBindCardDTO getApplyAndBindCardDTO() {
		return applyAndBindCardDTO;
	}

	public void setApplyAndBindCardDTO(ApplyAndBindCardDTO applyAndBindCardDTO) {
		this.applyAndBindCardDTO = applyAndBindCardDTO;
	}

	public CardManagementQueryDTO getCardManagementQueryDTO() {
		return cardManagementQueryDTO;
	}

	public void setCardManagementQueryDTO(
			CardManagementQueryDTO cardManagementQueryDTO) {
		this.cardManagementQueryDTO = cardManagementQueryDTO;
	}

	public PageDataDTO getPageDataDTO() {
		return pageDataDTO;
	}

	public void setPageDataDTO(PageDataDTO pageDataDTO) {
		this.pageDataDTO = pageDataDTO;
	}

	public CardBalanceDTO getCardBalanceDTO() {
		return cardBalanceDTO;
	}

	public void setCardBalanceDTO(CardBalanceDTO cardBalanceDTO) {
		this.cardBalanceDTO = cardBalanceDTO;
	}
	private void checkCardNoAndServiceFee(){
		if(null==cardManagementDTO){
			cardManagementDTO = new CardManagementDTO();
			}
		String cardNoRule="^[0-9]{13,19}$";
		String serviceFeeRule="^[0]|([1-9]{1}[0-9]{0,8})$";
		if(null == cardManagementDTO.getTransferOutCard().trim() || cardManagementDTO.getTransferOutCard().equals("") || !cardManagementDTO.getTransferOutCard().trim().matches(cardNoRule)){
			addFieldError("cardManagementDTO.transferOutCard", "请输入格式正确的卡号(13-19位数字)");
		}
		if(null == cardManagementDTO.getServiceFee() || cardManagementDTO.getServiceFee().equals("") || !cardManagementDTO.getServiceFee().matches(serviceFeeRule)){
			addFieldError("cardManagementDTO.serviceFee", "请输入格式正确的服务费(10位以内正整数)");
		}
	}

    /**
     * @return the invalidReason
     */
    public String getInvalidReason() {
        return invalidReason;
    }

    /**
     * @param invalidReason the invalidReason to set
     */
    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    /**
     * @return the invalidFlag
     */
    public String getInvalidFlag() {
        return invalidFlag;
    }

    /**
     * @param invalidFlag the invalidFlag to set
     */
    public void setInvalidFlag(String invalidFlag) {
        this.invalidFlag = invalidFlag;
    }

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getCardholderName() {
		return cardholderName;
	}

	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}

	
}